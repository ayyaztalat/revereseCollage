package com.picspool.instatextview.labelview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import java.util.HashMap;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.instatextview.textview.DMShowTextStickerView3;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMListLabelView3 extends FrameLayout {
    protected DMEditLabelView3 editLabelView;
    protected DMInstaTextView3 instaTextView;
    private View labelButton;
    private View loveButton;
    private View newYearButton;
    private DM_LabelPagerAdapter3 pagerAdapter;
    protected View rootLayout;
    protected DMShowTextStickerView3 showTextStickerView;
    private ViewPager viewPager;

    public DMListLabelView3(Context context) {
        super(context);
        iniView();
    }

    public DMListLabelView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView();
    }

    public void iniView() {
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_list_label_view, (ViewGroup) null);
        this.rootLayout = inflate;
        this.viewPager = (ViewPager) inflate.findViewById(R.id.label_view_pager);
        DM_LabelPagerAdapter3 dM_LabelPagerAdapter3 = new DM_LabelPagerAdapter3(this);
        this.pagerAdapter = dM_LabelPagerAdapter3;
        this.viewPager.setAdapter(dM_LabelPagerAdapter3);
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.picspool.instatextview.labelview.DMListLabelView3.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                DMListLabelView3.this.invisibleButton();
                if (i == 0) {
                    DMListLabelView3.this.newYearButton.setSelected(true);
                } else if (i == 1) {
                    DMListLabelView3.this.loveButton.setSelected(true);
                } else if (i != 2) {
                } else {
                    DMListLabelView3.this.labelButton.setSelected(true);
                }
            }
        });
        this.rootLayout.findViewById(R.id.button_back).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMListLabelView3.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    DMListLabelView3.this.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    DMListLabelView3.this.showTextStickerView.setSurfaceVisibility(0);
                } catch (Exception unused) {
                    new HashMap().put("showTextStickerSurfaceView", "showTextStickerSurfaceView");
                }
                if (DMListLabelView3.this.instaTextView != null) {
                    DMListLabelView3.this.instaTextView.releaseLabelView();
                }
                DMListLabelView3.this.instaTextView.callFinishEditLabel();
            }
        });
        View findViewById = this.rootLayout.findViewById(R.id.btn_label_new_year);
        this.newYearButton = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMListLabelView3.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMListLabelView3.this.invisibleButton();
                DMListLabelView3.this.newYearButton.setSelected(true);
                if (DMListLabelView3.this.viewPager != null) {
                    DMListLabelView3.this.viewPager.setCurrentItem(0);
                }
            }
        });
        View findViewById2 = this.rootLayout.findViewById(R.id.btn_label_love);
        this.loveButton = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMListLabelView3.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMListLabelView3.this.invisibleButton();
                DMListLabelView3.this.loveButton.setSelected(true);
                if (DMListLabelView3.this.viewPager != null) {
                    DMListLabelView3.this.viewPager.setCurrentItem(1);
                }
            }
        });
        View findViewById3 = this.rootLayout.findViewById(R.id.btn_label_label);
        this.labelButton = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMListLabelView3.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMListLabelView3.this.invisibleButton();
                DMListLabelView3.this.labelButton.setSelected(true);
                if (DMListLabelView3.this.viewPager != null) {
                    DMListLabelView3.this.viewPager.setCurrentItem(2);
                }
            }
        });
        this.newYearButton.setSelected(true);
        addView(this.rootLayout);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        DM_LabelPagerAdapter3 dM_LabelPagerAdapter3 = this.pagerAdapter;
        if (dM_LabelPagerAdapter3 != null) {
            if (i == 0) {
                dM_LabelPagerAdapter3.loadImage();
            } else if (i == 4) {
                dM_LabelPagerAdapter3.releaseImage();
            }
        }
    }

    public void showLabelList() {
        setVisibility(View.VISIBLE);
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
        setVisibility(View.INVISIBLE);
        this.editLabelView.editText(dMTextDrawer);
    }

    public DMEditLabelView3 getEditLabelView() {
        return this.editLabelView;
    }

    public void setEditLabelView(DMEditLabelView3 dMEditLabelView3) {
        this.editLabelView = dMEditLabelView3;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public DMInstaTextView3 getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DMInstaTextView3 dMInstaTextView3) {
        this.instaTextView = dMInstaTextView3;
    }

    public DMShowTextStickerView3 getShowTextStickerView() {
        return this.showTextStickerView;
    }

    public void setShowTextStickerView(DMShowTextStickerView3 dMShowTextStickerView3) {
        this.showTextStickerView = dMShowTextStickerView3;
    }
}
