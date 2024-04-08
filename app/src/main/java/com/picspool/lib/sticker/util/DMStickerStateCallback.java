package com.picspool.lib.sticker.util;

import com.picspool.lib.sticker.core.DMSticker;

/* loaded from: classes3.dex */
public interface DMStickerStateCallback {
    void editButtonClicked();

    void noStickerSelected();

    void onDoubleClicked();

    void onImageDown(DMSticker dMSticker);

    void onStickerChanged();

    void stickerSelected(DMSticker dMSticker);
}
