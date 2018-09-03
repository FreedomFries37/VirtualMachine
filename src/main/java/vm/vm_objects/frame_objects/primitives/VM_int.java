package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class VM_int implements IPrimitive<Integer>, VM_Number {
    
    private int data;
    private static long mask = (long) Math.pow(2,31) - 1;
    
    public VM_int(int data) {
        this.data = data;
    }
    
    public VM_int(){
        data = 0;
    }
    
    public VM_int interpretFromRegister(int register, VirtualMachine vm) {
        
        boolean positive = vm.getRegister(register)>>7 == 0;
        long data = 0;
        for (int i = 0; i < 4; i++) {
            data <<= 8;
            data |= vm.getRegister(register + i);
        }
        if(!positive) {
            data = -((~data + 1) & mask);
        }
        
        
        return new VM_int((int) data);
    }
    
    public void writeToMemory(Integer data, int register, VirtualMachine vm) {
        Stack<Boolean> bits = new Stack<>();
        
        long twos = -(data & mask) + (data & ~mask);
        
        while (twos != 0){
            boolean addBit = twos%2!=0;
            bits.push(addBit);
            twos /= 2;
        }
        while (bits.size() < 32){
            bits.push(false);
        }
        Collections.reverse(bits);
        for (int i = 0; i < 4; i++) {
            List<Boolean> sublist = bits.subList(i*8,(i+1)*8);
            vm.setRegister(register + i, sublist);
        }
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
        writeToMemory(data, register, vm);
    }
    
    public int getData() {
        return data;
    }
    
    public IPrimitive getValue() {
        return this;
    }
    
    public void getInfo() {
        System.out.println("Int value: " + data);
    }
    
    @Override
    public int intValue() {
        return data;
    }
    
    @Override
    public double doubleValue() {
        return (double) data;
    }
    
    @Override
    public int getSize() {
        return 4;
    }
    
    @Override
    public String toString() {
        return "VM_int{" +
                "data=" + data +
                '}';
    }
    
    @Override
    public String asString() {
        return "" + data;
    }
}
