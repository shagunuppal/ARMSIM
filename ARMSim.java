
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
public class ARMSim
{
	String[] R; // Array for registers
	String[] Heap; // Array for memory 
	Map<String,String> H; // HashMap for instruction address and the encoded instruction
	String instruction, op1, op2, answer, reg1, reg2, dest_reg; // variables to store instructions, operands, destination register, operand registers
	boolean load, store, branch; 
	boolean Z,N,C; // Conditional Flags to be set for the compare condition
	long condition;
	String sign,offset,imm; // variables for offset value, sign and immediate value
	boolean is_dataproc, is_branch, is_datatrans,is_read; 
	String opCode; // variable for operational code
	boolean exit; // to check for exit condition
	
	/*
	 * Constructor - to initialize all the variables 
	 */
	public ARMSim()
	{	
		R=new String[16];
		for(int i=0;i<16;i++)
			R[i]="0";
		Heap=new String[1000000];
		H=new HashMap<String,String>();
		condition=0;
		instruction=op1=op2=answer=reg1=reg2=dest_reg=opCode=imm=null;
		load=store=branch=is_dataproc=is_branch=is_datatrans=is_read=exit=Z=N=C=false;
	}
}