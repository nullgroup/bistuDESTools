package org.gnull.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeUtil {

	private Qrcode qrCode;

	public QRCodeUtil() {
		this('L', 'B', 1);
	}

	public QRCodeUtil(char arg0, char arg1, int arg2) {
		qrCode = new Qrcode();

		qrCode.setQrcodeErrorCorrect(arg0);
		qrCode.setQrcodeEncodeMode(arg1);
		qrCode.setQrcodeVersion(arg2);
	}

	public void setErrorCorrect(char arg0) {
		qrCode.setQrcodeErrorCorrect(arg0);
	}

	public void setEncodeMode(char arg0) {
		qrCode.setQrcodeEncodeMode(arg0);
	}

	public void setVersion(int arg0) {
		qrCode.setQrcodeVersion(arg0);
	}

	public BufferedImage createImage(String content, int width, int height)
			throws Exception {
		return createImage(content, Color.BLACK, width, height, "GBK");
	}

	public BufferedImage createImage(String content, Color color, int width, int height, String charSet) throws Exception {
		BufferedImage qrCodeImage = null;
		
			byte[] b = content.getBytes(charSet);
			
			System.out.println("V" + qrCode.getQrcodeVersion());
			
			if (b.length > 0 && b.length < 120) {
				boolean[][] d = qrCode.calQrcode(b);
				
				qrCodeImage = new BufferedImage(d.length, d[0].length, BufferedImage.TYPE_BYTE_BINARY);
				Graphics2D g = qrCodeImage.createGraphics();
				
				g.setBackground(Color.WHITE);
				g.clearRect(0, 0, d.length, d[0].length);
				g.setColor(color);

				int multiple = 1;
				for (int i = 0; i < d.length; i++) {
					for (int j = 0; j < d.length; j++) {
						if (d[j][i]) {
							g.fillRect(j * multiple, i * multiple, multiple, multiple);
						}
					}
				}
				
				g.dispose();
				qrCodeImage.flush();
			}
		
		return resize(qrCodeImage, width, height);
	}

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

	public static void main(String[] args) throws Exception {
		QRCodeUtil q = new QRCodeUtil('L', 'B', 4);

		String content = "www.baidu.com";
		int width = 530;
		int height = 530;

		BufferedImage bi = q.createImage(content, width, height);

		File out = new File("D:\\outQrCode.png");

		if (!out.exists()) {
			out.createNewFile();
		}

		ImageIO.write(bi, "png", out);

		System.out.println("OK");
	}
}