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
 * @author 伍至煊
 *
 */
public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int curVersion = 7; // 默认版本 = 7

	private int curSize = 225; // 默认尺寸 = 135px

	private QRCodeUtil qrUtil;

	SouthPanel pane;

	QRImagePanel qrCodePane;

	/** ‘错误修正’下拉框 */
	JComboBox<String> comboBoxErrorCorrect;

	/** 设置‘自动调整版本’的复选框 */
	JCheckBox checkBoxAutoVerison;

	/** 二维码‘版本’的下拉框 */
	JComboBox<String> comboBoxVersion;

	/** 调整图像大小 */
	JSpinner spinnerSize;

	/** 设置编码版本 */
	JComboBox<String> checkBoxEncodeType;

	/** 动作 生成二维码 */
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

		// 创建显示二维码图片的面板
		JPanel qrImagePane = createQrImagePanel(new BorderLayout(0, 0),
				new Dimension(260, 200));

		// 创建调整参数的面板
		JPanel paramPane = createParamPanel(new GridLayout(4, 1, 0, 0), null);

		add(new JScrollPane(qrImagePane), BorderLayout.WEST);
		add(new JScrollPane(paramPane), BorderLayout.CENTER);
	}

	/**
	 * 创建显示二维码图片的面板
	 * 
	 * @param layout
	 *            默认布局
	 * @param d
	 *            尺寸首选项
	 * @return
	 */
	private JPanel createParamPanel(LayoutManager layout, Dimension d) {
		JPanel pane = new JPanel(layout);

		// 错误修正面板
		JPanel fixPane = createBorderPanel(pane, new GridLayout(2, 1), "错误修正");
		pane.add(fixPane);

		Dimension dim = new Dimension(120, 30);

		comboBoxErrorCorrect = new JComboBox<String>();
		comboBoxErrorCorrect.setModel(new DefaultComboBoxModel<String>(
				Constants.ERROR_CORRECT));
		comboBoxErrorCorrect.setPreferredSize(dim);
		fixPane.add(comboBoxErrorCorrect);

		// 版本设置面板
		JPanel versionPane = createBorderPanel(pane, new GridLayout(2, 2), "版本");
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
					lblSize.setText("图片像素: " + curSize + " x " + curSize);
				}
			}

		});
		comboBoxVersion.setSelectedItem("7");
		versionPane.add(comboBoxVersion);

		checkBoxAutoVerison = new JCheckBox("自动调整");
		autoResizeListener = addAutoSize();

		checkBoxAutoVerison.setPreferredSize(dim);
		checkBoxAutoVerison.addChangeListener(autoResizeListener);
		versionPane.add(checkBoxAutoVerison);

		// 设置图片尺寸面板
		JPanel sizePane = createBorderPanel(pane, new GridLayout(2, 1), "尺寸");
		pane.add(sizePane);

		lblSize = new JLabel("图片像素: 225 x 225"); // <-

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

				lblSize.setText("图片像素: " + curSize + " x " + curSize);
			}

		});
		sizePane.add(spinnerSize);
		sizePane.add(lblSize);

		// 设置编码模式面板
		JPanel encodePane = createBorderPanel(pane, new GridLayout(2, 1),
				"编码模式");
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
				.getBorder("TitledBorder.border"), "二维码图片",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		createGenerateButton(pane, "生成二维码", BorderLayout.SOUTH);
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
	 * 根据输入的信息生成二维码图片
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
									"请重新确认密钥长度", "提示",
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
						// 发生错误的原因可能是图片尺寸太大
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

				lblSize.setText("图片像素: " + curSize + " x " + curSize);
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
