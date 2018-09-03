package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IExecutableFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class VM_long implements IPrimitive<Long> {
    
    private long data;
    private static long mask = (long) Math.pow(2,63) - 1;
    
    public VM_long(long data) {
        this.data = data;
    }
    
    public VM_long() {
    }
    
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        boolean positive = vm.getRegister(register)>>7 == 0;
        long data = 0;
        for (int i = 0; i < 8; i++) {
            data <<= 8;
            data |= vm.getRegister(register + i);
        }
        if(!positive) {
            data = -((~data + 1) & mask);
        }
    
    
        return new VM_long(data);
    }
    
    public void writeToMemory(Long data, int register, VirtualMachine vm) {
        Stack<Boolean> bits = new Stack<>();
    
        long twos = -(data & mask) + (data & ~mask);
    
        while (twos != 0){
            boolean addBit = twos%2!=0;
            bits.push(addBit);
            twos /= 2;
        }
        while (bits.size() < 64){
            bits.push(false);
        }
        Collections.reverse(bits);
        for (int i = 0; i < 8; i++) {
            List<Boolean> sublist = bits.subList(i*8,(i+1)*8);
            vm.setRegister(register + i, sublist);
        }
    }
    
    public long getData(){
        return data;
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
        writeToMemory(data,register,vm );
    }
    
    public IPrimitive getValue() {
        return null;
    }
    
    public void getInfo() {
    
    }
    
    @Override
    public int getSize() {
        return 8;
    }
    
    @Override
    public String asString() {
        return null;
    }
}
