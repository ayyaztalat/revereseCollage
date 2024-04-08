package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.net.Uri;
import java.util.List;

/* loaded from: classes2.dex */
public interface CSGalleryView {
    Context getContext();

    int getSelectMaxNumber();

    void onAddItem(int i);

    void onDeleteAll();

    void onDeleteItem(int i);

    void onNextBtnClick(List<Uri> list);

    void onOpenCamera();

    void onOpenGallery();

    void onShowMaxSelectedTips();

    void updateUi();
}
