
/*
 * ARM Simulator 
 * Computer Organization Rroject
 * Vishal Singh - 2016277 
 * Tanya Gupta - 2016107
 * Shagun Uppal - 2016088
 * 
*/

package arm;

public class memory
{
	public static void Bob(ARMSim A)
	{
		if(A.is_datatrans!=true) // No memory operation in case of branch and data processing instructions
		{
			System.out.println("There is no memory operation");
			return;
		}
		else // Memory operation in case of load / store instructions
		{
			try
			{
				A.imm= Long.toString((Long.parseLong(A.instruction,2) & Long.parseLong("2000000",16)) >> 25,2);
				String data="";
			
				if(!A.imm.equals("1")) // immediate offset 
				{
					A.offset = Long.toString(Long.parseLong(A.instruction,2) & Long.parseLong("FFF",16),2);
				}
				else // offset with shift
				{
					A.reg2 = Long.toString(Long.parseLong(A.instruction,2) & Long.parseLong("F",16),2);
					A.op2= A.R[Integer.parseInt(A.reg2,2)];
					Run.Shift_Check(A);
					A.offset=A.op2;
				}
					
				if(A.load) // load the value from the address of the offset added to the base register into the destination register 
				{
					A.R[Integer.parseInt(A.dest_reg,2)] = A.Heap [ Integer.parseInt(A.R[Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2)],2) +8 ];
					System.out.println("LOAD FROM "+(Integer.parseInt(A.R[(Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2))],2)+8)); 
					System.out.println("LOADED VALUE="+Integer.parseInt(A.R[Integer.parseInt(A.dest_reg,2)],2));
				}
				else if(A.store) // store the value present in the answer computed by ALU to the address specified in the memory 
				{
				
					A.Heap [ Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2) ] = A.answer;
					
					System.out.println("MEMORY : STORE VALUE "+Integer.parseInt(A.answer,2)+" TO ADDRESS OF R"+(Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2))+" \n");
				}
				}catch(Exception E)
				{
				E.printStackTrace();
			}
			return ;
		}
		
	}
	
}
