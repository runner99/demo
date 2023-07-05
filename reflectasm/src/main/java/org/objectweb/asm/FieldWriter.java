package org.objectweb.asm;

/* loaded from: asm-5.1.jar:org/objectweb/asm/FieldWriter.class */
final class FieldWriter extends FieldVisitor {
    private final ClassWriter b;
    private final int c;
    private final int d;
    private final int e;
    private int f;
    private int g;
    private AnnotationWriter h;
    private AnnotationWriter i;
    private AnnotationWriter k;
    private AnnotationWriter l;
    private Attribute j;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FieldWriter(ClassWriter classWriter, int i, String str, String str2, String str3, Object obj) {
        super(Opcodes.ASM5);
        if (classWriter.B == null) {
            classWriter.B = this;
        } else {
            classWriter.C.fv = this;
        }
        classWriter.C = this;
        this.b = classWriter;
        this.c = i;
        this.d = classWriter.newUTF8(str);
        this.e = classWriter.newUTF8(str2);
        if (str3 != null) {
            this.f = classWriter.newUTF8(str3);
        }
        if (obj != null) {
            this.g = classWriter.a(obj).a;
        }
    }

    @Override // org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.h;
            this.h = annotationWriter;
        } else {
            annotationWriter.g = this.i;
            this.i = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.k;
            this.k = annotationWriter;
        } else {
            annotationWriter.g = this.l;
            this.l = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // org.objectweb.asm.FieldVisitor
    public void visitAttribute(Attribute attribute) {
        attribute.a = this.j;
        this.j = attribute;
    }

    @Override // org.objectweb.asm.FieldVisitor
    public void visitEnd() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        int i = 8;
        if (this.g != 0) {
            this.b.newUTF8("ConstantValue");
            i = 8 + 8;
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            this.b.newUTF8("Synthetic");
            i += 6;
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            this.b.newUTF8("Deprecated");
            i += 6;
        }
        if (this.f != 0) {
            this.b.newUTF8("Signature");
            i += 8;
        }
        if (this.h != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            i += 8 + this.h.a();
        }
        if (this.i != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            i += 8 + this.i.a();
        }
        if (this.k != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            i += 8 + this.k.a();
        }
        if (this.l != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            i += 8 + this.l.a();
        }
        if (this.j != null) {
            i += this.j.a(this.b, null, 0, -1, -1);
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ByteVector byteVector) {
        byteVector.putShort(this.c & ((393216 | ((this.c & Opcodes.ASM4) / 64)) ^ -1)).putShort(this.d).putShort(this.e);
        int i = 0;
        if (this.g != 0) {
            i = 0 + 1;
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            i++;
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            i++;
        }
        if (this.f != 0) {
            i++;
        }
        if (this.h != null) {
            i++;
        }
        if (this.i != null) {
            i++;
        }
        if (this.k != null) {
            i++;
        }
        if (this.l != null) {
            i++;
        }
        if (this.j != null) {
            i += this.j.a();
        }
        byteVector.putShort(i);
        if (this.g != 0) {
            byteVector.putShort(this.b.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.g);
        }
        if ((this.c & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b.b & 65535) < 49 || (this.c & Opcodes.ASM4) != 0)) {
            byteVector.putShort(this.b.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.c & Opcodes.ACC_DEPRECATED) != 0) {
            byteVector.putShort(this.b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.f != 0) {
            byteVector.putShort(this.b.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.f);
        }
        if (this.h != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.h.a(byteVector);
        }
        if (this.i != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.i.a(byteVector);
        }
        if (this.k != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.k.a(byteVector);
        }
        if (this.l != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.l.a(byteVector);
        }
        if (this.j != null) {
            this.j.a(this.b, null, 0, -1, -1, byteVector);
        }
    }
}
