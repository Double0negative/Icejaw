package runtime.functions;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import runtime.main.index;
import runtime.net.NetEngine;

public class Updater extends JFrame {
	JTextArea textArea = null;

	public Updater() {
		 final JFrame f = this;
		 setTitle("Icejaw: Updater");
		 setResizable(false);
		setBounds(new Rectangle(0, 0, 450, 350));
		getContentPane().setLayout(null);
		
		JButton btnCheck = new JButton("Check ");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new chk4Update().start();
			}
		});
		btnCheck.setBounds(10, 279, 102, 31);
		getContentPane().add(btnCheck);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});
		btnClose.setBounds(122, 279, 102, 31);
		getContentPane().add(btnClose);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 73, 422, 195);
		getContentPane().add(textArea);
		textArea.setText("Current Version: " + index.version);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 11, 422, 45);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCheckForUpdates = new JLabel("<html><font size=5>Check for updates");
		lblCheckForUpdates.setBounds(10, 11, 282, 22);
		panel.add(lblCheckForUpdates);
		
		JButton btnNewButton = new JButton("Download Update");
		btnNewButton.setBounds(234, 279, 117, 31);
		getContentPane().add(btnNewButton);
		btnNewButton.setEnabled(false);
		this.setLocationRelativeTo(null);

	}
	
	public void start(){
		setVisible(true);
	}
	
	class chk4Update extends Thread{
		public void run(){
			textArea.setText("Checking... Please Wait");
			double ver = 0.00;
			String response = new NetEngine().fetch("http://rpfileserv.wdfiles.com/local--files/icejaw/update_server.txt");
		    ver = Double.parseDouble(response.substring(0,5));
		    if(ver>index.version){
				String updateMessage = new NetEngine().fetch("http://rpfileserv.wdfiles.com/local--files/icejaw/update_text.txt");
				textArea.setText(updateMessage);
		    }
		    else{
		    	textArea.setText("No Updates Avaliable");
		    }
		
		
		
		}
	}
}
