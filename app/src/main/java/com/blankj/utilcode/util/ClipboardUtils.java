package com.blankj.utilcode.util;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;

/* loaded from: classes.dex */
public final class ClipboardUtils {
    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void copyText(CharSequence charSequence) {
        ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(C0879Utils.getApp().getPackageName(), charSequence));
    }

    public static void copyText(CharSequence charSequence, CharSequence charSequence2) {
        ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(charSequence, charSequence2));
    }

    public static void clear() {
        ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public static CharSequence getLabel() {
        CharSequence label;
        ClipDescription primaryClipDescription = ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).getPrimaryClipDescription();
        return (primaryClipDescription == null || (label = primaryClipDescription.getLabel()) == null) ? "" : label;
    }

    public static CharSequence getText() {
        CharSequence coerceToText;
        ClipData primaryClip = ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).getPrimaryClip();
        return (primaryClip == null || primaryClip.getItemCount() <= 0 || (coerceToText = primaryClip.getItemAt(0).coerceToText(C0879Utils.getApp())) == null) ? "" : coerceToText;
    }

    public static void addChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public static void removeChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ((ClipboardManager) C0879Utils.getApp().getSystemService("clipboard")).removePrimaryClipChangedListener(onPrimaryClipChangedListener);
    }
}
