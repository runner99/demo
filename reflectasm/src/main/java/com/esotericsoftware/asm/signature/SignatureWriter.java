package com.esotericsoftware.asm.signature;

import com.esotericsoftware.asm.Opcodes;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/signature/SignatureWriter.class */
public class SignatureWriter extends SignatureVisitor {
    private final StringBuffer a = new StringBuffer();
    private boolean b;
    private boolean c;
    private int d;

    public SignatureWriter() {
        super(Opcodes.ASM5);
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitFormalTypeParameter(String str) {
        if (!this.b) {
            this.b = true;
            this.a.append('<');
        }
        this.a.append(str);
        this.a.append(':');
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        this.a.append(':');
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        a();
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        a();
        if (!this.c) {
            this.c = true;
            this.a.append('(');
        }
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        a();
        if (!this.c) {
            this.a.append('(');
        }
        this.a.append(')');
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        this.a.append('^');
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitBaseType(char c) {
        this.a.append(c);
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitTypeVariable(String str) {
        this.a.append('T');
        this.a.append(str);
        this.a.append(';');
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        this.a.append('[');
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitClassType(String str) {
        this.a.append('L');
        this.a.append(str);
        this.d *= 2;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitInnerClassType(String str) {
        b();
        this.a.append('.');
        this.a.append(str);
        this.d *= 2;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitTypeArgument() {
        if (this.d % 2 == 0) {
            this.d++;
            this.a.append('<');
        }
        this.a.append('*');
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char c) {
        if (this.d % 2 == 0) {
            this.d++;
            this.a.append('<');
        }
        if (c != '=') {
            this.a.append(c);
        }
        return this;
    }

    @Override // com.esotericsoftware.asm.signature.SignatureVisitor
    public void visitEnd() {
        b();
        this.a.append(';');
    }

    public String toString() {
        return this.a.toString();
    }

    private void a() {
        if (this.b) {
            this.b = false;
            this.a.append('>');
        }
    }

    private void b() {
        if (this.d % 2 != 0) {
            this.a.append('>');
        }
        this.d /= 2;
    }
}
