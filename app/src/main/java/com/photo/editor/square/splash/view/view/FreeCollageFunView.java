package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.photo.editor.square.splash.utils.AssBitManage;
import com.photo.editor.square.splash.view.CollageFunView;
import com.sky.testproject.R;
import com.winflag.libcollage.adapter.BordRecyclerViewAdapter;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes2.dex */
public class FreeCollageFunView extends CustomFrameLayoutImags<Void> implements AdapterView.OnItemClickListener {
    private View background;
    private FrameLayout backgroundFun;
    private View borderFrame;
    private View borderFramewai;
    private FrameLayout borderFun;
    private BordRecyclerViewAdapter boredRecyclerViewAdapter;
    private LinearLayout frameview;
    private CollageFunView.OnAdjustBtnClickListener listener;
    public CollageFunView.OnTemplateChangedListener mListener;
    private RecyclerView recyclerView;
    private ViewPager viewPager;

    /* loaded from: classes2.dex */
    public interface OnAdjustBtnClickListener {
        void onAdjustClick();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
    }

    public void setmListener(CollageFunView.OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }

    public FreeCollageFunView(Context context) {
        super(context);
    }

    public FreeCollageFunView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FreeCollageFunView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }


    @Override
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(true);
        inflate(context, R.layout.view_free_collage_bottom_fun_tablayout, frameLayout);
        this.borderFrame = findViewById(R.id.border_frame);
        this.borderFramewai = findViewById(R.id.border_frame_wai);
        View findViewById = findViewById(R.id.background);
        this.background = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$FreeCollageFunView$E-H7jBY24hvEXY7MBaxD4PrcnrE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FreeCollageFunView.this.lambda$f10$0$FreeCollageFunView(view);
            }
        });
        this.borderFrame.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$FreeCollageFunView$m9npVMnfWovsMRWmTyLswHCONaA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FreeCollageFunView.this.lambda$f10$1$FreeCollageFunView(view);
            }
        });
        this.borderFramewai.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$FreeCollageFunView$VJaW-pQx3TgURTQeO0Eay68_oKQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FreeCollageFunView.this.lambda$f10$2$FreeCollageFunView(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) inflate(context, R.layout.view_layout_border, null);
        this.frameview = linearLayout;
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.recyclerView = (RecyclerView) this.frameview.findViewById(R.id.recyclerview);
        BordRecyclerViewAdapter bordRecyclerViewAdapter = new BordRecyclerViewAdapter(context, AssBitManage.getBit(), this.recyclerView, BitmapFactory.decodeResource(getResources(), R.drawable.collage_border_corner));
        this.boredRecyclerViewAdapter = bordRecyclerViewAdapter;
        bordRecyclerViewAdapter.setShowText(false);
        this.recyclerView.setAdapter(this.boredRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        this.boredRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new BordRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.photo.editor.square.splash.view.view.FreeCollageFunView.1
            @Override // com.winflag.libcollage.adapter.BordRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(List<AssBitManage.bitBean> list, int i) {
                if (FreeCollageFunView.this.mListener != null) {
                    FreeCollageFunView.this.mListener.onTemplateChanged(list, i);
                }
            }
        });
        FrameLayout frameLayout3 = new FrameLayout(context);
        this.borderFun = frameLayout3;
        frameLayout3.setLayoutParams(new LayoutParams(-1, -1));
        FrameLayout frameLayout4 = new FrameLayout(context);
        this.backgroundFun = frameLayout4;
        frameLayout4.setLayoutParams(new LayoutParams(-1, -1));
        ViewPager viewPager = new ViewPager(context);
        this.viewPager = viewPager;
        viewPager.setLayoutParams(new LayoutParams(-1, DMScreenInfoUtil.dip2px(getContext(), 120.0f)));
        frameLayout2.addView(this.viewPager);
        this.viewPager.setAdapter(new PagerAdapter() { // from class: com.photo.editor.square.splash.view.view.FreeCollageFunView.2
            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 3;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup viewGroup, int i) {
                if (i == 0) {
                    viewGroup.addView(FreeCollageFunView.this.backgroundFun);
                    return FreeCollageFunView.this.backgroundFun;
                } else if (i == 1) {
                    viewGroup.addView(FreeCollageFunView.this.borderFun);
                    return FreeCollageFunView.this.borderFun;
                } else if (i == 2) {
                    viewGroup.addView(FreeCollageFunView.this.frameview);
                    return FreeCollageFunView.this.frameview;
                } else {
                    return null;
                }
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                viewGroup.removeView((View) obj);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.photo.editor.square.splash.view.view.FreeCollageFunView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                FreeCollageFunView.this.selectedTab(i);
            }
        });
        selectedTab(0);
    }

    public /* synthetic */ void lambda$f10$0$FreeCollageFunView(View view) {
        selectedTab(0);
    }

    public /* synthetic */ void lambda$f10$1$FreeCollageFunView(View view) {
        selectedTab(1);
    }

    public /* synthetic */ void lambda$f10$2$FreeCollageFunView(View view) {
        selectedTab(2);
    }

    public void setBackground(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        this.backgroundFun.removeAllViews();
        this.backgroundFun.addView(view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    public void setBorderFrame(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        this.borderFun.removeAllViews();
        this.borderFun.addView(view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    public /* synthetic */ void lambda$selectedTab$3$FreeCollageFunView(int i) {
        this.viewPager.setCurrentItem(i);
    }

    public void selectedTab(final int i) {
        this.viewPager.post(new Runnable() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$FreeCollageFunView$4aiIxjsxwWe7uQg_Bumz1-QTAbU
            @Override // java.lang.Runnable
            public final void run() {
                FreeCollageFunView.this.lambda$selectedTab$3$FreeCollageFunView(i);
            }
        });
        this.borderFrame.setSelected(false);
        this.background.setSelected(false);
        this.borderFramewai.setSelected(false);
        if (i == 0) {
            this.background.setSelected(true);
        } else if (i == 1) {
            this.borderFrame.setSelected(true);
        } else if (i != 2) {
        } else {
            this.borderFramewai.setSelected(true);
        }
    }

    public void setListener(CollageFunView.OnAdjustBtnClickListener onAdjustBtnClickListener) {
        this.listener = onAdjustBtnClickListener;
    }
}
