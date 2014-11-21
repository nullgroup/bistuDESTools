package org.gnull.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.gnull.md5.MD5Controller;

public final class ProgressPanelController {
	
	private JProgressBar jprogressbar;
	private JButton jbutton;
	private Timer timer;
	private ActionListener progresslistener;
	private ActionListener timerlistener;
	private MD5Controller md5;
	
	private Thread Md5thread;
	private Thread Md5Listener;
	
	
	public ProgressPanelController(JProgressBar progressbar, JButton button) {
		setJProgressBar(progressbar);
		setJButton(button);
		
		Md5thread = new Thread() {
			public void run() {
				md5 = new MD5Controller();
				md5.doMD5(new File("\testfile.txt"), true);
			}
		};
		
		Md5Listener = new Thread() {
			public void run() {
				
			}
		};
		
		
		progresslistener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (jprogressbar.getValue() < jprogressbar.getMaximum()) {
					jprogressbar.setValue(jprogressbar.getValue() + 1);
					jbutton.setEnabled(false);
				}else {
					timer.stop();
					jbutton.setEnabled(true);
				}
			}
			
		};
		timerlistener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jprogressbar.setValue(0);
				timer.start();
			}
			
		};
		
		timer = new Timer(100, progresslistener);
		button.addActionListener(timerlistener);
	}
	
	private void setJProgressBar(JProgressBar jpb) {
		this.jprogressbar = jpb;
	}
	
	private void setJButton(JButton btn) {
		this.jbutton = btn;
	}
	
	public JProgressBar getJProgressBar() {
		return jprogressbar;
	}
	
	public void StartProgressBar() {
	}

}
