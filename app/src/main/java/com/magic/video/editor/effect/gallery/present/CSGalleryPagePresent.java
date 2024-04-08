package com.magic.video.editor.effect.gallery.present;

import android.database.Cursor;
import androidx.fragment.app.FragmentActivity;

/* loaded from: classes2.dex */
public interface CSGalleryPagePresent {
    void destroy();

    FragmentActivity getActivity();

    String getSelectFolder();

    void requestPhotos(boolean z);

    void requestVideos(boolean z);

    void swapCursor(Cursor cursor);

    void switchToData(Cursor cursor);

    void switchToError();
}
