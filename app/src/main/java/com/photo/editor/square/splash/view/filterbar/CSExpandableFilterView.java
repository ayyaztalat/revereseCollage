package com.photo.editor.square.splash.view.filterbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.filterbar.CSExpandableLayout;
import com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSExpandableFilterView extends FrameLayout {
    private View btn_filter_like;
    private int count;
    private CSExpandableLayout filtergroup0;
    private CSExpandableLayout filtergroup1;
    private CSExpandableLayout filtergroup2;
    private CSExpandableLayout filtergroup3;
    private CSExpandableLayout filtergroup4;
    private CSExpandableLayout filtergroup5;
    private CSExpandableLayout filtergroup6;
    private CSExpandableLayout filtergroup7;
    private CSExpandableLayout filtergroup8;
    private CSExpandableLayout filtergroup9;
    private int filtergroupmode;
    private int[] filterid;
    private int filteritemWidthDp;
    private int filternum;
    private TabLayout groupList;
    private String[] groupName;
    private DMWBHorizontalListView hrzFilter;
    private ImageView imagegroupicon;
    private ImageView imagegroupicon1;
    private ImageView imagegroupicon2;
    private ImageView imagegroupicon3;
    private ImageView imagegroupicon4;
    private ImageView imagegroupicon5;
    private ImageView imagegroupicon6;
    private ImageView imagegroupicon7;
    private ImageView imagegroupicon8;
    private ImageView imagegroupicon9;
    private ImageView imgDone;
    private Context mContext;
    protected DMWBScrollBarArrayAdapter mFilterAdapter;
    private CSLidowFilterHrzListViewAdapter mFilterAdapter2;
    private CSSquareUiLidowFilterManager mFiterManager;
    private OnExpandableFilterViewListener mListener;
    protected int mposition;
    private int scrollheader;
    private HorizontalScrollView scrollview;

    /* loaded from: classes2.dex */
    public interface OnExpandableFilterViewListener {
        void doneView();

        void onFilterClick(DMWBRes dMWBRes, int i, int i2, String str);
    }

    public CSExpandableFilterView(Context context) {
        super(context);
        this.filterid = new int[]{R.id.hrzlvFilter0, R.id.hrzlvFilter1, R.id.hrzlvFilter2, R.id.hrzlvFilter3, R.id.hrzlvFilter4, R.id.hrzlvFilter5, R.id.hrzlvFilter6, R.id.hrzlvFilter7, R.id.hrzlvFilter8, R.id.hrzlvFilter9, R.id.hrzlvFilter9};
        this.count = 0;
        this.filternum = 0;
        this.filteritemWidthDp = 80;
        this.filtergroupmode = 0;
        this.groupName = new String[]{"Favorite", "Fresh", "Winter", "Classic", "Sweet", "Lomo", "Movie", "Fade", "B&W"};
        this.mContext = context;
        init(context);
    }

    public CSExpandableFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.filterid = new int[]{R.id.hrzlvFilter0, R.id.hrzlvFilter1, R.id.hrzlvFilter2, R.id.hrzlvFilter3, R.id.hrzlvFilter4, R.id.hrzlvFilter5, R.id.hrzlvFilter6, R.id.hrzlvFilter7, R.id.hrzlvFilter8, R.id.hrzlvFilter9, R.id.hrzlvFilter9};
        this.count = 0;
        this.filternum = 0;
        this.filteritemWidthDp = 80;
        this.filtergroupmode = 0;
        this.groupName = new String[]{"Favorite", "Fresh", "Winter", "Classic", "Sweet", "Lomo", "Movie", "Fade", "B&W"};
        this.mContext = context;
        init(context);
    }

    public void setOnExpandableFilterViewListener(OnExpandableFilterViewListener onExpandableFilterViewListener) {
        this.mListener = onExpandableFilterViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_expandable_filter, (ViewGroup) this, true);
        initgroupfilter();
    }

    private void initgroupfilter() {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/like.png");
        Bitmap imageFromAssetsFile2 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/fresh.png");
        Bitmap imageFromAssetsFile3 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/season.png");
        Bitmap imageFromAssetsFile4 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/classic.png");
        Bitmap imageFromAssetsFile5 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/sweet.png");
        Bitmap imageFromAssetsFile6 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/lomo.png");
        Bitmap imageFromAssetsFile7 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/film.png");
        Bitmap imageFromAssetsFile8 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/fade.png");
        Bitmap imageFromAssetsFile9 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/bw.png");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.group_list);
        this.groupList = tabLayout;
        tabLayout.removeAllTabs();
        for (int i = 0; i < this.groupName.length; i++) {
            TabLayout.Tab newTab = this.groupList.newTab();
            View inflate = LayoutInflater.from(this.groupList.getContext()).inflate(R.layout.layout_tab_item, (ViewGroup) null);
            inflate.findViewById(R.id.tab_indicator).setBackgroundResource(R.drawable.filter_tab_selector);
            ((TextView) inflate.findViewById(R.id.group_name)).setText(this.groupName[i]);
            newTab.setCustomView(inflate);
            this.groupList.addTab(newTab);
        }
        this.groupList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                CSExpandableFilterView.this.collapseothers(0);
                int position = tab.getPosition();
                CSExpandableFilterView cSExpandableFilterView = CSExpandableFilterView.this;
                cSExpandableFilterView.scrollheader = position * DMScreenInfoUtil.dip2px(cSExpandableFilterView.mContext, CSExpandableFilterView.this.filteritemWidthDp);
                CSExpandableFilterView.this.scrollview.smoothScrollTo(CSExpandableFilterView.this.scrollheader, 0);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.img_done);
        this.imgDone = imageView;
        imageView.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSExpandableFilterView.this.mListener != null) {
                    CSExpandableFilterView.this.mListener.doneView();
                }
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.img_FilterGroup_icon);
        this.imagegroupicon = imageView2;
        imageView2.setImageBitmap(imageFromAssetsFile);
        ((TextView) findViewById(R.id.txt_name)).setText(this.groupName[0]);
        ImageView imageView3 = (ImageView) findViewById(R.id.img_FilterGroup_icon1);
        this.imagegroupicon1 = imageView3;
        imageView3.setImageBitmap(imageFromAssetsFile2);
        ((TextView) findViewById(R.id.txt_name1)).setText(this.groupName[1]);
        ImageView imageView4 = (ImageView) findViewById(R.id.img_FilterGroup_icon2);
        this.imagegroupicon2 = imageView4;
        imageView4.setImageBitmap(imageFromAssetsFile3);
        ((TextView) findViewById(R.id.txt_name2)).setText(this.groupName[2]);
        ImageView imageView5 = (ImageView) findViewById(R.id.img_FilterGroup_icon3);
        this.imagegroupicon3 = imageView5;
        imageView5.setImageBitmap(imageFromAssetsFile4);
        ((TextView) findViewById(R.id.txt_name3)).setText(this.groupName[3]);
        ImageView imageView6 = (ImageView) findViewById(R.id.img_FilterGroup_icon4);
        this.imagegroupicon4 = imageView6;
        imageView6.setImageBitmap(imageFromAssetsFile5);
        ((TextView) findViewById(R.id.txt_name4)).setText(this.groupName[4]);
        ImageView imageView7 = (ImageView) findViewById(R.id.img_FilterGroup_icon5);
        this.imagegroupicon5 = imageView7;
        imageView7.setImageBitmap(imageFromAssetsFile6);
        ((TextView) findViewById(R.id.txt_name5)).setText(this.groupName[5]);
        ImageView imageView8 = (ImageView) findViewById(R.id.img_FilterGroup_icon6);
        this.imagegroupicon6 = imageView8;
        imageView8.setImageBitmap(imageFromAssetsFile7);
        ((TextView) findViewById(R.id.txt_name6)).setText(this.groupName[6]);
        ImageView imageView9 = (ImageView) findViewById(R.id.img_FilterGroup_icon7);
        this.imagegroupicon7 = imageView9;
        imageView9.setImageBitmap(imageFromAssetsFile8);
        ((TextView) findViewById(R.id.txt_name7)).setText(this.groupName[7]);
        ImageView imageView10 = (ImageView) findViewById(R.id.img_FilterGroup_icon8);
        this.imagegroupicon8 = imageView10;
        imageView10.setImageBitmap(imageFromAssetsFile9);
        ((TextView) findViewById(R.id.txt_name8)).setText(this.groupName[8]);
        CSExpandableLayout cSExpandableLayout = (CSExpandableLayout) findViewById(R.id.filtergroup0);
        this.filtergroup0 = cSExpandableLayout;
        cSExpandableLayout.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.3
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(0);
                CSExpandableFilterView.this.initfilter2(0);
                CSExpandableFilterView.this.filternum = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout2 = (CSExpandableLayout) findViewById(R.id.filtergroup1);
        this.filtergroup1 = cSExpandableLayout2;
        cSExpandableLayout2.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.4
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(1);
                CSExpandableFilterView.this.initfilter2(1);
                CSExpandableFilterView.this.filternum = 1;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout3 = (CSExpandableLayout) findViewById(R.id.filtergroup2);
        this.filtergroup2 = cSExpandableLayout3;
        cSExpandableLayout3.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.5
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(2);
                CSExpandableFilterView.this.initfilter2(2);
                CSExpandableFilterView.this.filternum = 2;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout4 = (CSExpandableLayout) findViewById(R.id.filtergroup3);
        this.filtergroup3 = cSExpandableLayout4;
        cSExpandableLayout4.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.6
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(3);
                CSExpandableFilterView.this.filtergroup3.show();
                CSExpandableFilterView.this.initfilter2(3);
                CSExpandableFilterView.this.filternum = 3;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout5 = (CSExpandableLayout) findViewById(R.id.filtergroup4);
        this.filtergroup4 = cSExpandableLayout5;
        cSExpandableLayout5.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.7
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(4);
                CSExpandableFilterView.this.initfilter2(4);
                CSExpandableFilterView.this.filternum = 4;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout6 = (CSExpandableLayout) findViewById(R.id.filtergroup5);
        this.filtergroup5 = cSExpandableLayout6;
        cSExpandableLayout6.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.8
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(5);
                CSExpandableFilterView.this.initfilter2(5);
                CSExpandableFilterView.this.filternum = 5;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout7 = (CSExpandableLayout) findViewById(R.id.filtergroup6);
        this.filtergroup6 = cSExpandableLayout7;
        cSExpandableLayout7.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.9
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(6);
                CSExpandableFilterView.this.initfilter2(6);
                CSExpandableFilterView.this.filternum = 6;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout8 = (CSExpandableLayout) findViewById(R.id.filtergroup7);
        this.filtergroup7 = cSExpandableLayout8;
        cSExpandableLayout8.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.10
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(7);
                CSExpandableFilterView.this.initfilter2(7);
                CSExpandableFilterView.this.filternum = 7;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout9 = (CSExpandableLayout) findViewById(R.id.filtergroup8);
        this.filtergroup8 = cSExpandableLayout9;
        cSExpandableLayout9.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.11
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(8);
                CSExpandableFilterView.this.initfilter2(8);
                CSExpandableFilterView.this.filternum = 8;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout10 = (CSExpandableLayout) findViewById(R.id.filtergroup9);
        this.filtergroup9 = cSExpandableLayout10;
        cSExpandableLayout10.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.12
            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpand() {
                CSExpandableFilterView.this.collapseothers(9);
                CSExpandableFilterView.this.initfilter2(9);
                CSExpandableFilterView.this.filternum = 9;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void oncollapse() {
                CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                CSExpandableFilterView.this.count = 0;
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpandall() {
                CSExpandableFilterView.this.onFilterGroupExpanding();
            }

            @Override // com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.onExpandableLayoutListener
            public void onexpanded() {
                CSExpandableFilterView.this.onFilterGroupExpanded();
            }
        });
        this.scrollview = (HorizontalScrollView) findViewById(R.id.hrzScrollView);
        View findViewById = findViewById(R.id.btn_filter_like);
        this.btn_filter_like = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GPUFilterRes gPUFilterRes = (GPUFilterRes) CSExpandableFilterView.this.mFiterManager.getRes(CSExpandableFilterView.this.mposition);
                String name = gPUFilterRes.getName();
                String gPUFilterType = gPUFilterRes.getFilterType().toString();
                String iconFileName = gPUFilterRes.getIconFileName();
                String str = DMPreferencesUtil.get(CSExpandableFilterView.this.mContext, "FilterLike", "IsFilterLike");
                if (name == null || gPUFilterType == null) {
                    return;
                }
                if (str == null || "".equals(str)) {
                    Context context = CSExpandableFilterView.this.mContext;
                    DMPreferencesUtil.save(context, "FilterLike", "IsFilterLike", name + "," + gPUFilterType + "," + iconFileName);
                    gPUFilterRes.setIsShowLikeIcon(true);
                    view.setSelected(true);
                    CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
                    return;
                }
                if (!str.contains(name + "," + gPUFilterType + "," + iconFileName)) {
                    Context context2 = CSExpandableFilterView.this.mContext;
                    DMPreferencesUtil.save(context2, "FilterLike", "IsFilterLike", str + "," + name + "," + gPUFilterType + "," + iconFileName);
                    gPUFilterRes.setIsShowLikeIcon(true);
                    view.setSelected(true);
                    CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
                    return;
                }
                String replace = str.replace(name + "," + gPUFilterType + "," + iconFileName + ",", "");
                String replace2 = replace.replace("," + name + "," + gPUFilterType + "," + iconFileName, "");
                StringBuilder sb = new StringBuilder();
                sb.append(name);
                sb.append(",");
                sb.append(gPUFilterType);
                sb.append(",");
                sb.append(iconFileName);
                DMPreferencesUtil.save(CSExpandableFilterView.this.mContext, "FilterLike", "IsFilterLike", replace2.replace(sb.toString(), ""));
                gPUFilterRes.setIsShowLikeIcon(false);
                view.setSelected(false);
                CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
            }
        });
        if (Build.MODEL.equals("GT-I9300")) {
            this.filtergroup9.setVisibility(View.GONE);
        }
    }

    protected void onFilterGroupExpanding() {
        int dip2px = this.filtergroupmode * CSScreenInfoUtil.dip2px(this.mContext, this.filteritemWidthDp);
        this.scrollheader = dip2px;
        this.scrollview.smoothScrollTo(dip2px, 0);
    }

    protected void onFilterGroupExpanded() {
        this.mFilterAdapter2.setExpanded(true);
        this.mFilterAdapter2.notifyDataSetChanged();
    }

    protected void collapseothers(int i) {
        this.btn_filter_like.setVisibility(View.GONE);
        this.filtergroup0.hideNow();
        this.filtergroup1.hideNow();
        this.filtergroup2.hideNow();
        this.filtergroup3.hideNow();
        this.filtergroup4.hideNow();
        this.filtergroup5.hideNow();
        this.filtergroup6.hideNow();
        this.filtergroup7.hideNow();
        this.filtergroup8.hideNow();
        this.filtergroup9.hideNow();
        if (i > this.filternum) {
            this.scrollview.scrollBy(CSScreenInfoUtil.dip2px(this.mContext, (-this.filteritemWidthDp) * this.count), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initfilter2(final int i) {
        this.filtergroupmode = i;
        final String str = DMPreferencesUtil.get(this.mContext, "FilterLike", "IsFilterLike");
        this.hrzFilter = (DMWBHorizontalListView) findViewById(this.filterid[i]);
        CSSquareUiLidowFilterManager cSSquareUiLidowFilterManager = new CSSquareUiLidowFilterManager(getContext(), i, str);
        this.mFiterManager = cSSquareUiLidowFilterManager;
        this.count = cSSquareUiLidowFilterManager.getCount();
        int dip2px = CSScreenInfoUtil.dip2px(this.mContext, this.filteritemWidthDp);
        this.hrzFilter.getLayoutParams().width = dip2px * this.count;
        GPUFilterRes[] gPUFilterResArr = new GPUFilterRes[this.count];
        int i2 = 0;
        while (true) {
            int i3 = this.count;
            if (i2 >= i3 || i2 >= i3) {
                break;
            }
            gPUFilterResArr[i2] = (GPUFilterRes) this.mFiterManager.getRes(i2);
            i2++;
        }
        CSLidowFilterHrzListViewAdapter cSLidowFilterHrzListViewAdapter = this.mFilterAdapter2;
        if (cSLidowFilterHrzListViewAdapter != null) {
            cSLidowFilterHrzListViewAdapter.dispose();
        }
        CSLidowFilterHrzListViewAdapter cSLidowFilterHrzListViewAdapter2 = new CSLidowFilterHrzListViewAdapter(this.mContext, gPUFilterResArr, CSFilterSetSysColors.getColor(i));
        this.mFilterAdapter2 = cSLidowFilterHrzListViewAdapter2;
        cSLidowFilterHrzListViewAdapter2.setOnLidowFilterHrzListViewAdapterListener(new CSLidowFilterHrzListViewAdapter.OnLidowFilterHrzListViewAdapterListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.14
            @Override // com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.OnLidowFilterHrzListViewAdapterListener
            public void onLikeClick(View view) {
                GPUFilterRes gPUFilterRes = (GPUFilterRes) CSExpandableFilterView.this.mFiterManager.getRes(CSExpandableFilterView.this.mposition);
                String name = gPUFilterRes.getName();
                String gPUFilterType = gPUFilterRes.getFilterType().toString();
                String iconFileName = gPUFilterRes.getIconFileName();
                String str2 = DMPreferencesUtil.get(CSExpandableFilterView.this.mContext, "FilterLike", "IsFilterLike");
                if (name == null || gPUFilterType == null) {
                    return;
                }
                if (str2 == null || "".equals(str2)) {
                    Context context = CSExpandableFilterView.this.mContext;
                    DMPreferencesUtil.save(context, "FilterLike", "IsFilterLike", name + "," + gPUFilterType + "," + iconFileName);
                    gPUFilterRes.setIsShowLikeIcon(true);
                    view.setSelected(true);
                    CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
                    return;
                }
                if (!str2.contains(name + "," + gPUFilterType + "," + iconFileName)) {
                    Context context2 = CSExpandableFilterView.this.mContext;
                    DMPreferencesUtil.save(context2, "FilterLike", "IsFilterLike", str2 + "," + name + "," + gPUFilterType + "," + iconFileName);
                    gPUFilterRes.setIsShowLikeIcon(true);
                    view.setSelected(true);
                    CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
                    return;
                }
                String replace = str2.replace(name + "," + gPUFilterType + "," + iconFileName + ",", "");
                String replace2 = replace.replace("," + name + "," + gPUFilterType + "," + iconFileName, "");
                StringBuilder sb = new StringBuilder();
                sb.append(name);
                sb.append(",");
                sb.append(gPUFilterType);
                sb.append(",");
                sb.append(iconFileName);
                DMPreferencesUtil.save(CSExpandableFilterView.this.mContext, "FilterLike", "IsFilterLike", replace2.replace(sb.toString(), ""));
                gPUFilterRes.setIsShowLikeIcon(false);
                view.setSelected(false);
                CSExpandableFilterView.this.mFilterAdapter2.notifyDataSetChanged();
            }
        });
        this.hrzFilter.setAdapter((ListAdapter) this.mFilterAdapter2);
        this.hrzFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableFilterView.15
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j) {
                if (i4 < 0 || i4 >= CSExpandableFilterView.this.mFiterManager.getCount()) {
                    return;
                }
                CSExpandableFilterView.this.mposition = i4;
                GPUFilterRes gPUFilterRes = (GPUFilterRes) CSExpandableFilterView.this.mFiterManager.getRes(i4);
                if (CSExpandableFilterView.this.mListener != null) {
                    CSExpandableFilterView.this.mListener.onFilterClick(gPUFilterRes, i4, i, str);
                    CSExpandableFilterView.this.mFilterAdapter2.setSelectPosition(i4);
                    if (CSExpandableFilterView.this.filtergroupmode != 0 || i4 != 0) {
                        CSExpandableFilterView.this.btn_filter_like.setVisibility(View.GONE);
                        CSExpandableFilterView.this.btn_filter_like.setSelected(gPUFilterRes.getIsShowLikeIcon().booleanValue());
                        return;
                    }
                    CSExpandableFilterView.this.btn_filter_like.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void dispose() {
        DMWBHorizontalListView dMWBHorizontalListView = this.hrzFilter;
        if (dMWBHorizontalListView != null) {
            dMWBHorizontalListView.setAdapter((ListAdapter) null);
            this.hrzFilter = null;
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = this.mFilterAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        this.mFilterAdapter = null;
    }
}
