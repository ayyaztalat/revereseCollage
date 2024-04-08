package com.photo.editor.square.pic.splash.libfreecollage;

import static com.sky.testproject.Opcodes.IF_ICMPNE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropExecute;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerRes;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.photo.editor.square.pic.splash.libfreecollage.view.FreeView;
import com.photoart.libsticker.sticker.DMStickerBarView;
import com.sky.testproject.R;
import com.winflag.lib.bitmap.AsyncBitmapsCrop;
import com.winflag.libcollage.activity.BaseSdk;
import com.winflag.libcollage.activity.LibCollageConfig;
import com.winflag.libcollage.resource.RatioRes;
import com.winflag.libcollage.resource.TemplateRes;
import com.winflag.libcollage.view.TemplateBottomBarView;
import com.winflag.libcollage.view.TemplateSingleBottomBarView;
import com.winflag.libcollage.widget.ViewSingleTemplateFilter;
import com.winflag.libcollage.widget.ViewTemplateAdjust;
import com.winflag.libcollage.widget.ViewTemplateBlur;
import com.winflag.libcollage.widget.ViewTemplateHorizonList;
import com.winflag.libcollage.widget.radioview.TemplateRatioAdapter;
import com.winflag.libcollage.widget.radioview.ViewTemplateRatio;
import com.winflag.libcollage.widget.radioview.ViewTemplateRatioStyle2;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.instatextview.textview.DMAndroidBug5497Workaround;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sticker.resource.manager.DMCommonStickersManager;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgradient.DMColorGradientDialogView;

/* loaded from: classes2.dex */
public class FreeCollageActivity extends DMFragmentActivityTemplate implements AsyncBitmapsCrop.OnBitmapCropListener, FreeView.onViewFreePhotoEditorBarClickListener {
    public ImageView B_L;
    public ImageView B_R;
    public ImageView T_L;
    public ImageView T_R;
    private boolean adIsShow;
    private LinearLayout ads;
    private ViewTemplateBlur blurBarView;
    AlertDialog colorGradientDialog;
    DMColorGradientDialogView colorGradientView;
    protected int containerWidth;
    private CSSquareUiFilterToolBarView filterBarView;
    protected RelativeLayout freeshareimage;
    private FrameLayout highbarlayout;
    private DMInstaTextView instaTextView;
    private DMInstaTextView3 instaTextView3;
    private boolean isSaveClick;
    public FrameLayout ly_rootcontainer;
    protected RelativeLayout ly_single_sub_function;
    protected RelativeLayout ly_sub_function;
    private int mCurrentBackgroundPos;
    protected int mCurrentFilterPos;
    protected List<Bitmap> mSrcBitmaps;
    private DMStickerBarView mStickerBarView;
    TemplateRes mTemplateRes;
    private View mViewBack;
    private ViewTemplateAdjust mViewTplAdjust;
    private ViewTemplateRatio mViewTplRatio;
    private int myBlurpos;
    protected ImageView random_bt;
    int screenHeight;
    int screenWidth;
    private ViewSingleTemplateFilter singleFilterBarView;
    protected DMWBBorderRes stickerborderRes;
    private ViewTemplateRatioStyle2 templateRatioStyle2;
    protected FreeView tlView;
    private ViewTemplateHorizonList tmplateHorizonListView;
    private TextView txtmessage;
    private boolean updateStickerBarFlag;
    private boolean updatebgBarFlag;
    protected List<Uri> uris;
    protected TemplateBottomBarView viewTemplateBottomBar1;
    private TemplateSingleBottomBarView viewTemplateSingleBottomBar1;
    private CSXlbStickerBarView xlbstickerbar;
    int sys_img_quality = 960;
    private int STICKER_REQUEST_CODE = 291;
    protected boolean isBottomOperationViewShow = false;
    protected Bitmap shareBitmap = null;
    protected int containerHeight = 0;
    float screenscale = 1.0f;
    protected int animationDuration = 300;
    public boolean isAdLoaded = false;
    public String func_layout = "layout_default";
    public String func_adjust = "adjust_default";
    public String func_sticker = "sticker_default";
    public String func_text = "text_default";
    public String func_filter = "filter_default";
    public String func_blur = "blur_default";
    public String func_bg = "bg_default";
    public String func_ratio = "ratio_default";
    Handler handler = new Handler(Looper.getMainLooper());
    protected int mBlurProgress = 20;
    int mShadowProgress = 0;
    int mShadowValue = 10;
    int mSizeScaleProgress = 50;
    float mScaleRatio = 1.0f;
    int mScalePos = 1;

