import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class WriteCSVYear {
	//加载JDBC驱动(对应于mysql)
	static String driverName = "com.mysql.jdbc.Driver";
	//连接服务器和数据库WeiboData
	static String dbURL = "jdbc:mysql://localhost/MovieLens";
	//SQL Sever用户名
	static String userName = "sa";
	//SQL Sever密码
	static String userPwd = "123";
	
	public static void query() throws SQLException
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
			String sql = "select m.year as Year, avg(r.rating) as AverageRating, count(*) as RecordNum from (select distinct movieid, year from movie) as m, (select movieid, rating from rating) as r where m.movieid = r.movieid group by m.Year";
			rs = stmt.executeQuery(sql);

			File file = new File("Year.csv");
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			br.write("year,avgRating,recordNum\n");
			while(rs.next())
			{
		         //Retrieve by column name
		         int year  = rs.getInt("Year");
		         double avg = rs.getDouble("AverageRating");
		         int num = rs.getInt("RecordNum");

		         br.write(year + "," + avg + "," + num + "\n");
		     }
		     rs.close();
		     br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static void main(String[] args) throws SQLException, IOException
	{
		query();
	}
}
