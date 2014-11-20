package org.gnull.des;

/**
 *
 *    DES based on Java
 *    @author Wuzhixuan
 *    @date 2014/9/17
 *    @last_modified 2014/11/04
 *
 *    Test Data
 *    key              plaintext        ciphertext
 *
 *    0000000000000000 0000000000000000 8CA64DE9C1B123A7
 *    FFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFF 7359B2163E4EDC58
 *    3000000000000000 1000000000000001 958E6E627A05557B
 *    1111111111111111 1111111111111111 F40379AB9E0EC533
 *    0123456789ABCDEF 1111111111111111 17668DFC7292532D
 *    1111111111111111 0123456789ABCDEF 8A5AE1F81AB8F2DD
 *    0000000000000000 0000000000000000 8CA64DE9C1B123A7
 *    FEDCBA9876543210 0123456789ABCDEF ED39D950FA74BCC4
 *    7CA110454A1A6E57 01A1D6D039776742 690F5B0D9A26939B
 *    0131D9619DC1376E 5CD54CA83DEF57DA 7A389D10354BD271
 *    07A1133E4A0B2686 0248D43806F67172 868EBB51CAB4599A
 *    3849674C2602319E 51454B582DDF440A 7178876E01F19B2A
 *    04B915BA43FEB5B6 42FD443059577FA2 AF37FB421F8C4095
 *    0113B970FD34F2CE 059B5E0851CF143A 86A560F10EC6D85B
 *    0170F175468FB5E6 0756D8E0774761D2 0CD3DA020021DC09
 *    43297FAD38E373FE 762514B829BF486A EA676B2CB7DB2B7A
 *    07A7137045DA2A16 3BDD119049372802 DFD64A815CAF1A0F
 *    04689104C2FD3B2F 26955F6835AF609A 5C513C9C4886C088
 *    37D06BB516CB7546 164D5E404F275232 0A2AEEAE3FF4AB77
 *    1F08260D1AC2465E 6B056E18759F5CCA EF1BF03E5DFA575A
 *    584023641ABA6176 004BD6EF09176062 88BF0DB6D70DEE56
 *    025816164629B007 480D39006EE762F2 A1F9915541020B56
 *    49793EBC79B3258F 437540C8698F3CFA 6FBF1CAFCFFD0556
 *    4FB05E1515AB73A7 072D43A077075292 2F22E49BAB7CA1AC
 *    49E95D6D4CA229BF 02FE55778117F12A 5A6B612CC26CCE4A
 *    018310DC409B26D6 1D9D5C5018F728C2 5F4C038ED12B2E41
 *    1C587F1C13924FEF 305532286D6F295A 63FAC0D034D9F793
 *    0101010101010101 0123456789ABCDEF 617B3A0CE8F07100
 *    1F1F1F1F0E0E0E0E 0123456789ABCDEF DB958605F8C8C606
 *    E0FEE0FEF1FEF1FE 0123456789ABCDEF EDBFD1C66C29CCC7
 *    0000000000000000 FFFFFFFFFFFFFFFF 355550B2150E2451
 *    FFFFFFFFFFFFFFFF 0000000000000000 CAAAAF4DEAF1DBAE
 *    0123456789ABCDEF 0000000000000000 D5D44FF720683D0D
 *    FEDCBA9876543210 FFFFFFFFFFFFFFFF 2A2BB008DF97C2F2
 *
 */

public class DESController {

	public static final int ENCRYPT = 0xCDC;
	public static final int DECRYPT = 0xDCD;
	
	public static void main(String[] args) {
		DESController des = new DESController();
		long data = 0x437540C8698F3CFAL;
		long key = 0x49793EBC79B3258FL;
		
		long cip = des.doDES(data, key, DESController.ENCRYPT);
		
		System.out.println(Long.toString(cip, 16));
	}

	/**
	 * DES Main Method
	 * @param msg  64bit message
	 * @param key  64bit cipher
	 * @param mode
	 */
	public long doDES(long data, long key, int mode) {
		long  dVector = doDisplace(data);   // IP
		long[] subKey = doGetSubkey(key);   // Generate Subkey

		int lMsg = (int) (dVector >>> 32);  // Left  32bit
		int rMsg = (int)  dVector;          // Right 32bit

		int round = 0;                      // Round

		if (ENCRYPT == mode) { // Encrypt Mode

			do {
				int tmpMsg = lMsg;
				lMsg = rMsg;
				// Ri = Li-1 ^ f(Ri-1, Ki)
				rMsg = tmpMsg ^ fTrans(rMsg, subKey[round++]);
			} while (round < 15);

			lMsg ^= fTrans(rMsg, subKey[round]); // Round Function

		} else {               // Decrypt Mode

			do {
				int tmpMsg = lMsg;
				lMsg = rMsg;
				// Ri = Li-1 ^ f(Ri-1, Ki)
				rMsg = tmpMsg ^ fTrans(rMsg, subKey[15 - round]);
				round++;
			} while (round < 15);

			lMsg ^= fTrans(rMsg, subKey[15 - round]); // Round Function
		}
	
		long dCipher = doAntiDisplace(lMsg, rMsg);    // IP-1
		return dCipher;
	}

