import java.util.*;
import java.io.*;
import java.lang.*;
public class ARMSim
{
	String[] R;
	String[] Heap;
	Map<String,String> H;
	String instruction, op1, op2, answer, reg1, reg2, dest_reg;
	boolean load, store, branch;
	boolean Z,N,C;
	long condition;
	String sign,offset,imm;
	boolean is_dataproc, is_branch, is_datatrans,is_read;
	String opCode;
	boolean exit;
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