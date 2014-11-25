package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QRImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;

	public QRImagePanel() {
		super(new BorderLayout());
		super.setBackground(Color.WHITE);
	}
	
	public void setShowImage(BufferedImage img) {
		this.img = img;
		repaint();
	}
	
	public BufferedImage getShowImg() {
		return img;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (img == null) {
			return;
		}
		
		int width = super.getWidth();
		int height = super.getHeight();
		
		BufferedImage newImg = resize(img, (int) (width * 0.8), (int) (height * 0.8));
		
		g.drawImage(newImg, (int) (width * 0.1), (int) (height * 0.1), null);
	}
	
	public BufferedImage resize(BufferedImage sourceImage, int tarWidth, int tarHeight) {
		int type = sourceImage.getType();
		
		double xVal = (double) tarWidth / sourceImage.getWidth();
		double yVal = (double) tarHeight / sourceImage.getHeight();
		
		BufferedImage targetImage = new BufferedImage(tarWidth, tarHeight, type);
		
		Graphics2D g = targetImage.createGraphics();
		g.drawRenderedImage(sourceImage, AffineTransform.getScaleInstance(xVal, yVal));
		g.dispose();
		
		return targetImage;
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
