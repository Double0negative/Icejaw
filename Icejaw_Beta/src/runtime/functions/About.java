package runtime.functions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import runtime.main.index;
import runtime.util.Logger;

public class About extends JFrame {
	JFrame f = this;
	FadePanel fp = new FadePanel();

	String [] aboutText = {"                  Icejaw",
			"-------------------------------------",
			"\t\t\tAbout:",
			"Version: "+ index.Sversion,
			"Version No:" + index.version,
			"Release Data: "+ index.release,
			"",
			"",
			"",
			"\t\t\tCredits: ",
			
			
			
			
			"Lead Dev: Double0negative",
			"Developers: Anon232", 
			"",};
	public About() {
		Logger.log("Showing About");
		this.setSize(400,495);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);


	}
	public void start(){
		fp.setBounds(0,0,400,500);

		this.add(fp);
		setVisible(true);
		new repaint().start();
	}

	class FadePanel extends JPanel{
		int index = 430;
		BufferedImage top = null;
		BufferedImage bottom = null;

		public FadePanel(){
			try {
			    top = ImageIO.read(getClass().getResource("/top.png"));
			    bottom = ImageIO.read(getClass().getResource("/bottom.png"));
			} catch (Exception e) {
				runtime.main.index.kill("Unknown Error",e);
				}
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D canvas = (Graphics2D)g;
			canvas.setColor(Color.BLACK);
			this.setBackground(Color.white);
			
			
			for(int a = 0; a<aboutText.length;a++){
				
				canvas.drawString(aboutText[a], 100, index+(a*25));
				
			}try{
				
			canvas.drawImage(top, 0, 0,null);
			canvas.drawImage(bottom, 0, 320,null);
			}catch(Exception e){
				runtime.main.index.kill("df",e);
			}
			canvas.setFont(new Font("Arial", Font.BOLD, 25));
			canvas.drawString("About ICEJAW", 100, 35);

			index--;
		}
	}
	
	class repaint extends Thread{
		public void run(){
			
			for(int a = 0; a<1500;a++){
				f.repaint();
				try {sleep(50);} catch (InterruptedException e) {}
			}
			
		}
	}
}
