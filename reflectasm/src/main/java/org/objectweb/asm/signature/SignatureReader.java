package org.objectweb.asm.signature;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypeReference;

/* loaded from: asm-5.1.jar:org/objectweb/asm/signature/SignatureReader.class */
public class SignatureReader {
    private final String a;

    public SignatureReader(String str) {
        this.a = str;
    }

    public void accept(SignatureVisitor signatureVisitor) {
        int i;
        char charAt;
        String str = this.a;
        int length = str.length();
        if (str.charAt(0) == '<') {
            i = 2;
            do {
                int indexOf = str.indexOf(58, i);
                signatureVisitor.visitFormalTypeParameter(str.substring(i - 1, indexOf));
                int i2 = indexOf + 1;
                char charAt2 = str.charAt(i2);
                if (charAt2 == 'L' || charAt2 == '[' || charAt2 == 'T') {
                    i2 = a(str, i2, signatureVisitor.visitClassBound());
                }
                while (true) {
                    i = i2 + 1;
                    charAt = str.charAt(i2);
                    if (charAt == ':') {
                        i2 = a(str, i, signatureVisitor.visitInterfaceBound());
                    }
                }
            } while (charAt != '>');
        } else {
            i = 0;
        }
        if (str.charAt(i) == '(') {
            int i3 = i + 1;
            while (str.charAt(i3) != ')') {
                i3 = a(str, i3, signatureVisitor.visitParameterType());
            }
            int a = a(str, i3 + 1, signatureVisitor.visitReturnType());
            while (a < length) {
                a = a(str, a + 1, signatureVisitor.visitExceptionType());
            }
            return;
        }
        int a2 = a(str, i, signatureVisitor.visitSuperclass());
        while (a2 < length) {
            a2 = a(str, a2, signatureVisitor.visitInterface());
        }
    }

    public void acceptType(SignatureVisitor signatureVisitor) {
        a(this.a, 0, signatureVisitor);
    }

    private static int a(String str, int i, SignatureVisitor signatureVisitor) {
        int i2 = i + 1;
        char charAt = str.charAt(i);
        switch (charAt) {
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
            case TypeReference.INSTANCEOF /* 67 */:
            case TypeReference.NEW /* 68 */:
            case TypeReference.METHOD_REFERENCE /* 70 */:
            case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
            case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.SASTORE /* 86 */:
            case Opcodes.DUP_X1 /* 90 */:
                signatureVisitor.visitBaseType(charAt);
                return i2;
            case TypeReference.CONSTRUCTOR_REFERENCE /* 69 */:
            case TypeReference.CAST /* 71 */:
            case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
            case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
            case 'L':
            case 'M':
            case 'N':
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.DASTORE /* 82 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.POP /* 87 */:
            case Opcodes.POP2 /* 88 */:
            case Opcodes.DUP /* 89 */:
            default:
                int i3 = i2;
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    i2++;
                    char charAt2 = str.charAt(i2);
                    switch (charAt2) {
                        case '.':
                        case ';':
                            if (!z) {
                                String substring = str.substring(i3, i2 - 1);
                                if (z2) {
                                    signatureVisitor.visitInnerClassType(substring);
                                } else {
                                    signatureVisitor.visitClassType(substring);
                                }
                            }
                            if (charAt2 != ';') {
                                i3 = i2;
                                z = false;
                                z2 = true;
                                break;
                            } else {
                                signatureVisitor.visitEnd();
                                return i2;
                            }
                        case '<':
                            String substring2 = str.substring(i3, i2 - 1);
                            if (z2) {
                                signatureVisitor.visitInnerClassType(substring2);
                            } else {
                                signatureVisitor.visitClassType(substring2);
                            }
                            z = true;
                            while (true) {
                                char charAt3 = str.charAt(i2);
                                switch (charAt3) {
                                    case '*':
                                        i2++;
                                        signatureVisitor.visitTypeArgument();
                                    case SignatureVisitor.EXTENDS /* 43 */:
                                    case SignatureVisitor.SUPER /* 45 */:
                                        i2 = a(str, i2 + 1, signatureVisitor.visitTypeArgument(charAt3));
                                    case '>':
                                        break;
                                    default:
                                        i2 = a(str, i2, signatureVisitor.visitTypeArgument('='));
                                }
                            }
                            break;
                    }
                }
                break;
            case Opcodes.BASTORE /* 84 */:
                int indexOf = str.indexOf(59, i2);
                signatureVisitor.visitTypeVariable(str.substring(i2, indexOf));
                return indexOf + 1;
            case Opcodes.DUP_X2 /* 91 */:
                return a(str, i2, signatureVisitor.visitArrayType());
        }
    }
}
