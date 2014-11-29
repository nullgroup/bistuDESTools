package org.gnull.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.gnull.entity.ComputeTask;
import org.gnull.entity.ListenTask;
import org.gnull.entity.MessagePacket;
import org.gnull.panel.ProgressPanel;

public final class ProgressPanelController implements PropertyChangeListener {
	
	private ComputeTask computeTask;
	private ListenTask listenTask;

	private ProgressPanel panel;

	@SuppressWarnings("unused")
	private MessagePacket packet = new MessagePacket();

	public ProgressPanelController(ProgressPanel panel) {

		this.panel = panel;
	}

	public void compute() {
		if (computeTask != null && listenTask != null) {
			computeTask = new ComputeTask(null, 0, 0, null);
			listenTask = new ListenTask();
			listenTask.addPropertyChangeListener(this);

			computeTask.execute();
			listenTask.execute();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress".equals(evt.getPropertyName())) {
			int i = (int) evt.getNewValue();
			panel.progressBar.setValue(i);
		}
	}

}
