package com.picspool.instatextview.textview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.List;
import java.util.Random;

import com.picspool.instatextview.edit.DMEditTextView3;
import com.picspool.instatextview.labelview.DMEditLabelView3;
import com.picspool.instatextview.labelview.DMListLabelView3;
import com.picspool.instatextview.utils.DMBlurUtil;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMInstaTextView3 extends FrameLayout {
    private static List<Typeface> tfList;
    private boolean addTextFlag;
    private OnClickListener addTextListener;
    private String[] defultText;
    protected DMEditLabelView3 editLabelView;
    private DMEditTextView3 editTextView;
    private FinishAddTextCall finishAddTextCall;
    private FinishEditLabelCall finishEditLabelCall;
    private FinishEditTextCall finishEditTextCall;
    protected Handler handler;
    protected DMListLabelView3 listLabelView;
    private DMWBImageRes mBgImageRes;
    private OnDoubleClickListener onDoubleClickListener;
    private FrameLayout rootLayout;
    protected DMShowTextStickerView3 showTextView;

    /* loaded from: classes3.dex */
    public interface ClickEditTextOKListener {
        void onClickOK();
    }

    /* loaded from: classes3.dex */
    public interface FinishAddTextCall {
        void finishAddText(DMSticker dMSticker);

        void finishEditText(DMSticker dMSticker);
    }

    /* loaded from: classes3.dex */
    public interface FinishEditLabelCall {
        void findshEditing();

        void startEditing();
    }

    /* loaded from: classes3.dex */
    public interface FinishEditTextCall {
        void findshEditing();

        void startEditing();
    }

    /* loaded from: classes3.dex */
    public interface OnDoubleClickListener {
        void onDoubleClick();
    }

    public void finishLabelText(DMTextDrawer dMTextDrawer) {
    }

    public DMInstaTextView3(Context context) {
        super(context);
        this.addTextFlag = false;
        this.handler = new Handler();
        this.defultText = new String[]{"Have a Nice Day", "Have Fun", "Hello,Monday!", "Beauty", "Holidays", "Best Wishes", "Love You", "Sunshine", "Miss You", "FASHION"};
        iniView();
    }

    public DMInstaTextView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addTextFlag = false;
        this.handler = new Handler();
        this.defultText = new String[]{"Have a Nice Day", "Have Fun", "Hello,Monday!", "Beauty", "Holidays", "Best Wishes", "Love You", "Sunshine", "Miss You", "FASHION"};
        iniView();
    }

    public void iniView() {
        FrameLayout frameLayout = (FrameLayout) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutView(), (ViewGroup) null);
        this.rootLayout = frameLayout;
        DMShowTextStickerView3 dMShowTextStickerView3 = (DMShowTextStickerView3) frameLayout.findViewById(R.id.show_text_view);
        this.showTextView = dMShowTextStickerView3;
        dMShowTextStickerView3.setInstaTextView(this);
        this.mBgImageRes = null;
        addView(this.rootLayout);
    }

    public int getLayoutView() {
        return R.layout.dm_text_insta_text_view3;
    }

    public void createEditView() {
        this.editTextView = new DMEditTextView3(getContext());
        this.editTextView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.editTextView);
        this.editTextView.setInstaTextView(this);
    }

    public void releaseEditView() {
        DMEditTextView3 dMEditTextView3 = this.editTextView;
        if (dMEditTextView3 != null) {
            this.rootLayout.removeView(dMEditTextView3);
            this.editTextView = null;
        }
    }

    public void createLabelView() {
        this.editLabelView = new DMEditLabelView3(getContext());
        this.editLabelView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.editLabelView);
        this.editLabelView.setInstaTextView(this);
        this.editLabelView.setSurfaceView(this.showTextView);
        this.listLabelView = createListLabelView();
        this.listLabelView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.listLabelView);
        this.listLabelView.setVisibility(View.INVISIBLE);
        this.listLabelView.setInstaTextView(this);
        this.listLabelView.setEditLabelView(this.editLabelView);
        this.editLabelView.setListLabelView(this.listLabelView);
        this.listLabelView.setShowTextStickerView(this.showTextView);
    }

    public void releaseLabelView() {
        DMEditLabelView3 dMEditLabelView3 = this.editLabelView;
        if (dMEditLabelView3 != null) {
            dMEditLabelView3.setVisibility(View.INVISIBLE);
            this.editLabelView.removeAllViews();
            FrameLayout frameLayout = this.rootLayout;
            if (frameLayout != null && frameLayout.indexOfChild(this.editLabelView) >= 0) {
                this.rootLayout.removeView(this.editLabelView);
            }
            this.editLabelView = null;
        }
        DMListLabelView3 dMListLabelView3 = this.listLabelView;
        if (dMListLabelView3 != null) {
            dMListLabelView3.setVisibility(View.INVISIBLE);
            this.listLabelView.removeAllViews();
            FrameLayout frameLayout2 = this.rootLayout;
            if (frameLayout2 != null && frameLayout2.indexOfChild(this.listLabelView) >= 0) {
                this.rootLayout.removeView(this.listLabelView);
            }
            this.listLabelView = null;
        }
    }

    public DMListLabelView3 createListLabelView() {
        return new DMListLabelView3(getContext());
    }

    public Bitmap getResultBitmap() {
        return this.showTextView.getResultBitmap();
    }

    public void setShowSize(RectF rectF) {
        this.showTextView.changeShowSufaceSize(rectF);
    }

    public void setTextSize(RectF rectF) {
        this.showTextView.changeSufaceSize(rectF);
    }

    public void addText() {
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), "");
        dMTextDrawer.setTypeface(getTfList().get(0));
        dMTextDrawer.setTypefaceIndex(0);
        dMTextDrawer.setPaintColorIndex(33);
        addText(dMTextDrawer);
    }

    public void addText(boolean z) {
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), this.defultText[new Random().nextInt(this.defultText.length)]);
        dMTextDrawer.setTypeface(getTfList().get(0));
        dMTextDrawer.setTypefaceIndex(0);
        dMTextDrawer.setPaintColorIndex(33);
        addText(dMTextDrawer);
        this.editTextView.showDefultText(z);
    }

    public void addText(DMWBImageRes dMWBImageRes) {
        this.mBgImageRes = dMWBImageRes;
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), "");
        dMTextDrawer.setTypeface(getTfList().get(0));
        dMTextDrawer.setTypefaceIndex(0);
        dMTextDrawer.setPaintColorIndex(33);
        addText(dMTextDrawer);
    }

    public void setImageBgRes(DMWBImageRes dMWBImageRes) {
        DMEditTextView3 dMEditTextView3 = this.editTextView;
        if (dMEditTextView3 != null) {
            dMEditTextView3.setBgRes(dMWBImageRes);
        }
    }

    protected void addText(final DMTextDrawer dMTextDrawer) {
        if (this.editTextView == null) {
            createEditView();
        }
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView3.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMInstaTextView3.this.editTextView != null) {
                    try {
                        DMInstaTextView3.this.showTextView.setSurfaceVisibility(View.INVISIBLE);
                        DMInstaTextView3.this.editTextView.setVisibility(View.VISIBLE);
                    } catch (Exception unused) {
                    }
                }
            }
        });
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView3.2
            @Override // java.lang.Runnable
            public void run() {
                if (DMInstaTextView3.this.editTextView != null) {
                    try {
                        if (DMInstaTextView3.this.finishEditTextCall != null) {
                            DMInstaTextView3.this.finishEditTextCall.startEditing();
                        }
                        DMInstaTextView3.this.editTextView.editText(dMTextDrawer);
                        if (DMInstaTextView3.this.mBgImageRes != null) {
                            DMInstaTextView3.this.editTextView.setBgRes(DMInstaTextView3.this.mBgImageRes);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        });
        this.addTextFlag = true;
    }

    public void addText(Activity activity) {
        if (DMBitmapIoCache.getBitmap("text_blur_bitmap.jpg") == null) {
            loadBlurCache(activity);
        }
        addText();
    }

    private void loadBlurCache(Activity activity) {
        @SuppressLint("ResourceType")
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

    public void addLabel() {
        if (this.listLabelView == null) {
            this.editLabelView = null;
            createLabelView();
            DMEditLabelView3 dMEditLabelView3 = this.editLabelView;
            if (dMEditLabelView3 != null) {
                dMEditLabelView3.setVisibility(View.INVISIBLE);
            }
        }
        this.showTextView.setSurfaceVisibility(View.INVISIBLE);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView3.3
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView3.this.listLabelView.showLabelList();
                DMInstaTextView3.this.editLabelView.setAddFlag(true);
            }
        });
    }

    public void addLabel(int i, int i2) {
        if (this.listLabelView == null) {
            this.editLabelView = null;
            createLabelView();
            DMEditLabelView3 dMEditLabelView3 = this.editLabelView;
            if (dMEditLabelView3 != null) {
                dMEditLabelView3.setVisibility(View.INVISIBLE);
            }
        }
        this.showTextView.setSurfaceVisibility(View.INVISIBLE);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView3.4
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView3.this.listLabelView.showLabelList();
                DMInstaTextView3.this.editLabelView.setAddFlag(true);
            }
        });
    }

    public void addLabel(Activity activity) {
        if (DMBitmapIoCache.getBitmap("text_blur_bitmap.jpg") == null) {
            loadBlurCache(activity);
        }
        addLabel();
    }

    public void editText(final DMTextDrawer dMTextDrawer) {
        FinishEditTextCall finishEditTextCall = this.finishEditTextCall;
        if (finishEditTextCall != null) {
            finishEditTextCall.startEditing();
        }
        if (this.editTextView == null) {
            createEditView();
        }
        this.editTextView.setVisibility(View.VISIBLE);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView3.5
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView3.this.editTextView.editText(dMTextDrawer);
                DMInstaTextView3.this.addTextFlag = false;
            }
        });
    }

    public void editLabel(DMTextDrawer dMTextDrawer) {
        FinishEditLabelCall finishEditLabelCall = this.finishEditLabelCall;
        if (finishEditLabelCall != null) {
            finishEditLabelCall.startEditing();
        }
        if (this.listLabelView == null || this.editLabelView == null) {
            createLabelView();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.editLabelView.setAddFlag(false);
    }

    public void finishEditText(DMTextDrawer dMTextDrawer) {
        if (this.addTextFlag) {
            DMSticker addTextView = this.showTextView.addTextView(dMTextDrawer);
            FinishAddTextCall finishAddTextCall = this.finishAddTextCall;
            if (finishAddTextCall != null) {
                finishAddTextCall.finishAddText(addTextView);
            }
        } else {
            this.showTextView.changeTextView();
            FinishAddTextCall finishAddTextCall2 = this.finishAddTextCall;
            if (finishAddTextCall2 != null) {
                finishAddTextCall2.finishEditText(this.showTextView.seletedBMSticker);
            }
        }
        DMEditTextView3 dMEditTextView3 = this.editTextView;
        if (dMEditTextView3 != null) {
            dMEditTextView3.setVisibility(View.INVISIBLE);
        }
        releaseEditView();
    }

    public OnClickListener getAddTextListener() {
        return this.addTextListener;
    }

    public void cancelEdit() {
        this.editTextView.setVisibility(View.INVISIBLE);
        this.showTextView.changeTextView();
        releaseEditView();
        FinishEditTextCall finishEditTextCall = this.finishEditTextCall;
        if (finishEditTextCall != null) {
            finishEditTextCall.findshEditing();
        }
    }

    public static List<Typeface> getTfList() {
        return tfList;
    }

    public static void setTfList(List<Typeface> list) {
        tfList = list;
    }

    public boolean backKey() {
        boolean z;
        DMShowTextStickerView3 dMShowTextStickerView3;
        DMListLabelView3 dMListLabelView3 = this.listLabelView;
        boolean z2 = true;
        if (dMListLabelView3 == null || dMListLabelView3.getVisibility() != View.VISIBLE) {
            z = false;
        } else {
            this.listLabelView.setVisibility(View.INVISIBLE);
            z = true;
        }
        DMEditLabelView3 dMEditLabelView3 = this.editLabelView;
        if (dMEditLabelView3 != null && dMEditLabelView3.getVisibility() == View.VISIBLE) {
            this.editLabelView.setVisibility(View.INVISIBLE);
            z = true;
        }
        DMEditTextView3 dMEditTextView3 = this.editTextView;
        if (dMEditTextView3 == null || dMEditTextView3.getVisibility() != View.VISIBLE) {
            z2 = z;
        } else {
            this.editTextView.setVisibility(View.INVISIBLE);
        }
        releaseEditView();
        releaseLabelView();
        if (z2 && (dMShowTextStickerView3 = this.showTextView) != null) {
            dMShowTextStickerView3.setSurfaceVisibility(0);
        }
        return z2;
    }

    public void callDoubleClick() {
        OnDoubleClickListener onDoubleClickListener = this.onDoubleClickListener;
        if (onDoubleClickListener != null) {
            onDoubleClickListener.onDoubleClick();
        }
    }

    public OnDoubleClickListener getOnDoubleClickListener() {
        return this.onDoubleClickListener;
    }

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public void callFinishEditText() {
        FinishEditTextCall finishEditTextCall = this.finishEditTextCall;
        if (finishEditTextCall != null) {
            finishEditTextCall.findshEditing();
        }
    }

    public void setFinishEditTextCall(FinishEditTextCall finishEditTextCall) {
        this.finishEditTextCall = finishEditTextCall;
    }

    public void setFinishAddTextCall(FinishAddTextCall finishAddTextCall) {
        this.finishAddTextCall = finishAddTextCall;
    }

    public void callFinishEditLabel() {
        FinishEditLabelCall finishEditLabelCall = this.finishEditLabelCall;
        if (finishEditLabelCall != null) {
            finishEditLabelCall.findshEditing();
        }
    }

    public void setFinishEditLabelCall(FinishEditLabelCall finishEditLabelCall) {
        this.finishEditLabelCall = finishEditLabelCall;
    }

    public DMShowTextStickerView3 getShowTextView() {
        return this.showTextView;
    }

    public void dispose() {
        FrameLayout frameLayout = this.rootLayout;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        DMShowTextStickerView3 dMShowTextStickerView3 = this.showTextView;
        if (dMShowTextStickerView3 != null) {
            dMShowTextStickerView3.dipose();
        }
        DMEditTextView3 dMEditTextView3 = this.editTextView;
        if (dMEditTextView3 != null) {
            dMEditTextView3.setVisibility(View.INVISIBLE);
            this.editTextView = null;
        }
        releaseLabelView();
    }
}
