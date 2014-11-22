package org.gnull.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.gnull.md5.MD5Controller;
import org.gnull.message.MessagePacket;
import org.gnull.panel.MessagePanel;

public final class ProgressPanelController {
	
	private JProgressBar jprogressbar;
	private JButton jbutton;
	private ActionListener progresslistener;
	private ActionListener timerlistener;
	private MD5Controller md5;
	private double md5byte;
	private double md5currentbyte;
	private int progress;
	private boolean endflag = false;
	private String Md5Value;
	
	private Thread Md5thread ;
	private Thread Md5Listener;
	
	private MessagePanel mp;
	
	private MessagePacket packet = new MessagePacket();
	
	public ProgressPanelController(JProgressBar progressbar, JButton button, MessagePanel messagepanel) {
		
		setMessagePanel(messagepanel);
		setJProgressBar(progressbar);
		setJButton(button);
		
		progresslistener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				final File file = new File("D:\\桌面杂项\\支教\\P1010432.jpg");
				md5 = new MD5Controller(file);
				
				Md5thread = new Thread() {
					public void run() {
						System.out.println("Md5 Start!");
						Md5Value = md5.doMD5();
						System.out.println(Md5Value);
						System.out.println("Md5 Complete!");
					}
				};
				
				Md5Listener = new Thread() {
					public void run() {
						System.out.println("Md5Listen Start!");
						while (true) {
							System.out.println(md5.getTotal()+ "," + md5.getActual());
							if (md5byte == -1) continue;
							progress = (int) (((double) md5.getActual() / md5.getTotal()) * 100);
							jbutton.setEnabled(false);
							jprogressbar.setValue(progress);
							if (Math.abs(md5.getTotal() - md5.getActual()) <= 64) break;
						}
						jbutton.setEnabled(true);
						System.out.println("Md5Listen Complete!");
						
						packet.setMode("摘要");
						packet.setDate(new Date());
						packet.setFileName(file.getName());
						packet.setSize(file.length());
						packet.setLastModified(new Date());
						packet.setMd5Value(Md5Value);
						System.out.println(packet.toString());
						mp.getMessagePanelController().insert(packet.toString());
						
						
						
					}
				};
				
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
	
	public void setMessagePanel(MessagePanel messagepanel) {
		mp = messagepanel;
	}
	
	public JProgressBar getJProgressBar() {
		return jprogressbar;
	}
	
	public MessagePacket getMessagePacket() {
		return packet;
	}

}
