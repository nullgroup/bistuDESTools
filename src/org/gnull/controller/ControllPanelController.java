package org.gnull.controller;

import org.gnull.entity.ArgumentsSet;
import org.gnull.panel.ControllPanel;

public class ControllPanelController {
	
	@SuppressWarnings("unused")
	private ControllPanel ctrlPane;
	private ArgumentsSet set;
	
	public ControllPanelController(ControllPanel ctrlPane) {
		this.ctrlPane = ctrlPane;
		set = new ArgumentsSet();
	}
	
	public ArgumentsSet getArguments() {
		return set;
	}
}
