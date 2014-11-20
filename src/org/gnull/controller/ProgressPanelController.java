package org.gnull.controller;

import javax.swing.JProgressBar;

public final class ProgressPanelController {
	
	private JProgressBar jprogressbar;
	
	public ProgressPanelController(JProgressBar progressbar) {
		setJProgressBar(progressbar);
	}
	
	private void setJProgressBar(JProgressBar jpb) {
		jprogressbar = jpb;
	}
	
	public JProgressBar getJProgressBar() {
		return jprogressbar;
	}
	
	public void StartProgressBar() {
		thread.start();
	}

	private Thread thread = new Thread(new Runnable() {
		public void run() {
			for (int i = 1;i <= jprogressbar.getMaximum();i++) {
				try {
					Thread.sleep(100);
					jprogressbar.setValue(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});

}
