package com.client.draw;

import com.client.Rasterizer2D;
import com.client.draw.rasterizer.AbstractRasterizer;
import com.client.draw.rasterizer.Clips;
import com.client.draw.rasterizer.RasterizerDepth;
import com.client.draw.rasterizer.RasterizerNormal;
import com.client.graphics.textures.TextureLoader;
import com.client.graphics.textures.TextureProvider;
import net.runelite.rs.api.RSRasterizer3D;

public class Rasterizer3D implements RSRasterizer3D {

	public static int[] Rasterizer3D_colorPalette;

	static int[] field2790;

	public static int[] field2795;
	public static int[] Rasterizer3D_sine;

	public static int[] Rasterizer3D_cosine;

	public static Clips clips;

	static double field2799;
	static float[] field2798;
	static float[] field2791;
	public static AbstractRasterizer currentRasterizer;

	public static final AbstractRasterizer rasterizerNormal;

	public static final AbstractRasterizer rasterizerDepth;


	public static void initDrawingArea(int[] var0, int var1, int var2, float[] var3) {
		if (var3 == null && Rasterizer3D.rasterizerDepth == Rasterizer3D.currentRasterizer) {
			Rasterizer3D.currentRasterizer = Rasterizer3D.rasterizerNormal;
		}

		currentRasterizer.method5536(var0, var1, var2, var3);
	}


	static {
		Rasterizer3D_colorPalette = new int[65536];
		field2790 = new int[512];
		field2795 = new int[2048];
		Rasterizer3D_sine = new int[2048];
		Rasterizer3D_cosine = new int[2048];
		field2798 = new float[2048];
		field2791 = new float[2048];

		int var0;
		for(var0 = 1; var0 < 512; ++var0) {
			field2790[var0] = 32768 /  var0;
		}

		for(var0 = 1; var0 < 2048; ++var0) {
			field2795[var0] = 65536 / var0;
		}

		for(var0 = 0; var0 < 2048; ++var0) {
			double var1 = Math.sin((double)var0 * 0.0030679615D);
			double var3 = Math.cos((double)var0 * 0.0030679615D);
			Rasterizer3D_sine[var0] = (int)(65536.0D * var1);
			Rasterizer3D_cosine[var0] = (int)(65536.0D * var3);
			field2798[var0] = (float)var1;
			field2791[var0] = (float)var3;
		}

		clips = new Clips();
		rasterizerNormal = new RasterizerNormal(clips);
		rasterizerDepth = new RasterizerDepth(clips);
		currentRasterizer = rasterizerNormal;
	}

	public static void setClipBounds() {
		clips.setClipBounds();

		}
	public static void resetRasterClipping() {
		setClipBoundsAndInitOffsets(Rasterizer2D.Rasterizer2D_xClipStart, Rasterizer2D.Rasterizer2D_yClipStart, Rasterizer2D.Rasterizer2D_xClipEnd, Rasterizer2D.Rasterizer2D_yClipEnd);
	}
	static void setClipBoundsAndInitOffsets(int var0, int var1, int var2, int var3) {
		clips.clipX = var2 - var0;
		clips.clipY = var3 - var1;
		setClipBounds();
		if (Rasterizer3D.clips.Rasterizer3D_rowOffsets.length < Rasterizer3D.clips.clipY) {
			Rasterizer3D.clips.Rasterizer3D_rowOffsets = new int[roundUpToNextPowerOfTwo(Rasterizer3D.clips.clipY)];
		}

		int var4 = var0 + var1 * Rasterizer2D.Rasterizer2D_width;

		for(int var5 = 0; var5 < Rasterizer3D.clips.clipY; ++var5) {
			Rasterizer3D.clips.Rasterizer3D_rowOffsets[var5] = var4;
			var4 += Rasterizer2D.Rasterizer2D_width;
		}

	}

