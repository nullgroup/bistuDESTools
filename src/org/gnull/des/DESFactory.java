package org.gnull.des;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DESFactory {
	
	static public final int BUFFER_SIZE = 8;

	private DESController con;
	
	public DESFactory() {
		setController(new DESController());
	}
	
	public DESController getController() {
		return con;
	}

	public void setController(DESController con) {
		this.con = con;
	}
	
	public long parseKey(String key) {
		return 0L;
	}

	public void doDES(String path, String key, int mode) {
		doDES(new File(path), key, mode);
	}
	
	public void doDES(File file, String key, int mode) {
		doDES(file, file, key, mode);
	}
	
	public void doDES(String srcPath, String dstPath, String key, int mode) {
		doDES(new File(srcPath), new File(dstPath), key, mode);
	}
	
	public void doDES(File srcFile, File dstFile, String key, int mode) {	
		FileInputStream in = null;
		FileOutputStream out = null;
		byte[] buffer = new byte[BUFFER_SIZE];
		
		long keyL = parseKey(key);
		
		try {
			if (!dstFile.exists()) {
				dstFile.createNewFile();
			}
			
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(dstFile);

			while (in.read(buffer) != -1) {
				
				long cip = con.doDES(doByteArrayToLong(buffer), keyL, mode);
				
				out.write(doLongToByteArray(cip));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public byte[] doLongToByteArray(long val) {
		byte[] b = new byte[8];

		b[0] = (byte) (val >> 56);
		b[1] = (byte) (val >> 48);
		b[2] = (byte) (val >> 40);
		b[3] = (byte) (val >> 32);
		b[4] = (byte) (val >> 24);
		b[5] = (byte) (val >> 16);
		b[6] = (byte) (val >>  8);
		b[7] = (byte) (val);
		
		return b;
	}
	
	public long doByteArrayToLong(byte[] b) {
		long val = 0L;
		
		long L1 = ((long) b[0] << 56);
		long L2 = ((long) b[1] << 56) >>>  8;
		long L3 = ((long) b[2] << 56) >>> 16;
		long L4 = ((long) b[3] << 56) >>> 24;
		long R1 = ((long) b[4] << 56) >>> 32;
		long R2 = ((long) b[5] << 56) >>> 40;
		long R3 = ((long) b[6] << 56) >>> 48;
		long R4 = ((long) b[7] << 56) >>> 56;
		
		val = L1 ^ L2 ^ L3 ^ L4 ^ R1 ^ R2 ^ R3 ^ R4;
		
		return val;
	}
	
	@SuppressWarnings("unused")
	private void copy(File srcFile, File dstFile) {
		
	}

	public static void main(String[] args) {
		DESFactory factory = new DESFactory();
		
		factory.doDES("d:\\test.txt", "d:\\out.txt", "", DESController.ENCRYPT);
		factory.doDES("d:\\out.txt", "d:\\test2.txt", "", DESController.DECRYPT);
		
		System.out.println("OK");
	}
}
