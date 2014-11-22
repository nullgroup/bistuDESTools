package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.gnull.controller.FontStyleController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private MessagePanel messagePane;
	private ProgressPanel progressPane;
	private JPanel controllPane;
	private ControllTabbedPanel ctrlTabPane;
	private FormatPanel forPanel;
	private JToolBar toolBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setLookAndFeel();
		createMainPanel();
	}

	private void createMainPanel() {
		JMenuBar bar = createMenuBar("������");
		toolBar = createToolBar("MainWindow.ToolBar", JToolBar.HORIZONTAL, false);
		controllPane = new JPanel(new BorderLayout(0, 0));
		messagePane = new MessagePanel();
		progressPane = new ProgressPanel();
		ctrlTabPane = new ControllTabbedPanel();
		forPanel = new FormatPanel();

		setJMenuBar(bar);
		controllPane.add(ctrlTabPane, BorderLayout.NORTH);
		controllPane.add(forPanel, BorderLayout.CENTER);
		add(toolBar, BorderLayout.NORTH);
		add(controllPane, BorderLayout.EAST);
		add(messagePane, BorderLayout.CENTER);
		add(progressPane, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 750, 550);
		setVisible(true);
	}

	public static void setLookAndFeel() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			FontStyleController.setGlobalFont(new Font("΢���ź�", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JMenuBar createMenuBar(String accessibleName) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.getAccessibleContext().setAccessibleName(accessibleName);

		JMenu fileMenu = (JMenu) menuBar.add(new JMenu("�ļ�"));
		createMenuItem(fileMenu, "�½�", KeyEvent.VK_N, "�½��ļ�", null);
		createMenuItem(fileMenu, "����", KeyEvent.VK_S, "�����ļ�", null);
		createMenuItem(fileMenu, "ɾ��", KeyEvent.VK_D, "ɾ���ļ�", null);
		fileMenu.addSeparator();

		JMenu editMenu = (JMenu) menuBar.add(new JMenu("�༭"));
		createMenuItem(editMenu, "�����С", KeyEvent.VK_S, "�����С", null);
		createMenuItem(editMenu, "��ɫ", KeyEvent.VK_C, "��ɫ", null);
		createMenuItem(editMenu, "��ʽ", KeyEvent.VK_F, "��ʽ", null);
		editMenu.addSeparator();

		JMenu helpMenu = (JMenu) menuBar.add(new JMenu("����"));
		createMenuItem(helpMenu, "����", KeyEvent.VK_S, "�����С", null);
		createMenuItem(helpMenu, "��������", KeyEvent.VK_A, "��ɫ", null);
		helpMenu.addSeparator();

		return menuBar;
	}

	private JMenuItem createMenuItem(JMenu menu, String label, int mnemonic,
			String accessibleDescription, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.setMnemonic(mnemonic);
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.addActionListener(action);
		return mi;
	}

	private JToolBar createToolBar(String name, int orientation, boolean isFloatable) {
		JToolBar bar = new JToolBar(name, orientation);
		bar.setFloatable(isFloatable);
		bar.getAccessibleContext().setAccessibleName("������");

		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"���", null);
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"���", null);
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"����", null);
		bar.addSeparator();
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"ֹͣ", null);
		bar.addSeparator();

		return bar;
	}

	private JButton createToolBarButton(JToolBar bar, String text, Icon icon,
			String accessibleDescription, Action action) {
		JButton btn = (JButton) bar.add(action);

		btn.setText(text);
		btn.setIcon(icon);
		btn.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		return btn;
	}

}
