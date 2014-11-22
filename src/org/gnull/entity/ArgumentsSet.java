package org.gnull.entity;

public class ArgumentsSet {

	private boolean md5;
	private boolean sha1;
	private boolean crc32;
	private boolean proMode;
	
	public ArgumentsSet() {
		this(false, false, false, false);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArgumentsSet [proMode=");
		builder.append(proMode);
		builder.append(", md5=");
		builder.append(md5);
		builder.append(", sha1=");
		builder.append(sha1);
		builder.append(", crc32=");
		builder.append(crc32);
		builder.append("]");
		return builder.toString();
	}

	public ArgumentsSet(boolean md5, boolean sha1, boolean crc32, boolean proMode) {
		this.md5 = md5;
		this.sha1 = sha1;
		this.crc32 = crc32;
		this.proMode = proMode;
	}

	public boolean isMd5() {
		return md5;
	}

	public void setMd5(boolean md5) {
		this.md5 = md5;
	}

	public boolean isSha1() {
		return sha1;
	}

	public void setSha1(boolean sha1) {
		this.sha1 = sha1;
	}

	public boolean isCrc32() {
		return crc32;
	}

	public void setCrc32(boolean crc32) {
		this.crc32 = crc32;
	}

	public boolean isProMode() {
		return proMode;
	}

	public void setProMode(boolean proMode) {
		this.proMode = proMode;
	}

}
