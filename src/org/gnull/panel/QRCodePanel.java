package org.gnull.panel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.gnull.controller.FontStyleController;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class QRCodePanel extends JPanel {

;	private static final long serialVersionUID = 1L;
	
	SouthPanel southPane;
	NorthPanel northPane;

	public static void setLookAndFeel() {
		try {
			// Set Graphics UI
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set Global Font
		Font globalFont = new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12);
		FontStyleController.setGlobalFont(globalFont);
	}
	
	public QRCodePanel() {
		setLookAndFeel();
		setLayout(new BorderLayout(0, 0));

		JPanel contentPane = new JPanel();
		add(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		southPane = new SouthPanel();
		contentPane.add(southPane, BorderLayout.SOUTH);
		
		northPane = new NorthPanel();
		contentPane.add(northPane, BorderLayout.CENTER);
	}

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
