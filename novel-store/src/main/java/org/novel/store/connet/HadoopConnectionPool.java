package org.novel.store.connet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 单例模式最优方案
 * 线程安全  并且效率高  
 *  
 */  
public class HadoopConnectionPool {
	
	private Logger logger = LoggerFactory.getLogger(HadoopConnectionPool.class);
		
	private static final int MIN_CONNECTIONS = 5; // 空闲池，最小连接数  
	
    private static final int MAX_CONNECTIONS = 20; // 空闲池，最大连接数  
      
    private static final int INITCONNECTIONS = 10;// 初始化连接数  
    
    private static final long LAZY_CHECK = 1000;// 延迟多少时间后开始 检查  
    
    private static final long PERIODCHECK = 2000;// 检查频率  
    
    private volatile List<Configuration> confList;
			
	/*
	 * 定义一个静态私有变量(不初始化，不使用final关键字，
	 * 使用volatile保证了多线程访问时instance变量的可见性，
	 * 避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用)
	 */
	private static volatile HadoopConnectionPool instance;
		
	/*
	 * 定义一个共有的静态方法，返回该类型实例
	 */
	public static HadoopConnectionPool getInstance() {
		
		//对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
		if(instance == null) {
			
			//同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
			synchronized (HadoopConnectionPool.class) {
				
				//未初始化，则初始instance变量
				if(instance == null) {					
					instance = new HadoopConnectionPool();
					instance.confList = new ArrayList<Configuration>(MAX_CONNECTIONS);
					instance.initPool();
					instance.cheackPool();
				}
			}
		}
		return instance;
	}
	
	
	/**
	 * 初始化连接池
	 */
	private void initPool() {
		//创建n个连接放入集合中 
		for(int i=0; i<INITCONNECTIONS; i++) {
			//连接hadoop
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://192.168.0.90:8020");
			this.confList.add(conf);
			logger.info("初始化-连接数->{}", this.confList.size());
		}
	}
	
	/**
	 * 获得连接
	 * @return
	 */
	public Configuration getConfiguration(){
		
		int length = this.confList.size();
		synchronized(this){
			if(length<MIN_CONNECTIONS) {
				addConn(MIN_CONNECTIONS-length);
			}
		}
		Configuration conf = null;
		
		Iterator<Configuration> iterator = this.confList.iterator();
		if(iterator.hasNext()) {
			conf = iterator.next();
		}
		logger.info("{}-获得连接-当前连接数->{}", Thread.currentThread().getName(),this.confList.size());
		return conf;
	}
	
	/**
	 * 增加连接
	 * @param n 需要增加的连接条数
	 */
	public synchronized void addConn(int n) {
		//创建n个连接放入集合中 
		for(int i=0; i<n; i++) {
			//连接hadoop
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://192.168.0.90:8020");
			this.confList.add(conf);
			logger.info("增加连接-当前连接数->{}", this.confList.size());
		}
	}
	
	/**
	 * 释放连接  
	 * @param n 需要释放连接的条数
	 */
    public synchronized void releaseConn(int n) {
    	Iterator<Configuration> iterator = this.confList.iterator();
    	for(int i=0;i<n;i++) {
    		if(iterator.hasNext()) {
    			iterator.remove();
    		}
    		logger.info("释放连接-当前连接数->{}", this.confList.size());
    	}
    }
    
    /**
     * 销毁清空  
     */
    public void destroy() {
    	this.confList.clear();
    }
	
	/**
	 * 定时任务，检查连接池连接数量
	 */
	 public void cheackPool() {  
	        
		new Timer().schedule(
			new TimerTask() {  
				 @Override  
				 public void run() {
					 int length = confList.size();
					 logger.info("检查连接-当前连接数->{}", length);
					 if(length<MIN_CONNECTIONS) {
						 addConn(MIN_CONNECTIONS-length);
						 logger.info("检查连接-增加连接数->{}", MIN_CONNECTIONS-length);
					 }else if(length>MAX_CONNECTIONS){
						 releaseConn(length-MAX_CONNECTIONS);
						 logger.info("检查连接-减少连接数->{}", length-MAX_CONNECTIONS);
					 }				 
				 }  
	         },
	         LAZY_CHECK,
	         PERIODCHECK
	      );  
	  }  
}
