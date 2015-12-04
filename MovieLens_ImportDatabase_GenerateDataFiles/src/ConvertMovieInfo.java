import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertMovieInfo {
	
	public static int S(String genre)
	{
		if(genre.startsWith("Act"))
			return 0;
		if(genre.startsWith("Adv"))
			return 1;
		if(genre.startsWith("Ani"))
			return 2;
		if(genre.startsWith("Chi"))
			return 3;
		if(genre.startsWith("Com"))
			return 4;
		if(genre.startsWith("Cri"))
			return 5;
		if(genre.startsWith("Doc"))
			return 6;
		if(genre.startsWith("Dra"))
			return 7;
		if(genre.startsWith("Fan"))
			return 8;
		if(genre.startsWith("Fil"))
			return 9;
		if(genre.startsWith("Hor"))
			return 10;
		if(genre.startsWith("Mus"))
			return 11;
		if(genre.startsWith("Mys"))
			return 12;
		if(genre.startsWith("Rom"))
			return 13;
		if(genre.startsWith("Sci"))
			return 14;
		if(genre.startsWith("Thr"))
			return 15;
		if(genre.startsWith("War"))
			return 16;
		if(genre.startsWith("Wes"))
			return 17;
		return -1;
	}
	
	public static void main(String[] args) throws IOException
	{
		int[] flag = new int[18];
		File file = new File("ml-1m\\movies.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		File file2 = new File("ml-1m\\movies.csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
		wr.write("movieID,title,year,genre0,genre1,genre2,genre3,genre4,genre5,genre6,genre7,genre8,genre9,genre10,genre11,genre12,genre13,genre14,genre15,genre16,genre17\n");
		while(true)
	    {
			String temp = null;
        	temp = br.readLine();
        	if(temp == null)
        		break;
        	String[] arr = temp.split("::");
        	
        	int slen = arr[1].length();
        	String title = arr[1].substring(0, slen - 7);
        	String year = arr[1].substring(slen - 5, slen - 1);
        	for(int i = 0; i < title.length(); i ++)
        		if(title.charAt(i) == ',')
	        title = title.substring(0, i) + "." + title.substring(i + 1);
        	
        	for(int k = 0; k < 18; k ++)
        		flag[k] = 0;
        	
        	String[] type = arr[2].split("[|]");
        	int len = type.length;
        	for(int k = 0; k < len; k ++)
        	{
	        	flag[S(type[k])] = 1;
        	}
        	String res = arr[0] + "," + title + "," + year;
        	for(int i = 0; i < 18; i ++)
        		res += "," + flag[i];
        	res += "\n";
	        wr.write(res);
	    }
	    br.close();
	    wr.close();
	}
}
