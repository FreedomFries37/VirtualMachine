package vm;

import vm.vm_modules.InstructionHandler;
import vm.vm_modules.InstructionInfo;
import vm.vm_objects.frame_objects.IPrimitive;

public class Method {
    
    private IPrimitive[] passThroughs;
    private InstructionInfo[] instructionInfos;
    
    public Method(IPrimitive[] passthroughs, InstructionInfo[] instructionInfos) {
        this.passThroughs = passthroughs;
        this.instructionInfos = instructionInfos;
    }
    
    public Method(InstructionInfo... instructionInfos) {
        this.instructionInfos = instructionInfos;
    }
    
    public IPrimitive[] getPassThroughs() {
        return passThroughs;
    }
    
    public InstructionInfo[] getInstructionInfos() {
        return instructionInfos;
    }
}
