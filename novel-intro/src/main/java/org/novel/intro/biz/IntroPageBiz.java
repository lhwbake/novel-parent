package org.novel.intro.biz;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.novel.common.entity.NovelChapterModel;
import org.novel.common.entity.NovelInfoModel;
import org.novel.common.enumeration.NovelStatus;
import org.novel.intro.db.InfoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntroPageBiz {

	Logger logger = LoggerFactory.getLogger(IntroPageBiz.class);
	
	private boolean flag = false;
	
	public void intro() {
		flag = true;
		try {
			InfoDB db = new InfoDB();
			while (flag) {
				//获取可以采集的简介页URL
				String url = db.getRandIntroPageUrl(NovelStatus.UNDO.getCode());
				
				logger.info("获取到的随机URL->{}", url);
				
				if (url != null) {
					IntroPage intro = new IntroPage(url);
					//获取简介页信息
					NovelInfoModel bean = intro.analyzer();
					
					if (bean != null) {
												
						ChapterPage chapterPage = new ChapterPage(bean.getChapterListUrl());
						//获取章节列表页信息
						List<NovelChapterModel> chapters = chapterPage.analyzer();
						
						logger.info("小说章节列表页信息->{}", chapters);
						
						//写入小说章节个数
						bean.setChapterCount(chapters == null ? 0 : chapters.size());
						
						logger.info("小说章节数->{}", bean.getChapterCount());
						
						//保存简介页信息
						db.updateNovelInfo(bean);
						//保存章节列表页信息
						db.saveNovelChpter(chapters, bean.getId());
						
						logger.info("小说信息  : {}",bean.getName() + "-" + bean.getType() + "-" + bean.getUrl() + "-" + bean.getAuthor());
					}
					TimeUnit.MILLISECONDS.sleep(500);
				} else {
					TimeUnit.MILLISECONDS.sleep(1000);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.intro();
		}
	}
}
