package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import jp.sourceforge.qrcode.exception.DecodingFailedException;

import org.gnull.des.DESUtil;
import org.gnull.util.Constants;
import org.gnull.util.QRCodeUtil;

/**
 * @author ������
 *
 */
public class QRImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** �ݴ�Ķ�ά��ͼƬ */
	private BufferedImage img;

	/** �Ҽ��˵� */
	JPopupMenu handleImgMenu;

	/** �ļ�ѡ���� */
	JFileChooser fileChooser;

	private QRCodeUtil qrUtil;

	/** �޲ι��췽�� */
	public QRImagePanel(QRCodeUtil qrUtil) {
		super(new BorderLayout());

		this.qrUtil = qrUtil;

		setBackground(Color.WHITE);

		addMouseListener(showWhenRightMouseButtonPressed());
	}

	public void setShowImage(BufferedImage img) {
		this.img = img;
		repaint();
	}

	public BufferedImage getShowImg() {
		return img;
	}

	public void clearPanel() {
		img = null;
		repaint();
	}

	/**
	 * �ػ�
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/** ������ */
		if (img == null) {
			return;
		}

		int width = super.getWidth();
		int height = super.getHeight();

		// ����ͼ���С����Ӧ���ڱ�����ʾ
		BufferedImage newImg = resize(img, (int) (width * 0.8),
				(int) (height * 0.8));

		// �ػ�ͼƬ
		g.drawImage(newImg, (int) (width * 0.1), (int) (height * 0.1), null);
	}

	/**
	 * ��ԴͼƬ��������
	 * 
	 * @param sourceImage
	 *            ԴͼƬ
	 * @param tarWidth
	 *            Ŀ����
	 * @param tarHeight
	 *            Ŀ��߶�
	 * @return ���ź��ͼƬ
	 */
	public BufferedImage resize(BufferedImage sourceImage, int tarWidth,
			int tarHeight) {
		int type = sourceImage.getType();

		double xVal = (double) tarWidth / sourceImage.getWidth();
		double yVal = (double) tarHeight / sourceImage.getHeight();

		BufferedImage targetImage = new BufferedImage(tarWidth, tarHeight, type);

		Graphics2D g = targetImage.createGraphics();
		g.drawRenderedImage(sourceImage,
				AffineTransform.getScaleInstance(xVal, yVal));
		g.dispose();

		return targetImage;
	}

	/**
	 * �������������ڵ����Ҽ��˵�
	 * 
	 * @return
	 */
	private MouseAdapter showWhenRightMouseButtonPressed() {
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				// ����û��ڸ�����°����Ҽ������������ͼƬ
				if (e.isMetaDown() && img != null) {
					// ������ģʽ
					if (handleImgMenu == null) {
						handleImgMenu = createPopupMenu("��ά�봦�����",
								"QRImagePanel.PopupMenu");
					}
					handleImgMenu.show((JPanel) e.getSource(), e.getX(),
							e.getY());
				}
			}
		};

		return ma;
	}

	/**
	 * �����Ҽ������˵�
	 * 
	 * @param name
	 *            �Ҽ��˵���
	 * @param accessibleName
	 *            �����Ա�ǩ
	 * @return
	 */
	private JPopupMenu createPopupMenu(String name, String accessibleName) {
		JPopupMenu menu = new JPopupMenu(name);
		menu.getAccessibleContext().setAccessibleName(accessibleName);

		// ���ÿ�ݼ� Ctrl + S
		KeyStroke keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK);

		KeyStroke keyImport = KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK);

		// ��˵������һ���˵������ͼƬ����������Ĭ�ϵĶ���
		addPopupMenuItem(menu, "����ͼƬ...", "QRImagePanel.SaveQRImage", keyCopy,
				createSaveAction());

		addPopupMenuItem(menu, "����ͼƬ...", "QRImagePanel.SaveQRImage",
				keyImport, createImportAction());

		return menu;
	}

	/**
	 * �����Ҽ������˵���
	 * 
	 * @param menu
	 *            �����˵����Ҽ��˵���
	 * @param label
	 *            �˵��������
	 * @param accessibleDescription
	 *            �˵������������ʾ
	 * @param keyStroke
	 *            �˵���Ŀ�ݼ�
	 * @param action
	 *            ����˵����Ĭ�϶���
	 * @return
	 */
	private JMenuItem addPopupMenuItem(JPopupMenu menu, String label,
			String actionCommand, KeyStroke keyStroke, Action a) {

		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.setActionCommand(actionCommand);
		mi.setAccelerator(keyStroke);
		mi.setAction(a);
		mi.setText(label);

		return mi;
	}

	/**
	 * ����һ������İ�ť
	 * 
	 * @return
	 */
	private Action createSaveAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// ��ʼ���ļ�ѡ������������ģʽ
				initFileChooser();

				// ��һ���������
				int i = fileChooser.showSaveDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {

					// ��ѡ���ļ��ĺϷ��Խ��в��ԣ������Ϸ��������Զ�����
					File outputFile = parseFile(fileChooser.getSelectedFile(),
							fileChooser);
					String filename = outputFile.getName();

					int index = filename.lastIndexOf('.');

					// ///////////////////
					try {
						// ʵ�ʱ������
						ImageIO.write(img, filename.substring(index + 1,
								filename.length()), outputFile);
					} catch (IOException ioe) {
						System.err.println("Can't save qrcode image: "
								+ ioe.getMessage());
					}
					// ///////////////////
				}
			}

			/**
			 * ��Ᵽ���ļ����ĺϷ��Բ���������
			 * 
			 * @param file
			 *            ѡ�е��ļ�
			 * @param fileChooser
			 *            �ļ�ѡ����
			 * @return
			 */
			private File parseFile(File file, final JFileChooser fileChooser) {
				String absPath = file.getAbsolutePath();

				StringBuilder b = new StringBuilder(128);
				FileNameExtensionFilter f = (FileNameExtensionFilter) fileChooser
						.getFileFilter();

				int i = absPath.lastIndexOf('.');
				String extensionNameEnd = absPath.substring(i + 1,
						absPath.length());

				if (i != -1 && extensionNameEnd.equals(f.getExtensions()[0])) {
					b.append(absPath);
				} else {
					b.append(absPath).append(".").append(f.getExtensions()[0]);
				}

				return new File(b.toString());
			}

		};

		return a;
	}

	private Action createImportAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ʼ���ļ�ѡ������������ģʽ
				initFileChooser();

				// ��һ���������
				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {

					// ��ѡ���ļ��ĺϷ��Խ��в��ԣ������Ϸ��������Զ�����
					File inputFile = fileChooser.getSelectedFile();
					String text = "";

					// ///////////////////
					try {
						// ʵ�ʴ򿪲���
						text = qrUtil.parseImage(inputFile.getAbsolutePath());
					} catch (Exception ex) {
						if (ex instanceof DecodingFailedException) {
							JOptionPane
									.showMessageDialog(QRImagePanel.this,
											"�޷�����ͼƬ", "��ʾ",
											JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					// ///////////////////
					showResultDialog(text, "�������",
							new String[] { "ȷ��", "���Խ���" });
				}
			}

			private void showResultDialog(String text, String title,
					String[] options) {// Messages
				Object[] message = new Object[1];
				message[0] = "�ö�ά�����������ϢΪ: " + text;

				int result = JOptionPane.showOptionDialog(QRImagePanel.this,
						message, title, JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
				
				switch (result) {
				case 1: // no
					String key = showPasswordDialog("��������", text);
					
					if (key == null) {
						return;
					}
					
					String mText = "";
					try {
						mText = DESUtil.decrypt(text, key);
					} catch (Exception e) {
						if (e instanceof java.security.InvalidKeyException) {
							JOptionPane.showMessageDialog(QRImagePanel.this,
									"������ȷ����Կ����", "��ʾ",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
					JOptionPane.showMessageDialog(QRImagePanel.this, "���ܺ����ϢΪ: " + mText);		
					break;
				default:
					break;
				}

			}

			private String showPasswordDialog(String string, String text) {
				Object[] message = new Object[2];
				
				message[0] = "�����������Գ��Խ���:";
				message[1] = new JPasswordField(13);
				
				String[] options = {
						"ȷ��", "ȡ��"
				};
				
				int result = JOptionPane.showOptionDialog(QRImagePanel.this,
						message, "��ʾ", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
				
				switch (result) {
				case 0:
					return new String(((JPasswordField) message[1]).getPassword());	
				default:
					return null;
				}
			}
		};

		return a;
	}

	/**
	 * ��ʼ���ļ�ѡ����
	 */
	private void initFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();

			// �����ļ�ѡ����ֻ��ѡ���ļ�������Ŀ¼
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			String[][] t = Constants.FILE_NAME_EXTENSION_FILTER;

			for (int i = 0; i < t.length; i++) {
				String[] value = t[i][1].split("/");
				FileNameExtensionFilter f = new FileNameExtensionFilter(
						t[i][0], value);
				fileChooser.addChoosableFileFilter(f);
			}

			// ȡ��ѡ��ģʽ �������ļ���
			fileChooser.setAcceptAllFileFilterUsed(false);
		}

		// �����ļ�������
		fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
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
				JFrame frame = new JFrame("SouthPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new QRImagePanel(new QRCodeUtil());
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 580);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
