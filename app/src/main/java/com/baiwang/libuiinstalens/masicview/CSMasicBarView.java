package com.baiwang.libuiinstalens.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.masicview.CSDrawMosaic;
import com.baiwang.libuiinstalens.masicview.CSMasicView;
import com.baiwang.libuiinstalens.masicview.CSMosaicRecyclerViewAdapter;
import com.baiwang.libuiinstalens.masicview.CSSmearMaskPath;
import com.baiwang.libuiinstalens.masicview.util.CSBmpData;

public class CSMasicBarView extends FrameLayout implements CSMasicView.GPUImageOnTouchListener, View.OnClickListener {
    private CSDrawMosaic.Mode currenMode;
    private ImageView imageView_eraser;
    private ImageView imageView_redo;
    private ImageView imageView_undo;
    private View ly_ponit_1;
    private View ly_ponit_2;
    private View ly_ponit_3;
    private View ly_ponit_4;
    private View ly_ponit_5;
    private Bitmap mBitmap;
    private Context mContext;
    private onMasicBarViewClickLinstener mListener;
    private CSMasicView mMasicView;
    private RecyclerView mRecyclerView;
    private Uri mUri;
    private onMasicBarAnalyticsLinstener masicBarAnalyticsLinstener;
    private CSMasicManager masicManager;
    private int maxSize;
    private CSDrawMosaic mcv_content;
    private SeekBar seekBar_pathwidth;
    private CSMosaicRecyclerViewAdapter viewAdapter;

    /* loaded from: classes.dex */
    public interface onMasicBarAnalyticsLinstener {
        void onMosaicPathWidth(String str);

        void onMosaicStyleClick(String str);
    }

    /* loaded from: classes.dex */
    public interface onMasicBarViewClickLinstener {
        void onBack();

        void onConfirm(Bitmap bitmap);
    }

    public CSMasicBarView(Context context) {
        super(context);
        initView(context);
    }

    public CSMasicBarView(Context context, Uri uri, int i) {
        super(context);
        mUri = uri;
        maxSize = i;
        initView(context);
    }

