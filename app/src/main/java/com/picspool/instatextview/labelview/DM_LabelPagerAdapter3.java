package com.picspool.instatextview.labelview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.sky.testproject.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DM_LabelPagerAdapter3 extends PagerAdapter {
    private Context context;
    private List<ImageView> icViews;
    private String[] labelImagePath = {"label/label_icon/label_ic_1.png", "label/label_icon/label_ic_2.png", "label/label_icon/label_ic_3.png", "label/label_icon/label_ic_4.jpg", "label/label_icon/label_ic_5.jpg", "label/label_icon/label_ic_6.jpg", "label/label_icon/label_ic_7.png", "label/label_icon/label_ic_8.png", "label/label_icon/label_ic_9.png", "label/label_icon/label_ic_10.png", "label/label_icon/label_ic_11.png", "label/label_icon/label_ic_12.png", "label/label_icon/label_ic_13.png", "label/label_icon/label_ic_14.png", "label/label_icon/label_ic_15.png", "label/label_icon/label_ic_16.png", "label/label_icon/label_ic_17.png", "label/label_icon/label_ic_18.png", "label/label_icon/label_ic_19.png", "label/label_icon/label_ic_20.png", "label/label_icon/label_ic_21.png"};
    private DMLabelManager labelManager;
    private DMListLabelView3 listLabelView;
    private List<View> views;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public DM_LabelPagerAdapter3(DMListLabelView3 dMListLabelView3) {
        Context context = dMListLabelView3.getContext();
        this.context = context;
        this.listLabelView = dMListLabelView3;
        this.labelManager = new DMLabelManager(context);
        this.views = new ArrayList();
        this.icViews = new ArrayList();
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = layoutInflater.inflate(R.layout.dm_text_label_pager_view_x1, (ViewGroup) null);
        View inflate2 = layoutInflater.inflate(R.layout.dm_text_label_pager_view_x2, (ViewGroup) null);
        View inflate3 = layoutInflater.inflate(R.layout.dm_text_label_pager_view_x3, (ViewGroup) null);
        this.views.add(inflate);
        this.views.add(inflate2);
        this.views.add(inflate3);
        iniTextViews(inflate);
        iniTextViews(inflate2);
        iniTextViews(inflate3);
        int i = 0;
        for (ImageView imageView : this.icViews) {
            if (imageView != null) {
                imageView.setTag(Integer.valueOf(i));
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.instatextview.labelview.DM_LabelPagerAdapter3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        try {
                            DM_LabelPagerAdapter3.this.listLabelView.editText(DM_LabelPagerAdapter3.this.labelManager.getTextDrawer(((Integer) view.getTag()).intValue()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                i++;
            }
        }
    }

    private void iniTextViews(View view) {
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_1));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_2));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_3));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_4));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_5));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_6));
        this.icViews.add((ImageView) view.findViewById(R.id.label_ic_7));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.views.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView(this.views.get(i));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        viewGroup.addView(this.views.get(i));
        return this.views.get(i);
    }

    public void loadImage() {
        Bitmap decodeStream;
        for (int i = 0; i < this.icViews.size() && i < this.labelImagePath.length; i++) {
            try {
                ImageView imageView = this.icViews.get(i);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                options.inPurgeable = true;
                options.inInputShareable = true;
                InputStream open = this.context.getAssets().open(this.labelImagePath[i]);
                if (open != null && (decodeStream = BitmapFactory.decodeStream(open, null, options)) != null) {
                    imageView.setImageBitmap(decodeStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void releaseImage() {
        for (ImageView imageView : this.icViews) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            if (bitmapDrawable != null) {
                imageView.setBackgroundResource(0);
                bitmapDrawable.setCallback(null);
                bitmapDrawable.getBitmap().recycle();
                imageView.setImageBitmap(null);
            }
        }
        System.gc();
    }

    public List<View> getViews() {
        return this.views;
    }
}
