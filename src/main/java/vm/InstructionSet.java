package vm;

public enum InstructionSet{
    jump(0x1),
    wtrg(0x2),
    exec(0x3),
    prnt(0x4),
    read(0x5),
    clrr(0x6),
    
    
    ifzj(0x10),
    ifgj(0x11),
    iflj(0x12),
    
    icre(0x1001),
    ired(0x1002),
    iadd(0x1003),
    isub(0x1004),
    idiv(0x1005),
    imul(0x1006);
    
    
    
    private int value;
    
    InstructionSet(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}