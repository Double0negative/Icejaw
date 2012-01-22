package runtime.functions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import runtime.main.SettingsManager;
import runtime.main.index;
import runtime.util.ConvertLAF;
import runtime.util.Logger;
import runtime.util.alert;

public class Settings{


	JFrame 			f 			= new JFrame("Settings");
	JPanel 			p 			= new JPanel();
	SettingsManager set 		= index.getSettingsManager();

	String [] temp = {"Java Metal", "OS Specific","Classic", "Motif"};
	JComboBox LAF  = new JComboBox(temp);
	String [] temp1 = new String [] {"View 1","View 2"};

	JComboBox theme = new JComboBox(temp1);
	JTextArea 		serverList 	= new JTextArea();
	boolean loaded = false;

	
	

	public Settings() {
		try {
			set.loadSettings();
			set.loadSites();
		} catch (IOException e) {
		}
		

		f.setResizable(false);
		
		f.setSize(415,499);
		f.getContentPane().add(p);
		p.setLayout(null);
		
		JLabel 			serverListT = new JLabel("Server List: ");

		JLabel 			LAFT 		= new JLabel("Look & feel:");

		JLabel 			themeT 		= new JLabel("Theme:");

		
		
		LAF.setSelectedItem(set.getLaf());

		theme.setSelectedItem(set.getLayout());

		
		
		
		JScrollPane jp = new JScrollPane(serverList);
		JPanel r = new JPanel();
		r.setBorder(BorderFactory.createTitledBorder( "Apperance"));
		p.add(r);
		r.setBounds(10,5,389,104);
		r.setLayout(null);
		LAFT.setBounds(10,24,100,20);
		LAF.setBounds(100,22,150,25);
		r.add(LAF);
		r.add(LAFT);
		themeT.setBounds(10,58,100,20);
		theme.setBounds(100,56,150,25);
		r.add(theme);
		r.add(themeT);

		JPanel y = new JPanel();
		y.setLayout(null);
		y.setBorder(BorderFactory.createTitledBorder( "Servers"));
		y.setBounds(10,120,389,271);
		//serverList.setBounds(0,0,370,255);
		jp.setBounds(10,20,360,235);
		y.add(jp);
		p.add(y);
		
		JButton btnSave = new JButton("Save ");
		btnSave.setToolTipText("Save the current settings");
		btnSave.setBounds(57, 413, 89, 37);
		btnSave.addActionListener(new Save());

		p.add(btnSave);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int a = (5 / (9-9));
				}
				catch(Exception  e){
				index.kill("Test Error",e);
				}
			}
		});
		btnReset.setToolTipText("Reset to the defualt settings. Also clears all servers");
		btnReset.setBounds(156, 413, 89, 37);
		p.add(btnReset);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Cancel Changes");
		btnCancel.setBounds(255, 413, 89, 37);
		p.add(btnCancel);
		
		f.setLocationRelativeTo(null);
		
	}
	public void start(){
		Logger.log("Loading Settings CP");
		Logger.log("Current LAF: "+ set.getLaf());
		Logger.log("Current Layout: "+ set.getLayout());
		LAF.setSelectedItem(ConvertLAF.convert(set.getLaf()));
		serverList.setText("");
		Object t[] = set.getSites().toArray();
		System.out.println("getSites: "+ set.getSites().size()+" t: "+ t.length);
		for(int a = 0;a<t.length; a++){
			if(a!=0 && !loaded){
				serverList.append("\n");
			}
			serverList.append(t[a]+"");
			
		}
		loaded = true;
		f.setVisible(true);

	}

	public class Save implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Logger.log("Saving Settings");

			set.setLaf(ConvertLAF.convert(LAF.getSelectedItem()+""));
			set.setLayout(theme.getSelectedIndex());
			set.clearSites();
			Logger.log("Saving LAF: "+set.getLaf());
			Logger.log("Saving Layout: "+set.getLayout());
			Logger.log("Saving Sites");
			String line = "";
			for(int a = 0; a<serverList.getLineCount();a++){
				line = getLine(a);
				line = line.replace("\n", "");
				if(line==null)
					break;
				set.addSite(line);
				Logger.log("     "+line);
			}
			try{
				set.save();
				set.saveSites();
				alert.message(null,"Saved","Settings saved!\nIcejaw Restart my be required for some settings to work right\n(Theme and server settings need a restart, LAF does not)");
			}
			catch(Exception r){
				index.kill("Failed To Save Settings", r);
			}
		
			try{
				Logger.log("Reloading LAF");
				System.out.println(set.getLaf());
				UIManager.setLookAndFeel(set.getLaf());
				index.refreshTree();
				SwingUtilities.updateComponentTreeUI(f);

				}catch(Exception u){
					index.kill("Failed to load Look and Feel - Non Fatal",u);
				}
			f.setVisible(false);

		}
		}
	
		
	
	public String getLine(int a){
		try{
			int startIndex = serverList.getLineStartOffset(a);
			int endIndex = serverList.getLineEndOffset(a);
			String line = serverList.getText().substring(startIndex, endIndex);
			System.out.println(line+" - END");
			return line;
		}catch(Exception e){
			System.out.println("Fail");
			e.printStackTrace();
			return null;
		}
	}
	
	public void close(){
		
		
	}
}
