package Tests;

import vm.Method;
import vm.VirtualMachine;
import vm.VirtualMachineFacory;
import vm.parts.ExecutableInstruction;
import vm.parts.InstructionInfo;
import vm.vm_objects.frame_objects.IPrimitive;
import vm.vm_objects.frame_objects.primitives.VM_int;

public class VMTest {
    
    
    public static void main(String[] args){
        VirtualMachine vm = VirtualMachineFacory.createVMMegabytes(4);
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
                new InstructionInfo("icre", 2000000000),
                new InstructionInfo("wtrg", 0),
                new InstructionInfo("icre", -1),
                new InstructionInfo("ired", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("iflj", 13),
                new InstructionInfo("ired", 0),
                new InstructionInfo("icre", 10000),
                new InstructionInfo("iadd"),
                new InstructionInfo("wtrg", 0),
                new InstructionInfo("ired", 0),
                new InstructionInfo("prnt"),
                new InstructionInfo("jump", 2),
                new InstructionInfo("ired", 0),
                new InstructionInfo("prnt")
        );
        
        Method countDown, main;
        vm.getMethodTracker().declareMethod("countDown");
        countDown = new Method(new IPrimitive[]{
                new VM_int()
        },
                new InstructionInfo("flpn", 0),
                new InstructionInfo("ifzj", 7),
                new InstructionInfo("icre", 1),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("stpt"),
                new ExecutableInstruction("countDown", vm),
                new InstructionInfo("rtrn", 1)
        );
    
        vm.getMethodTracker().declareMethod("factorial");
        
        Method fact = new Method(new IPrimitive[]{
            new VM_int()
        },
                new InstructionInfo("icre", 1),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("ifzj", 12),
                new InstructionInfo("icre", 1),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("stpt"),
                new ExecutableInstruction("factorial",vm),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("imul"),
                new InstructionInfo("rtrn", 0),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("rtrn", 0)
        );
    
    
        vm.getMethodTracker().declareMethod("fib");
        Method fib = new Method(new IPrimitive[]{
                new VM_int()
        },
                new InstructionInfo("flpn", 0),
                new InstructionInfo("ifgj", 4),
                new InstructionInfo("icre", 1),
                new InstructionInfo("rtrn", 0),
                new InstructionInfo("icre", 1),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("ifgj", 10),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("rtrn", 0),
                new InstructionInfo("icre", 1),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("stpt"),
                new ExecutableInstruction("fib",vm),
                new InstructionInfo("icre", 2),
                new InstructionInfo("flpn", 0),
                new InstructionInfo("isub"),
                new InstructionInfo("stpt"),
                new ExecutableInstruction("fib",vm),
                new InstructionInfo("iadd"),
                new InstructionInfo("rtrn", 0)
        );
        
        main = new Method(
                new InstructionInfo("icre", 10),
                new InstructionInfo("stpt"),
                new ExecutableInstruction("fib", vm),
                new InstructionInfo("prnt"),
                new InstructionInfo("rtrn", 1)
        );
        
        
        
        
    
        /*
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
        */
        
        
        vm.getMethodTracker().addMethod("factorial", fact);
        vm.getMethodTracker().addMethod("fib", fib);
        vm.getMethodTracker().addMethod("main", main);
        vm.runMethod("main");
        vm.dumpInformation();
    }
}
