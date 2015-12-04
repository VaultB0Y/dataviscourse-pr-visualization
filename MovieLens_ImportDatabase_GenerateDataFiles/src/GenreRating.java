import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GenreRating {
	
	static ArrayList <ArrayList <String> > mov = new ArrayList <ArrayList <String> > ();
	public static void main(String[] args) throws IOException
	{
		File file = new File("ml-1m\\movies.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int MID = 1;
		while(true)
		{
			String temp = null;
			temp = br.readLine();
			if(temp == null)
	    		break;
			String[] arr = temp.split("::");
			
			Integer mid = Integer.parseInt(arr[0]);
			
			while(MID < mid)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				mov.add(tmp);
				MID ++;
			}
			MID ++;
			
			ArrayList<String> tmp = new ArrayList<String>();
			String[] type = arr[2].split("[|]");
        	int len = type.length;
        	for(int k = 0; k < len; k ++)
        	{
	        	tmp.add(type[k]);
        	}
        	mov.add(tmp);
		}
		br.close();
		
		
		
		HashMap <String, HashMap <Integer, Integer> > res = new HashMap <String, HashMap <Integer, Integer> > ();
		
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
				int m = Integer.parseInt(arr[1]);
				Integer r = Integer.parseInt(arr[2]);
				
				ArrayList <String> gen = mov.get(m - 1);
				for(String g: gen)
				{
					if(!res.containsKey(g))
					{
						HashMap <Integer, Integer> hm = new HashMap <Integer, Integer> ();
						res.put(g, hm);
					}
					HashMap <Integer, Integer> hm = res.get(g);
					if(!hm.containsKey(r))
					{
						
						hm.put(r, 0);
					}
					Integer tmp = hm.get(r);
					tmp ++;
					hm.put(r, tmp);
					res.put(g, hm);
				}
			}
			br3.close();
		}
		
		File file2 = new File("ml-1m\\" + "GenreRating" + ".csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
		wr.write("genre, rating, recordNum\n");
		
		for(String gg: res.keySet())
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
