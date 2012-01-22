package runtime.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import runtime.functions.*;
import runtime.net.NetEngine;
import runtime.util.KillWindow;
import runtime.util.Logger;
import runtime.util.alert;

public class index {

	/*
	 * 
	 * Version Info
	 */

	public static String Sversion = "Beta 1 PRE 10";
	public static double version = 0.1110;
	public static String release = "No Release";

	/*
	 * 
	 * Window Objects
	 */
	static JFrame f = new JFrame("Icejaw " + Sversion);
	JPanel wrap = new JPanel();
	JPanel wrapTop = new JPanel();
	JPanel mainContent = new JPanel();
	JPanel wrapBottom = new JPanel();
	JPanel statusBar = new JPanel();
	private static JTabbedPane tp = new JTabbedPane();

	/* window components */

	JComboBox serverSelect = null;
	static JProgressBar loadbar = new JProgressBar();
	static JLabel loadText = new JLabel();
	JSeparator sepBottom = new JSeparator();
	JEditorPane inputT = new JEditorPane();
	JScrollPane input = new JScrollPane(inputT);
	JComponent submit = null;
	JComponent more = null;
	JComponent B = null;
	JComponent I = null;
	JComponent U = null;

	/*
	 * Menu Bar
	 */

	JMenuBar menu = new JMenuBar();

	JMenu menuFile = new JMenu("    File    ");
	JMenuItem fileSettings = new JMenuItem("Settings       ");
	JMenuItem fileExportConvo = new JMenuItem("Export Conversation");
	JMenuItem fileExportServer = new JMenuItem("Export Server List");
	JMenuItem fileExit = new JMenuItem("Exit");
	JMenu menuServer = new JMenu("    Server    ");
	JMenuItem serverCheck = new JMenuItem("Check Server Status    ");
	JMenuItem serverDC = new JMenuItem("Disconnect");
	JMenu menuAbout = new JMenu("    About   ");
	JMenuItem aboutAbout = new JMenuItem("About icejaw     ");
	JMenuItem aboutHelp = new JMenuItem("Help with icejaw");
	JMenuItem aboutUpdate = new JMenuItem("Check For Updates");

