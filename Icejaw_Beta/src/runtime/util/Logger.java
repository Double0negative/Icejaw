package runtime.util;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Logger {

	private static JDialog f = new JDialog(new JFrame(),"Icejaw: Logger");
	private static JPanel p = new JPanel();
	private static JTextArea ta = new JTextArea();
	public static JTextField tf = new JTextField();
	public Logger(){
		f.setAlwaysOnTop(true);
		f.setSize(400,430);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN,10));
		f.add(p);
		p.setLayout(null);
		JScrollPane jp =  new JScrollPane(ta);
		p.add(jp);
		p.add(tf);
		jp.setBounds(10,10,375,350);
		tf.setBounds(10, 362, 375, 25);
		tf.addActionListener(new LoggerCMD());
	}
	
	
	public static void showLogger(){
		f.setVisible(true);
	}
	
	public static void hideLogger(){
		f.setVisible(false);
	}
	
	public static void log(String text){
		ta.append("[INFO] "+ text+"\n");
		
	}
	public static void warn(String text){
		ta.append("[WARNING] "+ text+"\n");
		
	}
	public static void error(String text){
		ta.append("[ERROR] "+ text+"\n");
		
	}
	public static void cmd(String text){
		ta.append("[CMD] "+ text+"\n");
		
	}
}
