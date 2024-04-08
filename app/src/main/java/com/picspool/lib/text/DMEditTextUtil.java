package com.picspool.lib.text;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.DMTextStickerView;
import com.picspool.lib.text.edit.DMEditTextView;
import com.picspool.lib.text.font.DMFontList;
import com.picspool.lib.text.sticker.DMSmallTextBMSticker;
import com.picspool.lib.text.util.DMBlurUtil;

/* loaded from: classes3.dex */
public class DMEditTextUtil {
    private boolean addTextFlag = false;
    private DMEditTextView editTextView;
    private OnEditingListener finishEditTextCall;
    protected ViewGroup rootView;
    protected DMTextStickerView showTextView;

    /* loaded from: classes3.dex */
    public interface OnEditingListener {
        void findshEditing();

        void startEditing();
    }

    public DMEditTextUtil(ViewGroup viewGroup, DMTextStickerView dMTextStickerView) {
        this.rootView = viewGroup;
        this.showTextView = dMTextStickerView;
        dMTextStickerView.setStickerViewClickListener(new DMTextStickerView.StikcerViewOnClickListener() { // from class: com.picspool.lib.text.DMEditTextUtil.1
            @Override // com.picspool.lib.text.DMTextStickerView.StikcerViewOnClickListener
            public void clickSticker(DMSticker dMSticker) {
                if (dMSticker != null && (dMSticker instanceof DMSmallTextBMSticker)) {
                    DMEditTextUtil.this.editText(((DMSmallTextBMSticker) dMSticker).getTextDrawer());
                    DMEditTextUtil.this.showTextView.setSurfaceVisibility(4);
                }
            }

            @Override // com.picspool.lib.text.DMTextStickerView.StikcerViewOnClickListener
            public void deleteSticker(DMSticker dMSticker) {
                if (dMSticker != null && (dMSticker instanceof DMSmallTextBMSticker)) {
                    ((DMSmallTextBMSticker) dMSticker).releaseImage();
                }
            }
        });
    }

    public void setShowSize(RectF rectF) {
        this.showTextView.changeShowSufaceSize(rectF);
    }

    public void setTextSize(RectF rectF) {
        this.showTextView.changeSufaceSize(rectF);
    }

    public DMTextStickerView getShowTextView() {
        return this.showTextView;
    }

    public void setOnEditingListener(OnEditingListener onEditingListener) {
        this.finishEditTextCall = onEditingListener;
    }

    public void addText() {
        com.picspool.lib.text.draw.DMTextDrawer dMTextDrawer = new com.picspool.lib.text.draw.DMTextDrawer(this.rootView.getContext(), "");
        dMTextDrawer.setTypeface(DMFontList.getTfList().get(0));
        dMTextDrawer.setTypefaceIndex(0);
        dMTextDrawer.setPaintColorIndex(33);
        addText(dMTextDrawer);
    }

