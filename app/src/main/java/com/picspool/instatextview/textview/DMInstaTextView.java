package com.picspool.instatextview.textview;

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
import com.picspool.instatextview.edit.DMEditTextView2;
import com.picspool.instatextview.labelview.DMEditLabelView;
import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.utils.DMBlurUtil;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMInstaTextView extends FrameLayout {
    private static List<Typeface> tfList;
    private boolean addTextFlag;
    private OnClickListener addTextListener;
    protected DMEditLabelView editLabelView;
    private DMEditTextView2 editTextView;
    private FinishEditLabelCall finishEditLabelCall;
    private FinishEditTextCall finishEditTextCall;
    protected Handler handler;
    protected DMListLabelView listLabelView;
    private OnDoubleClickListener onDoubleClickListener;
    private FrameLayout rootLayout;
    protected DMShowTextStickerView showTextView;

    /* loaded from: classes3.dex */
    public interface ClickEditTextOKListener {
        void onClickOK();
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

    public DMInstaTextView(Context context) {
        super(context);
        this.addTextFlag = false;
        this.handler = new Handler();
        iniView();
    }

    public DMInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addTextFlag = false;
        this.handler = new Handler();
        iniView();
    }

    public void iniView() {
        FrameLayout frameLayout = (FrameLayout) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutView(), (ViewGroup) null);
        this.rootLayout = frameLayout;
        DMShowTextStickerView dMShowTextStickerView = (DMShowTextStickerView) frameLayout.findViewById(R.id.show_text_view);
        this.showTextView = dMShowTextStickerView;
        dMShowTextStickerView.setInstaTextView(this);
        addView(this.rootLayout);
    }

    public int getLayoutView() {
        return R.layout.dm_text_insta_text_view;
    }

    public void createEditView() {
        this.editTextView = new DMEditTextView2(getContext());
        this.editTextView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.editTextView);
        this.editTextView.setInstaTextView(this);
    }

    public void releaseEditView() {
        DMEditTextView2 dMEditTextView2 = this.editTextView;
        if (dMEditTextView2 != null) {
            this.rootLayout.removeView(dMEditTextView2);
            this.editTextView = null;
        }
    }

    public void createLabelView() {
        this.editLabelView = new DMEditLabelView(getContext());
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
        DMEditLabelView dMEditLabelView = this.editLabelView;
        if (dMEditLabelView != null) {
            dMEditLabelView.removeAllViews();
            this.rootLayout.removeView(this.editLabelView);
            this.editLabelView = null;
        }
        DMListLabelView dMListLabelView = this.listLabelView;
        if (dMListLabelView != null) {
            dMListLabelView.removeAllViews();
            this.rootLayout.removeView(this.listLabelView);
            this.listLabelView = null;
        }
    }

    public DMListLabelView createListLabelView() {
        return new DMListLabelView(getContext());
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

    public void setPhotoFreeSufaceSize(RectF rectF) {
        this.showTextView.changePhotoFreeSufaceSize(rectF);
    }

    public void addText() {
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), "");
        dMTextDrawer.setTypeface(getTfList().get(0));
        dMTextDrawer.setTypefaceIndex(0);
        dMTextDrawer.setPaintColorIndex(33);
        addText(dMTextDrawer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addText(final DMTextDrawer dMTextDrawer) {
        if (this.editTextView == null) {
            createEditView();
        }
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMInstaTextView.this.editTextView != null) {
                    try {
                        DMInstaTextView.this.showTextView.setSurfaceVisibility(4);
                        DMInstaTextView.this.editTextView.setVisibility(View.VISIBLE);
                    } catch (Exception unused) {
                    }
                }
            }
        });
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView.2
            @Override // java.lang.Runnable
            public void run() {
                if (DMInstaTextView.this.editTextView != null) {
                    try {
                        if (DMInstaTextView.this.finishEditTextCall != null) {
                            DMInstaTextView.this.finishEditTextCall.startEditing();
                        }
                        DMInstaTextView.this.editTextView.editText(dMTextDrawer);
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
            DMEditLabelView dMEditLabelView = this.editLabelView;
            if (dMEditLabelView != null) {
                dMEditLabelView.setVisibility(View.INVISIBLE);
            }
        }
        this.showTextView.setSurfaceVisibility(4);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView.3
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView.this.listLabelView.showLabelList();
                DMInstaTextView.this.editLabelView.setAddFlag(true);
            }
        });
    }

    public void addLabel(int i, int i2) {
        if (this.listLabelView == null) {
            this.editLabelView = null;
            createLabelView();
            DMEditLabelView dMEditLabelView = this.editLabelView;
            if (dMEditLabelView != null) {
                dMEditLabelView.setVisibility(View.INVISIBLE);
            }
        }
        this.showTextView.setSurfaceVisibility(4);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView.4
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView.this.listLabelView.showLabelList();
                DMInstaTextView.this.editLabelView.setAddFlag(true);
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.textview.DMInstaTextView.5
            @Override // java.lang.Runnable
            public void run() {
                DMInstaTextView.this.editTextView.editText(dMTextDrawer);
                DMInstaTextView.this.addTextFlag = false;
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
        this.editTextView.setVisibility(View.INVISIBLE);
        if (this.addTextFlag) {
            this.showTextView.addTextView(dMTextDrawer);
        } else {
            this.showTextView.changeTextView();
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
        DMShowTextStickerView dMShowTextStickerView;
        DMListLabelView dMListLabelView = this.listLabelView;
        boolean z2 = true;
        if (dMListLabelView == null || dMListLabelView.getVisibility() != View.VISIBLE) {
            z = false;
        } else {
            this.listLabelView.setVisibility(View.INVISIBLE);
            z = true;
        }
        DMEditLabelView dMEditLabelView = this.editLabelView;
        if (dMEditLabelView != null && dMEditLabelView.getVisibility() == View.VISIBLE) {
            this.editLabelView.setVisibility(View.INVISIBLE);
            z = true;
        }
        DMEditTextView2 dMEditTextView2 = this.editTextView;
        if (dMEditTextView2 == null || dMEditTextView2.getVisibility() != View.VISIBLE) {
            z2 = z;
        } else {
            this.editTextView.setVisibility(View.INVISIBLE);
        }
        releaseEditView();
        releaseLabelView();
        if (z2 && (dMShowTextStickerView = this.showTextView) != null) {
            dMShowTextStickerView.setSurfaceVisibility(0);
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

    public void callFinishEditLabel() {
        FinishEditLabelCall finishEditLabelCall = this.finishEditLabelCall;
        if (finishEditLabelCall != null) {
            finishEditLabelCall.findshEditing();
        }
    }

    public void setFinishEditLabelCall(FinishEditLabelCall finishEditLabelCall) {
        this.finishEditLabelCall = finishEditLabelCall;
    }

    public DMShowTextStickerView getShowTextView() {
        return this.showTextView;
    }

    public void dispose() {
        this.rootLayout.removeAllViews();
        DMShowTextStickerView dMShowTextStickerView = this.showTextView;
        if (dMShowTextStickerView != null) {
            dMShowTextStickerView.dipose();
        }
    }
}
