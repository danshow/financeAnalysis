package dao;

public class insertDemo {
	public static void main(String args[]){
		String sql="insert into timeseriesplotter_close(Date,0001.hk) values('2001-10-11','1000')";
		DBTransaction.executeQuery(sql);
		
	}

}
