package com.photo.editor.square.splash.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView;
import com.google.firebase.messaging.Constants;
import com.magic.video.editor.effect.gallery.view.CSGalleryActivity;
import com.photo.editor.square.splash.utils.CSSaveToSDNew;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.cropview.CSCropView;
import com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView;
import com.photo.editor.square.splash.view.glitch.IL1;
import com.photo.editor.square.splash.view.glitch.IL123456;
import com.photo.editor.square.splash.view.glitch.IL20;
import com.photo.editor.square.splash.view.view.FilterView;
import com.photo.editor.square.splash.view.view.BackgroundGroupsItemsObserver;
import com.photo.editor.square.splash.view.view.BackgroundDrawableView;
import com.photo.editor.square.splash.view.view.CustomFrameLayoutImags;
import com.photo.editor.square.splash.view.view.ColorView;
import com.photo.editor.square.splash.view.view.RatiosImageCases;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.photo.editor.square.splash.view.view.bean.FiltereTypes;
import com.picspool.libsquare.CSLibSquareConfig;
import com.picspool.snappic.activity.CSSysConfig;
import com.sky.testproject.R;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import com.winflag.libsquare.uiview.CSSquareUiMainToolBarView;
import java.util.List;
import java.util.Set;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;

import io.reactivex.rxjava3.disposables.Disposable;


/* loaded from: classes2.dex */
public class CSSquareMainActivity extends com.winflag.libsquare.CSSquareMainActivity {
    private static final String TAG = "Jie";
    private BackgroundGroupsItemsObserver BackgroundGroupsItemsObserver;
    private RatiosImageCases RatiosImageCases;
    private boolean adIsShow;
    private FrameLayout ads;
    private Bitmap cropBitmap;
    private CSCropView cropView;
    private GPUImageFilter currentGlitchGpuImageFilter;
    private int defaultMaxHeigth;
    private int defaultWidth;
    private Disposable disposable;
    private CSExpandableFilterView filterBarView;
    private IL1 il1;
    private FilterView filterView;
    private boolean isBannerShow;
    private ViewGroup layout_sub_full_toolbar;
    private Bitmap shareBitmap;
    private String currentRatio = "1:1";
    private String applyRatio = "1:1";
    private int currentPos = 1;
    private int REQUEST_CHOOSE_BG = CSXlbStickerBarView.STICKER_REQUESTCODE;
    boolean isSave = false;
    String from = "";

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public CSListNaitveAdRes getListNativeAd() {
        return null;
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void lockeResClicked(String str) {
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity, com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        CSLibSquareConfig.maxQuality = CSSysConfig.getImageQuality(this);
        super.onCreate(bundle);
        this.defaultWidth = CSScreenInfoUtil.screenWidth(this);
        this.defaultMaxHeigth = CSScreenInfoUtil.screenHeight(this) - CSScreenInfoUtil.dip2px(this, 200.0f);
        this.layout_sub_full_toolbar = (ViewGroup) findViewById(R.id.layout_sub_full_toolbar);
        findViewById(R.id.btn_square).setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$5CQTk0M_C6Fz4dbU5MAgBfcDDjU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CSSquareMainActivity.this.lambda$onCreate$0$CSSquareMainActivity(view);
            }
        });
//        IL23.getInstance().requestOnlineGroup(this);
        String stringExtra = getIntent().getStringExtra(CSOnlineStoreActivity.STICKER_EXTRA);
        if (!TextUtils.isEmpty(stringExtra)) {
            onStickerItemClicked2(stringExtra);
        }
        this.ads = (FrameLayout) findViewById(R.id.ad_banner);
