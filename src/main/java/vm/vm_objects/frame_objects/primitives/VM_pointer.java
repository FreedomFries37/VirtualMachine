package vm.vm_objects.frame_objects.primitives;

public class VM_pointer extends VM_int {
    
    public VM_pointer(int data) {
        super(data);
    }
    
    @Override
    public String toString() {
        return "VM_pointer{" +
                "data=" + getData() +
                '}';
    }
}
