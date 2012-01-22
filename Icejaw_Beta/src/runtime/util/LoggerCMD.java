package runtime.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import runtime.main.index;

public class LoggerCMD implements ActionListener{
	String dir = "";
	{try{dir = System.getProperty("user.home") + "/Desktop";	}catch (Exception e){}}
	
	Object output = "j";
	String args = "";
	String command ="";
	public static String cmdVersion = "35";
	
	String varIndex[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	Object var[] = new Object[26];
	
	
	
	
	
	public void parser(String input){
		args = "";
		
		if(input.indexOf("$")!= -1){
		int [] flags = new int[6];
		flags[0]=input.indexOf("$");
		flags[4]=input.indexOf(" ", flags[0]);
		if(flags[4]==-1){
			flags[4] = input.length();
		}
		flags[5] = input.length();
		
		try{
		if(input.substring((flags[0]+2), flags[0]+3).equals("=")){
			flags[1] = 1;
		}
		}catch(Throwable e){
			flags[1]=0;
		}
		flags[3]=getVarLoc(input.substring(flags[0]+1,flags[0]+2));
		if(flags[1]==1){
			var[flags[3]] = input.substring(flags[0]+3, flags[5]);
			
		}
		else{
			input = input.substring(0, flags[0])+var[flags[3]]+ input.substring(flags[4]);
		}
		
	}
		
		
		if(input.indexOf(" ")!= -1){
			command = input.trim().substring(0, input.indexOf(" "));
			args = input.trim().substring(input.indexOf(" ")).trim();
		}
		else
			command = input;

		try{

			ClassLoader classLoader = LoggerCMD.class.getClassLoader();

			Class aClass = classLoader.loadClass("runtime.util.LoggerCMD");

			Object iClass = aClass.newInstance();
			// get the method
			Class[] paramString = new Class[1];	
			paramString[0] = String.class;
			Method thisMethod =  aClass.getDeclaredMethod(command,paramString);
			// call the method
			output = thisMethod.invoke(iClass,args);
		}
		catch(Throwable e){
			Logger.error("Command Failed");
			StackTraceElement[] u = e.getStackTrace();
			for(int a = 0; a<u.length; a++){
				Logger.error("\t"+u[a]);
			}
		}
		finally{
			Logger.tf.setText("");
		}

	
	}
	public int getVarLoc(String a){
		for(int b =0;b<26;b++){
			if(varIndex[b].equalsIgnoreCase(a)){
				return b;
				
			}
		}
		return -1;
	}
	
	public String memory(String args){
		long used = Runtime.getRuntime().totalMemory();
		double left = (used*100) / (Runtime.getRuntime().maxMemory());
		
		Logger.cmd("Memory Usage: "+ left);
		
		return "done";
	}
	public String exit(String args){
		if(args.equalsIgnoreCase("force")){
			System.exit(0);
			return "";
		}
		index.kill();
		return "done";
	}
	public String stoploading(String args){
		index.networkUpdate = true;
		index.killNetHelper = true;
		Logger.cmd("Network Interactions Halted");
		return "Done";
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		parser(Logger.tf.getText());
		
	}
}
