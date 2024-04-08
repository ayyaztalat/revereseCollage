package com.picspool.lib.sticker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.resource.DMSingleStickerRes;
import com.picspool.lib.sticker.util.DMRenderer;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sticker.util.DM_ImageBackground;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;

/* loaded from: classes3.dex */
public class DMStickersRenderer implements DMRenderer {
    protected BitmapDrawable foreGroundDrawable;
    protected DM_ImageBackground mBg;
    protected DMStickerStateCallback mCallback;
    protected DMStickerBMRenderable mCurRenderable;
    protected GestureDetector mGesture;
    protected OnStickerSaveListener mSaveListener;
    protected List<DMStickerBMRenderable> mSprites = new LinkedList();
    protected DM_ImageTransformPanel mTransPanel;
    protected boolean mVisible;
    protected DMWBBorderRes wbBorderRes;

    /* loaded from: classes3.dex */
    public interface OnStickerSaveListener {
        void stickerSave(boolean z, int i);
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void setOnStickerSaveListener(OnStickerSaveListener onStickerSaveListener) {
        this.mSaveListener = onStickerSaveListener;
    }

    public void addSticker(DMStickerBMRenderable dMStickerBMRenderable) {
        synchronized (this.mSprites) {
            ((LinkedList) this.mSprites).addLast(dMStickerBMRenderable);
        }
    }

    public void addStickerBorder(DMWBBorderRes dMWBBorderRes) {
        this.wbBorderRes = dMWBBorderRes;
    }

    public List<DMStickerBMRenderable> getStickerRenderables() {
        return this.mSprites;
    }

    public void removeSprite(DMStickerBMRenderable dMStickerBMRenderable) {
        synchronized (this.mSprites) {
            ((LinkedList) this.mSprites).remove(dMStickerBMRenderable);
        }
    }

    public void setIsShowShadow(boolean z) {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            synchronized (list) {
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

    public void setIsShowShadow(boolean z, int i) {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            synchronized (list) {
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

    public void onShow() {
        synchronized (this) {
            this.mVisible = true;
        }
    }

    public void onHide() {
        synchronized (this) {
            this.mVisible = false;
        }
    }

    public void clearStickers() {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            if (list.size() > 0) {
                for (int i = 0; i < this.mSprites.size(); i++) {
                    DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(i);
                    if (dMStickerBMRenderable.getSticker().getIsFreePuzzleBitmap()) {
                        dMStickerBMRenderable.getSticker().dispos();
                    }
                }
            }
            synchronized (this.mSprites) {
                this.mSprites.clear();
            }
        }
    }

    public void clearStickersOnlyFreePuzzle() {
        synchronized (this.mSprites) {
            this.mSprites.clear();
        }
    }

    public int getStickersCount() {
        return this.mSprites.size();
    }

    public int getStickersNoFreePuzzleCount() {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list == null || list.size() <= 0) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mSprites.size(); i2++) {
            if (!this.mSprites.get(i2).getSticker().getIsFreePuzzleBitmap()) {
                i++;
            }
        }
        return i;
    }

    public void setBackground(DM_ImageBackground dM_ImageBackground) {
        this.mBg = dM_ImageBackground;
    }

    public void setForegroundBitmapDrawable(BitmapDrawable bitmapDrawable) {
        this.foreGroundDrawable = bitmapDrawable;
    }

    public void setTransPanel(DM_ImageTransformPanel dM_ImageTransformPanel) {
        this.mTransPanel = dM_ImageTransformPanel;
        if (this.mGesture == null) {
            this.mGesture = new GestureDetector(this.mTransPanel.getmContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.picspool.lib.sticker.view.DMStickersRenderer.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    return false;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public void onLongPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public void onShowPress(MotionEvent motionEvent) {
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    DMStickerBMRenderable hitTestWithCallback = DMStickersRenderer.this.hitTestWithCallback(motionEvent.getX(), motionEvent.getY());
                    if (hitTestWithCallback != null) {
                        DMStickersRenderer.this.removeSprite(hitTestWithCallback);
                        DMStickersRenderer.this.addSticker(hitTestWithCallback);
                        return true;
                    }
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (DMStickersRenderer.this.mCallback != null) {
                        DMStickersRenderer.this.mCallback.onStickerChanged();
                        return true;
                    }
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onDown(MotionEvent motionEvent) {
                    DMStickerBMRenderable hitTest = DMStickersRenderer.this.hitTest(motionEvent.getX(), motionEvent.getY());
                    if (hitTest != null) {
                        if (DMStickersRenderer.this.mCallback != null) {
                            DMStickersRenderer.this.mCallback.onImageDown(hitTest.getSticker());
                            return false;
                        }
                        return false;
                    } else if (DMStickersRenderer.this.mCallback != null) {
                        DMStickersRenderer.this.mCallback.noStickerSelected();
                        return false;
                    } else {
                        return false;
                    }
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    if (DMStickersRenderer.this.mCallback != null) {
                        DMStickersRenderer.this.mCallback.onDoubleClicked();
                        return false;
                    }
                    return false;
                }
            });
        }
    }

    public void removeCurSelectedSticker() {
        DMStickerBMRenderable sprite = this.mTransPanel.getSprite();
        if (sprite != null) {
            this.mSprites.remove(sprite);
            this.mTransPanel.setStickerRenderable(null);
        }
    }

    public DMSticker getCurRemoveSticker() {
        DMStickerBMRenderable sprite = this.mTransPanel.getSprite();
        if (sprite != null) {
            return sprite.getSticker();
        }
        return null;
    }

    public void hideCurSelectedSticker() {
        this.mTransPanel.getSprite();
    }

    public void cancelSelected() {
        DM_ImageTransformPanel dM_ImageTransformPanel = this.mTransPanel;
        if (dM_ImageTransformPanel != null) {
            dM_ImageTransformPanel.isVisible = false;
        }
    }

    public void setCurSelected(int i) {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            synchronized (list) {
                DMStickerBMRenderable dMStickerBMRenderable = null;
                if (this.mSprites.size() > 0) {
                    for (int i2 = 0; i2 < this.mSprites.size() && ((dMStickerBMRenderable = this.mSprites.get(i2)) == null || dMStickerBMRenderable.getSticker().stickerId != i); i2++) {
                    }
                }
                if (dMStickerBMRenderable != null) {
                    if (this.mTransPanel == null) {
                        return;
                    }
                    this.mTransPanel.setStickerRenderable(dMStickerBMRenderable);
                    this.mTransPanel.isVisible = true;
                    this.mCurRenderable = dMStickerBMRenderable;
                }
            }
        }
    }

