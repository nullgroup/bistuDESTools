package org.gnull.controller;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.SwingWorker;

import org.gnull.entity.MessagePacket;
import org.gnull.md5.MD5Controller;
import org.gnull.panel.ProgressPanel;

public final class ProgressPanelController implements PropertyChangeListener {

	private MD5Controller md5Con;

	private ComputeTask computeTask;
	private ListenTask listenTask;

	private ProgressPanel panel;

	private MessagePacket packet = new MessagePacket();

	public ProgressPanelController(ProgressPanel panel) {

		this.panel = panel;
	}

	class ComputeTask extends SwingWorker<Void, Void> {

		private String result;

		public ComputeTask(int mode, String[] params) {

		}

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			File inputFile = new File("D:\\Application Setup\\cn_sql_server_2014_express_x64_exe_3949524.exe");
			
			if (!inputFile.exists()) {
				return null;
			}
			
			md5Con = new MD5Controller(inputFile);

			System.out.println("Md5 Start!");
			result = md5Con.doMD5();

			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			System.out.println("Md5 Complete: " + result);
		}
	}

	class ListenTask extends SwingWorker<Void, Void> {
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

	public void compute() {
		computeTask = new ComputeTask(0, null);
		listenTask = new ListenTask();
		listenTask.addPropertyChangeListener(this);

		computeTask.execute();
		listenTask.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress".equals(evt.getPropertyName())) {
			int i = (int) evt.getNewValue();
			panel.progressbar.setValue(i);
		}
	}

}
