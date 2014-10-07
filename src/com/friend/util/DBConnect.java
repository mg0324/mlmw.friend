package com.friend.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 
 * @author 梅刚 2014-7-16 18:28
 *
 */
public class DBConnect {
	public static String username;
	public static String password;
	public static String url;
	public static String driver;
	public static Connection conn= null;
	
	static{
		InputStream is = DBConnect.class.getClassLoader().getResourceAsStream("db.properties");
		System.out.println(is);
		Properties prop = new Properties();
		try {
			prop.load(is);
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			driver = prop.getProperty("driver");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnect(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 关闭数据库连接
	 */
	public static void close(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
