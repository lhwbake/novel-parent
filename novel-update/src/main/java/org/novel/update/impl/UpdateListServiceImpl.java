/**  
 *@Description:     
 */
package org.novel.update.impl;

import org.novel.common.utils.ArrayUtils;
import org.novel.update.biz.UpdateBiz;
import org.novel.update.facade.UpdateListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateListServiceImpl implements UpdateListService {
	
	Logger logger = LoggerFactory.getLogger(UpdateListServiceImpl.class);
	
	public static final int THREAD_NUM = 10;

	@Override
	public void update(String[] urls) {
		
		int length = urls.length;
		
		int subSize = length%THREAD_NUM==0?length/THREAD_NUM:length/THREAD_NUM+1;
		
		Object[] arry = ArrayUtils.splitAry(urls, subSize);
		
		for(Object o : arry) {
			
			UpdateBiz service = new UpdateBiz((Object[])o);
			
			new Thread(()->service.update()).start();
		}
		
				
	}	 

}
