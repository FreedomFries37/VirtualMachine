package vm.vm_modules;

public class ExecutableInstruction extends InstructionInfo{
    InstructionInfo[] instructions;
    
    public ExecutableInstruction(InstructionInfo[] instructions) {
        super(0x3);
        this.instructions = instructions;
    }
}