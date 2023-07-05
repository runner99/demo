package com.esotericsoftware.asm;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/MethodVisitor.class */
public abstract class MethodVisitor {
    protected final int api;
    protected MethodVisitor mv;

    public MethodVisitor(int i) {
        this(i, null);
    }

    public MethodVisitor(int i, MethodVisitor methodVisitor) {
        if (i == 262144 || i == 327680) {
            this.api = i;
            this.mv = methodVisitor;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void visitParameter(String str, int i) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.mv != null) {
            this.mv.visitParameter(str, i);
        }
    }

    public AnnotationVisitor visitAnnotationDefault() {
        if (this.mv != null) {
            return this.mv.visitAnnotationDefault();
        }
        return null;
    }

    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        if (this.mv != null) {
            return this.mv.visitAnnotation(str, z);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.mv != null) {
            return this.mv.visitTypeAnnotation(i, typePath, str, z);
        } else {
            return null;
        }
    }

    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        if (this.mv != null) {
            return this.mv.visitParameterAnnotation(i, str, z);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.mv != null) {
            this.mv.visitAttribute(attribute);
        }
    }

    public void visitCode() {
        if (this.mv != null) {
            this.mv.visitCode();
        }
    }

    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        if (this.mv != null) {
            this.mv.visitFrame(i, i2, objArr, i3, objArr2);
        }
    }

    public void visitInsn(int i) {
        if (this.mv != null) {
            this.mv.visitInsn(i);
        }
    }

    public void visitIntInsn(int i, int i2) {
        if (this.mv != null) {
            this.mv.visitIntInsn(i, i2);
        }
    }

    public void visitVarInsn(int i, int i2) {
        if (this.mv != null) {
            this.mv.visitVarInsn(i, i2);
        }
    }

    public void visitTypeInsn(int i, String str) {
        if (this.mv != null) {
            this.mv.visitTypeInsn(i, str);
        }
    }

    public void visitFieldInsn(int i, String str, String str2, String str3) {
        if (this.mv != null) {
            this.mv.visitFieldInsn(i, str, str2, str3);
        }
    }

    public void visitMethodInsn(int i, String str, String str2, String str3) {
        if (this.api >= 327680) {
            visitMethodInsn(i, str, str2, str3, i == 185);
        } else if (this.mv != null) {
            this.mv.visitMethodInsn(i, str, str2, str3);
        }
    }

    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        if (this.api < 327680) {
            if (z != (i == 185)) {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
            }
            visitMethodInsn(i, str, str2, str3);
        } else if (this.mv != null) {
            this.mv.visitMethodInsn(i, str, str2, str3, z);
        }
    }

    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(str, str2, handle, objArr);
        }
    }

    public void visitJumpInsn(int i, Label label) {
        if (this.mv != null) {
            this.mv.visitJumpInsn(i, label);
        }
    }

    public void visitLabel(Label label) {
        if (this.mv != null) {
            this.mv.visitLabel(label);
        }
    }

    public void visitLdcInsn(Object obj) {
        if (this.mv != null) {
            this.mv.visitLdcInsn(obj);
        }
    }

    public void visitIincInsn(int i, int i2) {
        if (this.mv != null) {
            this.mv.visitIincInsn(i, i2);
        }
    }

    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        if (this.mv != null) {
            this.mv.visitTableSwitchInsn(i, i2, label, labelArr);
        }
    }

    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(label, iArr, labelArr);
        }
    }

    public void visitMultiANewArrayInsn(String str, int i) {
        if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(str, i);
        }
    }

    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.mv != null) {
            return this.mv.visitInsnAnnotation(i, typePath, str, z);
        } else {
            return null;
        }
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        if (this.mv != null) {
            this.mv.visitTryCatchBlock(label, label2, label3, str);
        }
    }

    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.mv != null) {
            return this.mv.visitTryCatchAnnotation(i, typePath, str, z);
        } else {
            return null;
        }
    }

    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (this.mv != null) {
            this.mv.visitLocalVariable(str, str2, str3, label, label2, i);
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        if (this.api < 327680) {
            throw new RuntimeException();
        } else if (this.mv != null) {
            return this.mv.visitLocalVariableAnnotation(i, typePath, labelArr, labelArr2, iArr, str, z);
        } else {
            return null;
        }
    }

    public void visitLineNumber(int i, Label label) {
        if (this.mv != null) {
            this.mv.visitLineNumber(i, label);
        }
    }

    public void visitMaxs(int i, int i2) {
        if (this.mv != null) {
            this.mv.visitMaxs(i, i2);
        }
    }

    public void visitEnd() {
        if (this.mv != null) {
            this.mv.visitEnd();
        }
    }
}
