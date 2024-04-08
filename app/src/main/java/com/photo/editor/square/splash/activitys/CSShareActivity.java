package com.photo.editor.square.splash.activitys;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.view.View.ALPHA;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Key;
import androidx.lifecycle.Observer;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.photo.editor.square.splash.rate.BpRateAgent;
import com.photo.editor.square.splash.utils.CSUriParseUtil;
import java.io.File;
import java.io.IOException;

import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.output.share.DMShareTag;
import com.picspool.lib.bitmap.output.share.DMShareToFacebook;
import com.picspool.lib.bitmap.output.share.DMShareToInstagram;
import com.picspool.lib.bitmap.output.share.DMShareToNoTagApp;
import com.picspool.lib.bitmap.output.share.DMShareToTwitter;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.otherapp.DMOtherApp;
import com.picspool.lib.packages.DMOtherAppPackages;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;


/* loaded from: classes2.dex */
public class CSShareActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int SHARE_RESULT = 1;
    private ViewGroup adContainer;
    private boolean adIsShow;
    private boolean isShareVideo = false;
    private ImageView mBigBmpPreview;
    private float[] mPivot;
    private float mScaleRatio;
    private Bitmap mShareBmp;
    private String mShareBmpPath;
    private View mTouchMaskView;
    private String mVideoSavePath;
    private Uri uriShareBmp;

