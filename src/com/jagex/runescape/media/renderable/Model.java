package com.jagex.runescape.media.renderable;

import com.jagex.runescape.net.Buffer;
import com.jagex.runescape.net.requester.Requester;
import com.jagex.runescape.media.*;

public class Model extends Renderable {


    public int anInt1636 = 932;
    public int anInt1637 = 426;
    public boolean aBoolean1638 = false;
    public boolean aBoolean1639 = true;
    public int anInt1640 = -252;
    ;
    public boolean aBoolean1641 = false;
    public static int anInt1642;
    public static Model EMPTY_MODEL = new Model();
    public static int anIntArray1644[] = new int[2000];
    public static int anIntArray1645[] = new int[2000];
    public static int anIntArray1646[] = new int[2000];
    public static int anIntArray1647[] = new int[2000];
    public int vertexCount;
    public int verticesX[];
    public int verticesY[];
    public int verticesZ[];
    public int triangleCount;
    public int trianglePointsX[];
    public int trianglePointsY[];
    public int trianglePointsZ[];
    public int anIntArray1656[];
    public int anIntArray1657[];
    public int anIntArray1658[];
    public int texturePoints[];
    public int trianglePriorities[];
    public int triangleAlphaValues[];
    public int triangleColorValues[];
    public int anInt1663;
    public int texturedTriangleCount;
    public int texturedTrianglePointsX[];
    public int texturedTrianglePointsY[];
    public int texturedTrianglePointsZ[];
    public int anInt1668;
    public int anInt1669;
    public int anInt1670;
    public int shadowIntensity;
    public int maxY;
    public int anInt1673;
    public int anInt1674;
    public int anInt1675;
    public int vertexSkins[];
    public int triangleSkinValues[];
    public int vectorSkin[][];
    public int triangleSkin[][];
    public boolean oneSquareModel = false;
    public VertexNormal aClass40Array1681[];
    public static ModelHeader modelHeaders[];
    public static Requester requester;
    public static boolean aBooleanArray1684[] = new boolean[4096];
    public static boolean aBooleanArray1685[] = new boolean[4096];
    public static int anIntArray1686[] = new int[4096];
    public static int anIntArray1687[] = new int[4096];
    public static int anIntArray1688[] = new int[4096];
    public static int anIntArray1689[] = new int[4096];
    public static int anIntArray1690[] = new int[4096];
    public static int anIntArray1691[] = new int[4096];
    public static int anIntArray1692[] = new int[1500];
    public static int anIntArrayArray1693[][] = new int[1500][512];
    public static int anIntArray1694[] = new int[12];
    public static int anIntArrayArray1695[][] = new int[12][2000];
    public static int anIntArray1696[] = new int[2000];
    public static int anIntArray1697[] = new int[2000];
    public static int anIntArray1698[] = new int[12];
    public static int anIntArray1699[] = new int[10];
    public static int anIntArray1700[] = new int[10];
    public static int anIntArray1701[] = new int[10];
    public static int vertexXModifier;
    public static int vertexYModifier;
    public static int vertexZModifier;
    public static boolean aBoolean1705;
    public static int anInt1706;
    public static int anInt1707;
    public static int anInt1708;
    public static int anIntArray1709[] = new int[1000];
    public static int SINE[];
    public static int COSINE[];
    public static int anIntArray1712[];
    public static int anIntArray1713[];

    public Model() {
    }


    public static void reset() {
        Model.modelHeaders = null;
        Model.aBooleanArray1684 = null;
        Model.aBooleanArray1685 = null;
        Model.anIntArray1686 = null;
        Model.anIntArray1687 = null;
        Model.anIntArray1688 = null;
        Model.anIntArray1689 = null;
        Model.anIntArray1690 = null;
        Model.anIntArray1691 = null;
        Model.anIntArray1692 = null;
        Model.anIntArrayArray1693 = null;
        Model.anIntArray1694 = null;
        Model.anIntArrayArray1695 = null;
        Model.anIntArray1696 = null;
        Model.anIntArray1697 = null;
        Model.anIntArray1698 = null;
        Model.SINE = null;
        Model.COSINE = null;
        Model.anIntArray1712 = null;
        Model.anIntArray1713 = null;

    }

    public static void init(int modelCount, Requester requester) {
        Model.modelHeaders = new ModelHeader[modelCount];
        Model.requester = requester;
    }

    public static void loadModelHeader(byte[] modelData, int modelId) {
        if (modelData == null) {
            ModelHeader modelHeader = modelHeaders[modelId] = new ModelHeader();
            modelHeader.vertexCount = 0;
            modelHeader.triangleCount = 0;
            modelHeader.texturedTriangleCount = 0;
            return;
        }
        Buffer buffer = new Buffer(modelData);
        buffer.currentPosition = modelData.length - 18;
        ModelHeader modelHeader = modelHeaders[modelId] = new ModelHeader();
        modelHeader.modelData = modelData;
        modelHeader.vertexCount = buffer.getUnsignedLEShort();
        modelHeader.triangleCount = buffer.getUnsignedLEShort();
        modelHeader.texturedTriangleCount = buffer.getUnsignedByte();
        int useTextures = buffer.getUnsignedByte();
        int useTrianglePriority = buffer.getUnsignedByte();
        int useTransparency = buffer.getUnsignedByte();
        int useTriangleSkinning = buffer.getUnsignedByte();
        int useVertexSkinning = buffer.getUnsignedByte();
        int xDataLength = buffer.getUnsignedLEShort();
        int yDataLength = buffer.getUnsignedLEShort();
        int zDataLength = buffer.getUnsignedLEShort();
        int triangleDataLength = buffer.getUnsignedLEShort();
        int offset = 0;
        modelHeader.vertexDirectionOffset = offset;
        offset += modelHeader.vertexCount;
        modelHeader.triangleTypeOffset = offset;
        offset += modelHeader.triangleCount;
        modelHeader.trianglePriorityOffset = offset;
        if (useTrianglePriority == 255)
            offset += modelHeader.triangleCount;
        else
            modelHeader.trianglePriorityOffset = -useTrianglePriority - 1;
        modelHeader.triangleSkinOffset = offset;
        if (useTriangleSkinning == 1)
            offset += modelHeader.triangleCount;
        else
            modelHeader.triangleSkinOffset = -1;
        modelHeader.texturePointerOffset = offset;
        if (useTextures == 1)
            offset += modelHeader.triangleCount;
        else
            modelHeader.texturePointerOffset = -1;
        modelHeader.vertexSkinOffset = offset;
        if (useVertexSkinning == 1)
            offset += modelHeader.vertexCount;
        else
            modelHeader.vertexSkinOffset = -1;
        modelHeader.triangleAlphaOffset = offset;
        if (useTransparency == 1)
            offset += modelHeader.triangleCount;
        else
            modelHeader.triangleAlphaOffset = -1;
        modelHeader.triangleDataOffset = offset;
        offset += triangleDataLength;
        modelHeader.colorDataOffset = offset;
        offset += modelHeader.triangleCount * 2;
        modelHeader.uvMapTriangleOffset = offset;
        offset += modelHeader.texturedTriangleCount * 6;
        modelHeader.xDataOffset = offset;
        offset += xDataLength;
        modelHeader.yDataOffset = offset;
        offset += yDataLength;
        modelHeader.zDataOffset = offset;
        offset += zDataLength;
    }

    public static void resetModel(int model) {
        Model.modelHeaders[model] = null;
    }

    public static Model getModel(int model) {
        if (Model.modelHeaders == null)
            return null;
        ModelHeader modelHeader = Model.modelHeaders[model];
        if (modelHeader == null) {
            Model.requester.requestModel(model);
            return null;
        } else {
            return new Model(model);
        }
    }

    public static boolean loaded(int id) {
        if (Model.modelHeaders == null)
            return false;
        ModelHeader modelHeader = Model.modelHeaders[id];
        if (modelHeader == null) {
            Model.requester.requestModel(id);
            return false;
        } else {
            return true;
        }
    }


