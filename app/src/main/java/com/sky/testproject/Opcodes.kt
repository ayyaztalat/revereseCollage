package com.sky.testproject


interface Opcodes {
    companion object {
        const val T_INT = 10

        // versions
        // int V1_1 = 3 << 16 | 45;
        // int V1_2 = 0 << 16 | 46;
        // int V1_3 = 0 << 16 | 47;
        // int V1_4 = 0 << 16 | 48;
        const val V1_5 = 0 shl 16 or 49

        // int V1_6 = 0 << 16 | 50;
        // int V1_7 = 0 << 16 | 51;
        // access flags
        const val ACC_PUBLIC = 0x0001 // class, field, method
        const val ACC_SUPER = 0x0020 // class

        // opcodes // visit method (- = idem)
        const val ACONST_NULL = 1 // -
        const val ICONST_0 = 3 // -
        const val ICONST_1 = 4 // -
        const val LCONST_0 = 9 // -
        const val LCONST_1 = 10 // -
        const val FCONST_0 = 11 // -
        const val DCONST_0 = 14 // -
        const val BIPUSH = 16 // visitIntInsn

        // int SIPUSH = 17; // -
        //    int    LDC                 = 18;                     // visitLdcInsn
        // int LDC_W = 19; // -
        // int LDC2_W = 20; // -
        const val ILOAD = 21 // visitVarInsn
        const val LLOAD = 22 // -
        const val FLOAD = 23 // -
        const val DLOAD = 24 // -
        const val ALOAD = 25 // -
        const val ISTORE = 54 // visitVarInsn
        const val LSTORE = 55 // -
        const val FSTORE = 56 // -
        const val DSTORE = 57 // -
        const val ASTORE = 58 // -
        const val IASTORE = 79 // visitInsn
        const val POP = 87 // -

        //    int    POP2                = 88;                     // -
        const val DUP = 89 // -
        const val IADD = 96 // -

        //    int    ISUB                = 100;                    // -
        const val IAND = 126 // -

        // int LAND = 127; // -
        const val IOR = 128 // -

        // int LOR = 129; // -
        // int IXOR = 130; // -
        // int LXOR = 131; // -
        // int    IINC                = 132;                    // visitIincInsn
        const val LCMP = 148 // -
        const val FCMPL = 149 // -
        const val DCMPL = 151 // -
        const val IFEQ = 153 // visitJumpInsn
        const val IFNE = 154 // -
        const val IFLE = 158 // -
        const val IF_ICMPEQ = 159 // -
        const val IF_ICMPNE = 160 // -
        const val IF_ICMPLT = 161 // -
        const val IF_ICMPGE = 162 // -
        const val IF_ICMPGT = 163 // -
        const val IF_ACMPEQ = 165 // -
        const val IF_ACMPNE = 166 // -
        const val GOTO = 167 // -
        const val RET = 169 // visitVarInsn
        const val ARETURN = 176 // -
        const val RETURN = 177 // -
        const val GETSTATIC = 178 // visitFieldInsn
        const val GETFIELD = 180 // -
        const val PUTFIELD = 181 // -
        const val INVOKEVIRTUAL = 182 // visitMethodInsn
        const val INVOKESPECIAL = 183 // -
        const val INVOKESTATIC = 184 // -
        const val INVOKEINTERFACE = 185 // -

        // int INVOKEDYNAMIC = 186; // -
        const val NEW = 187 // visitTypeInsn
        const val NEWARRAY = 188 // visitIntInsn

        // int ANEWARRAY = 189; // visitTypeInsn
        // int ARRAYLENGTH = 190; // visitInsn
        // int ATHROW = 191; // -
        const val CHECKCAST = 192 // visitTypeInsn
        const val INSTANCEOF = 193
        const val IFNULL = 198 // visitJumpInsn
        const val IFNONNULL = 199 // -
        const val GOTO_W = 200 // visitJumpInsn
        // int JSR_W = 201; // -
    }
}