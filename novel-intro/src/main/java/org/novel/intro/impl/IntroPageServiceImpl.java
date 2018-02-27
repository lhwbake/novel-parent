 /**  
 *@Description:     
 */ 
package org.novel.intro.impl;  

import org.novel.intro.biz.IntroPageBiz;
import org.novel.intro.facade.IntroPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
public class IntroPageServiceImpl implements IntroPageService {
	
	Logger logger = LoggerFactory.getLogger(IntroPageServiceImpl.class);
	
	/*
	 * 线程数
	 */
	public static final int THREAD_NUM = 10;
	
	@Override
	public void run() {
	
		for(int i=0;i<THREAD_NUM;i++) {
			
			IntroPageBiz introPageBiz = new IntroPageBiz();

			new Thread(()->introPageBiz.intro(),"Thread-intro-"+i).start();
		}
	} 
}
