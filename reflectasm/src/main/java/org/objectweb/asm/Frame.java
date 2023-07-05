package org.objectweb.asm;

import org.objectweb.asm.signature.SignatureVisitor;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: asm-5.1.jar:org/objectweb/asm/Frame.class */
public final class Frame {
    static final int[] a;
    Label b;
    int[] c;
    int[] d;
    private int[] e;
    private int[] f;
    private int g;
    private int h;
    private int[] i;

    private int a(int i) {
        if (this.e == null || i >= this.e.length) {
            return 33554432 | i;
        }
        int i2 = this.e[i];
        if (i2 == 0) {
            int i3 = 33554432 | i;
            this.e[i] = i3;
            i2 = i3;
        }
        return i2;
    }

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[10];
        }
        int length = this.e.length;
        if (i >= length) {
            int[] iArr = new int[Math.max(i + 1, 2 * length)];
            System.arraycopy(this.e, 0, iArr, 0, length);
            this.e = iArr;
        }
        this.e[i] = i2;
    }

    private void b(int i) {
        if (this.f == null) {
            this.f = new int[10];
        }
        int length = this.f.length;
        if (this.g >= length) {
            int[] iArr = new int[Math.max(this.g + 1, 2 * length)];
            System.arraycopy(this.f, 0, iArr, 0, length);
            this.f = iArr;
        }
        int[] iArr2 = this.f;
        int i2 = this.g;
        this.g = i2 + 1;
        iArr2[i2] = i;
        int i3 = this.b.f + this.g;
        if (i3 > this.b.g) {
            this.b.g = i3;
        }
    }

    private void a(ClassWriter classWriter, String str) {
        int b = b(classWriter, str);
        if (b != 0) {
            b(b);
            if (b == 16777220 || b == 16777219) {
                b(16777216);
            }
        }
    }

    private static int b(ClassWriter classWriter, String str) {
        int i;
        int indexOf = str.charAt(0) == '(' ? str.indexOf(41) + 1 : 0;
        switch (str.charAt(indexOf)) {
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
            case TypeReference.INSTANCEOF /* 67 */:
            case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.DUP_X1 /* 90 */:
                return 16777217;
            case TypeReference.NEW /* 68 */:
                return 16777219;
            case TypeReference.CONSTRUCTOR_REFERENCE /* 69 */:
            case TypeReference.CAST /* 71 */:
            case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
            case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
            case 'M':
            case 'N':
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.DASTORE /* 82 */:
            case Opcodes.BASTORE /* 84 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.POP /* 87 */:
            case Opcodes.POP2 /* 88 */:
            case Opcodes.DUP /* 89 */:
            default:
                int i2 = indexOf + 1;
                while (str.charAt(i2) == '[') {
                    i2++;
                }
                switch (str.charAt(i2)) {
                    case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                        i = 16777226;
                        break;
                    case TypeReference.INSTANCEOF /* 67 */:
                        i = 16777227;
                        break;
                    case TypeReference.NEW /* 68 */:
                        i = 16777219;
                        break;
                    case TypeReference.CONSTRUCTOR_REFERENCE /* 69 */:
                    case TypeReference.CAST /* 71 */:
                    case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
                    case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
                    case 'L':
                    case 'M':
                    case 'N':
                    case Opcodes.IASTORE /* 79 */:
                    case Opcodes.LASTORE /* 80 */:
                    case Opcodes.FASTORE /* 81 */:
                    case Opcodes.DASTORE /* 82 */:
                    case Opcodes.BASTORE /* 84 */:
                    case Opcodes.CASTORE /* 85 */:
                    case Opcodes.SASTORE /* 86 */:
                    case Opcodes.POP /* 87 */:
                    case Opcodes.POP2 /* 88 */:
                    case Opcodes.DUP /* 89 */:
                    default:
                        i = 24117248 | classWriter.m1c(str.substring(i2 + 1, str.length() - 1));
                        break;
                    case TypeReference.METHOD_REFERENCE /* 70 */:
                        i = 16777218;
                        break;
                    case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
                        i = 16777217;
                        break;
                    case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
                        i = 16777220;
                        break;
                    case Opcodes.AASTORE /* 83 */:
                        i = 16777228;
                        break;
                    case Opcodes.DUP_X1 /* 90 */:
                        i = 16777225;
                        break;
                }
                return ((i2 - indexOf) << 28) | i;
            case TypeReference.METHOD_REFERENCE /* 70 */:
                return 16777218;
            case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
                return 16777220;
            case 'L':
                return 24117248 | classWriter.m1c(str.substring(indexOf + 1, str.length() - 1));
            case Opcodes.SASTORE /* 86 */:
                return 0;
        }
    }

    private int a() {
        if (this.g > 0) {
            int[] iArr = this.f;
            int i = this.g - 1;
            this.g = i;
            return iArr[i];
        }
        Label label = this.b;
        int i2 = label.f - 1;
        label.f = i2;
        return 50331648 | (-i2);
    }

    private void c(int i) {
        if (this.g >= i) {
            this.g -= i;
            return;
        }
        this.b.f -= i - this.g;
        this.g = 0;
    }

    private void a(String str) {
        char charAt = str.charAt(0);
        if (charAt == '(') {
            c((Type.getArgumentsAndReturnSizes(str) >> 2) - 1);
        } else if (charAt == 'J' || charAt == 'D') {
            c(2);
        } else {
            c(1);
        }
    }

    private void d(int i) {
        if (this.i == null) {
            this.i = new int[2];
        }
        int length = this.i.length;
        if (this.h >= length) {
            int[] iArr = new int[Math.max(this.h + 1, 2 * length)];
            System.arraycopy(this.i, 0, iArr, 0, length);
            this.i = iArr;
        }
        int[] iArr2 = this.i;
        int i2 = this.h;
        this.h = i2 + 1;
        iArr2[i2] = i;
    }

    private int a(ClassWriter classWriter, int i) {
        int i2;
        if (i == 16777222) {
            i2 = 24117248 | classWriter.m1c(classWriter.I);
        } else if ((i & -1048576) != 25165824) {
            return i;
        } else {
            i2 = 24117248 | classWriter.m1c(classWriter.H[i & 1048575].g);
        }
        for (int i3 = 0; i3 < this.h; i3++) {
            int i4 = this.i[i3];
            int i5 = i4 & -268435456;
            int i6 = i4 & 251658240;
            if (i6 == 33554432) {
                i4 = i5 + this.c[i4 & 8388607];
            } else if (i6 == 50331648) {
                i4 = i5 + this.d[this.d.length - (i4 & 8388607)];
            }
            if (i == i4) {
                return i2;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ClassWriter classWriter, int i, Type[] typeArr, int i2) {
        this.c = new int[i2];
        this.d = new int[0];
        int i3 = 0;
        if ((i & 8) == 0) {
            if ((i & 524288) == 0) {
                i3 = 0 + 1;
                this.c[0] = 24117248 | classWriter.m1c(classWriter.I);
            } else {
                i3 = 0 + 1;
                this.c[0] = 16777222;
            }
        }
        for (Type type : typeArr) {
            int b = b(classWriter, type.getDescriptor());
            i3++;
            this.c[i3] = b;
            if (b == 16777220 || b == 16777219) {
                i3++;
                this.c[i3] = 16777216;
            }
        }
        while (i3 < i2) {
            i3++;
            this.c[i3] = 16777216;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, int i2, ClassWriter classWriter, Item item) {
        switch (i) {
            case 0:
            case Opcodes.INEG /* 116 */:
            case Opcodes.LNEG /* 117 */:
            case Opcodes.FNEG /* 118 */:
            case Opcodes.DNEG /* 119 */:
            case Opcodes.I2B /* 145 */:
            case Opcodes.I2C /* 146 */:
            case Opcodes.I2S /* 147 */:
            case Opcodes.GOTO /* 167 */:
            case Opcodes.RETURN /* 177 */:
                return;
            case 1:
                b(16777221);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 16:
            case 17:
            case 21:
                b(16777217);
                return;
            case 9:
            case 10:
            case 22:
                b(16777220);
                b(16777216);
                return;
            case 11:
            case Opcodes.FCONST_1 /* 12 */:
            case Opcodes.FCONST_2 /* 13 */:
            case 23:
                b(16777218);
                return;
            case Opcodes.DCONST_0 /* 14 */:
            case Opcodes.DCONST_1 /* 15 */:
            case Opcodes.DLOAD /* 24 */:
                b(16777219);
                b(16777216);
                return;
            case 18:
                switch (item.b) {
                    case 3:
                        b(16777217);
                        return;
                    case 4:
                        b(16777218);
                        return;
                    case 5:
                        b(16777220);
                        b(16777216);
                        return;
                    case 6:
                        b(16777219);
                        b(16777216);
                        return;
                    case 7:
                        b(24117248 | classWriter.m1c("java/lang/Class"));
                        return;
                    case 8:
                        b(24117248 | classWriter.m1c("java/lang/String"));
                        return;
                    case 9:
                    case 10:
                    case 11:
                    case Opcodes.FCONST_1 /* 12 */:
                    case Opcodes.FCONST_2 /* 13 */:
                    case Opcodes.DCONST_0 /* 14 */:
                    case Opcodes.DCONST_1 /* 15 */:
                    default:
                        b(24117248 | classWriter.m1c("java/lang/invoke/MethodHandle"));
                        return;
                    case 16:
                        b(24117248 | classWriter.m1c("java/lang/invoke/MethodType"));
                        return;
                }
            case TypeReference.FIELD /* 19 */:
            case TypeReference.METHOD_RETURN /* 20 */:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case SignatureVisitor.EXTENDS /* 43 */:
            case 44:
            case SignatureVisitor.SUPER /* 45 */:
            case 59:
            case 60:
            case SignatureVisitor.INSTANCEOF /* 61 */:
            case 62:
            case 63:
            case 64:
            case TypeReference.RESOURCE_VARIABLE /* 65 */:
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
            case TypeReference.INSTANCEOF /* 67 */:
            case TypeReference.NEW /* 68 */:
            case TypeReference.CONSTRUCTOR_REFERENCE /* 69 */:
            case TypeReference.METHOD_REFERENCE /* 70 */:
            case TypeReference.CAST /* 71 */:
            case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
            case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
            case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
            case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
            case 76:
            case 77:
            case 78:
            case 196:
            case Opcodes.MULTIANEWARRAY /* 197 */:
            default:
                c(i2);
                a(classWriter, item.g);
                return;
            case Opcodes.ALOAD /* 25 */:
                b(a(i2));
                return;
            case 46:
            case 51:
            case 52:
            case Opcodes.SALOAD /* 53 */:
                c(2);
                b(16777217);
                return;
            case 47:
            case Opcodes.D2L /* 143 */:
                c(2);
                b(16777220);
                b(16777216);
                return;
            case 48:
                c(2);
                b(16777218);
                return;
            case 49:
            case Opcodes.L2D /* 138 */:
                c(2);
                b(16777219);
                b(16777216);
                return;
            case 50:
                c(1);
                b(-268435456 + a());
                return;
            case Opcodes.ISTORE /* 54 */:
            case Opcodes.FSTORE /* 56 */:
            case Opcodes.ASTORE /* 58 */:
                a(i2, a());
                if (i2 > 0) {
                    int a2 = a(i2 - 1);
                    if (a2 == 16777220 || a2 == 16777219) {
                        a(i2 - 1, 16777216);
                        return;
                    } else if ((a2 & 251658240) != 16777216) {
                        a(i2 - 1, a2 | 8388608);
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            case Opcodes.LSTORE /* 55 */:
            case Opcodes.DSTORE /* 57 */:
                c(1);
                a(i2, a());
                a(i2 + 1, 16777216);
                if (i2 > 0) {
                    int a3 = a(i2 - 1);
                    if (a3 == 16777220 || a3 == 16777219) {
                        a(i2 - 1, 16777216);
                        return;
                    } else if ((a3 & 251658240) != 16777216) {
                        a(i2 - 1, a3 | 8388608);
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.BASTORE /* 84 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.SASTORE /* 86 */:
                c(3);
                return;
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.DASTORE /* 82 */:
                c(4);
                return;
            case Opcodes.POP /* 87 */:
            case Opcodes.IFEQ /* 153 */:
            case Opcodes.IFNE /* 154 */:
            case Opcodes.IFLT /* 155 */:
            case Opcodes.IFGE /* 156 */:
            case Opcodes.IFGT /* 157 */:
            case Opcodes.IFLE /* 158 */:
            case Opcodes.TABLESWITCH /* 170 */:
            case Opcodes.LOOKUPSWITCH /* 171 */:
            case Opcodes.IRETURN /* 172 */:
            case Opcodes.FRETURN /* 174 */:
            case Opcodes.ARETURN /* 176 */:
            case Opcodes.ATHROW /* 191 */:
            case Opcodes.MONITORENTER /* 194 */:
            case Opcodes.MONITOREXIT /* 195 */:
            case Opcodes.IFNULL /* 198 */:
            case Opcodes.IFNONNULL /* 199 */:
                c(1);
                return;
            case Opcodes.POP2 /* 88 */:
            case Opcodes.IF_ICMPEQ /* 159 */:
            case Opcodes.IF_ICMPNE /* 160 */:
            case Opcodes.IF_ICMPLT /* 161 */:
            case Opcodes.IF_ICMPGE /* 162 */:
            case Opcodes.IF_ICMPGT /* 163 */:
            case Opcodes.IF_ICMPLE /* 164 */:
            case Opcodes.IF_ACMPEQ /* 165 */:
            case Opcodes.IF_ACMPNE /* 166 */:
            case Opcodes.LRETURN /* 173 */:
            case Opcodes.DRETURN /* 175 */:
                c(2);
                return;
            case Opcodes.DUP /* 89 */:
                int a4 = a();
                b(a4);
                b(a4);
                return;
            case Opcodes.DUP_X1 /* 90 */:
                int a5 = a();
                int a6 = a();
                b(a5);
                b(a6);
                b(a5);
                return;
            case Opcodes.DUP_X2 /* 91 */:
                int a7 = a();
                int a8 = a();
                int a9 = a();
                b(a7);
                b(a9);
                b(a8);
                b(a7);
                return;
            case Opcodes.DUP2 /* 92 */:
                int a10 = a();
                int a11 = a();
                b(a11);
                b(a10);
                b(a11);
                b(a10);
                return;
            case Opcodes.DUP2_X1 /* 93 */:
                int a12 = a();
                int a13 = a();
                int a14 = a();
                b(a13);
                b(a12);
                b(a14);
                b(a13);
                b(a12);
                return;
            case Opcodes.DUP2_X2 /* 94 */:
                int a15 = a();
                int a16 = a();
                int a17 = a();
                int a18 = a();
                b(a16);
                b(a15);
                b(a18);
                b(a17);
                b(a16);
                b(a15);
                return;
            case Opcodes.SWAP /* 95 */:
                int a19 = a();
                int a20 = a();
                b(a19);
                b(a20);
                return;
            case Opcodes.IADD /* 96 */:
            case Opcodes.ISUB /* 100 */:
            case Opcodes.IMUL /* 104 */:
            case Opcodes.IDIV /* 108 */:
            case Opcodes.IREM /* 112 */:
            case Opcodes.ISHL /* 120 */:
            case Opcodes.ISHR /* 122 */:
            case Opcodes.IUSHR /* 124 */:
            case Opcodes.IAND /* 126 */:
            case 128:
            case Opcodes.IXOR /* 130 */:
            case Opcodes.L2I /* 136 */:
            case Opcodes.D2I /* 142 */:
            case Opcodes.FCMPL /* 149 */:
            case Opcodes.FCMPG /* 150 */:
                c(2);
                b(16777217);
                return;
            case Opcodes.LADD /* 97 */:
            case Opcodes.LSUB /* 101 */:
            case Opcodes.LMUL /* 105 */:
            case Opcodes.LDIV /* 109 */:
            case Opcodes.LREM /* 113 */:
            case Opcodes.LAND /* 127 */:
            case Opcodes.LOR /* 129 */:
            case Opcodes.LXOR /* 131 */:
                c(4);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.FADD /* 98 */:
            case Opcodes.FSUB /* 102 */:
            case Opcodes.FMUL /* 106 */:
            case Opcodes.FDIV /* 110 */:
            case Opcodes.FREM /* 114 */:
            case Opcodes.L2F /* 137 */:
            case Opcodes.D2F /* 144 */:
                c(2);
                b(16777218);
                return;
            case Opcodes.DADD /* 99 */:
            case Opcodes.DSUB /* 103 */:
            case Opcodes.DMUL /* 107 */:
            case Opcodes.DDIV /* 111 */:
            case Opcodes.DREM /* 115 */:
                c(4);
                b(16777219);
                b(16777216);
                return;
            case Opcodes.LSHL /* 121 */:
            case Opcodes.LSHR /* 123 */:
            case Opcodes.LUSHR /* 125 */:
                c(3);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.IINC /* 132 */:
                a(i2, 16777217);
                return;
            case Opcodes.I2L /* 133 */:
            case Opcodes.F2L /* 140 */:
                c(1);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.I2F /* 134 */:
                c(1);
                b(16777218);
                return;
            case Opcodes.I2D /* 135 */:
            case Opcodes.F2D /* 141 */:
                c(1);
                b(16777219);
                b(16777216);
                return;
            case Opcodes.F2I /* 139 */:
            case Opcodes.ARRAYLENGTH /* 190 */:
            case Opcodes.INSTANCEOF /* 193 */:
                c(1);
                b(16777217);
                return;
            case Opcodes.LCMP /* 148 */:
            case Opcodes.DCMPL /* 151 */:
            case Opcodes.DCMPG /* 152 */:
                c(4);
                b(16777217);
                return;
            case Opcodes.JSR /* 168 */:
            case Opcodes.RET /* 169 */:
                throw new RuntimeException("JSR/RET are not supported with computeFrames option");
            case Opcodes.GETSTATIC /* 178 */:
                a(classWriter, item.i);
                return;
            case Opcodes.PUTSTATIC /* 179 */:
                a(item.i);
                return;
            case Opcodes.GETFIELD /* 180 */:
                c(1);
                a(classWriter, item.i);
                return;
            case Opcodes.PUTFIELD /* 181 */:
                a(item.i);
                a();
                return;
            case Opcodes.INVOKEVIRTUAL /* 182 */:
            case Opcodes.INVOKESPECIAL /* 183 */:
            case Opcodes.INVOKESTATIC /* 184 */:
            case Opcodes.INVOKEINTERFACE /* 185 */:
                a(item.i);
                if (i != 184) {
                    int a21 = a();
                    if (i == 183 && item.h.charAt(0) == '<') {
                        d(a21);
                    }
                }
                a(classWriter, item.i);
                return;
            case Opcodes.INVOKEDYNAMIC /* 186 */:
                a(item.h);
                a(classWriter, item.h);
                return;
            case Opcodes.NEW /* 187 */:
                b(25165824 | classWriter.a(item.g, i2));
                return;
            case Opcodes.NEWARRAY /* 188 */:
                a();
                switch (i2) {
                    case 4:
                        b(285212681);
                        return;
                    case 5:
                        b(285212683);
                        return;
                    case 6:
                        b(285212674);
                        return;
                    case 7:
                        b(285212675);
                        return;
                    case 8:
                        b(285212682);
                        return;
                    case 9:
                        b(285212684);
                        return;
                    case 10:
                        b(285212673);
                        return;
                    default:
                        b(285212676);
                        return;
                }
            case Opcodes.ANEWARRAY /* 189 */:
                String str = item.g;
                a();
                if (str.charAt(0) == '[') {
                    a(classWriter, new StringBuffer().append('[').append(str).toString());
                    return;
                } else {
                    b(292552704 | classWriter.m1c(str));
                    return;
                }
            case Opcodes.CHECKCAST /* 192 */:
                String str2 = item.g;
                a();
                if (str2.charAt(0) == '[') {
                    a(classWriter, str2);
                    return;
                } else {
                    b(24117248 | classWriter.m1c(str2));
                    return;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(ClassWriter classWriter, Frame frame, int i) {
        int i2;
        int i3;
        boolean z = false;
        int length = this.c.length;
        int length2 = this.d.length;
        if (frame.c == null) {
            frame.c = new int[length];
            z = true;
        }
        for (int i4 = 0; i4 < length; i4++) {
            if (this.e == null || i4 >= this.e.length) {
                i3 = this.c[i4];
            } else {
                int i5 = this.e[i4];
                if (i5 == 0) {
                    i3 = this.c[i4];
                } else {
                    int i6 = i5 & -268435456;
                    int i7 = i5 & 251658240;
                    if (i7 == 16777216) {
                        i3 = i5;
                    } else {
                        i3 = i7 == 33554432 ? i6 + this.c[i5 & 8388607] : i6 + this.d[length2 - (i5 & 8388607)];
                        if ((i5 & 8388608) != 0 && (i3 == 16777220 || i3 == 16777219)) {
                            i3 = 16777216;
                        }
                    }
                }
            }
            if (this.i != null) {
                i3 = a(classWriter, i3);
            }
            z |= a(classWriter, i3, frame.c, i4);
        }
        if (i > 0) {
            for (int i8 = 0; i8 < length; i8++) {
                z |= a(classWriter, this.c[i8], frame.c, i8);
            }
            if (frame.d == null) {
                frame.d = new int[1];
                z = true;
            }
            return z | a(classWriter, i, frame.d, 0);
        }
        int length3 = this.d.length + this.b.f;
        if (frame.d == null) {
            frame.d = new int[length3 + this.g];
            z = true;
        }
        for (int i9 = 0; i9 < length3; i9++) {
            int i10 = this.d[i9];
            if (this.i != null) {
                i10 = a(classWriter, i10);
            }
            z |= a(classWriter, i10, frame.d, i9);
        }
        for (int i11 = 0; i11 < this.g; i11++) {
            int i12 = this.f[i11];
            int i13 = i12 & -268435456;
            int i14 = i12 & 251658240;
            if (i14 == 16777216) {
                i2 = i12;
            } else {
                i2 = i14 == 33554432 ? i13 + this.c[i12 & 8388607] : i13 + this.d[length2 - (i12 & 8388607)];
                if ((i12 & 8388608) != 0 && (i2 == 16777220 || i2 == 16777219)) {
                    i2 = 16777216;
                }
            }
            if (this.i != null) {
                i2 = a(classWriter, i2);
            }
            z |= a(classWriter, i2, frame.d, length3 + i11);
        }
        return z;
    }

    private static boolean a(ClassWriter classWriter, int i, int[] iArr, int i2) {
        int i3;
        int i4 = iArr[i2];
        if (i4 == i) {
            return false;
        }
        if ((i & 268435455) == 16777221) {
            if (i4 == 16777221) {
                return false;
            }
            i = 16777221;
        }
        if (i4 == 0) {
            iArr[i2] = i;
            return true;
        }
        if ((i4 & 267386880) != 24117248 && (i4 & -268435456) == 0) {
            i3 = i4 == 16777221 ? ((i & 267386880) == 24117248 || (i & -268435456) != 0) ? i : 16777216 : 16777216;
        } else if (i == 16777221) {
            return false;
        } else {
            if ((i & -1048576) == (i4 & -1048576)) {
                i3 = (i4 & 267386880) == 24117248 ? (i & -268435456) | 24117248 | classWriter.a(i & 1048575, i4 & 1048575) : (-268435456 + (i4 & -268435456)) | 24117248 | classWriter.m1c("java/lang/Object");
            } else if ((i & 267386880) == 24117248 || (i & -268435456) != 0) {
                i3 = Math.min((((i & -268435456) == 0 || (i & 267386880) == 24117248) ? 0 : -268435456) + (i & -268435456), (((i4 & -268435456) == 0 || (i4 & 267386880) == 24117248) ? 0 : -268435456) + (i4 & -268435456)) | 24117248 | classWriter.m1c("java/lang/Object");
            } else {
                i3 = 16777216;
            }
        }
        if (i4 == i3) {
            return false;
        }
        iArr[i2] = i3;
        return true;
    }

    static {
        _clinit_();
        int[] iArr = new int[202];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE".charAt(i) - 'E';
        }
        a = iArr;
    }

    static void _clinit_() {
    }
}
