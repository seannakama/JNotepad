//		Name: Nakama, Sean
//		Project: #5
//		Due: 12/7/18
//		Course: CS-2450-01-F18
//
//		Description:
//			This is the find class used by the JNotepad class. 
//			This creates a dialog for the two find functions
//			in JNotepad. It allows the user to type in a string 
//			so it can be searched for in the text area of JNotepad.
//			The showDialog method returns a string.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Find{
	public static String showDialog(JFrame parent){
		final JTextField jtf;
		final JDialog dlg = new JDialog(parent, "Find", false);
		dlg.setLayout(new FlowLayout());
		dlg.setLocationRelativeTo(null);
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setSize(450, 150);
		
		JLabel findWhat = new JLabel("Find What: ");
		dlg.add(findWhat);
		jtf = new JTextField(20);
		dlg.add(jtf);
		JButton findNext = new JButton("Find Next");
		dlg.add(findNext);
		JButton cancel = new JButton("Cancel");
		dlg.add(cancel);
		
		findNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dlg.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jtf.setText("");
				dlg.dispose();
			}
		});
		dlg.setVisible(true);
		return jtf.getText();
	}
}