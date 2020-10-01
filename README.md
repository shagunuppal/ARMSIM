# ARM_Simimulator 

<b> Course Project for Computer Organisation (CSE112) </b><br>
<b> Instructor:</b> Dr. Rahul Nagpal 

The objective of this project is to design and build an ARM Simulator. The project should be written in Java, which would read the instruction from instruction memory, decode the instruction, read the register, execute the operation, and write back to the register file.
The instruction set supported is same as given in the lecture notes. (MOV, ADD, SUB, LDR, STR, etc). Basic SWI instructions should be implemented: Read, Print, Exit etc (Read should be console input in Java). The execution of instruction continues till it reaches instruction “swi 0x11”. In other words as soon as instruction reads “0xEF000011”, the simulator stops execution.
All the instructions in the given in the input MEM file are executed as per the functional behavior of the instructions. Each instruction must go through the following phases: <br>
<b>
- Fetch <br>
- Decode <br>
- Execute <br>
- Memory <br>
- Writeback <br>
</b>
