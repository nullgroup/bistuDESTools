package org.gnull.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * �ļ�������������
 * 
 * @author OSX
 * @date 2014.11.20
 *
 */
public class FileIOController {

	public FileIOController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���ı��������ƶ���·��, �����ļ��ĸ�ʽΪ'log_<��ǰʱ��>.txt' �÷�����������������·����ʾһ��Ŀ¼�����ļ�
	 * 
	 * @param text
	 *            ��Ҫ�������ı�
	 * @param dstPath
	 *            ����·��
	 */
	public void exportTextToFile(String text, String dstPath) {
		File file = new File(dstPath);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String filename = "log_" + df.format(cal.getTime()) + ".txt";

		if (file.isFile()) {
			String parent = file.getParent();
			file = new File(parent + File.separator + filename);
		} else {
			file = new File(file.getAbsolutePath()+ File.separator + filename);
		}

		export(text, file);
	}

	/**
	 * �÷�������ִ�е����ļ�����(Ŀǰ�����)
	 * 
	 * @param text
	 *            ��Ҫ�������ı�
	 * @param file
	 *            ����·��
	 */
	private void export(String text, File file) {
		// �����
		System.out.println("File: " + file.getAbsolutePath());
		System.out.println(text);
	}
}
