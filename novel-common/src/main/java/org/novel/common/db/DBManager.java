 /**  
 *@Description:     
 */ 
package org.novel.common.db;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.alibaba.druid.pool.DruidDataSource;

public class DBManager {
	
	private DruidDataSource dataSource;
	
	private DBManager(){
				
		 try {
			//数据库连接池配置文件
			JAXPConfigurator.configure(DBPool.getDBPool().getPoolPath(), false);
			//数据库加载驱动类
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
		
		
	/*	InputStream in = DBManager.class.getClassLoader().getResourceAsStream("/jdbc.properties"); 
		
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(props);
		System.setProperties(props);
		dataSource = new DruidDataSource(true);
	*/	
	}
	
	
	/**
	 * @param poolName
	 * @return
	 * @throws SQLException
	 * @Author:lulei  
	 * @Description: 获取数据库连接
	 */
	public Connection getConnection(String poolName) throws SQLException {
		return DriverManager.getConnection(poolName);
	}
	
	/*public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}*/
	
	/**
	 *@Description: 内部静态类实现单例模式 
	 *@Author:lulei  
	 *@Version:1.1.0
	 */
	private static class DBManagerDao {
		private static DBManager dbManager = new DBManager();
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 返回数据库连接池管理类
	 */
	public static DBManager getDBManager() {
		return DBManagerDao.dbManager;
	}

}
