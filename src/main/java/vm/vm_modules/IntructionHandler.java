package vm.vm_modules;

import vm.VirtualMachine;
import vm.vm_objects.frame_objects.IFrameStackObject;

import java.util.Stack;

public class IntructionHandler {
    
    private VirtualMachine vm;
    
    public IntructionHandler(VirtualMachine vm) {
        this.vm = vm;
    }
    
    public enum Instruction{
        jump{
            void execute(Stack<IFrameStackObject> stack, int... registers) throws IncorrectNumberOfRegistersException {
                if(registers.length != 1) throw new IncorrectNumberOfRegistersException(1,registers.length);
                
            }
        };
        
        abstract void execute(Stack<IFrameStackObject> stack, int... registers) throws IncorrectNumberOfRegistersException;
    }
    
    static class IncorrectNumberOfRegistersException extends Exception{
    
        IncorrectNumberOfRegistersException(int intended, int recieved) {
            super(String.format("ERROR: Expected %d args, received %d", intended, recieved));
        }
    }
    
    private Instruction intToInstruction(int input){
        switch (input){
            default: return null;
            case 1: return Instruction.jump;
        }
    }
    
    public void doInstruction(int opCode, int... registers){
        try {
            intToInstruction(opCode).execute(vm.getFrames().peek().getObjectStack(), registers);
        }catch (IncorrectNumberOfRegistersException e){
            e.printStackTrace();
        }
    }
}