    public void ShowInterstitialAD() {
    }

    public void afterdeleteSticker() {
    }

    public CSListNaitveAdRes getListNativeAd() {
        return null;
    }

    public void gotoShare(Bitmap bitmap) {
    }

    public void loadAdView() {
    }

    public void lockeResClicked(String str) {
    }

    protected void onAddItemClicked() {
    }

    public void onBottomAdjustClick() {
    }

    public void onBottomGlitchClick() {
    }

    public void onBottomMarginClick() {
    }

    protected void onFilterItemClickImpl() {
    }

    public void onLayoutItemClicked() {
    }

    protected void onSingleAdjustClick() {
    }

    protected void onSingleGlitchClick() {
    }

    public void onSpaceItemClicked() {
    }

    public void onTemplateBgItemClicked() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTemplateItemClick(DMWBRes dMWBRes, int i) {
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.view.FreeView.onViewFreePhotoEditorBarClickListener
    public void removeViewFreePhotoEditorBar() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_free_collage);
        BaseSdk.setContext(this);
        getWindow().setFlags(1024, 1024);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("uris");
        this.uris = new ArrayList();
        if (stringArrayListExtra == null) {
            finish();
            return;
        }
        for (int i = 0; i < stringArrayListExtra.size(); i++) {
            this.uris.add(Uri.parse(stringArrayListExtra.get(i)));
        }
        initView();
        this.freeshareimage = (RelativeLayout) findViewById(R.id.image_fl);
        this.sys_img_quality = LibCollageConfig.maxQuality;
        List<Uri> list = this.uris;
        AsyncBitmapsCrop.AsyncBitmapCropExecute(this, list, FreeView.getCollageCropSize(list.size()), this);
        initInstaTextView();
        this.ads = (LinearLayout) findViewById(R.id.ad_banner);
//        AdmobBannerViewManager.getinstance().getAdCloseLiveData().observe(this, new Observer() { // from class: com.photo.editor.square.pic.splash.libfreecollage.-$$Lambda$FreeCollageActivity$Hf5F7zVBRCvfLVBq4J1YlNgy0O8
//            @Override // androidx.lifecycle.Observer
//            public final void onChanged(Object obj) {
//                FreeCollageActivity.this.lambda$onCreate$0$FreeCollageActivity((Boolean) obj);
//            }
//        });
    }

    public /* synthetic */ void lambda$onCreate$0$FreeCollageActivity(Boolean bool) {
        showBannerViewAd();
    }

    private void showBannerViewAd() {
//        if (AdmobConstants.isPositive(AdmobConstants.MAIN_BA_AS) && this.ads.getChildCount() == 0) {
//            boolean showAd = AdmobBannerViewManager.getinstance().showAd(this.ads, "cut_spiral");
//            this.adIsShow = showAd;
//            if (showAd) {
//                return;
//            }
//            AdmobBannerViewManager.getinstance().getAdStateLiveData().observe(this, new Observer<Boolean>() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.1
//                @Override // androidx.lifecycle.Observer
//                public void onChanged(Boolean bool) {
//                    FreeCollageActivity.this.adIsShow = AdmobBannerViewManager.getinstance().showAd(FreeCollageActivity.this.ads, "cut_spiral", false);
//                    if (FreeCollageActivity.this.adIsShow) {
//                        AdmobBannerViewManager.getinstance().getAdStateLiveData().removeObserver(this);
//                    }
//                }
//            });
//        }
    }

    private void initInstaTextView() {
        if (this.instaTextView3 == null) {
            this.instaTextView3 = (DMInstaTextView3) findViewById(R.id.instaTextView3);
            DMAndroidBug5497Workaround.assistActivity(this);
            this.instaTextView3.getShowTextView().setStickerCanvasView(this.tlView.getSfcView_faces());
            this.tlView.addStickerStateCallSpreader(this.instaTextView3.getShowTextView());
        }
        if (this.instaTextView == null) {
            this.instaTextView = (DMInstaTextView) findViewById(R.id.instaTextView);
        }
        DMAndroidBug5497Workaround.assistActivity(this);
        this.instaTextView.getShowTextView().setStickerCanvasView(this.tlView.getSfcView_faces());
        this.tlView.addStickerStateCallSpreader(this.instaTextView.getShowTextView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.tlView.updateGradientBackground();
        if (this.updateStickerBarFlag) {
            resetBottomBar();
            onStickerItemClicked();
            this.updateStickerBarFlag = false;
        }
        if (this.updatebgBarFlag) {
            resetBottomBar();
            onTemplateBgItemClicked();
            this.updatebgBarFlag = false;
        }
        CSXlbStickerBarView cSXlbStickerBarView = this.xlbstickerbar;
        if (cSXlbStickerBarView != null) {
            cSXlbStickerBarView.onResume();
        }
    }

    private void initView() {
        this.ly_rootcontainer = (FrameLayout) findViewById(R.id.ly_rootcontainer);
        this.ly_sub_function = (RelativeLayout) findViewById(R.id.ly_sub_function);
        this.ly_single_sub_function = (RelativeLayout) findViewById(R.id.ly_single_sub_function);
        this.highbarlayout = (FrameLayout) findViewById(R.id.highbarlayout);
        this.T_L = (ImageView) findViewById(R.id.image_left_top);
        this.T_R = (ImageView) findViewById(R.id.image_reit_top);
        this.B_L = (ImageView) findViewById(R.id.image_left_bottem);
        this.B_R = (ImageView) findViewById(R.id.image_reit_bottem);
        this.viewTemplateBottomBar1 = (TemplateBottomBarView) findViewById(R.id.viewTemplateBottomBar1);
        initBottomBar();
        this.viewTemplateBottomBar1.setOnTemplateBottomBarItemClickListener(new TemplateBottomBarView.OnTemplateBottomBarItemClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.2
            @Override // com.winflag.libcollage.view.TemplateBottomBarView.OnTemplateBottomBarItemClickListener
            public void OnTemplateBottomBarItemClick(TemplateBottomBarView.TemplateBottomItem templateBottomItem) {
                if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.ADD) {
                    FreeCollageActivity.this.onAddItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Layout) {
                    FreeCollageActivity.this.onLayoutItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Space) {
                    FreeCollageActivity.this.onSpaceItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Background) {
                    FreeCollageActivity.this.onTemplateBgItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Text) {
                    FreeCollageActivity.this.onTextItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.DMSticker) {
                    FreeCollageActivity.this.onStickerItemClicked2();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Ratio) {
                    FreeCollageActivity.this.onBottomRatioClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Filter) {
                    FreeCollageActivity.this.onBottomFilterClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Blur) {
                    FreeCollageActivity.this.onBottomBlurClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.GLITCH) {
                    FreeCollageActivity.this.onBottomGlitchClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.ADJUST) {
                    FreeCollageActivity.this.onBottomAdjustClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Margin) {
                    FreeCollageActivity.this.onBottomMarginClick();
                }
            }
        });
        TemplateSingleBottomBarView templateSingleBottomBarView = (TemplateSingleBottomBarView) findViewById(R.id.viewTemplateSingleBottomBar1);
        this.viewTemplateSingleBottomBar1 = templateSingleBottomBarView;
        templateSingleBottomBarView.setOnTemplateSingleBottomBarItemClickListener(new TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.3
            @Override // com.winflag.libcollage.view.TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener
            public void OnTemplateSingleBottomBarItemClick(TemplateSingleBottomBarView.TemplateSingleBottomItem templateSingleBottomItem) {
                if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Rotate) {
                    FreeCollageActivity.this.tlView.doRotation(90.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Horizontal) {
                    FreeCollageActivity.this.tlView.doReversal(180.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Vertical) {
                    FreeCollageActivity.this.tlView.doReversal(0.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Filter) {
                    FreeCollageActivity.this.onFilterItemClickImpl();
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_GLITCH) {
                    FreeCollageActivity.this.onSingleGlitchClick();
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_ADJUST) {
                    FreeCollageActivity.this.onSingleAdjustClick();
                }
            }

            @Override // com.winflag.libcollage.view.TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener
            public void OnTemplateSingleBottomBarClose() {
                FreeCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                FreeCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                FreeCollageActivity.this.tlView.noStickerSelected();
            }
        });
        findViewById(R.id.ly_done).setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FreeCollageActivity.this.isSaveClick) {
                    return;
                }
                FreeCollageActivity.this.isSaveClick = true;
                FreeCollageActivity.this.onTopShareClick();
                FreeCollageActivity.this.findViewById(R.id.ly_done).postDelayed(new Runnable() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FreeCollageActivity.this.isSaveClick = false;
                    }
                }, 1000L);
            }
        });
        View findViewById = findViewById(R.id.ly_back);
        this.mViewBack = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FreeCollageActivity.this.showQuitDialog();
            }
        });
        FreeView freeView = (FreeView) findViewById(R.id.templateView);
        this.tlView = freeView;
        freeView.setViewFreePhotoEditorBarOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.btnRandom);
        this.random_bt = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int nextInt;
                FreeCollageActivity.this.resetBottomBar();
                FreeCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                Random random = new Random();
                int stickerFreeCount = FreeCollageActivity.this.tlView.getStickerFreeCount();
                int composeIndex = FreeCollageActivity.this.tlView.getComposeIndex();
                if (stickerFreeCount <= 1 ? !((nextInt = random.nextInt(2)) != composeIndex || (nextInt = nextInt + 1) <= 2) : !((nextInt = random.nextInt(5)) != composeIndex || (nextInt = nextInt + 1) <= 5)) {
                    nextInt -= 2;
                }
                FreeCollageActivity.this.tlView.setComposeIndex(nextInt);
                FreeCollageActivity.this.tlView.ClearOnlyFreePuzzzle();
                FreeCollageActivity.this.tlView.changeToFreeCollage(FreeCollageActivity.this.containerWidth, FreeCollageActivity.this.containerHeight);
                FreeCollageActivity.this.tlView.invalidate();
            }
        });
        this.txtmessage = (TextView) findViewById(R.id.txtmessage);
        List<Uri> list = this.uris;
        if (list != null && list.size() == 1) {
            this.txtmessage.setVisibility(View.INVISIBLE);
        }
        this.screenHeight = DMScreenInfoUtil.dip2px(this, DMScreenInfoUtil.screenHeightDp(this) - (LibCollageConfig.isShowAd ? 210 : DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA));
        int screenWidth = DMScreenInfoUtil.screenWidth(this);
        this.screenWidth = screenWidth;
        if (this.screenHeight > ((int) (screenWidth + 0.5f))) {
            ViewGroup.LayoutParams layoutParams = this.tlView.getLayoutParams();
            layoutParams.width = this.screenWidth;
            layoutParams.height = (int) (this.screenWidth + 0.5f);
            this.screenscale = 1.0f;
            this.containerWidth = layoutParams.width;
            this.containerHeight = layoutParams.height;
        } else {
            ViewGroup.LayoutParams layoutParams2 = this.tlView.getLayoutParams();
            layoutParams2.width = (int) (this.screenHeight + 0.5f);
            layoutParams2.height = this.screenHeight;
            this.screenscale = 1.0f;
            this.containerWidth = layoutParams2.width;
            this.containerHeight = layoutParams2.height;
        }
        findViewById(R.id.image_fl).setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FreeCollageActivity.this.resetBottomBar();
                FreeCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                FreeCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                FreeCollageActivity.this.tlView.noStickerSelected();
            }
        });
    }

    protected void initBottomBar() {
        if (TemplateBottomBarView.TemplateBottomItem.Layout != null) {
            this.viewTemplateBottomBar1.setBottomItemVisible(TemplateBottomBarView.TemplateBottomItem.Layout, false);
            this.viewTemplateBottomBar1.setBottomItemVisible(TemplateBottomBarView.TemplateBottomItem.ADD, true);
            return;
        }
        try {
            new TemplateBottomBarView(this);
            initBottomBar();
        } catch (Exception ignored) {
        }
    }

    public void onBottomBlurClick() {
        if (this.blurBarView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Blur, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Blur, true);
        this.isBottomOperationViewShow = true;
        ViewTemplateBlur viewTemplateBlur = new ViewTemplateBlur(this, this.mBlurProgress, this.myBlurpos);
        this.blurBarView = viewTemplateBlur;
        viewTemplateBlur.setCurrentBitmap(this.mSrcBitmaps);
        this.blurBarView.setOnEventListener(new ViewTemplateBlur.OnTempBlurViewListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.8
            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void chooseBg() {
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void blurChange(int i, int i2) {
                FreeCollageActivity.this.mBlurProgress = i;
                FreeCollageActivity.this.myBlurpos = i2;
                FreeCollageActivity.this.tlView.setBlurBackground(FreeCollageActivity.this.mSrcBitmaps, FreeCollageActivity.this.mBlurProgress / 5, i2);
                FreeCollageActivity freeCollageActivity = FreeCollageActivity.this;
                freeCollageActivity.func_blur = "blur_" + i;
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void blurResize(int i) {
                FreeCollageActivity.this.mBlurProgress = i;
                FreeCollageActivity.this.tlView.resetBackgroud();
                FreeCollageActivity.this.func_blur = "no_blur";
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void resetBg(Bitmap bitmap, int i) {
                FreeCollageActivity.this.myBlurpos = i;
                FreeCollageActivity.this.tlView.setBlurBackground(FreeCollageActivity.this.mSrcBitmaps, FreeCollageActivity.this.mBlurProgress / 5, i);
            }
        });
        this.tlView.setBlurBackground(this.mSrcBitmaps, this.mBlurProgress / 5, this.myBlurpos);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.blurBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 160.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.blurBarView.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.blurBarView);
        ChangeViewHeight(IF_ICMPNE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void ChangeViewHeight(int i) {
        View findViewById = findViewById(R.id.image_fl);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.txtmessage.getLayoutParams();
        if (LibCollageConfig.isShowAd) {
            if (layoutParams != null) {
                layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 100);
                layoutParams2.topMargin = DMScreenInfoUtil.dip2px(this, 105.0f);
            }
        } else if (layoutParams != null) {
            layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 100);
        }
        findViewById.setLayoutParams(layoutParams);
    }

    protected void withoutAdView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById(R.id.image_fl).getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = DMScreenInfoUtil.dip2px(this, 50.0f);
        }
        findViewById(R.id.ad_banner).setVisibility(View.INVISIBLE);
    }

    public void resetBottomBar() {
        this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
        this.ly_sub_function.clearDisappearingChildren();
        this.ly_sub_function.removeAllViews();
        this.highbarlayout.removeAllViews();
        if (this.mViewTplAdjust != null) {
            this.mViewTplAdjust = null;
        }
        if (this.mViewTplRatio != null) {
            this.mViewTplRatio = null;
        }
        ViewTemplateHorizonList viewTemplateHorizonList = this.tmplateHorizonListView;
        if (viewTemplateHorizonList != null) {
            try {
                viewTemplateHorizonList.dispose();
                this.tmplateHorizonListView = null;
            } catch (Throwable ignored) {
            }
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
        if (this.blurBarView != null) {
            this.blurBarView = null;
        }
        if (this.templateRatioStyle2 != null) {
            this.templateRatioStyle2 = null;
        }
        ViewSingleTemplateFilter viewSingleTemplateFilter = this.singleFilterBarView;
        if (viewSingleTemplateFilter != null) {
            viewSingleTemplateFilter.dispose();
            this.singleFilterBarView = null;
        }
        this.viewTemplateBottomBar1.resetSelectorStat();
        this.isBottomOperationViewShow = false;
        ChangeViewHeight(0);
    }

    public void onStickerItemClicked2() {
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.DMSticker, true);
        this.isBottomOperationViewShow = true;
        CSXlbStickerBarView cSXlbStickerBarView = new CSXlbStickerBarView(this);
        this.xlbstickerbar = cSXlbStickerBarView;
        cSXlbStickerBarView.setRequestAppName("com.winflag.instalens");
        this.xlbstickerbar.setItemClickListener(new CSXlbStickerBarView.onStickerItemClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.9
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onStickerItemClicked(DMWBRes dMWBRes, int i) {
                Bitmap decodeFile;
                CSStickerRes cSStickerRes = (CSStickerRes) dMWBRes;
                if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                    decodeFile = DMBitmapDbUtil.getImageFromAssetsFile(FreeCollageActivity.this, cSStickerRes.getImageFileName());
                } else {
                    decodeFile = cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE ? BitmapFactory.decodeFile(cSStickerRes.getImageFileName()) : null;
                }
                if (decodeFile != null) {
                    if (FreeCollageActivity.this.tlView.getStickerCount() >= 8) {
                        Toast.makeText(FreeCollageActivity.this, FreeCollageActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_LONG).show();
                        return;
                    }
                    FreeCollageActivity.this.tlView.addSticker(decodeFile);
                } else {
                    Toast.makeText(FreeCollageActivity.this, "Resource Load faile !", Toast.LENGTH_LONG).show();
                }
                FreeCollageActivity freeCollageActivity = FreeCollageActivity.this;
                freeCollageActivity.func_sticker = FreeCollageActivity.this.func_sticker + "_" + cSStickerRes.getGroup_name() + dMWBRes.getName();
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onClose() {
                FreeCollageActivity.this.resetBottomBar();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.xlbstickerbar.getLayoutParams();
        int screenWidth = (int) ((DMScreenInfoUtil.screenWidth(this) * 11.0f) / 18.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, screenWidth);
        }
        this.xlbstickerbar.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.xlbstickerbar, new FrameLayout.LayoutParams(-2, -2, 80));
    }

    public void onStickerItemClicked() {
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.DMSticker, true);
        if (this.mStickerBarView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.DMSticker, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.DMSticker, true);
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
        this.mStickerBarView.setOnStickerChooseListener(new DMStickerBarView.OnStickerChooseListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.10
            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerChoose(DMWBRes dMWBRes) {
                ((DMWBImageRes) dMWBRes).getImageBitmap(FreeCollageActivity.this, new DMWBImageRes.OnResImageLoadListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.10.1
                    @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFinish(Bitmap bitmap) {
                        if (FreeCollageActivity.this.tlView.getStickerCount() >= 8) {
                            Toast.makeText(FreeCollageActivity.this, FreeCollageActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_LONG).show();
                            return;
                        }
                        FreeCollageActivity.this.tlView.addSticker(bitmap);
                        FreeCollageActivity.this.resetBottomBar();
                    }

                    @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFaile() {
                        Toast.makeText(FreeCollageActivity.this, "Resource Load faile !", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerClose() {
                FreeCollageActivity.this.resetBottomBar();
            }
        });
    }

    public void onTextItemClicked() {
        resetBottomBar();
        DMInstaTextView3 dMInstaTextView3 = this.instaTextView3;
        if (dMInstaTextView3 != null) {
            dMInstaTextView3.addText();
        }
        this.func_text += "_addtext";
    }

    public void onBottomFilterClick() {
        if (this.filterBarView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Filter, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Filter, true);
        CSSquareUiFilterToolBarView cSSquareUiFilterToolBarView = new CSSquareUiFilterToolBarView(this, this.mCurrentFilterPos, getListNativeAd());
        this.filterBarView = cSSquareUiFilterToolBarView;
        cSSquareUiFilterToolBarView.setOnSquareUiFilterToolBarViewListener(new CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.11
            @Override // com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener
            public void onFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z) {
                if (z && FreeCollageActivity.this.isAdLoaded) {
                    FreeCollageActivity.this.lockeResClicked(gPUFilterRes.getName());
                    return;
                }
                FreeCollageActivity.this.mCurrentFilterPos = i;
                FreeCollageActivity.this.tlView.setFilterAll(gPUFilterRes);
                FreeCollageActivity freeCollageActivity = FreeCollageActivity.this;
                freeCollageActivity.func_filter = "filter_" + gPUFilterRes.getShowText();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.filterBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 100.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.filterBarView.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.filterBarView);
        ChangeViewHeight(100);
    }

    public void onBottomRatioClick() {
        if (this.templateRatioStyle2 != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Ratio, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Ratio, true);
        this.isBottomOperationViewShow = true;
        ViewTemplateRatioStyle2 viewTemplateRatioStyle2 = new ViewTemplateRatioStyle2(this, this.mScalePos);
        this.templateRatioStyle2 = viewTemplateRatioStyle2;
        viewTemplateRatioStyle2.getAdapter().setOnRatioItemClikListener(new TemplateRatioAdapter.onRadioItemClikListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.12
            @Override // com.winflag.libcollage.widget.radioview.TemplateRatioAdapter.onRadioItemClikListener
            public void onClick(int i, RatioRes ratioRes, boolean z) {
                FreeCollageActivity.this.mScalePos = i;
                FreeCollageActivity.this.mScaleRatio = z ? 1.0f / ratioRes.getRadio() : ratioRes.getRadio();
                FreeCollageActivity freeCollageActivity = FreeCollageActivity.this;
                freeCollageActivity.changeScale(freeCollageActivity.mScaleRatio, Boolean.valueOf(z));
                FreeCollageActivity freeCollageActivity2 = FreeCollageActivity.this;
                freeCollageActivity2.func_ratio = "ratio_" + FreeCollageActivity.this.mScaleRatio;
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.templateRatioStyle2.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 100.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.templateRatioStyle2.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.templateRatioStyle2);
    }

    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate
    public void showProcessDialog() {
        dismissProcessDialog();
        super.showProcessDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        try {
            dismissProcessDialog();
        } catch (Exception unused) {
        }
        FreeView freeView = this.tlView;
        if (freeView != null) {
            freeView.clearStickerStateCallSpreader();
            this.tlView.restCollageViewAndClearBitmap();
            this.tlView.dispose();
        }
        Bitmap bitmap = this.shareBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        if (this.mSrcBitmaps != null) {
            for (int i = 0; i < this.mSrcBitmaps.size(); i++) {
                if (this.mSrcBitmaps.get(i) != null && !this.mSrcBitmaps.get(i).isRecycled()) {
                    this.mSrcBitmaps.get(i).recycle();
                }
            }
            this.mSrcBitmaps.clear();
            this.mSrcBitmaps = null;
        }
        resetBottomBar();
        super.onDestroy();
    }

    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.viewTemplateSingleBottomBar1.getVisibility() == View.VISIBLE) {
                this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                return false;
            }
            DMInstaTextView dMInstaTextView = this.instaTextView;
            if (dMInstaTextView != null && dMInstaTextView.backKey()) {
                return false;
            }
            if (this.isBottomOperationViewShow) {
                resetBottomBar();
                return true;
            }
            showQuitDialog();
        }
        return false;
    }

    public void showQuitDialog() {
        onBackImpl();
    }

    public void onBackImpl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.quit_string);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (Build.VERSION.SDK_INT > 12) {
                        FreeCollageActivity.this.ShowInterstitialAD();
                    } else {
                        FreeCollageActivity.this.finish();
                    }
                } catch (Exception unused) {
                    FreeCollageActivity.this.finish();
                } catch (Throwable unused2) {
                    FreeCollageActivity.this.finish();
                }
                dialogInterface.dismiss();
                dialogInterface.dismiss();
            }
        });
        // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.14
