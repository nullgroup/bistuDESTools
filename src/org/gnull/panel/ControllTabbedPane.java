package org.gnull.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ControllTabbedPane extends JFrame
{
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	JPanel encryptionPanel,decryptionPanel,synopsisPanel;
	JTabbedPane ControllTabbedPane = new JTabbedPane(JTabbedPane.TOP);

	JLabel miyao1,quchu1,miyao2,quchu2,haxizhi,moshixuanze;
	private JTextField miyaoField1,miyaoField2,haxizhiField;
	

	JTextArea quchuField1,quchuField2;
	JScrollPane jsp1,jsp2;
	JComboBox xlk;
	JButton wenjian1 = new JButton("..."),wenjian2 = new JButton("...");
	public ControllTabbedPane(){
		this.add(ControllTabbedPane);
		
		
		encryptionPanel = new JPanel();            //加密面板
		encryptionPanel.setLayout(new FlowLayout());
		miyao1 = new JLabel("秘钥");
		miyaoField1 = new JTextField(13);
		quchu1 = new JLabel("输出路径");
		quchuField1 = new JTextArea(1,13);
		jsp1 = new JScrollPane(quchuField1);
		jsp1.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		encryptionPanel.add(miyao1);
		encryptionPanel.add(miyaoField1);
		
		encryptionPanel.add(quchu1);
		encryptionPanel.add(jsp1);
		encryptionPanel.add(wenjian1);
		//wenjian1.addActionListener(this);	
		wenjian1.addActionListener(new ActionListener(){  //监听事件
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();  //对话框
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = fileChooser.showOpenDialog(fileChooser);  //opendialog
                if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
                {
                		
                		String selectedFilepath = fileChooser.getSelectedFile().getAbsolutePath();  //取得选中的文件
                    File selectedFile = new File(selectedFilepath);
                    quchuField1.setText(selectedFile.getPath());   //取得路径
                }
            }
        });
		
		decryptionPanel = new JPanel();                 //解密面板
		decryptionPanel.setLayout(new FlowLayout());
		miyao2 = new JLabel("秘钥");
		miyaoField2 = new JTextField(13);
		quchu2 = new JLabel("输出路径");
		quchuField2 = new JTextArea(1,13);
		jsp2 = new JScrollPane(quchuField2);
		jsp2.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		decryptionPanel.add(miyao2);
		decryptionPanel.add(miyaoField2);
		decryptionPanel.add(quchu2);
		decryptionPanel.add(jsp2);
		decryptionPanel.add(wenjian2);
		//wenjian2.addActionListener(this);
		wenjian2.addActionListener(new ActionListener(){  //监听事件
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();  //对话框
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = fileChooser.showOpenDialog(fileChooser);  //opendialog
                if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
                {
                		
                		String selectedFilepath = fileChooser.getSelectedFile().getAbsolutePath();  //取得选中的文件
                    File selectedFile = new File(selectedFilepath);
                    quchuField2.setText(selectedFile.getPath());   //取得路径
                }
            }
        });
		
		
		
		synopsisPanel = new JPanel();    //匹配摘要面板
		haxizhi = new JLabel("哈希值");
		haxizhiField = new JTextField(11);
		moshixuanze = new JLabel("模式选择");
		String[] ren = {"MD5","SHA-1","CRC32"};
		xlk = new JComboBox(ren);
		synopsisPanel.add(haxizhi);
		synopsisPanel.add(haxizhiField);
		synopsisPanel.add(moshixuanze);
		synopsisPanel.add(xlk);
		
				
		
		
		ControllTabbedPane.add("加密",encryptionPanel);
		ControllTabbedPane.add("解密",decryptionPanel);
		ControllTabbedPane.add("匹配摘要",synopsisPanel);
		
		
		//ControllTabbedPane.addChangeListener(this);
		
		
		
		this.add(ControllTabbedPane);
		this.setTitle("选项卡");
		this.setLocation(550, 700);
		this.setBounds(650,950,200,240);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		setOption(2);
		
	}
	
	public void stateChanged(ChangeEvent e){
		//获取外层选项卡窗格当前选中的选项卡索引
		int indexOuter = ControllTabbedPane.getSelectedIndex();
	}
	
	public static void main(String[] args) {
		new ControllTabbedPane();
	}
	
	public int getOption() {   //选项索引 0-加密，1-解密，2-匹配摘要
		return ControllTabbedPane.getSelectedIndex();
	}
	
	public void setOption(int n) {
		ControllTabbedPane.setSelectedIndex(n);
	}
	
	public String getPassword1() {       //返回加密秘钥
		return miyaoField1.getText();
	}

	public void setPassword1(String sr) {  //设置加密秘钥
		miyaoField1.setText(sr);
	}
	public String getPassword2() {        //解密秘钥
		return miyaoField2.getText();
	}
	
	public void setPassword2(String sr) {
		miyaoField2.setText(sr);
	}

	

	public String getHash() {            //哈希值
		return haxizhiField.getText();
	}

	public void setHash(String sr) {
		haxizhiField.setText(sr);
	}

	public String getWay1() {            //加密输出路径
		return quchuField1.getText();
	}

	public void setWay1(String sr) {
		quchuField1.setText(sr);
	}
	public String getWay2() {          //解密输出路径
		return quchuField2.getText();
	}
	
	public void setWay2(String sr) {  
		quchuField2.setText(sr);
	}

	

	public int getChoice() {          //选择模式索引：0-MD5,1-SHA-1,2-CRC32
		return xlk.getSelectedIndex();
	}

	public void setChoice(int n) {
		xlk.setSelectedIndex(n);
	}
	
}
