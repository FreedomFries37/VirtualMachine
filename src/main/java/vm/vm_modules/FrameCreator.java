package vm.vm_modules;

import vm.Method;
import vm.VirtualMachine;
import vm.parts.InstructionInfo;
import vm.vm_objects.Frame;
import vm.vm_objects.frame_objects.IPrimitive;

public class FrameCreator {
    
    private VirtualMachine vm;
    
    public FrameCreator(VirtualMachine vm) {
        this.vm = vm;
    }
    
    public boolean createFrame(InstructionInfo[] instructionInfos, int[] registers, IPrimitive... passThroughs){
        Frame f = new Frame(vm, instructionInfos);
        boolean succ = passThroughs == null || f.plantData(passThroughs, registers);
        if(succ) {
            vm.getFrames().push(f);
        }
        return succ;
    }
    
    public boolean createFrame(IPrimitive[] passThroughs, int[] registers,
                               InstructionInfo... instructionInfos){
        Frame f = new Frame(vm, instructionInfos);
        boolean succ = f.plantData(passThroughs, registers);
        if(succ) {
            vm.getFrames().push(f);
        }
        return succ;
    }
    
    public boolean createFrame(Method method, int[] registers){
        return createFrame(method.getPassThroughs(), registers, method.getInstructionInfos());
    }
    
    public boolean createFrame(InstructionInfo... instructionInfos){
        Frame f = new Frame(vm, instructionInfos);
        vm.getFrames().push(f);
        return true;
    }
    
}
