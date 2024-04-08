package com.photo.editor.square.splash.utils;

import android.os.Build;

/* loaded from: classes2.dex */
public class CSFeedBackUtil {
    public static String createEmailSubject() {
        return "black screen report";
    }

    public static String createUrlString() {
        return "https://docs.google.com/document/d/1Fh8gDnUYzyVb5NnJJ-t9xvaWdpHYFFmg2BfA8dXfsGc/edit?usp=sharing";
    }

    public static String createEmailText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Phone Info\n");
        sb.append("brand: ");
        sb.append(Build.BOARD);
        sb.append("\n");
        sb.append("model: ");
        sb.append(Build.MODEL);
        sb.append("\n");
        sb.append("time: ");
        sb.append("\n");
        sb.append("abi: ");
        if (Build.VERSION.SDK_INT >= 21) {
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr != null) {
                for (String str : strArr) {
                    if (str != null) {
                        sb.append(str);
                        sb.append("  ");
                    }
                }
            }
        } else {
            sb.append(Build.CPU_ABI);
            sb.append("  ");
            sb.append(Build.CPU_ABI2);
        }
        sb.append("\n\n");
        sb.append("1.operation steps:\n");
        sb.append("2.what happen at screen:\n\n");
        sb.append("  If you don't mind, please provide a video or image that causes a black screen to help us solve the black screen. thank you very much !\n");
        return sb.toString();
    }

    public static String[] createEmailAddress() {
        return new String[]{"wongbruce0816@gmail.com"};
    }
}
