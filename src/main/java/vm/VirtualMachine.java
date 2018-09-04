package vm;

import vm.vm_modules.FrameCreator;
import vm.vm_modules.MemoryManager;
import vm.vm_modules.MethodTracker;
import vm.vm_objects.Frame;

import java.util.Collection;
import java.util.Stack;

public class VirtualMachine {
    
    private char[] registers;
    private Stack<Frame> frames;
    private FrameCreator frameCreator;
    private MethodTracker methodTracker;
    private MemoryManager memoryManager;
    private long starttime;
    
    VirtualMachine(int numBytes) {
        registers = new char[numBytes];
        frames = new Stack<>();
        frameCreator = new FrameCreator(this);
        methodTracker = new MethodTracker();
        memoryManager = new MemoryManager(this);
        starttime = System.currentTimeMillis();
    }
    
    /**
     * For sake of civility, range is from 0 - 255
     * @param num register number
     * @param data data to set
     */
    public void setRegister(int num, char data){
        if((int) data < 0 || (int) data > 255) data = (char) ((int)data%255);
        registers[num] = data;
    }
    
    public void setRegister(int num, int data){
        if(data < 0 || data > 127) data = (char) (data%128);
        registers[num] = (char) data;
    }
    
    public void setRegister(int num, Collection<Boolean> bits){
        if(bits.size() > 8) return;
        int flat = 0;
        for (Boolean bit : bits) {
            if(bit){
                flat = flat*2 + 1;
            }else{
                flat *= 2;
            }
        }
        setRegister(num, (char) flat);
    }
    
    public char getRegister(int num){
        return registers[num];
    }
    
    public int getSize(){
        return registers.length;
    }
    
    public Stack<Frame> getFrames() {
        return frames;
    }
    
    public FrameCreator getFrameCreator() {
        return frameCreator;
    }
    
    public MethodTracker getMethodTracker() {
        return methodTracker;
    }
    
    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
    
    public void execute(){
        frames.pop().execute();
    }
    
    public void memoryDump(){
        for (int i = 0; i < getSize(); i++) {
            StringBuilder s = new StringBuilder(Integer.toBinaryString(registers[i]));
            while (s.length() < 8) s.insert(0, '0');
            System.out.printf("[reg %d] %s\n", i, s.toString());
        }
    }
    
    public double memoryUsage(){
        double filled = 0;
        for (char register : registers) {
            if(register != '\0') filled++;
        }
        
        return filled/registers.length;
    }
    
    public void dumpInformation(){
        System.out.println(
                "VM size: " + getSize() + " bytes"
        );
        System.out.println("Usage: " + String.format("%f", memoryUsage()*100) + "%");
        System.out.println("Allocated: " + String.format("%f", memoryManager.getMemoryAllocated()*100) + "%");
        System.out.println("Uptime: " + String.format("%d ms", System.currentTimeMillis() - starttime));
    
    }
    
    public void runMethod(String name, int... registers){
        Method m = methodTracker.getMethod(name).getRef();
        if(m == null) return;
        if(frameCreator.createFrame(m, registers)) execute();
    }
}
