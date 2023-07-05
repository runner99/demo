package org.objectweb.asm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: asm-5.1.jar:org/objectweb/asm/Item.class */
public final class Item {
    int a;
    int b;
    int c;
    long d;
    String g;
    String h;
    String i;
    int j;
    Item k;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item(int i) {
        this.a = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item(int i, Item item) {
        this.a = i;
        this.b = item.b;
        this.c = item.c;
        this.d = item.d;
        this.g = item.g;
        this.h = item.h;
        this.i = item.i;
        this.j = item.j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        this.b = 3;
        this.c = i;
        this.j = Integer.MAX_VALUE & (this.b + i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(long j) {
        this.b = 5;
        this.d = j;
        this.j = Integer.MAX_VALUE & (this.b + ((int) j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f) {
        this.b = 4;
        this.c = Float.floatToRawIntBits(f);
        this.j = Integer.MAX_VALUE & (this.b + ((int) f));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(double d) {
        this.b = 6;
        this.d = Double.doubleToRawLongBits(d);
        this.j = Integer.MAX_VALUE & (this.b + ((int) d));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void a(int i, String str, String str2, String str3) {
        this.b = i;
        this.g = str;
        this.h = str2;
        this.i = str3;
        switch (i) {
            case 1:
            case 8:
            case 16:
            case 30:
                break;
            case 7:
                this.c = 0;
                break;
            case Opcodes.FCONST_1 /* 12 */:
                this.j = Integer.MAX_VALUE & (i + (str.hashCode() * str2.hashCode()));
                return;
            default:
                this.j = Integer.MAX_VALUE & (i + (str.hashCode() * str2.hashCode() * str3.hashCode()));
                return;
        }
        this.j = Integer.MAX_VALUE & (i + str.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str, String str2, int i) {
        this.b = 18;
        this.d = (long) i;
        this.g = str;
        this.h = str2;
        this.j = Integer.MAX_VALUE & (18 + (i * this.g.hashCode() * this.h.hashCode()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, int i2) {
        this.b = 33;
        this.c = i;
        this.j = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(Item item) {
        switch (this.b) {
            case 1:
            case 7:
            case 8:
            case 16:
            case 30:
                return item.g.equals(this.g);
            case 2:
            case 9:
            case 10:
            case 11:
            case Opcodes.FCONST_2 /* 13 */:
            case Opcodes.DCONST_0 /* 14 */:
            case Opcodes.DCONST_1 /* 15 */:
            case 17:
            case TypeReference.FIELD /* 19 */:
            case TypeReference.METHOD_RETURN /* 20 */:
            case 21:
            case 22:
            case 23:
            case Opcodes.DLOAD /* 24 */:
            case Opcodes.ALOAD /* 25 */:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
                return item.g.equals(this.g) && item.h.equals(this.h) && item.i.equals(this.i);
            case 3:
            case 4:
                return item.c == this.c;
            case 5:
            case 6:
            case 32:
                return item.d == this.d;
            case Opcodes.FCONST_1 /* 12 */:
                return item.g.equals(this.g) && item.h.equals(this.h);
            case 18:
                return item.d == this.d && item.g.equals(this.g) && item.h.equals(this.h);
            case 31:
                return item.c == this.c && item.g.equals(this.g);
        }
    }
}
