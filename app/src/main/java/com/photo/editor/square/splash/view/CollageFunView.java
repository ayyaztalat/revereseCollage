package com.photo.editor.square.splash.view;

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
import com.photo.editor.square.splash.view.view.CustomFrameLayoutImags;
import com.sky.testproject.R;
import com.winflag.libcollage.adapter.BordRecyclerViewAdapter;
import com.winflag.libcollage.manager.FilterManager;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes2.dex */
public class CollageFunView extends CustomFrameLayoutImags<Void> implements AdapterView.OnItemClickListener {
    private View adjustBtn;
    private View background;
    private FrameLayout backgroundFun;
    private BordRecyclerViewAdapter boredRecyclerViewAdapter;
    private View frame;
    private FrameLayout framefun;
    private LinearLayout frameview;
    private View layout;
    private LinearLayout layoutFun;
    private FrameLayout layoutRoot;
    private OnAdjustBtnClickListener listener;
    private FilterManager mFiterManager;
    public OnTemplateChangedListener mListener;
    private RecyclerView recyclerView;
    private View space;
    private FrameLayout spaceFun;
    private ViewPager viewPager;

    /* loaded from: classes2.dex */
    public interface OnAdjustBtnClickListener {
        void onAdjustClick();
    }

    /* loaded from: classes2.dex */
    public interface OnTemplateChangedListener {
        void onTemplateChanged(List<AssBitManage.bitBean> list, int i);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
    }

    public void setmListener(OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }

    public CollageFunView(Context context) {
        super(context);
    }

