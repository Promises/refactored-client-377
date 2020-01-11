package com.jagex.runescape.scene;

import com.jagex.runescape.net.Buffer;
import com.jagex.runescape.net.requester.OnDemandRequester;
import com.jagex.runescape.cache.def.FloorDefinition;
import com.jagex.runescape.cache.def.GameObjectDefinition;
import com.jagex.runescape.media.Rasterizer3D;
import com.jagex.runescape.media.renderable.GameObject;
import com.jagex.runescape.media.renderable.Model;
import com.jagex.runescape.media.renderable.Renderable;
import com.jagex.runescape.scene.util.CollisionMap;
import com.jagex.runescape.scene.util.TiledUtils;

public class Region {
	public byte[][][] renderRuleFlags;
	public byte aByte139 = 0;
	public boolean aBoolean140 = true;
	public static int hueRandomizer = (int) (Math.random() * 17.0) - 8;
	public byte[][][] overlayRotations;
	public static final int[] anIntArray143 = { 0, -1, 0, 1 };
	public int[] blendedHue;
	public int[] blendedSaturation;
	public int[] blendedLightness;
	public int[] blendedHueDivisor;
	public int[] blendDirectionTracker;
	public int[][][] vertexHeights;
	public static int lowestPlane = 99;
	public int regionSizeX;
	public int regionSizeY;
	public byte[][][] overlayClippingPaths;
	public static byte aByte154 = -80;
	public byte[][][] overlayFloorIds;
	public byte aByte156 = 0;
	public boolean aBoolean157 = true;
	public static final int[] anIntArray158 = { 1, 2, 4, 8 };
	public byte[][][] underlayFloorIds;
	public int anInt160 = 20411;
	public static final int[] anIntArray161 = { 1, 0, -1, 0 };
	public static int onBuildTimePlane;
	public static int lightnessRandomizer = (int) (Math.random() * 33.0) - 16;
	public byte[][][] tileShadowIntensity;
	public int[][] tileLightingIntensity;
	public int anInt166 = 69;
	public static final int[] anIntArray167 = { 16, 32, 64, 128 };
	public int[][][] tileCullingBitsets;
	public static boolean lowMemory = true;


	public Region(byte[][][] renderRuleFlags, int regionSizeY, int regionSizeX, int[][][] vertexHeights) {
		lowestPlane = 99;
		this.regionSizeX = regionSizeX;
		this.regionSizeY = regionSizeY;
		this.vertexHeights = vertexHeights;
		this.renderRuleFlags = renderRuleFlags;
		underlayFloorIds = new byte[4][this.regionSizeX][this.regionSizeY];
		overlayFloorIds = new byte[4][this.regionSizeX][this.regionSizeY];
		overlayClippingPaths = new byte[4][this.regionSizeX][this.regionSizeY];
		overlayRotations = new byte[4][this.regionSizeX][this.regionSizeY];
		tileCullingBitsets = new int[4][this.regionSizeX + 1][this.regionSizeY + 1];
		tileShadowIntensity = new byte[4][this.regionSizeX + 1][this.regionSizeY + 1];
		tileLightingIntensity = new int[this.regionSizeX + 1][this.regionSizeY + 1];
		blendedHue = new int[this.regionSizeY];
		blendedSaturation = new int[this.regionSizeY];
		blendedLightness = new int[this.regionSizeY];
		blendedHueDivisor = new int[this.regionSizeY];
		blendDirectionTracker = new int[this.regionSizeY];

	}

	public static int calculateNoise(int x, int seed) {
		int n = x + seed * 57;
		n = n << 13 ^ n;
		int noise = n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff;
		return noise >> 19 & 0xff;
	}

	public static int method163(int i, int i_0_, int i_1_) {
		int i_2_ = i / i_1_;
		int i_3_ = i & i_1_ - 1;
		int i_4_ = i_0_ / i_1_;
		int i_5_ = i_0_ & i_1_ - 1;
		int i_6_ = method178(i_2_, i_4_);
		int i_7_ = method178(i_2_ + 1, i_4_);
		int i_8_ = method178(i_2_, i_4_ + 1);
		int i_9_ = method178(i_2_ + 1, i_4_ + 1);
		int i_10_ = method176(i_6_, i_7_, i_3_, i_1_);
		int i_11_ = method176(i_8_, i_9_, i_3_, i_1_);
		return method176(i_10_, i_11_, i_5_, i_1_);
	}

	public int getVisibilityPlaneFor(int i, int i_12_, int i_13_, byte i_14_) {
		if (i_14_ == aByte156) {
			// boolean bool = false;
		} else
			return 2;
		if ((renderRuleFlags[i_12_][i_13_][i] & 0x8) != 0)
			return 0;
		if (i_12_ > 0 && (renderRuleFlags[1][i_13_][i] & 0x2) != 0)
			return i_12_ - 1;
		return i_12_;
	}

