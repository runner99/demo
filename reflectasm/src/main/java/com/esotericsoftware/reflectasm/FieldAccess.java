package com.esotericsoftware.reflectasm;

import com.esotericsoftware.asm.ClassWriter;
import com.esotericsoftware.asm.Label;
import com.esotericsoftware.asm.MethodVisitor;
import com.esotericsoftware.asm.Opcodes;
import com.esotericsoftware.asm.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/reflectasm/FieldAccess.class */
public abstract class FieldAccess {
	private String[] fieldNames;
	private Class[] fieldTypes;
	private Field[] fields;

	public abstract void set(Object obj, int i, Object obj2);

	public abstract void setBoolean(Object obj, int i, boolean z);

	public abstract void setByte(Object obj, int i, byte b);

	public abstract void setShort(Object obj, int i, short s);

	public abstract void setInt(Object obj, int i, int i2);

	public abstract void setLong(Object obj, int i, long j);

	public abstract void setDouble(Object obj, int i, double d);

	public abstract void setFloat(Object obj, int i, float f);

	public abstract void setChar(Object obj, int i, char c);

	public abstract Object get(Object obj, int i);

	public abstract String getString(Object obj, int i);

	public abstract char getChar(Object obj, int i);

	public abstract boolean getBoolean(Object obj, int i);

	public abstract byte getByte(Object obj, int i);

	public abstract short getShort(Object obj, int i);

	public abstract int getInt(Object obj, int i);

	public abstract long getLong(Object obj, int i);

	public abstract double getDouble(Object obj, int i);

	public abstract float getFloat(Object obj, int i);

