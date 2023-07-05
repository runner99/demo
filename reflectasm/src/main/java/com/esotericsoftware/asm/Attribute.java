package com.esotericsoftware.asm;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/Attribute.class */
public class Attribute {
    public final String type;
    byte[] b;
    Attribute a;

    /* JADX INFO: Access modifiers changed from: protected */
    public Attribute(String str) {
        this.type = str;
    }

    public boolean isUnknown() {
        return true;
    }

    public boolean isCodeAttribute() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Label[] getLabels() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Attribute read(ClassReader classReader, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        Attribute attribute = new Attribute(this.type);
        attribute.b = new byte[i2];
        System.arraycopy(classReader.b, i, attribute.b, 0, i2);
        return attribute;
    }

    protected ByteVector write(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3) {
        ByteVector byteVector = new ByteVector();
        byteVector.a = this.b;
        byteVector.b = this.b.length;
        return byteVector;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int a() {
        int i = 0;
        for (Attribute attribute = this; attribute != null; attribute = attribute.a) {
            i++;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int a(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        for (Attribute attribute = this; attribute != null; attribute = attribute.a) {
            classWriter.newUTF8(attribute.type);
            i4 += attribute.write(classWriter, bArr, i, i2, i3).b + 6;
        }
        return i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3, ByteVector byteVector) {
        for (Attribute attribute = this; attribute != null; attribute = attribute.a) {
            ByteVector write = attribute.write(classWriter, bArr, i, i2, i3);
            byteVector.putShort(classWriter.newUTF8(attribute.type)).putInt(write.b);
            byteVector.putByteArray(write.a, 0, write.b);
        }
    }
}
