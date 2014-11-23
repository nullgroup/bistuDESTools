package org.gnull.entity;

import java.io.File;

import javax.swing.SwingWorker;

import org.gnull.des.DESController;
import org.gnull.md5.MD5Controller;

/**
 * 该类完成任务的封装 
 *
 * @author Wuzhixuan
 * @date 2014/11/23
 *
 */
public abstract class AbstractTask extends SwingWorker<Void, Void> {

	/** 加密模式 */
	static public final int ENCRYPT_TASK = 0xA;

	/** 解密模式 */	
	static public final int DECRYPT_TASK = 0xB;

	/** 匹配摘要模式 */
	static public final int DIGEST_MATCH_TASK = 0xC;
	

	/** 匹配摘要模式中的MD5匹配 */
	static public final int MD5_MODE = 0xC1;

	/** 匹配摘要模式中的SHA-1匹配 */
	static public final int SHA1_MODE = 0xC2;

	/** 匹配摘要模式中的CRC32匹配 */
	static public final int CRC32_MODE = 0xC3;
	
	private MD5Controller md5Con;
	
	private DESController desCon;

	/** 被选中的目标文件, 程序将以该文件为操作对象 */
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