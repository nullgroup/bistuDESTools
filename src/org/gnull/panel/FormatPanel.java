package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.gnull.controller.FontStyleController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.JButton;

public class FormatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();

		f.getContentPane().add(new FormatPanel());
		f.setSize(250, 330);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public FormatPanel() {
		setLookAndFeel();
		initContentPane();
	}

	private void initContentPane() {
		setPreferredSize(new Dimension(250, 330));
		setLayout(new GridLayout(6, 2, 0, 0));
		
		JCheckBox proMode = new JCheckBox("严格模式");
		add(proMode);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("MD5");
		add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("SHA-1");
		add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("CRC32");
		add(chckbxNewCheckBox_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		add(chckbxNewCheckBox);
		
		JButton button = new JButton("选择文件");
		add(button);
	}

	private void setLookAndFeel() {
		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.osLookAndFeelDecorated;
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			System.out.println("Can't not setGraphicUI (at "
					+ this.getClass().getName() + "): " + e.getMessage());
		}
		FontStyleController.setGlobalFont(new Font("微软雅黑", Font.PLAIN, 12));
		TitledBorder border = new TitledBorder(null, "参数选择",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		setBorder(border);
	}
}
