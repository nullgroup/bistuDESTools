package org.gnull.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileIOController {

	public FileIOController() {
		// TODO Auto-generated constructor stub
	}
	
	public void exportTextToFile(String text, String dstPath) {
		File file = new File(dstPath);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String filename = "log_" + df.format(cal.getTime()) + ".txt";
		
		if (file.isFile()) {
			String parent = file.getParent();
			file = new File(parent + File.pathSeparator + filename);
		} else {
			file = new File(file.getAbsolutePath() + filename);
		}
		 
		export(text, file);
	}

	private void export(String text, File file) {
		System.out.println("File: " + file.getAbsolutePath());
		System.out.println(text);
	}
}
