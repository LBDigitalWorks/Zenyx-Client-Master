package com.client.draw.rasterizer;

import com.client.Rasterizer2D;
import com.client.draw.Rasterizer3D;

public abstract class AbstractRasterizer extends Rasterizer2D {
    static int field2819;
    public boolean isTransparent = false;

    public boolean isLowDetail;

    public int[] hslToRgb;

    public Clips clips;

    static {
        field2819 = 0;
    }
    public AbstractRasterizer(Clips clips) {
        this.isLowDetail = false;
        this.hslToRgb = Rasterizer3D.Rasterizer3D_colorPalette;
        this.clips = clips;
    }
    public abstract boolean vmethod6185();

    public void method5536(int[] var1, int var2, int var3, float[] var4) {
        Rasterizer2D.Rasterizer2D_init(var1, var2, var3, var4);
    }

    public void drawShadedTriangleColorOverride(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12) {
        byte var13 = this.clips.field3166.overrideAmount;
        if (var13 > 0) {
            byte var14 = this.clips.field3166.overrideHue;
            byte var15 = this.clips.field3166.overrideSaturation;
            byte var16 = this.clips.field3166.overrideLuminance;
            var10 = adjustColor(var10, var14, var15, var16, var13);
            var11 = adjustColor(var11, var14, var15, var16, var13);
            var12 = adjustColor(var12, var14, var15, var16, var13);
        }

        this.drawAlphaBlendedGraphics(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
    }

    public void drawFlatTriangleColorOverride(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10) {
        byte var11 = this.clips.field3166.overrideAmount;
        if (var11 > 0) {
            int var12 = adjustColor(var10, this.clips.field3166.overrideHue, this.clips.field3166.overrideSaturation, this.clips.field3166.overrideLuminance, this.clips.field3166.overrideAmount);
            var10 = this.hslToRgb[var12];
        }

        this.vmethod6189(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
    }

    public abstract void drawAlphaBlendedGraphics(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12);

    public abstract void vmethod6189(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10);

    public abstract void drawGradientTriangle(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20, int var21, int var22);

    public abstract void textureMapPolygons(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20, int var21, int var22);

    /**
     * Adjusts the color values of the given pixel based on the provided color offsets.
     *
     * @param pixel the pixel to adjust
     * @param redOffset the red color offset, from 0 to 255, or -1 to leave unchanged
     * @param greenOffset the green color offset, from 0 to 255, or -1 to leave unchanged
     * @param blueOffset the blue color offset, from 0 to 255, or -1 to leave unchanged
     * @param alphaOffset the alpha color offset, from 0 to 255, or -1 to leave unchanged
     * @return the adjusted pixel value
     */
    public static int adjustColor(int pixel, byte redOffset, byte greenOffset, byte blueOffset, byte alphaOffset) {
        int red = pixel >> 10 & 63;
        int green = pixel >> 7 & 7;
        int blue = pixel & 127;
        int alpha = alphaOffset & 255;
        if (redOffset != -1) {
            red += alpha * (redOffset - red) >> 7;
        }

        if (greenOffset != -1) {
            green += alpha * (greenOffset - green) >> 7;
        }

        if (blueOffset != -1) {
            blue += alpha * (blueOffset - blue) >> 7;
        }

        return (red << 10 | green << 7 | blue) & '\uffff';
    }
    static final int method5497(int var0, int var1) {
        var1 = (var0 & 127) * var1 >> 7;
        if (var1 < 2) {
            var1 = 2;
        } else if (var1 > 126) {
            var1 = 126;
        }

        return (var0 & 65408) + var1;
    }

}
