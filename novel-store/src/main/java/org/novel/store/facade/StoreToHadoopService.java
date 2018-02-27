package org.novel.store.facade;

import org.novel.store.vo.StoreResult;

public interface StoreToHadoopService {

	public abstract StoreResult save(String fileName, String content) throws Exception;
}
