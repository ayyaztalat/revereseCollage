package com.photo.editor.square.splash.view.filterbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;


/* loaded from: classes2.dex */
public class CSLidowFilterHrzListViewAdapter extends ArrayAdapter<DMWBRes> {
    public static final String FilterIcon = "LidowFilterIcon";
    public static int LidowFilterIconVersion = 5;
    private static final int MSG_FAILURE = 1;
    private static final int MSG_SUCCESS = 0;
    float borderWidth;
    private float circleBorderRadius;
    private int containHeightDp;
    int count;
    private Bitmap filterSrc;
    private List<Holder> holderArray;
    private Bitmap imageBorderBitmap;
    private int imageBorderHeightDp;
    private int imageBorderWidthDp;
    private boolean isBottomSelState;
    private boolean isCircleBorderState;
    private boolean isExpanded;
    private boolean isFillet;
    private boolean isSetImageViewScaleType;
    private boolean isSetScaleType;
    private boolean isShowImageBorder;
    private boolean isWithHalfShow;
    OnLidowFilterHrzListViewAdapterListener listener;
    private ImageView.ScaleType mBorderViewScaleType;
    private Context mContext;
    Holder mCurSelectedHolder;
    ImageView mCurSelectedItem;
    private ImageView.ScaleType mImageViewScaleType;
    private LayoutInflater mInflater;
    private int mSelectBorderColor;
    private int mSelectViewWidthDp;
    private int mTextViewBackColor;
    private int mTextViewColor;
    private int mTextViewSeletedBackColor;
    private int mTextViewSeletedColor;
    private int mViewWidthDp;
    private String pkgName;
    HashMap<Integer, View> posViewMap;
    private int selectColor;
    public int selectedPos;
    private int textMarginBottomDp;
    private int textViewMarginBottom;

    /* loaded from: classes2.dex */
    public interface OnLidowFilterHrzListViewAdapterListener {
        void onLikeClick(View view);
    }

    public void setOnLidowFilterHrzListViewAdapterListener(OnLidowFilterHrzListViewAdapterListener onLidowFilterHrzListViewAdapterListener) {
        this.listener = onLidowFilterHrzListViewAdapterListener;
    }

    /* loaded from: classes2.dex */
    private class Holder {
        public Bitmap iconBitmap;
        public CSRoundedCornerImageView iconImageView;
        public CSRoundedCornerImageView iconfilterImageView;
        public ImageView imgLike;
        public TextView item_icon_select_view;
        public TextView tx_text;

        private Holder() {
        }

        public void dispose() {
            recycleImageView(this.iconImageView);
            recycleImageView(this.iconfilterImageView);
        }

        private void recycleImageView(ImageView imageView) {
            Drawable drawable;
            Bitmap bitmap;
            if (imageView == null || (drawable = imageView.getDrawable()) == null || !(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled()) {
                return;
            }
            bitmap.recycle();
        }
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean z) {
        this.isExpanded = z;
    }

