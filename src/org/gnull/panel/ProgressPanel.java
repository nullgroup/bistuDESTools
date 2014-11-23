package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.gnull.controller.ProgressPanelController;

public class ProgressPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MessagePanel messagePane;
	public JProgressBar progressbar;

	// tmp
	public JButton startButton;

	private ProgressPanelController controller;

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame f = new JFrame();
				ProgressPanel p = new ProgressPanel();

				f.getContentPane().add(p);
				f.pack();
				f.setSize(300, 100);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				f.pack();
				f.setVisible(true);
			}
		});
	}

	public ProgressPanelController getController() {
		return controller;
	}

	public ProgressPanel() {
		messagePane = new MessagePanel();
		createProgressPanel();
	}

	public void createProgressPanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(TOP_ALIGNMENT);

		controller = new ProgressPanelController(this);

		progressbar = createProgressBar(0, 100, JProgressBar.HORIZONTAL);
		progressbar.setStringPainted(true);

		startButton = createStartButton("ÔËÐÐ", "ProgressPanel.Start");

		add(progressbar, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
	}

	protected JProgressBar createProgressBar(int min, int max, int orient) {
		JProgressBar bar = new JProgressBar(orient);

		bar.setMaximum(max);
		bar.setMinimum(min);
		bar.setValue(0);

		return bar;
	}
	
	private JButton createStartButton(String text, String actionCommand) {
		JButton startButton = new JButton();
		
		Action a = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.compute();
			}
		};
		
		startButton.addActionListener(a);
		startButton.setText(text);
		startButton.setActionCommand(actionCommand);
		
		return startButton;
	}
}
