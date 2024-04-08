package com.picspool.lib.sticker.enumoperations;

import android.content.Context;

import com.picspool.lib.sticker.resource.manager.DMCommonStickersManager;
import com.picspool.lib.sticker.resource.manager.DMCuteStickerManager;
import com.picspool.lib.sticker.resource.manager.DMEmojiStickerManager;
import com.picspool.lib.sticker.resource.manager.DMHeartStickerManager;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMStickerTypeOperation {
    private Context mContext;

    /* loaded from: classes3.dex */
    public enum StickerType {
        EMOJI,
        HEART,
        CUTE
    }

    public int getStickerTypeCount() {
        return 3;
    }

    public DMStickerTypeOperation(Context context) {
        this.mContext = context;
    }

    public int getIntPositionByStickerType(StickerType stickerType) {
        if (stickerType == StickerType.HEART) {
            return 0;
        }
        if (stickerType == StickerType.EMOJI) {
            return 1;
        }
        return stickerType == StickerType.CUTE ? 2 : -1;
    }

    public StickerType getStickerTypeByIndex(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return null;
                }
                return StickerType.CUTE;
            }
            return StickerType.EMOJI;
        }
        return StickerType.HEART;
    }

    public String getStickerPageTitleByIndex(int i) {
        if (i != 0) {
            if (i != 1) {
                return i != 2 ? "" : this.mContext.getResources().getString(R.string.cute);
            }
            return this.mContext.getResources().getString(R.string.emoji);
        }
        return this.mContext.getResources().getString(R.string.heart);
    }

    public DMCommonStickersManager getCommonStickerManagerByStickType(StickerType stickerType) {
        if (stickerType == StickerType.EMOJI) {
            return new DMEmojiStickerManager();
        }
        if (stickerType == StickerType.HEART) {
            return new DMHeartStickerManager();
        }
        if (stickerType == StickerType.CUTE) {
            return new DMCuteStickerManager();
        }
        return null;
    }
}
