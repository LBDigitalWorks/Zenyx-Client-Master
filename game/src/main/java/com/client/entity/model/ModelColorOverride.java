package com.client.entity.model;

public class ModelColorOverride {
    public byte overrideHue;
    public byte overrideSaturation;
    public byte overrideLuminance;
    public byte overrideAmount;

    public ModelColorOverride() {
        this.overrideHue = -1;
        this.overrideSaturation = -1;
        this.overrideLuminance = -1;
        this.overrideAmount = 0;
    }

    public ModelColorOverride(byte var1, byte var2, byte var3, byte var4) {
        this.overrideHue = -1;
        this.overrideSaturation = -1;
        this.overrideLuminance = -1;
        this.overrideAmount = 0;
        this.overrideHue = var1;
        this.overrideSaturation = var2;
        this.overrideLuminance = var3;
        this.overrideAmount = var4;
    }

    void method6119(ModelColorOverride var1) {
        this.overrideHue = var1.overrideHue;
        this.overrideSaturation = var1.overrideSaturation;
        this.overrideLuminance = var1.overrideLuminance;
        this.overrideAmount = var1.overrideAmount;
    }

    public void method6123(byte var1, byte var2, byte var3, byte var4) {
        this.overrideHue = var1;
        this.overrideSaturation = var2;
        this.overrideLuminance = var3;
        this.overrideAmount = var4;
    }

    public void method6118() {
        this.overrideHue = -1;
        this.overrideSaturation = -1;
        this.overrideLuminance = -1;
        this.overrideAmount = 0;
    }

    public boolean method6121() {
        return this.overrideAmount > 0;
    }
}
