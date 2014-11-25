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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
 * @author OSX
 *
 */
public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JComboBox<String> comboBoxErrorCorrect;

	JCheckBox checkBoxAutoVerison;

	JComboBox<String> comboBoxVersion;

	JSpinner spinnerSize;

	JComboBox<String> checkBoxEncodeType;
	
	Action generateQrImage;
	
	QRImagePanel qrCodePane;
	
	JLabel lblSize;
	
	ChangeListener autoResizeListener;
	
	private int curVersion = 7;
	
	private int curSize = 45;
	
	private QRCodeUtil qrUtil;

	public NorthPanel() {

		setLayout(new BorderLayout(0, 0));
		qrUtil = new QRCodeUtil();

		JPanel qrImagePane = createQrImagePanel(new BorderLayout(0, 0), new Dimension(300, 200));
		JPanel paramPane = createParamPanel(new GridLayout(4, 1, 0, 0), null);

		add(new JScrollPane(qrImagePane), BorderLayout.WEST);
		add(new JScrollPane(paramPane), BorderLayout.CENTER);
	}

	private JPanel createParamPanel(LayoutManager layout, Dimension d) {
		JPanel pane = new JPanel(layout);

		JPanel fixPane = createBorderPanel(pane, new GridLayout(2, 1), "´íÎóÐÞÕý");
		pane.add(fixPane);
		
		Dimension dim = new Dimension(120, 30);

		comboBoxErrorCorrect = new JComboBox<String>();
		comboBoxErrorCorrect.setModel(new DefaultComboBoxModel<String>(Constants.ERROR_CORRECT));
		comboBoxErrorCorrect.setPreferredSize(dim);
		fixPane.add(comboBoxErrorCorrect);

		JPanel versionPane = createBorderPanel(pane, new GridLayout(2, 2), "°æ±¾");
		pane.add(versionPane);

		comboBoxVersion = new JComboBox<String>(new DefaultComboBoxModel<String>(
				Constants.QRCODE_VERSION));
		comboBoxVersion.setPreferredSize(dim);
		comboBoxVersion.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (spinnerSize != null) {
					String versionStr = (String) comboBoxVersion.getSelectedItem();
					curVersion = Integer.parseInt(versionStr);
					
					String curSizeStr = lblSize.getText().substring(6);
					curSize = Integer.parseInt(curSizeStr.split("x")[0].trim());
					
					while ((21 + (curVersion - 1) * 4) > curSize) {
						curSize += 4;
					}
					lblSize.setText("Í¼Æ¬ÏñËØ: " + curSize + " x " + curSize);
				}
			}
			
		});
		comboBoxVersion.setSelectedItem("7");
		versionPane.add(comboBoxVersion);

		
		checkBoxAutoVerison = new JCheckBox("×Ô¶¯Ëõ·Å");
		autoResizeListener = addAutoSize();
		
		checkBoxAutoVerison.setPreferredSize(dim);
		checkBoxAutoVerison.addChangeListener(autoResizeListener);
		versionPane.add(checkBoxAutoVerison);

		JPanel sizePane = createBorderPanel(pane, new GridLayout(2, 1),
				"³ß´ç");
		pane.add(sizePane);

		lblSize = new JLabel("Í¼Æ¬ÏñËØ: 45 x 45"); // <-
		
		spinnerSize = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		spinnerSize.setPreferredSize(dim);
		spinnerSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (curVersion != 0) {
					curSize = (21 + (curVersion - 1) * 4) * (int) spinnerSize.getValue();
				} else {
					curSize = 64 * (int) spinnerSize.getValue();
				}
				
				lblSize.setText("Í¼Æ¬ÏñËØ: " + curSize + " x " + curSize);
			}
			
		});
		sizePane.add(spinnerSize);
		sizePane.add(lblSize);

		JPanel encodePane = createBorderPanel(pane, new GridLayout(2, 1), "±àÂë");
		pane.add(encodePane);

		checkBoxEncodeType = new JComboBox<String>(new DefaultComboBoxModel<String>(
				Constants.ENCODE));
		checkBoxEncodeType.setPreferredSize(dim);
		encodePane.add(checkBoxEncodeType);

		return pane;
	}

	private JPanel createBorderPanel(JPanel parent, LayoutManager layout,
			String title) {
		JPanel pane = (JPanel) parent.add(new JPanel(layout));

		pane.setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		return pane;
	}

	private JPanel createQrImagePanel(LayoutManager layout, Dimension d) {
		JPanel pane = new JPanel(layout);

		pane.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "¶þÎ¬ÂëÍ¼Æ¬",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		createGenerateButton(pane, "Éú³É¶þÎ¬Âë", BorderLayout.SOUTH);
		qrCodePane = new QRImagePanel();
		
		pane.add(qrCodePane, BorderLayout.CENTER);
		pane.setPreferredSize(d);

		return pane;
	}

	private JButton createGenerateButton(JPanel parent, String text, String orient) {
		JButton button = new JButton();
		Font font = button.getFont();
		
		generateQrImage = createGenerateAction();
		
		button.setAction(generateQrImage);
		button.setText(text);
		button.setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		
		parent.add(button, orient);
		
		return button;
	}

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

						String errorCorrect = (String) comboBoxErrorCorrect.getSelectedItem();
						
						String encodeMode = (String) checkBoxEncodeType.getSelectedItem();
						
						String content = "mailto:sample@163.com";

						qrUtil.setErrorCorrect(errorCorrect.charAt(0));
						qrUtil.setEncodeMode(parseEnCodeMode(encodeMode));
						qrUtil.setVersion(curVersion);
						System.out.println(curVersion);
						
						bi = qrUtil.createImage(content, curSize, curSize);
						
						qrCodePane.setShowImage(bi);
					} catch (Exception e2) {
						if (e2 instanceof ArrayIndexOutOfBoundsException) {
							flag = true;
							int i = comboBoxVersion.getSelectedIndex();
							if (i < 40) {
								comboBoxVersion.setSelectedIndex(i + 1);
							}
						}
					}
				}
				
				//////////////////////////////////////
				File f = new File("d:\\outQrCode.png");
				try {
					ImageIO.write(bi, "png", f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//////////////////////////////////////
			}

			////////////////////////////////////////////////
			private char parseEnCodeMode(String encodeMode) {
				// TODO Auto-generated method stub
				return 'B';
			}
		};
		
		return a;
	}
	
	private ChangeListener addAutoSize() {
		ChangeListener c = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (checkBoxAutoVerison.isSelected()) {
					comboBoxVersion.setEnabled(false);
					curVersion = 0;
					curSize = 64 * (int) spinnerSize.getValue();
				} else {
					comboBoxVersion.setEnabled(true);
					String version = (String) comboBoxVersion.getSelectedItem();
					int v = Integer.parseInt(version);
					curVersion = v;
					curSize = (21 + (curVersion - 1) * 4) * (int) spinnerSize.getValue();
				}

				lblSize.setText("Í¼Æ¬ÏñËØ: " + curSize + " x " + curSize);
			}
			
		};
		
		return c;
	}

	/**
	 * Main Method for Test
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("NorthPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new NorthPanel();
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 580);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
