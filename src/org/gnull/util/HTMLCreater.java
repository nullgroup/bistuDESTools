package org.gnull.util;

public class HTMLCreater {

	public static String createHTMLText(String string) {
		if (string.equals("loginframe.warningtext")) {
			return createWarningText();
		}
		return null;
	}
	private static String createWarningText() {
		StringBuilder builder = new StringBuilder(256);
		
		builder.append("<html>");
		// builder.append("<head><style> body {margin:0px; padding:0px;"
		//		+ "background-color:#E7EAEB;font-family:\"΢���ź�\",\"����\",\"����\";"
		//		+ "font-size:12px;height:36px;} </style></head>");
		builder.append("<body>��ܰ��ʾ��<br>���������MD5��DES�����㷨<br>��֤����������벻��й¶."
				+ "<br>�Ӵ��Ժ���ֻҪ<b>��סΨһ�ĵ�½��ʾ��</b>���Ϳ��Խ��������������ţ�</body>");
		builder.append("</html>");
		
		return builder.toString();
	}
}