	public static void method165(int i, int i_15_, int i_16_, int i_17_, CollisionMap class46, int i_18_, int i_19_,
								 int i_20_, int i_21_, Scene class22, int[][][] is) {
		int i_22_ = is[i_15_][i_19_][i_17_];
		int i_23_ = is[i_15_][i_19_ + 1][i_17_];
		int i_24_ = is[i_15_][i_19_ + 1][i_17_ + 1];
		int i_25_ = is[i_15_][i_19_][i_17_ + 1];
		int i_26_ = i_22_ + i_23_ + i_24_ + i_25_ >> 2;
		GameObjectDefinition class47 = GameObjectDefinition.getDefinition(i);
		if (i_20_ == 0) {
			int i_27_ = i_19_ + (i_17_ << 7) + (i << 14) + 1073741824;
			if (!class47.actionsBoolean)
				i_27_ += -2147483648;
			byte i_28_ = (byte) ((i_18_ << 6) + i_16_);
			if (i_16_ == 22) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(22, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, 22, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addGroundDecoration(i_19_, i_17_, i_21_, i_26_, i_27_, class50_sub1_sub4, i_28_);
				if (class47.solid && class47.actionsBoolean)
					class46.markBlocked(i_19_, i_17_);
			} else if (i_16_ == 10 || i_16_ == 11) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(10, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, 10, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				if (class50_sub1_sub4 != null) {
					int i_29_ = 0;
					if (i_16_ == 11)
						i_29_ += 256;
					int i_30_;
					int i_31_;
					if (i_18_ == 1 || i_18_ == 3) {
						i_30_ = class47.sizeY;
						i_31_ = class47.sizeX;
					} else {
						i_30_ = class47.sizeX;
						i_31_ = class47.sizeY;
					}
					class22.addEntityB(i_19_, i_17_, i_21_, i_26_, i_29_, i_31_, i_30_, i_27_, class50_sub1_sub4, i_28_
					);
				}
				if (class47.solid)
					class46.method413(i_17_, i_18_, class47.sizeY, class47.sizeX, class47.walkable, i_19_,
							(byte) 52);
			} else if (i_16_ >= 12) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(i_16_, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, i_16_, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addEntityB(i_19_, i_17_, i_21_, i_26_, 0, 1, 1, i_27_, class50_sub1_sub4, i_28_);
				if (class47.solid)
					class46.method413(i_17_, i_18_, class47.sizeY, class47.sizeX, class47.walkable, i_19_,
							(byte) 52);
			} else if (i_16_ == 0) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(0, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, 0, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addWall(i_19_, i_17_, i_21_, i_26_, anIntArray158[i_18_], 0, i_27_, class50_sub1_sub4, null, i_28_
				);
				if (class47.solid)
					class46.markWall(i_19_, i_17_, i_16_, i_18_, class47.walkable);
			} else if (i_16_ == 1) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(1, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, 1, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addWall(i_19_, i_17_, i_21_, i_26_, anIntArray167[i_18_], 0, i_27_, class50_sub1_sub4, null, i_28_
				);
				if (class47.solid)
					class46.markWall(i_19_, i_17_, i_16_, i_18_, class47.walkable);
			} else if (i_16_ == 2) {
				int i_32_ = i_18_ + 1 & 0x3;
				Renderable class50_sub1_sub4;
				Renderable class50_sub1_sub4_33_;
				if (class47.animationId == -1 && class47.childrenIds == null) {
					class50_sub1_sub4 = class47.getGameObjectModel(2, 4 + i_18_, i_22_, i_23_, i_24_, i_25_, -1);
					class50_sub1_sub4_33_ = class47.getGameObjectModel(2, i_32_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(i, 4 + i_18_, 2, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
					class50_sub1_sub4_33_ = new GameObject(i, i_32_, 2, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				}
				class22.addWall(i_19_, i_17_, i_21_, i_26_, anIntArray158[i_18_], anIntArray158[i_32_], i_27_, class50_sub1_sub4, class50_sub1_sub4_33_, i_28_
				);
				if (class47.solid)
					class46.markWall(i_19_, i_17_, i_16_, i_18_, class47.walkable);
			} else if (i_16_ == 3) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(3, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, 3, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addWall(i_19_, i_17_, i_21_, i_26_, anIntArray167[i_18_], 0, i_27_, class50_sub1_sub4, null, i_28_
				);
				if (class47.solid)
					class46.markWall(i_19_, i_17_, i_16_, i_18_, class47.walkable);
			} else if (i_16_ == 9) {
				Renderable class50_sub1_sub4;
				if (class47.animationId == -1 && class47.childrenIds == null)
					class50_sub1_sub4 = class47.getGameObjectModel(i_16_, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				else
					class50_sub1_sub4 = new GameObject(i, i_18_, i_16_, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                            true);
				class22.addEntityB(i_19_, i_17_, i_21_, i_26_, 0, 1, 1, i_27_, class50_sub1_sub4, i_28_);
				if (class47.solid)
					class46.method413(i_17_, i_18_, class47.sizeY, class47.sizeX, class47.walkable, i_19_,
							(byte) 52);
			} else {
				if (class47.adjustToTerrain) {
					if (i_18_ == 1) {
						int i_34_ = i_25_;
						i_25_ = i_24_;
						i_24_ = i_23_;
						i_23_ = i_22_;
						i_22_ = i_34_;
					} else if (i_18_ == 2) {
						int i_35_ = i_25_;
						i_25_ = i_23_;
						i_23_ = i_35_;
						i_35_ = i_24_;
						i_24_ = i_22_;
						i_22_ = i_35_;
					} else if (i_18_ == 3) {
						int i_36_ = i_25_;
						i_25_ = i_22_;
						i_22_ = i_23_;
						i_23_ = i_24_;
						i_24_ = i_36_;
					}
				}
				if (i_16_ == 4) {
					Renderable class50_sub1_sub4;
					if (class47.animationId == -1 && class47.childrenIds == null)
						class50_sub1_sub4 = class47.getGameObjectModel(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					else
						class50_sub1_sub4 = new GameObject(i, 0, 4, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                                true);
					class22.addWallDecoration(i_19_, i_17_, i_21_, i_26_, 0, 0, i_18_ * 512, i_27_, class50_sub1_sub4, i_28_, anIntArray158[i_18_]
					);
				} else if (i_16_ == 5) {
					int i_37_ = 16;
					int i_38_ = class22.method267(i_21_, i_19_, i_17_);
					if (i_38_ > 0)
						i_37_ = GameObjectDefinition.getDefinition(i_38_ >> 14 & 0x7fff).unknown4;
					Renderable class50_sub1_sub4;
					if (class47.animationId == -1 && class47.childrenIds == null)
						class50_sub1_sub4 = class47.getGameObjectModel(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					else
						class50_sub1_sub4 = new GameObject(i, 0, 4, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                                true);
					class22.addWallDecoration(i_19_, i_17_, i_21_, i_26_, anIntArray161[i_18_] * i_37_, anIntArray143[i_18_] * i_37_, i_18_ * 512, i_27_, class50_sub1_sub4, i_28_, anIntArray158[i_18_]
					);
				} else if (i_16_ == 6) {
					Renderable class50_sub1_sub4;
					if (class47.animationId == -1 && class47.childrenIds == null)
						class50_sub1_sub4 = class47.getGameObjectModel(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					else
						class50_sub1_sub4 = new GameObject(i, 0, 4, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                                true);
					class22.addWallDecoration(i_19_, i_17_, i_21_, i_26_, 0, 0, i_18_, i_27_, class50_sub1_sub4, i_28_, 256
					);
				} else if (i_16_ == 7) {
					Renderable class50_sub1_sub4;
					if (class47.animationId == -1 && class47.childrenIds == null)
						class50_sub1_sub4 = class47.getGameObjectModel(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					else
						class50_sub1_sub4 = new GameObject(i, 0, 4, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                                true);
					class22.addWallDecoration(i_19_, i_17_, i_21_, i_26_, 0, 0, i_18_, i_27_, class50_sub1_sub4, i_28_, 512
					);
				} else if (i_16_ == 8) {
					Renderable class50_sub1_sub4;
					if (class47.animationId == -1 && class47.childrenIds == null)
						class50_sub1_sub4 = class47.getGameObjectModel(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					else
						class50_sub1_sub4 = new GameObject(i, 0, 4, i_23_, i_24_, i_22_, i_25_, class47.animationId,
                                true);
					class22.addWallDecoration(i_19_, i_17_, i_21_, i_26_, 0, 0, i_18_, i_27_, class50_sub1_sub4, i_28_, 768
					);
				}
			}
		}
	}

	public void method166(int i, int i_39_, int i_40_, int i_41_) {
		if (i == anInt160) {
			for (int i_42_ = 0; i_42_ < 8; i_42_++) {
				for (int i_43_ = 0; i_43_ < 8; i_43_++)
					vertexHeights[i_39_][i_41_ + i_42_][i_40_ + i_43_] = 0;
			}
			if (i_41_ > 0) {
				for (int i_44_ = 1; i_44_ < 8; i_44_++)
					vertexHeights[i_39_][i_41_][i_40_ + i_44_] = vertexHeights[i_39_][i_41_ - 1][i_40_
							+ i_44_];
			}
			if (i_40_ > 0) {
				for (int i_45_ = 1; i_45_ < 8; i_45_++)
					vertexHeights[i_39_][i_41_ + i_45_][i_40_] = vertexHeights[i_39_][i_41_ + i_45_][i_40_ - 1];
			}
			if (i_41_ > 0 && vertexHeights[i_39_][i_41_ - 1][i_40_] != 0)
				vertexHeights[i_39_][i_41_][i_40_] = vertexHeights[i_39_][i_41_ - 1][i_40_];
			else if (i_40_ > 0 && vertexHeights[i_39_][i_41_][i_40_ - 1] != 0)
				vertexHeights[i_39_][i_41_][i_40_] = vertexHeights[i_39_][i_41_][i_40_ - 1];
			else if (i_41_ > 0 && i_40_ > 0 && vertexHeights[i_39_][i_41_ - 1][i_40_ - 1] != 0)
				vertexHeights[i_39_][i_41_][i_40_] = vertexHeights[i_39_][i_41_ - 1][i_40_ - 1];
		}
	}

	public void createRegionScene(CollisionMap[] collisionMaps, Scene scene) {
		for (int plane = 0; plane < 4; plane++) {
			for (int x = 0; x < 104; x++) {
				for (int y = 0; y < 104; y++) {
					if ((renderRuleFlags[plane][x][y] & 0x1) == 1) {
						int originalPlane = plane;
						if ((renderRuleFlags[1][x][y] & 0x2) == 2)
							originalPlane--;
						if (originalPlane >= 0)
							collisionMaps[originalPlane].markBlocked(x, y);
					}
				}
			}
		}
		hueRandomizer += (int) (Math.random() * 5.0) - 2;
		if (hueRandomizer < -8)
			hueRandomizer = -8;
		if (hueRandomizer > 8)
			hueRandomizer = 8;
		lightnessRandomizer += (int) (Math.random() * 5.0) - 2;
		if (lightnessRandomizer < -16)
			lightnessRandomizer = -16;
		if (lightnessRandomizer > 16)
			lightnessRandomizer = 16;
		for (int plane = 0; plane < 4; plane++) {
			byte[][] shadowIntensity = tileShadowIntensity[plane];
			int directionalLightInitialIntensity = 96;
			int specularDistributionFactor = 768;
			int directionalLightX = -50;
			int directionalLightY = -10;
			int directionalLightZ = -50;
			int directionalLightLength = (int) Math.sqrt((directionalLightX * directionalLightX + directionalLightY * directionalLightY + directionalLightZ * directionalLightZ));
			int specularDistribution = specularDistributionFactor * directionalLightLength >> 8;
			for (int y = 1; y < regionSizeY - 1; y++) {
				for (int x = 1; x < regionSizeX - 1; x++) {
					int xHeightDifference = (vertexHeights[plane][x + 1][y] - vertexHeights[plane][x - 1][y]);
					int yHeightDifference = (vertexHeights[plane][x][y + 1] - vertexHeights[plane][x][y - 1]);
					int normalizedLength = (int) Math.sqrt((xHeightDifference * xHeightDifference + 65536 + yHeightDifference * yHeightDifference));
					int normalizedNormalX = (xHeightDifference << 8) / normalizedLength;
					int normalizedNormalY = 65536 / normalizedLength;
					int normalizedNormalZ = (yHeightDifference << 8) / normalizedLength;
					int directionalLightIntensity = directionalLightInitialIntensity + (directionalLightX * normalizedNormalX + directionalLightY * normalizedNormalY + directionalLightZ * normalizedNormalZ) / specularDistribution;
					int weightedShadowIntensity = ((shadowIntensity[x - 1][y] >> 2) + (shadowIntensity[x + 1][y] >> 3)
							+ (shadowIntensity[x][y - 1] >> 2) + (shadowIntensity[x][y + 1] >> 3) + (shadowIntensity[x][y] >> 1));
					tileLightingIntensity[x][y] = directionalLightIntensity - weightedShadowIntensity;
				}
			}
			for (int y = 0; y < regionSizeY; y++) {
				blendedHue[y] = 0;
				blendedSaturation[y] = 0;
				blendedLightness[y] = 0;
				blendedHueDivisor[y] = 0;
				blendDirectionTracker[y] = 0;
			}
			for (int x = -5; x < regionSizeX + 5; x++) {
				for (int y = 0; y < regionSizeY; y++) {
					int xPositiveOffset = x + 5;
					if (xPositiveOffset >= 0 && xPositiveOffset < regionSizeX) {
						int floorId = underlayFloorIds[plane][xPositiveOffset][y] & 0xff;
						if (floorId > 0) {
							FloorDefinition floor = FloorDefinition.cache[floorId - 1];
							blendedHue[y] += floor.hue;
							blendedSaturation[y] += floor.saturation;
							blendedLightness[y] += floor.lightness;
							blendedHueDivisor[y] += floor.hueDivisor;
							blendDirectionTracker[y]++;
						}
					}
					int xNegativeOffset = x - 5;
					if (xNegativeOffset >= 0 && xNegativeOffset < regionSizeX) {
						int floorId = underlayFloorIds[plane][xNegativeOffset][y] & 0xff;
						if (floorId > 0) {
							FloorDefinition floor = FloorDefinition.cache[floorId - 1];
							blendedHue[y] -= floor.hue;
							blendedSaturation[y] -= floor.saturation;
							blendedLightness[y] -= floor.lightness;
							blendedHueDivisor[y] -= floor.hueDivisor;
							blendDirectionTracker[y]--;
						}
					}
				}
				if (x >= 1 && x < regionSizeX - 1) {
					int i_75_ = 0;
					int i_76_ = 0;
					int i_77_ = 0;
					int i_78_ = 0;
					int i_79_ = 0;
					for (int y = -5; y < regionSizeY + 5; y++) {
						int yPositiveOffset = y + 5;
						if (yPositiveOffset >= 0 && yPositiveOffset < regionSizeY) {
							i_75_ += blendedHue[yPositiveOffset];
							i_76_ += blendedSaturation[yPositiveOffset];
							i_77_ += blendedLightness[yPositiveOffset];
							i_78_ += blendedHueDivisor[yPositiveOffset];
							i_79_ += blendDirectionTracker[yPositiveOffset];
						}
						int yNegativeOffset = y - 5;
						if (yNegativeOffset >= 0 && yNegativeOffset < regionSizeY) {
							i_75_ -= blendedHue[yNegativeOffset];
							i_76_ -= blendedSaturation[yNegativeOffset];
							i_77_ -= blendedLightness[yNegativeOffset];
							i_78_ -= blendedHueDivisor[yNegativeOffset];
							i_79_ -= blendDirectionTracker[yNegativeOffset];
						}
						if (y >= 1
								&& y < regionSizeY - 1
								&& (!lowMemory || (renderRuleFlags[0][x][y] & 0x2) != 0 || ((renderRuleFlags[plane][x][y] & 0x10) == 0 && (getVisibilityPlaneFor(
										y, plane, x, (byte) 0) == onBuildTimePlane)))) {
							if (plane < lowestPlane)
								lowestPlane = plane;
							int underlayFloorId = (underlayFloorIds[plane][x][y] & 0xff);
							int overlayFloorId = (overlayFloorIds[plane][x][y] & 0xff);
							if (underlayFloorId > 0 || overlayFloorId > 0) {
								int vertexSouthWest = vertexHeights[plane][x][y];
								int vertexSouthEast = (vertexHeights[plane][x + 1][y]);
								int vertexNorthEast = (vertexHeights[plane][x + 1][y + 1]);
								int vertexNorthWest = (vertexHeights[plane][x][y + 1]);
								int lightSouthWest = tileLightingIntensity[x][y];
								int lightSouthEast = tileLightingIntensity[x + 1][y];
								int lightNorthEast = tileLightingIntensity[x + 1][y + 1];
								int lightNorthWest = tileLightingIntensity[x][y + 1];
								int hslBitsetUnmodified = -1;
								int hslBitsetRandomized = -1;
								if (underlayFloorId > 0) {
									int hue = i_75_ * 256 / i_78_;
									int saturation = i_76_ / i_79_;
									int lightness = i_77_ / i_79_;
									hslBitsetUnmodified = getHSLBitset(hue, saturation, lightness);
									hue = hue + hueRandomizer & 0xff;
									lightness += lightnessRandomizer;
									if (lightness < 0)
										lightness = 0;
									else if (lightness > 255)
										lightness = 255;
									hslBitsetRandomized = getHSLBitset(hue, saturation, lightness);
								}
								if (plane > 0) {
									boolean bool = true;
									if (underlayFloorId == 0 && (overlayClippingPaths[plane][x][y]) != 0)
										bool = false;
									if (overlayFloorId > 0 && !(FloorDefinition.cache[overlayFloorId - 1].occlude))
										bool = false;
									if (bool && vertexSouthWest == vertexSouthEast && vertexSouthWest == vertexNorthEast && vertexSouthWest == vertexNorthWest)
										tileCullingBitsets[plane][x][y] |= 0x924;
								}
								int rgbBitsetRandomized = 0;
								if (hslBitsetUnmodified != -1)
									rgbBitsetRandomized = (Rasterizer3D.getRgbLookupTableId[trimHSLLightness(hslBitsetRandomized, 96)]);
								if (overlayFloorId == 0)
									scene.renderTile(plane, x, y, 0, 0, -1, vertexSouthWest, vertexSouthEast, vertexNorthEast, vertexNorthWest,
											trimHSLLightness(hslBitsetUnmodified, lightSouthWest), trimHSLLightness(hslBitsetUnmodified, lightSouthEast), trimHSLLightness(hslBitsetUnmodified, lightNorthEast),
											trimHSLLightness(hslBitsetUnmodified, lightNorthWest), 0, 0, 0, 0, rgbBitsetRandomized, 0);
								else {
									int clippingPath = ((overlayClippingPaths[plane][x][y]) + 1);
									byte clippingPathRotation = (overlayRotations[plane][x][y]);
									FloorDefinition floor = FloorDefinition.cache[overlayFloorId - 1];
									int textureid = floor.textureId;
									int hslBitset;
									int rgbBitset;
									if (textureid >= 0) {
										rgbBitset = Rasterizer3D.getAverageRgbColorForTexture(textureid, 0);
										hslBitset = -1;
									} else if (floor.rgbColor == 16711935) {
										hslBitset = -2;
										textureid = -1;
										rgbBitset = (Rasterizer3D.getRgbLookupTableId[method182(floor.hslColor2, 96)]);
									} else {
										hslBitset = getHSLBitset(floor.hue2, floor.saturation, floor.lightness);
										rgbBitset = (Rasterizer3D.getRgbLookupTableId[method182(floor.hslColor2, 96)]);
									}
									scene.renderTile(plane, x, y, clippingPath, clippingPathRotation, textureid, vertexSouthWest, vertexSouthEast, vertexNorthEast,
											vertexNorthWest, trimHSLLightness(hslBitsetUnmodified, lightSouthWest), trimHSLLightness(hslBitsetUnmodified, lightSouthEast), trimHSLLightness(hslBitsetUnmodified,
													lightNorthEast), trimHSLLightness(hslBitsetUnmodified, lightNorthWest), method182(hslBitset, lightSouthWest),
											method182(hslBitset, lightSouthEast), method182(hslBitset, lightNorthEast),
											method182(hslBitset, lightNorthWest), rgbBitsetRandomized, rgbBitset);
								}
							}
						}
					}
				}
			}
			for (int i_104_ = 1; i_104_ < regionSizeY - 1; i_104_++) {
				for (int i_105_ = 1; i_105_ < regionSizeX - 1; i_105_++)
					scene.setTileLogicHeight(plane, i_105_, i_104_, getVisibilityPlaneFor(i_104_, plane, i_105_, (byte) 0));
			}
		}
		scene.shadeModels(-10, -50, -50);
		for (int y = 0; y < regionSizeX; y++) {
			for (int x = 0; x < regionSizeY; x++) {
				if ((renderRuleFlags[1][y][x] & 0x2) == 2)
					scene.setBridgeMode(y, x);
			}
		}
		int renderRule1 = 1;
		int renderRule2 = 2;
		int renderRule3 = 4;
		for (int currentPlane = 0; currentPlane < 4; currentPlane++) {
			if (currentPlane > 0) {
				renderRule1 <<= 3;
				renderRule2 <<= 3;
				renderRule3 <<= 3;
			}
			for (int plane = 0; plane <= currentPlane; plane++) {
				for (int y = 0; y <= regionSizeY; y++) {
					for (int x = 0; x <= regionSizeX; x++) {
						if ((tileCullingBitsets[plane][x][y] & renderRule1) != 0) {
							int lowestOcclussionY = y;
							int higestOcclussionY = y;
							int lowestOcclussionPlane = plane;
							int higestOcclussionPlane = plane;
							for (/**/; lowestOcclussionY > 0; lowestOcclussionY--) {
								if (((tileCullingBitsets[plane][x][lowestOcclussionY - 1]) & renderRule1) == 0)
									break;
							}
							for (/**/; higestOcclussionY < regionSizeY; higestOcclussionY++) {
								if (((tileCullingBitsets[plane][x][higestOcclussionY + 1]) & renderRule1) == 0)
									break;
							}
							while_0_: for (/**/; lowestOcclussionPlane > 0; lowestOcclussionPlane--) {
								for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++) {
									if (((tileCullingBitsets[lowestOcclussionPlane - 1][x][occludedY]) & renderRule1) == 0)
										break while_0_;
								}
							}
							while_1_: for (/**/; higestOcclussionPlane < currentPlane; higestOcclussionPlane++) {
								for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++) {
									if (((tileCullingBitsets[higestOcclussionPlane + 1][x][occludedY]) & renderRule1) == 0)
										break while_1_;
								}
							}
							int occlussionSurface = (higestOcclussionPlane + 1 - lowestOcclussionPlane) * (higestOcclussionY - lowestOcclussionY + 1);
							if (occlussionSurface >= 8) {
								int highestOcclussionVertexHeightOffset = 240;
								int highestOcclussionVertexHeight = ((vertexHeights[higestOcclussionPlane][x][lowestOcclussionY]) - highestOcclussionVertexHeightOffset);
								int lowestOcclussionVertexHeight = (vertexHeights[lowestOcclussionPlane][x][lowestOcclussionY]);
								Scene.createCullingCluster(currentPlane, x * 128, x * 128, higestOcclussionY * 128 + 128, lowestOcclussionY * 128, highestOcclussionVertexHeight, lowestOcclussionVertexHeight,
										1);
								for (int occludedPlane = lowestOcclussionPlane; occludedPlane <= higestOcclussionPlane; occludedPlane++) {
									for (int occludedY = lowestOcclussionY; occludedY <= higestOcclussionY; occludedY++)
										tileCullingBitsets[occludedPlane][x][occludedY] &= renderRule1 ^ 0xffffffff;
								}
							}
						}
						if ((tileCullingBitsets[plane][x][y] & renderRule2) != 0) {
							int i_127_ = x;
							int i_128_ = x;
							int i_129_ = plane;
							int i_130_ = plane;
							for (/**/; i_127_ > 0; i_127_--) {
								if (((tileCullingBitsets[plane][i_127_ - 1][y]) & renderRule2) == 0)
									break;
							}
							for (/**/; i_128_ < regionSizeX; i_128_++) {
								if (((tileCullingBitsets[plane][i_128_ + 1][y]) & renderRule2) == 0)
									break;
							}
							while_2_: for (/**/; i_129_ > 0; i_129_--) {
								for (int i_131_ = i_127_; i_131_ <= i_128_; i_131_++) {
									if (((tileCullingBitsets[i_129_ - 1][i_131_][y]) & renderRule2) == 0)
										break while_2_;
								}
							}
							while_3_: for (/**/; i_130_ < currentPlane; i_130_++) {
								for (int i_132_ = i_127_; i_132_ <= i_128_; i_132_++) {
									if (((tileCullingBitsets[i_130_ + 1][i_132_][y]) & renderRule2) == 0)
										break while_3_;
								}
							}
							int i_133_ = (i_130_ + 1 - i_129_) * (i_128_ - i_127_ + 1);
							if (i_133_ >= 8) {
								int i_134_ = 240;
								int i_135_ = ((vertexHeights[i_130_][i_127_][y]) - i_134_);
								int i_136_ = (vertexHeights[i_129_][i_127_][y]);
								Scene.createCullingCluster(currentPlane, i_128_ * 128 + 128, i_127_ * 128, y * 128, y * 128, i_135_, i_136_,
										2);
								for (int i_137_ = i_129_; i_137_ <= i_130_; i_137_++) {
									for (int i_138_ = i_127_; i_138_ <= i_128_; i_138_++)
										tileCullingBitsets[i_137_][i_138_][y] &= renderRule2 ^ 0xffffffff;
								}
							}
						}
						if ((tileCullingBitsets[plane][x][y] & renderRule3) != 0) {
							int i_139_ = x;
							int i_140_ = x;
							int i_141_ = y;
							int i_142_ = y;
							for (/**/; i_141_ > 0; i_141_--) {
								if (((tileCullingBitsets[plane][x][i_141_ - 1]) & renderRule3) == 0)
									break;
							}
							for (/**/; i_142_ < regionSizeY; i_142_++) {
								if (((tileCullingBitsets[plane][x][i_142_ + 1]) & renderRule3) == 0)
									break;
							}
							while_4_: for (/**/; i_139_ > 0; i_139_--) {
								for (int i_143_ = i_141_; i_143_ <= i_142_; i_143_++) {
									if (((tileCullingBitsets[plane][i_139_ - 1][i_143_]) & renderRule3) == 0)
										break while_4_;
								}
							}
							while_5_: for (/**/; i_140_ < regionSizeX; i_140_++) {
								for (int i_144_ = i_141_; i_144_ <= i_142_; i_144_++) {
									if (((tileCullingBitsets[plane][i_140_ + 1][i_144_]) & renderRule3) == 0)
										break while_5_;
								}
							}
							if ((i_140_ - i_139_ + 1) * (i_142_ - i_141_ + 1) >= 4) {
								int i_145_ = (vertexHeights[plane][i_139_][i_141_]);
								Scene.createCullingCluster(currentPlane, i_140_ * 128 + 128, i_139_ * 128, i_142_ * 128 + 128, i_141_ * 128, i_145_, i_145_,
										4);
								for (int i_146_ = i_139_; i_146_ <= i_140_; i_146_++) {
									for (int i_147_ = i_141_; i_147_ <= i_142_; i_147_++)
										tileCullingBitsets[plane][i_146_][i_147_] &= renderRule3 ^ 0xffffffff;
								}
							}
						}
					}
				}
			}
		}
	}

	public void method168(int i, int i_148_, boolean bool, byte[] is, int i_149_, int i_150_, int i_151_,
                          CollisionMap[] class46s, int i_152_, int i_153_) {
		if (bool)
			anInt166 = 476;
		for (int i_154_ = 0; i_154_ < 8; i_154_++) {
			for (int i_155_ = 0; i_155_ < 8; i_155_++) {
				if (i_151_ + i_154_ > 0 && i_151_ + i_154_ < 103 && i_152_ + i_155_ > 0 && i_152_ + i_155_ < 103)
					class46s[i_149_].adjacency[i_151_ + i_154_][(i_152_ + i_155_)] &= ~0x1000000;
			}
		}
		Buffer class50_sub1_sub2 = new Buffer(is);
		for (int i_156_ = 0; i_156_ < 4; i_156_++) {
			for (int i_157_ = 0; i_157_ < 64; i_157_++) {
				for (int i_158_ = 0; i_158_ < 64; i_158_++) {
					if (i_156_ == i_150_ && i_157_ >= i_153_ && i_157_ < i_153_ + 8 && i_158_ >= i_148_
							&& i_158_ < i_148_ + 8)
						method183(0, (byte) -61, 0, class50_sub1_sub2, i, i_151_
								+ TiledUtils.getRotatedMapChunkX(i_157_ & 0x7, i_158_ & 0x7, i), i_149_, i_152_
								+ TiledUtils.getRotatedMapChunkY(i_157_ & 0x7, i_158_ & 0x7, i));
					else
						method183(0, (byte) -61, 0, class50_sub1_sub2, 0, -1, 0, -1);
				}
			}
		}
	}

	public static void passiveRequestGameObjectModels(OnDemandRequester onDemandRequester, Buffer buffer) {

		int gameObjectId = -1;
		while (true) {
			int gameObjectIdOffset = buffer.getSmart();
			if (gameObjectIdOffset == 0)
				break;
			gameObjectId += gameObjectIdOffset;
			GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(gameObjectId);
			gameObjectDefinition.passiveRequestModels(onDemandRequester);
			while (true) {
				int terminate = buffer.getSmart();
				if (terminate == 0)
					break;
				buffer.getUnsignedByte();
			}

		}
	}

	public static boolean method170(int i, byte i_162_, int i_163_) {
		GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(i_163_);
		if (i_162_ != aByte154) {
			for (int i_164_ = 1; i_164_ > 0; i_164_++) {
				/* empty */
			}
		}
		if (i == 11)
			i = 10;
		if (i >= 5 && i <= 8)
			i = 4;
		return gameObjectDefinition.method432(26261, i);
	}

	public static int trimHSLLightness(int i, int i_165_) {
		if (i == -1)
			return 12345678;
		i_165_ = i_165_ * (i & 0x7f) / 128;
		if (i_165_ < 2)
			i_165_ = 2;
		else if (i_165_ > 126)
			i_165_ = 126;
		return (i & 0xff80) + i_165_;
	}

	public void method172(int i, CollisionMap[] class46s, Scene class22, boolean bool, byte[] is, int i_166_, int i_167_,
						  int i_168_, int i_169_, int i_170_, int i_171_) {
		Buffer class50_sub1_sub2 = new Buffer(is);
		if (!bool) {
			int i_172_ = -1;
			for (;;) {
				int i_173_ = class50_sub1_sub2.getSmart();
				if (i_173_ == 0)
					break;
				i_172_ += i_173_;
				int i_174_ = 0;
				for (;;) {
					int i_175_ = class50_sub1_sub2.getSmart();
					if (i_175_ == 0)
						break;
					i_174_ += i_175_ - 1;
					int i_176_ = i_174_ & 0x3f;
					int i_177_ = i_174_ >> 6 & 0x3f;
					int i_178_ = i_174_ >> 12;
					int i_179_ = class50_sub1_sub2.getUnsignedByte();
					int i_180_ = i_179_ >> 2;
					int i_181_ = i_179_ & 0x3;
					if (i_178_ == i_171_ && i_177_ >= i_168_ && i_177_ < i_168_ + 8 && i_176_ >= i_170_
							&& i_176_ < i_170_ + 8) {
						GameObjectDefinition class47 = GameObjectDefinition.getDefinition(i_172_);
						int i_182_ = (i_169_ + TiledUtils.getRotatedLandscapeChunkX(i_167_, class47.sizeY, i_177_ & 0x7,
								i_176_ & 0x7, class47.sizeX));
						int i_183_ = (i_166_ + TiledUtils.getRotatedLandscapeChunkY(i_176_ & 0x7, class47.sizeY, i_167_, class47.sizeX, i_177_ & 0x7
						));
						if (i_182_ > 0 && i_183_ > 0 && i_182_ < 103 && i_183_ < 103) {
							int i_184_ = i;
							if ((renderRuleFlags[1][i_182_][i_183_] & 0x2) == 2)
								i_184_--;
							CollisionMap class46 = null;
							if (i_184_ >= 0)
								class46 = class46s[i_184_];
							renderObject(class22, class46, i_183_, i, i_182_, aByte139, i_181_ + i_167_ & 0x3, i_180_,
									i_172_);
						}
					}
				}
			}
		}
	}

	public void renderObject(Scene scene, CollisionMap collisionMap, int y, int plane, int x, byte i_187_, int face,
							 int type, int objectId) {
		if (!lowMemory
				|| (renderRuleFlags[0][x][y] & 0x2) != 0
				|| ((renderRuleFlags[plane][x][y] & 0x10) == 0 && getVisibilityPlaneFor(y, plane, x, (byte) 0) == onBuildTimePlane)) {
			if (plane < lowestPlane)
				lowestPlane = plane;
			int vertexHeight = vertexHeights[plane][x][y];
			int vertexHeightRight = vertexHeights[plane][x + 1][y];
			int vertexHeightTopRight = vertexHeights[plane][x + 1][y + 1];
			int vertexHeightTop = vertexHeights[plane][x][y + 1];
			int vertexMix = vertexHeight + vertexHeightRight + vertexHeightTopRight + vertexHeightTop >> 2;
			GameObjectDefinition gameObjectDefinition = GameObjectDefinition.getDefinition(objectId);
			int hash = x + (y << 7) + (objectId << 14) + 1073741824;
			if (!gameObjectDefinition.actionsBoolean)
				hash += -2147483648;
			byte objectConfig = (byte) ((face << 6) + type);
			if (type == 22) {
				if (!lowMemory || gameObjectDefinition.actionsBoolean || gameObjectDefinition.unknown) {
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(22, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, face, 22, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addGroundDecoration(x, y, plane, vertexMix, hash, renderable, objectConfig);
					if (gameObjectDefinition.solid && gameObjectDefinition.actionsBoolean && collisionMap != null)
						collisionMap.markBlocked(x, y);
				}
			} else if (type == 10 || type == 11) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(10, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, 10, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				if (renderable != null) {
					int i_198_ = 0;
					if (type == 11)
						i_198_ += 256;
					int sizeX;
					int sizeY;
					if (face == 1 || face == 3) {
						sizeX = gameObjectDefinition.sizeY;
						sizeY = gameObjectDefinition.sizeX;
					} else {
						sizeX = gameObjectDefinition.sizeX;
						sizeY = gameObjectDefinition.sizeY;
					}
					if (scene.addEntityB(x, y, plane, vertexMix, i_198_, sizeY, sizeX, hash, renderable, objectConfig
					)
							&& gameObjectDefinition.unknown2) {
						Model model;
						if (renderable instanceof Model)
							model = (Model) renderable;
						else
							model = gameObjectDefinition.getGameObjectModel(10, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
						if (model != null) {
							for (int sizeXCounter = 0; sizeXCounter <= sizeX; sizeXCounter++) {
								for (int sizeYCounter = 0; sizeYCounter <= sizeY; sizeYCounter++) {
									int shadowIntensity = model.diagonal2DAboveOrigin / 4;
									if (shadowIntensity > 30)
										shadowIntensity = 30;
									if (shadowIntensity > (tileShadowIntensity[plane][x + sizeXCounter][y + sizeYCounter]))
										tileShadowIntensity[plane][x + sizeXCounter][y + sizeYCounter] = (byte) shadowIntensity;
								}
							}
						}
					}
				}
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.method413(y, face, gameObjectDefinition.sizeY, gameObjectDefinition.sizeX, gameObjectDefinition.walkable, x,
							(byte) 52);
			} else if (type >= 12) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(type, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, type, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				scene.addEntityB(x, y, plane, vertexMix, 0, 1, 1, hash, renderable, objectConfig);
				if (type >= 12 && type <= 17 && type != 13 && plane > 0)
					tileCullingBitsets[plane][x][y] |= 0x924;
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.method413(y, face, gameObjectDefinition.sizeY, gameObjectDefinition.sizeX, gameObjectDefinition.walkable, x,
							(byte) 52);
			} else if (type == 0) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(0, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, 0, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				scene.addWall(x, y, plane, vertexMix, anIntArray158[face], 0, hash, renderable, null, objectConfig
				);
				if (face == 0) {
					if (gameObjectDefinition.unknown2) {
						tileShadowIntensity[plane][x][y] = (byte) 50;
						tileShadowIntensity[plane][x][y + 1] = (byte) 50;
					}
					if (gameObjectDefinition.aBoolean797)
						tileCullingBitsets[plane][x][y] |= 0x249;
				} else if (face == 1) {
					if (gameObjectDefinition.unknown2) {
						tileShadowIntensity[plane][x][y + 1] = (byte) 50;
						tileShadowIntensity[plane][x + 1][y + 1] = (byte) 50;
					}
					if (gameObjectDefinition.aBoolean797)
						tileCullingBitsets[plane][x][y + 1] |= 0x492;
				} else if (face == 2) {
					if (gameObjectDefinition.unknown2) {
						tileShadowIntensity[plane][x + 1][y] = (byte) 50;
						tileShadowIntensity[plane][x + 1][y + 1] = (byte) 50;
					}
					if (gameObjectDefinition.aBoolean797)
						tileCullingBitsets[plane][x + 1][y] |= 0x249;
				} else if (face == 3) {
					if (gameObjectDefinition.unknown2) {
						tileShadowIntensity[plane][x][y] = (byte) 50;
						tileShadowIntensity[plane][x + 1][y] = (byte) 50;
					}
					if (gameObjectDefinition.aBoolean797)
						tileCullingBitsets[plane][x][y] |= 0x492;
				}
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.markWall(x, y, type, face, gameObjectDefinition.walkable);
				if (gameObjectDefinition.unknown4 != 16)
					scene.displaceWallDecoration(x, y, plane, gameObjectDefinition.unknown4);
			} else if (type == 1) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(1, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, 1, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				scene.addWall(x, y, plane, vertexMix, anIntArray167[face], 0, hash, renderable, null, objectConfig
				);
				if (gameObjectDefinition.unknown2) {
					if (face == 0)
						tileShadowIntensity[plane][x][y + 1] = (byte) 50;
					else if (face == 1)
						tileShadowIntensity[plane][x + 1][y + 1] = (byte) 50;
					else if (face == 2)
						tileShadowIntensity[plane][x + 1][y] = (byte) 50;
					else if (face == 3)
						tileShadowIntensity[plane][x][y] = (byte) 50;
				}
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.markWall(x, y, type, face, gameObjectDefinition.walkable);
			} else if (type == 2) {
				int i_204_ = face + 1 & 0x3;
				Renderable renderable;
				Renderable renderable1;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null) {
					renderable = gameObjectDefinition.getGameObjectModel(2, 4 + face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					renderable1 = gameObjectDefinition.getGameObjectModel(2, i_204_, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				} else {
					renderable = new GameObject(objectId, 4 + face, 2, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
					renderable1 = new GameObject(objectId, i_204_, 2, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				}
				scene.addWall(x, y, plane, vertexMix, anIntArray158[face], anIntArray158[i_204_], hash, renderable, renderable1, objectConfig
				);
				if (gameObjectDefinition.aBoolean797) {
					if (face == 0) {
						tileCullingBitsets[plane][x][y] |= 0x249;
						tileCullingBitsets[plane][x][y + 1] |= 0x492;
					} else if (face == 1) {
						tileCullingBitsets[plane][x][y + 1] |= 0x492;
						tileCullingBitsets[plane][x + 1][y] |= 0x249;
					} else if (face == 2) {
						tileCullingBitsets[plane][x + 1][y] |= 0x249;
						tileCullingBitsets[plane][x][y] |= 0x492;
					} else if (face == 3) {
						tileCullingBitsets[plane][x][y] |= 0x492;
						tileCullingBitsets[plane][x][y] |= 0x249;
					}
				}
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.markWall(x, y, type, face, gameObjectDefinition.walkable);
				if (gameObjectDefinition.unknown4 != 16)
					scene.displaceWallDecoration(x, y, plane, gameObjectDefinition.unknown4);
			} else if (type == 3) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(3, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, 3, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				scene.addWall(x, y, plane, vertexMix, anIntArray167[face], 0, hash, renderable, null, objectConfig
				);
				if (gameObjectDefinition.unknown2) {
					if (face == 0)
						tileShadowIntensity[plane][x][y + 1] = (byte) 50;
					else if (face == 1)
						tileShadowIntensity[plane][x + 1][y + 1] = (byte) 50;
					else if (face == 2)
						tileShadowIntensity[plane][x + 1][y] = (byte) 50;
					else if (face == 3)
						tileShadowIntensity[plane][x][y] = (byte) 50;
				}
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.markWall(x, y, type, face, gameObjectDefinition.walkable);
			} else if (type == 9) {
				Renderable renderable;
				if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
					renderable = gameObjectDefinition.getGameObjectModel(type, face, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
				else
					renderable = new GameObject(objectId, face, type, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                            true);
				scene.addEntityB(x, y, plane, vertexMix, 0, 1, 1, hash, renderable, objectConfig);
				if (gameObjectDefinition.solid && collisionMap != null)
					collisionMap.method413(y, face, gameObjectDefinition.sizeY, gameObjectDefinition.sizeX, gameObjectDefinition.walkable, x,
							(byte) 52);
			} else {
				if (gameObjectDefinition.adjustToTerrain) {
					if (face == 1) {
						int i_206_ = vertexHeightTop;
						vertexHeightTop = vertexHeightTopRight;
						vertexHeightTopRight = vertexHeightRight;
						vertexHeightRight = vertexHeight;
						vertexHeight = i_206_;
					} else if (face == 2) {
						int i_207_ = vertexHeightTop;
						vertexHeightTop = vertexHeightRight;
						vertexHeightRight = i_207_;
						i_207_ = vertexHeightTopRight;
						vertexHeightTopRight = vertexHeight;
						vertexHeight = i_207_;
					} else if (face == 3) {
						int i_208_ = vertexHeightTop;
						vertexHeightTop = vertexHeight;
						vertexHeight = vertexHeightRight;
						vertexHeightRight = vertexHeightTopRight;
						vertexHeightTopRight = i_208_;
					}
				}
				if (type == 4) {
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(4, 0, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, 0, 4, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addWallDecoration(x, y, plane, vertexMix, 0, 0, face * 512, hash, renderable, objectConfig, anIntArray158[face]
					);
				} else if (type == 5) {
					int offset = 16;
					int i_210_ = scene.method267(plane, x, y);
					if (i_210_ > 0)
						offset = GameObjectDefinition.getDefinition(i_210_ >> 14 & 0x7fff).unknown4;
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(4, 0, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, 0, 4, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addWallDecoration(x, y, plane, vertexMix, anIntArray161[face] * offset, anIntArray143[face] * offset, face * 512, hash, renderable, objectConfig, anIntArray158[face]
					);
				} else if (type == 6) {
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(4, 0, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, 0, 4, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addWallDecoration(x, y, plane, vertexMix, 0, 0, face, hash, renderable, objectConfig, 256
					);
				} else if (type == 7) {
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(4, 0, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, 0, 4, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addWallDecoration(x, y, plane, vertexMix, 0, 0, face, hash, renderable, objectConfig, 512
					);
				} else if (type == 8) {
					Renderable renderable;
					if (gameObjectDefinition.animationId == -1 && gameObjectDefinition.childrenIds == null)
						renderable = gameObjectDefinition.getGameObjectModel(4, 0, vertexHeight, vertexHeightRight, vertexHeightTopRight, vertexHeightTop, -1);
					else
						renderable = new GameObject(objectId, 0, 4, vertexHeightRight, vertexHeightTopRight, vertexHeight, vertexHeightTop, gameObjectDefinition.animationId,
                                true);
					scene.addWallDecoration(x, y, plane, vertexMix, 0, 0, face, hash, renderable, objectConfig, 768
					);
				}
			}
		}
	}

	public void method174(int i, boolean bool, int i_211_, int i_212_, byte[] is, int i_213_, CollisionMap[] class46s) {
		if (bool)
			anInt166 = -379;
		for (int i_214_ = 0; i_214_ < 4; i_214_++) {
			for (int i_215_ = 0; i_215_ < 64; i_215_++) {
				for (int i_216_ = 0; i_216_ < 64; i_216_++) {
					if (i_212_ + i_215_ > 0 && i_212_ + i_215_ < 103 && i + i_216_ > 0 && i + i_216_ < 103)
						class46s[i_214_].adjacency[i_212_ + i_215_][i + i_216_] &= ~0x1000000;
				}
			}
		}
		Buffer class50_sub1_sub2 = new Buffer(is);
		for (int i_217_ = 0; i_217_ < 4; i_217_++) {
			for (int i_218_ = 0; i_218_ < 64; i_218_++) {
				for (int i_219_ = 0; i_219_ < 64; i_219_++)
					method183(i_213_, (byte) -61, i_211_, class50_sub1_sub2, 0, i_218_ + i_212_, i_217_, i_219_ + i);
			}
		}
	}





	public static int method176(int i, int i_226_, int i_227_, int i_228_) {
		int i_229_ = (65536 - Rasterizer3D.COSINE[i_227_ * 1024 / i_228_] >> 1);
		return (i * (65536 - i_229_) >> 16) + (i_226_ * i_229_ >> 16);
	}

	public int getHSLBitset(int i, int i_230_, int i_231_) {
		if (i_231_ > 179)
			i_230_ /= 2;
		if (i_231_ > 192)
			i_230_ /= 2;
		if (i_231_ > 217)
			i_230_ /= 2;
		if (i_231_ > 243)
			i_230_ /= 2;
		int i_232_ = (i / 4 << 10) + (i_230_ / 32 << 7) + i_231_ / 2;
		return i_232_;
	}

	public static int method178(int i, int i_233_) {
		int i_234_ = (calculateNoise(i - 1, i_233_ - 1) + calculateNoise(i + 1, i_233_ - 1) + calculateNoise(i - 1, i_233_ + 1) + calculateNoise(
				i + 1, i_233_ + 1));
		int i_235_ = (calculateNoise(i - 1, i_233_) + calculateNoise(i + 1, i_233_) + calculateNoise(i, i_233_ - 1) + calculateNoise(i,
				i_233_ + 1));
		int i_236_ = calculateNoise(i, i_233_);
		return i_234_ / 16 + i_235_ / 8 + i_236_ / 4;
	}

	public void method179(int i, CollisionMap[] class46s, int i_237_, int i_238_, Scene class22, byte[] is) {
		if (i_238_ < 0) {
			Buffer class50_sub1_sub2 = new Buffer(is);
			int i_239_ = -1;
			for (;;) {
				int i_240_ = class50_sub1_sub2.getSmart();
				if (i_240_ == 0)
					break;
				i_239_ += i_240_;
				int i_241_ = 0;
				for (;;) {
					int i_242_ = class50_sub1_sub2.getSmart();
					if (i_242_ == 0)
						break;
					i_241_ += i_242_ - 1;
					int i_243_ = i_241_ & 0x3f;
					int i_244_ = i_241_ >> 6 & 0x3f;
					int i_245_ = i_241_ >> 12;
					int i_246_ = class50_sub1_sub2.getUnsignedByte();
					int i_247_ = i_246_ >> 2;
					int i_248_ = i_246_ & 0x3;
					int i_249_ = i_244_ + i_237_;
					int i_250_ = i_243_ + i;
					if (i_249_ > 0 && i_250_ > 0 && i_249_ < 103 && i_250_ < 103) {
						int i_251_ = i_245_;
						if ((renderRuleFlags[1][i_249_][i_250_] & 0x2) == 2)
							i_251_--;
						CollisionMap class46 = null;
						if (i_251_ >= 0)
							class46 = class46s[i_251_];
						renderObject(class22, class46, i_250_, i_245_, i_249_, aByte139, i_248_, i_247_, i_239_);
					}
				}
			}
		}
	}

	public void initiateVertexHeights(int xOffset, int xLength, int yOffset, int yLength) {
		for (int y = yOffset; y <= yOffset + yLength; y++) {
			for (int x = xOffset; x <= xOffset + xLength; x++) {
				if (x >= 0 && x < regionSizeX && y >= 0 && y < regionSizeY) {
					tileShadowIntensity[0][x][y] = (byte) 127;
					if (x == xOffset && x > 0)
						vertexHeights[0][x][y] = vertexHeights[0][x - 1][y];
					if (x == xOffset + xLength && x < regionSizeX - 1)
						vertexHeights[0][x][y] = vertexHeights[0][x + 1][y];
					if (y == yOffset && y > 0)
						vertexHeights[0][x][y] = vertexHeights[0][x][y - 1];
					if (y == yOffset + yLength && y < regionSizeY - 1)
						vertexHeights[0][x][y] = vertexHeights[0][x][y + 1];
				}
			}
		}

	}

	public static boolean method181(int i, int i_258_, byte[] is, int i_259_) {
		boolean bool = true;
		Buffer class50_sub1_sub2 = new Buffer(is);
		if (i_259_ != 24515)
			throw new NullPointerException();
		int i_260_ = -1;
		for (;;) {
			int i_261_ = class50_sub1_sub2.getSmart();
			if (i_261_ == 0)
				break;
			i_260_ += i_261_;
			int i_262_ = 0;
			boolean bool_263_ = false;
			for (;;) {
				if (bool_263_) {
					int i_264_ = class50_sub1_sub2.getSmart();
					if (i_264_ == 0)
						break;
					class50_sub1_sub2.getUnsignedByte();
				} else {
					int i_265_ = class50_sub1_sub2.getSmart();
					if (i_265_ == 0)
						break;
					i_262_ += i_265_ - 1;
					int i_266_ = i_262_ & 0x3f;
					int i_267_ = i_262_ >> 6 & 0x3f;
					int i_268_ = class50_sub1_sub2.getUnsignedByte() >> 2;
					int i_269_ = i_267_ + i;
					int i_270_ = i_266_ + i_258_;
					if (i_269_ > 0 && i_270_ > 0 && i_269_ < 103 && i_270_ < 103) {
						GameObjectDefinition class47 = GameObjectDefinition.getDefinition(i_260_);
						if (i_268_ != 22 || !lowMemory || class47.actionsBoolean || class47.unknown) {
							bool &= class47.isModelCached();
							bool_263_ = true;
						}
					}
				}
			}
		}
		return bool;
	}

	public int method182(int i, int i_271_) {
		if (i == -2)
			return 12345678;
		if (i == -1) {
			if (i_271_ < 0)
				i_271_ = 0;
			else if (i_271_ > 127)
				i_271_ = 127;
			i_271_ = 127 - i_271_;
			return i_271_;
		}
		i_271_ = i_271_ * (i & 0x7f) / 128;
		if (i_271_ < 2)
			i_271_ = 2;
		else if (i_271_ > 126)
			i_271_ = 126;
		return (i & 0xff80) + i_271_;
	}

	public void method183(int i, byte i_272_, int i_273_, Buffer class50_sub1_sub2, int i_274_, int i_275_,
						  int i_276_, int i_277_) {
		if (i_272_ != -61)
			aBoolean140 = !aBoolean140;
		if (i_275_ >= 0 && i_275_ < 104 && i_277_ >= 0 && i_277_ < 104) {
			renderRuleFlags[i_276_][i_275_][i_277_] = (byte) 0;
			for (;;) {
				int i_278_ = class50_sub1_sub2.getUnsignedByte();
				if (i_278_ == 0) {
					if (i_276_ == 0)
						vertexHeights[0][i_275_][i_277_] = -calculateVertexHeight(932731 + i_275_ + i, 556238 + i_277_
								+ i_273_) * 8;
					else {
						vertexHeights[i_276_][i_275_][i_277_] = (vertexHeights[i_276_ - 1][i_275_][i_277_] - 240);
						break;
					}
					break;
				}
				if (i_278_ == 1) {
					int i_279_ = class50_sub1_sub2.getUnsignedByte();
					if (i_279_ == 1)
						i_279_ = 0;
					if (i_276_ == 0)
						vertexHeights[0][i_275_][i_277_] = -i_279_ * 8;
					else {
						vertexHeights[i_276_][i_275_][i_277_] = (vertexHeights[i_276_ - 1][i_275_][i_277_] - i_279_ * 8);
						break;
					}
					break;
				}
				if (i_278_ <= 49) {
					overlayFloorIds[i_276_][i_275_][i_277_] = class50_sub1_sub2.getByte();
					overlayClippingPaths[i_276_][i_275_][i_277_] = (byte) ((i_278_ - 2) / 4);
					overlayRotations[i_276_][i_275_][i_277_] = (byte) (i_278_ - 2 + i_274_ & 0x3);
				} else if (i_278_ <= 81)
					renderRuleFlags[i_276_][i_275_][i_277_] = (byte) (i_278_ - 49);
				else
					underlayFloorIds[i_276_][i_275_][i_277_] = (byte) (i_278_ - 81);
			}
		} else {
			for (;;) {
				int i_280_ = class50_sub1_sub2.getUnsignedByte();
				if (i_280_ == 0)
					break;
				if (i_280_ == 1) {
					class50_sub1_sub2.getUnsignedByte();
					break;
				}
				if (i_280_ <= 49)
					class50_sub1_sub2.getUnsignedByte();
			}
		}
	}

	public static int calculateVertexHeight(int i, int i_281_) {
		int mapHeight = (method163(i + 45365, i_281_ + 91923, 4) - 128
				+ (method163(i + 10294, i_281_ + 37821, 2) - 128 >> 1) + (method163(i, i_281_, 1) - 128 >> 2));
		mapHeight = (int) (mapHeight * 0.3) + 35;
		if (mapHeight < 10)
			mapHeight = 10;
		else if (mapHeight > 60)
			mapHeight = 60;
		return mapHeight;
	}
}
