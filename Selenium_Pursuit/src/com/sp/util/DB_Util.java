package com.sp.util;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;

public class DB_Util {
	
	public static ArrayList<String> results = new ArrayList<String>();
	
	static Connection conn = null; 
	static Statement stmt = null;
	static ResultSet rs = null;
	
	
	public static void connect2DB(String DB_URL,  String dbName, String JDBC_DRIVER, String userName, String password){

		// table lists down popular JDBC driver names and database URL.
		/*
		RDBMS 		JDBC_driver_name 					URL_format

		MySQL 		com.mysql.jdbc.Driver 				jdbc:mysql://hostname/ databaseName
		ORACLE 		oracle.jdbc.driver.OracleDriver		jdbc:oracle:thin:@hostname:port Number:databaseName
		DB2 		COM.ibm.db2.jdbc.net.DB2Driver 		jdbc:db2:hostname:portNumber/databaseName
		Sybase 		com.sybase.jdbc.SybDriver 			jdbc:sybase:Tds:hostname: port


		 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		 */

		/*
		 * Register JDBC Driver: You must register your driver in your
		 * program before you using it. 
		 * Registering the driver is the process by which the Oracle driver's class file is loaded into memory so
		 * it can be utilized as an implementation of the JDBC interfaces.
		 */

		
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER).newInstance();
			
			// Establish Connection to DB
			System.out.println("\n##########################################################");
			System.out.println("Connecting to DB : " + dbName + " - " + DB_URL);
			
			conn = DriverManager.getConnection(DB_URL + dbName, userName, password);
		
			System.out.println("Connection Established to DB : " + dbName + " - " + DB_URL);
			System.out.println("##########################################################\n");
			
			
			//Create Statement object
			stmt = conn.createStatement();
			
			//Create DatabaseMetaData object
			DatabaseMetaData dbmd = conn.getMetaData();
			
			// get getDriverName
			String driverName   = dbmd.getDriverName();
			
			// get getDriverVersion
			String[] driverVersion = dbmd.getDriverVersion().split(" ");
						
			//System.out.println(dbmd.getJDBCMajorVersion());
			//System.out.println(dbmd.getJDBCMinorVersion());
						
			
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Product Name : " + dbmd.getDatabaseProductName());
			System.out.println("Product Version : " + dbmd.getDatabaseProductVersion());
			//System.out.println("Product Major Version : " + dbmd.getDatabaseMajorVersion());
			//System.out.println("Product Minor Version : " + dbmd.getDatabaseMinorVersion());
			System.out.println("Driver Name : " + driverName);
			System.out.println("Driver Version : " + driverVersion[0].trim());
			//System.out.println("Driver Major Version : " + dbmd.getDriverMajorVersion());
			//System.out.println("Driver Minor Version : " + dbmd.getDriverMinorVersion());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
					
			
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			
			e.printStackTrace();
		}catch (SQLException e) {
			
			e.printStackTrace();
		}

		

	}

	public static void tableDetail(String tableName){
		
		
		try {
			//Execute Query to get Column Count
			rs = stmt.executeQuery("select count(*) from information_schema.columns where table_name='" + tableName + "'");
					
			
			if(rs.next())
				System.out.println("Table Name : " + tableName + " | Number of Columns : " +  rs.getInt(1) + "\n");
				
			//Execute Query to get Column Names
			rs = stmt.executeQuery("select * from " + tableName + " limit 2");
			//Get Result Meta data values
			ResultSetMetaData rsmd=rs.getMetaData();
			
			int columnCount=rsmd.getColumnCount();
			System.out.println("Column Names : ");
			for(int i = 1; i <= columnCount; i++){
				System.out.println(i + " : " + rsmd.getColumnName(i));
			}
			
			//Execute Query to get row count
			rs = stmt.executeQuery("select count(*) from " + tableName + ";");
			
			if(rs.next()) 
				System.out.println("\nTable Name : " + tableName + " | Number of Rows : " +  rs.getInt(1) +"\n");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public static void executeQuery(String sql){
		
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				// Get Values from url column
			/*	int id = rs.getInt("id");
				String heading2 = rs.getString("heading2");
				String address1 = rs.getString("address1");
				String address2 = rs.getString("address2");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip_code = rs.getString("zip_code");
				String store_phone = rs.getString("store_phone");*/
				//String url = rs.getString("url");
				/*String meta_title = rs.getString("meta_title");
				String meta_description = rs.getString("meta_description");
*/
				// Display values
				/*System.out.print("id: " + id);
				System.out.print("heading2: " + heading2);
				System.out.print(", address1: " + address1);
				System.out.print(", address2: " + address2);
				System.out.println(", city: " + city);
				System.out.println(", state: " + state);
				System.out.println(", zip_code: " + zip_code);
				System.out.println(", store_phone: " + store_phone);
				System.out.println("url: " + url);
				System.out.println(", meta_title: " + meta_title);
				System.out.println(", meta_description: " + meta_description);
*/
				//Store in an array
				results.add(rs.getString(1));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		cleanUp();

		
	}
	
	private static void cleanUp(){
		
		// Clean-up environment
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("------ Database Connection Closed Successfully ------\n");
		
	}
}
