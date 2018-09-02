package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

public class VM_char implements IPrimitive<Character> {
    
    private char data;
    
    public VM_char(char data) {
        this.data = data;
    }
    
    public VM_char(){
    
    }
    
    @Override
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        return null;
    }
    
    @Override
    public void writeToMemory(Character data, int register, VirtualMachine vm) {
    
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
    
    }
    
    @Override
    public IPrimitive getValue() {
        return null;
    }
    
    @Override
    public void getInfo() {
    
    }
    
    @Override
    public boolean isVMPrimitive() {
        return false;
    }
    
    @Override
    public int getSize() {
        return 2;
    }
    
    @Override
    public String asString() {
        return "" + data;
    }
}
