package org.novel.read.biz;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.producer.SendStatus;
import org.bson.Document;
import org.novel.common.entity.NovelChapterModel;
import org.novel.common.enumeration.NovelStatus;
import org.novel.common.utils.MongoDBUtil;
import org.novel.mq.facade.ProducerService;
import org.novel.read.db.ReadDB;
import org.novel.read.entity.NovelReadModel;
import org.novel.read.enumration.StoreType;
import org.novel.read.impl.ReadPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;

public class ReadBiz implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ReadBiz.class);

	@Override
	public void run() {
		ReadDB db = new ReadDB();
		try {
			//获取可以采集的章节信息
			NovelChapterModel chapter = db.getRandChapter(NovelStatus.UNDO.getCode());

			if (chapter != null) {
				ReadPage readPage = new ReadPage(chapter.getUrl());
				// 获取小说阅读页信息
				NovelReadModel novel = readPage.analyzer();
				//章节ID
			    novel.setId(chapter.getId());
				
				//mongoDB
				String dbName = "novel";
			    String collName = null;
			    if(novel.getId().hashCode()%2==0){
			    	collName = "novelOne";
			    }else{
			    	collName = "novelTwo";
			    }
			    MongoCollection<Document> coll = MongoDBUtil.instance.getCollection(dbName, collName);
			    Document doc = new Document();
			    doc.append("_id", novel.getId());
			    doc.append("content", novel.getContent());
			    coll.insertOne(doc);
			    
			    //重置小说内容为保存路径
			    novel.setContent(dbName+"/"+collName+"/"+novel.getId());
			    
				// 写入小说章节序号
				novel.setChapterId(chapter.getChapterId());

				// 写入章节发布时间
				novel.setChapterTime(chapter.getChapterTime());

				//小说正文存储位置
				//novel.setStoreType(StoreType.DB.getCode());
				novel.setStoreType(StoreType.MONGODB.getCode());

				//保存小说阅读页信息
				Integer result = db.saveNovelRead(novel);

				logger.info("线程{}->结果->{}", Thread.currentThread().getName(),result);

				// 将小说的章节设置成已采集
				db.updateChapterState(chapter.getId(),NovelStatus.DONE.getCode());
			}

		} catch (Exception e) {
			logger.error("发生异常->{}", e.getMessage());
		}
	}

	public void readToMq(ProducerService producerService) {
		ReadDB db = new ReadDB();
		try {
			// 获取可以采集的章节信息
			NovelChapterModel chapter = db.getRandChapter(NovelStatus.UNDO
					.getCode());

			if (chapter != null) {

				logger.info("线程{}->获取到的随机章节URL->{}", Thread.currentThread()
						.getName(), chapter.getUrl());

				ReadPage readPage = new ReadPage(chapter.getUrl());
				// 获取小说阅读页信息
				NovelReadModel novel = readPage.analyzer();

				SendStatus sendStatus = null;

				int retry = 0;
				do {
					// 将数据写入消息队列
					sendStatus = producerService.produce(novel.getId(),
							novel.getContent());

					logger.info("数据写入消息队列结果->{}	", sendStatus);

					retry++;

					// 重试次数
					if (retry > 5) {
						break;
					}
				} while (!SendStatus.SEND_OK.equals(sendStatus));

				if (sendStatus.equals(SendStatus.SEND_OK)) {

					// 写入小说章节序号
					novel.setChapterId(chapter.getChapterId());

					// 写入章节发布时间
					novel.setChapterTime(chapter.getChapterTime());

					// 小说内容保存位置
					novel.setContent("/novel/" + novel.getId());

					// 新增属性，小说正文存储位置
					novel.setStoreType(StoreType.HADOOP.getCode());

					// 保存小说阅读页信息
					db.saveNovelRead(novel);

					// 将小说的章节设置成已采集
					db.updateChapterState(chapter.getId(),
							NovelStatus.DONE.getCode());

					TimeUnit.MILLISECONDS.sleep(500);
				}

			} else {
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (Exception e) {
			logger.error("发生异常->{}", e.getMessage());
		}

	}
}
