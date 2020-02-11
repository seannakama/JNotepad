//		Name: Nakama, Sean
//		Project: #5
//		Due: 12/7/18
//		Course: CS-2450-01-F18
//
//		Description:
//			This is a copy of the basic Notepad app using Java
//			Swing. It contains most of the basic functions that
//			can be found in Notepad. Keyboard accelerators can be
//			used. It implements a file chooser, font chooser, and
//			several other functions. 

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class JNotepad {
	JFrame frame;
	ImageIcon icon;
	JFileChooser jfc;
	JPopupMenu jpu;
	JTextArea jta;
	boolean modify;
	int findIdx;
	public JNotepad(){
		frame = new JFrame("JNotepad");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("JNotepad.png"));
		icon = new ImageIcon(image);
		frame.setIconImage(icon.getImage());
		
		jta = new JTextArea();
		Font font = new Font("Courier", Font.PLAIN, 12);
		jta.setFont(font);
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(100,75));

		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');
		JMenuItem viewHelp = new JMenuItem("View Help", 'h');
		viewHelp.setEnabled(false);
		
		
		JMenuItem open = new JMenuItem("Open...", 'o');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		JMenuItem exit = new JMenuItem("Exit", 'x');
		JMenuItem about = new JMenuItem("About JNotepad", 'a');
		JMenuItem newF = new JMenuItem("New", 'n');
		newF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		JMenuItem save = new JMenuItem("Save", 's');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		JMenuItem saveAs = new JMenuItem("Save As...", 'A');
		saveAs.setDisplayedMnemonicIndex(5);
		JMenuItem pageSetup = new JMenuItem("Page Setup...", 'u');
		JMenuItem print = new JMenuItem("Print", 'p');
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic('E');
		JMenuItem undo = new JMenuItem("Undo", 'u');
		JMenuItem cuts = new JMenuItem("Cut", 't');
		cuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		JMenuItem copys = new JMenuItem("Copy", 'c');
		copys.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		JMenuItem pastes = new JMenuItem("Paste", 'p');
		pastes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		JMenuItem delete = new JMenuItem("Delete", 'l');
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		JMenuItem find = new JMenuItem("Find...", 'f');
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		JMenuItem findNext = new JMenuItem("Find Next", 'N');
		findNext.setDisplayedMnemonicIndex(5);
		JMenuItem replace = new JMenuItem("Replace...", 'r');
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		JMenuItem go = new JMenuItem("Go To...", 'G');
		go.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		JMenuItem select = new JMenuItem("Select All", 'A');
		select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		JMenuItem timeDate = new JMenuItem("Time/Date", 'D');
		timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		
		JMenu format = new JMenu("Format");
		format.setMnemonic('o');
		final JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem("Word Wrap");
		wordWrap.setMnemonic('W');
		wordWrap.setDisplayedMnemonicIndex(0);
		JMenuItem fonts = new JMenuItem("Font...", 'f');
		
		JMenu view = new JMenu("View");
		view.setMnemonic('v');
		JMenuItem statusBar = new JMenuItem("Status Bar", 0);
		
		pageSetup.setEnabled(false);
		print.setEnabled(false);
		undo.setEnabled(false);
		replace.setEnabled(false);
		go.setEnabled(false);
		statusBar.setEnabled(false);
		
		jta.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent ce){
				findIdx = jta.getCaretPosition();
			}
		});
		
		
		class JavaFileFilter extends FileFilter{
			public boolean accept(File file){
				if(file.getName().endsWith(".java"))
					return true;
				if(file.isDirectory())
					return true;
				return false;
			}
			public String getDescription(){
				return "Java Source Code Files";
			}
		}
		class TextFileFilter extends FileFilter{
			public boolean accept(File file){
				if(file.getName().endsWith(".txt"))
					return true;
				if(file.isDirectory())
					return true;
				return false;
			}
			public String getDescription(){
				return "Text Documents";
			}
		}
		jfc = new JFileChooser();
		jfc.setFileFilter(new JavaFileFilter());
		jfc.setFileFilter(new TextFileFilter());
		
		newF.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(jta.getText().length() == 0)
					jta.setText("");
				else{
					int response = JOptionPane.showConfirmDialog(frame, "Would you like to save this file?");
					if(response == JOptionPane.YES_OPTION){
						JFileChooser fc = new JFileChooser();
						int result = fc.showOpenDialog(frame);
						if(result == JFileChooser.APPROVE_OPTION){
					        File textFile = jfc.getSelectedFile();
					        StringBuilder sb = new StringBuilder();
					        BufferedReader reader;
					        try {
					            reader = new BufferedReader(new FileReader(textFile));
					            String line = reader.readLine();
					            while(line != null) {
					                sb.append(line);
					                sb.append(System.lineSeparator());
					                line = reader.readLine();
					            }
					            jta.setText(sb.toString());
					        } 
					        catch (FileNotFoundException e1) {
					            e1.printStackTrace();
					        }
					        catch (IOException e1) {
					            e1.printStackTrace();
					        }
						}
					}
					else if(response == JOptionPane.CANCEL_OPTION)
						return;
					else
						jta.setText("");
				}
			}
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int result = jfc.showOpenDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION){
			        File textFile = jfc.getSelectedFile();
			        StringBuilder sb = new StringBuilder();
			        BufferedReader reader;
			        try {
			            reader = new BufferedReader(new FileReader(textFile));
			            String line = reader.readLine();
			            while(line != null) {
			                sb.append(line);
			                sb.append(System.lineSeparator());
			                line = reader.readLine();
			            }
			            jta.setText(sb.toString());
			        } 
			        catch (FileNotFoundException e1) {
			            e1.printStackTrace();
			        }
			        catch (IOException e1) {
			            e1.printStackTrace();
			        }
				}
			}
		});
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser saveChooser = new JFileChooser();
				int result = saveChooser.showSaveDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION){
					try {
						// create a buffered writer to write to a file
						BufferedWriter out = new BufferedWriter(new FileWriter(saveChooser.getSelectedFile().getPath() + ".txt"));
						out.write(jta.getText());
						out.close();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
				else
					return;
			}
		});
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser saveChooser = new JFileChooser();
				int result = saveChooser.showSaveDialog(frame);
				if(result == JFileChooser.APPROVE_OPTION){
					try {
						// create a buffered writer to write to a file
						BufferedWriter out = new BufferedWriter(new FileWriter(saveChooser.getSelectedFile().getPath() + ".txt"));
						out.write(jta.getText());
						out.close();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
				else
					return;
			}
		});
		
		class GetTime{
		     DateFormat dateFormat = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
		     Calendar cal = Calendar.getInstance();
		     String time = dateFormat.format(cal.getTime());
		}
		
		timeDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				GetTime time = new GetTime();
				jta.setText(jta.getText() + time.time);
			}
		});
		
		find.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
					findIdx = 0;
					find(findIdx);
				}
		});
		
		findNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				find(findIdx + 1);
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(jta.getText().length() != 0){
					int response = JOptionPane.showConfirmDialog(frame, "Do you want to save changes?");
					if(response == JOptionPane.YES_OPTION){
						JFileChooser saveChooser = new JFileChooser();
						int result = saveChooser.showSaveDialog(frame);
						if(result == JFileChooser.APPROVE_OPTION){
							try {
								// create a buffered writer to write to a file
								BufferedWriter out = new BufferedWriter(new FileWriter(saveChooser.getSelectedFile().getPath() + ".txt"));
								out.write(jta.getText());
								out.close();
							} catch (Exception ex) {
								System.out.println(ex.getMessage());
							}
							System.exit(0);
						}
						else if(result == JFileChooser.CANCEL_OPTION)
							return;
						else
							System.exit(0);
					}
					else if(response == JOptionPane.NO_OPTION)
						System.exit(0);
					else
						return;
				}
			}
		});
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JOptionPane.showMessageDialog(frame, "<html>JNotepad<br>(c) Sean Nakama<html/>",
						null, 0, icon);
			}
		});
		
		wordWrap.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(wordWrap.isSelected()){
					jta.setLineWrap(true);
					jta.setWrapStyleWord(true);
				}
				else{
					jta.setLineWrap(false);
					jta.setWrapStyleWord(false);
				}
			}
		});
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.selectAll();
			}
		});
		fonts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Font fontSel = JFontChooser.showDialog(frame, new Font("Courier New", Font.PLAIN, 12));
				jta.setFont(fontSel);
			}
		});
		
		jpu = new JPopupMenu();
		JMenuItem cut = new JMenuItem("Cut", 't');
		JMenuItem copy = new JMenuItem("Copy", 'c');
		JMenuItem paste = new JMenuItem("Paste", 'p');
		
		cut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.cut();
			}
		});
		copy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.copy();
			}
		});
		paste.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.paste();
			}
		});
		jpu.add(cut);
		jpu.add(copy);
		jpu.add(paste);
		
		cuts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.cut();
			}
		});
		copys.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.copy();
			}
		});
		pastes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jta.paste();
			}
		});
		
		jta.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				if(me.isPopupTrigger())
					jpu.show(me.getComponent(), me.getX(), me.getY());
			}
			public void mouseReleased(MouseEvent me){
				if(me.isPopupTrigger())
					jpu.show(me.getComponent(), me.getX(), me.getY());
			}
		});
		
		file.add(newF);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(pageSetup);
		file.add(print);
		file.addSeparator();
		file.add(exit);
		
		edit.add(undo);
		edit.addSeparator();
		edit.add(cuts);
		edit.add(copys);
		edit.add(pastes);
		edit.add(delete);
		edit.addSeparator();
		edit.add(find);
		edit.add(findNext);
		edit.add(replace);
		edit.add(go);
		edit.addSeparator();
		edit.add(select);
		edit.add(timeDate);
		
		format.add(wordWrap);
		format.add(fonts);
		
		view.add(statusBar);
		
		help.add(viewHelp);
		help.addSeparator();
		help.add(about);
		menubar.add(file);
		menubar.add(edit);
		menubar.add(format);
		menubar.add(view);
		menubar.add(help);
		frame.setJMenuBar(menubar);
		frame.add(jsp);
		frame.setVisible(true);
	}
	
	public void find(int start){
		String str = jta.getText();
		String findStr = Find.showDialog(frame);
		int idx = str.indexOf(findStr, start);
		if(idx > -1){
			jta.setCaretPosition(idx);
			findIdx = idx;
		}
		else
			JOptionPane.showMessageDialog(frame, "String not found.");
		jta.requestFocusInWindow();
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new JNotepad();
			}
		});
	}
}
