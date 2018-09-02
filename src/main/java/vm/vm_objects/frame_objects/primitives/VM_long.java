package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IExecutableFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;

public class VM_long implements IPrimitive<VM_long> {
    
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        return null;
    }
    
    public void writeToMemory(VM_long data, int register, VirtualMachine vm) {
    
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
    
    }
    
    public IPrimitive getValue() {
        return null;
    }
    
    public void getInfo() {
    
    }
    
    @Override
    public int getSize() {
        return 0;
    }
    
    @Override
    public String asString() {
        return null;
    }
}