	public static void setCustomClipBounds(int var0, int var1) {
		int var2 = Rasterizer3D.clips.Rasterizer3D_rowOffsets[0];
		int var3 = var2 / Rasterizer2D.Rasterizer2D_width;
		int var4 = var2 - var3 * Rasterizer2D.Rasterizer2D_width;
		Rasterizer3D.clips.setCustomClipBounds(var0, var4, var1, var3);
	}


	public static int roundUpToNextPowerOfTwo(int height) {
		var clipY = currentRasterizer.clips.Rasterizer3D_rowOffsets;
		int v = height - 1;
		v |= v >>> 1;
		v |= v >>> 2;
		v |= v >>> 4;
		v |= v >>> 8;
		v |= v >>> 16;
		clipY = new int[v + 1];
		return clipY.length;
	}

	public static final void adjustBrightness(double var0) {
		Rasterizer3D.setBrightness(var0);
		((TextureProvider)Rasterizer3D.clips.Rasterizer3D_textureLoader).setBrightness(var0);

	}

	public static final int adjustLight(int color, int brightnessFactor) {
		brightnessFactor = (color & 127) * brightnessFactor >> 7;
		if (brightnessFactor < 2) {
			brightnessFactor = 2;
		} else if (brightnessFactor > 126) {
			brightnessFactor = 126;
		}
		return (color & 0xFF80) + brightnessFactor;
	}

	public static void setBrightness(double var0) {
		Rasterizer3D_buildPalette(var0, 0, 512);
	}

	public static void setTextureLoader(TextureLoader var0) {
		clips.Rasterizer3D_textureLoader = var0;
	}

	public static int adjustBrightness(int var0, double var1) {
		double var3 = (double)(var0 >> 16) / 256.0D;
		double var5 = (double)(var0 >> 8 & 255) / 256.0D;
		double var7 = (double)(var0 & 255) / 256.0D;
		var3 = Math.pow(var3, var1);
		var5 = Math.pow(var5, var1);
		var7 = Math.pow(var7, var1);
		int var9 = (int)(var3 * 256.0D);
		int var10 = (int)(var5 * 256.0D);
		int var11 = (int)(var7 * 256.0D);
		return var11 + (var10 << 8) + (var9 << 16);
	}


	static void Rasterizer3D_buildPalette(double var0, int var2, int var3) {
		field2799 = var0;
		int var4 = var2 * 128;

		for(int var5 = var2; var5 < var3; ++var5) {
			double var6 = (double)(var5 >> 3) / 64.0D + 0.0078125D;
			double var8 = 0.0625D + (double)(var5 & 7) / 8.0D;

			for(int var10 = 0; var10 < 128; ++var10) {
				double var11 = (double)var10 / 128.0D;
				double var13 = var11;
				double var15 = var11;
				double var17 = var11;
				if (var8 != 0.0D) {
					double var19;
					if (var11 < 0.5D) {
						var19 = var11 * (var8 + 1.0D);
					} else {
						var19 = var8 + var11 - var8 * var11;
					}

					double var21 = var11 * 2.0D - var19;
					double var23 = var6 + 0.3333333333333333D;
					if (var23 > 1.0D) {
						--var23;
					}

					double var27 = var6 - 0.3333333333333333D;
					if (var27 < 0.0D) {
						++var27;
					}

					if (var23 * 6.0D < 1.0D) {
						var13 = var23 * 6.0D * (var19 - var21) + var21;
					} else if (var23 * 2.0D < 1.0D) {
						var13 = var19;
					} else if (var23 * 3.0D < 2.0D) {
						var13 = var21 + 6.0D * (0.6666666666666666D - var23) * (var19 - var21);
					} else {
						var13 = var21;
					}

					if (var6 * 6.0D < 1.0D) {
						var15 = var21 + (var19 - var21) * 6.0D * var6;
					} else if (var6 * 2.0D < 1.0D) {
						var15 = var19;
					} else if (var6 * 3.0D < 2.0D) {
						var15 = var21 + (var19 - var21) * (0.6666666666666666D - var6) * 6.0D;
					} else {
						var15 = var21;
					}

					if (var27 * 6.0D < 1.0D) {
						var17 = var27 * (var19 - var21) * 6.0D + var21;
					} else if (var27 * 2.0D < 1.0D) {
						var17 = var19;
					} else if (var27 * 3.0D < 2.0D) {
						var17 = 6.0D * (var19 - var21) * (0.6666666666666666D - var27) + var21;
					} else {
						var17 = var21;
					}
				}

				int var29 = (int)(var13 * 256.0D);
				int var20 = (int)(var15 * 256.0D);
				int var30 = (int)(var17 * 256.0D);
				int var22 = var30 + (var20 << 8) + (var29 << 16);
				var22 = adjustBrightness(var22, var0);
				if (var22 == 0) {
					var22 = 1;
				}

				Rasterizer3D_colorPalette[var4++] = var22 | 0xFF000000;
			}
		}

	}