    public CSMasicBarView(Context context, Bitmap bitmap) {
        super(context);
        mBitmap = bitmap;
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        inflate(context,R.layout.view_masic_bar, this);
        imageView_eraser = (ImageView) findViewById(R.id.img_eraser);
        imageView_redo = (ImageView) findViewById(R.id.img_redo);
        imageView_undo = (ImageView) findViewById(R.id.img_undo);
        Bitmap bitmap = mBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            mBitmap = getMainBitmap();
        }
        initData(mBitmap);
        View findViewById = findViewById(R.id.ly_tips);
        if (findViewById != null) {
            if (DMPreferencesUtil.get(mContext, "mosaicbar_tips", "scroll_tips") != null) {
                findViewById.setVisibility(View.GONE);
            }
            findViewById.setOnClickListener(view -> {
                view.setVisibility(View.GONE);
                DMPreferencesUtil.save(mContext, "mosaicbar_tips", "scroll_tips", "tips_showed");
            });
        }
    }

    public Bitmap getMainBitmap() {
        return CSBmpData.sSrcBmp;
    }

    private void initData(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            onMasicBarViewClickLinstener onmasicbarviewclicklinstener = mListener;
            if (onmasicbarviewclicklinstener != null) {
                onmasicbarviewclicklinstener.onBack();
                return;
            }
            return;
        }
        CSMasicView cSMasicView = (CSMasicView) findViewById(R.id.main_view);
        mMasicView = cSMasicView;
        cSMasicView.initData(bitmap);
        mMasicView.registerGPUImageOnTouchListener(this);
        CSDrawMosaic cSDrawMosaic = new CSDrawMosaic(mContext, bitmap);
        mcv_content = cSDrawMosaic;
        cSDrawMosaic.setOnResultBmpListener(new CSSmearMaskPath.ResultBmpListener() { // from class: com.baiwang.libuiinstalens.masicview.2
            @Override // com.baiwang.libuiinstalens.masicview.CSSmearMaskPath.ResultBmpListener
            public void onReturnResultBmp(Bitmap bitmap2) {
                mMasicView.setImageBitmap(bitmap2);
            }
        });
        mcv_content.setOnBackStatusListener(new CSDrawMosaic.OnBackStatusListener() { // from class: com.baiwang.libuiinstalens.masicview.3
            @Override // com.baiwang.libuiinstalens.masicview.CSDrawMosaic.OnBackStatusListener
            public void backDisable() {
            }

            @Override // com.baiwang.libuiinstalens.masicview.CSDrawMosaic.OnBackStatusListener
            public void forwardAble() {
            }

            @Override // com.baiwang.libuiinstalens.masicview.CSDrawMosaic.OnBackStatusListener
            public void forwardDisable() {
                imageView_redo.setSelected(false);
            }

            @Override // com.baiwang.libuiinstalens.masicview.CSDrawMosaic.OnBackStatusListener
            public void backAble() {
                imageView_undo.setSelected(true);
            }
        });
        imageView_undo.setSelected(mcv_content.getBackStatus());
        imageView_redo.setSelected(mcv_content.getForwardStatus());
        findViewById(R.id.ly_undo).setOnClickListener(this);
        findViewById(R.id.ly_redo).setOnClickListener(this);
        findViewById(R.id.ly_eraser).setOnClickListener(this);
        findViewById(R.id.ly_cancel).setOnClickListener(this);
        findViewById(R.id.ly_confirm).setOnClickListener(this);
        ly_ponit_1 = findViewById(R.id.ly_point_1);
        ly_ponit_2 = findViewById(R.id.ly_point_2);
        ly_ponit_3 = findViewById(R.id.ly_point_3);
        ly_ponit_4 = findViewById(R.id.ly_point_4);
        ly_ponit_5 = findViewById(R.id.ly_point_5);
        ly_ponit_1.setOnClickListener(this);
        ly_ponit_2.setOnClickListener(this);
        ly_ponit_3.setOnClickListener(this);
        ly_ponit_4.setOnClickListener(this);
        ly_ponit_5.setOnClickListener(this);
        ly_ponit_5.setSelected(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        masicManager = new CSMasicManager(mContext);
        CSMosaicRecyclerViewAdapter cSMosaicRecyclerViewAdapter = new CSMosaicRecyclerViewAdapter(mContext, masicManager.getResList());
        viewAdapter = cSMosaicRecyclerViewAdapter;
        mRecyclerView.setAdapter(cSMosaicRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        // from class: com.baiwang.libuiinstalens.masicview.4
// com.baiwang.libuiinstalens.masicview.CSMosaicRecyclerViewAdapter.onRecyclerViewItemClikListener
        viewAdapter.setOnRecyclerViewItemClikListener((i, dMWBRes, z) -> {
            imageView_eraser.setSelected(false);
            CSMosaicRes cSMosaicRes = (CSMosaicRes) dMWBRes;
            mcv_content.setRes(cSMosaicRes);
            currenMode = cSMosaicRes.getmMosaicMode();
            if (masicBarAnalyticsLinstener != null) {
                masicBarAnalyticsLinstener.onMosaicStyleClick(dMWBRes.getName());
            }
        });
        viewAdapter.setSelectedPos(1);
        currenMode = CSDrawMosaic.Mode.NORMAL;
    }

    @Override // com.baiwang.libuiinstalens.masicview.CSMasicView.GPUImageOnTouchListener
    public void onGPUImageTouchEvent(MotionEvent motionEvent, float f, float f2, float f3) {
        mcv_content.actionTouch(motionEvent, f, f2, f3);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id ==R.id.ly_undo) {
            if (mcv_content.getBackStatus()) {
                mcv_content.actionUndo();
            }
            imageView_undo.setSelected(mcv_content.getBackStatus());
            imageView_redo.setSelected(mcv_content.getForwardStatus());
        } else if (id ==R.id.ly_redo) {
            if (mcv_content.getForwardStatus()) {
                mcv_content.actionRedo();
            }
            imageView_undo.setSelected(mcv_content.getBackStatus());
            imageView_redo.setSelected(mcv_content.getForwardStatus());
        } else if (id ==R.id.ly_cancel) {
            onMasicBarViewClickLinstener onmasicbarviewclicklinstener = mListener;
            if (onmasicbarviewclicklinstener != null) {
                onmasicbarviewclicklinstener.onBack();
            }
        } else if (id ==R.id.ly_confirm) {
            onMasicBarViewClickLinstener onmasicbarviewclicklinstener2 = mListener;
            if (onmasicbarviewclicklinstener2 != null) {
                onmasicbarviewclicklinstener2.onConfirm(mMasicView.getImageBitmap());
            }
        } else if (id ==R.id.ly_eraser) {
            if (mcv_content.getMode() == CSDrawMosaic.Mode.ERASE) {
                imageView_eraser.setSelected(false);
                mcv_content.setMode(currenMode);
                return;
            }
            imageView_eraser.setSelected(true);
            mcv_content.actionEraser();
        } else if (id ==R.id.ly_point_1) {
            resetpointselect();
            ly_ponit_1.setSelected(true);
            mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(mContext, 4.0f));
            mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(mContext, 4.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener = masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener != null) {
                onmasicbaranalyticslinstener.onMosaicPathWidth("4");
            }
        } else if (id ==R.id.ly_point_2) {
            resetpointselect();
            ly_ponit_2.setSelected(true);
            mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(mContext, 12.0f));
            mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(mContext, 12.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener2 = masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener2 != null) {
                onmasicbaranalyticslinstener2.onMosaicPathWidth("12");
            }
        } else if (id ==R.id.ly_point_3) {
            resetpointselect();
            ly_ponit_3.setSelected(true);
            mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(mContext, 14.0f));
            mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(mContext, 14.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener3 = masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener3 != null) {
                onmasicbaranalyticslinstener3.onMosaicPathWidth("14");
            }
        } else if (id ==R.id.ly_point_4) {
            resetpointselect();
            ly_ponit_4.setSelected(true);
            mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(mContext, 18.0f));
            mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(mContext, 18.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener4 = masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener4 != null) {
                onmasicbaranalyticslinstener4.onMosaicPathWidth("18");
            }
        } else if (id ==R.id.ly_point_5) {
            resetpointselect();
            ly_ponit_5.setSelected(true);
            mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(mContext, 26.0f));
            mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(mContext, 26.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener5 = masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener5 != null) {
                onmasicbaranalyticslinstener5.onMosaicPathWidth("26");
            }
        }
    }

    private void resetpointselect() {
        ly_ponit_1.setSelected(false);
        ly_ponit_2.setSelected(false);
        ly_ponit_4.setSelected(false);
        ly_ponit_5.setSelected(false);
        ly_ponit_3.setSelected(false);
    }

    public void setonMasicBarViewClickLinstener(onMasicBarViewClickLinstener onmasicbarviewclicklinstener) {
        mListener = onmasicbarviewclicklinstener;
    }

    public void setMasicBarAnalyticsLinstener(onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener) {
        masicBarAnalyticsLinstener = onmasicbaranalyticslinstener;
    }
}
