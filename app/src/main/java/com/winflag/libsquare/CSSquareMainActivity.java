package com.winflag.libsquare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.fragment.app.FragmentTransaction;
import com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropExecute;
import com.baiwang.libuiinstalens.crop.CSCropOnBitmapCropListener;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerRes;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.photoart.libsticker.sticker.DMStickerBarView;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.instatextview.textview.DMAndroidBug5497Workaround;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.libsquare.CSLibSquareConfig;
import com.sky.testproject.R;
import com.winflag.libsquare.adapter.CSSquareShapeBarViewAdapter;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import com.winflag.libsquare.res.CSShapeRes;
import com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView;
import com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView;
import com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView;
import com.winflag.libsquare.uiview.CSSquareUiFrameToolBarView;
import com.winflag.libsquare.uiview.CSSquareUiMainToolBarView;
import com.winflag.libsquare.uiview.CSSquareUiShapeBarView;
import com.winflag.libsquare.view.CSSquareView;
import com.winflag.libsquare.widget.label.CSShowTextStickerView;
import java.util.HashSet;
import java.util.Set;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.activity.DMProcessDialogFragment;
/* loaded from: classes3.dex */
public class CSSquareMainActivity extends DMFragmentActivityTemplate implements CSSquareView.OnGetResultBitmapListener {
    private CSSquareUiBlurAdjustView blurAdjustBarView;
    private CSSquareUiEditorToolBarView editorToolBarView;
    private CSSquareUiFilterToolBarView filterBarView;
    private CSSquareUiFrameToolBarView frameBarView;
    private FrameLayout highbarlayout;
    protected Uri imageUri;
    private DMInstaTextView instaTextView;
    private DMInstaTextView3 instaTextView3;
    protected RelativeLayout layout_sub_toolbar;
    private View ly_back;
    private View ly_done;
    public FrameLayout ly_maincontainer;
    private String mCurrentBackgroundName;
    private int mCurrentBackgroundPos;
    protected float mCurrentBlurRatio;
    private String mCurrentFilterName;
    protected int mCurrentFilterPos;
    private String mCurrentFrameName;
    private int mCurrentFramePos;
    private DMStickerBarView mStickerBarView;
    private CSXlbStickerBarView mStickerbar;
    protected CSSquareUiMainToolBarView mainToolBarView;
    protected Bitmap oriBitmap;
    protected CSSquareView squareMainView;
    private CSSquareUiShapeBarView squareUiShapeBarView;
    protected boolean isBottomOperationViewShow = false;
    protected int animationDuration = 300;
    public boolean isAdLoaded = false;
    public String func_blur = "blur_default";
    public String func_frame = "frame_default";
    public String func_bg = "bg_default";
    public String func_sticker = "sticker_default";
    public String func_filter = "filter_default";
    public String func_text = "text_default";
    public String func_shape = "shape_default";
    public String func_editor = "editor_default";
    private Set<String> mCurrentStickerNames = new HashSet();

    public void ShowInterstitialAD() {
    }

    public void chooseBlurBgFromLocal() {
    }

    public CSListNaitveAdRes getListNativeAd() {
        return null;
    }

    public void getResultBitmapSuccess(Bitmap bitmap) {
    }

    public void loadAdView() {
    }

    public void lockeResClicked(String str) {
    }

