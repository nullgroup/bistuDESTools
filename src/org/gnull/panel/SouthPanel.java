package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.gnull.des.DESUtil;

public class SouthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static public final int PLAIN_TEXT_OPTION = 0;
	static public final int WEB_PAGE_OPTION = 1;
	static public final int EMAIL_OPTION = 2;
	static public final int CONTACTS_OPTION = 3;

	static public final char SEPATATOR = ' ';

	JTabbedPane southTabPane;

	JTextField tfName1;

	JTextField tfName2;

	JTextField tfPhone1;

	JTextField tfPhone2;

	JTextField tfEmail1;

	JTextField tfEmail2;

	JTextField tfRemark;

	JPasswordField pswfKey;

	JTextField tfUrl;

	JTextField tfMailto;

	JTextField tfSubject;

	JTextArea tfBody;

	JTextPane msgPane;

	public SouthPanel() {
		createSouthPane();
	}

	public String getContentByTabbedIndex() {
		String content = null;

		int i = southTabPane.getSelectedIndex();

		switch (i) {
		case PLAIN_TEXT_OPTION:
			content = getPlainTextContent();
			break;
		case WEB_PAGE_OPTION:
			content = getWebPageContent();
			break;
		case EMAIL_OPTION:
			content = getEmailContent();
			break;
		case CONTACTS_OPTION:
			content = getoContactsContent();
			break;
		}

		return content;
	}

	private String getPlainTextContent() {
		StringBuilder b = new StringBuilder(128);
		
		String key = new String(pswfKey.getPassword());
		
		if (!key.equals("") && !msgPane.getText().equals("")) {
			try {
				b.append(DESUtil.encrypt(msgPane.getText(), key));
			} catch (Exception e) {
				if (e instanceof java.security.InvalidKeyException) {
					return null;
				}
			}
		} else if (!msgPane.getText().equals("")) {
			b.append(msgPane.getText());
		}

		return b.toString();
	}

	private String getWebPageContent() {
		StringBuilder b = new StringBuilder(128);

		b.append(tfUrl.getText());

		return b.toString();
	}

	private String getEmailContent() {
		StringBuilder b = new StringBuilder(128);

		if (!tfMailto.getText().equals("") || !tfSubject.getText().equals("")
				|| !tfBody.getText().equals("")) {

			b.append("MAILTO:").append(tfMailto.getText()).append(SEPATATOR);

			b.append("SUBJECT:").append(tfSubject.getText()).append(SEPATATOR);

			b.append("BODY:").append(tfBody.getText());

		}

		return b.toString();
	}

	private String getoContactsContent() {
		StringBuilder b = new StringBuilder(128);

		if (!tfRemark.getText().equals("") || !tfName1.getText().equals("")
				|| !tfName2.getText().equals("")
				|| !tfPhone1.getText().equals("")
				|| !tfPhone2.getText().equals("")
				|| !tfEmail1.getText().equals("")
				|| !tfEmail2.getText().equals("")) {

			b.append("MEMORY:").append(tfRemark.getText()).append(SEPATATOR);

			b.append("NAME1:").append(tfName1.getText()).append(SEPATATOR);

			b.append("NAME2:").append(tfName2.getText()).append(SEPATATOR);

			b.append("TEL1:").append(tfPhone1.getText()).append(SEPATATOR);

			b.append("TEL2:").append(tfPhone2.getText()).append(SEPATATOR);

			b.append("MAIL1:").append(tfEmail1.getText()).append(SEPATATOR);

			b.append("MAIL2:").append(tfEmail2.getText());
		}

		return b.toString();

	}

	private void createSouthPane() {
		setLayout(new FlowLayout());

		southTabPane = new JTabbedPane(JTabbedPane.TOP);
		southTabPane.setPreferredSize(new Dimension(500, 260));

		Dimension dLabel = new Dimension(60, 25);
		Dimension dTextField = new Dimension(150, 25);

		JPanel textPane = createTextPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("文本信息", null, textPane, null);

		JPanel webPane = createWebPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("网页信息", null, webPane, null);

		JPanel emailPane = createEmailPane(new BorderLayout(0, 0), dLabel,
				dTextField);
		southTabPane.addTab("电子邮件", null, emailPane, null);

		JPanel telPane = createTelephonePane(new GridLayout(7, 1, 0, 0),
				dLabel, dTextField);
		southTabPane.addTab("电话簿信息", null, telPane, null);

		add(southTabPane, BorderLayout.CENTER);
	}

	private JPanel createTextPane(LayoutManager layout, Dimension dLabel,
			Dimension dTextField) {
		JPanel pane = new JPanel(layout);

		msgPane = new JTextPane();
		pane.add(msgPane, BorderLayout.CENTER);

		JPanel panel_13 = new JPanel();
		pane.add(panel_13, BorderLayout.SOUTH);

		JLabel lblEncrypt = new JLabel("密码");
		panel_13.add(lblEncrypt);

		pswfKey = new JPasswordField(10);
		panel_13.add(pswfKey);

		final JCheckBox chckbxIsEncrypt = new JCheckBox("加密");
		panel_13.add(chckbxIsEncrypt);

		pswfKey.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				chckbxIsEncrypt.setSelected(pswfKey.getPassword() != null
						&& pswfKey.getPassword().length != 0);
			}

		});

		return pane;
	}

	private JPanel createWebPane(LayoutManager layout, Dimension dL,
			Dimension dTF) {
		JPanel pane = new JPanel(layout);

		JPanel nPane = new JPanel(new GridLayout(1, 1, 0, 0));
		pane.add(nPane, BorderLayout.NORTH);

		FlowLayout flowLayout = new FlowLayout();

		JPanel p = new JPanel(flowLayout);
		JLabel lblUrl = addLabel(p, "URL", "", dL);
		tfUrl = addTextField(p, lblUrl.getText(), 35, dTF);
		nPane.add(p);

		return pane;
	}

	private JPanel createEmailPane(LayoutManager layout, Dimension dL,
			Dimension dTF) {
		JPanel pane = new JPanel(layout);

		JPanel nPane = new JPanel(new GridLayout(3, 1, 0, 0));
		pane.add(nPane, BorderLayout.NORTH);

		FlowLayout flowLayout = new FlowLayout();

		JPanel p1 = new JPanel(flowLayout);
		JLabel lblMailto = addLabel(p1, "地址", "", dL);
		tfMailto = addTextField(p1, lblMailto.getText(), 35, dTF);
		nPane.add(p1);

		JPanel p2 = new JPanel(flowLayout);
		JLabel lblSubject = addLabel(p2, "主题", "", dL);
		tfSubject = addTextField(p2, lblSubject.getText(), 35, dTF);
		nPane.add(p2);

		JPanel p3 = new JPanel(flowLayout);
		@SuppressWarnings("unused")
		JLabel lblBody = addLabel(p3, "正文", "", dL);
		nPane.add(p3);

		tfBody = new JTextArea();
		tfBody.setLineWrap(true);
		tfBody.setPreferredSize(new Dimension(200, 25));
		tfBody.setColumns(35);

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
		tfName1 = addTextField(p1, lblName.getText(), 35, dTF);
		pane.add(p1);

		JPanel p2 = new JPanel(flowLayout);
		JLabel lblNewLabel_2 = addLabel(p2, "注音", "", dL);
		tfName2 = addTextField(p2, lblNewLabel_2.getText(), 35, dTF);
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
