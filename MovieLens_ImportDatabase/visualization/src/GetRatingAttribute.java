import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetRatingAttribute {
	//加载JDBC驱动(对应于mysql)
	static String driverName = "com.mysql.jdbc.Driver";
	//连接服务器和数据库WeiboData
	static String dbURL = "jdbc:mysql://localhost/MovieLens";
	//SQL Sever用户名
	static String userName = "sa";
	//SQL Sever密码
	static String userPwd = "123";
	
	public static void init() throws SQLException
	{
		//Connection
		Connection conn = null;
		//Statement
		Statement stmt = null;
		//ResultSet
		ResultSet rs = null;
		try 
		{
			try
			{
				Class.forName(driverName);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			stmt = conn.createStatement();
			String tableName = "Rating";
			stmt.execute(
					"Create table if not exists " + tableName + "( UserID int, MovieID int, Rating int, Timestamp varchar(15) );"
					);
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
	}
	public static boolean insertRating(Rating[] rating, int count) throws SQLException
	{
		//Connection
		Connection conn = null;
		//Statement
		Statement stmt = null;
		//ResultSet
		ResultSet rs = null;
		try 
		{
			try
			{
				Class.forName(driverName);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			stmt = conn.createStatement();
			String SQL = "insert into Rating(UserID, MovieID, Rating, Timestamp) values ";
			for(int i = 0; i < count - 1; i ++)
			{
				SQL += "('" + rating[i].UserID + "', '" + rating[i].MovieID + "', '" + rating[i].Rating + "', '" + rating[i].Timestamp + "'), ";
			}
			SQL += "('" + rating[count - 1].UserID + "', '" + rating[count - 1].MovieID + "', '" + rating[count - 1].Rating + "', '" + rating[count - 1].Timestamp + "');";
			stmt.executeUpdate(SQL);
			return true;
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
	}
	public static void readRatingFile() throws IOException, SQLException
	{
		File file = new File("ml-1m\\ratings.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int size = 1000209;
		Rating[] r_tmp = new Rating[10000];
		for(int i = 0; i < 10000; i ++)
        {
			r_tmp[i] = new Rating();
        }
		int count = 0;
		int group = size / 10000 + 1;
		for(int j = 0; j < group; j ++)
		{
			int i = 0;
	        for(; i < 10000 && count < size; i ++)
	        {
	        	String temp = br.readLine();
	        	String[] arr = temp.split("::");
	        	r_tmp[i].UserID = Integer.parseInt(arr[0]);
	        	r_tmp[i].MovieID = Integer.parseInt(arr[1]);
	        	r_tmp[i].Rating = Integer.parseInt(arr[2]);
	        	r_tmp[i].Timestamp = Integer.parseInt(arr[3]);
	        	count ++;
	        }
	        insertRating(r_tmp, i);
	        System.out.println(j);
		}
        br.close();
        
	}
	public static void main(String[] args) throws SQLException, IOException
	{
		init();
		readRatingFile();
	}
}
