package org.gnull.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.sun.awt.AWTUtilities;

/**
 * ͼƬ���,������Ҫ�ӱ���ͼƬʱ��
 * 
 * @author waitatlee@163.com
 */
public class CustomImgPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 0;
	private int height = 0;
	private String imgPath = "";

	/**
	 *
	 * @param _width
	 *            ����,���ڵĿ��
	 * @param _height
	 *            ����,���ڵĸ߶�
	 * @param _imgPath
	 *            ͼƬ��URL,�������·��
	 */
	public CustomImgPanel(int _width, int _height, String _imgPath) {
		width = _width;
		height = _height;
		imgPath = _imgPath;
		setSize(width, height);
	}

	/**
	 *
	 * @param _width
	 *            ������,���ڵĿ��
	 * @param _height
	 *            ������,���ڵĸ߶�
	 * @param _imgPath
	 *            �ַ���,ͼƬ��URL,�������
	 */
	public CustomImgPanel(double _width, double _height, String _imgPath) {
		width = (int) _width;
		height = (int) _height;
		imgPath = _imgPath;
		setSize(width, height);
	}

	@Override
	public void paintComponent(Graphics gs) {
		Graphics2D g = (Graphics2D) gs;
		super.paintComponent(g);
		// ������ͼƬ
		Image image = new ImageIcon(imgPath).getImage();
		g.drawImage(image, 0, 0, width, height, this);
	}

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
		BeautyEyeLNFHelper.launchBeautyEyeLNF();

		f.add(new CustomImgPanel(500, 251, "d:\\ecl.jpg"));
		f.setSize(500, 251);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		AWTUtilities.setWindowOpaque(f, false);
		f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		f.setVisible(true);
		
		Thread.sleep(4000);
		
		f.dispose();
		
		MainFrame a = new MainFrame();
		a.setLocationRelativeTo(f);
		a.setVisible(true);;
	}
}
