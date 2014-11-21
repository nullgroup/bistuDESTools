package org.gnull.controller;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 * 字体样式控制器
 * 
 * @author OSX
 *
 */
public final class FontStyleController {

	public static Font getDefaultFont() {
		return font;
	}

	public static void setDefaultFont(Font font) {
		FontStyleController.font = font;
	}

	public FontStyleController() {
		// TODO Auto-generated constructor stub
	}

	private static Font font;

	/**
	 * 设置字体种类, 需要给出一个指针指向目标textPane
	 * 
	 * @param textPane
	 *            目标textPane
	 * @param fontFamily
	 *            新的字体种类
	 */
	public static void setFontFamily(final JTextPane textPane, String fontFamily) {
		if (textPane != null) {
			MutableAttributeSet attr = textPane.getInputAttributes();
			StyleConstants.setFontFamily(attr, fontFamily);
			setCharacterAttributes(textPane, attr, false);
		}
	}

	/**
	 * 设置字体大小, 需要给出一个指针指向目标textPane
	 * 
	 * @param textPane
	 *            目标textPane
	 * @param fontFamily
	 *            新的字体大小
	 */
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

	/**
	 * 设置字体颜色, 需要给出一个指针指向目标textPane
	 * 
	 * @param textPane
	 *            目标textPane
	 * @param fontFamily
	 *            新的字体颜色
	 */
	public static void setForeground(final JTextPane textPane, int begin,
			int end, Color fg, boolean replace) {
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

	/**
	 * 对整个文档的文字设置属性
	 * 
	 * @param textPane
	 * @param attr
	 * @param replace
	 */
	public static void setCharacterAttributes(final JTextPane textPane,
			AttributeSet attr, boolean replace) {
		StyledDocument doc = getStyledDocument(textPane);
		doc.setCharacterAttributes(0, doc.getLength(), attr, replace);
	}

	/**
	 * 对文档的某个区域的文字设置属性, 受影响区域自start到end
	 * 
	 * @param textPane
	 * @param start
	 * @param end
	 * @param attr
	 *            新的属性
	 * @param replace
	 */
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

	/**
	 * 返回目标textPane的Document
	 * 
	 * @param textPane
	 * @return
	 */
	public static StyledDocument getStyledDocument(final JTextPane textPane) {
		Document d = textPane.getDocument();
		if (d instanceof StyledDocument) {
			return (StyledDocument) d;
		}
		throw new IllegalArgumentException("document must be StyledDocument");
	}

	/**
	 * 返回目标textPane的Document
	 * 
	 * @param textPane
	 * @return
	 */
	public static StyledEditorKit getStyledEditorKit(final JTextPane textPane) {
		EditorKit k = textPane.getEditorKit();
		if (k instanceof StyledEditorKit) {
			return (StyledEditorKit) k;
		}
		throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
	}

	public static void setGlobalFont(Font font) {
	    FontUIResource fontRes = new FontUIResource(font);
	    Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof FontUIResource) {
	            UIManager.put(key, fontRes);
	        }
	    }
	}
}
