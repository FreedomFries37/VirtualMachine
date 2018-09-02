package Tests;

import vm.Method;
import vm.VirtualMachine;
import vm.vm_modules.InstructionHandler;
import vm.vm_modules.InstructionInfo;

public class VMTest {
    
    
    public static void main(String[] args){
        VirtualMachine vm = new VirtualMachine(128);
        Method method1 = new Method(
                new InstructionInfo(0x1001, 0),
                new InstructionInfo(0x2, 0),
                new InstructionInfo(0x1002, 0),
                new InstructionInfo(0x1001, 100),
                new InstructionInfo(0x1004),
                new InstructionInfo(0x10, 13),
                new InstructionInfo(0x1002, 0),
                new InstructionInfo(0x1001, 1),
                new InstructionInfo(0x1003),
                new InstructionInfo(0x2, 0),
                new InstructionInfo(0x1002, 0),
                new InstructionInfo(0x4),
                new InstructionInfo(0x1, 2),
                new InstructionInfo(0x1002, 0),
                new InstructionInfo(0x4)
        );
        Method method2 = new Method(
                new InstructionInfo("icre", 0),
                new InstructionInfo("wtrg", 0),
                new InstructionInfo("ired", 0),
                new InstructionInfo("icre", 255),
                new InstructionInfo("isub"),
                new InstructionInfo("ifzj", 13),
                new InstructionInfo("ired", 0),
                new InstructionInfo("icre", 1),
                new InstructionInfo("iadd"),
                new InstructionInfo("wtrg", 0),
                new InstructionInfo("ired", 0),
                new InstructionInfo("prnt"),
                new InstructionInfo("jump", 2),
                new InstructionInfo("ired", 0),
                new InstructionInfo("prnt")
        );
    
        vm.getFrameCreator().createFrame(
                method2.getInstructionInfos()
        );
        vm.getFrameCreator().createFrame(
                method1.getInstructionInfos()
        );
        vm.execute();
        vm.memoryDump();
        vm.execute();
        vm.memoryDump();
    }
}
