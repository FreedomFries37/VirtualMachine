package vm;

public class VirtualMachineFacory {
    
    public static VirtualMachine createVMBytes(int bytes){
        return new VirtualMachine(bytes);
    }
    
    public static VirtualMachine createVMKilobytes(int kilo){
        return createVMBytes(kilo * 1024);
    }
    
    public static VirtualMachine createVMMegabytes(int mega){
        return createVMKilobytes(mega * 1024);
    }
}
