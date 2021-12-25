import java.util.Arrays;

public class GeneralPurposeRegisters {
    byte[][] registers = new byte[16][2];

    public GeneralPurposeRegisters() {
    }
    public GeneralPurposeRegisters(byte[][] registers) {
        this.registers = registers;
    }

    public byte[][] getRegisters() {
        return registers;
    }

    public void setRegisters(byte[][] registers) {
        this.registers = registers;
    }

    @Override
    public String toString() {
        String n = "";
        n = n+"[";
        for(int i=0; i<registers.length; i++){
            n = n +Arrays.toString(registers[i])+",";
        }
        n= n.substring(0,n.length()-1) + "]";

        return "GeneralPurposeRegisters{" +
               n +
                '}';
    }
}