    public Model(int modelId) {
        aBoolean1638 = false;
        aBoolean1639 = true;
        anInt1640 = -252;
        anInt1642++;
        ModelHeader modelHeader = modelHeaders[modelId];
        vertexCount = modelHeader.vertexCount;
        triangleCount = modelHeader.triangleCount;
        texturedTriangleCount = modelHeader.texturedTriangleCount;
        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        trianglePointsX = new int[triangleCount];
        trianglePointsY = new int[triangleCount];
        trianglePointsZ = new int[triangleCount];
        texturedTrianglePointsX = new int[texturedTriangleCount];
        texturedTrianglePointsY = new int[texturedTriangleCount];
        texturedTrianglePointsZ = new int[texturedTriangleCount];
        if (modelHeader.vertexSkinOffset >= 0)
            vertexSkins = new int[vertexCount];
        if (modelHeader.texturePointerOffset >= 0)
            texturePoints = new int[triangleCount];
        if (modelHeader.trianglePriorityOffset >= 0)
            trianglePriorities = new int[triangleCount];
        else
            anInt1663 = -modelHeader.trianglePriorityOffset - 1;
        if (modelHeader.triangleAlphaOffset >= 0)
            triangleAlphaValues = new int[triangleCount];
        if (modelHeader.triangleSkinOffset >= 0)
            triangleSkinValues = new int[triangleCount];
        triangleColorValues = new int[triangleCount];
        Buffer vertexDirectionOffsetBuffer = new Buffer(modelHeader.modelData);
        vertexDirectionOffsetBuffer.currentPosition = modelHeader.vertexDirectionOffset;
        Buffer xDataOffsetBuffer = new Buffer(modelHeader.modelData);
        xDataOffsetBuffer.currentPosition = modelHeader.xDataOffset;
        Buffer yDataOffsetBuffer = new Buffer(modelHeader.modelData);
        yDataOffsetBuffer.currentPosition = modelHeader.yDataOffset;
        Buffer zDataOffsetBuffer = new Buffer(modelHeader.modelData);
        zDataOffsetBuffer.currentPosition = modelHeader.zDataOffset;
        Buffer vertexSkinOffsetBuffer = new Buffer(modelHeader.modelData);
        vertexSkinOffsetBuffer.currentPosition = modelHeader.vertexSkinOffset;
        int baseOffsetX = 0;
        int baseOffsetY = 0;
        int baseOffsetz = 0;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int flag = vertexDirectionOffsetBuffer.getUnsignedByte();
            int currentOffsetX = 0;
            if ((flag & 1) != 0)
                currentOffsetX = xDataOffsetBuffer.getSignedSmart();
            int currentOffsetY = 0;
            if ((flag & 2) != 0)
                currentOffsetY = yDataOffsetBuffer.getSignedSmart();
            int currentOffsetZ = 0;
            if ((flag & 4) != 0)
                currentOffsetZ = zDataOffsetBuffer.getSignedSmart();
            verticesX[vertex] = baseOffsetX + currentOffsetX;
            verticesY[vertex] = baseOffsetY + currentOffsetY;
            verticesZ[vertex] = baseOffsetz + currentOffsetZ;
            baseOffsetX = verticesX[vertex];
            baseOffsetY = verticesY[vertex];
            baseOffsetz = verticesZ[vertex];
            if (vertexSkins != null)
                vertexSkins[vertex] = vertexSkinOffsetBuffer.getUnsignedByte();
        }

        vertexDirectionOffsetBuffer.currentPosition = modelHeader.colorDataOffset;
        xDataOffsetBuffer.currentPosition = modelHeader.texturePointerOffset;
        yDataOffsetBuffer.currentPosition = modelHeader.trianglePriorityOffset;
        zDataOffsetBuffer.currentPosition = modelHeader.triangleAlphaOffset;
        vertexSkinOffsetBuffer.currentPosition = modelHeader.triangleSkinOffset;
        for (int l1 = 0; l1 < triangleCount; l1++) {
            triangleColorValues[l1] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
            if (texturePoints != null)
                texturePoints[l1] = xDataOffsetBuffer.getUnsignedByte();
            if (trianglePriorities != null)
                trianglePriorities[l1] = yDataOffsetBuffer.getUnsignedByte();
            if (triangleAlphaValues != null)
                triangleAlphaValues[l1] = zDataOffsetBuffer.getUnsignedByte();
            if (triangleSkinValues != null)
                triangleSkinValues[l1] = vertexSkinOffsetBuffer.getUnsignedByte();
        }

