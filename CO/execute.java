public class execute
{
	public static void exec(ARMSim A)
	{
		if(A.exit!=true)
		{
			if(A.is_dataproc)
			{
				exec_dataproc(A);
			}
			else if(A.is_branch && A.branch)
			{
				exec_branch(A);
			}
			else if(A.is_datatrans)
			{
				exec_datatrans(A);
			}
		}
		return;
		
	}
	
	public static void exec_dataproc(ARMSim A)
	{
		if (A.opCode.equals("0"))
	    {
	        A.answer = Integer.toString( (Integer.parseInt(A.op1,2) & Integer.parseInt(A.op2,2)),2 );
	        System.out.println("EXECUTE : Operation is : AND");
	        System.out.println("EXECUTE : AND "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
	    }
		else if (A.opCode.equals("1"))
		{
			A.answer = Integer.toString( Integer.parseInt(A.op1,2) ^ Integer.parseInt(A.op2,2) ,2);
			System.out.println("EXECUTE : Operation is : EXCLUSIVE OR");
	        System.out.println("EXECUTE : EOR "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		else if(A.opCode.equals("2"))
		{
			A.answer = Integer.toString( Integer.parseInt(A.op1,2) - Integer.parseInt(A.op2,2),2 );
			System.out.println("EXECUTE : Operation is : SUBTRACT");
	        System.out.println("EXECUTE : SUB "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		else if(A.opCode.equals("4"))
		{
			A.answer = Integer.toString( (Integer.parseInt(A.op1,2) + Integer.parseInt(A.op2,2) ),2);
			System.out.println("EXECUTE : Operation is : ADDITION");
	        System.out.println("EXECUTE : ADD "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)); 
		}
		else if(A.opCode.equals("5"))
		{
			A.answer = Integer.toString( Integer.parseInt(A.op1,2) + Integer.parseInt(A.op2,2) + 1,2);
			System.out.println("EXECUTE : Operation is : ADD WITH CARRY");
	        System.out.println("EXECUTE : ADC "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}	
		else if(A.opCode.equals("6"))
		{
			A.answer = Integer.toString( Integer.parseInt(A.op1,2) - Integer.parseInt(A.op2,2) - 1,2);
			System.out.println("EXECUTE : Operation is : SUBTRACT WITH CARRY");
	        System.out.println("EXECUTE : SBC "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		else if(A.opCode.equals("10"))
		{
			if(Integer.parseInt(A.op1,2)==Integer.parseInt(A.op2,2))
			{
				A.Z=true;
			}
			else
				A.Z=false;
			if(Integer.parseInt(A.op1,2)<Integer.parseInt(A.op2,2))
			{
				A.N=true;
			}
			else
				A.N=false;
			
			System.out.println("EXECUTE : Operation is : COMPARE");
	        System.out.println("EXECUTE : CMP "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		else if(A.opCode.equals("12"))
		{
			A.answer = Integer.toString( Integer.parseInt(A.op1,2) | Integer.parseInt(A.op2,2) ,2);
			System.out.println("EXECUTE : Operation is : INCLUSIVE OR");
	        System.out.println("EXECUTE : ORR "+Integer.parseInt(A.op1,2)+" and "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		else if(A.opCode.equals("13"))
		{
			A.answer = A.op2;
			System.out.println("EXECUTE : Operation is : MOVE");
			System.out.println("MOV "+Integer.parseInt(A.op2,2)+" TO R"+A.dest_reg);

		}
		else if(A.opCode.equals("15"))
		{
			A.answer = Integer.toString( ~ Integer.parseInt(A.op2,2),2);
			System.out.println("EXECUTE : Operation is : NOT");
	        System.out.println("EXECUTE : NOT OF "+Integer.parseInt(A.op2,2)+"\n"); 
		}
		return;
	} 
	
	public static void exec_branch(ARMSim A)
	{
		try{
		//A.R[15]="0x"+Long.toString(Long.parseLong(A.R[15].substring(2,A.R[15].length()),16)-4,16);	
		A.offset = A.instruction.substring(8,32);
		A.sign = A.offset.substring(0,1);
		A.offset = Long.toString(Long.parseLong(A.offset,2)<<2,2);
		A.offset = A.offset.substring(2,A.offset.length());
		if(A.sign.equals("1"))
		{
			A.offset = Long.toString( Long.parseLong(A.offset,2) | Long.parseLong("FF000000",16) ,2);
		}
		else
			A.offset = Long.toString( Long.parseLong(A.offset,2) & Long.parseLong("00FFFFFF",16) ,2);
	
		A.offset = Long.toString(Long.parseLong(A.offset,2)+8,2);
		A.R[15]=Long.toString(Long.parseLong(A.R[15].substring(2,A.R[15].length()),16)+Long.parseLong(A.offset,2),2);
		A.R[15]="0x"+Long.toString(Long.parseLong(A.R[15].substring(1,A.R[15].length()),2),16);
	
		return;
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public static void exec_datatrans(ARMSim A)
	{
		try{


		A.reg1 = Long.toString(( Long.parseLong(A.instruction,2) & Long.parseLong("000F0000",16)) >> 16,2) ;
		A.dest_reg = Long.toString(( Long.parseLong(A.instruction,2) & Long.parseLong("0000F000",16)) >> 12,2); // destination register for load and data to be stored for store instruction
		
		if(A.load)
		{
			System.out.println("LOAD "+Integer.parseInt(A.reg1,2)+" to "+Integer.parseInt(A.dest_reg,2));
		}
		else if(A.store)
		{
			System.out.println("STORE "+Integer.parseInt(A.dest_reg,2)+" to "+Integer.parseInt(A.reg1,2));
		}
	}catch(Exception E)
	{
		E.printStackTrace();
	}
		return;
	
	}
	
}
