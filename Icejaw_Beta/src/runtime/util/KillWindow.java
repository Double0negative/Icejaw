package runtime.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import runtime.main.SettingsManager;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JProgressBar;

public class KillWindow extends JDialog {
	private JButton btnAdvancedVeiw;
	private JTextField txtThisIsWhere = new JTextField();
	private JTextArea textArea = new JTextArea();


	public KillWindow(){
		setResizable(false);
		txtThisIsWhere.setText("An Unspecified Error Occured while running Icejaw");
		Kill();
		btnAdvancedVeiw.setEnabled(false);
		


	}
	
	public KillWindow(String text){
		txtThisIsWhere.setText(text);
		Kill();
		btnAdvancedVeiw.setEnabled(false);

	}
	public KillWindow(String text,Exception e){
		txtThisIsWhere.setText(text);
		StackTraceElement trace [] = e.getStackTrace();
		textArea.append(e.toString()+"\n");
		for(int a = 0; a<trace.length;a++){
			textArea.append("\t"+trace[a]+"\n");
		}
		Kill();
	}

	
	
	
	public void Kill() {

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Icejaw: Error");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 685, 135);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 649, 91);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txtThisIsWhere.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtThisIsWhere.setEditable(false);
		txtThisIsWhere.setOpaque(false);
		txtThisIsWhere.setFocusable(false);
		txtThisIsWhere.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.LIGHT_GRAY));
		txtThisIsWhere.setBounds(10, 11, 618, 26);
		panel.add(txtThisIsWhere);
		txtThisIsWhere.setColumns(10);
		
		JButton btnCloseProgram = new JButton("Close Program - Reset Settings");
		btnCloseProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SettingsManager().removeSettings();
				System.exit(1);
			}
		});
		btnCloseProgram.setActionCommand("NO_SAVE");
		btnCloseProgram.setFocusPainted(false);
		btnCloseProgram.setToolTipText("Closes icejaw and resets all settings (if possible). usefull if your settings have become corrupt and is preventing icejaw from loading");
		btnCloseProgram.setBounds(410, 49, 216, 35);
		panel.add(btnCloseProgram);
		
		JButton btnCloseSaveSettings = new JButton("Close");
		btnCloseSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnCloseSaveSettings.setActionCommand("SAVE");
		btnCloseSaveSettings.setFocusPainted(false);
		btnCloseSaveSettings.setToolTipText("Closes icejaw and attempts to save the settings");
		btnCloseSaveSettings.setBounds(298, 48, 102, 35);
		panel.add(btnCloseSaveSettings);
		
		JButton btnResumeProgram = new JButton("Resume");
		btnResumeProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		btnResumeProgram.setActionCommand("RESUME");
		btnResumeProgram.setFocusPainted(false);
		btnResumeProgram.setToolTipText("Attempts to resume the program");
		btnResumeProgram.setBounds(199, 48, 89, 36);
		panel.add(btnResumeProgram);
		
		btnAdvancedVeiw = new JButton("Advanced View");
		btnAdvancedVeiw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSize( 685, 576);
				btnAdvancedVeiw.setEnabled(false);
			}
		});
		btnAdvancedVeiw.setActionCommand("VIEW_ADVANCED_TOGGLE");
		btnAdvancedVeiw.setFocusPainted(false);
		btnAdvancedVeiw.setToolTipText("Shows more advanced information about the error");
		btnAdvancedVeiw.setBounds(64, 48, 125, 35);
		panel.add(btnAdvancedVeiw);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 113, 649, 389);
		getContentPane().add(scrollPane);
		textArea.setAutoscrolls(false);
		
		textArea.setEditable(false);
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setViewportView(textArea);
		JPanel panel1 = new JPanel();
		panel1.setBorder(null);
		panel1.setBounds(10, 513, 649, 25);
		getContentPane().add(panel1);
		panel1.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(468, 0, 171, 25);
		panel1.add(progressBar);
		
		long used = Runtime.getRuntime().totalMemory();
		double left = (used*100) / (Runtime.getRuntime().maxMemory());

		progressBar.setValue((int) left);
		
		JLabel lblMemoryUsage = new JLabel("Memory Usage:");
		lblMemoryUsage.setBounds(298, 0, 111, 25);
		panel1.add(lblMemoryUsage);
		
		JLabel label = new JLabel((int)left+"%");
		label.setBounds(399, 0, 59, 25);
		panel1.add(label);
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	public void close(){
		this.setVisible(false);
		this.dispose();
	}
}

