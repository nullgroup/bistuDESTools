package org.gnull.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.gnull.panel.LoginFrame;

public class LoginFrameController {

	public LoginFrameController() {

	}

	public Action createDefaultLoginAction(LoginFrame loginFrame,
			JTextField userIdentifier) {
		return new DefaultLoginAction(loginFrame, userIdentifier);
	}
}

class DefaultLoginAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private JFrame frame;
	private JTextField textField;

	public DefaultLoginAction(JFrame frame, JTextField textField) {
		this.frame = frame;
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userText = textField.getText();

		if (userText.equals("") || userText == "") {
			JOptionPane.showMessageDialog(null, "输入信息不能为空", "信息",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
