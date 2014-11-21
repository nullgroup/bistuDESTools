package org.gnull.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *
 * MD5 based on Java
 * 
 * @author Wuzhixuan
 * @date 2014/11/05
 * @last_modified 2014/11/10
 *
 *                Test Data
 *
 *                msg ""
 *                dig "d41d8cd98f00b204e9800998ecf8427e"
 *
 *                msg "a"
 *                dig "0cc175b9c0f1b6a831c399e269772661"
 *
 *                msg "abc"
 *                dig "900150983cd24fb0d6963f7d28e17f72"
 *
 *                msg "message digest"
 *                dig "f96b697d7cb7938d525a2f31aaf161d0"
 *
 *                msg "abcdefghijklmnopqrstuvwxyz"
 *                dig "c3fcd3d76192e4007dfb496cca67e13b"
 *
 *                msg "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
 *                dig "d174ab98d277d9f5a5611c2c9f419d9f"
 *
 *                msg "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
 *                dig "57edf4a22be3c955ac49da2e2107b67a"
 *
 *
 */
public class MD5Controller {

	/** 链接变量 */
	static public final long CV_A = 0x67452301L;
	static public final long CV_B = 0xEFCDAB89L;
	static public final long CV_C = 0x98BADCFEL;
	static public final long CV_D = 0x10325476L;

	static public final int BUFFER_SIZE = 64;

	static public final String HEX_VAL_UPPERCASE = "0123456789ABCDEF";
	static public final String HEX_VAL_LOWERCASE = "0123456789abcdef";
	
	public long TOTAL_BYTE = -1L;
	public long ACTUAL_BYTE = 0L;

	// 保存哈希值
	private long[] hash = null;

	public static void main(String[] args) throws UnsupportedEncodingException {
		MD5Controller md5 = new MD5Controller();
		System.out.printf(md5.doMD5(new File("D:\\00000.MTS"), true));
		md5.test();
	}

	public void test() throws UnsupportedEncodingException {
		String s1 = "abcdefghijklmnopqrstuvwxyz";
		String res = "c3fcd3d76192e4007dfb496cca67e13b";

		String md5 = doMD5(s1, false);

		System.out.println("---------- MD5 ----------");
		System.out.println("Message: " + s1);
		System.out.println("MD5:     " + md5);
		System.out.println("Correct: " + res);
	}

	public String doMD5(String message) throws UnsupportedEncodingException {
		return doMD5(message, false, Charset.defaultCharset());
	}

	public String doMD5(String message, boolean isUpperCase)
			throws UnsupportedEncodingException {
		return doMD5(message, isUpperCase, Charset.defaultCharset());
	}

