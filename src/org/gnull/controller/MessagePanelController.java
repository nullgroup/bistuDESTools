package org.gnull.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.gnull.message.MessagePacket;
import org.gnull.util.FileIOController;

public final class MessagePanelController {

	private FileIOController io;
	private JTextPane textPane;

	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	public MessagePanelController() {
		this(null);
	}

	public MessagePanelController(JTextPane textPane) {
		io = new FileIOController();
		this.textPane = textPane;
	}

	/**
	 * 导出textPane中的文字至指定路径
	 * 
	 * @param dstPath
	 */
	public void export(String dstPath) {
		if (textPane != null) {
			Document doc = textPane.getDocument();
			try {
				io.exportTextToFile(doc.getText(0, doc.getLength()), dstPath);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 清空textPane中的文字
	 */
	public void clear() {
		if (textPane != null) {
			Document doc = textPane.getDocument();

			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向textPane中插入一段文字
	 * 
	 * @param text
	 */
	public void insert(String text) {
		if (textPane != null) {
			StyledDocument doc = FontStyleController
					.getStyledDocument(textPane);

			MutableAttributeSet attr = textPane.getInputAttributes();

			StyleConstants.setFontFamily(attr,
					StyleConstants.getFontFamily(attr));
			StyleConstants.setFontSize(attr, StyleConstants.getFontSize(attr));

			try {
				doc.insertString(doc.getLength(), text, attr);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 插入一个MessagePacket
	 * 
	 * @param packet
	 */
	public void insert(MessagePacket packet) {
		insert(packet.toString());
	}

	/**
	 * @param packet
	 * @param color
	 * @param replace
	 */
	public void insertColored(MessagePacket packet, Color color, boolean replace) {
		int start = textPane.getCaretPosition();
		StyledDocument doc = FontStyleController.getStyledDocument(textPane);
		MutableAttributeSet attr = textPane.getInputAttributes();
		MutableAttributeSet a = new SimpleAttributeSet();
		Color c = StyleConstants.getForeground(attr);

		StyleConstants.setFontFamily(a, StyleConstants.getFontFamily(attr));
		StyleConstants.setFontSize(a, StyleConstants.getFontSize(attr));
		StyleConstants.setForeground(a, color);

		try {
			doc.insertString(doc.getLength(), packet.toString(), attr);
			doc.setCharacterAttributes(start, doc.getLength(), a, false);
			StyleConstants.setForeground(a, c);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public Action createTextComponentAction() {
		final JPopupMenu menu = textPane.getComponentPopupMenu();
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextComponent tc = (JTextComponent) menu.getInvoker();
				JMenuItem item = (JMenuItem) e.getSource();
				String acc = item.getAccessibleContext().getAccessibleDescription();
				
				if (acc.equals("Copy")) {
					tc.copy();
				} else if (acc.equals("Select.All")) {
					tc.selectAll();
				} else if (acc.equals("Search")) {
					// search dialog
				}
			}
			
		};
		
		return a;
	}
}
