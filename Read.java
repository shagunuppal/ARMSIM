
/*
 * ARM Simulator 
 * Computer Organization Rroject
 * Vishal Singh - 2016277 
 * Tanya Gupta - 2016107
 * Shagun Uppal - 2016088
 * 
*/

package arm;

import java.util.*;
import java.io.*;
import java.lang.*;
public class Read
{
	
	// Read the .MEM file for instructions and maintain the hashmap for it
	static void Initialize(ARMSim A)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("3.MEM"));
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
