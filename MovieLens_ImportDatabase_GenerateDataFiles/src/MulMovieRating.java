import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class att
{
	int gender; //M:0, F:1
	int age;
	//*0  1:  "Under 18"
	//*1 18:  "18-24"
	//*2 25:  "25-34"
	//*3 35:  "35-44"
	//*4 45:  "45-49"
	//*5 50:  "50-55"
	//*6 56:  "56+"
};

class ratingInfo
{
	int tot;
	int num;
	int totMale;
	int numMale;
	int totFem;
	int numFem;
	
	int tot1;
	int num1;
	int tot2;
	int num2;
	int tot3;
	int num3;
	int tot4;
	int num4;
	int tot5;
	int num5;
	int tot6;
	int num6;
	int tot7;
	int num7;
	
	ratingInfo()
	{
		tot = 0;
		num = 0;
		totMale = 0;
		numMale = 0;
		totFem = 0;
		numFem = 0;
		
		tot1 = 0;
		num1 = 0;
		tot2 = 0;
		num2 = 0;
		tot3 = 0;
		num3 = 0;
		tot4 = 0;
		num4 = 0;
		tot5 = 0;
		num5 = 0;
		tot6 = 0;
		num6 = 0;
		tot7 = 0;
		num7 = 0;
	}
};

public class MulMovieRating {
	
	static ArrayList <att> peo = new ArrayList <att> ();
	public static void main(String[] args) throws IOException
	{	
		File file = new File("ml-1m\\users.dat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		while(true)
		{
			String temp = null;
			temp = br.readLine();
			if(temp == null)
	    		break;
			String[] arr = temp.split("::");
			String g = arr[1];
			String a = arr[2];
			att tmp = new att();
			if(g.charAt(0) == 'M')
				tmp.gender = 0;
			else if(g.charAt(0) == 'F')
				tmp.gender = 1;
			else tmp.gender = -1;
			
			int age = Integer.parseInt(a);
			if(age == 1)
				tmp.age = 0;
			else if(age == 18)
				tmp.age = 1;
			else if(age == 25)
				tmp.age = 2;
			else if(age == 35)
				tmp.age = 3;
			else if(age == 45)
				tmp.age = 4;
			else if(age == 50)
				tmp.age = 5;
			else if(age == 56)
				tmp.age = 6;
			else tmp.age = -1;
			peo.add(tmp);
		}
		br.close();
		
		File file2 = new File("ml-1m\\" + "MovieRatingInfo" + ".csv");
		BufferedWriter wr = new BufferedWriter(new FileWriter(file2));
		wr.write("movieID,avgRating,recordNum,avgMale,numMale,avgFemale,numFemale," +
				"avgAge0,numAge0,avgAge1,numAge1,avgAge2,numAge2,avgAge3,numAge3," +
				"avgAge4,numAge4,avgAge5,numAge5,avgAge6,numAge6\n");
		
		int curm = -1;
		ratingInfo curR = null;
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
				
				if(curm != m)
				{
					if(curm != -1)
					{
						wr.write(curm + "," + (double)(curR.tot) / curR.num + "," + curR.num + "," + (double)(curR.totMale) / curR.numMale + "," + curR.numMale + ","
								+ (double)(curR.totFem) / curR.numFem + "," + curR.numFem + "," + (double)(curR.tot1) / curR.num1 + "," + curR.num1 + ","
								 + (double)(curR.tot2) / curR.num2 + "," + curR.num2 + "," + (double)(curR.tot3) / curR.num3 + "," + curR.num3 + ","
								 + (double)(curR.tot4) / curR.num4 + "," + curR.num4 + "," + (double)(curR.tot5) / curR.num5 + "," + curR.num5 + ","
								 + (double)(curR.tot6) / curR.num6 + "," + curR.num6 + "," + (double)(curR.tot7) / curR.num7 + "," + curR.num7 + "\n");
					}
					curR = new ratingInfo();
					curm = m;
				}
				att curA = peo.get(u - 1);
				curR.tot += r;
				curR.num ++;
				if(curA.gender == 0)
				{
					curR.totMale += r;
					curR.numMale ++;
				}
				else if(curA.gender == 1)
				{
					curR.totFem += r;
					curR.numFem ++;
				}
				
				if(curA.age == 0)
				{
					curR.tot1 += r;
					curR.num1 ++;
				}
				else if(curA.age == 1)
				{
					curR.tot2 += r;
					curR.num2 ++;
				}
				else if(curA.age == 2)
				{
					curR.tot3 += r;
					curR.num3 ++;
				}
				else if(curA.age == 3)
				{
					curR.tot4 += r;
					curR.num4 ++;
				}
				else if(curA.age == 4)
				{
					curR.tot5 += r;
					curR.num5 ++;
				}
				else if(curA.age == 5)
				{
					curR.tot6 += r;
					curR.num6 ++;
				}
				else if(curA.age == 6)
				{
					curR.tot7 += r;
					curR.num7 ++;
				}
			}
			br3.close();
		}
		wr.write(curm + "," + (double)(curR.tot) / curR.num + "," + curR.num + "," + (double)(curR.totMale) / curR.numMale + "," + curR.numMale + ","
				+ (double)(curR.totFem) / curR.numFem + "," + curR.numFem + "," + (double)(curR.tot1) / curR.num1 + "," + curR.num1 + ","
				 + (double)(curR.tot2) / curR.num2 + "," + curR.num2 + "," + (double)(curR.tot3) / curR.num3 + "," + curR.num3 + ","
				 + (double)(curR.tot4) / curR.num4 + "," + curR.num4 + "," + (double)(curR.tot5) / curR.num5 + "," + curR.num5 + ","
				 + (double)(curR.tot6) / curR.num6 + "," + curR.num6 + "," + (double)(curR.tot7) / curR.num7 + "," + curR.num7 + "\n");
	
		wr.close();
	}
}
