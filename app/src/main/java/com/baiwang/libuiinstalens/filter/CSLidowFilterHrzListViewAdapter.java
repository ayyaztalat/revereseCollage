package com.baiwang.libuiinstalens.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.bitmap.output.save.DMSaveToSD;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class CSLidowFilterHrzListViewAdapter extends ArrayAdapter<DMWBRes> {
    public static final int LidowFilterIconVersion = 4;
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
    public int selectedPos;
    private int textMarginBottomDp;
    private int textViewMarginBottom;

    /* loaded from: classes.dex */
    private class Holder {
        public Bitmap iconBitmap;
        public ImageView iconImageView;
        public ImageView iconfilterImageView;
        public ImageView imgLike;
        public TextView tx_text;

        private Holder() {
        }

        public void dispose() {
            recycleImageView(iconImageView);
            recycleImageView(iconfilterImageView);
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
        return isExpanded;
    }

    public void setExpanded(boolean z) {
        isExpanded = z;
    }

    public CSLidowFilterHrzListViewAdapter(Context context, DMWBRes[] dMWBResArr) {
        super(context, R.layout.recyclerview_filteritem, dMWBResArr);
        selectedPos = -1;
        mSelectBorderColor = Color.rgb(0, 235, 232);
        posViewMap = new HashMap<>();
        imageBorderWidthDp = 52;
        imageBorderHeightDp = 52;
        containHeightDp = 60;
        textMarginBottomDp = 0;
        holderArray = new ArrayList();
        mTextViewColor = ViewCompat.MEASURED_STATE_MASK;
        mTextViewBackColor = -7829368;
        mTextViewSeletedColor = -1;
        mTextViewSeletedBackColor = -7829368;
        isWithHalfShow = false;
        mViewWidthDp = 0;
        mBorderViewScaleType = ImageView.ScaleType.FIT_CENTER;
        mImageViewScaleType = ImageView.ScaleType.FIT_CENTER;
        isSetScaleType = false;
        isFillet = false;
        isBottomSelState = false;
        mSelectViewWidthDp = -1;
        isCircleBorderState = false;
        circleBorderRadius = 500.0f;
        borderWidth = 5.0f;
        isSetImageViewScaleType = false;
        textViewMarginBottom = 6;
        count = 0;
        isShowImageBorder = false;
        imageBorderBitmap = null;
        isExpanded = false;
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        pkgName = context.getApplicationInfo().packageName;
    }

    public int getmTextViewSeletedColor() {
        return mTextViewSeletedColor;
    }

    public void setmTextViewSeletedColor(int i) {
        mTextViewSeletedColor = i;
    }

    public int getmTextViewSeletedBackColor() {
        return mTextViewSeletedBackColor;
    }

    public void setmTextViewSeletedBackColor(int i) {
        mTextViewSeletedBackColor = i;
    }

    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        final Holder holder;
        try {
            DMWBRes item = getItem(i);
            assert item != null;
            Bitmap iconBitmap = item.getIconBitmap();
            final String gPUFilterType = ((GPUFilterRes) item).getFilterType().toString();
            boolean z = ((DMWBImageRes) item).getIsShowLikeIcon();
            if (view == null) {
                view = mInflater.inflate(R.layout.recyclerview_filteritem, viewGroup, false);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_icon);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.item_icon_filter);
                ImageView imageView3 = (ImageView) view.findViewById(R.id.imageLike);
                TextView textView = (TextView) view.findViewById(R.id.textView1);
                textView.setTextColor(mTextViewColor);
                if (mTextViewBackColor != 0) {
                    textView.setBackgroundColor(mTextViewBackColor);
                }
                holder = new Holder();
                holder.iconImageView = imageView;
                holder.iconfilterImageView = imageView2;
                holder.imgLike = imageView3;
                holder.tx_text = textView;
                if (selectedPos == i) {
                    mCurSelectedItem = holder.iconImageView;
                    mCurSelectedHolder = holder;
                    holder.tx_text.setTextColor(mTextViewSeletedColor);
                    mCurSelectedHolder.tx_text.setBackgroundColor(mTextViewSeletedBackColor);
                }
                view.setTag(holder);
                holderArray.add(holder);
            } else {
                holder = (Holder) view.getTag();
                holder.iconImageView.setTag(item);
                if (selectedPos != i) {
                    mCurSelectedHolder.tx_text.setTextColor(mTextViewColor);
                    mCurSelectedHolder.tx_text.setBackgroundColor(mTextViewBackColor);
                } else {
                    mCurSelectedItem = holder.iconImageView;
                    mCurSelectedHolder.tx_text.setTextColor(mTextViewSeletedColor);
                    mCurSelectedHolder.tx_text.setBackgroundColor(mTextViewSeletedBackColor);
                }
            }
            holder.dispose();
            if (z) {
                holder.imgLike.setVisibility(View.VISIBLE);
            } else {
                holder.imgLike.setVisibility(View.INVISIBLE);
            }
            holder.tx_text.setText(item.getShowText());
            String str = DMPreferencesUtil.get(mContext, com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.FilterIcon, "FilterIconVersion");
            final String str2 = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getPath() + "/LidowFilterIcon";
            File file = new File(str2);
            if (str == null || Integer.valueOf(str) < LidowFilterIconVersion) {
                delete(file);
                DMPreferencesUtil.save(mContext, com.photo.editor.square.splash.view.filterbar.CSLidowFilterHrzListViewAdapter.FilterIcon, "FilterIconVersion", String.valueOf(LidowFilterIconVersion));
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            Bitmap imageFromSDFile = DMBitmapUtil.getImageFromSDFile(mContext, str2 + "/" + gPUFilterType + ".abc");
            if (imageFromSDFile != null) {
                if ("NOFILTER".equals(gPUFilterType)) {
                    holder.iconImageView.setImageBitmap(iconBitmap);
                } else {
                    holder.iconImageView.setImageBitmap(imageFromSDFile);
                }
            } else {
                holder.iconImageView.setImageBitmap(iconBitmap);
                if (isExpanded) {
                    item.getAsyncIconBitmap(bitmap -> {
                        if (bitmap != null) {
                            holder.iconfilterImageView.setImageBitmap(bitmap);
                            holder.iconfilterImageView.setVisibility(View.VISIBLE);
                            DMSaveToSD.saveImage(mContext, bitmap, str2, gPUFilterType + ".abc", Bitmap.CompressFormat.JPEG, new DMSaveDoneListener() {
                                @Override
                                public void onSaveDone(String str4, Uri uri) {
                                }

                                @Override
                                public void onSavingException(Exception exc) {
                                }
                            });
                        }
                    });
                }
            }
            posViewMap.put(i, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert view != null;
        return view;
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
        selectedPos = i;
        View view = posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder3 = (Holder) view.getTag();
            ImageView imageView = holder3.iconImageView;
            ImageView imageView2 = mCurSelectedItem;
            if (imageView != imageView2) {
                if (imageView2 != null && (holder2 = mCurSelectedHolder) != null) {
                    holder2.tx_text.setTextColor(mTextViewColor);
                    mCurSelectedHolder.tx_text.setBackgroundColor(mTextViewBackColor);
                }
                mCurSelectedItem = imageView;
                mCurSelectedHolder = holder3;
            }
            if (mCurSelectedItem == null || (holder = mCurSelectedHolder) == null) {
                return;
            }
            holder.tx_text.setTextColor(mTextViewSeletedColor);
            mCurSelectedHolder.tx_text.setBackgroundColor(mTextViewSeletedBackColor);
        }
    }

    public void setSelectBorderColor(int i) {
        mSelectBorderColor = i;
    }

    public int getSelectBorderColor() {
        return mSelectBorderColor;
    }

    public void dispose() {
        for (int i = 0; i < holderArray.size(); i++) {
            Holder holder = holderArray.get(i);
            holder.iconImageView.setImageBitmap(null);
            holder.iconfilterImageView.setImageBitmap(null);
            if (holder.iconBitmap != null && !holder.iconBitmap.isRecycled()) {
                holder.iconBitmap.recycle();
            }
            holder.iconBitmap = null;
        }
    }
}