//        AdmobBannerViewManager.getinstance().getAdCloseLiveData().observe(this, new Observer() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$hTuUzKLZ5pihXJVuohbucn7wUNA
//            @Override // androidx.lifecycle.Observer
//            public final void onChanged(Object obj) {
//                CSSquareMainActivity.this.lambda$onCreate$1$CSSquareMainActivity((Boolean) obj);
//            }
//        });
    }

    public /* synthetic */ void lambda$onCreate$0$CSSquareMainActivity(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected()) {
            applyRatio(null);
        } else {
            applyRatio(this.currentRatio);
        }
    }

    public /* synthetic */ void lambda$onCreate$1$CSSquareMainActivity(Boolean bool) {
        showBannerViewAd();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isSave = false;
    }

    private void showBannerViewAd() {
//        if (AdmobConstants.isPositive(AdmobConstants.MAIN_BA_AS) && this.ads.getChildCount() == 0) {
//            boolean showAd = AdmobBannerViewManager.getinstance().showAd(this.ads, "cut_spiral");
//            this.adIsShow = showAd;
//            if (showAd) {
//                return;
//            }
//            AdmobBannerViewManager.getinstance().getAdStateLiveData().observe(this, new Observer<Boolean>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.1
//                @Override // androidx.lifecycle.Observer
//                public void onChanged(Boolean bool) {
//                    CSSquareMainActivity.this.adIsShow = AdmobBannerViewManager.getinstance().showAd(CSSquareMainActivity.this.ads, "cut_spiral", false);
//                    if (CSSquareMainActivity.this.adIsShow) {
//                        AdmobBannerViewManager.getinstance().getAdStateLiveData().removeObserver(this);
//                    }
//                }
//            });
//        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onEditorItemClickImpl() {
        super.onEditorItemClickImpl();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onAdustItemClickImpl() {
        super.onAdustItemClickImpl();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onSquareBgItemClicked() {
        if (this.BackgroundGroupsItemsObserver != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Background, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Background, true);
        this.isBottomOperationViewShow = true;
        BackgroundGroupsItemsObserver backgroundGroupsItemsObserver = new BackgroundGroupsItemsObserver(this);
        this.BackgroundGroupsItemsObserver = backgroundGroupsItemsObserver;
//        il27.setIl5(new C20942());
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.BackgroundGroupsItemsObserver);
        ChangeViewHeight(100);
        this.BackgroundGroupsItemsObserver.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.activitys.CSSquareMainActivity$2 */
    /* loaded from: classes2.dex */
    /*public class C20942 implements IL29.IL4567123<Observable<IL23.BackgroundGroup>> {
        @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
        *//* renamed from: f1 *//*
        public void mo72f1() {
        }

        @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
        *//* renamed from: f2 *//*
        public void mo71f2() {
        }

        @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
        *//* renamed from: f3  reason: avoid collision after fix types in other method *//*
        public void mo70f3(Observable<IL23.BackgroundGroup> observable) {
        }

        C20942() {
        }

        @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
        *//* renamed from: f4  reason: avoid collision after fix types in other method *//*
        public void mo69f4(Observable<IL23.BackgroundGroup> observable) {
            observable.filter(new Predicate() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$2$37BUeckwwTQ4pzcgSjRgj3FNMn4
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    return C20942.this.lambda$f4$0$CSSquareMainActivity$2((IL23.BackgroundGroup) obj);
                }
            }).subscribe(new io.reactivex.Observer<IL23.BackgroundGroup>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.2.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    CSSquareMainActivity.this.disposable = disposable;
                }

                @Override // io.reactivex.Observer
                public void onNext(IL23.BackgroundGroup backgroundGroup) {
                    CSSquareMainActivity.this.onSquareBgGroupItemClicked(backgroundGroup.getName(), backgroundGroup.getItems());
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    Log.e("Jie", "onError: ", th);
                }
            });
        }

        public *//* synthetic *//* boolean lambda$f4$0$CSSquareMainActivity$2(IL23.BackgroundGroup backgroundGroup) throws Exception {
            return CSSquareMainActivity.this.layout_sub_full_toolbar.getChildCount() == 0;
        }
    }*/

    public void onSquareBgGroupItemClicked(String str, List<BackgroundGroupsItems.BackgroundItem> list) {
        Drawable background;
        if (list == null) {
            return;
        }
        onBackgroundItemClick(str);
        BackgroundDrawableView backgroundDrawableView = new BackgroundDrawableView(this, list);
        backgroundDrawableView.setIl6(str);
        if (this.squareMainView.img_bg.getBackground() instanceof BitmapDrawable) {
            background = new BitmapDrawable(getResources(), ((BitmapDrawable) this.squareMainView.img_bg.getBackground()).getBitmap().copy(Bitmap.Config.ARGB_8888, true));
        } else {
            background = this.squareMainView.img_bg.getBackground();
        }
        backgroundDrawableView.setIl2(background);
        backgroundDrawableView.setIl5(new CustomFrameLayoutImags.IL4567123<Drawable>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.3
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(Drawable drawable) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                CSSquareMainActivity.this.layout_sub_full_toolbar.removeAllViews();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                CSSquareMainActivity.this.layout_sub_full_toolbar.removeAllViews();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(Drawable drawable) {
                if (drawable == null) {
                    CSSquareMainActivity.this.squareMainView.setBlurBackground(0.0f, 0.5f, false);
                } else if (drawable instanceof BitmapDrawable) {
                    CSSquareMainActivity.this.squareMainView.setSquareBackground(new BitmapDrawable(CSSquareMainActivity.this.getResources(), ((BitmapDrawable) drawable).getBitmap().copy(Bitmap.Config.ARGB_8888, true)), false);
                } else {
                    CSSquareMainActivity.this.squareMainView.setSquareBackground(drawable, false);
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 100.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_full_toolbar.addView(backgroundDrawableView);
        ChangeViewHeight(100);
        backgroundDrawableView.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
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
        CSExpandableFilterView cSExpandableFilterView = new CSExpandableFilterView(this);
        this.filterBarView = cSExpandableFilterView;
        cSExpandableFilterView.setOnExpandableFilterViewListener(new C20974());
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.filterBarView);
        ChangeViewHeight(100);
        this.filterBarView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.activitys.CSSquareMainActivity$4 */
    /* loaded from: classes2.dex */
    public class C20974 implements CSExpandableFilterView.OnExpandableFilterViewListener {
        C20974() {
        }

        @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
        public void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str) {
            CSSquareMainActivity.this.mCurrentFilterPos = i;
            CSSquareMainActivity.this.onFilterItemClick(dMWBRes.getName());
            CSSquareMainActivity.this.squareMainView.setFilter(dMWBRes, new OnFilterFinishedListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$4$9dbAj9dXZMhjy_dhvJpTwexDVPA
                @Override // com.picspool.lib.filter.listener.OnFilterFinishedListener
                public final void postFinished() {
                    C20974.this.lambda$onFilterClick$0$CSSquareMainActivity$4();
                }
            });
            CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
            cSSquareMainActivity.func_filter = "filter_" + dMWBRes.getShowText();
        }

        public /* synthetic */ void lambda$onFilterClick$0$CSSquareMainActivity$4() {
            CSSquareMainActivity.this.dismissProcessDialog();
            if (CSSquareMainActivity.this.mCurrentBlurRatio == 0.0f || !CSSquareMainActivity.this.squareMainView.getIsBlurBackground()) {
                return;
            }
            CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
            cSSquareMainActivity.setBlurBackground(cSSquareMainActivity.mCurrentBlurRatio, true);
        }

        @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.OnExpandableFilterViewListener
        public void doneView() {
            CSSquareMainActivity.this.onFilterItemClickImpl();
        }
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onTextItemClicked() {
        super.onTextItemClicked();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onStickerItemClicked2(String str) {
        super.onStickerItemClicked2(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onStickerItemClicked(String str) {
        super.onStickerItemClicked(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onFrameItemClicked() {
        super.onFrameItemClicked();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onShapeItemClicked() {
        super.onShapeItemClicked();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onShareClicked() {
        super.onShareClicked();
        Log.d("xlb_share", this.func_blur + this.func_frame + this.func_bg + this.func_sticker + this.func_filter + this.func_text + this.func_shape + this.func_editor);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity, com.winflag.libsquare.view.CSSquareView.OnGetResultBitmapListener
    public void getResultBitmapSuccess(final Bitmap bitmap) {
        showProcessDialog();
        CSSaveToSDNew.saveImage(getApplicationContext(), bitmap, "Color Splash", Bitmap.CompressFormat.PNG, new DMSaveDoneListener() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.5
            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSavingException(Exception exc) {
                CSSquareMainActivity.this.dismissProcessDialog();
            }

            @Override // com.picspool.lib.bitmap.output.save.DMSaveDoneListener
            public void onSaveDone(String str, Uri uri) {
                CSSquareMainActivity.this.dismissProcessDialog();
                if (CSSquareMainActivity.this.isSave) {
                    return;
                }
                CSSquareMainActivity.this.isSave = true;
                Intent intent = new Intent(CSSquareMainActivity.this, CSShareActivity.class);
                intent.putExtra("keyShareBmp", uri.toString());
                intent.putExtra("shareBmpWidth", bitmap.getWidth());
                intent.putExtra("keyShareBmpPath", str);
                if (!TextUtils.isEmpty(CSSquareMainActivity.this.from) && CSSquareMainActivity.this.from.equals("edit")) {
                    intent.putExtra(Constants.MessagePayloadKeys.FROM, "edit");
                } else if (!TextUtils.isEmpty(CSSquareMainActivity.this.from) && CSSquareMainActivity.this.from.equals("camera")) {
                    intent.putExtra(Constants.MessagePayloadKeys.FROM, "camera");
                }
                CSSquareMainActivity.this.startActivity(intent);
                CSSquareMainActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDestroy();
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onFrameItemClick(String str) {
        super.onFrameItemClick(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onFrameItemSave(String str) {
        super.onFrameItemSave(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onBackgroundItemClick(String str) {
        super.onBackgroundItemClick(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onBackgroundItemSave(String str) {
        super.onBackgroundItemSave(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onFilterItemClick(String str) {
        super.onFilterItemClick(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onFilterItemSave(String str) {
        super.onFilterItemSave(str);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    protected void onGlitchItemClicked() {
        if (this.il1 != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Glitch, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Glitch, true);
        if (this.il1 == null) {
            IL1 il1 = new IL1(this);
            this.il1 = il1;
            il1.setIl3(new IL1.IL234567() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.6
                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f1 */
                public void mo84f1(Bitmap bitmap) {
                    if (CSSquareMainActivity.this.squareMainView != null) {
                        CSSquareMainActivity.this.squareMainView.setGlitchFilter(bitmap);
                    }
                }

                @Override // com.photo.editor.square.splash.view.glitch.IL1.IL234567
                /* renamed from: f2 */
                public void mo83f2(GPUImageFilter gPUImageFilter) {
                    CSSquareMainActivity.this.currentGlitchGpuImageFilter = gPUImageFilter;
                    Bitmap cropBitmap = CSSquareMainActivity.this.getCropBitmap();
                    if (cropBitmap == null || cropBitmap.isRecycled()) {
                        return;
                    }
                    CSSquareMainActivity.this.il1.f12(cropBitmap.copy(Bitmap.Config.ARGB_8888, true));
                }
            });
            this.il1.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.il1);
        ChangeViewHeight(100);
        this.il1.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    protected void onAdjustItemClicked() {
        FilterView filterView = this.filterView;
        if (filterView != null && filterView.getParent() == this.layout_sub_toolbar) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Adjust, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Adjust, true);
        if (this.filterView == null) {
            FilterView il262 = new FilterView(this);
            this.filterView = il262;
            il262.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.7
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(FiltereTypes filtereTypes) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(FiltereTypes filtereTypes) {
                    if (filtereTypes != null) {
                        CSSquareMainActivity.this.squareMainView.setAdjust(filtereTypes.getColorMatrix(), filtereTypes.getAdjustImageFilter());
                    } else {
                        CSSquareMainActivity.this.squareMainView.setAdjust(null, null);
                    }
                }
            });
            this.filterView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            this.filterView.setColorShowListener(new FilterView.OnColorShowListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$XQpanhRajv4BmO4nXKWbLbQnKlU
                @Override // com.photo.editor.square.splash.view.view.IL26.OnColorShowListener
                public final void onColorViewShow() {
                    CSSquareMainActivity.this.showColorView();
                }
            });
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.filterView);
        ChangeViewHeight(100);
        this.filterView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorView() {
        this.layout_sub_full_toolbar.removeAllViews();
        ColorView colorView = new ColorView(this);
        colorView.setIl5(new CustomFrameLayoutImags.IL4567123<FiltereTypes>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.8
            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f3  reason: avoid collision after fix types in other method */
            public void mo70f3(FiltereTypes filtereTypes) {
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f2 */
            public void mo71f2() {
                CSSquareMainActivity.this.layout_sub_full_toolbar.removeAllViews();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f1 */
            public void mo72f1() {
                CSSquareMainActivity.this.layout_sub_full_toolbar.removeAllViews();
            }

            @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
            /* renamed from: f4  reason: avoid collision after fix types in other method */
            public void mo69f4(FiltereTypes filtereTypes) {
                if (filtereTypes != null) {
                    CSSquareMainActivity.this.squareMainView.setAdjust(filtereTypes.getColorMatrix());
                } else {
                    CSSquareMainActivity.this.squareMainView.setAdjust(null);
                }
            }
        });
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_full_toolbar.addView(colorView);
        ChangeViewHeight(100);
        colorView.startAnimation(translateAnimation);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void resetBottomBar() {
        super.resetBottomBar();
        this.layout_sub_full_toolbar.removeAllViews();
        if (this.filterBarView != null) {
            this.filterBarView = null;
        }
        if (this.il1 != null) {
            this.il1 = null;
        }
        if (this.cropView != null) {
            this.cropView = null;
        }
        if (this.RatiosImageCases != null) {
            this.RatiosImageCases = null;
        }
        if (this.BackgroundGroupsItemsObserver != null) {
            this.BackgroundGroupsItemsObserver = null;
        }
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onCropItemClicked() {
        super.onCropItemClicked();
        if (this.cropView != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Crop, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Crop, true);
        if (this.cropView == null && this.oriBitmap != null) {
            CSCropView cSCropView = new CSCropView(this, this.oriBitmap.copy(Bitmap.Config.ARGB_8888, true));
            this.cropView = cSCropView;
            cSCropView.setOnResultBmpListener(new C21029());
        }
        this.layout_sub_full_toolbar.addView(this.cropView);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.photo.editor.square.splash.activitys.CSSquareMainActivity$9 */
    /* loaded from: classes2.dex */
    public class C21029 implements CSCropView.OnResultBmpListener {
        C21029() {
        }

        @Override // com.photo.editor.square.splash.view.cropview.CSCropView.OnResultBmpListener
        public void onResultBmp(Bitmap bitmap) {
            CSSquareMainActivity.this.cropBitmap = bitmap;
            final IL20 il20 = new IL20();
            GPUImageFilter il4 = CSSquareMainActivity.this.currentGlitchGpuImageFilter instanceof IL123456 ? ((IL123456) CSSquareMainActivity.this.currentGlitchGpuImageFilter).il4() : null;
            Bitmap cropBitmap = CSSquareMainActivity.this.getCropBitmap();
            if (cropBitmap != null && !cropBitmap.isRecycled()) {
                il20.setData(cropBitmap.copy(Bitmap.Config.ARGB_8888, true), il4, new OnPostFilteredListener() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSSquareMainActivity$9$kdTuVk_V6ovksPSs4k6PfGIUhso
                    @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                    public final void postFiltered(Bitmap bitmap2) {
                        C21029.this.lambda$onResultBmp$0$CSSquareMainActivity$9(il20, bitmap2);
                    }
                });
            }
            il20.execute(new Void[0]);
            CSSquareMainActivity.this.resetBottomBar();
        }

        public /* synthetic */ void lambda$onResultBmp$0$CSSquareMainActivity$9(IL20 il20, Bitmap bitmap) {
            il20.shutdownLoder();
            CSSquareMainActivity.this.squareMainView.initSetPictureImageBitmap(bitmap);
            CSSquareMainActivity.this.squareMainView.setBlurBackground(0.0f, 0.5f, false);
            CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
            cSSquareMainActivity.applyRatio(cSSquareMainActivity.applyRatio);
        }

        @Override // com.photo.editor.square.splash.view.cropview.CSCropView.OnResultBmpListener
        public void onCropViewFinish() {
            CSSquareMainActivity.this.resetBottomBar();
        }
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    protected void onFillBtnClick() {
        if (this.RatiosImageCases != null) {
            resetBottomBar();
            this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Ratio, false);
            ChangeViewHeight(0);
            return;
        }
        resetBottomBar();
        this.isBottomOperationViewShow = true;
        this.mainToolBarView.setInSelectorStat(CSSquareUiMainToolBarView.SquareBottomItem.Ratio, true);
        if (this.RatiosImageCases == null) {
            RatiosImageCases ratiosImageCases = new RatiosImageCases(this);
            this.RatiosImageCases = ratiosImageCases;
            ratiosImageCases.setIl1(this.currentRatio);
            this.RatiosImageCases.setIl2(this.currentRatio);
            this.RatiosImageCases.setIl3(this.currentPos);
            this.RatiosImageCases.setIl4(this.currentPos);
            this.RatiosImageCases.setIl5(new CustomFrameLayoutImags.IL4567123<String>() { // from class: com.photo.editor.square.splash.activitys.CSSquareMainActivity.10
                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f1 */
                public void mo72f1() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f2 */
                public void mo71f2() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f3  reason: avoid collision after fix types in other method */
                public void mo70f3(String str) {
                }

                @Override // com.photo.editor.square.splash.view.view.IL29.IL4567123
                /* renamed from: f4  reason: avoid collision after fix types in other method */
                public void mo69f4(String str) {
                    CSSquareMainActivity.this.currentRatio = str;
                    CSSquareMainActivity cSSquareMainActivity = CSSquareMainActivity.this;
                    cSSquareMainActivity.currentPos = cSSquareMainActivity.RatiosImageCases.getIl3();
                    Log.e("Jie", "onApply: " + CSSquareMainActivity.this.currentRatio + "----" + CSSquareMainActivity.this.currentPos);
                    CSSquareMainActivity.this.applyRatio(str);
                }
            });
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, CSScreenInfoUtil.dip2px(this, 110.0f), 0.0f);
        translateAnimation.setDuration(this.animationDuration);
        this.layout_sub_toolbar.addView(this.RatiosImageCases);
        ChangeViewHeight(100);
        this.RatiosImageCases.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyRatio(String str) {
        this.applyRatio = str;
        if (str == null) {
            Bitmap cropBitmap = getCropBitmap();
            if (cropBitmap == null || cropBitmap.isRecycled()) {
                return;
            }
            str = cropBitmap.getWidth() + ":" + cropBitmap.getHeight();
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.squareMainView.getLayoutParams();
        layoutParams.dimensionRatio = str;
        this.squareMainView.setLayoutParams(layoutParams);
    }

    public Bitmap getCropBitmap() {
        if (this.cropBitmap == null && this.oriBitmap != null && !this.oriBitmap.isRecycled()) {
            this.cropBitmap = this.oriBitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        return this.cropBitmap;
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void onStickerItemSave(Set<String> set) {
        super.onStickerItemSave(set);
        for (String str : set) {
        }
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity
    public void chooseBlurBgFromLocal() {
        super.chooseBlurBgFromLocal();
        Intent intent = new Intent(this, CSGalleryActivity.class);
        intent.putExtra(CSGalleryActivity.MAX_SELECT_PIC_NUMBER_KEY, 1);
        intent.putExtra(CSGalleryActivity.SHOW_PEOPLE_TIPS_KEY, false);
        startActivityForResult(intent, this.REQUEST_CHOOSE_BG);
    }

    @Override // com.winflag.libsquare.CSSquareMainActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.REQUEST_CHOOSE_BG && i2 == -1) {
            Uri uri = null;
            try {
                uri = intent.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (uri == null) {
                return;
            }
            setBlurBg(uri);
        }
    }
}
