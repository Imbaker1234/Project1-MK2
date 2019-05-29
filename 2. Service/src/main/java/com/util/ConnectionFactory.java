package com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	private static ConnectionFactory cF = new ConnectionFactory();
	
	private ConnectionFactory() {
		super();
	}
	
	public static ConnectionFactory getInstance() {
		return cF;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		
		try {
			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream input = loader.getResourceAsStream("Application.properties");
			prop.load(input);
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("pass"));
			
		} catch (SQLException sale){
			System.out.println("[ERROR] - Unable to establish connection:" + sale.getMessage());
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("[ERROR] - File not found:" + fnfe.getMessage());
			
		} catch (IOException ioe) {
			System.out.println("[ERROR] - Unable to read from file:" + ioe.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
