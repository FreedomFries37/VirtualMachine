package vm.vm_objects;

import vm.vm_objects.frame_objects.IFrameStackObject;

import java.util.Stack;

public class Frame {

    private Stack<IFrameStackObject> objectStack;
    
    public Frame() {
        objectStack = new Stack<IFrameStackObject>();
    }
    
    public Stack<IFrameStackObject> getObjectStack() {
        return objectStack;
    }
}
