package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MessagePanelController controller;
	
	public JTextPane messagePane;

	public MessagePanelController getController() {
		return controller;
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

	public MessagePanel() {
		createMessagePanel();
		addTestMessage(); // Just for test
	}

	private void addTestMessage() {
		controller.insert("测试文本abcdefghijklmnopqrstuvwxyz");
	}

	public void createMessagePanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(LEFT_ALIGNMENT);

		Document document = new DefaultStyledDocument();
		messagePane = createMessagePane(false, document);

		controller = new MessagePanelController(this);

		JPopupMenu popMenu = createPopupMenu("消息面板弹出菜单", "MessagePanel.PopupMenu");
		messagePane.setComponentPopupMenu(popMenu);

		add(new JScrollPane(messagePane), BorderLayout.CENTER);
	}

	private JTextPane createMessagePane(boolean editable, Document document) {
		JTextPane pane = new JTextPane();

		pane.setEditable(editable);
		pane.setDocument(document);

		return pane;
	}

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

		createPopupMenuItem(menu, "复制", "Copy", keyCopy,
				DefaultEditorKit.copyAction);
		createPopupMenuItem(menu, "全选", "Select.All", keySelectAll,
				DefaultEditorKit.selectAllAction);
		menu.addSeparator();
		createPopupMenuItem(menu, "查找", "Search", keySearch, null);

		return menu;
	}

	private JMenuItem createPopupMenuItem(JPopupMenu menu, String label,
			String accessibleDescription, KeyStroke keyStroke, String action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.setAccelerator(keyStroke);
		mi.addActionListener(controller.getActionByName(action));

		return mi;
	}
}
