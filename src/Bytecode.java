public class Bytecode {
    //Register-Register Instructions
    public static final byte MOV = 0x16;
    public static final byte ADD = 0x17;
    public static final byte SUB = 0x18;
    public static final byte MUL = 0x19;
    public static final byte DIV = 0X1A;
    public static final byte AND = 0x1B;
    public static final byte OR = 0x1C;

    //Register-Immediate Instructions
    public static final byte MOVI = 0x30; //48
    public static final byte ADDI = 0x31;
    public static final byte SUBI = 0x32;
    public static final byte MULI = 0x33;
    public static final byte DIVI = 0x34;
    public static final byte ANDI = 0x35;
    public static final byte ORI = 0x36;
    public static final byte BZ = 0x37;
    public static final byte BNZ = 0x38;
    public static final byte BC = 0x39;
    public static final byte BS = 0x3A;
    public static final byte JMP = 0x3B;

    //Memory Instructions
    public static final byte MOVL = 0x51;
    public static final byte MOVS = 0x52;

    //Single Operand Instructions
    public static final byte SHL = 0x71;
    public static final byte SHR = 0x72;
    public static final byte RTL = 0x73;
    public static final byte RTR = 0x74;
    public static final byte INC = 0x75;
    public static final byte DEC = 0x76;

    //Zero Operand Instructions
    public static final byte NOOP = (byte) 0xF2;
    public static final byte END = (byte) 0xF3;

    //Print Instruction - Added to test the functionality of the code
    public static final byte PRINT = (byte) 0xF4;
}
