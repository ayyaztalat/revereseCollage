package com.picspool.libfuncview.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.libfuncview.masicview.CSDrawMosaic;
import com.picspool.libfuncview.masicview.CSMasicView;
import com.picspool.libfuncview.masicview.CSMosaicRecyclerViewAdapter;
import com.picspool.libfuncview.masicview.CSSmearMaskPath;
import com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener;
import com.picspool.libfuncview.masicview.util.CSBmpData;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
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
        this.mUri = uri;
        this.maxSize = i;
        initView(context);
    }

    public CSMasicBarView(Context context, Bitmap bitmap) {
        super(context);
        this.mBitmap = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_mosaic, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.imageView_eraser = (ImageView) findViewById(R.id.img_eraser);
        this.imageView_redo = (ImageView) findViewById(R.id.img_redo);
        this.imageView_undo = (ImageView) findViewById(R.id.img_undo);
        if (this.mBitmap == null) {
            this.mBitmap = getMainBitmap();
        }
        initData(this.mBitmap);
        if (DMPreferencesUtil.get(this.mContext, "mosaicbar_tips", "scroll_tips") != null) {
            findViewById(R.id.ly_tips).setVisibility(View.GONE);
        }
        findViewById(R.id.ly_tips).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.masicview.CSMasicBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSMasicBarView.this.findViewById(R.id.ly_tips).setVisibility(View.GONE);
                DMPreferencesUtil.save(CSMasicBarView.this.mContext, "mosaicbar_tips", "scroll_tips", "tips_showed");
            }
        });
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context2 = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context2, context2.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    public Bitmap getMainBitmap() {
        return CSBmpData.sSrcBmp;
    }

    private void initData(Bitmap bitmap) {
        if (bitmap == null) {
            this.mListener.onBack();
            return;
        }
        CSMasicView cSMasicView = (CSMasicView) findViewById(R.id.main_view);
        this.mMasicView = cSMasicView;
        cSMasicView.initData(bitmap);
        this.mMasicView.registerGPUImageOnTouchListener(this);
        CSDrawMosaic cSDrawMosaic = new CSDrawMosaic(this.mContext, bitmap);
        this.mcv_content = cSDrawMosaic;
        cSDrawMosaic.setOnResultBmpListener(new CSSmearMaskPath.ResultBmpListener() { // from class: com.picspool.libfuncview.masicview.CSMasicBarView.2
            @Override // com.picspool.libfuncview.masicview.CSSmearMaskPath.ResultBmpListener
            public void onReturnResultBmp(Bitmap bitmap2) {
                CSMasicBarView.this.mMasicView.setImageBitmap(bitmap2);
            }
        });
        this.mcv_content.setOnBackStatusListener(new CSOnBackStatusListener() { // from class: com.picspool.libfuncview.masicview.CSMasicBarView.3
            @Override // com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener
            public void backDisable() {
            }

            @Override // com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener
            public void forwardAble() {
            }

            @Override // com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener
            public void forwardDisable() {
                CSMasicBarView.this.imageView_redo.setSelected(false);
            }

            @Override // com.picspool.libfuncview.masicview.drawmosaic.CSOnBackStatusListener
            public void backAble() {
                CSMasicBarView.this.imageView_undo.setSelected(true);
            }
        });
        this.imageView_undo.setSelected(this.mcv_content.getBackStatus());
        this.imageView_redo.setSelected(this.mcv_content.getForwardStatus());
        findViewById(R.id.ly_undo).setOnClickListener(this);
        findViewById(R.id.ly_redo).setOnClickListener(this);
        findViewById(R.id.ly_eraser).setOnClickListener(this);
        findViewById(R.id.ly_cancel).setOnClickListener(this);
        findViewById(R.id.ly_confirm).setOnClickListener(this);
        this.ly_ponit_1 = findViewById(R.id.ly_point_1);
        this.ly_ponit_2 = findViewById(R.id.ly_point_2);
        this.ly_ponit_3 = findViewById(R.id.ly_point_3);
        this.ly_ponit_4 = findViewById(R.id.ly_point_4);
        this.ly_ponit_5 = findViewById(R.id.ly_point_5);
        this.ly_ponit_1.setOnClickListener(this);
        this.ly_ponit_2.setOnClickListener(this);
        this.ly_ponit_3.setOnClickListener(this);
        this.ly_ponit_4.setOnClickListener(this);
        this.ly_ponit_5.setOnClickListener(this);
        this.ly_ponit_5.setSelected(true);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSMasicManager cSMasicManager = new CSMasicManager(this.mContext);
        this.masicManager = cSMasicManager;
        CSMosaicRecyclerViewAdapter cSMosaicRecyclerViewAdapter = new CSMosaicRecyclerViewAdapter(this.mContext, cSMasicManager.getResList());
        this.viewAdapter = cSMosaicRecyclerViewAdapter;
        this.mRecyclerView.setAdapter(cSMosaicRecyclerViewAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.viewAdapter.setOnRecyclerViewItemClikListener(new CSMosaicRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.libfuncview.masicview.CSMasicBarView.4
            @Override // com.picspool.libfuncview.masicview.CSMosaicRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                CSMasicBarView.this.imageView_eraser.setSelected(false);
                CSMosaicRes cSMosaicRes = (CSMosaicRes) dMWBRes;
                CSMasicBarView.this.mcv_content.setRes(cSMosaicRes);
                CSMasicBarView.this.currenMode = cSMosaicRes.getmMosaicMode();
                if (CSMasicBarView.this.masicBarAnalyticsLinstener != null) {
                    CSMasicBarView.this.masicBarAnalyticsLinstener.onMosaicStyleClick(dMWBRes.getName());
                }
                Context context = CSMasicBarView.this.mContext;
                CSLibUiConfig.pointEventSingleSaveMode1(context, "mosaic", "" + i + dMWBRes.getName());
            }
        });
        this.viewAdapter.setSelectedPos(1);
        this.currenMode = CSDrawMosaic.Mode.NORMAL;
    }

    @Override // com.picspool.libfuncview.masicview.CSMasicView.GPUImageOnTouchListener
    public void onGPUImageTouchEvent(MotionEvent motionEvent, float f, float f2, float f3) {
        this.mcv_content.actionTouch(motionEvent, f, f2, f3);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ly_undo) {
            if (this.mcv_content.getBackStatus()) {
                this.mcv_content.actionUndo();
            }
            this.imageView_undo.setSelected(this.mcv_content.getBackStatus());
            this.imageView_redo.setSelected(this.mcv_content.getForwardStatus());
        } else if (id == R.id.ly_redo) {
            if (this.mcv_content.getForwardStatus()) {
                this.mcv_content.actionRedo();
            }
            this.imageView_undo.setSelected(this.mcv_content.getBackStatus());
            this.imageView_redo.setSelected(this.mcv_content.getForwardStatus());
        } else if (id == R.id.ly_cancel) {
            onMasicBarViewClickLinstener onmasicbarviewclicklinstener = this.mListener;
            if (onmasicbarviewclicklinstener != null) {
                onmasicbarviewclicklinstener.onBack();
            }
        } else if (id == R.id.ly_confirm) {
            onMasicBarViewClickLinstener onmasicbarviewclicklinstener2 = this.mListener;
            if (onmasicbarviewclicklinstener2 != null) {
                onmasicbarviewclicklinstener2.onConfirm(this.mMasicView.getImageBitmap());
            }
        } else if (id == R.id.ly_eraser) {
            if (this.mcv_content.getMode() == CSDrawMosaic.Mode.ERASE) {
                this.imageView_eraser.setSelected(false);
                this.mcv_content.setMode(this.currenMode);
                return;
            }
            this.imageView_eraser.setSelected(true);
            this.mcv_content.actionEraser();
        } else if (id == R.id.ly_point_1) {
            resetpointselect();
            this.ly_ponit_1.setSelected(true);
            this.mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(this.mContext, 4.0f));
            this.mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(this.mContext, 4.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener = this.masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener != null) {
                onmasicbaranalyticslinstener.onMosaicPathWidth("4");
            }
        } else if (id == R.id.ly_point_2) {
            resetpointselect();
            this.ly_ponit_2.setSelected(true);
            this.mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(this.mContext, 12.0f));
            this.mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(this.mContext, 12.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener2 = this.masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener2 != null) {
                onmasicbaranalyticslinstener2.onMosaicPathWidth("12");
            }
        } else if (id == R.id.ly_point_3) {
            resetpointselect();
            this.ly_ponit_3.setSelected(true);
            this.mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(this.mContext, 14.0f));
            this.mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(this.mContext, 14.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener3 = this.masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener3 != null) {
                onmasicbaranalyticslinstener3.onMosaicPathWidth("14");
            }
        } else if (id == R.id.ly_point_4) {
            resetpointselect();
            this.ly_ponit_4.setSelected(true);
            this.mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(this.mContext, 18.0f));
            this.mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(this.mContext, 18.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener4 = this.masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener4 != null) {
                onmasicbaranalyticslinstener4.onMosaicPathWidth("18");
            }
        } else if (id == R.id.ly_point_5) {
            resetpointselect();
            this.ly_ponit_5.setSelected(true);
            this.mcv_content.setGridWidth(DMScreenInfoUtil.dip2px(this.mContext, 26.0f));
            this.mcv_content.setPathWidth(DMScreenInfoUtil.dip2px(this.mContext, 26.0f));
            onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener5 = this.masicBarAnalyticsLinstener;
            if (onmasicbaranalyticslinstener5 != null) {
                onmasicbaranalyticslinstener5.onMosaicPathWidth("26");
            }
        }
    }

    private void resetpointselect() {
        this.ly_ponit_1.setSelected(false);
        this.ly_ponit_2.setSelected(false);
        this.ly_ponit_4.setSelected(false);
        this.ly_ponit_5.setSelected(false);
        this.ly_ponit_3.setSelected(false);
    }

    public void setonMasicBarViewClickLinstener(onMasicBarViewClickLinstener onmasicbarviewclicklinstener) {
        this.mListener = onmasicbarviewclicklinstener;
    }

    public void setMasicBarAnalyticsLinstener(onMasicBarAnalyticsLinstener onmasicbaranalyticslinstener) {
        this.masicBarAnalyticsLinstener = onmasicbaranalyticslinstener;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        onMasicBarViewClickLinstener onmasicbarviewclicklinstener;
        if (i != 4 || (onmasicbarviewclicklinstener = this.mListener) == null) {
            return true;
        }
        onmasicbarviewclicklinstener.onBack();
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CSDrawMosaic cSDrawMosaic = this.mcv_content;
        if (cSDrawMosaic != null) {
            cSDrawMosaic.release();
        }
    }
}