    private void addText(com.picspool.lib.text.draw.DMTextDrawer dMTextDrawer) {
        if (this.editTextView == null) {
            createEditView();
        }
        if (this.editTextView != null) {
            try {
                this.showTextView.setSurfaceVisibility(4);
                this.editTextView.setVisibility(0);
                if (this.finishEditTextCall != null) {
                    this.finishEditTextCall.startEditing();
                }
                this.editTextView.editText(dMTextDrawer);
            } catch (Exception unused) {
            }
        }
        this.addTextFlag = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void editText(com.picspool.lib.text.draw.DMTextDrawer dMTextDrawer) {
        OnEditingListener onEditingListener = this.finishEditTextCall;
        if (onEditingListener != null) {
            onEditingListener.startEditing();
        }
        if (this.editTextView == null) {
            createEditView();
        }
        this.editTextView.setVisibility(0);
        this.editTextView.editText(dMTextDrawer);
        this.addTextFlag = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishEdit(com.picspool.lib.text.draw.DMTextDrawer dMTextDrawer) {
        this.editTextView.setVisibility(4);
        if (this.addTextFlag) {
            if (dMTextDrawer != null && dMTextDrawer.getTextString() != null && dMTextDrawer.getTextString().length() != 0) {
                DMSmallTextBMSticker dMSmallTextBMSticker = new DMSmallTextBMSticker(dMTextDrawer, this.showTextView.getWidth());
                dMSmallTextBMSticker.updateBitmap();
                this.showTextView.addTextSticker(dMSmallTextBMSticker);
            }
        } else {
            DMSticker selectedSticker = this.showTextView.getSelectedSticker();
            if (selectedSticker != null && (selectedSticker instanceof DMSmallTextBMSticker)) {
                DMSmallTextBMSticker dMSmallTextBMSticker2 = (DMSmallTextBMSticker) selectedSticker;
                dMSmallTextBMSticker2.updateBitmap();
                this.showTextView.updateTextSticker(dMSmallTextBMSticker2.getWidth(), dMSmallTextBMSticker2.getHeight());
            }
        }
        releaseEditView();
        this.showTextView.setSurfaceVisibility(0);
        OnEditingListener onEditingListener = this.finishEditTextCall;
        if (onEditingListener != null) {
            onEditingListener.findshEditing();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelEdit() {
        DMSticker selectedSticker = this.showTextView.getSelectedSticker();
        if (selectedSticker != null && (selectedSticker instanceof DMSmallTextBMSticker)) {
            DMSmallTextBMSticker dMSmallTextBMSticker = (DMSmallTextBMSticker) selectedSticker;
            dMSmallTextBMSticker.updateBitmap();
            this.showTextView.updateTextSticker(dMSmallTextBMSticker.getWidth(), dMSmallTextBMSticker.getHeight());
        }
        this.editTextView.setVisibility(4);
        this.showTextView.setSurfaceVisibility(0);
        releaseEditView();
        OnEditingListener onEditingListener = this.finishEditTextCall;
        if (onEditingListener != null) {
            onEditingListener.findshEditing();
        }
    }

    private void createEditView() {
        this.editTextView = new DMEditTextView(this.rootView.getContext());
        this.rootView.addView(this.editTextView, new FrameLayout.LayoutParams(-1, -1));
        this.editTextView.setEditingChangedListener(new DMEditTextView.EditingChangedListener() { // from class: com.picspool.lib.text.DMEditTextUtil.2
            @Override // com.picspool.lib.text.edit.DMEditTextView.EditingChangedListener
            public void finishEditing(com.picspool.lib.text.draw.DMTextDrawer dMTextDrawer) {
                DMEditTextUtil.this.finishEdit(dMTextDrawer);
            }

            @Override // com.picspool.lib.text.edit.DMEditTextView.EditingChangedListener
            public void cancelEditing() {
                DMEditTextUtil.this.cancelEdit();
            }
        });
    }

    private void releaseEditView() {
        DMEditTextView dMEditTextView = this.editTextView;
        if (dMEditTextView != null) {
            this.rootView.removeView(dMEditTextView);
            this.editTextView = null;
        }
    }

    public void addTextWithBlurBackground(Activity activity) {
        if (DMBitmapIoCache.getBitmap("text_blur_bitmap.jpg") == null) {
            loadBlurCache(activity);
        }
        addText();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadBlurCache(Activity activity) {
        View childAt = ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
        childAt.setDrawingCacheEnabled(true);
        Bitmap drawingCache = childAt.getDrawingCache();
        Bitmap fastblur = DMBlurUtil.fastblur(activity, drawingCache, 23);
        Bitmap createBitmap = Bitmap.createBitmap(fastblur.getWidth(), fastblur.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        canvas.drawBitmap(fastblur, 0.0f, 0.0f, (Paint) null);
        DMBitmapIoCache.putJPG("text_blur_bitmap.jpg", createBitmap);
        childAt.setDrawingCacheEnabled(false);
        if (drawingCache != null && !drawingCache.isRecycled()) {
            drawingCache.recycle();
        }
        if (fastblur != null && !fastblur.isRecycled()) {
            fastblur.recycle();
        }
        if (createBitmap == null || createBitmap.isRecycled()) {
            return;
        }
        createBitmap.recycle();
    }

    public Bitmap getResultBitmap() {
        return this.showTextView.getResultBitmap();
    }

    public void resume() {
        DMEditTextView dMEditTextView = this.editTextView;
        if (dMEditTextView != null) {
            dMEditTextView.cancelEdit();
            if (this.rootView.indexOfChild(this.editTextView) != -1) {
                this.rootView.removeView(this.editTextView);
            }
            this.editTextView = null;
        }
        this.showTextView.setSurfaceVisibility(0);
    }

    public void dispose() {
        this.rootView.removeAllViews();
        if (this.editTextView != null) {
            this.editTextView = null;
        }
        DMTextStickerView dMTextStickerView = this.showTextView;
        if (dMTextStickerView != null) {
            dMTextStickerView.dipose();
        }
    }
}
