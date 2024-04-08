package com.magic.video.editor.effect.cut.utils;

import android.net.Uri;

/* loaded from: classes2.dex */
public interface CSSaveDoneListener {
    void onSaveDone(String str, Uri uri);

    void onSavingException(Exception exc);
}
