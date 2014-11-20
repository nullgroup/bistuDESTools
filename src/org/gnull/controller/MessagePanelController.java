package org.gnull.controller;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
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

	public void insert(String text) {
		if (textPane != null) {
			StyledDocument doc = FontStyleController.getStyledDocument(textPane);

			MutableAttributeSet attr = textPane.getInputAttributes();

			StyleConstants.setFontFamily(attr,
					StyleConstants.getFontFamily(attr));
			StyleConstants.setFontSize(attr, StyleConstants.getFontSize(attr));
			
			System.out.println(StyleConstants.getFontSize(attr));

			try {
				doc.insertString(doc.getLength(), text, attr);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(MessagePacket packet) {
		insert(packet.toString());
	}

	public void insertColored(MessagePacket packet, Color color,
			boolean replace) {
		int start = textPane.getCaretPosition();
		StyledDocument doc = FontStyleController.getStyledDocument(textPane);
		MutableAttributeSet attr = textPane.getInputAttributes();
		MutableAttributeSet a = new SimpleAttributeSet();
		Color c = StyleConstants.getForeground(attr);

		StyleConstants.setFontFamily(a, StyleConstants.getFontFamily(attr));
		StyleConstants.setFontSize(a, StyleConstants.getFontSize(attr));
		StyleConstants.setForeground(a, color);
		
		System.out.println(StyleConstants.getFontSize(attr));

		try {
			doc.insertString(doc.getLength(), packet.toString(), attr);
			doc.setCharacterAttributes(start, doc.getLength(), a, false);
			StyleConstants.setForeground(a, c);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
