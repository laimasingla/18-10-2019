package com.cg.ibs.cardmanagement.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
	
	private static final String PROPS = "/databaseInfo.properties";
	private static ConnectionProvider instance;

	private String url;
	private String user;
	private String pass;

	private ConnectionProvider() throws IOException {
	/* try {
	  Class.forName("oracle.jdbc.driver.OracleDriver");
	 } catch (ClassNotFoundException e) {
	  System.out.println("Could not load the driver");
	  return;
	 
	 }*/
	 Properties props = new Properties();
	 String file = ConnectionProvider.class.getResource(PROPS).getFile();
	 props.load(new FileInputStream(file));
	 url = props.getProperty("url");
	 user = props.getProperty("user");
	 pass = props.getProperty("pass");
	}

	public static ConnectionProvider getInstance() throws IOException {
	 if(instance==null) {
	  synchronized (ConnectionProvider.class) {
	   instance= new ConnectionProvider();
	  }
	 }
	 
	 return instance;
	}

	public Connection getConnection() throws SQLException {
	 return DriverManager.getConnection(url,user,pass);
	}


}
