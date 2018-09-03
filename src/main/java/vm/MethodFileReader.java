package vm;

import vm.parts.InstructionInfo;
import vm.vm_objects.frame_objects.IPrimitive;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MethodFileReader {
    
    private HashMap<String, Method> nameToMethodMap;
    private String path;
    
    public MethodFileReader(String path) {
        this.path = path;
        nameToMethodMap = new HashMap<>();
    }
    
    public HashMap<String, Method> getNameToMethodMap() {
        return nameToMethodMap;
    }
    
    public boolean createMethodMap(){
        File f = new File(path);
        
        if(!f.exists()) return false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            LinkedList<String> list = new LinkedList<>();
            reader.lines().forEach( (o) -> list.add(o));
            list.removeIf(
                        (o) -> o.startsWith("#")
            );
            boolean inMethod = false;
            LinkedList<InstructionInfo> info = new LinkedList<>();
            LinkedList<IPrimitive> pts = new LinkedList<>();
            String current = "";
            
            for (String s : list) {
                if(inMethod){
                    if(s.matches("\\w+")){
                    
                    }else{
                        inMethod = false;
                        nameToMethodMap.replace(current, new Method(
                                        pts.toArray(new IPrimitive[pts.size()]),
                                        info.toArray(new InstructionInfo[info.size()])
                                ));
                        info = new LinkedList<>();
                        pts = new LinkedList<>();
                    }
                }else{
                    if(s.matches("\\w+(\\s*\\w+\\s*)*")){
                        current = s;
                        inMethod = true;
                        nameToMethodMap.put(current, null);
                        
                    }
                }
            }
            return true;
        }catch (FileNotFoundException e){
            return false;
        }
    }
}