    public CSLidowFilterHrzListViewAdapter(Context context, DMWBRes[] dMWBResArr, int i) {
        super(context, (int) R.layout.recyclerview_filteritem, dMWBResArr);
        this.selectedPos = -1;
        this.mSelectBorderColor = Color.rgb(0, 235, 232);
        this.posViewMap = new HashMap<>();
        this.imageBorderWidthDp = 52;
        this.imageBorderHeightDp = 52;
        this.containHeightDp = 60;
        this.textMarginBottomDp = 0;
        this.holderArray = new ArrayList();
        this.mTextViewColor = -1;
        this.mTextViewBackColor = 0;
        this.mTextViewSeletedColor = -1;
        this.mTextViewSeletedBackColor = 0;
        this.isWithHalfShow = false;
        this.mViewWidthDp = 0;
        this.mBorderViewScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mImageViewScaleType = ImageView.ScaleType.FIT_CENTER;
        this.isSetScaleType = false;
        this.isFillet = false;
        this.isBottomSelState = false;
        this.mSelectViewWidthDp = -1;
        this.isCircleBorderState = false;
        this.circleBorderRadius = 500.0f;
        this.borderWidth = 5.0f;
        this.isSetImageViewScaleType = false;
        this.textViewMarginBottom = 6;
        this.count = 0;
        this.isShowImageBorder = false;
        this.imageBorderBitmap = null;
        this.isExpanded = false;
        this.mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.selectColor = i;
        this.pkgName = context.getApplicationInfo().packageName;
        try {
            LidowFilterIconVersion = context.getPackageManager().getPackageInfo(this.pkgName, 0).versionCode * 10;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getmTextViewSeletedColor() {
        return this.mTextViewSeletedColor;
    }

    public void setmTextViewSeletedColor(int i) {
        this.mTextViewSeletedColor = i;
    }

    public int getmTextViewSeletedBackColor() {
        return this.mTextViewSeletedBackColor;
    }

    public void setmTextViewSeletedBackColor(int i) {
        this.mTextViewSeletedBackColor = i;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        final Holder holder;
        try {
            final DMWBRes item = getItem(i);
            Bitmap iconBitmap = item.getIconBitmap();
            ((GPUFilterRes) item).getFilterType().toString();
            boolean z = (item instanceof DMWBImageRes) && ((DMWBImageRes) item).getIsShowLikeIcon().booleanValue();
            if (view == null) {
                view2 = this.mInflater.inflate(R.layout.recyclerview_filteritem, viewGroup, false);
                try {
                    CSRoundedCornerImageView cSRoundedCornerImageView = (CSRoundedCornerImageView) view2.findViewById(R.id.item_icon);
                    CSRoundedCornerImageView cSRoundedCornerImageView2 = (CSRoundedCornerImageView) view2.findViewById(R.id.item_icon_filter);
                    ImageView imageView = (ImageView) view2.findViewById(R.id.imageLike);
                    TextView textView = (TextView) view2.findViewById(R.id.textView1);
                    TextView textView2 = (TextView) view2.findViewById(R.id.item_icon_select_view);
                    textView.setTextColor(this.mTextViewColor);
                    if (this.mTextViewBackColor != 0) {
                        textView.setBackgroundColor(this.mTextViewBackColor);
                    }
                    holder = new Holder();
                    holder.iconImageView = cSRoundedCornerImageView;
                    holder.iconfilterImageView = cSRoundedCornerImageView2;
                    holder.imgLike = imageView;
                    holder.tx_text = textView;
                    holder.item_icon_select_view = textView2;
                    textView2.setBackgroundColor(this.selectColor);
                    textView.setBackgroundColor(this.selectColor);
                    textView2.setVisibility(View.INVISIBLE);
                    view2.setTag(holder);
                    this.holderArray.add(holder);
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return view2;
                }
            } else {
                holder = (Holder) view.getTag();
                holder.iconImageView.setTag(item);
                this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewColor);
                this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewBackColor);
                view2 = view;
            }
            holder.dispose();
            if (z) {
                holder.imgLike.setSelected(true);
            } else {
                holder.imgLike.setSelected(false);
            }
            if (((GPUFilterRes) item).getName().equals("ORI")) {
                holder.imgLike.setVisibility(View.GONE);
            } else {
                holder.imgLike.setVisibility(View.VISIBLE);
            }
            holder.imgLike.setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view3) {
                    GPUFilterRes gPUFilterRes = (GPUFilterRes) item;
                    String name = gPUFilterRes.getName();
                    String gPUFilterType = gPUFilterRes.getFilterType().toString();
                    String iconFileName = gPUFilterRes.getIconFileName();
                    String str = DMPreferencesUtil.get(CSLidowFilterHrzListViewAdapter.this.mContext, "FilterLike", "IsFilterLike");
                    if (name == null || gPUFilterType == null) {
                        return;
                    }
                    if (str == null || "".equals(str)) {
                        Context context = CSLidowFilterHrzListViewAdapter.this.mContext;
                        DMPreferencesUtil.save(context, "FilterLike", "IsFilterLike", name + "," + gPUFilterType + "," + iconFileName);
                        gPUFilterRes.setIsShowLikeIcon(true);
                        view3.setSelected(true);
                        CSLidowFilterHrzListViewAdapter.this.notifyDataSetChanged();
                        return;
                    }
                    if (!str.contains(name + "," + gPUFilterType + "," + iconFileName)) {
                        Context context2 = CSLidowFilterHrzListViewAdapter.this.mContext;
                        DMPreferencesUtil.save(context2, "FilterLike", "IsFilterLike", str + "," + name + "," + gPUFilterType + "," + iconFileName);
                        gPUFilterRes.setIsShowLikeIcon(true);
                        view3.setSelected(true);
                        CSLidowFilterHrzListViewAdapter.this.notifyDataSetChanged();
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
                    DMPreferencesUtil.save(CSLidowFilterHrzListViewAdapter.this.mContext, "FilterLike", "IsFilterLike", replace2.replace(sb.toString(), ""));
                    gPUFilterRes.setIsShowLikeIcon(false);
                    view3.setSelected(false);
                    CSLidowFilterHrzListViewAdapter.this.notifyDataSetChanged();
                }
            });
            holder.tx_text.setText(item.getShowText());
            String str = DMPreferencesUtil.get(this.mContext, FilterIcon, "FilterIconVersion");
            File file = new File(this.mContext.getFilesDir().getPath() + "/" + FilterIcon);
            if (str == null || Integer.valueOf(str).intValue() < LidowFilterIconVersion) {
                delete(file);
                Context context = this.mContext;
                DMPreferencesUtil.save(context, FilterIcon, "FilterIconVersion", LidowFilterIconVersion + "");
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            holder.iconImageView.setImageBitmap(iconBitmap);
            if (this.isExpanded) {
                ((GPUFilterRes) item).getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.2
                    @Override // com.picspool.lib.resource.DMWBAsyncPostIconListener
                    public void postIcon(Bitmap bitmap) {
                        if (bitmap != null) {
                            holder.iconfilterImageView.setImageBitmap(bitmap);
                            holder.iconfilterImageView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
            this.posViewMap.put(Integer.valueOf(i), view2);
        } catch (Exception e2) {
            e2.printStackTrace();
            view2 = view;
        }
        return view2;
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File file2 : listFiles) {
                delete(file2);
            }
            file.delete();
        }
    }

