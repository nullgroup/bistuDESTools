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
		
		
		encryptionPanel = new JPanel();            //�������
		encryptionPanel.setLayout(new FlowLayout());
		miyao1 = new JLabel("��Կ");
		miyaoField1 = new JTextField(13);
		quchu1 = new JLabel("���·��");
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
		wenjian1.addActionListener(new ActionListener(){  //�����¼�
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();  //�Ի���
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = fileChooser.showOpenDialog(fileChooser);  //opendialog
                if(i==JFileChooser.APPROVE_OPTION)  //�ж��Ƿ�Ϊ�򿪵İ�ť
                {
                		
                		String selectedFilepath = fileChooser.getSelectedFile().getAbsolutePath();  //ȡ��ѡ�е��ļ�
                    File selectedFile = new File(selectedFilepath);
                    quchuField1.setText(selectedFile.getPath());   //ȡ��·��
                }
            }
        });
		
		decryptionPanel = new JPanel();                 //�������
		decryptionPanel.setLayout(new FlowLayout());
		miyao2 = new JLabel("��Կ");
		miyaoField2 = new JTextField(13);
		quchu2 = new JLabel("���·��");
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
		wenjian2.addActionListener(new ActionListener(){  //�����¼�
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();  //�Ի���
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = fileChooser.showOpenDialog(fileChooser);  //opendialog
                if(i==JFileChooser.APPROVE_OPTION)  //�ж��Ƿ�Ϊ�򿪵İ�ť
                {
                		
                		String selectedFilepath = fileChooser.getSelectedFile().getAbsolutePath();  //ȡ��ѡ�е��ļ�
                    File selectedFile = new File(selectedFilepath);
                    quchuField2.setText(selectedFile.getPath());   //ȡ��·��
                }
            }
        });
		
		
		
		synopsisPanel = new JPanel();    //ƥ��ժҪ���
		haxizhi = new JLabel("��ϣֵ");
		haxizhiField = new JTextField(11);
		moshixuanze = new JLabel("ģʽѡ��");
		String[] ren = {"MD5","SHA-1","CRC32"};
		xlk = new JComboBox(ren);
		synopsisPanel.add(haxizhi);
		synopsisPanel.add(haxizhiField);
		synopsisPanel.add(moshixuanze);
		synopsisPanel.add(xlk);
		
				
		
		
		ControllTabbedPane.add("����",encryptionPanel);
		ControllTabbedPane.add("����",decryptionPanel);
		ControllTabbedPane.add("ƥ��ժҪ",synopsisPanel);
		
		
		//ControllTabbedPane.addChangeListener(this);
		
		
		
		this.add(ControllTabbedPane);
		this.setTitle("ѡ�");
		this.setLocation(550, 700);
		this.setBounds(650,950,200,240);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		setOption(2);
		
	}
	
	public void stateChanged(ChangeEvent e){
		//��ȡ���ѡ�����ǰѡ�е�ѡ�����
		int indexOuter = ControllTabbedPane.getSelectedIndex();
	}
	
	public static void main(String[] args) {
		new ControllTabbedPane();
	}
	
	public int getOption() {   //ѡ������ 0-���ܣ�1-���ܣ�2-ƥ��ժҪ
		return ControllTabbedPane.getSelectedIndex();
	}
	
	public void setOption(int n) {
		ControllTabbedPane.setSelectedIndex(n);
	}
	
	public String getPassword1() {       //���ؼ�����Կ
		return miyaoField1.getText();
	}

	public void setPassword1(String sr) {  //���ü�����Կ
		miyaoField1.setText(sr);
	}
	public String getPassword2() {        //������Կ
		return miyaoField2.getText();
	}
	
	public void setPassword2(String sr) {
		miyaoField2.setText(sr);
	}

	

	public String getHash() {            //��ϣֵ
		return haxizhiField.getText();
	}

	public void setHash(String sr) {
		haxizhiField.setText(sr);
	}

	public String getWay1() {            //�������·��
		return quchuField1.getText();
	}

	public void setWay1(String sr) {
		quchuField1.setText(sr);
	}
	public String getWay2() {          //�������·��
		return quchuField2.getText();
	}
	
	public void setWay2(String sr) {  
		quchuField2.setText(sr);
	}

	

	public int getChoice() {          //ѡ��ģʽ������0-MD5,1-SHA-1,2-CRC32
		return xlk.getSelectedIndex();
	}

	public void setChoice(int n) {
		xlk.setSelectedIndex(n);
	}
	
}
