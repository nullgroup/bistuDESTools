package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class MessageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setLookAndFeel();
					MessageFrame frame = new MessageFrame();
					frame.add(new MessagePanel(), BorderLayout.CENTER);
					//frame.add(new ProgressPanel(), BorderLayout.SOUTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MessageFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		addButton(this);
	}
	

	private void addButton(JFrame messageFrame) {
		JButton btn = new JButton("这是一个用来测试的长文本按钮");
		BEButtonUI btnUI = new BEButtonUI();
		btnUI.setNormalColor(BEButtonUI.NormalColor.normal);
		btn.setUI(btnUI);
		btn.setForeground(Color.white);
		messageFrame.add(btn, BorderLayout.SOUTH);
		
	}

	public static void setLookAndFeel() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("Button.font", new Font("微软雅黑", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
