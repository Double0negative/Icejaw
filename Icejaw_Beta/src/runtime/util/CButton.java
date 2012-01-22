package runtime.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/*
 * Custom Button JFrame Component
 * 
 * Code by Double_0_negative
 */

public class CButton extends JPanel{
    int paintMode = 1;
    private String text = "";
    private int tSize = 15;
    Color c1 = Color.LIGHT_GRAY;
    Color c2 = Color.GRAY;
    Color c3 = new  Color(220,220,220);
    Color textC =  Color.BLACK;
    public CButton(String t){
        text =t;
        setUp();
    }

    public void setUp(){
        this.addMouseListener(new Click());
    }
    JPanel p = null;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        p = this;
        Graphics2D canvas = (Graphics2D)g;
        canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        canvas.setColor(Color.BLACK);
        //this.setSize(500,500);
        Font font = new Font("times", Font.BOLD, tSize);

        FontMetrics metrics = g.getFontMetrics(font);
        int hgt = metrics.getHeight();
        int adv = metrics.stringWidth(text);

        this.setSize(adv +30, hgt +10);
        int x = this.getWidth();
        int h = this.getHeight();
        g.setFont(font);
        int xPos = (x / 2 - adv / 2);
        int hPos = (h/2 - hgt  / 2) + 15;
        if (xPos < 1)
            xPos = 1;

        GradientPaint gradient1 = null;
        if(paintMode == 1){ 
            gradient1 = new GradientPaint(0,0,c1,0,this.getHeight(),c2);
        }
        else if(paintMode == 2)
            gradient1 = new GradientPaint(0,0,c2,0,this.getHeight(),c3);
        else if(paintMode == 3)
            gradient1 = new GradientPaint(0,0, c3,0,this.getHeight(),c2);  

        canvas.setPaint(gradient1);
        canvas.fillRoundRect(0,0,this.getWidth(),this.getHeight(),5,5);
        canvas.setColor(textC);
        canvas.drawString(text, xPos, hPos);

    }

    public void setText(String t){
        text = t;
    }


    public String getText(){
        return text;
    }

    class Click implements MouseListener{

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            paintMode = 3;
            p.repaint();

        }

        public void mouseExited(MouseEvent e) {
            paintMode = 1;
            p.repaint();

        }

        public void mousePressed(MouseEvent e) {
            paintMode = 2;
            p.repaint();

        }

        public void mouseReleased(MouseEvent e) {
            paintMode = 3;      
            p.repaint();

        }

    }
    public void setMainColor(Color w){
        c1 = w; 
    }
    public void setSecondColor(Color w){
        c2 = w; 
    }
    public void setHighliteColor(Color w){
        c3 = w; 
    }
    public void setTextColor(Color w){
        textC = w;
}
}