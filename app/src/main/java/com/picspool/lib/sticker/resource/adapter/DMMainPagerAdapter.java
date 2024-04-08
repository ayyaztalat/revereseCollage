package com.picspool.lib.sticker.resource.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sticker.fragment.DMStickerGridFragment;

/* loaded from: classes3.dex */
public class DMMainPagerAdapter extends FragmentStatePagerAdapter {

    /* renamed from: fr */
    DMStickerGridFragment f1985fr;
    DMStickerGridFragment.OnStickerIconItemClickListener listener;
    private Context mContext;
    DMStickerTypeOperation sto;

    public DMMainPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    public DMMainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setOnStickerIconItemClickListener(DMStickerGridFragment.OnStickerIconItemClickListener onStickerIconItemClickListener) {
        this.listener = onStickerIconItemClickListener;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        if (this.sto == null) {
            this.sto = new DMStickerTypeOperation(this.mContext);
        }
        DMStickerGridFragment dMStickerGridFragment = new DMStickerGridFragment(this.sto.getStickerTypeByIndex(i));
        this.f1985fr = dMStickerGridFragment;
        dMStickerGridFragment.setOnStickerIconItemClickListener(this.listener);
        return this.f1985fr;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        if (this.sto == null) {
            this.sto = new DMStickerTypeOperation(this.mContext);
        }
        return this.sto.getStickerTypeCount();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        if (this.sto == null) {
            this.sto = new DMStickerTypeOperation(this.mContext);
        }
        return this.sto.getStickerPageTitleByIndex(i);
    }

    public void clearAll() {
        DMStickerGridFragment dMStickerGridFragment = this.f1985fr;
        if (dMStickerGridFragment != null) {
            dMStickerGridFragment.clearBitmapMemory();
        }
    }
}