        vertexDirectionOffsetBuffer.currentPosition = modelHeader.triangleDataOffset;
        xDataOffsetBuffer.currentPosition = modelHeader.triangleTypeOffset;
        int trianglePointOffsetX = 0;
        int trianglePointOffsetY = 0;
        int trianglePointOffsetZ = 0;
        int offset = 0;
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            int type = xDataOffsetBuffer.getUnsignedByte();
            if (type == 1) {
                trianglePointOffsetX = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetX;
                trianglePointOffsetY = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetY;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetZ;
                trianglePointsX[triangle] = trianglePointOffsetX;
                trianglePointsY[triangle] = trianglePointOffsetY;
                trianglePointsZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 2) {
                trianglePointOffsetY = trianglePointOffsetZ;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetZ;
                trianglePointsX[triangle] = trianglePointOffsetX;
                trianglePointsY[triangle] = trianglePointOffsetY;
                trianglePointsZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 3) {
                trianglePointOffsetX = trianglePointOffsetZ;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetZ;
                trianglePointsX[triangle] = trianglePointOffsetX;
                trianglePointsY[triangle] = trianglePointOffsetY;
                trianglePointsZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 4) {
                int oldTrianglePointOffsetX = trianglePointOffsetX;
                trianglePointOffsetX = trianglePointOffsetY;
                trianglePointOffsetY = oldTrianglePointOffsetX;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.getSignedSmart() + offset;
                offset = trianglePointOffsetZ;
                trianglePointsX[triangle] = trianglePointOffsetX;
                trianglePointsY[triangle] = trianglePointOffsetY;
                trianglePointsZ[triangle] = trianglePointOffsetZ;
            }
        }

        vertexDirectionOffsetBuffer.currentPosition = modelHeader.uvMapTriangleOffset;
        for (int triangle = 0; triangle < texturedTriangleCount; triangle++) {
            texturedTrianglePointsX[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
            texturedTrianglePointsY[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
            texturedTrianglePointsZ[triangle] = vertexDirectionOffsetBuffer.getUnsignedLEShort();
        }

    }

    public Model(int i, Model subModels[]) {
        aBoolean1638 = false;
        aBoolean1639 = true;
        anInt1640 = -252;
        anInt1642++;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        anInt1663 = -1;
        for (int j = 0; j < i; j++) {
            Model class50_sub1_sub4_sub4 = subModels[j];
            if (class50_sub1_sub4_sub4 != null) {
                vertexCount += class50_sub1_sub4_sub4.vertexCount;
                triangleCount += class50_sub1_sub4_sub4.triangleCount;
                texturedTriangleCount += class50_sub1_sub4_sub4.texturedTriangleCount;
                flag |= class50_sub1_sub4_sub4.texturePoints != null;
                if (class50_sub1_sub4_sub4.trianglePriorities != null) {
                    flag1 = true;
                } else {
                    if (anInt1663 == -1)
                        anInt1663 = class50_sub1_sub4_sub4.anInt1663;
                    if (anInt1663 != class50_sub1_sub4_sub4.anInt1663)
                        flag1 = true;
                }
                flag2 |= class50_sub1_sub4_sub4.triangleAlphaValues != null;
                flag3 |= class50_sub1_sub4_sub4.triangleSkinValues != null;
            }
        }

        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        vertexSkins = new int[vertexCount];
        trianglePointsX = new int[triangleCount];
        trianglePointsY = new int[triangleCount];
        trianglePointsZ = new int[triangleCount];
        texturedTrianglePointsX = new int[texturedTriangleCount];
        texturedTrianglePointsY = new int[texturedTriangleCount];
        texturedTrianglePointsZ = new int[texturedTriangleCount];
        if (flag)
            texturePoints = new int[triangleCount];
        if (flag1)
            trianglePriorities = new int[triangleCount];
        if (flag2)
            triangleAlphaValues = new int[triangleCount];
        if (flag3)
            triangleSkinValues = new int[triangleCount];
        triangleColorValues = new int[triangleCount];
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        int k = 0;
        for (int l = 0; l < i; l++) {
            Model model_44_ = subModels[l];
            if (model_44_ != null) {
                for (int i1 = 0; i1 < model_44_.triangleCount; i1++) {
                    if (flag)
                        if (model_44_.texturePoints == null) {
                            texturePoints[triangleCount] = 0;
                        } else {
                            int j1 = model_44_.texturePoints[i1];
                            if ((j1 & 2) == 2)
                                j1 += k << 2;
                            texturePoints[triangleCount] = j1;
                        }
                    if (flag1)
                        if (model_44_.trianglePriorities == null)
                            trianglePriorities[triangleCount] = model_44_.anInt1663;
                        else
                            trianglePriorities[triangleCount] = model_44_.trianglePriorities[i1];
                    if (flag2)
                        if (model_44_.triangleAlphaValues == null)
                            triangleAlphaValues[triangleCount] = 0;
                        else
                            triangleAlphaValues[triangleCount] = model_44_.triangleAlphaValues[i1];
                    if (flag3 && model_44_.triangleSkinValues != null)
                        triangleSkinValues[triangleCount] = model_44_.triangleSkinValues[i1];
                    triangleColorValues[triangleCount] = model_44_.triangleColorValues[i1];
                    trianglePointsX[triangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.trianglePointsX[i1]);
                    trianglePointsY[triangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.trianglePointsY[i1]);
                    trianglePointsZ[triangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.trianglePointsZ[i1]);
                    triangleCount++;
                }

                for (int k1 = 0; k1 < model_44_.texturedTriangleCount; k1++) {
                    texturedTrianglePointsX[texturedTriangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.texturedTrianglePointsX[k1]);
                    texturedTrianglePointsY[texturedTriangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.texturedTrianglePointsY[k1]);
                    texturedTrianglePointsZ[texturedTriangleCount] = getFirstIdenticalVertexIndex(model_44_,
                            model_44_.texturedTrianglePointsZ[k1]);
                    texturedTriangleCount++;
                }

                k += model_44_.texturedTriangleCount;
            }
        }

    }

    public Model(int i, int j, Model[] models) {
        aBoolean1638 = false;
        aBoolean1639 = true;
        anInt1640 = -252;
        anInt1642++;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        anInt1663 = -1;
        for (int k = 0; k < i; k++) {
            Model class50_sub1_sub4_sub4 = models[k];
            if (class50_sub1_sub4_sub4 != null) {
                vertexCount += class50_sub1_sub4_sub4.vertexCount;
                triangleCount += class50_sub1_sub4_sub4.triangleCount;
                texturedTriangleCount += class50_sub1_sub4_sub4.texturedTriangleCount;
                flag1 |= class50_sub1_sub4_sub4.texturePoints != null;
                if (class50_sub1_sub4_sub4.trianglePriorities != null) {
                    flag2 = true;
                } else {
                    if (anInt1663 == -1)
                        anInt1663 = class50_sub1_sub4_sub4.anInt1663;
                    if (anInt1663 != class50_sub1_sub4_sub4.anInt1663)
                        flag2 = true;
                }
                flag3 |= class50_sub1_sub4_sub4.triangleAlphaValues != null;
                flag4 |= class50_sub1_sub4_sub4.triangleColorValues != null;
            }
        }

        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        trianglePointsX = new int[triangleCount];
        trianglePointsY = new int[triangleCount];
        trianglePointsZ = new int[triangleCount];
        anIntArray1656 = new int[triangleCount];
        anIntArray1657 = new int[triangleCount];
        anIntArray1658 = new int[triangleCount];
        texturedTrianglePointsX = new int[texturedTriangleCount];
        texturedTrianglePointsY = new int[texturedTriangleCount];
        texturedTrianglePointsZ = new int[texturedTriangleCount];
        if (flag1)
            texturePoints = new int[triangleCount];
        if (flag2)
            trianglePriorities = new int[triangleCount];
        if (flag3)
            triangleAlphaValues = new int[triangleCount];
        if (flag4)
            triangleColorValues = new int[triangleCount];
        vertexCount = 0;
        if (j != 0)
            throw new NullPointerException();
        triangleCount = 0;
        texturedTriangleCount = 0;
        int l = 0;
        for (int i1 = 0; i1 < i; i1++) {
            Model class50_sub1_sub4_sub4_1 = models[i1];
            if (class50_sub1_sub4_sub4_1 != null) {
                int j1 = vertexCount;
                for (int k1 = 0; k1 < class50_sub1_sub4_sub4_1.vertexCount; k1++) {
                    verticesX[vertexCount] = class50_sub1_sub4_sub4_1.verticesX[k1];
                    verticesY[vertexCount] = class50_sub1_sub4_sub4_1.verticesY[k1];
                    verticesZ[vertexCount] = class50_sub1_sub4_sub4_1.verticesZ[k1];
                    vertexCount++;
                }

                for (int l1 = 0; l1 < class50_sub1_sub4_sub4_1.triangleCount; l1++) {
                    trianglePointsX[triangleCount] = class50_sub1_sub4_sub4_1.trianglePointsX[l1] + j1;
                    trianglePointsY[triangleCount] = class50_sub1_sub4_sub4_1.trianglePointsY[l1] + j1;
                    trianglePointsZ[triangleCount] = class50_sub1_sub4_sub4_1.trianglePointsZ[l1] + j1;
                    anIntArray1656[triangleCount] = class50_sub1_sub4_sub4_1.anIntArray1656[l1];
                    anIntArray1657[triangleCount] = class50_sub1_sub4_sub4_1.anIntArray1657[l1];
                    anIntArray1658[triangleCount] = class50_sub1_sub4_sub4_1.anIntArray1658[l1];
                    if (flag1)
                        if (class50_sub1_sub4_sub4_1.texturePoints == null) {
                            texturePoints[triangleCount] = 0;
                        } else {
                            int i2 = class50_sub1_sub4_sub4_1.texturePoints[l1];
                            if ((i2 & 2) == 2)
                                i2 += l << 2;
                            texturePoints[triangleCount] = i2;
                        }
                    if (flag2)
                        if (class50_sub1_sub4_sub4_1.trianglePriorities == null)
                            trianglePriorities[triangleCount] = class50_sub1_sub4_sub4_1.anInt1663;
                        else
                            trianglePriorities[triangleCount] = class50_sub1_sub4_sub4_1.trianglePriorities[l1];
                    if (flag3)
                        if (class50_sub1_sub4_sub4_1.triangleAlphaValues == null)
                            triangleAlphaValues[triangleCount] = 0;
                        else
                            triangleAlphaValues[triangleCount] = class50_sub1_sub4_sub4_1.triangleAlphaValues[l1];
                    if (flag4 && class50_sub1_sub4_sub4_1.triangleColorValues != null)
                        triangleColorValues[triangleCount] = class50_sub1_sub4_sub4_1.triangleColorValues[l1];
                    triangleCount++;
                }

                for (int j2 = 0; j2 < class50_sub1_sub4_sub4_1.texturedTriangleCount; j2++) {
                    texturedTrianglePointsX[texturedTriangleCount] = class50_sub1_sub4_sub4_1.texturedTrianglePointsX[j2] + j1;
                    texturedTrianglePointsY[texturedTriangleCount] = class50_sub1_sub4_sub4_1.texturedTrianglePointsY[j2] + j1;
                    texturedTrianglePointsZ[texturedTriangleCount] = class50_sub1_sub4_sub4_1.texturedTrianglePointsZ[j2] + j1;
                    texturedTriangleCount++;
                }

                l += class50_sub1_sub4_sub4_1.texturedTriangleCount;
            }
        }

        calculateDiagonals();
    }

    public Model(boolean flag2,
                 Model model, boolean flag3) {
        aBoolean1638 = false;
        aBoolean1639 = true;
        anInt1640 = -252;
        anInt1642++;
        vertexCount = model.vertexCount;
        triangleCount = model.triangleCount;
        texturedTriangleCount = model.texturedTriangleCount;
        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            verticesX[i] = model.verticesX[i];
            verticesY[i] = model.verticesY[i];
            verticesZ[i] = model.verticesZ[i];
        }


        if (flag2)

        {
            triangleColorValues = model.triangleColorValues;
        } else

        {
            triangleColorValues = new int[triangleCount];
            for (int j = 0; j < triangleCount; j++)
                triangleColorValues[j] = model.triangleColorValues[j];

        }
        if (flag3)

        {
            triangleAlphaValues = model.triangleAlphaValues;
        } else

        {
            triangleAlphaValues = new int[triangleCount];
            if (model.triangleAlphaValues == null) {
                for (int k = 0; k < triangleCount; k++)
                    triangleAlphaValues[k] = 0;

            } else {
                for (int l = 0; l < triangleCount; l++)
                    triangleAlphaValues[l] = model.triangleAlphaValues[l];

            }
        }

        vertexSkins = model.vertexSkins;
        triangleSkinValues = model.triangleSkinValues;
        texturePoints = model.texturePoints;
        trianglePointsX = model.trianglePointsX;
        trianglePointsY = model.trianglePointsY;
        trianglePointsZ = model.trianglePointsZ;
        trianglePriorities = model.trianglePriorities;
        anInt1663 = model.anInt1663;
        texturedTrianglePointsX = model.texturedTrianglePointsX;
        texturedTrianglePointsY = model.texturedTrianglePointsY;
        texturedTrianglePointsZ = model.texturedTrianglePointsZ;
    }

    public Model(boolean flag, boolean flag1, int i, Model class50_sub1_sub4_sub4) {
        aBoolean1638 = false;
        aBoolean1639 = true;
        anInt1640 = -252;
        anInt1642++;
        vertexCount = class50_sub1_sub4_sub4.vertexCount;
        triangleCount = class50_sub1_sub4_sub4.triangleCount;
        texturedTriangleCount = class50_sub1_sub4_sub4.texturedTriangleCount;
        if (flag) {
            verticesY = new int[vertexCount];
            for (int j = 0; j < vertexCount; j++)
                verticesY[j] = class50_sub1_sub4_sub4.verticesY[j];

        } else {
            verticesY = class50_sub1_sub4_sub4.verticesY;
        }
        if (flag1) {
            anIntArray1656 = new int[triangleCount];
            anIntArray1657 = new int[triangleCount];
            anIntArray1658 = new int[triangleCount];
            for (int k = 0; k < triangleCount; k++) {
                anIntArray1656[k] = class50_sub1_sub4_sub4.anIntArray1656[k];
                anIntArray1657[k] = class50_sub1_sub4_sub4.anIntArray1657[k];
                anIntArray1658[k] = class50_sub1_sub4_sub4.anIntArray1658[k];
            }

            texturePoints = new int[triangleCount];
            if (class50_sub1_sub4_sub4.texturePoints == null) {
                for (int l = 0; l < triangleCount; l++)
                    texturePoints[l] = 0;

            } else {
                for (int i1 = 0; i1 < triangleCount; i1++)
                    texturePoints[i1] = class50_sub1_sub4_sub4.texturePoints[i1];

            }
            super.verticesNormal = new VertexNormal[vertexCount];
            for (int j1 = 0; j1 < vertexCount; j1++) {
                VertexNormal class40 = super.verticesNormal[j1] = new VertexNormal();
                VertexNormal class40_1 = ((Renderable) (class50_sub1_sub4_sub4)).verticesNormal[j1];
                class40.x = class40_1.x;
                class40.y = class40_1.y;
                class40.z = class40_1.z;
                class40.magnitude = class40_1.magnitude;
            }

            aClass40Array1681 = class50_sub1_sub4_sub4.aClass40Array1681;
        } else {
            anIntArray1656 = class50_sub1_sub4_sub4.anIntArray1656;
            anIntArray1657 = class50_sub1_sub4_sub4.anIntArray1657;
            anIntArray1658 = class50_sub1_sub4_sub4.anIntArray1658;
            texturePoints = class50_sub1_sub4_sub4.texturePoints;
        }
        verticesX = class50_sub1_sub4_sub4.verticesX;
        verticesZ = class50_sub1_sub4_sub4.verticesZ;
        if (i != 0)
            aBoolean1638 = !aBoolean1638;
        triangleColorValues = class50_sub1_sub4_sub4.triangleColorValues;
        triangleAlphaValues = class50_sub1_sub4_sub4.triangleAlphaValues;
        trianglePriorities = class50_sub1_sub4_sub4.trianglePriorities;
        anInt1663 = class50_sub1_sub4_sub4.anInt1663;
        trianglePointsX = class50_sub1_sub4_sub4.trianglePointsX;
        trianglePointsY = class50_sub1_sub4_sub4.trianglePointsY;
        trianglePointsZ = class50_sub1_sub4_sub4.trianglePointsZ;
        texturedTrianglePointsX = class50_sub1_sub4_sub4.texturedTrianglePointsX;
        texturedTrianglePointsY = class50_sub1_sub4_sub4.texturedTrianglePointsY;
        texturedTrianglePointsZ = class50_sub1_sub4_sub4.texturedTrianglePointsZ;
        super.modelHeight = ((Renderable) (class50_sub1_sub4_sub4)).modelHeight;
        maxY = class50_sub1_sub4_sub4.maxY;
        shadowIntensity = class50_sub1_sub4_sub4.shadowIntensity;
        anInt1674 = class50_sub1_sub4_sub4.anInt1674;
        anInt1673 = class50_sub1_sub4_sub4.anInt1673;
        anInt1669 = class50_sub1_sub4_sub4.anInt1669;
        anInt1670 = class50_sub1_sub4_sub4.anInt1670;
        anInt1668 = class50_sub1_sub4_sub4.anInt1668;
    }

    public void replaceWithModel(Model model, boolean replaceAlphaValues) {
        vertexCount = model.vertexCount;
        triangleCount = model.triangleCount;
        texturedTriangleCount = model.texturedTriangleCount;
        if (Model.anIntArray1644.length < vertexCount) {
            Model.anIntArray1644 = new int[vertexCount + 100];
            Model.anIntArray1645 = new int[vertexCount + 100];
            Model.anIntArray1646 = new int[vertexCount + 100];
        }
        verticesX = Model.anIntArray1644;
        verticesY = Model.anIntArray1645;
        verticesZ = Model.anIntArray1646;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            verticesX[vertex] = model.verticesX[vertex];
            verticesY[vertex] = model.verticesY[vertex];
            verticesZ[vertex] = model.verticesZ[vertex];
        }

        if (replaceAlphaValues) {
            triangleAlphaValues = model.triangleAlphaValues;
        } else {
            if (Model.anIntArray1647.length < triangleCount)
                Model.anIntArray1647 = new int[triangleCount + 100];
            triangleAlphaValues = Model.anIntArray1647;
            if (model.triangleAlphaValues == null) {
                for (int triangle = 0; triangle < triangleCount; triangle++)
                    triangleAlphaValues[triangle] = 0;

            } else {
                for (int triangle = 0; triangle < triangleCount; triangle++)
                    triangleAlphaValues[triangle] = model.triangleAlphaValues[triangle];

            }
        }
        texturePoints = model.texturePoints;
        triangleColorValues = model.triangleColorValues;
        trianglePriorities = model.trianglePriorities;
        anInt1663 = model.anInt1663;
        triangleSkin = model.triangleSkin;
        vectorSkin = model.vectorSkin;
        trianglePointsX = model.trianglePointsX;
        trianglePointsY = model.trianglePointsY;
        trianglePointsZ = model.trianglePointsZ;
        anIntArray1656 = model.anIntArray1656;
        anIntArray1657 = model.anIntArray1657;
        anIntArray1658 = model.anIntArray1658;
        texturedTrianglePointsX = model.texturedTrianglePointsX;
        texturedTrianglePointsY = model.texturedTrianglePointsY;
        texturedTrianglePointsZ = model.texturedTrianglePointsZ;
    }

    public int getFirstIdenticalVertexIndex(Model model, int vertex) {
        int identicalVertexIndex = -1;
        int vertexX = model.verticesX[vertex];
        int vertexY = model.verticesY[vertex];
        int vertexZ = model.verticesZ[vertex];
        for (int index = 0; index < vertexCount; index++) {
            if (vertexX != verticesX[index] || vertexY != verticesY[index] || vertexZ != verticesZ[index])
                continue;
            identicalVertexIndex = index;
            break;
        }

        if (identicalVertexIndex == -1) {
            verticesX[vertexCount] = vertexX;
            verticesY[vertexCount] = vertexY;
            verticesZ[vertexCount] = vertexZ;
            if (model.vertexSkins != null)
                vertexSkins[vertexCount] = model.vertexSkins[vertex];
            identicalVertexIndex = vertexCount++;
        }
        return identicalVertexIndex;
    }

    public void calculateDiagonals() {
        super.modelHeight = 0;
        shadowIntensity = 0;
        maxY = 0;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int vertexX = verticesX[vertex];
            int vertexY = verticesY[vertex];
            int vertexZ = verticesZ[vertex];
            if (-vertexY > super.modelHeight)
                super.modelHeight = -vertexY;
            if (vertexY > maxY)
                maxY = vertexY;
            int j1 = vertexX * vertexX + vertexZ * vertexZ;
            if (j1 > shadowIntensity)
                shadowIntensity = j1;
        }

        shadowIntensity = (int) (Math.sqrt(shadowIntensity) + 0.98999999999999999D);
        anInt1674 = (int) (Math.sqrt(shadowIntensity * shadowIntensity + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1673 = anInt1674 + (int) (Math.sqrt(shadowIntensity * shadowIntensity + maxY * maxY) + 0.98999999999999999D);
    }

    public void normalise() {
        super.modelHeight = 0;
        maxY = 0;
        for (int j = 0; j < vertexCount; j++) {
            int k = verticesY[j];
            if (-k > super.modelHeight)
                super.modelHeight = -k;
            if (k > maxY)
                maxY = k;
        }

        anInt1674 = (int) (Math.sqrt(shadowIntensity * shadowIntensity + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1673 = anInt1674 + (int) (Math.sqrt(shadowIntensity * shadowIntensity + maxY * maxY) + 0.98999999999999999D);
    }

    public void method583(int i) {
        super.modelHeight = 0;
        shadowIntensity = 0;
        maxY = 0;
        int j = 32767;
        int k = -32767;
        int l = -32767;
        int i1 = 32767;
        for (int j1 = 0; j1 < vertexCount; j1++) {
            int k1 = verticesX[j1];
            int l1 = verticesY[j1];
            int i2 = verticesZ[j1];
            if (k1 < j)
                j = k1;
            if (k1 > k)
                k = k1;
            if (i2 < i1)
                i1 = i2;
            if (i2 > l)
                l = i2;
            if (-l1 > super.modelHeight)
                super.modelHeight = -l1;
            if (l1 > maxY)
                maxY = l1;
            int j2 = k1 * k1 + i2 * i2;
            if (j2 > shadowIntensity)
                shadowIntensity = j2;
        }

        shadowIntensity = (int) Math.sqrt(shadowIntensity);
        anInt1674 = (int) Math.sqrt(shadowIntensity * shadowIntensity + super.modelHeight * super.modelHeight);
        anInt1673 = anInt1674 + (int) Math.sqrt(shadowIntensity * shadowIntensity + maxY * maxY);
        anInt1669 = (j << 16) + (k & 0xffff);
        if (i <= 0)
            anInt1637 = 50;
        anInt1670 = (l << 16) + (i1 & 0xffff);
    }

    public void createBones() {
        if (vertexSkins != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < vertexCount; l++) {
                int j1 = vertexSkins[l];
                ai[j1]++;
                if (j1 > j)
                    j = j1;
            }

            vectorSkin = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vectorSkin[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < vertexCount; j2++) {
                int l2 = vertexSkins[j2];
                vectorSkin[l2][ai[l2]++] = j2;
            }

            vertexSkins = null;
        }
        if (triangleSkinValues != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < triangleCount; i1++) {
                int l1 = triangleSkinValues[i1];
                ai1[l1]++;
                if (l1 > k)
                    k = l1;
            }

            triangleSkin = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                triangleSkin[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < triangleCount; k2++) {
                int i3 = triangleSkinValues[k2];
                triangleSkin[i3][ai1[i3]++] = k2;
            }

            triangleSkinValues = null;
        }
    }

    public void applyTransform(int frameId) {
        if (vectorSkin == null)
            return;
        if (frameId == -1)
            return;
        Animation animation = Animation.getAnimation(frameId);
        if (animation == null)
            return;
        Skins skins = animation.animationSkins;
        vertexXModifier = 0;
        vertexYModifier = 0;
        vertexZModifier = 0;
        for (int stepId = 0; stepId < animation.anInt433; stepId++) {
            int opcode = animation.opcodeTable[stepId];
            transformStep(skins.opcodes[opcode], skins.skinList[opcode], animation.modifier1[stepId],
                    animation.modifier2[stepId], animation.modifier3[stepId]);
        }

    }

    public void mixAnimationFrames(int i, int j, int k, int ai[]) {
        if (k == -1)
            return;
        if (ai == null || i == -1) {
            applyTransform(k);
            return;
        }
        Animation animation = Animation.getAnimation(k);
        if (animation == null)
            return;
        Animation animation_1 = Animation.getAnimation(i);
        if (animation_1 == null) {
            applyTransform(k);
            return;
        }
        Skins skins = animation.animationSkins;
        vertexXModifier = 0;
        if (j != 0)
            aBoolean1641 = !aBoolean1641;
        vertexYModifier = 0;
        vertexZModifier = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < animation.anInt433; j1++) {
            int k1;
            for (k1 = animation.opcodeTable[j1]; k1 > i1; i1 = ai[l++]) ;
            if (k1 != i1 || skins.opcodes[k1] == 0)
                transformStep(skins.opcodes[k1], skins.skinList[k1], animation.modifier1[j1],
                        animation.modifier2[j1], animation.modifier3[j1]);
        }

        vertexXModifier = 0;
        vertexYModifier = 0;
        vertexZModifier = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < animation_1.anInt433; l1++) {
            int i2;
            for (i2 = animation_1.opcodeTable[l1]; i2 > i1; i1 = ai[l++]) ;
            if (i2 == i1 || skins.opcodes[i2] == 0)
                transformStep(skins.opcodes[i2], skins.skinList[i2], animation_1.modifier1[l1],
                        animation_1.modifier2[l1], animation_1.modifier3[l1]);
        }

    }

    public void transformStep(int i, int ai[], int j, int k, int l) {
        int i1 = ai.length;
        if (i == 0) {
            int j1 = 0;
            vertexXModifier = 0;
            vertexYModifier = 0;
            vertexZModifier = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = ai[k2];
                if (l3 < vectorSkin.length) {
                    int ai5[] = vectorSkin[l3];
                    for (int i5 = 0; i5 < ai5.length; i5++) {
                        int j6 = ai5[i5];
                        vertexXModifier += verticesX[j6];
                        vertexYModifier += verticesY[j6];
                        vertexZModifier += verticesZ[j6];
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                vertexXModifier = vertexXModifier / j1 + j;
                vertexYModifier = vertexYModifier / j1 + k;
                vertexZModifier = vertexZModifier / j1 + l;
                return;
            } else {
                vertexXModifier = j;
                vertexYModifier = k;
                vertexZModifier = l;
                return;
            }
        }
        if (i == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = ai[k1];
                if (l2 < vectorSkin.length) {
                    int ai1[] = vectorSkin[l2];
                    for (int i4 = 0; i4 < ai1.length; i4++) {
                        int j5 = ai1[i4];
                        verticesX[j5] += j;
                        verticesY[j5] += k;
                        verticesZ[j5] += l;
                    }

                }
            }

            return;
        }
        if (i == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = ai[l1];
                if (i3 < vectorSkin.length) {
                    int ai2[] = vectorSkin[i3];
                    for (int j4 = 0; j4 < ai2.length; j4++) {
                        int k5 = ai2[j4];
                        verticesX[k5] -= vertexXModifier;
                        verticesY[k5] -= vertexYModifier;
                        verticesZ[k5] -= vertexZModifier;
                        int k6 = (j & 0xff) * 8;
                        int l6 = (k & 0xff) * 8;
                        int i7 = (l & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = SINE[i7];
                            int i8 = COSINE[i7];
                            int l8 = verticesY[k5] * j7 + verticesX[k5] * i8 >> 16;
                            verticesY[k5] = verticesY[k5] * i8 - verticesX[k5] * j7 >> 16;
                            verticesX[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = SINE[k6];
                            int j8 = COSINE[k6];
                            int i9 = verticesY[k5] * j8 - verticesZ[k5] * k7 >> 16;
                            verticesZ[k5] = verticesY[k5] * k7 + verticesZ[k5] * j8 >> 16;
                            verticesY[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = SINE[l6];
                            int k8 = COSINE[l6];
                            int j9 = verticesZ[k5] * l7 + verticesX[k5] * k8 >> 16;
                            verticesZ[k5] = verticesZ[k5] * k8 - verticesX[k5] * l7 >> 16;
                            verticesX[k5] = j9;
                        }
                        verticesX[k5] += vertexXModifier;
                        verticesY[k5] += vertexYModifier;
                        verticesZ[k5] += vertexZModifier;
                    }

                }
            }

            return;
        }
        if (i == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = ai[i2];
                if (j3 < vectorSkin.length) {
                    int ai3[] = vectorSkin[j3];
                    for (int k4 = 0; k4 < ai3.length; k4++) {
                        int l5 = ai3[k4];
                        verticesX[l5] -= vertexXModifier;
                        verticesY[l5] -= vertexYModifier;
                        verticesZ[l5] -= vertexZModifier;
                        verticesX[l5] = (verticesX[l5] * j) / 128;
                        verticesY[l5] = (verticesY[l5] * k) / 128;
                        verticesZ[l5] = (verticesZ[l5] * l) / 128;
                        verticesX[l5] += vertexXModifier;
                        verticesY[l5] += vertexYModifier;
                        verticesZ[l5] += vertexZModifier;
                    }

                }
            }

            return;
        }
        if (i == 5 && triangleSkin != null && triangleAlphaValues != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = ai[j2];
                if (k3 < triangleSkin.length) {
                    int ai4[] = triangleSkin[k3];
                    for (int l4 = 0; l4 < ai4.length; l4++) {
                        int i6 = ai4[l4];
                        triangleAlphaValues[i6] += j * 8;
                        if (triangleAlphaValues[i6] < 0)
                            triangleAlphaValues[i6] = 0;
                        if (triangleAlphaValues[i6] > 255)
                            triangleAlphaValues[i6] = 255;
                    }

                }
            }

        }
    }

    public void rotate90Degrees() {
        for (int i = 0; i < vertexCount; i++) {
            int j = verticesX[i];
            verticesX[i] = verticesZ[i];
            verticesZ[i] = -j;
        }

    }

    public void rotateX(int i) {
        int k = SINE[i];
        int l = COSINE[i];
        for (int i1 = 0; i1 < vertexCount; i1++) {
            int j1 = verticesY[i1] * l - verticesZ[i1] * k >> 16;
            verticesZ[i1] = verticesY[i1] * k + verticesZ[i1] * l >> 16;
            verticesY[i1] = j1;
        }

    }

    public void translate(int i, int j, int k) {
        for (int l = 0; l < vertexCount; l++) {
            verticesX[l] += i;
            verticesY[l] += k;
            verticesZ[l] += j;
        }

    }

    public void replaceColor(int oldColor, int newColor) {
        for (int i = 0; i < triangleCount; i++)
            if (triangleColorValues[i] == oldColor)
                triangleColorValues[i] = newColor;

    }

    public void mirror(int i) {
        if (i != 0) {
            for (int j = 1; j > 0; j++) ;
        }
        for (int k = 0; k < vertexCount; k++)
            verticesZ[k] = -verticesZ[k];

        for (int l = 0; l < triangleCount; l++) {
            int i1 = trianglePointsX[l];
            trianglePointsX[l] = trianglePointsZ[l];
            trianglePointsZ[l] = i1;
        }

    }

    public void scaleT(int i, int j, int k, int l) {
        for (int i1 = 0; i1 < vertexCount; i1++) {
            verticesX[i1] = (verticesX[i1] * l) / 128;
            verticesY[i1] = (verticesY[i1] * i) / 128;
            verticesZ[i1] = (verticesZ[i1] * j) / 128;
        }

        if (k != 9)
            anInt1636 = 322;
    }

    public void applyLighting(int i, int j, int k, int l, int i1, boolean flag) {
        int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
        int k1 = j * j1 >> 8;
        if (anIntArray1656 == null) {
            anIntArray1656 = new int[triangleCount];
            anIntArray1657 = new int[triangleCount];
            anIntArray1658 = new int[triangleCount];
        }
        if (super.verticesNormal == null) {
            super.verticesNormal = new VertexNormal[vertexCount];
            for (int l1 = 0; l1 < vertexCount; l1++)
                super.verticesNormal[l1] = new VertexNormal();

        }
        for (int i2 = 0; i2 < triangleCount; i2++) {
            int j2 = trianglePointsX[i2];
            int l2 = trianglePointsY[i2];
            int i3 = trianglePointsZ[i2];
            int j3 = verticesX[l2] - verticesX[j2];
            int k3 = verticesY[l2] - verticesY[j2];
            int l3 = verticesZ[l2] - verticesZ[j2];
            int i4 = verticesX[i3] - verticesX[j2];
            int j4 = verticesY[i3] - verticesY[j2];
            int k4 = verticesZ[i3] - verticesZ[j2];
            int l4 = k3 * k4 - j4 * l3;
            int i5 = l3 * i4 - k4 * j3;
            int j5;
            for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
                l4 >>= 1;
                i5 >>= 1;
            }

            int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
            if (k5 <= 0)
                k5 = 1;
            l4 = (l4 * 256) / k5;
            i5 = (i5 * 256) / k5;
            j5 = (j5 * 256) / k5;
            if (texturePoints == null || (texturePoints[i2] & 1) == 0) {
                VertexNormal class40_2 = super.verticesNormal[j2];
                class40_2.x += l4;
                class40_2.y += i5;
                class40_2.z += j5;
                class40_2.magnitude++;
                class40_2 = super.verticesNormal[l2];
                class40_2.x += l4;
                class40_2.y += i5;
                class40_2.z += j5;
                class40_2.magnitude++;
                class40_2 = super.verticesNormal[i3];
                class40_2.x += l4;
                class40_2.y += i5;
                class40_2.z += j5;
                class40_2.magnitude++;
            } else {
                int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                anIntArray1656[i2] = method597(triangleColorValues[i2], l5, texturePoints[i2]);
            }
        }

        if (flag) {
            method596(i, k1, k, l, i1);
        } else {
            aClass40Array1681 = new VertexNormal[vertexCount];
            for (int k2 = 0; k2 < vertexCount; k2++) {
                VertexNormal class40 = super.verticesNormal[k2];
                VertexNormal class40_1 = aClass40Array1681[k2] = new VertexNormal();
                class40_1.x = class40.x;
                class40_1.y = class40.y;
                class40_1.z = class40.z;
                class40_1.magnitude = class40.magnitude;
            }

            anInt1668 = (i << 16) + (k1 & 0xffff);
        }
        if (flag) {
            calculateDiagonals();
            return;
        } else {
            method583(426);
            return;
        }
    }

    public void method595(int i, int j, int k, int l) {
        int i1 = anInt1668 >> 16;
        int j1 = (anInt1668 << 16) >> 16;
        if (k != 0) {
            for (int k1 = 1; k1 > 0; k1++) ;
        }
        method596(i1, j1, l, i, j);
    }

    public void method596(int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < triangleCount; j1++) {
            int k1 = trianglePointsX[j1];
            int i2 = trianglePointsY[j1];
            int j2 = trianglePointsZ[j1];
            if (texturePoints == null) {
                int i3 = triangleColorValues[j1];
                VertexNormal class40 = super.verticesNormal[k1];
                int k2 = i + (k * class40.x + l * class40.y + i1 * class40.z)
                        / (j * class40.magnitude);
                anIntArray1656[j1] = method597(i3, k2, 0);
                class40 = super.verticesNormal[i2];
                k2 = i + (k * class40.x + l * class40.y + i1 * class40.z) / (j * class40.magnitude);
                anIntArray1657[j1] = method597(i3, k2, 0);
                class40 = super.verticesNormal[j2];
                k2 = i + (k * class40.x + l * class40.y + i1 * class40.z) / (j * class40.magnitude);
                anIntArray1658[j1] = method597(i3, k2, 0);
            } else if ((texturePoints[j1] & 1) == 0) {
                int j3 = triangleColorValues[j1];
                int k3 = texturePoints[j1];
                VertexNormal class40_1 = super.verticesNormal[k1];
                int l2 = i + (k * class40_1.x + l * class40_1.y + i1 * class40_1.z)
                        / (j * class40_1.magnitude);
                anIntArray1656[j1] = method597(j3, l2, k3);
                class40_1 = super.verticesNormal[i2];
                l2 = i + (k * class40_1.x + l * class40_1.y + i1 * class40_1.z)
                        / (j * class40_1.magnitude);
                anIntArray1657[j1] = method597(j3, l2, k3);
                class40_1 = super.verticesNormal[j2];
                l2 = i + (k * class40_1.x + l * class40_1.y + i1 * class40_1.z)
                        / (j * class40_1.magnitude);
                anIntArray1658[j1] = method597(j3, l2, k3);
            }
        }

        super.verticesNormal = null;
        aClass40Array1681 = null;
        vertexSkins = null;
        triangleSkinValues = null;
        if (texturePoints != null) {
            for (int l1 = 0; l1 < triangleCount; l1++)
                if ((texturePoints[l1] & 2) == 2)
                    return;

        }
        triangleColorValues = null;
    }

    public static int method597(int i, int j, int k) {
        if ((k & 2) == 2) {
            if (j < 0)
                j = 0;
            else if (j > 127)
                j = 127;
            j = 127 - j;
            return j;
        }
        j = j * (i & 0x7f) >> 7;
        if (j < 2)
            j = 2;
        else if (j > 126)
            j = 126;
        return (i & 0xff80) + j;
    }

    public void render(int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1 = Rasterizer3D.centerX;
        int i2 = Rasterizer3D.centerY;
        int j2 = SINE[i];
        int k2 = COSINE[i];
        int l2 = SINE[j];
        int i3 = COSINE[j];
        int j3 = SINE[k];
        int k3 = COSINE[k];
        int l3 = SINE[l];
        int i4 = COSINE[l];
        int j4 = j1 * l3 + k1 * i4 >> 16;
        for (int k4 = 0; k4 < vertexCount; k4++) {
            int l4 = verticesX[k4];
            int i5 = verticesY[k4];
            int j5 = verticesZ[k4];
            if (k != 0) {
                int k5 = i5 * j3 + l4 * k3 >> 16;
                i5 = i5 * k3 - l4 * j3 >> 16;
                l4 = k5;
            }
            if (i != 0) {
                int l5 = i5 * k2 - j5 * j2 >> 16;
                j5 = i5 * j2 + j5 * k2 >> 16;
                i5 = l5;
            }
            if (j != 0) {
                int i6 = j5 * l2 + l4 * i3 >> 16;
                j5 = j5 * i3 - l4 * l2 >> 16;
                l4 = i6;
            }
            l4 += i1;
            i5 += j1;
            j5 += k1;
            int j6 = i5 * i4 - j5 * l3 >> 16;
            j5 = i5 * l3 + j5 * i4 >> 16;
            i5 = j6;
            anIntArray1688[k4] = j5 - j4;
            anIntArray1686[k4] = l1 + (l4 << 9) / j5;
            anIntArray1687[k4] = i2 + (i5 << 9) / j5;
            if (texturedTriangleCount > 0) {
                anIntArray1689[k4] = l4;
                anIntArray1690[k4] = i5;
                anIntArray1691[k4] = j5;
            }
        }

        try {
            method599(false, false, 0);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    @Override
    public void renderAtPoint(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        int j2 = l1 * i1 - j1 * l >> 16;
        int k2 = k1 * j + j2 * k >> 16;
        int l2 = shadowIntensity * k >> 16;
        int i3 = k2 + l2;
        if (i3 <= 50 || k2 >= 3500)
            return;
        int j3 = l1 * l + j1 * i1 >> 16;
        int k3 = j3 - shadowIntensity << 9;
        if (k3 / i3 >= Rasterizer.centerX)
            return;
        int l3 = j3 + shadowIntensity << 9;
        if (l3 / i3 <= -Rasterizer.centerX)
            return;
        int i4 = k1 * k - j2 * j >> 16;
        int j4 = shadowIntensity * j >> 16;
        int k4 = i4 + j4 << 9;
        if (k4 / i3 <= -Rasterizer.centerY)
            return;
        int l4 = j4 + (super.modelHeight * k >> 16);
        int i5 = i4 - l4 << 9;
        if (i5 / i3 >= Rasterizer.centerY)
            return;
        int j5 = l2 + (super.modelHeight * j >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50)
            flag = true;
        boolean flag1 = false;
        if (i2 > 0 && aBoolean1705) {
            int k5 = k2 - l2;
            if (k5 <= 50)
                k5 = 50;
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = anInt1706 - Rasterizer3D.centerX;
            int k6 = anInt1707 - Rasterizer3D.centerY;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
                if (oneSquareModel)
                    anIntArray1709[anInt1708++] = i2;
                else
                    flag1 = true;
        }
        int l5 = Rasterizer3D.centerX;
        int j6 = Rasterizer3D.centerY;
        int l6 = 0;
        int i7 = 0;
        if (i != 0) {
            l6 = SINE[i];
            i7 = COSINE[i];
        }
        for (int j7 = 0; j7 < vertexCount; j7++) {
            int k7 = verticesX[j7];
            int l7 = verticesY[j7];
            int i8 = verticesZ[j7];
            if (i != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += j1;
            l7 += k1;
            i8 += l1;
            int k8 = i8 * l + k7 * i1 >> 16;
            i8 = i8 * i1 - k7 * l >> 16;
            k7 = k8;
            k8 = l7 * k - i8 * j >> 16;
            i8 = l7 * j + i8 * k >> 16;
            l7 = k8;
            anIntArray1688[j7] = i8 - k2;
            if (i8 >= 50) {
                anIntArray1686[j7] = l5 + (k7 << 9) / i8;
                anIntArray1687[j7] = j6 + (l7 << 9) / i8;
            } else {
                anIntArray1686[j7] = -5000;
                flag = true;
            }
            if (flag || texturedTriangleCount > 0) {
                anIntArray1689[j7] = k7;
                anIntArray1690[j7] = l7;
                anIntArray1691[j7] = i8;
            }
        }

        try {
            method599(flag, flag1, i2);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void method599(boolean flag, boolean flag1, int i) {
        for (int j = 0; j < anInt1673; j++)
            anIntArray1692[j] = 0;

        for (int k = 0; k < triangleCount; k++)
            if (texturePoints == null || texturePoints[k] != -1) {
                int l = trianglePointsX[k];
                int k1 = trianglePointsY[k];
                int j2 = trianglePointsZ[k];
                int i3 = anIntArray1686[l];
                int l3 = anIntArray1686[k1];
                int k4 = anIntArray1686[j2];
                if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
                    aBooleanArray1685[k] = true;
                    int j5 = (anIntArray1688[l] + anIntArray1688[k1] + anIntArray1688[j2]) / 3 + anInt1674;
                    anIntArrayArray1693[j5][anIntArray1692[j5]++] = k;
                } else {
                    if (flag1
                            && method602(anInt1706, anInt1707, anIntArray1687[l], anIntArray1687[k1],
                            anIntArray1687[j2], i3, l3, k4)) {
                        anIntArray1709[anInt1708++] = i;
                        flag1 = false;
                    }
                    if ((i3 - l3) * (anIntArray1687[j2] - anIntArray1687[k1])
                            - (anIntArray1687[l] - anIntArray1687[k1]) * (k4 - l3) > 0) {
                        aBooleanArray1685[k] = false;
                        if (i3 < 0 || l3 < 0 || k4 < 0 || i3 > Rasterizer.virtualBottomX
                                || l3 > Rasterizer.virtualBottomX || k4 > Rasterizer.virtualBottomX)
                            aBooleanArray1684[k] = true;
                        else
                            aBooleanArray1684[k] = false;
                        int k5 = (anIntArray1688[l] + anIntArray1688[k1] + anIntArray1688[j2]) / 3 + anInt1674;
                        anIntArrayArray1693[k5][anIntArray1692[k5]++] = k;
                    }
                }
            }

        if (trianglePriorities == null) {
            for (int i1 = anInt1673 - 1; i1 >= 0; i1--) {
                int l1 = anIntArray1692[i1];
                if (l1 > 0) {
                    int ai[] = anIntArrayArray1693[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        method600(ai[j3]);

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1694[j1] = 0;
            anIntArray1698[j1] = 0;
        }

        for (int i2 = anInt1673 - 1; i2 >= 0; i2--) {
            int k2 = anIntArray1692[i2];
            if (k2 > 0) {
                int ai1[] = anIntArrayArray1693[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = trianglePriorities[l4];
                    int j6 = anIntArray1694[l5]++;
                    anIntArrayArray1695[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1698[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1696[j6] = i2;
                    else
                        anIntArray1697[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1694[1] > 0 || anIntArray1694[2] > 0)
            l2 = (anIntArray1698[1] + anIntArray1698[2]) / (anIntArray1694[1] + anIntArray1694[2]);
        int k3 = 0;
        if (anIntArray1694[3] > 0 || anIntArray1694[4] > 0)
            k3 = (anIntArray1698[3] + anIntArray1698[4]) / (anIntArray1694[3] + anIntArray1694[4]);
        int j4 = 0;
        if (anIntArray1694[6] > 0 || anIntArray1694[8] > 0)
            j4 = (anIntArray1698[6] + anIntArray1698[8]) / (anIntArray1694[6] + anIntArray1694[8]);
        int i6 = 0;
        int k6 = anIntArray1694[10];
        int ai2[] = anIntArrayArray1695[10];
        int ai3[] = anIntArray1696;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1694[11];
            ai2 = anIntArrayArray1695[11];
            ai3 = anIntArray1697;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                method600(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1695[11]) {
                    i6 = 0;
                    k6 = anIntArray1694[11];
                    ai2 = anIntArrayArray1695[11];
                    ai3 = anIntArray1697;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                method600(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1695[11]) {
                    i6 = 0;
                    k6 = anIntArray1694[11];
                    ai2 = anIntArrayArray1695[11];
                    ai3 = anIntArray1697;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                method600(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1695[11]) {
                    i6 = 0;
                    k6 = anIntArray1694[11];
                    ai2 = anIntArrayArray1695[11];
                    ai3 = anIntArray1697;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1694[l6];
            int ai4[] = anIntArrayArray1695[l6];
            for (int j7 = 0; j7 < i7; j7++)
                method600(ai4[j7]);

        }

        while (i5 != -1000) {
            method600(ai2[i6++]);
            if (i6 == k6 && ai2 != anIntArrayArray1695[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1695[11];
                k6 = anIntArray1694[11];
                ai3 = anIntArray1697;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -1000;
        }
    }

    public void method600(int i) {
        if (aBooleanArray1685[i]) {
            method601(i);
            return;
        }
        int j = trianglePointsX[i];
        int k = trianglePointsY[i];
        int l = trianglePointsZ[i];
        Rasterizer3D.aBoolean1528 = aBooleanArray1684[i];
        if (triangleAlphaValues == null)
            Rasterizer3D.anInt1531 = 0;
        else
            Rasterizer3D.anInt1531 = triangleAlphaValues[i];
        int i1;
        if (texturePoints == null)
            i1 = 0;
        else
            i1 = texturePoints[i] & 3;
        if (i1 == 0) {
            Rasterizer3D.method503(anIntArray1687[j], anIntArray1687[k], anIntArray1687[l],
                    anIntArray1686[j], anIntArray1686[k], anIntArray1686[l], anIntArray1656[i], anIntArray1657[i],
                    anIntArray1658[i]);
            return;
        }
        if (i1 == 1) {
            Rasterizer3D.method505(anIntArray1687[j], anIntArray1687[k], anIntArray1687[l],
                    anIntArray1686[j], anIntArray1686[k], anIntArray1686[l], anIntArray1712[anIntArray1656[i]]);
            return;
        }
        if (i1 == 2) {
            int j1 = texturePoints[i] >> 2;
            int l1 = texturedTrianglePointsX[j1];
            int j2 = texturedTrianglePointsY[j1];
            int l2 = texturedTrianglePointsZ[j1];
            Rasterizer3D.method507(anIntArray1687[j], anIntArray1687[k], anIntArray1687[l],
                    anIntArray1686[j], anIntArray1686[k], anIntArray1686[l], anIntArray1656[i], anIntArray1657[i],
                    anIntArray1658[i], anIntArray1689[l1], anIntArray1689[j2], anIntArray1689[l2], anIntArray1690[l1],
                    anIntArray1690[j2], anIntArray1690[l2], anIntArray1691[l1], anIntArray1691[j2], anIntArray1691[l2],
                    triangleColorValues[i]);
            return;
        }
        if (i1 == 3) {
            int k1 = texturePoints[i] >> 2;
            int i2 = texturedTrianglePointsX[k1];
            int k2 = texturedTrianglePointsY[k1];
            int i3 = texturedTrianglePointsZ[k1];
            Rasterizer3D.method507(anIntArray1687[j], anIntArray1687[k], anIntArray1687[l],
                    anIntArray1686[j], anIntArray1686[k], anIntArray1686[l], anIntArray1656[i], anIntArray1656[i],
                    anIntArray1656[i], anIntArray1689[i2], anIntArray1689[k2], anIntArray1689[i3], anIntArray1690[i2],
                    anIntArray1690[k2], anIntArray1690[i3], anIntArray1691[i2], anIntArray1691[k2], anIntArray1691[i3],
                    triangleColorValues[i]);
        }
    }

    public void method601(int i) {
        int j = Rasterizer3D.centerX;
        int k = Rasterizer3D.centerY;
        int l = 0;
        int i1 = trianglePointsX[i];
        int j1 = trianglePointsY[i];
        int k1 = trianglePointsZ[i];
        int l1 = anIntArray1691[i1];
        int i2 = anIntArray1691[j1];
        int j2 = anIntArray1691[k1];
        if (l1 >= 50) {
            anIntArray1699[l] = anIntArray1686[i1];
            anIntArray1700[l] = anIntArray1687[i1];
            anIntArray1701[l++] = anIntArray1656[i];
        } else {
            int k2 = anIntArray1689[i1];
            int k3 = anIntArray1690[i1];
            int k4 = anIntArray1656[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * anIntArray1713[j2 - l1];
                anIntArray1699[l] = j + (k2 + ((anIntArray1689[k1] - k2) * k5 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (k3 + ((anIntArray1690[k1] - k3) * k5 >> 16) << 9) / 50;
                anIntArray1701[l++] = k4 + ((anIntArray1658[i] - k4) * k5 >> 16);
            }
            if (i2 >= 50) {
                int l5 = (50 - l1) * anIntArray1713[i2 - l1];
                anIntArray1699[l] = j + (k2 + ((anIntArray1689[j1] - k2) * l5 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (k3 + ((anIntArray1690[j1] - k3) * l5 >> 16) << 9) / 50;
                anIntArray1701[l++] = k4 + ((anIntArray1657[i] - k4) * l5 >> 16);
            }
        }
        if (i2 >= 50) {
            anIntArray1699[l] = anIntArray1686[j1];
            anIntArray1700[l] = anIntArray1687[j1];
            anIntArray1701[l++] = anIntArray1657[i];
        } else {
            int l2 = anIntArray1689[j1];
            int l3 = anIntArray1690[j1];
            int l4 = anIntArray1657[i];
            if (l1 >= 50) {
                int i6 = (50 - i2) * anIntArray1713[l1 - i2];
                anIntArray1699[l] = j + (l2 + ((anIntArray1689[i1] - l2) * i6 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (l3 + ((anIntArray1690[i1] - l3) * i6 >> 16) << 9) / 50;
                anIntArray1701[l++] = l4 + ((anIntArray1656[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - i2) * anIntArray1713[j2 - i2];
                anIntArray1699[l] = j + (l2 + ((anIntArray1689[k1] - l2) * j6 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (l3 + ((anIntArray1690[k1] - l3) * j6 >> 16) << 9) / 50;
                anIntArray1701[l++] = l4 + ((anIntArray1658[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1699[l] = anIntArray1686[k1];
            anIntArray1700[l] = anIntArray1687[k1];
            anIntArray1701[l++] = anIntArray1658[i];
        } else {
            int i3 = anIntArray1689[k1];
            int i4 = anIntArray1690[k1];
            int i5 = anIntArray1658[i];
            if (i2 >= 50) {
                int k6 = (50 - j2) * anIntArray1713[i2 - j2];
                anIntArray1699[l] = j + (i3 + ((anIntArray1689[j1] - i3) * k6 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (i4 + ((anIntArray1690[j1] - i4) * k6 >> 16) << 9) / 50;
                anIntArray1701[l++] = i5 + ((anIntArray1657[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * anIntArray1713[l1 - j2];
                anIntArray1699[l] = j + (i3 + ((anIntArray1689[i1] - i3) * l6 >> 16) << 9) / 50;
                anIntArray1700[l] = k + (i4 + ((anIntArray1690[i1] - i4) * l6 >> 16) << 9) / 50;
                anIntArray1701[l++] = i5 + ((anIntArray1656[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1699[0];
        int j4 = anIntArray1699[1];
        int j5 = anIntArray1699[2];
        int i7 = anIntArray1700[0];
        int j7 = anIntArray1700[1];
        int k7 = anIntArray1700[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.aBoolean1528 = false;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.virtualBottomX || j4 > Rasterizer.virtualBottomX
                        || j5 > Rasterizer.virtualBottomX)
                    Rasterizer3D.aBoolean1528 = true;
                int l7;
                if (texturePoints == null)
                    l7 = 0;
                else
                    l7 = texturePoints[i] & 3;
                if (l7 == 0)
                    Rasterizer3D.method503(i7, j7, k7, j3, j4, j5, anIntArray1701[0], anIntArray1701[1],
                            anIntArray1701[2]);
                else if (l7 == 1)
                    Rasterizer3D.method505(i7, j7, k7, j3, j4, j5, anIntArray1712[anIntArray1656[i]]);
                else if (l7 == 2) {
                    int j8 = texturePoints[i] >> 2;
                    int k9 = texturedTrianglePointsX[j8];
                    int k10 = texturedTrianglePointsY[j8];
                    int k11 = texturedTrianglePointsZ[j8];
                    Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1701[0], anIntArray1701[1],
                            anIntArray1701[2], anIntArray1689[k9], anIntArray1689[k10], anIntArray1689[k11],
                            anIntArray1690[k9], anIntArray1690[k10], anIntArray1690[k11], anIntArray1691[k9],
                            anIntArray1691[k10], anIntArray1691[k11], triangleColorValues[i]);
                } else if (l7 == 3) {
                    int k8 = texturePoints[i] >> 2;
                    int l9 = texturedTrianglePointsX[k8];
                    int l10 = texturedTrianglePointsY[k8];
                    int l11 = texturedTrianglePointsZ[k8];
                    Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1656[i], anIntArray1656[i],
                            anIntArray1656[i], anIntArray1689[l9], anIntArray1689[l10], anIntArray1689[l11],
                            anIntArray1690[l9], anIntArray1690[l10], anIntArray1690[l11], anIntArray1691[l9],
                            anIntArray1691[l10], anIntArray1691[l11], triangleColorValues[i]);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.virtualBottomX || j4 > Rasterizer.virtualBottomX
                        || j5 > Rasterizer.virtualBottomX || anIntArray1699[3] < 0
                        || anIntArray1699[3] > Rasterizer.virtualBottomX)
                    Rasterizer3D.aBoolean1528 = true;
                int i8;
                if (texturePoints == null)
                    i8 = 0;
                else
                    i8 = texturePoints[i] & 3;
                if (i8 == 0) {
                    Rasterizer3D.method503(i7, j7, k7, j3, j4, j5, anIntArray1701[0], anIntArray1701[1],
                            anIntArray1701[2]);
                    Rasterizer3D.method503(i7, k7, anIntArray1700[3], j3, j5, anIntArray1699[3],
                            anIntArray1701[0], anIntArray1701[2], anIntArray1701[3]);
                    return;
                }
                if (i8 == 1) {
                    int l8 = anIntArray1712[anIntArray1656[i]];
                    Rasterizer3D.method505(i7, j7, k7, j3, j4, j5, l8);
                    Rasterizer3D.method505(i7, k7, anIntArray1700[3], j3, j5, anIntArray1699[3], l8);
                    return;
                }
                if (i8 == 2) {
                    int i9 = texturePoints[i] >> 2;
                    int i10 = texturedTrianglePointsX[i9];
                    int i11 = texturedTrianglePointsY[i9];
                    int i12 = texturedTrianglePointsZ[i9];
                    Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1701[0], anIntArray1701[1],
                            anIntArray1701[2], anIntArray1689[i10], anIntArray1689[i11], anIntArray1689[i12],
                            anIntArray1690[i10], anIntArray1690[i11], anIntArray1690[i12], anIntArray1691[i10],
                            anIntArray1691[i11], anIntArray1691[i12], triangleColorValues[i]);
                    Rasterizer3D.method507(i7, k7, anIntArray1700[3], j3, j5, anIntArray1699[3],
                            anIntArray1701[0], anIntArray1701[2], anIntArray1701[3], anIntArray1689[i10],
                            anIntArray1689[i11], anIntArray1689[i12], anIntArray1690[i10], anIntArray1690[i11],
                            anIntArray1690[i12], anIntArray1691[i10], anIntArray1691[i11], anIntArray1691[i12],
                            triangleColorValues[i]);
                    return;
                }
                if (i8 == 3) {
                    int j9 = texturePoints[i] >> 2;
                    int j10 = texturedTrianglePointsX[j9];
                    int j11 = texturedTrianglePointsY[j9];
                    int j12 = texturedTrianglePointsZ[j9];
                    Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1656[i], anIntArray1656[i],
                            anIntArray1656[i], anIntArray1689[j10], anIntArray1689[j11], anIntArray1689[j12],
                            anIntArray1690[j10], anIntArray1690[j11], anIntArray1690[j12], anIntArray1691[j10],
                            anIntArray1691[j11], anIntArray1691[j12], triangleColorValues[i]);
                    Rasterizer3D.method507(i7, k7, anIntArray1700[3], j3, j5, anIntArray1699[3],
                            anIntArray1656[i], anIntArray1656[i], anIntArray1656[i], anIntArray1689[j10],
                            anIntArray1689[j11], anIntArray1689[j12], anIntArray1690[j10], anIntArray1690[j11],
                            anIntArray1690[j12], anIntArray1691[j10], anIntArray1691[j11], anIntArray1691[j12],
                            triangleColorValues[i]);
                }
            }
        }
    }

    public boolean method602(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        if (i < j1 && i < k1 && i < l1)
            return false;
        return i <= j1 || i <= k1 || i <= l1;
    }


    static {
        SINE = Rasterizer3D.SINE;
        COSINE = Rasterizer3D.COSINE;
        anIntArray1712 = Rasterizer3D.getRgbLookupTableId;
        anIntArray1713 = Rasterizer3D.anIntArray1535;
    }
}

//TODO Some more missing class names