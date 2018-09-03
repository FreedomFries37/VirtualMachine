package vm.parts;

import vm.InstructionSet;

public class InstructionInfo {
    public final int opCode;
    public final int[] data;
    
    
    public InstructionInfo(int opCode, int... data) {
        this.opCode = opCode;
        this.data = data;
    }
    
    public InstructionInfo(InstructionSet instruction, int... data) {
        this.opCode = instruction.getValue();
        this.data = data;
    }
    
    public InstructionInfo(String instruction, int... data) {
        this.opCode = InstructionSet.valueOf(instruction).getValue();
        this.data = data;
    }
}