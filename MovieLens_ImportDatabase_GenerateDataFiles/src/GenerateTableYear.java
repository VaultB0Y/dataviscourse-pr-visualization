import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateTableYear {
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
			String tableName = "Genre";
			stmt.execute(
					"Create table if not exists " + tableName + "( genre varchar(12), avgRating decimal(5, 4), recordNum int );"
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

	public static void readFile() throws IOException, SQLException
	{
		File file = new File("Genre.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();

		while(true)
		{
        	String temp = null;
        	temp = br.readLine();
        	if(temp == null)
        		break;
        	temp = temp.replace("'", "");
        	String[] arr = temp.split(",");
        	String temp2 = "";
        	for(int i = 0; i < arr.length - 1; i ++)
        		temp2 += "'" + arr[i] + "', ";
        	temp2 += "'" + arr[arr.length - 1] + "'";
        	
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
    			String SQL = "insert into Genre(genre, avgRating, recordNum) values (" + temp2 + ");";
    			stmt.executeUpdate(SQL);
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
        br.close();
	}
	public static void main(String[] args) throws SQLException, IOException
	{
		init();
		readFile();
	}
}
