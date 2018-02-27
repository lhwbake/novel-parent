package org.novel.update.biz;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.novel.update.db.UpdateDB;
import org.novel.update.impl.UpdateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateBiz {
	
	private static Logger logger = LoggerFactory.getLogger(UpdateBiz.class);
	
	private boolean flag = false;
	
	private Object[] urls;
	
	public static final int  FREQUENCY = 10;		
	
	public UpdateBiz(Object[] urls) {
		this.urls = urls;
	}
	
	public void update() {
		flag = true;
		UpdateDB db = new UpdateDB();
		while (flag) {
			for(Object url : urls) {
				try {
					UpdateList updateList = new UpdateList(url.toString());
					
					//获取更新列表页URL信息
					List<String> urls = updateList.getPageUrl(true);
					logger.info("更新列表页->{}", urls);
					
					db.saveInfoUrls(urls);
					
					TimeUnit.SECONDS.sleep(FREQUENCY);
				} catch (SQLException e) {
					logger.info("occur error->{}",e.getMessage());
				}catch (InterruptedException e) {
					logger.info("occur error->{}",e.getMessage());
				}catch (Exception e) {
					logger.info("occur error->{}",e.getMessage());
				} 			
			}
		}
		
	}

}
