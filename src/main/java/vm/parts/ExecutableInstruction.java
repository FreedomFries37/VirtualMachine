package vm.parts;

import vm.Method;
import vm.VirtualMachine;

public class ExecutableInstruction extends InstructionInfo{
    private MethodReference method;
    
    public ExecutableInstruction(String name, VirtualMachine virtualMachine) {
        super(0x3);
        method = virtualMachine.getMethodTracker().getMethod(name);
    }
    
    public ExecutableInstruction(Method m) {
        super(0x3);
        method = new MethodReference(m);
    }
    
    public Method getMethod() {
        return method.getRef();
    }
    
    public int passThroughs(){
        if(method.getRef() != null){
            return method.getRef().getPassThroughs().length;
        }
        return 0;
    }
}