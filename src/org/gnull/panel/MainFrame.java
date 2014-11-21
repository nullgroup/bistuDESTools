package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel messagePane;
	private JPanel progressPane;
	private JPanel controllPane;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblSad;
	private JToolBar toolBar;
	private JButton btnBrowse;
	private JButton btnClear;
	private JButton btnExport;
	private JButton btnStop;

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
		controllPane = new JPanel();
		getContentPane().add(controllPane, BorderLayout.EAST);
		
		messagePane = new MessagePanel();
		getContentPane().add(messagePane, BorderLayout.SOUTH);
		
		progressPane = new ProgressPanel();
		getContentPane().add(progressPane, BorderLayout.CENTER);
		
		getContentPane().add(messagePane, BorderLayout.CENTER);
		getContentPane().add(progressPane, BorderLayout.SOUTH);
		getContentPane().add(controllPane, BorderLayout.EAST);
		controllPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		controllPane.add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("a", null, panel, null);
		
		lblSad = new JLabel("sadsdadasdas");
		panel.add(lblSad);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("b", null, panel_1, null);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("c", null, panel_2, null);
		
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnBrowse = new JButton(new ImageIcon("/"));
		toolBar.add(btnBrowse);
		
		btnClear = new JButton("New button");
		toolBar.add(btnClear);
		
		btnExport = new JButton("New button");
		toolBar.add(btnExport);
		
		btnStop = new JButton("New button");
		toolBar.add(btnStop);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 800, 400);
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
