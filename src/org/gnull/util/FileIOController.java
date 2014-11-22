package org.gnull.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件控制器工具类
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
	 * 将文本导出到制定的路径, 导出文件的格式为'log_<当前时间>.txt' 该方法期望参数给出的路径表示一个目录而非文件
	 * 
	 * @param text
	 *            需要导出的文本
	 * @param dstPath
	 *            导出路径
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
	 * 该方法真正执行导出文件操作(目前待完成)
	 * 
	 * @param text
	 *            需要导出的文本
	 * @param file
	 *            导出路径
	 */
	private void export(String text, File file) {
		// 待完成
		System.out.println("File: " + file.getAbsolutePath());
		System.out.println(text);
	}
}