	/**
	 * 64bit -> 64bit IP
	 */
	private long doDisplace(long msg) {
		long dVector = 0L;

		for (int index = 0; index < displaceTable.length; index++) {
			long bitVal = msg & (1L << (64 - displaceTable[index]));

			if (bitVal != 0L) {
				dVector ^= 1L << (63 - index);
			}
		}

		return dVector;
	}

	/**
	 * 64bit -> 64bit IP-1
	 */
	private long doAntiDisplace(int lMsg, int rMsg) {
		long rMsgL = rMsg;
		rMsgL = (rMsgL << 32) >>> 32; //

		long inMsg = (((long) lMsg) << 32) + rMsgL;
		long dVector = 0L;

		for (int index = 0; index < antiDisplaceTable.length; index++) {
			long bitVal = inMsg & (1L << (64 - antiDisplaceTable[index]));

			if (bitVal != 0L) {
				dVector ^= 1L << (63 - index);
			}
		}

		return dVector;
	}

	/**
	 * 1. Ri-1 from 32 to 48 by EBOX
	 * 2. #1 + Ki
	 * 3. #2 by SBOX and by P
	 */
	private int fTrans(int rMsg, long roundKey) {
		long eVector = doExpand(rMsg);             // 32bit -> 48bit
		int  sVector = doSBox(eVector ^ roundKey); // 48bit -> 32bit
		int  pVector = doPBox(sVector);            // 32bit -> 32bit
		return pVector;
	}

	/**
	 * 32bit -> 48bit Expand
	 */
	private long doExpand(int rMsg) {
		long eVector = 0L;

		for (int index = 0; index < expandTable.length; index++) {
			int bitVal = rMsg & (1 << (32 - expandTable[index]));

			if (bitVal != 0) {
				eVector ^= 1L << (47 - index);
			}
		}

		return eVector;
	}

	/**
	 * 48bit -> 32bit SBOX
	 */
	private int doSBox(long eVector) {
		int sVector = 0;

		for (int index = sBox.length - 1; index >= 0 ; index--) {
			int offset1 = 6 * (7 - index);
			int offset2 = 4 * (7 - index);

			// 077L(8) == 111111(2)
			byte sixVal = (byte) ((eVector >>> offset1) & 077L);
			int[][] box = sBox[index];

			sVector ^= doSixToFour(sixVal, box) << offset2;
		}

		return sVector;
	}

	/**
	 * 32bit -> 32bit PBOX
	 */
	private int doPBox(int sVector) {
		int pVector = 0;

		for (int index = 0; index < pTable.length; index++) {
			int bitVal = sVector & (1 << (32 - pTable[index]));

			if (bitVal != 0) {
				pVector ^= 1 << (31 - index);
			}
		}

		return pVector;
	}

	/**
	 * 6bit -> 4bit
	 */
	private int doSixToFour(byte bVal, int[][] box) {
		int[] a = new int[6];
		int offset = 1;

		for (int i = 5; i >= 0; i--) {
			a[i] = (0 == (bVal & offset)) ? 0 : 1;
			offset = offset << 1;	
		}

		int xVal = a[5] + a[0] * 2;
		int yVal = a[4] + a[3] * 2 + a[2] * 4 + a[1] * 8;

		return box[xVal][yVal];
	}

	/**
	 * Generate Subkey
	 */
	private long[] doGetSubkey(long key) {
		long[] subKey = new long[16];
		long pcVector = doPc1Displace(key);

		int LK = (int) (pcVector >>> 28) & 0x0FFFFFFF;
		int RK = (int) (pcVector & 0x0FFFFFFF);

		for (int round = 0; round < 16; round++) {
			int dist = rotateTable[round];

			LK = doLRotate(LK, dist);
			RK = doLRotate(RK, dist);

			subKey[round] = doPc2Displace(LK, RK);
		}

		return subKey;
	}

