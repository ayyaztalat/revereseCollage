package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSStickerBarConfig;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGridViewAdapter;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGroupViewAdapter;
import com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

import java.util.List;

/* loaded from: classes.dex */
public class CSXlbStickerBarView extends FrameLayout {
    public static final int STICKER_REQUESTCODE = 273;
    public static int grid_item_count = 8;
    public static int grid_line_count = 2;
    private View btn_onlinestore;
    private View btn_stickersetting;
    private String currentSelectName;
    private final String defSelectName;
    private int[] group_itemscount;
    private int[] group_pagerscount;
    private ImageView img_morestore;
    private View img_newicon;
    private CSCircleIndicator indicator;
    private List<CSStickerGroup> list_groups;
    private Activity mContext;
    private onStickerItemClickListener mListener;
    private Class mOnlineStoreActivity;
    private String requestAppName;
    private CSStickerGroupViewAdapter stickerGroupViewAdapter;
    private CSStickerManager stickerManager;
    private CSStickerPagerAdapter stickerPagerAdapter;
    private ViewPager viewPager;
    private RecyclerView viewStickerGroup;

    /* loaded from: classes.dex */
    public interface onStickerItemClickListener {
        void onClose();

        void onStickerItemClicked(DMWBRes dMWBRes, int i);
    }

    public CSXlbStickerBarView(Activity activity) {
        super(activity);
        this.defSelectName = null;
        initView(activity);
    }

    public CSXlbStickerBarView(Activity activity, String str) {
        super(activity);
        this.defSelectName = str;
        initView(activity);
    }

