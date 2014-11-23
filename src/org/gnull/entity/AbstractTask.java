package org.gnull.entity;

import java.io.File;

import javax.swing.SwingWorker;

import org.gnull.des.DESController;
import org.gnull.md5.MD5Controller;

public abstract class AbstractTask extends SwingWorker<Void, Void> {

	static public final int ENCRYPT_TASK = 0xA;
	
	static public final int DECRYPT_TASK = 0xB;
	
	static public final int DIGEST_MATCH_TASK = 0xC;
	

	static public final int MD5_MODE = 0xC1;

	static public final int SHA1_MODE = 0xC2;

	static public final int CRC32_MODE = 0xC3;
	
	private MD5Controller md5Con;
	
	private DESController desCon;
	
	private File target;
	
	public AbstractTask() {
		this(null);
	}
	
	public AbstractTask(File target) {
		this.target = target;
		md5Con = new MD5Controller(target);
		desCon = new DESController();
	}

	public MD5Controller getMd5Con() {
		return md5Con;
	}

	public void setMd5Con(MD5Controller md5Con) {
		this.md5Con = md5Con;
	}

	public DESController getDesCon() {
		return desCon;
	}

	public void setDesCon(DESController desCon) {
		this.desCon = desCon;
	}

	public File getTarget() {
		return target;
	}

	public void setTarget(File target) {
		this.target = target;
	}

}
