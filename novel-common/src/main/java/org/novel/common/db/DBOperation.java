 /**  
 *@Description:     
 */ 
package org.novel.common.db;  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

public class DBOperation {
	
	private String poolName;//数据库连接池别名
	
	private Connection con = null;//数据库连接
	
	@Autowired
	private DBManager dbManager;
	
	public DBOperation(String poolName) {
		this.poolName = poolName;
	}
	
	public DBOperation() {
		
	}
	
	/**
	 * @Author:lulei  
	 * @Description:关闭数据库连接
	 */
	public void close() {
		try {
			if (this.con != null) {
				this.con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws SQLException
	 * @Author:lulei  
	 * @Description: 打开数据库连接
	 */
	private void open() throws SQLException {
		//先关闭后打开，防止数据库连接溢出
		close();
		//TODO 连接proxool
		this.con = DBManager.getDBManager().getConnection(this.poolName);
		
		//TODO连接druid
		//this.con = DBManager.getDBManager().getConnection();
		
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Author:lulei  
	 * @Description: sql语句参数转化
	 */
	private PreparedStatement setPres(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException{
		if (null == params || params.size() < 1) {
			return null;
		}
		PreparedStatement pres = this.con.prepareStatement(sql);
		for (int i = 1; i <= params.size(); i++) {
			if (params.get(i) == null) {
				pres.setString(i, "");
			} else if (params.get(i).getClass() == Class.forName("java.lang.String")) {
				pres.setString(i, params.get(i).toString());
			} else if (params.get(i).getClass() == Class.forName("java.lang.Integer")) {
				pres.setInt(i, (Integer) params.get(i));
			} else if (params.get(i).getClass() == Class.forName("java.lang.Long")) {
				pres.setLong(i, (Long) params.get(i));
			} else if (params.get(i).getClass() == Class.forName("java.lang.Double")) {
				pres.setDouble(i, (Double) params.get(i));
			} else if (params.get(i).getClass() == Class.forName("java.lang.Float")) {
				pres.setFloat(i, (Float) params.get(i));
			} else if (params.get(i).getClass() == Class.forName("java.lang.Boolean")) {
				pres.setBoolean(i, (Boolean) params.get(i));
			} else if (params.get(i).getClass() == Class.forName("java.sql.Date")) {
				pres.setDate(i, java.sql.Date.valueOf(params.get(i).toString()));
			} else {
				return null;
			}
		}
		return pres;
	}
	
	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @Author:lulei  
	 * @Description: 执行SQL语句，返回影响行数
	 */
	public int executeUpdate(String sql) throws SQLException {
		this.open();
		Statement state = this.con.createStatement();
		return state.executeUpdate(sql);
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Author:lulei  
	 * @Description: 执行sql语句，返回影响行数
	 */
	public int executeUpdate(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
		this.open();
		PreparedStatement pres = setPres(sql, params);
		if (null == pres) {
			return 0;
		}
		return pres.executeUpdate();
	}
	
	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @Author:lulei  
	 * @Description: 执行sql语句，返回结果集
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		this.open();
		Statement state = this.con.createStatement();
		return state.executeQuery(sql);
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Author:lulei  
	 * @Description:执行sql语句，返回结果集
	 */
	public ResultSet executeQuery(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
		this.open();
		PreparedStatement pres = setPres(sql, params);
		if (null == pres) {
			return null;
		}
		return pres.executeQuery();
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
