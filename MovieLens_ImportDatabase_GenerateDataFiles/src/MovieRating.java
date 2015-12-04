import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MovieRating {

	public static void main(String[] args) throws IOException
	{	
		HashMap <Integer, HashMap <Integer, Integer> > res = new HashMap <Integer, HashMap <Integer, Integer> > ();
		
		for(int i = 0; i < 40; i ++)
		{
			File file3 = new File("ml-1m\\" + i + ".csv");
			BufferedReader br3 = new BufferedReader(new FileReader(file3));
			while(true)
			{
				String temp = null;
				temp = br3.readLine();
				if(temp == null)
		    		break;
		
				String[] arr = temp.split(",");
				int u = Integer.parseInt(arr[0]);
				Integer m = Integer.parseInt(arr[1]);
				Integer r = Integer.parseInt(arr[2]);
				
				
				
					if(!res.containsKey(m))
					{
						HashMap <Integer, Integer> hm = new HashMap <Integer, Integer> ();
						res.put(m, hm);
					}
					HashMap <Integer, Integer> hm = res.get(m);
					if(!hm.containsKey(r))
					{
						
						hm.put(r, 0);
					}
					Integer tmp = hm.get(r);
					tmp ++;
					hm.put(r, tmp);
					res.put(m, hm);
				
			}
			br3.close();
		}
		
		File file2 = new File("ml-1m\\" + "MovieRating" + ".csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
		wr.write("movieID, rating, recordNum\n");
		
		for(Integer gg: res.keySet())
		{
			for(Integer ss: res.get(gg).keySet())
			{
				Integer tmp = res.get(gg).get(ss);
				wr.write(gg + "," + ss + "," + tmp + "\n");
			}
		}
		wr.close();
	
	}
}
