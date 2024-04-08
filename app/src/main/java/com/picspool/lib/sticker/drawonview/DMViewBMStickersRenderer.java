package com.picspool.lib.sticker.drawonview;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.GestureDetector;
import java.util.LinkedList;
import java.util.List;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sticker.util.DM_ImageBackground;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;
import com.picspool.lib.sticker.view.DMStickersRenderer;

/* loaded from: classes3.dex */
public class DMViewBMStickersRenderer extends DMStickersRenderer {
    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void addSticker(DMStickerBMRenderable dMStickerBMRenderable) {
        ((LinkedList) this.mSprites).addLast(dMStickerBMRenderable);
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void addStickerBorder(DMWBBorderRes dMWBBorderRes) {
        this.wbBorderRes = dMWBBorderRes;
        for (int i = 0; i < this.mSprites.size(); i++) {
            this.mSprites.get(i).addWBBorderRes(this.wbBorderRes);
        }
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void removeSprite(DMStickerBMRenderable dMStickerBMRenderable) {
        ((LinkedList) this.mSprites).remove(dMStickerBMRenderable);
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void onShow() {
        this.mVisible = true;
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void onHide() {
        this.mVisible = false;
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void clearStickersOnlyFreePuzzle() {
        int size = this.mSprites.size();
        if (this.mSprites == null || size <= 0) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < size; i++) {
            if (!this.mSprites.get(i).getSticker().getIsFreePuzzleBitmap()) {
                linkedList.add(this.mSprites.get(i));
            }
        }
        this.mSprites.clear();
        this.mSprites = null;
        this.mSprites = linkedList;
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer, com.picspool.lib.sticker.util.DMRenderer
    public void drawFrame(Canvas canvas) {
        if (this.mVisible) {
            if (this.mBg != null) {
                this.mBg.draw(canvas);
            }
            if (this.mSprites != null) {
                for (int i = 0; i < this.mSprites.size(); i++) {
                    this.mSprites.get(i).draw(canvas);
                }
            }
            if (this.mTransPanel != null) {
                this.mTransPanel.draw(canvas);
            }
            if (this.foreGroundDrawable != null) {
                this.foreGroundDrawable.draw(canvas);
            }
        }
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void setIsShowShadow(boolean z) {
        if (this.mSprites != null) {
            synchronized (this.mSprites) {
                if (this.mSprites.size() > 0) {
                    for (int i = 0; i < this.mSprites.size(); i++) {
                        DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(i);
                        if (dMStickerBMRenderable.getSticker().getIsFreePuzzleBitmap()) {
                            dMStickerBMRenderable.getSticker().setIsShowShadow(z);
                        }
                    }
                }
            }
        }
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public void setIsShowShadow(boolean z, int i) {
        if (this.mSprites != null) {
            synchronized (this.mSprites) {
                if (this.mSprites.size() > 0) {
                    for (int i2 = 0; i2 < this.mSprites.size(); i2++) {
                        DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(i2);
                        if (dMStickerBMRenderable.getSticker().getIsFreePuzzleBitmap()) {
                            dMStickerBMRenderable.getSticker().setIsShowShadow(z, i);
                        }
                    }
                }
            }
        }
    }

    protected DM_ImageBackground getmBg() {
        return this.mBg;
    }

    protected void setmBg(DM_ImageBackground dM_ImageBackground) {
        this.mBg = dM_ImageBackground;
    }

    protected BitmapDrawable getForeGroundDrawable() {
        return this.foreGroundDrawable;
    }

    protected void setForeGroundDrawable(BitmapDrawable bitmapDrawable) {
        this.foreGroundDrawable = bitmapDrawable;
    }

    protected List<DMStickerBMRenderable> getmSprites() {
        return this.mSprites;
    }

    protected void setmSprites(List<DMStickerBMRenderable> list) {
        this.mSprites = list;
    }

    protected DM_ImageTransformPanel getmTransPanel() {
        return this.mTransPanel;
    }

    protected void setmTransPanel(DM_ImageTransformPanel dM_ImageTransformPanel) {
        this.mTransPanel = dM_ImageTransformPanel;
    }

    protected boolean ismVisible() {
        return this.mVisible;
    }

    protected void setmVisible(boolean z) {
        this.mVisible = z;
    }

    protected DMStickerStateCallback getmCallback() {
        return this.mCallback;
    }

    protected void setmCallback(DMStickerStateCallback dMStickerStateCallback) {
        this.mCallback = dMStickerStateCallback;
    }

    protected GestureDetector getmGesture() {
        return this.mGesture;
    }

    protected void setmGesture(GestureDetector gestureDetector) {
        this.mGesture = gestureDetector;
    }
}
