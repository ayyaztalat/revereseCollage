package com.photoart.libsticker.sticker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import androidx.viewpager.widget.ViewPager;

import com.photoart.libsticker.sticker2.DMStickerGroupAdapter;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import com.photoart.libsticker.sticker2.DMStickerPagerAdapter;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickerBarView extends FrameLayout implements AdapterView.OnItemClickListener {
    private DMStickerGroupAdapter BMStickerGroupAdapter;
    private DMStickerPagerAdapter BMStickerPagerAdapter;
    private DMWBHorizontalListView group_horizontalListView;
    private Context mContext;
    private OnStickerChooseListener mListener;
    private ViewPager pager;

    /* loaded from: classes2.dex */
    public interface OnStickerChooseListener {
        void onStickerChoose(DMWBRes dMWBRes);

        void onStickerClose();
    }

    public DMStickerBarView(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public DMStickerBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init(context);
    }

    public void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_tool_sticker2, (ViewGroup) this, true);
        findViewById(R.id.layout_close).setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerBarView.this.mListener != null) {
                    DMStickerBarView.this.mListener.onStickerClose();
                }
            }
        });
        this.group_horizontalListView = (DMWBHorizontalListView) findViewById(R.id.group_horizontalListView);
        DMStickerGroupAdapter dMStickerGroupAdapter = new DMStickerGroupAdapter(context);
        this.BMStickerGroupAdapter = dMStickerGroupAdapter;
        this.group_horizontalListView.setAdapter((ListAdapter) dMStickerGroupAdapter);
        this.group_horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerBarView.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMStickerBarView.this.BMStickerGroupAdapter.setSelectpos(i);
                DMStickerBarView.this.pager.setCurrentItem(i);
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        this.pager = viewPager;
        viewPager.setOffscreenPageLimit(0);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.photoart.libsticker.sticker.DMStickerBarView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DMStickerBarView.this.BMStickerGroupAdapter.setSelectpos(i);
                DMStickerBarView.this.group_horizontalListView.scrollTo((i * DMScreenInfoUtil.dip2px(DMStickerBarView.this.mContext, 90.0f)) + ((DMScreenInfoUtil.dip2px(DMStickerBarView.this.mContext, 90.0f) - DMScreenInfoUtil.screenWidth(DMStickerBarView.this.mContext)) / 2));
            }
        });
        DMStickerPagerAdapter dMStickerPagerAdapter = new DMStickerPagerAdapter(context);
        this.pager.setAdapter(dMStickerPagerAdapter);
        dMStickerPagerAdapter.setStickerOnItemClickListener(this);
    }

    public void dispose() {
        DMStickerGroupAdapter dMStickerGroupAdapter = this.BMStickerGroupAdapter;
        if (dMStickerGroupAdapter != null) {
            dMStickerGroupAdapter.dispose();
        }
        DMStickerPagerAdapter dMStickerPagerAdapter = this.BMStickerPagerAdapter;
        if (dMStickerPagerAdapter != null) {
            dMStickerPagerAdapter.dispose();
        }
    }

    public void setOnStickerChooseListener(OnStickerChooseListener onStickerChooseListener) {
        this.mListener = onStickerChooseListener;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        DMStickerModeManager.StickerMode stickerMode = (DMStickerModeManager.StickerMode) adapterView.getTag();
        OnStickerChooseListener onStickerChooseListener = this.mListener;
        if (onStickerChooseListener != null) {
            onStickerChooseListener.onStickerChoose(DMStickerModeManager.getStickerImageManager(this.mContext, stickerMode).getRes(i));
        }
    }
}
