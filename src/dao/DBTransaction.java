package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTransaction {
	
	public static ResultSet executeQuery(String sql){
		ResultSet rs=null;
		DBConnection dbc=new DBConnection();
		
		Connection con=dbc.getConnection();
		
		try {
			Statement stat=con.createStatement();
			rs=stat.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
				
	}
	public static int executeUpdate(String sql){
		DBConnection dbc=new DBConnection();
		Connection con=dbc.getConnection();
		
		int result=0;
		try {
			
			PreparedStatement stat=con.prepareStatement(sql);
			result= stat.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public static void main(String args[]){
		
		String sql="insert into timeseriesplotter_close(Date,`0001.hk`) values('2000-11-11','1000')";
		System.out.println(executeUpdate(sql));
		
	}
	

}
