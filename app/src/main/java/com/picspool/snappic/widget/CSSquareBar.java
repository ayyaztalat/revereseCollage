package com.picspool.snappic.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import com.picspool.libsquare.res.CSShapeRes;
import com.picspool.libsquare.view.CSSquareView;
import com.picspool.snappic.activity.CSSysConfig;
import com.picspool.snappic.adapter.CSSquareRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSquareBarManager;
import com.picspool.snappic.res.CSSquareBarRes;
import com.picspool.snappic.widget.square.CSBgEffectBar;
import com.picspool.snappic.widget.square.CSShapeBar;
import com.picspool.snappic.widget.square.CSSquareBgBar;
import com.picspool.lib.bitmap.DMAsyncBitmapCropExecute;
import com.picspool.lib.bitmap.OnBitmapCropListener;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareBar extends FrameLayout implements CSBgEffectBar.onBgEffectClickListner {
    public static int BGEFFECTBLUR = 1;
    public static int BGEFFECTMOSAIC = 2;
    public static int BGEFFECTTILE = 3;
    public static int BGSQUARE = 4;
    private int bgcfColorvalue;
    private int bgcfPos;
    private int blurProgress;
    private int currentBgEffect;
    private long lastTimeMillis;
    private FrameLayout ly_root;
    private CSSquareBgBar mBgBar;
    private View mBgBar2;
    private CSBgEffectBar mBgEffectBar;
    private Context mContext;
    private onSquareBarClickListner mListenrer;
    private CSShapeBar mShapeBar;
    private CSSquareView mSquareview;
    private int mosaicProgress;
    private Bitmap oribitmap;
    private RecyclerView recyclerView;
    private int shapePos;
    private int tileProgress;

    /* loaded from: classes.dex */
    public interface onSquareBarClickListner {
        void onCancel();

        void onOk(Bitmap bitmap);

        void onPhotoSelectorClicked();
    }

    public CSSquareBar(Context context, Bitmap bitmap) {
        super(context);
        this.blurProgress = 60;
        this.mosaicProgress = 20;
        this.tileProgress = 40;
        this.bgcfPos = 0;
        this.bgcfColorvalue = 0;
        this.shapePos = 0;
        this.lastTimeMillis = 0L;
        this.currentBgEffect = 0;
        this.oribitmap = bitmap;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_square_bar, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        this.ly_root = (FrameLayout) findViewById(R.id.ly_squarebar_root);
        CSSquareView cSSquareView = (CSSquareView) findViewById(R.id.main_view);
        this.mSquareview = cSSquareView;
        cSSquareView.initSetPictureImageBitmap(this.oribitmap);
        DMWBColorRes dMWBColorRes = new DMWBColorRes();
        dMWBColorRes.setName("");
        dMWBColorRes.setIconID(-1);
        dMWBColorRes.setColorValue(-1);
        this.mSquareview.setSquareBackgroundRes(dMWBColorRes);
        this.mSquareview.setColorbg(this.bgcfColorvalue);
        this.mSquareview.setOnGetResultBitmapListener(new CSSquareView.OnGetResultBitmapListener() { // from class: com.picspool.snappic.widget.CSSquareBar.1
            @Override // com.picspool.libsquare.view.CSSquareView.OnGetResultBitmapListener
            public void getResultBitmapSuccess(Bitmap bitmap) {
                if (CSSquareBar.this.mListenrer != null) {
                    CSSquareBar.this.mListenrer.onOk(bitmap);
                }
            }
        });
        resetPicWH();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSquareBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSquareBar.this.mListenrer != null) {
                    CSSquareBar.this.mListenrer.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSSquareBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareBar.this.mSquareview.getSizeBitmap(CSSquareBar.this.oribitmap.getHeight() > CSSquareBar.this.oribitmap.getWidth() ? CSSquareBar.this.oribitmap.getHeight() : CSSquareBar.this.oribitmap.getWidth());
            }
        });
        CSSquareBarManager cSSquareBarManager = new CSSquareBarManager(this.mContext, this.oribitmap.getWidth() / this.oribitmap.getHeight());
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSSquareRecyclerViewAdapter cSSquareRecyclerViewAdapter = new CSSquareRecyclerViewAdapter(this.mContext, cSSquareBarManager.getBMWBResList());
        this.recyclerView.setAdapter(cSSquareRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        cSSquareRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSSquareRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.snappic.widget.CSSquareBar.4
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.picspool.snappic.adapter.CSSquareRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                char c;
                CSLibUiConfig.pointEventSingleSaveMode1(CSSquareBar.this.mContext, "square", dMWBRes.getName());
                String name = dMWBRes.getName();
                switch (name.hashCode()) {
                    case -1383304148:
                        if (name.equals("border")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1332194002:
                        if (name.equals("background")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1068356470:
                        if (name.equals("mosaic")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3027047:
                        if (name.equals("blur")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3560110:
                        if (name.equals("tile")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 109250890:
                        if (name.equals("scale")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 109399969:
                        if (name.equals("shape")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 280523342:
                        if (name.equals("gravity")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        CSSquareBar.this.onScaleClicked(dMWBRes);
                        return;
                    case 1:
                        CSSquareBar.this.onGravityClicked(dMWBRes);
                        return;
                    case 2:
                        CSSquareBar.this.onBorderClicked(dMWBRes);
                        return;
                    case 3:
                        CSSquareBar.this.onBlurClicked();
                        return;
                    case 4:
                        CSSquareBar.this.onMosaicClicked();
                        return;
                    case 5:
                        CSSquareBar.this.onTileClicked();
                        return;
                    case 6:
                        CSSquareBar.this.onShapeClicked();
                        return;
                    case 7:
                        CSSquareBar.this.onBgClicked();
                        return;
                    default:
                        return;
                }
            }
        });
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context2 = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context2, context2.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBorderClicked(DMWBRes dMWBRes) {
        DMWBImageRes currentRes = ((CSSquareBarRes) dMWBRes).getCurrentRes();
        if (currentRes == null || currentRes.getName() == null) {
            return;
        }
        this.mSquareview.setOverlapping(false);
        this.mSquareview.setFeatherBitmap(false);
        this.mSquareview.setShadow(false);
        this.mSquareview.clearBorder();
        if (currentRes.getName().compareTo("border_shadow") == 0) {
            this.mSquareview.setShadow(true);
        } else if (currentRes.getName().compareTo("border_feather") == 0) {
            this.mSquareview.setFeatherBitmap(true);
        } else if (currentRes.getName().compareTo("border_overlay") == 0) {
            this.mSquareview.setOverlapping(true);
        } else {
            this.mSquareview.setBorder(currentRes, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGravityClicked(DMWBRes dMWBRes) {
        String name = ((CSSquareBarRes) dMWBRes).getCurrentRes().getName();
        if (name.equals("gravity_center")) {
            this.mSquareview.img_pic.setCenter();
        } else if (name.equals("gravity_right")) {
            this.mSquareview.img_pic.setCenter();
            this.mSquareview.img_pic.setRight();
        } else if (name.equals("gravity_left")) {
            this.mSquareview.img_pic.setCenter();
            this.mSquareview.img_pic.setLeft();
        } else if (name.equals("gravity_top")) {
            this.mSquareview.img_pic.setCenter();
            this.mSquareview.img_pic.setTop();
        } else if (name.equals("gravity_bottom")) {
            this.mSquareview.img_pic.setCenter();
            this.mSquareview.img_pic.setBottom();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScaleClicked(DMWBRes dMWBRes) {
        String name = ((CSSquareBarRes) dMWBRes).getCurrentRes().getName();
        if (name.equals("scale_fill")) {
            this.mSquareview.setPicFill();
        } else if (name.equals("scale_inside")) {
            this.mSquareview.setPicToCenter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBgClicked() {
        if (this.mBgBar == null) {
            resetFuncBar();
            CSSquareBgBar cSSquareBgBar = new CSSquareBgBar(this.mContext);
            this.mBgBar = cSSquareBgBar;
            cSSquareBgBar.setSquareBgBarListener(new CSSquareBgBar.SquareBgBarListener() { // from class: com.picspool.snappic.widget.CSSquareBar.5
                @Override // com.picspool.snappic.widget.square.CSSquareBgBar.SquareBgBarListener
                public void onColorClicked(DMWBColorRes dMWBColorRes, int i) {
                    CSSquareBar.this.mSquareview.setSquareBackgroundRes(dMWBColorRes);
                }

                @Override // com.picspool.snappic.widget.square.CSSquareBgBar.SquareBgBarListener
                public void onCancel() {
                    CSSquareBar.this.resetFuncBar();
                }
            });
            this.ly_root.addView(this.mBgBar, new LayoutParams(-1, -2, 80));
            return;
        }
        resetFuncBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShapeClicked() {
        if (this.mShapeBar == null) {
            resetFuncBar();
            CSShapeBar cSShapeBar = new CSShapeBar(this.mContext);
            this.mShapeBar = cSShapeBar;
            cSShapeBar.getShapadapter().setSelectedPos(this.shapePos);
            this.mShapeBar.setShapeBarClickListner(new CSShapeBar.onShapeBarClickListner() { // from class: com.picspool.snappic.widget.CSSquareBar.6
                @Override // com.picspool.snappic.widget.square.CSShapeBar.onShapeBarClickListner
                public void onShapeItemClicke(int i, CSShapeRes cSShapeRes, boolean z) {
                    CSSquareBar.this.shapePos = i;
                    CSSquareBar.this.mSquareview.setShape(cSShapeRes);
                }

                @Override // com.picspool.snappic.widget.square.CSShapeBar.onShapeBarClickListner
                public void onCancel() {
                    CSSquareBar.this.resetFuncBar();
                }
            });
            this.ly_root.addView(this.mShapeBar, new LayoutParams(-1, -2, 80));
            return;
        }
        resetFuncBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTileClicked() {
        if (this.mBgEffectBar == null) {
            resetFuncBar();
            this.currentBgEffect = BGEFFECTTILE;
            CSBgEffectBar cSBgEffectBar = new CSBgEffectBar(this.mContext, this.currentBgEffect);
            this.mBgEffectBar = cSBgEffectBar;
            cSBgEffectBar.setDefalutValue(this.tileProgress, this.bgcfPos);
            this.mSquareview.setTileBackgroud(this.tileProgress / 100.0f);
            this.mSquareview.setColorbg(this.bgcfColorvalue);
            this.mBgEffectBar.setBgEffectClickListner(this);
            this.ly_root.addView(this.mBgEffectBar, new LayoutParams(-1, -2, 80));
            return;
        }
        resetFuncBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMosaicClicked() {
        if (this.mBgEffectBar == null) {
            resetFuncBar();
            this.currentBgEffect = BGEFFECTMOSAIC;
            CSBgEffectBar cSBgEffectBar = new CSBgEffectBar(this.mContext, this.currentBgEffect);
            this.mBgEffectBar = cSBgEffectBar;
            cSBgEffectBar.setDefalutValue(this.mosaicProgress, this.bgcfPos);
            this.mSquareview.setMosaicBackgroud(this.mosaicProgress / 100.0f);
            this.mSquareview.setColorbg(this.bgcfColorvalue);
            this.mBgEffectBar.setBgEffectClickListner(this);
            this.ly_root.addView(this.mBgEffectBar, new LayoutParams(-1, -2, 80));
            return;
        }
        resetFuncBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBlurClicked() {
        if (this.mBgEffectBar == null) {
            resetFuncBar();
            this.currentBgEffect = BGEFFECTBLUR;
            CSBgEffectBar cSBgEffectBar = new CSBgEffectBar(this.mContext, this.currentBgEffect);
            this.mBgEffectBar = cSBgEffectBar;
            cSBgEffectBar.setDefalutValue(this.blurProgress, this.bgcfPos);
            this.mSquareview.setBlurBackground(0.0f, this.blurProgress / 100.0f, true);
            this.mSquareview.setColorbg(this.bgcfColorvalue);
            this.mBgEffectBar.setBgEffectClickListner(this);
            this.ly_root.addView(this.mBgEffectBar, new LayoutParams(-1, -2, 80));
            return;
        }
        resetFuncBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetFuncBar() {
        CSBgEffectBar cSBgEffectBar = this.mBgEffectBar;
        if (cSBgEffectBar != null) {
            this.ly_root.removeView(cSBgEffectBar);
            this.mBgEffectBar = null;
        }
        CSShapeBar cSShapeBar = this.mShapeBar;
        if (cSShapeBar != null) {
            this.ly_root.removeView(cSShapeBar);
            this.mShapeBar = null;
        }
        CSSquareBgBar cSSquareBgBar = this.mBgBar;
        if (cSSquareBgBar != null) {
            this.ly_root.removeView(cSSquareBgBar);
            this.mBgBar = null;
        }
        View view = this.mBgBar2;
        if (view != null) {
            this.ly_root.removeView(view);
            this.mBgBar2 = null;
        }
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
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

    public void setOnSquareBarClickListner(onSquareBarClickListner onsquarebarclicklistner) {
        this.mListenrer = onsquarebarclicklistner;
    }

    public void setBgBitmap(Uri uri) {
        DMAsyncBitmapCropExecute.asyncBitmapCrop(this.mContext, uri, CSSysConfig.getImageQuality(this.mContext), new OnBitmapCropListener() { // from class: com.picspool.snappic.widget.CSSquareBar.7
            @Override // com.picspool.lib.bitmap.OnBitmapCropListener
            public void onBitmapCropFinish(Bitmap bitmap) {
                CSSquareBar.this.mSquareview.setBgsrc(bitmap);
                if (CSSquareBar.this.currentBgEffect == CSSquareBar.BGEFFECTBLUR) {
                    CSSquareBar.this.mSquareview.setBlurBackground(0.0f, CSSquareBar.this.blurProgress / 100.0f, true);
                } else if (CSSquareBar.this.currentBgEffect == CSSquareBar.BGEFFECTMOSAIC) {
                    CSSquareBar.this.mSquareview.setMosaicBackgroud(CSSquareBar.this.mosaicProgress / 100.0f);
                } else if (CSSquareBar.this.currentBgEffect == CSSquareBar.BGEFFECTTILE) {
                    CSSquareBar.this.mSquareview.setTileBackgroud(CSSquareBar.this.tileProgress / 100.0f);
                } else if (CSSquareBar.this.currentBgEffect == CSSquareBar.BGSQUARE) {
                    CSSquareBar.this.mSquareview.setTileBackgroud(-0.8f);
                }
            }
        });
    }

    @Override // com.picspool.snappic.widget.square.CSBgEffectBar.onBgEffectClickListner
    public void onProgressChanged(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        int i = this.currentBgEffect;
        if (i == BGEFFECTBLUR) {
            this.blurProgress = progress;
            this.mSquareview.setBlurBackground(0.0f, progress / 100.0f, true);
        } else if (i == BGEFFECTTILE) {
            this.tileProgress = progress;
            this.mSquareview.setTileBackgroud(progress / 100.0f);
        }
    }

    @Override // com.picspool.snappic.widget.square.CSBgEffectBar.onBgEffectClickListner
    public void onProgressStop(SeekBar seekBar) {
        if (this.currentBgEffect == BGEFFECTMOSAIC) {
            int progress = seekBar.getProgress();
            this.mosaicProgress = progress;
            this.mSquareview.setMosaicBackgroud(progress / 100.0f);
        }
    }

    @Override // com.picspool.snappic.widget.square.CSBgEffectBar.onBgEffectClickListner
    public void onColorClicked(DMWBColorRes dMWBColorRes, int i) {
        this.bgcfPos = i;
        int colorValue = dMWBColorRes.getColorValue();
        this.bgcfColorvalue = colorValue;
        int i2 = colorValue & 1442840575;
        this.bgcfColorvalue = i2;
        this.mSquareview.setColorbg(i2);
    }

    @Override // com.picspool.snappic.widget.square.CSBgEffectBar.onBgEffectClickListner
    public void onCancel() {
        resetFuncBar();
    }

    @Override // com.picspool.snappic.widget.square.CSBgEffectBar.onBgEffectClickListner
    public void onPhotoSelectorClicked() {
        onSquareBarClickListner onsquarebarclicklistner = this.mListenrer;
        if (onsquarebarclicklistner != null) {
            onsquarebarclicklistner.onPhotoSelectorClicked();
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        onSquareBarClickListner onsquarebarclicklistner;
        if (i != 4 || (onsquarebarclicklistner = this.mListenrer) == null) {
            return true;
        }
        onsquarebarclicklistner.onCancel();
        return true;
    }
}
