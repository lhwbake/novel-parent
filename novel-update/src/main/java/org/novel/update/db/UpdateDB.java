 /**  
 *@Description:     
 */ 
package org.novel.update.db;  

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.novel.common.db.DBServer;
import org.novel.common.enumeration.NovelStatus;
import org.novel.common.utils.ParseMD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
public class UpdateDB {
	
	Logger logger = LoggerFactory.getLogger(UpdateDB.class);
	
	//数据库连接池的别名
	private static final String POOLNAME = "proxool.novel";
	
	/**
	 * @param urls
	 * @Author:lulei  
	 * @Description: 将采集到的简介页url信息保存到数据库中
	 */
	public void saveInfoUrls(List<String> urls) throws SQLException {
		if(urls == null || urls.size() < 1) {
			return;
		}
		for (String url : urls) {
			String id = ParseMD5.parseStrToMD5(url);
			int result = 0;
			if (!haveNovelInfo(id)) {
				result = insertInfoUrl(id, url);
				logger.info("新增数据条数->{}", result);
			}
		}
	}
	
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取更新列表页地址信息
	 */
	public int updateCrawlInfo(String id) {
		
		DBServer dbServer = new DBServer(POOLNAME);
		
		int result = 0;
		
		try {
			String sql = "update crawl_list set `state` = '0' where id = " + id;
			result = dbServer.update(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return result;
	}
	
	
	
	/**
	 * @param id
	 * @return
	 * @Author:lulei  
	 * @Description: 判断小说简介信息是否存在
	 */
	private boolean haveNovelInfo(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select sum(1) as count from novel_info where id='" + id +"'";
			ResultSet rs = dbServer.select(sql);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return true;
	}
	
	/**
	 * @param id
	 * @param state
	 * @Author:lulei  
	 * @Description: 更新简介信息的state值
	 */
	public int updateInfoState(String id, int state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "update novel_info set `state`='" + state + "' where id = '" + id + "'";
			return dbServer.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return 0;
	}
	
	/**
	 * @param id
	 * @param url
	 * @Author:lulei  
	 * @Description:将采集到的简介页url保存到数据库中
	 */
	private int insertInfoUrl(String id, String url) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, id);
			params.put(i++, url);
			long now = System.currentTimeMillis();
			params.put(i++, now);
			params.put(i++, now);
			params.put(i++, NovelStatus.UNDO.getCode());
			
			return dbServer.insert("novel_info", "id,url,createtime,updatetime,state", params);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return 0;
	}
}
