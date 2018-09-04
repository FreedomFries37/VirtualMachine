package vm.vm_modules;

import vm.VirtualMachine;
import vm.parts.ExecutableInstruction;
import vm.parts.InstructionInfo;
import vm.vm_objects.Frame;
import vm.vm_objects.frame_objects.IFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;
import vm.vm_objects.frame_objects.primitives.*;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class InstructionHandler {
    
    
    
    static boolean DEBUG = true;
    
    private VirtualMachine vm;
    private Frame frame;
    private InstructionInfo[] instructions;
    private int currentInstruction;
    private HashMap<Integer, Integer> registerAndSizeMap;
    
    {
        currentInstruction = 0;
    }
    
    public InstructionHandler(VirtualMachine vm, Frame frame, InstructionInfo[] instructions) {
        this.vm = vm;
        this.instructions = instructions;
        this.frame = frame;
        registerAndSizeMap = new HashMap<>();
    }
    
    public interface Instruction{
        IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException;
    }
    
    public class Instructions{
        Instruction jump = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                currentInstruction = inData[0] - 1;
                return new VM_void();
            }
        };
        
        Instruction writeToRegister = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IFrameStackObject stackObject = stack.pop();
                if(stackObject.isVMPrimitive()){
                    IPrimitive primitive = (IPrimitive) stackObject;
                    primitive.writeToMemory(inData[0], vm);
                }
                return new VM_void();
            }
        };
        
        Instruction execute = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                ExecutableInstruction instruction = (ExecutableInstruction) instructions[currentInstruction];
                if(stack.size() < instruction.passThroughs()) return null;
                
                int added = 0;
                int[] registers = new int[instruction.passThroughs()];
                while(added < instruction.passThroughs() && stack.peek() instanceof VM_pointer){
                    VM_pointer pointer = (VM_pointer) stack.pop();
                    registers[added++] = pointer.getData();
                }
                
                
                boolean succ = vm.getFrameCreator().createFrame(instruction.getMethod(), registers);
                if(succ) {
                    
                    stack.push(((VM_return) vm.getFrames().pop().execute()).getObject());
                }
                return new VM_void();
            }
        };
        
        Instruction print = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.peek() instanceof IPrimitive) System.out.print(((IPrimitive) stack.pop()).asString());
                else System.out.print(stack.pop());
                return new VM_void();
            }
        };
    
        Instruction read = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                Scanner scanner = new Scanner(System.in);
                String ss = scanner.next();
                int index = 0;
                for (char c : ss.toCharArray()) {
                    new VM_char().writeToMemory(c, inData[0] + index, vm);
                    index++;
                }
                return new VM_void();
            }
        };
    
        /**
         * inData[0] = register
         * inData[1] = size
         */
        Instruction clear = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 2) throw new IncorrectNumberOfRegistersException(2, inData.length);
                for (int i = 0; i < inData[1]; i++) {
                    vm.setRegister(inData[0] + i, 0);
                }
                return new VM_void();
            }
        };
    
    
        Instruction loadVar = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IPrimitive primitive = frame.getPassThrough(inData[0]);
                if(!(primitive instanceof VM_void)){
                    stack.push(primitive);
                }
                return new VM_void();
            }
        };
    
        Instruction smartPointer = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(2, inData.length);
                if(!(stack.peek() instanceof IPrimitive)) return new VM_void();
                IPrimitive p = (IPrimitive) stack.pop();
                int size = p.getSize();
                int register = vm.getMemoryManager().findSpace(size);
                if(register > -1){
                            p.writeToMemory(register,vm);
                            registerAndSizeMap.put(register, size);
                            stack.push(new VM_pointer(register));
                            //vm.memoryDump()
                }
                
                return new VM_void();
            }
        };
    
        Instruction _return = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IFrameStackObject object = new VM_void();
                if(inData[0] == 0){
                    if(stack.size() == 0) return null;
                    object = stack.pop();
                }
                
                stack.clear();
                for (Integer register : registerAndSizeMap.keySet()) {
                    int size = registerAndSizeMap.get(register);
                    for (int i = 0; i < size; i++) {
                        vm.setRegister(register + i, 0);
                    }
                }
                return new VM_return(object);
                
            }
        };
        
        
        Instruction ifZeroJump = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_Number){
                    if (((VM_Number) object).intValue() == 0){
                        currentInstruction = inData[0] - 1;
                    }
                }
               return new VM_void();
            }
        };
        
        Instruction ifGreaterJump = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_Number){
                    if (((VM_Number) object).intValue() > 0){
                        currentInstruction = inData[0] - 1;
                    }
                }
                return new VM_void();
            }
        };
    
        Instruction ifLessJump = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_Number){
                    if (((VM_Number) object).intValue() < 0){
                        currentInstruction = inData[0] - 1;
                    }
                }
                return new VM_void();
            }
        };
    
        Instruction malloc = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_int){
                    int size = ((VM_int) object).getData();
                    stack.push(vm.getMemoryManager().malloc(size));
                }
                return new VM_void();
            }
        };
    
        Instruction calloc = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 2) return null;
                int size, count;
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_int){
                    size = ((VM_int) object).getData();
                }else return null;
                object = stack.pop();
                if(object instanceof VM_int){
                    count = ((VM_int) object).getData();
                }else return null;
                stack.push(vm.getMemoryManager().calloc(size, count));
                return new VM_void();
            }
        };
    
        Instruction free = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 1) return null;
                IFrameStackObject object = stack.pop();
                if(object instanceof VM_pointer){
                    VM_pointer ptr = (VM_pointer) object;
                    vm.getMemoryManager().free(ptr);
                }else return null;
                return new VM_void();
            }
        };
        
        
        Instruction intCreate = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                for (int inDatum : inData) {
                    stack.push(new VM_int(inDatum));
                }
                return new VM_void();
            }
        };
    
        Instruction intRead = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 1) throw new IncorrectNumberOfRegistersException(1, inData.length);
                stack.push(new VM_int().interpretFromRegister(inData[0],vm));
                
                return new VM_void();
            }
        };
        Instruction intAdd = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 2) return new VM_void();
                if(stack.peek() instanceof VM_int){
                    VM_int a = (VM_int) stack.pop();
                    if(stack.peek() instanceof  VM_int){
                        VM_int b = (VM_int) stack.pop();
                        stack.push(new VM_int(a.getData() + b.getData()));
                    }
                }
                return new VM_void();
            }
        };
        Instruction intSubtract = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 2) return new VM_void();
                if(stack.peek() instanceof VM_int){
                    VM_int a = (VM_int) stack.pop();
                    if(stack.peek() instanceof  VM_int){
                        VM_int b = (VM_int) stack.pop();
                        stack.push(new VM_int(a.getData() - b.getData()));
                    }
                }
                return new VM_void();
            }
        };
        Instruction intDiv = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 2) return new VM_void();
                if(stack.peek() instanceof VM_int){
                    VM_int a = (VM_int) stack.pop();
                    if(stack.peek() instanceof  VM_int){
                        VM_int b = (VM_int) stack.pop();
                        stack.push(new VM_int(a.getData() / b.getData()));
                    }
                }
                return new VM_void();
            }
        };
        Instruction intMult = new Instruction() {
            @Override
            public IFrameStackObject execute(Stack<IFrameStackObject> stack, int[] inData) throws IncorrectNumberOfRegistersException {
                if(inData.length != 0) throw new IncorrectNumberOfRegistersException(0, inData.length);
                if(stack.size() < 2) return new VM_void();
                if(stack.peek() instanceof VM_int){
                    VM_int a = (VM_int) stack.pop();
                    if(stack.peek() instanceof  VM_int){
                        VM_int b = (VM_int) stack.pop();
                        stack.push(new VM_int(a.getData() * b.getData()));
                    }
                }
                return new VM_void();
            }
        };
    }
    
    static class IncorrectNumberOfRegistersException extends Exception{
    
        IncorrectNumberOfRegistersException(int intended, int recieved) {
            super(String.format("ERROR: Expected %d args, received %d", intended, recieved));
        }
    }
    
    private Instruction intToInstruction(int input){
        Instructions instructions = new Instructions();
        switch (input){
            default: return null;
            case 0x1: return instructions.jump;
            case 0x2: return instructions.writeToRegister;
            case 0x3: return instructions.execute;
            case 0x4: return instructions.print;
            case 0x5: return instructions.read;
            case 0x6: return instructions.clear;
    
            case 0xc: return instructions.loadVar;
            
            case 0xe: return instructions.smartPointer;
            case 0xf: return instructions._return;
            
            case 0x10: return instructions.ifZeroJump;
            case 0x11: return instructions.ifGreaterJump;
            case 0x12: return instructions.ifLessJump;
            
            case 0x20: return instructions.malloc;
            case 0x21: return instructions.calloc;
            case 0x22: return instructions.free;
            
            case 0x1001: return instructions.intCreate;
            case 0x1002: return instructions.intRead;
            case 0x1003: return instructions.intAdd;
            case 0x1004: return instructions.intSubtract;
            case 0x1005: return instructions.intDiv;
            case 0x1006: return instructions.intMult;
            
        }
    }
    
    
    
    private IFrameStackObject doInstruction(int opCode, int... registers){
        try {
            return intToInstruction(opCode).execute(frame.getObjectStack(), registers);
        }catch (IncorrectNumberOfRegistersException | NullPointerException e){
            e.printStackTrace();
        }
        return new VM_void();
    }
    
    public IFrameStackObject execute(){
        for (currentInstruction = 0; currentInstruction < instructions.length; currentInstruction++) {
            if(DEBUG) System.out.println(toString());
            IFrameStackObject object = doInstruction(instructions[currentInstruction].opCode,
                    instructions[currentInstruction].data);
            
            if(object instanceof VM_return){
                System.out.println(toString());
                return object;
            }
            
        }
        if(DEBUG) System.out.println(toString());
        return new VM_void();
    }
    
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("[Frame at %d] INSTRUCT: %d STACK: ", frame.getRegisterNum(), currentInstruction));
        for (IFrameStackObject stackObject : frame.getObjectStack()) {
            output.append(stackObject.toString());
            output.append(" ");
        }
        output.deleteCharAt(output.length()-1);
        return output.toString();
    }
}
