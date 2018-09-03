package vm;

public enum InstructionSet{
    jump(0x1),
    wtrg(0x2),
    exec(0x3),
    prnt(0x4),
    read(0x5),
    clrr(0x6),
    
    band(0x7), //bitwise and
    borr(0x8), //bitwise or
    bnot(0x9), //bitwise not
    
    ptrd(0xa), //read pointer, first taking in a type to create from the stack, then taking a pointer
    
    flpn(0xc), //load method variable number
    freg(0xd), //get the register number of the frame
    
    stpt(0xe), //smart frame based pointer
    rtrn(0xf), //return whats in the stack if input is 0, returns a VM_void otherwise
    
    ifzj(0x10),
    ifgj(0x11),
    iflj(0x12),
    
    icre(0x1001),
    ired(0x1002),
    iadd(0x1003),
    isub(0x1004),
    idiv(0x1005),
    imul(0x1006),
    
    ccre(0x1101),
    cred(0x1102),
    cadd(0x1103),
    csub(0x1104),
    cdiv(0x1105),
    cmul(0x1106)
    
    ;
    
    
    
    private int value;
    
    InstructionSet(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}