package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.gnull.controller.MessagePanelController;
import org.gnull.controller.ProgressPanelController;
import org.gnull.md5.MD5Controller;

public class ProgressPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JProgressBar progressbar;
	private JButton button;
	private ProgressPanelController ppc;
	
	private Timer timer;
	
	private MD5Controller md5;
	
	public ProgressPanel() {
		createProgressPanel();
	}
	
	public void createProgressPanel() {
		
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(TOP_ALIGNMENT);
		
		progressbar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbar.setMaximum(20);
		progressbar.setMinimum(0);
		progressbar.setValue(0);
		
		button = new JButton("测试进度条按钮");
		ppc = new ProgressPanelController(progressbar, button);
		
		add(progressbar, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		ProgressPanel p = new ProgressPanel();
		
		f.getContentPane().add(p);
		f.pack();
		f.setSize(300, 100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
