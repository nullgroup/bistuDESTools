package org.gnull.des;

/**
*
*    Encoder between String <- Transfer -> Long[]
*    @author Wuzhixuan
*    @date 2014/10/04
*    @last_modified 2014/11/04
*
*/
public class EncoderController {

	static public final int STRING_TO_LONG_ARRAY = 0xCC;
	static public final int LONG_ARRAY_TO_STRING = 0xCD;
	static public final byte COMPLEMENT_DATA = (byte) 0xA7;

	static public final int ASCII = 0xBA;
	static public final int UTF8  = 0xBB;
	static public final int UTF16 = 0xBC;
	
	public static void main(String[] args) {
		DESController des = new DESController();
		
		String clientSay  = "hello i am hugo";
		String sessionKey = "abcd";
		int correctLength = clientSay.length();

		long[] messageL = doTransfer(clientSay);
		long[] cipherL = new long[messageL.length];
		long actualKey = doTransfer(sessionKey)[0];

		// Encrypt
		for (int i = 0; i < messageL.length; i++) {
			cipherL[i] = des.doDES(messageL[i], actualKey, DESController.ENCRYPT);
		}

		String cip = doTransfer(cipherL);
		System.out.println("Cipher: " + cip);
		
		// TCP....
		
		long[] receivedL = doTransfer(cip);

		// Decrypt
		long[] tmpL = new long[messageL.length];
		for (int i = 0; i < messageL.length; i++) {
			tmpL[i] = des.doDES(receivedL[i], actualKey, DESController.DECRYPT);
		}
		
		String serverReceived = doTransfer(tmpL);
		String correctString = serverReceived.substring(0, correctLength);
		
		System.out.println(serverReceived);
		System.out.println("Server Received: " + correctString);
	}

	public static long[] doTransfer(String message) {
		// String -> Byte[]
		byte[] byteVal = doGetByteArray(message);

		// Check Length
		byteVal = doValidate(byteVal);

		// Byte[] -> Long[]
		long[] longVal = doLongAndByteTransfer(byteVal);
		return longVal;
	}

	public static String doTransfer(long[] longVal) {
		// Long[] -> Byte[]
		byte[] byteVal = doLongAndByteTransfer(longVal);

		// Byte[] -> String
		String output = doGetString(byteVal);
		return output;
	}

	public static long[] doLongAndByteTransfer(byte[] byteVal) {
		int byteLength = byteVal.length;
		long[] longVal = new long[byteLength / 8];

		for (int index = 0, i = 0; index < byteLength; index += 8, i++) {
			long L1 = ((long) byteVal[index + 0] << 56);
			long L2 = ((long) byteVal[index + 1] << 56) >>>  8;
			long L3 = ((long) byteVal[index + 2] << 56) >>> 16;
			long L4 = ((long) byteVal[index + 3] << 56) >>> 24;
			long R1 = ((long) byteVal[index + 4] << 56) >>> 32;
			long R2 = ((long) byteVal[index + 5] << 56) >>> 40;
			long R3 = ((long) byteVal[index + 6] << 56) >>> 48;
			long R4 = ((long) byteVal[index + 7] << 56) >>> 56;

			longVal[i] = L1 ^ L2 ^ L3 ^ L4 ^ R1 ^ R2 ^ R3 ^ R4;
		}

		return longVal;
	}

	public static byte[] doLongAndByteTransfer(long[] longVal) {
		int length = longVal.length * 8;
		byte[] byteVal = new byte[length];

		for (int index = 0, i = 0; i < longVal.length; index += 8, i++) {
			byteVal[index + 0] = (byte) (longVal[i] >> 56);
			byteVal[index + 1] = (byte) (longVal[i] >> 48);
			byteVal[index + 2] = (byte) (longVal[i] >> 40);
			byteVal[index + 3] = (byte) (longVal[i] >> 32);
			byteVal[index + 4] = (byte) (longVal[i] >> 24);
			byteVal[index + 5] = (byte) (longVal[i] >> 16);
			byteVal[index + 6] = (byte) (longVal[i] >>  8);
			byteVal[index + 7] = (byte) (longVal[i]);
		}

		return byteVal;
	}

	private static byte[] doValidate(byte[] byteVal) {
		int byteLength = byteVal.length;

		int rVal = byteLength % 8;
		if (rVal != 0) {
			byte[] newByteVal = new byte[byteLength + 8 - rVal];
			System.arraycopy(byteVal, 0, newByteVal, 0, byteLength);

			for (int i = 0; i < 8 - rVal; i++) {
				newByteVal[byteLength + i] = COMPLEMENT_DATA; // Platform Charset
			}

			byteVal = newByteVal;
		}

		return byteVal;
	}

	public static byte[] doGetByteArray(String message) {
		byte[] byteVal = new byte[message.length() * 2];

		for (int index = 0, i = 0; index < byteVal.length; index += 2, i++) {
			short token = (short) message.charAt(i);

			byteVal[index]     = (byte) (token >>> 8);
			byteVal[index + 1] = (byte)  token;
		}

		return byteVal;
	}

	public static String doGetString(byte[] byteVal) {
		if (byteVal.length % 2 != 0) {
			return "null";
		}

		StringBuffer b = new StringBuffer();

		for (int index = 0; index < byteVal.length; index += 2) {
			short LB = (short) (byteVal[index] << 8);
			short RB = (short) (byteVal[index + 1] & 0x00FF);

			b.append((char) (LB ^ RB));
		}

		return b.toString();
	}
}