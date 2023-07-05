package org.objectweb.asm;

/* loaded from: asm-5.1.jar:org/objectweb/asm/AnnotationVisitor.class */
public abstract class AnnotationVisitor {
    protected final int api;
    protected AnnotationVisitor av;

    public AnnotationVisitor(int i) {
        this(i, null);
    }

    public AnnotationVisitor(int i, AnnotationVisitor annotationVisitor) {
        if (i == 262144 || i == 327680) {
            this.api = i;
            this.av = annotationVisitor;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void visit(String str, Object obj) {
        if (this.av != null) {
            this.av.visit(str, obj);
        }
    }

    public void visitEnum(String str, String str2, String str3) {
        if (this.av != null) {
            this.av.visitEnum(str, str2, str3);
        }
    }

    public AnnotationVisitor visitAnnotation(String str, String str2) {
        if (this.av != null) {
            return this.av.visitAnnotation(str, str2);
        }
        return null;
    }

    public AnnotationVisitor visitArray(String str) {
        if (this.av != null) {
            return this.av.visitArray(str);
        }
        return null;
    }

    public void visitEnd() {
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
}