    private void initView(Activity activity) {
        this.mContext = activity;
        ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_xlbstikerbar, (ViewGroup) this, true);
        setOnlineStoreActivity(CSStickerBarConfig.getStickerOnlineStoreAcitvity());
        this.viewPager = (ViewPager) findViewById(R.id.viewpager_1);
        this.viewStickerGroup = (RecyclerView) findViewById(R.id.recyclerview_1);
        this.indicator = (CSCircleIndicator) findViewById(R.id.indicator);
        View findViewById = findViewById(R.id.onlinestore);
        this.btn_onlinestore = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSXlbStickerBarView.this.mOnlineStoreActivity != null) {
                    CSXlbStickerBarView.this.img_newicon.setVisibility(View.INVISIBLE);
                    DMPreferencesUtil.save(CSXlbStickerBarView.this.mContext, "xlb_stickerbar_new", "stickerbar_new_icon", "newicon_clicked");
                    Intent intent = new Intent(CSXlbStickerBarView.this.mContext, CSXlbStickerBarView.this.mOnlineStoreActivity);
                    intent.putExtra("appName", CSXlbStickerBarView.this.requestAppName);
                    CSXlbStickerBarView.this.mContext.startActivityForResult(intent, CSXlbStickerBarView.STICKER_REQUESTCODE);
                }
            }
        });
        this.btn_stickersetting = findViewById(R.id.stickersetting);
        initData(this.defSelectName);
        int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
        this.viewPager.getLayoutParams().height = screenWidth / 2;
        this.viewStickerGroup.getLayoutParams().height = screenWidth / 9;
        this.btn_onlinestore.getLayoutParams().width = screenWidth / 6;
        this.btn_stickersetting.getLayoutParams().width = 0;
        View findViewById2 = findViewById(R.id.img_newicon);
        this.img_newicon = findViewById2;
        findViewById2.setVisibility(DMPreferencesUtil.get(this.mContext, "xlb_stickerbar_new", "stickerbar_new_icon") != null ? View.INVISIBLE : View.VISIBLE);
        this.img_morestore = (ImageView) findViewById(R.id.img_morestore);
        if (DMPreferencesUtil.get(this.mContext, "stickerbar_store_icon", "store_icon_anim") == null) {
            this.img_morestore.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.stickerbar_morestore_icon_anim));
            DMPreferencesUtil.save(this.mContext, "stickerbar_store_icon", "store_icon_anim", "anim_showed");
        }
        findViewById(R.id.close).setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSXlbStickerBarView.this.mListener != null) {
                    CSXlbStickerBarView.this.mListener.onClose();
                }
            }
        });
    }

    public void initData(String str) {
        CSStickerManager cSStickerManager = new CSStickerManager(this.mContext);
        this.stickerManager = cSStickerManager;
        this.list_groups = cSStickerManager.getList_groups();
        this.group_pagerscount = this.stickerManager.getGroup_pagerscount();
        CSStickerPagerAdapter cSStickerPagerAdapter = new CSStickerPagerAdapter(this.mContext, this.list_groups);
        this.stickerPagerAdapter = cSStickerPagerAdapter;
        this.viewPager.setAdapter(cSStickerPagerAdapter);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                CSXlbStickerBarView.this.stickerPagerAdapter.getmAdapters().get(i).setOnStickerGridViewItemClikListener(new CSStickerGridViewAdapter.onStickerGridViewItemClikListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.3.1
                    @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGridViewAdapter.onStickerGridViewItemClikListener
                    public void onClick(int i2, DMWBRes dMWBRes, boolean z) {
                        if (CSXlbStickerBarView.this.mListener != null) {
                            CSXlbStickerBarView.this.mListener.onStickerItemClicked(dMWBRes, i2);
                        }
                    }
                });
                int i2 = 0;
                int i3 = 0;
                while (i2 < CSXlbStickerBarView.this.group_pagerscount.length) {
                    i3 += CSXlbStickerBarView.this.group_pagerscount[i2];
                    if (i - i3 < 0) {
                        break;
                    }
                    i2++;
                }
                CSXlbStickerBarView.this.stickerGroupViewAdapter.setSelectedPos(i2);
                CSXlbStickerBarView.this.viewStickerGroup.scrollToPosition(i2);
            }
        });
        this.stickerPagerAdapter.getmAdapters().get(0).setOnStickerGridViewItemClikListener(new CSStickerGridViewAdapter.onStickerGridViewItemClikListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.4
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGridViewAdapter.onStickerGridViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (CSXlbStickerBarView.this.mListener != null) {
                    CSXlbStickerBarView.this.mListener.onStickerItemClicked(dMWBRes, i);
                }
            }
        });
        CSStickerGroupViewAdapter cSStickerGroupViewAdapter = new CSStickerGroupViewAdapter(this.mContext, this.list_groups);
        this.stickerGroupViewAdapter = cSStickerGroupViewAdapter;
        this.viewStickerGroup.setAdapter(cSStickerGroupViewAdapter);
        this.viewStickerGroup.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.stickerGroupViewAdapter.setOnStickerGroupItemClikListener(new CSStickerGroupViewAdapter.onStickerGroupItemClikListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.5
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGroupViewAdapter.onStickerGroupItemClikListener
            public void onClick(int i) {
                if (i < CSXlbStickerBarView.this.list_groups.size()) {
                    CSXlbStickerBarView.this.clickStickerItemGroup(i);
                } else if (i == CSXlbStickerBarView.this.list_groups.size()) {
                    CSXlbStickerBarView.this.mContext.startActivity(new Intent(CSXlbStickerBarView.this.mContext, CSStickerSettingActivity.class));
                }
            }
        });
        this.indicator.setmPagerItemCount(grid_item_count);
        this.indicator.setStickerGroups(this.list_groups);
        this.indicator.setGroup_pagerscount(this.group_pagerscount);
        this.indicator.setViewPager(this.viewPager);
        if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(this.currentSelectName)) {
            str = this.currentSelectName;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (int i = 0; i < this.list_groups.size(); i++) {
            if (this.list_groups.get(i).getGroup_name().equals(str)) {
                int finalI = i;
                new Handler().postDelayed(new Runnable() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSXlbStickerBarView.6
                    @Override // java.lang.Runnable
                    public void run() {
                        CSXlbStickerBarView.this.clickStickerItemGroup(finalI);
                    }
                }, 200L);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickStickerItemGroup(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += this.stickerManager.getGroup_pagerscount()[i3];
        }
        this.currentSelectName = this.list_groups.get(i).getGroup_name();
        this.viewPager.setCurrentItem(i2, false);
    }

    public void setRequestAppName(String str) {
        this.requestAppName = str;
    }

    public void setItemClickListener(onStickerItemClickListener onstickeritemclicklistener) {
        this.mListener = onstickeritemclicklistener;
    }

    public void setOnlineStoreActivity(Class cls) {
        this.mOnlineStoreActivity = cls;
    }

    public void onResume() {
        initData(this.defSelectName);
    }
}
