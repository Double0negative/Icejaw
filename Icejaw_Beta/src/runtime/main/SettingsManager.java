package runtime.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.UIManager;

public class SettingsManager {

	int x, y, h, w;
	String laf;

	Properties prop = new Properties();

	ArrayList<String> sites = new ArrayList<String>();
	int layout;

	public void loadSettings() throws IOException {

		/*
		 * Read Properties
		 */
		File f = new File(index.appDir);

		f.mkdir();

		f = new File(index.appPropLoc);
		if (!f.exists()) {

			f.createNewFile();

		}

		prop.load(new FileInputStream(index.appPropLoc));

		BufferedWriter out = null;

		out = new BufferedWriter(new FileWriter(index.appPropLoc));
		out.write("");

		String settingsHaveBeenCreated = prop.getProperty("settings", "false");
		if (!settingsHaveBeenCreated.equals("true")) {
			prop.setProperty("settings", "true");
			prop.setProperty("laf", UIManager.getSystemLookAndFeelClassName());
			prop.setProperty("x", "0");
			prop.setProperty("layout", "0");
			prop.setProperty("y", "0");
			prop.setProperty("w", "500");
			prop.setProperty("h", "400");

		}

		x = Integer.parseInt(prop.getProperty("x", "0"));
		y = Integer.parseInt(prop.getProperty("y", "0"));
		layout = Integer.parseInt(prop.getProperty("layout", "0"));
		h = Integer.parseInt(prop.getProperty("h", "500"));
		w = Integer.parseInt(prop.getProperty("w", "400"));
		laf = prop.getProperty("laf", "0");

	}
	
	public void removeSettings(){
		File f = new File(index.appPropLoc);
		f.delete();
		f = new File(index.siteList);
		f.delete();

	}
	
	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public void loadSites() throws IOException {
		File f = new File(index.appDir);
		f.mkdir();

		f = new File(index.siteList);
		if (!f.exists()) {

			f.createNewFile();

		}
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(index.siteList));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
		sites.clear();
		while (inFile.hasNext()) {
			sites.add(inFile.nextLine());
		}

	}

	public ArrayList<String> getSites() {
		return sites;
	}

	public void addSite(String site) {
		sites.add(site);
	}
	public void clearSites() {
		sites = null;
		sites = new ArrayList<String>();
	}
	public void save() throws IOException {

		FileOutputStream out = new FileOutputStream(index.appPropLoc);
		prop.store(out, "---Settings file---");
		prop.setProperty("settings", "true");
		prop.setProperty("laf", laf);
		prop.setProperty("layout", layout+"");
		out.close();
	}

	public void saveSites() {
		BufferedWriter serv = null;
		try {
			File settings = new File(index.siteList);
			serv = new BufferedWriter(new FileWriter(settings));
		} catch (Exception e) {
			index.kill();
		}
		System.out.println(sites.size());
		for (int a = 0; a < sites.size(); a++) {
			try {
				serv.write(sites.get(a).trim()+"\n");
			} catch (Exception e) {
				index.kill("Failed to write to file",e);
			}
			
		}
		try {
			serv.flush();serv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			index.kill("Unknown error");
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		prop.setProperty("x", "" + x);
		try {
			loadSettings();
		} catch (IOException e) {
			index.kill("Failed to set/get X Location from Settings",e);
		}

	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		prop.setProperty("y", "" + y);
		try {
			loadSettings();
		} catch (IOException e) {
			index.kill("Failed to set/get Y Loaction from Settings",e);
		}

	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		prop.setProperty("h", "" + h);
		try {
			loadSettings();
		} catch (IOException e) {
			index.kill("Failed to set/get Height from Settings",e);
		}

	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		prop.setProperty("w", "" + w);
		try {
			loadSettings();
		} catch (IOException e) {
			index.kill("Failed to set/get Width from Settings",e);
		}

	}

	public String getLaf() {
		return laf;
	}

	public void setLaf(String laf) {
		prop.setProperty("laf", laf);
		this.laf = laf;

	}


}
