package com.winflag.libcollage.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropExecute;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerRes;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.google.firebase.messaging.ServiceStarter;
import com.photoart.libsticker.sticker.DMStickerBarView;
import com.sky.testproject.R;
import com.winflag.lib.bitmap.AsyncBitmapsCrop;
import com.winflag.libcollage.manager.TemplateManager;
import com.winflag.libcollage.resource.RatioRes;
import com.winflag.libcollage.resource.TemplateRes;
import com.winflag.libcollage.view.TemplateBottomBarView;
import com.winflag.libcollage.view.TemplateSingleBottomBarView;
import com.winflag.libcollage.view.TemplateTopBarView;
import com.winflag.libcollage.view.TemplateView;
import com.winflag.libcollage.widget.ViewSingleTemplateFilter;
import com.winflag.libcollage.widget.ViewTemplateAdjust;
import com.winflag.libcollage.widget.ViewTemplateBlur;
import com.winflag.libcollage.widget.ViewTemplateHorizonList;
import com.winflag.libcollage.widget.ViewTemplateMangerList;
import com.winflag.libcollage.widget.radioview.TemplateRatioAdapter;
import com.winflag.libcollage.widget.radioview.ViewTemplateRatio;
import com.winflag.libcollage.widget.radioview.ViewTemplateRatioStyle2;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.instatextview.textview.DMAndroidBug5497Workaround;
import com.picspool.instatextview.textview.DMInstaTextView;
import  com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.activity.DMProcessDialogFragment;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sticker.resource.manager.DMCommonStickersManager;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.colorgradient.DMColorGradientDialogView;

/* loaded from: classes.dex */
public class TemplateCollageActivity extends DMFragmentActivityTemplate implements AsyncBitmapsCrop.OnBitmapCropListener {
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
    private FrameLayout highbarlayout;
    public RelativeLayout image;
    private DMInstaTextView instaTextView;
    private DMInstaTextView3 instaTextView3;
    private boolean isSaveClick;
    public FrameLayout ly_rootcontainer;
    protected RelativeLayout ly_single_sub_function;
    protected RelativeLayout ly_sub_function;
    private int mCurrentBackgroundPos;
    protected int mCurrentFilterPos;
    private TemplateManager mManager;
    private List<Bitmap> mSrcBitmaps;
    private DMStickerBarView mStickerBarView;
    TemplateRes mTemplateRes;
    private View mViewBack;
    private ViewTemplateAdjust mViewTplAdjust;
    private ViewTemplateRatio mViewTplRatio;
    private TemplateManager mtlManager;
    private int myBlurpos;
    int screenHeight;
    int screenWidth;
    public RelativeLayout share;
    private ViewSingleTemplateFilter singleFilterBarView;
    private ViewTemplateRatioStyle2 templateRatioStyle2;
    public TemplateView tlView;
    private ViewTemplateHorizonList tmplateHorizonListView;
    private TextView txtmessage;
    private boolean updateStickerBarFlag;
    private boolean updatebgBarFlag;
    public List<Uri> uris;
    protected TemplateBottomBarView viewTemplateBottomBar1;
    private ViewTemplateMangerList viewTemplateMangerList;
    private TemplateSingleBottomBarView viewTemplateSingleBottomBar1;
    private TemplateTopBarView viewTemplateTopBar;
    private CSXlbStickerBarView xlbstickerbar;
    int sys_img_quality = 960;
    private int STICKER_REQUEST_CODE = 291;
    protected boolean isBottomOperationViewShow = false;
    public Bitmap shareBitmap = null;
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
    Handler handler = new Handler();
    protected int mBlurProgress = 20;
    int mShadowProgress = 0;
    int mShadowValue = 10;
    int mSizeScaleProgress = 50;
    float mScaleRatio = 1.0f;
    int mScalePos = 0;

