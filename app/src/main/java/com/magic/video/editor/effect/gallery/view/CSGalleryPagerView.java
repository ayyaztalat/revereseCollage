package com.magic.video.editor.effect.gallery.view;

import android.database.Cursor;
import androidx.fragment.app.FragmentActivity;

/* loaded from: classes2.dex */
public interface CSGalleryPagerView {
    FragmentActivity getActivity();

    String getSelectFolder();

    void swapCursor(Cursor cursor);

    void switchToData(Cursor cursor);

    void switchToError();
}
