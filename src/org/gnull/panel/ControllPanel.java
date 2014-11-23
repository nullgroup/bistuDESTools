package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.gnull.controller.ControllPanelController;
import org.gnull.entity.ArgumentsSet;

public class ControllPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ControllTabbedPanel tabPane;

	public JButton btnStart;

	public Vector<JCheckBox> checkbox = new Vector<JCheckBox>();

	public ItemListener changeState;

	private ControllPanelController controller;

	public ControllPanelController getController() {
		return controller;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();

		f.getContentPane().add(new ControllPanel());
		f.setSize(250, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public ControllPanel() {
		createContentPane();
	}

	private void createContentPane() {
		setPreferredSize(new Dimension(250, 330));
		setLayout(new BorderLayout(0, 0));

		JPanel bottomPane = new JPanel(new GridLayout(6, 2, 0, 0));
		bottomPane.setBorder(createBorder("参数选择"));
		
		tabPane = new ControllTabbedPanel();
		controller = new ControllPanelController(this);

		changeState = createChangeStateListener();

		JCheckBox cbProMode = createArgumentCheckBox("严格加解密",
				"ControllPanel.Professional.Mode", "执行安全的加解密模式(耗时更长)",
				KeyEvent.VK_P, changeState);

		JCheckBox cbMd5 = createArgumentCheckBox("MD5", "ControllPanel.Md5",
				"生成目标文件的MD5值", KeyEvent.VK_M, changeState);

		JCheckBox cbSha1 = createArgumentCheckBox("SHA-1",
				"ControllPanel.Sha1", "生成目标文件的SHA-1值", KeyEvent.VK_S,
				changeState);

		JCheckBox cbCrc32 = createArgumentCheckBox("CRC32",
				"ControllPanel.Crc32", "生成目标文件的CRC32值", KeyEvent.VK_C,
				changeState);

		JCheckBox cbStub = createArgumentCheckBox("Stub", "ControllPanel.Stub",
				"生成目标文件的Stub值", KeyEvent.VK_A, changeState);

		btnStart = createBrowseFileButton("浏览文件", "ControllPanel.Select.InputFile");

		checkbox.add((JCheckBox) bottomPane.add(cbProMode));
		checkbox.add((JCheckBox) bottomPane.add(cbMd5));
		checkbox.add((JCheckBox) bottomPane.add(cbSha1));
		checkbox.add((JCheckBox) bottomPane.add(cbCrc32));
		checkbox.add((JCheckBox) bottomPane.add(cbStub));
		
		bottomPane.add(btnStart);

		add(tabPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
	}

	private JCheckBox createArgumentCheckBox(String text, String actionCommand,
			String toolTipText, int mnemonic, ItemListener itemListener) {
		JCheckBox box = new JCheckBox(text);

		box.setActionCommand(actionCommand);
		box.setToolTipText(toolTipText);
		box.setMnemonic(mnemonic);
		box.addItemListener(itemListener);

		return box;
	}

	private ItemListener createChangeStateListener() {
		ItemListener itemListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				ControllPanelController controller = getController();
				ArgumentsSet set = controller.getArguments();
				
				JCheckBox box = (JCheckBox) e.getSource();
				String actionCommand = box.getActionCommand();
				
				if (actionCommand.equals("ControllPanel.Professional.Mode")) {
					set.setProMode(box.isSelected());
				} else if (actionCommand.equals("ControllPanel.Md5")) {
					set.setMd5(box.isSelected());
				} else if (actionCommand.equals("ControllPanel.Sha1")) {
					set.setSha1(box.isSelected());
				} else if (actionCommand.equals("ControllPanel.Crc32")) {
					set.setCrc32(box.isSelected());
				}
			}
			
		};
		
		return itemListener;
	}

	private Border createBorder(String title) {
		TitledBorder border = new TitledBorder(null, title,
				TitledBorder.LEADING, TitledBorder.TOP, null, null);

		return border;
	}

	private JButton createBrowseFileButton(String text, String accessibleName) {
		JButton button = new JButton();

		Action a = createBrowseAction();
		button.setAction(a);
		button.getAccessibleContext().setAccessibleName(accessibleName);
		button.setText(text);

		return button;
	}

	private Action createBrowseAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		};

		return a;
	}
}
