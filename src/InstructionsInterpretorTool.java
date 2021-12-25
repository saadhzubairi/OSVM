import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class InstructionsInterpretorTool {

    public static ArrayList interpreter(String filename){
        {
            Memory main_Memory = new Memory();
            SpecialPurposeRegisters spregister = new SpecialPurposeRegisters();
            Instructions instruction = new Instructions();
            String hexValue = "";
            spregister.codeBase = main_Memory.memory[0];
            System.out.println(spregister.codeBase);
            spregister.codeCounter = spregister.codeBase;
            spregister.programCounter = spregister.codeBase;

            /*
             * Reading Instructions mentioned in the File and copying those instructions in
             * the initialized main_Memory (memory2)
             */

            try {

                File file = new File(filename);
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextInt()) {
                    byte data = (byte) myReader.nextInt();
                    main_Memory.memory[spregister.codeCounter] = data;
                    spregister.codeCounter++;

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            /*
             * The following while loop picks out the instructions from the main_Memory, executes
             * them via the Switch Cases, and terminates when all the instructions have been
             * executed
             */

            while (spregister.programCounter < spregister.codeCounter) {
                System.out.println("Program Counter\t: " + spregister.programCounter);
                System.out.println("Code Counter\t: " + spregister.codeCounter);

                /*
                 * The instruction register is always set by the program counter which moves
                 * after the execution of each instruction
                 */
                spregister.instructionRegister = main_Memory.memory[spregister.programCounter];

                System.out.println();

                /* The instructions before execution are converted to hexadecimal format */
                if (spregister.instructionRegister < 0) {
                    int posByte = Instructions.unsignedToBytes(spregister.instructionRegister);
                    hexValue = String.format("%02X", posByte);
                } else {
                    hexValue = String.format("%02X", spregister.instructionRegister);
                }

                System.out.println("Instruction\t: " + hexValue);

                System.out.println("-");

                String instructions = hexValue;
                System.out.println("instructions:\t" + hexValue);

                /* The instructions are then executed through this switch statement */

                switch (instructions) {
                    /* Register-register Instructions */
                    case "16": // MOV
                        /*
                         * Copies content from one register to another and increments Program counter by
                         * 3
                         */
                        int R1 = main_Memory.memory[spregister.programCounter + 1];
                        int R2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.MOV(R1, R2);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;
                    case "17": // ADD
                        /* Add Register Contents and increments Program counter by 3 */
                        int reg1 = main_Memory.memory[spregister.programCounter + 1];
                        int reg2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.ADD(reg1, reg2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    case "18": // SUB
                        /* Subtracts Register contents and increments Program counter by 3 */
                        int regi1 = main_Memory.memory[spregister.programCounter + 1];
                        int regi2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.SUB(regi1, regi2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    case "19": // MUL
                        /* Multiplies Register contents and increments Program counter by 3 */
                        int register1 = main_Memory.memory[spregister.programCounter + 1];
                        int register2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.MUL(register1, register2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    case "1A": // DIV
                        /* Divides Register contents and increments Program counter by 3 */
                        int regis1 = main_Memory.memory[spregister.programCounter + 1];
                        int regis2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.DIV(regis1, regis2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    case "1B": // AND
                        /*
                         * applies AND operation Register contents and increments Program counter by 3
                         */
                        int regist1 = main_Memory.memory[spregister.programCounter + 1];
                        int regist2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.AND(regist1, regist2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    case "1C": // OR
                        /* applies OR operation Register contents and increments Program counter by 3 */
                        int registe1 = main_Memory.memory[spregister.programCounter + 1];
                        int registe2 = main_Memory.memory[spregister.programCounter + 2];
                        instruction.OR(registe1, registe2, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 3);
                        break;

                    /* Register-Immediate Instructions */

                    case "30": // MOVI
                        /* Copies an immediate to a register and increments Program counter by 4 */
                        int r30 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed1 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed2 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.MOVI(r30, immed1, immed2);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "31": // ADDI
                        /*
                         * Adds a Register content and an immediate and increments Program counter by 4
                         */
                        int r31 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed3 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed4 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.ADDI(r31, immed3, immed4, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "32": // SUBI
                        /*
                         * Subtracts a Register content and an immediate and increments Program counter
                         * by 4
                         */
                        int r32 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed5 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed6 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.SUBI(r32, immed5, immed6, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "33": // MULI
                        /*
                         * Multiplies a Register content and an immediate and increments Program counter
                         * by 4
                         */
                        int r33 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed7 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed8 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.MULI(r33, immed7, immed8, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "34": // DIVI
                        /*
                         * Divides a Register content and an immediate and increments Program counter by
                         * 4
                         */
                        int r34 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed9 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed10 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.DIVI(r34, immed9, immed10, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "35": // ANDI
                        /*
                         * applies AND operation on a Register and an immediate and increments Program
                         * counter by 4
                         */
                        int r35 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed11 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed12 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.ANDI(r35, immed11, immed12);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    case "36": // ORI
                        /*
                         * applies OR operation on a Register and an immediate and increments Program
                         * counter by 4
                         */
                        int r36 = main_Memory.memory[spregister.programCounter + 1];
                        byte immed13 = main_Memory.memory[spregister.programCounter + 2];
                        byte immed14 = main_Memory.memory[spregister.programCounter + 3];
                        instruction.ORI(r36, immed13, immed14);
                        spregister.programCounter = (short) (spregister.programCounter + 4);
                        break;

                    // The following instructions have no increments to the program counter

                    case "37": // BZ Branch to offset if flag = 1
                        instruction.BZ(Instructions.Zerobit(spregister.flag), main_Memory.memory[spregister.programCounter + 1],
                                spregister);
                        break;

                    case "38": // BZ Branch to offset if flag = 0
                        instruction.BNZ(Instructions.Zerobit(spregister.flag), main_Memory.memory[spregister.programCounter + 1],
                                spregister);
                        break;

                    case "39":// Branch if Carry
                        instruction.BC(Instructions.Carrybit(spregister.flag), main_Memory.memory[spregister.programCounter + 1],
                                spregister);
                        break;

                    case "3A":// Branch if sign
                        instruction.BS(Instructions.Signbit(spregister.flag), main_Memory.memory[spregister.programCounter + 1],
                                spregister);
                        break;

                    case "3B":// Jump
                        instruction.JMP(spregister, main_Memory.memory[spregister.programCounter + 1]);
                        break;
                    /* Memory Instructions */
                    case "51": // MOVL
                        int r51 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.MOVL(r51, main_Memory, spregister);
                        spregister.programCounter = (byte) (spregister.programCounter + 4);
                        break;
                    case "52": // MOVS
                        int r52 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.MOVS(r52, main_Memory, spregister);
                        spregister.programCounter = (byte) (spregister.programCounter + 4);
                        break;

                    /* Single Operand Instructions */
                    case "71": // Shift logical Left
                        int r71 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.SHL(r71, 1, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;
                    case "72": // Shift logical Right
                        int r72 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.SHR(r72, 1, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;
                    case "73": // Rotate Left
                        int r73 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.RTL(r73, 1, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;
                    case "74": // Rotate Right
                        int r74 = main_Memory.memory[spregister.programCounter + 1];
                        instruction.RTR(r74, 1, spregister);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;
                    case "75": // Increment
                        int R75 = (spregister.programCounter + 1);
                        instruction.INC(R75);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;
                    case "76": // Decrement
                        int R76 = (spregister.programCounter + 1);
                        instruction.DEC(R76);
                        spregister.programCounter = (short) (spregister.programCounter + 2);
                        break;

                    /* No Operand Instructions */
                    case "F2": // NOOP
                        spregister.programCounter = (short) (spregister.programCounter + 1);
                        break;
                    case "F3": // END
                        spregister.programCounter = (short) (spregister.codeCounter + 1);
                        break;
                }

            }

            /*do not remove these lines >>>*/
            //instruction.printAllRegisters();
            System.out.println();
            instruction.printAllSpecialRegisters(spregister);

            ArrayList ret = new ArrayList();
            ret.add(spregister);
            ret.add(instruction.register);

            return ret;
        }

    }

    static class Instructions {

        GeneralPurposeRegisters register = new GeneralPurposeRegisters();

        /* Register to Register Instructions: */
        /*
         * Subsequent methods attatched to each exection of the instructions with
         * various functions
         */
        public void MOV(int index1, int index2) {
            register.registers[index1 - 1][0] = register.registers[index2 - 1][0];
            register.registers[index1 - 1][1] = register.registers[index2 - 1][1];
        }

        public void ADD(int reg1, int reg2, SpecialPurposeRegisters spregister) {
            overflowcheck(reg1, reg2, "*", spregister);
            register.registers[reg1 - 1][0] = (byte) (register.registers[reg1 - 1][0] + register.registers[reg2 - 1][0]);
            register.registers[reg1 - 1][1] = (byte) (register.registers[reg1 - 1][1] + register.registers[reg2 - 1][1]);

            if (register.registers[reg1 - 1][0] == 0 && register.registers[reg1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[reg1 - 1][0] < 0 || register.registers[reg1 - 1][1] < 0) {
                SetSignbit(spregister);
            }


        }

        public void SUB(int reg1, int reg2, SpecialPurposeRegisters spregister) {
            overflowcheck(reg1, reg2, "*", spregister);
            register.registers[reg1 - 1][0] = (byte) (register.registers[reg1 - 1][0] - register.registers[reg2 - 1][0]);
            register.registers[reg1 - 1][1] = (byte) (register.registers[reg1 - 1][1] - register.registers[reg2 - 1][1]);

            if (register.registers[reg1 - 1][0] == 0 && register.registers[reg1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[reg1 - 1][0] < 0 || register.registers[reg1 - 1][1] < 0) {
                SetSignbit(spregister);
            }

        }

        public void MUL(int register1, int register2, SpecialPurposeRegisters spregister) {
            overflowcheck(register1, register2, "*", spregister);
            register.registers[register1 - 1][0] = (byte) (register.registers[register1 - 1][0]
                    * register.registers[register2 - 1][0]);
            register.registers[register1 - 1][1] = (byte) (register.registers[register1 - 1][1]
                    * register.registers[register2 - 1][1]);

            if (register.registers[register1 - 1][0] == 0 && register.registers[register1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[register1 - 1][0] < 0 || register.registers[register1 - 1][1] < 0) {
                SetSignbit(spregister);
            }

        }

        public void DIV(int reg1, int reg2, SpecialPurposeRegisters spregister) {
            overflowcheck(reg1, reg2, "*", spregister);
            register.registers[reg1 - 1][0] = (byte) (register.registers[reg1 - 1][0] / register.registers[reg2 - 1][0]);
            register.registers[reg1 - 1][1] = (byte) (register.registers[reg1 - 1][1] / register.registers[reg2 - 1][1]);

            if (register.registers[reg1 - 1][0] == 0 && register.registers[reg1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[reg1 - 1][0] < 0 || register.registers[reg1 - 1][1] < 0) {
                SetSignbit(spregister);
            }

        }

        public void AND(int regist1, int regist2, SpecialPurposeRegisters spregister) {

            String register00 = toBits(register.registers[regist1 - 1][0]);
            String register01 = toBits(register.registers[regist1 - 1][1]);
            String register10 = toBits(register.registers[regist2 - 1][0]);
            String register11 = toBits(register.registers[regist2 - 1][1]);

            char[] r00 = toCharArr(register00);
            char[] r01 = toCharArr(register01);
            char[] r10 = toCharArr(register10);
            char[] r11 = toCharArr(register11);

            for (int i = 0; i < r00.length; i++) {
                if (r00[i] == '1' && r10[i] == '1') {
                    r00[i] = '1';
                } else {
                    r00[i] = '0';
                }

                if (r01[i] == '1' && r11[i] == '1') {
                    r01[i] = '1';
                } else {
                    r01[i] = '0';
                }
            }
            register.registers[regist1 - 1][0] = Byte.parseByte(String.valueOf(r00), 2);
            register.registers[regist1 - 1][1] = Byte.parseByte(String.valueOf(r01), 2);

            if (register.registers[regist1 - 1][0] == 0 && register.registers[regist1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[regist1 - 1][0] < 0 || register.registers[regist1 - 1][1] < 0) {
                SetSignbit(spregister);
            }
        }

        public void OR(int regist1, int regist2, SpecialPurposeRegisters spregister) {

            String register00 = toBits(register.registers[regist1 - 1][0]);
            String register01 = toBits(register.registers[regist1 - 1][1]);
            String register10 = toBits(register.registers[regist2 - 1][0]);
            String register11 = toBits(register.registers[regist2 - 1][1]);

            char[] r00 = toCharArr(register00);
            char[] r01 = toCharArr(register01);
            char[] r10 = toCharArr(register10);
            char[] r11 = toCharArr(register11);

            for (int i = 0; i < r00.length; i++) {
                if (r00[i] == '0' && r10[i] == '0') {
                    r00[i] = '0';
                } else {
                    r00[i] = '1';
                }

                if (r01[i] == '0' && r11[i] == '0') {
                    r01[i] = '0';
                } else {
                    r01[i] = '1';
                }
            }

            register.registers[regist1 - 1][0] = Byte.parseByte(String.valueOf(r00), 2);
            register.registers[regist1 - 1][1] = Byte.parseByte(String.valueOf(r01), 2);

            if (register.registers[regist1 - 1][0] == 0 && register.registers[regist1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[regist1 - 1][0] < 0 || register.registers[regist1 - 1][1] < 0) {
                SetSignbit(spregister);
            }

        }

        /* Register to Immediate Functions: */

        public void MOVI(int index, byte memory1, byte memory2) {

            register.registers[index - 1][0] = memory1;
            register.registers[index - 1][1] = memory2;

        }

        public void MULI(int index, byte memory1, byte memory2, SpecialPurposeRegisters spregister) {
            overflowcheck(index, memory1, memory2, "*", spregister);
            if (memory1 < 0) {
                byte mem1 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] * mem1);
            } else {
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] * memory1);
            }
            if (memory2 < 0) {
                byte mem2 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] * mem2);
            } else {
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] * memory2);
            }
            if (register.registers[index - 1][0] == 0 && register.registers[index - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[index - 1][0] < 0 || register.registers[index - 1][1] < 0) {
                SetSignbit(spregister);
            }


        }

        public void ADDI(int index, byte memory1, byte memory2, SpecialPurposeRegisters spregister) {
            overflowcheck(index, memory1, memory2, "*", spregister);
            if (memory1 < 0) {
                byte mem1 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] + mem1);
            } else {
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] + memory1);
            }
            if (memory2 < 0) {
                byte mem2 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] + mem2);
            } else {
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] + memory2);
            }
            if (register.registers[index - 1][0] == 0 && register.registers[index - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[index - 1][0] < 0 || register.registers[index - 1][1] < 0) {
                SetSignbit(spregister);
            }


        }

        public void SUBI(int index, byte memory1, byte memory2, SpecialPurposeRegisters spregister) {
            overflowcheck(index, memory1, memory2, "*", spregister);
            if (memory1 < 0) {
                byte mem1 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] - mem1);
            } else {
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] - memory1);
            }
            if (memory2 < 0) {
                byte mem2 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] - mem2);
            } else {
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] - memory2);
            }
            if (register.registers[index - 1][0] == 0 && register.registers[index - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[index - 1][0] < 0 || register.registers[index - 1][1] < 0) {
                SetSignbit(spregister);
            }
        }

        public void DIVI(int index, byte memory1, byte memory2, SpecialPurposeRegisters spregister) {
            overflowcheck(index, memory1, memory2, "*", spregister);
            if (memory1 < 0) {
                byte mem1 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] / mem1);
            } else {
                register.registers[index - 1][0] = (byte) (register.registers[index - 1][0] / memory1);
            }
            if (memory2 < 0) {
                byte mem2 = (byte) unsignedToBytes(memory1);
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] / mem2);
            } else {
                register.registers[index - 1][1] = (byte) (register.registers[index - 1][1] / memory2);
            }
            if (register.registers[index - 1][0] == 0 && register.registers[index - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[index - 1][0] < 0 || register.registers[index - 1][1] < 0) {
                SetSignbit(spregister);
            }


        }

        public void ANDI(int index, byte memory1, byte memory2) {
            String register00 = toBits(register.registers[index - 1][0]);
            String register01 = toBits(register.registers[index - 1][1]);
            String mem, mem1 = "";
            if (memory1 < 0) {
                byte m1 = (byte) unsignedToBytes(memory1);
                mem = toBits(m1);
            } else {
                mem = toBits(memory1);
            }
            if (memory2 < 0) {
                byte m2 = (byte) unsignedToBytes(memory2);
                mem1 = toBits(m2);
            } else {
                mem1 = toBits(memory2);
            }

            char[] r00 = toCharArr(register00);
            char[] r01 = toCharArr(register01);
            char[] m1 = toCharArr(mem);
            char[] m2 = toCharArr(mem1);

            for (int i = 0; i < r00.length; i++) {
                if (r00[i] == '1' && m1[i] == '1') {
                    r00[i] = '1';
                } else {
                    r00[i] = '0';
                }

                if (r01[i] == '1' && m2[i] == '1') {
                    r01[i] = '1';
                } else {
                    r01[i] = '0';
                }
            }

            register.registers[index - 1][0] = Byte.parseByte(String.valueOf(r00), 2);
            register.registers[index - 1][1] = Byte.parseByte(String.valueOf(r01), 2);

        }

        public void ORI(int index, byte memory1, byte memory2) {
            String register00 = toBits(register.registers[index - 1][0]);
            String register01 = toBits(register.registers[index - 1][1]);
            String mem, mem1 = "";
            if (memory1 < 0) {
                byte m1 = (byte) unsignedToBytes(memory1);
                mem = toBits(m1);
            } else {
                mem = toBits(memory1);
            }
            if (memory2 < 0) {
                byte m2 = (byte) unsignedToBytes(memory2);
                mem1 = toBits(m2);
            } else {
                mem1 = toBits(memory2);
            }

            char[] r00 = toCharArr(register00);
            char[] r01 = toCharArr(register01);
            char[] m1 = toCharArr(mem);
            char[] m2 = toCharArr(mem1);

            for (int i = 0; i < r00.length; i++) {
                if (r00[i] == '0' && m1[i] == '0') {
                    r00[i] = '0';
                } else {
                    r00[i] = '1';
                }

                if (r01[i] == '0' && m2[i] == '0') {
                    r01[i] = '0';
                } else {
                    r01[i] = '1';
                }
            }

            register.registers[index - 1][0] = Byte.parseByte(String.valueOf(r00), 2);
            register.registers[index - 1][1] = Byte.parseByte(String.valueOf(r01), 2);

        }

        public void BZ(char Zerobit, byte num, SpecialPurposeRegisters spr) {
            /*
             * Checking if Zero-bit of flag is 1: if yes, we check and jump, if no we just
             * move forward.
             */

            if (Zerobit == '1') {

                /*
                 * We now check if the offset is in the range of the codelimit, if yes we jump
                 * our program counter to the offset, if not we increment program counter by 2
                 * to move to the next code (not jumping)
                 */

                if ((spr.codeBase + num) < spr.codeLimit) {
                    spr.programCounter = (byte) (spr.codeBase + num);
                } else {
                    spr.programCounter = (byte) (spr.programCounter + 2);
                }
            } else {
                spr.programCounter = (byte) (spr.programCounter + 2);
            }
        }

        public void BNZ(char Zerobit, byte num, SpecialPurposeRegisters spr) {

            /*
             * Checking if Zero-bit of flag is 0: if yes, we check and jump, if no we just
             * move forward.
             */

            if (Zerobit == '0') {

                /* same logic applied here as in BZ */

                if ((spr.codeBase + num) < spr.codeLimit) {
                    spr.programCounter = (byte) (spr.codeBase + num);
                } else {
                    spr.programCounter = (byte) (spr.programCounter + 2);
                }
            } else {
                spr.programCounter = (byte) (spr.programCounter + 2);
            }
        }

        public void BC(char Carrybit, byte num, SpecialPurposeRegisters spr) {

            if (Carrybit == '1') {
                if ((spr.codeBase + num) < spr.codeLimit) {
                    spr.programCounter = (byte) (spr.codeBase + num);
                } else {
                    spr.programCounter = (byte) (spr.programCounter + 2);
                }
            } else {
                spr.programCounter = (byte) (spr.programCounter + 2);
            }
        }

        public void BS(char Signbit, byte num, SpecialPurposeRegisters spr) {
            if (Signbit == '1') {
                if ((spr.codeBase + num) < spr.codeLimit) {
                    spr.programCounter = (byte) (spr.codeBase + num);
                } else {
                    spr.programCounter = (byte) (spr.programCounter + 2);
                }
            } else {
                spr.programCounter = (byte) (spr.programCounter + 2);
            }
        }

        public void JMP(SpecialPurposeRegisters spr, byte num) {
            spr.programCounter = (byte) (spr.programCounter + num);
        }

        /* Memory Instructions */

        public void MOVL(int r1, Memory memory2, SpecialPurposeRegisters spr) {
            register.registers[r1][0] = memory2.memory[spr.programCounter + 2];
            register.registers[r1][1] = memory2.memory[spr.programCounter + 3];
        }

        public void MOVS(int r1, Memory memory2, SpecialPurposeRegisters spr) {
            memory2.memory[spr.programCounter + 2] = register.registers[r1][0];
            memory2.memory[spr.programCounter + 3] = register.registers[r1][1];
        }

        /* Single Operand Instructions */

        public void SHL(int r1, int n, SpecialPurposeRegisters spregister) {
            shifter(r1, 1, 1, register, n);

            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[r1 - 1][0] < 0 || register.registers[r1 - 1][1] < 0) {
                SetSignbit(spregister);
            }
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 1) {
                SetCarrybit(spregister);
            }

        }

        public void SHR(int r1, int n, SpecialPurposeRegisters spregister) {
            shifter(r1, 1, 2, register, n);
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[r1 - 1][0] < 0 || register.registers[r1 - 1][1] < 0) {
                SetSignbit(spregister);
            }
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 1) {
                SetCarrybit(spregister);
            }
        }

        public void RTL(int r1, int n, SpecialPurposeRegisters spregister) {
            shifter(r1, 1, 3, register, n);
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[r1 - 1][0] < 0 || register.registers[r1 - 1][1] < 0) {
                SetSignbit(spregister);
            }
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 1) {
                SetCarrybit(spregister);
            }
        }

        public void RTR(int r1, int n, SpecialPurposeRegisters spregister) {
            shifter(r1, 1, 4, register, n);
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 0) {
                SetZerobit(spregister);
            }
            if (register.registers[r1 - 1][0] < 0 || register.registers[r1 - 1][1] < 0) {
                SetSignbit(spregister);
            }
            if (register.registers[r1 - 1][0] == 0 && register.registers[r1 - 1][1] == 1) {
                SetCarrybit(spregister);
            }
        }

        public void INC(int r1) {

            // register value converted to bit-strings
            String s1 = toBits(register.registers[r1 - 1][0]);
            String s2 = toBits(register.registers[r1 - 1][1]);

            // 16-bit value
            String conc = s1 + s2;

            // conc converted to byte
            Byte b = Byte.parseByte(conc, 2);

            // b converted to int i and then j is i-sll'ed
            int i = b;
            i++;

            String shifted = toBits((byte) i);
            String st1 = shifted.substring(0, 8);
            String st2 = shifted.substring(8);

            // Saving the values in registers!
            register.registers[r1 - 1][0] = Byte.parseByte(st1, 2);
            register.registers[r1 - 1][1] = Byte.parseByte(st2, 2);

        }

        public void DEC(int r1) {
            // register value converted to bit-strings
            String s1 = toBits(register.registers[r1 - 1][0]);
            String s2 = toBits(register.registers[r1 - 1][1]);

            // 16-bit value
            String conc = s1 + s2;

            // conc converted to byte
            Byte b = Byte.parseByte(conc, 2);

            // b converted to int i and then j is i-sll'ed
            int i = b;
            i--;

            String shifted = toBits((byte) i);
            String st1 = shifted.substring(0, 8);
            String st2 = shifted.substring(8);

            // Saving the values in registers!
            register.registers[r1 - 1][0] = Byte.parseByte(st1, 2);
            register.registers[r1 - 1][1] = Byte.parseByte(st2, 2);
        }

        /* CARRY, ZERO, SIGN, OVERFLOW BIT METHODS */

        public static char Carrybit(short flag) {
            String s1 = toBits(flag);
            char[] s2 = toCharArr(s1);
            return s2[0];
        }

        public static void SetCarrybit(SpecialPurposeRegisters spregister) {
            String s1 = toBits(spregister.flag);
            char[] s2 = toCharArr(s1);
            if (s2[0] == '1')
                s2[0] = '0';
            else if (s2[0] == '0')
                s2[0] = '1';

            for (int i = 0; i < s2.length; i++) {
                s1 = s1 + s2[i];
            }

            spregister.flag = Short.parseShort(String.valueOf(s1), 2);


        }

        public static char Zerobit(short flag) {
            String s1 = toBits(flag);
            char[] s2 = toCharArr(s1);
            return s2[1];
        }

        public static void SetZerobit(SpecialPurposeRegisters spregister) {
            String s1 = toBits(spregister.flag);
            char[] s2 = toCharArr(s1);
            if (s2[1] == '1')
                s2[1] = '0';
            else if (s2[0] == '0')
                s2[1] = '1';

            for (int i = 0; i < s2.length; i++) {
                s1 = s1 + s2[i];
            }

            spregister.flag = Short.parseShort(String.valueOf(s1), 2);

        }

        public static char Signbit(short flag) {
            String s1 = toBits(flag);
            char[] s2 = toCharArr(s1);
            return s2[2];
        }

        public static void SetSignbit(SpecialPurposeRegisters spregister) {
            String s1 = toBits(spregister.flag);
            char[] s2 = toCharArr(s1);
            if (s2[2] == '1')
                s2[2] = '0';
            else if (s2[0] == '0')
                s2[2] = '1';

            for (int i = 0; i < s2.length; i++) {
                s1 = s1 + s2[i];
            }

            spregister.flag = Short.parseShort(String.valueOf(s1), 2);
        }

        public static char Overflowbit(short flag) {
            String s1 = toBits(flag);
            char[] s2 = toCharArr(s1);
            return s2[3];
        }

        public static void SetOverflowbit(SpecialPurposeRegisters spregister) {
            String s1 = toBits(spregister.flag);
            char[] s2 = toCharArr(s1);
            if (s2[3] == '1')
                s2[3] = '0';
            else if (s2[0] == '0')
                s2[3] = '1';

            for (int i = 0; i < s2.length; i++) {
                s1 = s1 + s2[i];
            }

            spregister.flag = Short.parseShort(String.valueOf(s1), 2);
        }


        public void overflowcheck(int r1, byte num1, byte num2, String operation, SpecialPurposeRegisters spregister) {
            String s1 = toBits(register.registers[r1 - 1][0]);
            String s2 = toBits(register.registers[r1 - 1][1]);
            String s3 = toBits(num1);
            String s4 = toBits(num2);

            int first = Integer.parseInt(s1 + s2, 2);
            int second = Integer.parseInt(s3 + s4, 2);

            if (operation.equals("*")) {
                if ((first * second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("/")) {
                if ((first / second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("+")) {
                if ((first + second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("-")) {
                if ((first - second) > 65535) {
                    SetOverflowbit(spregister);
                }
            }


        }

        public void overflowcheck(int r1, int r2, String operation, SpecialPurposeRegisters spregister) {
            String s1 = toBits(register.registers[r1 - 1][0]);
            String s2 = toBits(register.registers[r1 - 1][1]);
            String s3 = toBits(register.registers[r2 - 1][0]);
            String s4 = toBits(register.registers[r2 - 1][1]);

            int first = Integer.parseInt(s1 + s2, 2);
            int second = Integer.parseInt(s3 + s4, 2);

            if (operation.equals("*")) {
                if ((first * second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("/")) {
                if ((first / second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("+")) {
                if ((first + second) > 65535) {
                    SetOverflowbit(spregister);
                }
            } else if (operation.equals("-")) {
                if ((first - second) > 65535) {
                    SetOverflowbit(spregister);
                }
            }


        }


        /* Static + Utitlity Methods */

        public static void shifter(int r1, int n, int cond, GeneralPurposeRegisters r, int shiftby) {
            // register value converted to bit-strings
            String s1 = toBits(r.registers[r1 - 1][0]);
            String s2 = toBits(r.registers[r1 - 1][1]);

            // 16-bit value
            String conc = s1 + s2;

            // conc converted to byte
            Byte b = Byte.parseByte(conc, 2);

            // b converted to int i and then j is i-sll'ed
            int i = b;

            int j = 0;

            switch (cond) {
                case 1:
                    j = i << n;
                    break;
                case 2:
                    j = i >> n;
                    break;
                case 3:
                    j = Integer.rotateLeft(i, n);
                    break;
                case 4:
                    j = Integer.rotateRight(i, n);
                    break;

            }

            // now going in reverse
            String shifted = toBits((byte) j);
            String st1 = shifted.substring(0, 8);
            String st2 = shifted.substring(8);

            // Saving the values in registers!
            r.registers[r1 - 1][0] = Byte.parseByte(st1, 2);
            r.registers[r1 - 1][1] = Byte.parseByte(st2, 2);
        }

        public static char[] toCharArr(String str) {
            char[] ch = new char[str.length()];
            for (int i = 0; i < str.length(); i++) {
                ch[i] = str.charAt(i);
            }
            return ch;
        }

        public static String toBits(Byte numbah) {
            String r00;
            if (numbah < 0) {
                int result = numbah & 0xff;
                r00 = Integer.toBinaryString(result);
            } else {
                r00 = Integer.toBinaryString(numbah);
            }
            String inBits = String.format("%8s", r00).replace(" ", "0");
            return inBits;
        }

        public static String toBits(short numbah) {
            String r00;
            if (numbah < 0) {
                int result = numbah & 0xff;
                r00 = Integer.toBinaryString(result);
            } else {
                r00 = Integer.toBinaryString(numbah);
            }
            String inBits = String.format("%8s", r00).replace(" ", "0");
            return inBits;
        }

        public static int unsignedToBytes(short a) {
            int b = (short) (a & 0xFF);
            return b;
        }

        public void printAllRegisters() {
            for (int i = 0; i < register.registers.length; i++) {
                System.out.println("Register " + (i + 1) + "\t: " + java.util.Arrays.toString(register.registers[i]));
            }
        }

        public void printAllSpecialRegisters(SpecialPurposeRegisters spregister) {
            spregister.registers[1] = spregister.codeBase;
            spregister.registers[2] = spregister.codeCounter;
            spregister.registers[3] = spregister.codeLimit;
            spregister.registers[4] = spregister.dataBase;
            spregister.registers[5] = spregister.dataCounter;
            spregister.registers[6] = spregister.dataLimit;
            spregister.registers[7] = spregister.stackBase;
            spregister.registers[8] = spregister.stackCounter;
            spregister.registers[9] = spregister.stackLimit;
            spregister.registers[10] = spregister.programCounter;
            spregister.registers[11] = (short) unsignedToBytes(spregister.instructionRegister);
            spregister.registers[12] = spregister.flag;
        }
    }

}
