package vm.vm_modules;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IPrimitive;
import vm.vm_objects.frame_objects.primitives.VM_pointer;

import java.util.HashMap;

public class MemoryManager {
    
    private VirtualMachine vm;
    private boolean[] allocatedMemory;
    private HashMap<VM_pointer, Integer> registerToAllocatedSpace;
    
    public MemoryManager(VirtualMachine vm) {
        this.vm = vm;
        allocatedMemory = new boolean[vm.getSize()];
        registerToAllocatedSpace = new HashMap<>();
    }
    
    public int findSpace(int size){
        int register = -1;
        for (int i = 0; i < allocatedMemory.length; i++) {
            if(!allocatedMemory[i]){
                boolean spaceFound = true;
                for (int j = i; j < allocatedMemory.length; j++) {
                    if(allocatedMemory[i]){
                        spaceFound = false;
                        break;
                    }
                }
            
                if(spaceFound){
                    register = i;
                    break;
                }
            }
        }
        
        return register;
    }
    
    public VM_pointer malloc(int size){
        int register = findSpace(size);
        if(register == -1){
            return null;
        }
    
        for (int i = 0; i < size; i++) {
            allocatedMemory[register + i] = true;
        }
        
        VM_pointer output = new VM_pointer(register);
        registerToAllocatedSpace.put(output, size);
        return output;
    }
    
    public VM_pointer calloc(int size, int count){
        VM_pointer output = malloc(size * count);
        if(output == null) return null;
        
        int register = output.getData();
        for(int i = 0; i < count*size; i += size){
            for (int i1 = 0; i1 < size; i1++) {
                vm.setRegister(register + i + i1, 0);
            }
        }
        
        return output;
    }
    
    public void free(VM_pointer ptr){
        int spaceAllocated = registerToAllocatedSpace.getOrDefault(ptr, 0);
        for (int i = 0; i < spaceAllocated; i++) {
            allocatedMemory[ptr.getData() + i] = false;
        }
    }
    
    public double getMemoryAllocated(){
        double count = 0;
        for (boolean b : allocatedMemory) {
            if(b) count++;
        }
        
        return count/allocatedMemory.length;
    }
}
