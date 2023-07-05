package com.esotericsoftware.asm;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/FieldVisitor.class */
public abstract class FieldVisitor {
    protected final int api;
    protected FieldVisitor fv;

    public FieldVisitor(int i) {
        this(i, null);
    }

    public FieldVisitor(int i, FieldVisitor fieldVisitor) {
        if (i == 262144 || i == 327680) {
            this.api = i;
            this.fv = fieldVisitor;
            return;
        }
        throw new IllegalArgumentException();
    }

    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        if (this.fv != null) {
            return this.fv.visitAnnotation(str, z);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.fv != null) {
            return this.fv.visitTypeAnnotation(i, typePath, str, z);
        } else {
            return null;
        }
    }

    public void visitAttribute(Attribute attribute) {
        if (this.fv != null) {
            this.fv.visitAttribute(attribute);
        }
    }

    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
}
