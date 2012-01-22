package runtime.functions;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Window;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;

import runtime.main.Profile;
import runtime.main.SettingsManager;
import runtime.main.index;
import runtime.util.Logger;
import runtime.util.ReadConfig;

import java.awt.Rectangle;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class ServerCheck extends JFrame {
	private JTextArea textField;

	public ServerCheck() {

	}
/*
 * 
 */

	public void start(){
		setBounds(new Rectangle(0, 0, 450, 480));
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);


		JProgressBar progressBar = new JProgressBar();
		progressBar .setBounds(273, 417, 153, 19);
		getContentPane().add(progressBar);

		JLabel lblCheckingServerStatus = new JLabel("Checking Server Status:");
		lblCheckingServerStatus.setBounds(74, 417, 143, 19);
		getContentPane().add(lblCheckingServerStatus);

		JLabel label = new JLabel("0/0");
		label.setBounds(230, 417, 46, 19);
		getContentPane().add(label);

		textField = new JTextArea();
		textField.setBounds(10, 70, 416, 326);
		getContentPane().add(textField);
		textField.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 11, 416, 48);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblCheckServerStatus = new JLabel("<html><font size=5><b>Server Status</b>");
		lblCheckServerStatus.setBounds(10, 15, 121, 22);
		panel.add(lblCheckServerStatus);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 404, 416, 2);
		getContentPane().add(separator);
		setVisible(true);


		SettingsManager sites = index.getSettingsManager();

		progressBar.setMaximum(sites.getSites().size());
		label.setText("0/"+sites.getSites().size());

		new Ping(label,progressBar,textField).start();
		this.setLocationRelativeTo(null);
		setResizable(false);

	}

	class Ping extends Thread{
		private JLabel l;
		private JTextArea t;
		private JProgressBar p;

		public Ping(JLabel l, JProgressBar p, JTextArea t){
			this.l = l;
			this.p = p;
			this.t = t;
		}
		public void run(){
			SettingsManager set = index.getSettingsManager();
			ArrayList<String> list = set.getSites();
			for(int a = 0; a<list.size();a++){
				long time = new Date().getTime();
				t.append(list.get(a));

				boolean b = false;

				String y [] = new ReadConfig().parseConfig(list.get(a));
				if(y!=null)
					b = true;

				if(b){
					t.append(" - Connection Sucessfull\n");
					Logger.log("Connection to ["+ list.get(a)+"] Made.. "+( new Date().getTime()- time)+"ms");
				}
					
					
				else{
					t.append(" - Failed to reach site\n");
					Logger.log("Connection to ["+ list.get(a)+"] Failed.. "+( new Date().getTime()- time)+"ms");
				}

				l.setText(a+1+"/"+list.size());
				p.setValue(a+1);
				
				
			}
		}
	}


}
