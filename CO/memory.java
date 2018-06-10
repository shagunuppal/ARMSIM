public class memory
{
	public static void Bob(ARMSim A)
	{
		if(A.is_datatrans!=true)
		{
			System.out.println("There is no memory operation");
			return;
		}
		else
		{
			try{
			A.imm= Long.toString((Long.parseLong(A.instruction,2) & Long.parseLong("2000000",16)) >> 25,2);
			String data="";
			
			if(!A.imm.equals("1"))
			{
				A.offset = Long.toString(Long.parseLong(A.instruction,2) & Long.parseLong("FFF",16),2);
			}
			else
			{
				A.reg2 = Long.toString(Long.parseLong(A.instruction,2) & Long.parseLong("F",16),2);
				A.op2= A.R[Integer.parseInt(A.reg2,2)];
				Run.Shift_Check(A);
				A.offset=A.op2;
			}
				
			if(A.load)
			{
				data = A.Heap [ Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2) ];

			}
			else if(A.store)
			{
				A.Heap [ Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2) ] = A.answer;
				
				System.out.println("MEMORY : STORE VALUE "+Integer.parseInt(A.answer,2)+" TO ADDRESS "+(Integer.parseInt(A.offset,2) + Integer.parseInt(A.reg1,2))+" \n");
			}
			}catch(Exception E)
			{
				E.printStackTrace();
			}
			return ;
		}
		
	}
	
}