package ui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import rmi.RemoteHelper;
import runner.Compiler;


public class MainFrame extends JFrame {
	private JTextArea textArea;
	private JTextArea inputArea;	 
	private JTextArea resultArea;
	
	private Compiler result;
	

	public MainFrame() {
		// 
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem runMenuItem = new JMenuItem("Run");
		fileMenu.add(runMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());

		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setSize(490,200);
		textArea.setLineWrap(true);
		textArea.setLocation(0,0);
		frame.add(textArea);
		
		inputArea = new JTextArea();
		inputArea.setMargin(new Insets(10,10,10,10));
		inputArea.setBackground(Color.GRAY);
		inputArea.setSize(250, 200);
		inputArea.setLocation(0,200);
		frame.add(inputArea);

		// 
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultArea.setMargin(new Insets(10,10,10,10));
		resultArea.setBackground(Color.WHITE);
		resultArea.setSize(250,200);
		resultArea.setLocation(250,200);
		frame.add(resultArea);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}
	
	class MenuItemActionListener implements ActionListener {
		/**
		 * 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals("New")){
				textArea.setText("");
				inputArea.setText("");
				resultArea.setText("");
			}else if (cmd.equals("Open")) {
				textArea.setText("Open");
			} else if (cmd.equals("Save")) {
				textArea.setText("Save");
			} else if (cmd.equals("Run")) {
				if(inputArea.getText() == null){
					result = new Compiler(textArea.getText());
				}else{
					result = new Compiler(textArea.getText(),inputArea.getText());
				}
				resultArea.setText(result.getResult());
			} else if (cmd.equals("Exit")) {
				System.exit(0);
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
}
