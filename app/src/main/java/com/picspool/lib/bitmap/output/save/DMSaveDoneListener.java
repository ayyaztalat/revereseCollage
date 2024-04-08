package com.picspool.lib.bitmap.output.save;

import android.net.Uri;

/* loaded from: classes3.dex */
public interface DMSaveDoneListener {
    void onSaveDone(String str, Uri uri);

    void onSavingException(Exception exc);
}
