package com.esotericsoftware.reflectasm;

import com.esotericsoftware.asm.ClassWriter;
import com.esotericsoftware.asm.Label;
import com.esotericsoftware.asm.MethodVisitor;
import com.esotericsoftware.asm.Opcodes;
import com.esotericsoftware.asm.Type;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/reflectasm/MethodAccess.class */
public abstract class MethodAccess {
	private String[] methodNames;
	private Class[][] parameterTypes;
	private Class[] returnTypes;

	public abstract Object invoke(Object obj, int i, Object... objArr);

	public Object invoke(Object obj, String str, Class[] clsArr, Object... objArr) {
		return invoke(obj, getIndex(str, clsArr), objArr);
	}

	public Object invoke(Object obj, String str, Object... objArr) {
		return invoke(obj, getIndex(str, objArr == null ? 0 : objArr.length), objArr);
	}

	public int getIndex(String str) {
		int length = this.methodNames.length;
		for (int i = 0; i < length; i++) {
			if (this.methodNames[i].equals(str)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Unable to find non-private method: " + str);
	}

	public int getIndex(String str, Class... clsArr) {
		int length = this.methodNames.length;
		for (int i = 0; i < length; i++) {
			if (this.methodNames[i].equals(str) && Arrays.equals(clsArr, this.parameterTypes[i])) {
				return i;
			}
		}
		throw new IllegalArgumentException("Unable to find non-private method: " + str + " " + Arrays.toString(clsArr));
	}

	public int getIndex(String str, int i) {
		int length = this.methodNames.length;
		for (int i2 = 0; i2 < length; i2++) {
			if (this.methodNames[i2].equals(str) && this.parameterTypes[i2].length == i) {
				return i2;
			}
		}
		throw new IllegalArgumentException("Unable to find non-private method: " + str + " with " + i + " params.");
	}

	public String[] getMethodNames() {
		return this.methodNames;
	}

	public Class[][] getParameterTypes() {
		return this.parameterTypes;
	}

	public Class[] getReturnTypes() {
		return this.returnTypes;
	}

	public static MethodAccess get(Class cls) {
		Class<?> cls2;
		int i;
		ArrayList arrayList = new ArrayList();
		boolean isInterface = cls.isInterface();
		if (!isInterface) {
			for (Class cls3 = cls; cls3 != Object.class; cls3 = cls3.getSuperclass()) {
				addDeclaredMethodsToList(cls3, arrayList);
			}
		} else {
			recursiveAddInterfaceMethodsToList(cls, arrayList);
		}
		int size = arrayList.size();
		String[] strArr = new String[size];
		Class[][] clsArr = new Class[size];
		Class[] clsArr2 = new Class[size];
		for (int i2 = 0; i2 < size; i2++) {
			Method method = (Method) arrayList.get(i2);
			strArr[i2] = method.getName();
			clsArr[i2] = method.getParameterTypes();
			clsArr2[i2] = method.getReturnType();
		}
		String name = cls.getName();
		String str = name + "MethodAccess";
		if (str.startsWith("java.")) {
			str = "reflectasm." + str;
		}
		AccessClassLoader accessClassLoader = AccessClassLoader.get(cls);
		try {
			cls2 = accessClassLoader.loadClass(str);
		} catch (ClassNotFoundException e) {
			synchronized (accessClassLoader) {
				try {
					cls2 = accessClassLoader.loadClass(str);
				} catch (ClassNotFoundException e2) {
					String replace = str.replace('.', '/');
					String replace2 = name.replace('.', '/');
					ClassWriter classWriter = new ClassWriter(1);
					classWriter.visit(Opcodes.V1_1, 33, replace, null, "com/esotericsoftware/reflectasm/MethodAccess", null);
					MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
					visitMethod.visitCode();
					visitMethod.visitVarInsn(25, 0);
					visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/esotericsoftware/reflectasm/MethodAccess", "<init>", "()V");
					visitMethod.visitInsn(Opcodes.RETURN);
					visitMethod.visitMaxs(0, 0);
					visitMethod.visitEnd();
					MethodVisitor visitMethod2 = classWriter.visitMethod(Opcodes.LOR, "invoke", "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
					visitMethod2.visitCode();
					if (!arrayList.isEmpty()) {
						visitMethod2.visitVarInsn(25, 1);
						visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, replace2);
						visitMethod2.visitVarInsn(58, 4);
						visitMethod2.visitVarInsn(21, 2);
						Label[] labelArr = new Label[size];
						for (int i3 = 0; i3 < size; i3++) {
							labelArr[i3] = new Label();
						}
						Label label = new Label();
						visitMethod2.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
						StringBuilder sb = new StringBuilder(128);
						for (int i4 = 0; i4 < size; i4++) {
							visitMethod2.visitLabel(labelArr[i4]);
							if (i4 == 0) {
								visitMethod2.visitFrame(1, 1, new Object[]{replace2}, 0, null);
							} else {
								visitMethod2.visitFrame(3, 0, null, 0, null);
							}
							visitMethod2.visitVarInsn(25, 4);
							sb.setLength(0);
							sb.append('(');
							Class[] clsArr3 = clsArr[i4];
							Class cls4 = clsArr2[i4];
							for (int i5 = 0; i5 < clsArr3.length; i5++) {
								visitMethod2.visitVarInsn(25, 3);
								visitMethod2.visitIntInsn(16, i5);
								visitMethod2.visitInsn(50);
								Type type = Type.getType(clsArr3[i5]);
								switch (type.getSort()) {
									case 1:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
										break;
									case 2:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
										break;
									case 3:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
										break;
									case 4:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
										break;
									case 5:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
										break;
									case 6:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
										break;
									case 7:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
										break;
									case 8:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
										visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
										break;
									case 9:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, type.getDescriptor());
										break;
									case 10:
										visitMethod2.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
										break;
								}
								sb.append(type.getDescriptor());
							}
							sb.append(')');
							sb.append(Type.getDescriptor(cls4));
							if (isInterface) {
								i = 185;
							} else if (Modifier.isStatic(((Method) arrayList.get(i4)).getModifiers())) {
								i = 184;
							} else {
								i = 182;
							}
							visitMethod2.visitMethodInsn(i, replace2, strArr[i4], sb.toString());
							switch (Type.getType(cls4).getSort()) {
								case 0:
									visitMethod2.visitInsn(1);
									break;
								case 1:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
									break;
								case 2:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
									break;
								case 3:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
									break;
								case 4:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
									break;
								case 5:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
									break;
								case 6:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
									break;
								case 7:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
									break;
								case 8:
									visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
									break;
							}
							visitMethod2.visitInsn(Opcodes.ARETURN);
						}
						visitMethod2.visitLabel(label);
						visitMethod2.visitFrame(3, 0, null, 0, null);
					}
					visitMethod2.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
					visitMethod2.visitInsn(89);
					visitMethod2.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
					visitMethod2.visitInsn(89);
					visitMethod2.visitLdcInsn("Method not found: ");
					visitMethod2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
					visitMethod2.visitVarInsn(21, 2);
					visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
					visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
					visitMethod2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
					visitMethod2.visitInsn(Opcodes.ATHROW);
					visitMethod2.visitMaxs(0, 0);
					visitMethod2.visitEnd();
					classWriter.visitEnd();
					cls2 = accessClassLoader.defineClass(str, classWriter.toByteArray());
				}
			}
		}
		try {
			MethodAccess methodAccess = (MethodAccess) cls2.newInstance();
			methodAccess.methodNames = strArr;
			methodAccess.parameterTypes = clsArr;
			methodAccess.returnTypes = clsArr2;
			return methodAccess;
		} catch (Throwable th) {
			throw new RuntimeException("Error constructing method access class: " + str, th);
		}
	}

	private static void addDeclaredMethodsToList(Class cls, ArrayList<Method> arrayList) {
		Method[] declaredMethods = cls.getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (!Modifier.isPrivate(method.getModifiers())) {
				arrayList.add(method);
			}
		}
	}

	private static void recursiveAddInterfaceMethodsToList(Class cls, ArrayList<Method> arrayList) {
		addDeclaredMethodsToList(cls, arrayList);
		for (Class<?> cls2 : cls.getInterfaces()) {
			recursiveAddInterfaceMethodsToList(cls2, arrayList);
		}
	}
}
