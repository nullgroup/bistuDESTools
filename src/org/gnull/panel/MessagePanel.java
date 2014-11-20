package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultStyledDocument;

import org.gnull.controller.FontStyleController;
import org.gnull.controller.MessagePanelController;
import org.gnull.message.MessagePacket;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextPane msgTextPane;
	private MessagePanelController mpc;
	
	private int fontSize = 12;
	
	public JTextPane getTextPane() {
		return msgTextPane;
	}
	
	public MessagePanelController getMessagePanelController() {
		return mpc;
	}

	public MessagePanel() {
		setLookAndFeel();
		createMessagePanel();
	}

	private void setLookAndFeel() {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("Button.font", new Font("微软雅黑", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createMessagePanel() {
		setLayout(new BorderLayout(0, 0));
		setAlignmentX(LEFT_ALIGNMENT);
		
		msgTextPane = new JTextPane();
		msgTextPane.setEditable(false);
		msgTextPane.setDocument(new DefaultStyledDocument());
		mpc = new MessagePanelController(msgTextPane);
		FontStyleController.setFontFamily(msgTextPane, "微软雅黑");
		
		JPanel btnPanel = new JPanel(new GridLayout());

		JButton btn1 = new JButton();
		btn1.setText("增加普通文本");
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.insert(new MessagePacket());
			}
			
		});
		
		JButton btn2 = new JButton();
		btn2.setText("增加红色文本");
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.insertColored(new MessagePacket(), Color.RED, true);
			}
			
		});
		
		JButton btn3 = new JButton();
		btn3.setText("增大字体");
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FontStyleController.setFontSize(msgTextPane, ++fontSize);
			}
			
		});

		JButton btn4 = new JButton();
		btn4.setText("减小字体");
		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FontStyleController.setFontSize(msgTextPane, --fontSize);
			}
			
		});

		JButton btn5 = new JButton();
		btn5.setText("清空");
		btn5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.clear();
			}
			
		});
		
		JButton btn6 = new JButton();
		btn6.setText("打印");
		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mpc.export("d:\\");
			}
			
		});

		btnPanel.add(btn1);
		btnPanel.add(btn2);
		btnPanel.add(btn3);
		btnPanel.add(btn4);
		btnPanel.add(btn5);
		btnPanel.add(btn6);
		
		add(new JScrollPane(msgTextPane), BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		MessagePanel p = new MessagePanel();
		
		f.getContentPane().add(p);
		f.pack();
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
