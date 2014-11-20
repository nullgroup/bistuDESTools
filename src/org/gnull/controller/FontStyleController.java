package org.gnull.controller;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public final class FontStyleController {
	
	public FontStyleController() {
		// TODO Auto-generated constructor stub
	}

	public static void setFontFamily(final JTextPane textPane, String fontFamily) {
		if (textPane != null) {
			MutableAttributeSet attr = textPane.getInputAttributes();
			StyleConstants.setFontFamily(attr, fontFamily);
			setCharacterAttributes(textPane, attr, false);
		}
	}
	
	public static void setFontSize(final JTextPane textPane, int size) {
		if (textPane != null) {
			if (size >= 0 && size <= 512) {
				MutableAttributeSet attr = textPane.getInputAttributes();
				StyleConstants.setFontSize(attr, size);
				setCharacterAttributes(textPane, attr, true);
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(textPane);
			}
		}
	}

	public static void setForeground(final JTextPane textPane, int begin, int end, Color fg, boolean replace) {
		if (textPane != null) {
			if (fg != null) {
				MutableAttributeSet attr = textPane.getInputAttributes();
				StyleConstants.setForeground(attr, fg);
				setCharacterAttributes(textPane, begin, end, attr, replace);
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(textPane);
			}
		}
	}
	
	public static void setCharacterAttributes(final JTextPane textPane, AttributeSet attr,
			boolean replace) {
		StyledDocument doc = getStyledDocument(textPane);
		doc.setCharacterAttributes(0, doc.getLength(), attr, replace);
	}

	public static void setCharacterAttributes(JTextPane textPane, int start,
			int end, AttributeSet attr, boolean replace) {
		StyledDocument doc = getStyledDocument(textPane);
		doc.setCharacterAttributes(start, end, attr, replace);

		StyledEditorKit k = getStyledEditorKit(textPane);
		MutableAttributeSet inputAttributes = k.getInputAttributes();
		if (replace) {
			inputAttributes.removeAttributes(inputAttributes);
		}
		inputAttributes.addAttributes(attr);
	}

	public static StyledDocument getStyledDocument(final JTextPane textPane) {
		Document d = textPane.getDocument();
		if (d instanceof StyledDocument) {
			return (StyledDocument) d;
		}
		throw new IllegalArgumentException("document must be StyledDocument");
	}

	public static StyledEditorKit getStyledEditorKit(final JTextPane textPane) {
		EditorKit k = textPane.getEditorKit();
		if (k instanceof StyledEditorKit) {
			return (StyledEditorKit) k;
		}
		throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
	}
}
