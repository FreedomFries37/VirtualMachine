package vm;

public enum InstructionSet{
    jump(0x1), //jump
    wtrg(0x2), //write to register
    exec(0x3), //execute "method"
    prnt(0x4), //print whats on top of the stack
    read(0x5), //read an input from a scanner
    clrr(0x6), //clears a register
    
    band(0x7), //bitwise and
    borr(0x8), //bitwise or
    bnot(0x9), //bitwise not
    
    ptrd(0xa), //read pointer, first taking in a type to create from the stack, then taking a pointer
    
    flpn(0xc), //load method variable number
    freg(0xd), //get the register number of the frame
    
    stpt(0xe), //smart frame based pointer, auto-deletes
    rtrn(0xf), //return whats in the stack if input is 0, returns a VM_void otherwise
    
    ifzj(0x10), //if whats on the stack is equal to 0, jump
    ifgj(0x11), //if whats on the stack is greater than 0, jump
    iflj(0x12), //if whats on the stack is less than 0, jump
    
    mllc(0x20), //works like C malloc
    cllc(0x21), //works like C calloc
    free(0x22), //works like C free
    usep(0x23), //takes in a pointer, then a primitive and writes to the pointer
    
    icre(0x1001), //create a constant integer
    ired(0x1002), //read an integer from a register
    iadd(0x1003), //takes two ints from the stack and adds them
    isub(0x1004), //takes two ints from the stack and subtracts them
    idiv(0x1005), //takes two ints from the stack and divides them
    imul(0x1006), //takes two ints from the stack and multiplies them
    
    ccre(0x1101), //creates a constant character
    cred(0x1102), //reads a character from a register
    cadd(0x1103), //takes two chars from the stack and adds them
    csub(0x1104), //takes two chars from the stack and subtracts them
    cdiv(0x1105), //takes two chars from the stack and divides them
    cmul(0x1106) //takes two chars from the stack and multiplies them
    
    ;
    
    
    
    private int value;
    
    InstructionSet(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}