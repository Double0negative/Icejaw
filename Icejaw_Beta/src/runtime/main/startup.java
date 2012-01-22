package runtime.main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import runtime.util.Logger;

public class startup {
	
	static JFrame net = new JFrame("Waiting for Network");
	JPanel p = new JPanel();
	JLabel l = new JLabel("Wating for Network");
	
	
	
	
    public static void main(String args[]){
    	new startup().setup();
    }
    
    
    
    
    public void setup(){
		Logger.showLogger();
		Logger.log("Icejaw Version "+ index.version+ " - "+ index.Sversion);
		Logger.log("Initializing");
    	try{
    		Logger.log("Checking Network Connection");
    		net.setSize(200,25);
    		net.setUndecorated(true);
    		net.setLocationRelativeTo(null);
    		
    		net.add(p);
    		p.setBackground(Color.black);
    		
    		p.add(l);
    		l.setForeground(Color.white);
    		net.setVisible(true);
    		
    		
    		
    		
    		Logger.log("Loading Main");
    	new index().main();
    	}
    	catch(Exception e){
    		Logger.error("Problem Loading Icejaw");
    		index.kill("there was a problem with icejaw",e);
    	}
    	
    	}
    
    
    public static void closeStartWindow(){
    	
    	net.dispose();
    	
    }
    
}
