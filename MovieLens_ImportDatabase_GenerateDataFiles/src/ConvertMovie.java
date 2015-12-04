import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertMovie {
	
	public static void main(String[] args) throws IOException
	{
		File file = new File("ml-1m\\movies.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		File file2 = new File("ml-1m\\movies.csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
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
	        wr.write(arr[0] + "," + title + "," + year + "," + arr[2] + "\n");
	    }
	    br.close();
	    wr.close();
	}
}
