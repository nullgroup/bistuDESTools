package org.gnull.entity;

import java.awt.Toolkit;

import javax.swing.SwingWorker;

public class ListenTask extends AbstractTask {
	/*
	 * Main task. Executed in background thread.
	 */
	@Override
	public Void doInBackground() {
		int progress = 0;
		setProgress(0);

		panel.startButton.setEnabled(false);

		System.out.println("Md5Listen Start!");
		
		while (true) {
			System.out.println(md5Con.getTotal() + "," + md5Con.getActual());
			
			if (md5Con.getTotal() == -1) {
				continue;
			}
			
			progress = (int) (((double) md5Con.getActual() / md5Con.getTotal()) * 100);
			
			setProgress(progress);
			
			if (md5Con.getTotal() == md5Con.getActual()) {
				break;
			}
		}
		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		System.out.println("Md5Listen Complete!");
		Toolkit.getDefaultToolkit().beep();
		panel.startButton.setEnabled(true);
		panel.setCursor(null);
		panel.messagePane.getController().insert(packet.toString());
	}
}