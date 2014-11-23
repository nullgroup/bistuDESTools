package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.accessibility.AccessibleContext;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import org.gnull.controller.MessagePanelController;

/**
 * @author OSX
 *
 */
public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public JTextPane messagePane;
	
	/**
	 * 
	 */
	private MessagePanelController controller;

	/**
	 * @return
	 */
	public MessagePanelController getController() {
		return controller;
	}

	/**
	 * 
	 */
	public MessagePanel() {
		createMessagePanel();
		addTestMessage(); // Just for test
	}

	/**
	 * 
	 */
	private void addTestMessage() {
		controller.insert("测试文本abcdefghijklmnopqrstuvwxyz");
	}

	/**
	 * 
	 */
	public void createMessagePanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(LEFT_ALIGNMENT);

		Document document = new DefaultStyledDocument();
		messagePane = createMessagePane(false, document,
				"MessagePanel.TextPane");

		controller = new MessagePanelController(this);

		JPopupMenu popMenu = createPopupMenu("消息面板弹出菜单",
				"MessagePanel.PopupMenu");
		messagePane.setComponentPopupMenu(popMenu);

		add(new JScrollPane(messagePane), BorderLayout.CENTER);
	}

	/**
	 * @param editable
	 * @param document
	 * @return
	 */
	private JTextPane createMessagePane(boolean editable, Document document,
			String accessibleName) {
		JTextPane pane = new JTextPane();
		AccessibleContext context = pane.getAccessibleContext();
		context.setAccessibleName(accessibleName);

		pane.setEditable(editable);
		pane.setDocument(document);

		return pane;
	}

	/**
	 * @param name
	 * @param accessibleName
	 * @return
	 */
	private JPopupMenu createPopupMenu(String name, String accessibleName) {
		JPopupMenu menu = new JPopupMenu(name);
		AccessibleContext context = menu.getAccessibleContext();
		context.setAccessibleName(accessibleName);

		KeyStroke keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK);
		KeyStroke keySelectAll = KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK);
		KeyStroke keySearch = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK);

		addPopupMenuItem(menu, "复制", "String.Copy", keyCopy,
				DefaultEditorKit.copyAction);

		addPopupMenuItem(menu, "全选", "String.SelectAll", keySelectAll,
				DefaultEditorKit.selectAllAction);

		menu.addSeparator();

		addPopupMenuItem(menu, "查找", "String.Search", keySearch, null);

		return menu;
	}

	/**
	 * @param menu
	 * @param label
	 * @param accessibleDescription
	 * @param keyStroke
	 * @param action
	 * @return
	 */
	private JMenuItem addPopupMenuItem(JPopupMenu menu, String label,
			String accessibleDescription, KeyStroke keyStroke, String action) {

		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		// 根据动作的名称匹配动作
		// @see MessagePanelController#getActionByName(String action)
		ActionListener actionListener = this.getController().getActionByName(
				action);

		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.setAccelerator(keyStroke);
		mi.addActionListener(actionListener);

		return mi;
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		        //Create and set up the window.
		        JFrame frame = new JFrame("MessagePanelDemo");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		        //Create and set up the content pane.
		        javax.swing.JComponent newContentPane = new MessagePanel();
		        newContentPane.setOpaque(true); //content panes must be opaque
		        frame.setContentPane(newContentPane);
				frame.setSize(600, 400);
		 
		        //Display the window.
		        frame.setVisible(true);
			}
		});
	}
}
