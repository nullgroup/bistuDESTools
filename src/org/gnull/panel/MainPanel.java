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
		
		toolBar = createToolBar("������", "MainFrame.ToolBar", JToolBar.HORIZONTAL, false);
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
		Font globalFont = new Font("΢���ź�", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
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

	private JToolBar createToolBar(String name, String accessiblename, int orientation, boolean isFloatable) {
		JToolBar bar = new JToolBar(name, orientation);
		bar.setFloatable(isFloatable);
		bar.getAccessibleContext().setAccessibleName(accessiblename);

		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"���", createBrowseAction());
		
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"���", createClearAction());
		
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"����", createExportAction());
		
		bar.addSeparator();
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"ֹͣ", createStopAction());
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
				fileChooser.setDialogTitle("ѡ��Ŀ���ļ�(�ļ���)");
				
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
				fileChooser.setDialogTitle("����");
				
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
