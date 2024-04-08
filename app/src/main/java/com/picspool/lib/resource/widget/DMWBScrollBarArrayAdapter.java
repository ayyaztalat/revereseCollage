package com.picspool.lib.resource.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.view.image.DMBorderImageView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMWBScrollBarArrayAdapter extends ArrayAdapter<DMWBRes> {
    private static final int MSG_FAILURE = 1;
    private static final int MSG_SUCCESS = 0;
    float borderWidth;
    private float circleBorderRadius;
    private int circleRadius;
    private int containHeightDp;
    int count;
    private Bitmap filterSrc;
    private List<Holder> holderArray;
    private Bitmap imageBorderBitmap;
    private int imageBorderHeightDp;
    private int imageBorderWidthDp;
    private boolean isBottomSelState;
    private boolean isCircle;
    private boolean isCircleBorderState;
    private boolean isFillet;
    private boolean isSetImageViewScaleType;
    private boolean isSetScaleType;
    private boolean isShowImageBorder;
    private boolean isWithHalfShow;
    private ImageView.ScaleType mBorderViewScaleType;
    private Context mContext;
    Holder mCurSelectedHolder;
    DMBorderImageView mCurSelectedItem;
    private Handler mHandler;
    private ImageView.ScaleType mImageViewScaleType;
    private LayoutInflater mInflater;
    private int mSelectBorderColor;
    private int mSelectViewWidthDp;
    private int mTextViewBackColor;
    private int mTextViewColor;
    private int mTextViewHeight;
    private int mTextViewSeletedBackColor;
    private int mTextViewSeletedColor;
    private int mTextViewTextSize;
    private int mTextViewWidth;
    private int mViewWidthDp;
    private String pkgName;
    HashMap<Integer, View> posViewMap;
    public int selectedPos;
    private int textMarginBottomDp;
    private int textViewMarginBottom;

    /* loaded from: classes3.dex */
    private class Holder {
        public Bitmap iconBitmap;
        public DMBorderImageView iconImageView;
        public ImageView imageNew;
        public ImageView imgBackGround;
        public ImageView imgDownload;
        public ImageView imgItemSelect;
        public ImageView imgLike;
        public ProgressBar progressBar;
        public TextView tx_text;

        private Holder() {
        }
    }

    @SuppressLint("HandlerLeak")
    public DMWBScrollBarArrayAdapter(Context context, DMWBRes[] dMWBResArr) {
        super(context, R.layout.dm_res_view_widget_selectitem, dMWBResArr);
        this.selectedPos = -1;
        this.mSelectBorderColor = Color.rgb(0, 235, 232);
        this.posViewMap = new HashMap<>();
        this.imageBorderWidthDp = 52;
        this.imageBorderHeightDp = 52;
        this.containHeightDp = 60;
        this.textMarginBottomDp = 0;
        this.holderArray = new ArrayList();
        this.mTextViewColor = ViewCompat.MEASURED_STATE_MASK;
        this.mTextViewBackColor = 0;
        this.mTextViewSeletedColor = -1;
        this.mTextViewSeletedBackColor = -7829368;
        this.mTextViewWidth = 52;
        this.mTextViewHeight = -1;
        this.mTextViewTextSize = 11;
        this.isWithHalfShow = false;
        this.mViewWidthDp = 0;
        this.mBorderViewScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mImageViewScaleType = ImageView.ScaleType.FIT_CENTER;
        this.isSetScaleType = false;
        this.isFillet = false;
        this.isBottomSelState = false;
        this.mSelectViewWidthDp = -1;
        this.isCircle = false;
        this.circleRadius = 0;
        this.isCircleBorderState = false;
        this.circleBorderRadius = 500.0f;
        this.borderWidth = 5.0f;
        this.isSetImageViewScaleType = false;
        this.textViewMarginBottom = 6;
        this.count = 0;
        this.isShowImageBorder = false;
        this.imageBorderBitmap = null;
        this.mHandler = new Handler() { // from class: com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 0 && message.what == 1) {
                    View view = DMWBScrollBarArrayAdapter.this.posViewMap.get(Integer.valueOf(Integer.parseInt(message.obj.toString())));
                    if (view != null) {
                        Holder holder = (Holder) view.getTag();
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.imgBackGround.setVisibility(View.VISIBLE);
                        holder.imgDownload.setVisibility(View.VISIBLE);
                        Toast.makeText(DMWBScrollBarArrayAdapter.this.mContext, "Download failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        this.count = dMWBResArr.length;
        this.mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.pkgName = context.getApplicationInfo().packageName;
    }

    public float getBorderWidth() {
        return this.borderWidth;
    }

    public void setBorderWidth(float f) {
        this.borderWidth = f;
    }

    public void setBottomSelState(boolean z) {
        this.isBottomSelState = z;
    }

    public boolean getBottomSelState() {
        return this.isBottomSelState;
    }

    public boolean getCircleBorderState() {
        return this.isCircleBorderState;
    }

    public float getCircleBorderRadius() {
        return this.circleBorderRadius;
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

    public void setSelectItemCircleBorderState(boolean z, float f) {
        this.isCircleBorderState = z;
        this.circleBorderRadius = f;
    }

    public void setCircleRadius(boolean z, int i) {
        this.isCircle = z;
        this.circleRadius = i;
    }

    public boolean isCircle() {
        return this.isCircle;
    }

    public void setCircle(boolean z) {
        this.isCircle = z;
    }

    public int getCircleRadius() {
        return this.circleRadius;
    }

    public void setCircleRadius(int i) {
        this.circleRadius = i;
    }

    public void setFilletState(boolean z) {
        this.isFillet = z;
    }

    public boolean getFilletState() {
        return this.isFillet;
    }

    public void setIsWithHalfShow(boolean z) {
        this.isWithHalfShow = z;
    }

    public boolean isWithHalfShow() {
        return this.isWithHalfShow;
    }

    public void setImageBorderViewLayout(int i, int i2) {
        this.imageBorderWidthDp = i;
        this.imageBorderHeightDp = i2;
    }

    public void setImageBorderViewLayout(int i, int i2, int i3) {
        this.containHeightDp = i;
        this.imageBorderWidthDp = i2;
        this.imageBorderHeightDp = i3;
    }

    public void setIsShowImageBorder(boolean z, Bitmap bitmap) {
        this.isShowImageBorder = z;
        this.imageBorderBitmap = bitmap;
    }

    public void setImageBorderViewScaleType(ImageView.ScaleType scaleType) {
        this.isSetScaleType = true;
        this.mBorderViewScaleType = scaleType;
    }

    public void setViewWidthDp(int i) {
        this.mViewWidthDp = i;
    }

    public void setTextViewColor(int i) {
        this.mTextViewColor = i;
    }

    public void setTextViewBackColor(int i) {
        this.mTextViewBackColor = i;
    }

    public void setTextViewWidthDp(int i) {
        this.mTextViewWidth = i;
    }

    public void setTextViewHeightDp(int i) {
        this.mTextViewHeight = i;
    }

    public void setTextViewTextSize(int i) {
        this.mTextViewTextSize = i;
    }

    public void setTextMarginBottomDp(int i) {
        this.textMarginBottomDp = i;
    }

    public void setSelectViewWidthDp(int i) {
        this.mSelectViewWidthDp = i;
    }

    public void setImageViewScaleType(ImageView.ScaleType scaleType) {
        this.isSetImageViewScaleType = true;
        this.mImageViewScaleType = scaleType;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        final Holder holder;
        Bitmap circleBitmap;
        int i2;
        float f = 0;
        try {
            final DMWBRes dMWBRes = (DMWBRes) getItem(i);
            dMWBRes.setContext(this.mContext);
            boolean z = !(dMWBRes instanceof DMWBImageRes) || ((DMWBImageRes) dMWBRes).isImageResInLocal(getContext());
            boolean z2 = (dMWBRes instanceof DMWBImageRes) && ((DMWBImageRes) dMWBRes).getIsShowLikeIcon().booleanValue();
            if (view == null) {
                view2 = this.mInflater.inflate(R.layout.dm_res_view_widget_selectitem, viewGroup, false);
                try {
                    DMBorderImageView dMBorderImageView = (DMBorderImageView) view2.findViewById(R.id.item_icon);
                    dMBorderImageView.setCircleRadius(this.isCircle, this.circleRadius);
                    if (this.isFillet) {
                        dMBorderImageView.setFilletState(true);
                    }
                    if (this.isSetScaleType) {
                        dMBorderImageView.setScaleType(this.mBorderViewScaleType);
                    }
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.height = DMScreenInfoUtil.dip2px(getContext(), this.containHeightDp);
                        if (DMScreenInfoUtil.dip2px(getContext(), this.imageBorderWidthDp + 8) > layoutParams.width) {
                            layoutParams.width = DMScreenInfoUtil.dip2px(getContext(), this.imageBorderWidthDp + 8);
                        }
                        if (this.mViewWidthDp > 0) {
                            layoutParams.width = DMScreenInfoUtil.dip2px(getContext(), this.mViewWidthDp);
                        }
                        if (this.isWithHalfShow) {
                            float screenWidth = DMScreenInfoUtil.screenWidth(this.mContext);
                            int i3 = layoutParams.width;
                            while (true) {
                                double d = (screenWidth / i3) - ((int) f);
                                if (d > 0.4d && d < 0.6d) {
                                    break;
                                }
                                i3++;
                            }
                            layoutParams.width = i3;
                        }
                    }
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) dMBorderImageView.getLayoutParams();
                    if (layoutParams2 != null) {
                        layoutParams2.width = DMScreenInfoUtil.dip2px(getContext(), this.imageBorderWidthDp);
                        layoutParams2.height = DMScreenInfoUtil.dip2px(getContext(), this.imageBorderHeightDp);
                    }
                    if (this.isSetImageViewScaleType) {
                        dMBorderImageView.setScaleType(this.mImageViewScaleType);
                    }
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) ((FrameLayout) view2.findViewById(R.id.item_layout)).getLayoutParams();
                    if (layoutParams3 != null) {
                        layoutParams3.width = DMScreenInfoUtil.dip2px(getContext(), this.imageBorderWidthDp);
                        layoutParams3.height = DMScreenInfoUtil.dip2px(getContext(), this.imageBorderHeightDp);
                    }
                    if (dMWBRes.getIsShowText().booleanValue()) {
                        layoutParams2.bottomMargin = DMScreenInfoUtil.dip2px(getContext(), this.textViewMarginBottom);
                        layoutParams3.bottomMargin = DMScreenInfoUtil.dip2px(getContext(), this.textViewMarginBottom);
                    }
                    ProgressBar progressBar = (ProgressBar) view2.findViewById(R.id.progressBar);
                    ImageView imageView = (ImageView) view2.findViewById(R.id.imageDownload);
                    ImageView imageView2 = (ImageView) view2.findViewById(R.id.imageBackGround);
                    ImageView imageView3 = (ImageView) view2.findViewById(R.id.imgItemSelect);
                    ImageView imageView4 = (ImageView) view2.findViewById(R.id.imageLike);
                    TextView textView = (TextView) view2.findViewById(R.id.textView1);
                    if (textView != null) {
                        textView.setTextColor(this.mTextViewColor);
                        if (this.mTextViewBackColor != 0) {
                            textView.setBackgroundColor(this.mTextViewBackColor);
                        }
                        textView.setWidth(DMScreenInfoUtil.dip2px(getContext(), this.mTextViewWidth));
                        textView.setTextSize(this.mTextViewTextSize);
                        if (this.mTextViewHeight > 0) {
                            textView.setHeight(DMScreenInfoUtil.dip2px(getContext(), this.mTextViewHeight));
                        }
                        if (dMWBRes.getIsShowText().booleanValue()) {
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.INVISIBLE);
                        }
                        if (this.textMarginBottomDp != 0) {
                            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams4.bottomMargin = DMScreenInfoUtil.dip2px(getContext(), this.textMarginBottomDp);
                            textView.setLayoutParams(layoutParams4);
                        }
                    }
                    if (this.mSelectViewWidthDp > 0) {
                        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) imageView3.getLayoutParams();
                        layoutParams5.width = DMScreenInfoUtil.dip2px(getContext(), this.mSelectViewWidthDp);
                        imageView3.setLayoutParams(layoutParams5);
                    }
                    dMBorderImageView.setTag(dMWBRes);
                    holder = new Holder();
                    holder.iconImageView = dMBorderImageView;
                    holder.progressBar = progressBar;
                    holder.imgDownload = imageView;
                    holder.imgBackGround = imageView2;
                    holder.imgItemSelect = imageView3;
                    holder.imgLike = imageView4;
                    holder.tx_text = textView;
                    if (this.selectedPos == i) {
                        this.mCurSelectedItem = holder.iconImageView;
                        this.mCurSelectedHolder = holder;
                        if (!this.isBottomSelState) {
                            holder.iconImageView.setBorderColor(this.mSelectBorderColor);
                            holder.iconImageView.setShowBorder(true);
                            holder.iconImageView.setBorderWidth(this.borderWidth);
                            holder.iconImageView.setShowImageBorder(this.isShowImageBorder, this.imageBorderBitmap);
                            if (this.isCircleBorderState) {
                                holder.iconImageView.setCircleBorder(true, this.circleBorderRadius);
                            }
                            holder.iconImageView.invalidate();
                        } else if (holder.tx_text != null) {
                            this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewSeletedColor);
                            this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewSeletedBackColor);
                        }
                    }
                    if (z) {
                        imageView.setVisibility(View.INVISIBLE);
                        holder.imgBackGround.setVisibility(View.INVISIBLE);
                        setAlphaForView(imageView, 0.0f);
                        setAlphaForView(imageView2, 0.0f);
                        i2 = 0;
                    } else {
                        i2 = 0;
                        imageView.setVisibility(View.VISIBLE);
                        holder.imgBackGround.setVisibility(View.VISIBLE);
                        setAlphaForView(imageView, 0.5f);
                        setAlphaForView(imageView2, 0.2f);
                    }
                    if (z2) {
                        holder.imgLike.setVisibility(View.VISIBLE);
                    } else {
                        holder.imgLike.setVisibility(View.INVISIBLE);
                    }
                    view2.setTag(holder);
                    this.holderArray.add(holder);
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return view2;
                }
            } else {
                holder = (Holder) view.getTag();
                holder.iconImageView.setTag(dMWBRes);
                holder.iconImageView.setCircleRadius(this.isCircle, this.circleRadius);
                if (this.selectedPos != i) {
                    if (this.isBottomSelState) {
                        if (this.mCurSelectedHolder != null && this.mCurSelectedHolder.tx_text != null) {
                            this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewColor);
                            this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewBackColor);
                        }
                    } else {
                        holder.iconImageView.setShowBorder(false);
                    }
                } else {
                    this.mCurSelectedItem = holder.iconImageView;
                    if (this.isBottomSelState) {
                        if (this.mCurSelectedHolder != null && this.mCurSelectedHolder.tx_text != null) {
                            this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewSeletedColor);
                            this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewSeletedBackColor);
                        }
                    } else {
                        holder.iconImageView.setBorderColor(this.mSelectBorderColor);
                        holder.iconImageView.setShowBorder(true);
                        holder.iconImageView.setBorderWidth(this.borderWidth);
                        holder.iconImageView.setShowImageBorder(this.isShowImageBorder, this.imageBorderBitmap);
                        if (this.isCircleBorderState) {
                            holder.iconImageView.setCircleBorder(true, this.circleBorderRadius);
                        }
                    }
                }
                holder.iconImageView.setImageBitmap(null);
                if (holder.iconBitmap != this.filterSrc && holder.iconBitmap != null && !holder.iconBitmap.isRecycled()) {
                    holder.iconBitmap.recycle();
                }
                holder.iconBitmap = null;
                ImageView imageView5 = holder.imgDownload;
                ProgressBar progressBar2 = holder.progressBar;
                if (imageView5 != null) {
                    if (z) {
                        imageView5.setVisibility(View.INVISIBLE);
                        holder.imgBackGround.setVisibility(View.INVISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);
                    } else {
                        imageView5.setVisibility(View.VISIBLE);
                        holder.imgBackGround.setVisibility(View.VISIBLE);
                    }
                }
                if (z2) {
                    holder.imgLike.setVisibility(View.VISIBLE);
                } else {
                    holder.imgLike.setVisibility(View.INVISIBLE);
                }
                view2 = view;
            }
            if (dMWBRes instanceof DMWBColorRes) {
                holder.iconImageView.setBackgroundColor(((DMWBColorRes) dMWBRes).getColorValue());
                holder.iconBitmap = null;
                if (holder.tx_text != null) {
                    if (dMWBRes.getIsShowText().booleanValue()) {
                        if (dMWBRes.getTextColor() != 0) {
                            holder.tx_text.setTextColor(dMWBRes.getTextColor());
                        }
                        holder.tx_text.setText(dMWBRes.getShowText());
                    } else {
                        holder.tx_text.setText("");
                    }
                }
            } else {
                Bitmap bitmap = holder.iconBitmap;
                Bitmap iconBitmap = dMWBRes.getIconBitmap();
                if (bitmap != this.filterSrc && bitmap != null && !bitmap.isRecycled()) {
                    holder.iconImageView.setImageBitmap(null);
                    bitmap.recycle();
                }
                if (holder.tx_text != null) {
                    if (dMWBRes.getIsShowText().booleanValue()) {
                        if (dMWBRes.getTextColor() != 0) {
                            holder.tx_text.setTextColor(dMWBRes.getTextColor());
                        }
                        holder.tx_text.setText(dMWBRes.getShowText());
                    } else {
                        holder.tx_text.setText("");
                    }
                }
                if (dMWBRes.getAsyncIcon().booleanValue()) {
                    this.filterSrc = iconBitmap;
                    dMWBRes.getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter.1
                        @Override // com.picspool.lib.resource.DMWBAsyncPostIconListener
                        public void postIcon(Bitmap bitmap2) {
                            Bitmap circleBitmap2;
                            if (bitmap2 != null) {
                                if (dMWBRes.isCircle() && (circleBitmap2 = DMWBScrollBarArrayAdapter.this.getCircleBitmap(bitmap2)) != null && !circleBitmap2.isRecycled()) {
                                    if (bitmap2 != null && !bitmap2.isRecycled()) {
                                        bitmap2.recycle();
                                    }
                                    bitmap2 = circleBitmap2;
                                }
                                holder.iconImageView.setImageBitmap(bitmap2);
                                holder.iconBitmap = bitmap2;
                            }
                        }
                    });
                } else {
                    if (dMWBRes.isCircle() && (circleBitmap = getCircleBitmap(iconBitmap)) != null && !circleBitmap.isRecycled()) {
                        if (iconBitmap != null && !iconBitmap.isRecycled()) {
                            iconBitmap.recycle();
                        }
                        iconBitmap = circleBitmap;
                    }
                    holder.iconImageView.setImageBitmap(iconBitmap);
                    holder.iconBitmap = iconBitmap;
                }
            }
            if (z) {
                setAlphaForView(holder.imgDownload, 0.0f);
                setAlphaForView(holder.imgBackGround, 0.0f);
            } else {
                setAlphaForView(holder.imgDownload, 0.5f);
                setAlphaForView(holder.imgBackGround, 0.2f);
            }
            this.posViewMap.put(Integer.valueOf(i), view2);
        } catch (Exception e2) {
            e2.printStackTrace();
            view2 = view;
        }
        return view2;
    }

    public void setSelectBorderColor(int i) {
        this.mSelectBorderColor = i;
    }

    public int getSelectBorderColor() {
        return this.mSelectBorderColor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Canvas canvas;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        int height = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getHeight() : bitmap.getWidth();
        if (height > 90) {
            height = 90;
        }
        Bitmap cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(bitmap, height, height);
        if (cropCenterScaleBitmap != null) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = cropCenterScaleBitmap;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            try {
                canvas = new Canvas(createBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                if (createBitmap != null && !createBitmap.isRecycled()) {
                    createBitmap.recycle();
                }
                createBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                canvas = new Canvas(createBitmap);
            }
            Canvas canvas2 = canvas;
            Rect rect = new Rect(0, 0, height, height);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas2.drawARGB(0, 255, 255, 255);
            paint.setColor(-12434878);
            bitmap.getWidth();
            RectF rectF = new RectF(10.0f, 10.0f, bitmap.getWidth() - 10, bitmap.getHeight() - 10);
            bitmap.getWidth();
            canvas2.drawArc(rectF, 0.0f, 360.0f, true, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas2.drawBitmap(bitmap, rect, rect, paint);
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setStrokeWidth(3.0f);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setColor(-1);
            float width = bitmap.getWidth() / 2.0f;
            canvas2.drawCircle(width, width, width - 5.0f, paint2);
            return createBitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void setSelectPosition(int i) {
        this.selectedPos = i;
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder = (Holder) view.getTag();
            DMBorderImageView dMBorderImageView = holder.iconImageView;
            DMBorderImageView dMBorderImageView2 = this.mCurSelectedItem;
            if (dMBorderImageView != dMBorderImageView2) {
                if (dMBorderImageView2 != null) {
                    if (this.isBottomSelState) {
                        Holder holder2 = this.mCurSelectedHolder;
                        if (holder2 != null && holder2.tx_text != null) {
                            this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewColor);
                            this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewBackColor);
                        }
                    } else {
                        dMBorderImageView2.setShowBorder(false);
                        this.mCurSelectedItem.setShowImageBorder(this.isShowImageBorder, this.imageBorderBitmap);
                        this.mCurSelectedItem.invalidate();
                    }
                }
                this.mCurSelectedItem = dMBorderImageView;
                this.mCurSelectedHolder = holder;
            }
            DMBorderImageView dMBorderImageView3 = this.mCurSelectedItem;
            if (dMBorderImageView3 != null) {
                dMBorderImageView3.setBorderColor(this.mSelectBorderColor);
                this.mCurSelectedItem.setBorderWidth(this.borderWidth);
                if (this.isBottomSelState) {
                    Holder holder3 = this.mCurSelectedHolder;
                    if (holder3 == null || holder3.tx_text == null) {
                        return;
                    }
                    this.mCurSelectedHolder.tx_text.setTextColor(this.mTextViewSeletedColor);
                    this.mCurSelectedHolder.tx_text.setBackgroundColor(this.mTextViewSeletedBackColor);
                    return;
                }
                this.mCurSelectedItem.setShowBorder(true);
                this.mCurSelectedItem.setShowImageBorder(this.isShowImageBorder, this.imageBorderBitmap);
                boolean z = this.isCircleBorderState;
                if (z) {
                    this.mCurSelectedItem.setCircleBorder(z, this.circleBorderRadius);
                }
                this.mCurSelectedItem.invalidate();
            }
        }
    }

    public void changePositionIconRes(int i, Bitmap bitmap) {
        this.selectedPos = i;
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view == null || this.mCurSelectedItem == null) {
            return;
        }
        Holder holder = (Holder) view.getTag();
        if (holder.iconImageView.image != null || !holder.iconImageView.image.isRecycled()) {
            holder.iconImageView.image.recycle();
        }
        holder.iconImageView.setImageBitmap(bitmap);
    }

    public void setViewInDownloading(int i) {
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder = (Holder) view.getTag();
            holder.progressBar.setVisibility(View.VISIBLE);
            ImageView imageView = holder.imgDownload;
            if (imageView != null) {
                imageView.setVisibility(View.INVISIBLE);
                holder.imgBackGround.setVisibility(View.VISIBLE);
                setAlphaForView(imageView, 0.0f);
                setAlphaForView(holder.imgBackGround, 0.0f);
            }
        }
    }

    public void setViewInDownloadOk(int i) {
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder = (Holder) view.getTag();
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.imgBackGround.setVisibility(View.INVISIBLE);
        }
    }

    public void setViewInDownloadFail(int i) {
        this.mHandler.obtainMessage(1, Integer.valueOf(i)).sendToTarget();
    }

    public void dispose() {
        Bitmap bitmap = this.filterSrc;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.filterSrc.recycle();
        }
        for (int i = 0; i < this.holderArray.size(); i++) {
            Holder holder = this.holderArray.get(i);
            holder.iconImageView.setImageBitmap(null);
            if (holder.iconBitmap != null && !holder.iconBitmap.isRecycled()) {
                holder.iconBitmap.recycle();
            }
            holder.iconBitmap = null;
        }
        Bitmap bitmap2 = this.imageBorderBitmap;
        if (bitmap2 != null) {
            if (!bitmap2.isRecycled()) {
                this.imageBorderBitmap.recycle();
            }
            this.imageBorderBitmap = null;
        }
    }

    public void changePositionIconResAnyWay(int i, Bitmap bitmap) {
        this.selectedPos = i;
        View view = this.posViewMap.get(Integer.valueOf(i));
        if (view != null) {
            Holder holder = (Holder) view.getTag();
            if (holder.iconImageView.image != null || !holder.iconImageView.image.isRecycled()) {
                holder.iconImageView.image.recycle();
            }
            holder.iconImageView.setImageBitmap(bitmap);
        }
    }

    private void setAlphaForView(View view, float f) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
        alphaAnimation.setDuration(0L);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    public int getTextViewMarginBottom() {
        return this.textViewMarginBottom;
    }

    public void setTextViewMarginBottom(int i) {
        this.textViewMarginBottom = i;
    }
}
