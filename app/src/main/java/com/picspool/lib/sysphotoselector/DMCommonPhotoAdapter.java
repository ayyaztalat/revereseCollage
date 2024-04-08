package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.sysphotoselector.DMCommonPhotoGridFragment;

/* loaded from: classes3.dex */
public class DMCommonPhotoAdapter extends FragmentStatePagerAdapter {
    private int isMultiSelector;
    private List<List<DMImageMediaItem>> mBudgetList;
    private Context mContext;
    private OnCommonPhotoAdapterItemSelectedListener mListener;

    /* loaded from: classes3.dex */
    public interface OnCommonPhotoAdapterItemSelectedListener {
        void commonPhotoAdapterItemSelected(DMImageMediaItem dMImageMediaItem, View view);

        void exceedGivenMaxPhotoCount();
    }

    public void clearAll() {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return -2;
    }

    public DMCommonPhotoAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.isMultiSelector = 1;
        this.mContext = context;
    }

    public void setOnCommonPhotoAdapterItemSelectedListener(OnCommonPhotoAdapterItemSelectedListener onCommonPhotoAdapterItemSelectedListener) {
        this.mListener = onCommonPhotoAdapterItemSelectedListener;
    }

    public void setBudgetList(List<List<DMImageMediaItem>> list) {
        this.mBudgetList = list;
        notifyDataSetChanged();
    }

    public DMCommonPhotoAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.isMultiSelector = 1;
    }

    public void setMultiSelector(int i) {
        this.isMultiSelector = i;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        return DMCommonPhotoGridFragment.newInstance("");
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        DMCommonPhotoGridFragment dMCommonPhotoGridFragment = (DMCommonPhotoGridFragment) super.instantiateItem(viewGroup, i);
        dMCommonPhotoGridFragment.setContext(this.mContext);
        dMCommonPhotoGridFragment.setMultiSelector(this.isMultiSelector);
        dMCommonPhotoGridFragment.setDisplayData(this.mBudgetList.get(i), false);
        dMCommonPhotoGridFragment.setOnCommonPhotoItemSelectedListener(new DMCommonPhotoGridFragment.OnCommonPhotoItemSelectedListener() { // from class: com.picspool.lib.sysphotoselector.DMCommonPhotoAdapter.1
            @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoGridFragment.OnCommonPhotoItemSelectedListener
            public void commonPhotoItemSelected(DMImageMediaItem dMImageMediaItem, View view) {
                if (DMCommonPhotoAdapter.this.mListener != null) {
                    DMCommonPhotoAdapter.this.mListener.commonPhotoAdapterItemSelected(dMImageMediaItem, view);
                }
            }

            @Override // com.picspool.lib.sysphotoselector.DMCommonPhotoGridFragment.OnCommonPhotoItemSelectedListener
            public void exceedGivenMaxPhotoCount() {
                if (DMCommonPhotoAdapter.this.mListener != null) {
                    DMCommonPhotoAdapter.this.mListener.exceedGivenMaxPhotoCount();
                }
            }
        });
        return dMCommonPhotoGridFragment;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<List<DMImageMediaItem>> list = this.mBudgetList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        List<List<DMImageMediaItem>> list = this.mBudgetList;
        return (list == null || list.size() <= i || this.mBudgetList.get(i) == null || this.mBudgetList.get(i).get(0) == null || this.mBudgetList.get(i).get(0).getBuketDisplayName() == null) ? "" : this.mBudgetList.get(i).get(0).getBuketDisplayName().toUpperCase();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        super.destroyItem(viewGroup, i, obj);
    }
}
