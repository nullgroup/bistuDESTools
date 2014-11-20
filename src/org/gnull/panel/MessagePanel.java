package org.gnull.panel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;

import org.gnull.controller.FontStyleController;
import org.gnull.controller.MessagePanelController;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextPane msgTextPane;
	private MessagePanelController mpc;
	
	public JTextPane getTextPane() {
		return msgTextPane;
	}
	
	public MessagePanelController getMessagePanelController() {
		return mpc;
	}

	public MessagePanel() {
		createMessagePanel();
		addTestMessage();
	}

	private void addTestMessage() {
		mpc.insert("≤‚ ‘Œƒ±æabcdefghijklmnopqrstuvwxyz");
	}

	public void createMessagePanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(LEFT_ALIGNMENT);
		
		msgTextPane = new JTextPane();
		msgTextPane.setEditable(false);
		msgTextPane.setDocument(new DefaultStyledDocument());
		mpc = new MessagePanelController(msgTextPane);
		FontStyleController.setFontFamily(msgTextPane, "Œ¢»Ì—≈∫⁄");
		
		add(new JScrollPane(msgTextPane), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		MessagePanel p = new MessagePanel();
		
		f.getContentPane().add(p);
		f.pack();
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