	public int getIndex(String str) {
		int length = this.fieldNames.length;
		for (int i = 0; i < length; i++) {
			if (this.fieldNames[i].equals(str)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Unable to find non-private field: " + str);
	}

	public int getIndex(Field field) {
		int length = this.fields.length;
		for (int i = 0; i < length; i++) {
			if (this.fields[i].equals(field)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Unable to find non-private field: " + field);
	}

	public void set(Object obj, String str, Object obj2) {
		set(obj, getIndex(str), obj2);
	}

	public Object get(Object obj, String str) {
		return get(obj, getIndex(str));
	}

	public String[] getFieldNames() {
		return this.fieldNames;
	}

	public Class[] getFieldTypes() {
		return this.fieldTypes;
	}

	public int getFieldCount() {
		return this.fieldTypes.length;
	}

	public Field[] getFields() {
		return this.fields;
	}

	public void setFields(Field[] fieldArr) {
		this.fields = fieldArr;
	}

	public static FieldAccess get(Class cls) {
		Class<?> cls2;
		ArrayList arrayList = new ArrayList();
		for (Class cls3 = cls; cls3 != Object.class; cls3 = cls3.getSuperclass()) {
			Field[] declaredFields = cls3.getDeclaredFields();
			for (Field field : declaredFields) {
				int modifiers = field.getModifiers();
				if (!Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers)) {
					arrayList.add(field);
				}
			}
		}
		String[] strArr = new String[arrayList.size()];
		Class[] clsArr = new Class[arrayList.size()];
		int length = strArr.length;
		for (int i = 0; i < length; i++) {
			strArr[i] = ((Field) arrayList.get(i)).getName();
			clsArr[i] = ((Field) arrayList.get(i)).getType();
		}
		String name = cls.getName();
		String str = name + "FieldAccess";
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
					ClassWriter classWriter = new ClassWriter(0);
					classWriter.visit(Opcodes.V1_1, 33, replace, null, "com/esotericsoftware/reflectasm/FieldAccess", null);
					insertConstructor(classWriter);
					insertGetObject(classWriter, replace2, arrayList);
					insertSetObject(classWriter, replace2, arrayList);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.BOOLEAN_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.BOOLEAN_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.BYTE_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.BYTE_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.SHORT_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.SHORT_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.INT_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.INT_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.LONG_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.LONG_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.DOUBLE_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.DOUBLE_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.FLOAT_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.FLOAT_TYPE);
					insertGetPrimitive(classWriter, replace2, arrayList, Type.CHAR_TYPE);
					insertSetPrimitive(classWriter, replace2, arrayList, Type.CHAR_TYPE);
					insertGetString(classWriter, replace2, arrayList);
					classWriter.visitEnd();
					cls2 = accessClassLoader.defineClass(str, classWriter.toByteArray());
				}
			}
		}
		try {
			FieldAccess fieldAccess = (FieldAccess) cls2.newInstance();
			fieldAccess.fieldNames = strArr;
			fieldAccess.fieldTypes = clsArr;
			fieldAccess.fields = (Field[]) arrayList.toArray(new Field[arrayList.size()]);
			return fieldAccess;
		} catch (Throwable th) {
			throw new RuntimeException("Error constructing field access class: " + str, th);
		}
	}

	private static void insertConstructor(ClassWriter classWriter) {
		MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(25, 0);
		visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/esotericsoftware/reflectasm/FieldAccess", "<init>", "()V");
		visitMethod.visitInsn(Opcodes.RETURN);
		visitMethod.visitMaxs(1, 1);
		visitMethod.visitEnd();
	}

	private static void insertSetObject(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
		int i = 6;
		MethodVisitor visitMethod = classWriter.visitMethod(1, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(21, 2);
		if (!arrayList.isEmpty()) {
			i = 6 - 1;
			Label[] labelArr = new Label[arrayList.size()];
			int length = labelArr.length;
			for (int i2 = 0; i2 < length; i2++) {
				labelArr[i2] = new Label();
			}
			Label label = new Label();
			visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
			int length2 = labelArr.length;
			for (int i3 = 0; i3 < length2; i3++) {
				Field field = arrayList.get(i3);
				Type type = Type.getType(field.getType());
				visitMethod.visitLabel(labelArr[i3]);
				visitMethod.visitFrame(3, 0, null, 0, null);
				visitMethod.visitVarInsn(25, 1);
				visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str);
				visitMethod.visitVarInsn(25, 3);
				switch (type.getSort()) {
					case 1:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
						break;
					case 2:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
						break;
					case 3:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
						break;
					case 4:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
						break;
					case 5:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
						break;
					case 6:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
						break;
					case 7:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
						break;
					case 8:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
						visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
						break;
					case 9:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, type.getDescriptor());
						break;
					case 10:
						visitMethod.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
						break;
				}
				visitMethod.visitFieldInsn(Opcodes.PUTFIELD, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), type.getDescriptor());
				visitMethod.visitInsn(Opcodes.RETURN);
			}
			visitMethod.visitLabel(label);
			visitMethod.visitFrame(3, 0, null, 0, null);
		}
		MethodVisitor insertThrowExceptionForFieldNotFound = insertThrowExceptionForFieldNotFound(visitMethod);
		insertThrowExceptionForFieldNotFound.visitMaxs(i, 4);
		insertThrowExceptionForFieldNotFound.visitEnd();
	}

	private static void insertGetObject(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
		int i = 6;
		MethodVisitor visitMethod = classWriter.visitMethod(1, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(21, 2);
		if (!arrayList.isEmpty()) {
			i = 6 - 1;
			Label[] labelArr = new Label[arrayList.size()];
			int length = labelArr.length;
			for (int i2 = 0; i2 < length; i2++) {
				labelArr[i2] = new Label();
			}
			Label label = new Label();
			visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
			int length2 = labelArr.length;
			for (int i3 = 0; i3 < length2; i3++) {
				Field field = arrayList.get(i3);
				visitMethod.visitLabel(labelArr[i3]);
				visitMethod.visitFrame(3, 0, null, 0, null);
				visitMethod.visitVarInsn(25, 1);
				visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str);
				visitMethod.visitFieldInsn(Opcodes.GETFIELD, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), Type.getDescriptor(field.getType()));
				switch (Type.getType(field.getType()).getSort()) {
					case 1:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
						break;
					case 2:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
						break;
					case 3:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
						break;
					case 4:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
						break;
					case 5:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
						break;
					case 6:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
						break;
					case 7:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
						break;
					case 8:
						visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
						break;
				}
				visitMethod.visitInsn(Opcodes.ARETURN);
			}
			visitMethod.visitLabel(label);
			visitMethod.visitFrame(3, 0, null, 0, null);
		}
		insertThrowExceptionForFieldNotFound(visitMethod);
		visitMethod.visitMaxs(i, 3);
		visitMethod.visitEnd();
	}

	private static void insertGetString(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
		int i = 6;
		MethodVisitor visitMethod = classWriter.visitMethod(1, "getString", "(Ljava/lang/Object;I)Ljava/lang/String;", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(21, 2);
		if (!arrayList.isEmpty()) {
			i = 6 - 1;
			Label[] labelArr = new Label[arrayList.size()];
			Label label = new Label();
			boolean z = false;
			int length = labelArr.length;
			for (int i2 = 0; i2 < length; i2++) {
				if (arrayList.get(i2).getType().equals(String.class)) {
					labelArr[i2] = new Label();
				} else {
					labelArr[i2] = label;
					z = true;
				}
			}
			Label label2 = new Label();
			visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label2, labelArr);
			int length2 = labelArr.length;
			for (int i3 = 0; i3 < length2; i3++) {
				if (!labelArr[i3].equals(label)) {
					Field field = arrayList.get(i3);
					visitMethod.visitLabel(labelArr[i3]);
					visitMethod.visitFrame(3, 0, null, 0, null);
					visitMethod.visitVarInsn(25, 1);
					visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str);
					visitMethod.visitFieldInsn(Opcodes.GETFIELD, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), "Ljava/lang/String;");
					visitMethod.visitInsn(Opcodes.ARETURN);
				}
			}
			if (z) {
				visitMethod.visitLabel(label);
				visitMethod.visitFrame(3, 0, null, 0, null);
				insertThrowExceptionForFieldType(visitMethod, "String");
			}
			visitMethod.visitLabel(label2);
			visitMethod.visitFrame(3, 0, null, 0, null);
		}
		insertThrowExceptionForFieldNotFound(visitMethod);
		visitMethod.visitMaxs(i, 3);
		visitMethod.visitEnd();
	}

	private static void insertSetPrimitive(ClassWriter classWriter, String str, ArrayList<Field> arrayList, Type type) {
		int i;
		String str2;
		int i2 = 6;
		int i3 = 4;
		String descriptor = type.getDescriptor();
		switch (type.getSort()) {
			case 1:
				str2 = "setBoolean";
				i = 21;
				break;
			case 2:
				str2 = "setChar";
				i = 21;
				break;
			case 3:
				str2 = "setByte";
				i = 21;
				break;
			case 4:
				str2 = "setShort";
				i = 21;
				break;
			case 5:
				str2 = "setInt";
				i = 21;
				break;
			case 6:
				str2 = "setFloat";
				i = 23;
				break;
			case 7:
				str2 = "setLong";
				i = 22;
				i3 = 4 + 1;
				break;
			case 8:
				str2 = "setDouble";
				i = 24;
				i3 = 4 + 1;
				break;
			default:
				str2 = "set";
				i = 25;
				break;
		}
		MethodVisitor visitMethod = classWriter.visitMethod(1, str2, "(Ljava/lang/Object;I" + descriptor + ")V", null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(21, 2);
		if (!arrayList.isEmpty()) {
			i2 = 6 - 1;
			Label[] labelArr = new Label[arrayList.size()];
			Label label = new Label();
			boolean z = false;
			int length = labelArr.length;
			for (int i4 = 0; i4 < length; i4++) {
				if (Type.getType(arrayList.get(i4).getType()).equals(type)) {
					labelArr[i4] = new Label();
				} else {
					labelArr[i4] = label;
					z = true;
				}
			}
			Label label2 = new Label();
			visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label2, labelArr);
			int length2 = labelArr.length;
			for (int i5 = 0; i5 < length2; i5++) {
				if (!labelArr[i5].equals(label)) {
					Field field = arrayList.get(i5);
					visitMethod.visitLabel(labelArr[i5]);
					visitMethod.visitFrame(3, 0, null, 0, null);
					visitMethod.visitVarInsn(25, 1);
					visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str);
					visitMethod.visitVarInsn(i, 3);
					visitMethod.visitFieldInsn(Opcodes.PUTFIELD, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), descriptor);
					visitMethod.visitInsn(Opcodes.RETURN);
				}
			}
			if (z) {
				visitMethod.visitLabel(label);
				visitMethod.visitFrame(3, 0, null, 0, null);
				insertThrowExceptionForFieldType(visitMethod, type.getClassName());
			}
			visitMethod.visitLabel(label2);
			visitMethod.visitFrame(3, 0, null, 0, null);
		}
		MethodVisitor insertThrowExceptionForFieldNotFound = insertThrowExceptionForFieldNotFound(visitMethod);
		insertThrowExceptionForFieldNotFound.visitMaxs(i2, i3);
		insertThrowExceptionForFieldNotFound.visitEnd();
	}

	private static void insertGetPrimitive(ClassWriter classWriter, String str, ArrayList<Field> arrayList, Type type) {
		int i;
		String str2;
		int i2 = 6;
		String descriptor = type.getDescriptor();
		switch (type.getSort()) {
			case 1:
				str2 = "getBoolean";
				i = 172;
				break;
			case 2:
				str2 = "getChar";
				i = 172;
				break;
			case 3:
				str2 = "getByte";
				i = 172;
				break;
			case 4:
				str2 = "getShort";
				i = 172;
				break;
			case 5:
				str2 = "getInt";
				i = 172;
				break;
			case 6:
				str2 = "getFloat";
				i = 174;
				break;
			case 7:
				str2 = "getLong";
				i = 173;
				break;
			case 8:
				str2 = "getDouble";
				i = 175;
				break;
			default:
				str2 = "get";
				i = 176;
				break;
		}
		MethodVisitor visitMethod = classWriter.visitMethod(1, str2, "(Ljava/lang/Object;I)" + descriptor, null, null);
		visitMethod.visitCode();
		visitMethod.visitVarInsn(21, 2);
		if (!arrayList.isEmpty()) {
			i2 = 6 - 1;
			Label[] labelArr = new Label[arrayList.size()];
			Label label = new Label();
			boolean z = false;
			int length = labelArr.length;
			for (int i3 = 0; i3 < length; i3++) {
				if (Type.getType(arrayList.get(i3).getType()).equals(type)) {
					labelArr[i3] = new Label();
				} else {
					labelArr[i3] = label;
					z = true;
				}
			}
			Label label2 = new Label();
			visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label2, labelArr);
			int length2 = labelArr.length;
			for (int i4 = 0; i4 < length2; i4++) {
				Field field = arrayList.get(i4);
				if (!labelArr[i4].equals(label)) {
					visitMethod.visitLabel(labelArr[i4]);
					visitMethod.visitFrame(3, 0, null, 0, null);
					visitMethod.visitVarInsn(25, 1);
					visitMethod.visitTypeInsn(Opcodes.CHECKCAST, str);
					visitMethod.visitFieldInsn(Opcodes.GETFIELD, field.getDeclaringClass().getName().replace('.', '/'), field.getName(), descriptor);
					visitMethod.visitInsn(i);
				}
			}
			if (z) {
				visitMethod.visitLabel(label);
				visitMethod.visitFrame(3, 0, null, 0, null);
				insertThrowExceptionForFieldType(visitMethod, type.getClassName());
			}
			visitMethod.visitLabel(label2);
			visitMethod.visitFrame(3, 0, null, 0, null);
		}
		MethodVisitor insertThrowExceptionForFieldNotFound = insertThrowExceptionForFieldNotFound(visitMethod);
		insertThrowExceptionForFieldNotFound.visitMaxs(i2, 3);
		insertThrowExceptionForFieldNotFound.visitEnd();
	}

	private static MethodVisitor insertThrowExceptionForFieldNotFound(MethodVisitor methodVisitor) {
		methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
		methodVisitor.visitInsn(89);
		methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
		methodVisitor.visitInsn(89);
		methodVisitor.visitLdcInsn("Field not found: ");
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
		methodVisitor.visitVarInsn(21, 2);
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
		methodVisitor.visitInsn(Opcodes.ATHROW);
		return methodVisitor;
	}

	private static MethodVisitor insertThrowExceptionForFieldType(MethodVisitor methodVisitor, String str) {
		methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
		methodVisitor.visitInsn(89);
		methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
		methodVisitor.visitInsn(89);
		methodVisitor.visitLdcInsn("Field not declared as " + str + ": ");
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
		methodVisitor.visitVarInsn(21, 2);
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
		methodVisitor.visitInsn(Opcodes.ATHROW);
		return methodVisitor;
	}
}
