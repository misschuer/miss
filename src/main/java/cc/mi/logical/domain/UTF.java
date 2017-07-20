package cc.mi.logical.domain;

import java.io.UTFDataFormatException;

public class UTF {
	public byte[] src;
	public int length;

	public UTF(byte[] src, int length) {
		this.src = src;
		this.length = length;
	}

	public static UTF getUTF(String str) {
		if (str.isEmpty()) {
			return new UTF(new byte[2], 2);
		}

		int strlen = str.length(), utflen = 0, count = 0, i;
		char c;

		/* use charAt instead of copying String to char array */
		for (i = 0; i < strlen; ++i) {
			c = str.charAt(i);
			if (c > 0x00 && c < 0x80) {
				++utflen;
			} else if (c > 0x07FF) {
				utflen += 3;
			} else {
				utflen += 2;
			}
		}

		if (utflen > 0xFFFF) {
			try {
				throw new UTFDataFormatException("encoded string too long: " + utflen + " bytes");
			} catch (UTFDataFormatException e) {
				e.printStackTrace();
			}
		}

		byte[] byteArr = new byte[utflen + 2];

		byteArr[count++] = (byte) (utflen >> 8);
		byteArr[count++] = (byte) (utflen & 0xFF);

		for (i = 0; i < strlen; ++i) {
			c = str.charAt(i);
			if (c > 0x00 && c < 0x80) {
				byteArr[count++] = (byte) c;
			} else if (c > 0x07FF) {
				byteArr[count++] = (byte) (0xE0 | c >> 12);
				byteArr[count++] = (byte) (0x80 | c >> 6 & 0x3F);
				byteArr[count++] = (byte) (0x80 | c & 0x3F);
			} else {
				byteArr[count++] = (byte) (0xC0 | c >> 6 & 0x1F);
				byteArr[count++] = (byte) (0x80 | c & 0x3F);
			}
		}

		return new UTF(byteArr, utflen + 2);
	}

	public static String getString(UTF utf) {
		int utflen = utf.length - 2;
		if (utflen > 0) {
			byte[] byteArr = utf.src;
			char[] charArr = new char[utflen];
			int byteArrCount = 2, charArrCount = 0, c, b;
			byte b1, b2;

			while (byteArrCount < utflen) {
				c = byteArr[byteArrCount++] & 0xFF;
				b = c >> 4;
				if (b >= 0x00 && b < 0x08) {
					charArr[charArrCount++] = (char) c;
				} else if (b == 0x0C || b == 0x0D) {
					b1 = byteArr[byteArrCount++];
					charArr[charArrCount++] = (char) ((c & 0x1F) << 6 | b1 & 0x3F);
				} else if (b == 0x0E) {
					b1 = byteArr[byteArrCount++];
					b2 = byteArr[byteArrCount++];
					charArr[charArrCount++] = (char) ((c & 0x0F) << 12 | (b1 & 0x3F) << 6 | b2 & 0x3F);
				} else {
					try {
						throw new UTFDataFormatException("malformed input around byte " + byteArrCount);
					} catch (UTFDataFormatException e) {
						e.printStackTrace();
					}
				}
			}

			return new String(charArr, 0, charArrCount);
		}

		return "";
	}

}
