package com.photo.editor.square.splash.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.photo.editor.square.splash.utils.AdjustInfo;
import com.photo.editor.square.splash.utils.AssBitManage;
import com.photo.editor.square.splash.utils.CSSaveToSDNew;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.CollageFunView;
import com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView;
import com.photo.editor.square.splash.view.glitch.IL1;
import com.photo.editor.square.splash.view.view.FilterView;
import com.photo.editor.square.splash.view.view.BackgroundGroupsItemsObserver;
import com.photo.editor.square.splash.view.view.BackgroundDrawableView;
import com.photo.editor.square.splash.view.view.CustomFrameLayoutImags;
import com.photo.editor.square.splash.view.view.ColorView;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.photo.editor.square.splash.view.view.bean.FiltereTypes;
import com.picspool.snappic.activity.CSSysConfig;
import com.sky.testproject.R;
import com.winflag.Utils.Data_Change;
import com.winflag.libcollage.activity.LibCollageConfig;
import com.winflag.libcollage.activity.TemplateCollageActivity;
import com.winflag.libcollage.view.TemplateBottomBarView;
import com.winflag.libcollage.view.TemplateView;
import com.winflag.libcollage.widget.ViewTemplateAdjust;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import java.util.List;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/* loaded from: classes2.dex */
public class TemplateActivity extends TemplateCollageActivity {
    static final String TAG = "TemplateActivity";
    private CollageFunView collageFunView;
    private AdjustInfo currentAdjustInfo;
    private AssBitManage.bitBean currentBitBean;
    private GPUImageFilter currentGlitchGpuImageFilter;
    private Disposable disposable;
    private CSExpandableFilterView filterBarView;
    private IL1 il1;
    private FilterView filterView;
    private boolean isSave;
    private boolean isShowSecondFun;
    List<LibDMCollageInfo> lstCollageInfo;
    private String mResname;
    private int position;
    private RelativeLayout share;
    private CSExpandableFilterView singleFilterBarView;
    private ViewTemplateAdjust viewTemplateAdjust;

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public CSListNaitveAdRes getListNativeAd() {
        return null;
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void lockeResClicked(String str) {
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity, com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        LibCollageConfig.maxQuality = CSSysConfig.getImageQuality(this);
        LibCollageConfig.isLowMemery = false;
        super.onCreate(bundle);
        this.share = (RelativeLayout) findViewById(R.id.share);
        this.T_L = (ImageView) findViewById(R.id.image_left_top);
        this.T_R = (ImageView) findViewById(R.id.image_reit_top);
        this.B_L = (ImageView) findViewById(R.id.image_left_bottem);
        this.B_R = (ImageView) findViewById(R.id.image_reit_bottem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isSave = false;
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onLayoutItemClicked() {
        showCollageFunView(0);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onSpaceItemClicked() {
        showCollageFunView(1);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void resetBottomBar() {
        super.resetBottomBar();
        if (this.il1 != null) {
            this.il1 = null;
        }
        if (this.filterView != null) {
            this.filterView = null;
        }
        if (!this.isShowSecondFun) {
            this.ly_single_sub_function.removeAllViews();
        } else if (this.ly_single_sub_function.getChildCount() == 2) {
            this.isShowSecondFun = false;
            this.ly_single_sub_function.removeViewAt(1);
            this.isBottomOperationViewShow = true;
            ChangeViewHeight(100);
        }
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onTemplateBgItemClicked() {
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Background, true);
        this.isBottomOperationViewShow = true;
        showCollageFunView(2);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onMangerItemClicked() {
        resetBottomBar();
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Background, true);
        this.isBottomOperationViewShow = true;
        showCollageFunView(3);
    }

    public void onSquareBgGroupItemClicked(String str, List<BackgroundGroupsItems.BackgroundItem> list) {
        Drawable background;
        final BackgroundDrawableView backgroundDrawableView = new BackgroundDrawableView(this, list);
        backgroundDrawableView.setIl6(str);
        if (this.tlView.img_bg.getBackground() instanceof BitmapDrawable) {
            background = new BitmapDrawable(getResources(), ((BitmapDrawable) this.tlView.img_bg.getBackground()).getBitmap().copy(Bitmap.Config.ARGB_8888, true));
        } else {
            background = this.tlView.img_bg.getBackground();
        }
        backgroundDrawableView.setIl2(background);
        backgroundDrawableView.setIl5(new CustomFrameLayoutImags.IL4567123<Drawable>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.1
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(Drawable drawable) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                TemplateActivity.this.ly_single_sub_function.removeView(backgroundDrawableView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                TemplateActivity.this.ly_single_sub_function.removeView(backgroundDrawableView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(Drawable drawable) {
                if (drawable != null) {
                    if (drawable instanceof BitmapDrawable) {
                        TemplateActivity.this.tlView.setSquareBackground(new BitmapDrawable(TemplateActivity.this.getResources(), ((BitmapDrawable) drawable).getBitmap().copy(Bitmap.Config.ARGB_8888, true)), false);
                        return;
                    } else {
                        TemplateActivity.this.tlView.setSquareBackground(drawable, false);
                        return;
                    }
                }
                TemplateActivity.this.tlView.setBlurBackground(10, 0);
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, DMScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(backgroundDrawableView);
        ChangeViewHeight(100);
        backgroundDrawableView.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    protected void onFilterItemClickImpl() {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        CSExpandableFilterView cSExpandableFilterView = new CSExpandableFilterView(this);
        this.singleFilterBarView = cSExpandableFilterView;
        cSExpandableFilterView.setOnExpandableFilterViewListener(new CSExpandableFilterView.OnExpandableFilterViewListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.2
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
            public void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str) {
                TemplateActivity.this.tlView.setSingleFilter(dMWBRes, null);
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
            public void doneView() {
                TemplateActivity.this.resetBottomBar();
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.singleFilterBarView.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this, 150.0f);
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
        }
        this.singleFilterBarView.setLayoutParams(layoutParams);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, dip2px, 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.singleFilterBarView);
        this.ly_single_sub_function.setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$TemplateActivity$ovEigL6rLpZAkbmNc1QIUOG_PfA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TemplateActivity.this.lambda$onFilterItemClickImpl$0$TemplateActivity(view);
            }
        });
        ChangeViewHeight(DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
        this.singleFilterBarView.startAnimation(translateAnimation);
        this.singleFilterBarView.findViewById(R.id.filter_close).setVisibility(View.VISIBLE);
    }

    public /* synthetic */ void lambda$onFilterItemClickImpl$0$TemplateActivity(View view) {
        resetBottomBar();
        this.singleFilterBarView.findViewById(R.id.filter_close).setVisibility(View.GONE);
        this.ly_single_sub_function.setOnClickListener(null);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onBottomFilterClick() {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.Filter, true);
        if (this.filterBarView == null) {
            CSExpandableFilterView cSExpandableFilterView = new CSExpandableFilterView(this);
            this.filterBarView = cSExpandableFilterView;
            cSExpandableFilterView.setOnExpandableFilterViewListener(new CSExpandableFilterView.OnExpandableFilterViewListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.3
                @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
                public void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str) {
                    TemplateActivity.this.mCurrentFilterPos = i;
                    TemplateView templateView = TemplateActivity.this.tlView;
                    final TemplateActivity templateActivity = TemplateActivity.this;
                    templateView.setFilter(dMWBRes, new OnFilterFinishedListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$vwdX0PQ2FbipOIoqrvToq4Mdyu0
                        @Override // com.picspool.lib.filter.listener.OnFilterFinishedListener
                        public final void postFinished() {
                            TemplateActivity.this.dismissProcessDialog();
                        }
                    });
                    TemplateActivity templateActivity2 = TemplateActivity.this;
                    templateActivity2.func_filter = "filter_" + dMWBRes.getShowText();
                }

                @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
                public void doneView() {
                    TemplateActivity.this.resetBottomBar();
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.filterBarView.getLayoutParams();
            int dip2px = DMScreenInfoUtil.dip2px(this, 150.0f);
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(-1, dip2px);
            }
            this.filterBarView.setLayoutParams(layoutParams);
        }
        showFunView(getString(R.string.bottom_filter), this.filterBarView, false, DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onSingleGlitchClick() {
        if (this.il1 != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, false);
            this.currentAdjustInfo = null;
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        Bitmap selBitmap = this.tlView.getSelBitmap();
        if (selBitmap == null) {
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        this.currentAdjustInfo = new AdjustInfo(this, selBitmap.copy(Bitmap.Config.ARGB_8888, true));
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.GLITCH, true);
        if (this.il1 == null) {
            IL1 il1 = new IL1(this);
            this.il1 = il1;
            il1.setShowBottomTool(true);
            this.il1.setIl3(new IL1.IL234567() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.4
                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f1 */
                public void mo84f1(Bitmap bitmap) {
                    if (TemplateActivity.this.tlView != null) {
                        TemplateActivity.this.tlView.setSelViewBitmap(bitmap);
                    }
                }

                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f2 */
                public void mo83f2(GPUImageFilter gPUImageFilter) {
                    TemplateActivity.this.currentGlitchGpuImageFilter = gPUImageFilter;
                    Bitmap src = TemplateActivity.this.currentAdjustInfo.getSrc();
                    if (src == null || src.isRecycled()) {
                        return;
                    }
                    TemplateActivity.this.il1.f12(src.copy(Bitmap.Config.ARGB_8888, true));
                }
            });
            this.il1.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.il1);
        ChangeViewHeight(100);
        this.il1.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onSingleAdjustClick() {
        if (this.filterView != null) {
            resetBottomBar();
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        resetBottomBar();
        Bitmap selBitmap = this.tlView.getSelBitmap();
        if (selBitmap == null) {
            this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, false);
            ChangeViewHeight(0);
            this.currentAdjustInfo = null;
            return;
        }
        this.currentAdjustInfo = new AdjustInfo(this, selBitmap.copy(Bitmap.Config.ARGB_8888, true));
        this.isBottomOperationViewShow = true;
        this.viewTemplateBottomBar1.setInSelectorStat(TemplateBottomBarView.TemplateBottomItem.ADJUST, true);
        if (this.filterView == null) {
            FilterView filterView = new FilterView(this) { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.5
                @Override // com.photo.editor.square.splash.view.view.IL26, com.photo.editor.square.splash.view.view.IL29
                public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
                    super.f10(context, frameLayout, frameLayout2);
                    showBottomToolView(true);
                }
            };
            this.filterView = filterView;
            filterView.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.6
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(FiltereTypes filtereTypes) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    TemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(FiltereTypes filtereTypes) {
                    if (filtereTypes != null) {
                        TemplateActivity.this.currentAdjustInfo.setAdjust(filtereTypes.getColorMatrix(), filtereTypes.getAdjustImageFilter());
                    } else {
                        TemplateActivity.this.currentAdjustInfo.setAdjust(null, null);
                    }
                    if (TemplateActivity.this.tlView != null) {
                        TemplateActivity.this.tlView.setSelViewBitmap(TemplateActivity.this.currentAdjustInfo.getResult());
                    }
                }
            });
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(12);
            this.filterView.setLayoutParams(layoutParams);
            this.filterView.setColorShowListener(new FilterView.OnColorShowListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$TemplateActivity$Xmpxp4VVQRDq6GwRbLBwjJy9Hdw
                @Override // com.photo.editor.square.splash.view.view.IL26.OnColorShowListener
                public final void onColorViewShow() {
                    TemplateActivity.this.showColorView();
                }
            });
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.filterView);
        ChangeViewHeight(100);
        this.filterView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorView() {
        if (this.currentAdjustInfo == null) {
            return;
        }
        final ColorView colorView = new ColorView(this);
        colorView.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.7
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(FiltereTypes filtereTypes) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                TemplateActivity.this.ly_single_sub_function.removeView(colorView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                TemplateActivity.this.ly_single_sub_function.removeView(colorView);
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(FiltereTypes filtereTypes) {
                if (filtereTypes != null) {
                    TemplateActivity.this.currentAdjustInfo.setAdjust(filtereTypes.getColorMatrix());
                } else {
                    TemplateActivity.this.currentAdjustInfo.setAdjust(null);
                }
                if (TemplateActivity.this.tlView != null) {
                    TemplateActivity.this.tlView.setSelViewBitmap(TemplateActivity.this.currentAdjustInfo.getResult());
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(12);
        this.ly_single_sub_function.addView(colorView, layoutParams);
        ChangeViewHeight(100);
        colorView.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onTextItemClicked() {
        super.onTextItemClicked();
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onStickerItemClicked2() {
        super.onStickerItemClicked2();
        showStickerFunView(this.ly_sub_function.getChildAt(0));
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onBottomRatioClick() {
        super.onBottomRatioClick();
        showFunView(getString(R.string.bottom_ratio), this.ly_sub_function.getChildAt(0));
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onBottomBlurClick() {
        super.onBottomBlurClick();
        showFunView(getString(R.string.bottom_blur), this.ly_sub_function.getChildAt(0));
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void gotoShare(Bitmap bitmap) {
        if (this.shareBitmap != null) {
            if (!this.shareBitmap.isRecycled()) {
                this.shareBitmap.recycle();
            }
            this.shareBitmap = null;
        }
        this.shareBitmap = bitmap;
        showProcessDialog();
        CSSaveToSDNew.saveImage(getApplicationContext(), this.shareBitmap, "Color Splash", Bitmap.CompressFormat.PNG, new DMSaveDoneListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.8
            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSavingException(Exception exc) {
                TemplateActivity.this.dismissProcessDialog();
            }

            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSaveDone(String str, Uri uri) {
                TemplateActivity.this.dismissProcessDialog();
                if (TemplateActivity.this.isSave || TemplateActivity.this.shareBitmap == null || TemplateActivity.this.shareBitmap.isRecycled()) {
                    return;
                }
                TemplateActivity.this.isSave = true;
                try {
                    Intent intent = new Intent(TemplateActivity.this, CSShareActivity.class);
                    intent.putExtra("keyShareBmp", uri.toString());
                    intent.putExtra("shareBmpWidth", TemplateActivity.this.shareBitmap.getWidth());
                    intent.putExtra("keyShareBmpPath", str);
                    TemplateActivity.this.startActivity(intent);
                    TemplateActivity.this.overridePendingTransition(0, 0);
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDestroy();
    }

    private void showStickerFunView(View view) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (int) ((DMScreenInfoUtil.screenWidth(this) * 11.0f) / 18.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(view);
        ChangeViewHeight(100);
        view.startAnimation(translateAnimation);
    }

    private void showFunView(String str, View view) {
        showFunView(str, view, true, 100);
    }

    private void showFunView(final String str, final View view, final boolean z, int i) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        final CustomFrameLayoutImags<Void> customFrameLayoutImags = new CustomFrameLayoutImags<Void>(this) { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.9
            @Override // com.photo.editor.square.splash.view.view.IL29
            protected void onSelectedPosition(int i2) {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.photo.editor.square.splash.view.view.IL29
            public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
                super.f10(context, frameLayout, frameLayout2);
                showToolView(z);
                setIl6(str);
                frameLayout2.addView(view);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(-1, -1);
                }
                layoutParams.gravity = 17;
                view.setLayoutParams(layoutParams);
            }
        };
        customFrameLayoutImags.setIl5(new CustomFrameLayoutImags.IL4567123<Void>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.10
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(Void r1) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(Void r1) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                if (TemplateActivity.this.isShowSecondFun) {
                    TemplateActivity.this.isShowSecondFun = false;
                    TemplateActivity.this.ly_single_sub_function.removeView(customFrameLayoutImags);
                    TemplateActivity.this.isBottomOperationViewShow = true;
                    return;
                }
                TemplateActivity.this.resetBottomBar();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                if (TemplateActivity.this.isShowSecondFun) {
                    TemplateActivity.this.isShowSecondFun = false;
                    TemplateActivity.this.ly_single_sub_function.removeView(customFrameLayoutImags);
                    TemplateActivity.this.isBottomOperationViewShow = true;
                    return;
                }
                TemplateActivity.this.resetBottomBar();
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, customFrameLayoutImags.f11(), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(customFrameLayoutImags);
        ChangeViewHeight(i);
        customFrameLayoutImags.startAnimation(translateAnimation);
    }

    private void showCollageFunView(int i) {
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        if (this.collageFunView == null) {
            CollageFunView collageFunView = new CollageFunView(this);
            this.collageFunView = collageFunView;
            collageFunView.setmListener(new CollageFunView.OnTemplateChangedListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.11
                @Override // com.photo.editor.square.splash.view.CollageFunView.OnTemplateChangedListener
                public void onTemplateChanged(List<AssBitManage.bitBean> list, int i2) {
                    TemplateActivity.this.currentBitBean = list.get(i2);
                    TemplateActivity.this.refreshCurrentFrame();
                }
            });
            this.collageFunView.setListener(new CollageFunView.OnAdjustBtnClickListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$TemplateActivity$9YRxUyRVqb3iR_QrSjYm3boMJoo
                @Override // com.photo.editor.square.splash.view.CollageFunView.OnAdjustBtnClickListener
                public final void onAdjustClick() {
                    TemplateActivity.this.lambda$showCollageFunView$1$TemplateActivity();
                }
            });
            this.collageFunView.setIl5(new CustomFrameLayoutImags.IL4567123<Void>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.12
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(Void r1) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(Void r1) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                    TemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    TemplateActivity.this.resetBottomBar();
                }
            });
            super.onLayoutItemClicked();
            this.collageFunView.setLayout(this.ly_sub_function.getChildAt(0));
            resetBottomBar();
            super.onSpaceItemClicked();
            ViewTemplateAdjust viewTemplateAdjust = (ViewTemplateAdjust) this.ly_sub_function.getChildAt(0);
            this.viewTemplateAdjust = viewTemplateAdjust;
            this.collageFunView.setSpace(viewTemplateAdjust);
            resetBottomBar();
            BackgroundGroupsItemsObserver backgroundGroupsItemsObserver = new BackgroundGroupsItemsObserver(this);
            backgroundGroupsItemsObserver.setIl5(new CustomFrameLayoutImags.IL4567123<Observable<BackgroundGroupsItems.BackgroundGroup>>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.13
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(Observable<BackgroundGroupsItems.BackgroundGroup> observable) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                    TemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                    TemplateActivity.this.resetBottomBar();
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(Observable<BackgroundGroupsItems.BackgroundGroup> observable) {
                    observable.subscribe(new Observer<BackgroundGroupsItems.BackgroundGroup>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.13.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                            TemplateActivity.this.disposable = disposable;
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(BackgroundGroupsItems.BackgroundGroup backgroundGroup) {
                            TemplateActivity.this.onSquareBgGroupItemClicked(backgroundGroup.getName(), backgroundGroup.getItems());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e(TemplateActivity.TAG, "onError: ", e);
                        }

//                        @Override // io.reactivex.Observer
//                        public void onError(Throwable th) {
//                            Log.e(TemplateActivity.TAG, "onError: ", th);
//                        }
                    });
                }
            });
            this.collageFunView.setBackground(backgroundGroupsItemsObserver);
        }
        this.collageFunView.selectedTab(i);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.collageFunView.f11(), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.ly_single_sub_function.addView(this.collageFunView);
        ChangeViewHeight(100);
        this.collageFunView.startAnimation(translateAnimation);
    }

    public /* synthetic */ void lambda$showCollageFunView$1$TemplateActivity() {
        if (this.tlView.mComposeInfo == null || !this.tlView.mComposeInfo.isSingleModel()) {
            this.isShowSecondFun = true;
            onBottomRatioClick();
        }
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void changeScale(float f, Boolean bool) {
        super.changeScale(f, bool);
        refreshCurrentFrame();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCurrentFrame() {
        Data_Change.getInstance().setTl(null);
        Data_Change.getInstance().setTr(null);
        Data_Change.getInstance().setBr(null);
        Data_Change.getInstance().setBl(null);
        AssBitManage.bitBean bitbean = this.currentBitBean;
        if (bitbean != null) {
            loadFrameBitmap(bitbean.getL_t(), this.T_L);
            loadFrameBitmap(this.currentBitBean.getR_t(), this.T_R);
            loadFrameBitmap(this.currentBitBean.getL_b(), this.B_L);
            loadFrameBitmap(this.currentBitBean.getR_b(), this.B_R);
            return;
        }
        this.T_L.setImageBitmap(null);
        this.T_R.setImageBitmap(null);
        this.B_L.setImageBitmap(null);
        this.B_R.setImageBitmap(null);
    }

    private void loadFrameBitmap(final String str, final ImageView imageView) {
        Glide.with((FragmentActivity) this).asBitmap().load(str).listener(new RequestListener<Bitmap>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.15
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                imageView.setImageBitmap(null);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                if (!str.equals(TemplateActivity.this.currentBitBean.getL_t())) {
                    if (!str.equals(TemplateActivity.this.currentBitBean.getR_t())) {
                        if (!str.equals(TemplateActivity.this.currentBitBean.getR_b())) {
                            if (str.equals(TemplateActivity.this.currentBitBean.getL_b())) {
                                Data_Change.getInstance().setBl(bitmap);
                                return false;
                            }
                            return false;
                        }
                        Data_Change.getInstance().setBr(bitmap);
                        return false;
                    }
                    Data_Change.getInstance().setTr(bitmap);
                    return false;
                }
                Data_Change.getInstance().setTl(bitmap);
                return false;
            }
        }).into(imageView);

//                .into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.14
//            @Override // com.bumptech.glide.request.target.Target
//            public void onResourceReady(Object obj, Transition transition) {
//                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//            }
//
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                float f = TemplateActivity.this.containerWidth / 800.0f;
//                if (TemplateActivity.this.containerWidth > TemplateActivity.this.containerHeight) {
//                    f = TemplateActivity.this.containerHeight / 800.0f;
//                }
//                Data_Change.getInstance().setContainerWidth(TemplateActivity.this.containerWidth);
//                Data_Change.getInstance().setContainerHeight(TemplateActivity.this.containerHeight);
//                imageView.setImageBitmap(ImageUtils.scale(bitmap, (int) (width * f), (int) (height * f)));
//            }
//        });
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onTemplateItemClick(DMWBRes dMWBRes, int i) {
        super.onTemplateItemClick(dMWBRes, i);
        this.position = i;
        ViewTemplateAdjust viewTemplateAdjust = this.viewTemplateAdjust;
        if (viewTemplateAdjust != null) {
            viewTemplateAdjust.setSingleModel(this.tlView.mComposeInfo.isSingleModel());
        }
    }

    public Bitmap viewConversionBitmaptem(View view, View view2) {
        Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        ((ViewGroup) view2.getParent()).draw(canvas);
        return createBitmap;
    }

    @Override // com.winflag.libcollage.activity.TemplateCollageActivity
    public void onBackImpl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setMessage(R.string.quit_string);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.16
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
//                if (AdmobConstants.isPositive(AdmobConstants.BACK_MAIN_INT_AS)) {
//                    AdmobLevelManager.getinstance().showAd(TemplateActivity.this, new AdListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.16.1
//                        @Override // com.google.android.gms.ads.AdListener
//                        public void onAdClosed() {
//                        }
//                    }, "Template");
//                }
                TemplateActivity.this.finish();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.TemplateActivity.17
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
