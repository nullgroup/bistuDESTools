package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;

import org.gnull.controller.MainFrameController;

/**
 *
 * @author OSX
 */
public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** ��Ϣ��� */
	MessagePanel messagePane;

	/** ��������� */
	ProgressPanel progressPane;

	/** ������� */
	ControllPanel controllPane;

	private MainFrameController controller;

	public MainFrameController getController() {
		return controller;
	}
	
	/**
	 * 
	 */
	public MainPanel() {
		setLayout(new BorderLayout(0, 0));

		controller = new MainFrameController();

		// �������
		controllPane = new ControllPanel();
		// ���������
		progressPane = new ProgressPanel();
		// ��Ϣ���(���Խ��������)
		messagePane = progressPane.messagePane;

		add(controllPane, BorderLayout.EAST);
		add(messagePane, BorderLayout.CENTER);
		add(progressPane, BorderLayout.SOUTH);
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
}
