package com.client;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.util.Hashtable;

import com.client.collection.node.DualNode;
import com.client.draw.Rasterizer3D;
import net.runelite.rs.api.RSRasterizer2D;

public class Rasterizer2D extends DualNode implements RSRasterizer2D {


    public static void drawAlpha(int[] pixels, int index, int value, int alpha) {
        if (! Client.instance.isGpu() || pixels != Client.instance.getBufferProvider().getPixels())
        {
            pixels[index] = value;
            return;
        }

        // (int) x * 0x8081 >>> 23 is equivalent to (short) x / 255
        int outAlpha = alpha + ((pixels[index] >>> 24) * (255 - alpha) * 0x8081 >>> 23);
        pixels[index] = value & 0x00FFFFFF | outAlpha << 24;
    }

    public static void initDrawingArea(int height, int width, int[] pixels, float[] depth) {
        Rasterizer2D.Rasterizer2D_pixels = pixels;
        Rasterizer2D.Rasterizer2D_width = width;
        Rasterizer2D.Rasterizer2D_height = height;
        Rasterizer2D.Rasterizer2D_brightness = depth;
        setDrawingAreaOSRS(height, 0, width, 0);
    }

    /**
     * Draws an item box filled with a certain colour.
     *
     * @param leftX     The left edge X-Coordinate of the box.
     * @param topY      The top edge Y-Coordinate of the box.
     * @param width     The width of the box.
     * @param height    The height of the box.
     * @param rgbColour The RGBColour of the box.
     */
    public static void drawItemBox(int leftX, int topY, int width, int height, int rgbColour) {
        if (leftX < Rasterizer2D.Rasterizer2D_xClipStart) {
            width -= Rasterizer2D.Rasterizer2D_xClipStart - leftX;
            leftX = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if (topY < Rasterizer2D.Rasterizer2D_yClipStart) {
            height -= Rasterizer2D.Rasterizer2D_yClipStart - topY;
            topY = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if (leftX + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - leftX;
        if (topY + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - topY;
        int leftOver = Rasterizer2D.Rasterizer2D_width - width;
        int pixelIndex = leftX + topY * Rasterizer2D.Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++)
                //pixels[pixelIndex++] = rgbColour;
                drawAlpha(Rasterizer2D_pixels, pixelIndex++, rgbColour, 0);
            pixelIndex += leftOver;
        }
    }

    public static void drawAlphaGradient(int x, int y, int gradientWidth, int gradientHeight, int startColor, int endColor, int alpha) {
        int k1 = 0;
        int l1 = 0x10000 / gradientHeight;
        if (x < Rasterizer2D_xClipStart) {
            gradientWidth -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (y < Rasterizer2D_yClipStart) {
            k1 += (Rasterizer2D_yClipStart - y) * l1;
            gradientHeight -= Rasterizer2D_yClipStart - y;
            y = Rasterizer2D_yClipStart;
        }
        if (x + gradientWidth > Rasterizer2D_xClipEnd)
            gradientWidth = Rasterizer2D_xClipEnd - x;
        if (y + gradientHeight > Rasterizer2D_yClipEnd)
            gradientHeight = Rasterizer2D_yClipEnd - y;
        int i2 = Rasterizer2D_width - gradientWidth;
        int result_alpha = 256 - alpha;
        int total_pixels = x + y * Rasterizer2D_width;
        for (int k2 = -gradientHeight; k2 < 0; k2++) {
            int gradient1 = 0x10000 - k1 >> 8;
            int gradient2 = k1 >> 8;
            int gradient_color = ((startColor & 0xff00ff) * gradient1 + (endColor & 0xff00ff) * gradient2 & 0xff00ff00) + ((startColor & 0xff00) * gradient1 + (endColor & 0xff00) * gradient2 & 0xff0000) >>> 8;
            int color = ((gradient_color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((gradient_color & 0xff00) * alpha >> 8 & 0xff00);
            for (int k3 = -gradientWidth; k3 < 0; k3++) {
                int colored_pixel = Rasterizer2D_pixels[total_pixels];
                colored_pixel = ((colored_pixel & 0xff00ff) * result_alpha >> 8 & 0xff00ff) + ((colored_pixel & 0xff00) * result_alpha >> 8 & 0xff00);
                drawAlpha(Rasterizer2D_pixels, total_pixels++, color + colored_pixel, alpha);
            }
            total_pixels += i2;
            k1 += l1;
        }
    }


    public static void init(int width, int height, int[] pixels, float[] depth) {
        Rasterizer2D.Rasterizer2D_brightness = depth;
        Rasterizer2D.Rasterizer2D_pixels = pixels;
        Rasterizer2D.Rasterizer2D_width = width;
        Rasterizer2D.Rasterizer2D_height = height;
        setDrawingArea(0, 0, width, height);
    }

    protected static void Rasterizer2D_init(int[] var0, int var1, int var2, float[] var3) {
        Rasterizer2D_pixels = var0;
        Rasterizer2D_width = var1;
        Rasterizer2D_height = var2;
        Rasterizer2D_brightness = var3;
        setClip(0, 0, var1, var2);
    }

    public static void getClipArray(int[] clip) {
        clip[0] = Rasterizer2D_xClipStart;
        clip[1] = Rasterizer2D_yClipStart;
        clip[2] = Rasterizer2D_xClipEnd;
        clip[3] = Rasterizer2D_yClipEnd;
    }

    public static void setClipArray(int[] clip) {
        Rasterizer2D_xClipStart = clip[0];
        Rasterizer2D_yClipStart = clip[1];
        Rasterizer2D_xClipEnd = clip[2];
        Rasterizer2D_yClipEnd = clip[3];
    }

    public static void resetClip() {
        Rasterizer2D_xClipStart = 0;
        Rasterizer2D_yClipStart = 0;
        Rasterizer2D_xClipEnd = Rasterizer2D_width;
        Rasterizer2D_yClipEnd = Rasterizer2D_height;
    }

    public static void resetDepth() {
        if (Rasterizer2D_brightness != null) {
            int var0;
            int var1;
            int var2;
            if (Rasterizer2D_xClipStart == 0 && Rasterizer2D_xClipEnd == Rasterizer2D_width && Rasterizer2D_yClipStart == 0 && Rasterizer2D_yClipEnd == Rasterizer2D_height) {
                var0 = Rasterizer2D_brightness.length;
                var1 = var0 - (var0 & 7);

                for(var2 = 0; var2 < var1; Rasterizer2D_brightness[var2++] = 0.0F) {
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                    Rasterizer2D_brightness[var2++] = 0.0F;
                }

                while(var2 < var0) {
                    Rasterizer2D_brightness[var2++] = 0.0F;
                }
            } else {
                var0 = Rasterizer2D_xClipEnd - Rasterizer2D_xClipStart;
                var1 = Rasterizer2D_yClipEnd - Rasterizer2D_yClipStart;
                var2 = Rasterizer2D_width - var0;
                int var3 = Rasterizer2D_yClipStart * Rasterizer2D_width + Rasterizer2D_xClipStart;
                int var4 = var0 >> 3;
                int var5 = var0 & 7;
                var0 = var3 - 1;

                for(int var7 = -var1; var7 < 0; ++var7) {
                    int var6;
                    if (var4 > 0) {
                        var6 = var4;

                        do {
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            --var6;
                        } while(var6 > 0);
                    }

                    if (var5 > 0) {
                        var6 = var5;

                        do {
                            ++var0;
                            Rasterizer2D_brightness[var0] = 0.0F;
                            --var6;
                        } while(var6 > 0);
                    }

                    var0 += var2;
                }
            }

        }
    }

    public static void setClip(int leftX, int topY, int rightX, int bottomY) {
        setDrawingAreaOSRS(bottomY,leftX,rightX,topY);
    }

    public static void rasterizerDrawGradientAlpha(int x, int y, int w, int h, int rgbTop, int rgbBottom, int alphaTop, int alphaBottom) {
        if (w > 0 && h > 0) {
            int var8 = 0;
            int var9 = 65536 / h;
            if (x < Rasterizer2D.Rasterizer2D_xClipStart) {
                w -= Rasterizer2D.Rasterizer2D_xClipStart - x;
                x = Rasterizer2D.Rasterizer2D_xClipStart;
            }

            if (y < Rasterizer2D.Rasterizer2D_yClipStart) {
                var8 += (Rasterizer2D.Rasterizer2D_yClipStart - y) * var9;
                h -= Rasterizer2D.Rasterizer2D_yClipStart - y;
                y = Rasterizer2D.Rasterizer2D_yClipStart;
            }

            if (x + w > Rasterizer2D.Rasterizer2D_xClipEnd) {
                w = Rasterizer2D.Rasterizer2D_xClipEnd - x;
            }

            if (h + y > Rasterizer2D.Rasterizer2D_yClipEnd) {
                h = Rasterizer2D.Rasterizer2D_yClipEnd - y;
            }

            int var10 = Rasterizer2D.Rasterizer2D_width - w;
            int var11 = x + Rasterizer2D.Rasterizer2D_width * y;

            for(int var12 = -h; var12 < 0; ++var12) {
                int var13 = 65536 - var8 >> 8;
                int var14 = var8 >> 8;
                int var15 = (var13 * alphaTop + var14 * alphaBottom & '\uff00') >>> 8;
                if (var15 == 0) {
                    var11 += Rasterizer2D.Rasterizer2D_width;
                    var8 += var9;
                } else {
                    int var16 = (var14 * (rgbBottom & 16711935) + var13 * (rgbTop & 16711935) & -16711936) + (var14 * (rgbBottom & '\uff00') + var13 * (rgbTop & '\uff00') & 16711680) >>> 8;
                    int var17 = 255 - var15;
                    int var18 = ((var16 & 16711935) * var15 >> 8 & 16711935) + (var15 * (var16 & '\uff00') >> 8 & '\uff00');

                    for(int var19 = -w; var19 < 0; ++var19) {
                        int var20 = Rasterizer2D.Rasterizer2D_pixels[var11];
                        if (var20 == 0) {
                            drawAlpha(Rasterizer2D.Rasterizer2D_pixels, var11++, var18, var15);
                        } else {
                            var20 = ((var20 & 16711935) * var17 >> 8 & 16711935) + (var17 * (var20 & '\uff00') >> 8 & '\uff00');
                            drawAlpha(Rasterizer2D.Rasterizer2D_pixels, var11++, var18 + var20, var15);
                        }
                    }

                    var11 += var10;
                    var8 += var9;
                }
            }
        }
    }

    /**
     * Draws a coloured vertical line in the drawingArea.
     * @param xPosition The X-Position of the line.
     * @param yPosition The start Y-Position of the line.
     * @param height The height of the line.
     * @param rgbColour The colour of the line.
     */
    public static void drawVerticalLine(int xPosition, int yPosition, int height, int rgbColour){
        if(xPosition < Rasterizer2D_xClipStart || xPosition >= Rasterizer2D_xClipEnd)
            return;
        if(yPosition < Rasterizer2D_yClipStart){
            height -= Rasterizer2D_yClipStart - yPosition;
            yPosition = Rasterizer2D_yClipStart;
        }
        if(yPosition + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - yPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D_width;
        for(int rowIndex = 0; rowIndex < height; rowIndex++)
            drawAlpha(Rasterizer2D_pixels, pixelIndex + rowIndex * Rasterizer2D_width, rgbColour, 255);
    }


    /**
     * Draws a transparent box with a gradient that changes from top to bottom.
     * @param leftX The left edge X-Coordinate of the box.
     * @param topY The top edge Y-Coordinate of the box.
     * @param width The width of the box.
     * @param height The height of the box.
     * @param topColour The top rgbColour of the gradient.
     * @param bottomColour The bottom rgbColour of the gradient.
     * @param opacity The opacity value ranging from 0 to 256.
     */
    public static void drawTransparentGradientBox(int leftX, int topY, int width, int height, int topColour, int bottomColour, int opacity) {
        int gradientProgress = 0;
        int progressPerPixel = 0x10000 / height;
        if (leftX < Rasterizer2D.Rasterizer2D_xClipStart) {
            width -= Rasterizer2D.Rasterizer2D_xClipStart - leftX;
            leftX = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if (topY < Rasterizer2D.Rasterizer2D_yClipStart) {
            gradientProgress += (Rasterizer2D.Rasterizer2D_yClipStart - topY) * progressPerPixel;
            height -= Rasterizer2D.Rasterizer2D_yClipStart - topY;
            topY = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if (leftX + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - leftX;
        if (topY + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - topY;
        int leftOver = Rasterizer2D.Rasterizer2D_width - width;
        int transparency = 256 - opacity;
        int pixelIndex = leftX + topY * Rasterizer2D.Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            int gradient = 0x10000 - gradientProgress >> 8;
            int inverseGradient = gradientProgress >> 8;
            int gradientColour = ((topColour & 0xff00ff) * gradient + (bottomColour & 0xff00ff) * inverseGradient & 0xff00ff00) + ((topColour & 0xff00) * gradient + (bottomColour & 0xff00) * inverseGradient & 0xff0000) >>> 8;
            int transparentPixel = ((gradientColour & 0xff00ff) * opacity >> 8 & 0xff00ff) + ((gradientColour & 0xff00) * opacity >> 8 & 0xff00);
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                int backgroundPixel = Rasterizer2D_pixels[pixelIndex];
                backgroundPixel = ((backgroundPixel & 0xff00ff) * transparency >> 8 & 0xff00ff) + ((backgroundPixel & 0xff00) * transparency >> 8 & 0xff00);
                drawAlpha(Rasterizer2D_pixels,pixelIndex++,transparentPixel + backgroundPixel,opacity);
            }
            pixelIndex += leftOver;
            gradientProgress += progressPerPixel;
        }
    }

    /**
     * Sets the drawingArea to the default size and position.
     * Position: Upper left corner.
     * Size: As specified before.
     */
    public static void set_default_size() {
        Rasterizer2D_xClipStart = 0;
        Rasterizer2D_yClipStart = 0;
        Rasterizer2D_xClipEnd = Rasterizer2D_width;
        Rasterizer2D_yClipEnd = Rasterizer2D_height;

    }

    public static void setDrawingArea(int x, int y, int width, int height) {
        if(x < 0) {
            x = 0;
        }
        if(y < 0) {
            y = 0;
        }
        if (width > Rasterizer2D.Rasterizer2D_width) {
            width = Rasterizer2D.Rasterizer2D_width;
        }
        if (height > Rasterizer2D.Rasterizer2D_height) {
            height = Rasterizer2D.Rasterizer2D_height;
        }
        Rasterizer2D_xClipStart = x;
        Rasterizer2D_yClipStart = y;
        Rasterizer2D_xClipEnd = width;
        Rasterizer2D_yClipEnd = height;

    }

    public static void expandClip(int var0, int var1, int var2, int var3) {
        if (Rasterizer2D_xClipStart < var0) {
            Rasterizer2D_xClipStart = var0;
        }

        if (Rasterizer2D_yClipStart < var1) {
            Rasterizer2D_yClipStart = var1;
        }

        if (Rasterizer2D_xClipEnd > var2) {
            Rasterizer2D_xClipEnd = var2;
        }

        if (Rasterizer2D_yClipEnd > var3) {
            Rasterizer2D_yClipEnd = var3;
        }
    }

    /**
     * Sets the drawingArea based on the coordinates of the edges.
     * @param bottomY The bottom edge Y-Coordinate.
     * @param leftX The left edge X-Coordinate.
     * @param rightX The right edge X-Coordinate.
     * @param topY The top edge Y-Coordinate.
     */
    public static void setDrawingAreaOSRS(int bottomY, int leftX, int rightX, int topY) {
        if(leftX < 0) {
            leftX = 0;
        }
        if(topY < 0) {
            topY = 0;
        }
        if(rightX > Rasterizer2D_width) {
            rightX = Rasterizer2D_width;
        }
        if(bottomY > Rasterizer2D_height) {
            bottomY = Rasterizer2D_height;
        }
        Rasterizer2D.Rasterizer2D_xClipStart = leftX;
        Rasterizer2D.Rasterizer2D_yClipStart = topY;
        Rasterizer2D.Rasterizer2D_xClipEnd = rightX;
        Rasterizer2D.Rasterizer2D_yClipEnd = bottomY;

    }

    /**
     * Clears the drawingArea by setting every pixel to 0 (black).
     */
    public static void clear()    {
        int size = Rasterizer2D_width * Rasterizer2D_height;
        for(int coordinates = 0; coordinates < size; coordinates++) {
            Rasterizer2D_pixels[coordinates] = 0;
        }
        resetDepth();
    }

    public static void Rasterizer2D_clear() {
        int var0 = 0;

        int var1;
        for (var1 = Rasterizer2D_width * Rasterizer2D_height - 7; var0 < var1; Rasterizer2D_pixels[var0++] = 0) {
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
            Rasterizer2D_pixels[var0++] = 0;
        }

        for (var1 += 7; var0 < var1; Rasterizer2D_pixels[var0++] = 0) {
        }

        resetDepth();
    }

    public static void draw_filled_rect(int x, int y, int width, int height, int color) {
        draw_filled_rect(x, y, width, height, color, 255);
    }

    /**
     * Draws a transparent box.
     * @param leftX The left edge X-Coordinate of the box.
     * @param topY The top edge Y-Coordinate of the box.
     * @param width The box width.
     * @param height The box height.
     * @param rgbColour The box colour.
     * @param opacity The opacity value ranging from 0 to 256.
     */
    public static void draw_filled_rect(int leftX, int topY, int width, int height, int rgbColour, int opacity) {
        if (leftX < Rasterizer2D.Rasterizer2D_xClipStart) {
            width -= Rasterizer2D.Rasterizer2D_xClipStart - leftX;
            leftX = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if (topY < Rasterizer2D.Rasterizer2D_yClipStart) {
            height -= Rasterizer2D.Rasterizer2D_yClipStart - topY;
            topY = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if (leftX + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - leftX;
        if (topY + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - topY;
        int transparency = 256 - opacity;
        int red = (rgbColour >> 16 & 0xff) * opacity;
        int green = (rgbColour >> 8 & 0xff) * opacity;
        int blue = (rgbColour & 0xff) * opacity;
        int leftOver = Rasterizer2D.Rasterizer2D_width - width;
        int pixelIndex = leftX + topY * Rasterizer2D.Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                int otherRed = (Rasterizer2D_pixels[pixelIndex] >> 16 & 0xff) * transparency;
                int otherGreen = (Rasterizer2D_pixels[pixelIndex] >> 8 & 0xff) * transparency;
                int otherBlue = (Rasterizer2D_pixels[pixelIndex] & 0xff) * transparency;
                int transparentColour = ((red + otherRed >> 8) << 16) + ((green + otherGreen >> 8) << 8) + (blue + otherBlue >> 8);
                drawAlpha(Rasterizer2D_pixels,pixelIndex++,transparentColour,opacity);
            }
            pixelIndex += leftOver;
        }
    }

    public static void drawPixels(int height, int posY, int posX, int color, int w) {
        if (posX < Rasterizer2D_xClipStart) {
            w -= Rasterizer2D_xClipStart - posX;
            posX = Rasterizer2D_xClipStart;
        }
        if (posY < Rasterizer2D_yClipStart) {
            height -= Rasterizer2D_yClipStart - posY;
            posY = Rasterizer2D_yClipStart;
        }
        if (posX + w > Rasterizer2D_xClipEnd) {
            w = Rasterizer2D_xClipEnd - posX;
        }
        if (posY + height > Rasterizer2D_yClipEnd) {
            height = Rasterizer2D_yClipEnd - posY;
        }
        int k1 = Rasterizer2D_width - w;
        int l1 = posX + posY * Rasterizer2D_width;
        for (int i2 = -height; i2 < 0; i2++) {
            for (int j2 = -w; j2 < 0; j2++) {
                drawAlpha(Rasterizer2D_pixels,l1++,color,255);
            }

            l1 += k1;
        }
    }

    /**
     * Draws a 1 pixel thick box outline in a certain colour.
     * @param x The left edge X-Coordinate.
     * @param y The top edge Y-Coordinate.
     * @param width The width.
     * @param height The height.
     * @param rgbColour The RGB-Colour.
     */
    public static void draw_rect_outline(int x, int y, int width, int height, int rgbColour) {
        draw_horizontal_line(x, y, width, rgbColour);
        draw_horizontal_line(x, (y + height) - 1, width, rgbColour);
        draw_vertical_line(x, y, height, rgbColour);
        draw_vertical_line((x + width) - 1, y, height, rgbColour);
    }

    /**
     * Draws a coloured horizontal line in the drawingArea.
     * @param xPosition The start X-Position of the line.
     * @param yPosition The Y-Position of the line.
     * @param width The width of the line.
     * @param rgbColour The colour of the line.
     */
    public static void draw_horizontal_line(int xPosition, int yPosition, int width, int rgbColour) {
        if (yPosition < Rasterizer2D_yClipStart || yPosition >= Rasterizer2D_yClipEnd)
            return;
        if (xPosition < Rasterizer2D_xClipStart) {
            width -= Rasterizer2D_xClipStart - xPosition;
            xPosition = Rasterizer2D_xClipStart;
        }
        if (xPosition + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - xPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D.Rasterizer2D_width;
        for (int i = 0; i < width; i++)
            drawAlpha(Rasterizer2D_pixels,pixelIndex + i,rgbColour,255);
    }

    public static void drawStroke(int xPos, int yPos, int width, int height, int color, int strokeWidth) {

        drawVerticalStrokeLine(xPos, yPos, height, color, strokeWidth);
        drawVerticalStrokeLine((xPos + width) - strokeWidth, yPos, height, color, strokeWidth);
        drawHorizontalStrokeLine(xPos, yPos, width, color, strokeWidth);
        drawHorizontalStrokeLine(xPos, (yPos + height) - strokeWidth, width, color, strokeWidth);

    }

    /**
     * Draws a coloured horizontal line in the drawingArea.
     * @param xPosition The start X-Position of the line.
     * @param yPosition The Y-Position of the line.
     * @param width The width of the line.
     * @param rgbColour The colour of the line.
     */
    public static void drawHorizontalLine(int xPosition, int yPosition, int width, int rgbColour){
        if(yPosition < Rasterizer2D_yClipStart || yPosition >= Rasterizer2D_yClipEnd)
            return;
        if(xPosition < Rasterizer2D_xClipStart){
            width -= Rasterizer2D_xClipStart - xPosition;
            xPosition = Rasterizer2D_xClipStart;
        }
        if(xPosition + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - xPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D.Rasterizer2D_width;
        for(int i = 0; i < width; i++)
            drawAlpha(Rasterizer2D_pixels, pixelIndex + i, rgbColour, 255);
    }

    public static void drawHorizontalStrokeLine(int xPos, int yPos, int w, int hexColor, int strokeWidth) {

        if (yPos < Rasterizer2D_yClipStart || yPos >= Rasterizer2D_yClipEnd)
            return;
        if (xPos < Rasterizer2D_xClipStart) {
            w -= Rasterizer2D_xClipStart - xPos;
            xPos = Rasterizer2D_xClipStart;
        }
        if (xPos + w > Rasterizer2D_xClipEnd)
            w = Rasterizer2D_xClipEnd - xPos;
        int index = xPos + yPos * Rasterizer2D_width;
        int leftWidth = Rasterizer2D_width - w;
        for (int x = 0; x < strokeWidth; x++) {
            for (int y = 0; y < w; y++) {
                drawAlpha(Rasterizer2D_pixels,index++,hexColor,255);
            }
            index += leftWidth;
        }

    }

    public static void drawVerticalStrokeLine(int xPosition, int yPosition, int height, int hexColor, int strokeWidth) {
        if (xPosition < Rasterizer2D_xClipStart || xPosition >= Rasterizer2D_xClipEnd)
            return;
        if (yPosition < Rasterizer2D_yClipStart) {
            height -= Rasterizer2D_yClipStart - yPosition;
            yPosition = Rasterizer2D_yClipStart;
        }
        if (yPosition + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - yPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int x = 0; x < strokeWidth; x++) {
                drawAlpha(Rasterizer2D_pixels,pixelIndex + x + rowIndex * Rasterizer2D_width,hexColor,255);
            }
        }
    }

    /**
     * Draws a coloured vertical line in the drawingArea.
     * @param xPosition The X-Position of the line.
     * @param yPosition The start Y-Position of the line.
     * @param height The height of the line.
     * @param rgbColour The colour of the line.
     */
    public static void draw_vertical_line(int xPosition, int yPosition, int height, int rgbColour) {
        if (xPosition < Rasterizer2D_xClipStart || xPosition >= Rasterizer2D_xClipEnd)
            return;
        if (yPosition < Rasterizer2D_yClipStart) {
            height -= Rasterizer2D_yClipStart - yPosition;
            yPosition = Rasterizer2D_yClipStart;
        }
        if (yPosition + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - yPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++)
            drawAlpha(Rasterizer2D_pixels,pixelIndex + rowIndex * Rasterizer2D_width,rgbColour,255);
    }

    public static void drawHorizontalLine(int x, int y, int length, int color, int alpha) {
        if (y < Rasterizer2D_yClipStart || y >= Rasterizer2D_yClipEnd) {
            return;
        }
        if (x < Rasterizer2D_xClipStart) {
            length -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (x + length > Rasterizer2D_xClipEnd) {
            length = Rasterizer2D_xClipEnd - x;
        }
        final int j1 = 256 - alpha;
        final int k1 = (color >> 16 & 0xff) * alpha;
        final int l1 = (color >> 8 & 0xff) * alpha;
        final int i2 = (color & 0xff) * alpha;
        int i3 = x + y * Rasterizer2D_width;
        for (int j3 = 0; j3 < length; j3++) {
            final int j2 = (Rasterizer2D_pixels[i3] >> 16 & 0xff) * j1;
            final int k2 = (Rasterizer2D_pixels[i3] >> 8 & 0xff) * j1;
            final int l2 = (Rasterizer2D_pixels[i3] & 0xff) * j1;
            final int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
            drawAlpha(Rasterizer2D_pixels,i3++,k3,255);
        }
    }

    public static void draw_line(int i, int j, int k, int l)
    {
        if (i < Rasterizer2D_yClipStart || i >= Rasterizer2D_yClipEnd)
            return;
        if (l < Rasterizer2D_xClipStart)
        {
            k -= Rasterizer2D_xClipStart - l;
            l = Rasterizer2D_xClipStart;
        }
        if (l + k > Rasterizer2D_xClipEnd)
            k = Rasterizer2D_xClipEnd - l;
        int i1 = l + i * Rasterizer2D_width;
        for (int j1 = 0; j1 < k; j1++)
            drawAlpha(Rasterizer2D_pixels,i1 + j1,j,255);

    }

    public static void drawAlphaBox(int x, int y, int lineWidth, int lineHeight, int color, int alpha) {
        if (y < Rasterizer2D_yClipStart) {
            if (y > (Rasterizer2D_yClipStart - lineHeight)) {
                lineHeight -= (Rasterizer2D_yClipStart - y);
                y += (Rasterizer2D_yClipStart - y);
            } else {
                return;
            }
        }
        if (y + lineHeight > Rasterizer2D_yClipEnd) {
            lineHeight -= y + lineHeight - Rasterizer2D_yClipEnd;
        }
        //if (y >= bottomY - lineHeight)
        //return;
        if (x < Rasterizer2D_xClipStart) {
            lineWidth -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (x + lineWidth > Rasterizer2D_xClipEnd)
            lineWidth = Rasterizer2D_xClipEnd - x;
        for(int yOff = 0; yOff < lineHeight; yOff++) {
            int i3 = x + (y + (yOff)) * Rasterizer2D_width;
            for (int j3 = 0; j3 < lineWidth; j3++) {
                //int alpha2 = (lineWidth-j3) / (lineWidth/alpha);
                int j1 = 256 - alpha;//alpha2 is for gradient
                int k1 = (color >> 16 & 0xff) * alpha;
                int l1 = (color >> 8 & 0xff) * alpha;
                int i2 = (color & 0xff) * alpha;
                int j2 = (Rasterizer2D_pixels[i3] >> 16 & 0xff) * j1;
                int k2 = (Rasterizer2D_pixels[i3] >> 8 & 0xff) * j1;
                int l2 = (Rasterizer2D_pixels[i3] & 0xff) * j1;
                int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8)
                        + (i2 + l2 >> 8);
                drawAlpha(Rasterizer2D_pixels,i3 ++,k3,alpha);
            }
        }
    }

    /**
     * Draws a 1 pixel thick transparent box outline in a certain colour.
     * @param leftX The left edge X-Coordinate
     * @param topY The top edge Y-Coordinate.
     * @param width The width.
     * @param height The height.
     * @param rgbColour The RGB-Colour.
     * @param opacity The opacity value ranging from 0 to 256.
     */
    public static void drawTransparentBoxOutline(int leftX, int topY, int width, int height, int rgbColour, int opacity) {
        drawTransparentHorizontalLine(leftX, topY, width, rgbColour, opacity);
        drawTransparentHorizontalLine(leftX, topY + height - 1, width, rgbColour, opacity);
        if (height >= 3) {
            drawTransparentVerticalLine(leftX, topY + 1, height - 2, rgbColour, opacity);
            drawTransparentVerticalLine(leftX + width - 1, topY + 1, height - 2, rgbColour, opacity);
        }
    }

    /**
     * Draws a transparent coloured horizontal line in the drawingArea.
     * @param xPosition The start X-Position of the line.
     * @param yPosition The Y-Position of the line.
     * @param width The width of the line.
     * @param rgbColour The colour of the line.
     * @param opacity The opacity value ranging from 0 to 256.
     */
    public static void drawTransparentHorizontalLine(int xPosition, int yPosition, int width, int rgbColour, int opacity) {
        if (yPosition < Rasterizer2D_yClipStart || yPosition >= Rasterizer2D_yClipEnd) {
            return;
        }
        if (xPosition < Rasterizer2D_xClipStart) {
            width -= Rasterizer2D_xClipStart - xPosition;
            xPosition = Rasterizer2D_xClipStart;
        }
        if (xPosition + width > Rasterizer2D_xClipEnd) {
            width = Rasterizer2D_xClipEnd - xPosition;
        }
        final int transparency = 256 - opacity;
        final int red = (rgbColour >> 16 & 0xff) * opacity;
        final int green = (rgbColour >> 8 & 0xff) * opacity;
        final int blue = (rgbColour & 0xff) * opacity;
        int pixelIndex = xPosition + yPosition * Rasterizer2D.Rasterizer2D_width;
        for (int i = 0; i < width; i++) {
            final int otherRed = (Rasterizer2D_pixels[pixelIndex] >> 16 & 0xff) * transparency;
            final int otherGreen = (Rasterizer2D_pixels[pixelIndex] >> 8 & 0xff) * transparency;
            final int otherBlue = (Rasterizer2D_pixels[pixelIndex] & 0xff) * transparency;
            final int transparentColour = (red + otherRed >> 8 << 16) + (green + otherGreen >> 8 << 8) + (blue + otherBlue >> 8);
            drawAlpha(Rasterizer2D_pixels,pixelIndex ++,transparentColour,opacity);
        }
    }

    /**
     * Draws a transparent coloured vertical line in the drawingArea.
     * @param xPosition The X-Position of the line.
     * @param yPosition The start Y-Position of the line.
     * @param height The height of the line.
     * @param rgbColour The colour of the line.
     * @param opacity The opacity value ranging from 0 to 256.
     */
    public static void drawTransparentVerticalLine(int xPosition, int yPosition, int height, int rgbColour, int opacity) {
        if (xPosition < Rasterizer2D_xClipStart || xPosition >= Rasterizer2D_xClipEnd) {
            return;
        }
        if (yPosition < Rasterizer2D_yClipStart) {
            height -= Rasterizer2D_yClipStart - yPosition;
            yPosition = Rasterizer2D_yClipStart;
        }
        if (yPosition + height > Rasterizer2D_yClipEnd) {
            height = Rasterizer2D_yClipEnd - yPosition;
        }
        final int transparency = 256 - opacity;
        final int red = (rgbColour >> 16 & 0xff) * opacity;
        final int green = (rgbColour >> 8 & 0xff) * opacity;
        final int blue = (rgbColour & 0xff) * opacity;
        int pixelIndex = xPosition + yPosition * Rasterizer2D_width;
        for (int i = 0; i < height; i++) {
            final int otherRed = (Rasterizer2D_pixels[pixelIndex] >> 16 & 0xff) * transparency;
            final int otherGreen = (Rasterizer2D_pixels[pixelIndex] >> 8 & 0xff) * transparency;
            final int otherBlue = (Rasterizer2D_pixels[pixelIndex] & 0xff) * transparency;
            final int transparentColour = (red + otherRed >> 8 << 16) + (green + otherGreen >> 8 << 8) + (blue + otherBlue >> 8);
            drawAlpha(Rasterizer2D_pixels,pixelIndex,transparentColour,opacity);
            pixelIndex += Rasterizer2D_width;
        }
    }

    public static Graphics2D createGraphics(boolean renderingHints) {
        Graphics2D g2d = createGraphics(Rasterizer2D_pixels, Rasterizer2D_width, Rasterizer2D_height);
        if (renderingHints) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return g2d;
    }

    public static Graphics2D createGraphics(int[] pixels, int width, int height) {
        return new BufferedImage(COLOR_MODEL, Raster.createWritableRaster(COLOR_MODEL.createCompatibleSampleModel(width, height), new DataBufferInt(pixels, width * height), null), false, new Hashtable<Object, Object>()).createGraphics();
    }

    public static Shape createSector(int x, int y, int r, int angle) {
        return new Arc2D.Double(x, y, r, r, 90, -angle, Arc2D.PIE);
    }

    public static Shape createCircle(int x, int y, int r) {
        return new Ellipse2D.Double(x, y, r, r);
    }

    public static Shape createRing(Shape sector, Shape innerCircle) {
        Area ring = new Area(sector);
        ring.subtract(new Area(innerCircle));
        return ring;
    }

    public static void drawFilledCircle(int x, int y, int radius, int color, int alpha) {
        int y1 = y - radius;
        if (y1 < 0) {
            y1 = 0;
        }
        int y2 = y + radius;
        if (y2 >= Rasterizer2D_height) {
            y2 = Rasterizer2D_height - 1;
        }
        int a2 = 256 - alpha;
        int r1 = (color >> 16 & 0xff) * alpha;
        int g1 = (color >> 8 & 0xff) * alpha;
        int b1 = (color & 0xff) * alpha;
        for (int iy = y1; iy <= y2; iy++) {
            int dy = iy - y;
            int dist = (int) Math.sqrt(radius * radius - dy * dy);
            int x1 = x - dist;
            if (x1 < 0) {
                x1 = 0;
            }
            int x2 = x + dist;
            if (x2 >= Rasterizer2D_width) {
                x2 = Rasterizer2D_width - 1;
            }
            int pos = x1 + iy * Rasterizer2D_width;
            for (int ix = x1; ix <= x2; ix++) {
                /*  Tried replacing all pixels[pos] with:
                    Client.instance.gameScreenImageProducer.canvasRaster[pos]
                    AND Rasterizer3D.pixels[pos] */
                int r2 = (Rasterizer2D_pixels[pos] >> 16 & 0xff) * a2;
                int g2 = (Rasterizer2D_pixels[pos] >> 8 & 0xff) * a2;
                int b2 = (Rasterizer2D_pixels[pos] & 0xff) * a2;
                drawAlpha(Rasterizer2D_pixels,pos++,((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8),255);
            }
        }
    }

    public static void drawRectangle(int x, int y, int width, int height, int color, int alpha) {
        drawHorizontalLine(x, y, width, color, alpha);
        drawHorizontalLine(x, y + height - 1, width, color, alpha);
        if(height >= 3) {
            drawAlphaVerticalLine(x, y + 1, height - 2, color, alpha);
            drawAlphaVerticalLine(x + width - 1, y + 1, height - 2, color, alpha);
        }
    }

    public static void drawAlphaVerticalLine(int x, int y, int length, int color, int alpha) {
        if(x < Rasterizer2D_xClipStart || x >= Rasterizer2D_xClipEnd) {
            return;
        }
        if(y < Rasterizer2D_yClipStart) {
            length -= Rasterizer2D_yClipStart - y;
            y = Rasterizer2D_yClipStart;
        }
        if(y + length > Rasterizer2D_yClipEnd) {
            length = Rasterizer2D_yClipEnd - y;
        }
        final int j1 = 256 - alpha;
        final int k1 = (color >> 16 & 0xff) * alpha;
        final int l1 = (color >> 8 & 0xff) * alpha;
        final int i2 = (color & 0xff) * alpha;
        int i3 = x + y * Rasterizer2D_width;
        for(int j3 = 0; j3 < length; j3++) {
            final int j2 = (Rasterizer2D_pixels[i3] >> 16 & 0xff) * j1;
            final int k2 = (Rasterizer2D_pixels[i3] >> 8 & 0xff) * j1;
            final int l2 = (Rasterizer2D_pixels[i3] & 0xff) * j1;
            final int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
            drawAlpha(Rasterizer2D_pixels,i3,(k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8),255);
            i3 += Rasterizer2D_width;
        }
    }

    public static void fillRectangle(int x, int y, int w, int h, int color) {
        if (x < Rasterizer2D_xClipStart) {
            w -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (y < Rasterizer2D_yClipStart) {
            h -= Rasterizer2D_yClipStart - y;
            y = Rasterizer2D_yClipStart;
        }
        if (x + w > Rasterizer2D_xClipEnd) {
            w = Rasterizer2D_xClipEnd - x;
        }
        if (y + h > Rasterizer2D_yClipEnd) {
            h = Rasterizer2D_yClipEnd - y;
        }
        int k1 = Rasterizer2D_width - w;
        int l1 = x + y * Rasterizer2D_width;
        for (int i2 = -h; i2 < 0; i2++) {
            for (int j2 = -w; j2 < 0; j2++) {
                Rasterizer2D_pixels[l1++] = color;

            }
            l1 += k1;
        }
    }

    public static void fillRectangle(int x, int y, int w, int h, int color, int alpha) {
        if (x < Rasterizer2D_xClipStart) {
            w -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (y < Rasterizer2D_yClipStart) {
            h -= Rasterizer2D_yClipStart - y;
            y = Rasterizer2D_yClipStart;
        }
        if (x + w > Rasterizer2D_xClipEnd) {
            w = Rasterizer2D_xClipEnd - x;
        }
        if (y + h > Rasterizer2D_yClipEnd) {
            h = Rasterizer2D_yClipEnd - y;
        }
        int a2 = 256 - alpha;
        int r1 = (color >> 16 & 0xff) * alpha;
        int g1 = (color >> 8 & 0xff) * alpha;
        int b1 = (color & 0xff) * alpha;
        int k3 = Rasterizer2D_width - w;
        int pixel = x + y * Rasterizer2D_width;
        for (int i4 = 0; i4 < h; i4++) {
            for (int j4 = -w; j4 < 0; j4++) {
                int r2 = (Rasterizer2D_pixels[pixel] >> 16 & 0xff) * a2;
                int g2 = (Rasterizer2D_pixels[pixel] >> 8 & 0xff) * a2;
                int b2 = (Rasterizer2D_pixels[pixel] & 0xff) * a2;
                int rgb = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
                drawAlpha(Rasterizer2D_pixels,pixel++,rgb,255);
            }
            pixel += k3;
        }
    }

    public static final void drawAlphaCircle(final int x, int y, int radius, final int color, final int alpha) {
        if (alpha != 0) {
            if (alpha == 256) {
                drawCircle(x, y, radius, color);
            } else {
                if (radius < 0) {
                    radius = -radius;
                }
                final int opacity = 256 - alpha;
                final int source_red = (color >> 16 & 0xff) * alpha;
                final int source_green = (color >> 8 & 0xff) * alpha;
                final int source_blue = (color & 0xff) * alpha;
                int diameter_start = y - radius;
                if (diameter_start < Rasterizer2D_yClipStart) {
                    diameter_start = Rasterizer2D_yClipStart;
                }
                int diameter_end = y + radius + 1;
                if (diameter_end > Rasterizer2D_yClipEnd) {
                    diameter_end = Rasterizer2D_yClipEnd;
                }
                int i_26_ = diameter_start;
                final int i_27_ = radius * radius;
                int i_28_ = 0;
                int i_29_ = y - i_26_;
                int i_30_ = i_29_ * i_29_;
                int i_31_ = i_30_ - i_29_;
                if (y > diameter_end) {
                    y = diameter_end;
                }
                while (i_26_ < y) {
                    for (/**/; i_31_ <= i_27_ || i_30_ <= i_27_; i_31_ += i_28_++ + i_28_) {
                        i_30_ += i_28_ + i_28_;
                    }
                    int i_32_ = x - i_28_ + 1;
                    if (i_32_ < Rasterizer2D_xClipStart) {
                        i_32_ = Rasterizer2D_xClipStart;
                    }
                    int i_33_ = x + i_28_;
                    if (i_33_ > Rasterizer2D_xClipEnd) {
                        i_33_ = Rasterizer2D_xClipEnd;
                    }
                    int coordinates = i_32_ + i_26_ * Rasterizer2D_width;
                    for (int i_35_ = i_32_; i_35_ < i_33_; i_35_++) {
                        final int dest_red = (Rasterizer2D_pixels[coordinates] >> 16 & 0xff) * opacity;
                        final int dest_green = (Rasterizer2D_pixels[coordinates] >> 8 & 0xff) * opacity;
                        final int dest_blue = (Rasterizer2D_pixels[coordinates] & 0xff) * opacity;
                        final int dest_color = (source_red + dest_red >> 8 << 16) + (source_green + dest_green >> 8 << 8) + (source_blue + dest_blue >> 8);
                        Rasterizer2D_pixels[coordinates++] = dest_color;
                    }
                    i_26_++;
                    i_30_ -= i_29_-- + i_29_;
                    i_31_ -= i_29_ + i_29_;
                }
                i_28_ = radius;
                i_29_ = -i_29_;
                i_31_ = i_29_ * i_29_ + i_27_;
                i_30_ = i_31_ - i_28_;
                i_31_ -= i_29_;
                while (i_26_ < diameter_end) {
                    for (/**/; i_31_ > i_27_ && i_30_ > i_27_; i_30_ -= i_28_ + i_28_) {
                        i_31_ -= i_28_-- + i_28_;
                    }
                    int i_40_ = x - i_28_;
                    if (i_40_ < Rasterizer2D_xClipStart) {
                        i_40_ = Rasterizer2D_xClipStart;
                    }
                    int i_41_ = x + i_28_;
                    if (i_41_ > Rasterizer2D_xClipEnd - 1) {
                        i_41_ = Rasterizer2D_xClipEnd - 1;
                    }
                    int coordinates = i_40_ + i_26_ * Rasterizer2D_width;
                    for (int i_43_ = i_40_; i_43_ <= i_41_; i_43_++) {
                        final int i_44_ = (Rasterizer2D_pixels[coordinates] >> 16 & 0xff) * opacity;
                        final int i_45_ = (Rasterizer2D_pixels[coordinates] >> 8 & 0xff) * opacity;
                        final int i_46_ = (Rasterizer2D_pixels[coordinates] & 0xff) * opacity;
                        final int i_47_ = (source_red + i_44_ >> 8 << 16) + (source_green + i_45_ >> 8 << 8) + (source_blue + i_46_ >> 8);
                        Rasterizer2D_pixels[coordinates++] = i_47_;
                    }
                    i_26_++;
                    i_31_ += i_29_ + i_29_;
                    i_30_ += i_29_++ + i_29_;
                }
            }
        }
    }

    private static final void setPixel(final int x, final int y, final int color) {
        if (x >= Rasterizer2D_xClipStart && y >= Rasterizer2D_yClipStart && x < Rasterizer2D_xClipEnd && y < Rasterizer2D_yClipEnd) {
            Rasterizer2D_pixels[x + y * Rasterizer2D_width] = color;
        }
    }

    private static final void drawCircle(final int x, int y, int radius, final int color) {
        if (radius == 0) {
            setPixel(x, y, color);
        } else {
            if (radius < 0) {
                radius = -radius;
            }
            int i_67_ = y - radius;
            if (i_67_ < Rasterizer2D_yClipStart) {
                i_67_ = Rasterizer2D_yClipStart;
            }
            int i_68_ = y + radius + 1;
            if (i_68_ > Rasterizer2D_yClipEnd) {
                i_68_ = Rasterizer2D_yClipEnd;
            }
            int i_69_ = i_67_;
            final int i_70_ = radius * radius;
            int i_71_ = 0;
            int i_72_ = y - i_69_;
            int i_73_ = i_72_ * i_72_;
            int i_74_ = i_73_ - i_72_;
            if (y > i_68_) {
                y = i_68_;
            }
            while (i_69_ < y) {
                for (/**/; i_74_ <= i_70_ || i_73_ <= i_70_; i_74_ += i_71_++ + i_71_) {
                    i_73_ += i_71_ + i_71_;
                }
                int i_75_ = x - i_71_ + 1;
                if (i_75_ < Rasterizer2D_xClipStart) {
                    i_75_ = Rasterizer2D_xClipStart;
                }
                int i_76_ = x + i_71_;
                if (i_76_ > Rasterizer2D_xClipEnd) {
                    i_76_ = Rasterizer2D_xClipEnd;
                }
                int i_77_ = i_75_ + i_69_ * Rasterizer2D_width;
                for (int i_78_ = i_75_; i_78_ < i_76_; i_78_++) {
                    Rasterizer2D_pixels[i_77_++] = color;
                }
                i_69_++;
                i_73_ -= i_72_-- + i_72_;
                i_74_ -= i_72_ + i_72_;
            }
            i_71_ = radius;
            i_72_ = i_69_ - y;
            i_74_ = i_72_ * i_72_ + i_70_;
            i_73_ = i_74_ - i_71_;
            i_74_ -= i_72_;
            while (i_69_ < i_68_) {
                for (/**/; i_74_ > i_70_ && i_73_ > i_70_; i_73_ -= i_71_ + i_71_) {
                    i_74_ -= i_71_-- + i_71_;
                }
                int i_79_ = x - i_71_;
                if (i_79_ < Rasterizer2D_xClipStart) {
                    i_79_ = Rasterizer2D_xClipStart;
                }
                int i_80_ = x + i_71_;
                if (i_80_ > Rasterizer2D_xClipEnd - 1) {
                    i_80_ = Rasterizer2D_xClipEnd - 1;
                }
                int i_81_ = i_79_ + i_69_ * Rasterizer2D_width;
                for (int i_82_ = i_79_; i_82_ <= i_80_; i_82_++) {
                    drawAlpha(Rasterizer2D_pixels,i_81_++,color,255);
                }
                i_69_++;
                i_74_ += i_72_ + i_72_;
                i_73_ += i_72_++ + i_72_;
            }
        }
    }

    public static void draw_rectangle_outline(int x, int y, int line_width, int line_height, int color) {
        draw_vertical_line1(x, y, line_width, color);
        draw_vertical_line1(x, line_height + y - 1, line_width, color);
        draw_horizontal_line1(x, y, line_height, color);
        draw_horizontal_line1(x + line_width - 1, y, line_height, color);
    }

    public static void draw_vertical_line1(int x, int y, int line_width, int color) {
        if (y >= Rasterizer2D_yClipStart && y < Rasterizer2D_yClipEnd) {
            if (x < Rasterizer2D_xClipStart) {
                line_width -= Rasterizer2D_xClipStart - x;
                x = Rasterizer2D_xClipStart;
            }
            if (x + line_width > Rasterizer2D_xClipEnd) {
                line_width = Rasterizer2D_xClipEnd - x;
            }
            int coordinates = x + Rasterizer2D_width * y;
            for (int step = 0; step < line_width; step++) {
                drawAlpha(Rasterizer2D_pixels,coordinates + step,color,255);
            }
        }
    }

    public static void draw_horizontal_line1(int x, int y, int line_height, int color) {
        if (x >= Rasterizer2D_xClipStart && x < Rasterizer2D_xClipEnd) {
            if (y < Rasterizer2D_yClipStart) {
                line_height -= Rasterizer2D_yClipStart - y;
                y = Rasterizer2D_yClipStart;
            }
            if (line_height + y > Rasterizer2D_yClipEnd) {
                line_height = Rasterizer2D_yClipEnd - y;
            }
            int coordinates = x + Rasterizer2D_width * y;
            for (int step = 0; step < line_height; step++) {
                drawAlpha(Rasterizer2D_pixels,coordinates + step * Rasterizer2D_width,color,255);
            }
        }
    }

    public static void draw_line(int x, int y, int line_width, int line_height, int color) {
        line_width -= x;
        line_height -= y;
        if (line_height == 0) {//check for straight lines
            if (line_width >= 0) {
                draw_vertical_line1(x, y, line_width + 1, color);
            } else {
                draw_vertical_line1(x + line_width, y, -line_width + 1, color);
            }

        } else if (line_width == 0) {//check for straight lines
            if (line_height >= 0) {
                draw_horizontal_line1(x, y, line_height + 1, color);
            } else {
                draw_horizontal_line1(x, line_height + y, -line_height + 1, color);
            }
        } else {
            //bresenham algorithm?
            if (line_height + line_width < 0) {
                x += line_width;
                line_width = -line_width;
                y += line_height;
                line_height = -line_height;
            }
            int height_step;
            int width_step;
            if (line_width > line_height) {
                y <<= 16;
                y += 16384;
                line_height <<= 16;
                height_step = (int) Math.floor((double) line_height / (double) line_width + 0.5D);
                line_width += x;
                if (x < Rasterizer2D_xClipStart) {
                    y += height_step * (Rasterizer2D_xClipStart - x);
                    x = Rasterizer2D_xClipStart;
                }
                if (line_width >= Rasterizer2D_xClipEnd) {
                    line_width = Rasterizer2D_xClipEnd - 1;
                }
                while (x <= line_width) {
                    width_step = y >> 16;
                    if (width_step >= Rasterizer2D_yClipStart && width_step < Rasterizer2D_yClipEnd) {
                        drawAlpha(Rasterizer2D_pixels,x + width_step * Rasterizer2D_width,color,255);
                    }
                    y += height_step;
                    x++;
                }
            } else {
                x <<= 16;
                x += 16384;
                line_width <<= 16;
                height_step = (int) Math.floor((double) line_width / (double) line_height + 0.5D);
                line_height += y;
                if (y < Rasterizer2D_yClipStart) {
                    x += (Rasterizer2D_yClipStart - y) * height_step;
                    y = Rasterizer2D_yClipStart;
                }
                if (line_height >= Rasterizer2D_yClipEnd) {
                    line_height = Rasterizer2D_yClipEnd - 1;
                }
                while (y <= line_height) {
                    width_step = x >> 16;
                    if (width_step >= Rasterizer2D_xClipStart && width_step < Rasterizer2D_xClipEnd) {
                        drawAlpha(Rasterizer2D_pixels,width_step * Rasterizer2D_width,color,255);
                    }
                    x += height_step;
                    y++;
                }
            }
        }
    }

    private static final ColorModel COLOR_MODEL = new DirectColorModel(32, 0xff0000, 0xff00, 0xff);

    public static int[] Rasterizer2D_pixels;
    public static int Rasterizer2D_width;
    public static int Rasterizer2D_height;
    public static int Rasterizer2D_yClipStart;
    public static int Rasterizer2D_yClipEnd;
    public static int Rasterizer2D_xClipStart;
    public static int Rasterizer2D_xClipEnd;

    public static float[] Rasterizer2D_brightness;

    static {
        Rasterizer2D_yClipStart = 0;
        Rasterizer2D_yClipEnd = 0;
        Rasterizer2D_xClipStart = 0;
        Rasterizer2D_xClipEnd = 0;
    }

    public static void drawBox(int leftX, int topY, int width, int height, int rgbColour) {
        if (leftX < Rasterizer2D.Rasterizer2D_xClipStart) {
            width -= Rasterizer2D.Rasterizer2D_xClipStart - leftX;
            leftX = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if (topY < Rasterizer2D.Rasterizer2D_yClipStart) {
            height -= Rasterizer2D.Rasterizer2D_yClipStart - topY;
            topY = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if (leftX + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - leftX;
        if (topY + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - topY;
        int leftOver = Rasterizer2D.Rasterizer2D_width - width;
        int pixelIndex = leftX + topY * Rasterizer2D.Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++)
                drawAlpha(Rasterizer2D_pixels, pixelIndex++, rgbColour, 255);
            pixelIndex += leftOver;
        }
    }


    public static void transparentBox(int i, int j, int k, int l, int i1, int opac) {
        int j3 = 256 - opac;
        if (k < Rasterizer2D.Rasterizer2D_xClipStart) {
            i1 -= Rasterizer2D.Rasterizer2D_xClipStart - k;
            k = Rasterizer2D.Rasterizer2D_xClipStart;
        }

        if (j < Rasterizer2D_yClipStart) {
            i -= Rasterizer2D_yClipStart - j;
            j = Rasterizer2D_yClipStart;
        }

        if (k + i1 > Rasterizer2D_xClipEnd) {
            i1 = Rasterizer2D_xClipEnd - k;
        }

        if (j + i > Rasterizer2D_yClipEnd) {
            i = Rasterizer2D_yClipEnd - j;
        }

        int k1 = Rasterizer2D_width - i1;
        int l1 = k + j * Rasterizer2D_width;

        for(int i2 = -i; i2 < 0; ++i2) {
            for(int j2 = -i1; j2 < 0; ++j2) {
                int i3 = Rasterizer2D_pixels[l1];
                Rasterizer2D_pixels[l1++] = ((l & 16711935) * opac + (i3 & 16711935) * j3 & -16711936) + ((l & '\uff00') * opac + (i3 & '\uff00') * j3 & 16711680) >> 8;
            }

            l1 += k1;
        }
    }

    public static void fillPixels(int i, int j, int k, int l, int i1) {
        method339(i1, l, j, i);
        method339((i1 + k) - 1, l, j, i);
        method341(i1, l, k, i);
        method341(i1, l, k, (i + j) - 1);
    }

    public static void method339(int i, int j, int k, int l) {
        if (i < Rasterizer2D_yClipStart || i >= Rasterizer2D_yClipEnd)
            return;
        if (l < Rasterizer2D_xClipStart) {
            k -= Rasterizer2D_xClipStart - l;
            l = Rasterizer2D_xClipStart;
        }
        if (l + k > Rasterizer2D_xClipEnd)
            k = Rasterizer2D_xClipEnd - l;
        int i1 = l + i * Rasterizer2D_width;
        for (int j1 = 0; j1 < k; j1++)
            drawAlpha(Rasterizer2D_pixels, i1 + j1, j, 255);

    }

    public static void method341(int i, int j, int k, int l) {
        if (l < Rasterizer2D_xClipStart || l >= Rasterizer2D_xClipEnd)
            return;
        if (i < Rasterizer2D_yClipStart) {
            k -= Rasterizer2D_yClipStart - i;
            i = Rasterizer2D_yClipStart;
        }
        if (i + k > Rasterizer2D_yClipEnd)
            k = Rasterizer2D_yClipEnd - i;
        int j1 = l + i * Rasterizer2D_width;
        for (int k1 = 0; k1 < k; k1++)
            drawAlpha(Rasterizer2D_pixels,j1 + k1 * Rasterizer2D_width,j,255);

    }


    public static void drawBox(int x, int y, int width, int height, int border, int borderColor, int color, int alpha) {
        drawHorizontalLine(x + 1, y, width, color, alpha);
        drawHorizontalLine(x, y + height - 2, width, color, alpha);

        drawAlphaVerticalLine(x, y, height - 2, color, alpha);
        drawAlphaVerticalLine(x + width, y + 1, height - 2, color, alpha);

        for (int i = 1; i < border; i++) {
            drawHorizontalLine(x + 1, y + i, width - 1, borderColor, alpha);
            drawHorizontalLine(x + border, y + height - i - 2, width - border * 2 + 1, borderColor, alpha);

            drawAlphaVerticalLine(x + i, y + border, height - border - 2, borderColor, alpha);
            drawAlphaVerticalLine(x + width - border + i, y + border, height - border - 2, borderColor, alpha);
        }
    }

    public static void drawHorizontalLine2(int xPosition, int yPosition, int width, int rgbColour) {
        if (yPosition < Rasterizer2D_yClipStart || yPosition >= Rasterizer2D_yClipEnd)
            return;
        if (xPosition < Rasterizer2D_xClipStart) {
            width -= Rasterizer2D_xClipStart - xPosition;
            xPosition = Rasterizer2D_xClipStart;
        }
        if (xPosition + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - xPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D.Rasterizer2D_width;
        for (int i = 0; i < width; i++)
            drawAlpha(Rasterizer2D_pixels, pixelIndex + i, rgbColour, 255);

    }
    public static void drawVerticalLine2(int xPosition, int yPosition, int height, int rgbColour) {
        if (xPosition < Rasterizer2D_xClipStart || xPosition >= Rasterizer2D_xClipEnd)
            return;
        if (yPosition < Rasterizer2D_yClipStart) {
            height -= Rasterizer2D_yClipStart - yPosition;
            yPosition = Rasterizer2D_yClipStart;
        }
        if (yPosition + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - yPosition;
        int pixelIndex = xPosition + yPosition * Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++)
            drawAlpha(Rasterizer2D_pixels, pixelIndex + rowIndex * Rasterizer2D_width, rgbColour, 255);
    }

    public static void drawTransparentBox(int leftX, int topY, int width, int height, int rgbColour, int opacity) {
        if (leftX < Rasterizer2D.Rasterizer2D_xClipStart) {
            width -= Rasterizer2D.Rasterizer2D_xClipStart - leftX;
            leftX = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if (topY < Rasterizer2D.Rasterizer2D_yClipStart) {
            height -= Rasterizer2D.Rasterizer2D_yClipStart - topY;
            topY = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if (leftX + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - leftX;
        if (topY + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - topY;
        int transparency = 256 - opacity;
        int red = (rgbColour >> 16 & 0xff) * opacity;
        int green = (rgbColour >> 8 & 0xff) * opacity;
        int blue = (rgbColour & 0xff) * opacity;
        int leftOver = Rasterizer2D.Rasterizer2D_width - width;
        int pixelIndex = leftX + topY * Rasterizer2D.Rasterizer2D_width;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                int otherRed = (Rasterizer2D_pixels[pixelIndex] >> 16 & 0xff) * transparency;
                int otherGreen = (Rasterizer2D_pixels[pixelIndex] >> 8 & 0xff) * transparency;
                int otherBlue = (Rasterizer2D_pixels[pixelIndex] & 0xff) * transparency;
                int transparentColour = ((red + otherRed >> 8) << 16) + ((green + otherGreen >> 8) << 8) + (blue + otherBlue >> 8);
                drawAlpha(Rasterizer2D_pixels, pixelIndex++, transparentColour, opacity);
            }
            pixelIndex += leftOver;
        }
    }

    public static void filterGrayscale(int x, int y, int width, int height, double amount) {
        if (amount <= 0) {
            return;
        }


        if(x < Rasterizer2D.Rasterizer2D_xClipStart) {
            x = Rasterizer2D.Rasterizer2D_xClipStart;
        }
        if(y < Rasterizer2D.Rasterizer2D_yClipStart) {
            y = Rasterizer2D.Rasterizer2D_yClipStart;
        }
        if(x + width > Rasterizer2D_xClipEnd)
            width = Rasterizer2D_xClipEnd - x;
        if(y + height > Rasterizer2D_yClipEnd)
            height = Rasterizer2D_yClipEnd - y;


        int pos = x + y * Rasterizer2D.Rasterizer2D_width;
        int offset = Rasterizer2D.Rasterizer2D_width - width;
        if (amount >= 1) {
            while (height-- > 0) {
                for (int i = 0; i < width; i++) {
                    int red = Rasterizer2D_pixels[pos] >> 16 & 0xff;
                    int green = Rasterizer2D_pixels[pos] >> 8 & 0xff;
                    int blue = Rasterizer2D_pixels[pos] & 0xff;
                    int lightness = (red + green + blue) / 3;
                    int color = lightness << 16 | lightness << 8 | lightness;
                    drawAlpha(Rasterizer2D_pixels, pos++, color, 255);
                }
                pos += offset;
            }
        } else {
            double divider = 2 * amount + 1;
            while (height-- > 0) {
                for (int i = 0; i < width; i++) {
                    int red = Rasterizer2D_pixels[pos] >> 16 & 0xff;
                    int green = Rasterizer2D_pixels[pos] >> 8 & 0xff;
                    int blue = Rasterizer2D_pixels[pos] & 0xff;
                    int red2 = (int) (red * amount);
                    int green2 = (int) (green * amount);
                    int blue2 = (int) (blue * amount);
                    red = (int) ((red + green2 + blue2) / divider);
                    green = (int) ((red2 + green + blue2) / divider);
                    blue = (int) ((red2 + green2 + blue) / divider);
                    int color = red << 16 | green << 8 | blue;
                    drawAlpha(Rasterizer2D_pixels, pos++, color, 255);
                }
                pos += offset;
            }
        }
    }

    public static void drawBoxOutline(int leftX, int topY, int width, int height, int rgbColour) {
        drawHorizontalLine2(leftX, topY, width, rgbColour);
        drawHorizontalLine2(leftX, (topY + height) - 1, width, rgbColour);
        drawVerticalLine2(leftX, topY, height, rgbColour);
        drawVerticalLine2((leftX + width) - 1, topY, height, rgbColour);
    }


    public void drawAlphaGradientOnSprite(Sprite sprite, int x, int y, int gradientWidth,
                                          int gradientHeight, int startColor, int endColor, int alpha) {
        int k1 = 0;
        int l1 = 0x10000 / gradientHeight;
        if (x < Rasterizer2D_xClipStart) {
            gradientWidth -= Rasterizer2D_xClipStart - x;
            x = Rasterizer2D_xClipStart;
        }
        if (y < Rasterizer2D_yClipStart) {
            k1 += (Rasterizer2D_yClipStart - y) * l1;
            gradientHeight -= Rasterizer2D_yClipStart - y;
            y = Rasterizer2D_yClipStart;
        }
        if (x + gradientWidth > Rasterizer2D_xClipEnd)
            gradientWidth = Rasterizer2D_xClipEnd - x;
        if (y + gradientHeight > Rasterizer2D_yClipEnd)
            gradientHeight = Rasterizer2D_yClipEnd - y;
        int i2 = Rasterizer2D_width - gradientWidth;
        int result_alpha = 256 - alpha;
        int total_pixels = x + y * Rasterizer2D_width;
        for (int k2 = -gradientHeight; k2 < 0; k2++) {
            int gradient1 = 0x10000 - k1 >> 8;
            int gradient2 = k1 >> 8;
            int gradient_color = ((startColor & 0xff00ff) * gradient1
                    + (endColor & 0xff00ff) * gradient2 & 0xff00ff00)
                    + ((startColor & 0xff00) * gradient1 + (endColor & 0xff00)
                    * gradient2 & 0xff0000) >>> 8;
            int color = ((gradient_color & 0xff00ff) * alpha >> 8 & 0xff00ff)
                    + ((gradient_color & 0xff00) * alpha >> 8 & 0xff00);
            for (int k3 = -gradientWidth; k3 < 0; k3++) {
                int colored_pixel = Rasterizer2D_pixels[total_pixels];
                colored_pixel = ((colored_pixel & 0xff00ff) * result_alpha >> 8 & 0xff00ff)
                        + ((colored_pixel & 0xff00) * result_alpha >> 8 & 0xff00);
                drawAlpha(Rasterizer2D_pixels, total_pixels++, colored_pixel, alpha);
            }
            total_pixels += i2;
            k1 += l1;
        }
    }

    public static void method338(int i, int j, int k, int l, int i1, int j1) {
        method340(l, i1, i, k, j1);
        method340(l, i1, (i + j) - 1, k, j1);
        if (j >= 3) {
            method342(l, j1, k, i + 1, j - 2);
            method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
        }
    }

    private static void method340(int i, int j, int k, int l, int i1) {
        if (k < Rasterizer2D_yClipStart || k >= Rasterizer2D_yClipEnd)
            return;
        if (i1 < Rasterizer2D_xClipStart) {
            j -= Rasterizer2D_xClipStart - i1;
            i1 = Rasterizer2D_xClipStart;
        }
        if (i1 + j > Rasterizer2D_xClipEnd)
            j = Rasterizer2D_xClipEnd - i1;
        int j1 = 256 - l;
        int k1 = (i >> 16 & 0xff) * l;
        int l1 = (i >> 8 & 0xff) * l;
        int i2 = (i & 0xff) * l;
        int i3 = i1 + k * Rasterizer2D_width;
        for (int j3 = 0; j3 < j; j3++) {
            int j2 = (Rasterizer2D_pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (Rasterizer2D_pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (Rasterizer2D_pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
            drawAlpha(Rasterizer2D_pixels, i3++, k3, 255);
        }

    }

    private static void method342(int i, int j, int k, int l, int i1) {
        if (j < Rasterizer2D_xClipStart || j >= Rasterizer2D_xClipEnd)
            return;
        if (l < Rasterizer2D_yClipStart) {
            i1 -= Rasterizer2D_yClipStart - l;
            l = Rasterizer2D_yClipStart;
        }
        if (l + i1 > Rasterizer2D_yClipEnd)
            i1 = Rasterizer2D_yClipEnd - l;
        int j1 = 256 - k;
        int k1 = (i >> 16 & 0xff) * k;
        int l1 = (i >> 8 & 0xff) * k;
        int i2 = (i & 0xff) * k;
        int i3 = j + l * Rasterizer2D_width;
        for (int j3 = 0; j3 < i1; j3++) {
            int j2 = (Rasterizer2D_pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (Rasterizer2D_pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (Rasterizer2D_pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
            drawAlpha(Rasterizer2D_pixels, i3, k3, 255);
            i3 += Rasterizer2D_width;
        }
    }

    public static void renderGlow(int drawX, int drawY, int glowColor, int r) {
        // center
        drawX += r / 2;
        drawY += r / 2;

        int startX = drawX - r;
        int endX = drawX + r;
        int startY = drawY - r;
        int endY = drawY + r;

        // clipping
        if (startX < Rasterizer2D_xClipStart) {
            startX = Rasterizer2D_xClipStart;
        }

        if (endX > Rasterizer2D_xClipEnd) {
            endX = Rasterizer2D_xClipEnd;
        }

        if (startY < Rasterizer2D_yClipStart) {
            startY = Rasterizer2D_yClipStart;
        }

        if (endY > Rasterizer2D_yClipEnd) {
            endY = Rasterizer2D_yClipEnd;
        }

        float edge0 = -(r / 2f);
        float edge1 = MathUtils.map((float) Math.sin(Client.loopCycle / 20f), -1, 1, edge0 + (r / 1.35f), r);
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) { // what did i have to get working again, texture animation? Yeah uhh, new boxes textures 96>100 not aniamting
                int index = x + y * Rasterizer2D_width;
                float d = MathUtils.dist(x, y, drawX, drawY);
                float dist = MathUtils.smoothstep(edge0, edge1, d);
                int oldColor = Rasterizer2D_pixels[index];
                int newColor = blend(oldColor, glowColor, 1f - dist);
                drawAlpha(Rasterizer2D_pixels, index, newColor, r);
            }
        }
    }

    public static int blend(int rgb1, int rgb2, float factor) {
        if (factor >= 1f) {
            return rgb2;
        }
        if (factor <= 0f) {
            return rgb1;
        }

        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = (rgb1) & 0xff;

        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = (rgb2) & 0xff;

        int r3 = r2 - r1;
        int g3 = g2 - g1;
        int b3 = b2 - b1;

        int r = (int) (r1 + (r3 * factor));
        int g = (int) (g1 + (g3 * factor));
        int b = (int) (b1 + (b3 * factor));

        return (r << 16) + (g << 8) + b;
    }

    public static void drawBorder(int x, int y, int width, int height, int color) {
        Rasterizer2D.drawPixels(1, y, x, color, width);
        Rasterizer2D.drawPixels(height, y, x, color, 1);
        Rasterizer2D.drawPixels(1, y + height, x, color, width + 1);
        Rasterizer2D.drawPixels(height, y, x + width, color, 1);
    }


    public static void fillCircle(int x, int y, int radius, int color) {
        int y1 = y - radius;
        if (y1 < 0) {
            y1 = 0;
        }
        int y2 = y + radius;
        if (y2 >= Rasterizer2D_height) {
            y2 = Rasterizer2D_height - 1;
        }
        for (int iy = y1; iy <= y2; iy++) {
            int dy = iy - y;
            int dist = (int) Math.sqrt(radius * radius - dy * dy);
            int x1 = x - dist;
            if (x1 < 0) {
                x1 = 0;
            }
            int x2 = x + dist;
            if (x2 >= Rasterizer2D_width) {
                x2 = Rasterizer2D_width - 1;
            }
            int pos = x1 + iy * Rasterizer2D_width;
            for (int ix = x1; ix <= x2; ix++) {
                drawAlpha(Rasterizer2D_pixels, pos++, color, 255);
            }
        }
    }
    public static void drawPixelsWithOpacity2(int xPos, int yPos, int pixelWidth, int pixelHeight, int color, int opacityLevel) {
        drawPixelsWithOpacity(color, yPos, pixelWidth, pixelHeight, opacityLevel, xPos);
    }

    public static void drawPixelsWithOpacity(int color, int yPos, int pixelWidth, int pixelHeight, int opacityLevel,
                                             int xPos) {
        if (xPos < Rasterizer2D_xClipStart) {
            pixelWidth -= Rasterizer2D_xClipStart - xPos;
            xPos = Rasterizer2D_xClipStart;
        }
        if (yPos < Rasterizer2D_yClipStart) {
            pixelHeight -= Rasterizer2D_yClipStart - yPos;
            yPos = Rasterizer2D_yClipStart;
        }
        if (xPos + pixelWidth > Rasterizer2D_xClipEnd) {
            pixelWidth = Rasterizer2D_xClipEnd - xPos;
        }
        if (yPos + pixelHeight > Rasterizer2D_yClipEnd) {
            pixelHeight = Rasterizer2D_yClipEnd - yPos;
        }
        int l1 = 256 - opacityLevel;
        int i2 = (color >> 16 & 0xFF) * opacityLevel;
        int j2 = (color >> 8 & 0xFF) * opacityLevel;
        int k2 = (color & 0xFF) * opacityLevel;
        int k3 = Rasterizer2D_width - pixelWidth;
        int l3 = xPos + yPos * Rasterizer2D_width;
        if (l3 > Rasterizer2D_pixels.length - 1) {
            l3 = Rasterizer2D_pixels.length - 1;
        }
        for (int i4 = 0; i4 < pixelHeight; i4++) {
            for (int j4 = -pixelWidth; j4 < 0; j4++) {
                int l2 = (Rasterizer2D_pixels[l3] >> 16 & 0xFF) * l1;
                int i3 = (Rasterizer2D_pixels[l3] >> 8 & 0xFF) * l1;
                int j3 = (Rasterizer2D_pixels[l3] & 0xFF) * l1;
                int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
                drawAlpha(Rasterizer2D_pixels, l3++, k4, opacityLevel);
            }
            l3 += k3;
        }
    }

    static boolean method5376() {
        return Rasterizer3D.currentRasterizer.vmethod6185();
    }

}
