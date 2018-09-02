package vm.vm_objects.frame_objects;

public interface IFrameStackObject {
    void getInfo();
    default boolean isVMPrimitive() {
        return false;
    }
    default boolean isRecursive(){
        return false;
    }
    
    
}