    public void ShowInterstitialAD() {
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

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public /* synthetic */ int onBitmapRequireItemSize(int i) {
        return CC.$default$onBitmapRequireItemSize(this, i);
    }

    public void onBottomAdjustClick() {
    }

    public void onBottomGlitchClick() {
    }

    public void onMangerItemClicked() {
    }

    public void onSingleAdjustClick() {
    }

    public void onSingleGlitchClick() {
    }

    public void onTemplateBgItemClicked() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTemplateItemClick(DMWBRes dMWBRes, int i) {
    }


    @Override // org.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        System.gc();
        super.onCreate(bundle);
        setContentView(R.layout.activity_template);
        this.ads = (LinearLayout) findViewById(R.id.ad_banner);
        if (this.image == null) {
            this.image = (RelativeLayout) findViewById(R.id.image_fl);
        }
        this.T_L = (ImageView) findViewById(R.id.image_left_top);
        this.T_R = (ImageView) findViewById(R.id.image_reit_top);
        this.B_L = (ImageView) findViewById(R.id.image_left_bottem);
        this.B_R = (ImageView) findViewById(R.id.image_reit_bottem);
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
        int i2 = LibCollageConfig.maxQuality;
        this.sys_img_quality = i2;
        getCollageCropSize(i2, this.uris.size());
        List<Uri> list = this.uris;
        AsyncBitmapsCrop.AsyncBitmapCropExecute(this, list, CSCropAsyncBitmapCropExecute.getCollageCropSize(this, list.size()), this);
        initInstaTextView();
    }

    public  void lambda$onCreate$0$TemplateCollageActivity(Boolean bool) {
//        showBannerViewAd();
    }

