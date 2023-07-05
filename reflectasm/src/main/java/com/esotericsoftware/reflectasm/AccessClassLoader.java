package com.esotericsoftware.reflectasm;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* loaded from: reflectasm-1.11.5-all.jar:com/esotericsoftware/reflectasm/AccessClassLoader.class */
class AccessClassLoader extends ClassLoader {
	private static final WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> accessClassLoaders = new WeakHashMap<>();
	private static final ClassLoader selfContextParentClassLoader = getParentClassLoader(AccessClassLoader.class);
	private static volatile AccessClassLoader selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
	private static volatile Method defineClassMethod;

	/* JADX INFO: Access modifiers changed from: package-private */
	public static AccessClassLoader get(Class cls) {
		ClassLoader parentClassLoader = getParentClassLoader(cls);
		if (selfContextParentClassLoader.equals(parentClassLoader)) {
			if (selfContextAccessClassLoader == null) {
				synchronized (accessClassLoaders) {
					if (selfContextAccessClassLoader == null) {
						selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
					}
				}
			}
			return selfContextAccessClassLoader;
		}
		synchronized (accessClassLoaders) {
			WeakReference<AccessClassLoader> weakReference = accessClassLoaders.get(parentClassLoader);
			if (weakReference != null) {
				AccessClassLoader accessClassLoader = weakReference.get();
				if (accessClassLoader != null) {
					return accessClassLoader;
				}
				accessClassLoaders.remove(parentClassLoader);
			}
			AccessClassLoader accessClassLoader2 = new AccessClassLoader(parentClassLoader);
			accessClassLoaders.put(parentClassLoader, new WeakReference<>(accessClassLoader2));
			return accessClassLoader2;
		}
	}

	public static void remove(ClassLoader classLoader) {
		if (selfContextParentClassLoader.equals(classLoader)) {
			selfContextAccessClassLoader = null;
			return;
		}
		synchronized (accessClassLoaders) {
			accessClassLoaders.remove(classLoader);
		}
	}

	public static int activeAccessClassLoaders() {
		int size = accessClassLoaders.size();
		if (selfContextAccessClassLoader != null) {
			size++;
		}
		return size;
	}

	private AccessClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	@Override // java.lang.ClassLoader
	protected Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
		return str.equals(FieldAccess.class.getName()) ? FieldAccess.class : str.equals(MethodAccess.class.getName()) ? MethodAccess.class : str.equals(ConstructorAccess.class.getName()) ? ConstructorAccess.class : str.equals(PublicConstructorAccess.class.getName()) ? PublicConstructorAccess.class : super.loadClass(str, z);
	}

	/* JADX INFO: Access modifiers changed from: package-private */
	public Class<?> defineClass(String str, byte[] bArr) throws ClassFormatError {
		try {
			return (Class) getDefineClassMethod().invoke(getParent(), str, bArr, 0, Integer.valueOf(bArr.length), getClass().getProtectionDomain());
		} catch (Exception e) {
			return defineClass(str, bArr, 0, bArr.length, getClass().getProtectionDomain());
		}
	}

	/* JADX INFO: Access modifiers changed from: package-private */
	public static boolean areInSameRuntimeClassLoader(Class cls, Class cls2) {
		if (cls.getPackage() != cls2.getPackage()) {
			return false;
		}
		ClassLoader classLoader = cls.getClassLoader();
		ClassLoader classLoader2 = cls2.getClassLoader();
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		return classLoader == null ? classLoader2 == null || classLoader2 == systemClassLoader : classLoader2 == null ? classLoader == systemClassLoader : classLoader == classLoader2;
	}

	private static ClassLoader getParentClassLoader(Class cls) {
		ClassLoader classLoader = cls.getClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}
		return classLoader;
	}

	/*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:8:0x0043
            at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:86)
            at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
            at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
	private static java.lang.reflect.Method getDefineClassMethod() throws java.lang.Exception {
        /*
            java.lang.reflect.Method r0 = com.esotericsoftware.reflectasm.AccessClassLoader.defineClassMethod
            if (r0 != 0) goto L_0x004e
            java.util.WeakHashMap<java.lang.ClassLoader, java.lang.ref.WeakReference<com.esotericsoftware.reflectasm.AccessClassLoader>> r0 = com.esotericsoftware.reflectasm.AccessClassLoader.accessClassLoaders
            r1 = r0
            r7 = r1
            monitor-enter(r0)
            java.lang.Class<java.lang.ClassLoader> r0 = java.lang.ClassLoader.class
            java.lang.String r1 = "defineClass"
            r2 = 5
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch: all -> 0x0049
            r3 = r2
            r4 = 0
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r3[r4] = r5     // Catch: all -> 0x0049
            r3 = r2
            r4 = 1
            java.lang.Class<byte[]> r5 = byte[].class
            r3[r4] = r5     // Catch: all -> 0x0049
            r3 = r2
            r4 = 2
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch: all -> 0x0049
            r3[r4] = r5     // Catch: all -> 0x0049
            r3 = r2
            r4 = 3
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch: all -> 0x0049
            r3[r4] = r5     // Catch: all -> 0x0049
            r3 = r2
            r4 = 4
            java.lang.Class<java.security.ProtectionDomain> r5 = java.security.ProtectionDomain.class
            r3[r4] = r5     // Catch: all -> 0x0049
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r1, r2)     // Catch: all -> 0x0049
            com.esotericsoftware.reflectasm.AccessClassLoader.defineClassMethod = r0     // Catch: all -> 0x0049
            java.lang.reflect.Method r0 = com.esotericsoftware.reflectasm.AccessClassLoader.defineClassMethod     // Catch: Exception -> 0x0043, all -> 0x0049
            r1 = 1
            r0.setAccessible(r1)     // Catch: Exception -> 0x0043, all -> 0x0049
            goto L_0x0044
        L_0x0043:
            r8 = move-exception
        L_0x0044:
            r0 = r7
            monitor-exit(r0)     // Catch: all -> 0x0049
            goto L_0x004e
        L_0x0049:
            r9 = move-exception
            r0 = r7
            monitor-exit(r0)     // Catch: all -> 0x0049
            r0 = r9
            throw r0
        L_0x004e:
            java.lang.reflect.Method r0 = com.esotericsoftware.reflectasm.AccessClassLoader.defineClassMethod
            return r0
        */
		throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.reflectasm.AccessClassLoader.getDefineClassMethod():java.lang.reflect.Method");
	}
}
