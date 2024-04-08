package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import androidx.viewpager.widget.ViewPager;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMLibStickerBarView extends FrameLayout implements AdapterView.OnItemClickListener {
    private DMStickerGroupAdapter groupAdapter;
    private Context mContext;
    private OnStickerChooseListener mListener;
    private DMStickerPagerAdapter pagerAdapter;
    private ViewPager pagerView;
    private DMWBHorizontalListView sticker_group_list;

    /* loaded from: classes2.dex */
    public interface OnStickerChooseListener {
        void onStickerChoose(DMWBRes dMWBRes);

        void onStickerClose();
    }

    public DMLibStickerBarView(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public DMLibStickerBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init(context);
    }

    public void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_tool_sticker, (ViewGroup) this, true);
        findViewById(R.id.rl_root).setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker2.DMLibStickerBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMLibStickerBarView.this.mListener != null) {
                    DMLibStickerBarView.this.mListener.onStickerClose();
                }
            }
        });
        this.sticker_group_list = (DMWBHorizontalListView) findViewById(R.id.sticker_group_list);
        DMStickerGroupAdapter dMStickerGroupAdapter = new DMStickerGroupAdapter(context);
        this.groupAdapter = dMStickerGroupAdapter;
        this.sticker_group_list.setAdapter((ListAdapter) dMStickerGroupAdapter);
        this.sticker_group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photoart.libsticker.sticker2.DMLibStickerBarView.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMLibStickerBarView.this.groupAdapter.setSelectpos(i);
                DMLibStickerBarView.this.pagerView.setCurrentItem(i);
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.pagerView = viewPager;
        viewPager.setOffscreenPageLimit(0);
        this.pagerView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.photoart.libsticker.sticker2.DMLibStickerBarView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DMLibStickerBarView.this.groupAdapter.setSelectpos(i);
                DMLibStickerBarView.this.sticker_group_list.scrollTo((i * DMScreenInfoUtil.dip2px(DMLibStickerBarView.this.mContext, 100.0f)) + ((DMScreenInfoUtil.dip2px(DMLibStickerBarView.this.mContext, 100.0f) - DMScreenInfoUtil.screenWidth(DMLibStickerBarView.this.mContext)) / 2));
            }
        });
        DMStickerPagerAdapter dMStickerPagerAdapter = new DMStickerPagerAdapter(context);
        this.pagerView.setAdapter(dMStickerPagerAdapter);
        dMStickerPagerAdapter.setStickerOnItemClickListener(this);
    }

    public void dispose() {
        DMStickerGroupAdapter dMStickerGroupAdapter = this.groupAdapter;
        if (dMStickerGroupAdapter != null) {
            dMStickerGroupAdapter.dispose();
        }
        DMStickerPagerAdapter dMStickerPagerAdapter = this.pagerAdapter;
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
