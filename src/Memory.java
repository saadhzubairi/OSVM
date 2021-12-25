class Memory {
    static byte[] memory = new byte[65536];
    int pagesize = 128;
    static Pagetable pgTable = new Pagetable((short) 128);

    Memory() {
    }

    int loadCodeIntoMemory(byte[] code, int framesize) {

        // THIS INSERTS CODE INTO MAIN MEMORY FRAME BY FRAME
        int loc = checkAvailableFrames();

        if (loc == -1) {
            System.out.println("Memory is Full");
        }

        else {
            // need to determine what this constant 1 is
            pgTable.setframe((short) 1, loc);

            for (int j = 0; j < framesize; j++) {
                // main memory, each location further divided by pages
                // you do location*128 so you reach the position of that page
                // where it is found IN MAIN MEMORYYYYYYY
                int index = (loc * 128) + j;
                memory[index] = code[j];
            }

        }
        return loc;
    }

    int checkAvailableFrames() {

        // check for empty frames inside the WHOLEEEEEEEE ARRAY
        int location = 0;

        for (location = 0; location < pgTable.size; location++) {
            if (pgTable.getframe(location) == 0) {
                return location;
            }
        }
        return -1;

    }

    byte getMemory(int i) {
        return memory[i];
    }

    byte byte1(short x) {
        byte temp = (byte) (x >> 8);
        return temp;
    }

    byte byte2(short x) {
        return (byte) x;
    }

    short concat(byte byte1, byte byte2) {
        return (short) ((byte1 << 8) | (byte2 & 0xFF));
    }

    @Override
    public String toString() {
        return "Memory{" +
                "pagesize=" + pagesize +
                "}";
    }
}