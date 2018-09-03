package vm.vm_objects;

import vm.VirtualMachine;
import vm.vm_modules.InstructionHandler;
import vm.parts.InstructionInfo;
import vm.vm_objects.frame_objects.IFrameStackObject;
import vm.vm_objects.frame_objects.IPrimitive;
import vm.vm_objects.frame_objects.primitives.VM_void;

import java.util.HashMap;
import java.util.Stack;

public class Frame {

    private Stack<IFrameStackObject> objectStack;
    private InstructionInfo[] instructionInfos;
    private InstructionHandler handler;
    private VirtualMachine vm;
    private int registerNum;
    private int size;
    
    private HashMap<Integer, IPrimitive> passThroughs;
    
    public Frame(VirtualMachine vm, InstructionInfo[] instructionInfos) {
        objectStack = new Stack<>();
        this.instructionInfos = instructionInfos;
        handler = new InstructionHandler(vm, this, instructionInfos);
        passThroughs = new HashMap<>();
        this.vm = vm;
    }
    
    
    public boolean plantData(int size){
        this.size = size;
        if(size == 0) return true;
        for (int i = 0; i < vm.getSize(); i++) {
            if(vm.getRegister(i) == '\0'){
                boolean spaceFound = true;
                for (int i1 = 1; i1 < size && i < vm.getSize(); i1++) {
                    if(vm.getRegister(i + i1) != '\0'){
                        spaceFound = false;
                        break;
                    }
                }
                if(spaceFound){
                    registerNum = i;
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean plantData(IPrimitive[] primitives, int[] registers){
        if(primitives.length != registers.length) return false;
        int totalSize = 0;
        for (IPrimitive primitive : primitives) {
            totalSize += primitive.getSize();
        }
        boolean success = plantData(totalSize);
        if(success) {
            int index = 0;
            for (IPrimitive primitive : primitives) {
                passThroughs.put(index, primitive.interpretFromRegister(registers[index], vm));
                index++;
            }
        }
        return success;
    }
    
    public Stack<IFrameStackObject> getObjectStack() {
        return objectStack;
    }
    
    public InstructionInfo[] getInstructionInfos() {
        return instructionInfos;
    }
    
    public IFrameStackObject execute(){
        return handler.execute();
    }
    
    public int getRegisterNum() {
        return registerNum;
    }
    
    public int getSize() {
        return size;
    }
    
    public IPrimitive getPassThrough(int index){
        return passThroughs.getOrDefault(index, new VM_void());
    }
}
