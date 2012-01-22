package runtime.util;

import javax.swing.*;

public class alert{

public static void error(JFrame frame,String error){
JOptionPane.showMessageDialog(frame,
    error,
    "Error",
    JOptionPane.ERROR_MESSAGE);



}
public static void error(JFrame frame,String title, String error){
JOptionPane.showMessageDialog(frame,
    error,
    title,
    JOptionPane.ERROR_MESSAGE);



}

public static void warning(JFrame frame,String warning){

JOptionPane.showMessageDialog(frame,
    warning,
    "Warning",
    JOptionPane.WARNING_MESSAGE);

}
public static void warning(JFrame frame,String title, String warning){

JOptionPane.showMessageDialog(frame,
    warning,
    title,
    JOptionPane.WARNING_MESSAGE);

}
public static void info( JFrame frame,String info){

JOptionPane.showMessageDialog(frame,
    info, 
    "Message",
    JOptionPane.INFORMATION_MESSAGE);

}
public static void info( JFrame frame,String title, String info){

JOptionPane.showMessageDialog(frame,
    info, 
    title,
    JOptionPane.INFORMATION_MESSAGE);

}
public static void message( JFrame frame,String info){

JOptionPane.showMessageDialog(frame,
    info, 
    "Message",
    JOptionPane.INFORMATION_MESSAGE);

}
public static void message( JFrame frame,String title, String info){

JOptionPane.showMessageDialog(frame,
    info, 
    title,
    JOptionPane.INFORMATION_MESSAGE);

}
public int YN( JFrame frame,String ask){

 int n = JOptionPane.showConfirmDialog(
                            frame, ask,
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);

return n;
}
/*
public void test(JFrame frame){
Frame f = new Frame();
Panel p = new Panel();
JLabel l = new JLabel("Are you sure?");
Button y= new Button("     YES     ");
Button n= new Button("     NO     ");
f.add(p);
p.add(y);
f.setVisible(true);
}
*/








/*
JInternalFrame jif = new JInternalFrame("Error",true,true,true,true);
JPanel jp = new JPanel();
JButton jb = new JButton("Close");
public void create(JPanel cp){
jif.setSize(500,500);
cp.add(jif);
jif.add(jp);

jif.setVisible(true);
}

public void show(){
System.out.println("dfsdf");
jif.setVisible(true);
}
*/

}