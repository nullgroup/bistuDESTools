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
 * @author 伍至煊
 *
 */
public class QRImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 暂存的二维码图片 */
	private BufferedImage img;

	/** 右键菜单 */
	JPopupMenu handleImgMenu;

	/** 文件选择器 */
	JFileChooser fileChooser;

	private QRCodeUtil qrUtil;

	/** 无参构造方法 */
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
	 * 重绘
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/** 懒加载 */
		if (img == null) {
			return;
		}

		int width = super.getWidth();
		int height = super.getHeight();

		// 调整图像大小以适应窗口便于显示
		BufferedImage newImg = resize(img, (int) (width * 0.8),
				(int) (height * 0.8));

		// 重绘图片
		g.drawImage(newImg, (int) (width * 0.1), (int) (height * 0.1), null);
	}

	/**
	 * 对源图片进行缩放
	 * 
	 * @param sourceImage
	 *            源图片
	 * @param tarWidth
	 *            目标宽度
	 * @param tarHeight
	 *            目标高度
	 * @return 缩放后的图片
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
	 * 鼠标监听器，用于弹出右键菜单
	 * 
	 * @return
	 */
	private MouseAdapter showWhenRightMouseButtonPressed() {
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				// 如果用户在该面板下按下右键且面板中已有图片
				if (e.isMetaDown() && img != null) {
					// 懒加载模式
					if (handleImgMenu == null) {
						handleImgMenu = createPopupMenu("二维码处理面板",
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
	 * 创建右键弹出菜单
	 * 
	 * @param name
	 *            右键菜单名
	 * @param accessibleName
	 *            描述性标签
	 * @return
	 */
	private JPopupMenu createPopupMenu(String name, String accessibleName) {
		JPopupMenu menu = new JPopupMenu(name);
		menu.getAccessibleContext().setAccessibleName(accessibleName);

		// 设置快捷键 Ctrl + S
		KeyStroke keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK);

		KeyStroke keyImport = KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK);

		// 向菜单里添加一个菜单项（保存图片），并创建默认的动作
		addPopupMenuItem(menu, "保存图片...", "QRImagePanel.SaveQRImage", keyCopy,
				createSaveAction());

		addPopupMenuItem(menu, "导入图片...", "QRImagePanel.SaveQRImage",
				keyImport, createImportAction());

		return menu;
	}

	/**
	 * 创建右键弹出菜单项
	 * 
	 * @param menu
	 *            父级菜单（右键菜单）
	 * @param label
	 *            菜单项的文字
	 * @param accessibleDescription
	 *            菜单项的描述性提示
	 * @param keyStroke
	 *            菜单项的快捷键
	 * @param action
	 *            点击菜单项的默认动作
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
	 * 创建一键保存的按钮
	 * 
	 * @return
	 */
	private Action createSaveAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// 初始化文件选择器，懒加载模式
				initFileChooser();

				// 打开一个保存界面
				int i = fileChooser.showSaveDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {

					// 对选中文件的合法性进行测试，若不合法将进行自动纠正
					File outputFile = parseFile(fileChooser.getSelectedFile(),
							fileChooser);
					String filename = outputFile.getName();

					int index = filename.lastIndexOf('.');

					// ///////////////////
					try {
						// 实际保存操作
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
			 * 检测保存文件名的合法性并进行修正
			 * 
			 * @param file
			 *            选中的文件
			 * @param fileChooser
			 *            文件选择器
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
				// 初始化文件选择器，懒加载模式
				initFileChooser();

				// 打开一个保存界面
				int i = fileChooser.showOpenDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {

					// 对选中文件的合法性进行测试，若不合法将进行自动纠正
					File inputFile = fileChooser.getSelectedFile();
					String text = "";

					// ///////////////////
					try {
						// 实际打开操作
						text = qrUtil.parseImage(inputFile.getAbsolutePath());
					} catch (Exception ex) {
						if (ex instanceof DecodingFailedException) {
							JOptionPane
									.showMessageDialog(QRImagePanel.this,
											"无法解析图片", "提示",
											JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					// ///////////////////
					showResultDialog(text, "解析结果",
							new String[] { "确定", "尝试解密" });
				}
			}

			private void showResultDialog(String text, String title,
					String[] options) {// Messages
				Object[] message = new Object[1];
				message[0] = "该二维码解析出的消息为: " + text;

				int result = JOptionPane.showOptionDialog(QRImagePanel.this,
						message, title, JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
				
				switch (result) {
				case 1: // no
					String key = showPasswordDialog("输入密码", text);
					
					if (key == null) {
						return;
					}
					
					String mText = "";
					try {
						mText = DESUtil.decrypt(text, key);
					} catch (Exception e) {
						if (e instanceof java.security.InvalidKeyException) {
							JOptionPane.showMessageDialog(QRImagePanel.this,
									"请重新确认密钥长度", "提示",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					
					JOptionPane.showMessageDialog(QRImagePanel.this, "解密后的消息为: " + mText);		
					break;
				default:
					break;
				}

			}

			private String showPasswordDialog(String string, String text) {
				Object[] message = new Object[2];
				
				message[0] = "请输入密码以尝试解密:";
				message[1] = new JPasswordField(13);
				
				String[] options = {
						"确定", "取消"
				};
				
				int result = JOptionPane.showOptionDialog(QRImagePanel.this,
						message, "提示", JOptionPane.DEFAULT_OPTION,
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
	 * 初始化文件选择器
	 */
	private void initFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();

			// 设置文件选择器只能选择文件而不是目录
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			String[][] t = Constants.FILE_NAME_EXTENSION_FILTER;

			for (int i = 0; i < t.length; i++) {
				String[] value = t[i][1].split("/");
				FileNameExtensionFilter f = new FileNameExtensionFilter(
						t[i][0], value);
				fileChooser.addChoosableFileFilter(f);
			}

			// 取消选择模式 ‘所有文件’
			fileChooser.setAcceptAllFileFilterUsed(false);
		}

		// 重置文件过滤器
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
