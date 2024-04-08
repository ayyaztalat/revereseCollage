package com.picspool.libfuncview.effect.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.ServiceStarter;
import com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle2Adapter;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialDStoreRes;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.viewpagerindicator.DMCirclePageIndicator;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSOLSEffectListStyle1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_Style1 = 0;
    private int TYPE_Style2 = 1;
    private int TYPE_Style3 = 2;
    private Context context;
    private ItemClickListener itemClickListener;
    private List<CSEMaterialDStoreRes> mEList;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i, CSEMaterialRes cSEMaterialRes);
    }

    public CSOLSEffectListStyle1Adapter(Context context) {
        this.context = context;
    }

    public CSOLSEffectListStyle1Adapter(Context context, List<CSEMaterialDStoreRes> list) {
        this.context = context;
        this.mEList = list;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == this.TYPE_Style1) {
            return new Style1ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style1_vh1, viewGroup, false));
        }
        if (i == this.TYPE_Style2) {
            return new Style2ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style1_vh2, viewGroup, false));
        }
        return new Style3ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style1_vh3, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CSOLSEffectListStyle2Adapter.Style1ViewHolder) {
            ((Style1ViewHolder) viewHolder).setDataItem(this.mEList, i);
        } else if (viewHolder instanceof Style2ViewHolder) {
            ((Style2ViewHolder) viewHolder).setData(this.mEList, i);
        } else if (viewHolder instanceof Style3ViewHolder) {
            ((Style3ViewHolder) viewHolder).setDataItem(this.mEList, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mEList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        CSEMaterialDStoreRes cSEMaterialDStoreRes = this.mEList.get(i);
        if (cSEMaterialDStoreRes.getItemType() == CSEMaterialDStoreRes.ItemType.VPBANNER) {
            return this.TYPE_Style1;
        }
        if (cSEMaterialDStoreRes.getItemType() == CSEMaterialDStoreRes.ItemType.TITLE) {
            return this.TYPE_Style2;
        }
        return this.TYPE_Style3;
    }

    /* loaded from: classes.dex */
    class Style1ViewHolder extends RecyclerView.ViewHolder {
        private PagerAdapter adapter;
        DMCirclePageIndicator homeTopViewPagerIndicator;
        ViewPager viewPager;

        public void setDataItem(List<CSEMaterialDStoreRes> list, int i) {
        }

        public Style1ViewHolder(View view) {
            super(view);
            this.adapter = new PagerAdapter() { // from class: com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.Style1ViewHolder.3
                @Override // androidx.viewpager.widget.PagerAdapter
                public boolean isViewFromObject(View view2, Object obj) {
                    return view2 == obj;
                }

                @Override // androidx.viewpager.widget.PagerAdapter
                public int getCount() {
                    return ((CSEMaterialDStoreRes) CSOLSEffectListStyle1Adapter.this.mEList.get(0)).getBmwbResList().size();
                }

                @Override // androidx.viewpager.widget.PagerAdapter
                public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                    if (obj instanceof View) {
                        viewGroup.removeView((View) obj);
                    }
                }

                @Override // androidx.viewpager.widget.PagerAdapter
                public Object instantiateItem(ViewGroup viewGroup, final int i) {
                    View inflate = View.inflate(CSOLSEffectListStyle1Adapter.this.context, R.layout.view_adapter_item_ols_viewpager_style1, null);
                    Glide.with(CSOLSEffectListStyle1Adapter.this.context).load(((CSEMaterialDStoreRes) CSOLSEffectListStyle1Adapter.this.mEList.get(0)).getBmwbResList().get(i).getBanner()).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.material_glide_load_default_500).override(720, ServiceStarter.ERROR_UNKNOWN)).into((ImageView) inflate.findViewById(R.id.vp_img_main));
                    viewGroup.addView(inflate);
                    inflate.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.Style1ViewHolder.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            int i2 = i;
                            if (i2 < 0 || i2 >= ((CSEMaterialDStoreRes) CSOLSEffectListStyle1Adapter.this.mEList.get(0)).getBmwbResList().size() || CSOLSEffectListStyle1Adapter.this.itemClickListener == null) {
                                return;
                            }
                            CSOLSEffectListStyle1Adapter.this.itemClickListener.onItemClick(Style1ViewHolder.this.itemView, i, ((CSEMaterialDStoreRes) CSOLSEffectListStyle1Adapter.this.mEList.get(0)).getBmwbResList().get(i));
                        }
                    });
                    return inflate;
                }
            };
            this.viewPager = (ViewPager) view.findViewById(R.id.view_pager);
            view.getLayoutParams().width = DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle1Adapter.this.context);
            view.getLayoutParams().height = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle1Adapter.this.context) * 0.6944444f);
            this.viewPager.setPageTransformer(true, new ViewPager.PageTransformer() { // from class: com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.Style1ViewHolder.1
                private static final float MIN_ALPHA = 1.0f;
                private static final float MIN_SCALE = 1.0f;

                @Override // androidx.viewpager.widget.ViewPager.PageTransformer
                public void transformPage(View view2, float f) {
                    int width = view2.getWidth();
                    int height = view2.getHeight();
                    if (f < -1.0f) {
                        view2.setAlpha(0.0f);
                    } else if (f <= 1.0f) {
                        float max = Math.max(1.0f, 1.0f - Math.abs(f));
                        float f2 = 1.0f - max;
                        float f3 = (height * f2) / 2.0f;
                        float f4 = (width * f2) / 2.0f;
                        if (f < 0.0f) {
                            view2.setTranslationX(f4 - (f3 / 2.0f));
                        } else {
                            view2.setTranslationX((-f4) + (f3 / 2.0f));
                        }
                        view2.setScaleX(max);
                        view2.setScaleY(max);
                        view2.setAlpha((((max - 1.0f) / 0.0f) * 0.0f) + 1.0f);
                    } else {
                        view2.setAlpha(0.0f);
                    }
                }
            });
            this.viewPager.setAdapter(this.adapter);
            this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.Style1ViewHolder.2
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    Style1ViewHolder.this.homeTopViewPagerIndicator.setCurrentItem(i);
                }
            });
            DMCirclePageIndicator dMCirclePageIndicator = (DMCirclePageIndicator) view.findViewById(R.id.indicator_viewpager);
            this.homeTopViewPagerIndicator = dMCirclePageIndicator;
            dMCirclePageIndicator.setViewPager(this.viewPager);
        }
    }

    /* loaded from: classes.dex */
    class Style2ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_main;

        public Style2ViewHolder(View view) {
            super(view);
            this.textView_main = (TextView) view.findViewById(R.id.txt_title);
            view.getLayoutParams().width = DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle1Adapter.this.context);
            view.getLayoutParams().height = DMScreenInfoUtil.dip2px(CSOLSEffectListStyle1Adapter.this.context, 50.0f);
        }

        public void setData(List<CSEMaterialDStoreRes> list, int i) {
            this.textView_main.setText(list.get(i).getShowText());
        }
    }

    /* loaded from: classes.dex */
    class Style3ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgmain;
        View ly_download;
        TextView showtext;

        public Style3ViewHolder(final View view) {
            super(view);
            this.imgmain = (ImageView) view.findViewById(R.id.img_main);
            this.showtext = (TextView) view.findViewById(R.id.showtext);
            this.ly_download = view.findViewById(R.id.ly_download);
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle1Adapter.this.context) / 2.0f);
            view.getLayoutParams().height = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle1Adapter.this.context) / 2.0f);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.Style3ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = Style3ViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSOLSEffectListStyle1Adapter.this.mEList.size() || CSOLSEffectListStyle1Adapter.this.itemClickListener == null) {
                        return;
                    }
                    CSOLSEffectListStyle1Adapter.this.itemClickListener.onItemClick(view, Style3ViewHolder.this.getAdapterPosition(), ((CSEMaterialDStoreRes) CSOLSEffectListStyle1Adapter.this.mEList.get(adapterPosition)).getWbeMaterialRes());
                }
            });
        }

        public void setDataItem(List<CSEMaterialDStoreRes> list, int i) {
            CSEMaterialRes wbeMaterialRes;
            if (list == null || i < 0 || i >= list.size() || (wbeMaterialRes = list.get(i).getWbeMaterialRes()) == null) {
                return;
            }
            this.showtext.setText(wbeMaterialRes.getName());
            Glide.with(CSOLSEffectListStyle1Adapter.this.context).load(wbeMaterialRes.getIcon()).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.material_glide_load_default_300).override(300, 300)).into(this.imgmain);
            if (wbeMaterialRes.isContentExist()) {
                this.ly_download.setVisibility(View.GONE);
                this.showtext.setGravity(17);
                this.showtext.setPadding(0, 0, 0, 0);
                return;
            }
            this.ly_download.setVisibility(View.VISIBLE);
            this.showtext.setGravity(8388627);
            this.showtext.setPadding(10, 0, 0, 0);
        }
    }
}
