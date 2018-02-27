package org.novel.store.impl;

import org.novel.store.biz.FileStore;
import org.novel.store.facade.StoreToHadoopService;
import org.novel.store.vo.StoreResult;

public class StoreToHadoopServiceImpl implements StoreToHadoopService{

	@Override
	public StoreResult save(String fileName, String content) throws Exception {
		
		FileStore fileStore = new FileStore(fileName, content);
		
		//TODO 启动一个线程定时删除c/:tmp下面的过期（10分钟）文件
		//new Thread(()->fileStore.deleteFile()).start();
		
		return fileStore.saveFile(fileName, content);
		
	}

}
