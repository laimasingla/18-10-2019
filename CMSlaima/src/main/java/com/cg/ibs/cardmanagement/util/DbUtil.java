package com.cg.ibs.cardmanagement.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DbUtil {
private static Connection connection;
	
	public static Connection getConnection() {
		if (null == connection) {
			ResourceBundle bundle=ResourceBundle.getBundle("databaseInfo");
			String url=bundle.getString("url");
			String username=bundle.getString("user");
			String password=bundle.getString("pass");
			
			try(Connection conn = DriverManager.getConnection(url, username, password);){
				connection=conn;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;}


}
