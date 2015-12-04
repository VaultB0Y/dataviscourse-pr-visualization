import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GenreLocation {
	static ArrayList <String> peo = new ArrayList <String> ();
	static ArrayList <ArrayList <String> > mov = new ArrayList <ArrayList <String> > ();
	
	public static String S(String zip)
	{
		if(zip.startsWith("06")) return "CT";
		if(zip.compareTo("010") >= 0 && zip.compareTo("028") < 0) return "MA";
		if(zip.compareTo("039") >= 0 && zip.compareTo("050") < 0) return "ME";
		if(zip.compareTo("030") >= 0 && zip.compareTo("039") < 0) return "NH";
		if(zip.startsWith("07") || zip.startsWith("08")) return "NJ";
		if(zip.compareTo("006") >= 0 && zip.compareTo("010") < 0) return "PR";
		if(zip.compareTo("028") >= 0 && zip.compareTo("030") < 0) return "RI";
		if(zip.startsWith("05")) return "VT";
		if(zip.startsWith("201") || zip.compareTo("220") >= 0 && zip.compareTo("247") < 0) return "VA";
		if(zip.compareTo("197") >= 0 && zip.compareTo("200") < 0) return "DE";
		if(zip.startsWith("005") || zip.compareTo("10") >= 0 && zip.compareTo("15") < 0) return "NY";
		if(zip.compareTo("150") >= 0 && zip.compareTo("197") < 0) return "PA";
		if(zip.startsWith("569") || zip.compareTo("200") >= 0 && zip.compareTo("206") < 0) return "DC";
		if(zip.compareTo("206") >= 0 && zip.compareTo("220") < 0) return "MD";
		if(zip.compareTo("27") >= 0 && zip.compareTo("29") < 0) return "NC";
		if(zip.startsWith("29")) return "SC";
		if(zip.compareTo("247") >= 0 && zip.compareTo("269") < 0) return "WV";
		if(zip.compareTo("35") >= 0 && zip.compareTo("37") < 0) return "AL";
		if(zip.compareTo("32") >= 0 && zip.compareTo("35") < 0) return "FL";
		if(zip.compareTo("30") >= 0 && zip.compareTo("31") < 0 || zip.compareTo("398") >= 0 && zip.compareTo("400") < 0) return "GA";
		if(zip.compareTo("386") >= 0 && zip.compareTo("398") < 0) return "MS";
		if(zip.compareTo("370") >= 0 && zip.compareTo("386") < 0) return "TN";
		if(zip.compareTo("46") >= 0 && zip.compareTo("48") < 0) return "IN";
		if(zip.compareTo("40") >= 0 && zip.compareTo("43") < 0) return "KY";
		if(zip.compareTo("48") >= 0 && zip.compareTo("50") < 0) return "MI";
		if(zip.compareTo("43") >= 0 && zip.compareTo("46") < 0) return "OH";
		if(zip.compareTo("50") >= 0 && zip.compareTo("53") < 0) return "IA";
		if(zip.compareTo("550") >= 0 && zip.compareTo("568") < 0) return "MN";
		if(zip.startsWith("59")) return "MT";
		if(zip.startsWith("58")) return "ND";
		if(zip.startsWith("57")) return "SD";
		if(zip.startsWith("53") || zip.startsWith("54")) return "WI";
		if(zip.compareTo("60") >= 0 && zip.compareTo("63") < 0) return "IL";
		if(zip.startsWith("66") || zip.startsWith("67")) return "KS";
		if(zip.compareTo("63") >= 0 && zip.compareTo("66") < 0) return "MO";
		if(zip.startsWith("68") || zip.startsWith("69")) return "NE";
		if(zip.compareTo("716") >= 0 && zip.compareTo("730") < 0) return "AR";
		if(zip.compareTo("700") >= 0 && zip.compareTo("716") < 0) return "LA";
		if(zip.startsWith("73") || zip.startsWith("74")) return "OK";
		if(zip.compareTo("75") >= 0 && zip.compareTo("80") < 0) return "TX";
		if(zip.startsWith("85") || zip.startsWith("86")) return "AZ";
		if(zip.startsWith("84")) return "UT";
		if(zip.startsWith("80") || zip.startsWith("81")) return "CO";
		if(zip.compareTo("832") >= 0 && zip.compareTo("84") < 0) return "ID";
		if(zip.compareTo("870") >= 0 && zip.compareTo("885") < 0) return "NM";
		if(zip.compareTo("889") >= 0 && zip.compareTo("899") < 0) return "NV";
		if(zip.compareTo("820") >= 0 && zip.compareTo("832") < 0) return "WY";
		if(zip.compareTo("995") >= 0) return "AK";
		if(zip.compareTo("900") >= 0 && zip.compareTo("962") < 0) return "CA";
		if(zip.startsWith("967") || zip.startsWith("968")) return "HI";
		if(zip.startsWith("97")) return "OR";
		if(zip.compareTo("980") >= 0 && zip.compareTo("995") < 0) return "WA";
		else return zip;
		
	}
	
	public static void main(String[] args) throws IOException
	{
		File file = new File("ml-1m\\users.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		Integer uid = 0;
		while(true)
		{
			String temp = null;
			temp = br.readLine();
			if(temp == null)
	    		break;
			uid ++;
			String[] arr = temp.split("::");
			peo.add(S(arr[4]));
		}
		br.close();
		
		file = new File("ml-1m\\movies.dat");
		br = new BufferedReader(new FileReader(file));
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
		
		HashMap <String, HashMap <String, Rec> > res = new HashMap <String, HashMap <String, Rec> > ();
		
		File file3 = new File("ml-1m\\ratings.dat");
		BufferedReader br3 = new BufferedReader(new FileReader(file3));
		while(true)
		{
			String temp = null;
			temp = br3.readLine();
			if(temp == null)
	    		break;

			String[] arr = temp.split("::");
			int u = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int r = Integer.parseInt(arr[2]);
			
			String state = peo.get(u - 1);
			if(state.compareTo("AA") >= 0 && state.compareTo("ZZ") <= 0)
			{
				ArrayList <String> genre = mov.get(m - 1);
				for(String gg: genre)
				{
					if(!res.containsKey(gg))
					{
						HashMap <String, Rec> hm = new HashMap <String, Rec> ();
						res.put(gg, hm);
					}
					HashMap <String, Rec> hm = res.get(gg);
					if(!hm.containsKey(state))
					{
						Rec rec = new Rec();
						rec.num = 0;
						rec.tot = 0;
						hm.put(state, rec);
					}
					Rec tmp = hm.get(state);
					tmp.num ++;
					tmp.tot += r;
					hm.put(state, tmp);
					res.put(gg, hm);
				}
			}
		}
		br3.close();
				
			File file2 = new File("ml-1m\\" + "GenreLocation" + ".csv");
			BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
			wr.write("genre,state,avgRating,recordNum\n");
			
			for(String gg: res.keySet())
			{
				for(String ss: res.get(gg).keySet())
				{
					Rec tmp = res.get(gg).get(ss);
					wr.write(gg + "," + ss + "," + (double)(tmp.tot) / tmp.num + "," + tmp.num + "\n");
				}
			}
			wr.close();
	}
}
