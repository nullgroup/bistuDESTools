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

/**
 * @author OSX
 *
 */
public class ProgressPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MessagePanel messagePane;

	/**
	 * 
	 */
	public JProgressBar progressBar;

	// tmp
	public JButton startButton;

	/**
	 * 
	 */
	private ProgressPanelController controller;

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

		progressBar = createProgressBar(0, 100, JProgressBar.HORIZONTAL, true);

		startButton = createStartButton("ÔËÐÐ", "ProgressPanel.StartButton");

		add(progressBar, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
	}

	protected JProgressBar createProgressBar(int minValue, int maxValue, int orient,
			boolean stringPainted) {
		JProgressBar bar = new JProgressBar();

		bar.setMaximum(maxValue);
		bar.setMinimum(minValue);
		bar.setValue(maxValue - minValue);
		bar.setOrientation(orient);
		bar.setStringPainted(stringPainted);

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

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("ProgressPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new ProgressPanel();
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(300, 100);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
