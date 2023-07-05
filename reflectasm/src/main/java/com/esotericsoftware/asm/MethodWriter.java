package com.esotericsoftware.asm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/MethodWriter.class */
public class MethodWriter extends MethodVisitor {
    final ClassWriter b;
    private int c;
    private final int d;
    private final int e;
    private final String f;
    String g;
    int h;
    int i;
    int j;
    int[] k;
    private ByteVector l;
    private AnnotationWriter m;
    private AnnotationWriter n;
    private AnnotationWriter U;
    private AnnotationWriter V;
    private AnnotationWriter[] o;
    private AnnotationWriter[] p;
    private int S;
    private Attribute q;
    private ByteVector r = new ByteVector();
    private int s;
    private int t;
    private int T;
    private int u;
    private ByteVector v;
    private int w;
    private int[] x;
    private int[] z;
    private int A;
    private Handler B;
    private Handler C;
    private int Z;
    private ByteVector $;
    private int D;
    private ByteVector E;
    private int F;
    private ByteVector G;
    private int H;
    private ByteVector I;
    private int Y;
    private AnnotationWriter W;
    private AnnotationWriter X;
    private Attribute J;
    private boolean K;
    private int L;
    private final int M;
    private Label N;
    private Label O;
    private Label P;
    private int Q;
    private int R;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MethodWriter(ClassWriter classWriter, int i, String str, String str2, String str3, String[] strArr, boolean z, boolean z2) {
        super(Opcodes.ASM5);
        if (classWriter.D == null) {
            classWriter.D = this;
        } else {
            classWriter.E.mv = this;
        }
        classWriter.E = this;
        this.b = classWriter;
        this.c = i;
        if ("<init>".equals(str)) {
            this.c |= 524288;
        }
        this.d = classWriter.newUTF8(str);
        this.e = classWriter.newUTF8(str2);
        this.f = str2;
        this.g = str3;
        if (strArr != null && strArr.length > 0) {
            this.j = strArr.length;
            this.k = new int[this.j];
            for (int i2 = 0; i2 < this.j; i2++) {
                this.k[i2] = classWriter.newClass(strArr[i2]);
            }
        }
        this.M = z2 ? 0 : z ? 1 : 2;
        if (z || z2) {
            int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(this.f) >> 2;
            argumentsAndReturnSizes = (i & 8) != 0 ? argumentsAndReturnSizes - 1 : argumentsAndReturnSizes;
            this.t = argumentsAndReturnSizes;
            this.T = argumentsAndReturnSizes;
            this.N = new Label();
            this.N.a |= 8;
            visitLabel(this.N);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitParameter(String str, int i) {
        if (this.$ == null) {
            this.$ = new ByteVector();
        }
        this.Z++;
        this.$.putShort(str == null ? 0 : this.b.newUTF8(str)).putShort(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        this.l = new ByteVector();
        return new AnnotationWriter(this.b, false, this.l, null, 0);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.m;
            this.m = annotationWriter;
        } else {
            annotationWriter.g = this.n;
            this.n = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.U;
            this.U = annotationWriter;
        } else {
            annotationWriter.g = this.V;
            this.V = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(str)) {
            this.S = Math.max(this.S, i + 1);
            return new AnnotationWriter(this.b, false, byteVector, null, 0);
        }
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            if (this.o == null) {
                this.o = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.o[i];
            this.o[i] = annotationWriter;
        } else {
            if (this.p == null) {
                this.p = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.p[i];
            this.p[i] = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.a = this.J;
            this.J = attribute;
            return;
        }
        attribute.a = this.q;
        this.q = attribute;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitCode() {
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        if (this.M != 0) {
            if (i == -1) {
                if (this.x == null) {
                    f();
                }
                this.T = i2;
                int a = a(this.r.b, i2, i3);
                for (int i5 = 0; i5 < i2; i5++) {
                    if (objArr[i5] instanceof String) {
                        a++;
                        this.z[a] = 24117248 | this.b.m1c((String) objArr[i5]);
                    } else if (objArr[i5] instanceof Integer) {
                        a++;
                        this.z[a] = ((Integer) objArr[i5]).intValue();
                    } else {
                        a++;
                        this.z[a] = 25165824 | this.b.a("", ((Label) objArr[i5]).c);
                    }
                }
                for (int i6 = 0; i6 < i3; i6++) {
                    if (objArr2[i6] instanceof String) {
                        a++;
                        this.z[a] = 24117248 | this.b.m1c((String) objArr2[i6]);
                    } else if (objArr2[i6] instanceof Integer) {
                        a++;
                        this.z[a] = ((Integer) objArr2[i6]).intValue();
                    } else {
                        a++;
                        this.z[a] = 25165824 | this.b.a("", ((Label) objArr2[i6]).c);
                    }
                }
                b();
            } else {
                if (this.v == null) {
                    this.v = new ByteVector();
                    i4 = this.r.b;
                } else {
                    i4 = (this.r.b - this.w) - 1;
                    if (i4 < 0) {
                        if (i != 3) {
                            throw new IllegalStateException();
                        }
                        return;
                    }
                }
                switch (i) {
                    case 0:
                        this.T = i2;
                        this.v.putByte(255).putShort(i4).putShort(i2);
                        for (int i7 = 0; i7 < i2; i7++) {
                            a(objArr[i7]);
                        }
                        this.v.putShort(i3);
                        for (int i8 = 0; i8 < i3; i8++) {
                            a(objArr2[i8]);
                        }
                        break;
                    case 1:
                        this.T += i2;
                        this.v.putByte(251 + i2).putShort(i4);
                        for (int i9 = 0; i9 < i2; i9++) {
                            a(objArr[i9]);
                        }
                        break;
                    case 2:
                        this.T -= i2;
                        this.v.putByte(251 - i2).putShort(i4);
                        break;
                    case 3:
                        if (i4 < 64) {
                            this.v.putByte(i4);
                            break;
                        } else {
                            this.v.putByte(251).putShort(i4);
                            break;
                        }
                    case 4:
                        if (i4 < 64) {
                            this.v.putByte(64 + i4);
                        } else {
                            this.v.putByte(247).putShort(i4);
                        }
                        a(objArr2[0]);
                        break;
                }
                this.w = this.r.b;
                this.u++;
            }
            this.s = Math.max(this.s, i3);
            this.t = Math.max(this.t, this.T);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitInsn(int i) {
        this.Y = this.r.b;
        this.r.putByte(i);
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, 0, (ClassWriter) null, (Item) null);
            } else {
                int i2 = this.Q + Frame.a[i];
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
            if ((i >= 172 && i <= 177) || i == 191) {
                e();
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitIntInsn(int i, int i2) {
        this.Y = this.r.b;
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i != 188) {
                int i3 = this.Q + 1;
                if (i3 > this.R) {
                    this.R = i3;
                }
                this.Q = i3;
            }
        }
        if (i == 17) {
            this.r.b(i, i2);
        } else {
            this.r.a(i, i2);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        this.Y = this.r.b;
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i == 169) {
                this.P.a |= Opcodes.ACC_NATIVE;
                this.P.f = this.Q;
                e();
            } else {
                int i3 = this.Q + Frame.a[i];
                if (i3 > this.R) {
                    this.R = i3;
                }
                this.Q = i3;
            }
        }
        if (this.M != 2) {
            int i4 = (i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1;
            if (i4 > this.t) {
                this.t = i4;
            }
        }
        if (i2 < 4 && i != 169) {
            this.r.putByte(i < 54 ? 26 + ((i - 21) << 2) + i2 : 59 + ((i - 54) << 2) + i2);
        } else if (i2 >= 256) {
            this.r.putByte(196).b(i, i2);
        } else {
            this.r.a(i, i2);
        }
        if (i >= 54 && this.M == 0 && this.A > 0) {
            visitLabel(new Label());
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        this.Y = this.r.b;
        Item a = this.b.a(str);
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, this.r.b, this.b, a);
            } else if (i == 187) {
                int i2 = this.Q + 1;
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        this.r.b(i, a.a);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        int i2;
        this.Y = this.r.b;
        Item a = this.b.a(str, str2, str3);
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, 0, this.b, a);
            } else {
                char charAt = str3.charAt(0);
                switch (i) {
                    case Opcodes.GETSTATIC /* 178 */:
                        i2 = this.Q + ((charAt == 'D' || charAt == 'J') ? 2 : 1);
                        break;
                    case Opcodes.PUTSTATIC /* 179 */:
                        i2 = this.Q + ((charAt == 'D' || charAt == 'J') ? -2 : -1);
                        break;
                    case Opcodes.GETFIELD /* 180 */:
                        i2 = this.Q + ((charAt == 'D' || charAt == 'J') ? 1 : 0);
                        break;
                    default:
                        i2 = this.Q + ((charAt == 'D' || charAt == 'J') ? -3 : -2);
                        break;
                }
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        this.r.b(i, a.a);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        this.Y = this.r.b;
        Item a = this.b.a(str, str2, str3, z);
        int i2 = a.c;
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, 0, this.b, a);
            } else {
                if (i2 == 0) {
                    i2 = Type.getArgumentsAndReturnSizes(str3);
                    a.c = i2;
                }
                int i3 = i == 184 ? (this.Q - (i2 >> 2)) + (i2 & 3) + 1 : (this.Q - (i2 >> 2)) + (i2 & 3);
                if (i3 > this.R) {
                    this.R = i3;
                }
                this.Q = i3;
            }
        }
        if (i == 185) {
            if (i2 == 0) {
                i2 = Type.getArgumentsAndReturnSizes(str3);
                a.c = i2;
            }
            this.r.b(Opcodes.INVOKEINTERFACE, a.a).a(i2 >> 2, 0);
            return;
        }
        this.r.b(i, a.a);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.Y = this.r.b;
        Item a = this.b.a(str, str2, handle, objArr);
        int i = a.c;
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(Opcodes.INVOKEDYNAMIC, 0, this.b, a);
            } else {
                if (i == 0) {
                    i = Type.getArgumentsAndReturnSizes(str2);
                    a.c = i;
                }
                int i2 = (this.Q - (i >> 2)) + (i & 3) + 1;
                if (i2 > this.R) {
                    this.R = i2;
                }
                this.Q = i2;
            }
        }
        this.r.b(Opcodes.INVOKEDYNAMIC, a.a);
        this.r.putShort(0);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitJumpInsn(int i, Label label) {
        this.Y = this.r.b;
        Label label2 = null;
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(i, 0, (ClassWriter) null, (Item) null);
                label.a().a |= 16;
                a(0, label);
                if (i != 167) {
                    label2 = new Label();
                }
            } else if (i == 168) {
                if ((label.a & Opcodes.ACC_INTERFACE) == 0) {
                    label.a |= Opcodes.ACC_INTERFACE;
                    this.L++;
                }
                this.P.a |= 128;
                a(this.Q + 1, label);
                label2 = new Label();
            } else {
                this.Q += Frame.a[i];
                a(this.Q, label);
            }
        }
        if ((label.a & 2) == 0 || label.c - this.r.b >= -32768) {
            this.r.putByte(i);
            label.a(this, this.r, this.r.b - 1, false);
        } else {
            if (i == 167) {
                this.r.putByte(200);
            } else if (i == 168) {
                this.r.putByte(201);
            } else {
                if (label2 != null) {
                    label2.a |= 16;
                }
                this.r.putByte(i <= 166 ? ((i + 1) ^ 1) - 1 : i ^ 1);
                this.r.putShort(8);
                this.r.putByte(200);
            }
            label.a(this, this.r, this.r.b - 1, true);
        }
        if (this.P != null) {
            if (label2 != null) {
                visitLabel(label2);
            }
            if (i == 167) {
                e();
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLabel(Label label) {
        this.K |= label.a(this, this.r.b, this.r.a);
        if ((label.a & 1) == 0) {
            if (this.M == 0) {
                if (this.P != null) {
                    if (label.c == this.P.c) {
                        this.P.a |= label.a & 16;
                        label.h = this.P.h;
                        return;
                    }
                    a(0, label);
                }
                this.P = label;
                if (label.h == null) {
                    label.h = new Frame();
                    label.h.b = label;
                }
                if (this.O != null) {
                    if (label.c == this.O.c) {
                        this.O.a |= label.a & 16;
                        label.h = this.O.h;
                        this.P = this.O;
                        return;
                    }
                    this.O.i = label;
                }
                this.O = label;
            } else if (this.M == 1) {
                if (this.P != null) {
                    this.P.g = this.R;
                    a(this.Q, label);
                }
                this.P = label;
                this.Q = 0;
                this.R = 0;
                if (this.O != null) {
                    this.O.i = label;
                }
                this.O = label;
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        this.Y = this.r.b;
        Item a = this.b.a(obj);
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(18, 0, this.b, a);
            } else {
                int i = (a.b == 5 || a.b == 6) ? this.Q + 2 : this.Q + 1;
                if (i > this.R) {
                    this.R = i;
                }
                this.Q = i;
            }
        }
        int i2 = a.a;
        if (a.b == 5 || a.b == 6) {
            this.r.b(20, i2);
        } else if (i2 >= 256) {
            this.r.b(19, i2);
        } else {
            this.r.a(18, i2);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        int i3;
        this.Y = this.r.b;
        if (this.P != null && this.M == 0) {
            this.P.h.a(Opcodes.IINC, i, (ClassWriter) null, (Item) null);
        }
        if (this.M != 2 && (i3 = i + 1) > this.t) {
            this.t = i3;
        }
        if (i > 255 || i2 > 127 || i2 < -128) {
            this.r.putByte(196).b(Opcodes.IINC, i).putShort(i2);
        } else {
            this.r.putByte(Opcodes.IINC).a(i, i2);
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.Y = this.r.b;
        int i3 = this.r.b;
        this.r.putByte(Opcodes.TABLESWITCH);
        this.r.putByteArray(null, 0, (4 - (this.r.b % 4)) % 4);
        label.a(this, this.r, i3, true);
        this.r.putInt(i).putInt(i2);
        for (Label label2 : labelArr) {
            label2.a(this, this.r, i3, true);
        }
        a(label, labelArr);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.Y = this.r.b;
        int i = this.r.b;
        this.r.putByte(Opcodes.LOOKUPSWITCH);
        this.r.putByteArray(null, 0, (4 - (this.r.b % 4)) % 4);
        label.a(this, this.r, i, true);
        this.r.putInt(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            this.r.putInt(iArr[i2]);
            labelArr[i2].a(this, this.r, i, true);
        }
        a(label, labelArr);
    }

    private void a(Label label, Label[] labelArr) {
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(Opcodes.LOOKUPSWITCH, 0, (ClassWriter) null, (Item) null);
                a(0, label);
                label.a().a |= 16;
                for (int i = 0; i < labelArr.length; i++) {
                    a(0, labelArr[i]);
                    labelArr[i].a().a |= 16;
                }
            } else {
                this.Q--;
                a(this.Q, label);
                for (Label label2 : labelArr) {
                    a(this.Q, label2);
                }
            }
            e();
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        this.Y = this.r.b;
        Item a = this.b.a(str);
        if (this.P != null) {
            if (this.M == 0) {
                this.P.h.a(Opcodes.MULTIANEWARRAY, i, this.b, a);
            } else {
                this.Q += 1 - i;
            }
        }
        this.r.b(Opcodes.MULTIANEWARRAY, a.a).putByte(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a((i & -16776961) | (this.Y << 8), typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        this.A++;
        Handler handler = new Handler();
        handler.a = label;
        handler.b = label2;
        handler.c = label3;
        handler.d = str;
        handler.e = str != null ? this.b.newClass(str) : 0;
        if (this.C == null) {
            this.B = handler;
        } else {
            this.C.f = handler;
        }
        this.C = handler;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (str3 != null) {
            if (this.G == null) {
                this.G = new ByteVector();
            }
            this.F++;
            this.G.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(str)).putShort(this.b.newUTF8(str3)).putShort(i);
        }
        if (this.E == null) {
            this.E = new ByteVector();
        }
        this.D++;
        this.E.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(str)).putShort(this.b.newUTF8(str2)).putShort(i);
        if (this.M != 2) {
            char charAt = str2.charAt(0);
            int i2 = i + ((charAt == 'J' || charAt == 'D') ? 2 : 1);
            if (i2 > this.t) {
                this.t = i2;
            }
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(i >>> 24).putShort(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            byteVector.putShort(labelArr[i2].c).putShort(labelArr2[i2].c - labelArr[i2].c).putShort(iArr[i2]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            byteVector.putByteArray(typePath.a, typePath.b, (typePath.a[typePath.b] * 2) + 1);
        }
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitLineNumber(int i, Label label) {
        if (this.I == null) {
            this.I = new ByteVector();
        }
        this.H++;
        this.I.putShort(label.c);
        this.I.putShort(i);
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        if (this.K) {
            d();
        }
        if (this.M == 0) {
            for (Handler handler = this.B; handler != null; handler = handler.f) {
                Label a = handler.c.a();
                Label a2 = handler.b.a();
                int c = 24117248 | this.b.m1c(handler.d == null ? "java/lang/Throwable" : handler.d);
                a.a |= 16;
                for (Label a3 = handler.a.a(); a3 != a2; a3 = a3.i) {
                    Edge edge = new Edge();
                    edge.a = c;
                    edge.b = a;
                    edge.c = a3.j;
                    a3.j = edge;
                }
            }
            Frame frame = this.N.h;
            frame.a(this.b, this.c, Type.getArgumentTypes(this.f), this.t);
            b(frame);
            int i3 = 0;
            Label label = this.N;
            while (label != null) {
                label = label.k;
                label.k = null;
                Frame frame2 = label.h;
                if ((label.a & 16) != 0) {
                    label.a |= 32;
                }
                label.a |= 64;
                int length = frame2.d.length + label.g;
                if (length > i3) {
                    i3 = length;
                }
                for (Edge edge2 = label.j; edge2 != null; edge2 = edge2.c) {
                    Label a4 = edge2.b.a();
                    if (frame2.a(this.b, a4.h, edge2.a) && a4.k == null) {
                        a4.k = label;
                        label = a4;
                    }
                }
            }
            for (Label label2 = this.N; label2 != null; label2 = label2.i) {
                Frame frame3 = label2.h;
                if ((label2.a & 32) != 0) {
                    b(frame3);
                }
                if ((label2.a & 64) == 0) {
                    Label label3 = label2.i;
                    int i4 = label2.c;
                    int i5 = (label3 == null ? this.r.b : label3.c) - 1;
                    if (i5 >= i4) {
                        i3 = Math.max(i3, 1);
                        for (int i6 = i4; i6 < i5; i6++) {
                            this.r.a[i6] = 0;
                        }
                        this.r.a[i5] = -65;
                        this.z[a(i4, 0, 1)] = 24117248 | this.b.m1c("java/lang/Throwable");
                        b();
                        this.B = Handler.a(this.B, label2, label3);
                    }
                }
            }
            this.A = 0;
            for (Handler handler2 = this.B; handler2 != null; handler2 = handler2.f) {
                this.A++;
            }
            this.s = i3;
        } else if (this.M == 1) {
            for (Handler handler3 = this.B; handler3 != null; handler3 = handler3.f) {
                Label label4 = handler3.c;
                Label label5 = handler3.b;
                for (Label label6 = handler3.a; label6 != label5; label6 = label6.i) {
                    Edge edge3 = new Edge();
                    edge3.a = Integer.MAX_VALUE;
                    edge3.b = label4;
                    if ((label6.a & 128) == 0) {
                        edge3.c = label6.j;
                        label6.j = edge3;
                    } else {
                        edge3.c = label6.j.c.c;
                        label6.j.c.c = edge3;
                    }
                }
            }
            if (this.L > 0) {
                int i7 = 0;
                this.N.b(null, 1, this.L);
                for (Label label7 = this.N; label7 != null; label7 = label7.i) {
                    if ((label7.a & 128) != 0) {
                        Label label8 = label7.j.c.b;
                        if ((label8.a & Opcodes.ACC_ABSTRACT) == 0) {
                            i7++;
                            label8.b(null, ((((long) i7) / 32) << 32) | (1 << (i7 % 32)), this.L);
                        }
                    }
                }
                for (Label label9 = this.N; label9 != null; label9 = label9.i) {
                    if ((label9.a & 128) != 0) {
                        for (Label label10 = this.N; label10 != null; label10 = label10.i) {
                            label10.a &= -2049;
                        }
                        label9.j.c.b.b(label9, 0, this.L);
                    }
                }
            }
            int i8 = 0;
            Label label11 = this.N;
            while (label11 != null) {
                label11 = label11.k;
                int i9 = label11.f;
                int i10 = i9 + label11.g;
                if (i10 > i8) {
                    i8 = i10;
                }
                Edge edge4 = label11.j;
                if ((label11.a & 128) != 0) {
                    edge4 = edge4.c;
                }
                while (edge4 != null) {
                    Label label12 = edge4.b;
                    if ((label12.a & 8) == 0) {
                        label12.f = edge4.a == Integer.MAX_VALUE ? 1 : i9 + edge4.a;
                        label12.a |= 8;
                        label12.k = label11;
                        label11 = label12;
                    }
                    edge4 = edge4.c;
                }
            }
            this.s = Math.max(i, i8);
        } else {
            this.s = i;
            this.t = i2;
        }
    }

    @Override // com.esotericsoftware.asm.MethodVisitor
    public void visitEnd() {
    }

    private void a(int i, Label label) {
        Edge edge = new Edge();
        edge.a = i;
        edge.b = label;
        edge.c = this.P.j;
        this.P.j = edge;
    }

    private void e() {
        if (this.M == 0) {
            Label label = new Label();
            label.h = new Frame();
            label.h.b = label;
            label.a(this, this.r.b, this.r.a);
            this.O.i = label;
            this.O = label;
        } else {
            this.P.g = this.R;
        }
        this.P = null;
    }

    private void b(Frame frame) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int[] iArr = frame.c;
        int[] iArr2 = frame.d;
        int i4 = 0;
        while (i4 < iArr.length) {
            int i5 = iArr[i4];
            if (i5 == 16777216) {
                i++;
            } else {
                i2 += i + 1;
                i = 0;
            }
            if (i5 == 16777220 || i5 == 16777219) {
                i4++;
            }
            i4++;
        }
        int i6 = 0;
        while (i6 < iArr2.length) {
            int i7 = iArr2[i6];
            i3++;
            if (i7 == 16777220 || i7 == 16777219) {
                i6++;
            }
            i6++;
        }
        int a = a(frame.b.c, i2, i3);
        int i8 = 0;
        while (i2 > 0) {
            int i9 = iArr[i8];
            a++;
            this.z[a] = i9;
            if (i9 == 16777220 || i9 == 16777219) {
                i8++;
            }
            i8++;
            i2--;
        }
        int i10 = 0;
        while (i10 < iArr2.length) {
            int i11 = iArr2[i10];
            a++;
            this.z[a] = i11;
            if (i11 == 16777220 || i11 == 16777219) {
                i10++;
            }
            i10++;
        }
        b();
    }

    private void f() {
        int a = a(0, this.f.length() + 1, 0);
        if ((this.c & 8) == 0) {
            if ((this.c & 524288) == 0) {
                a++;
                this.z[a] = 24117248 | this.b.m1c(this.b.I);
            } else {
                a++;
                this.z[a] = 6;
            }
        }
        int i = 1;
        while (true) {
            i++;
            switch (this.f.charAt(i)) {
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
                case Opcodes.AASTORE /* 83 */:
                case Opcodes.DUP_X1 /* 90 */:
                    a++;
                    this.z[a] = 1;
                    break;
                case TypeReference.NEW /* 68 */:
                    a++;
                    this.z[a] = 3;
                    break;
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
                case Opcodes.SASTORE /* 86 */:
                case Opcodes.POP /* 87 */:
                case Opcodes.POP2 /* 88 */:
                case Opcodes.DUP /* 89 */:
                default:
                    this.z[1] = a - 3;
                    b();
                    return;
                case TypeReference.METHOD_REFERENCE /* 70 */:
                    a++;
                    this.z[a] = 2;
                    break;
                case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
                    a++;
                    this.z[a] = 4;
                    break;
                case 'L':
                    while (this.f.charAt(i) != ';') {
                        i++;
                    }
                    a++;
                    int i2 = i + 1;
                    i++;
                    this.z[a] = 24117248 | this.b.m1c(this.f.substring(i2, i));
                    break;
                case Opcodes.DUP_X2 /* 91 */:
                    while (this.f.charAt(i) == '[') {
                        i++;
                    }
                    if (this.f.charAt(i) == 'L') {
                        while (true) {
                            i++;
                            if (this.f.charAt(i) != ';') {
                            }
                        }
                    }
                    a++;
                    i++;
                    this.z[a] = 24117248 | this.b.m1c(this.f.substring(i, i));
                    break;
            }
        }
    }

    private int a(int i, int i2, int i3) {
        int i4 = 3 + i2 + i3;
        if (this.z == null || this.z.length < i4) {
            this.z = new int[i4];
        }
        this.z[0] = i;
        this.z[1] = i2;
        this.z[2] = i3;
        return 3;
    }

    private void b() {
        if (this.x != null) {
            if (this.v == null) {
                this.v = new ByteVector();
            }
            c();
            this.u++;
        }
        this.x = this.z;
        this.z = null;
    }

    private void c() {
        int i = this.z[1];
        int i2 = this.z[2];
        if ((this.b.b & 65535) < 50) {
            this.v.putShort(this.z[0]).putShort(i);
            a(3, 3 + i);
            this.v.putShort(i2);
            a(3 + i, 3 + i + i2);
            return;
        }
        int i3 = this.x[1];
        char c = 255;
        int i4 = 0;
        int i5 = this.u == 0 ? this.z[0] : (this.z[0] - this.x[0]) - 1;
        if (i2 == 0) {
            i4 = i - i3;
            switch (i4) {
                case -3:
                case -2:
                case Opcodes.F_NEW /* -1 */:
                    c = 248;
                    i3 = i;
                    break;
                case 0:
                    c = i5 < 64 ? (char) 0 : 251;
                    break;
                case 1:
                case 2:
                case 3:
                    c = 252;
                    break;
            }
        } else if (i == i3 && i2 == 1) {
            c = i5 < 63 ? '@' : 247;
        }
        if (c != 255) {
            int i6 = 3;
            int i7 = 0;
            while (true) {
                if (i7 < i3) {
                    if (this.z[i6] != this.x[i6]) {
                        c = 255;
                    } else {
                        i6++;
                        i7++;
                    }
                }
            }
        }
        switch (c) {
            case 0:
                this.v.putByte(i5);
                return;
            case '@':
                this.v.putByte(64 + i5);
                a(3 + i, 4 + i);
                return;
            case 247:
                this.v.putByte(247).putShort(i5);
                a(3 + i, 4 + i);
                return;
            case 248:
                this.v.putByte(251 + i4).putShort(i5);
                return;
            case 251:
                this.v.putByte(251).putShort(i5);
                return;
            case 252:
                this.v.putByte(251 + i4).putShort(i5);
                a(3 + i3, 3 + i);
                return;
            default:
                this.v.putByte(255).putShort(i5).putShort(i);
                a(3, 3 + i);
                this.v.putShort(i2);
                a(3 + i, 3 + i + i2);
                return;
        }
    }

    private void a(int i, int i2) {
        for (int i3 = i; i3 < i2; i3++) {
            int i4 = this.z[i3];
            int i5 = i4 & -268435456;
            if (i5 == 0) {
                int i6 = i4 & 1048575;
                switch (i4 & 267386880) {
                    case 24117248:
                        this.v.putByte(7).putShort(this.b.newClass(this.b.H[i6].g));
                        continue;
                    case 25165824:
                        this.v.putByte(8).putShort(this.b.H[i6].c);
                        continue;
                    default:
                        this.v.putByte(i6);
                        continue;
                }
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                int i7 = i5 >> 28;
                while (true) {
                    i7--;
                    if (i7 > 0) {
                        stringBuffer.append('[');
                    } else {
                        if ((i4 & 267386880) != 24117248) {
                            switch (i4 & 15) {
                                case 1:
                                    stringBuffer.append('I');
                                    break;
                                case 2:
                                    stringBuffer.append('F');
                                    break;
                                case 3:
                                    stringBuffer.append('D');
                                    break;
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                default:
                                    stringBuffer.append('J');
                                    break;
                                case 9:
                                    stringBuffer.append('Z');
                                    break;
                                case 10:
                                    stringBuffer.append('B');
                                    break;
                                case 11:
                                    stringBuffer.append('C');
                                    break;
                                case Opcodes.FCONST_1 /* 12 */:
                                    stringBuffer.append('S');
                                    break;
                            }
                        } else {
                            stringBuffer.append('L');
                            stringBuffer.append(this.b.H[i4 & 1048575].g);
                            stringBuffer.append(';');
                        }
                        this.v.putByte(7).putShort(this.b.newClass(stringBuffer.toString()));
                    }
                }
            }
        }
    }

    private void a(Object obj) {
        if (obj instanceof String) {
            this.v.putByte(7).putShort(this.b.newClass((String) obj));
        } else if (obj instanceof Integer) {
            this.v.putByte(((Integer) obj).intValue());
        } else {
            this.v.putByte(8).putShort(((Label) obj).c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int a() {
        if (this.h != 0) {
            return 6 + this.i;
        }
        int i = 8;
        if (this.r.b > 0) {
            if (this.r.b > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.b.newUTF8("Code");
            i = 8 + 18 + this.r.b + (8 * this.A);
            if (this.E != null) {
                this.b.newUTF8("LocalVariableTable");
                i += 8 + this.E.b;
            }
            if (this.G != null) {
                this.b.newUTF8("LocalVariableTypeTable");
                i += 8 + this.G.b;
            }
            if (this.I != null) {
                this.b.newUTF8("LineNumberTable");
                i += 8 + this.I.b;
            }
            if (this.v != null) {
                this.b.newUTF8((this.b.b & 65535) >= 50 ? "StackMapTable" : "StackMap");
                i += 8 + this.v.b;
            }
            if (this.W != null) {
                this.b.newUTF8("RuntimeVisibleTypeAnnotations");
                i += 8 + this.W.a();
            }
            if (this.X != null) {
                this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
                i += 8 + this.X.a();
            }
            if (this.J != null) {
                i += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
        }
        if (this.j > 0) {
            this.b.newUTF8("Exceptions");
            i += 8 + (2 * this.j);
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            this.b.newUTF8("Synthetic");
            i += 6;
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            this.b.newUTF8("Deprecated");
            i += 6;
        }
        if (this.g != null) {
            this.b.newUTF8("Signature");
            this.b.newUTF8(this.g);
            i += 8;
        }
        if (this.$ != null) {
            this.b.newUTF8("MethodParameters");
            i += 7 + this.$.b;
        }
        if (this.l != null) {
            this.b.newUTF8("AnnotationDefault");
            i += 6 + this.l.b;
        }
        if (this.m != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            i += 8 + this.m.a();
        }
        if (this.n != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            i += 8 + this.n.a();
        }
        if (this.U != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            i += 8 + this.U.a();
        }
        if (this.V != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            i += 8 + this.V.a();
        }
        if (this.o != null) {
            this.b.newUTF8("RuntimeVisibleParameterAnnotations");
            i += 7 + (2 * (this.o.length - this.S));
            for (int length = this.o.length - 1; length >= this.S; length--) {
                i += this.o[length] == null ? 0 : this.o[length].a();
            }
        }
        if (this.p != null) {
            this.b.newUTF8("RuntimeInvisibleParameterAnnotations");
            i += 7 + (2 * (this.p.length - this.S));
            for (int length2 = this.p.length - 1; length2 >= this.S; length2--) {
                i += this.p[length2] == null ? 0 : this.p[length2].a();
            }
        }
        if (this.q != null) {
            i += this.q.a(this.b, null, 0, -1, -1);
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(ByteVector byteVector) {
        byteVector.putShort(this.c & ((917504 | ((this.c & Opcodes.ASM4) / 64)) ^ -1)).putShort(this.d).putShort(this.e);
        if (this.h != 0) {
            byteVector.putByteArray(this.b.M.b, this.h, this.i);
            return;
        }
        int i = 0;
        if (this.r.b > 0) {
            i = 0 + 1;
        }
        if (this.j > 0) {
            i++;
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            i++;
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            i++;
        }
        if (this.g != null) {
            i++;
        }
        if (this.$ != null) {
            i++;
        }
        if (this.l != null) {
            i++;
        }
        if (this.m != null) {
            i++;
        }
        if (this.n != null) {
            i++;
        }
        if (this.U != null) {
            i++;
        }
        if (this.V != null) {
            i++;
        }
        if (this.o != null) {
            i++;
        }
        if (this.p != null) {
            i++;
        }
        if (this.q != null) {
            i += this.q.a();
        }
        byteVector.putShort(i);
        if (this.r.b > 0) {
            int i2 = 12 + this.r.b + (8 * this.A);
            if (this.E != null) {
                i2 += 8 + this.E.b;
            }
            if (this.G != null) {
                i2 += 8 + this.G.b;
            }
            if (this.I != null) {
                i2 += 8 + this.I.b;
            }
            if (this.v != null) {
                i2 += 8 + this.v.b;
            }
            if (this.W != null) {
                i2 += 8 + this.W.a();
            }
            if (this.X != null) {
                i2 += 8 + this.X.a();
            }
            if (this.J != null) {
                i2 += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
            byteVector.putShort(this.b.newUTF8("Code")).putInt(i2);
            byteVector.putShort(this.s).putShort(this.t);
            byteVector.putInt(this.r.b).putByteArray(this.r.a, 0, this.r.b);
            byteVector.putShort(this.A);
            if (this.A > 0) {
                for (Handler handler = this.B; handler != null; handler = handler.f) {
                    byteVector.putShort(handler.a.c).putShort(handler.b.c).putShort(handler.c.c).putShort(handler.e);
                }
            }
            int i3 = 0;
            if (this.E != null) {
                i3 = 0 + 1;
            }
            if (this.G != null) {
                i3++;
            }
            if (this.I != null) {
                i3++;
            }
            if (this.v != null) {
                i3++;
            }
            if (this.W != null) {
                i3++;
            }
            if (this.X != null) {
                i3++;
            }
            if (this.J != null) {
                i3 += this.J.a();
            }
            byteVector.putShort(i3);
            if (this.E != null) {
                byteVector.putShort(this.b.newUTF8("LocalVariableTable"));
                byteVector.putInt(this.E.b + 2).putShort(this.D);
                byteVector.putByteArray(this.E.a, 0, this.E.b);
            }
            if (this.G != null) {
                byteVector.putShort(this.b.newUTF8("LocalVariableTypeTable"));
                byteVector.putInt(this.G.b + 2).putShort(this.F);
                byteVector.putByteArray(this.G.a, 0, this.G.b);
            }
            if (this.I != null) {
                byteVector.putShort(this.b.newUTF8("LineNumberTable"));
                byteVector.putInt(this.I.b + 2).putShort(this.H);
                byteVector.putByteArray(this.I.a, 0, this.I.b);
            }
            if (this.v != null) {
                byteVector.putShort(this.b.newUTF8((this.b.b & 65535) >= 50 ? "StackMapTable" : "StackMap"));
                byteVector.putInt(this.v.b + 2).putShort(this.u);
                byteVector.putByteArray(this.v.a, 0, this.v.b);
            }
            if (this.W != null) {
                byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.W.a(byteVector);
            }
            if (this.X != null) {
                byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.X.a(byteVector);
            }
            if (this.J != null) {
                this.J.a(this.b, this.r.a, this.r.b, this.t, this.s, byteVector);
            }
        }
        if (this.j > 0) {
            byteVector.putShort(this.b.newUTF8("Exceptions")).putInt((2 * this.j) + 2);
            byteVector.putShort(this.j);
            for (int i4 = 0; i4 < this.j; i4++) {
                byteVector.putShort(this.k[i4]);
            }
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            byteVector.putShort(this.b.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            byteVector.putShort(this.b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.g != null) {
            byteVector.putShort(this.b.newUTF8("Signature")).putInt(2).putShort(this.b.newUTF8(this.g));
        }
        if (this.$ != null) {
            byteVector.putShort(this.b.newUTF8("MethodParameters"));
            byteVector.putInt(this.$.b + 1).putByte(this.Z);
            byteVector.putByteArray(this.$.a, 0, this.$.b);
        }
        if (this.l != null) {
            byteVector.putShort(this.b.newUTF8("AnnotationDefault"));
            byteVector.putInt(this.l.b);
            byteVector.putByteArray(this.l.a, 0, this.l.b);
        }
        if (this.m != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.m.a(byteVector);
        }
        if (this.n != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.n.a(byteVector);
        }
        if (this.U != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.U.a(byteVector);
        }
        if (this.V != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.V.a(byteVector);
        }
        if (this.o != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.a(this.o, this.S, byteVector);
        }
        if (this.p != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.a(this.p, this.S, byteVector);
        }
        if (this.q != null) {
            this.q.a(this.b, null, 0, -1, -1, byteVector);
        }
    }

    private void d() {
        int i;
        int i2;
        byte[] bArr = this.r.a;
        int[] iArr = new int[0];
        int[] iArr2 = new int[0];
        boolean[] zArr = new boolean[this.r.b];
        int i3 = 3;
        do {
            if (i3 == 3) {
                i3 = 2;
            }
            int i4 = 0;
            while (i4 < bArr.length) {
                int i5 = bArr[i4] & 255;
                int i6 = 0;
                switch (ClassWriter.a[i5]) {
                    case 0:
                    case 4:
                        i4++;
                        break;
                    case 1:
                    case 3:
                    case 11:
                        i4 += 2;
                        break;
                    case 2:
                    case 5:
                    case 6:
                    case Opcodes.FCONST_1 /* 12 */:
                    case Opcodes.FCONST_2 /* 13 */:
                        i4 += 3;
                        break;
                    case 7:
                    case 8:
                        i4 += 5;
                        break;
                    case 9:
                        if (i5 > 201) {
                            i5 = i5 < 218 ? i5 - 49 : i5 - 20;
                            i2 = i4 + c(bArr, i4 + 1);
                        } else {
                            i2 = i4 + b(bArr, i4 + 1);
                        }
                        int a = a(iArr, iArr2, i4, i2);
                        if ((a < -32768 || a > 32767) && !zArr[i4]) {
                            i6 = (i5 == 167 || i5 == 168) ? 2 : 5;
                            zArr[i4] = true;
                        }
                        i4 += 3;
                        break;
                    case 10:
                        i4 += 5;
                        break;
                    case Opcodes.DCONST_0 /* 14 */:
                        if (i3 == 1) {
                            i6 = -(a(iArr, iArr2, 0, i4) & 3);
                        } else if (!zArr[i4]) {
                            i6 = i4 & 3;
                            zArr[i4] = true;
                        }
                        int i7 = (i4 + 4) - (i4 & 3);
                        i4 = i7 + (4 * ((a(bArr, i7 + 8) - a(bArr, i7 + 4)) + 1)) + 12;
                        break;
                    case Opcodes.DCONST_1 /* 15 */:
                        if (i3 == 1) {
                            i6 = -(a(iArr, iArr2, 0, i4) & 3);
                        } else if (!zArr[i4]) {
                            i6 = i4 & 3;
                            zArr[i4] = true;
                        }
                        int i8 = (i4 + 4) - (i4 & 3);
                        i4 = i8 + (8 * a(bArr, i8 + 4)) + 8;
                        break;
                    case 16:
                    default:
                        i4 += 4;
                        break;
                    case 17:
                        if ((bArr[i4 + 1] & 255) == 132) {
                            i4 += 6;
                            break;
                        } else {
                            i4 += 4;
                            break;
                        }
                }
                if (i6 != 0) {
                    int[] iArr3 = new int[iArr.length + 1];
                    int[] iArr4 = new int[iArr2.length + 1];
                    System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                    System.arraycopy(iArr2, 0, iArr4, 0, iArr2.length);
                    iArr3[iArr.length] = i4;
                    iArr4[iArr2.length] = i6;
                    iArr = iArr3;
                    iArr2 = iArr4;
                    if (i6 > 0) {
                        i3 = 3;
                    }
                }
            }
            if (i3 < 3) {
                i3--;
            }
        } while (i3 != 0);
        ByteVector byteVector = new ByteVector(this.r.b);
        short s = 0;
        while (s < this.r.b) {
            int i9 = bArr[s] & 255;
            switch (ClassWriter.a[i9]) {
                case 0:
                case 4:
                    byteVector.putByte(i9);
                    s++;
                    break;
                case 1:
                case 3:
                case 11:
                    byteVector.putByteArray(bArr, s, 2);
                    s += 2;
                    break;
                case 2:
                case 5:
                case 6:
                case Opcodes.FCONST_1 /* 12 */:
                case Opcodes.FCONST_2 /* 13 */:
                    byteVector.putByteArray(bArr, s, 3);
                    s += 3;
                    break;
                case 7:
                case 8:
                    byteVector.putByteArray(bArr, s, 5);
                    s += 5;
                    break;
                case 9:
                    if (i9 > 201) {
                        i9 = i9 < 218 ? i9 - 49 : i9 - 20;
                        i = s + c(bArr, s + 1);
                    } else {
                        i = s + b(bArr, s + 1);
                    }
                    int a2 = a(iArr, iArr2, s, i);
                    if (zArr[s]) {
                        if (i9 == 167) {
                            byteVector.putByte(200);
                        } else if (i9 == 168) {
                            byteVector.putByte(201);
                        } else {
                            byteVector.putByte(i9 <= 166 ? ((i9 + 1) ^ 1) - 1 : i9 ^ 1);
                            byteVector.putShort(8);
                            byteVector.putByte(200);
                            a2 -= 3;
                        }
                        byteVector.putInt(a2);
                    } else {
                        byteVector.putByte(i9);
                        byteVector.putShort(a2);
                    }
                    s += 3;
                    break;
                case 10:
                    int a3 = a(iArr, iArr2, s, s + a(bArr, s + 1));
                    byteVector.putByte(i9);
                    byteVector.putInt(a3);
                    s += 5;
                    break;
                case Opcodes.DCONST_0 /* 14 */:
                    int i10 = (s + 4) - (s & 3);
                    byteVector.putByte(Opcodes.TABLESWITCH);
                    byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
                    int a4 = s + a(bArr, i10);
                    int i11 = i10 + 4;
                    byteVector.putInt(a(iArr, iArr2, s, a4));
                    int a5 = a(bArr, i11);
                    int i12 = i11 + 4;
                    byteVector.putInt(a5);
                    s = i12 + 4;
                    byteVector.putInt(a(bArr, s - 4));
                    for (int a6 = (a(bArr, i12) - a5) + 1; a6 > 0; a6--) {
                        int a7 = s + a(bArr, s);
                        s += 4;
                        byteVector.putInt(a(iArr, iArr2, s, a7));
                    }
                    break;
                case Opcodes.DCONST_1 /* 15 */:
                    int i13 = (s + 4) - (s & 3);
                    byteVector.putByte(Opcodes.LOOKUPSWITCH);
                    byteVector.putByteArray(null, 0, (4 - (byteVector.b % 4)) % 4);
                    int a8 = s + a(bArr, i13);
                    int i14 = i13 + 4;
                    byteVector.putInt(a(iArr, iArr2, s, a8));
                    int a9 = a(bArr, i14);
                    s = i14 + 4;
                    byteVector.putInt(a9);
                    while (a9 > 0) {
                        byteVector.putInt(a(bArr, s));
                        int i15 = s + 4;
                        int a10 = s + a(bArr, i15);
                        s = i15 + 4;
                        byteVector.putInt(a(iArr, iArr2, s, a10));
                        a9--;
                    }
                    break;
                case 16:
                default:
                    byteVector.putByteArray(bArr, s, 4);
                    s += 4;
                    break;
                case 17:
                    if ((bArr[s + 1] & 255) == 132) {
                        byteVector.putByteArray(bArr, s, 6);
                        s += 6;
                        break;
                    } else {
                        byteVector.putByteArray(bArr, s, 4);
                        s += 4;
                        break;
                    }
            }
        }
        if (this.M == 0) {
            for (Label label = this.N; label != null; label = label.i) {
                int i16 = label.c - 3;
                if (i16 >= 0 && zArr[i16]) {
                    label.a |= 16;
                }
                a(iArr, iArr2, label);
            }
            if (this.b.H != null) {
                for (int i17 = 0; i17 < this.b.H.length; i17++) {
                    Item item = this.b.H[i17];
                    if (item != null && item.b == 31) {
                        item.c = a(iArr, iArr2, 0, item.c);
                    }
                }
            }
        } else if (this.u > 0) {
            this.b.L = true;
        }
        for (Handler handler = this.B; handler != null; handler = handler.f) {
            a(iArr, iArr2, handler.a);
            a(iArr, iArr2, handler.b);
            a(iArr, iArr2, handler.c);
        }
        int i18 = 0;
        while (i18 < 2) {
            ByteVector byteVector2 = i18 == 0 ? this.E : this.G;
            if (byteVector2 != null) {
                byte[] bArr2 = byteVector2.a;
                for (int i19 = 0; i19 < byteVector2.b; i19 += 10) {
                    int c = c(bArr2, i19);
                    int a11 = a(iArr, iArr2, 0, c);
                    a(bArr2, i19, a11);
                    a(bArr2, i19 + 2, a(iArr, iArr2, 0, c + c(bArr2, i19 + 2)) - a11);
                }
            }
            i18++;
        }
        if (this.I != null) {
            byte[] bArr3 = this.I.a;
            for (int i20 = 0; i20 < this.I.b; i20 += 4) {
                a(bArr3, i20, a(iArr, iArr2, 0, c(bArr3, i20)));
            }
        }
        for (Attribute attribute = this.J; attribute != null; attribute = attribute.a) {
            Label[] labels = attribute.getLabels();
            if (labels != null) {
                for (int length = labels.length - 1; length >= 0; length--) {
                    a(iArr, iArr2, labels[length]);
                }
            }
        }
        this.r = byteVector;
    }

    static int c(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 8) | (bArr[i + 1] & 255);
    }

    static short b(byte[] bArr, int i) {
        return (short) (((bArr[i] & 255) << 8) | (bArr[i + 1] & 255));
    }

    static int a(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255);
    }

    static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    static int a(int[] iArr, int[] iArr2, int i, int i2) {
        int i3 = i2 - i;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            if (i < iArr[i4] && iArr[i4] <= i2) {
                i3 += iArr2[i4];
            } else if (i2 < iArr[i4] && iArr[i4] <= i) {
                i3 -= iArr2[i4];
            }
        }
        return i3;
    }

    static void a(int[] iArr, int[] iArr2, Label label) {
        if ((label.a & 4) == 0) {
            label.c = a(iArr, iArr2, 0, label.c);
            label.a |= 4;
        }
    }
}
