package com.esotericsoftware.reflectasm;

import com.esotericsoftware.asm.ClassWriter;
import com.esotericsoftware.asm.MethodVisitor;
import com.esotericsoftware.asm.Opcodes;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/reflectasm/ConstructorAccess.class */
public abstract class ConstructorAccess<T> {
	boolean isNonStaticMemberClass;

	public abstract T newInstance();

	public abstract T newInstance(Object obj);

	public boolean isNonStaticMemberClass() {
		return this.isNonStaticMemberClass;
	}

	/*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:24:0x00b8
            at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
            at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
            at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
	public static <T> com.esotericsoftware.reflectasm.ConstructorAccess<T> get(java.lang.Class<T> r8) {
        /*
            Method dump skipped, instructions count: 573
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
		throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.reflectasm.ConstructorAccess.get(java.lang.Class):com.esotericsoftware.reflectasm.ConstructorAccess");
	}

	private static void insertConstructor(ClassWriter classWriter, String str) {
		MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(25, 0);
		visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, "<init>", "()V");
		visitMethod.visitInsn(Opcodes.RETURN);
		visitMethod.visitMaxs(1, 1);
		visitMethod.visitEnd();
	}

	static void insertNewInstance(ClassWriter classWriter, String str) {
		MethodVisitor visitMethod = classWriter.visitMethod(1, "newInstance", "()Ljava/lang/Object;", null, null);
		visitMethod.visitCode();
		visitMethod.visitTypeInsn(Opcodes.NEW, str);
		visitMethod.visitInsn(89);
		visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, "<init>", "()V");
		visitMethod.visitInsn(Opcodes.ARETURN);
		visitMethod.visitMaxs(2, 1);
		visitMethod.visitEnd();
	}

	static void insertNewInstanceInner(ClassWriter classWriter, String str, String str2) {
		MethodVisitor visitMethod = classWriter.visitMethod(1, "newInstance", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
		visitMethod.visitCode();
		if (str2 != null) {
			visitMethod.visitTypeInsn(Opcodes.NEW, str);
			visitMethod.visitInsn(89);
			visitMethod.visitVarInsn(25, 1);
			visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str2);
			visitMethod.visitInsn(89);
			visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
			visitMethod.visitInsn(87);
			visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, "<init>", "(L" + str2 + ";)V");
			visitMethod.visitInsn(Opcodes.ARETURN);
			visitMethod.visitMaxs(4, 2);
		} else {
			visitMethod.visitTypeInsn(Opcodes.NEW, "java/lang/UnsupportedOperationException");
			visitMethod.visitInsn(89);
			visitMethod.visitLdcInsn("Not an inner class.");
			visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/UnsupportedOperationException", "<init>", "(Ljava/lang/String;)V");
			visitMethod.visitInsn(Opcodes.ATHROW);
			visitMethod.visitMaxs(3, 2);
		}
		visitMethod.visitEnd();
	}
}
