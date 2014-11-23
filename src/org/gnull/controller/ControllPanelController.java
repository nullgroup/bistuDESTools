package org.gnull.controller;

import java.util.HashMap;

import javax.swing.Action;
import javax.swing.text.JTextComponent;

import org.gnull.entity.ArgumentsSet;
import org.gnull.panel.ControllPanel;

public class ControllPanelController {
	
	@SuppressWarnings("unused")
	private ControllPanel ctrlPane;
	
	private ArgumentsSet set;

	private HashMap<Object, Action> actionsMapA;
	private HashMap<Object, Action> actionsMapB;
	private HashMap<Object, Action> actionsMapC;
	
	public ControllPanelController(ControllPanel ctrlPane) {
		this.ctrlPane = ctrlPane;
		set = new ArgumentsSet();
		actionsMapA = createActionTable(ctrlPane.tabPane.getKeyFieldA());
		actionsMapB = createActionTable(ctrlPane.tabPane.getKeyFieldB());
		actionsMapC = createActionTable(ctrlPane.tabPane.getHashField());
	}
	
	public ArgumentsSet getArguments() {
		return set;
	}
	
	private HashMap<Object, Action> createActionTable(
			JTextComponent textComponent) {
		HashMap<Object, Action> actions = new HashMap<Object, Action>();
		Action[] actionsArray = textComponent.getActions();
		
		for (int i = 0; i < actionsArray.length; i++) {
			Action a = actionsArray[i];
			actions.put(a.getValue(Action.NAME), a);
		}
		
		return actions;
	}
	
	public Action getActionByName(int index, String name) {	
		if (index == 0) {
			return actionsMapA.get(name);
		} else if (index == 1) {
			return actionsMapB.get(name);
		} else {
			return actionsMapC.get(name);
		}
	}
}
