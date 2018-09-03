package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class VM_char implements IPrimitive<Character> {
    
    private char data;
    
    public VM_char(char data) {
        this.data = data;
    }
    
    public VM_char(){
    
    }
    
    @Override
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        int data = 0;
        for (int i = 0; i < 2; i++) {
            data <<= 8;
            data |= vm.getRegister(register + i);
        }
        return new VM_char((char) data);
    }
    
    @Override
    public void writeToMemory(Character data, int register, VirtualMachine vm) {
        Stack<Boolean> bits = new Stack<>();
        int num = (int) data;
        while (num > 0){
            bits.push(num%2!=0);
            num /= 2;
        }
        while (bits.size() < 16){
            bits.push(false);
        }
        Collections.reverse(bits);
        for (int i = 0; i < 2; i++) {
            List<Boolean> sublist = bits.subList(i*8,(i+1)*8);
            vm.setRegister(register + i, sublist);
        }
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
        writeToMemory(data, register, vm);
    }
    
    @Override
    public IPrimitive getValue() {
        return this;
    }
    
    @Override
    public void getInfo() {
    
    }
    
    @Override
    public boolean isVMPrimitive() {
        return true;
    }
    
    @Override
    public int getSize() {
        return 2;
    }
    
    @Override
    public String asString() {
        return "" + data;
    }
    
    @Override
    public String toString() {
        return "VM_char{" +
                "data=" + data +
                '}';
    }
}