	/**
	 * 64bit -> 56bit PC1
	 */
	private long doPc1Displace(long key) {
		long pcVector = 0L;

		for (int index = 0; index < dTable1.length; index++) {
			long bitVal = key & (1L << (64 - dTable1[index]));

			if (bitVal != 0L) {
				pcVector ^= 1L << (55 - index);
			}
		}

		return pcVector;
	}

	/**
	 * 56bit -> 48bit PC2
	 */
	private long doPc2Displace(int lKey, int rKey) {
		long inKey = (((long) lKey) << 28) + (rKey & 0x0FFFFFFF);
		long pcVector = 0L;

		for (int index = 0; index < dTable2.length; index++) {
			long bitVal = inKey & (1L << (56 - dTable2[index]));

			if (bitVal != 0L) {
				pcVector ^= 1L << (47 - index);
			}
		}

		return pcVector;
	}

	/**
	 * Cyclic Left Shift
	 */
	private int doLRotate(int inKey, int distance) {
		for (int round = 0; round < distance; round++) {
			int bitVal = (inKey >>> 27) & 1;

			if (bitVal != 0) {
				inKey &= 0x07FFFFFF;
				inKey <<= 1;
				inKey ^= 1;
			} else {
				inKey <<= 1;
			}
		}

		return inKey;
	}

	public static final int[] displaceTable = {
		58, 50, 42, 34, 26, 18, 10, 2,
		60, 52, 44, 36, 28, 20, 12, 4,
		62, 54, 46, 38, 30, 22, 14, 6,
		64, 56, 48, 40, 32, 24, 16, 8,
		57, 49, 41, 33, 25, 17,  9, 1,
		59, 51, 43, 35, 27, 19, 11, 3,
		61, 53, 45, 37, 29, 21, 13, 5,
		63, 55, 47, 39, 31, 23, 15, 7
	};

	public static final int[] antiDisplaceTable = {
		40, 8, 48, 16, 56, 24, 64, 32, 
		39, 7, 47, 15, 55, 23, 63, 31, 
		38, 6, 46, 14, 54, 22, 62, 30, 
		37, 5, 45, 13, 53, 21, 61, 29, 
		36, 4, 44, 12, 52, 20, 60, 28, 
		35, 3, 43, 11, 51, 19, 59, 27, 
		34, 2, 42, 10, 50, 18, 58, 26, 
		33, 1, 41,  9, 49, 17, 57, 25
	};

	public static final int[] expandTable = {
		32, 1,   2,  3,  4,  5,
		 4, 5,   6,  7,  8,  9,
		 8, 9,  10, 11, 12, 13,
		12, 13, 14, 15, 16, 17,
		16, 17, 18, 19, 20, 21,
		20, 21, 22, 23, 24, 25,
		24, 25, 26, 27, 28, 29,
		28, 29, 30, 31, 32,  1
	};

	public static final int[] pTable = {
		16, 7,  20, 21, 
		29, 12, 28, 17, 
		 1, 15, 23, 26, 
		 5, 18, 31, 10, 
		 2,  8, 24, 14, 
		32, 27,  3,  9, 
		19, 13, 30,  6, 
		22, 11,  4, 25
	};

	public static final int[][][] sBox = {
		// S1
		{
			{14, 4,	13,	1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
			{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
			{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
			{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
		},
		// S2
		{
			{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
			{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
			{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
			{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
		},
		// S3
		{
			{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
			{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
			{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
			{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
		},
		// S4
		{
			{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
			{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
			{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
			{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
		},
		// S5
		{
			{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
			{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
			{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
			{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
		},
		// S6
		{
			{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
			{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
			{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
			{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
		},
		// S7
		{
			{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
			{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
			{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
			{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
		},
		// S8
		{
			{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
			{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
			{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
			{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
		}
	};

	public static final int[] dTable1 = {
		57, 49, 41, 33, 25, 17,  9,
		1,  58, 50, 42, 34, 26, 18,
		10,  2, 59, 51, 43, 35, 27,
		19, 11,  3, 60, 52, 44, 36,
		63, 55, 47, 39, 31, 23, 15,
		7,  62, 54, 46, 38, 30, 22,
		14,  6, 61, 53, 45, 37, 29,
		21, 13,  5, 28, 20, 12,  4
	};

	public static final int[] dTable2 = {
		14, 17, 11, 24,  1,  5,
		3,  28, 15,  6, 21, 10,
		23, 19, 12,  4, 26,  8,
		16,  7, 27, 20, 13,  2,
		41, 52, 31, 37, 47, 55,
		30, 40, 51, 45, 33, 48,
		44, 49, 39, 56, 34, 53,
		46, 42, 50, 36, 29, 32
	};

	public static final int[] rotateTable = {
		1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
	};
}