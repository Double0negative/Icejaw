package runtime.util;

import javax.swing.UIManager;

public class ConvertLAF {

	public static String convert(String input) {
		System.out.println("LAF CONVERTER\n---------------------------------");
		System.out.println("INPUT: "+input);
		String output = "";
		if(input.equalsIgnoreCase("Windows") || input.equalsIgnoreCase("System") || input.equalsIgnoreCase("OS Specific")){
			output =  UIManager.getSystemLookAndFeelClassName();
		}
		else if(input.equalsIgnoreCase("Classic")){
			output =  "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
		}
		else if(input.equalsIgnoreCase("GTK")){
			output = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		}
		else if(input.equalsIgnoreCase("nimbus")){
			output =  "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		}
		else if(input.equalsIgnoreCase("motif")){
			output = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		}
		else 
			output =  "javax.swing.plaf.metal.MetalLookAndFeel";
		System.out.println("OUPUT: "+ output);
		return output;
	}
	

}