    private void initInstaTextView() {
        if (this.instaTextView3 == null) {
            this.instaTextView3 = (DMInstaTextView3) findViewById(R.id.instaTextView3);
            DMAndroidBug5497Workaround.assistActivity(this);
            this.instaTextView3.getShowTextView().setStickerCanvasView(this.tlView.getSfcView_faces());
            this.tlView.addStickerStateCallSpreader(this.instaTextView3.getShowTextView());
        }
        this.instaTextView = (DMInstaTextView) findViewById(R.id.instaTextView);
        DMAndroidBug5497Workaround.assistActivity(this);
        this.instaTextView.getShowTextView().setStickerCanvasView(this.tlView.getSfcView_faces());
        this.tlView.addStickerStateCallSpreader(this.instaTextView.getShowTextView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        TemplateView templateView;
        super.onResume();
        if (this.mSrcBitmaps != null && this.mtlManager != null && (templateView = this.tlView) != null) {
            templateView.setRotationDegree(templateView.getRotaitonDegree());
        }
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

    public int getCollageCropSize(int i, int i2) {
        boolean z = !LibCollageConfig.isLowMemery;
        int i3 = 300;
        switch (i2) {
            case 1:
                return z ? 960 : 800;
            case 2:
                return z ? 800 : 600;
            case 3:
                if (z) {
                    return 700;
                }
                return ServiceStarter.ERROR_UNKNOWN;
            case 4:
                return z ? 600 : 400;
            case 5:
                return z ? 520 : 340;
            case 6:
                if (z) {
                    i3 = 460;
                    break;
                }
                break;
            case 7:
                if (z) {
                    i3 = 450;
                    break;
                }
                break;
            case 8:
                return z ? 430 : 280;
            case 9:
                return z ? 400 : 260;
            default:
                return 612;
        }
        return i3;
    }

    private void initView() {
        this.ly_rootcontainer = (FrameLayout) findViewById(R.id.ly_rootcontainer);
        this.ly_sub_function = (RelativeLayout) findViewById(R.id.ly_sub_function);
        this.ly_single_sub_function = (RelativeLayout) findViewById(R.id.ly_single_sub_function);
        this.highbarlayout = (FrameLayout) findViewById(R.id.highbarlayout);
        TemplateBottomBarView templateBottomBarView = (TemplateBottomBarView) findViewById(R.id.viewTemplateBottomBar1);
        this.viewTemplateBottomBar1 = templateBottomBarView;
        templateBottomBarView.setOnTemplateBottomBarItemClickListener(new TemplateBottomBarView.OnTemplateBottomBarItemClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.2
            @Override // com.winflag.libcollage.view.TemplateBottomBarView.OnTemplateBottomBarItemClickListener
            public void OnTemplateBottomBarItemClick(TemplateBottomBarView.TemplateBottomItem templateBottomItem) {
                if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Layout) {
                    TemplateCollageActivity.this.onLayoutItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Space) {
                    TemplateCollageActivity.this.onSpaceItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Margin) {
                    if (TemplateCollageActivity.this.image == null) {
                        TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                        templateCollageActivity.image = (RelativeLayout) templateCollageActivity.findViewById(R.id.image_fl);
                    }
                    TemplateCollageActivity.this.onMangerItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Background) {
                    TemplateCollageActivity.this.onTemplateBgItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Text) {
                    TemplateCollageActivity.this.onTextItemClicked();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.DMSticker) {
                    TemplateCollageActivity.this.onStickerItemClicked2();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Ratio) {
                    TemplateCollageActivity.this.onBottomRatioClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Filter) {
                    TemplateCollageActivity.this.onBottomFilterClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.Blur) {
                    TemplateCollageActivity.this.onBottomBlurClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.GLITCH) {
                    TemplateCollageActivity.this.onBottomGlitchClick();
                } else if (templateBottomItem == TemplateBottomBarView.TemplateBottomItem.ADJUST) {
                    TemplateCollageActivity.this.onBottomAdjustClick();
                }
            }
        });
        TemplateSingleBottomBarView templateSingleBottomBarView = (TemplateSingleBottomBarView) findViewById(R.id.viewTemplateSingleBottomBar1);
        this.viewTemplateSingleBottomBar1 = templateSingleBottomBarView;
        templateSingleBottomBarView.setOnTemplateSingleBottomBarItemClickListener(new TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.3
            @Override // com.winflag.libcollage.view.TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener
            public void OnTemplateSingleBottomBarItemClick(TemplateSingleBottomBarView.TemplateSingleBottomItem templateSingleBottomItem) {
                if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Rotate) {
                    TemplateCollageActivity.this.tlView.doRotation(90.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Horizontal) {
                    TemplateCollageActivity.this.tlView.doReversal(180.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Vertical) {
                    TemplateCollageActivity.this.tlView.doReversal(0.0f);
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_Filter) {
                    TemplateCollageActivity.this.onFilterItemClickImpl();
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_GLITCH) {
                    TemplateCollageActivity.this.onSingleGlitchClick();
                } else if (templateSingleBottomItem == TemplateSingleBottomBarView.TemplateSingleBottomItem.Single_ADJUST) {
                    TemplateCollageActivity.this.onSingleAdjustClick();
                }
            }

            @Override // com.winflag.libcollage.view.TemplateSingleBottomBarView.OnTemplateSingleBottomBarItemClickListener
            public void OnTemplateSingleBottomBarClose() {
                TemplateCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                TemplateCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                TemplateCollageActivity.this.tlView.resetNoSelected();
            }
        });
        findViewById(R.id.ly_done).setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateCollageActivity.this.isSaveClick) {
                    return;
                }
                TemplateCollageActivity.this.isSaveClick = true;
                TemplateCollageActivity.this.onTopShareClick();
                TemplateCollageActivity.this.findViewById(R.id.ly_done).postDelayed(new Runnable() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TemplateCollageActivity.this.isSaveClick = false;
                    }
                }, 1000L);
            }
        });
        View findViewById = findViewById(R.id.ly_back);
        this.mViewBack = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TemplateCollageActivity.this.showQuitDialog();
            }
        });
        this.tlView = (TemplateView) findViewById(R.id.templateView);
        this.txtmessage = (TextView) findViewById(R.id.txtmessage);
        List<Uri> list = this.uris;
        if (list != null && list.size() == 1) {
            this.txtmessage.setVisibility(View.INVISIBLE);
        }
        this.tlView.mItemlistener = new TemplateView.OnItemClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.6
            @Override // com.winflag.libcollage.view.TemplateView.OnItemClickListener
            public void ItemChange(View view, View view2) {
            }

            @Override // com.winflag.libcollage.view.TemplateView.OnItemClickListener
            public void ItemSizeChange() {
            }

            @Override // com.winflag.libcollage.view.TemplateView.OnItemClickListener
            public void ItemClick(View view, String str) {
                TemplateCollageActivity.this.txtmessage.setVisibility(View.INVISIBLE);
            }
        };
        this.tlView.mItemLonglistener = new TemplateView.OnItemLongClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.7
            @Override // com.winflag.libcollage.view.TemplateView.OnItemLongClickListener
            public void ItemLongMove() {
            }

            @Override // com.winflag.libcollage.view.TemplateView.OnItemLongClickListener
            public void ItemLongClick(View view, int i, String str) {
                TemplateCollageActivity.this.txtmessage.setText(TemplateCollageActivity.this.getString(R.string.exchangeimage));
                if (TemplateCollageActivity.this.uris == null || TemplateCollageActivity.this.uris.size() == 1) {
                    return;
                }
                TemplateCollageActivity.this.txtmessage.setVisibility(View.VISIBLE);
            }
        };
        this.tlView.mItemTapuplistener = new TemplateView.OnItemTapupClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.8
            @Override // com.winflag.libcollage.view.TemplateView.OnItemTapupClickListener
            public void ItemTapupClick(int i) {
                TemplateCollageActivity.this.resetBottomBar();
                if (i == 1) {
                    TemplateCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.INVISIBLE);
                    TemplateCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.VISIBLE);
                    return;
                }
                TemplateCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                TemplateCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
            }
        };
        boolean z = LibCollageConfig.isShowAd;
        this.screenWidth = DMScreenInfoUtil.screenWidth(this);
        int dip2px = DMScreenInfoUtil.dip2px(this, (DMScreenInfoUtil.screenHeightDp(this) / 3.0f) * 1.6f);
        this.screenHeight = dip2px;
        if (dip2px > ((int) (this.screenWidth + 0.5f))) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.tlView.getLayoutParams();
            layoutParams.width = this.screenWidth;
            layoutParams.height = (int) (this.screenWidth + 0.5f);
            this.screenscale = 1.0f;
            this.containerWidth = layoutParams.width;
            this.containerHeight = layoutParams.height;
        } else {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.tlView.getLayoutParams();
            layoutParams2.width = (int) (this.screenHeight + 0.5f);
            layoutParams2.height = this.screenHeight;
            this.screenscale = 1.0f;
            this.containerWidth = layoutParams2.width;
            this.containerHeight = layoutParams2.height;
        }
        findViewById(R.id.image_fl).setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TemplateCollageActivity.this.resetBottomBar();
                TemplateCollageActivity.this.viewTemplateBottomBar1.setVisibility(View.VISIBLE);
                TemplateCollageActivity.this.viewTemplateSingleBottomBar1.setVisibility(View.INVISIBLE);
                TemplateCollageActivity.this.tlView.resetNoSelected();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
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
        this.blurBarView.setOnEventListener(new ViewTemplateBlur.OnTempBlurViewListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.10
            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void chooseBg() {
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void blurChange(int i, int i2) {
                TemplateCollageActivity.this.mBlurProgress = i;
                TemplateCollageActivity.this.myBlurpos = i2;
                TemplateCollageActivity.this.tlView.setBlurBackground(TemplateCollageActivity.this.mBlurProgress / 5, i2);
                TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                templateCollageActivity.func_blur = "blur_" + i;
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void blurResize(int i) {
                TemplateCollageActivity.this.mBlurProgress = i;
                TemplateCollageActivity.this.tlView.resetBackgroud();
                TemplateCollageActivity.this.func_blur = "no_blur";
            }

            @Override // com.winflag.libcollage.widget.ViewTemplateBlur.OnTempBlurViewListener
            public void resetBg(Bitmap bitmap, int i) {
                TemplateCollageActivity.this.myBlurpos = i;
                TemplateCollageActivity.this.tlView.setBlurBackground(TemplateCollageActivity.this.mBlurProgress / 5, i);
            }
        });
        this.tlView.setBlurBackground(this.mBlurProgress / 5, this.myBlurpos);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.blurBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 160.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.blurBarView.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.blurBarView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void ChangeViewHeight(int i) {
        if (i == 100) {
            this.tlView.clearTouchImage();
        }
        View findViewById = findViewById(R.id.image_fl);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.txtmessage.getLayoutParams();
        if (LibCollageConfig.isShowAd) {
            if (layoutParams != null) {
                layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 50);
                layoutParams2.topMargin = DMScreenInfoUtil.dip2px(this, 105.0f);
            }
        } else if (layoutParams != null) {
            layoutParams.bottomMargin = DMScreenInfoUtil.dip2px(this, i + 50);
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

    private void withTemplateAndCollage() {
        List<Bitmap> list;
        TemplateRes res;
        TemplateManager templateManager = this.mtlManager;
        if (templateManager == null || templateManager.getCount() <= 0 || (list = this.mSrcBitmaps) == null || list.size() <= 0 || (res = this.mtlManager.getRes(0)) == null || this.mSrcBitmaps.size() <= 0) {
            return;
        }
        this.tlView.imagecount = this.mSrcBitmaps.size();
        this.tlView.setCollageStyle(res, this.containerHeight, this.containerWidth);
        this.tlView.setBitmapList(this.mSrcBitmaps);
        this.tlView.setCollageImages(this.mSrcBitmaps, true);
    }

    public void resetBottomBar() {
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
            } catch (Throwable unused) {
            }
        }
        ViewTemplateMangerList viewTemplateMangerList = this.viewTemplateMangerList;
        if (viewTemplateMangerList != null) {
            try {
                viewTemplateMangerList.dispose();
                this.viewTemplateMangerList = null;
            } catch (Throwable unused2) {
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

    public void onLayoutItemClicked() {
        if (this.tmplateHorizonListView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Layout, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Layout, true);
        ViewTemplateHorizonList viewTemplateHorizonList = new ViewTemplateHorizonList(this, null);
        this.tmplateHorizonListView = viewTemplateHorizonList;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewTemplateHorizonList.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 100.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.tmplateHorizonListView.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.tmplateHorizonListView);
        this.tmplateHorizonListView.setManager(this.mtlManager);
        this.tmplateHorizonListView.setOnTemplateChangedListener(new ViewTemplateHorizonList.OnTemplateChangedListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.11
            @Override // com.winflag.libcollage.widget.ViewTemplateHorizonList.OnTemplateChangedListener
            public void onTemplateChanged(DMWBRes dMWBRes, int i, boolean z) {
                if (z && TemplateCollageActivity.this.isAdLoaded) {
                    TemplateCollageActivity.this.lockeResClicked(dMWBRes.getName());
                    return;
                }
                TemplateRes templateRes = (TemplateRes) dMWBRes;
                TemplateCollageActivity.this.tlView.setCollageStyle(templateRes, TemplateCollageActivity.this.containerHeight, TemplateCollageActivity.this.containerWidth);
                TemplateCollageActivity.this.tlView.setRotationDegree(0);
                if (TemplateCollageActivity.this.mViewTplAdjust != null) {
                    TemplateCollageActivity.this.mViewTplAdjust.setSingleModel(templateRes.isSingleModel());
                }
                if (templateRes.isSingleModel()) {
                    TemplateCollageActivity.this.changeScale(1.0f, true);
                }
                TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                templateCollageActivity.func_layout = "layout_" + dMWBRes.getName();
                TemplateCollageActivity.this.onTemplateItemClick(dMWBRes, i);
            }
        });
    }

    public void onSpaceItemClicked() {
        if (this.mViewTplAdjust != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Space, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Space, true);
        ViewTemplateAdjust viewTemplateAdjust = new ViewTemplateAdjust(this);
        this.mViewTplAdjust = viewTemplateAdjust;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewTemplateAdjust.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 100.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.mViewTplAdjust.setLayoutParams(layoutParams);
        if (this.tlView.mComposeInfo != null) {
            this.mViewTplAdjust.setSingleModel(this.tlView.mComposeInfo.isSingleModel());
        }
        this.ly_sub_function.addView(this.mViewTplAdjust);
        this.mViewTplAdjust.setOuterValue((int) this.tlView.getOuterWidth());
        this.mViewTplAdjust.setInnerValue((int) this.tlView.getInnerWidth());
        this.mViewTplAdjust.setCornerValue((int) this.tlView.getRadius());
        this.mViewTplAdjust.setRotationValue(this.tlView.getRotaitonDegree() + 15);
        this.mViewTplAdjust.mListener = new ViewTemplateAdjust.OnCropSeekBarChangeListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.12
            @Override // com.winflag.libcollage.widget.ViewTemplateAdjust.OnCropSeekBarChangeListener
            public void progressChanged(int i, int i2) {
                if (i == 1) {
                    TemplateCollageActivity.this.tlView.Changelayout(i2, -1, i2 * 2);
                    TemplateCollageActivity.this.tlView.setRotationDegree(TemplateCollageActivity.this.tlView.getRotaitonDegree());
                } else if (i == 2) {
                    TemplateCollageActivity.this.tlView.Changelayout(i2, i2 * 2, -1);
                    TemplateCollageActivity.this.tlView.setRotationDegree(TemplateCollageActivity.this.tlView.getRotaitonDegree());
                } else if (i == 3) {
                    TemplateCollageActivity.this.tlView.changeCornerRadius(DMScreenInfoUtil.dip2px(TemplateCollageActivity.this, i2));
                } else {
                    if (i2 >= 13 && i2 <= 17) {
                        i2 = 15;
                    }
                    TemplateCollageActivity.this.tlView.setRotationDegree(i2 - 15);
                }
            }
        };
    }

    public void onStickerItemClicked2() {
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.DMSticker, true);
        this.isBottomOperationViewShow = true;
        CSXlbStickerBarView cSXlbStickerBarView = new CSXlbStickerBarView(this);
        this.xlbstickerbar = cSXlbStickerBarView;
        cSXlbStickerBarView.setRequestAppName("com.winflag.instalens");
        this.xlbstickerbar.setItemClickListener(new CSXlbStickerBarView.onStickerItemClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.13
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onStickerItemClicked(DMWBRes dMWBRes, int i) {
                Bitmap decodeFile;
                CSStickerRes cSStickerRes = (CSStickerRes) dMWBRes;
                if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                    decodeFile = DMBitmapDbUtil.getImageFromAssetsFile(TemplateCollageActivity.this, cSStickerRes.getImageFileName());
                } else {
                    decodeFile = cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE ? BitmapFactory.decodeFile(cSStickerRes.getImageFileName()) : null;
                }
                if (decodeFile != null) {
                    if (TemplateCollageActivity.this.tlView.getStickerCount() >= 8) {
                        Toast.makeText(TemplateCollageActivity.this, TemplateCollageActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TemplateCollageActivity.this.tlView.addSticker(decodeFile);
                } else {
                    Toast.makeText(TemplateCollageActivity.this, "Resource Load faile !", Toast.LENGTH_SHORT).show();
                }
                TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                templateCollageActivity.func_sticker = TemplateCollageActivity.this.func_sticker + "_" + cSStickerRes.getGroup_name() + dMWBRes.getName();
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.onStickerItemClickListener
            public void onClose() {
                TemplateCollageActivity.this.resetBottomBar();
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
        this.mStickerBarView.setOnStickerChooseListener(new DMStickerBarView.OnStickerChooseListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.14
            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerChoose(DMWBRes dMWBRes) {
                ((DMWBImageRes) dMWBRes).getImageBitmap(TemplateCollageActivity.this, new DMWBImageRes.OnResImageLoadListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.14.1
                    @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFinish(Bitmap bitmap) {
                        if (TemplateCollageActivity.this.tlView.getStickerCount() >= 8) {
                            Toast.makeText(TemplateCollageActivity.this, TemplateCollageActivity.this.getResources().getString(R.string.max_sticker_toast), Toast.LENGTH_LONG).show();
                            return;
                        }
                        TemplateCollageActivity.this.tlView.addSticker(bitmap);
                        TemplateCollageActivity.this.resetBottomBar();
                    }

                    @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                    public void onImageLoadFaile() {
                        Toast.makeText(TemplateCollageActivity.this, "Resource Load faile !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override // com.photoart.libsticker.sticker.DMStickerBarView.OnStickerChooseListener
            public void onStickerClose() {
                TemplateCollageActivity.this.resetBottomBar();
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
        cSSquareUiFilterToolBarView.setOnSquareUiFilterToolBarViewListener(new CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.15
            @Override // com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView.onSquareUiFilterToolBarViewListener
            public void onFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z) {
                if (z && TemplateCollageActivity.this.isAdLoaded) {
                    TemplateCollageActivity.this.lockeResClicked(gPUFilterRes.getName());
                    return;
                }
                TemplateCollageActivity.this.mCurrentFilterPos = i;
                TemplateCollageActivity.this.tlView.setFilter(gPUFilterRes, new OnFilterFinishedListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.15.1
                    @Override // org.picspool.lib.filter.listener.OnFilterFinishedListener
                    public void postFinished() {
                        TemplateCollageActivity.this.dismissProcessDialog();
                    }
                });
                TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                templateCollageActivity.func_filter = "filter_" + gPUFilterRes.getShowText();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.filterBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 100.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.filterBarView.setLayoutParams(layoutParams);
        this.ly_sub_function.addView(this.filterBarView);
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
        viewTemplateRatioStyle2.getAdapter().setOnRatioItemClikListener(new TemplateRatioAdapter.onRadioItemClikListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.16
            @Override // com.winflag.libcollage.widget.radioview.TemplateRatioAdapter.onRadioItemClikListener
            public void onClick(int i, RatioRes ratioRes, boolean z) {
                TemplateCollageActivity.this.mScalePos = i;
                TemplateCollageActivity.this.mScaleRatio = z ? 1.0f / ratioRes.getRadio() : ratioRes.getRadio();
                TemplateCollageActivity templateCollageActivity = TemplateCollageActivity.this;
                templateCollageActivity.changeScale(templateCollageActivity.mScaleRatio, Boolean.valueOf(z));
                TemplateCollageActivity templateCollageActivity2 = TemplateCollageActivity.this;
                templateCollageActivity2.func_ratio = "ratio_" + TemplateCollageActivity.this.mScaleRatio;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        TemplateView templateView = this.tlView;
        if (templateView != null) {
            templateView.clearStickerStateCallSpreader();
            this.tlView.restCollageViewAndClearBitmap();
            if (this.tlView.bmps != null) {
                for (int i = 0; i < this.tlView.bmps.size(); i++) {
                    Bitmap bitmap = this.tlView.bmps.get(i);
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            }
        }
        Bitmap bitmap2 = this.shareBitmap;
        if (bitmap2 != null) {
            if (!bitmap2.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        if (this.mSrcBitmaps != null) {
            for (int i2 = 0; i2 < this.mSrcBitmaps.size(); i2++) {
                if (this.mSrcBitmaps.get(i2) != null && !this.mSrcBitmaps.get(i2).isRecycled()) {
                    this.mSrcBitmaps.get(i2).recycle();
                }
            }
            this.mSrcBitmaps.clear();
            this.mSrcBitmaps = null;
        }
        TemplateManager templateManager = this.mtlManager;
        if (templateManager != null) {
            templateManager.clear();
            this.mtlManager = null;
        }
        resetBottomBar();
        super.onDestroy();
    }

    @Override // org.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
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
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.17
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (Build.VERSION.SDK_INT > 12) {
                        TemplateCollageActivity.this.ShowInterstitialAD();
                    } else {
                        TemplateCollageActivity.this.finish();
                    }
                } catch (Exception unused) {
                    TemplateCollageActivity.this.finish();
                } catch (Throwable unused2) {
                    TemplateCollageActivity.this.finish();
                }
                dialogInterface.dismiss();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.18
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

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public void onBitmapCropStart() {
        showProcessDialog();
    }

    @Override // com.winflag.lib.bitmap.AsyncBitmapsCrop.OnBitmapCropListener
    public void onBitmapCropSuccess(List<Bitmap> list) {
        this.mSrcBitmaps = list;
        if (list == null || list.size() < 1) {
            this.handler.post(new Runnable() { // from class: com.winflag.libcollage.activity.-$$Lambda$TemplateCollageActivity$sN5MhEA3ns7g31bURWiBV6TrLFE
                @Override // java.lang.Runnable
                public void run() {
                    TemplateCollageActivity.this.lambda$onBitmapCropSuccess$1$TemplateCollageActivity();
                }
            });
            return;
        }
        if (this.mSrcBitmaps.size() == 1) {
            if (this.mSrcBitmaps.get(0) != null && this.mSrcBitmaps.get(0).getWidth() > 0) {
                this.mtlManager = new TemplateManager(this, this.mSrcBitmaps.size(), this.mSrcBitmaps.get(0).getWidth(), this.mSrcBitmaps.get(0).getHeight());
            } else {
                this.mtlManager = new TemplateManager(this, this.mSrcBitmaps.size());
            }
        } else if (this.mtlManager == null) {
            this.mtlManager = new TemplateManager(this, this.mSrcBitmaps.size());
        }
        this.mtlManager.setupTemplateIcon(this.mSrcBitmaps);
        this.handler.post(new Runnable() { // from class: com.winflag.libcollage.activity.-$$Lambda$TemplateCollageActivity$5JsOfcWNpoIbpvwJ0dtIQyeDGtQ
            @Override // java.lang.Runnable
            public final void run() {
                TemplateCollageActivity.this.lambda$onBitmapCropSuccess$2$TemplateCollageActivity();
            }
        });
    }

    public void lambda$onBitmapCropSuccess$1$TemplateCollageActivity() {
        dismissProcessDialog();
        Toast.makeText(this, "Image is not exist!", Toast.LENGTH_LONG).show();
    }

    public  void lambda$onBitmapCropSuccess$2$TemplateCollageActivity() {
        dismissProcessDialog();
        withTemplateAndCollage();
        onLayoutItemClicked();
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
        this.tlView.getOutputImage(CSCropAsyncBitmapCropExecute.getImageQuality(this), new TemplateView.onOutputImageListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.19
            @Override // com.winflag.libcollage.view.TemplateView.onOutputImageListener
            public void onOutputImageFinish(Bitmap bitmap2) {
                TemplateCollageActivity.this.gotoShare(bitmap2);
            }
        });
        this.func_adjust = "outer:" + this.tlView.getOuterWidth() + "/inner:" + this.tlView.getInnerWidth() + "/corner:" + this.tlView.getRadius() + "/rotation:" + this.tlView.getRotaitonDegree();
        this.func_adjust = "outer:" + this.tlView.getOuterWidth() + "/inner:" + this.tlView.getInnerWidth() + "/corner:" + this.tlView.getRadius() + "/rotation:" + this.tlView.getRotaitonDegree();
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
        if (stringExtra == null || stringExtra.length() <= 0 || stringExtra2 == null || stringExtra2.length() == 0) {
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
                Toast.makeText(this, "DMSticker Add faile !", Toast.LENGTH_SHORT).show();
                return;
            }
            DMCommonStickersManager commonStickerManagerByStickType = new DMStickerTypeOperation(this).getCommonStickerManagerByStickType(stickerType);
            if (commonStickerManagerByStickType == null) {
                return;
            }
            commonStickerManagerByStickType.setContext(this);
            commonStickerManagerByStickType.init();
            commonStickerManagerByStickType.getRes(stringExtra).getImageBitmap(this, new DMWBImageRes.OnResImageLoadListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.20
                @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                public void onImageLoadFinish(Bitmap bitmap) {
                    TemplateCollageActivity.this.tlView.addSticker(bitmap);
                }

                @Override // org.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
                public void onImageLoadFaile() {
                    Toast.makeText(TemplateCollageActivity.this, "Resource Load faile !", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception unused) {
            Toast.makeText(this, "DMSticker Add faile !", Toast.LENGTH_SHORT).show();
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
        this.tlView.setCollageStyle(null, this.containerHeight, this.containerWidth);
        TemplateView templateView = this.tlView;
        templateView.setRotationDegree(templateView.getRotaitonDegree());
        ViewGroup viewGroup = (ViewGroup) this.tlView.getParent();
        viewGroup.getLayoutParams().width = this.containerWidth;
        viewGroup.getLayoutParams().height = this.containerHeight;
        this.handler.postDelayed(new Runnable() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.21
            @Override // java.lang.Runnable
            public void run() {
                TemplateCollageActivity.this.tlView.setRotationDegree(TemplateCollageActivity.this.tlView.getRotaitonDegree());
            }
        }, 50L);
    }

    protected void onFilterItemClickImpl() {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        ViewSingleTemplateFilter viewSingleTemplateFilter = new ViewSingleTemplateFilter(this);
        this.singleFilterBarView = viewSingleTemplateFilter;
        viewSingleTemplateFilter.setOnSingleTemplateFilterViewListener(new ViewSingleTemplateFilter.OnSingleTemplateFilterViewListener() { // from class: com.winflag.libcollage.activity.TemplateCollageActivity.22
            @Override // com.winflag.libcollage.widget.ViewSingleTemplateFilter.OnSingleTemplateFilterViewListener
            public void onSingleFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z) {
                if (z && TemplateCollageActivity.this.isAdLoaded) {
                    TemplateCollageActivity.this.lockeResClicked(gPUFilterRes.getName());
                } else {
                    TemplateCollageActivity.this.tlView.setSingleFilter(gPUFilterRes, null);
                }
            }

            @Override // com.winflag.libcollage.widget.ViewSingleTemplateFilter.OnSingleTemplateFilterViewListener
            public void onSingleClose() {
                TemplateCollageActivity.this.resetBottomBar();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.singleFilterBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 120.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.singleFilterBarView.setLayoutParams(layoutParams);
        this.ly_single_sub_function.addView(this.singleFilterBarView);
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

    public static Bitmap openImage(String str) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bitmap;
        } catch (IOException e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }
}
