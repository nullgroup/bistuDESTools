package org.gnull.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class ControllTabbedPanelController {


	private JTextField keyFieldA;
	private JTextField keyFieldB;
	
	private JTextField enOutputPath;
	private JTextField deOutputPath;
	
	public ControllTabbedPanelController() {
	}
	
	public void setKeyFieldA(JTextField keyFieldA) {
		this.keyFieldA = keyFieldA;
	}

	public void setKeyFieldB(JTextField keyFieldB) {
		this.keyFieldB = keyFieldB;
	}

	public void setEnOutputPath(JTextField enOutputPath) {
		this.enOutputPath = enOutputPath;
	}

	public void setDeOutputPath(JTextField deOutputPath) {
		this.deOutputPath = deOutputPath;
	}
}
