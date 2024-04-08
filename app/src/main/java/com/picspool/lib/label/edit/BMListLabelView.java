package com.picspool.lib.label.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class BMListLabelView extends FrameLayout {
    private View labelButton;
    private View loveButton;
    protected ListChangedListener mChangedListener;
    private View newYearButton;
    private BMLabelPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    /* loaded from: classes3.dex */
    public interface ListChangedListener {
        void labelEdit(DMTextDrawer dMTextDrawer);

        void listBack();

        void textClick();
    }

    public BMListLabelView(Context context) {
        super(context);
        iniView();
    }

    public BMListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniView();
    }

    public void iniView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_list_label_view, (ViewGroup) this, true);
        this.viewPager = (ViewPager) findViewById(R.id.label_view_pager);
        BMLabelPagerAdapter bMLabelPagerAdapter = new BMLabelPagerAdapter(this);
        this.pagerAdapter = bMLabelPagerAdapter;
        this.viewPager.setAdapter(bMLabelPagerAdapter);
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.picspool.lib.label.edit.BMListLabelView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                BMListLabelView.this.invisibleButton();
                if (i == 0) {
                    BMListLabelView.this.newYearButton.setSelected(true);
                } else if (i == 1) {
                    BMListLabelView.this.loveButton.setSelected(true);
                } else if (i != 2) {
                } else {
                    BMListLabelView.this.labelButton.setSelected(true);
                }
            }
        });
        findViewById(R.id.button_back).setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMListLabelView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BMListLabelView.this.mChangedListener != null) {
                    BMListLabelView.this.mChangedListener.listBack();
                }
            }
        });
        View findViewById = findViewById(R.id.btn_label_new_year);
        this.newYearButton = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMListLabelView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMListLabelView.this.invisibleButton();
                BMListLabelView.this.newYearButton.setSelected(true);
                if (BMListLabelView.this.viewPager != null) {
                    BMListLabelView.this.viewPager.setCurrentItem(0);
                }
            }
        });
        View findViewById2 = findViewById(R.id.btn_label_love);
        this.loveButton = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMListLabelView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMListLabelView.this.invisibleButton();
                BMListLabelView.this.loveButton.setSelected(true);
                if (BMListLabelView.this.viewPager != null) {
                    BMListLabelView.this.viewPager.setCurrentItem(1);
                }
            }
        });
        View findViewById3 = findViewById(R.id.btn_label_label);
        this.labelButton = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMListLabelView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMListLabelView.this.invisibleButton();
                BMListLabelView.this.labelButton.setSelected(true);
                if (BMListLabelView.this.viewPager != null) {
                    BMListLabelView.this.viewPager.setCurrentItem(2);
                }
            }
        });
        this.newYearButton.setSelected(true);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        BMLabelPagerAdapter bMLabelPagerAdapter = this.pagerAdapter;
        if (bMLabelPagerAdapter != null) {
            if (i == 0) {
                bMLabelPagerAdapter.loadImage();
            } else if (i == 4) {
                bMLabelPagerAdapter.releaseImage();
            }
        }
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
        ListChangedListener listChangedListener = this.mChangedListener;
        if (listChangedListener != null) {
            listChangedListener.labelEdit(dMTextDrawer);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setListChangedListener(ListChangedListener listChangedListener) {
        this.mChangedListener = listChangedListener;
    }
}
