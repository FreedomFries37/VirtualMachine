package vm.vm_modules;

import vm.Method;
import vm.parts.MethodReference;

import java.util.HashMap;

public class MethodTracker {
    
    
    private HashMap<String, MethodReference> methodHashMap;
    
    public MethodTracker() {
        methodHashMap = new HashMap<>();
        
    }
    
    public void addMethod(String name, Method method){
        if(!methodHashMap.containsKey(name)){
            methodHashMap.put(name, new MethodReference(method));
        }else{
            methodHashMap.get(name).setRef(method);
        }
    }
    
    public void declareMethod(String name){
        if(!methodHashMap.containsKey(name)){
            methodHashMap.put(name, new MethodReference());
        }
    }
    
    
    
    public MethodReference getMethod(String name){
        return methodHashMap.getOrDefault(name, null);
    }
    
}
