package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

import java.util.ArrayList;
import java.util.Stack;

public class VM_int implements IPrimitive<Integer> {
    
    private int data;
    
    private VM_int(int data) {
        this.data = data;
    }
    
    VM_int(){
        data = 0;
    }
    
    public VM_int interpretFromRegister(int register, VirtualMachine vm) {
        long data = 0;
        for (int i = 0; i < 4; i++) {
            data <<= 8;
            data |= vm.getRegister(register + i);
        }
        return new VM_int((int) data);
    }
    
    public void writeToMemory(Integer data, int register, VirtualMachine vm) {
        Stack<Boolean> bits = new Stack<Boolean>();
        long twosComp = (long) 2e32d - data;
        while (twosComp > 0){
            boolean addBit = twosComp%2!=0;
            bits.push(addBit);
            twosComp /= 2;
        }
        while (bits.size() < 32){
            bits.push(false);
        }
        for (int i = 0; i < 4; i++) {
            vm.setRegister(register + i, bits.subList(i*8,(i+1)*8));
        }
    }
   
    
    public IPrimitive getValue() {
        return this;
    }
    
    public void getInfo() {
        System.out.println("Int value: " + data);
    }
}
