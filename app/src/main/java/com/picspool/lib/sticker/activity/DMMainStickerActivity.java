package com.picspool.lib.sticker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sticker.fragment.DMStickerGridFragment;
import com.picspool.lib.sticker.resource.adapter.DMMainPagerAdapter;
import com.picspool.viewpagerindicator.DMTabPageIndicator;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMMainStickerActivity extends FragmentActivity implements DMStickerGridFragment.OnStickerIconItemClickListener {
    DMMainPagerAdapter BMMainPagerAdapter;
    View bt_topleft;
    String imageUri;
    ViewPager mViewPager;
    DMStickerTypeOperation sto;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main_dmsticker);
        getWindow().setFlags(1024, 1024);
        DMMainPagerAdapter dMMainPagerAdapter = new DMMainPagerAdapter(getSupportFragmentManager(), this);
        this.BMMainPagerAdapter = dMMainPagerAdapter;
        dMMainPagerAdapter.setOnStickerIconItemClickListener(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        this.mViewPager = viewPager;
        viewPager.setAdapter(this.BMMainPagerAdapter);
        ((DMTabPageIndicator) findViewById(R.id.indicator)).setViewPager(this.mViewPager);
        ((FrameLayout) findViewById(R.id.vTopBack)).setOnClickListener(new BtnTopBackOnClickListener());
    }

    /* loaded from: classes3.dex */
    protected class BtnTopLeftOnClickListener implements View.OnClickListener {
        protected BtnTopLeftOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DMMainStickerActivity.this.finish();
        }
    }

    @Override // com.picspool.lib.sticker.fragment.DMStickerGridFragment.OnStickerIconItemClickListener
    public void onStickerIconItemClick(DMWBImageRes dMWBImageRes, DMStickerTypeOperation.StickerType stickerType) {
        String str;
        Intent intent = new Intent();
        if (stickerType == DMStickerTypeOperation.StickerType.EMOJI) {
            str = "1";
        } else {
            str = stickerType == DMStickerTypeOperation.StickerType.HEART ? ExifInterface.GPS_MEASUREMENT_2D : ExifInterface.GPS_MEASUREMENT_3D;
        }
        intent.putExtra("stickerResName", dMWBImageRes.getName());
        intent.putExtra("stickerType", str);
        setResult(-1, intent);
        finish();
    }

    public void clearBitmapMemory() {
        DMMainPagerAdapter dMMainPagerAdapter = this.BMMainPagerAdapter;
        if (dMMainPagerAdapter != null) {
            dMMainPagerAdapter.clearAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        clearBitmapMemory();
        super.onDestroy();
    }

    /* loaded from: classes3.dex */
    protected class BtnTopBackOnClickListener implements View.OnClickListener {
        protected BtnTopBackOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            DMMainStickerActivity.this.finish();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finish();
            return false;
        }
        return false;
    }
}
