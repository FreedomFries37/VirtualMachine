package vm;

import java.util.Arrays;
import java.util.Scanner;

public class VMRunner {
    
    public static void run(VirtualMachine virtualMachine){
        Scanner scanner = new Scanner(System.in);
        
        while (true){
            System.out.print(">: ");
            String[] args = scanner.next().split(" ");
            String mainarg = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            virtualMachine.runMethod(mainarg);
        }
    }
    
    public static void main(String[] args){
        if(args.length == 0){
            run(VirtualMachineFacory.createVMMegabytes(4));
        }else{
            if(args.length != 2) return;
            int size = Integer.parseInt(args[1]);
            switch (args[0]){
                case "b":
                    run(VirtualMachineFacory.createVMBytes(size));
                    break;
                case "kb":
                    run(VirtualMachineFacory.createVMKilobytes(size));
                    break;
                case "mb":
                    run(VirtualMachineFacory.createVMMegabytes(size));
                    break;
                    default:
                        break;
            }
        }
    }
}