    public void showSticker(int i) {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            synchronized (list) {
                DMStickerBMRenderable dMStickerBMRenderable = null;
                if (this.mSprites.size() > 0) {
                    for (int i2 = 0; i2 < this.mSprites.size() && ((dMStickerBMRenderable = this.mSprites.get(i2)) == null || dMStickerBMRenderable.getSticker().stickerId != i); i2++) {
                    }
                }
                if (dMStickerBMRenderable != null) {
                    dMStickerBMRenderable.isVisible = true;
                }
            }
        }
    }

    public void hideSticker(int i) {
        List<DMStickerBMRenderable> list = this.mSprites;
        if (list != null) {
            synchronized (list) {
                DMStickerBMRenderable dMStickerBMRenderable = null;
                if (this.mSprites.size() > 0) {
                    for (int i2 = 0; i2 < this.mSprites.size() && ((dMStickerBMRenderable = this.mSprites.get(i2)) == null || dMStickerBMRenderable.getSticker().stickerId != i); i2++) {
                    }
                }
                if (dMStickerBMRenderable != null) {
                    dMStickerBMRenderable.isVisible = false;
                }
            }
        }
    }

    public void replaceCurrentStickerWithImage(Bitmap bitmap) {
        DM_ImageTransformPanel dM_ImageTransformPanel;
        DMStickerBMRenderable sprite;
        DMSticker sticker;
        if (bitmap == null || bitmap.isRecycled() || (dM_ImageTransformPanel = this.mTransPanel) == null || (sprite = dM_ImageTransformPanel.getSprite()) == null || (sticker = sprite.getSticker()) == null) {
            return;
        }
        sticker.setBitmap(bitmap);
        sprite.width = bitmap.getWidth();
        sprite.height = bitmap.getHeight();
    }

    public void replaceCurrentStickerSize(int i, int i2) {
        DMStickerBMRenderable sprite = this.mTransPanel.getSprite();
        if (sprite == null || sprite.getSticker().getisNoDrag()) {
            return;
        }
        sprite.width = i;
        sprite.height = i2;
    }

