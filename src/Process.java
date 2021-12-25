import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

class Process{


    //prmem is just a variable used to call functions in non static way
    Memory prmem;
    byte pageblockcode[];
    byte pageblockdata[];
    PCB pcb;
    static int pid =0;
    String filename;

    Process() {
        pcb = new PCB();
        prmem = new Memory();

    }

//why do i have to set the filename in a function man what is this

    void startbuildingprocess ( String filename ) throws Exception {

        // code size counter will be used later

        int codesizecounter = 0;
        //is data and code size going to be the same????

        byte code[];
        byte data[];
        byte byteRead;

        pcb.setP_filename(filename);
        File file = new File (filename);

        FileInputStream FIS = new FileInputStream(file);

        //dummy file reader to initalise code
        FileInputStream FISD = new FileInputStream(file);

        //set pcb --> process priority
        pcb.p_priority = (byte)FIS.read();

        //set pcb --> proces ID
        byte b2 = (byte)FIS.read();
        byte b3 = (byte)FIS.read();
        pcb.setP_id(concat(b2,b3));


        //set pcb --> process ID
        byte b4 = (byte)FIS.read();
        byte b5 = (byte)FIS.read();
        pcb.setP_sizedata(concat(b4,b5));
        data = new byte[pcb.getP_sizedata()];


        //byte 6,7,8 read and nothing done with it
        FIS.read();
        FIS.read();
        FIS.read();


        //read data bytes
        for ( int i = 0; (255*i)<pcb.getP_sizedata(); i ++) {
            data[i] = (byte) FIS.read();
            i++;
        }

        //   long xyzminus = (int) Math.pow(2,64) - 1;

        while ( FIS.read() != -1 ) {
            codesizecounter++;
        }
        pcb.setP_sizecode(codesizecounter);
        FIS.close();

        //determine size of code and initialise code array
        //    codesizecounter = ((int)file.length() - pcb.getdatasize() - (int)(xyzminus)/8);
        code = new byte[codesizecounter];

        // read code bytes

        for ( int i =0; i < (pcb.getP_sizedata() + 8); i ++ ) {
            FISD.read();
        }

        int codestorageloop = 0;

        while(( FISD.read()) != -1 ) {
            byteRead = (byte)FISD.read();
            code[codestorageloop]  = byteRead;
            codestorageloop++;
        }
        FISD.close();

        // our page size is 128 bytes
        // in order to determine how many pages we will divide code&data size by 128
        // to create an iterator loop


        //128 is size of page
        int noOfCodePages = pcb.getP_sizecode() / 128;
        int noOfDataPages = pcb.getP_sizedata() / 128;


        pageblockcode = new byte[128];

        pageblockdata = new byte[128];

        int tempcode = noOfCodePages;
        int tempdata = noOfDataPages;
        int iterator;
        int currentpageNo=0;

        //code is loaded onto main memory

        while ( tempcode > 0 ) {
            for ( iterator = 0; ((128*currentpageNo) +iterator)<pcb.getP_sizecode() && iterator<128; iterator ++ ) {
                // you run it for 128 instances to get the
                // values for the first index of your array block code

                //we loaded the entire code information into a code array
                // now we are sending it to a codeblockarray which has same dimensions
                // as your page table and frame

                pageblockcode[iterator] = code [iterator + (128*currentpageNo)];
            }

            // load code into memory, determines available free frame in your process control block
            // and then you use it to set the frame in pcb

            short fr = (short) prmem.loadCodeIntoMemory(pageblockcode, 128);
            pcb.codepart.setframe(fr, currentpageNo);

            //go to next index of pagetable
            currentpageNo++;

            // you are minusing it because you want it to run for the entire size of code information
            tempcode--;
        }

        // same shit as code but for data

        iterator = 0;
        currentpageNo = 0;
        while ( tempdata > 0) {
            for ( iterator = 0; ((128*currentpageNo) +iterator)<pcb.getP_sizedata() && iterator<128; iterator ++ ) {
                // you run it for 128 instances to get the
                // values for the first index of your array block code

                //we loaded the entire code information into a code array
                // now we are sending it to a codeblockarray which has same dimensions
                // as your page table and frame

                pageblockdata[iterator] = data[iterator + (128*currentpageNo)];
            }

            short fr = (short) prmem.loadCodeIntoMemory(pageblockcode, 128);
            pcb.datapart.setframe(fr, currentpageNo);

            //go to next index of pagetable
            currentpageNo++;

            // you are minusing it because you want it to run for the entire size of code information
            tempdata--;

        }

        pcb.set_regs();

    }

    byte getCode ( short programcounter ) {
        // where current program counter is,
        // check where is frame found at current loc of program counter

        // you divide by 128 to determine frame number
        short page =  (short) ( programcounter / 128 );

        // you do Mod % 128 to determine where within that page's frame
        // at which location in that frame is the value you want
        short data = (short) ( programcounter % 128 ) ;
        int fr = pcb.codepart.getframe(page);

        return (byte)Memory.memory[(fr*128)+data];

    }

    short getData ( short offset ) {

        // you divide by 128 to determine frame number
        short page =  (short) ( offset/ 128 );

        // you do Mod % 128 to determine where within that page's frame
        // at which location in that frame is the value you want
        short data = (short) ( offset % 128 ) ;

        int fr = pcb.datapart.getframe(page);

        byte byte1 = Memory.memory[(fr*128)+data];
        byte byte2 = Memory.memory[(fr*128)+ data + 1];
        return concat(byte1,byte2);
        //  return prmem.concat(byte1,byte2);
    }


    // void set data is the same as loadCodeIntoMemory function but for data
    void setData ( short offset, short value ) {
        short page =  (short) ( offset/ 128 );

        // you do Mod % 128 to determine where within that page's frame
        // at which location in that frame is the value you want
        short data = (short) ( offset % 128 ) ;

        int fr = pcb.datapart.getframe(page);

        byte byte1 = Memory.memory[(fr*128)+data];
        byte byte2 =  Memory.memory[(fr*128)+ data + 1];

        Memory.memory[(fr*128)+data] = byte1;
        Memory.memory[(fr*128)+data + 1] = byte2;

    }

    byte getFirstByte( short val ) {
        byte temp = ( byte ) ( val >> 8 ) ;
        return temp;
    }

    byte getSecondByte( short val ) {
        byte temp = ( byte )(val) ;
        return temp;
    }


    short concat ( byte byte1, byte byte2 ) {
        return (short) ((byte1 << 8) | (byte2 & 0xFF));
    }

    @Override
    public String toString() {
        return "Process{" +
                "\n\tprmem=" + prmem +
                ",\n\tpageblockcode=" + pageblockcode +
                ",\n\tpageblockdata=" + pageblockdata +
                ",\n\tpcb=" + pcb +
                ",\n\tfilename='" + filename + '\'' +
                "\n}";
    }
}