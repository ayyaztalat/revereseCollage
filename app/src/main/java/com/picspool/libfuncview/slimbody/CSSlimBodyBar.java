package com.picspool.libfuncview.slimbody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.slimbody.CSSlimFaceView;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.libfuncview.utils.CSTextReadUtil;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSlimBodyBar extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "SlimFaceActivity";
    private List<Bitmap> addedSlimFace;
    private FrameLayout fl_preview_left;
    private CSGPUImageSlimBodyFilter gpuImageSlimFaceFilter;
    private ImageView iv_compare;
    private ImageView iv_left;
    private ImageView iv_preview;
    private ImageView iv_preview_point;
    private ImageView iv_right;
    private ImageView iv_src;
    private CSSlimFaceView ivt_main;
    private View ly_ponit_1;
    private View ly_ponit_2;
    private View ly_ponit_3;
    private View ly_ponit_4;
    private View ly_ponit_5;
    private float mAdjustradius;
    private Context mContext;
    private CSBarViewControlListener mListener;
    private int previewWidth;
    private Bitmap previewbmp;
    private List<Bitmap> revokeSlimFace;
    private Bitmap srcBitmap;
    private Bitmap temp;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSSlimBodyBar(Context context, Bitmap bitmap) {
        super(context);
        this.mAdjustradius = 0.5f;
        this.previewWidth = 0;
        this.mContext = context;
        this.srcBitmap = bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        initView();
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_slimebody, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.slimbody.CSSlimBodyBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSlimBodyBar.this.mListener != null) {
                    CSSlimBodyBar.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.slimbody.CSSlimBodyBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSlimBodyBar.this.mListener != null) {
                    CSSlimBodyBar.this.mListener.onOk(CSSlimBodyBar.this.temp);
                }
            }
        });
        this.ivt_main = (CSSlimFaceView) findViewById(R.id.ivt_main);
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
        this.ly_ponit_3.setSelected(true);
        this.iv_src = (ImageView) findViewById(R.id.iv_src);
        ImageView imageView = (ImageView) findViewById(R.id.iv_compare);
        this.iv_compare = imageView;
        // from class: com.picspool.libfuncview.slimbody.CSSlimBodyBar.3
