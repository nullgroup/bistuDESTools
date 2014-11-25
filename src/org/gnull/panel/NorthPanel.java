package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
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

public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JComboBox<String> cbFix;

	JCheckBox chckbxType;

	JComboBox<String> cbAuto;

	JSpinner spinSize;

	JComboBox<String> cbEncodeType;
	
	Action generateQrImage;
	
	QRImagePanel qrCodePane;
	
	JLabel lblSize;
	
	ChangeListener autoResizeListener;
	
	private QRCodeUtil qrUtil;

	public NorthPanel() {

		setLayout(new BorderLayout(0, 0));
		qrUtil = new QRCodeUtil();

		JPanel qrImagePane = createQrImagePanel(new BorderLayout(0, 0));
		JPanel paramPane = createParamPanel(new GridLayout(4, 1, 0, 0));
		
		qrImagePane.setPreferredSize(new Dimension(300, 200));

		add(new JScrollPane(qrImagePane), BorderLayout.WEST);
		add(new JScrollPane(paramPane), BorderLayout.CENTER);
	}

	private JPanel createParamPanel(LayoutManager layout) {
		JPanel pane = new JPanel(layout);

		JPanel fixPane = createBorderPanel(pane, new GridLayout(2, 1), "´íÎóÐÞÕý");
		pane.add(fixPane);
		
		Dimension d = new Dimension(120, 30);

		cbFix = new JComboBox<String>();
		cbFix.setModel(new DefaultComboBoxModel<String>(Constants.ERROR_FIX));
		cbFix.setPreferredSize(d);
		fixPane.add(cbFix);

		JPanel typePane = createBorderPanel(pane, new GridLayout(2, 2), "ÐÍºÅ");
		pane.add(typePane);

		cbAuto = new JComboBox<String>(new DefaultComboBoxModel<String>(
				Constants.AUTO));
		cbAuto.setPreferredSize(d);
		typePane.add(cbAuto);

		
		chckbxType = new JCheckBox("×Ô¶¯Ëõ·Å");
		autoResizeListener = addAutoSize();
		
		chckbxType.setPreferredSize(d);
		chckbxType.addChangeListener(autoResizeListener);
		typePane.add(chckbxType);

		JPanel sizePane = createBorderPanel(pane, new GridLayout(2, 1),
				"³ß´ç");
		pane.add(sizePane);

		lblSize = new JLabel("Í¼Æ¬ÏñËØ: 41 x 41");
		
		spinSize = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		spinSize.setPreferredSize(d);
		spinSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int[] size = getSizeArray();
				
				lblSize.setText("Í¼Æ¬ÏñËØ: " + size[0] + " x " + size[1]);
			}
			
		});
		sizePane.add(spinSize);
		sizePane.add(lblSize);

		JPanel encodePane = createBorderPanel(pane, new GridLayout(2, 1), "±àÂë");
		pane.add(encodePane);

		cbEncodeType = new JComboBox<String>(new DefaultComboBoxModel<String>(
				Constants.ENCODE));
		cbEncodeType.setPreferredSize(d);
		encodePane.add(cbEncodeType);

		return pane;
	}

	private JPanel createBorderPanel(JPanel parent, LayoutManager layout,
			String title) {
		JPanel pane = (JPanel) parent.add(new JPanel(layout));

		pane.setBorder(new TitledBorder(null, title, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		return pane;
	}

	private JPanel createQrImagePanel(LayoutManager layout) {
		JPanel pane = new JPanel(layout);

		pane.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "¶þÎ¬ÂëÍ¼Æ¬",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		createGenerateButton(pane, "Éú³É¶þÎ¬Âë", BorderLayout.SOUTH);
		qrCodePane = new QRImagePanel();
		
		pane.add(qrCodePane, BorderLayout.CENTER);

		return pane;
	}

	private JButton createGenerateButton(JPanel parent, String text, String orient) {
		JButton button = new JButton();
		Font font = button.getFont();
		
		generateQrImage = createGenerateAction();
		
		button.setAction(generateQrImage);
		button.setText(text);
		button.setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
		parent.add(button, orient);
		
		return button;
	}

	private Action createGenerateAction() {
		Action a = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// set params
				// paint
				String errorCorrect = (String) cbFix.getSelectedItem();
				String versionStr = (String) cbAuto.getSelectedItem();
				int version = (versionStr.equals("×Ô¶¯")) ? 0 : Integer.parseInt(versionStr);
				
				int[] size = getSizeArray();
				
				qrUtil.setErrorCorrect(errorCorrect.charAt(0));
				qrUtil.setEncodeMode('B');
				qrUtil.setVersion(version);
				
				String content = "hello world";
				BufferedImage bi = qrUtil.createImage(content, size[0], size[1]);
				qrCodePane.setShowImage(bi);
				
				File f = new File("d:\\outQrCode.png");
				try {
					ImageIO.write(bi, "png", f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		
		return a;
	}
	
	private int[] getSizeArray() {
		int[] size = new int[2];
		
		int multiple = (int) spinSize.getValue();
		
		size[0] = size[1] = multiple * 41;
		
		return size;
	}
	
	private ChangeListener addAutoSize() {
		ChangeListener c = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (chckbxType.isSelected()) {
					cbAuto.setEnabled(false);
					qrUtil.setVersion(0);
				} else {
					cbAuto.setEnabled(true);
					String i = (String) cbAuto.getSelectedItem();
					int version = (i.equals("×Ô¶¯")) ? 0 : Integer.parseInt(i);
					qrUtil.setVersion(version);
				}
			}
			
		};
		
		return c;
	}

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
