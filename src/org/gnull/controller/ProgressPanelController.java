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
	private ActionListener progresslistener;
	private ActionListener timerlistener;
	private MD5Controller md5;
	private double md5byte;
	private double md5currentbyte;
	private int progress;
	private boolean flag = true;
	
	private Thread Md5thread;
	private Thread Md5Listener;
	
	
	public ProgressPanelController(JProgressBar progressbar, JButton button) {
		setJProgressBar(progressbar);
		setJButton(button);
		md5 = new MD5Controller();
		
		Md5thread = new Thread() {
			public void run() {
				System.out.println("Md5 Start!");
				if (flag == false) {
					md5.doMD5(new File("D:\\P1010489.JPG"), true);
					flag = true;
				}				
				System.out.println("Md5 Complete!");
			}
		};
		
		Md5Listener = new Thread() {
			public void run() {
				System.out.println("Md5Listen Start!");
				while (true) {
					System.out.println(md5.TOTAL_BYTE+ "," + md5.ACTUAL_BYTE);
					if (flag == true) {
						if (md5byte == -1) {
							flag = false;
							continue;
						}
						progress = (int) (((double) md5.ACTUAL_BYTE / md5.TOTAL_BYTE) * 100);
						jbutton.setEnabled(false);
						jprogressbar.setValue(progress);
						if (md5.TOTAL_BYTE == md5.ACTUAL_BYTE) break;
					}
				}
				jbutton.setEnabled(true);
				System.out.println("Md5Listen Complete!");
				
			}
		};
		
		
		progresslistener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Md5Listener.start();
				Md5thread.start();
			}
			
		};
		button.addActionListener(progresslistener);
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
