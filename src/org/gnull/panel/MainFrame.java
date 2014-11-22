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
		JMenuBar bar = createMenuBar("工具栏");
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
			FontStyleController.setGlobalFont(new Font("微软雅黑", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JMenuBar createMenuBar(String accessibleName) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.getAccessibleContext().setAccessibleName(accessibleName);

		JMenu fileMenu = (JMenu) menuBar.add(new JMenu("文件"));
		createMenuItem(fileMenu, "新建", KeyEvent.VK_N, "新建文件", null);
		createMenuItem(fileMenu, "保存", KeyEvent.VK_S, "保存文件", null);
		createMenuItem(fileMenu, "删除", KeyEvent.VK_D, "删除文件", null);
		fileMenu.addSeparator();

		JMenu editMenu = (JMenu) menuBar.add(new JMenu("编辑"));
		createMenuItem(editMenu, "字体大小", KeyEvent.VK_S, "字体大小", null);
		createMenuItem(editMenu, "颜色", KeyEvent.VK_C, "颜色", null);
		createMenuItem(editMenu, "格式", KeyEvent.VK_F, "格式", null);
		editMenu.addSeparator();

		JMenu helpMenu = (JMenu) menuBar.add(new JMenu("帮助"));
		createMenuItem(helpMenu, "设置", KeyEvent.VK_S, "字体大小", null);
		createMenuItem(helpMenu, "关于我们", KeyEvent.VK_A, "颜色", null);
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
		bar.getAccessibleContext().setAccessibleName("工具栏");

		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"浏览", null);
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"清空", null);
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"导出", null);
		bar.addSeparator();
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"停止", null);
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
