
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
public class Run
{
	public static void main(String[] args)
	{
		// R[15] : Register for the program counter (PC)
		try
		{
			ARMSim A=new ARMSim();
			Read.Initialize(A);	
			long Curr=0;
			while(!A.exit)
			{
				if(!A.branch)
				{
					A.R[15]="0x"+Long.toString(Curr,16);
					Curr+=4;
				}
				else
				{
					Curr=Long.parseLong(A.R[15].substring(2,A.R[15].length()),16);
					A.branch=false;
					Curr+=4;
				}
				/*
				 * Each instruction passes through some basic steps as follows :
				 * Fetch - Reads from the instruction memory and updates the instruction register
				 * Decode - Reads the instruction register, reads operand1, operand2 from the register file, decides the operation to be performed in execute stage
				 * Execute - Executes the ALU operation based on ALUop
				 * Memory - Performs the memory function
				 * Write Back - Writes the results back to register file
				 */
				Fetch(A);
				Decode(A);
				execute.exec(A);
				memory.Bob(A);
				write_back.WB(A);
				System.out.println();
			}
		}catch(Exception E){}
	}
	
	public static void Fetch(ARMSim A)
	{
		// Fetching instruction from instruction memory 
		String 	S1=A.R[15]; 
		A.instruction=A.H.get(S1);
		System.out.println(A.instruction+" Fetched from "+S1);
	}
	
	public static void Decode(ARMSim A)
	{
		String S1=A.instruction;
		String[] S2=S1.trim().split("x");
		long T=Long.parseLong(S2[1],16);
		String S3=Long.toString(T,2);
		
		// Get instruction into a 32 bit format 
		if(S3.length()!=32)
		{
			String X="";
			for(int i=0;i<(32-S3.length());i++)
				X+="0";
			S3=X+S3;
		}
		
		// Checking for the type of the instruction : (i) Data Process (ii) Data Transfer (iii) Branch (iv) Exit instruction 
		if(S3.substring(4,6).equals("00"))
		{
			A.is_datatrans=false;
			A.is_branch=false;
			A.is_dataproc=true;
			System.out.println("Data Process");
		}
		if(S3.substring(4,6).equals("01"))
		{
			A.is_dataproc=false;
			A.is_branch=false;
			A.is_datatrans=true;
			System.out.println("Data Transfer");
		}
		if(S3.substring(4,6).equals("10"))
		{	
			A.is_datatrans=false;
			A.is_dataproc=false;
			A.is_branch=true;
			System.out.println("Branch");
		}
		
		// Basic SWI Instructions
		if(S3.substring(4,6).equals("11"))
		{
			
			if(A.instruction.substring(8,10).equals("11")) // Exit 
			{
				A.is_datatrans=false;
				A.is_dataproc=false;
				A.is_branch=false;
				A.exit=true;
			}
			else if(A.H.get(A.R[15]).equals("0xef00006c")) // Read
			{
				A.is_datatrans=false;
				A.is_dataproc=false;
				A.is_branch=false;
				try{
					BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
					A.R[0]=Long.toString(Long.parseLong(br.readLine()),2);
				}catch(Exception E){}
			}
			else if(A.H.get(A.R[15]).equals("0xef00006b")) // Print 
			{
				A.is_datatrans=false;
				A.is_dataproc=false;
				A.is_branch=false;
				System.out.println(Long.parseLong(A.R[1],2));
				
			}
		}
		
		A.instruction=S3;
		
		if(A.is_branch) //Branch Instruction
		{
			try{
			A.condition=(Long.parseLong(A.instruction,2) & Long.parseLong("F0000000",16))>> 28;
			System.out.println(A.condition+" N="+A.N+" Z="+A.Z);
			if(A.condition==0 && A.Z) //BEQ - branch equal
			{
				A.branch=true;
				System.out.println("BEQ");
			}
			else if(A.condition==1 && !A.Z) //BNE - branch not equal
			{
				A.branch=true;
				System.out.println("BNE");
			}
			else if(A.condition==10 && !A.N) //BGE - branch greater than equal to
			{
				A.branch=true;
				System.out.println("BGE");
			}
			else if(A.condition==11 && A.N) //BLT - branch less than
			{
				A.branch=true;
				System.out.println("BLT");
			}
			else if(A.condition==12 && !(A.N) && !(A.Z)) // BGT -branch greater than
			{
				A.branch=true;
				System.out.println("BGT");
			}
			else if(A.condition==13 && (A.N || A.Z)) //BLE - branch less than equal to
			{
				A.branch=true;
				System.out.println("BLE");
			}
			else if(A.condition==14) //Always
			{
				A.branch=true;
			}
			else
			{
				A.branch=false;
			}
			if(A.branch)
				System.out.println("BRANCHING");
			else
				System.out.println("NOT BRANCHING");
			}catch(Exception E){E.printStackTrace();}
		}	
		if(A.is_dataproc) // Data Processing Instruction
		{
			String Temp=S3.substring(7,11);
			A.opCode=Integer.toString(Integer.parseInt(Temp,2));
			A.reg1=S3.substring(12,16);
			String DEST=S3.substring(16,20);
			A.dest_reg=Integer.toString(Integer.parseInt(DEST,2));
			A.imm=S3.substring(6,7);
			A.op1=A.R[Integer.parseInt(A.reg1,2)];
			if(A.imm.equals("1")) // Immediate offset 
			{
				A.op2=S3.substring(24,32);
				long Shift=Long.parseLong(S3.substring(20,24),2);
				long X=Long.parseLong(A.op2,2);
				X=X <<Shift;
				A.op2=Long.toString(X,2);
			}
			else // Offset with 
			{				
				Shift_Check(A); 
				A.reg2=S3.substring(28,32);
				A.op2=A.R[Integer.parseInt(A.reg2,2)];
			}
		}
		if(A.is_datatrans) // Data Transfer Instruction - LDR / STR
		{
			A.store=false;
			A.load=false;
			String X=A.instruction.substring(11,12);
			if(X.equals("0"))
				A.store=true;
			else
				A.load=true;
		}
	}
	
