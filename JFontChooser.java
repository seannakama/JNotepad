//		Name: Nakama, Sean
//		Project: #5
//		Due: 12/7/18
//		Course: CS-2450-01-F18
//
//		Description:
//			This is the JFontChooser class that is used by the 
//			JNotepad class. This creates a dialog for the user
//			to select their font, font size, and font style. 
//			It will later change the font and style in the 
//			text area of JNotepad. 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JFontChooser{
	
	public static Font showDialog(JFrame parent, Font deafult){
		final JDialog dlg = new JDialog(parent, "Font Chooser", true);
		dlg.setLayout(new FlowLayout());
		dlg.setSize(700,400);
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("cancel");
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				dlg.setVisible(false);
			}});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				dlg.dispose();
			}
		});
		
		final String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		DefaultListModel lm = new DefaultListModel();
		for(int i = 0; i < fonts.length; i++){
			lm.addElement(fonts[i]);
		}
		
		final JList jlst = new JList(lm);
		final JScrollPane pane = new JScrollPane(jlst);
		pane.setPreferredSize(new Dimension(100,300));
		final JLabel displayFont = new JLabel("Current Font: ");
		
		final JLabel fontN2 = new JLabel("");
		final JLabel fontS2 = new JLabel("12");
		final JLabel fontStyle2 = new JLabel("0");
		
		jlst.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent le){
				int fontN = jlst.getSelectedIndex();
				fontN2.setText("" + fonts[fontN]);
				displayFont.setText("Current Font:" + fonts[fontN]);
			}});
		
		final JSlider slider = new JSlider(JSlider.VERTICAL);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setLabelTable(slider.createStandardLabels(20));
		slider.setPaintTicks(true);
		final JLabel displaySize = new JLabel("Current Font Size: " + slider.getValue());
	    slider.addChangeListener(new ChangeListener() {  
	        public void stateChanged(ChangeEvent ce) {  
	          // Display the new value. 
	          displaySize.setText("Current Font Size: " 
	                            + slider.getValue());     
	          int fontS = slider.getValue();
	          fontS2.setText("" + fontS);
	        } 
	      });  
	    
	    DefaultListModel lm2 = new DefaultListModel();
	    lm2.addElement("Plain");
	    lm2.addElement("Bold");
	    lm2.addElement("Italic");
	    final JList list2 = new JList(lm2);
	    
	    JScrollPane pane2 = new JScrollPane(list2);
	    
	    list2.setPreferredSize(new Dimension(100,50));
	    final JLabel displayStyle = new JLabel("Current Style: ");
	    final int fontStyle = 0;
	    list2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent le){
				Object values[] = list2.getSelectedValues();
				String style = "";
				for(int i = 0; i < values.length; i++){
					style = (String)values[i];
				}
				displayStyle.setText("Current Style: " + style);
				
				int fontStyle = 0;
				if(style.equals("italic")){
					fontStyle = Font.ITALIC;
				}
				else if(style.equals("bold")){
					fontStyle = Font.BOLD;
				}
				else
					fontStyle = Font.PLAIN;
				fontStyle2.setText("" + fontStyle);
			}});
		
		dlg.add(pane);
		dlg.add(displayFont);
		dlg.add(slider);
		dlg.add(displaySize);
		dlg.add(pane2);
		dlg.add(displayStyle);
		dlg.add(ok);
		dlg.add(cancel);
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
		
		Font returnFont;
		
		int fontStyle3 = Integer.parseInt(fontStyle2.getText());
		int fontS3 = Integer.parseInt(fontS2.getText());
		if((fontN2.getText().length() == 0)){
			returnFont = new Font("Courier New", fontStyle3, fontS3);
		}
		else{
			returnFont = new Font(fontN2.getText(), fontStyle3, fontS3);
		}
		return returnFont;
	}
}