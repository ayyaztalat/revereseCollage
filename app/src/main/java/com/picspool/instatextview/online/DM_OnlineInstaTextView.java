package com.picspool.instatextview.online;

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
import com.sky.testproject.R;
import com.picspool.instatextview.utils.DMBlurUtil;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DM_OnlineInstaTextView extends FrameLayout {
    private static List<DM_OnlineFontRes> resList;
    private static List<Typeface> tfList;
    private boolean addTextFlag;
    private OnClickListener addTextListener;
    private DM_OnlineEditLabelView editLabelView;
    private DM_OnlineEditTextView editTextView;
    private FinishEditLabelCall finishEditLabelCall;
    private FinishEditTextCall finishEditTextCall;
    private Handler handler;
    private DM_OnlineListLabelView listLabelView;
    private FrameLayout rootLayout;
    private DM_OnlineShowTextBMStickerView showTextView;

    /* loaded from: classes3.dex */
    public interface FinishEditLabelCall {
        void findshEditLabel();
    }

    /* loaded from: classes3.dex */
    public interface FinishEditTextCall {
        void findshEditText();
    }

    public void finishLabelText(DMTextDrawer dMTextDrawer) {
    }

    public DM_OnlineInstaTextView(Context context) {
        super(context);
        this.addTextFlag = false;
        this.handler = new Handler();
        iniView();
    }

    public DM_OnlineInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addTextFlag = false;
        this.handler = new Handler();
        iniView();
    }

    public void iniView() {
        FrameLayout frameLayout = (FrameLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_online_insta_text_view, (ViewGroup) null);
        this.rootLayout = frameLayout;
        DM_OnlineShowTextBMStickerView dM_OnlineShowTextBMStickerView = (DM_OnlineShowTextBMStickerView) frameLayout.findViewById(R.id.show_text_view);
        this.showTextView = dM_OnlineShowTextBMStickerView;
        dM_OnlineShowTextBMStickerView.setInstaTextView(this);
        addView(this.rootLayout);
    }

    public void createEditView() {
        this.editTextView = new DM_OnlineEditTextView(getContext());
        this.editTextView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.editTextView);
        this.editTextView.setInstaTextView(this);
    }

    public void releaseEditView() {
        DM_OnlineEditTextView dM_OnlineEditTextView = this.editTextView;
        if (dM_OnlineEditTextView != null) {
            this.rootLayout.removeView(dM_OnlineEditTextView);
            this.editTextView.dispose();
            this.editTextView = null;
        }
    }

    public void createLabelView() {
        this.editLabelView = new DM_OnlineEditLabelView(getContext());
        this.editLabelView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.editLabelView);
        this.editLabelView.setInstaTextView(this);
        this.editLabelView.setSurfaceView(this.showTextView);
        this.listLabelView = createListLabelView();
        this.listLabelView.setLayoutParams(new LayoutParams(-1, -1));
        this.rootLayout.addView(this.listLabelView);
        this.listLabelView.setVisibility(4);
        this.listLabelView.setInstaTextView(this);
        this.listLabelView.setEditLabelView(this.editLabelView);
        this.editLabelView.setListLabelView(this.listLabelView);
        this.listLabelView.setShowTextStickerView(this.showTextView);
    }

    public void releaseLabelView() {
        DM_OnlineEditLabelView dM_OnlineEditLabelView = this.editLabelView;
        if (dM_OnlineEditLabelView != null) {
            dM_OnlineEditLabelView.removeAllViews();
            this.rootLayout.removeView(this.editLabelView);
            this.editLabelView = null;
        }
        DM_OnlineListLabelView dM_OnlineListLabelView = this.listLabelView;
        if (dM_OnlineListLabelView != null) {
            dM_OnlineListLabelView.removeAllViews();
            this.rootLayout.removeView(this.listLabelView);
            this.listLabelView = null;
        }
    }

    public DM_OnlineListLabelView createListLabelView() {
        return new DM_OnlineListLabelView(getContext());
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

    private void addText(final DMTextDrawer dMTextDrawer) {
        if (this.editTextView == null) {
            createEditView();
        }
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineInstaTextView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DM_OnlineInstaTextView.this.editTextView != null) {
                    try {
                        DM_OnlineInstaTextView.this.showTextView.setSurfaceVisibility(4);
                        DM_OnlineInstaTextView.this.editTextView.setVisibility(0);
                    } catch (Exception unused) {
                    }
                }
            }
        });
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineInstaTextView.2
            @Override // java.lang.Runnable
            public void run() {
                if (DM_OnlineInstaTextView.this.editTextView != null) {
                    try {
                        DM_OnlineInstaTextView.this.editTextView.editText(dMTextDrawer);
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
            DM_OnlineEditLabelView dM_OnlineEditLabelView = this.editLabelView;
            if (dM_OnlineEditLabelView != null) {
                dM_OnlineEditLabelView.setVisibility(4);
            }
        }
        this.showTextView.setSurfaceVisibility(4);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineInstaTextView.3
            @Override // java.lang.Runnable
            public void run() {
                DM_OnlineInstaTextView.this.listLabelView.showLabelList();
                DM_OnlineInstaTextView.this.editLabelView.setAddFlag(true);
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
        if (this.editTextView == null) {
            createEditView();
        }
        this.editTextView.setVisibility(0);
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineInstaTextView.4
            @Override // java.lang.Runnable
            public void run() {
                DM_OnlineInstaTextView.this.editTextView.editText(dMTextDrawer);
                DM_OnlineInstaTextView.this.addTextFlag = false;
            }
        });
    }

    public void editLabel(DMTextDrawer dMTextDrawer) {
        if (this.listLabelView == null || this.editLabelView == null) {
            createLabelView();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.editLabelView.setAddFlag(false);
    }

    public void finishEditText(DMTextDrawer dMTextDrawer) {
        this.editTextView.setVisibility(4);
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
        this.editTextView.setVisibility(4);
        this.showTextView.changeTextView();
        releaseEditView();
    }

    public static List<Typeface> getTfList() {
        return tfList;
    }

    public static void setTfList(List<Typeface> list) {
        tfList = list;
    }

    public static List<DM_OnlineFontRes> getResList() {
        return resList;
    }

    public static void setResList(List<DM_OnlineFontRes> list) {
        resList = list;
    }

    public boolean backKey() {
        boolean z;
        DM_OnlineShowTextBMStickerView dM_OnlineShowTextBMStickerView;
        DM_OnlineListLabelView dM_OnlineListLabelView = this.listLabelView;
        boolean z2 = true;
        if (dM_OnlineListLabelView == null || dM_OnlineListLabelView.getVisibility() != 0) {
            z = false;
        } else {
            this.listLabelView.setVisibility(4);
            z = true;
        }
        DM_OnlineEditLabelView dM_OnlineEditLabelView = this.editLabelView;
        if (dM_OnlineEditLabelView != null && dM_OnlineEditLabelView.getVisibility() == 0) {
            this.editLabelView.setVisibility(4);
            z = true;
        }
        DM_OnlineEditTextView dM_OnlineEditTextView = this.editTextView;
        if (dM_OnlineEditTextView == null || dM_OnlineEditTextView.getVisibility() != 0) {
            z2 = z;
        } else {
            this.editTextView.setVisibility(4);
        }
        releaseEditView();
        releaseLabelView();
        if (z2 && (dM_OnlineShowTextBMStickerView = this.showTextView) != null) {
            dM_OnlineShowTextBMStickerView.setSurfaceVisibility(0);
        }
        return z2;
    }

    public void callFinishEditText() {
        FinishEditTextCall finishEditTextCall = this.finishEditTextCall;
        if (finishEditTextCall != null) {
            finishEditTextCall.findshEditText();
        }
    }

    public void setFinishEditTextCall(FinishEditTextCall finishEditTextCall) {
        this.finishEditTextCall = finishEditTextCall;
    }

    public void callFinishEditLabel() {
        FinishEditLabelCall finishEditLabelCall = this.finishEditLabelCall;
        if (finishEditLabelCall != null) {
            finishEditLabelCall.findshEditLabel();
        }
    }

    public void setFinishEditLabelCall(FinishEditLabelCall finishEditLabelCall) {
        this.finishEditLabelCall = finishEditLabelCall;
    }

    public DM_OnlineShowTextBMStickerView getShowTextView() {
        return this.showTextView;
    }

    public void dispose() {
        this.rootLayout.removeAllViews();
        DM_OnlineShowTextBMStickerView dM_OnlineShowTextBMStickerView = this.showTextView;
        if (dM_OnlineShowTextBMStickerView != null) {
            dM_OnlineShowTextBMStickerView.dipose();
        }
        DM_OnlineEditTextView dM_OnlineEditTextView = this.editTextView;
        if (dM_OnlineEditTextView != null) {
            dM_OnlineEditTextView.dispose();
        }
    }
}
