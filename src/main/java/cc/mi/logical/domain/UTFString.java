package cc.mi.logical.domain;

import java.io.UTFDataFormatException;

import io.netty.buffer.ByteBuf;

public class UTFString {
	public static UTF readUTF(ByteBuf buffer) {
		int utflen = buffer.readUnsignedShort();
		if (utflen > 0) {
			byte[] byteArr = new byte[utflen + 2];

			byteArr[0] = (byte) (utflen >> 8);
			byteArr[1] = (byte) (utflen & 0xFF);

			buffer.readBytes(byteArr, 2, utflen);

			return new UTF(byteArr, utflen + 2);
		}

		return new UTF(new byte[2], 2);
	}

	public static String readString(ByteBuf buffer) {
		int utflen = buffer.readUnsignedShort();
		if (utflen > 0) {
			byte[] byteArr = new byte[utflen];
			char[] charArr = new char[utflen];
			int byteArrCount = 0, charArrCount = 0, c, b;
			byte b1, b2;

			buffer.readBytes(byteArr, 0, utflen);

			while (byteArrCount < utflen) {
				c = byteArr[byteArrCount++] & 0xFF;
				b = c >> 4;
				if (b >= 0x00 && b < 0x08) {/* 0xxxxxxx */
					charArr[charArrCount++] = (char) c;
				} else if (b == 0x0C || b == 0x0D) {/* 110x xxxx 10xx xxxx */
					/*
					 * if (++byteArrCount > utflen) { throw new
					 * UTFDataFormatException
					 * ("malformed input: partial character at end"); }
					 */
					b1 = byteArr[byteArrCount++];
					/*
					 * if ((b1 & 0xC0) != 0x80) { throw new
					 * UTFDataFormatException("malformed input around byte " +
					 * byteArrCount); }
					 */
					charArr[charArrCount++] = (char) ((c & 0x1F) << 6 | b1 & 0x3F);
				} else if (b == 0x0E) {/* 1110 xxxx 10xx xxxx 10xx xxxx */
					/*
					 * if ((byteArrCount += 2) > utflen) { throw new
					 * UTFDataFormatException
					 * ("malformed input: partial character at end"); }
					 */
					b1 = byteArr[byteArrCount++];
					b2 = byteArr[byteArrCount++];
					/*
					 * if ((b1 & 0xC0) != 0x80 || (b2 & 0xC0) != 0x80) { throw
					 * new UTFDataFormatException("malformed input around byte "
					 * + (byteArrCount - 1)); }
					 */
					charArr[charArrCount++] = (char) ((c & 0x0F) << 12 | (b1 & 0x3F) << 6 | b2 & 0x3F);
				} else {/* 10xx xxxx, 1111 xxxx */
					try {
						throw new UTFDataFormatException("malformed input around byte " + byteArrCount);
					} catch (UTFDataFormatException e) {
						e.printStackTrace();
					}
				}
			}
			// The number of chars produced may be less than utflen
			return new String(charArr, 0, charArrCount);
		}

		return "";
	}

	public static void writeUTF(ByteBuf buf, UTF utf) {
		buf.writeBytes(utf.src, 0, utf.length);
	}

	public static void writeString(ByteBuf buf, String str) {
		UTF utf = UTF.getUTF(null != str ? str : "");
		writeUTF(buf, utf);
	}
}
