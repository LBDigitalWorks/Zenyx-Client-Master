package com.client.draw.font.osrs;

import com.client.Rasterizer2D;

public final class RSFontOSRS extends AbstractFont implements net.runelite.rs.api.RSFont {

    public RSFontOSRS(byte[] fontData, int[] leftBearings, int[] topBearings, int[] widths, int[] heights, int[] var6, byte[][] pixels) {
        super(fontData, leftBearings, topBearings, widths, heights, var6, pixels);
    }

    public RSFontOSRS(byte[] fontData) {
        super(fontData);
    }

    public void drawGlyph(byte[] fontData, int x, int y, int width, int height, int charIndex) {
        int pixelIndex = y * Rasterizer2D.Rasterizer2D_width + x;
        int rowWidth = Rasterizer2D.Rasterizer2D_width - width;
        int deltaX = 0;
        int deltaY = 0;
        int delta;

        if (y < Rasterizer2D.Rasterizer2D_yClipStart) {
            delta = Rasterizer2D.Rasterizer2D_yClipStart - y;
            height -= delta;
            y = Rasterizer2D.Rasterizer2D_yClipStart;
            deltaY += delta * width;
            pixelIndex += delta * Rasterizer2D.Rasterizer2D_width;
        }

        if (y + height > Rasterizer2D.Rasterizer2D_yClipEnd) {
            height -= y + height - Rasterizer2D.Rasterizer2D_yClipEnd;
        }

        if (x < Rasterizer2D.Rasterizer2D_xClipStart) {
            delta = Rasterizer2D.Rasterizer2D_xClipStart - x;
            width -= delta;
            x = Rasterizer2D.Rasterizer2D_xClipStart;
            deltaY += delta;
            pixelIndex += delta;
            deltaX += delta;
            rowWidth += delta;
        }

        if (x + width > Rasterizer2D.Rasterizer2D_xClipEnd) {
            delta = x + width - Rasterizer2D.Rasterizer2D_xClipEnd;
            width -= delta;
            deltaX += delta;
            rowWidth += delta;
        }

        if (width > 0 && height > 0) {
            AbstractFont.placeGlyph(Rasterizer2D.Rasterizer2D_pixels, fontData, charIndex, deltaY, pixelIndex, width, height, rowWidth, deltaX);
        }
    }


    public void drawGlyphAlpha(byte[] imageData, int x, int y, int width, int height, int color, int alpha) {
        int pixelIndex = y * Rasterizer2D.Rasterizer2D_width + x;
        int rightOverlap = Rasterizer2D.Rasterizer2D_width - width;
        int currentWidth = 0;
        int currentHeight = 0;
        int temp;
        if (y < Rasterizer2D.Rasterizer2D_yClipStart) {
            temp = Rasterizer2D.Rasterizer2D_yClipStart - y;
            height -= temp;
            y = Rasterizer2D.Rasterizer2D_yClipStart;
            currentHeight += temp * width;
            pixelIndex += temp * Rasterizer2D.Rasterizer2D_width;
        }

        if (y + height > Rasterizer2D.Rasterizer2D_yClipEnd) {
            height -= y + height - Rasterizer2D.Rasterizer2D_yClipEnd;
        }

        if (x < Rasterizer2D.Rasterizer2D_xClipStart) {
            temp = Rasterizer2D.Rasterizer2D_xClipStart - x;
            width -= temp;
            x = Rasterizer2D.Rasterizer2D_xClipStart;
            currentHeight += temp;
            pixelIndex += temp;
            currentWidth += temp;
            rightOverlap += temp;
        }

        if (x + width > Rasterizer2D.Rasterizer2D_xClipEnd) {
            temp = x + width - Rasterizer2D.Rasterizer2D_xClipEnd;
            width -= temp;
            currentWidth += temp;
            rightOverlap += temp;
        }

        if (width > 0 && height > 0) {
            placeGlyphAlpha(Rasterizer2D.Rasterizer2D_pixels, imageData, color, currentHeight, pixelIndex, width, height, rightOverlap, currentWidth, alpha);
        }
    }

    @Override
    public int getBaseline() {
        return ascent;
    }

    @Override
    public void drawTextLeftAligned(String text, int x, int y, int fontColor, int shadowColor) {
        draw(text,x,y,fontColor,shadowColor);
    }
}