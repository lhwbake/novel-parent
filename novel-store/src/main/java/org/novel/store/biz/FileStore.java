package org.novel.store.biz;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.novel.store.connet.HadoopConnectionPool;
import org.novel.store.vo.StoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStore {
	
	private static Logger logger = LoggerFactory.getLogger(FileStore.class);
	
	private String fileName;
	
	private String content;
	
	private static int maxTryTimes = 3;

	public FileStore(String fileName, String content) {
		this.fileName = fileName;
		this.content = content;
	}


	public StoreResult saveFile(String fileName, String content) throws Exception {
		//重试次数
		int n = maxTryTimes;
		
		while (n>0) {
			
			FSDataOutputStream out = null;
			
			//原方法为先创建一个临时文件，再创建输入流读取临时文件内容，上传hadoop,
			//现直接创建一个字节数组，不再创建临时文件，提高了程序运行效率。
			//FileInputStream in = null;
			ByteArrayInputStream in = null;
			
			try{
				//创建临时文件
				//String tmpPath = createTmpFile(fileName, content);
					
				//连接hadoop
				HadoopConnectionPool conn = HadoopConnectionPool.getInstance();
				Configuration conf = conn.getConfiguration();
				
				//向hadoop里写文件
				FileSystem fs = FileSystem.get(conf);
				
				String filePath = "/novel/"+fileName;
				Path path = new Path(filePath);
				
				out = fs.create(path, true);
				in = new ByteArrayInputStream(content.getBytes());
				//in = new FileInputStream(tmpPath);
				
				IOUtils.copyBytes(in, out, 4096, true);
				out.flush();
				
				logger.info("file store success!filePath = : {}", filePath);		
				
				return new StoreResult(true, filePath);
				
			}catch(Exception e) {
				n--;
				logger.error("file can`t store to hadoop! tryTime = {}" , n);
				logger.error("error message ->{}" , e.getMessage());		
				
				//当n=0时抛出异常
				if(n==0) {
					throw e;
				}
				
			}finally{
				
				if(in != null) {
					in.close();
				}
				
				if(out != null) {
					out.close();
				}
				
			
			}
		}
		
		return new StoreResult(false);
		
		
	}
	
	
	/**
	 * 创建临时文件
	 * @param fileName  文件名
	 * @param content   文件内容
	 * @throws IOException 
	 */
	public static String createTmpFile(String fileName, String content) throws IOException {
		
		String tmpPath = "c:/tmp";
		File directory = new File(tmpPath);
		if(!directory.exists()){
			directory.mkdir();
		}
		
		File tmpFile = new File(directory + "/" + fileName + ".tmp");
		tmpFile.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
		bw.write(content);
		bw.flush();
		bw.close();		
		return tmpPath + "/" + fileName + ".tmp";
	}
	
	
	/**
	 * 删除10分钟前的目录清空目录
	 * @param directory
	 */
	public void deleteFile() {
		
		String tmpPath = "c:/tmp";
		File directory = new File(tmpPath);
		if(!directory.exists()){
			directory.mkdir();
		}
		while(true) {
			if(directory.isDirectory()) {
				File[] childrenFile = directory.listFiles();
				
				for(int i=0; i<childrenFile.length; i++) {
					File file = childrenFile[i];
					long lastModifiedTime = file.lastModified();
					long now = System.currentTimeMillis();
					long exp = 600000l;
					if(now-lastModifiedTime>exp) {
						file.delete();
					}
				}
				
				try {
					//每隔60s循环删除一次
					TimeUnit.SECONDS.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
		
	
}
