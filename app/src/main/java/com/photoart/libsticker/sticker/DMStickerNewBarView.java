package com.photoart.libsticker.sticker;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.photoart.libsticker.sticker2.DMStickerGroupAdapterNew;
import com.photoart.libsticker.sticker2.DMStickerGroupManager;
import com.photoart.libsticker.sticker2.DMStickerImageRes;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import com.photoart.libsticker.sticker2.DMStikcerListAdapter;
import com.photoart.libsticker.sticker3.DMLibStickerManager;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickerNewBarView extends FrameLayout {
    int btn_leftMargin_end;
    int btn_leftMargin_start;
    private int curStickerCount;
    private DMStickerImageRes curgroupItem;
    private String curgroupItemName;
    private String lastgroupItemName;
    View leftBtnView;
    View leftItemView;
    private Context mContext;
    private OnStickerNewChooseListener mListener;
    private DMStickerModeManager.StickerMode mStickerMode;
    private int maxStickerCount;
    private List<DMSelectStickerRes> selectResList;
    private DMStickerGroupAdapterNew stickerGroupAdapter;
    private GridView stickerGroupGridView;
    private DMStikcerListAdapter stickerItemAdapter;
    private GridView stickerItemGridView;
    TextView topLabelTextView;
    String topLableFormat;
    String topLableString;
    int view_leftMargin_end;
    int view_leftMargin_start;

    /* loaded from: classes2.dex */
    public interface OnStickerNewChooseListener {
        void onStickerChoose(List<DMWBRes> list);

        void onStickerClose();
    }

    public DMStickerNewBarView(Context context) {
        super(context);
        this.maxStickerCount = 8;
        this.curStickerCount = 0;
        this.selectResList = new ArrayList();
        String string = getResources().getString(R.string.sticker_top_label_text);
        this.topLableFormat = string;
        this.topLableString = String.format(string, 0, Integer.valueOf(this.maxStickerCount));
        this.lastgroupItemName = "";
        this.curgroupItemName = "";
        this.mContext = context;
        init(context);
        initGroup();
        initItem();
    }

    public DMStickerNewBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxStickerCount = 8;
        this.curStickerCount = 0;
        this.selectResList = new ArrayList();
        String string = getResources().getString(R.string.sticker_top_label_text);
        this.topLableFormat = string;
        this.topLableString = String.format(string, 0, Integer.valueOf(this.maxStickerCount));
        this.lastgroupItemName = "";
        this.curgroupItemName = "";
        this.mContext = context;
        init(context);
        initGroup();
        initItem();
    }

    public void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_tool_sticker_new, (ViewGroup) this, true);
        this.mContext = context;
        TextView textView = (TextView) findViewById(R.id.sticker_top_label);
        this.topLabelTextView = textView;
        textView.setText(this.topLableString);
        View findViewById = findViewById(R.id.layout_close);
        this.leftBtnView = findViewById(R.id.lyLeftBtn);
        this.leftItemView = findViewById(R.id.lyLeftView);
        findViewById(R.id.layout_ok).setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMStickerNewBarView.this.okClick();
            }
        });
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMStickerNewBarView.this.mListener != null) {
                    DMStickerNewBarView.this.mListener.onStickerClose();
                }
            }
        });
        this.leftBtnView.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMStickerNewBarView.this.isShowLeftMenu(true);
            }
        });
        this.leftItemView.setOnClickListener(new OnClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMStickerNewBarView.this.isShowLeftMenu(false);
            }
        });
    }

    private void initGroup() {
        this.stickerGroupGridView = (GridView) findViewById(R.id.group_grid);
        DMStickerGroupAdapterNew dMStickerGroupAdapterNew = new DMStickerGroupAdapterNew(this.mContext);
        this.stickerGroupAdapter = dMStickerGroupAdapterNew;
        dMStickerGroupAdapterNew.setSelectpos(0);
        this.stickerGroupGridView.setAdapter((ListAdapter) this.stickerGroupAdapter);
        this.stickerGroupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.5
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                final DMStickerImageRes dMStickerImageRes = (DMStickerImageRes) DMStickerNewBarView.this.stickerGroupAdapter.getItem(i);
                if (dMStickerImageRes.getIconType() == DMWBRes.LocationType.ONLINE) {
                    DMStickerGroupManager singletManager = DMStickerGroupManager.getSingletManager(DMStickerNewBarView.this.mContext.getApplicationContext());
                    if (dMStickerImageRes.getIslock().booleanValue() && DMPreferencesUtil.get(DMStickerNewBarView.this.mContext, DMLibStickerManager.CONFIG, dMStickerImageRes.getName()) == null && singletManager.isShowLockView((Activity) DMStickerNewBarView.this.mContext)) {
                        singletManager.setOnStickerGroupManagerListener(new DMStickerGroupManager.OnStickerGroupManagerListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.5.1
                            @Override // com.photoart.libsticker.sticker2.DMStickerGroupManager.OnStickerGroupManagerListener
                            public void adClick() {
                                DMPreferencesUtil.save(DMStickerNewBarView.this.mContext, DMLibStickerManager.CONFIG, dMStickerImageRes.getName(), dMStickerImageRes.getName());
                                DMStickerNewBarView.this.mStickerMode = DMStickerModeManager.StickerMode.ONLINE;
                                DMStickerNewBarView.this.setStickerMode(dMStickerImageRes);
                                DMStickerNewBarView.this.stickerItemGridView.setAdapter((ListAdapter) DMStickerNewBarView.this.stickerItemAdapter);
                                DMStickerNewBarView.this.isShowLeftMenu(false);
                                DMStickerNewBarView.this.stickerGroupAdapter.setImageViewLockGone(i);
                            }
                        });
                        return;
                    }
                    DMStickerNewBarView.this.mStickerMode = DMStickerModeManager.StickerMode.ONLINE;
                    DMStickerNewBarView.this.setStickerMode(dMStickerImageRes);
                    DMStickerNewBarView.this.stickerItemGridView.setAdapter((ListAdapter) DMStickerNewBarView.this.stickerItemAdapter);
                    DMStickerNewBarView.this.isShowLeftMenu(false);
                    return;
                }
                DMStickerNewBarView.this.mStickerMode = DMStickerModeManager.StickerMode.STICKERALL;
                DMStickerNewBarView.this.setStickerMode(dMStickerImageRes);
                DMStickerNewBarView.this.stickerItemAdapter.resetSelectList(DMStickerNewBarView.this.selectResList, DMStickerNewBarView.this.curgroupItemName);
                DMStickerNewBarView.this.stickerItemGridView.setAdapter((ListAdapter) DMStickerNewBarView.this.stickerItemAdapter);
                DMStickerNewBarView.this.isShowLeftMenu(false);
            }
        });
        this.curgroupItemName = ((DMStickerImageRes) this.stickerGroupAdapter.getItem(0)).getName();
        this.curgroupItem = (DMStickerImageRes) this.stickerGroupAdapter.getItem(0);
    }

    private void initItem() {
        this.stickerItemGridView = (GridView) findViewById(R.id.item_grid);
        DMStikcerListAdapter dMStikcerListAdapter = new DMStikcerListAdapter(this.mContext, this.curgroupItemName, this.curgroupItem);
        this.stickerItemAdapter = dMStikcerListAdapter;
        this.stickerItemGridView.setAdapter((ListAdapter) dMStikcerListAdapter);
        this.stickerItemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.6
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMStickerNewBarView.this.rebuildStickerList(i);
                DMStickerNewBarView.this.stickerItemAdapter.resetSelectList(DMStickerNewBarView.this.selectResList, DMStickerNewBarView.this.curgroupItemName);
                DMStickerNewBarView dMStickerNewBarView = DMStickerNewBarView.this;
                dMStickerNewBarView.topLableString = String.format(dMStickerNewBarView.topLableFormat, Integer.valueOf(DMStickerNewBarView.this.curStickerCount), Integer.valueOf(DMStickerNewBarView.this.maxStickerCount));
                DMStickerNewBarView.this.topLabelTextView.setText(DMStickerNewBarView.this.topLableString);
            }
        });
        try {
            new Handler().postDelayed(new Runnable() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.7
                @Override // java.lang.Runnable
                public void run() {
                    DMStickerNewBarView.this.isShowLeftMenu(true);
                }
            }, 100L);
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebuildStickerList(int i) {
        DMSelectStickerRes checkStickerList = checkStickerList(i);
        if (checkStickerList == null) {
            if (this.curStickerCount >= this.maxStickerCount) {
                Toast.makeText(this.mContext, String.format(this.mContext.getResources().getString(R.string.max_selected_sticker_cnt), Integer.valueOf(this.maxStickerCount)), Toast.LENGTH_SHORT).show();
                return;
            }
            DMSelectStickerRes dMSelectStickerRes = new DMSelectStickerRes();
            dMSelectStickerRes.mode = this.mStickerMode;
            dMSelectStickerRes.posInGridView = i;
            String str = this.curgroupItemName;
            if (str != null) {
                dMSelectStickerRes.resName = str;
                dMSelectStickerRes.curgroupItem = this.curgroupItem;
            }
            this.selectResList.add(dMSelectStickerRes);
            this.curStickerCount++;
            return;
        }
        this.selectResList.remove(checkStickerList);
        this.curStickerCount--;
    }

    public void setMaxStickerCount(int i) {
        this.maxStickerCount = i;
        setCurStickerCount(this.curStickerCount);
    }

    public void setCurStickerCount(int i) {
        this.curStickerCount = i;
        String format = String.format(this.topLableFormat, Integer.valueOf(i), Integer.valueOf(this.maxStickerCount));
        this.topLableString = format;
        this.topLabelTextView.setText(format);
    }

    private DMSelectStickerRes checkStickerList(int i) {
        for (DMSelectStickerRes dMSelectStickerRes : this.selectResList) {
            if (dMSelectStickerRes.resName == this.curgroupItemName && dMSelectStickerRes.posInGridView == i) {
                return dMSelectStickerRes;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void okClick() {
        if (this.mListener != null) {
            ArrayList arrayList = new ArrayList();
            for (DMSelectStickerRes dMSelectStickerRes : this.selectResList) {
                DMStickerImageRes res = (DMStickerImageRes) DMStickerModeManager.getStickerImageManager(this.mContext, DMStickerModeManager.getStickerModeByName(dMSelectStickerRes.resName), dMSelectStickerRes.curgroupItem).getRes(dMSelectStickerRes.posInGridView);
                if (res.isNativeJsonSticker()) {
                    res.setImageType(DMWBRes.LocationType.ASSERT);
                    res.setImageFileName(res.getIconFileName());
                    res.setIconType(DMWBRes.LocationType.ASSERT);
                }
                arrayList.add(res);
            }
            this.mListener.onStickerChoose(arrayList);
        }
    }

    public void dispose() {
        DMStickerGroupAdapterNew dMStickerGroupAdapterNew = this.stickerGroupAdapter;
        if (dMStickerGroupAdapterNew != null) {
            dMStickerGroupAdapterNew.dispose();
        }
        DMStikcerListAdapter dMStikcerListAdapter = this.stickerItemAdapter;
        if (dMStikcerListAdapter != null) {
            dMStikcerListAdapter.dispose();
        }
    }

    public void setOnStickerNewChooseListener(OnStickerNewChooseListener onStickerNewChooseListener) {
        this.mListener = onStickerNewChooseListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isShowLeftMenu(boolean z) {
        int measuredWidth = this.stickerGroupGridView.getMeasuredWidth();
        int measuredWidth2 = this.leftBtnView.getMeasuredWidth();
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.stickerGroupGridView.getLayoutParams();
        final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.leftBtnView.getLayoutParams();
        if (z) {
            this.view_leftMargin_start = -measuredWidth;
            this.view_leftMargin_end = 0;
            this.btn_leftMargin_start = 0;
            this.btn_leftMargin_end = -measuredWidth2;
        } else {
            this.view_leftMargin_start = 0;
            this.view_leftMargin_end = -measuredWidth;
            this.btn_leftMargin_start = -measuredWidth2;
            this.btn_leftMargin_end = 0;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(this.view_leftMargin_start, this.view_leftMargin_end, 0.0f, 0.0f);
        long j = (long) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        translateAnimation.setDuration(j);
        this.stickerGroupGridView.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.8
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                layoutParams.leftMargin = DMStickerNewBarView.this.view_leftMargin_end;
                DMStickerNewBarView.this.stickerGroupGridView.setLayoutParams(layoutParams);
            }
        });
        TranslateAnimation translateAnimation2 = new TranslateAnimation(this.btn_leftMargin_start, this.btn_leftMargin_end, 0.0f, 0.0f);
        translateAnimation2.setDuration(j);
        this.leftBtnView.startAnimation(translateAnimation2);
        translateAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.9
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                layoutParams2.leftMargin = DMStickerNewBarView.this.btn_leftMargin_end;
                DMStickerNewBarView.this.leftBtnView.setLayoutParams(layoutParams2);
            }
        });
        if (z) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(j);
            this.leftItemView.setAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.10
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    DMStickerNewBarView.this.leftItemView.setVisibility(0);
                }
            });
            return;
        }
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation2.setDuration(j);
        this.leftItemView.setAnimation(alphaAnimation2);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.photoart.libsticker.sticker.DMStickerNewBarView.11
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                DMStickerNewBarView.this.leftItemView.setVisibility(4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStickerMode(DMStickerImageRes dMStickerImageRes) {
        String name = dMStickerImageRes.getName();
        this.curgroupItemName = name;
        if (name != null && this.mStickerMode == DMStickerModeManager.StickerMode.ONLINE) {
            this.curgroupItem = dMStickerImageRes;
            this.stickerItemAdapter.setStickerMode(this.mStickerMode, dMStickerImageRes);
            if (!this.lastgroupItemName.equals(name)) {
                this.stickerItemAdapter.resetSelectListOnLine(this.selectResList, this.mStickerMode, this.curgroupItemName);
                this.lastgroupItemName = name;
                return;
            }
            this.stickerItemAdapter.resetSelectList(this.selectResList, this.curgroupItemName);
            return;
        }
        this.stickerItemAdapter.setStickerMode(DMStickerModeManager.getStickerModeByName(name));
    }
}
