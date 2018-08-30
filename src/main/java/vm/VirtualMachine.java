package vm;

import vm.vm_objects.Frame;

import java.util.Collection;
import java.util.Stack;

public class VirtualMachine {
    
    private char[] registers;
    private Stack<Frame> frames;
    
    public VirtualMachine(int numBytes) {
        registers = new char[numBytes];
        frames = new Stack<Frame>();
    }
    
    /**
     * For sake of civility, range is from 0 - 127
     * @param num register number
     * @param data data to set
     */
    public void setRegister(int num, char data){
        if((int) data < 0 || (int) data > 127) data = (char) ((int)data%128);
        registers[num] = data;
    }
    
    public void setRegister(int num, Collection<Boolean> bits){
        if(bits.size() > 8) return;
        int flat = 0;
        for (Boolean bit : bits) {
            if(bit){
                flat = flat*2 + 1;
            }else{
                flat *= 2;
            }
        }
        setRegister(num, (char) flat);
    }
    
    public char getRegister(int num){
        return registers[num];
    }
    
    public Stack<Frame> getFrames() {
        return frames;
    }
    
}
