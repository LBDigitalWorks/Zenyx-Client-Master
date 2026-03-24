package com.client.entity.model;

import com.client.Client;
import com.client.Renderable;
import com.client.definitions.anim.Animation;
import com.client.definitions.anim.Frames;
import com.client.definitions.anim.Skeleton;
import com.client.definitions.anim.skeleton.AnimationKeyFrame;
import com.client.definitions.anim.skeleton.SkeletalAnimBase;
import com.client.definitions.anim.skeleton.SkeletalBone;
import com.client.definitions.anim.skeleton.SkeletalFrameHandler;
import com.client.draw.Rasterizer3D;
import com.client.draw.rasterizer.Clips;
import com.client.entity.AABB;
import com.client.util.math.Matrix4f;
import net.runelite.api.Perspective;
import net.runelite.api.model.Jarvis;
import net.runelite.api.model.Triangle;
import net.runelite.api.model.Vertex;
import net.runelite.rs.api.RSAnimation;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSSkeleton;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Model extends Renderable implements RSModel {

    static Model Model_sharedSequenceModel = new Model();

    static byte[] Model_sharedSequenceModelFaceAlphas = new byte[1];

    static Model Model_sharedSpotAnimationModel = new Model();

    static byte[] Model_sharedSpotAnimationModelFaceAlphas = new byte[1];


    private static final Matrix4f totalSkeletalTransforms = new Matrix4f();
    private static final Matrix4f skeletalScaleMatrix = new Matrix4f();
    private static final Matrix4f skeletalTransformMatrix = new Matrix4f();


    static boolean[] field3032 = new boolean[70000];

    static boolean[] field3096 = new boolean[70000];
    static boolean[] field3034 = new boolean[6500];
    static float[] modelViewportXs = new float[70000];

    static float[] modelViewportYs = new float[70000];

    static float[] field3037 = new float[70000];

    static int[] field3090 = new int[70000];

    static int[] field3054 = new int[70000];

    static int[] field3040 = new int[70000];

    static int[] field3041 = new int[70000];

    static char[] field3067 = new char[70000];

    static char[][] field3055 = new char[70000][512];

    static int[] field3044 = new int[12];

    static int[][] field3045 = new int[12][2000];

    static int[] field3046 = new int[2000];

    static int[] field3047 = new int[2000];

    static int[] field3064 = new int[12];

    static int[] field3062 = new int[10];

    static int[] field3075 = new int[10];

    static int[] field3065 = new int[10];

    static float[] field3053 = new float[10];

    public static int xAnimOffset;

    public static int yAnimOffset;

    public static int zAnimOffset;

    static boolean field2180 = true;

    public static int[] SINE;

    public static int[] COSINE;

    static int[] hslToRgb;

    static int[] field3098;

    static final float field2198;

    public int verticesCount = 0;

    int[] verticesX;

    int[] verticesY;

    int[] verticesZ;

    public int indicesCount = 0;

    int[] indices1;

    int[] indices2;

    int[] indices3;

    int[] faceColors1;

    int[] faceColors2;

    int[] faceColors3;

    byte[] faceRenderPriorities;

    byte[] faceAlphas;

    byte[] textureFaces;

    short[] faceTextures;

    byte[] faceBias;

    public byte field3085 = 0;

    public int texIndicesCount = 0;

    int[] texIndices1;

    int[] texIndices2;

    int[] texIndices3;

    public int[][] vertexLabels;

    public int[][] faceLabelsAlpha;

    int[][] skeletalBones;

    int[][] skeletalScales;

    public boolean isSingleTile = false;

    int boundsType;

    int bottomY;

    int xzRadius;

    int diameter;

    int radius;

    HashMap aabb = new HashMap();

    public byte overrideHue;

    public byte overrideSaturation;

    public byte overrideLuminance;

    public byte overrideAmount;

    public short field3103;
    class248 field3102;
    ModelColorOverride modelColorOverride;
    static {
        SINE = Rasterizer3D.Rasterizer3D_sine;
        COSINE = Rasterizer3D.Rasterizer3D_cosine;
        hslToRgb = Rasterizer3D.Rasterizer3D_colorPalette;
        field3098 = Rasterizer3D.field2795;
        field2198 = Rasterizer3D.calculateDepth(50);
    }

    Model() {
        this.modelColorOverride = null;
        this.field3102 = class248.field2783;
    }

    public Model(int var1, int var2, int var3, byte var4) {
        this.method1339(var1, var2, var3);
        this.field3085 = var4;
        this.verticesCount = 0;
        this.field3102 = class248.field2783;
        this.indicesCount = 0;
        this.texIndicesCount = 0;
    }

    public Model(Model[] var1) {
        new Model(var1,var1.length);
    }
    public Model(Model[] var1, int var2) {
        this.verticesCount = 0;
        this.indicesCount = 0;
        this.texIndicesCount = 0;
        this.field3085 = -1;

        int var3;
        for(var3 = 0; var3 < var2; ++var3) {
            Model var4 = var1[var3];
            if (var4 != null) {
                this.verticesCount += var4.verticesCount;
                this.indicesCount += var4.indicesCount;
                this.texIndicesCount += var4.texIndicesCount;
                if (this.field3085 == -1) {
                    this.field3085 = var4.field3085;
                }
            }
        }

        this.method1339(this.verticesCount, this.indicesCount, this.texIndicesCount);
        this.verticesCount = 0;
        this.indicesCount = 0;
        this.texIndicesCount = 0;

        for(var3 = 0; var3 < var2; ++var3) {
            this.method1342(var1[var3]);
        }

        vertexNormals();

    }

    void method1339(int var1, int var2, int var3) {
        this.verticesX = new int[var1];
        this.verticesY = new int[var1];
        this.verticesZ = new int[var1];
        this.indices1 = new int[var2];
        this.indices2 = new int[var2];
        this.indices3 = new int[var2];
        this.faceColors1 = new int[var2];
        this.faceColors2 = new int[var2];
        this.faceColors3 = new int[var2];
        if (var3 > 0) {
            this.texIndices1 = new int[var3];
            this.texIndices2 = new int[var3];
            this.texIndices3 = new int[var3];
        }

    }

    void method1310(Model var1) {
        int var2 = this.indices1.length;
        if (this.faceRenderPriorities == null && (var1.faceRenderPriorities != null || this.field3085 != var1.field3085)) {
            this.faceRenderPriorities = new byte[var2];
            Arrays.fill(this.faceRenderPriorities, this.field3085);
        }

        if (this.faceAlphas == null && var1.faceAlphas != null) {
            this.faceAlphas = new byte[var2];
            Arrays.fill(this.faceAlphas, (byte)0);
        }

        if (this.faceTextures == null && var1.faceTextures != null) {
            this.faceTextures = new short[var2];
            Arrays.fill(this.faceTextures, (short)-1);
        }

        if (this.textureFaces == null && var1.textureFaces != null) {
            this.textureFaces = new byte[var2];
            Arrays.fill(this.textureFaces, (byte)-1);
        }
        if (this.faceBias == null && var1.faceBias != null) {
            this.faceBias = new byte[var2];
            Arrays.fill(this.faceBias, (byte)0);
        }
    }

    public void rotate90Degrees() {
        for (int vertex = 0; vertex < verticesCount; vertex++) {
            int x = verticesX[vertex];
            verticesX[vertex] = verticesZ[vertex];
            verticesZ[vertex] = -x;
        }

        this.resetBounds();

    }


    public void method1342(Model var1) {
        if (var1 != null) {
            this.method1310(var1);

            int var2;
            for(var2 = 0; var2 < var1.indicesCount; ++var2) {
                this.indices1[this.indicesCount] = this.verticesCount + var1.indices1[var2];
                this.indices2[this.indicesCount] = this.verticesCount + var1.indices2[var2];
                this.indices3[this.indicesCount] = this.verticesCount + var1.indices3[var2];
                this.faceColors1[this.indicesCount] = var1.faceColors1[var2];
                this.faceColors2[this.indicesCount] = var1.faceColors2[var2];
                this.faceColors3[this.indicesCount] = var1.faceColors3[var2];
                if (this.faceRenderPriorities != null) {
                    this.faceRenderPriorities[this.indicesCount] = var1.faceRenderPriorities != null ? var1.faceRenderPriorities[var2] : var1.field3085;
                }

                if (this.faceAlphas != null && var1.faceAlphas != null) {
                    this.faceAlphas[this.indicesCount] = var1.faceAlphas[var2];
                }

                if (this.faceTextures != null) {
                    this.faceTextures[this.indicesCount] = var1.faceTextures != null ? var1.faceTextures[var2] : -1;
                }

                if (this.textureFaces != null) {
                    if (var1.textureFaces != null && var1.textureFaces[var2] != -1) {
                        this.textureFaces[this.indicesCount] = (byte)(var1.textureFaces[var2] + this.texIndicesCount);
                    } else {
                        this.textureFaces[this.indicesCount] = -1;
                    }
                }
                if (this.faceBias != null && var1.faceBias != null) {
                    this.faceBias[this.indicesCount] = var1.faceBias[var2];
                }

                ++this.indicesCount;
            }

            for(var2 = 0; var2 < var1.texIndicesCount; ++var2) {
                this.texIndices1[this.texIndicesCount] = this.verticesCount + var1.texIndices1[var2];
                this.texIndices2[this.texIndicesCount] = this.verticesCount + var1.texIndices2[var2];
                this.texIndices3[this.texIndicesCount] = this.verticesCount + var1.texIndices3[var2];
                ++this.texIndicesCount;
            }

            for(var2 = 0; var2 < var1.verticesCount; ++var2) {
                this.verticesX[this.verticesCount] = var1.verticesX[var2];
                this.verticesY[this.verticesCount] = var1.verticesY[var2];
                this.verticesZ[this.verticesCount] = var1.verticesZ[var2];
                ++this.verticesCount;
            }

        }
    }

    public Model copy$contourGround(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
        this.calculateBoundsCylinder();
        int var7 = var2 - this.xzRadius;
        int var8 = var2 + this.xzRadius;
        int var9 = var4 - this.xzRadius;
        int var10 = var4 + this.xzRadius;
        if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
            var7 >>= 7;
            var8 = var8 + 127 >> 7;
            var9 >>= 7;
            var10 = var10 + 127 >> 7;
            if (var3 == var1[var7][var9] && var3 == var1[var8][var9] && var3 == var1[var7][var10] && var3 == var1[var8][var10]) {
                return this;
            } else {
                Model var11;
                if (var5) {
                    var11 = new Model();
                    var11.verticesCount = this.verticesCount;
                    var11.indicesCount = this.indicesCount;
                    var11.texIndicesCount = this.texIndicesCount;
                    var11.verticesX = this.verticesX;
                    var11.verticesZ = this.verticesZ;
                    var11.indices1 = this.indices1;
                    var11.indices2 = this.indices2;
                    var11.indices3 = this.indices3;
                    var11.faceColors1 = this.faceColors1;
                    var11.faceColors2 = this.faceColors2;
                    var11.faceColors3 = this.faceColors3;
                    var11.faceRenderPriorities = this.faceRenderPriorities;
                    var11.faceAlphas = this.faceAlphas;
                    var11.textureFaces = this.textureFaces;
                    var11.faceTextures = this.faceTextures;
                    var11.faceBias = this.faceBias;
                    var11.field3085 = this.field3085;
                    var11.texIndices1 = this.texIndices1;
                    var11.texIndices2 = this.texIndices2;
                    var11.texIndices3 = this.texIndices3;
                    var11.vertexLabels = this.vertexLabels;
                    var11.faceLabelsAlpha = this.faceLabelsAlpha;
                    var11.isSingleTile = this.isSingleTile;
                    var11.verticesY = new int[var11.verticesCount];
                } else {
                    var11 = this;
                }

                int var12;
                int var13;
                int var14;
                int var15;
                int var16;
                int var17;
                int var18;
                int var19;
                int var20;
                int var21;
                if (var6 == 0) {
                    for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                        var13 = var2 + this.verticesX[var12];
                        var14 = var4 + this.verticesZ[var12];
                        var15 = var13 & 127;
                        var16 = var14 & 127;
                        var17 = var13 >> 7;
                        var18 = var14 >> 7;
                        var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                        var20 = var1[var17][var18 + 1] * (128 - var15) + var15 * var1[var17 + 1][var18 + 1] >> 7;
                        var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                        var11.verticesY[var12] = var21 + this.verticesY[var12] - var3;
                    }
                } else {
                    for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                        var13 = (-this.verticesY[var12] << 16) / super.model_height;
                        if (var13 < var6) {
                            var14 = var2 + this.verticesX[var12];
                            var15 = var4 + this.verticesZ[var12];
                            var16 = var14 & 127;
                            var17 = var15 & 127;
                            var18 = var14 >> 7;
                            var19 = var15 >> 7;
                            var20 = var1[var18][var19] * (128 - var16) + var1[var18 + 1][var19] * var16 >> 7;
                            var21 = var1[var18][var19 + 1] * (128 - var16) + var16 * var1[var18 + 1][var19 + 1] >> 7;
                            int var22 = var20 * (128 - var17) + var21 * var17 >> 7;
                            var11.verticesY[var12] = (var6 - var13) * (var22 - var3) / var6 + this.verticesY[var12];
                        }
                    }
                }

                var11.resetBounds();
                return var11;
            }
        } else {
            return this;
        }

    }

    public Model contourGround(int[][] tileHeights, int packedX, int height, int packedY, boolean copy, int contouredGround)
    {
        // With contouredGround >= 0 lighted models are countoured, so we need to copy uvs
        Model rsModel = copy$contourGround(tileHeights, packedX, height, packedY, copy, contouredGround);
        if (rsModel != this)
        {
            rsModel.setVertexNormalsX(vertexNormalsX);
            rsModel.setVertexNormalsY(vertexNormalsY);
            rsModel.setVertexNormalsZ(vertexNormalsZ);
            if ((Client.instance.getGpuFlags() & 2) == 2)
            {
                rsModel.setUnskewedModel(this);
            }
        }
        return rsModel;
    }

    public Model toSharedSequenceModel(boolean var1) {
        if (!var1 && Model_sharedSequenceModelFaceAlphas.length < this.indicesCount) {
            Model_sharedSequenceModelFaceAlphas = new byte[this.indicesCount + 100];
        }

        return this.buildSharedModel(var1, Model_sharedSequenceModel, Model_sharedSequenceModelFaceAlphas);
    }

    public Model toSharedSpotAnimationModel(boolean var1) {
        if (!var1 && Model_sharedSpotAnimationModelFaceAlphas.length < this.indicesCount) {
            Model_sharedSpotAnimationModelFaceAlphas = new byte[this.indicesCount + 100];
        }

        return this.buildSharedModel(var1, Model_sharedSpotAnimationModel, Model_sharedSpotAnimationModelFaceAlphas);
    }

    Model buildSharedModel(boolean var1, Model var2, byte[] var3) {
        var2.verticesCount = this.verticesCount;
        var2.indicesCount = this.indicesCount;
        var2.texIndicesCount = this.texIndicesCount;
        if (var2.verticesX == null || var2.verticesX.length < this.verticesCount) {
            var2.verticesX = new int[this.verticesCount + 100];
            var2.verticesY = new int[this.verticesCount + 100];
            var2.verticesZ = new int[this.verticesCount + 100];
        }

        int var4;
        for(var4 = 0; var4 < this.verticesCount; ++var4) {
            var2.verticesX[var4] = this.verticesX[var4];
            var2.verticesY[var4] = this.verticesY[var4];
            var2.verticesZ[var4] = this.verticesZ[var4];
        }

        if (var1) {
            var2.faceAlphas = this.faceAlphas;
        } else {
            var2.faceAlphas = var3;
            if (this.faceAlphas == null) {
                for(var4 = 0; var4 < this.indicesCount; ++var4) {
                    var2.faceAlphas[var4] = 0;
                }
            } else {
                for(var4 = 0; var4 < this.indicesCount; ++var4) {
                    var2.faceAlphas[var4] = this.faceAlphas[var4];
                }
            }
        }

        var2.indices1 = this.indices1;
        var2.indices2 = this.indices2;
        var2.indices3 = this.indices3;
        var2.faceColors1 = this.faceColors1;
        var2.faceColors2 = this.faceColors2;
        var2.faceColors3 = this.faceColors3;
        var2.faceRenderPriorities = this.faceRenderPriorities;
        var2.textureFaces = this.textureFaces;
        var2.faceTextures = this.faceTextures;
        var2.faceBias = this.faceBias;
        var2.field3085 = this.field3085;
        var2.texIndices1 = this.texIndices1;
        var2.texIndices2 = this.texIndices2;
        var2.texIndices3 = this.texIndices3;
        var2.vertexLabels = this.vertexLabels;
        var2.faceLabelsAlpha = this.faceLabelsAlpha;
        var2.skeletalBones = this.skeletalBones;
        var2.skeletalScales = this.skeletalScales;
        var2.isSingleTile = this.isSingleTile;
        var2.resetBounds();
        var2.overrideAmount = 0;

        var2.vertexNormalsX = this.vertexNormalsX;
        var2.vertexNormalsY = this.vertexNormalsY;
        var2.vertexNormalsZ = this.vertexNormalsZ;

        return var2;
    }

    public void calculateBoundingBox(int var1) {
        if (!this.aabb.containsKey(var1)) {
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;
            int var5 = 0;
            int var6 = 0;
            int var7 = 0;
            int var8 = COSINE[var1];
            int var9 = SINE[var1];

            for(int var10 = 0; var10 < this.verticesCount; ++var10) {
                int var11 = Rasterizer3D.method812(this.verticesX[var10], this.verticesZ[var10], var8, var9);
                int var12 = this.verticesY[var10];
                int var13 = Rasterizer3D.method903(this.verticesX[var10], this.verticesZ[var10], var8, var9);
                if (var11 < var2) {
                    var2 = var11;
                }

                if (var11 > var5) {
                    var5 = var11;
                }

                if (var12 < var3) {
                    var3 = var12;
                }

                if (var12 > var6) {
                    var6 = var12;
                }

                if (var13 < var4) {
                    var4 = var13;
                }

                if (var13 > var7) {
                    var7 = var13;
                }
            }

            AABB var14 = new AABB((var5 + var2) / 2, (var6 + var3) / 2, (var7 + var4) / 2, (var5 - var2 + 1) / 2, (var6 - var3 + 1) / 2, (var7 - var4 + 1) / 2);
            boolean var15 = true;
            if (var14.xMidOffset < 32) {
                var14.xMidOffset = 32;
            }

            if (var14.zMidOffset < 32) {
                var14.zMidOffset = 32;
            }

            if (this.isSingleTile) {
                boolean var16 = true;
                var14.xMidOffset += 8;
                var14.zMidOffset += 8;
            }

            this.aabb.put(var1, var14);
        }
    }


    public void calculateBoundsCylinder() {
        if (this.boundsType != 1) {
            this.boundsType = 1;
            super.model_height = 0;
            this.bottomY = 0;
            this.xzRadius = 0;

            for(int var1 = 0; var1 < this.verticesCount; ++var1) {
                int var2 = this.verticesX[var1];
                int var3 = this.verticesY[var1];
                int var4 = this.verticesZ[var1];
                if (-var3 > super.model_height) {
                    super.model_height = -var3;
                }

                if (var3 > this.bottomY) {
                    this.bottomY = var3;
                }

                int var5 = var2 * var2 + var4 * var4;
                if (var5 > this.xzRadius) {
                    this.xzRadius = var5;
                }
            }

            this.xzRadius = (int)(Math.sqrt((double)this.xzRadius) + 0.99D);
            this.radius = (int)(Math.sqrt((double)(this.xzRadius * this.xzRadius + super.model_height * super.model_height)) + 0.99D);
            this.diameter = this.radius + (int)(Math.sqrt((double)(this.xzRadius * this.xzRadius + this.bottomY * this.bottomY)) + 0.99D);
        }
    }

    void method1341() {
        if (this.boundsType != 2) {
            this.boundsType = 2;
            this.xzRadius = 0;

            for(int var1 = 0; var1 < this.verticesCount; ++var1) {
                int var2 = this.verticesX[var1];
                int var3 = this.verticesY[var1];
                int var4 = this.verticesZ[var1];
                int var5 = var2 * var2 + var4 * var4 + var3 * var3;
                if (var5 > this.xzRadius) {
                    this.xzRadius = var5;
                }
            }

            this.xzRadius = (int)(Math.sqrt((double)this.xzRadius) + 0.99D);
            this.radius = this.xzRadius;
            this.diameter = this.xzRadius + this.xzRadius;
        }
    }

    public int getShadowIntensity() {
        this.calculateBoundsCylinder();
        return this.xzRadius;
    }

    public void resetBounds() {
        this.boundsType = 0;
        this.aabb.clear();
    }



    public void animate(Frames var1, int var2, int[] var3, boolean var4) {
        if (var3 == null) {
            this.animate(var1, var2);
        } else {
            if (var1 == null || var1.frames == null || var2 < 0 || var2 >= var1.frames.length || var3.length == 0) {
                return;
            }
            Animation var5 = var1.frames[var2];
            if (var5 == null || var5.skeleton == null) {
                return;
            }
            Skeleton var6 = var5.skeleton;
            byte var7 = 0;
            int var11 = var7 + 1;
            int var8 = var3[var7];
            xAnimOffset = 0;
            yAnimOffset = 0;
            zAnimOffset = 0;

            for (int var9 = 0; var9 < var5.transformCount; ++var9) {
                int var10;
                for (var10 = var5.transformSkeletonLabels[var9]; var10 > var8; ) {
                    if (var11 >= var3.length) {
                        break;
                    }
                    var8 = var3[var11++];
                }
                if (var10 < 0 || var10 >= var6.transformTypes.length || var10 >= var6.labels.length) {
                    continue;
                }

                if (var4) {
                    if (var10 == var8 || var6.transformTypes[var10] == 0) {
                        this.transform(var6.transformTypes[var10], var6.labels[var10], var5.transformXs[var9], var5.transformYs[var9], var5.transformZs[var9]);
                    }
                } else if (var10 != var8 || var6.transformTypes[var10] == 0) {
                    this.transform(var6.transformTypes[var10], var6.labels[var10], var5.transformXs[var9], var5.transformYs[var9], var5.transformZs[var9]);
                }
            }

        }
    }


    public void applySkeletalTransform(SkeletalFrameHandler var1, int var2) {
        Skeleton var3 = var1.base;
        SkeletalAnimBase var4 = var3.getSkeletalBase();
        if (var4 != null) {
            var3.getSkeletalBase().update(var1, var2);
            this.applySkeletalAnimation(var3.getSkeletalBase(), var1.method3190());
        }

        if (var1.hasAlphaTransforms()) {
            this.applySkeletalTransparency(var1, var2);
        }

        this.resetBounds();
    }

    void applySkeletalAnimation(SkeletalAnimBase skeletalAnim, int animationIndex) {
        this.transformSkeletal(skeletalAnim, animationIndex);
    }


    public void applyComplexTransform(Skeleton var1, SkeletalFrameHandler var2, int var3, boolean[] var4, boolean var5, boolean var6) {
        SkeletalAnimBase var7 = var1.getSkeletalBase();
        if (var7 != null) {
            var7.update(var2, var3, var4, var5);
            if (var6) {
                this.applySkeletalAnimation(var7, var2.method3190());
            }
        }

        if (!var5 && var2.hasAlphaTransforms()) {
            this.applySkeletalTransparency(var2, var3);
        }
    }

    public void animate2(Frames var1, int var2, Frames var3, int var4, int[] var5) {
        if (var2 != -1) {
            if (var5 != null && var4 != -1) {
                if (var1 == null || var1.frames == null || var3 == null || var3.frames == null
                        || var2 < 0 || var2 >= var1.frames.length || var4 < 0 || var4 >= var3.frames.length
                        || var5.length == 0) {
                    return;
                }
                Animation var6 = var1.frames[var2];
                Animation var7 = var3.frames[var4];
                if (var6 == null || var7 == null || var6.skeleton == null) {
                    return;
                }
                Skeleton var8 = var6.skeleton;
                xAnimOffset = 0;
                yAnimOffset = 0;
                zAnimOffset = 0;
                byte var9 = 0;
                int var13 = var9 + 1;
                int var10 = var5[var9];

                int var11;
                int var12;
                for (var11 = 0; var11 < var6.transformCount; ++var11) {
                    for (var12 = var6.transformSkeletonLabels[var11]; var12 > var10; ) {
                        if (var13 >= var5.length) {
                            break;
                        }
                        var10 = var5[var13++];
                    }
                    if (var12 < 0 || var12 >= var8.transformTypes.length || var12 >= var8.labels.length) {
                        continue;
                    }

                    if (var12 != var10 || var8.transformTypes[var12] == 0) {
                        this.transform(var8.transformTypes[var12], var8.labels[var12], var6.transformXs[var11], var6.transformYs[var11], var6.transformZs[var11]);
                    }
                }

                xAnimOffset = 0;
                yAnimOffset = 0;
                zAnimOffset = 0;
                var9 = 0;
                var13 = var9 + 1;
                var10 = var5[var9];

                for (var11 = 0; var11 < var7.transformCount; ++var11) {
                    for (var12 = var7.transformSkeletonLabels[var11]; var12 > var10; ) {
                        if (var13 >= var5.length) {
                            break;
                        }
                        var10 = var5[var13++];
                    }
                    if (var12 < 0 || var12 >= var8.transformTypes.length || var12 >= var8.labels.length) {
                        continue;
                    }

                    if (var12 == var10 || var8.transformTypes[var12] == 0) {
                        this.transform(var8.transformTypes[var12], var8.labels[var12], var7.transformXs[var11], var7.transformYs[var11], var7.transformZs[var11]);
                    }
                }

                this.resetBounds();
            } else {
                this.animate(var1, var2);
            }
        }
    }

    public void animate(Frames var1, int var2) {
        if (this.vertexLabels != null) {
            if (var2 != -1) {
                if (var1 == null || var1.frames == null || var2 < 0 || var2 >= var1.frames.length) {
                    return;
                }
                Animation var3 = var1.frames[var2];
                if (var3 == null || var3.skeleton == null) {
                    return;
                }
                Skeleton var4 = var3.skeleton;
                xAnimOffset = 0;
                yAnimOffset = 0;
                zAnimOffset = 0;

                for(int var5 = 0; var5 < var3.transformCount; ++var5) {
                    int var6 = var3.transformSkeletonLabels[var5];
                    if (var6 < 0 || var6 >= var4.transformTypes.length || var6 >= var4.labels.length) {
                        continue;
                    }
                    this.transform(var4.transformTypes[var6], var4.labels[var6], var3.transformXs[var5], var3.transformYs[var5], var3.transformZs[var5]);
                }

                this.resetBounds();
            }
        }
    }

    void transformSkeletal(SkeletalAnimBase skeleton, int frameID) {
        if (this.skeletalBones != null) {
            for(int vertex = 0; vertex < this.verticesCount; ++vertex) {
                int[] originBones = this.skeletalBones[vertex];
                if (originBones != null && originBones.length != 0) {
                    int[] vertexScale = this.skeletalScales[vertex];
                    totalSkeletalTransforms.setZero();
                    for(int var6 = 0; var6 < originBones.length; ++var6) {
                        int bone_index = originBones[var6];
                        SkeletalBone bone = skeleton.getBone(bone_index);
                        if (bone != null) {
                            skeletalScaleMatrix.setScale((float)vertexScale[var6] / 255.0F);
                            skeletalTransformMatrix.set(bone.getSkinning(frameID));
                            skeletalTransformMatrix.multiply(skeletalScaleMatrix);
                            totalSkeletalTransforms.add(skeletalTransformMatrix);
                        }
                    }
                    this.transformVertex(vertex, totalSkeletalTransforms);
                }
            }
        }
    }

    void transformVertex(int vertex, Matrix4f matrix) {
        float x = (float)this.verticesX[vertex];
        float y = (float)(-this.verticesY[vertex]);
        float z = (float)(-this.verticesZ[vertex]);
        float weight = 1.0F;
        this.verticesX[vertex] = (int)(matrix.values[0] * x + matrix.values[4] * y + matrix.values[8] * z + matrix.values[12] * weight);
        this.verticesY[vertex] = -((int)(matrix.values[1] * x + matrix.values[5] * y + matrix.values[9] * z + matrix.values[13] * weight));
        this.verticesZ[vertex] = -((int)(matrix.values[2] * x + matrix.values[6] * y + matrix.values[10] * z + matrix.values[14] * weight));
    }

    void applySkeletalTransparency(SkeletalFrameHandler keyframes, int keyframe) {
        Skeleton base = keyframes.base;

        for(int var4 = 0; var4 < base.count; ++var4) {
            int transform_type = base.transformTypes[var4];
            if (transform_type == 5 && keyframes.transforms != null && keyframes.transforms[var4] != null && keyframes.transforms[var4][0] != null && this.faceLabelsAlpha != null && this.faceAlphas != null) {
                AnimationKeyFrame var6 = keyframes.transforms[var4][0];
                int[] anim_labels = base.labels[var4];
                int anim_labels_count = anim_labels.length;

                for (int anim_label : anim_labels) {
                    if (anim_label < this.faceLabelsAlpha.length) {
                        int[] face_labels = this.faceLabelsAlpha[anim_label];

                        for (int i = 0; i < face_labels.length; ++i) {
                            int face_label = face_labels[i];
                            int alpha = (int) ((float) (this.faceAlphas[face_label] & 255) + var6.getValue(keyframe) * 255.0F);
                            if (alpha < 0) {
                                alpha = 0;
                            } else if (alpha > 255) {
                                alpha = 255;
                            }

                            this.faceAlphas[face_label] = (byte) alpha;
                        }
                    }
                }
            }
        }

    }


    public void transform(int animationType, int[] skinArray, int x, int y, int z) {
        int var6 = skinArray.length;
        int var7;
        int var8;
        int var11;
        int var12;
        if (animationType == 0) {
            var7 = 0;
            xAnimOffset = 0;
            yAnimOffset = 0;
            zAnimOffset = 0;

            for(var8 = 0; var8 < var6; ++var8) {
                int var18 = skinArray[var8];
                if (var18 < this.vertexLabels.length) {
                    int[] var19 = this.vertexLabels[var18];

                    for(var11 = 0; var11 < var19.length; ++var11) {
                        var12 = var19[var11];
                        xAnimOffset += this.verticesX[var12];
                        yAnimOffset += this.verticesY[var12];
                        zAnimOffset += this.verticesZ[var12];
                        ++var7;
                    }
                }
            }

            if (var7 > 0) {
                xAnimOffset = x + xAnimOffset / var7;
                yAnimOffset = y + yAnimOffset / var7;
                zAnimOffset = z + zAnimOffset / var7;
            } else {
                xAnimOffset = x;
                yAnimOffset = y;
                zAnimOffset = z;
            }

        } else {
            int[] var9;
            int var10;
            int[] var10000;
            if (animationType == 1) {
                for(var7 = 0; var7 < var6; ++var7) {
                    var8 = skinArray[var7];
                    if (var8 < this.vertexLabels.length) {
                        var9 = this.vertexLabels[var8];

                        for(var10 = 0; var10 < var9.length; ++var10) {
                            var11 = var9[var10];
                            var10000 = this.verticesX;
                            var10000[var11] += x;
                            var10000 = this.verticesY;
                            var10000[var11] += y;
                            var10000 = this.verticesZ;
                            var10000[var11] += z;
                        }
                    }
                }

            } else if (animationType == 2) {
                for(var7 = 0; var7 < var6; ++var7) {
                    var8 = skinArray[var7];
                    if (var8 < this.vertexLabels.length) {
                        var9 = this.vertexLabels[var8];

                        for(var10 = 0; var10 < var9.length; ++var10) {
                            var11 = var9[var10];
                            var10000 = this.verticesX;
                            var10000[var11] -= xAnimOffset;
                            var10000 = this.verticesY;
                            var10000[var11] -= yAnimOffset;
                            var10000 = this.verticesZ;
                            var10000[var11] -= zAnimOffset;
                            var12 = (x & 255) * 8;
                            int var13 = (y & 255) * 8;
                            int var14 = (z & 255) * 8;
                            int var15;
                            int var16;
                            int var17;
                            if (var14 != 0) {
                                var15 = SINE[var14];
                                var16 = COSINE[var14];
                                var17 = var15 * this.verticesY[var11] + var16 * this.verticesX[var11] >> 16;
                                this.verticesY[var11] = var16 * this.verticesY[var11] - var15 * this.verticesX[var11] >> 16;
                                this.verticesX[var11] = var17;
                            }

                            if (var12 != 0) {
                                var15 = SINE[var12];
                                var16 = COSINE[var12];
                                var17 = var16 * this.verticesY[var11] - var15 * this.verticesZ[var11] >> 16;
                                this.verticesZ[var11] = var15 * this.verticesY[var11] + var16 * this.verticesZ[var11] >> 16;
                                this.verticesY[var11] = var17;
                            }

                            if (var13 != 0) {
                                var15 = SINE[var13];
                                var16 = COSINE[var13];
                                var17 = var15 * this.verticesZ[var11] + var16 * this.verticesX[var11] >> 16;
                                this.verticesZ[var11] = var16 * this.verticesZ[var11] - var15 * this.verticesX[var11] >> 16;
                                this.verticesX[var11] = var17;
                            }

                            var10000 = this.verticesX;
                            var10000[var11] += xAnimOffset;
                            var10000 = this.verticesY;
                            var10000[var11] += yAnimOffset;
                            var10000 = this.verticesZ;
                            var10000[var11] += zAnimOffset;
                        }
                    }
                }

            } else if (animationType == 3) {
                for(var7 = 0; var7 < var6; ++var7) {
                    var8 = skinArray[var7];
                    if (var8 < this.vertexLabels.length) {
                        var9 = this.vertexLabels[var8];

                        for(var10 = 0; var10 < var9.length; ++var10) {
                            var11 = var9[var10];
                            var10000 = this.verticesX;
                            var10000[var11] -= xAnimOffset;
                            var10000 = this.verticesY;
                            var10000[var11] -= yAnimOffset;
                            var10000 = this.verticesZ;
                            var10000[var11] -= zAnimOffset;
                            this.verticesX[var11] = x * this.verticesX[var11] / 128;
                            this.verticesY[var11] = y * this.verticesY[var11] / 128;
                            this.verticesZ[var11] = z * this.verticesZ[var11] / 128;
                            var10000 = this.verticesX;
                            var10000[var11] += xAnimOffset;
                            var10000 = this.verticesY;
                            var10000[var11] += yAnimOffset;
                            var10000 = this.verticesZ;
                            var10000[var11] += zAnimOffset;
                        }
                    }
                }

            } else if (animationType == 5) {
                if (this.faceLabelsAlpha != null && this.faceAlphas != null) {
                    for(var7 = 0; var7 < var6; ++var7) {
                        var8 = skinArray[var7];
                        if (var8 < this.faceLabelsAlpha.length) {
                            var9 = this.faceLabelsAlpha[var8];

                            for(var10 = 0; var10 < var9.length; ++var10) {
                                var11 = var9[var10];
                                var12 = (this.faceAlphas[var11] & 255) + x * 8;
                                if (var12 < 0) {
                                    var12 = 0;
                                } else if (var12 > 255) {
                                    var12 = 255;
                                }

                                this.faceAlphas[var11] = (byte)var12;
                            }
                        }
                    }
                }

            }
        }
    }

    public RSModel rotateY90Ccw()
    {
        this.rs$rotateY90Ccw();

        return this;
    }

    public RSModel rotateY180Ccw()
    {
        this.rs$rotateY180Ccw();

        return this;
    }

    public RSModel rotateY270Ccw()
    {
        this.rs$rotateY270Ccw();

        return this;
    }

    public RSModel scale(int var1, int var2, int var3)
    {
        this.rs$scale(var1, var2, var3);

        return this;
    }

    @Override
    public RSModel translate(int var1, int var2, int var3)
    {
        this.offsetBy(var1, var2, var3);

        return this;
    }

    public void rotateZ(int var1) {
        int var2 = SINE[var1];
        int var3 = COSINE[var1];

        for(int var4 = 0; var4 < this.verticesCount; ++var4) {
            int var5 = var3 * this.verticesY[var4] - var2 * this.verticesZ[var4] >> 16;
            this.verticesZ[var4] = var2 * this.verticesY[var4] + var3 * this.verticesZ[var4] >> 16;
            this.verticesY[var4] = var5;
        }

        this.resetBounds();
    }

    public void offsetBy(int var1, int var2, int var3) {
        for(int var4 = 0; var4 < this.verticesCount; ++var4) {
            this.verticesX[var4] += var1;
            this.verticesY[var4] += var2;
            this.verticesZ[var4] += var3;
        }

        this.resetBounds();
    }


    public void renderModel(final int rotationY, final int rotationZ, final int rotationXW, final int translationX, final int translationY, final int translationZ) {

        if (this.boundsType != 2 && this.boundsType != 1) {
            this.method1341();
        }

        int centerX = Clips.getClipMidX();
        int centerY = Clips.getClipMidY();
        final int sineY = SINE[rotationY];
        final int cosineY = COSINE[rotationY];
        final int sineZ = SINE[rotationZ];
        final int cosineZ = COSINE[rotationZ];
        final int sineXW = SINE[rotationXW];
        final int cosineXW = COSINE[rotationXW];
        final int transformation = translationY * sineXW + translationZ * cosineXW >> 16;
        for (int vertex = 0; vertex < verticesCount; vertex++) {
            int x = this.verticesX[vertex];
            int y = this.verticesY[vertex];
            int z = this.verticesZ[vertex];
            if (rotationZ != 0) {
                final int newX = y * sineZ + x * cosineZ >> 16;
                y = y * cosineZ - x * sineZ >> 16;
                x = newX;
            }
            if (rotationY != 0) {
                final int newX = z * sineY + x * cosineY >> 16;
                z = z * cosineY - x * sineY >> 16;
                x = newX;
            }
            x += translationX;
            y += translationY;
            z += translationZ;
            final int newY = y * cosineXW - z * sineXW >> 16;
            z = y * sineXW + z * cosineXW >> 16;
            y = newY;
            field3090[vertex] = z - transformation;
            modelViewportXs[vertex] = (float)centerX + (x << 9) / z;
            modelViewportYs[vertex] = (float)centerY + (y << 9) / z;
            if (texIndicesCount > 0) {
                field3054[vertex] = x;
                field3040[vertex] = y;
                field3041[vertex] = z;
            }
        }

        try {
            this.draw0(false, false, false,0);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public final void method5586(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        if (this.boundsType != 2 && this.boundsType != 1) {
            this.method1341();
        }

        int var9 = Clips.getClipMidX();
        int var10 = Clips.getClipMidY();
        int var11 = SINE[var1];
        int var12 = COSINE[var1];
        int var13 = SINE[var2];
        int var14 = COSINE[var2];
        int var15 = SINE[var3];
        int var16 = COSINE[var3];
        int var17 = SINE[var4];
        int var18 = COSINE[var4];
        int var19 = var17 * var6 + var18 * var7 >> 16;

        for(int var20 = 0; var20 < this.verticesCount; ++var20) {
            int var21 = this.verticesX[var20];
            int var22 = this.verticesY[var20];
            int var23 = this.verticesZ[var20];
            int var24;
            if (var3 != 0) {
                var24 = var22 * var15 + var21 * var16 >> 16;
                var22 = var22 * var16 - var21 * var15 >> 16;
                var21 = var24;
            }

            if (var1 != 0) {
                var24 = var22 * var12 - var23 * var11 >> 16;
                var23 = var22 * var11 + var23 * var12 >> 16;
                var22 = var24;
            }

            if (var2 != 0) {
                var24 = var23 * var13 + var21 * var14 >> 16;
                var23 = var23 * var14 - var21 * var13 >> 16;
                var21 = var24;
            }

            var21 += var5;
            var22 += var6;
            var23 += var7;
            var24 = var22 * var18 - var23 * var17 >> 16;
            var23 = var22 * var17 + var23 * var18 >> 16;
            field3090[var20] = var23 - var19;
            modelViewportXs[var20] = (float)var9 + var21 * Rasterizer3D.clips.viewportZoom / var8;
            modelViewportYs[var20] = (float)var10 + var24 * Rasterizer3D.clips.viewportZoom / var8;
            field3037[var20] = Rasterizer3D.calculateDepth(var8);
            if (this.texIndicesCount > 0) {
                field3054[var20] = var21;
                field3040[var20] = var24;
                field3041[var20] = var23;
            }
        }

        try {
            this.draw0(false, false, false, 0L);
        } catch (Exception var26) {
            ;
        }

    }

    public final void drawFrustum(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (this.boundsType != 2 && this.boundsType != 1) {
            this.method1341();
        }

        int var8 = Clips.getClipMidX();
        int var9 = Clips.getClipMidY();
        int var10 = SINE[var1];
        int var11 = COSINE[var1];
        int var12 = SINE[var2];
        int var13 = COSINE[var2];
        int var14 = SINE[var3];
        int var15 = COSINE[var3];
        int var16 = SINE[var4];
        int var17 = COSINE[var4];
        int var18 = var16 * var6 + var17 * var7 >> 16;

        for (int var19 = 0; var19 < this.verticesCount; ++var19) {
            int var20 = this.verticesX[var19];
            int var21 = this.verticesY[var19];
            int var22 = this.verticesZ[var19];
            int var23;
            if (var3 != 0) {
                var23 = var21 * var14 + var20 * var15 >> 16;
                var21 = var21 * var15 - var20 * var14 >> 16;
                var20 = var23;
            }

            if (var1 != 0) {
                var23 = var21 * var11 - var22 * var10 >> 16;
                var22 = var21 * var10 + var22 * var11 >> 16;
                var21 = var23;
            }

            if (var2 != 0) {
                var23 = var22 * var12 + var20 * var13 >> 16;
                var22 = var22 * var13 - var20 * var12 >> 16;
                var20 = var23;
            }

            var20 += var5;
            var21 += var6;
            var22 += var7;
            var23 = var21 * var17 - var22 * var16 >> 16;
            var22 = var21 * var16 + var22 * var17 >> 16;
            field3090[var19] = var22 - var18;
            modelViewportXs[var19] = var8 + var20 * Rasterizer3D.clips.viewportZoom / var22;
            modelViewportYs[var19] = var9 + var23 * Rasterizer3D.clips.viewportZoom / var22;
            field3037[var19] = Rasterizer3D.calculateDepth(var22);
            if (this.texIndicesCount > 0) {
                field3054[var19] = var20;
                field3040[var19] = var23;
                field3041[var19] = var22;
            }
        }

        try {
            this.draw0(false, false, false, 0L);
        } catch (Exception var25) {
        }

    }

    @Override
    public void drawOrtho(int zero, int xRotate, int yRotate, int zRotate, int xCamera, int yCamera, int zCamera, int zoom) {

    }

    public boolean renderonGpu = true;

    final void draw0(boolean var1, boolean var2, boolean var3, long var4) {
        final boolean gpu = Client.instance.isGpu() && renderonGpu;
        if (this.diameter < 6000) {
            int var6;
            for (var6 = 0; var6 < this.diameter; ++var6) {
                field3067[var6] = 0;
            }

            var6 = var3 ? 20 : 5;
            int var7 = Rasterizer3D.method5412();

            for (int var8 = 0; var8 < this.indicesCount; ++var8) {
                if (this.faceColors3[var8] == -2) {
                    field3034[var8] = true;
                } else {
                    if (this.field3102 != class248.field2785) {
                        if (Rasterizer3D.clips.field3150 == 1 && (this.faceAlphas == null || this.faceAlphas[var8] == 0)) {
                            field3034[var8] = true;
                            continue;
                        }

                        if (Rasterizer3D.clips.field3150 == 0 && this.faceAlphas != null && this.faceAlphas[var8] != 0) {
                            field3034[var8] = true;
                            continue;
                        }
                    }

                    int var9 = this.indices1[var8];
                    int var10 = this.indices2[var8];
                    int var11 = this.indices3[var8];
                    float var12 = modelViewportXs[var9];
                    float var13 = modelViewportXs[var10];
                    float var14 = modelViewportXs[var11];
                    field3096[var8] = var1 && (var12 == -5000.0F || var13 == -5000.0F || var14 == -5000.0F);
                    int var15;
                    if (field3096[var8]) {
                        var15 = field3054[var9];
                        int var16 = field3054[var10];
                        int var17 = field3054[var11];
                        int var18 = field3040[var9];
                        int var19 = field3040[var10];
                        int var20 = field3040[var11];
                        int var21 = field3041[var9];
                        int var22 = field3041[var10];
                        int var23 = field3041[var11];
                        var15 -= var16;
                        var17 -= var16;
                        var18 -= var19;
                        var20 -= var19;
                        var21 -= var22;
                        var23 -= var22;
                        int var24 = var18 * var23 - var21 * var20;
                        int var25 = var21 * var17 - var15 * var23;
                        int var26 = var15 * var20 - var18 * var17;
                        field3034[var8] = (float)(var16 * var24 + var19 * var25 + var22 * var26) <= 0.0F;
                    } else {
                        field3034[var8] = (var12 - var13) * (modelViewportYs[var11] - modelViewportYs[var10]) - (modelViewportYs[var9] - modelViewportYs[var10]) * (var14 - var13) <= 0.0F;
                        field3032[var8] = var12 < 0.0F || var13 < 0.0F || var14 < 0.0F || var12 > (float)var7 || var13 > (float)var7 || var14 > (float)var7;
                        if (var2 && VarcInt.method4357((int)modelViewportYs[var9], (int)modelViewportYs[var10], (int)modelViewportYs[var11], (int)var12, (int)var13, (int)var14, var6)) {
                            var15 = (int)(field3037[var9] + field3037[var10] + field3037[var11]) / 3;
                            ViewportMouse.setEntity(var4);
                        }
                    }
                }
            }

            // Skip software rasterization when GPU mode is active - only entity detection above is needed
            if (!gpu) {
                short var31 = this.field3103;
                boolean var32 = false;
                if (Rasterizer3D.clips.field3166.method6121()) {
                    this.field3103 = (short)this.indicesCount;
                } else if (this.modelColorOverride != null && this.modelColorOverride.method6121()) {
                    Rasterizer3D.clips.field3166.method6119(this.modelColorOverride);
                    var32 = true;
                }

                try {
                    if (!Rasterizer3D.method5376()) {
                        this.method5936();
                    } else {
                        class248 var28 = this.field3102;
                        if (var28 == class248.field2783) {
                            if (Rasterizer3D.clips.field3150 != 0 && this.faceAlphas != null) {
                                var28 = class248.field2784;
                            } else {
                                var28 = class248.field2786;
                            }
                        }

                        switch(var28.field2787) {
                            case 1:
                                Rasterizer3D.clips.field3142 = -1;
                                Rasterizer3D.clips.field3139 = -1;
                                this.method5936();
                                break;
                            case 2:
                                Rasterizer3D.clips.field3142 = -1;
                                Rasterizer3D.clips.field3139 = 0;
                                this.method5936();
                                Rasterizer3D.clips.field3142 = 0;
                                Rasterizer3D.clips.field3139 = -1;
                                this.method5936();
                                Rasterizer3D.clips.field3142 = -1;
                                Rasterizer3D.clips.field3139 = -1;
                                break;
                            case 3:
                                Rasterizer3D.clips.field3142 = -1;
                                Rasterizer3D.clips.field3139 = -1;
                                this.method5944();
                        }
                    }
                } finally {
                    this.field3103 = var31;
                    if (var32) {
                        Rasterizer3D.clips.field3166.method6118();
                    }

                }
            }

        }
    }
    static final float method6970(float var0) {
        var0 = (var0 - 75.0F) * 1.0100503F;
        return 1.0100503F + 150.75377F / var0;
    }
    void method5944() {
        for (int var1 = 0; var1 < this.indicesCount; ++var1) {
            if (!field3034[var1]) {
                this.drawFace(var1);
            }
        }

    }
    
    void method5936() {
        for (char var1 = 0; var1 < this.indicesCount; ++var1) {
            if (!field3034[var1]) {
                int var2 = (field3090[this.indices1[var1]] + field3090[this.indices2[var1]] + field3090[this.indices3[var1]]) / 3 + this.radius;
                field3055[var2][field3067[var2]++] = var1;
            }
        }

        if (this.faceRenderPriorities == null) {
            for (int var5 = this.diameter - 1; var5 >= 0; --var5) {
                char var6 = field3067[var5];
                if (var6 > 0) {
                    char[] var3 = field3055[var5];

                    for (int var4 = 0; var4 < var6; ++var4) {
                        this.drawFace(var3[var4]);
                    }
                }
            }
        } else {
            this.method5946();
        }

    }
    
    void method5946() {
        int var1;
        for (var1 = 0; var1 < 12; ++var1) {
            field3044[var1] = 0;
            field3064[var1] = 0;
        }

        int var4;
        for (var1 = this.diameter - 1; var1 >= 0; --var1) {
            char var2 = field3067[var1];
            if (var2 > 0) {
                char[] var13 = field3055[var1];

                for (var4 = 0; var4 < var2; ++var4) {
                    char var5 = var13[var4];
                    byte var6 = this.faceRenderPriorities[var5];
                    int var14 = field3044[var6]++;
                    field3045[var6][var14] = var5;
                    if (var6 < 10) {
                        int[] var10000 = field3064;
                        var10000[var6] += var1;
                    } else if (var6 == 10) {
                        field3046[var14] = var1;
                    } else {
                        field3047[var14] = var1;
                    }
                }
            }
        }

        var1 = 0;
        if (field3044[1] > 0 || field3044[2] > 0) {
            var1 = (field3064[1] + field3064[2]) / (field3044[1] + field3044[2]);
        }

        int var15 = 0;
        if (field3044[3] > 0 || field3044[4] > 0) {
            var15 = (field3064[3] + field3064[4]) / (field3044[3] + field3044[4]);
        }

        int var3 = 0;
        if (field3044[6] > 0 || field3044[8] > 0) {
            var3 = (field3064[8] + field3064[6]) / (field3044[8] + field3044[6]);
        }

        int var16 = 0;
        int var17 = field3044[10];
        int[] var7 = field3045[10];
        int[] var8 = field3046;
        if (var16 == var17) {
            var16 = 0;
            var17 = field3044[11];
            var7 = field3045[11];
            var8 = field3047;
        }

        if (var16 < var17) {
            var4 = var8[var16];
        } else {
            var4 = -1000;
        }

        for (int var9 = 0; var9 < 10; ++var9) {
            while (var9 == 0 && var4 > var1) {
                this.drawFace(var7[var16++]);
                if (var16 == var17 && var7 != field3045[11]) {
                    var16 = 0;
                    var17 = field3044[11];
                    var7 = field3045[11];
                    var8 = field3047;
                }

                if (var16 < var17) {
                    var4 = var8[var16];
                } else {
                    var4 = -1000;
                }
            }

            while (var9 == 3 && var4 > var15) {
                this.drawFace(var7[var16++]);
                if (var16 == var17 && var7 != field3045[11]) {
                    var16 = 0;
                    var17 = field3044[11];
                    var7 = field3045[11];
                    var8 = field3047;
                }

                if (var16 < var17) {
                    var4 = var8[var16];
                } else {
                    var4 = -1000;
                }
            }

            while (var9 == 5 && var4 > var3) {
                this.drawFace(var7[var16++]);
                if (var16 == var17 && var7 != field3045[11]) {
                    var16 = 0;
                    var17 = field3044[11];
                    var7 = field3045[11];
                    var8 = field3047;
                }

                if (var16 < var17) {
                    var4 = var8[var16];
                } else {
                    var4 = -1000;
                }
            }

            int var10 = field3044[var9];
            int[] var11 = field3045[var9];

            for (int var12 = 0; var12 < var10; ++var12) {
                this.drawFace(var11[var12]);
            }
        }

        while (var4 != -1000) {
            this.drawFace(var7[var16++]);
            if (var16 == var17 && var7 != field3045[11]) {
                var16 = 0;
                var7 = field3045[11];
                var17 = field3044[11];
                var8 = field3047;
            }

            if (var16 < var17) {
                var4 = var8[var16];
            } else {
                var4 = -1000;
            }
        }

    }
    public final void drawFace(int var1) {
        if (field3096[var1]) {
            this.method5950(var1);
        } else {
            int var2 = this.indices1[var1];
            int var3 = this.indices2[var1];
            int var4 = this.indices3[var1];
            Rasterizer3D.clips.clipping = field3032[var1];
            int var5 = this.faceBias == null ? 0 : this.faceBias[var1] * 2;
            float var6 = method6970(field3090[var2] - (float)var5);
            float var7 = method6970(field3090[var3] - (float)var5);
            float var8 = method6970(field3090[var4] - (float)var5);
            if (this.faceAlphas == null) {
                Rasterizer3D.clips.currentFaceAlpha = 0;
            } else {
                Rasterizer3D.clips.currentFaceAlpha = this.faceAlphas[var1] & 255;
            }

            this.method5949(var1, modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], var6, var7, var8, this.faceColors1[var1], this.faceColors2[var1], this.faceColors3[var1]);
        }
    }

    boolean method6027(int var1) {
        return this.overrideAmount > 0 && var1 < this.field3103;
    }

    final void method5949(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11, int var12, int var13) {
        if (this.faceTextures != null && this.faceTextures[var1] != -1) {
            int var15;
            int var16;
            int var18;
            if (this.textureFaces != null && this.textureFaces[var1] != -1) {
                int var17 = this.textureFaces[var1] & 255;
                var18 = this.texIndices1[var17];
                var15 = this.texIndices2[var17];
                var16 = this.texIndices3[var17];
            } else {
                var18 = this.indices1[var1];
                var15 = this.indices2[var1];
                var16 = this.indices3[var1];
            }

            if (this.faceColors3[var1] == -1) {
                Rasterizer3D.rasterTextureWithShadingAndLighting(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var11, var11, field3054[var18], field3054[var15], field3054[var16], field3040[var18], field3040[var15], field3040[var16], field3041[var18], field3041[var15], field3041[var16], this.faceTextures[var1]);
            } else {
                Rasterizer3D.rasterTextureWithShadingAndLighting(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, field3054[var18], field3054[var15], field3054[var16], field3040[var18], field3040[var15], field3040[var16], field3041[var18], field3041[var15], field3041[var16], this.faceTextures[var1]);
            }
        } else {
            boolean var14 = this.method6027(var1);
            if (this.faceColors3[var1] == -1 && var14) {
                Rasterizer3D.rasterTriangle(var2, var3, var4, var5, var6, var7, var8, var9, var10, hslToRgb[this.faceColors1[var1]]);
            } else if (this.faceColors3[var1] == -1) {
                Rasterizer3D.rasterFlat(var2, var3, var4, var5, var6, var7, var8, var9, var10, hslToRgb[this.faceColors1[var1]]);
            } else if (var14) {
                Rasterizer3D.rasterTriangleWithGradient(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
            } else {
                Rasterizer3D.rasterGouraud(var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
            }
        }

    }

    final void method5950(int var1) {
        int var2 = Clips.getClipMidX();
        int var3 = Clips.getClipMidY();
        int var4 = 0;
        int var5 = this.indices1[var1];
        int var6 = this.indices2[var1];
        int var7 = this.indices3[var1];
        int var8 = field3041[var5];
        int var9 = field3041[var6];
        int var10 = field3041[var7];
        if (this.faceAlphas == null) {
            Rasterizer3D.clips.currentFaceAlpha = 0;
        } else {
            Rasterizer3D.clips.currentFaceAlpha = this.faceAlphas[var1] & 255;
        }

        int var11;
        int var12;
        int var13;
        int var14;
        if (var8 >= 50) {
            field3062[var4] = (int)modelViewportXs[var5];
            field3075[var4] = (int)modelViewportYs[var5];
            field3053[var4] = field3037[var5];
            field3065[var4++] = this.faceColors1[var1];
        } else {
            var11 = field3054[var5];
            var12 = field3040[var5];
            var13 = this.faceColors1[var1];
            if (var10 >= 50) {
                var14 = field3098[var10 - var8] * (50 - var8);
                field3062[var4] = var2 + (var11 + ((field3054[var7] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var7] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors3[var1] - var13) * var14 >> 16);
            }

            if (var9 >= 50) {
                var14 = field3098[var9 - var8] * (50 - var8);
                field3062[var4] = var2 + (var11 + ((field3054[var6] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var6] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors2[var1] - var13) * var14 >> 16);
            }
        }

        if (var9 >= 50) {
            field3062[var4] = (int)modelViewportXs[var6];
            field3075[var4] = (int)modelViewportYs[var6];
            field3053[var4] = field3037[var6];
            field3065[var4++] = this.faceColors2[var1];
        } else {
            var11 = field3054[var6];
            var12 = field3040[var6];
            var13 = this.faceColors2[var1];
            if (var8 >= 50) {
                var14 = field3098[var8 - var9] * (50 - var9);
                field3062[var4] = var2 + (var11 + ((field3054[var5] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var5] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors1[var1] - var13) * var14 >> 16);
            }

            if (var10 >= 50) {
                var14 = field3098[var10 - var9] * (50 - var9);
                field3062[var4] = var2 + (var11 + ((field3054[var7] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var7] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors3[var1] - var13) * var14 >> 16);
            }
        }

        if (var10 >= 50) {
            field3062[var4] = (int)modelViewportXs[var7];
            field3075[var4] = (int)modelViewportYs[var7];
            field3053[var4] = field3037[var7];
            field3065[var4++] = this.faceColors3[var1];
        } else {
            var11 = field3054[var7];
            var12 = field3040[var7];
            var13 = this.faceColors3[var1];
            if (var9 >= 50) {
                var14 = field3098[var9 - var10] * (50 - var10);
                field3062[var4] = var2 + (var11 + ((field3054[var6] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var6] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors2[var1] - var13) * var14 >> 16);
            }

            if (var8 >= 50) {
                var14 = field3098[var8 - var10] * (50 - var10);
                field3062[var4] = var2 + (var11 + ((field3054[var5] - var11) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3075[var4] = var3 + (var12 + ((field3040[var5] - var12) * var14 >> 16)) * Clips.get3dZoom() / 50;
                field3053[var4] = 50.0F;
                field3065[var4++] = var13 + ((this.faceColors1[var1] - var13) * var14 >> 16);
            }
        }

        var11 = field3062[0];
        var12 = field3062[1];
        var13 = field3062[2];
        var14 = field3075[0];
        int var15 = field3075[1];
        int var16 = field3075[2];
        int var17 = this.faceBias == null ? 0 : this.faceBias[var1] * 2;
        float var18 = method6970(field3053[0] - (float)var17);
        float var19 = method6970(field3053[1] - (float)var17);
        float var20 = method6970(field3053[2] - (float)var17);
        Rasterizer3D.clips.clipping = false;
        int var21 = Rasterizer3D.method5412();
        if (var4 == 3) {
            if (var11 < 0 || var12 < 0 || var13 < 0 || var11 > var21 || var12 > var21 || var13 > var21) {
                Rasterizer3D.clips.clipping = true;
            }

            this.method5949(var1, (float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, field3065[0], field3065[1], field3065[2]);
        }

        if (var4 == 4) {
            if (var11 < 0 || var12 < 0 || var13 < 0 || var11 > var21 || var12 > var21 || var13 > var21 || field3062[3] < 0 || field3062[3] > var21) {
                Rasterizer3D.clips.clipping = true;
            }

            int var23;
            if (this.faceTextures != null && this.faceTextures[var1] != -1) {
                int var24;
                int var26;
                if (this.textureFaces != null && this.textureFaces[var1] != -1) {
                    int var25 = this.textureFaces[var1] & 255;
                    var26 = this.texIndices1[var25];
                    var23 = this.texIndices2[var25];
                    var24 = this.texIndices3[var25];
                } else {
                    var26 = var5;
                    var23 = var6;
                    var24 = var7;
                }

                short var27 = this.faceTextures[var1];
                if (this.faceColors3[var1] == -1) {
                    Rasterizer3D.rasterTextureWithShadingAndLighting((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field3054[var26], field3054[var23], field3054[var24], field3040[var26], field3040[var23], field3040[var24], field3041[var26], field3041[var23], field3041[var24], var27);
                    Rasterizer3D.rasterTextureWithShadingAndLighting((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field3054[var26], field3054[var23], field3054[var24], field3040[var26], field3040[var23], field3040[var24], field3041[var26], field3041[var23], field3041[var24], var27);
                } else {
                    Rasterizer3D.rasterTextureWithShadingAndLighting((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, field3065[0], field3065[1], field3065[2], field3054[var26], field3054[var23], field3054[var24], field3040[var26], field3040[var23], field3040[var24], field3041[var26], field3041[var23], field3041[var24], var27);
                    Rasterizer3D.rasterTextureWithShadingAndLighting((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], field3065[0], field3065[2], field3065[3], field3054[var26], field3054[var23], field3054[var24], field3040[var26], field3040[var23], field3040[var24], field3041[var26], field3041[var23], field3041[var24], var27);
                }
            } else {
                boolean var22 = this.method6027(var1);
                if (this.faceColors3[var1] == -1 && var22) {
                    var23 = hslToRgb[this.faceColors1[var1]];
                    Rasterizer3D.rasterTriangle((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, var23);
                    Rasterizer3D.rasterTriangle((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], var23);
                } else if (this.faceColors3[var1] == -1) {
                    var23 = hslToRgb[this.faceColors1[var1]];
                    Rasterizer3D.rasterFlat((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, var23);
                    Rasterizer3D.rasterFlat((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], var23);
                } else if (var22) {
                    Rasterizer3D.rasterTriangleWithGradient((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, field3065[0], field3065[1], field3065[2]);
                    Rasterizer3D.rasterTriangleWithGradient((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], field3065[0], field3065[2], field3065[3]);
                } else {
                    Rasterizer3D.rasterGouraud((float)var14, (float)var15, (float)var16, (float)var11, (float)var12, (float)var13, var18, var19, var20, field3065[0], field3065[1], field3065[2]);
                    Rasterizer3D.rasterGouraud((float)var14, (float)var16, (float)field3075[3], (float)var11, (float)var13, (float)field3062[3], var18, var20, field3053[3], field3065[0], field3065[2], field3065[3]);
                }
            }
        }

    }
    public void renderAtPoint(int orientation, int pitchSine, int pitchCos, int yawSin, int yawCos, int offsetX, int offsetY, int offsetZ, long uid) {
        if (this.boundsType != 1) {
            this.calculateBoundsCylinder();
        }
        final boolean gpu = Client.instance.isGpu();
        this.calculateBoundingBox(orientation);
        int var11 = yawCos * offsetZ - yawSin * offsetX >> 16;
        int var12 = pitchSine * offsetY + pitchCos * var11 >> 16;
        int var13 = pitchCos * this.xzRadius >> 16;
        int var14 = var12 + var13;
        boolean withinView = gpu ? true : var12 < 4500;
        if (var14 > 50 &&  withinView) {
            int var15 = offsetZ * yawSin + yawCos * offsetX >> 16;
            int var16 = (var15 - this.xzRadius) * Clips.get3dZoom();
            if (var16 / var14 < Clips.getClipMidX2()) {
                int var17 = (var15 + this.xzRadius) * Clips.get3dZoom();
                int var18 = var17 / var14;
                int var19 = Rasterizer3D.clips.clipNegativeMidX;
                if (var18 > var19) {
                    int var20 = pitchCos * offsetY - var11 * pitchSine >> 16;
                    int var21 = pitchSine * this.xzRadius >> 16;
                    int var22 = (pitchCos * this.bottomY >> 16) + var21;
                    int var23 = (var20 + var22) * Clips.get3dZoom();
                    int var24 = var23 / var14;
                    int var25 = Rasterizer3D.clips.clipNegativeMidY;
                    if (var24 > var25) {
                        int var26 = (pitchCos * super.model_height >> 16) + var21;
                        int var27 = (var20 - var26) * Clips.get3dZoom();
                        if (var27 / var14 < Clips.getClipMidY2()) {
                            int var28 = var13 + (pitchSine * super.model_height >> 16);
                            boolean var29 = false;
                            boolean var30 = false;
                            if (var12 - var28 <= 50) {
                                var30 = true;
                            }

                            boolean var31 = var30 || this.texIndicesCount > 0;
                            int var32 = ViewportMouse.ViewportMouse_x;
                            int var33 = ViewportMouse.ViewportMouse_y;
                            boolean var35 = ViewportMouse.isInViewport;
                            boolean var37 = method957(uid);
                            boolean var38 = false;
                            int var40;
                            int var41;
                            int var42;
                            if (var37 && var35) {
                                boolean var39 = false;
                                if (field2180) {
                                    var39 = method2272(this, orientation, offsetX, offsetY, offsetZ);
                                } else {
                                    var40 = var12 - var13;
                                    if (var40 <= 50) {
                                        var40 = 50;
                                    }

                                    if (var15 > 0) {
                                        var16 /= var14;
                                        var17 /= var40;
                                    } else {
                                        var17 /= var14;
                                        var16 /= var40;
                                    }

                                    if (var20 > 0) {
                                        var27 /= var14;
                                        var23 /= var40;
                                    } else {
                                        var23 /= var14;
                                        var27 /= var40;
                                    }

                                    var41 = var32 - Clips.getClipMidX();
                                    var42 = var33 - Clips.getClipMidY();
                                    if (var41 > var16 && var41 < var17 && var42 > var27 && var42 < var23) {
                                        var39 = true;
                                    }
                                }

                                if (var39) {
                                    if (this.isSingleTile) {
                                        ViewportMouse.setEntity(uid);
                                    } else {
                                        var38 = true;
                                    }
                                }
                            }

                            int var50 = Clips.getClipMidX();
                            var40 = Clips.getClipMidY();
                            var41 = 0;
                            var42 = 0;
                            if (orientation != 0) {
                                var41 = SINE[orientation];
                                var42 = COSINE[orientation];
                            }

                            for(int var43 = 0; var43 < this.verticesCount; ++var43) {
                                int var44 = this.verticesX[var43];
                                int var45 = this.verticesY[var43];
                                int var46 = this.verticesZ[var43];
                                int var47;
                                if (orientation != 0) {
                                    var47 = var46 * var41 + var44 * var42 >> 16;
                                    var46 = var46 * var42 - var44 * var41 >> 16;
                                    var44 = var47;
                                }

                                var44 += offsetX;
                                var45 += offsetY;
                                var46 += offsetZ;
                                var47 = var46 * yawSin + yawCos * var44 >> 16;
                                var46 = yawCos * var46 - var44 * yawSin >> 16;
                                var44 = var47;
                                var47 = pitchCos * var45 - var46 * pitchSine >> 16;
                                var46 = var45 * pitchSine + pitchCos * var46 >> 16;
                                field3090[var43] = var46 - var12;
                                if (var46 >= 50) {
                                    modelViewportXs[var43] = var50 + var44 * Clips.get3dZoom() / var46;
                                    modelViewportYs[var43] = var40 + var47 * Clips.get3dZoom() / var46;
                                    field3037[var43] = Rasterizer3D.calculateDepth(var46);
                                } else {
                                    modelViewportXs[var43] = -5000;
                                    var29 = true;
                                }

                                if (var31) {
                                    field3054[var43] = var44;
                                    field3040[var43] = var47;
                                    field3041[var43] = var46;
                                }
                            }

                            try {
                                if (!gpu || (var38 && !(Math.sqrt(offsetX * offsetX + offsetZ * offsetZ) > 35 * Perspective.LOCAL_TILE_SIZE))) {
                                    this.draw0(var29, var38, isSingleTile, uid);
                                }
                                if (gpu) {
                                    Client.instance.getDrawCallbacks().draw(this, orientation, pitchSine, pitchCos, yawSin, yawCos, offsetX, offsetY, offsetZ, uid);
                                }
                            } catch (Exception var49) {
                                ;
                            }

                        }
                    }
                }
            }
        }
    }

    static final boolean method2272(Model var0, int var1, int var2, int var3, int var4) {
        boolean var5 = ViewportMouse.isInViewport;
        if (!var5) {
            return false;
        } else {
            ViewportMouse.method737();
            AABB var6 = (AABB)var0.aabb.get(var1);
            int var7 = var2 + var6.xMid;
            int var8 = var3 + var6.yMid;
            int var9 = var4 + var6.zMid;
            int var10 = var6.xMidOffset;
            int var11 = var6.yMidOffset;
            int var12 = var6.zMidOffset;
            int var13 = ViewportMouse.field2207 - var7;
            int var14 = ViewportMouse.field2715 - var8;
            int var15 = ViewportMouse.field2208 - var9;
            if (Math.abs(var13) > var10 + ViewportMouse.field1536) {
                return false;
            } else if (Math.abs(var14) > var11 + ViewportMouse.field2211) {
                return false;
            } else if (Math.abs(var15) > var12 + ViewportMouse.field29) {
                return false;
            } else if (Math.abs(var15 * ViewportMouse.field1157 - var14 * ViewportMouse.field166) > var11 * ViewportMouse.field29 + var12 * ViewportMouse.field2211) {
                return false;
            } else if (Math.abs(var13 * ViewportMouse.field166 - var15 * ViewportMouse.field2209) > var12 * ViewportMouse.field1536 + var10 * ViewportMouse.field29) {
                return false;
            } else {
                return Math.abs(var14 * ViewportMouse.field2209 - var13 * ViewportMouse.field1157) <= var11 * ViewportMouse.field1536 + var10 * ViewportMouse.field2211;
            }
        }
    }

    public static boolean method957(long var0) {
        boolean var2 = 0L != var0;
        if (var2) {
            boolean var3 = (int)(var0 >>> 16 & 1L) == 1;
            var2 = !var3;
        }

        return var2;
    }

    /*
     * Runelite
     */

    public int[] vertexNormalsX;
    public int[] vertexNormalsY;
    public int[] vertexNormalsZ;
    private int bufferOffset;
    private int uvBufferOffset;

    private int lastOrientation;

    public void vertexNormals()
    {
        if (vertexNormalsX == null)
        {
            int verticesCount = getVerticesCount();

            vertexNormalsX = new int[verticesCount];
            vertexNormalsY = new int[verticesCount];
            vertexNormalsZ = new int[verticesCount];

            int[] trianglesX = getFaceIndices1();
            int[] trianglesY = getFaceIndices2();
            int[] trianglesZ = getFaceIndices3();
            int[] verticesX = getVerticesX();
            int[] verticesY = getVerticesY();
            int[] verticesZ = getVerticesZ();

            for (int i = 0; i < getFaceCount(); ++i)
            {
                int var9 = trianglesX[i];
                int var10 = trianglesY[i];
                int var11 = trianglesZ[i];

                int var12 = verticesX[var10] - verticesX[var9];
                int var13 = verticesY[var10] - verticesY[var9];
                int var14 = verticesZ[var10] - verticesZ[var9];
                int var15 = verticesX[var11] - verticesX[var9];
                int var16 = verticesY[var11] - verticesY[var9];
                int var17 = verticesZ[var11] - verticesZ[var9];

                int var18 = var13 * var17 - var16 * var14;
                int var19 = var14 * var15 - var17 * var12;

                int var20;
                for (var20 = var12 * var16 - var15 * var13; var18 > 8192 || var19 > 8192 || var20 > 8192 || var18 < -8192 || var19 < -8192 || var20 < -8192; var20 >>= 1)
                {
                    var18 >>= 1;
                    var19 >>= 1;
                }

                int var21 = (int) Math.sqrt(var18 * var18 + var19 * var19 + var20 * var20);
                if (var21 <= 0)
                {
                    var21 = 1;
                }

                var18 = var18 * 256 / var21;
                var19 = var19 * 256 / var21;
                var20 = var20 * 256 / var21;

                vertexNormalsX[var9] += var18;
                vertexNormalsY[var9] += var19;
                vertexNormalsZ[var9] += var20;

                vertexNormalsX[var10] += var18;
                vertexNormalsY[var10] += var19;
                vertexNormalsZ[var10] += var20;

                vertexNormalsX[var11] += var18;
                vertexNormalsY[var11] += var19;
                vertexNormalsZ[var11] += var20;
            }
        }
    }

    @Override
    public List<Vertex> getVertices() {
        int[] verticesX = getVerticesX();
        int[] verticesY = getVerticesY();
        int[] verticesZ = getVerticesZ();
        ArrayList<Vertex> vertices = new ArrayList<>(getVerticesCount());
        for (int i = 0; i < getVerticesCount(); i++) {
            Vertex vertex = new Vertex(verticesX[i], verticesY[i], verticesZ[i]);
            vertices.add(vertex);
        }
        return vertices;
    }

    @Override
    public List<Triangle> getTriangles() {
        int[] trianglesX = getFaceIndices1();
        int[] trianglesY = getFaceIndices2();
        int[] trianglesZ = getFaceIndices3();

        List<Vertex> vertices = getVertices();
        List<Triangle> triangles = new ArrayList<>(getFaceCount());

        for (int i = 0; i < getFaceCount(); ++i)
        {
            int triangleX = trianglesX[i];
            int triangleY = trianglesY[i];
            int triangleZ = trianglesZ[i];

            Triangle triangle = new Triangle(vertices.get(triangleX),vertices.get(triangleY),vertices.get(triangleZ));
            triangles.add(triangle);
        }

        return triangles;
    }

    @Override
    public int getVerticesCount() {
        return verticesCount;
    }

    @Override
    public int[] getVerticesX() {
        return verticesX;
    }

    @Override
    public int[] getVerticesY() {
        return verticesY;
    }

    @Override
    public int[] getVerticesZ() {
        return verticesZ;
    }

    @Override
    public int getFaceCount() {
        return this.indicesCount;
    }

    @Override
    public int[] getFaceIndices1() {
        return indices1;
    }

    @Override
    public int[] getFaceIndices2() {
        return indices2;
    }

    @Override
    public int[] getFaceIndices3() {
        return indices3;
    }

    @Override
    public int[] getFaceColors1() {
        return this.faceColors1;
    }

    @Override
    public int[] getFaceColors2() {
        return faceColors2;
    }

    @Override
    public int[] getFaceColors3() {
        return faceColors3;
    }

    @Override
    public byte[] getFaceTransparencies() {
        return faceAlphas;
    }

    private int sceneId;

    @Override
    public int getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public int getBufferOffset() {
        return bufferOffset;
    }

    @Override
    public void setBufferOffset(int bufferOffset) {
        this.bufferOffset = bufferOffset;
    }

    @Override
    public int getUvBufferOffset() {
        return uvBufferOffset;
    }

    @Override
    public void setUvBufferOffset(int uvBufferOffset) {
        this.uvBufferOffset = uvBufferOffset;
    }

    @Override
    public void animate(int type, int[] list, int x, int y, int z) {
        transform(type,list,x,y,z);
    }

    @Override
    public byte[] getFaceRenderPriorities() {
        return this.faceRenderPriorities;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getDiameter() {
        return diameter;
    }

    @Override
    public short[] getFaceTextures() {
        return faceTextures;
    }

    @Override
    public void calculateExtreme(int orientation) {
        calculateBoundingBox(orientation);
    }
    @Override
    public RSModel toSharedModel(boolean b) {
        return toSharedSequenceModel(b);
    }

    @Override
    public RSModel toSharedSpotAnimModel(boolean b) {
        return toSharedSpotAnimationModel(b);
    }

    @Override
    public void rs$rotateY90Ccw() {
        for(int var1 = 0; var1 < this.verticesCount; ++var1) {
            int var2 = this.verticesX[var1];
            this.verticesX[var1] = this.verticesZ[var1];
            this.verticesZ[var1] = -var2;
        }

        this.resetBounds();
    }

    @Override
    public void rs$rotateY180Ccw() {
        for (int vert = 0; vert < this.verticesCount; ++vert)
        {
            this.verticesX[vert] = -this.verticesX[vert];
            this.verticesZ[vert] = -this.verticesZ[vert];
        }

        this.resetBounds();
    }

    @Override
    public void rs$rotateY270Ccw() {
        for(int var1 = 0; var1 < this.verticesCount; ++var1) {
            int var2 = this.verticesZ[var1];
            this.verticesZ[var1] = this.verticesX[var1];
            this.verticesX[var1] = -var2;
        }

        this.resetBounds();
    }

    @Override
    public void rs$scale(int var1, int var2, int var3) {
        for(int var4 = 0; var4 < this.verticesCount; ++var4) {
            this.verticesX[var4] = this.verticesX[var4] * var1 / 128;
            this.verticesY[var4] = var2 * this.verticesY[var4] / 128;
            this.verticesZ[var4] = var3 * this.verticesZ[var4] / 128;
        }

        this.resetBounds();
    }

    @Override
    public void rs$translate(int var1, int var2, int var3) {

    }

    @Override
    public int getXYZMag() {
        return xzRadius;
    }

    @Override
    public boolean isClickable() {
        return isSingleTile;
    }

    public int[][] getVertexGroups() {
        return vertexLabels;
    }

    @Override
    public void interpolateFrames(RSFrames frames, int frameId, RSFrames nextFrames, int nextFrameId, int interval, int intervalCount) {
        if (getVertexGroups() != null)
        {
            if (frameId != -1)
            {
                RSAnimation frame = frames.getFrames()[frameId];
                RSSkeleton skeleton = frame.getSkeleton();
                RSAnimation nextFrame = null;
                if (nextFrames != null)
                {
                    nextFrame = nextFrames.getFrames()[nextFrameId];
                    if (nextFrame.getSkeleton() != skeleton)
                    {
                        nextFrame = null;
                    }
                }

                Client.instance.setAnimOffsetX(0);
                Client.instance.setAnimOffsetY(0);
                Client.instance.setAnimOffsetZ(0);

                interpolateFrames(skeleton, frame, nextFrame, interval, intervalCount);
                resetBounds();
            }
        }
    }

    public void interpolateFrames(RSSkeleton skin, RSAnimation frame, RSAnimation nextFrame, int interval, int intervalCount)
    {
        if (nextFrame == null || interval == 0)
        {
            // if there is no next frame or interval then animate the model as we normally would
            for (int i = 0; i < frame.getTransformCount(); i++)
            {
                int type = frame.getTransformTypes()[i];
                animate(skin.getTypes()[type], skin.getList()[type], frame.getTranslatorX()[i],
                        frame.getTranslatorY()[i], frame.getTranslatorZ()[i]);
            }
        }
        else
        {
            int transformIndex = 0;
            int nextTransformIndex = 0;
            for (int i = 0; i < skin.getCount(); i++)
            {
                boolean frameValid = false;
                if (transformIndex < frame.getTransformCount()
                        && frame.getTransformTypes()[transformIndex] == i)
                {
                    frameValid = true;
                }
                boolean nextFrameValid = false;
                if (nextTransformIndex < nextFrame.getTransformCount()
                        && nextFrame.getTransformTypes()[nextTransformIndex] == i)
                {
                    nextFrameValid = true;
                }
                if (frameValid || nextFrameValid)
                {
                    int staticFrame = 0;
                    int type = skin.getTypes()[i];
                    if (type == 3 || type == 10)
                    {
                        staticFrame = 128;
                    }
                    int currentTranslateX = staticFrame;
                    int currentTranslateY = staticFrame;
                    int currentTranslateZ = staticFrame;
                    if (frameValid)
                    {
                        currentTranslateX = frame.getTranslatorX()[transformIndex];
                        currentTranslateY = frame.getTranslatorY()[transformIndex];
                        currentTranslateZ = frame.getTranslatorZ()[transformIndex];
                        transformIndex++;
                    }
                    int nextTranslateX = staticFrame;
                    int nextTranslateY = staticFrame;
                    int nextTranslateZ = staticFrame;
                    if (nextFrameValid)
                    {
                        nextTranslateX = nextFrame.getTranslatorX()[nextTransformIndex];
                        nextTranslateY = nextFrame.getTranslatorY()[nextTransformIndex];
                        nextTranslateZ = nextFrame.getTranslatorZ()[nextTransformIndex];
                        nextTransformIndex++;
                    }
                    int translateX;
                    int translateY;
                    int translateZ;
                    if (type == 2)
                    {
                        int deltaX = nextTranslateX - currentTranslateX & 0xFF;
                        int deltaY = nextTranslateY - currentTranslateY & 0xFF;
                        int deltaZ = nextTranslateZ - currentTranslateZ & 0xFF;
                        if (deltaX >= 128)
                        {
                            deltaX -= 256;
                        }
                        if (deltaY >= 128)
                        {
                            deltaY -= 256;
                        }
                        if (deltaZ >= 128)
                        {
                            deltaZ -= 256;
                        }
                        translateX = currentTranslateX + deltaX * interval / intervalCount & 0xFF;
                        translateY = currentTranslateY + deltaY * interval / intervalCount & 0xFF;
                        translateZ = currentTranslateZ + deltaZ * interval / intervalCount & 0xFF;
                    }
                    else if (type == 5)
                    {
                        // don't interpolate alpha transformations
                        // alpha
                        translateX = currentTranslateX;
                        translateY = 0;
                        translateZ = 0;
                    }
                    else
                    {
                        translateX = currentTranslateX + (nextTranslateX - currentTranslateX) * interval / intervalCount;
                        translateY = currentTranslateY + (nextTranslateY - currentTranslateY) * interval / intervalCount;
                        translateZ = currentTranslateZ + (nextTranslateZ - currentTranslateZ) * interval / intervalCount;
                    }
                    // use interpolated translations to animate
                    animate(type, skin.getList()[i], translateX, translateY, translateZ);
                }
            }
        }
    }

    @Override
    public byte getOverrideAmount() {
        return overrideAmount;
    }

    @Override
    public byte getOverrideHue() {
        return overrideHue;
    }

    @Override
    public byte getOverrideSaturation() {
        return overrideSaturation;
    }

    @Override
    public byte getOverrideLuminance() {
        return overrideLuminance;
    }

    @Override
    public HashMap<Integer, net.runelite.api.AABB> getAABBMap() {
        return aabb;
    }

    @Override
    public Shape getConvexHull(int localX, int localY, int orientation, int tileHeight) {
        int[] x2d = new int[getVerticesCount()];
        int[] y2d = new int[getVerticesCount()];

        Perspective.modelToCanvas(Client.instance, getVerticesCount(), localX, localY, tileHeight, orientation, getVerticesX(), getVerticesZ(), getVerticesY(), x2d, y2d);

        return Jarvis.convexHull(x2d, y2d);
    }

    @Override
    public int getBottomY() {
        return bottomY;
    }

    @Override
    public byte[] getTextureFaces() {
        return textureFaces;
    }

    @Override
    public int[] getTexIndices1() {
        return texIndices1;
    }

    @Override
    public int[] getTexIndices2() {
        return texIndices2;
    }

    @Override
    public int[] getTexIndices3() {
        return texIndices3;
    }

    @Override
    public int[] getVertexNormalsX() {
        if (vertexNormalsX == null) {
            vertexNormals();
        }
        return vertexNormalsX;
    }

    @Override
    public void setVertexNormalsX(int[] vertexNormalsX) {
        this.vertexNormalsX = vertexNormalsX;
    }

    @Override
    public int[] getVertexNormalsY() {
        if (vertexNormalsY == null) {
            vertexNormals();
        }
        return vertexNormalsY;
    }

    @Override
    public void setVertexNormalsY(int[] vertexNormalsY) {
        this.vertexNormalsY = vertexNormalsY;
    }

    @Override
    public int[] getVertexNormalsZ() {
        if (vertexNormalsZ == null) {
            vertexNormals();
        }
        return vertexNormalsZ;
    }

    @Override
    public void setVertexNormalsZ(int[] vertexNormalsZ) {
        this.vertexNormalsZ = vertexNormalsZ;
    }

    @Override
    public AABB getAABB(int orientation) {
        calculateExtreme(orientation);
        lastOrientation = orientation;
        return (AABB) getAABBMap().get(lastOrientation);
    }

    private RSModel unskewedModel;

    @Override
    public void setUnskewedModel(RSModel unskewedModel)
    {
        this.unskewedModel = unskewedModel;
    }

    @Override
    public RSModel getUnskewedModel()
    {
        return unskewedModel;
    }

}
