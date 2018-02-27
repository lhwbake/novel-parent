 /**  
 *@Description:     
 */ 
package org.novel.intro.db;  

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.novel.common.db.DBServer;
import org.novel.common.entity.NovelChapterModel;
import org.novel.common.entity.NovelInfoModel;
import org.novel.common.enumeration.NovelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
public class InfoDB {
	
	Logger logger = LoggerFactory.getLogger(InfoDB.class);
	
	//数据库连接池的别名
	private static final String POOLNAME = "proxool.novel";
	
	/**
	 * @param bean
	 * @Author:lulei  
	 * @Description: 更新小说的简介信息
	 */
	public void updateNovelInfo(NovelInfoModel bean) {
		if (bean == null) {
			return;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getName());
			params.put(i++, bean.getAuthor());
			params.put(i++, bean.getDesc());
			params.put(i++, bean.getType());
			params.put(i++, bean.getLastChapter());
			params.put(i++, bean.getChapterListUrl());
			params.put(i++, bean.getWordCount());
			params.put(i++, bean.getKeyWords());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, NovelStatus.DONE.getCode());
			
			String columns = "name,author,description,type,lastchapter,chapterlisturl,wordcount,keywords,updatetime,state";
			String condition = "where id = '" + bean.getId() + "'";
			int result  = dbServer.update("novel_info", columns, condition, params);
			logger.info("小说" + bean.getId() + "简介页信息保存成功条数->{}", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("小说->{}简介页信息保存失败->{}", bean.getId());
		} finally {
			dbServer.close();
		}
	}
	
	
	/**
	 * @param beans
	 * @param novelId
	 * @Author:lulei  
	 * @Description: 将采集到的章节信息保存到数据库中
	 */
	public void saveNovelChpter(List<NovelChapterModel> beans, String novelId){
		if (beans == null) {
			return;
		}
		for (NovelChapterModel bean : beans) {
			if (!haveNovelChapter(bean.getId())) {
				inserNovelChapter(bean, novelId);
			}
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断章节信息时候存在
	 */
	private boolean haveNovelChapter(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from novel_chapter where id='" + id + "'";
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
	 * @param bean
	 * @param novelId
	 * @Author:lulei  
	 * @Description: 将章节信息保存到数据库中
	 */
	private void inserNovelChapter(NovelChapterModel bean, String novelId) {
		if(bean == null) {
			return;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, bean.getId());
			params.put(i++, novelId);
			params.put(i++, bean.getUrl());
			params.put(i++, bean.getTitle());
			params.put(i++, bean.getWordCount());
			params.put(i++, bean.getChapterId());
			params.put(i++, bean.getChapterTime());
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, NovelStatus.UNDO.getCode());
			String columns = "id,novelid,url,title,wordcount,chapterid,chaptertime,createtime,state";
			int result = dbServer.insert("novel_chapter", columns, params);
			logger.info("小说" + bean.getId() + "章节信息保存成功条数->{}", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("小说->{}章节信息保存失败", bean.getId());
		} finally {
			dbServer.close();
		}
	}
	
	/**
	 * @param state
	 * @return
	 * @Author:lulei  
	 * @Description: 随机获取简介页url
	 */
	public String getRandIntroPageUrl(String state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			//String sql = "select url from novelinfo where state='" + state + "' order by rand() limit 1";
			String sql = "SELECT FLOOR(RAND() * COUNT(id)) AS offset FROM novel_info where state=" + state ;
			ResultSet rs = dbServer.select(sql);
			int offset = 0;
			while (rs.next()) {
				offset =  rs.getInt("offset");
			}
									
			sql = "SELECT url FROM novel_info where state=" + state + " LIMIT " + offset + ",1";
			ResultSet rs1 = dbServer.select(sql);
			while (rs1.next()) {
				return rs1.getString("url");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}
	
}
