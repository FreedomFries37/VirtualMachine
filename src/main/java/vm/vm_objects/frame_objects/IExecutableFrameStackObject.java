package vm.vm_objects.frame_objects;

import java.util.Stack;

public interface IExecutableFrameStackObject<T extends IFrameStackObject> extends IFrameStackObject {
    T getValue();
}
