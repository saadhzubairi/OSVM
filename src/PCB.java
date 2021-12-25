import java.util.ArrayList;

public class PCB {
    int p_id;
    byte p_priority;
    int p_sizecode;
    int p_sizedata;
    String p_filename;
    /*Interpreter inter;*/
    GeneralPurposeRegisters generalPurposeRegister = new GeneralPurposeRegisters();
    SpecialPurposeRegisters specialPurposeRegisters = new SpecialPurposeRegisters();
    /* page table implementation */
    Pagetable datapart = new Pagetable( (short) 512);
    Pagetable codepart = new Pagetable( (short) 512);
    int currentPC;
    int execution_time=0;
    int waiting_time=0;

    public PCB() {

    }

    void set_regs(){
        InstructionsInterpretorTool instructionsInterpretorTool = new InstructionsInterpretorTool();
        ArrayList ret = instructionsInterpretorTool.interpreter(p_filename);
        this.generalPurposeRegister = (GeneralPurposeRegisters) ret.get(1);
        this.specialPurposeRegisters = (SpecialPurposeRegisters) ret.get(0);
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public byte getP_priority() {
        return p_priority;
    }

    public void setP_priority(byte p_priority) {
        this.p_priority = p_priority;
    }

    public int getP_sizecode() {
        return p_sizecode;
    }

    public void setP_sizecode(int p_sizecode) {
        this.p_sizecode = p_sizecode;
    }

    public int getP_sizedata() {
        return p_sizedata;
    }

    public void setP_sizedata(int p_sizedata) {
        this.p_sizedata = p_sizedata;
    }

    public String getP_filename() {
        return p_filename;
    }

    public void setP_filename(String p_filename) {
        this.p_filename = p_filename;
    }

    public GeneralPurposeRegisters getGeneralPurposeRegister() {
        return generalPurposeRegister;
    }

    public void setGeneralPurposeRegister(GeneralPurposeRegisters generalPurposeRegister) {
        this.generalPurposeRegister = generalPurposeRegister;
    }

    public SpecialPurposeRegisters getSpecialPurposeRegisters() {
        return specialPurposeRegisters;
    }

    public void setSpecialPurposeRegisters(SpecialPurposeRegisters specialPurposeRegisters) {
        this.specialPurposeRegisters = specialPurposeRegisters;
    }

    public int getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(int execution_time) {
        this.execution_time = execution_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "p_id=" + p_id +
                ",\n\tp_priority=" + p_priority +
                ",\n\tp_sizecode=" + p_sizecode +
                ",\n\tp_sizedata=" + p_sizedata +
                ",\n\tp_filename='" + p_filename + '\'' +
                ",\n\tgeneralPurposeRegister=" + generalPurposeRegister +
                ",\n\tspecialPurposeRegisters=" + specialPurposeRegisters +
                ",\n\tdatapart=" + datapart +
                ",\n\tcodepart=" + codepart +
                ",\n\tcurrentPC=" + currentPC +
                ",\n\texecution_time=" + execution_time +
                ",\n\twaiting_time=" + waiting_time +
                "\n}";
    }
}
