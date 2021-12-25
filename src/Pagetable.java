public class Pagetable {
    //  --> 65536/128
    short  pagetable[] = new short [512];
    int flag [] = new int[512];

    short size  = 512;

    Pagetable ( short size ) {
        this.size = size;
        pagetable = null;
        flag = null;
        pagetable = new short [this.size];
        flag = new int[this.size];

    }

    public int getframe ( int pageNo) {
        //getter for the frame when given page number
        return pagetable[pageNo];
    }

    public void setframe ( short frameNo, int pageNo) {
        pagetable[pageNo] = frameNo;
        flag[pageNo] = 1;
    }

    public int deleteframe ( int i ) {
        flag[i] = 0;
        return pagetable[i];
    }

    int getflag ( int i  ) {
        return flag[i];
    }

    int checkPageEmpty ( int i  ) {
        return pagetable[i];
    }

    public void printPageTable () {
        for ( int i =0 ; i< size; i++ ) {
            System.out.println(pagetable[i] + " " + flag[i]);
        }

    }

    public void setflag( short frameNo, int pageNo) {
        pagetable[pageNo] = frameNo;
        flag[pageNo] = 1;

    }

    @Override
    public String toString() {
        return "pagetable [flag=. pagetable=. size="
                + size + "]";
    }
}
