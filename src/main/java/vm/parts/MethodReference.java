package vm.parts;

import vm.Method;



public class MethodReference {
    
    private Method method;
    
    public MethodReference(Method method) {
        this.method = method;
    }
    
    public MethodReference(){ }
    
    public void setRef(Method method){
        this.method = method;
    }
    
    public Method getRef(){
        return method;
    }
    
    public void clear(){
        method = null;
    }
}
