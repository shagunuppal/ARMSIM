
/*
 * ARM Simulator 
 * Computer Organization Rroject
 * Vishal Singh - 2016277 
 * Tanya Gupta - 2016107
 * Shagun Uppal - 2016088
 * 
*/

package arm;

public class write_back
{
	public static void WB(ARMSim A)
	{
		if(A.is_dataproc!=true) // No write back operation if there is no data processing
		{
			System.out.println("No WriteBack operation is required.");
		}
		else
		{
			if(!A.opCode.equals("10")) // if not a compare instruction
			{
				A.R[Integer.parseInt(A.dest_reg)]=A.answer;
				System.out.println(Integer.parseInt(A.answer,2)+" written back in R"+A.dest_reg);
			}
		}		
	}
	
}
