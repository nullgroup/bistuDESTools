package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SouthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JTextField tfName;

	JTextField textField_1;

	JTextField tfPhone1;

	JTextField tfPhone2;

	JTextField tfEmail1;

	JTextField tfEmail2;

	JTextField tfRemark;

	JPasswordField pswfKey;

	JTextField tfSiteName;

	JTextField tfUrl;

	JTextField tfMailto;

	JTextField tfSubject;

	JTextArea tfBody;

	public SouthPanel() {
		createSouthPane();
	}

	private void createSouthPane() {
		setLayout(new FlowLayout());

		JTabbedPane southTabPane = new JTabbedPane(JTabbedPane.TOP);
		southTabPane.setPreferredSize(new Dimension(500, 260));

		Dimension dLabel = new Dimension(60, 25);
		Dimension dTextField = new Dimension(150, 25);

		JPanel telPane = createTelephonePane(new GridLayout(7, 1, 0, 0),
				dLabel, dTextField);
		southTabPane.addTab("电话簿信息", null, telPane, null);

		JPanel emailPane = createEmailPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("电子邮件", null, emailPane, null);

		JPanel webPane = createWebPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("网页信息", null, webPane, null);

		JPanel textPane = createTextPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("文本信息", null, textPane, null);
		
		add(southTabPane, BorderLayout.CENTER);
	}

	private JPanel createTextPane(LayoutManager layout, Dimension dLabel,
			Dimension dTextField) {
		JPanel pane = new JPanel(layout);

		JTextPane msgPane = new JTextPane();
		pane.add(msgPane, BorderLayout.CENTER);

		JPanel panel_13 = new JPanel();
		pane.add(panel_13, BorderLayout.SOUTH);

		JLabel lblEncrypt = new JLabel("密码");
		panel_13.add(lblEncrypt);

		pswfKey = new JPasswordField(10);
		panel_13.add(pswfKey);

		JCheckBox chckbxIsEncrypt = new JCheckBox("加密");
		panel_13.add(chckbxIsEncrypt);

		return pane;
	}

	private JPanel createWebPane(LayoutManager layout, Dimension dL,
			Dimension dTF) {
		JPanel pane = new JPanel(layout);

		JPanel nPane = new JPanel(new GridLayout(2, 1, 0, 0));
		pane.add(nPane, BorderLayout.NORTH);

		FlowLayout flowLayout = new FlowLayout();

		JPanel p1 = new JPanel(flowLayout);
		JLabel lblSiteName = addLabel(p1 , "网站名称", "", dL);
		tfSiteName = addTextField(p1, lblSiteName.getText(), 35, dTF);
		nPane.add(p1);

		JPanel p2 = new JPanel(flowLayout);
		JLabel lblUrl = addLabel(p2 , "URL", "", dL);
		tfUrl = addTextField(p2, lblUrl.getText(), 35, dTF);
		nPane.add(p2);

		return pane;
	}

	private JPanel createEmailPane(LayoutManager layout, Dimension dL,
			Dimension dTF) {
		JPanel pane = new JPanel(layout);

		JPanel nPane = new JPanel(new GridLayout(3, 1, 0, 0));
		pane.add(nPane, BorderLayout.NORTH);

		FlowLayout flowLayout = new FlowLayout();

		JPanel p1 = new JPanel(flowLayout);
		JLabel lblMailto = addLabel(p1 , "地址", "", dL);
		tfMailto = addTextField(p1, lblMailto.getText(), 35, dTF);
		nPane.add(p1);

		JPanel p2 = new JPanel(flowLayout);
		JLabel lblSubject = addLabel(p2, "主题", "", dL);
		tfSubject = addTextField(p2, lblSubject.getText(), 35, dTF);
		nPane.add(p2);

		JPanel p3 = new JPanel(flowLayout);
		JLabel lblBody = addLabel(p3, "正文", "", dL);
		nPane.add(p3);

		tfBody = new JTextArea();
		tfBody.setLineWrap(true);
		tfBody.setPreferredSize(new Dimension(200, 25));
		tfBody.setColumns(35);
		
		Rectangle r = tfBody.getBounds();
		p3.add(tfBody);
		nPane.add(p3);

		return pane;
	}

	private JPanel createTelephonePane(LayoutManager layout, Dimension dL,
			Dimension dTF) {
		JPanel pane = new JPanel(layout);
		
		FlowLayout flowLayout = new FlowLayout();

		JPanel p1 = new JPanel(flowLayout);
		JLabel lblName = addLabel(p1, "姓名", "", dL);
		tfName = addTextField(p1, lblName.getText(), 35, dTF);
		pane.add(p1);

		JPanel p2 = new JPanel(flowLayout);
		JLabel lblNewLabel_2 = addLabel(p2, "注音", "", dL);
		textField_1 = addTextField(p2, lblNewLabel_2.getText(), 35, dTF);
		pane.add(p2);

		JPanel p3 = new JPanel(flowLayout);
		JLabel lblPhone1 = addLabel(p3, "电话号码1", "", dL);
		tfPhone1 = addTextField(p3, lblPhone1.getText(), 35, dTF);
		pane.add(p3);

		JPanel p4 = new JPanel(flowLayout);
		JLabel lblPhone2 = addLabel(p4, "电话号码2", "", dL);
		tfPhone2 = addTextField(p4, lblPhone2.getText(), 35, dTF);
		pane.add(p4);

		JPanel p5 = new JPanel(flowLayout);
		JLabel lblEmail1 = addLabel(p5, "邮件地址1", "", dL);
		tfEmail1 = addTextField(p5, lblEmail1.getText(), 35, dTF);
		pane.add(p5);

		JPanel p6 = new JPanel(flowLayout);
		JLabel lblEmail2 = addLabel(p6, "邮件地址2", "", dL);
		tfEmail2 = addTextField(p6, lblEmail2.getText(), 35, dTF);
		pane.add(p6);

		JPanel p7 = new JPanel(flowLayout);
		JLabel lblRemark = addLabel(p7, "备注", "", dL);
		tfRemark = addTextField(p7, lblRemark.getText(), 35, dTF);
		pane.add(p7);

		return pane;
	}

	private JLabel addLabel(JPanel p, String text, String accessibleName,
			Dimension d) {
		JLabel lab = (JLabel) p.add(new JLabel(text));

		lab.setPreferredSize(d);
		lab.getAccessibleContext().setAccessibleName(accessibleName);

		return lab;
	}

	private JTextField addTextField(JPanel p, String actionCommand,
			int columns, Dimension d) {
		JTextField field = (JTextField) p.add(new JTextField(columns));

		field.setPreferredSize(d);
		field.setActionCommand(actionCommand);

		return field;
	}
	
	public String getEmailAsString() {
		StringBuilder b = new StringBuilder(256);
		
		return b.toString();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("SouthPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new SouthPanel();
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 580);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
