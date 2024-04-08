package com.photo.editor.square.splash.view.cropview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.lib.resource.view.DMResImageLayout;
import com.sky.testproject.R;


/* loaded from: classes2.dex */
public class CSCropView extends FrameLayout implements DMResImageLayout.OnItemClickListener {
    private View bt_cancel;
    private View bt_ok;
    private CSCropBottomBar cropBottomBar;
    private onCropViewAnalyticListener cropViewAnalyticListener;
    private CSCropImageView img_display;
    private Context mContext;
    private OnResultBmpListener mListener;
    private Uri mUri;
    private int maxSize;
    private Bitmap ori_bitmap;

    /* loaded from: classes2.dex */
    public interface OnResultBmpListener {
        void onCropViewFinish();

        void onResultBmp(Bitmap bitmap);
    }

    /* loaded from: classes2.dex */
    public interface onCropViewAnalyticListener {
        void onratioclick(String str);
    }

    public void setOnResultBmpListener(OnResultBmpListener onResultBmpListener) {
        this.mListener = onResultBmpListener;
    }

    public CSCropView(Context context) {
        super(context);
        initView(context);
    }

    public CSCropView(Context context, Uri uri, int i) {
        super(context);
        this.mUri = uri;
        this.maxSize = i;
        initView(context);
    }

    public CSCropView(Context context, Bitmap bitmap) {
        super(context);
        this.ori_bitmap = bitmap;
        initView(context);
    }

    public CSCropView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CSCropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_crop, (ViewGroup) this, true);
        this.img_display = (CSCropImageView) findViewById(R.id.img_display);
        CSCropBottomBar cSCropBottomBar = (CSCropBottomBar) findViewById(R.id.crop_bottom_bar);
        this.cropBottomBar = cSCropBottomBar;
        cSCropBottomBar.loadAdapter();
        this.cropBottomBar.setItemClickListener(this);
        View findViewById = findViewById(R.id.overlayAccept);
        this.bt_ok = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.cropview.CSCropView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Bitmap cropImage = CSCropView.this.img_display.getCropImage();
                if (CSCropView.this.mListener != null) {
                    CSCropView.this.mListener.onResultBmp(cropImage);
                }
            }
        });
        View findViewById2 = findViewById(R.id.overlayBack);
        this.bt_cancel = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.cropview.CSCropView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSCropView.this.mListener != null) {
                    CSCropView.this.mListener.onCropViewFinish();
                }
            }
        });
        initData();
    }

    private void initData() {
        this.img_display.setDrawable(new BitmapDrawable(getResources(), this.ori_bitmap), 0, 0);
        this.img_display.setFloatRationWH(0.0f);
    }

    @Override // com.picspool.lib.resource.view.DMResImageLayout.OnItemClickListener
    public void ItemClick(View view, int i, String str) {
        switch (i) {
            case 1:
                this.img_display.setFloatRationWH(this.ori_bitmap.getWidth() / this.ori_bitmap.getHeight());
                onCropViewAnalyticListener oncropviewanalyticlistener = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener != null) {
                    oncropviewanalyticlistener.onratioclick("origin");
                    return;
                }
                return;
            case 2:
                this.img_display.setFloatRationWH(0.0f);
                onCropViewAnalyticListener oncropviewanalyticlistener2 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener2 != null) {
                    oncropviewanalyticlistener2.onratioclick("free");
                    return;
                }
                return;
            case 3:
                this.img_display.setFloatRationWH(1.0f);
                onCropViewAnalyticListener oncropviewanalyticlistener3 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener3 != null) {
                    oncropviewanalyticlistener3.onratioclick("1_1");
                    return;
                }
                return;
            case 4:
                this.img_display.setFloatRationWH(1.3333334f);
                onCropViewAnalyticListener oncropviewanalyticlistener4 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener4 != null) {
                    oncropviewanalyticlistener4.onratioclick("4_3");
                    return;
                }
                return;
            case 5:
                this.img_display.setFloatRationWH(0.75f);
                onCropViewAnalyticListener oncropviewanalyticlistener5 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener5 != null) {
                    oncropviewanalyticlistener5.onratioclick("3_4");
                    return;
                }
                return;
            case 6:
                this.img_display.setFloatRationWH(1.7777778f);
                onCropViewAnalyticListener oncropviewanalyticlistener6 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener6 != null) {
                    oncropviewanalyticlistener6.onratioclick("16_9");
                    return;
                }
                return;
            case 7:
                this.img_display.setFloatRationWH(0.5625f);
                onCropViewAnalyticListener oncropviewanalyticlistener7 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener7 != null) {
                    oncropviewanalyticlistener7.onratioclick("9_16");
                    return;
                }
                return;
            case 8:
                this.img_display.setFloatRationWH(0.618f);
                onCropViewAnalyticListener oncropviewanalyticlistener8 = this.cropViewAnalyticListener;
                if (oncropviewanalyticlistener8 != null) {
                    oncropviewanalyticlistener8.onratioclick("0618");
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setCropViewAnalyticListener(onCropViewAnalyticListener oncropviewanalyticlistener) {
        this.cropViewAnalyticListener = oncropviewanalyticlistener;
    }
}