    public void setSelectPosition(int i) {
        Holder holder;
        Holder holder2;
        this.selectedPos = i;
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder3 = (Holder) view.getTag();
            CSRoundedCornerImageView cSRoundedCornerImageView = holder3.iconImageView;
            ImageView imageView = this.mCurSelectedItem;
            if (cSRoundedCornerImageView != imageView) {
                if (imageView != null && (holder2 = this.mCurSelectedHolder) != null) {
                    holder2.tx_text.setTextColor(this.mTextViewColor);
                    this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewBackColor);
                    this.mCurSelectedHolder.item_icon_select_view.setVisibility(View.INVISIBLE);
                    TranslateAnimation translateAnimation = (TranslateAnimation) this.mCurSelectedHolder.tx_text.getAnimation();
                    if (translateAnimation != null) {
                        translateAnimation.setFillAfter(false);
                    }
                    this.mCurSelectedHolder.tx_text.setBackgroundColor(this.selectColor);
                }
                this.mCurSelectedItem = cSRoundedCornerImageView;
                this.mCurSelectedHolder = holder3;
            }
            if (this.mCurSelectedItem == null || (holder = this.mCurSelectedHolder) == null) {
                return;
            }
            holder.tx_text.setTextColor(this.mTextViewSeletedColor);
            this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewSeletedBackColor);
            openToolBarAnimation(this.mCurSelectedHolder.item_icon_select_view, this.mCurSelectedHolder.tx_text, this.mCurSelectedHolder.item_icon_select_view.getHeight());
        }
    }

    public void openToolBarAnimation(final View view, View view2, int i) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator ofInt = ValueAnimator.ofInt(view2.getMeasuredHeight(), i);
        ofInt.setDuration(500L);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.height = intValue;
                view.setLayoutParams(layoutParams);
            }
        });
        ofInt.start();
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (-(i - view2.getHeight())) / 2.0f);
        view2.setAnimation(translateAnimation);
        translateAnimation.setDuration(500L);
        translateAnimation.start();
        translateAnimation.setFillAfter(true);
        view2.setVisibility(View.VISIBLE);
        view2.setBackgroundDrawable(null);
    }

    public void setSelectBorderColor(int i) {
        this.mSelectBorderColor = i;
    }

    public int getSelectBorderColor() {
        return this.mSelectBorderColor;
    }

    public void dispose() {
        for (int i = 0; i < this.holderArray.size(); i++) {
            Holder holder = this.holderArray.get(i);
            holder.iconImageView.setImageBitmap(null);
            holder.iconfilterImageView.setImageBitmap(null);
            if (holder.iconBitmap != null && !holder.iconBitmap.isRecycled()) {
                holder.iconBitmap.recycle();
            }
            holder.iconBitmap = null;
        }
    }
}
