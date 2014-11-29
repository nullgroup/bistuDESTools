package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gnull.util.Constants;
import org.gnull.util.QRCodeUtil;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

/**
 * @author ������
 *
 */
public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int curVersion = 7; // Ĭ�ϰ汾 = 7

	private int curSize = 225; // Ĭ�ϳߴ� = 135px

	private QRCodeUtil qrUtil;

	SouthPanel pane;

	QRImagePanel qrCodePane;

	/** ������������������ */
	JComboBox<String> comboBoxErrorCorrect;

	/** ���á��Զ������汾���ĸ�ѡ�� */
	JCheckBox checkBoxAutoVerison;

	/** ��ά�롮�汾���������� */
	JComboBox<String> comboBoxVersion;

	/** ����ͼ���С */
	JSpinner spinnerSize;

	/** ���ñ���汾 */
	JComboBox<String> checkBoxEncodeType;

	/** ���� ���ɶ�ά�� */
	Action generateQrImage;

	JLabel lblSize;

	ChangeListener autoResizeListener;

	JButton generateQrcodeButton;

	/**
	 * @param pane
	 */
	public NorthPanel(SouthPanel pane) {

		this.pane = pane;

		setLayout(new BorderLayout(0, 0));
		qrUtil = new QRCodeUtil();

		// ������ʾ��ά��ͼƬ�����
		JPanel qrImagePane = createQrImagePanel(new BorderLayout(0, 0),
				new Dimension(260, 200));

		// �����������������
		JPanel paramPane = createParamPanel(new GridLayout(4, 1, 0, 0), null);

		add(new JScrollPane(qrImagePane), BorderLayout.WEST);
		add(new JScrollPane(paramPane), BorderLayout.CENTER);
	}

	/**
	 * ������ʾ��ά��ͼƬ�����
	 * 
	 * @param layout
	 *            Ĭ�ϲ���
	 * @param d
	 *            �ߴ���ѡ��
	 * @return
	 */
	private JPanel createParamPanel(LayoutManager layout, Dimension d) {
		JPanel pane = new JPanel(layout);

		// �����������
		JPanel fixPane = createBorderPanel(pane, new GridLayout(2, 1), "��������");
		pane.add(fixPane);

		Dimension dim = new Dimension(120, 30);

		comboBoxErrorCorrect = new JComboBox<String>();
		comboBoxErrorCorrect.setModel(new DefaultComboBoxModel<String>(
				Constants.ERROR_CORRECT));
		comboBoxErrorCorrect.setPreferredSize(dim);
		fixPane.add(comboBoxErrorCorrect);

		// �汾�������
		JPanel versionPane = createBorderPanel(pane, new GridLayout(2, 2), "�汾");
		pane.add(versionPane);

		comboBoxVersion = new JComboBox<String>(
				new DefaultComboBoxModel<String>(Constants.QRCODE_VERSION));
		comboBoxVersion.setPreferredSize(dim);
		comboBoxVersion.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (spinnerSize != null) {
					String versionStr = (String) comboBoxVersion
							.getSelectedItem();
					curVersion = Integer.parseInt(versionStr);

					String curSizeStr = lblSize.getText().substring(6);
					curSize = Integer.parseInt(curSizeStr.split("x")[0].trim());

					while ((21 + (curVersion - 1) * 4) > curSize) {
						curSize += 4;
					}
					lblSize.setText("ͼƬ����: " + curSize + " x " + curSize);
				}
			}

		});
		comboBoxVersion.setSelectedItem("7");
		versionPane.add(comboBoxVersion);

		checkBoxAutoVerison = new JCheckBox("�Զ�����");
		autoResizeListener = addAutoSize();

		checkBoxAutoVerison.setPreferredSize(dim);
		checkBoxAutoVerison.addChangeListener(autoResizeListener);
		versionPane.add(checkBoxAutoVerison);

		// ����ͼƬ�ߴ����
		JPanel sizePane = createBorderPanel(pane, new GridLayout(2, 1), "�ߴ�");
		pane.add(sizePane);

		lblSize = new JLabel("ͼƬ����: 225 x 225"); // <-

		spinnerSize = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		spinnerSize.setPreferredSize(dim);
		spinnerSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (curVersion != 0) {
					curSize = (21 + (curVersion - 1) * 4)
							* (int) spinnerSize.getValue() * 5;
				} else {
					curSize = 200 * (int) spinnerSize.getValue();
				}

				lblSize.setText("ͼƬ����: " + curSize + " x " + curSize);
			}

		});
		sizePane.add(spinnerSize);
		sizePane.add(lblSize);

		// ���ñ���ģʽ���
		JPanel encodePane = createBorderPanel(pane, new GridLayout(2, 1),
				"����ģʽ");
		pane.add(encodePane);

		checkBoxEncodeType = new JComboBox<String>(
				new DefaultComboBoxModel<String>(Constants.ENCODE));
		checkBoxEncodeType.setPreferredSize(dim);
		encodePane.add(checkBoxEncodeType);

		return pane;
	}

	/**
	 * 
	 * @param parent
	 * @param layout
	 * @param title
	 * @return
	 */
	private JPanel createBorderPanel(JPanel parent, LayoutManager layout,
			String title) {
		JPanel pane = (JPanel) parent.add(new JPanel(layout));

		pane.setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		return pane;
	}

	/**
	 * @param layout
	 * @param d
	 * @return
	 */
	private JPanel createQrImagePanel(LayoutManager layout, Dimension d) {
		JPanel pane = new JPanel(layout);

		pane.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "��ά��ͼƬ",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		createGenerateButton(pane, "���ɶ�ά��", BorderLayout.SOUTH);
		qrCodePane = new QRImagePanel(qrUtil);

		pane.add(qrCodePane, BorderLayout.CENTER);
		pane.setPreferredSize(d);

		return pane;
	}

	/**
	 * @param parent
	 * @param text
	 * @param orient
	 * @return
	 */
	private JButton createGenerateButton(JPanel parent, String text,
			String orient) {
		generateQrcodeButton = new JButton();
		Font font = generateQrcodeButton.getFont();

		generateQrImage = createGenerateAction();

		generateQrcodeButton.setAction(generateQrImage);
		generateQrcodeButton.setText(text);
		generateQrcodeButton.setFont(new Font(font.getName(), Font.BOLD, font
				.getSize()));
		generateQrcodeButton.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));

		parent.add(generateQrcodeButton, orient);

		return generateQrcodeButton;
	}

	/**
	 * �����������Ϣ���ɶ�ά��ͼƬ
	 * 
	 * @return
	 */
	private Action createGenerateAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage bi = null;

				boolean flag = true;
				while (flag) {
					try {
						flag = false;

						String errorCorrect = (String) comboBoxErrorCorrect
								.getSelectedItem();
						String encodeMode = (String) checkBoxEncodeType
								.getSelectedItem();

						String content = pane.getContentByTabbedIndex();

						if (content == null) {
							JOptionPane.showMessageDialog(NorthPanel.this,
									"������ȷ����Կ����", "��ʾ",
									JOptionPane.WARNING_MESSAGE);
							return;
						} else if (content.equals("")) {
							qrCodePane.clearPanel();
						}

						qrUtil.setErrorCorrect(errorCorrect.charAt(0));
						qrUtil.setEncodeMode(encodeMode.charAt(0));
						qrUtil.setVersion(curVersion);

						bi = qrUtil.createImage(content, curSize, curSize);

						qrCodePane.setShowImage(bi);
					} catch (Exception e2) {
						// ���������ԭ�������ͼƬ�ߴ�̫��
						if (e2 instanceof ArrayIndexOutOfBoundsException) {
							flag = true;
							int i = comboBoxVersion.getSelectedIndex();
							if (i < 40) {
								comboBoxVersion.setSelectedIndex(i + 1);
							}
						}
					}
				}
			}
		};

		return a;
	}

	/**
	 * @return
	 */
	private ChangeListener addAutoSize() {
		ChangeListener c = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (checkBoxAutoVerison.isSelected()) {
					comboBoxVersion.setEnabled(false);
					curVersion = 0;
					curSize = 200 * (int) spinnerSize.getValue();
				} else {
					comboBoxVersion.setEnabled(true);
					String version = (String) comboBoxVersion.getSelectedItem();
					int v = Integer.parseInt(version);
					curVersion = v;
					curSize = (21 + (curVersion - 1) * 4)
							* (int) spinnerSize.getValue() * 5;
				}

				lblSize.setText("ͼƬ����: " + curSize + " x " + curSize);
			}

		};

		return c;
	}

	/**
	 * Main method for test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("NorthPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new NorthPanel(
						new SouthPanel());
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 580);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
