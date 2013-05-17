package dao;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String sql="select count(*) from information_schema.columns where TABLE_SCHEMA='financeinfo' and TABLE_NAME='timeseriesplotter_close' and COLUMN_NAME='002201.hk'; ";
		int count;
		
		System.out.println(DBTransaction.executeQuery(sql).));
	}

}
