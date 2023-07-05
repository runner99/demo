package com.esotericsoftware.asm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/AnnotationWriter.class */
public final class AnnotationWriter extends AnnotationVisitor {
    private final ClassWriter a;
    private int b;
    private final boolean c;
    private final ByteVector d;
    private final ByteVector e;
    private final int f;
    AnnotationWriter g;
    AnnotationWriter h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnnotationWriter(ClassWriter classWriter, boolean z, ByteVector byteVector, ByteVector byteVector2, int i) {
        super(Opcodes.ASM5);
        this.a = classWriter;
        this.c = z;
        this.d = byteVector;
        this.e = byteVector2;
        this.f = i;
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        if (obj instanceof String) {
            this.d.b(Opcodes.DREM, this.a.newUTF8((String) obj));
        } else if (obj instanceof Byte) {
            this.d.b(66, this.a.a((int) ((Byte) obj).byteValue()).a);
        } else if (obj instanceof Boolean) {
            this.d.b(90, this.a.a(((Boolean) obj).booleanValue() ? 1 : 0).a);
        } else if (obj instanceof Character) {
            this.d.b(67, this.a.a((int) ((Character) obj).charValue()).a);
        } else if (obj instanceof Short) {
            this.d.b(83, this.a.a((int) ((Short) obj).shortValue()).a);
        } else if (obj instanceof Type) {
            this.d.b(99, this.a.newUTF8(((Type) obj).getDescriptor()));
        } else if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            this.d.b(91, bArr.length);
            for (byte b : bArr) {
                this.d.b(66, this.a.a((int) b).a);
            }
        } else if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            this.d.b(91, zArr.length);
            for (boolean z : zArr) {
                this.d.b(90, this.a.a(z ? 1 : 0).a);
            }
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            this.d.b(91, sArr.length);
            for (short s : sArr) {
                this.d.b(83, this.a.a((int) s).a);
            }
        } else if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            this.d.b(91, cArr.length);
            for (char c : cArr) {
                this.d.b(67, this.a.a((int) c).a);
            }
        } else if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            this.d.b(91, iArr.length);
            for (int i : iArr) {
                this.d.b(73, this.a.a(i).a);
            }
        } else if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            this.d.b(91, jArr.length);
            for (long j : jArr) {
                this.d.b(74, this.a.a(j).a);
            }
        } else if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            this.d.b(91, fArr.length);
            for (float f : fArr) {
                this.d.b(70, this.a.a(f).a);
            }
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            this.d.b(91, dArr.length);
            for (double d : dArr) {
                this.d.b(68, this.a.a(d).a);
            }
        } else {
            Item a = this.a.a(obj);
            this.d.b(".s.IFJDCS".charAt(a.b), a.a);
        }
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(Opcodes.LSUB, this.a.newUTF8(str2)).putShort(this.a.newUTF8(str3));
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(64, this.a.newUTF8(str2)).putShort(0);
        return new AnnotationWriter(this.a, true, this.d, this.d, this.d.b - 2);
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        this.b++;
        if (this.c) {
            this.d.putShort(this.a.newUTF8(str));
        }
        this.d.b(91, 0);
        return new AnnotationWriter(this.a, false, this.d, this.d, this.d.b - 2);
    }

    @Override // com.esotericsoftware.asm.AnnotationVisitor
    public void visitEnd() {
        if (this.e != null) {
            byte[] bArr = this.e.a;
            bArr[this.f] = (byte) (this.b >>> 8);
            bArr[this.f + 1] = (byte) this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        int i = 0;
        for (AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.g) {
            i += annotationWriter.d.b;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteVector byteVector) {
        int i = 0;
        int i2 = 2;
        AnnotationWriter annotationWriter = null;
        for (AnnotationWriter annotationWriter2 = this; annotationWriter2 != null; annotationWriter2 = annotationWriter2.g) {
            i++;
            i2 += annotationWriter2.d.b;
            annotationWriter2.visitEnd();
            annotationWriter2.h = annotationWriter;
            annotationWriter = annotationWriter2;
        }
        byteVector.putInt(i2);
        byteVector.putShort(i);
        for (AnnotationWriter annotationWriter3 = annotationWriter; annotationWriter3 != null; annotationWriter3 = annotationWriter3.h) {
            byteVector.putByteArray(annotationWriter3.d.a, 0, annotationWriter3.d.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(AnnotationWriter[] annotationWriterArr, int i, ByteVector byteVector) {
        int length = 1 + (2 * (annotationWriterArr.length - i));
        for (int i2 = i; i2 < annotationWriterArr.length; i2++) {
            length += annotationWriterArr[i2] == null ? 0 : annotationWriterArr[i2].a();
        }
        byteVector.putInt(length).putByte(annotationWriterArr.length - i);
        for (int i3 = i; i3 < annotationWriterArr.length; i3++) {
            AnnotationWriter annotationWriter = null;
            int i4 = 0;
            for (AnnotationWriter annotationWriter2 = annotationWriterArr[i3]; annotationWriter2 != null; annotationWriter2 = annotationWriter2.g) {
                i4++;
                annotationWriter2.visitEnd();
                annotationWriter2.h = annotationWriter;
                annotationWriter = annotationWriter2;
            }
            byteVector.putShort(i4);
            for (AnnotationWriter annotationWriter3 = annotationWriter; annotationWriter3 != null; annotationWriter3 = annotationWriter3.h) {
                byteVector.putByteArray(annotationWriter3.d.a, 0, annotationWriter3.d.b);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int i, TypePath typePath, ByteVector byteVector) {
        switch (i >>> 24) {
            case 0:
            case 1:
            case 22:
                byteVector.putShort(i >>> 16);
                break;
            case TypeReference.FIELD /* 19 */:
            case TypeReference.METHOD_RETURN /* 20 */:
            case 21:
                byteVector.putByte(i >>> 24);
                break;
            case TypeReference.CAST /* 71 */:
            case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
            case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
            case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
            case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
                byteVector.putInt(i);
                break;
            default:
                byteVector.b(i >>> 24, (i & 16776960) >> 8);
                break;
        }
        if (typePath == null) {
            byteVector.putByte(0);
            return;
        }
        byteVector.putByteArray(typePath.a, typePath.b, (typePath.a[typePath.b] * 2) + 1);
    }
}
