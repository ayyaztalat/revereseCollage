package com.picspool.instatextview.online;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import java.util.HashMap;
import com.sky.testproject.R;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DM_OnlineListLabelView extends FrameLayout {
    protected DM_OnlineEditLabelView editLabelView;
    protected DM_OnlineInstaTextView instaTextView;
    private View labelButton;
    private View loveButton;
    private View newYearButton;
    private DM_OnlineLabelPagerAdapter pagerAdapter;
    protected View rootLayout;
    protected DM_OnlineShowTextBMStickerView showTextStickerView;
    private ViewPager viewPager;

    public DM_OnlineListLabelView(Context context) {
        super(context);
        iniView();
    }

    public DM_OnlineListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView();
    }

    public void iniView() {
        View inflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_list_label_view, (ViewGroup) null);
        this.rootLayout = inflate;
        this.viewPager = (ViewPager) inflate.findViewById(R.id.label_view_pager);
        DM_OnlineLabelPagerAdapter dM_OnlineLabelPagerAdapter = new DM_OnlineLabelPagerAdapter(this);
        this.pagerAdapter = dM_OnlineLabelPagerAdapter;
        this.viewPager.setAdapter(dM_OnlineLabelPagerAdapter);
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.picspool.instatextview.online.DM_OnlineListLabelView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DM_OnlineListLabelView.this.invisibleButton();
                if (i == 0) {
                    DM_OnlineListLabelView.this.newYearButton.setSelected(true);
                } else if (i == 1) {
                    DM_OnlineListLabelView.this.loveButton.setSelected(true);
                } else if (i != 2) {
                } else {
                    DM_OnlineListLabelView.this.labelButton.setSelected(true);
                }
            }
        });
        this.rootLayout.findViewById(R.id.button_back).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineListLabelView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    DM_OnlineListLabelView.this.setVisibility(4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    DM_OnlineListLabelView.this.showTextStickerView.setSurfaceVisibility(0);
                } catch (Exception unused) {
                    new HashMap().put("showTextStickerSurfaceView", "showTextStickerSurfaceView");
                }
                if (DM_OnlineListLabelView.this.instaTextView != null) {
                    DM_OnlineListLabelView.this.instaTextView.releaseLabelView();
                }
            }
        });
        View findViewById = this.rootLayout.findViewById(R.id.btn_label_new_year);
        this.newYearButton = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineListLabelView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineListLabelView.this.invisibleButton();
                DM_OnlineListLabelView.this.newYearButton.setSelected(true);
                if (DM_OnlineListLabelView.this.viewPager != null) {
                    DM_OnlineListLabelView.this.viewPager.setCurrentItem(0);
                }
            }
        });
        View findViewById2 = this.rootLayout.findViewById(R.id.btn_label_love);
        this.loveButton = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineListLabelView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineListLabelView.this.invisibleButton();
                DM_OnlineListLabelView.this.loveButton.setSelected(true);
                if (DM_OnlineListLabelView.this.viewPager != null) {
                    DM_OnlineListLabelView.this.viewPager.setCurrentItem(1);
                }
            }
        });
        View findViewById3 = this.rootLayout.findViewById(R.id.btn_label_label);
        this.labelButton = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineListLabelView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineListLabelView.this.invisibleButton();
                DM_OnlineListLabelView.this.labelButton.setSelected(true);
                if (DM_OnlineListLabelView.this.viewPager != null) {
                    DM_OnlineListLabelView.this.viewPager.setCurrentItem(2);
                }
            }
        });
        this.newYearButton.setSelected(true);
        addView(this.rootLayout);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        DM_OnlineLabelPagerAdapter dM_OnlineLabelPagerAdapter = this.pagerAdapter;
        if (dM_OnlineLabelPagerAdapter != null) {
            if (i == 0) {
                dM_OnlineLabelPagerAdapter.loadImage();
            } else if (i == 4) {
                dM_OnlineLabelPagerAdapter.releaseImage();
            }
        }
        DM_OnlineInstaTextView dM_OnlineInstaTextView = this.instaTextView;
        if (dM_OnlineInstaTextView == null || i != 4) {
            return;
        }
        dM_OnlineInstaTextView.callFinishEditLabel();
    }

    public void showLabelList() {
        setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invisibleButton() {
        this.newYearButton.setSelected(false);
        this.loveButton.setSelected(false);
        this.labelButton.setSelected(false);
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        if (this.editLabelView == null || dMTextDrawer == null) {
            return;
        }
        setVisibility(4);
        this.editLabelView.editText(dMTextDrawer);
    }

    public DM_OnlineEditLabelView getEditLabelView() {
        return this.editLabelView;
    }

    public void setEditLabelView(DM_OnlineEditLabelView dM_OnlineEditLabelView) {
        this.editLabelView = dM_OnlineEditLabelView;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public DM_OnlineInstaTextView getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DM_OnlineInstaTextView dM_OnlineInstaTextView) {
        this.instaTextView = dM_OnlineInstaTextView;
    }

    public DM_OnlineShowTextBMStickerView getShowTextStickerView() {
        return this.showTextStickerView;
    }

    public void setShowTextStickerView(DM_OnlineShowTextBMStickerView dM_OnlineShowTextBMStickerView) {
        this.showTextStickerView = dM_OnlineShowTextBMStickerView;
    }
}
