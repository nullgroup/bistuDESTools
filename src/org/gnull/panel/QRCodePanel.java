package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.gnull.controller.FontStyleController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * @author 伍至煊
 * @date 2014/11/25
 */
public class QRCodePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	SouthPanel southPane; // 布局南部的面板，用于收集用户输入，包括密码（若要生成加密的二维码）
	NorthPanel northPane; // 布局北边的面板，用户显示生成的二维码和设置参数（包括错误修正度、版本、图片大小）

	public static void setLookAndFeel() {
		try {
			// 设置图形界面UI风格
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置全局字体
		// @see org.gnull.controller.FontStyleController#setGlobalFont(Font font)
		Font globalFont = new Font("微软雅黑", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
	}
	
	public QRCodePanel() {
		setLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		southPane = new SouthPanel();
		add(southPane, BorderLayout.CENTER);

		// 北部面板需要收集南部面板的数据
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
