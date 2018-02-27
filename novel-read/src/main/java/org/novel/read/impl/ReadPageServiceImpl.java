 /**  
 *@Description:     
 */ 
package org.novel.read.impl;  

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.novel.read.biz.ReadBiz;
import org.novel.read.facade.ReadPageService;

public class ReadPageServiceImpl implements ReadPageService {
	
	//@Autowired(required=false)
	//private ProducerService producerService;
	
	//public static final int THREAD_NUM = 20;
	
	@Override
	public void run() {
		
		ExecutorService pool = Executors.newFixedThreadPool(50);
			
		ThreadPoolExecutor executor = null;
		if(pool instanceof ThreadPoolExecutor) {
			executor = (ThreadPoolExecutor)pool;
		}
		
		while(true) {							
			
			ReadBiz readBiz = new ReadBiz();
			executor.execute(readBiz);
						
			try {
				int activeCountNum = executor.getActiveCount();
				TimeUnit.MILLISECONDS.sleep(5);
				if (activeCountNum > 40) {
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			//存在数据库中
			//new Thread(()->readBiz.read(),"Thread-read-" + i).start();			
		}
		//pool.shutdown();
		
	} 

}
