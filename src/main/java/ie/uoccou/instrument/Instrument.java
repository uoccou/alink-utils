package ie.uoccou.instrument;

import java.lang.instrument.Instrumentation;  
public class Instrument {  
     public Instrument() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Instrumentation inst;  
    
     public static Instrumentation getInstrumentation() { return inst; }  
    
     public static void premain(String agentArgs, Instrumentation inst) {  
         System.out.println(inst.getClass() + ": " + inst);  
         Instrument.inst = inst;  
     }  
 }  
