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
		//		+ "background-color:#E7EAEB;font-family:\"微软雅黑\",\"黑体\",\"宋体\";"
		//		+ "font-size:12px;height:36px;} </style></head>");
		builder.append("<body>温馨提示：<br>本软件采用MD5和DES加密算法<br>保证您保存的密码不会泄露."
				+ "<br>从此以后，您只要<b>记住唯一的登陆标示符</b>，就可以解除无数密码的困扰！</body>");
		builder.append("</html>");
		
		return builder.toString();
	}
}
