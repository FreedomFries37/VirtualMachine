package vm.vm_objects.frame_objects.primitives;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;

//TODO: this entire class
public class VM_double implements IPrimitive<Double> {
    
    private double data;
    
    public VM_double(double data) {
        this.data = data;
    }
    
    public VM_double() {
    }
    
    @Override
    public IPrimitive interpretFromRegister(int register, VirtualMachine vm) {
        return null;
    }
    
    @Override
    public void writeToMemory(Double data, int register, VirtualMachine vm) {
    
    }
    
    @Override
    public void writeToMemory(int register, VirtualMachine vm) {
    
    }
    
    public double getData() {
        return data;
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
    
    @Override
    public void getInfo() {
    
    }
}
