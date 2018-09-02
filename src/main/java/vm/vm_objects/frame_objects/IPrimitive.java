package vm.vm_objects.frame_objects;

import vm.VirtualMachine;

public interface IPrimitive<T> extends IExecutableFrameStackObject<IPrimitive> {
    IPrimitive interpretFromRegister(int register, VirtualMachine vm);
    void writeToMemory(T data, int register, VirtualMachine vm);
    void writeToMemory(int register, VirtualMachine vm);
    
    int getSize();
    String asString();
    
    @Override
    default boolean isVMPrimitive() {
        return true;
    }
    
    @Override
    default boolean isRecursive() {
        return false;
    }
    
}
