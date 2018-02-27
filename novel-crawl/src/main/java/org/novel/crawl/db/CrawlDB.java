 /**  
 *@Description:     
 */ 
package org.novel.crawl.db;  

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.novel.common.db.DBServer;
import org.novel.common.entity.CrawlListInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

  
public class CrawlDB {
	
	Logger logger = LoggerFactory.getLogger(CrawlDB.class);
	
	//数据库连接池的别名
	private static final String POOLNAME = "proxool.novel";
	
	public void saveCrawlListInfos() {
		
		DBServer dbServer = new DBServer(POOLNAME);
		
		try {
			for(int i=1;i<1000;i++) {
				
				HashMap<Integer,Object> params = new HashMap<Integer, Object>();
				int j = 1;
				String url = "http://book.zongheng.com/store/c0/c0/b9/u0/p" + i + "/v0/s9/t0/ALL.html";
				
				logger.info("url-" + i + " ： {}", url);
				
				params.put(j++, i);
				params.put(j++, url);
				params.put(j++, 1);
				params.put(j++, "纵横中文网");
				params.put(j++, 60);
				
				String columns = "id, url, state, info, frequency";
				dbServer.insert("crawl_list", columns, params);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			dbServer.close();
		}
	}
		
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取更新列表页地址信息
	 */
	public List<CrawlListInfo> getCrawlListInfos() {
		List<CrawlListInfo> infos = new ArrayList<CrawlListInfo>();
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select * from crawl_list where `state` = '1'";
			ResultSet rs = dbServer.select(sql);
			while(rs.next()) {
				CrawlListInfo info = new CrawlListInfo();
				infos.add(info);
				info.setUrl(rs.getString("url"));
				info.setInfo(rs.getString("info"));
				info.setFrequency(rs.getInt("frequency"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return infos;
	}	
	
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取更新列表页地址信息
	 */
	public List<String> getCrawlUrlList(String state) {
		List<String> infos = new ArrayList<String>();
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "select url from crawl_list where `state` =" + state;
			ResultSet rs = dbServer.select(sql);
			while(rs.next()) {				
				infos.add(rs.getString("url"));			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return infos;
	}	
	
}
