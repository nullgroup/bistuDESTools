package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class ControllTabbedPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static public final int ENCRYPT = 0xCDC;
	static public final int DECRYPT = 0xDCD;

	private JPanel encryptOptionPanel;
	private JPanel decryptOptionPanel;
	private JPanel digestOptionPanel;

	private JTabbedPane modeTabbedPane;

	private JTextField keyFieldA;
	private JTextField keyFieldB;

	private JTextField enOutputPath;
	private JTextField deOutputPath;
	
	private JTextField hashField;

	private JComboBox<Object> digestModeComboBox;
	
	public JTextField getKeyFieldA() {
		return keyFieldA;
	}
	
	public JTextField getKeyFieldB() {
		return keyFieldB;
	}
	
	public JTextField getEnOutputPath() {
		return enOutputPath;
	}
	
	public JTextField getDeOutputPath() {
		return deOutputPath;
	}

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();

		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
		BeautyEyeLNFHelper.launchBeautyEyeLNF();
		f.getContentPane().add(new ControllTabbedPanel());
		f.setSize(250, 220);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public int getMode() {
		return modeTabbedPane.getSelectedIndex();
	}

	public String getOutputPath(int i) {
		if (modeTabbedPane.getSelectedIndex() == i && i != 2) {
			return (i == 0) ? enOutputPath.getText() : deOutputPath.getText();
		}
		return null;
	}

	public String getKey(int i) {
		if (modeTabbedPane.getSelectedIndex() == i && i != 2) {
			String a = keyFieldA.getText();
			String b = keyFieldB.getText();
			if (i == 0 && a.equals("«Î ‰»ÎΩ‚√‹√‹‘ø")) {
				return a;
			} else if (i == 0 && !b.equals("«Î ‰»ÎΩ‚√‹√‹‘ø")) {
				return b;
			}
		}
		return null;
	}

	public ControllTabbedPanel() {
		setLookAndFeel();
		createContentPane();
	}

	private void createContentPane() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250, 220));

		modeTabbedPane = new JTabbedPane(JTabbedPane.TOP);

		createEncryptOptionPane();
		createDecryptOptionPane();
		createDigestOptionPane();

		add(modeTabbedPane, BorderLayout.CENTER);
	}

	private void createEncryptOptionPane() {
		encryptOptionPanel = new JPanel();
		encryptOptionPanel.setLayout(new BorderLayout(0, 0));

		JPanel p1 = new JPanel();
		p1.setAlignmentX(LEFT_ALIGNMENT);
		JPanel p2 = new JPanel();
		p2.setAlignmentX(LEFT_ALIGNMENT);
		JPanel p3 = new JPanel();
		p3.setAlignmentX(LEFT_ALIGNMENT);

		JLabel keyValA = new JLabel("º”√‹√‹‘ø");
		keyFieldA = createKeyField(ENCRYPT);
		keyFieldA.setText("«Î ‰»Îº”√‹√‹‘ø");
		JLabel outputPathA = new JLabel(" ‰≥ˆ¬∑æ∂");
		enOutputPath = new JTextField(13);
		enOutputPath.setEditable(false);
		enOutputPath.setAutoscrolls(false);
		enOutputPath.setPreferredSize(new Dimension(140, 30));
		JButton btnSelectFileA = createSelectFileButton(ENCRYPT);
		btnSelectFileA.setText("‰Ø¿¿Œƒº˛");
		btnSelectFileA.setPreferredSize(new Dimension(80, 30));

		p1.add(keyValA, BorderLayout.WEST);
		p1.add(keyFieldA, BorderLayout.EAST);
		p2.add(outputPathA, BorderLayout.WEST);
		p2.add(enOutputPath, BorderLayout.EAST);
		p3.add(btnSelectFileA, BorderLayout.NORTH);

		encryptOptionPanel.add(p1, BorderLayout.NORTH);
		encryptOptionPanel.add(p2, BorderLayout.CENTER);
		encryptOptionPanel.add(p3, BorderLayout.SOUTH);
		
		modeTabbedPane.add("º”√‹", encryptOptionPanel);
	}

	private void createDecryptOptionPane() {
		decryptOptionPanel = new JPanel();
		decryptOptionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel p1 = new JPanel();
		p1.setAlignmentX(LEFT_ALIGNMENT);
		JPanel p2 = new JPanel();
		p2.setAlignmentX(LEFT_ALIGNMENT);
		JPanel p3 = new JPanel();
		p3.setAlignmentX(LEFT_ALIGNMENT);
		JLabel keyValB = new JLabel("Ω‚√‹√‹‘ø");

		keyFieldB = createKeyField(DECRYPT);
		keyFieldB.setText("«Î ‰»ÎΩ‚√‹√‹‘ø");

		JLabel outputPathB = new JLabel(" ‰≥ˆ¬∑æ∂");
		deOutputPath = new JTextField(13);
		deOutputPath.setEditable(false);
		deOutputPath.setAutoscrolls(false);
		deOutputPath.setPreferredSize(new Dimension(140, 30));
		
		JButton btnSelectFileB = createSelectFileButton(DECRYPT);
		btnSelectFileB.setText("‰Ø¿¿Œƒº˛");
		btnSelectFileB.setPreferredSize(new Dimension(80, 30));

		p1.add(keyValB, BorderLayout.WEST);
		p1.add(keyFieldB, BorderLayout.EAST);
		p2.add(outputPathB, BorderLayout.WEST);
		p2.add(deOutputPath, BorderLayout.EAST);
		p3.add(btnSelectFileB, BorderLayout.NORTH);

		decryptOptionPanel.add(p1, BorderLayout.NORTH);
		decryptOptionPanel.add(p2, BorderLayout.CENTER);
		decryptOptionPanel.add(p3, BorderLayout.SOUTH);

		modeTabbedPane.add("Ω‚√‹", decryptOptionPanel);
	}

	private void createDigestOptionPane() {
		digestOptionPanel = new JPanel();
		digestOptionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel p1 = new JPanel();
		p1.setAlignmentX(LEFT_ALIGNMENT);
		JPanel p2 = new JPanel();
		p2.setAlignmentX(LEFT_ALIGNMENT);

		JLabel hashVal = new JLabel("π˛œ£÷µ¥Æ");
		hashField = new JTextField(13);
		hashField.setPreferredSize(new Dimension(140, 30));
		JLabel modeVal = new JLabel("ƒ£ Ω—°‘Ò");
		final Object[] HASH_MODE = {"MD5", "SHA-1", "CRC32"};
		digestModeComboBox = new JComboBox<Object>(HASH_MODE);
		digestModeComboBox.setPreferredSize(new Dimension(150, 30));
		digestModeComboBox.setSelectedIndex(0);

		p1.add(hashVal, BorderLayout.WEST);
		p1.add(hashField, BorderLayout.EAST);
		p2.add(modeVal, BorderLayout.WEST);
		p2.add(digestModeComboBox, BorderLayout.EAST);

		digestOptionPanel.add(p1, BorderLayout.NORTH);
		digestOptionPanel.add(p2, BorderLayout.CENTER);
		
		modeTabbedPane.add("∆•≈‰’™“™", digestOptionPanel);
	}

	private void setLookAndFeel() {
		TitledBorder border = new TitledBorder(null, "ƒ£ Ω—°‘Ò",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		setBorder(border);
	}
	
	private FocusListener createHintTextFieldFocusListener(final int mode) {
		FocusListener hint = new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (mode == ENCRYPT && keyFieldA.getText().equals("«Î ‰»Îº”√‹√‹‘ø")) {
					keyFieldA.setText("");
				} else if (mode == DECRYPT
						&& keyFieldB.getText().equals("«Î ‰»ÎΩ‚√‹√‹‘ø")) {
					keyFieldB.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (mode == ENCRYPT && keyFieldA.getText().equals("")) {
					keyFieldA.setText("«Î ‰»Îº”√‹√‹‘ø");
				} else if (mode == DECRYPT && keyFieldB.getText().equals("")) {
					keyFieldB.setText("«Î ‰»ÎΩ‚√‹√‹‘ø");
				}
			}

		};
		return hint;
	}

	public JTextField createKeyField(final int mode) {
		JTextField k = new JTextField(13);
		k.setPreferredSize(new Dimension(140, 30));
		k.addFocusListener(createHintTextFieldFocusListener(mode));
		if (mode == ENCRYPT) {
			keyFieldA = k;
		} else {
			keyFieldB = k;
		}
		return k;
	}
	
	private Action createSelectFileAction(final int mode) {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {
					String selectedFilepath = fileChooser.getSelectedFile()
							.getAbsolutePath();
					File selectedFile = new File(selectedFilepath);
					if (mode == ENCRYPT) {
						enOutputPath.setText(selectedFile.getPath());
					} else {
						deOutputPath.setText(selectedFile.getPath());
					}
				}
			}

		};

		return a;
	}

	public JButton createSelectFileButton(final int mode) {
		JButton b = new JButton();
		b.setPreferredSize(new Dimension(50, 30));
		b.setAction(createSelectFileAction(mode));
		return b;
	}
}
