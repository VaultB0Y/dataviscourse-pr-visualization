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


public class GetUserAttribute
{
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
			String tableName = "User";
			stmt.execute(
					"Create table if not exists " + tableName + "( UserID int, Gender int, Age int, Occupation int, ZipCode varchar(15) );"
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
	public static boolean insertUser(ArrayList <User> user) throws SQLException
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
			String SQL = "insert into User(UserID, Gender, Age, Occupation, ZipCode) values ";
			for(int i = 0; i < user.size() - 1; i ++)
			{
				SQL += "('" + user.get(i).UserID + "', '" + user.get(i).Gender + "', '" + user.get(i).Age + "', '" + user.get(i).Occupation + "', '" + user.get(i).ZipCode + "'), ";
			}
			SQL += "('" + user.get(user.size() - 1).UserID + "', '" + user.get(user.size() - 1).Gender + "', '" + user.get(user.size() - 1).Age + "', '" + user.get(user.size() - 1).Occupation + "', '" + user.get(user.size() - 1).ZipCode + "');";
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
	public static void readUserFile() throws IOException, SQLException
	{
		File file = new File("ml-1m\\users.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList <User> u_tmp = new ArrayList <User> ();
        while(true)
        {
        	String temp = null;
        	temp = br.readLine();
        	if(temp == null)
        		break;
        	String[] arr = temp.split("::");
        	User user = new User();
        	user.UserID = Integer.parseInt(arr[0]);
        	if(arr[1].charAt(0) == 'F')
        		user.Gender = 0;
        	else if(arr[1].charAt(0) == 'M')
        		user.Gender = 1;
        	user.Age = Integer.parseInt(arr[2]);
        	user.Occupation = Integer.parseInt(arr[3]);
        	user.ZipCode = arr[4];
        	u_tmp.add(user);
        }
        br.close();
        insertUser(u_tmp);
	}
	public static void main(String[] args) throws SQLException, IOException
	{
		init();
		readUserFile();
	}
}
