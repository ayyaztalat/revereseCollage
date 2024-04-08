package com.magic.video.editor.effect.gallery.present;

import android.net.Uri;
import java.util.List;

/* loaded from: classes2.dex */
public interface CSGalleryPresent {
    void deleteAll();

    void deleteItem(int i);

    void destroy();

    int getGroupCount();

    String getGroupItem(int i);

    int getSelectedImageCount();

    List<Uri> getSelectedImages();

    boolean isSelectedMax();

    void next();

    void openCamera();

    void openGallery();

    void pause();

    void resume();

    void selectedItem(Uri uri);
}