//    @Override // com.mhh.libraryads.libads.adsBaseAdActivity
//    protected String getAppOpenTag() {
//        return FirebaseAnalytics.Event.SHARE;
//    }
//
//    @Override // com.mhh.libraryads.libads.adsBaseAdActivity
//    protected String getBackAdTag() {
//        return FirebaseAnalytics.Event.SHARE;
//    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_share_cs);
        Intent intent = getIntent();
        this.adContainer = findViewById(R.id.ly_bottomad_container);
        if (intent != null) {
            try {
                boolean booleanExtra = intent.getBooleanExtra("key_share_video", false);
                this.isShareVideo = booleanExtra;
                if (!booleanExtra) {
                    String stringExtra = intent.getStringExtra("keyShareBmp");
                    this.mShareBmpPath = intent.getStringExtra("keyShareBmpPath");
                    if (stringExtra == null) {
                        return;
                    }
                    int intExtra = intent.getIntExtra("shareBmpWidth", 960);
                    Uri parse = Uri.parse(stringExtra);
                    this.uriShareBmp = parse;
                    Bitmap CropItemImage = DMBitmapCrop.CropItemImage(this, parse, intExtra);
                    this.mShareBmp = CropItemImage;
                    if (CropItemImage == null || CropItemImage.isRecycled()) {
                        this.mShareBmp = BitmapFactory.decodeFile(CSUriParseUtil.getImageAbsolutePath(this, Uri.parse(stringExtra)));
                    }
                } else {
                    String stringExtra2 = intent.getStringExtra("keyShareVideoPath");
                    this.mVideoSavePath = stringExtra2;
                    this.mShareBmp = getVideoThumbnail(stringExtra2);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (this.mShareBmp != null && !this.mShareBmp.isRecycled()) {
            initView();
            new BpRateAgent(false, false).showRateDialog(this);
//            AdUtils.getInstance().initNativeViewAd(false);
//            AdUtils.getInstance().getAdCloseLiveData().observe(this, new Observer() { // from class: com.photo.editor.square.splash.activitys.-$$Lambda$CSShareActivity$M6stGPuUYRflPzGJ2lp8gUhkp_Y
//                @Override // androidx.lifecycle.Observer
//                public final void onChanged(Object obj) {
//                    CSShareActivity.this.lambda$onCreate$0$CSShareActivity((Boolean) obj);
//                }
//            });

            return;
        }
        Toast.makeText(this, "Sorry,share error!", Toast.LENGTH_SHORT).show();
        setResult(1);
        finish();
    }

    public /* synthetic */ void lambda$onCreate$0$CSShareActivity(Boolean bool) {
       // showNativeViewAd();
    }

//    private void showNativeViewAd() {
//        if (AdmobConstants.isPositive(AdmobConstants.SHARE_NA_AS) && this.adContainer.getChildCount() == 0) {
//            boolean showAd = AdmobNativeViewManager.getinstance().showAd(this.adContainer, FirebaseAnalytics.Event.SHARE);
//            this.adIsShow = showAd;
//            if (showAd) {
//                return;
//            }
//            AdUtils.getInstance().getAdStateLivedata().observe(this, new Observer<Boolean>() { // from class: com.photo.editor.square.splash.activitys.CSShareActivity.1
//                @Override // androidx.lifecycle.Observer
//                public void onChanged(Boolean bool) {
//                    CSShareActivity.this.adIsShow = AdmobNativeViewManager.getinstance().showAd(CSShareActivity.this.adContainer, FirebaseAnalytics.Event.SHARE, false);
//                    if (CSShareActivity.this.adIsShow) {
//                        AdUtils.getInstance().getAdStateLivedata().removeObserver(this);
//                    }
//                }
//            });
//        }


    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_back_home).setOnClickListener(this);
        findViewById(R.id.btn_share_instagram).setOnClickListener(this);
        findViewById(R.id.btn_share_facebook).setOnClickListener(this);
        findViewById(R.id.btn_share_more).setOnClickListener(this);
        findViewById(R.id.btn_share_twitter).setOnClickListener(this);
        this.mBigBmpPreview = (ImageView) findViewById(R.id.iv_share_big_bmp_preview);
        View findViewById = findViewById(R.id.touch_event_mask_view);
        this.mTouchMaskView = findViewById;
        findViewById.setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.bmp_preview);
        imageView.setImageBitmap(this.mShareBmp);
        imageView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.txt_save_path);
        if (this.isShareVideo) {
            textView.setText("Save To:" + this.mVideoSavePath);
            return;
        }
        textView.setText("Save To:" + this.mShareBmpPath);
    }

    private void setBlurBg() {
        Bitmap bitmap = this.mShareBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        ((ImageView) findViewById(R.id.iv_blur_bg)).setImageBitmap(FastBlurFilter.blur(Bitmap.createScaledBitmap(this.mShareBmp, 300, 300, false), 50, true));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bmp_preview /* 2131296399 */:
//                if (this.isShareVideo) {
//                    File file = new File(this.mVideoSavePath);
//                    if (file.exists()) {
//                        Intent intent = new Intent("android.intent.action.VIEW");
//                        intent.setDataAndType(Uri.fromFile(file), "video/mp4");
//                        startActivity(intent);
//                        return;
//                    }
//                    return;
//                }
//                performZoomOutAnim();
//                return;
//            case R.id.btn_back /* 2131296432 */:
//                setResult(1);
//                finish();
//                return;
//            case R.id.btn_back_home /* 2131296433 */:
//                startHomeAct();
//                return;
//            case R.id.btn_share_facebook /* 2131296454 */:
//                if (this.isShareVideo) {
//                    shareVideo(DMOtherAppPackages.facebookPackage);
//                    return;
//                } else {
//                    DMShareToFacebook.shareImageFromUri(this, this.uriShareBmp);
//                    return;
//                }
//            case R.id.btn_share_instagram /* 2131296455 */:
//                if (this.isShareVideo) {
//                    shareVideo(DMOtherAppPackages.instagramPackage);
//                    return;
//                } else {
//                    DMShareToInstagram.shareImageFromUri(this, this.uriShareBmp, false);
//                    return;
//                }
//            case R.id.btn_share_more /* 2131296456 */:
//                if (this.isShareVideo) {
//                    if (this.mVideoSavePath == null) {
//                        return;
//                    }
//                    File file2 = new File(this.mVideoSavePath);
//                    if (file2.exists()) {
//                        Intent intent2 = new Intent();
//                        intent2.setAction("android.intent.action.SEND");
//                        intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
//                        intent2.setType("video/mp4");
//                        intent2.putExtra("android.intent.extra.TITLE", "sharevideo");
//                        intent2.putExtra("android.intent.extra.TEXT", DMShareTag.getDefaultTag(this));
//                        startActivity(intent2);
//                        return;
//                    }
//                    return;
//                }
//                DMShareToNoTagApp.shareImageFromUri(this, this.uriShareBmp);
//                return;
//            case R.id.btn_share_twitter /* 2131296458 */:
//                if (this.isShareVideo) {
//                    shareVideo(DMOtherAppPackages.twitterPackage);
//                    return;
//                } else {
//                    DMShareToTwitter.shareImageFromUri(this, this.uriShareBmp);
//                    return;
//                }
//            case R.id.touch_event_mask_view /* 2131297512 */:
//                performZoomInAnim();
//                return;
//            default:
//                return;
//        }
    }

    private void startHomeAct() {
        try {
            setResult(1);
            Intent intent = new Intent(this, CSMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //67108864
            intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP); //536870912
            intent.putExtra("shared", true);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void computeScaleRatioAndPivot() {
        float f = 0;
        float f2 = 0;
        float min = 0;
        int[] iArr = new int[0];
        int height = (int) (((this.mShareBmp.getHeight() * 1.0f) / this.mShareBmp.getWidth()) * this.mBigBmpPreview.getWidth());
        ViewGroup.LayoutParams layoutParams = this.mBigBmpPreview.getLayoutParams();
        layoutParams.height = height;
        this.mBigBmpPreview.setLayoutParams(layoutParams);
        this.mBigBmpPreview.setImageBitmap(this.mShareBmp);
        float screenWidth = DMScreenInfoUtil.screenWidth(this);
        float screenHeight = DMScreenInfoUtil.screenHeight(this);
        this.mScaleRatio = Math.min(screenWidth / this.mBigBmpPreview.getWidth(), screenHeight / height);
        this.mBigBmpPreview.getLocationOnScreen(new int[2]);
        this.mPivot = getScalePivot(new float[]{0.0f, 0.0f}, new float[]{Math.round((screenWidth - (f * min)) * 0.5f) - iArr[0], Math.round((screenHeight - (f2 * this.mScaleRatio)) * 0.5f) - iArr[1]}, this.mScaleRatio);
    }

    private void startAlphaAnim(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, ALPHA, 0.0f, 1.0f);
        ofFloat.setDuration(500L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.photo.editor.square.splash.activitys.CSShareActivity.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((Float) valueAnimator.getAnimatedValue()).floatValue();
            }
        });
        ofFloat.start();
    }

    private void performZoomOutAnim() {
        this.mTouchMaskView.setVisibility(View.VISIBLE);
        this.mBigBmpPreview.setVisibility(View.VISIBLE);
        if (this.mPivot == null) {
            computeScaleRatioAndPivot();
        }
        float f = this.mScaleRatio;
        float[] fArr = this.mPivot;
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, f, 1.0f, f, fArr[0], fArr[1]);
        scaleAnimation.setDuration(300L);
        scaleAnimation.setFillAfter(true);
        this.mBigBmpPreview.setAnimation(scaleAnimation);
        scaleAnimation.start();
    }

    private void performZoomInAnim() {
        this.mTouchMaskView.setVisibility(View.GONE);
        this.mBigBmpPreview.setVisibility(View.INVISIBLE);
        if (this.mPivot == null) {
            computeScaleRatioAndPivot();
        }
        float f = this.mScaleRatio;
        float[] fArr = this.mPivot;
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, 1.0f, f, 1.0f, fArr[0], fArr[1]);
        scaleAnimation.setDuration(300L);
        this.mBigBmpPreview.setAnimation(scaleAnimation);
        scaleAnimation.start();
    }

    private float[] getScalePivot(float[] fArr, float[] fArr2, float f) {
        float[] fArr3 = new float[2];
        if (f != 1.0f) {
            float f2 = 1.0f - f;
            fArr3[0] = (fArr2[0] - (fArr[0] * f)) / f2;
            fArr3[1] = (fArr2[1] - (fArr[1] * f)) / f2;
        }
        return fArr3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.mhh.libraryads.libads.adsBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        ImageView imageView;
        if (i == 4 && (imageView = this.mBigBmpPreview) != null && imageView.getVisibility() == View.VISIBLE) {
            performZoomInAnim();
            return true;
        }
        if (i == 4) {
            setResult(1);
        }
        return super.onKeyDown(i, keyEvent);
    }

    public Bitmap getVideoThumbnail(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                try {
                    try {
                        mediaMetadataRetriever.setDataSource(str);
                        Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
                        try {
                            mediaMetadataRetriever.release();
                            return frameAtTime;
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            return frameAtTime;
                        }
                    } catch (Exception th) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (RuntimeException e2) {
                            e2.printStackTrace();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        th.printStackTrace();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    mediaMetadataRetriever.release();
                    return null;
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                mediaMetadataRetriever.release();
                return null;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
            return null;
        }
        return null;
    }

    public void shareVideo(String str) {
        if (str == null || str.length() == 0 || this.mVideoSavePath == null) {
            return;
        }
        File file = new File(this.mVideoSavePath);
        if (file.exists()) {
            if (!DMOtherApp.isInstalled(this, str).booleanValue()) {
                Toast.makeText(this, getResources().getString(R.string.warning_no_installed), Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            intent.setPackage(str);
            intent.setType("video/mp4");
            intent.putExtra("android.intent.extra.TITLE", "sharevideo");
            intent.putExtra("android.intent.extra.TEXT", DMShareTag.getDefaultTag(this));
            startActivityForResult(intent, 10001);
        }
    }
}
