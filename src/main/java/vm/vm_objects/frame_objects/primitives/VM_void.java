package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;

public class VM_void implements IFrameStackObject, IPrimitive<Integer> {
    
    @Override
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        return null;
    }
    
    @Override
    public void writeToMemory(Integer data, int register, VirtualMachine vm) {
    
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
    
    }
    
    @Override
    public int getSize() {
        return 0;
    }
    
    @Override
    public String asString() {
        return null;
    }
    
    @Override
    public IPrimitive getValue() {
        return null;
    }
    
    public void getInfo() {
    
    }
}