    public CollageFunView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CollageFunView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(true);
        inflate(context, R.layout.view_collage_bottom_fun_tablayout, frameLayout);
        this.layout = findViewById(R.id.layout);
        this.space = findViewById(R.id.space);
        this.background = findViewById(R.id.background);
        this.frame = findViewById(R.id.border_frame);
        this.layout.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$Wmf35j9Ik0jcpfSDVt3eNygktas
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollageFunView.this.lambda$f10$0$CollageFunView(view);
            }
        });
        this.space.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$CffpwH24UBTVWlSM1uu8cY-Xqe4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollageFunView.this.lambda$f10$1$CollageFunView(view);
            }
        });
        this.background.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$mxEBK3hC6qsOMzHrH9fFAeFZ_zo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollageFunView.this.lambda$f10$2$CollageFunView(view);
            }
        });
        this.frame.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$4xa18pEjkGHkmcH9E2ji7kTmksM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollageFunView.this.lambda$f10$3$CollageFunView(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) inflate(context, R.layout.view_layout_fun, null);
        this.layoutFun = linearLayout;
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.adjustBtn = this.layoutFun.findViewById(R.id.adjustBtn);
        this.layoutRoot = (FrameLayout) this.layoutFun.findViewById(R.id.layoutRoot);
        this.adjustBtn.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$tPJMMQy5wbQufQGT_TYFJGoQFzY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollageFunView.this.lambda$f10$4$CollageFunView(view);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) inflate(context, R.layout.view_layout_border, null);
        this.frameview = linearLayout2;
        linearLayout2.setLayoutParams(new LayoutParams(-1, -1));
        this.recyclerView = (RecyclerView) this.frameview.findViewById(R.id.recyclerview);
        BordRecyclerViewAdapter bordRecyclerViewAdapter = new BordRecyclerViewAdapter(context, AssBitManage.getBit(), this.recyclerView, BitmapFactory.decodeResource(getResources(), R.drawable.collage_border_corner));
        this.boredRecyclerViewAdapter = bordRecyclerViewAdapter;
        bordRecyclerViewAdapter.setShowText(false);
        this.recyclerView.setAdapter(this.boredRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        this.boredRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new BordRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.photo.editor.square.splash.view.CollageFunView.1
            @Override // com.winflag.libcollage.adapter.BordRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(List<AssBitManage.bitBean> list, int i) {
                if (CollageFunView.this.mListener != null) {
                    CollageFunView.this.mListener.onTemplateChanged(list, i);
                }
            }
        });
        FrameLayout frameLayout3 = new FrameLayout(context);
        this.spaceFun = frameLayout3;
        frameLayout3.setLayoutParams(new LayoutParams(-1, -1));
        FrameLayout frameLayout4 = new FrameLayout(context);
        this.backgroundFun = frameLayout4;
        frameLayout4.setLayoutParams(new LayoutParams(-1, -1));
        ViewPager viewPager = new ViewPager(context);
        this.viewPager = viewPager;
        viewPager.setLayoutParams(new LayoutParams(-1, DMScreenInfoUtil.dip2px(getContext(), 120.0f)));
        frameLayout2.addView(this.viewPager);
        this.viewPager.setBackgroundResource(R.color.bootm_pop);
        this.viewPager.setAdapter(new PagerAdapter() { // from class: com.photo.editor.square.splash.view.CollageFunView.2
            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 4;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup viewGroup, int i) {
                if (i == 0) {
                    viewGroup.addView(CollageFunView.this.layoutFun);
                    return CollageFunView.this.layoutFun;
                } else if (i == 1) {
                    viewGroup.addView(CollageFunView.this.spaceFun);
                    return CollageFunView.this.spaceFun;
                } else if (i == 2) {
                    viewGroup.addView(CollageFunView.this.backgroundFun);
                    return CollageFunView.this.backgroundFun;
                } else if (i == 3) {
                    viewGroup.addView(CollageFunView.this.frameview);
                    return CollageFunView.this.frameview;
                } else {
                    return null;
                }
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                if (viewGroup == null || obj == null) {
                    return;
                }
                viewGroup.removeView((View) obj);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.photo.editor.square.splash.view.CollageFunView.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                CollageFunView.this.selectedTab(i);
            }
        });
        selectedTab(0);
    }

    public /* synthetic */ void lambda$f10$0$CollageFunView(View view) {
        selectedTab(0);
    }

    public /* synthetic */ void lambda$f10$1$CollageFunView(View view) {
        selectedTab(1);
    }

    public /* synthetic */ void lambda$f10$2$CollageFunView(View view) {
        selectedTab(2);
    }

    public /* synthetic */ void lambda$f10$3$CollageFunView(View view) {
        selectedTab(3);
    }

    public /* synthetic */ void lambda$f10$4$CollageFunView(View view) {
        OnAdjustBtnClickListener onAdjustBtnClickListener = this.listener;
        if (onAdjustBtnClickListener != null) {
            onAdjustBtnClickListener.onAdjustClick();
        }
    }

    public void setLayout(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        this.layoutRoot.removeAllViews();
        this.layoutRoot.addView(view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    public void setBackground(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        this.backgroundFun.removeAllViews();
        this.backgroundFun.addView(view);
        this.backgroundFun.setBackgroundResource(R.color.bootm_pop);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    public void setSpace(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        this.spaceFun.removeAllViews();
        this.spaceFun.addView(view);
        this.spaceFun.setBackgroundResource(R.color.bootm_pop);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    public /* synthetic */ void lambda$selectedTab$5$CollageFunView(int i) {
        this.viewPager.setCurrentItem(i);
    }

    public void selectedTab(final int i) {
        this.viewPager.post(new Runnable() { // from class: com.photo.editor.square.splash.view.-$$Lambda$CollageFunView$G8w3RxGURHivaMx_yVb6OKi0LWY
            @Override // java.lang.Runnable
            public final void run() {
                CollageFunView.this.lambda$selectedTab$5$CollageFunView(i);
            }
        });
        this.layout.setSelected(false);
        this.space.setSelected(false);
        this.background.setSelected(false);
        this.background.setBackgroundResource(R.color.bootm_pop);
        this.frame.setSelected(false);
        if (i == 0) {
            this.layout.setSelected(true);
        } else if (i == 1) {
            this.space.setSelected(true);
        } else if (i == 2) {
            this.background.setSelected(true);
        } else if (i != 3) {
        } else {
            this.frame.setSelected(true);
        }
    }

    public void setListener(OnAdjustBtnClickListener onAdjustBtnClickListener) {
        this.listener = onAdjustBtnClickListener;
    }
}