// android.view.View.OnTouchListener
        imageView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == 0) {
                CSSlimBodyBar.this.iv_src.setImageMatrix(CSSlimBodyBar.this.ivt_main.getImageViewMatrix());
                CSSlimBodyBar.this.iv_src.setVisibility(View.VISIBLE);
                CSSlimBodyBar.this.iv_compare.setPressed(true);
                return true;
            } else if (motionEvent.getAction() == 1) {
                CSSlimBodyBar.this.iv_src.setVisibility(View.GONE);
                CSSlimBodyBar.this.iv_compare.setPressed(false);
                return true;
            } else {
                return false;
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.iv_left);
        this.iv_left = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.iv_right);
        this.iv_right = imageView3;
        imageView3.setOnClickListener(this);
        this.fl_preview_left = (FrameLayout) findViewById(R.id.fl_preview_left);
        this.iv_preview = (ImageView) findViewById(R.id.iv_preview);
        this.previewWidth = DMScreenInfoUtil.dip2px(this.mContext, 103.0f);
        this.iv_preview_point = (ImageView) findViewById(R.id.iv_preview_point);
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    private void initData() {
        this.addedSlimFace = new ArrayList();
        this.revokeSlimFace = new ArrayList();
        this.ivt_main.setLockTouch(true);
        this.ivt_main.setImageBitmap(this.srcBitmap);
        this.ivt_main.setDisplayType(CSImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        this.iv_src.setImageBitmap(this.srcBitmap);
        this.iv_preview.setImageBitmap(this.srcBitmap);
        this.temp = this.srcBitmap;
        this.iv_src.setImageMatrix(this.ivt_main.getImageViewMatrix());
        this.ivt_main.setOnSingleDragListener(new CSSlimFaceView.OnSingleDragListener() { // from class: com.picspool.libfuncview.slimbody.CSSlimBodyBar.4
            @Override // com.picspool.libfuncview.slimbody.CSSlimFaceView.OnSingleDragListener
            public void onActionDown(MotionEvent motionEvent) {
            }

            @Override // com.picspool.libfuncview.slimbody.CSSlimFaceView.OnSingleDragListener
            public void onSingleDrag(Point point, MotionEvent motionEvent) {
                CSSlimBodyBar.this.fl_preview_left.setVisibility(View.GONE);
                CSSlimBodyBar.this.iv_left.setImageResource(R.drawable.libui_do_left_able);
                CSSlimBodyBar.this.revokeSlimFace.clear();
                CSSlimBodyBar.this.iv_right.setImageResource(R.drawable.libui_do_right_disable);
                RectF bitmapRect = CSSlimBodyBar.this.ivt_main.getBitmapRect();
                CSSlimBodyBar.this.setBitmapToDisplay(new float[]{(point.x - bitmapRect.left) / bitmapRect.width(), (point.y - bitmapRect.top) / bitmapRect.height()}, new float[]{(motionEvent.getX() - bitmapRect.left) / bitmapRect.width(), (motionEvent.getY() - bitmapRect.top) / bitmapRect.height()});
            }

            @Override // com.picspool.libfuncview.slimbody.CSSlimFaceView.OnSingleDragListener
            public void onActionMove(MotionEvent motionEvent) {
                CSSlimBodyBar.this.fl_preview_left.setVisibility(View.VISIBLE);
                CSSlimBodyBar.this.setPreviewBitmap(motionEvent.getX(), motionEvent.getY(), CSSlimBodyBar.this.ivt_main.getImageViewMatrix());
            }

            @Override // com.picspool.libfuncview.slimbody.CSSlimFaceView.OnSingleDragListener
            public void onActionPointerDown(MotionEvent motionEvent) {
                CSSlimBodyBar.this.fl_preview_left.setVisibility(View.GONE);
            }
        });
        slimFace(new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBitmapToDisplay(float[] fArr, float[] fArr2) {
        slimFace(fArr, fArr2);
        GPUFilter.asyncFilterForFilter(this.temp, this.gpuImageSlimFaceFilter, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.slimbody.CSSlimBodyBar.5
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap) {
                Bitmap bitmap2;
                CSSlimBodyBar.this.temp = bitmap;
                if (CSSlimBodyBar.this.addedSlimFace.size() >= 5 && (bitmap2 = (Bitmap) CSSlimBodyBar.this.addedSlimFace.remove(0)) != null && !bitmap2.isRecycled()) {
                    bitmap2.recycle();
                }
                CSSlimBodyBar.this.addedSlimFace.add(bitmap);
                CSSlimBodyBar.this.ivt_main.setImageBitmapWithStatKeep(bitmap);
            }
        });
    }

    private void slimFace(float[] fArr, float[] fArr2) {
        if (this.gpuImageSlimFaceFilter == null) {
            this.gpuImageSlimFaceFilter = new CSGPUImageSlimBodyFilter(CSTextReadUtil.readTextFromAsset(this.mContext, "slimbody/slimface_fragment_shader.glsl"));
        }
        this.gpuImageSlimFaceFilter.setAdjustRadiusLocation(this.mAdjustradius * 0.2f);
        this.gpuImageSlimFaceFilter.setAdjustSrcLocation(fArr);
        this.gpuImageSlimFaceFilter.setAdjustDstLocation(fArr2);
    }

    private void adjustLeftPreviewPointSize(int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) this.iv_preview_point.getLayoutParams();
        layoutParams.width = DMScreenInfoUtil.dip2px(this.mContext, i);
        layoutParams.height = DMScreenInfoUtil.dip2px(this.mContext, i2);
        this.iv_preview_point.setLayoutParams(layoutParams);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_left) {
            if (this.addedSlimFace.size() > 0) {
                List<Bitmap> list = this.revokeSlimFace;
                List<Bitmap> list2 = this.addedSlimFace;
                list.add(list2.remove(list2.size() - 1));
                if (this.addedSlimFace.size() == 0) {
                    this.iv_left.setImageResource(R.drawable.libui_do_left_disable);
                    Bitmap bitmap = this.srcBitmap;
                    this.temp = bitmap;
                    this.ivt_main.setImageBitmapWithStatKeep(bitmap);
                } else {
                    List<Bitmap> list3 = this.addedSlimFace;
                    Bitmap bitmap2 = list3.get(list3.size() - 1);
                    this.temp = bitmap2;
                    this.ivt_main.setImageBitmapWithStatKeep(bitmap2);
                }
                this.iv_right.setImageResource(R.drawable.libui_do_right_able);
            }
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "left");
        } else if (id == R.id.iv_right) {
            if (this.revokeSlimFace.size() > 0) {
                List<Bitmap> list4 = this.addedSlimFace;
                List<Bitmap> list5 = this.revokeSlimFace;
                list4.add(list5.remove(list5.size() - 1));
                if (this.revokeSlimFace.size() == 0) {
                    this.iv_right.setImageResource(R.drawable.libui_do_right_disable);
                }
                List<Bitmap> list6 = this.addedSlimFace;
                Bitmap bitmap3 = list6.get(list6.size() - 1);
                this.temp = bitmap3;
                this.ivt_main.setImageBitmapWithStatKeep(bitmap3);
                this.iv_left.setImageResource(R.drawable.libui_do_left_able);
            }
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "right");
        } else if (id == R.id.ly_point_1) {
            resetpointselect();
            this.ly_ponit_1.setSelected(true);
            this.ivt_main.setRadius(DMScreenInfoUtil.dip2px(this.mContext, 42.0f));
            adjustLeftPreviewPointSize(12, 12);
            this.mAdjustradius = 0.2f;
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "point1");
        } else if (id == R.id.ly_point_2) {
            resetpointselect();
            this.ly_ponit_2.setSelected(true);
            this.ivt_main.setRadius(DMScreenInfoUtil.dip2px(this.mContext, 50.0f));
            adjustLeftPreviewPointSize(15, 15);
            this.mAdjustradius = 0.35f;
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "point2");
        } else if (id == R.id.ly_point_3) {
            resetpointselect();
            this.ly_ponit_3.setSelected(true);
            this.ivt_main.setRadius(DMScreenInfoUtil.dip2px(this.mContext, 58.0f));
            adjustLeftPreviewPointSize(18, 18);
            this.mAdjustradius = 0.5f;
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "point3");
        } else if (id == R.id.ly_point_4) {
            resetpointselect();
            this.ly_ponit_4.setSelected(true);
            this.ivt_main.setRadius(DMScreenInfoUtil.dip2px(this.mContext, 68.0f));
            adjustLeftPreviewPointSize(21, 21);
            this.mAdjustradius = 0.65f;
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "point4");
        } else if (id == R.id.ly_point_5) {
            resetpointselect();
            this.ly_ponit_5.setSelected(true);
            this.ivt_main.setRadius(DMScreenInfoUtil.dip2px(this.mContext, 76.0f));
            adjustLeftPreviewPointSize(24, 24);
            this.mAdjustradius = 0.8f;
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "slimbody", "point5");
        }
    }

    private void resetpointselect() {
        this.ly_ponit_1.setSelected(false);
        this.ly_ponit_2.setSelected(false);
        this.ly_ponit_4.setSelected(false);
        this.ly_ponit_5.setSelected(false);
        this.ly_ponit_3.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPreviewBitmap(float f, float f2, Matrix matrix) {
        matrix.set(matrix);
        int i = this.previewWidth;
        matrix.postTranslate((i / 2) - f, (i / 2) - f2);
        this.iv_preview.setImageMatrix(matrix);
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
