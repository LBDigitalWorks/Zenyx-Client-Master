package com.client;

public enum class343 implements Enum {
    field3891(0, 0),
    field3883(1, 1),
    field3884(2, 2),
    field3885(3, 3),
    field3888(4, 4),
    field3887(5, 5),
    field3905(6, 6),
    field3889(7, 7),
    field3890(8, 8),
    field3904(9, 9),
    field3912(10, 10),
    field3893(11, 11),
    field3894(12, 12),
    field3892(13, 13),
    field3899(14, 14),
    field3897(15, 15),
    field3895(16, 16),
    field3886(17, 17),
    field3900(18, 18),
    field3901(19, 19),
    field3902(20, 20),
    field3882(21, 21),
    field3896(22, 22),
    field3903(23, 23),
    field3906(24, 24),
    field3907(25, 25),
    field3908(26, 26),
    field3909(27, 27);

    static final class343[] field3911;
    final int field3910;
    final int field3898;

    static {
        class343[] var0 = class288.method6546();
        field3911 = new class343[var0.length];
        class343[] var1 = var0;

        for (int var2 = 0; var2 < var1.length; ++var2) {
            class343 var3 = var1[var2];
            field3911[var3.field3898] = var3;
        }

    }

    class343(int var3, int var4) {
        this.field3910 = var3;
        this.field3898 = var4;
    }
    public int rsOrdinal() {
        return this.field3898;
    }
}
