package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String sql="select count(*) as rowCount from information_schema.columns where TABLE_SCHEMA='financeinfo' and TABLE_NAME='timeseriesplotter_close' and COLUMN_NAME='0001.hk'; ";
		
		
		try {
			
		ResultSet rs=DBTransaction.executeQuery(sql);
		rs.next();
		
			System.out.println(rs.getInt("rowCount"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
