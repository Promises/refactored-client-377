package com.jagex.runescape.cache.bzip;

public class BZip2Decompressor {

	public static Bzip2Context state = new Bzip2Context();

	public static int decompress(byte output[], int lenght, byte compressed[], int decompressedLength, int minLen) {
		synchronized (state) {
			state.compressed = compressed;
			state.nextIn = minLen;
			state.decompressed = output;
			state.nextOut = 0;
			state.decompressedLength = decompressedLength;
			state.lenght = lenght;
			state.bsLive = 0;
			state.bsBuff = 0;
			state.totalInLo32 = 0;
			state.totalInHi32 = 0;
			state.totalOutLo32 = 0;
			state.totalOutHigh32 = 0;
			state.currentBlock = 0;
			decompress(state);
			lenght -= state.lenght;
			return lenght;
		}
	}

	public static void method313(Bzip2Context bzip2Context) {
		byte byte4 = bzip2Context.aByte57;
		int i = bzip2Context.anInt58;
		int j = bzip2Context.anInt68;
		int k = bzip2Context.anInt66;
		int ai[] = Bzip2Context.anIntArray71;
		int l = bzip2Context.anInt65;
		byte abyte0[] = bzip2Context.decompressed;
		int i1 = bzip2Context.nextOut;
		int j1 = bzip2Context.lenght;
		int k1 = j1;
		int l1 = bzip2Context.anInt85 + 1;
		label0: do {
			if (i > 0) {
				do {
					if (j1 == 0)
						break label0;
					if (i == 1)
						break;
					abyte0[i1] = byte4;
					i--;
					i1++;
					j1--;
				} while (true);
				if (j1 == 0) {
					i = 1;
					break;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
			}
			boolean flag = true;
			while (flag) {
				flag = false;
				if (j == l1) {
					i = 0;
					break label0;
				}
				byte4 = (byte) k;
				l = ai[l];
				byte byte0 = (byte) (l & 0xff);
				l >>= 8;
				j++;
				if (byte0 != k) {
					k = byte0;
					if (j1 == 0) {
						i = 1;
					} else {
						abyte0[i1] = byte4;
						i1++;
						j1--;
						flag = true;
						continue;
					}
					break label0;
				}
				if (j != l1)
					continue;
				if (j1 == 0) {
					i = 1;
					break label0;
				}
				abyte0[i1] = byte4;
				i1++;
				j1--;
				flag = true;
			}
			i = 2;
			l = ai[l];
			byte byte1 = (byte) (l & 0xff);
			l >>= 8;
			if (++j != l1)
				if (byte1 != k) {
					k = byte1;
				} else {
					i = 3;
					l = ai[l];
					byte byte2 = (byte) (l & 0xff);
					l >>= 8;
					if (++j != l1)
						if (byte2 != k) {
							k = byte2;
						} else {
							l = ai[l];
							byte byte3 = (byte) (l & 0xff);
							l >>= 8;
							j++;
							i = (byte3 & 0xff) + 4;
							l = ai[l];
							k = (byte) (l & 0xff);
							l >>= 8;
							j++;
						}
				}
		} while (true);
		int i2 = bzip2Context.totalOutLo32;
		bzip2Context.totalOutLo32 += k1 - j1;
		if (bzip2Context.totalOutLo32 < i2)
			bzip2Context.totalOutHigh32++;
		bzip2Context.aByte57 = byte4;
		bzip2Context.anInt58 = i;
		bzip2Context.anInt68 = j;
		bzip2Context.anInt66 = k;
		Bzip2Context.anIntArray71 = ai;
		bzip2Context.anInt65 = l;
		bzip2Context.decompressed = abyte0;
		bzip2Context.nextOut = i1;
		bzip2Context.lenght = j1;
	}

	public static void decompress(Bzip2Context bzip2Context) {
		int k8 = 0;
		int ai[] = null;
		int ai1[] = null;
		int ai2[] = null;
		bzip2Context.anInt62 = 1;
		if (Bzip2Context.anIntArray71 == null)
			Bzip2Context.anIntArray71 = new int[bzip2Context.anInt62 * 0x186a0];
		boolean flag19 = true;
		while (flag19) {
			byte b = method315(bzip2Context);
			if (b == 23)
				return;
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			bzip2Context.currentBlock++;
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method315(bzip2Context);
			b = method316(bzip2Context);
			if (b != 0)
				bzip2Context.aBoolean59 = true;
			else
				bzip2Context.aBoolean59 = false;
			if (bzip2Context.aBoolean59)
				System.out.println("PANIC! RANDOMISED BLOCK!");
			bzip2Context.anInt64 = 0;
			b = method315(bzip2Context);
			bzip2Context.anInt64 = bzip2Context.anInt64 << 8 | b & 0xff;
			b = method315(bzip2Context);
			bzip2Context.anInt64 = bzip2Context.anInt64 << 8 | b & 0xff;
			b = method315(bzip2Context);
			bzip2Context.anInt64 = bzip2Context.anInt64 << 8 | b & 0xff;
			for (int j = 0; j < 16; j++) {
				byte byte1 = method316(bzip2Context);
				if (byte1 == 1)
					bzip2Context.aBooleanArray74[j] = true;
				else
					bzip2Context.aBooleanArray74[j] = false;
			}

			for (int k = 0; k < 256; k++)
				bzip2Context.aBooleanArray73[k] = false;

			for (int l = 0; l < 16; l++)
				if (bzip2Context.aBooleanArray74[l]) {
					for (int i3 = 0; i3 < 16; i3++) {
						byte byte2 = method316(bzip2Context);
						if (byte2 == 1)
							bzip2Context.aBooleanArray73[l * 16 + i3] = true;
					}

				}

			method318(bzip2Context);
			int i4 = bzip2Context.anInt72 + 2;
			int j4 = method317(3, bzip2Context);
			int k4 = method317(15, bzip2Context);
			for (int i1 = 0; i1 < k4; i1++) {
				int j3 = 0;
				do {
					byte byte3 = method316(bzip2Context);
					if (byte3 == 0)
						break;
					j3++;
				} while (true);
				bzip2Context.aByteArray79[i1] = (byte) j3;
			}

			byte abyte0[] = new byte[6];
			for (byte byte16 = 0; byte16 < j4; byte16++)
				abyte0[byte16] = byte16;

			for (int j1 = 0; j1 < k4; j1++) {
				byte byte17 = bzip2Context.aByteArray79[j1];
				byte byte15 = abyte0[byte17];
				for (; byte17 > 0; byte17--)
					abyte0[byte17] = abyte0[byte17 - 1];

				abyte0[0] = byte15;
				bzip2Context.aByteArray78[j1] = byte15;
			}

			for (int k3 = 0; k3 < j4; k3++) {
				int l6 = method317(5, bzip2Context);
				for (int k1 = 0; k1 < i4; k1++) {
					do {
						byte byte4 = method316(bzip2Context);
						if (byte4 == 0)
							break;
						byte4 = method316(bzip2Context);
						if (byte4 == 0)
							l6++;
						else
							l6--;
					} while (true);
					bzip2Context.aByteArrayArray80[k3][k1] = (byte) l6;
				}

			}

			for (int l3 = 0; l3 < j4; l3++) {
				byte byte8 = 32;
				int i = 0;
				for (int l1 = 0; l1 < i4; l1++) {
					if (bzip2Context.aByteArrayArray80[l3][l1] > i)
						i = bzip2Context.aByteArrayArray80[l3][l1];
					if (bzip2Context.aByteArrayArray80[l3][l1] < byte8)
						byte8 = bzip2Context.aByteArrayArray80[l3][l1];
				}

				method319(bzip2Context.anIntArrayArray81[l3], bzip2Context.anIntArrayArray82[l3], bzip2Context.anIntArrayArray83[l3],
						bzip2Context.aByteArrayArray80[l3], byte8, i, i4);
				bzip2Context.anIntArray84[l3] = byte8;
			}

			int l4 = bzip2Context.anInt72 + 1;
			//int l5 = 0x186a0 * class1.anInt62;
			int i5 = -1;
			int j5 = 0;
			for (int i2 = 0; i2 <= 255; i2++)
				bzip2Context.anIntArray67[i2] = 0;

			int j9 = 4095;
			for (int l8 = 15; l8 >= 0; l8--) {
				for (int i9 = 15; i9 >= 0; i9--) {
					bzip2Context.aByteArray76[j9] = (byte) (l8 * 16 + i9);
					j9--;
				}

				bzip2Context.anIntArray77[l8] = j9 + 1;
			}

			int i6 = 0;
			if (j5 == 0) {
				i5++;
				j5 = 50;
				byte byte12 = bzip2Context.aByteArray78[i5];
				k8 = bzip2Context.anIntArray84[byte12];
				ai = bzip2Context.anIntArrayArray81[byte12];
				ai2 = bzip2Context.anIntArrayArray83[byte12];
				ai1 = bzip2Context.anIntArrayArray82[byte12];
			}
			j5--;
			int i7 = k8;
			int l7;
			byte byte9;
			for (l7 = method317(i7, bzip2Context); l7 > ai[i7]; l7 = l7 << 1 | byte9) {
				i7++;
				byte9 = method316(bzip2Context);
			}

			for (int k5 = ai2[l7 - ai1[i7]]; k5 != l4;)
				if (k5 == 0 || k5 == 1) {
					int j6 = -1;
					int k6 = 1;
					do {
						if (k5 == 0)
							j6 += k6;
						else if (k5 == 1)
							j6 += 2 * k6;
						k6 *= 2;
						if (j5 == 0) {
							i5++;
							j5 = 50;
							byte byte13 = bzip2Context.aByteArray78[i5];
							k8 = bzip2Context.anIntArray84[byte13];
							ai = bzip2Context.anIntArrayArray81[byte13];
							ai2 = bzip2Context.anIntArrayArray83[byte13];
							ai1 = bzip2Context.anIntArrayArray82[byte13];
						}
						j5--;
						int j7 = k8;
						int i8;
						byte byte10;
						for (i8 = method317(j7, bzip2Context); i8 > ai[j7]; i8 = i8 << 1 | byte10) {
							j7++;
							byte10 = method316(bzip2Context);
						}

						k5 = ai2[i8 - ai1[j7]];
					} while (k5 == 0 || k5 == 1);
					j6++;
					byte byte5 = bzip2Context.aByteArray75[bzip2Context.aByteArray76[bzip2Context.anIntArray77[0]] & 0xff];
					bzip2Context.anIntArray67[byte5 & 0xff] += j6;
					for (; j6 > 0; j6--) {
						Bzip2Context.anIntArray71[i6] = byte5 & 0xff;
						i6++;
					}

				} else {
					int j11 = k5 - 1;
					byte byte6;
					if (j11 < 16) {
						int j10 = bzip2Context.anIntArray77[0];
						byte6 = bzip2Context.aByteArray76[j10 + j11];
						for (; j11 > 3; j11 -= 4) {
							int k11 = j10 + j11;
							bzip2Context.aByteArray76[k11] = bzip2Context.aByteArray76[k11 - 1];
							bzip2Context.aByteArray76[k11 - 1] = bzip2Context.aByteArray76[k11 - 2];
							bzip2Context.aByteArray76[k11 - 2] = bzip2Context.aByteArray76[k11 - 3];
							bzip2Context.aByteArray76[k11 - 3] = bzip2Context.aByteArray76[k11 - 4];
						}

						for (; j11 > 0; j11--)
							bzip2Context.aByteArray76[j10 + j11] = bzip2Context.aByteArray76[(j10 + j11) - 1];

						bzip2Context.aByteArray76[j10] = byte6;
					} else {
						int l10 = j11 / 16;
						int i11 = j11 % 16;
						int k10 = bzip2Context.anIntArray77[l10] + i11;
						byte6 = bzip2Context.aByteArray76[k10];
						for (; k10 > bzip2Context.anIntArray77[l10]; k10--)
							bzip2Context.aByteArray76[k10] = bzip2Context.aByteArray76[k10 - 1];

						bzip2Context.anIntArray77[l10]++;
						for (; l10 > 0; l10--) {
							bzip2Context.anIntArray77[l10]--;
							bzip2Context.aByteArray76[bzip2Context.anIntArray77[l10]] = bzip2Context.aByteArray76[(bzip2Context.anIntArray77[l10 - 1] + 16) - 1];
						}

						bzip2Context.anIntArray77[0]--;
						bzip2Context.aByteArray76[bzip2Context.anIntArray77[0]] = byte6;
						if (bzip2Context.anIntArray77[0] == 0) {
							int i10 = 4095;
							for (int k9 = 15; k9 >= 0; k9--) {
								for (int l9 = 15; l9 >= 0; l9--) {
									bzip2Context.aByteArray76[i10] = bzip2Context.aByteArray76[bzip2Context.anIntArray77[k9] + l9];
									i10--;
								}

								bzip2Context.anIntArray77[k9] = i10 + 1;
							}

						}
					}
					bzip2Context.anIntArray67[bzip2Context.aByteArray75[byte6 & 0xff] & 0xff]++;
					Bzip2Context.anIntArray71[i6] = bzip2Context.aByteArray75[byte6 & 0xff] & 0xff;
					i6++;
					if (j5 == 0) {
						i5++;
						j5 = 50;
						byte byte14 = bzip2Context.aByteArray78[i5];
						k8 = bzip2Context.anIntArray84[byte14];
						ai = bzip2Context.anIntArrayArray81[byte14];
						ai2 = bzip2Context.anIntArrayArray83[byte14];
						ai1 = bzip2Context.anIntArrayArray82[byte14];
					}
					j5--;
					int k7 = k8;
					int j8;
					byte byte11;
					for (j8 = method317(k7, bzip2Context); j8 > ai[k7]; j8 = j8 << 1 | byte11) {
						k7++;
						byte11 = method316(bzip2Context);
					}

					k5 = ai2[j8 - ai1[k7]];
				}

			bzip2Context.anInt58 = 0;
			bzip2Context.aByte57 = 0;
			bzip2Context.anIntArray69[0] = 0;
			for (int j2 = 1; j2 <= 256; j2++)
				bzip2Context.anIntArray69[j2] = bzip2Context.anIntArray67[j2 - 1];

			for (int k2 = 1; k2 <= 256; k2++)
				bzip2Context.anIntArray69[k2] += bzip2Context.anIntArray69[k2 - 1];

			for (int l2 = 0; l2 < i6; l2++) {
				byte byte7 = (byte) (Bzip2Context.anIntArray71[l2] & 0xff);
				Bzip2Context.anIntArray71[bzip2Context.anIntArray69[byte7 & 0xff]] |= l2 << 8;
				bzip2Context.anIntArray69[byte7 & 0xff]++;
			}

			bzip2Context.anInt65 = Bzip2Context.anIntArray71[bzip2Context.anInt64] >> 8;
			bzip2Context.anInt68 = 0;
			bzip2Context.anInt65 = Bzip2Context.anIntArray71[bzip2Context.anInt65];
			bzip2Context.anInt66 = (byte) (bzip2Context.anInt65 & 0xff);
			bzip2Context.anInt65 >>= 8;
			bzip2Context.anInt68++;
			bzip2Context.anInt85 = i6;
			method313(bzip2Context);
			if (bzip2Context.anInt68 == bzip2Context.anInt85 + 1 && bzip2Context.anInt58 == 0)
				flag19 = true;
			else
				flag19 = false;
		}
	}

	public static byte method315(Bzip2Context class1) {
		return (byte) method317(8, class1);
	}

	public static byte method316(Bzip2Context class1) {
		return (byte) method317(1, class1);
	}

	public static int method317(int i, Bzip2Context class1) {
		int j;
		do {
			if (class1.bsLive >= i) {
				int k = class1.bsBuff >> class1.bsLive - i & (1 << i) - 1;
				class1.bsLive -= i;
				j = k;
				break;
			}
			class1.bsBuff = class1.bsBuff << 8 | class1.compressed[class1.nextIn] & 0xff;
			class1.bsLive += 8;
			class1.nextIn++;
			class1.decompressedLength--;
			class1.totalInLo32++;
			if (class1.totalInLo32 == 0)
				class1.totalInHi32++;
		} while (true);
		return j;
	}

	public static void method318(Bzip2Context class1) {
		class1.anInt72 = 0;
		for (int i = 0; i < 256; i++)
			if (class1.aBooleanArray73[i]) {
				class1.aByteArray75[class1.anInt72] = (byte) i;
				class1.anInt72++;
			}

	}

	public static void method319(int ai[], int ai1[], int ai2[], byte abyte0[], int i, int j, int k) {
		int l = 0;
		for (int i1 = i; i1 <= j; i1++) {
			for (int l2 = 0; l2 < k; l2++)
				if (abyte0[l2] == i1) {
					ai2[l] = l2;
					l++;
				}

		}

		for (int j1 = 0; j1 < 23; j1++)
			ai1[j1] = 0;

		for (int k1 = 0; k1 < k; k1++)
			ai1[abyte0[k1] + 1]++;

		for (int l1 = 1; l1 < 23; l1++)
			ai1[l1] += ai1[l1 - 1];

		for (int i2 = 0; i2 < 23; i2++)
			ai[i2] = 0;

		int i3 = 0;
		for (int j2 = i; j2 <= j; j2++) {
			i3 += ai1[j2 + 1] - ai1[j2];
			ai[j2] = i3 - 1;
			i3 <<= 1;
		}

		for (int k2 = i + 1; k2 <= j; k2++)
			ai1[k2] = (ai[k2 - 1] + 1 << 1) - ai1[k2];

	}


}