	{
		Logger.log("Setting up Menu");
		try{
			settings.loadSettings();
			settings.loadSites();
		}catch(Exception e){
			kill("Failed to load settings");
		}
		menu.add(menuFile);
		menu.add(menuServer);
		menu.add(menuAbout);

		menuFile.add(fileSettings);
		menuFile.add(new JSeparator());
		menuFile.add(fileExportConvo);
		fileExportConvo.addActionListener(new saveConvo());

		menuFile.add(fileExportServer);
		fileExportServer.addActionListener(new exportServ());
		menuFile.add(new JSeparator());
		menuFile.add(fileExit);

		menuServer.add(serverCheck);
		serverCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ServerCheck().start();
			}
		});
		menuServer.add(serverDC);

		menuAbout.add(aboutAbout);
		aboutAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About().start();
			}
		});
		menuAbout.add(aboutHelp);
		aboutHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Help().start();
			}
		});
		menuAbout.add(new JSeparator());
		menuAbout.add(aboutUpdate);
		aboutUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Updater().start();
			}
		});

	}

	/*
	 * 
	 * Settings data
	 */

	static String appDataLoc = System.getenv("APPDATA");
	static String appDir = appDataLoc + "\\.icejaw";
	public static String appPropLoc = appDir + "\\settings.ini";
	public static String siteList = appDir + "\\server.list";

	static SettingsManager settings = new SettingsManager();

	/*
	 * 
	 * Data
	 */

	private static ArrayList<Profile> openSites = new ArrayList<Profile>();
	private static ArrayList<siteData> siteData = new ArrayList<siteData>();
	public static boolean networkUpdate = false;
	public static boolean killNetHelper = false;
	boolean background = true;
	NetEngine netEngine = new NetEngine();
	layoutEngine LayoutEngine = new layoutEngine();
	Settings settingsWindow= new Settings();
	Logger log = new Logger();
	public void main() {
		boolean failed = load();
		if (failed)
			kill("Failed to load program");

		f.setVisible(true);

	}

	public boolean load() {
		Logger.log("Loading...");
		boolean failed = false;

		f.setSize(settings.getW(), settings.getH());
		f.setLocation(settings.getX(), settings.getY());
		f.addWindowListener(new WindowListen());
		Object[] serverListArray = settings.getSites().toArray();
		serverSelect = new JComboBox();
		serverSelect.addItem("Welcome");
		for(int c= 0; c<serverListArray.length;c++)
			serverSelect.addItem(serverListArray[c]);
		loadLayout(settings.getLayout());
		addSite(new Profile("http://rpfileserv.wikidot.com/local--files/icejaw/icejaw_welcome_A9.html", true, "", "", false, false, "Welcome"));
		updateOutput(0);
		f.repaint();
		inputT.addKeyListener(new submit());
		/*
		 * Create Listeners
		 */

		f.addComponentListener(new FListen());
		fileSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsWindow.start();
			}
		});
		serverSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().loginIntoSite(new Profile(serverSelect.getSelectedItem()+"",false, null, null, true, true, null));
			}
		});
		
		inputT.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					netEngine.send(openSites.get(tp.getSelectedIndex()), inputT.getText());
					inputT.setText("");
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});
		serverSelect.setEditable(true);

		new netHelper().start();
		new backgroundTask().start();
		LayoutEngine.start();
		networkUpdate = true;

		startup.closeStartWindow();
		loadbar.setStringPainted(true);
		System.out.println(settings.getLaf());

		try{
			UIManager.setLookAndFeel(settings.getLaf());
			refreshTree();

		}catch(Exception e){
			kill("Failed to load Look and Feel - Non Fatal",e);
		}
		return failed;

	}

	class netHelper extends Thread {

		public netHelper() {
			Logger.log("Network Update Thread started successfully");

		}

		public void run() {
			// System.out.println("Thread method started");
			while (!killNetHelper) {

				while (!networkUpdate) {
					try {
						sleep(500);
					} catch (InterruptedException e) {
					}
				}
				for (int a = 0; a < openSites.size(); a++) {
					if (openSites.get(a).isActive()) {
						setLoading("update: " + openSites.get(a).getTitle(), 0);
						LayoutEngine.forceUpdate();
						updateOutput(a);
						stopLoading();
						LayoutEngine.forceUpdate();
					}
				}
				try {
					sleep(5000);
				} catch (InterruptedException e) {
				}

			}
			Logger.warn("Network Update Thread has EXITED");

		}
	}

	public void updateOutput(int x) {


		if(openSites.get(x).getabs())
			siteData.get(x).setText(netEngine.fetch(openSites.get(x).getUrl()));
		else
			siteData.get(x).setText(netEngine.fetch(openSites.get(x).getUrl()+"/icejaw/feed.php"));



	}

	public static void addSite(Profile prof) {
		Logger.log("Adding Site: "+ prof.getTitle() + "["+prof.getUrl()+"]");
		openSites.add(prof);
		siteData.add(new siteData(new JPanel(), new JEditorPane()));
		refreshSiteList();
		
	}

	public static void removeSite(int index) {
		Logger.log("Removing Site: "+ openSites.get(index).getTitle() + "["+openSites.get(index).getUrl()+"]");
		openSites.remove(index);
		siteData.remove(index);
		tp.remove(index);

	}

	private static void refreshSiteList() {

		tp.removeAll();
		for (int a = 0; a < openSites.size(); a++) {
			tp.addTab(openSites.get(a).getTitle(), null, siteData.get(a)
					.getPanel(), openSites.get(a).getTitle());

		}
		tp.setSelectedIndex(openSites.size()-1);
		
	}

	public static void kill() {
		new KillWindow();

	}

	public static void kill(String str) {
		new KillWindow(str);

	}

	public static void kill(String str, Exception e) {
		new KillWindow(str,e);
	}


	public void exit() {
		Logger.log("Shutting down. Saving Settings");
		try {
			settings.setH(f.getHeight());
			settings.setW(f.getWidth());
			settings.setX(f.getX());
			settings.setY(f.getY());
			settings.save();
			Logger.log("Shutdown Complete");
			System.exit(0);
		}

		catch (Exception e) {
			kill("Failed to shut down. (Possible file Lock?)",e);
		}
	}

	class WindowListen implements WindowListener {

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowClosed(WindowEvent arg0) {
		}

		public void windowClosing(WindowEvent arg0) {
			exit();
		}

		public void windowDeactivated(WindowEvent arg0) {
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {
		}

		public void windowOpened(WindowEvent arg0) {
		}

	}

	int LENUM = 0;
	int sleep = 1000;

	class layoutEngine extends Thread {
		int h, w;
		int thisThread = LENUM;

		public layoutEngine() {
			Logger.log("Layout Engine Started");
			this.setName("LayoutEngine-" + LENUM + " (NO PARAM)");
			LENUM++;
		}
		public void run() {
			while (true) {
				updateLayout();
				try {sleep(sleep);}catch (Throwable e) {}
			}
		}
		public void updateLayout(){
			if(reloadLayout){
				reloadLayout = false;
				loadLayout(settings.getLayout());
			}
			if (settings.getLayout() == 0) {

				mainContent.setBounds(0, 0, f.getWidth() - 5,
						f.getHeight() - 150);
				wrapBottom.setBounds(0, f.getHeight() - 150, f.getWidth(),
						100);
				wrapTop.setBounds(f.getWidth() - 235, 0, 235, 60);

				serverSelect.setBounds(f.getWidth() - 250, 10, 250, 20);
				tp.setBounds(0, 0, mainContent.getWidth() - 5,
						mainContent.getHeight());
				input.setBounds(10, 10, f.getWidth() - 350, 40);
				inputT.setSize(input.getWidth() - 25, input.getHeight());

				submit.setBounds(f.getWidth() - 330, 3, 80, 25);
				more.setBounds(f.getWidth() - 330, 33, 80, 25);
				statusBar.setBounds(0, wrapBottom.getHeight() - 38,
						wrapBottom.getWidth(), 30);
				sepBottom.setBounds(10, 2, wrapBottom.getWidth() - 30, 2);
				loadbar.setBounds(10, 10, wrapBottom.getWidth() - 30, 15);
				serverSelect.setBounds(0, 3, 215, 25);
				B.setBounds(0, 33, 50, 25);
				I.setBounds(55, 33, 50, 25);
				U.setBounds(110, 33, 50, 25);
			}
			if (settings.getLayout() == 1) {
				wrapTop.setBounds(0, 0, f.getWidth(), 50);
				mainContent.setBounds(0, 50, f.getWidth(),
						f.getHeight() - 200);
				wrapBottom.setBounds(0, f.getHeight() - 150, f.getWidth(),
						100);
				serverSelect.setBounds(f.getWidth() - 300, 10, 250, 40);
				tp.setBounds(0, 0, mainContent.getWidth() - 5,
						mainContent.getHeight());
				input.setBounds(10, 10, f.getWidth() - 200, 40);
				submit.setBounds(f.getWidth() - 185, 15, 80, 40);
				more.setBounds(f.getWidth() - 100, 15, 120, 40);
				statusBar.setBounds(0, wrapBottom.getHeight() - 39,
						wrapBottom.getWidth(), 35);
				sepBottom.setBounds(10, 1, wrapBottom.getWidth() - 25, 2);
				loadbar.setBounds(10, 10, wrapBottom.getWidth() - 20, 15);
				// loadText.setBounds(170,10,300,15);
			}

			if (f.getWidth() < 570) {
				f.setSize(570, f.getHeight());
			}
			if (f.getHeight() < 300) {
				f.setSize(f.getWidth(), 300);
			}
			f.repaint();
		}


		public void forceUpdate() {
			updateLayout();
		}
	}



	class backgroundTask extends Thread {

		public void run() {

			while (background) {
				try {
					sleep(1000);
				} catch (Throwable e) {
				}
			}
		}
	}

	static boolean Override = false;
	private static boolean reloadLayout;

	public static void setLoading(String text, int j) {

		if (j == 0 && !Override) {
			loadbar.setIndeterminate(true);
			if (!text.equals("")) {
				loadbar.setString(text);
				loadbar.setStringPainted(true);
			} else {
				loadbar.setString("");
				loadbar.setStringPainted(false);
			}
		}

		if (j == 1 && !Override) {
			loadbar.setIndeterminate(false);
			if (text == null) {
				loadbar.setStringPainted(false);
			} else {
				loadbar.setStringPainted(true);
			}
			loadbar.setValue(0);

		}
	}

	public static void stopLoading() {
		if (!Override) {
			loadbar.setIndeterminate(false);
			loadbar.setValue(0);
			loadbar.setStringPainted(true);
			loadbar.setString("Idle");
		}
	}

	public static void setOverride() {
		Override = false;
	}

	public static void removeOverride() {
		Override = false;
	}

	/*
	 * 
	 * LISTENERS
	 */

	class FListen implements ComponentListener {

		public void componentHidden(ComponentEvent arg0) {
			LayoutEngine.forceUpdate();

		}

		public void componentMoved(ComponentEvent arg0) {
			LayoutEngine.forceUpdate();
		}

		public void componentResized(ComponentEvent arg0) {
			LayoutEngine.forceUpdate();

		}

		public void componentShown(ComponentEvent arg0) {
			LayoutEngine.forceUpdate();

		}

	}

	public void loadLayout(int layout) {
		f.add(menu);
		f.setJMenuBar(menu);

		// wrap.setLayout(new GridBagLayout());
		wrap.setLayout(null);
		wrapTop.setLayout(null);
		wrapBottom.setLayout(null);

		if (layout == 0) {

			wrap.setLayout(null);
			wrap.add(mainContent);
			wrap.add(wrapBottom);
			wrapBottom.add(wrapTop);

			/*
			 * mainContent.setBackground(Color.BLACK);
			 * wrapBottom.setBackground(Color.red);
			 * wrapTop.setBackground(Color.blue);
			 */

			f.add(wrap);

			mainContent.setLayout(null);
			mainContent.add(tp);

			wrapTop.setLayout(null);

			submit = new JButton("Submit");
			more = new JButton("more ->");

			B = new JButton("B");
			I = new JButton("I");
			U = new JButton("U");

			wrapTop.add(B);
			wrapTop.add(I);
			wrapTop.add(U);
			wrapTop.add(serverSelect);

			wrapBottom.add(input);
			wrapBottom.add(submit);
			wrapBottom.add(more);

			wrapBottom.add(statusBar);
			statusBar.add(loadbar);
			// statusBar.add(loadText);
			statusBar.add(sepBottom);

		}

		if (layout == 1) {

			/*
			 * 
			 * Set Layout & add menu
			 */

			/*
			 * WRAPPERS
			 */

			wrap.setLayout(null);
			wrap.add(wrapTop);
			wrap.add(mainContent);
			wrap.add(wrapBottom);

			f.add(wrap);

			/*
			 * MAIN CONTENT
			 */

			mainContent.setLayout(null);
			mainContent.add(tp);

			/*
			 * Top
			 */
			wrapTop.setLayout(null);

			submit = new runtime.util.CButton("Submit");
			more = new runtime.util.CButton("more ->");
			B = new runtime.util.CButton("Bold");
			I = new runtime.util.CButton("Italic");
			U = new runtime.util.CButton("Underline");
			B.setBounds(10, 10, 60, 25);
			I.setBounds(80, 10, 150, 25);
			U.setBounds(150, 10, 340, 25);
			// serverSelect.setBounds(f.getWidth()-300,10,300,20);

			wrapTop.add(B);
			wrapTop.add(I);
			wrapTop.add(U);
			wrapTop.add(serverSelect);

			/*
			 * Bottom
			 */
			wrapBottom.add(input);
			wrapBottom.add(submit);
			wrapBottom.add(more);

			wrapBottom.add(statusBar);
			statusBar.add(loadbar);
			// statusBar.add(loadText);
			statusBar.add(sepBottom);

		}

	}
	public static SettingsManager getSettingsManager(){
		return settings;
	}
	public static void refreshTree(){
		try{

			SwingUtilities.updateComponentTreeUI(index.f);
			f.setVisible(false);
			System.out.println("Refresing");
			f.validate();
			f.setVisible(true);
		}catch(Exception e){
			kill("Failed to load Look and Feel - Non Fatal",e);
		}

	}

	public static ArrayList<Profile> getSiteArray(){
		return openSites;
	}

	class exportServ implements ActionListener{ 
		public void actionPerformed (ActionEvent e){

			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(f);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// File dir  = fc.getCurrentDirectory();
				String fileName = file.getPath();
				// String dirName = dir.getName();
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(fileName+".txt"));

					for(int c= 0; c<settings.getSites().size();c++)
						out.write(settings.getSites().get(c)+"\n");
					out.close();
					System.out.println("File Write Success: " /*dirName*/ +fileName );
					alert.message(f,"Complete", "File saved!\nNote: Notepad tends to lose the formating!\nit is recomended that you open it with notepad++ or wordpad!");
				} catch (IOException w) {
					alert.error(f, "Failed to write to file!");

				} 
			}
		}
	}
	class saveConvo implements ActionListener{ 
		public void actionPerformed (ActionEvent e){

			JFileChooser fc = new JFileChooser();

			int returnVal = fc.showSaveDialog(f);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String fileName = file.getPath();

				String temp = siteData.get(tp.getSelectedIndex()).getText();
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(fileName+".html"));
					out.write(temp);
					out.close();
					System.out.println("File Write Success: " /*dirName*/ +fileName );
					alert.message(f,"Complete", "File saved!");
				} catch (IOException w) {
					kill("Failed to write to file!",w);

				}
			} 
		}
	}

	class submit implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){

			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
