 /**  
 *@Description:     
 */ 
package org.novel.common.db;  

import org.novel.common.utils.ClassUtil;

 
  
public class DBPool {
	private String poolPath;//数据库连接池的配置文件路径
	
	private DBPool() {
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 返回DBPool对象
	 */
	public static DBPool getDBPool() {
		return DBPoolDao.dbPool;
	}

	/**
	 *@Description: 静态内部类实现单例模式
	 *@Author:lulei  
	 *@Version:1.1.0
	 */
	private static class DBPoolDao{
		private static DBPool dbPool = new DBPool();
	}
	
	public String getPoolPath() {
		if (poolPath == null) {
			//如果poolPath为空，赋值为默认值
			poolPath = ClassUtil.getClassRootPath(DBPool.class) +"proxool.xml";
		}
		return poolPath;
	}

	/**
	 * @param poolPath
	 * @Author:lulei  
	 * @Description: 设置数据库连接池的配置文件路径
	 */
	public void setPoolPath(String poolPath) {
		this.poolPath = poolPath;
	}

	/**  
	 * @param args
	 * @Author:lulei  
	 * @Description:  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub  

	}

}
