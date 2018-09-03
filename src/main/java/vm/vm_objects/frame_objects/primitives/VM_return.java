package vm.vm_objects.frame_objects.primitives;

import vm.vm_objects.frame_objects.IExecutableFrameStackObject;
import vm.vm_objects.frame_objects.IFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;

public class VM_return implements IFrameStackObject {
    
    private IFrameStackObject primitive;
    public VM_return(IFrameStackObject primitive) {
        this.primitive = primitive;
        if(primitive.isVMPrimitive()) this.primitive = ((IExecutableFrameStackObject) primitive).getValue();
    }
    
    public IFrameStackObject getObject() {
        return primitive;
    }
    
    @Override
    public void getInfo() {
    
    }
    
    @Override
    public boolean isVMPrimitive() {
        return false;
    }
    
    @Override
    public boolean isRecursive() {
        return false;
    }
}