	public static void Shift_Check(ARMSim A) // Shift method if the offset is not immediate 
	{
		String Bits=A.instruction.substring(20,28);
		String Type=A.instruction.substring(31,32);
		
		if(Type.equals("0"))
		{
			String W=Bits.substring(5,7);
			String Am=Bits.substring(0,5);
			if(W.equals("00"))
			{
				Long X=Long.parseLong(Am,2);
				Long T=Long.parseLong(A.op2,2);
				T <<=X;
				A.op2=Long.toString(T,2);
			}
			if(W.equals("01"))
			{
				Long X=Long.parseLong(Am,2);
				Long T=Long.parseLong(A.op2,2);
				T >>=X;
				A.op2=Long.toString(T,2);

			}
			if(W.equals("11"))
			{
				Long X=Long.parseLong(Am,2);
				Long T=Long.parseLong(A.op2,2);
				T= (T>>X)|(T <<(64-X));
				A.op2=Long.toString(T,2);
			}
		}
		else 
		{
			String W=Bits.substring(5,7);
			int Rno=Integer.parseInt(Bits.substring(0,4),2);
			String Am=Long.toHexString(Long.parseLong(A.R[Rno],16)&Long.parseLong("0000000F",16));
			if(W.equals("00"))
			{
				Long X=Long.parseLong(Am,16);
				Long T=Long.parseLong(A.op2,2);
				T <<=X;
				A.op2=Long.toString(T,2);
			}
			if(W.equals("01"))
			{
				Long X=Long.parseLong(Am,16);
				Long T=Long.parseLong(A.op2,2);
				T >>=X;
				A.op2=Long.toString(T,2);

			}
			if(W.equals("11"))
			{
				Long X=Long.parseLong(Am,16);
				Long T=Long.parseLong(A.op2,2);
				T= (T>>X)|(T <<(64-X));
				A.op2=Long.toString(T,2);
			}
		}
	}
}
