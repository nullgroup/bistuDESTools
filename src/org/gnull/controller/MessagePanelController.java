package org.gnull.controller;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.gnull.entity.MessagePacket;
import org.gnull.panel.MessagePanel;
import org.gnull.util.FileIOController;

public final class MessagePanelController {

	private FileIOController ioController;
	
	private HashMap<Object, Action> actionsMap;
	
	private JTextPane textPane;

	public JTextPane getTextPane() {
		return textPane;
	}

	public MessagePanelController() {
		this(null);
	}

	public MessagePanelController(MessagePanel msgPane) {
		textPane = msgPane.messagePane;
		actionsMap = createActionTable(textPane);
		ioController = new FileIOController();
	}

	/**
	 * 导出textPane中的文字至指定路径
	 * 
	 * @param dstPath
	 */
	public void export(String dstPath) {
		if (textPane != null) {
			Document document = textPane.getDocument();
			
			String outputMessage = "";
			try {
				outputMessage = document.getText(0, document.getLength());
			} catch (BadLocationException e) {
				System.out.println("Location Bad At Export: " + e.getMessage());
			}

			ioController.exportTextToFile(outputMessage, dstPath);
		}
	}

	/**
	 * 清空textPane中的文字
	 */
	public void clear() {
		if (textPane != null) {
			Document document = textPane.getDocument();

			try {
				document.remove(0, document.getLength());
			} catch (BadLocationException e) {
				System.out.println("Location Bad At Clear: " + e.getMessage());
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

	private HashMap<Object, Action> createActionTable(
			JTextComponent textComponent) {
		HashMap<Object, Action> actions = new HashMap<Object, Action>();
		Action[] actionsArray = textComponent.getActions();
		
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
		}
		return actions;
	}
	
	public Action getActionByName(String name) {
	    return actionsMap.get(name);
	}
}
