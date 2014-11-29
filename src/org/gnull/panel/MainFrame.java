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
			// ����UI����
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ����ȫ������
		Font globalFont = new Font("΢���ź�", Font.PLAIN, 12);
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

		tabbedPane.add("�ļ��ӽ���", mainPane);
		tabbedPane.add("��ά��ӽ���", qrPane);
		

		// �����˵���
		JMenuBar bar = createMenuBar("MainFrame.MenuBar");

		// ����������
		JToolBar toolBar = createToolBar("������", "MainFrame.ToolBar",
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
	 * �����˵���
	 * 
	 * @param accessibleName
	 * @return
	 */
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

	/**
	 * ����˵���
	 * 
	 * @param menu
	 *            �������˵���
	 * @param label
	 *            �˵��������
	 * @param mnemonic
	 *            �˵����ݼ�
	 * @param accessibleDescription
	 *            �˵��������
	 * @param action
	 *            �˵���Ķ���
	 * @return
	 */
	private JMenuItem createMenuItem(JMenu menu, String label, int mnemonic,
			String accessibleDescription, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());

		// ���ò˵����ݼ�
		mi.setMnemonic(mnemonic);
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		// ���ò˵����
		mi.addActionListener(action);

		return mi;
	}

	/**
	 * ����һ��������
	 *
	 * @param name
	 *            ����������
	 * @param accessiblename
	 *            ������������
	 * @param orientation
	 *            �������ķ���(ͨ����HORIZONTAL ˮƽ����)
	 * @param isFloatable
	 *            �������Ƿ񸡶�
	 * @return
	 */
	private JToolBar createToolBar(String name, String accessiblename,
			int orientation, boolean isFloatable) {

		JToolBar bar = new JToolBar(name, orientation);

		bar.setFloatable(isFloatable);
		bar.getAccessibleContext().setAccessibleName(accessiblename);

		// ��������ť��������
		createToolBarButton(bar, null, new ImageIcon("res/browse/browse.png"),
				"���", createBrowseAction());

		// �����հ�ť��������
		createToolBarButton(bar, null, new ImageIcon("res/clear/clear.png"),
				"���", createClearAction());

		// ��ӵ�����ť��������
		createToolBarButton(bar, null, new ImageIcon("res/export/export.png"),
				"����", createExportAction());

		bar.addSeparator();

		// ���ֹͣ��ť��������
		createToolBarButton(bar, null, new ImageIcon("res/stop/stop.png"),
				"ֹͣ", createStopAction());

		bar.addSeparator();

		return bar;
	}

	/**
	 * ����һ����������ť������Ӱ�ť����������
	 *
	 * @param toolBar
	 *            ������
	 * @param text
	 *            ��������ť������
	 * @param icon
	 *            ��������ť��ͼ��
	 * @param accessibleDescription
	 *            ��������ť������
	 * @param action
	 *            ��������ť�Ķ���
	 * @return
	 */
	private JButton createToolBarButton(JToolBar toolBar, String text,
			Icon icon, String accessibleDescription, Action action) {
		// ���찴ť�����
		JButton toolBarButton = (JButton) toolBar.add(action);

		// ���ð�ť����
		toolBarButton.setText(text);

		// ���ð�ťͼ��
		toolBarButton.setIcon(icon);

		// ���ð�ť��ʾ����
		toolBarButton.setToolTipText(accessibleDescription);

		// ���ð�ť����
		toolBarButton.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);

		return toolBarButton;
	}

	/**
	 * ѡ��һ���ļ����в���, Ϊ�������� '���' ��ť���ö���
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
				fileChooser.setDialogTitle("ѡ��Ŀ���ļ�(�ļ���)");

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
	 * Ϊ�������� '���' ��ť���ö���
	 * 
	 * @return
	 */
	private Action createClearAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ȡ��Ϣ���Ŀ�����
				MessagePanelController mpc = mainPane.messagePane.getController();

				// ����������
				mpc.clear();
			}
		};

		return a;
	}

	/**
	 * Ϊ�������� '����' ��ť���ö���
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
				fileChooser.setDialogTitle("����");

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
	 * Ϊ�������� 'ֹͣ' ��ť���ö��� (�����)
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
