 /**  
 *@Description:     
 */ 
package org.novel.read.db;  

import java.sql.ResultSet;
import java.util.HashMap;

import org.novel.common.db.DBServer;
import org.novel.common.entity.NovelChapterModel;
import org.novel.read.entity.NovelReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

  
public class ReadDB {
	
	Logger logger = LoggerFactory.getLogger(ReadDB.class);
	
	//数据库连接池的别名
	private static final String POOLNAME = "proxool.novel";
	
	/**
	 * @param bean
	 * @Author:lulei  
	 * @Description: 将采集到的阅读页信息保存到数据库中
	 */
	public int saveNovelRead(NovelReadModel bean){
		if (bean == null) {
			return 0;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			if (haveNovelRead(bean.getId())) {
				return 0;
			}
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getId());
			params.put(i++, bean.getUrl());
			params.put(i++, bean.getTitle());
			params.put(i++, bean.getWordCount());
			params.put(i++, bean.getChapterId());
			params.put(i++, bean.getContent());
			params.put(i++, bean.getStoreType());
			params.put(i++, bean.getChapterTime());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, now);
			String columns = "id,url,title,wordcount,chapterid,content,store_type,chaptertime,createtime,updatetime";
			//logger.info("准备保存小说->{}", bean.toString());
			//logger.info("sql参数->{}", params);
			int result = dbServer.insert("novel_chapter_detail", columns, params);
			return result;
			//logger.info("小说章节详情保存结果-{}", result);
		} catch (Exception e) {
			logger.info("小说章节详情保存失败->{}",e.getMessage());
		} finally {
			dbServer.close();
		}
		return 0;
	}
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断阅读页信息是否存在
	 */
	private boolean haveNovelRead(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from novel_chapter_detail where id='" + id + "'";
			ResultSet rs = dbServer.select(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return true;
	}
	
	/**
	 * @param state
	 * @return
	 * @Author:lulei  
	 * @Description:随机获取章节信息
	 */
	public NovelChapterModel getRandChapter(String state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			
			String sql = "SELECT FLOOR(RAND() * COUNT(id)) AS offset FROM novel_chapter where state=" + state;
			ResultSet rs = dbServer.select(sql);
			int offset = 0;
			while(rs.next()) {
				offset =  rs.getInt("offset");
			}
			
			//String sql = "select * from novelchapter where state='" + state + "' order by rand() limit 1";
			sql = "select id, url, chapterid, chaptertime from novel_chapter where state=" + state + " limit " + offset + ",1"; 
			ResultSet rs1 = dbServer.select(sql);
			while (rs1.next()) {
				NovelChapterModel bean = new NovelChapterModel();
				bean.setId(rs1.getString("id"));
				bean.setUrl(rs1.getString("url"));
				bean.setChapterId(rs1.getInt("chapterid"));
				bean.setChapterTime(rs1.getLong("chaptertime"));
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}
	
	/**
	 * @param id
	 * @param state
	 * @Author:lulei  
	 * @Description:更新章节信息的state值
	 */
	public void updateChapterState(String id, String state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "update novel_chapter set `state`='" + state + "' where id = '" + id + "'";
			int result =  dbServer.update(sql);
			//logger.info("章节信息更新成功条数->{}", result);
		} catch (Exception e) {
			logger.info("章节->{}信息更新异常", id);
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

}
