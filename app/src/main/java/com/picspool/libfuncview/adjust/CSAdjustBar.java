package com.picspool.libfuncview.adjust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.adjust.CSAdjustCurveTouchView;
import com.picspool.libfuncview.adjust.CSCurveBarViewAdapter;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSAdjustBar extends FrameLayout implements View.OnClickListener {
    private CSAdjustCurveTouchView adjustCurveTouchView;
    private CSCurveBarViewAdapter curveBarViewAdapter;
    private CSCurveResManager curveResManager;
    private GPUImageToneCurveFilter gpuImageToneCurveFilter;
    private GPUImageView gpuImageView;
    private List<PointF[]> listcurve;
    private View ly_cBlue_select;
    private View ly_cGreen_select;
    private View ly_cRed_select;
    private View ly_cRgb_select;
    private FrameLayout ly_img_container;
    private View ly_visablecm;
    private Context mContext;
    private CSBarViewControlListener mListener;
    private RecyclerView recyclerView;
    private int selectlistpos;
    private Bitmap srcbmp;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSAdjustBar(Context context, Bitmap bitmap) {
        super(context);
        this.selectlistpos = -1;
        this.mContext = context;
        this.srcbmp = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_adjust, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.adjust.CSAdjustBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSAdjustBar.this.mListener != null) {
                    CSAdjustBar.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.adjust.CSAdjustBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSAdjustBar.this.mListener != null) {
                    GPUFilter.asyncFilterForFilter(CSAdjustBar.this.srcbmp, CSAdjustBar.this.gpuImageToneCurveFilter, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.adjust.CSAdjustBar.2.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap) {
                            CSAdjustBar.this.mListener.onOk(bitmap);
                            Context context = CSAdjustBar.this.mContext;
                            CSLibUiConfig.pointEventSingleSaveMode1(context, "curve", "" + CSAdjustBar.this.selectlistpos);
                        }
                    });
                }
            }
        });
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
        creatModeListView();
        this.ly_img_container = (FrameLayout) findViewById(R.id.ly_img_container);
        GPUImageView gPUImageView = (GPUImageView) findViewById(R.id.gpu_imageview);
        this.gpuImageView = gPUImageView;
        gPUImageView.setImage(this.srcbmp);
        this.gpuImageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.libui_gpubg_grey));
        this.gpuImageToneCurveFilter = new GPUImageToneCurveFilter();
        List<PointF[]> creatListPointF = creatListPointF();
        this.listcurve = creatListPointF;
        this.gpuImageToneCurveFilter.setRgbCompositeControlPoints(creatListPointF.get(0));
        this.gpuImageToneCurveFilter.setRedControlPoints(this.listcurve.get(1));
        this.gpuImageToneCurveFilter.setGreenControlPoints(this.listcurve.get(2));
        this.gpuImageToneCurveFilter.setBlueControlPoints(this.listcurve.get(3));
        this.gpuImageView.setFilter(this.gpuImageToneCurveFilter);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 40.0f);
        CSAdjustCurveTouchView cSAdjustCurveTouchView = new CSAdjustCurveTouchView(this.mContext, screenWidth);
        this.adjustCurveTouchView = cSAdjustCurveTouchView;
        cSAdjustCurveTouchView.initsejiebmp(this.srcbmp);
        this.adjustCurveTouchView.setCurveTouchEventCallBack(new CSAdjustCurveTouchView.CurveTouchEventCallBack() { // from class: com.picspool.libfuncview.adjust.CSAdjustBar.3
            @Override // com.picspool.libfuncview.adjust.CSAdjustCurveTouchView.CurveTouchEventCallBack
            public void getPoints(CSCurveLine cSCurveLine) {
                CSAdjustCurveTouchView.CurveColor curveColor = cSCurveLine.getCurveColor();
                if (curveColor == CSAdjustCurveTouchView.CurveColor.RGB) {
                    CSAdjustBar.this.gpuImageToneCurveFilter.setRgbCompositeControlPoints(cSCurveLine.getPontFArray());
                } else if (curveColor == CSAdjustCurveTouchView.CurveColor.Red) {
                    CSAdjustBar.this.gpuImageToneCurveFilter.setRedControlPoints(cSCurveLine.getPontFArray());
                } else if (curveColor == CSAdjustCurveTouchView.CurveColor.Green) {
                    CSAdjustBar.this.gpuImageToneCurveFilter.setGreenControlPoints(cSCurveLine.getPontFArray());
                } else if (curveColor == CSAdjustCurveTouchView.CurveColor.Blue) {
                    CSAdjustBar.this.gpuImageToneCurveFilter.setBlueControlPoints(cSCurveLine.getPontFArray());
                }
                CSAdjustBar.this.gpuImageView.requestRender();
            }
        });
        this.ly_img_container.addView(this.adjustCurveTouchView, new LayoutParams(screenWidth, screenWidth, 17));
        findViewById(R.id.ly_cRgb).setOnClickListener(this);
        findViewById(R.id.ly_cRed).setOnClickListener(this);
        findViewById(R.id.ly_cGreen).setOnClickListener(this);
        findViewById(R.id.ly_cBlue).setOnClickListener(this);
        this.ly_cRgb_select = findViewById(R.id.ly_cRgb_select);
        this.ly_cRed_select = findViewById(R.id.ly_cRed_select);
        this.ly_cGreen_select = findViewById(R.id.ly_cGreen_select);
        this.ly_cBlue_select = findViewById(R.id.ly_cBlue_select);
        View findViewById2 = findViewById(R.id.ly_visablecm);
        this.ly_visablecm = findViewById2;
        findViewById2.setOnClickListener(this);
        this.ly_visablecm.setSelected(true);
    }

    private void creatModeListView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSCurveResManager cSCurveResManager = new CSCurveResManager(this.mContext);
        this.curveResManager = cSCurveResManager;
        CSCurveBarViewAdapter cSCurveBarViewAdapter = new CSCurveBarViewAdapter(this.mContext, cSCurveResManager.getCurveModeList(), this.srcbmp);
        this.curveBarViewAdapter = cSCurveBarViewAdapter;
        this.recyclerView.setAdapter(cSCurveBarViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.curveBarViewAdapter.setOnBarViewItemClikListener(new CSCurveBarViewAdapter.onABarViewItemClikListener() { // from class: com.picspool.libfuncview.adjust.CSAdjustBar.4
            @Override // com.picspool.libfuncview.adjust.CSCurveBarViewAdapter.onABarViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes) {
                if (CSAdjustBar.this.adjustCurveTouchView == null || !(dMWBRes instanceof CSCurveMode)) {
                    return;
                }
                CSCurveMode cSCurveMode = (CSCurveMode) dMWBRes;
                CSAdjustBar.this.adjustCurveTouchView.resetCurve(cSCurveMode);
                CSAdjustBar.this.resetCurveFilter(cSCurveMode);
                CSAdjustBar.this.selectlistpos = i;
            }
        });
    }

    private void resetCurveFilter() {
        this.gpuImageToneCurveFilter.setRgbCompositeControlPoints(this.listcurve.get(0));
        this.gpuImageToneCurveFilter.setRedControlPoints(this.listcurve.get(1));
        this.gpuImageToneCurveFilter.setGreenControlPoints(this.listcurve.get(2));
        this.gpuImageToneCurveFilter.setBlueControlPoints(this.listcurve.get(3));
        this.gpuImageView.requestRender();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCurveFilter(CSCurveMode cSCurveMode) {
        setSelect(this.ly_cRgb_select);
        this.gpuImageToneCurveFilter.setRgbCompositeControlPoints(cSCurveMode.getCurveLineList().get(0).getPontFArray());
        this.gpuImageToneCurveFilter.setRedControlPoints(cSCurveMode.getCurveLineList().get(1).getPontFArray());
        this.gpuImageToneCurveFilter.setGreenControlPoints(cSCurveMode.getCurveLineList().get(2).getPontFArray());
        this.gpuImageToneCurveFilter.setBlueControlPoints(cSCurveMode.getCurveLineList().get(3).getPontFArray());
        this.gpuImageView.requestRender();
    }

    private static List<PointF[]> creatListPointF() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        arrayList.add(initPointFs(0.0f, 0.0f, 1.0f, 1.0f));
        return arrayList;
    }

    private static PointF[] initPointFs(float f, float f2, float f3, float f4) {
        return new PointF[]{new PointF(f, f2), new PointF(f3, f4)};
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ly_cRgb) {
            setSelect(this.ly_cRgb_select);
            this.adjustCurveTouchView.setCurveCurrent(CSAdjustCurveTouchView.CurveColor.RGB);
        } else if (id == R.id.ly_cRed) {
            setSelect(this.ly_cRed_select);
            this.adjustCurveTouchView.setCurveCurrent(CSAdjustCurveTouchView.CurveColor.Red);
        } else if (id == R.id.ly_cGreen) {
            setSelect(this.ly_cGreen_select);
            this.adjustCurveTouchView.setCurveCurrent(CSAdjustCurveTouchView.CurveColor.Green);
        } else if (id == R.id.ly_cBlue) {
            setSelect(this.ly_cBlue_select);
            this.adjustCurveTouchView.setCurveCurrent(CSAdjustCurveTouchView.CurveColor.Blue);
        } else if (id == R.id.ly_visablecm) {
            if (this.adjustCurveTouchView.getVisibility() == View.VISIBLE) {
                this.adjustCurveTouchView.setVisibility(View.INVISIBLE);
                view.setSelected(false);
                return;
            }
            this.adjustCurveTouchView.setVisibility(View.VISIBLE);
            view.setSelected(true);
        }
    }

    private void setSelect(View view) {
        this.ly_cRgb_select.setVisibility(View.INVISIBLE);
        this.ly_cRed_select.setVisibility(View.INVISIBLE);
        this.ly_cGreen_select.setVisibility(View.INVISIBLE);
        this.ly_cBlue_select.setVisibility(View.INVISIBLE);
        view.setVisibility(View.VISIBLE);
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
}
