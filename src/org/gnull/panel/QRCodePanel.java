package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.gnull.controller.FontStyleController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * @author ������
 * @date 2014/11/25
 */
public class QRCodePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	SouthPanel southPane; // �����ϲ�����壬�����ռ��û����룬�������루��Ҫ���ɼ��ܵĶ�ά�룩
	NorthPanel northPane; // ���ֱ��ߵ���壬�û���ʾ���ɵĶ�ά������ò������������������ȡ��汾��ͼƬ��С��

	public static void setLookAndFeel() {
		try {
			// ����ͼ�ν���UI���
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ����ȫ������
		// @see org.gnull.controller.FontStyleController#setGlobalFont(Font font)
		Font globalFont = new Font("΢���ź�", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
	}
	
	public QRCodePanel() {
		setLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		southPane = new SouthPanel();
		add(southPane, BorderLayout.CENTER);

		// ���������Ҫ�ռ��ϲ���������
		northPane = new NorthPanel(southPane);
		northPane.setPreferredSize(new Dimension(550, 300));
		add(northPane, BorderLayout.NORTH);
	}

	/**
	 * Main Method for Test
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame("QRCodePanelDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JComponent newContentPane = new QRCodePanel();
				newContentPane.setOpaque(true); // content panes must be opaque
				frame.setContentPane(newContentPane);
				frame.setSize(550, 660);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}
}
