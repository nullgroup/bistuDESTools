package org.gnull.entity;

import java.io.File;

import org.gnull.md5.MD5Controller;

public class ComputeTask extends AbstractTask {

	private String result;
	
	@SuppressWarnings("unused")
	private String targetDigest;

	private int mode;
	
	private int digMode;

	public ComputeTask(File target, int mode, int digMode, String[] params) {
		super(target);
		this.mode = mode;
	}

	/*
	 * Main task. Executed in background thread.
	 */
	@Override
	public Void doInBackground() {
		File file = super.getTarget();
		Void v = null;

		switch (mode) {
		case ENCRYPT_TASK:
			doEncrypt(file);
			break;
		case DECRYPT_TASK:
			doDecrypt(file);
			break;
		case DIGEST_MATCH_TASK:
			doDigest(file, digMode);
			break;
		}

		return v;
	}

	public Void doDigest(File file, int mode) {
		if (!file.exists()) {
			return null;
		}
		
		if (mode == MD5_MODE) {
			MD5Controller md5Con = super.getMd5Con();
			
			System.out.println("Md5 Start!");
			result = md5Con.doMD5();
		} else if (mode == SHA1_MODE) {
			
		} else if (mode == CRC32_MODE) {
			
		}

		return null;
	}

	public Void doEncrypt(File file) {
		return null;
	}

	public Void doDecrypt(File file) {
		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		System.out.println("Md5 Complete: " + result);
	}
}