package vm.vm_modules;

import vm.VirtualMachine;
import vm.vm_objects.Frame;
import vm.vm_objects.frame_objects.IPrimitive;

public class FrameCreator {
    
    private VirtualMachine vm;
    
    public FrameCreator(VirtualMachine vm) {
        this.vm = vm;
    }
    
    public boolean createFrame(InstructionInfo[] instructionInfos, IPrimitive... passThroughs){
        Frame f = new Frame(vm, instructionInfos);
        boolean succ = f.plantData(passThroughs);
        if(succ) {
            vm.getFrames().push(f);
        }
        return succ;
    }
    
    public boolean createFrame(IPrimitive[] passThroughs,
                               InstructionInfo... instructionInfos){
        Frame f = new Frame(vm, instructionInfos);
        boolean succ = f.plantData(passThroughs);
        if(succ) {
            vm.getFrames().push(f);
        }
        return succ;
    }
    
    public boolean createFrame(InstructionInfo... instructionInfos){
        Frame f = new Frame(vm, instructionInfos);
        vm.getFrames().push(f);
        return true;
    }
    
}