	public static int method2093(int var0, int var1, int var2, int var3) {
		return var0 * var2 - var3 * var1 >> 16;
	}

	public static int method2295(int var0, int var1, int var2, int var3) {
		return var2 * var1 + var3 * var0 >> 16;
	}

	public static int method812(int var0, int var1, int var2, int var3) {
		return var0 * var2 + var3 * var1 >> 16;
	}

	public static int method903(int var0, int var1, int var2, int var3) {
		return var2 * var1 - var3 * var0 >> 16;
	}

	public static void rasterGouraud(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11) {
		currentRasterizer.drawAlphaBlendedGraphics(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
	}

	public static void rasterFlat(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
		currentRasterizer.vmethod6189(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
	}

	public static void rasterTextureWithShadingAndLighting(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20, int var21) {
		currentRasterizer.drawGradientTriangle(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21);
	}

	public static void rasterTextureWithColors(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20, int var21) {
		currentRasterizer.textureMapPolygons(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21);
	}

	public static void rasterTriangleWithGradient(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11) {
		currentRasterizer.drawShadedTriangleColorOverride(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
	}
	public static void rasterTriangle(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
		currentRasterizer.drawFlatTriangleColorOverride(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
	}
	public static float calculateDepth(int depth) {
		final float CONST_A = 10075.0F;
		final float CONST_B = 1.0075567F;
		final float CONST_C = 75.56675F;

		float var1 = CONST_A - (float)depth;
		return (var1 * CONST_B - CONST_C) / var1;
	}

	public static void toggleZBuffering(boolean var0) {
		if (var0 && Rasterizer2D.Rasterizer2D_brightness != null) {
			currentRasterizer = rasterizerDepth;
		} else {
			currentRasterizer = rasterizerNormal;
		}
	}
	public static double method5367() {
		return field2799;
	}

	public static final int method4430(int arg0, int arg1, int arg2, int arg3) {
		return arg0 * arg2 + arg1 * arg3 >> 16;
	}

	public static final int method4410(int arg0, int arg1, int arg2, int arg3) {
		return arg1 * arg2 - arg0 * arg3 >> 16;
	}

	public static final int method4454(int arg0, int arg1, int arg2, int arg3) {
		return arg0 * arg2 - arg1 * arg3 >> 16;
	}

	public static final int method4412(int arg0, int arg1, int arg2, int arg3) {
		return arg0 * arg3 + arg1 * arg2 >> 16;
	}

	public static final int method4429(int arg0, int arg1, int arg2, int arg3) {
		return arg0 * arg2 + arg1 * arg3 >> 16;
	}

	public static final int method4439(int arg0, int arg1, int arg2, int arg3) {
		return arg1 * arg2 - arg0 * arg3 >> 16;
	}
	public  static boolean method5376() {
		return currentRasterizer.vmethod6185();
	}
	public static int method5412() {
		return clips.clipX;
	}
}
