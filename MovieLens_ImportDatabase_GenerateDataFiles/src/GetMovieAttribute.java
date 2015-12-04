import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetMovieAttribute {
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
			String tableName = "Movie";
			stmt.execute(
					"Create table if not exists " + tableName + "( MovieID int, Title varchar(100), Year int, Genres varchar(12) );"
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
	public static boolean insertMovie(ArrayList <Movie> movie) throws SQLException
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
			String SQL = "insert into Movie(MovieID, Title, Year, Genres) values ";
			for(int i = 0; i < movie.size() - 1; i ++)
			{
				SQL += "('" + movie.get(i).MovieID + "', '" + movie.get(i).Title + "', '" + movie.get(i).Year + "', '" + movie.get(i).Genres + "'), ";
			}
			SQL += "('" + movie.get(movie.size() - 1).MovieID + "', '" + movie.get(movie.size() - 1).Title + "', '" + movie.get(movie.size() - 1).Year + "', '" + movie.get(movie.size() - 1).Genres + "');";
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
	public static void readMovieFile() throws IOException, SQLException
	{
		File file = new File("ml-1m\\movies.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int size = 3883;
		ArrayList <Movie> m_tmp = new ArrayList <Movie> ();
		int count = 0;
		int group = size / 500 + 1;
		for(int j = 0; j < group; j ++)
		{
			int i = 0;
			m_tmp.clear();
	        for(; i < 500 && count < size; i ++, count ++)
	        {
	        	String temp = null;
	        	temp = br.readLine();
	        	if(temp == null)
	        		break;
	        	temp = temp.replace("'", "");
	        	String[] arr = temp.split("::");
	        	String[] type = arr[2].split("[|]");
	        	int len = type.length;
	        	for(int k = 0; k < len; k ++)
	        	{
		        	Movie movie = new Movie();
		        	movie.MovieID = Integer.parseInt(arr[0]);
		        	int slen = arr[1].length();
		        	movie.Title = arr[1].substring(0, slen - 7);
		        	movie.Year = Integer.parseInt(arr[1].substring(slen - 5, slen - 1));
		        	movie.Genres = type[k];
		        	m_tmp.add(movie);
	        	}
	        }
	        insertMovie(m_tmp);
	        System.out.println(j);
        }
        br.close();
	}
	public static void main(String[] args) throws SQLException, IOException
	{
		init();
		readMovieFile();
	}
}
