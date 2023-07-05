package com.esotericsoftware.asm;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/asm/ClassWriter.class */
public class ClassWriter extends ClassVisitor {
    public static final int COMPUTE_MAXS = 1;
    public static final int COMPUTE_FRAMES = 2;
    static final byte[] a;
    ClassReader M;
    int b;
    int c;
    final ByteVector d;
    Item[] e;
    int f;
    final Item g;
    final Item h;
    final Item i;
    final Item j;
    Item[] H;
    private short G;
    private int k;
    private int l;
    String I;
    private int m;
    private int n;
    private int o;
    private int[] p;
    private int q;
    private ByteVector r;
    private int s;
    private int t;
    private AnnotationWriter u;
    private AnnotationWriter v;
    private AnnotationWriter N;
    private AnnotationWriter O;
    private Attribute w;
    private int x;
    private ByteVector y;
    int z;
    ByteVector A;
    FieldWriter B;
    FieldWriter C;
    MethodWriter D;
    MethodWriter E;
    private boolean K;
    private boolean J;
    boolean L;

    public ClassWriter(int i) {
        super(Opcodes.ASM5);
        this.c = 1;
        this.d = new ByteVector();
        this.e = new Item[Opcodes.ACC_NATIVE];
        this.f = (int) (0.75d * ((double) this.e.length));
        this.g = new Item();
        this.h = new Item();
        this.i = new Item();
        this.j = new Item();
        this.K = (i & 1) != 0;
        this.J = (i & 2) != 0;
    }

