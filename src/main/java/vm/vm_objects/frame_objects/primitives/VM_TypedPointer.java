package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

public class VM_TypedPointer implements IPrimitive<IPrimitive> {
    
    private IPrimitive object;
    private int register;
    
    public VM_TypedPointer(IPrimitive object, int register) {
        this.object = object;
        this.register = register;
    }
    
    public VM_TypedPointer(int register) {
        object = new VM_void();
        this.register = register;
    }
    
    public VM_TypedPointer(){
    }
    
    @Override
    public void writeToMemory(IPrimitive data, int register, VirtualMachine vm) {
    
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
    
    }
    
    @Override
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        return null;
    }
    
    @Override
    public int getSize() {
        return 1;
    }
    
    @Override
    public String asString() {
        return null;
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
        return true;
    }
    
    @Override
    public boolean isRecursive() {
        return true;
    }
    
    
    
   
}
