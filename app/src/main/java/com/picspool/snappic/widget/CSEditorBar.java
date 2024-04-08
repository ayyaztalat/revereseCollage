package com.picspool.snappic.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;
import com.touch.android.library.imagezoom.ImageViewTouch;
import com.touch.android.library.imagezoom.ImageViewTouchBase;

/* loaded from: classes.dex */
public class CSEditorBar extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "CSEditorBar";
    private CSBarViewControlListener barViewControlListener;
    private Bitmap dstbmp;
    private ImageViewTouch imageViewTouch;
    private ImageView img_main;
    private View ly_crop;
    private FrameLayout ly_root;
    private Context mContext;
    private Matrix matrix;
    int rotation;
    private Bitmap srcbmp;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.barViewControlListener = cSBarViewControlListener;
    }

    public CSEditorBar(Context context, Bitmap bitmap) {
        super(context);
        this.rotation = 0;
        this.mContext = context;
        this.srcbmp = bitmap;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_editor, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        ImageViewTouch imageViewTouch = (ImageViewTouch) findViewById(R.id.img_pic);
        this.imageViewTouch = imageViewTouch;
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        this.imageViewTouch.setImageBitmap(this.srcbmp);
        this.imageViewTouch.setLockTouch(true);
        this.ly_root = (FrameLayout) findViewById(R.id.ly_editor_root);
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSEditorBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEditorBar.this.barViewControlListener != null) {
                    CSEditorBar.this.barViewControlListener.onOk(CSEditorBar.this.imageViewTouch.getDispalyImage());
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSEditorBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEditorBar.this.barViewControlListener != null) {
                    CSEditorBar.this.barViewControlListener.onCancel();
                }
            }
        });
        this.ly_crop = findViewById(R.id.ly_crop);
        findViewById(R.id.ly_right).setOnClickListener(this);
        findViewById(R.id.ly_left).setOnClickListener(this);
        findViewById(R.id.ly_hflip).setOnClickListener(this);
        findViewById(R.id.ly_vflip).setOnClickListener(this);
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ly_right) {
            this.imageViewTouch.postRotation(90.0f);
            this.rotation += 90;
            fittoscreen();
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "editor", "right");
        }
        if (id == R.id.ly_left) {
            this.imageViewTouch.postRotation(-90.0f);
            this.rotation -= 90;
            fittoscreen();
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "editor", "left");
        }
        if (id == R.id.ly_hflip) {
            this.imageViewTouch.Reversal(180.0f);
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "editor", "hflip");
        }
        if (id == R.id.ly_vflip) {
            this.imageViewTouch.Reversal(0.0f);
            CSLibUiConfig.pointEventSingleSaveMode1(this.mContext, "editor", "vflip");
        }
    }

    private void fittoscreen() {
        float width;
        int i = 0;
        Bitmap bitmap = this.srcbmp;
        if (bitmap == null || bitmap.getHeight() == 0 || this.srcbmp.getWidth() == 0 || this.imageViewTouch.getWidth() == 0 || this.imageViewTouch.getHeight() == 0 || (width = this.srcbmp.getWidth() / this.srcbmp.getHeight()) == 1.0f) {
            return;
        }
        float width2 = this.imageViewTouch.getWidth() / this.imageViewTouch.getHeight();
        if (width <= width2 || width >= 1.0f / width2) {
            width = i > 0 ? 1.0f / width2 : width2;
        }
        if (this.rotation % 180.0f == 0.0f) {
            width = 1.0f / width;
        }
        this.imageViewTouch.changeScale(width);
    }

    public View getLy_crop() {
        return this.ly_crop;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        CSBarViewControlListener cSBarViewControlListener;
        if (i != 4 || (cSBarViewControlListener = this.barViewControlListener) == null) {
            return true;
        }
        cSBarViewControlListener.onCancel();
        return true;
    }
}
