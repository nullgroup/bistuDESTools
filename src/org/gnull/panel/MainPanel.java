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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import org.gnull.controller.FontStyleController;
import org.gnull.controller.MainFrameController;
import org.gnull.controller.MessagePanelController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MainPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	public MessagePanel messagePane;
	public ProgressPanel progressPane;
	public ControllPanel controllPane;
	public JToolBar toolBar;
	
	private MainFrameController controller;

	public MainFrameController getController() {
		return controller;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPanel frame = new MainPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainPanel() {
		setLookAndFeel();
		createMainPanel();
	}

	private void createMainPanel() {
		controller = new MainFrameController();
		
		JMenuBar bar = createMenuBar("MainFrame.MenuBar");
		setJMenuBar(bar);
		
		toolBar = createToolBar("工具栏", "MainFrame.ToolBar", JToolBar.HORIZONTAL, false);
		controllPane = new ControllPanel();
		progressPane = new ProgressPanel();
		messagePane = progressPane.messagePane;

		add(toolBar, BorderLayout.NORTH);
		add(controllPane, BorderLayout.EAST);
		add(messagePane, BorderLayout.CENTER);
		add(progressPane, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 550, 580);
		setVisible(true);
	}

	public static void setLookAndFeel() {
		try {
			// Set Graphics UI
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set Global Font
		Font globalFont = new Font("微软雅黑", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
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

	private JToolBar createToolBar(String name, String accessiblename, int orientation, boolean isFloatable) {
		JToolBar bar = new JToolBar(name, orientation);
		bar.setFloatable(isFloatable);
		bar.getAccessibleContext().setAccessibleName(accessiblename);

		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"浏览", createBrowseAction());
		
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"清空", createClearAction());
		
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"导出", createExportAction());
		
		bar.addSeparator();
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"停止", createStopAction());
		bar.addSeparator();

		return bar;
	}

	private JButton createToolBarButton(JToolBar bar, String text, Icon icon,
			String accessibleDescription, Action action) {
		JButton btn = (JButton) bar.add(action);

		btn.setText(text);
		btn.setIcon(icon);
		btn.setToolTipText(accessibleDescription);
		btn.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		return btn;
	}
	
	private Action createBrowseAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				@SuppressWarnings("unused")
				String selectedFilepath = null;	
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setDialogTitle("选择目标文件(文件夹)");
				
				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {
					selectedFilepath = fileChooser.getSelectedFile()
							.getAbsolutePath();
				} else {
					return;
				}
				
				System.out.println(((ControllPanel) controllPane).getController().getArguments().toString());
			}
		};
		
		return a;
	}

	private Action createClearAction() {
		Action a = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MessagePanelController mpc = messagePane.getController();
				mpc.clear();
			}
		};
		
		return a;
	}
	
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

				MessagePanelController mpc = messagePane.getController();	
				if (selectedFilePath != null) {
					mpc.export(selectedFilePath);
				}
			}		
		};
		
		return a;
	}
	
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