    protected void onAdjustItemClicked() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBackgroundItemSave(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCropItemClicked() {
    }

    protected void onFillBtnClick() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFilterItemSave(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrameItemSave(String str) {
    }

    protected void onGlitchItemClicked() {
    }

    public void onSquareBgItemClicked() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStickerItemSave(Set<String> set) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.square_activity_main);
        initViewImpl();
        Intent intent = getIntent();
        Uri data = intent.getData();
        this.imageUri = data;
        if (data == null) {
            this.imageUri = (Uri) intent.getParcelableExtra("SelectPicturePath");
        }
        if (this.imageUri == null) {
            this.imageUri = (Uri) intent.getParcelableExtra("uri");
        }
        if (this.imageUri == null) {
            String stringExtra = intent.getStringExtra("uri");
            if (stringExtra != null) {
                this.imageUri = Uri.parse(stringExtra);
            }
            if (this.imageUri == null) {
                Toast.makeText(this, "data wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        initData(this.imageUri);
        initInstaTextView();
    }

    private void initInstaTextView() {
        if (this.instaTextView3 == null) {
            this.instaTextView3 = (DMInstaTextView3) findViewById(R.id.instaTextView3);
            DMAndroidBug5497Workaround.assistActivity(this);
            this.instaTextView3.getShowTextView().setStickerCanvasView(this.squareMainView.getStickerCanvasView());
            this.squareMainView.addStickerStateCallSpreader(this.instaTextView3.getShowTextView());
        }
        this.instaTextView = (DMInstaTextView) findViewById(R.id.instaTextView);
        DMAndroidBug5497Workaround.assistActivity(this);
        this.instaTextView.getShowTextView().setStickerCanvasView(this.squareMainView.getStickerCanvasView());
        this.squareMainView.addStickerStateCallSpreader(this.instaTextView.getShowTextView());
        this.squareMainView.setOnGetResultBitmapListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.squareMainView.resetClear();
        Bitmap bitmap = this.oriBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.oriBitmap.recycle();
        }
        this.oriBitmap = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void initViewImpl() {
        this.mainToolBarView = (CSSquareUiMainToolBarView) findViewById(R.id.mainToolBarView);
        this.layout_sub_toolbar = (RelativeLayout) findViewById(R.id.layout_sub_toolbar);
        this.highbarlayout = (FrameLayout) findViewById(R.id.highbarlayout);
        this.mainToolBarView.setOnSquareUiMainToolBarViewListner(new CSSquareUiMainToolBarView.OnSquareUiMainToolBarViewListner() { // from class: com.winflag.libsquare.CSSquareMainActivity.1
            @Override // com.winflag.libsquare.uiview.CSSquareUiMainToolBarView.OnSquareUiMainToolBarViewListner
            public void OnSquareBottomBarItemClick(CSSquareUiMainToolBarView.SquareBottomItem squareBottomItem) {
                if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Editor) {
                    CSSquareMainActivity.this.onEditorItemClickImpl();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Blur) {
                    CSSquareMainActivity.this.onAdustItemClickImpl();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Background) {
                    CSSquareMainActivity.this.onSquareBgItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Filter) {
                    CSSquareMainActivity.this.onFilterItemClickImpl();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Text) {
                    CSSquareMainActivity.this.onTextItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Sticker) {
                    CSSquareMainActivity.this.onStickerItemClicked2(null);
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Frame) {
                    CSSquareMainActivity.this.onFrameItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Shape) {
                    CSSquareMainActivity.this.onShapeItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Glitch) {
                    CSSquareMainActivity.this.onGlitchItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Adjust) {
                    CSSquareMainActivity.this.onAdjustItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Crop) {
                    CSSquareMainActivity.this.onCropItemClicked();
                } else if (squareBottomItem == CSSquareUiMainToolBarView.SquareBottomItem.Ratio) {
                    CSSquareMainActivity.this.onFillBtnClick();
                }
            }
        });
        View findViewById = findViewById(R.id.ly_back);
        this.ly_back = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareMainActivity.this.showQuitDialog();
            }
        });
        View findViewById2 = findViewById(R.id.ly_done);
        this.ly_done = findViewById2;
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareMainActivity.this.onShareClicked();
            }
        });
        this.squareMainView = (CSSquareView) findViewById(R.id.squareMainView);
        this.ly_maincontainer = (FrameLayout) findViewById(R.id.ly_container);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onShareClicked() {
        showProcessDialog();
        this.squareMainView.getSizeBitmap(CSCropAsyncBitmapCropExecute.getImageQuality(this));
        String str = this.mCurrentFrameName;
        if (str != null) {
            onFrameItemSave(str);
        }
        Set<String> set = this.mCurrentStickerNames;
        if (set != null) {
            onStickerItemSave(set);
        }
        String str2 = this.mCurrentBackgroundName;
        if (str2 != null) {
            onBackgroundItemSave(str2);
        }
        String str3 = this.mCurrentFilterName;
        if (str3 != null) {
            onFilterItemSave(str3);
        }
    }

    private void initData(Uri uri) {
        showProcessDialog();
        cropBitmapImpl(uri);
    }

    private void cropBitmapImpl(Uri uri) {
        final int i = CSLibSquareConfig.maxQuality <= 0 ? 960 : CSLibSquareConfig.maxQuality;
        CSCropAsyncBitmapCropExecute.asyncBitmapCrop(this, uri, CSCropAsyncBitmapCropExecute.getImageQuality(this), new CSCropOnBitmapCropListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.4
            @Override // com.baiwang.libuiinstalens.crop.CSCropOnBitmapCropListener
            public void onBitmapCropFinish(Bitmap bitmap) {
                if (bitmap != null) {
                    Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(bitmap, i);
                    if (sampeZoomFromBitmap != bitmap && sampeZoomFromBitmap != null && !sampeZoomFromBitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    CSSquareMainActivity.this.oriBitmap = sampeZoomFromBitmap;
                    CSSquareMainActivity.this.squareMainView.initSetPictureImageBitmap(CSSquareMainActivity.this.oriBitmap.copy(Bitmap.Config.ARGB_8888, true));
                    if (CSSquareMainActivity.this.mCurrentBlurRatio == 0.0f) {
                        CSSquareMainActivity.this.setBlurBackground(0.5f, false);
                    } else {
                        CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                        cSSquareMainActivity.setBlurBackground(cSSquareMainActivity.mCurrentBlurRatio, false);
                    }
                }
                CSSquareMainActivity.this.dismissProcessDialog();
            }
        });
    }

    public void onShapeItemClicked() {
        if (this.squareUiShapeBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Shape, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Shape, true);
        CSSquareUiShapeBarView cSSquareUiShapeBarView = new CSSquareUiShapeBarView(this);
        this.squareUiShapeBarView = cSSquareUiShapeBarView;
        cSSquareUiShapeBarView.getShapadapter().setShapeBarItemClikListener(new CSSquareShapeBarViewAdapter.onShapeBarViewItemClikListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.5
            @Override // com.winflag.libsquare.adapter.CSSquareShapeBarViewAdapter.onShapeBarViewItemClikListener
            public void onClick(int i, CSShapeRes cSShapeRes, boolean z) {
                if (z && CSSquareMainActivity.this.isAdLoaded) {
                    CSSquareMainActivity.this.lockeResClicked(cSShapeRes.getName());
                    return;
                }
                CSSquareMainActivity.this.squareMainView.setShape(cSShapeRes);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_shape = "shape_" + cSShapeRes.getName();
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.squareUiShapeBarView);
        ChangeViewHeight(100);
        this.squareUiShapeBarView.startAnimation(translateAnimation);
    }

    public void locklistNotifyChanged() {
        CSSquareUiShapeBarView cSSquareUiShapeBarView = this.squareUiShapeBarView;
        if (cSSquareUiShapeBarView != null) {
            cSSquareUiShapeBarView.getShapadapter().notifyDataSetChanged();
        }
        CSSquareUiFrameToolBarView cSSquareUiFrameToolBarView = this.frameBarView;
        if (cSSquareUiFrameToolBarView != null) {
            cSSquareUiFrameToolBarView.getSquareFrameBarViewAdapter().notifyDataSetChanged();
        }
        CSSquareUiFilterToolBarView cSSquareUiFilterToolBarView = this.filterBarView;
        if (cSSquareUiFilterToolBarView != null) {
            cSSquareUiFilterToolBarView.getRecyclerViewAdapter().notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEditorItemClickImpl() {
        if (this.editorToolBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Editor, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Editor, true);
        CSSquareUiEditorToolBarView cSSquareUiEditorToolBarView = new CSSquareUiEditorToolBarView(this);
        this.editorToolBarView = cSSquareUiEditorToolBarView;
        cSSquareUiEditorToolBarView.setOnSquareUiEditorToolBarViewListner(new CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner() { // from class: com.winflag.libsquare.CSSquareMainActivity.6
            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onFillClick() {
                CSSquareMainActivity.this.squareMainView.setPicFill();
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_fill";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onSquareClick() {
                CSSquareMainActivity.this.squareMainView.setPicToCenter();
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_square";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onHorzontalClick() {
                CSSquareMainActivity.this.squareMainView.reversal(180.0f);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_horzontal";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onVertialClick() {
                CSSquareMainActivity.this.squareMainView.reversal(0.0f);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_vertical";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onLeftClick() {
                CSSquareMainActivity.this.squareMainView.rotation(-90.0f);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_left";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiEditorToolBarView.OnSquareUiEditorToolBarViewListner
            public void onRightClick() {
                CSSquareMainActivity.this.squareMainView.rotation(90.0f);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_editor = CSSquareMainActivity.this.func_editor + "_right";
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.editorToolBarView);
        ChangeViewHeight(100);
        this.editorToolBarView.startAnimation(translateAnimation);
    }

    protected void onFilterItemClickImpl() {
        if (this.filterBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Filter, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Filter, true);
        CSSquareUiFilterToolBarView cSSquareUiFilterToolBarView = new CSSquareUiFilterToolBarView(this, this.mCurrentFilterPos, getListNativeAd());
        this.filterBarView = cSSquareUiFilterToolBarView;
        cSSquareUiFilterToolBarView.setOnSquareUiFilterToolBarViewListener(new CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.7
            @Override // com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener
            public void onFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z) {
                CSSquareMainActivity.this.mCurrentFilterPos = i;
                if (z && CSSquareMainActivity.this.isAdLoaded) {
                    CSSquareMainActivity.this.lockeResClicked(gPUFilterRes.getName());
                } else {
                    CSSquareMainActivity.this.onFilterItemClick(gPUFilterRes.getName());
                    CSSquareMainActivity.this.squareMainView.setFilter(gPUFilterRes, new OnFilterFinishedListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.7.1
                        @Override // org.picspool.lib.filter.listener.OnFilterFinishedListener
                        public void postFinished() {
                            CSSquareMainActivity.this.dismissProcessDialog();
                            if (CSSquareMainActivity.this.mCurrentBlurRatio == 0.0f || !CSSquareMainActivity.this.squareMainView.getIsBlurBackground()) {
                                return;
                            }
                            CSSquareMainActivity.this.setBlurBackground(CSSquareMainActivity.this.mCurrentBlurRatio, true);
                        }
                    });
                }
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_filter = "filter_" + gPUFilterRes.getShowText();
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.filterBarView);
        ChangeViewHeight(100);
        this.filterBarView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrameItemClicked() {
        if (this.frameBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Frame, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Frame, true);
        CSSquareUiFrameToolBarView cSSquareUiFrameToolBarView = new CSSquareUiFrameToolBarView(this, this.mCurrentFramePos);
        this.frameBarView = cSSquareUiFrameToolBarView;
        cSSquareUiFrameToolBarView.setOnSquareUiFrameToolBarViewListener(new CSSquareUiFrameToolBarView.OnSquareUiFrameToolBarViewListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.8
            @Override // com.winflag.libsquare.uiview.CSSquareUiFrameToolBarView.OnSquareUiFrameToolBarViewListener
            public void onFrameClick(DMWBRes dMWBRes, int i, boolean z) {
                String name = dMWBRes.getName();
                if (z && CSSquareMainActivity.this.isAdLoaded) {
                    CSSquareMainActivity.this.lockeResClicked(name);
                } else if (dMWBRes == null || name == null) {
                } else {
                    CSSquareMainActivity.this.onFrameItemClick(name);
                    CSSquareMainActivity.this.mCurrentFramePos = i;
                    CSSquareMainActivity.this.resetBorder();
                    if (name.compareTo("border_shadow") == 0) {
                        CSSquareMainActivity.this.squareMainView.setShadow(true);
                    } else if (name.compareTo("border_feather") == 0) {
                        CSSquareMainActivity.this.squareMainView.setFeatherBitmap(true);
                    } else if (name.compareTo("border_overlay") == 0) {
                        CSSquareMainActivity.this.squareMainView.setOverlapping(true);
                    } else {
                        CSSquareMainActivity.this.squareMainView.setBorder(dMWBRes, null);
                    }
                    if (dMWBRes.getShowText() != null) {
                        CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                        cSSquareMainActivity.func_frame = "frame_" + dMWBRes.getShowText();
                    }
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.frameBarView);
        ChangeViewHeight(100);
        this.frameBarView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetBorder() {
        this.squareMainView.setOverlapping(false);
        this.squareMainView.setFeatherBitmap(false);
        this.squareMainView.setShadow(false);
        this.squareMainView.clearBorder();
    }

    public void onStickerItemClicked2(String str) {
        if (this.mStickerbar != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Sticker, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Sticker, true);
        this.isBottomOperationViewShow = true;
        CSXlbStickerBarView cSXlbStickerBarView = new CSXlbStickerBarView(this, str);
        this.mStickerbar = cSXlbStickerBarView;
        cSXlbStickerBarView.setRequestAppName("com.winflag.instalens");
        this.mStickerbar.setItemClickListener(new CSXlbStickerBarView.onStickerItemClickListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.9
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onStickerItemClicked(DMWBRes dMWBRes, int i) {
                Bitmap decodeFile;
                CSShowTextStickerView cSShowTextStickerView;
                CSStickerRes cSStickerRes = (CSStickerRes) dMWBRes;
                if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                    decodeFile = DMBitmapDbUtil.getImageFromAssetsFile(CSSquareMainActivity.this, cSStickerRes.getImageFileName());
                } else {
                    decodeFile = cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE ? BitmapFactory.decodeFile(cSStickerRes.getImageFileName()) : null;
                }
                if (decodeFile != null) {
                    if (CSSquareMainActivity.this.instaTextView != null && (cSShowTextStickerView = (CSShowTextStickerView) CSSquareMainActivity.this.instaTextView.getShowTextView()) != null) {
                        if (cSShowTextStickerView.getStickerCount() >= 8) {
                            Toast.makeText(CSSquareMainActivity.this, CSSquareMainActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_LONG).show();
                            return;
                        }
                        cSShowTextStickerView.addSticker(decodeFile);
                    }
                } else {
                    Toast.makeText(CSSquareMainActivity.this, "Resource Load faile !", Toast.LENGTH_LONG).show();
                }
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_sticker = CSSquareMainActivity.this.func_sticker + "_" + cSStickerRes.getGroup_name() + dMWBRes.getName();
                CSSquareMainActivity.this.onStickerItemClicked(cSStickerRes.getGroup_name());
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onClose() {
                CSSquareMainActivity.this.resetBottomBar();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStickerbar.getLayoutParams();
        int screenWidth = (int) ((DMScreenInfoUtil.screenWidth(this) * 11.0f) / 18.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, screenWidth);
        }
        this.mStickerbar.setLayoutParams(layoutParams);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, screenWidth, 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.mStickerbar, new FrameLayout.LayoutParams(-2, -2, 80));
        this.mStickerbar.startAnimation(translateAnimation);
        ChangeViewHeight(100);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStickerItemClicked(String str) {
        this.mCurrentStickerNames.add(str);
    }

    public void onStickerItemClicked() {
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Sticker, true);
        if (this.mStickerBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Sticker, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Sticker, true);
        this.isBottomOperationViewShow = true;
        DMStickerBarView dMStickerBarView = new DMStickerBarView(this);
        this.mStickerBarView = dMStickerBarView;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dMStickerBarView.getLayoutParams();
        int screenHeight = DMScreenInfoUtil.screenHeight(this);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, screenHeight);
        }
        this.mStickerBarView.setLayoutParams(layoutParams);
        new TranslateAnimation(0.0f, 0.0f, screenHeight, 0.0f).setDuration(this.animationDuration);
        this.highbarlayout.addView(this.mStickerBarView);
        this.mStickerBarView.setOnStickerChooseListener(new DMStickerBarView.OnStickerChooseListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.10
            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerChoose(DMWBRes dMWBRes) {
                ((DMWBImageRes) dMWBRes).getImageBitmap(CSSquareMainActivity.this, new DMWBImageRes.OnResImageLoadListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.10.1
                    @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFinish(Bitmap bitmap) {
                        CSShowTextStickerView cSShowTextStickerView;
                        if (CSSquareMainActivity.this.instaTextView == null || (cSShowTextStickerView = (CSShowTextStickerView) CSSquareMainActivity.this.instaTextView.getShowTextView()) == null) {
                            return;
                        }
                        if (cSShowTextStickerView.getStickerCount() >= 8) {
                            Toast.makeText(CSSquareMainActivity.this, CSSquareMainActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_LONG).show();
                            return;
                        }
                        cSShowTextStickerView.addSticker(bitmap);
                        CSSquareMainActivity.this.resetBottomBar();
                    }

                    @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFaile() {
                        Toast.makeText(CSSquareMainActivity.this, "Resource Load faile !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerClose() {
                CSSquareMainActivity.this.resetBottomBar();
            }
        });
    }

    public void onTextItemClicked() {
        resetBottomBar();
        DMInstaTextView3 dMInstaTextView3 = this.instaTextView3;
        if (dMInstaTextView3 != null) {
            dMInstaTextView3.addText();
            this.func_text += "_addtext";
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAdustItemClickImpl() {
        if (this.blurAdjustBarView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Blur, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Blur, true);
        this.isBottomOperationViewShow = true;
        CSSquareUiBlurAdjustView cSSquareUiBlurAdjustView = new CSSquareUiBlurAdjustView(this);
        this.blurAdjustBarView = cSSquareUiBlurAdjustView;
        float f = this.mCurrentBlurRatio;
        if (f == 0.0f) {
            cSSquareUiBlurAdjustView.setCurrentRatio(0.5f);
        } else {
            cSSquareUiBlurAdjustView.setCurrentRatio(f);
        }
        this.blurAdjustBarView.setCurrentBitmap(this.oriBitmap);
        this.blurAdjustBarView.setOnSquareUiBlurAdjustViewListener(new CSSquareUiBlurAdjustView.OnSquareUiBlurAdjustViewListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.11
            @Override // com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.OnSquareUiBlurAdjustViewListener
            public void onBlurClose() {
                DMWBColorRes dMWBColorRes = new DMWBColorRes();
                dMWBColorRes.setContext(CSSquareMainActivity.this);
                dMWBColorRes.setColorValue(-1);
                ColorDrawable colorDrawable = new ColorDrawable(dMWBColorRes.getColorValue());
                CSSquareMainActivity.this.squareMainView.backgroundColor = dMWBColorRes.getColorValue();
                CSSquareMainActivity.this.squareMainView.setSquareBackground(colorDrawable, true);
                CSSquareMainActivity.this.func_blur = "no_blur";
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.OnSquareUiBlurAdjustViewListener
            public void blurRatioChange(float f2) {
                CSSquareMainActivity.this.setBlurBackground(f2, false);
                CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                cSSquareMainActivity.func_blur = "blur_" + f2;
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.OnSquareUiBlurAdjustViewListener
            public void chooseBg() {
                CSSquareMainActivity.this.chooseBlurBgFromLocal();
            }

            @Override // com.winflag.libsquare.uiview.CSSquareUiBlurAdjustView.OnSquareUiBlurAdjustViewListener
            public void resetBg(Bitmap bitmap) {
                CSSquareMainActivity.this.squareMainView.setBlurBackground(bitmap, true);
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.blurAdjustBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 150.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.blurAdjustBarView.setLayoutParams(layoutParams);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, dip2px, 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.blurAdjustBarView);
        ChangeViewHeight(DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
        this.blurAdjustBarView.startAnimation(translateAnimation);
    }

    public void setBlurBg(Uri uri) {
        showProcessDialog();
        final int i = CSLibSquareConfig.maxQuality <= 0 ? 960 : CSLibSquareConfig.maxQuality;
        CSCropAsyncBitmapCropExecute.asyncBitmapCrop(this, uri, CSCropAsyncBitmapCropExecute.getImageQuality(this), new CSCropOnBitmapCropListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.12
            @Override // com.baiwang.libuiinstalens.crop.CSCropOnBitmapCropListener
            public void onBitmapCropFinish(Bitmap bitmap) {
                if (bitmap != null) {
                    Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(bitmap, i);
                    if (sampeZoomFromBitmap != bitmap && sampeZoomFromBitmap != null && !sampeZoomFromBitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                    if (CSSquareMainActivity.this.blurAdjustBarView != null) {
                        CSSquareMainActivity.this.blurAdjustBarView.setCurrentBitmap(sampeZoomFromBitmap);
                    }
                    CSSquareMainActivity.this.squareMainView.setBlurBackground(sampeZoomFromBitmap, true);
                }
                CSSquareMainActivity.this.dismissProcessDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void ChangeViewHeight(int i) {
        View findViewById = findViewById(R.id.layout_container);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        if (CSLibSquareConfig.isShowAd) {
            if (layoutParams != null) {
                layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 60);
            }
        } else if (layoutParams != null) {
            layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 60);
        }
        findViewById.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void resetBottomBar() {
        this.layout_sub_toolbar.removeAllViews();
        this.highbarlayout.removeAllViews();
        if (this.editorToolBarView != null) {
            this.editorToolBarView = null;
        }
        if (this.blurAdjustBarView != null) {
            this.blurAdjustBarView = null;
        }
        DMStickerBarView dMStickerBarView = this.mStickerBarView;
        if (dMStickerBarView != null) {
            dMStickerBarView.dispose();
            this.mStickerBarView = null;
        }
        CSSquareUiFilterToolBarView cSSquareUiFilterToolBarView = this.filterBarView;
        if (cSSquareUiFilterToolBarView != null) {
            cSSquareUiFilterToolBarView.dispose();
            this.filterBarView = null;
        }
        CSSquareUiFrameToolBarView cSSquareUiFrameToolBarView = this.frameBarView;
        if (cSSquareUiFrameToolBarView != null) {
            cSSquareUiFrameToolBarView.dispose();
            this.frameBarView = null;
        }
        if (this.squareUiShapeBarView != null) {
            this.squareUiShapeBarView = null;
        }
        if (this.mStickerbar != null) {
            this.mStickerbar = null;
        }
        this.mainToolBarView.resetSelectorStat();
        this.isBottomOperationViewShow = false;
        ChangeViewHeight(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBlurBackground(float f, boolean z) {
        this.squareMainView.setBlurBackground(this.mCurrentBlurRatio, f, z);
        this.mCurrentBlurRatio = f;
    }

    public void onBackImpl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.quit_string);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
//                if (AdmobConstants.isPositive(AdmobConstants.BACK_MAIN_INT_AS)) {
//                    AdmobLevelManager.getinstance().showAd(CSSquareMainActivity.this, new AdListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.13.1
//                        @Override // com.google.android.gms.ads.AdListener
//                        public void onAdClosed() {
//                        }
//                    }, "CSSquareMain");
//                }
                CSSquareMainActivity.this.finish();
                dialogInterface.dismiss();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.winflag.libsquare.CSSquareMainActivity.14
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    protected void showProcessDialog1() {
        try {
            if (this.dlg != null) {
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                beginTransaction.remove(this.dlg);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                this.dlg = null;
            }
            if (this.dlg == null) {
                this.dlg = new DMProcessDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("text", "Showing Ads …......");
                this.dlg.setArguments(bundle);
            }
            this.dlg.show(getSupportFragmentManager(), "process");
        } catch (Exception unused) {
        }
    }

    @Override // org.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        DMInstaTextView dMInstaTextView;
        if (i != 4 || ((dMInstaTextView = this.instaTextView) != null && dMInstaTextView.backKey())) {
            return false;
        }
        if (this.isBottomOperationViewShow) {
            resetBottomBar();
            return true;
        }
        showQuitDialog();
        return false;
    }

    public void showQuitDialog() {
        onBackImpl();
    }

    public ViewGroup getBannerAdContainer() {
        return (ViewGroup) findViewById(R.id.ad_banner);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrameItemClick(String str) {
        this.mCurrentFrameName = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBackgroundItemClick(String str) {
        this.mCurrentBackgroundName = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFilterItemClick(String str) {
        this.mCurrentFilterName = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 273) {
            String stringExtra = intent.getStringExtra(CSOnlineStoreActivity.STICKER_EXTRA);
            CSXlbStickerBarView cSXlbStickerBarView = this.mStickerbar;
            if (cSXlbStickerBarView != null) {
                cSXlbStickerBarView.initData(stringExtra);
            }
        }
    }
}
