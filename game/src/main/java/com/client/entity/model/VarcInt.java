package com.client.entity.model;

public class VarcInt {

    static final boolean method4357(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = ViewportMouse.ViewportMouse_y + var6;
        if (var7 < var0 && var7 < var1 && var7 < var2) {
            return false;
        } else {
            var7 = ViewportMouse.ViewportMouse_y - var6;
            if (var7 > var0 && var7 > var1 && var7 > var2) {
                return false;
            } else {
                var7 = ViewportMouse.ViewportMouse_x + var6;
                if (var7 < var3 && var7 < var4 && var7 < var5) {
                    return false;
                } else {
                    var7 = ViewportMouse.ViewportMouse_x - var6;
                    return var7 <= var3 || var7 <= var4 || var7 <= var5;
                }
            }
        }
    }
}
