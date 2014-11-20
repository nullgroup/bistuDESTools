package org.gnull.message;

import java.util.Date;

public class MessagePacket {
	
	static private final String DELEMETER = "====================";
	static private final String VOID_STRING = "";
	
	private String mode;
	private Date date;
	private String fileName;
	private long size;
	private Date lastModified;
	private String outputPath;
	private String md5Value;
	private String sha1Value;
	private String crc32Value;
	
	private int state;

	public MessagePacket() {
		initField();
	}
	
	private void initField() {
		mode = VOID_STRING;
		date = null;
		fileName = VOID_STRING;
		lastModified = null;
		size = 0;
		outputPath = VOID_STRING;
		md5Value = VOID_STRING;
		sha1Value = VOID_STRING;
		crc32Value = VOID_STRING;
		state = 0;
	}

	public int getState() {
		return state;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getMd5Value() {
		return md5Value;
	}

	public void setMd5Value(String md5Value) {
		this.md5Value = md5Value;
		
		if (!this.md5Value.equals(VOID_STRING)) {
			state |= 4;
		}
	}

	public String getSha1Value() {
		return sha1Value;
	}

	public void setSha1Value(String sha1Value) {
		this.sha1Value = sha1Value;
		
		if (!this.sha1Value.equals(VOID_STRING)) {
			state |= 2;
		}
	}

	public String getCrc32Value() {
		return crc32Value;
	}

	public void setCrc32Value(String crc32Value) {
		this.crc32Value = crc32Value;
		
		if (!this.crc32Value.equals(VOID_STRING)) {
			state |= 1;
		}
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n模式: ");
		builder.append(mode);
		builder.append("\n时间: ");
		builder.append(date);
		builder.append("\n文件: ");
		builder.append(fileName);
		builder.append("\n大小: ");
		builder.append(size);
		builder.append("\n修改时间: ");
		builder.append(lastModified);
		
		if (mode.equals("加密") || mode.equals("解密")) {
			builder.append("输出路径");
			builder.append(outputPath);
		}
		
		if ((state & 4) == 4) {
			builder.append("\nMD5: ");
			builder.append(md5Value);
		}
		
		if ((state & 2) == 2) {
			builder.append("\nSHA-1: ");
			builder.append(sha1Value);
		}
		
		if ((state & 1) == 1) {
			builder.append("\nCRC32: ");
			builder.append(crc32Value);
		}
		
		builder.append('\n' + DELEMETER);
		
		return builder.toString();
	}

}
