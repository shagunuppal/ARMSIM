import java.util.*;
import java.io.*;
import java.lang.*;
public class Read
{
	static void Initialize(ARMSim A)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("1.MEM"));
			String S=br.readLine();
			while(S!=null)
			{
				String[] S1=S.trim().split("\\s+");
				A.H.put(S1[0].toLowerCase(),S1[1].toLowerCase());
				S=br.readLine();
			}
		}catch(Exception E){}

	}
}