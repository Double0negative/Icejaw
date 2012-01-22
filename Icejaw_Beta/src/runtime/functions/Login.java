package runtime.functions;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import runtime.main.Profile;
import runtime.main.index;
import runtime.util.Logger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JPanel panel_1;
	private JLabel lblUsername;
	private JLabel lblpassword;
	private JLabel lblupdateToVersion;
	private JDialog frame = null;
	String[] settings = null;

	/**
	 * Create the dialog.
	 */
	public Login() {
		frame = this;

	}
	Profile p = null;
	public void loginIntoSite(Profile p){
		Logger.log("Starting Login Process");

		this.p = p;
		setResizable(false);
		setAlwaysOnTop(true);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Login to: http://hackers-alliance.com/icejaw");
		setBounds(100, 100, 348, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 11, 317, 108);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);
			{
				lblUsername = new JLabel("<html><b>Username:");
				lblUsername.setBounds(10, 35, 61, 24);
				panel_1.add(lblUsername);
			}
			{
				textField = new JTextField();
				textField.setBounds(91, 34, 219, 27);
				panel_1.add(textField);
				textField.setColumns(10);
			}

			passwordField = new JPasswordField();
			passwordField.setBounds(91, 70, 219, 27);
			panel_1.add(passwordField);
			{
				lblpassword = new JLabel("<html><b>Password:");
				lblpassword.setBounds(10, 70, 61, 27);
				panel_1.add(lblpassword);
			}

			lblupdateToVersion = new JLabel("");
			lblupdateToVersion.setBounds(91, 15, 185, 14);
			panel_1.add(lblupdateToVersion);
		}

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(238, 130, 89, 30);
		contentPanel.add(btnLogin);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(149, 130, 89, 30);
		contentPanel.add(btnCancel);
		contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel_1, lblupdateToVersion, lblUsername, textField, passwordField, lblpassword, btnLogin, btnCancel}));
		
		btnLogin.addActionListener(new cLogin());
		passwordField.addActionListener(new cLogin());
		btnCancel.addActionListener(new Close());
		
		settings = new runtime.util.ReadConfig().parseConfig(p.getUrl());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
	class cLogin implements ActionListener{

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			Logger.log("Checking Login [" + p.getUrl()+"]");
			String response[] = new String[2];
			String data="";
			try{
				data = URLEncoder.encode("username", "UTF-8") +"=" + URLEncoder.encode(textField.getText(), "UTF-8");
				data += "&"+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(passwordField.getText(), "UTF-8");
				URL url = new URL(p.getUrl()+"/icejaw/login.php");
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();
				   
				// Get The Response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				int a =0;
				while ((line = rd.readLine()) != null) {
					response[a] = line;
					a++;
				}
			}catch(Exception e){
				index.kill("Error",e);
			}
			System.out.println(settings[1]);
			
			if(response[0].equalsIgnoreCase("true")){
				p.setLoggedin(true);
				p.setTitle(settings[1]);
				p.setUsername(textField.getText());
				p.setPass(response[1]);
				frame.dispose();
				index.addSite(p);
			}else{
				Logger.warn("Incorect Login");
				lblupdateToVersion.setText("Login Incorrect");
			}
		}
	}
	
	class Close implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
			
		}
		
	}
}
