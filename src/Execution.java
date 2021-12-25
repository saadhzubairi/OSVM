import java.util.Arrays;

public class Execution {
    queue <Process> rrQ = new queue<Process>();
    queue <Process> fcfsQ = new queue<Process>();
    queue <Process> runQ = new queue<Process>();
    String filename;
    Process p;
    int priority;
    short[] code;
    int clockcycle = 0;
    int timeslice = 0;

    public Execution( ) {

    }


    public void load ( String filename) throws Exception {
        this.filename = filename;
        p = new Process();
        p.startbuildingprocess(this.filename);
        priority = p.pcb.getP_priority();

        if ( priority > 15 && priority < 32 ) {
            fcfsQ.enqueue(p);
        }

        else if (priority < 15 && priority >0){
            rrQ.enqueue(p);
        }

        System.out.println("Loaded Process: " + p);
    }

    public void execute ( )  {

        short codeCounter;
        short codeLimit;

        System.out.println("rrq="+rrQ.length());
        System.out.println("fcfs="+fcfsQ.length());

        while (!rrQ.isEmpty()) {
            //when while loop is entered again, 2 clockcycles have passed

            timeslice = timeslice+4;

            p=rrQ.dequeue();

            p.pcb.set_regs();

            //System.out.println("hi from while");
            for ( int i = 0; i <2; i++ ) {
                //System.out.println("hi");
                clockcycle = clockcycle +2;
                codeCounter = p.pcb.getSpecialPurposeRegisters().getCodeCounter();
                codeLimit = p.pcb.getSpecialPurposeRegisters().getCodeLimit();
                //sending opcodes from codepartofPCB to interpretor for execution
               /* opCodeDecode();*/
                // will check for termination after every burst clock cycle which has value of value 2
                // to see if
                System.out.println("codeCounter: " + codeCounter);
                System.out.println("codeLimit: " + codeLimit);
                if ( codeCounter > codeLimit ) {
                    rrQ.dequeue();
                }
                else {
                    rrQ.enqueue(p);
                    p.pcb.currentPC = p.pcb.getSpecialPurposeRegisters().getCodeCounter();
                }
                p.pcb.getSpecialPurposeRegisters().setCodeCounter((short) (p.pcb.getSpecialPurposeRegisters().getCodeCounter() + (short) 1));
                //end of for loop
            }
        }
        p.pcb.getSpecialPurposeRegisters().setCodeCounter((short)0);
    }

    public void terminate( Process p ) {

        // we do it 2 separate times becuase data & code
        // can be of different sizes
        int i;

        //for codepart
        for ( i =0; i < p.pcb.codepart.pagetable.length ; i++ ) {
            if ( p.pcb.codepart.checkPageEmpty(i) != 0 ) {
                int frame = p.pcb.codepart.deleteframe(i);
                Memory.pgTable.setframe((short) frame, 0);
            }
        }

        //for datapart
        i=0;
        for ( i =0; i < p.pcb.datapart.pagetable.length ; i++ ) {
            if ( p.pcb.codepart.checkPageEmpty(i) != 0 ) {
                int frame = p.pcb.datapart.deleteframe(i);
                Memory.pgTable.setframe((short) frame, 0);
            }
        }

        Process p2;
        for ( int x = 0; x<rrQ.length(); x++ ) {
            p2 = rrQ.dequeue();

            if ( p2.pcb.getP_id()!=p.pcb.getP_id()) {
                rrQ.enqueue(p2);
            }
        }

        for ( int y = 0; y<rrQ.length(); y++ ) {
            p2 = fcfsQ.dequeue();

            if ( p2.pcb.getP_id()!=p.pcb.getP_id()) {
                fcfsQ.enqueue(p2);
            }
        }

    }

    /*public void opCodeDecode() {
        p.pcb.interpretor.CPU(p.pageblockcode, p.pcb.interpretor.getSpecReg2());
    }*/

    /*public short[] loadIntoCode() {
        int i;
        int j = p.pcb.interpretor.getSpecReg2();
        for ( i = p.pcb.interpretor.specReg[3]; i < j ; i++ ) {
            code[i] =  p.pcb.interpretor.getSpecReg3CodeCounter();
        }

        return code;
    }*/
}
