package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.gnull.controller.ToolBarController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private MessagePanel messagePane;
	private ProgressPanel progressPane;
	private JPanel controllPane;
	private ControllTabbedPanel ctrlTabPane;
	private JToolBar toolBar;
	private JButton btnBrowse;
	private JButton btnClear;
	private JButton btnExport;
	private JButton btnStop;
	
	private ToolBarController tbc = new ToolBarController();

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
		controllPane = new JPanel(new BorderLayout(0, 0));
		getContentPane().add(controllPane, BorderLayout.EAST);
		
		messagePane = new MessagePanel();
		getContentPane().add(messagePane, BorderLayout.SOUTH);
		
		progressPane = new ProgressPanel();
		getContentPane().add(progressPane, BorderLayout.CENTER);
		
		getContentPane().add(messagePane, BorderLayout.CENTER);
		getContentPane().add(progressPane, BorderLayout.SOUTH);
		getContentPane().add(controllPane, BorderLayout.EAST);
		controllPane.setLayout(new BorderLayout(0, 0));
		
		ctrlTabPane = new ControllTabbedPanel();
		controllPane.add(ctrlTabPane, BorderLayout.NORTH);
		
		FormatPanel p = new FormatPanel();
		controllPane.add(p, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnBrowse = new JButton(new ImageIcon("res/browse/browse.png"));
		toolBar.add(btnBrowse);
		
		btnClear = new JButton(new ImageIcon("res/clear/clear.png"));
		toolBar.add(btnClear);
		
		btnExport = new JButton(new ImageIcon("res/export/export.png"));
		toolBar.add(btnExport);
		
		btnStop = new JButton(new ImageIcon("res/stop/stop.png"));
		toolBar.add(btnStop);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 600, 600);
		setVisible(true);
	}

	public static void setLookAndFeel() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("Button.font", new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
