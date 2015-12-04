import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class SortByAge implements Comparator {
	 public int compare(Object o1, Object o2) {
	  Rating s1 = (Rating) o1;
	  Rating s2 = (Rating) o2;
	  if (s1.MovieID > s2.MovieID)
	   return 1;
	  return 0;
	 }
};
public class Convert {
	
	public static void main(String[] args) throws IOException
	{
		File file = new File("ml-1m\\ratings.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList <Rating> r_tmp = new ArrayList <Rating> ();
		while(true)
        {
        	String temp = null;
        	temp = br.readLine();
        	if(temp != null)
        	{
	        	String[] arr = temp.split("::");
	        	Rating r = new Rating();
	        	r.UserID = Integer.parseInt(arr[0]);
	        	r.MovieID = Integer.parseInt(arr[1]);
	        	r.Rating = Integer.parseInt(arr[2]);
	        	r.Timestamp = Integer.parseInt(arr[3]);
	        	r_tmp.add(r);
        	}
        	else break;
        }
        br.close();
        
        int count = 0;
        file = new File("ml-1m\\" + "ratings" + ".csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file));
		wr.write("userID,movieID,rating\n");
        while(count < r_tmp.size())
        {
        	Rating R = r_tmp.get(count);
	        wr.write(R.UserID + "," + R.MovieID + "," + R.Rating + "\n");
	        count ++;
        }
        wr.close();
        
        count = 0;
		for(int i = 0; i < 61; i ++)
		{
			File file2 = new File("ml-1m\\userid\\" + i + ".csv");
			wr = new BufferedWriter(new FileWriter(file2));
			wr.write("userID,movieID,rating\n");
	        while(count < r_tmp.size())
	        {
	        	Rating R = r_tmp.get(count);
	        	if((R.UserID - 1) / 100 == i)
	        	{
		        	wr.write(R.UserID + "," + R.MovieID + "," + R.Rating + "\n");
		        	count ++;
	        	}
	        	else break;
	        }
	        wr.close();
		}
        
        Collections.sort(r_tmp, new SortByAge());
        count = 0;
		for(int i = 0; i < 40; i ++)
		{
			File file2 = new File("ml-1m\\movieid\\" + i + ".csv");
			wr = new BufferedWriter(new FileWriter(file2));
			wr.write("userID,movieID,rating\n");
	        while(count < r_tmp.size())
	        {
	        	Rating R = r_tmp.get(count);
	        	if((R.MovieID - 1) / 100 == i)
	        	{
		        	wr.write(R.UserID + "," + R.MovieID + "," + R.Rating + "\n");
		        	count ++;
	        	}
	        	else break;
	        }
	        wr.close();
		}
	}
}