    public void replaceCurrentStickerWithImageNoBorder(Bitmap bitmap) {
        DMStickerBMRenderable sprite = this.mTransPanel.getSprite();
        DMSticker sticker = sprite.getSticker();
        sticker.setBitmap(bitmap);
        sticker.setIsShowBorder(!sticker.getIsShowBorder());
        sprite.width = bitmap.getWidth();
        sprite.height = bitmap.getHeight();
    }

    public void cloneCurSelectedSticker() {
        DMStickerBMRenderable sprite = this.mTransPanel.getSprite();
        if (sprite != null) {
            DMStickerBMRenderable dMStickerBMRenderable = null;
            try {
                dMStickerBMRenderable = sprite.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            addSticker(dMStickerBMRenderable);
            this.mTransPanel.setStickerRenderable(dMStickerBMRenderable);
        }
    }

    public DMStickerBMRenderable hitTest(float f, float f2) {
        for (int stickersCount = getStickersCount() - 1; stickersCount >= 0; stickersCount--) {
            DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(stickersCount);
            if (dMStickerBMRenderable.isVisible && dMStickerBMRenderable.containsPt(f, f2)) {
                return dMStickerBMRenderable;
            }
        }
        return null;
    }

    public DMStickerBMRenderable hitTestWithCallback(float f, float f2) {
        for (int stickersCount = getStickersCount() - 1; stickersCount >= 0; stickersCount--) {
            DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(stickersCount);
            if (dMStickerBMRenderable.isVisible && dMStickerBMRenderable.containsPt(f, f2)) {
                DMStickerStateCallback dMStickerStateCallback = this.mCallback;
                if (dMStickerStateCallback != null) {
                    dMStickerStateCallback.stickerSelected(dMStickerBMRenderable.getSticker());
                }
                return dMStickerBMRenderable;
            }
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            if (this.mTransPanel.isTransformButtonClicked((int) motionEvent.getX(), (int) motionEvent.getY())) {
                DMStickerStateCallback dMStickerStateCallback = this.mCallback;
                if (dMStickerStateCallback != null) {
                    dMStickerStateCallback.onStickerChanged();
                }
                return this.mTransPanel.onTouchEvent(motionEvent);
            } else if (this.mTransPanel.isEditButtonClicked((int) motionEvent.getX(), (int) motionEvent.getY())) {
                DMStickerStateCallback dMStickerStateCallback2 = this.mCallback;
                if (dMStickerStateCallback2 != null) {
                    dMStickerStateCallback2.editButtonClicked();
                }
                return this.mTransPanel.onTouchEvent(motionEvent);
            } else {
                DMStickerBMRenderable hitTest = hitTest(motionEvent.getX(), motionEvent.getY());
                if (hitTest != null) {
                    this.mTransPanel.isVisible = true;
                    if (this.mCurRenderable != hitTest) {
                        this.mCurRenderable = hitTest;
                        DMStickerStateCallback dMStickerStateCallback3 = this.mCallback;
                        if (dMStickerStateCallback3 != null) {
                            dMStickerStateCallback3.stickerSelected(hitTest.getSticker());
                        }
                    }
                    this.mTransPanel.setStickerRenderable(hitTest);
                } else {
                    this.mTransPanel.setStickerRenderable(null);
                    this.mCurRenderable = null;
                    DMStickerStateCallback dMStickerStateCallback4 = this.mCallback;
                    if (dMStickerStateCallback4 != null) {
                        dMStickerStateCallback4.noStickerSelected();
                    }
                }
            }
        }
        GestureDetector gestureDetector = this.mGesture;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        return this.mTransPanel.onTouchEvent(motionEvent);
    }

    @Override // com.picspool.lib.sticker.util.DMRenderer
    public void drawFrame(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (this.mVisible) {
            DM_ImageBackground dM_ImageBackground = this.mBg;
            if (dM_ImageBackground != null) {
                dM_ImageBackground.draw(canvas);
            }
            synchronized (this.mSprites) {
                if (this.mSprites != null) {
                    for (int i = 0; i < this.mSprites.size(); i++) {
                        DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(i);
                        if (dMStickerBMRenderable != null && dMStickerBMRenderable.isVisible) {
                            dMStickerBMRenderable.draw(canvas);
                        }
                    }
                }
            }
            DM_ImageTransformPanel dM_ImageTransformPanel = this.mTransPanel;
            if (dM_ImageTransformPanel != null) {
                dM_ImageTransformPanel.draw(canvas);
            }
            BitmapDrawable bitmapDrawable = this.foreGroundDrawable;
            if (bitmapDrawable != null) {
                bitmapDrawable.draw(canvas);
            }
        }
    }

    @Override // com.picspool.lib.sticker.util.DMRenderer
    public void sizeChanged(int i, int i2) {
        DM_ImageBackground dM_ImageBackground = this.mBg;
        if (dM_ImageBackground != null) {
            dM_ImageBackground.setWidth(i);
            this.mBg.setHeight(i2);
        }
    }

    public Bitmap createFrameBitmap() {
        DM_ImageTransformPanel dM_ImageTransformPanel = this.mTransPanel;
        if (dM_ImageTransformPanel != null && dM_ImageTransformPanel.isVisible) {
            this.mTransPanel.isVisible = false;
        }
        int actualWidth = this.mBg.getActualWidth();
        int actualHeight = this.mBg.getActualHeight();
        float width = actualWidth / this.mBg.getWidth();
        float height = actualHeight / this.mBg.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.scale(width, height);
        drawFrame(canvas);
        return createBitmap;
    }

    public List<DMSingleStickerRes> getResultBitmapSingle(int i) {
        DM_ImageTransformPanel dM_ImageTransformPanel = this.mTransPanel;
        if (dM_ImageTransformPanel != null && dM_ImageTransformPanel.isVisible) {
            this.mTransPanel.isVisible = false;
        }
        if (this.mSprites != null) {
            ArrayList arrayList = new ArrayList();
            int actualWidth = this.mBg.getActualWidth();
            int actualHeight = this.mBg.getActualHeight();
            this.mBg.getWidth();
            this.mBg.getHeight();
            Log.i("Test", "actualWidth:" + actualWidth + "   actualHeight:" + actualHeight);
            synchronized (this.mSprites) {
                if (this.mSprites.size() > 0) {
                    for (int i2 = 0; i2 < this.mSprites.size(); i2++) {
                        DMStickerBMRenderable dMStickerBMRenderable = this.mSprites.get(i2);
                        if (dMStickerBMRenderable != null && dMStickerBMRenderable.isVisible) {
                            Log.i("Test", "start draw sticker " + i2);
                            DMSingleStickerRes resultSticker = dMStickerBMRenderable.getResultSticker(actualWidth, actualHeight, i);
                            if (resultSticker != null) {
                                arrayList.add(resultSticker);
                            }
                        }
                    }
                }
            }
            return arrayList;
        }
        return null;
    }

    public List<DMSingleStickerRes> saveStickerToSD(Context context, String str, String str2, int i, DMSaveDoneListener dMSaveDoneListener) {
        int i2;
        int i3;
        DM_ImageBackground dM_ImageBackground = this.mBg;
        if (dM_ImageBackground != null) {
            int actualWidth = dM_ImageBackground.getActualWidth();
            i3 = this.mBg.getActualHeight();
            i2 = actualWidth;
        } else {
            i2 = 0;
            i3 = 0;
        }
        return saveStickerToSD(context, str, str2, i2, i3, i, dMSaveDoneListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x014a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f3 A[Catch: all -> 0x0157, TRY_LEAVE, TryCatch #2 {, blocks: (B:14:0x0029, B:17:0x0032, B:19:0x003a, B:21:0x0044, B:23:0x004c, B:25:0x0052, B:27:0x0058, B:33:0x00c4, B:35:0x00c9, B:37:0x00cf, B:77:0x014a, B:50:0x00e4, B:55:0x00ed, B:57:0x00f3, B:54:0x00ea, B:63:0x00fe, B:68:0x0107, B:70:0x010d, B:72:0x0113, B:73:0x0116, B:67:0x0104, B:74:0x0117, B:75:0x012f, B:79:0x0151, B:80:0x0155), top: B:95:0x0029, inners: #0, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00e4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public List<DMSingleStickerRes> saveStickerToSD(Context r19, String r20, String r21, int r22, int r23, int r24, com.picspool.lib.bitmap.output.save.DMSaveDoneListener r25) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.sticker.view.DMStickersRenderer.saveStickerToSD(android.content.Context, java.lang.String, java.lang.String, int, int, int, com.picspool.lib.bitmap.output.save.DMSaveDoneListener):java.util.List");
    }

    public void setCallback(DMStickerStateCallback dMStickerStateCallback) {
        this.mCallback = dMStickerStateCallback;
    }

    public DMStickerBMRenderable getCurRenderable() {
        return this.mCurRenderable;
    }
}