    public ClassWriter(ClassReader classReader, int i) {
        this(i);
        classReader.a(this);
        this.M = classReader;
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.b = i;
        this.k = i2;
        this.l = newClass(str);
        this.I = str;
        if (str2 != null) {
            this.m = newUTF8(str2);
        }
        this.n = str3 == null ? 0 : newClass(str3);
        if (strArr != null && strArr.length > 0) {
            this.o = strArr.length;
            this.p = new int[this.o];
            for (int i3 = 0; i3 < this.o; i3++) {
                this.p[i3] = newClass(strArr[i3]);
            }
        }
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visitSource(String str, String str2) {
        if (str != null) {
            this.q = newUTF8(str);
        }
        if (str2 != null) {
            this.r = new ByteVector().c(str2, 0, Integer.MAX_VALUE);
        }
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visitOuterClass(String str, String str2, String str3) {
        this.s = newClass(str);
        if (str2 != null && str3 != null) {
            this.t = newNameType(str2, str3);
        }
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.u;
            this.u = annotationWriter;
        } else {
            annotationWriter.g = this.v;
            this.v = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(i, typePath, byteVector);
        byteVector.putShort(newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, byteVector.b - 2);
        if (z) {
            annotationWriter.g = this.N;
            this.N = annotationWriter;
        } else {
            annotationWriter.g = this.O;
            this.O = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visitAttribute(Attribute attribute) {
        attribute.a = this.w;
        this.w = attribute;
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visitInnerClass(String str, String str2, String str3, int i) {
        if (this.y == null) {
            this.y = new ByteVector();
        }
        Item a2 = a(str);
        if (a2.c == 0) {
            this.x++;
            this.y.putShort(a2.a);
            this.y.putShort(str2 == null ? 0 : newClass(str2));
            this.y.putShort(str3 == null ? 0 : newUTF8(str3));
            this.y.putShort(i);
            a2.c = this.x;
        }
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        return new FieldWriter(this, i, str, str2, str3, obj);
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        return new MethodWriter(this, i, str, str2, str3, strArr, this.K, this.J);
    }

    @Override // com.esotericsoftware.asm.ClassVisitor
    public final void visitEnd() {
    }

    public byte[] toByteArray() {
        if (this.c > 65535) {
            throw new RuntimeException("Class file too large!");
        }
        int i = 24 + (2 * this.o);
        int i2 = 0;
        for (FieldWriter fieldWriter = this.B; fieldWriter != null; fieldWriter = (FieldWriter) fieldWriter.fv) {
            i2++;
            i += fieldWriter.a();
        }
        int i3 = 0;
        for (MethodWriter methodWriter = this.D; methodWriter != null; methodWriter = (MethodWriter) methodWriter.mv) {
            i3++;
            i += methodWriter.a();
        }
        int i4 = 0;
        if (this.A != null) {
            i4 = 0 + 1;
            i += 8 + this.A.b;
            newUTF8("BootstrapMethods");
        }
        if (this.m != 0) {
            i4++;
            i += 8;
            newUTF8("Signature");
        }
        if (this.q != 0) {
            i4++;
            i += 8;
            newUTF8("SourceFile");
        }
        if (this.r != null) {
            i4++;
            i += this.r.b + 6;
            newUTF8("SourceDebugExtension");
        }
        if (this.s != 0) {
            i4++;
            i += 10;
            newUTF8("EnclosingMethod");
        }
        if ((this.k & Opcodes.ACC_DEPRECATED) != 0) {
            i4++;
            i += 6;
            newUTF8("Deprecated");
        }
        if ((this.k & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b & 65535) < 49 || (this.k & Opcodes.ASM4) != 0)) {
            i4++;
            i += 6;
            newUTF8("Synthetic");
        }
        if (this.y != null) {
            i4++;
            i += 8 + this.y.b;
            newUTF8("InnerClasses");
        }
        if (this.u != null) {
            i4++;
            i += 8 + this.u.a();
            newUTF8("RuntimeVisibleAnnotations");
        }
        if (this.v != null) {
            i4++;
            i += 8 + this.v.a();
            newUTF8("RuntimeInvisibleAnnotations");
        }
        if (this.N != null) {
            i4++;
            i += 8 + this.N.a();
            newUTF8("RuntimeVisibleTypeAnnotations");
        }
        if (this.O != null) {
            i4++;
            i += 8 + this.O.a();
            newUTF8("RuntimeInvisibleTypeAnnotations");
        }
        if (this.w != null) {
            i4 += this.w.a();
            i += this.w.a(this, null, 0, -1, -1);
        }
        ByteVector byteVector = new ByteVector(i + this.d.b);
        byteVector.putInt(-889275714).putInt(this.b);
        byteVector.putShort(this.c).putByteArray(this.d.a, 0, this.d.b);
        byteVector.putShort(this.k & ((393216 | ((this.k & Opcodes.ASM4) / 64)) ^ -1)).putShort(this.l).putShort(this.n);
        byteVector.putShort(this.o);
        for (int i5 = 0; i5 < this.o; i5++) {
            byteVector.putShort(this.p[i5]);
        }
        byteVector.putShort(i2);
        for (FieldWriter fieldWriter2 = this.B; fieldWriter2 != null; fieldWriter2 = (FieldWriter) fieldWriter2.fv) {
            fieldWriter2.a(byteVector);
        }
        byteVector.putShort(i3);
        for (MethodWriter methodWriter2 = this.D; methodWriter2 != null; methodWriter2 = (MethodWriter) methodWriter2.mv) {
            methodWriter2.a(byteVector);
        }
        byteVector.putShort(i4);
        if (this.A != null) {
            byteVector.putShort(newUTF8("BootstrapMethods"));
            byteVector.putInt(this.A.b + 2).putShort(this.z);
            byteVector.putByteArray(this.A.a, 0, this.A.b);
        }
        if (this.m != 0) {
            byteVector.putShort(newUTF8("Signature")).putInt(2).putShort(this.m);
        }
        if (this.q != 0) {
            byteVector.putShort(newUTF8("SourceFile")).putInt(2).putShort(this.q);
        }
        if (this.r != null) {
            int i6 = this.r.b;
            byteVector.putShort(newUTF8("SourceDebugExtension")).putInt(i6);
            byteVector.putByteArray(this.r.a, 0, i6);
        }
        if (this.s != 0) {
            byteVector.putShort(newUTF8("EnclosingMethod")).putInt(4);
            byteVector.putShort(this.s).putShort(this.t);
        }
        if ((this.k & Opcodes.ACC_DEPRECATED) != 0) {
            byteVector.putShort(newUTF8("Deprecated")).putInt(0);
        }
        if ((this.k & Opcodes.ACC_SYNTHETIC) != 0 && ((this.b & 65535) < 49 || (this.k & Opcodes.ASM4) != 0)) {
            byteVector.putShort(newUTF8("Synthetic")).putInt(0);
        }
        if (this.y != null) {
            byteVector.putShort(newUTF8("InnerClasses"));
            byteVector.putInt(this.y.b + 2).putShort(this.x);
            byteVector.putByteArray(this.y.a, 0, this.y.b);
        }
        if (this.u != null) {
            byteVector.putShort(newUTF8("RuntimeVisibleAnnotations"));
            this.u.a(byteVector);
        }
        if (this.v != null) {
            byteVector.putShort(newUTF8("RuntimeInvisibleAnnotations"));
            this.v.a(byteVector);
        }
        if (this.N != null) {
            byteVector.putShort(newUTF8("RuntimeVisibleTypeAnnotations"));
            this.N.a(byteVector);
        }
        if (this.O != null) {
            byteVector.putShort(newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.O.a(byteVector);
        }
        if (this.w != null) {
            this.w.a(this, null, 0, -1, -1, byteVector);
        }
        if (!this.L) {
            return byteVector.a;
        }
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = 0;
        this.y = null;
        this.z = 0;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.K = false;
        this.J = true;
        this.L = false;
        new ClassReader(byteVector.a).accept(this, 4);
        return toByteArray();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(Object obj) {
        if (obj instanceof Integer) {
            return a(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return a(((Byte) obj).intValue());
        }
        if (obj instanceof Character) {
            return a((int) ((Character) obj).charValue());
        }
        if (obj instanceof Short) {
            return a(((Short) obj).intValue());
        }
        if (obj instanceof Boolean) {
            return a(((Boolean) obj).booleanValue() ? 1 : 0);
        } else if (obj instanceof Float) {
            return a(((Float) obj).floatValue());
        } else {
            if (obj instanceof Long) {
                return a(((Long) obj).longValue());
            }
            if (obj instanceof Double) {
                return a(((Double) obj).doubleValue());
            }
            if (obj instanceof String) {
                return b((String) obj);
            }
            if (obj instanceof Type) {
                Type type = (Type) obj;
                int sort = type.getSort();
                return sort == 10 ? a(type.getInternalName()) : sort == 11 ? c(type.getDescriptor()) : a(type.getDescriptor());
            } else if (obj instanceof Handle) {
                Handle handle = (Handle) obj;
                return a(handle.a, handle.b, handle.c, handle.d, handle.e);
            } else {
                throw new IllegalArgumentException(new StringBuffer().append("value ").append(obj).toString());
            }
        }
    }

    public int newConst(Object obj) {
        return a(obj).a;
    }

    public int newUTF8(String str) {
        this.g.a(1, str, null, null);
        Item a2 = a(this.g);
        if (a2 == null) {
            this.d.putByte(1).putUTF8(str);
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.g);
            b(a2);
        }
        return a2.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(String str) {
        this.h.a(7, str, null, null);
        Item a2 = a(this.h);
        if (a2 == null) {
            this.d.b(7, newUTF8(str));
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.h);
            b(a2);
        }
        return a2;
    }

    public int newClass(String str) {
        return a(str).a;
    }

    Item c(String str) {
        this.h.a(16, str, null, null);
        Item a2 = a(this.h);
        if (a2 == null) {
            this.d.b(16, newUTF8(str));
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.h);
            b(a2);
        }
        return a2;
    }

    public int newMethodType(String str) {
        return c(str).a;
    }

    Item a(int i, String str, String str2, String str3, boolean z) {
        this.j.a(20 + i, str, str2, str3);
        Item a2 = a(this.j);
        if (a2 == null) {
            if (i <= 4) {
                b(15, i, newField(str, str2, str3));
            } else {
                b(15, i, newMethod(str, str2, str3, z));
            }
            int i2 = this.c;
            this.c = i2 + 1;
            a2 = new Item(i2, this.j);
            b(a2);
        }
        return a2;
    }

    public int newHandle(int i, String str, String str2, String str3) {
        return newHandle(i, str, str2, str3, i == 9);
    }

    public int newHandle(int i, String str, String str2, String str3, boolean z) {
        return a(i, str, str2, str3, z).a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(String str, String str2, Handle handle, Object... objArr) {
        int i;
        ByteVector byteVector = this.A;
        if (byteVector == null) {
            ByteVector byteVector2 = new ByteVector();
            this.A = byteVector2;
            byteVector = byteVector2;
        }
        int i2 = byteVector.b;
        int hashCode = handle.hashCode();
        byteVector.putShort(newHandle(handle.a, handle.b, handle.c, handle.d, handle.isInterface()));
        int length = objArr.length;
        byteVector.putShort(length);
        for (Object obj : objArr) {
            hashCode ^= obj.hashCode();
            byteVector.putShort(newConst(obj));
        }
        byte[] bArr = byteVector.a;
        int i3 = (2 + length) << 1;
        int i4 = hashCode & Integer.MAX_VALUE;
        Item item = this.e[i4 % this.e.length];
        loop1: while (item != null) {
            if (item.b == 33 && item.j == i4) {
                int i5 = item.c;
                for (int i6 = 0; i6 < i3; i6++) {
                    if (bArr[i2 + i6] != bArr[i5 + i6]) {
                        item = item.k;
                    }
                }
                break loop1;
            }
            item = item.k;
        }
        if (item != null) {
            i = item.a;
            byteVector.b = i2;
        } else {
            int i7 = this.z;
            this.z = i7 + 1;
            i = i7;
            Item item2 = new Item(i);
            item2.a(i2, i4);
            b(item2);
        }
        this.i.a(str, str2, i);
        Item a2 = a(this.i);
        if (a2 == null) {
            a(18, i, newNameType(str, str2));
            int i8 = this.c;
            this.c = i8 + 1;
            a2 = new Item(i8, this.i);
            b(a2);
        }
        return a2;
    }

    public int newInvokeDynamic(String str, String str2, Handle handle, Object... objArr) {
        return a(str, str2, handle, objArr).a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(String str, String str2, String str3) {
        this.i.a(9, str, str2, str3);
        Item a2 = a(this.i);
        if (a2 == null) {
            a(9, newClass(str), newNameType(str2, str3));
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.i);
            b(a2);
        }
        return a2;
    }

    public int newField(String str, String str2, String str3) {
        return a(str, str2, str3).a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(String str, String str2, String str3, boolean z) {
        int i = z ? 11 : 10;
        this.i.a(i, str, str2, str3);
        Item a2 = a(this.i);
        if (a2 == null) {
            a(i, newClass(str), newNameType(str2, str3));
            int i2 = this.c;
            this.c = i2 + 1;
            a2 = new Item(i2, this.i);
            b(a2);
        }
        return a2;
    }

    public int newMethod(String str, String str2, String str3, boolean z) {
        return a(str, str2, str3, z).a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(int i) {
        this.g.a(i);
        Item a2 = a(this.g);
        if (a2 == null) {
            this.d.putByte(3).putInt(i);
            int i2 = this.c;
            this.c = i2 + 1;
            a2 = new Item(i2, this.g);
            b(a2);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(float f) {
        this.g.a(f);
        Item a2 = a(this.g);
        if (a2 == null) {
            this.d.putByte(4).putInt(this.g.c);
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.g);
            b(a2);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(long j) {
        this.g.a(j);
        Item a2 = a(this.g);
        if (a2 == null) {
            this.d.putByte(5).putLong(j);
            a2 = new Item(this.c, this.g);
            this.c += 2;
            b(a2);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Item a(double d) {
        this.g.a(d);
        Item a2 = a(this.g);
        if (a2 == null) {
            this.d.putByte(6).putLong(this.g.d);
            a2 = new Item(this.c, this.g);
            this.c += 2;
            b(a2);
        }
        return a2;
    }

    private Item b(String str) {
        this.h.a(8, str, null, null);
        Item a2 = a(this.h);
        if (a2 == null) {
            this.d.b(8, newUTF8(str));
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.h);
            b(a2);
        }
        return a2;
    }

    public int newNameType(String str, String str2) {
        return a(str, str2).a;
    }

    Item a(String str, String str2) {
        this.h.a(12, str, str2, null);
        Item a2 = a(this.h);
        if (a2 == null) {
            a(12, newUTF8(str), newUTF8(str2));
            int i = this.c;
            this.c = i + 1;
            a2 = new Item(i, this.h);
            b(a2);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c  reason: collision with other method in class */
    public int m1c(String str) {
        this.g.a(30, str, null, null);
        Item a2 = a(this.g);
        if (a2 == null) {
            a2 = c(this.g);
        }
        return a2.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(String str, int i) {
        this.g.b = 31;
        this.g.c = i;
        this.g.g = str;
        this.g.j = Integer.MAX_VALUE & (31 + str.hashCode() + i);
        Item a2 = a(this.g);
        if (a2 == null) {
            a2 = c(this.g);
        }
        return a2.a;
    }

    private Item c(Item item) {
        this.G = (short) (this.G + 1);
        Item item2 = new Item(this.G, this.g);
        b(item2);
        if (this.H == null) {
            this.H = new Item[16];
        }
        if (this.G == this.H.length) {
            Item[] itemArr = new Item[2 * this.H.length];
            System.arraycopy(this.H, 0, itemArr, 0, this.H.length);
            this.H = itemArr;
        }
        this.H[this.G] = item2;
        return item2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i, int i2) {
        this.h.b = 32;
        this.h.d = ((long) i) | (((long) i2) << 32);
        this.h.j = Integer.MAX_VALUE & (32 + i + i2);
        Item a2 = a(this.h);
        if (a2 == null) {
            String str = this.H[i].g;
            String str2 = this.H[i2].g;
            this.h.c = m1c(getCommonSuperClass(str, str2));
            a2 = new Item(0, this.h);
            b(a2);
        }
        return a2.c;
    }

    protected String getCommonSuperClass(String str, String str2) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Class<?> cls = Class.forName(str.replace('/', '.'), false, classLoader);
            Class<?> cls2 = Class.forName(str2.replace('/', '.'), false, classLoader);
            if (cls.isAssignableFrom(cls2)) {
                return str;
            }
            if (cls2.isAssignableFrom(cls)) {
                return str2;
            }
            if (cls.isInterface() || cls2.isInterface()) {
                return "java/lang/Object";
            }
            do {
                cls = cls.getSuperclass();
            } while (!cls.isAssignableFrom(cls2));
            return cls.getName().replace('.', '/');
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    private Item a(Item item) {
        Item item2 = this.e[item.j % this.e.length];
        while (item2 != null && (item2.b != item.b || !item.a(item2))) {
            item2 = item2.k;
        }
        return item2;
    }

    private void b(Item item) {
        if (this.c + this.G > this.f) {
            int length = this.e.length;
            int i = (length * 2) + 1;
            Item[] itemArr = new Item[i];
            for (int i2 = length - 1; i2 >= 0; i2--) {
                Item item2 = this.e[i2];
                while (item2 != null) {
                    int length2 = item2.j % itemArr.length;
                    item2 = item2.k;
                    item2.k = itemArr[length2];
                    itemArr[length2] = item2;
                }
            }
            this.e = itemArr;
            this.f = (int) (((double) i) * 0.75d);
        }
        int length3 = item.j % this.e.length;
        item.k = this.e[length3];
        this.e[length3] = item;
    }

    private void a(int i, int i2, int i3) {
        this.d.b(i, i2).putShort(i3);
    }

    private void b(int i, int i2, int i3) {
        this.d.a(i, i2).putShort(i3);
    }

    static {
        _clinit_();
        byte[] bArr = new byte[220];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ("AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKJJJJJJJJJJJJJJJJJJ".charAt(i) - 'A');
        }
        a = bArr;
    }

    static void _clinit_() {
    }
}
