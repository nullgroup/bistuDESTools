package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.gnull.controller.FontStyleController;
import org.gnull.controller.LoginFrameController;
import org.gnull.util.HTMLCreater;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private LoginFrameController lfc;
	private Action loginAction = null;
	
	public static void main(String[] args) {
		FontStyleController.setDefaultFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		LoginFrame f = new LoginFrame();
		f.setVisible(true);
	}

	public LoginFrame() {
		setLookAndFeel();
		initContentPane();
	}

	private void initContentPane() {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new FlowLayout());
		mainPane.setAlignmentX(CENTER_ALIGNMENT);
		
		lfc = new LoginFrameController();
		
		JTextField userIdentifier = new JTextField("input.identifier");
		userIdentifier.setPreferredSize(new Dimension(200, 30));
		
		String warningText = HTMLCreater.createHTMLText("loginframe.warningtext");
		JLabel warning = new JLabel(warningText);
		warning.setPreferredSize(new Dimension(250, 100));
		warning.setAutoscrolls(true);
		
		loginAction = lfc.createDefaultLoginAction(this, userIdentifier);

		JButton btnLogin = new JButton();
		btnLogin.setAction(loginAction);
		btnLogin.setText("login");

		mainPane.add(userIdentifier);
		mainPane.add(btnLogin);
		mainPane.add(warning);
		add(mainPane, BorderLayout.CENTER);
	}

	private void setLookAndFeel() {
		setGraphicUI();
		setTitle("login.title");
		setSize(new Dimension(300, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
	}

	private void setGraphicUI() {
		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.osLookAndFeelDecorated;
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			System.out.println("Can't not setGraphicUI (at "
					+ this.getClass().getName() + "): " + e.getMessage());
		}

		Font dafaultFont = FontStyleController.getDefaultFont();
		UIManager.put("Button.font", dafaultFont);
		UIManager.put("Label.font", dafaultFont);
		UIManager.put("TextField.font", dafaultFont);
	}
}
