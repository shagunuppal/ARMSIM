package ARM_SIM;

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
			else if(A.is_branch)
			{
				exec_branch(A);
			}
			else if(A.is_datatrans)
			{
				exec_datatrans(A);
			}
		}
		
	}
	
	public static void exec_dataproc(ARMSim A)
	{
		if (A.opCode.equals("0"))
	    {
	        A.answer = Integer.toHexString( Integer.decode(A.op1) & Integer.decode(A.op2) );
	        System.out.println("EXECUTE : Operation is : AND\n");
	        System.out.println("EXECUTE : AND "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
	    }
		else if (A.opCode.equals("1"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) ^ Integer.decode(A.op2) );
			System.out.println("EXECUTE : Operation is : EXCLUSIVE OR\n");
	        System.out.println("EXECUTE : EOR "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("2"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) - Integer.decode(A.op2) );
			System.out.println("EXECUTE : Operation is : SUBTRACT\n");
	        System.out.println("EXECUTE : SUB "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("4"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) + Integer.decode(A.op2) );
			System.out.println("EXECUTE : Operation is : ADDITION\n");
	        System.out.println("EXECUTE : ADD "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("5"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) + Integer.decode(A.op2) + 1);
			System.out.println("EXECUTE : Operation is : ADD WITH CARRY\n");
	        System.out.println("EXECUTE : ADC "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("6"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) - Integer.decode(A.op2) - 1);
			System.out.println("EXECUTE : Operation is : SUBTRACT WITH CARRY\n");
	        System.out.println("EXECUTE : SBC "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("10"))
		{
			if(Integer.parseInt(A.op1,16)==Integer.parseInt(A.op2,16))
			{
				A.Z=true;
			}
			else if(Integer.parseInt(A.op1,16)<Integer.parseInt(A.op2,16))
			{
				A.N=true;
			}
			
			System.out.println("EXECUTE : Operation is : COMPARE\n");
	        System.out.println("EXECUTE : CMP "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("12"))
		{
			A.answer = Integer.toHexString( Integer.decode(A.op1) | Integer.decode(A.op2) );
			System.out.println("EXECUTE : Operation is : INCLUSIVE OR\n");
	        System.out.println("EXECUTE : ORR "+Integer.parseInt(A.op1,16)+" and "+Integer.parseInt(A.op2,16)+"\n"); 
		}
		else if(A.opCode.equals("13"))
		{
			A.answer = Integer.toHexString(Integer.decode(A.op2));
			System.out.println("EXECUTE : Operation is : MOVE\n");
			System.out.println("MOV "+Integer.parseInt(A.op1,16)+" TO "+A.dest_reg);
		}
		else if(A.opCode.equals("15"))
		{
			A.answer = Integer.toHexString( ~ Integer.decode(A.op2));
			System.out.println("EXECUTE : Operation is : NOT\n");
	        System.out.println("EXECUTE : NOT OF "+Integer.parseInt(A.op2,16)+"\n"); 
		}
	} 
	
	public static void exec_branch(ARMSim A)
	{
		A.offset = Integer.toHexString( Integer.decode(A.instruction) & Integer.decode("0x00FFFFFF") );
		A.sign = Integer.toHexString((Integer.decode(A.offset) & Integer.decode("0x800000")) >> 23);
		
		if(A.sign.equals("1"))
		{
			A.offset = Integer.toHexString( Integer.decode(A.offset) | Integer.decode("0xFF000000") );
		}
		
		A.offset += 8;
	}
	
	public static void exec_datatrans(ARMSim A)
	{
		A.reg1 = Integer.toHexString(( Integer.decode(A.instruction) & Integer.decode("0x000F0000")) >> 16) ;
		A.dest_reg = Integer.toHexString(( Integer.decode(A.instruction) & Integer.decode("0x0000F000") ) >> 12); // destination register for load and data to be stored for store instruction
		
		if(A.load)
		{
			System.out.println("MOV "+Integer.parseInt(A.reg1,16)+" to "+Integer.parseInt(A.dest_reg,16));
		}
		else if(A.store)
		{
			System.out.println("STORE "+Integer.parseInt(A.dest_reg,16)+" to "+Integer.parseInt(A.reg1,16));
		}
	}
	
}