	/**
	 * 对一个字符串以给定的编码方式编码成字节数组，并生成MD5值
	 * 
	 * @param message 消息值
	 * @param isUpperCase 输出是否以大写形式
	 * @param cs 编码方式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doMD5(String message, boolean isUpperCase, Charset cs)
			throws UnsupportedEncodingException {
		byte[] dataArray = doComplement(message.getBytes(cs)); // (N + 1) * 512
																// bit
		int packetCount = dataArray.length / 64;

		hash = new long[] { CV_A, CV_B, CV_C, CV_D }; // Initialization

		for (int i = 0; i < packetCount; i++) {
			// M.length == 16
			long[] M = doGetPacket(dataArray, i);
			doMD5Round(M);
		}

		String md5 = doGetMD5String(hash, isUpperCase);
		return md5;
	}

	public String doMD5(File file) {
		return doMD5(file, false);
	}
	
	private long check(File file) {
		long length = 0;
		
		length = file.length();
		int r = (int) (length % 64);
		
		long bitLength = r * 8;

		int rVal = (int) (bitLength % 512);
		if (bitLength == 448 || rVal != 448) {
			// if bitLength == 448
			// then comple 512 bit (64 byte)
			int compleByte = (bitLength == 448) ? 64 : (448 - rVal) / 8;

			// rVal > 448
			if (compleByte < 0) {
				compleByte += 64;
			}
			
			length += compleByte;
		}
		
		
		return length;
		
	}

	/**
	 * 对文件进行MD5运算，生成MD5值 
	 *
	 * @param file
	 * @param isUpperCase
	 * @return
	 */
	public String doMD5(File file, boolean isUpperCase) {
		FileInputStream in = null;
		byte[] buffer = new byte[BUFFER_SIZE];

		long byteLength = 0;

		hash = new long[] { CV_A, CV_B, CV_C, CV_D }; // Initialization
		TOTAL_BYTE = check(file);

		try {
			in = new FileInputStream(file);
			int actualBytes = 0;

			// if empty file
			// then return "d41d8cd98f00b204e9800998ecf8427e" directly
			if (file.length() == 0L) {
				return doMD5("", isUpperCase);
			}

			while ((actualBytes = in.read(buffer)) != -1) {
				byteLength += actualBytes;

				// Almost end of file
				if (actualBytes < BUFFER_SIZE) {
					buffer = doComplement(buffer, actualBytes, byteLength);
				}

				long[] M = doGetPacket(buffer, 0);
				doMD5Round(M);
				ACTUAL_BYTE += 64;
			}
		} catch (Exception e) {
			// There is a stub for Client class
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String md5 = doGetMD5String(hash, isUpperCase);
		return md5;
	}

	/**
	 * MD5轮函数，输入为16 * 4 = 64字节的数据块
	 * 对每一个M中的long元素，只有低32位有效
	 * @param M
	 */
	private void doMD5Round(long[] M) {

		long aVal = hash[0];
		long bVal = hash[1];
		long cVal = hash[2];
		long dVal = hash[3];

		// Round 1
		aVal = doFF(aVal, bVal, cVal, dVal, M[0],   7, 0xD76AA478L); // 1
		dVal = doFF(dVal, aVal, bVal, cVal, M[1],  12, 0xE8C7B756L); // 2
		cVal = doFF(cVal, dVal, aVal, bVal, M[2],  17, 0x242070DBL); // 3
		bVal = doFF(bVal, cVal, dVal, aVal, M[3],  22, 0xC1BDCEEEL); // 4
		aVal = doFF(aVal, bVal, cVal, dVal, M[4],   7, 0xF57C0FAFL); // 5
		dVal = doFF(dVal, aVal, bVal, cVal, M[5],  12, 0x4787C62AL); // 6
		cVal = doFF(cVal, dVal, aVal, bVal, M[6],  17, 0xA8304613L); // 7
		bVal = doFF(bVal, cVal, dVal, aVal, M[7],  22, 0xFD469501L); // 8
		aVal = doFF(aVal, bVal, cVal, dVal, M[8],   7, 0x698098D8L); // 9
		dVal = doFF(dVal, aVal, bVal, cVal, M[9],  12, 0x8B44F7AFL); // 10
		cVal = doFF(cVal, dVal, aVal, bVal, M[10], 17, 0xFFFF5BB1L); // 11
		bVal = doFF(bVal, cVal, dVal, aVal, M[11], 22, 0x895CD7BEL); // 12
		aVal = doFF(aVal, bVal, cVal, dVal, M[12],  7, 0x6B901122L); // 13
		dVal = doFF(dVal, aVal, bVal, cVal, M[13], 12, 0xFD987193L); // 14
		cVal = doFF(cVal, dVal, aVal, bVal, M[14], 17, 0xA679438EL); // 15
		bVal = doFF(bVal, cVal, dVal, aVal, M[15], 22, 0x49B40821L); // 16

		// Round 2
		aVal = doGG(aVal, bVal, cVal, dVal, M[1],   5, 0xF61E2562L); // 17
		dVal = doGG(dVal, aVal, bVal, cVal, M[6],   9, 0xC040B340L); // 18
		cVal = doGG(cVal, dVal, aVal, bVal, M[11], 14, 0x265E5A51L); // 19
		bVal = doGG(bVal, cVal, dVal, aVal, M[0],  20, 0xE9B6C7AAL); // 20
		aVal = doGG(aVal, bVal, cVal, dVal, M[5],   5, 0xD62F105DL); // 21
		dVal = doGG(dVal, aVal, bVal, cVal, M[10],  9, 0x02441453L); // 22
		cVal = doGG(cVal, dVal, aVal, bVal, M[15], 14, 0xD8A1E681L); // 23
		bVal = doGG(bVal, cVal, dVal, aVal, M[4],  20, 0xE7D3FBC8L); // 24
		aVal = doGG(aVal, bVal, cVal, dVal, M[9],   5, 0x21E1CDE6L); // 25
		dVal = doGG(dVal, aVal, bVal, cVal, M[14],  9, 0xC33707D6L); // 26
		cVal = doGG(cVal, dVal, aVal, bVal, M[3],  14, 0xF4D50D87L); // 27
		bVal = doGG(bVal, cVal, dVal, aVal, M[8],  20, 0x455A14EDL); // 28
		aVal = doGG(aVal, bVal, cVal, dVal, M[13],  5, 0xA9E3E905L); // 29
		dVal = doGG(dVal, aVal, bVal, cVal, M[2],   9, 0xFCEfA3F8L); // 30
		cVal = doGG(cVal, dVal, aVal, bVal, M[7],  14, 0x676f02D9L); // 31
		bVal = doGG(bVal, cVal, dVal, aVal, M[12], 20, 0x8D2A4C8AL); // 32

		// Round 3
		aVal = doHH(aVal, bVal, cVal, dVal, M[5],   4, 0xFFFA3942L); // 33
		dVal = doHH(dVal, aVal, bVal, cVal, M[8],  11, 0x8771F681L); // 34
		cVal = doHH(cVal, dVal, aVal, bVal, M[11], 16, 0x6D9D6122L); // 35
		bVal = doHH(bVal, cVal, dVal, aVal, M[14], 23, 0xFDE5380CL); // 36
		aVal = doHH(aVal, bVal, cVal, dVal, M[1],   4, 0xA4BEEA44L); // 37
		dVal = doHH(dVal, aVal, bVal, cVal, M[4],  11, 0x4BDECFA9L); // 38
		cVal = doHH(cVal, dVal, aVal, bVal, M[7],  16, 0xF6BB4B60L); // 39
		bVal = doHH(bVal, cVal, dVal, aVal, M[10], 23, 0xBEBFBC70L); // 40
		aVal = doHH(aVal, bVal, cVal, dVal, M[13],  4, 0x289B7EC6L); // 41
		dVal = doHH(dVal, aVal, bVal, cVal, M[0],  11, 0xEAA127FAL); // 42
		cVal = doHH(cVal, dVal, aVal, bVal, M[3],  16, 0xD4EF3085L); // 43
		bVal = doHH(bVal, cVal, dVal, aVal, M[6],  23, 0x04881D05L); // 44
		aVal = doHH(aVal, bVal, cVal, dVal, M[9],   4, 0xD9D4D039L); // 45
		dVal = doHH(dVal, aVal, bVal, cVal, M[12], 11, 0xE6DB99E5L); // 46
		cVal = doHH(cVal, dVal, aVal, bVal, M[15], 16, 0x1FA27CF8L); // 47
		bVal = doHH(bVal, cVal, dVal, aVal, M[2],  23, 0xC4AC5665L); // 48

		// Round 4
		aVal = doII(aVal, bVal, cVal, dVal, M[0],   6, 0xF4292244L); // 49
		dVal = doII(dVal, aVal, bVal, cVal, M[7],  10, 0x432AFF97L); // 50
		cVal = doII(cVal, dVal, aVal, bVal, M[14], 15, 0xAB9423A7L); // 51
		bVal = doII(bVal, cVal, dVal, aVal, M[5],  21, 0xFC93A039L); // 52
		aVal = doII(aVal, bVal, cVal, dVal, M[12],  6, 0x655B59C3L); // 53
		dVal = doII(dVal, aVal, bVal, cVal, M[3],  10, 0x8F0CCC92L); // 54
		cVal = doII(cVal, dVal, aVal, bVal, M[10], 15, 0xFFEFF47DL); // 55
		bVal = doII(bVal, cVal, dVal, aVal, M[1],  21, 0x85845DD1L); // 56
		aVal = doII(aVal, bVal, cVal, dVal, M[8],   6, 0x6FA87E4FL); // 57
		dVal = doII(dVal, aVal, bVal, cVal, M[15], 10, 0xFE2CE6E0L); // 58
		cVal = doII(cVal, dVal, aVal, bVal, M[6],  15, 0xA3014314L); // 59
		bVal = doII(bVal, cVal, dVal, aVal, M[13], 21, 0x4E0811A1L); // 60
		aVal = doII(aVal, bVal, cVal, dVal, M[4],   6, 0xF7537E82L); // 61
		dVal = doII(dVal, aVal, bVal, cVal, M[11], 10, 0xBD3AF235L); // 62
		cVal = doII(cVal, dVal, aVal, bVal, M[2],  15, 0x2AD7D2BBL); // 63
		bVal = doII(bVal, cVal, dVal, aVal, M[9],  21, 0xEB86D391L); // 64

		hash[0] += aVal;
		hash[1] += bVal;
		hash[2] += cVal;
		hash[3] += dVal;
	}

	private long doF(long xVal, long yVal, long zVal) {
		return (xVal & yVal) | ((~xVal) & zVal);
	}

	private long doG(long xVal, long yVal, long zVal) {
		return (xVal & zVal) | (yVal & (~zVal));
	}

	private long doH(long xVal, long yVal, long zVal) {
		return xVal ^ yVal ^ zVal;
	}

	private long doI(long xVal, long yVal, long zVal) {
		return yVal ^ (xVal | (~zVal));
	}

	/**
	 * FF(a, b, c, d, Mj, s, ti) -> a = b + ((a + F(b, c, d) + Mj + ti) <<< s)
	 */
	private long doFF(long aVal, long bVal, long cVal, long dVal, long mVal,
			long sVal, long tVal) {

		aVal += doF(bVal, cVal, dVal) + mVal + tVal;
		aVal = ((int) aVal << sVal) | ((int) aVal >>> (32 - sVal));
		aVal += bVal;

		return aVal;
	}

	/**
	 * GG(a, b, c, d, Mj, s, ti) -> a = b + ((a + G(b, c, d) + Mj + ti) <<< s)
	 */
	private long doGG(long aVal, long bVal, long cVal, long dVal, long mVal,
			long sVal, long tVal) {

		aVal += doG(bVal, cVal, dVal) + mVal + tVal;
		aVal = ((int) aVal << sVal) | ((int) aVal >>> (32 - sVal));
		aVal += bVal;

		return aVal;
	}

	/**
	 * HH(a, b, c, d, Mj, s, ti) -> a = b + ((a + H(b, c, d) + Mj + ti) <<< s)
	 */
	private long doHH(long aVal, long bVal, long cVal, long dVal, long mVal,
			long sVal, long tVal) {

		aVal += doH(bVal, cVal, dVal) + mVal + tVal;
		aVal = ((int) aVal << sVal) | ((int) aVal >>> (32 - sVal));
		aVal += bVal;

		return aVal;
	}

	/**
	 * II(a, b, c, d, Mj, s, ti) -> a = b + ((a + I(b, c, d) + Mj + ti) <<< s)
	 */
	private long doII(long aVal, long bVal, long cVal, long dVal, long mVal,
			long sVal, long tVal) {

		aVal += doI(bVal, cVal, dVal) + mVal + tVal;
		aVal = ((int) aVal << sVal) | ((int) aVal >>> (32 - sVal));
		aVal += bVal;

		return aVal;
	}

	/**
	 * 对给定的字节数组进行补位(用于字符串)
	 * @param dataArray
	 * @return
	 */
	public byte[] doComplement(byte[] dataArray) {
		return doComplement(dataArray, dataArray.length);
	}

	/**
	 * 对给定的字节数组进行给定的补位(用于文件)
	 * @param dataArray
	 * @param byteLength
	 * @return
	 */
	private byte[] doComplement(byte[] dataArray, long byteLength) {
		long bitLength = byteLength * 8;

		int rVal = (int) (bitLength % 512);
		if (bitLength == 448 || rVal != 448) {
			// if bitLength == 448
			// then comple 512 bit (64 byte)
			int compleByte = (bitLength == 448) ? 64 : (448 - rVal) / 8;

			// rVal > 448
			if (compleByte < 0) {
				compleByte += 64;
			}

			if (dataArray.length != byteLength) {
				byteLength = dataArray.length;
			}

			byte[] newByteArray = new byte[(int) (byteLength + compleByte)];

			System.arraycopy(dataArray, 0, newByteArray, 0, (int) byteLength);
			newByteArray[(int) byteLength] = (byte) 0x80;

			for (int i = (int) (byteLength + 1); i < newByteArray.length; i++) {
				newByteArray[i] = (byte) 0x0;
			}

			dataArray = newByteArray;
		}

		// Little-Endian
		byte[] lengthArray = doGetLengthArray(bitLength);

		return doConcatArray(dataArray, lengthArray);
	}

	/**
	 * 对给定的字节数组进行给定的补位(用于文件)，会先截取实际有效的字节数
	 * @param dataArray
	 * @param actualBytes
	 * @param byteLength
	 * @return
	 */
	private byte[] doComplement(byte[] dataArray, int actualBytes,
			long byteLength) {
		byte[] newByteArray = new byte[actualBytes];

		System.arraycopy(dataArray, 0, newByteArray, 0, actualBytes);

		return doComplement(newByteArray, byteLength);
	}

	/**
	 * 将一个long变量转换为长度为8的字节数组
	 * @param bitLengthL
	 * @return
	 */
	public byte[] doGetLengthArray(long bitLengthL) {
		byte[] lengthArray = new byte[8];

		lengthArray[0] = (byte) bitLengthL;
		lengthArray[1] = (byte) (bitLengthL >>> 8);
		lengthArray[2] = (byte) (bitLengthL >>> 16);
		lengthArray[3] = (byte) (bitLengthL >>> 24);
		lengthArray[4] = (byte) (bitLengthL >>> 32);
		lengthArray[5] = (byte) (bitLengthL >>> 40);
		lengthArray[6] = (byte) (bitLengthL >>> 48);
		lengthArray[7] = (byte) (bitLengthL >>> 56);

		return lengthArray;
	}

	/**
	 * 将两个字节数组连接起来
	 * @param dataArray
	 * @param lengthArray
	 * @return
	 */
	public byte[] doConcatArray(byte[] dataArray, byte[] lengthArray) {
		byte[] byteArray = new byte[dataArray.length + lengthArray.length];

		System.arraycopy(dataArray, 0, byteArray, 0, dataArray.length);
		System.arraycopy(lengthArray, 0, byteArray, dataArray.length,
				lengthArray.length);

		return byteArray;
	}

	/**
	 * 对源数据获取特定轮数的数据块
	 * @param byteArray 源数据
	 * @param stepCount 轮数
	 * @return
	 */
	private long[] doGetPacket(byte[] byteArray, int stepCount) {
		long[] M = new long[16];

		for (int j = 0, i = stepCount * 64; j < 16; j++, i += 4) {
			long L1 = ((long) byteArray[i + 3] << 24) & 0xFF000000L;
			long L2 = ((long) byteArray[i + 2] << 16) & 0xFF0000L;
			long R1 = ((long) byteArray[i + 1] << 8) & 0xFF00L;
			long R2 = ((long) byteArray[i + 0]) & 0xFFL;

			M[j] = L1 ^ L2 ^ R1 ^ R2;
		}

		return M;
	}

	/**
	 * 将哈希值输出转化为字符串表示
	 * @param a
	 * @param isUpperCase
	 * @return
	 */
	private String doGetMD5String(long[] a, boolean isUpperCase) {
		StringBuffer md5 = new StringBuffer();

		for (int i = 0; i < a.length; i++) {
			md5.append(doByteToHex((byte) a[i], isUpperCase));
			md5.append(doByteToHex((byte) (a[i] >>> 8), isUpperCase));
			md5.append(doByteToHex((byte) (a[i] >>> 16), isUpperCase));
			md5.append(doByteToHex((byte) (a[i] >>> 24), isUpperCase));
		}

		return md5.toString();
	}

	/**
	 * eg. b = 0xCC, return "CC" or "cc" (depends on param 'isUpperCase')
	 * 
	 * @param b
	 * @param isUpperCase
	 * @return
	 */
	private String doByteToHex(byte b, boolean isUpperCase) {
		final String HEX_VAL = (isUpperCase) ? HEX_VAL_UPPERCASE
				: HEX_VAL_LOWERCASE;

		StringBuffer hex = new StringBuffer();

		// signed bVal -> unsigned bVal
		int bVal = (b < 0) ? (b & 0x7F + 128) : b;

		int LV = bVal >>> 4; // LV = bVal / 16
		int RV = bVal & 0xF; // rv = BVal % 16

		hex.append(HEX_VAL.charAt(LV)).append(HEX_VAL.charAt(RV));

		return hex.toString();
	}
}