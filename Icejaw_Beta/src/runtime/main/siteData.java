package runtime.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class siteData {

	JPanel panel = null;
	JEditorPane output = null;

	
	public siteData(JPanel p, JEditorPane output){
		panel = p;
		this.output = output;
		
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		panel.setLayout(new GridBagLayout());
		panel.add(new JScrollPane(output),c);
		output.setContentType("text/html");
		output.setEditable(false);

	}
	
	
	public void setText(String text){
		output.setText(text);
		
	}
	
	public String getText(){
		return output.getText();
	}
	




	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JEditorPane getOutput() {
		return output;
	}
	public void setOutput(JEditorPane output) {
		this.output = output;
	}
	
	
}
