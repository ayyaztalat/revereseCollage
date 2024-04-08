package com.picspool.libfuncview.effect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PaintFlagsDrawFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView;
import com.picspool.libfuncview.effect.explistview.CSExpListView;
import com.picspool.libfuncview.effect.onlinestore.CSOLSEffectActivity;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import com.picspool.libfuncview.effect.res.CSEffectRes;
import com.picspool.libfuncview.onlinestore.CSMaterialFactory;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.res.CSGroupRes;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSEffectBar extends FrameLayout {
    public static final String EffectApply = "effect_apply";
    public static int OLSACT1 = 64;
    public static int OLSACT2 = 65;
    private CSEffectAdjustView effectAdjustView;
    private CSEffectBarManager2 effectBarManager;
    private CSEffectRes effectRes;
    private int effectStrength;
    private Bitmap effectbmp;
    private CSExpListView expListView;
    private GPUFilterType gpuFilterType;
    private GPUImageView gpuImageView;
    private View lyProgress;
    private FrameLayout lyRoot;
    private Context mContext;
    Bitmap mFilterBmp;
    private CSBarViewControlListener mListener;
    private float progress_rotate;
    private RecyclerView recyclerView;
    private int selectEffectPos;
    private Bitmap srcbmp;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSEffectBar(Context context, Bitmap bitmap) {
        super(context);
        this.gpuFilterType = GPUFilterType.BLEND_SCREEN;
        this.selectEffectPos = -1;
        this.mFilterBmp = null;
        this.mContext = context;
        this.srcbmp = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_effect, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.lyRoot = (FrameLayout) findViewById(R.id.ly_effectbar_root);
        this.lyProgress = findViewById(R.id.ly_progress);
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.effect.CSEffectBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEffectBar.this.mListener != null) {
                    CSEffectBar.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.effect.CSEffectBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEffectBar.this.mListener != null) {
                    GPUFilter.asyncFilterForFilter(CSEffectBar.this.srcbmp, CSEffectBar.this.gpuImageView.getFilter(), new OnPostFilteredListener() { // from class: com.picspool.libfuncview.effect.CSEffectBar.2.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap) {
                            CSEffectBar.this.mListener.onOk(bitmap);
                            Context context = CSEffectBar.this.mContext;
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            sb.append(CSEffectBar.this.effectRes != null ? CSEffectBar.this.effectRes.getName() : "null");
                            CSLibUiConfig.pointEventSingleSaveMode1(context, CSMaterialFactory.EffectFunName, sb.toString());
                        }
                    });
                }
            }
        });
        findViewById(R.id.ly_store).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.effect.CSEffectBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSEffectBar.this.showOnlineStoreAct2();
            }
        });
        GPUImageView gPUImageView = (GPUImageView) findViewById(R.id.gpu_imageview);
        this.gpuImageView = gPUImageView;
        gPUImageView.setImage(this.srcbmp);
        this.gpuImageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.libui_gpubg_grey));
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        creatEffectListView();
        this.lyProgress.setVisibility(View.GONE);
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    private void creatEffectListView() {
        this.effectBarManager = new CSEffectBarManager2(this.mContext, true);
        CSExpListView cSExpListView = new CSExpListView();
        this.expListView = cSExpListView;
        cSExpListView.initView(this.mContext, this.recyclerView, this.effectBarManager.getGroupResList());
        this.expListView.setOnItemClickListener(new CSExpListView.onItemClickListener() { // from class: com.picspool.libfuncview.effect.CSEffectBar.4
            @Override // com.picspool.libfuncview.effect.explistview.CSExpListView.onItemClickListener
            public void onItemClicked(int i, int i2, DMWBRes dMWBRes) {
                if (dMWBRes instanceof CSEffectRes) {
                    CSEffectRes cSEffectRes = (CSEffectRes) dMWBRes;
                    if (cSEffectRes.isStoreAddIcon()) {
                        return;
                    }
                    int i3 = i + (i2 * 100);
                    if (i3 != CSEffectBar.this.selectEffectPos) {
                        CSEffectBar.this.selectEffectPos = i3;
                        CSEffectBar.this.effectRes = cSEffectRes;
                        Bitmap imageFromSDFile = CSEffectBar.this.effectRes.getImageType() == DMWBRes.LocationType.ONLINE ? DMBitmapUtil.getImageFromSDFile(CSEffectBar.this.mContext, CSEffectBar.this.effectRes.getImageFileName()) : DMBitmapUtil.getImageFromAssetsFile(CSEffectBar.this.mContext.getResources(), CSEffectBar.this.effectRes.getImageFileName());
                        if (imageFromSDFile != null && !imageFromSDFile.isRecycled()) {
                            if (CSEffectBar.this.effectbmp != null && !CSEffectBar.this.effectbmp.isRecycled() && CSEffectBar.this.effectbmp != imageFromSDFile) {
                                CSEffectBar.this.effectbmp.recycle();
                                CSEffectBar.this.effectbmp = null;
                            }
                            CSEffectBar.this.effectbmp = imageFromSDFile;
                        }
                        CSEffectBar cSEffectBar = CSEffectBar.this;
                        cSEffectBar.effectbmp = cSEffectBar.sampeZoomFromBitmap(cSEffectBar.effectbmp, 800);
                        CSEffectBar cSEffectBar2 = CSEffectBar.this;
                        cSEffectBar2.gpuFilterType = cSEffectBar2.effectRes.getFilterType();
                        if (CSEffectBar.this.gpuFilterType == null || CSEffectBar.this.gpuFilterType.equals(GPUFilterType.NOFILTER)) {
                            CSEffectBar.this.gpuFilterType = GPUFilterType.BLEND_SCREEN;
                        }
                        CSEffectBar cSEffectBar3 = CSEffectBar.this;
                        cSEffectBar3.effectStrength = cSEffectBar3.effectRes.getEffetStrength();
                        CSEffectBar.this.resetFilter(false);
                        return;
                    }
                    CSEffectBar.this.showAdjustView();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOnlineStoreAct2() {
        Intent intent = new Intent(this.mContext, CSOLSEffectActivity.class);
        intent.putExtra("effectbar", "true");
        ((Activity) this.mContext).startActivityForResult(intent, OLSACT2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAdjustView() {
        CSEffectAdjustView cSEffectAdjustView = this.effectAdjustView;
        if (cSEffectAdjustView == null) {
            CSEffectAdjustView cSEffectAdjustView2 = new CSEffectAdjustView(this.mContext);
            this.effectAdjustView = cSEffectAdjustView2;
            cSEffectAdjustView2.initDefaultDate(this.effectStrength, 0, this.gpuFilterType);
            this.lyRoot.addView(this.effectAdjustView, new LayoutParams(-1, -2, 80));
            this.effectAdjustView.setOnEffectAdjustViewListner(new CSEffectAdjustView.onEffectAdjustViewListner() { // from class: com.picspool.libfuncview.effect.CSEffectBar.5
                @Override // com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.onEffectAdjustViewListner
                public void onProgressStrengthChanged(SeekBar seekBar) {
                    CSEffectBar.this.effectStrength = seekBar.getProgress();
                    CSEffectBar.this.gpuImageView.getFilter().setMix(CSEffectBar.this.effectStrength / 100.0f);
                    CSEffectBar.this.gpuImageView.requestRender();
                }

                @Override // com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.onEffectAdjustViewListner
                public void onProgressRotationChanged(SeekBar seekBar) {
                    CSEffectBar.this.progress_rotate = seekBar.getProgress();
                    CSEffectBar.this.resetFilter(true);
                }

                @Override // com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.onEffectAdjustViewListner
                public void onPoterDuffModeClick(DMWBRes dMWBRes) {
                    if (dMWBRes instanceof GPUFilterRes) {
                        CSEffectBar.this.gpuFilterType = ((GPUFilterRes) dMWBRes).getFilterType();
                        CSEffectBar.this.resetFilter(false);
                    }
                }

                @Override // com.picspool.libfuncview.effect.adjustview.CSEffectAdjustView.onEffectAdjustViewListner
                public void onCancel() {
                    if (CSEffectBar.this.effectAdjustView != null) {
                        CSEffectBar.this.lyRoot.removeView(CSEffectBar.this.effectAdjustView);
                        CSEffectBar.this.effectAdjustView = null;
                    }
                }
            });
            return;
        }
        this.lyRoot.removeView(cSEffectAdjustView);
        this.effectAdjustView = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetFilter(boolean z) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.effectbmp;
        if (bitmap2 != null) {
            Bitmap copy = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
            if (z) {
                copy = getRotateBitmap(copy, this.progress_rotate);
            } else {
                this.progress_rotate = 0.0f;
            }
            if (copy != null && !copy.isRecycled()) {
                Bitmap bitmap3 = this.mFilterBmp;
                if (bitmap3 != null && !bitmap3.isRecycled() && (bitmap = this.mFilterBmp) != copy) {
                    bitmap.recycle();
                    this.mFilterBmp = null;
                }
                this.mFilterBmp = copy;
            }
            GPUImageFilter createFilterForBlendType = GPUFilter.createFilterForBlendType(this.mContext, this.gpuFilterType, this.mFilterBmp);
            createFilterForBlendType.setMix(this.effectStrength / 100.0f);
            this.gpuImageView.setFilter(createFilterForBlendType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap sampeZoomFromBitmap(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i;
        float f2 = f / width;
        float f3 = f / height;
        if (f2 >= f3) {
            f2 = f3;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f2);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (createBitmap == null || createBitmap.isRecycled() || createBitmap == bitmap) {
            return bitmap;
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap getRotateBitmap(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        Matrix matrix = new Matrix();
        float f2 = width / 2;
        float f3 = height / 2;
        matrix.preRotate(f, f2, f3);
        float abs = 1.0f - (Math.abs(45.0f - (f % 90.0f)) / 45.0f);
        if (abs > 0.42f) {
            abs = 0.42f;
        }
        float f4 = abs + 1.0f;
        matrix.postScale(f4, f4, f2, f3);
        canvas.drawBitmap(bitmap, matrix, null);
        if (bitmap != null && !bitmap.isRecycled() && bitmap != createBitmap) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        CSBarViewControlListener cSBarViewControlListener;
        if (i != 4 || (cSBarViewControlListener = this.mListener) == null) {
            return true;
        }
        cSBarViewControlListener.onCancel();
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (intent != null) {
            defaultSelect((CSEMaterialRes) intent.getSerializableExtra(EffectApply));
        } else {
            defaultSelect(null);
        }
    }

    public void defaultSelect(CSEMaterialRes cSEMaterialRes) {
        this.lyProgress.setVisibility(View.VISIBLE);
        creatEffectListView();
        List<DMWBRes> groupResList = this.effectBarManager.getGroupResList();
        int i = -1;
        CSGroupRes cSGroupRes = null;
        if (groupResList != null && groupResList.size() > 0 && cSEMaterialRes != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= groupResList.size()) {
                    break;
                }
                cSGroupRes = (CSGroupRes) groupResList.get(i2);
                if (cSGroupRes.getOlFilePath() != null && cSGroupRes.getOlFilePath().equals(cSEMaterialRes.getContentFilePath())) {
                    cSGroupRes.setExpanded(true);
                    i = i2;
                    break;
                }
                i2++;
            }
        }
        if (i >= 0 && cSGroupRes != null) {
            int size = cSGroupRes.getList_res().size();
            for (int i3 = 0; i3 < size; i3++) {
                groupResList.add(i + i3 + 1, cSGroupRes.getList_res().get(i3));
            }
        }
        if (i >= 0 && i < this.effectBarManager.getGroupResList().size()) {
            this.expListView.getmAdapter().setSelectPos(i);
            this.expListView.getmAdapter().notifyDataSetChanged();
            this.recyclerView.scrollToPosition(i);
        }
        this.lyProgress.setVisibility(View.GONE);
    }
}
