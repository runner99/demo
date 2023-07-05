package com.esotericsoftware.asm;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/Label.class */
public class Label {
    public Object info;
    int a;
    int b;
    int c;
    private int d;
    private int[] e;
    int f;
    int g;
    Frame h;
    Label i;
    Edge j;
    Label k;

    public int getOffset() {
        if ((this.a & 2) != 0) {
            return this.c;
        }
        throw new IllegalStateException("Label offset position has not been resolved yet");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(MethodWriter methodWriter, ByteVector byteVector, int i, boolean z) {
        if ((this.a & 2) == 0) {
            if (z) {
                a(-1 - i, byteVector.b);
                byteVector.putInt(-1);
                return;
            }
            a(i, byteVector.b);
            byteVector.putShort(-1);
        } else if (z) {
            byteVector.putInt(this.c - i);
        } else {
            byteVector.putShort(this.c - i);
        }
    }

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[6];
        }
        if (this.d >= this.e.length) {
            int[] iArr = new int[this.e.length + 6];
            System.arraycopy(this.e, 0, iArr, 0, this.e.length);
            this.e = iArr;
        }
        int[] iArr2 = this.e;
        int i3 = this.d;
        this.d = i3 + 1;
        iArr2[i3] = i;
        int[] iArr3 = this.e;
        int i4 = this.d;
        this.d = i4 + 1;
        iArr3[i4] = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(MethodWriter methodWriter, int i, byte[] bArr) {
        boolean z = false;
        this.a |= 2;
        this.c = i;
        int i2 = 0;
        while (i2 < this.d) {
            int i3 = i2 + 1;
            int i4 = this.e[i2];
            i2 = i3 + 1;
            int i5 = this.e[i3];
            if (i4 >= 0) {
                int i6 = i - i4;
                if (i6 < -32768 || i6 > 32767) {
                    int i7 = bArr[i5 - 1] & 255;
                    if (i7 <= 168) {
                        bArr[i5 - 1] = (byte) (i7 + 49);
                    } else {
                        bArr[i5 - 1] = (byte) (i7 + 20);
                    }
                    z = true;
                }
                bArr[i5] = (byte) (i6 >>> 8);
                bArr[i5 + 1] = (byte) i6;
            } else {
                int i8 = i + i4 + 1;
                int i9 = i5 + 1;
                bArr[i5] = (byte) (i8 >>> 24);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (i8 >>> 16);
                bArr[i10] = (byte) (i8 >>> 8);
                bArr[i10 + 1] = (byte) i8;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Label a() {
        return this.h == null ? this : this.h.b;
    }

    boolean a(long j) {
        return ((this.a & Opcodes.ACC_ABSTRACT) == 0 || (this.e[(int) (j >>> 32)] & ((int) j)) == 0) ? false : true;
    }

    boolean a(Label label) {
        if ((this.a & Opcodes.ACC_ABSTRACT) == 0 || (label.a & Opcodes.ACC_ABSTRACT) == 0) {
            return false;
        }
        for (int i = 0; i < this.e.length; i++) {
            if ((this.e[i] & label.e[i]) != 0) {
                return true;
            }
        }
        return false;
    }

    void a(long j, int i) {
        if ((this.a & Opcodes.ACC_ABSTRACT) == 0) {
            this.a |= Opcodes.ACC_ABSTRACT;
            this.e = new int[(i / 32) + 1];
        }
        int[] iArr = this.e;
        int i2 = (int) (j >>> 32);
        iArr[i2] = iArr[i2] | ((int) j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(com.esotericsoftware.asm.Label r6, long r7, int r9) {
        /*
            r5 = this;
            r0 = r5
            r10 = r0
        L_0x0003:
            r0 = r10
            if (r0 == 0) goto L_0x00e3
            r0 = r10
            r11 = r0
            r0 = r11
            com.esotericsoftware.asm.Label r0 = r0.k
            r10 = r0
            r0 = r11
            r1 = 0
            r0.k = r1
            r0 = r6
            if (r0 == 0) goto L_0x0081
            r0 = r11
            int r0 = r0.a
            r1 = 2048(0x800, float:2.87E-42)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x002c
            goto L_0x0003
        L_0x002c:
            r0 = r11
            r1 = r0
            int r1 = r1.a
            r2 = 2048(0x800, float:2.87E-42)
            r1 = r1 | r2
            r0.a = r1
            r0 = r11
            int r0 = r0.a
            r1 = 256(0x100, float:3.59E-43)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0095
            r0 = r11
            r1 = r6
            boolean r0 = r0.a(r1)
            if (r0 != 0) goto L_0x0095
            com.esotericsoftware.asm.Edge r0 = new com.esotericsoftware.asm.Edge
            r1 = r0
            r1.<init>()
            r12 = r0
            r0 = r12
            r1 = r11
            int r1 = r1.f
            r0.a = r1
            r0 = r12
            r1 = r6
            com.esotericsoftware.asm.Edge r1 = r1.j
            com.esotericsoftware.asm.Label r1 = r1.b
            r0.b = r1
            r0 = r12
            r1 = r11
            com.esotericsoftware.asm.Edge r1 = r1.j
            r0.c = r1
            r0 = r11
            r1 = r12
            r0.j = r1
            goto L_0x0095
        L_0x0081:
            r0 = r11
            r1 = r7
            boolean r0 = r0.a(r1)
            if (r0 == 0) goto L_0x008d
            goto L_0x0003
        L_0x008d:
            r0 = r11
            r1 = r7
            r2 = r9
            r0.a(r1, r2)
        L_0x0095:
            r0 = r11
            com.esotericsoftware.asm.Edge r0 = r0.j
            r12 = r0
        L_0x009c:
            r0 = r12
            if (r0 == 0) goto L_0x00e0
            r0 = r11
            int r0 = r0.a
            r1 = 128(0x80, float:1.794E-43)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x00ba
            r0 = r12
            r1 = r11
            com.esotericsoftware.asm.Edge r1 = r1.j
            com.esotericsoftware.asm.Edge r1 = r1.c
            if (r0 == r1) goto L_0x00d6
        L_0x00ba:
            r0 = r12
            com.esotericsoftware.asm.Label r0 = r0.b
            com.esotericsoftware.asm.Label r0 = r0.k
            if (r0 != 0) goto L_0x00d6
            r0 = r12
            com.esotericsoftware.asm.Label r0 = r0.b
            r1 = r10
            r0.k = r1
            r0 = r12
            com.esotericsoftware.asm.Label r0 = r0.b
            r10 = r0
        L_0x00d6:
            r0 = r12
            com.esotericsoftware.asm.Edge r0 = r0.c
            r12 = r0
            goto L_0x009c
        L_0x00e0:
            goto L_0x0003
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.asm.Label.b(com.esotericsoftware.asm.Label, long, int):void");
    }

    public String toString() {
        return new StringBuffer().append("L").append(System.identityHashCode(this)).toString();
    }
}
