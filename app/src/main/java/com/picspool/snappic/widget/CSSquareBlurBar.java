package com.picspool.snappic.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libsquare.view.CSSquareView;
import com.picspool.snappic.activity.CSSysConfig;
import com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSquareBgEffectColorManager;
import java.util.List;
import com.picspool.lib.bitmap.DMAsyncBitmapCropExecute;
import com.picspool.lib.bitmap.OnBitmapCropListener;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;
import com.touch.android.library.imagezoom.ImageViewTouchBase;

/* loaded from: classes.dex */
public class CSSquareBlurBar extends FrameLayout {
    private int bgPos;
    private int bgcfColorvalue;
    private int blurProgress;
    private CSBgEffectRecyclerViewAdapter effectRecyclerViewAdapter;
    private Context mContext;
    private onBgBlurClickListner mListenrer;
    private CSSquareView mSquareview;
    private Bitmap oribitmap;
    private RecyclerView recyclerView;
    private List<DMWBRes> reslist;
    private SeekBar seekBar;

    /* loaded from: classes.dex */
    public interface onBgBlurClickListner {
        void onCancel();

        void onOk(Bitmap bitmap);

        void onPhotoSelectorClicked();
    }

    public CSSquareBlurBar(Context context, Bitmap bitmap) {
        super(context);
        this.blurProgress = 60;
        this.bgcfColorvalue = 0;
        this.bgPos = 0;
        this.oribitmap = bitmap;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_square_blureffect_bar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        CSSquareView cSSquareView = (CSSquareView) findViewById(R.id.main_view);
        this.mSquareview = cSSquareView;
        cSSquareView.initSetPictureImageBitmap(this.oribitmap);
        this.mSquareview.setBlurBackground(0.0f, this.blurProgress / 100.0f, true);
        this.mSquareview.setColorbg(this.bgcfColorvalue);
        this.mSquareview.setOnGetResultBitmapListener(new CSSquareView.OnGetResultBitmapListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.1
            @Override // com.picspool.libsquare.view.CSSquareView.OnGetResultBitmapListener
            public void getResultBitmapSuccess(Bitmap bitmap) {
                if (CSSquareBlurBar.this.mListenrer != null) {
                    CSSquareBlurBar.this.mListenrer.onOk(bitmap);
                }
            }
        });
        resetPicWH();
        if (this.oribitmap.getHeight() == this.oribitmap.getWidth()) {
            this.mSquareview.setSmallerType(ImageViewTouchBase.DisplayType.SMALLER_IF_FIT);
        }
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareBlurBar.this.mListenrer != null) {
                    CSSquareBlurBar.this.mListenrer.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareBlurBar.this.mSquareview.getSizeBitmap(CSSquareBlurBar.this.oribitmap.getHeight() > CSSquareBlurBar.this.oribitmap.getWidth() ? CSSquareBlurBar.this.oribitmap.getHeight() : CSSquareBlurBar.this.oribitmap.getWidth());
            }
        });
        this.oribitmap.getWidth();
        this.oribitmap.getHeight();
        List<DMWBRes> resList = new CSSquareBgEffectColorManager(this.mContext).getResList();
        this.reslist = resList;
        this.effectRecyclerViewAdapter = new CSBgEffectRecyclerViewAdapter(this.mContext, resList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this.effectRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.effectRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.4
            @Override // com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (dMWBRes instanceof DMWBColorRes) {
                    CSSquareBlurBar.this.bgPos = i;
                    CSSquareBlurBar.this.bgcfColorvalue = ((DMWBColorRes) dMWBRes).getColorValue();
                    CSSquareBlurBar.this.bgcfColorvalue &= 1442840575;
                    CSSquareBlurBar.this.mSquareview.setColorbg(CSSquareBlurBar.this.bgcfColorvalue);
                }
            }
        });
        findViewById(R.id.ly_photoselector).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareBlurBar.this.mListenrer != null) {
                    CSSquareBlurBar.this.mListenrer.onPhotoSelectorClicked();
                }
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_main);
        this.seekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                CSSquareBlurBar.this.blurProgress = i;
                CSSquareBlurBar.this.mSquareview.setBlurBackground(0.0f, CSSquareBlurBar.this.blurProgress / 100.0f, true);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                CSSquareBlurBar.this.blurProgress = seekBar2.getProgress();
                CSSquareBlurBar.this.mSquareview.setBlurBackground(0.0f, CSSquareBlurBar.this.blurProgress / 100.0f, true);
            }
        });
    }

    private void resetPicWH() {
        int screenHeight = DMScreenInfoUtil.screenHeight(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 210.0f);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        LayoutParams layoutParams = (LayoutParams) this.mSquareview.getLayoutParams();
        if (screenHeight > screenWidth) {
            layoutParams.width = screenWidth;
            layoutParams.height = screenWidth;
        } else {
            layoutParams.width = screenHeight;
            layoutParams.height = screenHeight;
        }
        this.mSquareview.setLayoutParams(layoutParams);
    }

    public void setDefalutValue(int i) {
        int i2;
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            seekBar.setProgress(i);
        }
        if (this.effectRecyclerViewAdapter == null || (i2 = this.bgPos) < 0 || i2 >= this.reslist.size()) {
            return;
        }
        this.effectRecyclerViewAdapter.setSelectedPos(this.bgPos);
        this.recyclerView.smoothScrollToPosition(this.bgPos);
    }

    public void setBgBitmap(Uri uri) {
        DMAsyncBitmapCropExecute.asyncBitmapCrop(this.mContext, uri, CSSysConfig.getImageQuality(this.mContext), new OnBitmapCropListener() { // from class: com.picspool.snappic.widget.CSSquareBlurBar.7
            @Override // com.picspool.lib.bitmap.OnBitmapCropListener
            public void onBitmapCropFinish(Bitmap bitmap) {
                CSSquareBlurBar.this.mSquareview.setBgsrc(bitmap);
                CSSquareBlurBar.this.mSquareview.setBlurBackground(0.0f, CSSquareBlurBar.this.blurProgress / 100.0f, true);
            }
        });
    }

    public void setBgBlurClickListner(onBgBlurClickListner onbgblurclicklistner) {
        this.mListenrer = onbgblurclicklistner;
    }
}
