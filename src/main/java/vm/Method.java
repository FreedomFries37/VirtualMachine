package vm;

import vm.parts.InstructionInfo;
import vm.vm_objects.frame_objects.IPrimitive;

public class Method {
    
    private IPrimitive[] passThroughs;
    private InstructionInfo[] instructionInfos;
    
    public Method(IPrimitive[] passthroughs, InstructionInfo... instructionInfos) {
        this.passThroughs = passthroughs;
        this.instructionInfos = instructionInfos;
    }
    
    public Method(InstructionInfo... instructionInfos) {
        this.instructionInfos = instructionInfos;
        passThroughs = new IPrimitive[0];
    }
    
    public IPrimitive[] getPassThroughs() {
        return passThroughs;
    }
    
    public InstructionInfo[] getInstructionInfos() {
        return instructionInfos;
    }
}