// android.content.DialogInterface.OnClickListener
        builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public void onBitmapCropStart() {
        showProcessDialog();
    }

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public int onBitmapRequireItemSize(int i) {
        return FreeView.getCollageCropSize(i + 1);
    }

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public void onBitmapCropSuccess(List<Bitmap> list) {
        this.mSrcBitmaps = list;
        if (list == null || list.size() < 1) {
            // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.15
// java.lang.Runnable
            this.handler.post(() -> Toast.makeText(FreeCollageActivity.this, "Image is not exist!", Toast.LENGTH_LONG).show());
        } else {
            // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.16
// java.lang.Runnable
            this.handler.post(() -> {
                FreeCollageActivity.this.tlView.imagecount = FreeCollageActivity.this.mSrcBitmaps.size();
                FreeCollageActivity.this.tlView.ClearOnlyFreePuzzzle();
                FreeCollageActivity.this.tlView.setComposeIndex(0);
                FreeCollageActivity.this.tlView.setBitmapList(FreeCollageActivity.this.mSrcBitmaps, FreeCollageActivity.this.uris);
                FreeCollageActivity.this.tlView.setStickerBorderRes(FreeCollageActivity.this.stickerborderRes);
                FreeCollageActivity.this.tlView.changeToFreeCollage(FreeCollageActivity.this.containerWidth, FreeCollageActivity.this.containerHeight);
                FreeCollageActivity.this.dismissProcessDialog();
            });
        }
    }

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public void onBitmapCriopFaile() {
        dismissProcessDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTopShareClick() {
        resetBottomBar();
        Bitmap bitmap = this.shareBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        this.tlView.getOutputImage(CSCropAsyncBitmapCropExecute.getImageQuality(this), new FreeView.onOutputImageListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.17
            @Override // com.photo.editor.square.pic.splash.libfreecollage.view.FreeView.onOutputImageListener
            public void onOutputImageFinish(Bitmap bitmap2) {
                FreeCollageActivity.this.gotoShare(bitmap2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == this.STICKER_REQUEST_CODE) {
            onStickerPickReturn(intent);
        }
        this.updatebgBarFlag = false;
        this.updateStickerBarFlag = false;
        if (i2 == 256) {
            this.updateStickerBarFlag = true;
        }
        if (i2 == 257) {
            this.updatebgBarFlag = true;
        }
    }

    private void onStickerPickReturn(Intent intent) {
        DMStickerTypeOperation.StickerType stickerType;
        resetBottomBar();
        String stringExtra = intent.getStringExtra("stickerResName");
        String stringExtra2 = intent.getStringExtra("stickerType");
        if (stringExtra == null || stringExtra.length() <= 0 || stringExtra2 == null || stringExtra2.length() <= 0) {
            return;
        }
        try {
            int intValue = Integer.valueOf(stringExtra2).intValue();
            if (intValue == 0) {
                return;
            }
            DMStickerTypeOperation.StickerType stickerType2 = DMStickerTypeOperation.StickerType.EMOJI;
            if (intValue == 1) {
                stickerType = DMStickerTypeOperation.StickerType.EMOJI;
            } else if (intValue == 2) {
                stickerType = DMStickerTypeOperation.StickerType.HEART;
            } else if (intValue == 3) {
                stickerType = DMStickerTypeOperation.StickerType.CUTE;
            } else {
                Toast.makeText(this, "DMSticker Add faile !", Toast.LENGTH_LONG).show();
                return;
            }
            DMCommonStickersManager commonStickerManagerByStickType = new DMStickerTypeOperation(this).getCommonStickerManagerByStickType(stickerType);
            if (commonStickerManagerByStickType == null) {
                return;
            }
            commonStickerManagerByStickType.setContext(this);
            commonStickerManagerByStickType.init();
            commonStickerManagerByStickType.getRes(stringExtra).getImageBitmap(this, new DMWBImageRes.OnResImageLoadListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.FreeCollageActivity.18
                @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                public void onImageLoadFinish(Bitmap bitmap) {
                    FreeCollageActivity.this.tlView.addSticker(bitmap);
                }

                @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                public void onImageLoadFaile() {
                    Toast.makeText(FreeCollageActivity.this, "Resource Load faile !", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception unused) {
            Toast.makeText(this, "DMSticker Add faile !", Toast.LENGTH_LONG).show();
        }
    }

    public void changeScale(float f, Boolean bool) {
        this.screenscale = f;
        if (this.screenHeight > ((int) ((this.screenWidth * f) + 0.5f))) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.tlView.getLayoutParams();
            layoutParams.width = this.screenWidth;
            layoutParams.height = (int) ((this.screenWidth * this.screenscale) + 0.5f);
            this.containerWidth = layoutParams.width;
            this.containerHeight = layoutParams.height;
        } else {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.tlView.getLayoutParams();
            layoutParams2.width = (int) ((this.screenHeight / this.screenscale) + 0.5f);
            layoutParams2.height = this.screenHeight;
            this.containerWidth = layoutParams2.width;
            this.containerHeight = layoutParams2.height;
        }
        this.tlView.ClearOnlyFreePuzzzle();
        this.tlView.changeToFreeCollage(this.containerWidth, this.containerHeight);
        this.tlView.invalidate();
        ViewGroup viewGroup = (ViewGroup) this.tlView.getParent();
        viewGroup.getLayoutParams().width = this.containerWidth;
        viewGroup.getLayoutParams().height = this.containerHeight;
    }

    public ViewGroup getBannerAdContainer() {
        return (ViewGroup) findViewById(R.id.ad_banner);
    }

    public void locklistNotifyChanged() {
        CSSquareUiFilterToolBarView cSSquareUiFilterToolBarView = this.filterBarView;
        if (cSSquareUiFilterToolBarView != null) {
            cSSquareUiFilterToolBarView.getRecyclerViewAdapter().notifyDataSetChanged();
        }
        ViewSingleTemplateFilter viewSingleTemplateFilter = this.singleFilterBarView;
        if (viewSingleTemplateFilter != null) {
            viewSingleTemplateFilter.getRecyclerViewAdapter().notifyDataSetChanged();
        }
        ViewTemplateHorizonList viewTemplateHorizonList = this.tmplateHorizonListView;
        if (viewTemplateHorizonList != null) {
            viewTemplateHorizonList.getRecyclerViewAdapter().notifyDataSetChanged();
        }
    }

    @Override // com.photo.editor.square.pic.splash.libfreecollage.view.FreeView.onViewFreePhotoEditorBarClickListener
    public void addViewFreePhotoEditorBar(Bitmap bitmap, Uri uri) {
        resetBottomBar();
        this.viewTemplateSingleBottomBar1.setVisibility(View.VISIBLE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
    }
}
