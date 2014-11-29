package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import org.gnull.controller.FontStyleController;
import org.gnull.controller.MessagePanelController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private MainPanel mainPane;
	
	private QRCodePanel qrPane;
	
	public MainFrame() {
		setLookAndFeel();
		setupComponents();
	}
	
	public void setLookAndFeel() {
		try {
			// 设置UI界面
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置全局字体
		Font globalFont = new Font("微软雅黑", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 20, 550, 720);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new BorderLayout(0, 0));
	}

	private void setupComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		mainPane = new MainPanel();
		qrPane = new QRCodePanel();

		tabbedPane.add("文件加解密", mainPane);
		tabbedPane.add("二维码加解密", qrPane);
		

		// 创建菜单栏
		JMenuBar bar = createMenuBar("MainFrame.MenuBar");

		// 创建工具栏
		JToolBar toolBar = createToolBar("工具栏", "MainFrame.ToolBar",
				JToolBar.HORIZONTAL, false);
		

		setJMenuBar(bar);
		add(toolBar, BorderLayout.NORTH);
	}

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
	
	/**
	 * 创建菜单栏
	 * 
	 * @param accessibleName
	 * @return
	 */
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

	/**
	 * 创造菜单项
	 * 
	 * @param menu
	 *            父容器菜单栏
	 * @param label
	 *            菜单项的文字
	 * @param mnemonic
	 *            菜单项快捷键
	 * @param accessibleDescription
	 *            菜单项的描述
	 * @param action
	 *            菜单项的动作
	 * @return
	 */
	private JMenuItem createMenuItem(JMenu menu, String label, int mnemonic,
			String accessibleDescription, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());

		// 设置菜单项快捷键
		mi.setMnemonic(mnemonic);
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		// 设置菜单项动作
		mi.addActionListener(action);

		return mi;
	}

	/**
	 * 创建一个工具栏
	 *
	 * @param name
	 *            工具栏名称
	 * @param accessiblename
	 *            工具栏的描述
	 * @param orientation
	 *            工具栏的方向(通常是HORIZONTAL 水平方向)
	 * @param isFloatable
	 *            工具栏是否浮动
	 * @return
	 */
	private JToolBar createToolBar(String name, String accessiblename,
			int orientation, boolean isFloatable) {

		JToolBar bar = new JToolBar(name, orientation);

		bar.setFloatable(isFloatable);
		bar.getAccessibleContext().setAccessibleName(accessiblename);

		// 添加浏览按钮至工具栏
		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"浏览", createBrowseAction());

		// 添加清空按钮至工具栏
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"清空", createClearAction());

		// 添加导出按钮至工具栏
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"导出", createExportAction());

		bar.addSeparator();

		// 添加停止按钮至工具栏
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"停止", createStopAction());

		bar.addSeparator();

		return bar;
	}

	/**
	 * 创造一个工具栏按钮，并添加按钮至工具栏中
	 *
	 * @param toolBar
	 *            工具栏
	 * @param text
	 *            工具栏按钮的文字
	 * @param icon
	 *            工具栏按钮的图标
	 * @param accessibleDescription
	 *            工具栏按钮的描述
	 * @param action
	 *            工具栏按钮的动作
	 * @return
	 */
	private JButton createToolBarButton(JToolBar toolBar, String text,
			Icon icon, String accessibleDescription, Action action) {
		// 创造按钮并添加
		JButton toolBarButton = (JButton) toolBar.add(action);

		// 设置按钮文字
		toolBarButton.setText(text);

		// 设置按钮图标
		toolBarButton.setIcon(icon);

		// 设置按钮提示文字
		toolBarButton.setToolTipText(accessibleDescription);

		// 设置按钮描述
		toolBarButton.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		return toolBarButton;
	}

	/**
	 * 选中一个文件进行操作, 为工具栏的 '浏览' 按钮设置动作
	 * 
	 * @return
	 */
	private Action createBrowseAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				@SuppressWarnings("unused")
				String selectedFilepath = null;
				fileChooser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setDialogTitle("选择目标文件(文件夹)");

				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {
					selectedFilepath = fileChooser.getSelectedFile()
							.getAbsolutePath();
				} else {
					return;
				}

				System.out.println(((ControllPanel) mainPane.controllPane)
						.getController().getArguments().toString());
			}
		};

		return a;
	}

	/**
	 * 为工具栏的 '清空' 按钮设置动作
	 * 
	 * @return
	 */
	private Action createClearAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取消息面板的控制器
				MessagePanelController mpc = mainPane.messagePane.getController();

				// 清除面板内容
				mpc.clear();
			}
		};

		return a;
	}

	/**
	 * 为工具栏的 '导出' 按钮设置动作
	 * 
	 * @return
	 */
	private Action createExportAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String selectedFilePath = null;

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("导出");

				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {
					selectedFilePath = fileChooser.getSelectedFile()
							.getAbsolutePath();
				} else {
					return;
				}

				MessagePanelController mpc = mainPane.messagePane.getController();

				if (selectedFilePath != null) {
					mpc.export(selectedFilePath);
				}
			}
		};

		return a;
	}

	/**
	 * 为工具栏的 '停止' 按钮设置动作 (待完成)
	 * 
	 * @return
	 */
	private Action createStopAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Stop the proceed
			}

		};

		return a;
	}
}
