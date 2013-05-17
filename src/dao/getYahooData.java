package dao;

		import java.net.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.io.*; 
public class getYahooData { 
		
		private static String   date;
		static 	String 			open,
								high,
								low,
								close,	
								volume,	
								adj_close;
	
	
	
	public static void main(String argv[]) throws Exception { 
		
		int count=0; 
		String sql="select count(Date) as datecount from timeseriesplotter_close" ;
		ResultSet rs=DBTransaction.executeQuery(sql);
		
		if(rs.next()){
			count=rs.getInt("datecount");
			System.out.println(count);
		}
		
		//getBatchData(1,1);	
		if(count==0){
			getFirstData("0001.hk");
			getBatchData(1,100);
		}else
		getBatchData(1,100);
		
			
	}
		
	public static void getAlterData(String stockName){
		
		
		
		String urlInfo="http://ichart.yahoo.com/table.csv?s="+stockName+
						"&a=01&b=1&c=2003&d=04&e=16&f=2013&g=d";
		URL url;
		String newLine="";
		BufferedReader in=null;
		try{
		
			url = new URL(urlInfo);
			in= new BufferedReader(new InputStreamReader(url.openStream()));
			newLine=in.readLine();
			
		}catch(FileNotFoundException fn){
			fn.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(newLine==null)
			return ;
		System.out.println(newLine);
//		System.out.println(stockName);	
			
		alterTable(stockName,"close");
		alterTable(stockName,"low");
		alterTable(stockName,"high");
		alterTable(stockName,"volume");
		alterTable(stockName,"open");
				
		try {
			while((newLine=in.readLine())!=null){
				String[] stock=newLine.split(",");
				date=stock[0];
				System.out.println(date);
				open=stock[1];
				high=stock[2];
				low=stock[3];		
				close=stock[4];
				volume=stock[5];
				
				String sqlclose="UPDATE timeseriesplotter_close set `"+stockName+"`="+close+" where `Date`='"+stock[0]+"';";//update close data
				String sqlopen="UPDATE timeseriesplotter_open set `"+stockName+"`="+open+" where `Date`='"+stock[0]+"';";//update close data
				String sqlhigh="UPDATE timeseriesplotter_high set `"+stockName+"`="+high+" where `Date`='"+stock[0]+"';";//update close data
				String sqllow="UPDATE timeseriesplotter_low set `"+stockName+"`="+low+" where `Date`='"+stock[0]+"';";//update close data
				String sqlvolume="UPDATE timeseriesplotter_volume set `"+stockName+"`="+volume+" where `Date`='"+stock[0]+"';";//update close data
				
				DBTransaction.executeUpdate(sqlclose);
				DBTransaction.executeUpdate(sqlopen);
				DBTransaction.executeUpdate(sqllow);
				DBTransaction.executeUpdate(sqlhigh);
				DBTransaction.executeUpdate(sqlvolume);
				
			
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		
		
		
	}

	private static void alterTable(String stockName,String tableName) {
		String sql="select count(*) as rowCount from information_schema.columns where TABLE_SCHEMA='financeinfo'" +
				"and TABLE_NAME='timeseriesplotter_"+tableName+"' and COLUMN_NAME='"+stockName+"'";
		int i=0;
		try {
			
			ResultSet rs=DBTransaction.executeQuery(sql);
			rs.next();
			
				i=rs.getInt("rowCount");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(i==0){
				String addStockameToMySQL="Alter table timeseriesplotter_"+tableName+" add `"+stockName+"` TINYTEXT;";
				DBTransaction.executeUpdate(addStockameToMySQL);
			}
	}
	public static void getFirstData(String stockName)throws Exception{
		
		
		String urlInfo="http://ichart.yahoo.com/table.csv?s="+stockName+
		"&a=01&b=1&c=2003&d=04&e=16&f=2013&g=d";
		URL url = new URL(urlInfo);        
		BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));   
		String newLine=in.readLine(); //获取标题
		System.out.println(newLine);
		
				
		newLine=in.readLine();//对应字段（日期，开盘价，最高，最低，收盘，交易量，调整收盘)
		
		while((newLine=in.readLine())!=null){
			String[] stock=newLine.split(",");
			date=stock[0];
			System.out.println(date);
			open=stock[1];
			high=stock[2];
			low=stock[3];		
			close=stock[4];
			volume=stock[5];
			
			String sqlclose="insert into timeseriesplotter_close(Date,`"+stockName+"`) values ('"+date+"','"+close+"')";//insert close data
			String sqlhigh="insert into timeseriesplotter_high(Date,`"+stockName+"`) values ('"+date+"','"+high+"')";//insert high data
			String sqlvolume="insert into timeseriesplotter_volume(Date,`"+stockName+"`) values ('"+date+"','"+volume+"')";//insert high data
			String sqllow="insert into timeseriesplotter_low(Date,`"+stockName+"`) values ('"+date+"','"+low+"')";//insert high data
			String sqlopen="insert into timeseriesplotter_open(Date,`"+stockName+"`) values ('"+date+"','"+open+"')";//insert high data
			
			
			DBTransaction.executeUpdate(sqlclose);
			DBTransaction.executeUpdate(sqlhigh);
			DBTransaction.executeUpdate(sqlvolume);
			DBTransaction.executeUpdate(sqllow);
			DBTransaction.executeUpdate(sqlopen);
			
		
		}
		
	}
	
	static void getBatchData(int m,int n)throws Exception{
		ArrayList<String> list=new ArrayList<String>();
		list.addAll(getStockName(m,n));
		Iterator<String> iter=list.iterator();
		
		while(iter.hasNext()){
		
			getAlterData(iter.next());
			
		}
	}
	
	
	//get stockName List
	/**
	 * @return ArrayList of stockName;
	 * 
	 */
	public static ArrayList<String> getStockName(int n,int m){
		ArrayList<String> stockName=new ArrayList<String>();
		for(int i=n;i<=m;i++){
			stockName.add(getNumber(i));
		}
		return stockName;
		
	}
	
	//convert integer to 0000.hk String
	public static String getNumber(int n){
		if(n<10)
			return "000"+n+".hk";
		else if(n<100)
			return "00"+n+".hk";
		else if(n<1000)
			return "0"+n+".hk";
		else
			return String.valueOf(n)+".hk";
			
		}
	}

	