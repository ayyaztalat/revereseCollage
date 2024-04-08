package com.baiwang.libuiinstalens.filter;

import android.content.Context;
import android.content.res.Resources;
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

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUILidowFilterView extends FrameLayout {
    private static final int ALL = 1;
    private static final int BACK = 3;
    private static final int FRONT = 2;
    protected DMWBScrollBarArrayAdapter mFilterAdapter;
    protected DMWBScrollBarArrayAdapter mFilterGroupAdapter;
    protected int mposition;
    private int adjustmode;
    private View btn_cancel;
    private View btn_done;
    private View btn_filter_like;
    private int count;
    private CSExpandableLayout[] expandablelayout;
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
    private int[] filtergroupid;
    private int filtergroupmode;
    private int[] filterid;
    private int filteritemWidthDp;
    private int filternum;
    private DMWBHorizontalListView groupFilter;
    private DMWBHorizontalListView hrzFilter;
    private ImageView imageFilterBack;
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
    private Context mContext;
    private CSLidowFilterHrzListViewAdapter mFilterAdapter2;
    private CSSquareUiLidowFilterManager mFiterManager;
    private onSquareUiFilterToolBarViewListener mListener;
    private int mPos;
    private int scrollheader;
    private HorizontalScrollView scrollview;

    public CSSquareUILidowFilterView(Context context, int i) {
        super(context);
        init(context);
        adjustmode = 1;
        filterid = new int[]{R.id.hrzlvFilter0, R.id.hrzlvFilter1, R.id.hrzlvFilter2, R.id.hrzlvFilter3, R.id.hrzlvFilter4, R.id.hrzlvFilter5, R.id.hrzlvFilter6, R.id.hrzlvFilter7, R.id.hrzlvFilter8, R.id.hrzlvFilter9};
        expandablelayout = new CSExpandableLayout[]{filtergroup0, filtergroup1, filtergroup2, filtergroup3, filtergroup4, filtergroup5, filtergroup6, filtergroup7, filtergroup8, filtergroup9};
        filtergroupid = new int[]{R.id.filtergroup0, R.id.filtergroup1, R.id.filtergroup2, R.id.filtergroup3, R.id.filtergroup4, R.id.filtergroup5, R.id.filtergroup6, R.id.filtergroup7, R.id.filtergroup8, R.id.filtergroup9};
        count = 0;
        filternum = 0;
        filteritemWidthDp = 65;
        filtergroupmode = 0;
        mContext = context;
        mPos = i;

    }

    public CSSquareUILidowFilterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        init(context);
        adjustmode = 1;
        filterid = new int[]{R.id.hrzlvFilter0, R.id.hrzlvFilter1, R.id.hrzlvFilter2, R.id.hrzlvFilter3, R.id.hrzlvFilter4, R.id.hrzlvFilter5, R.id.hrzlvFilter6, R.id.hrzlvFilter7, R.id.hrzlvFilter8, R.id.hrzlvFilter9};
        expandablelayout = new CSExpandableLayout[]{filtergroup0, filtergroup1, filtergroup2, filtergroup3, filtergroup4, filtergroup5, filtergroup6, filtergroup7, filtergroup8, filtergroup9};
        filtergroupid = new int[]{R.id.filtergroup0, R.id.filtergroup1, R.id.filtergroup2, R.id.filtergroup3, R.id.filtergroup4, R.id.filtergroup5, R.id.filtergroup6, R.id.filtergroup7, R.id.filtergroup8, R.id.filtergroup9};
        count = 0;
        filternum = 0;
        filteritemWidthDp = 65;
        filtergroupmode = 0;
        mContext = context;
        mPos = i;

    }

    public void setOnSquareUiFilterToolBarViewListener(onSquareUiFilterToolBarViewListener onsquareuifiltertoolbarviewlistener) {
        mListener = onsquareuifiltertoolbarviewlistener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.photolab_ui_filter_view, (ViewGroup) this, true);
        View findViewById = findViewById(R.id.btn_back);
        btn_cancel = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.filter.CSSquareUILidowFilterView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onCancelClicked();
                }
            }
        });
        View findViewById2 = findViewById(R.id.btn_done);
        btn_done = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.baiwang.libuiinstalens.filter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDoneClicked();
                }
            }
        });
        initgroupfilter();
    }

    private void initgroupfilter() {
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/like.png");
        Bitmap imageFromAssetsFile2 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/season.png");
        Bitmap imageFromAssetsFile3 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/classic.png");
        Bitmap imageFromAssetsFile4 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/sweet.png");
        Bitmap imageFromAssetsFile5 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/lomo.png");
        Bitmap imageFromAssetsFile6 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/film.png");
        Bitmap imageFromAssetsFile7 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/fade.png");
        Bitmap imageFromAssetsFile8 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/bw.png");
        Bitmap imageFromAssetsFile9 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/vintage.png");
        Bitmap imageFromAssetsFile10 = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/group/halo.png");
        imagegroupicon = (ImageView) findViewById(R.id.img_FilterGroup_icon);
        imagegroupicon.setImageBitmap(imageFromAssetsFile);

        imagegroupicon1 = (ImageView) findViewById(R.id.img_FilterGroup_icon1);
        imagegroupicon1.setImageBitmap(imageFromAssetsFile2);

        imagegroupicon2 = (ImageView) findViewById(R.id.img_FilterGroup_icon2);
        imagegroupicon2.setImageBitmap(imageFromAssetsFile3);

        imagegroupicon3 = (ImageView) findViewById(R.id.img_FilterGroup_icon3);
        imagegroupicon3.setImageBitmap(imageFromAssetsFile4);

        imagegroupicon4 = (ImageView) findViewById(R.id.img_FilterGroup_icon4);
        imagegroupicon4.setImageBitmap(imageFromAssetsFile5);

        imagegroupicon4 = (ImageView) findViewById(R.id.img_FilterGroup_icon5);
        imagegroupicon4.setImageBitmap(imageFromAssetsFile6);

        imagegroupicon6 = (ImageView) findViewById(R.id.img_FilterGroup_icon6);
        imagegroupicon6.setImageBitmap(imageFromAssetsFile7);

        imagegroupicon7 = (ImageView) findViewById(R.id.img_FilterGroup_icon7);
        imagegroupicon7.setImageBitmap(imageFromAssetsFile8);

        imagegroupicon8 = (ImageView) findViewById(R.id.img_FilterGroup_icon8);
        imagegroupicon8.setImageBitmap(imageFromAssetsFile9);

        imagegroupicon9 = (ImageView) findViewById(R.id.img_FilterGroup_icon9);
        imagegroupicon9.setImageBitmap(imageFromAssetsFile10);

        filtergroup0 = (CSExpandableLayout) findViewById(R.id.filtergroup0);
        filtergroup0.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() {
            @Override
            public void onexpand() {
                collapseothers(0);
                initfilter(0);
                filternum = 0;
            }

            @Override
            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override
            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override
            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout2 = (CSExpandableLayout) findViewById(R.id.filtergroup1);
        filtergroup1 = cSExpandableLayout2;
        cSExpandableLayout2.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.4
            @Override
            public void onexpand() {
                collapseothers(1);
                initfilter(1);
                filternum = 1;
            }

            @Override
            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override
            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout3 = (CSExpandableLayout) findViewById(R.id.filtergroup2);
        filtergroup2 = cSExpandableLayout3;
        cSExpandableLayout3.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.5
            @Override

            public void onexpand() {
                collapseothers(2);
                initfilter(2);
                filternum = 2;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout4 = (CSExpandableLayout) findViewById(R.id.filtergroup3);
        filtergroup3 = cSExpandableLayout4;
        cSExpandableLayout4.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.6
            @Override

            public void onexpand() {
                collapseothers(3);
                filtergroup3.show();
                initfilter(3);
                filternum = 3;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout5 = (CSExpandableLayout) findViewById(R.id.filtergroup4);
        filtergroup4 = cSExpandableLayout5;
        cSExpandableLayout5.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.7
            @Override

            public void onexpand() {
                collapseothers(4);
                initfilter(4);
                filternum = 4;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout6 = (CSExpandableLayout) findViewById(R.id.filtergroup5);
        filtergroup5 = cSExpandableLayout6;
        cSExpandableLayout6.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.8
            @Override

            public void onexpand() {
                collapseothers(5);
                initfilter(5);
                filternum = 5;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout7 = (CSExpandableLayout) findViewById(R.id.filtergroup6);
        filtergroup6 = cSExpandableLayout7;
        cSExpandableLayout7.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.9
            @Override

            public void onexpand() {
                collapseothers(6);
                initfilter(6);
                filternum = 6;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout8 = (CSExpandableLayout) findViewById(R.id.filtergroup7);
        filtergroup7 = cSExpandableLayout8;
        cSExpandableLayout8.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.10
            @Override

            public void onexpand() {
                collapseothers(7);
                initfilter(7);
                filternum = 7;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout9 = (CSExpandableLayout) findViewById(R.id.filtergroup8);
        filtergroup8 = cSExpandableLayout9;
        cSExpandableLayout9.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.11
            @Override

            public void onexpand() {
                collapseothers(8);
                initfilter(8);
                filternum = 8;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        CSExpandableLayout cSExpandableLayout10 = (CSExpandableLayout) findViewById(R.id.filtergroup9);
        filtergroup9 = cSExpandableLayout10;
        cSExpandableLayout10.setonExpandableLayoutListener(new CSExpandableLayout.onExpandableLayoutListener() { // from class: com.baiwang.libuiinstalens.filter.12
            @Override

            public void onexpand() {
                collapseothers(9);
                initfilter(9);
                filternum = 9;
            }

            @Override

            public void oncollapse() {
                btn_filter_like.setVisibility(View.INVISIBLE);
                count = 0;
            }

            @Override

            public void onexpandall() {
                onFilterGroupExpanding();
            }

            @Override

            public void onexpanded() {
                onFilterGroupExpanded();
            }
        });
        scrollview = (HorizontalScrollView) findViewById(R.id.hrzScrollView);
        btn_filter_like  = findViewById(R.id.btn_filter_like);
        btn_filter_like.setOnClickListener(view -> {
            GPUFilterRes gPUFilterRes = (GPUFilterRes) mFiterManager.getRes(mposition);
            String name = gPUFilterRes.getName();
            String gPUFilterType = gPUFilterRes.getFilterType().toString();
            String iconFileName = gPUFilterRes.getIconFileName();
            String str = DMPreferencesUtil.get(mContext, "FilterLike", "IsFilterLike");
            if (name == null) {
                return;
            }
            if (str == null || str.isEmpty()) {
                Context context = mContext;
                DMPreferencesUtil.save(context, "FilterLike", "IsFilterLike", name + "," + gPUFilterType + "," + iconFileName);
                gPUFilterRes.setIsShowLikeIcon(true);
                view.setSelected(true);
                mFilterAdapter2.notifyDataSetChanged();
                return;
            }
            if (!str.contains(name + "," + gPUFilterType + "," + iconFileName)) {
                Context context2 = mContext;
                DMPreferencesUtil.save(context2, "FilterLike", "IsFilterLike", str + "," + name + "," + gPUFilterType + "," + iconFileName);
                gPUFilterRes.setIsShowLikeIcon(true);
                view.setSelected(true);
                mFilterAdapter2.notifyDataSetChanged();
                return;
            }
            String replace = str.replace(name + "," + gPUFilterType + "," + iconFileName + ",", "");
            String replace2 = replace.replace("," + name + "," + gPUFilterType + "," + iconFileName, "");
            String sb = name +
                    "," +
                    gPUFilterType +
                    "," +
                    iconFileName;
            DMPreferencesUtil.save(mContext, "FilterLike", "IsFilterLike", replace2.replace(sb, ""));
            gPUFilterRes.setIsShowLikeIcon(false);
            view.setSelected(false);
            mFilterAdapter2.notifyDataSetChanged();
        });
        if (Build.MODEL.equals("GT-I9300")) {
            filtergroup9.setVisibility(View.GONE);
        }
    }

    protected void onFilterGroupExpanding() {
        int dip2px = filtergroupmode * DMScreenInfoUtil.dip2px(mContext, 65.0f);
        scrollheader = dip2px;
        scrollview.smoothScrollTo(dip2px, 0);
    }

    protected void onFilterGroupExpanded() {
        mFilterAdapter2.setExpanded(true);
        mFilterAdapter2.notifyDataSetChanged();
    }

    protected void collapseothers(int i) {
        btn_filter_like.setVisibility(View.INVISIBLE);
        filtergroup0.hideNow();
        filtergroup1.hideNow();
        filtergroup2.hideNow();
        filtergroup3.hideNow();
        filtergroup4.hideNow();
        filtergroup5.hideNow();
        filtergroup6.hideNow();
        filtergroup7.hideNow();
        filtergroup8.hideNow();
        filtergroup9.hideNow();
        if (i > filternum) {
            scrollview.scrollBy(DMScreenInfoUtil.dip2px(mContext, count * (-65)), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initfilter(int i) {
        filtergroupmode = i;
        String str = DMPreferencesUtil.get(mContext, "FilterLike", "IsFilterLike");
        hrzFilter = (DMWBHorizontalListView) findViewById(filterid[i]);
        mFiterManager =new CSSquareUiLidowFilterManager(getContext(), i, str);
        count = mFiterManager.getCount();
        int dip2px = DMScreenInfoUtil.dip2px(mContext, filteritemWidthDp);
        hrzFilter.getLayoutParams().width = dip2px * count;
        GPUFilterRes[] gPUFilterResArr = new GPUFilterRes[count];
        Resources resources = getResources();
        DMBitmapUtil.getImageFromAssetsFile(resources, "filter/image/mode" + i + ".png");
        int i2 = 0;
        while (true) {
            int i3 = count;
            if (i2 >= i3 || i2 >= i3) {
                break;
            }
            gPUFilterResArr[i2] = (GPUFilterRes) mFiterManager.getRes(i2);
            i2++;
        }
        CSLidowFilterHrzListViewAdapter cSLidowFilterHrzListViewAdapter = mFilterAdapter2;
        if (cSLidowFilterHrzListViewAdapter != null) {
            cSLidowFilterHrzListViewAdapter.dispose();
        }
        mFilterAdapter2 = new CSLidowFilterHrzListViewAdapter(mContext, gPUFilterResArr);
        mFilterAdapter2.setmTextViewSeletedBackColor(getResources().getColor(R.color.xlb_stickerbar_main_color_green));
        hrzFilter.setAdapter((ListAdapter) mFilterAdapter2);
        hrzFilter.setOnItemClickListener((adapterView, view, i4, j) -> {
            mposition = i4;
            GPUFilterRes gPUFilterRes = (GPUFilterRes) mFiterManager.getRes(i4);
            if (mListener != null) {
                mFilterAdapter2.setSelectPosition(i4);
                if (filtergroupmode != 0 || i4 != 0) {
                    btn_filter_like.setVisibility(View.VISIBLE);
                    btn_filter_like.setSelected(gPUFilterRes.getIsShowLikeIcon());
                } else {
                    btn_filter_like.setVisibility(View.INVISIBLE);
                }
                mListener.onAllFilterClick(gPUFilterRes, i4);
            }
        });
    }

    public void dispose() {
        DMWBHorizontalListView dMWBHorizontalListView = hrzFilter;
        if (dMWBHorizontalListView != null) {
            dMWBHorizontalListView.setAdapter((ListAdapter) null);
            hrzFilter = null;
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = mFilterAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        mFilterAdapter = null;
    }

    /* loaded from: classes.dex */
    public interface onSquareUiFilterToolBarViewListener {
        void onAllFilterClick(GPUFilterRes gPUFilterRes, int i);

        void onCancelClicked();

        void onDoneClicked();
    }
}
