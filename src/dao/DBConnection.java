package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static final String DRIVER="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://172.16.3.131:3306/financeinfo";
	public static final String USERNAME="root";
	public static final String PASSWORD="Ab123456";

	public Connection getConnection(){
		Connection conn=null;
		while(conn==null){
			try{
				Class.forName(DRIVER);
				conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
				
				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException sqlex){
				sqlex.printStackTrace();
			}
			
		}
		return conn;
		
	}
	
	/*	
	public static void main(String args[]){
		
		System.out.println(getConnection());
	}*/
	
	
}
