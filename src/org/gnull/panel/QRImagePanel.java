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

import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.gnull.util.Constants;

/**
 * @author OSX
 *
 */
public class QRImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage img;

	JPopupMenu handleImgMenu;

	JFileChooser fileChooser;

	public QRImagePanel() {
		super(new BorderLayout());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (img == null) {
			return;
		}

		int width = super.getWidth();
		int height = super.getHeight();

		BufferedImage newImg = resize(img, (int) (width * 0.8),
				(int) (height * 0.8));

		g.drawImage(newImg, (int) (width * 0.1), (int) (height * 0.1), null);
	}

	/**
	 * @param sourceImage
	 * @param tarWidth
	 * @param tarHeight
	 * @return
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
	 * @return
	 */
	private MouseAdapter showWhenRightMouseButtonPressed() {
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isMetaDown() && img != null) {
					initPopupMenu(); // Lazy Loading
					JPanel p = (JPanel) e.getSource();
					handleImgMenu.show(p, e.getX(), e.getY());
				}
			}
		};

		return ma;
	}

	/**
	 * Lazy Loading
	 */
	private void initPopupMenu() {
		if (handleImgMenu == null) {
			handleImgMenu = createPopupMenu("二维码处理面板", "QRImagePanel.PopupMenu");
		}
	}

	/**
	 * @param name
	 * @param accessibleName
	 * @return
	 */
	private JPopupMenu createPopupMenu(String name, String accessibleName) {
		JPopupMenu menu = new JPopupMenu(name);
		AccessibleContext context = menu.getAccessibleContext();
		context.setAccessibleName(accessibleName);

		KeyStroke keyCopy = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK);

		addPopupMenuItem(menu, "保存图片...", "QRImagePanel.SaveQRImage", keyCopy,
				createSaveAction());

		return menu;
	}

	/**
	 * @param menu
	 * @param label
	 * @param accessibleDescription
	 * @param keyStroke
	 * @param action
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
	 * @return
	 */
	private Action createSaveAction() {
		Action a = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				initFileChooser();

				int i = fileChooser.showSaveDialog(fileChooser);
				if (i == JFileChooser.APPROVE_OPTION) {

					File outputFile = parseFile(fileChooser.getSelectedFile(),
							fileChooser);
					String filename = outputFile.getName();
					
					int index = filename.lastIndexOf('.');

					// ///////////////////
					try {
						ImageIO.write(img, filename.substring(index + 1, filename.length()), outputFile);
					} catch (IOException ioe) {
						System.err.println("Can't save qrcode image: " + ioe.getMessage());
					}
					// ///////////////////
				}
			}

			private File parseFile(File file, final JFileChooser fileChooser) {
				String absPath = file.getAbsolutePath();

				StringBuilder b = new StringBuilder(128);
				FileNameExtensionFilter f = (FileNameExtensionFilter) fileChooser
						.getFileFilter();

				int i = absPath.lastIndexOf('.');
				String extensionNameEnd = absPath.substring(i + 1, absPath.length());

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

	private void initFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			String[][] t = Constants.FILE_NAME_EXTENSION_FILTER;

			for (int i = 0; i < t.length; i++) {
				String[] value = t[i][1].split("/");
				FileNameExtensionFilter f = new FileNameExtensionFilter(
						t[i][0], value);
				fileChooser.addChoosableFileFilter(f);
			}

			fileChooser.setAcceptAllFileFilterUsed(false);
		}
		fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("SouthPanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new QRImagePanel();
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 580);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
