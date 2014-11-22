package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultStyledDocument;

import org.gnull.controller.MessagePanelController;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextPane msgTextPane;
	private MessagePanelController mpc;

	public JTextPane getTextPane() {
		return msgTextPane;
	}

	public MessagePanelController getController() {
		return mpc;
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
		mpc.insert("测试文本abcdefghijklmnopqrstuvwxyz");
	}

	public void createMessagePanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(LEFT_ALIGNMENT);

		msgTextPane = new JTextPane();
		msgTextPane.setEditable(false);
		msgTextPane.setDocument(new DefaultStyledDocument());

		mpc = new MessagePanelController(msgTextPane);

		JPopupMenu popMenu = createPopupMenu("消息面板弹出菜单");

		msgTextPane.setComponentPopupMenu(popMenu);
		add(new JScrollPane(msgTextPane), BorderLayout.CENTER);
	}

	private JPopupMenu createPopupMenu(String accessibleName) {
		JPopupMenu menu = new JPopupMenu();
		menu.getAccessibleContext().setAccessibleName(accessibleName);

		Action textComponentAction = mpc.createTextComponentAction();

		createPopupMenuItem(menu, "复制", "Copy",
				KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK),
				textComponentAction);
		createPopupMenuItem(menu, "全选", "Select.All",
				KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK),
				textComponentAction);
		menu.addSeparator();
		createPopupMenuItem(menu, "查找", "Search",
				KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK),
				textComponentAction);

		return menu;
	}

	private JMenuItem createPopupMenuItem(JPopupMenu menu, String label,
			String accessibleDescription, KeyStroke ks, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.addActionListener(action);

		return mi;
	}
}
