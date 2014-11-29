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

	/** 消息面板 */
	MessagePanel messagePane;

	/** 进度条面板 */
	ProgressPanel progressPane;

	/** 控制面板 */
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

		// 控制面板
		controllPane = new ControllPanel();
		// 进度条面板
		progressPane = new ProgressPanel();
		// 消息面板(来自进度条面板)
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
