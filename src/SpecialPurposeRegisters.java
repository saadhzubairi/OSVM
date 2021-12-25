import java.util.Arrays;

public class SpecialPurposeRegisters {

    public short[] registers = new short[15];
    public short codeBase = registers[1];
    public short codeLimit = registers[2];
    public short codeCounter = registers[3];
    public short dataBase = registers[4];
    public short dataLimit = registers[5];
    public short dataCounter = registers[6];
    public short stackBase = registers[7];
    public short stackCounter = registers[8];
    public short stackLimit = registers[9];
    public short programCounter = registers[10];
    public short instructionRegister = registers[11];
    public short flag = registers[12];
    // byte[] dataBase = (byte[]) (registers[12]);
    // byte[] dataLimit = (byte[]) (registers[13]);
    // byte[] dataCounter = (byte[]) (registers[14]);
    // byte[] stackBase = (byte[]) (registers[15]);


    public SpecialPurposeRegisters() {
    }

    @Override
    public String toString() {
        return "SpecialPurposeRegisters{" +
                "registers=" + Arrays.toString(registers) +
                ", codeBase=" + codeBase +
                ", codeLimit=" + codeLimit +
                ", codeCounter=" + codeCounter +
                ", dataBase=" + dataBase +
                ", dataLimit=" + dataLimit +
                ", dataCounter=" + dataCounter +
                ", stackBase=" + stackBase +
                ", stackCounter=" + stackCounter +
                ", stackLimit=" + stackLimit +
                ", programCounter=" + programCounter +
                ", instructionRegister=" + instructionRegister +
                ", flag=" + flag +
                '}';
    }



    public short[] getRegisters() {
        return registers;
    }

    public void setRegisters(short[] registers) {
        this.registers = registers;refresh();
    }

    public short getCodeBase() {
        return codeBase;
    }

    public void setCodeBase(short codeBase) {
        this.codeBase = codeBase;refresh();
    }

    public short getCodeLimit() {
        return codeLimit;
    }

    public void setCodeLimit(short codeLimit) {
        this.codeLimit = codeLimit;refresh();
    }

    public short getCodeCounter() {
        return codeCounter;
    }

    public void setCodeCounter(short codeCounter) {
        this.codeCounter = codeCounter;
        refresh();
    }

    public short getDataBase() {
        return dataBase;
    }

    public void setDataBase(short dataBase) {
        this.dataBase = dataBase;refresh();
    }

    public short getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(short dataLimit) {
        this.dataLimit = dataLimit;refresh();
    }

    public short getDataCounter() {
        return dataCounter;
    }

    public void setDataCounter(short dataCounter) {
        this.dataCounter = dataCounter;refresh();
    }

    public short getStackBase() {
        return stackBase;
    }

    public void setStackBase(short stackBase) {
        this.stackBase = stackBase;refresh();
    }

    public short getStackCounter() {
        return stackCounter;
    }

    public void setStackCounter(short stackCounter) {
        this.stackCounter = stackCounter;refresh();
    }

    public short getStackLimit() {
        return stackLimit;
    }

    public void setStackLimit(short stackLimit) {
        this.stackLimit = stackLimit;
        refresh();
    }

    public short getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(short programCounter) {
        this.programCounter = programCounter;refresh();
    }

    public short getInstructionRegister() {
        return instructionRegister;
    }

    public void setInstructionRegister(short instructionRegister) {
        this.instructionRegister = instructionRegister;refresh();
    }

    public short getFlag() {
        return flag;
    }

    public void setFlag(short flag) {
        this.flag = flag;
        refresh();
    }

    void refresh(){
        registers[1] = codeBase;
        registers[2] = codeLimit;
        registers[3] = codeCounter;
        registers[4] = dataBase;
        registers[5] = dataLimit;
        registers[6] = dataCounter;
        registers[7] = stackBase;
        registers[8] = stackCounter;
        registers[9] = stackLimit;
        registers[10] = programCounter;
        registers[11] = instructionRegister;
        registers[12] = flag;
    }
}

