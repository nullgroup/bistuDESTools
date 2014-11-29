package org.gnull.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import com.swetake.util.Qrcode;

/**
 * @author OSX
 *
 */
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
		return createImage(content, Color.BLACK, width, height, "UTF-8");
	}

	/**
	 * @param content
	 * @param color
	 * @param width
	 * @param height
	 * @param charSet
	 * @return
	 * @throws Exception
	 */
	public BufferedImage createImage(String content, Color color, int width,
			int height, String charSet) throws Exception {
		BufferedImage qrCodeImage = null;

		byte[] b = content.getBytes(charSet);

		if (b.length > 0 && b.length < 120) {
			boolean[][] d = qrCode.calQrcode(b);

			qrCodeImage = new BufferedImage(d.length, d[0].length,
					BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g = qrCodeImage.createGraphics();

			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, d.length, d[0].length);
			g.setColor(color);

			int multiple = 1;
			for (int i = 0; i < d.length; i++) {
				for (int j = 0; j < d.length; j++) {
					if (d[j][i]) {
						g.fillRect(j * multiple, i * multiple, multiple,
								multiple);
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
		targetImage.flush();

		return targetImage;
	}
	
	public BufferedImage resize(BufferedImage sourceImage, double value) {
		int type = sourceImage.getType();

		int tarWidth = (int) ((int) sourceImage.getWidth() * value);
		int tarHeight = (int) ((int) sourceImage.getHeight() * value);

		BufferedImage targetImage = new BufferedImage(tarWidth, tarHeight, type);

		Graphics2D g = targetImage.createGraphics();
		g.drawRenderedImage(sourceImage,
				AffineTransform.getScaleInstance(value, value));
		g.dispose();
		targetImage.flush();

		return targetImage;
	}

	public String parseImage(String imgPath) throws Exception {
		File imageFile = new File(imgPath);
		String content = null;
		BufferedImage sourceImage = null;
		
		int trialCounts = 1;
		double value = 0.9;
		
		try {
			sourceImage = ImageIO.read(imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean flag = true;
		while (flag) {
			try {

				flag = false;
				QRCodeDecoder decoder = new QRCodeDecoder();
				content = new String(decoder.decode(new GeneralQRCodeImage(
						sourceImage)), "UTF-8");

			} catch (Exception e) {
				flag = true;
				int width = sourceImage.getWidth();
				int height = sourceImage.getHeight();

				System.out.println(sourceImage.getWidth());
				
				if (value < 1E-8 || width <= 21 || height <= 21) {
					throw e;
				} else if (trialCounts % 10 == 0) {
					sourceImage = resize(ImageIO.read(imageFile), value);
					value = value - 0.05;
				} else {
					sourceImage = resize(sourceImage, value);	
				}
				trialCounts++;
			}
		}

		return content;
	}

	public static void main(String[] args) throws Exception {
		QRCodeUtil q = new QRCodeUtil('M', 'B', 4);

		String content = "英国那些事情总有些答案";
		int width = 459;

		BufferedImage bi = q.createImage(content, width, width);

		File out = new File("D:\\aaa.jpg");

		if (!out.exists()) {
			out.createNewFile();
		}

		ImageIO.write(bi, "jpg", out);

		System.out.println("OK");

		String decode = q.parseImage("d:\\aaa.jpg");

		System.out.println(decode);
	}
}

class GeneralQRCodeImage implements QRCodeImage {

	private BufferedImage bi;

	public GeneralQRCodeImage(BufferedImage bi) {
		this.bi = bi;
	}

	public int getWidth() {
		return bi.getWidth();
	}

	public int getHeight() {
		return bi.getHeight();
	}

	public int getPixel(int x, int y) {
		return bi.getRGB(x, y);
	}

}