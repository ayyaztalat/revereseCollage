package com.picspool.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.viewpager.widget.ViewPager;

import com.sky.testproject.R;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class DMTitlePageIndicator extends View implements DMPageIndicator {
    private static final float BOLD_FADE_PERCENTAGE = 0.05f;
    private static final String EMPTY_TITLE = "";
    private static final int INVALID_POINTER = -1;
    private static final float SELECTION_FADE_PERCENTAGE = 0.25f;
    private int mActivePointerId;
    private boolean mBoldText;
    private final Rect mBounds;
    private OnCenterItemClickListener mCenterItemClickListener;
    private float mClipPadding;
    private int mColorSelected;
    private int mColorText;
    private int mCurrentPage;
    private float mFooterIndicatorHeight;
    private IndicatorStyle mFooterIndicatorStyle;
    private float mFooterIndicatorUnderlinePadding;
    private float mFooterLineHeight;
    private float mFooterPadding;
    private boolean mIsDragging;
    private float mLastMotionX;
    private LinePosition mLinePosition;
    private ViewPager.OnPageChangeListener mListener;
    private float mPageOffset;
    private final Paint mPaintFooterIndicator;
    private final Paint mPaintFooterLine;
    private final Paint mPaintText;
    private Path mPath;
    private int mScrollState;
    private float mTitlePadding;
    private float mTopPadding;
    private int mTouchSlop;
    private ViewPager mViewPager;

    /* loaded from: classes3.dex */
    public interface OnCenterItemClickListener {
        void onCenterItemClick(int i);
    }

    /* loaded from: classes3.dex */
    public enum IndicatorStyle {
        None(0),
        Triangle(1),
        Underline(2);
        
        public final int value;

        IndicatorStyle(int i) {
            this.value = i;
        }

        public static IndicatorStyle fromValue(int i) {
            IndicatorStyle[] values;
            for (IndicatorStyle indicatorStyle : values()) {
                if (indicatorStyle.value == i) {
                    return indicatorStyle;
                }
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public enum LinePosition {
        Bottom(0),
        Top(1);
        
        public final int value;

        LinePosition(int i) {
            this.value = i;
        }

        public static LinePosition fromValue(int i) {
            LinePosition[] values;
            for (LinePosition linePosition : values()) {
                if (linePosition.value == i) {
                    return linePosition;
                }
            }
            return null;
        }
    }

    public DMTitlePageIndicator(Context context) {
        this(context, null);
    }

    public DMTitlePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiTitlePageIndicatorStyle);
    }

    public DMTitlePageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentPage = -1;
        this.mPaintText = new Paint();
        this.mPath = new Path();
        this.mBounds = new Rect();
        this.mPaintFooterLine = new Paint();
        this.mPaintFooterIndicator = new Paint();
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (isInEditMode()) {
            return;
        }
        Resources resources = getResources();
        int color = resources.getColor(R.color.default_title_indicator_footer_color);
        float dimension = resources.getDimension(R.dimen.default_title_indicator_footer_line_height);
        int integer = resources.getInteger(R.integer.default_title_indicator_footer_indicator_style);
        float dimension2 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
        float dimension3 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
        float dimension4 = resources.getDimension(R.dimen.default_title_indicator_footer_padding);
        int integer2 = resources.getInteger(R.integer.default_title_indicator_line_position);
        int color2 = resources.getColor(R.color.default_title_indicator_selected_color);
        boolean z = resources.getBoolean(R.bool.default_title_indicator_selected_bold);
        int color3 = resources.getColor(R.color.default_title_indicator_text_color);
        float dimension5 = resources.getDimension(R.dimen.default_title_indicator_text_size);
        float dimension6 = resources.getDimension(R.dimen.default_title_indicator_title_padding);
        float dimension7 = resources.getDimension(R.dimen.default_title_indicator_clip_padding);
        float dimension8 = resources.getDimension(R.dimen.default_title_indicator_top_padding);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DMTitlePageIndicator, i, 0);
        this.mFooterLineHeight = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_indicator_footerLineHeight, dimension);
        this.mFooterIndicatorStyle = IndicatorStyle.fromValue(obtainStyledAttributes.getInteger(R.styleable.DMTitlePageIndicator_indicator_footerIndicatorStyle, integer));
        this.mFooterIndicatorHeight = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_indicator_footerIndicatorHeight, dimension2);
        this.mFooterIndicatorUnderlinePadding = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_footerIndicatorUnderlinePadding, dimension3);
        this.mFooterPadding = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_footerPadding, dimension4);
        this.mLinePosition = LinePosition.fromValue(obtainStyledAttributes.getInteger(R.styleable.DMTitlePageIndicator_linePosition, integer2));
        this.mTopPadding = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_topPadding, dimension8);
        this.mTitlePadding = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_titlePadding, dimension6);
        this.mClipPadding = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_clipPadding, dimension7);
        this.mColorSelected = obtainStyledAttributes.getColor(R.styleable.DMTitlePageIndicator_indicator_selectedColor, color2);
        this.mColorText = obtainStyledAttributes.getColor(R.styleable.DMTitlePageIndicator_android_textColor, color3);
        this.mBoldText = obtainStyledAttributes.getBoolean(R.styleable.DMTitlePageIndicator_indicator_selectedBold, z);
        float dimension9 = obtainStyledAttributes.getDimension(R.styleable.DMTitlePageIndicator_indicator_android_textSize, dimension5);
        int color4 = obtainStyledAttributes.getColor(R.styleable.DMTitlePageIndicator_indicator_footerColor, color);
        this.mPaintText.setTextSize(dimension9);
        this.mPaintText.setAntiAlias(true);
        this.mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
        this.mPaintFooterLine.setColor(color4);
        this.mPaintFooterIndicator.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaintFooterIndicator.setColor(color4);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.DMTitlePageIndicator_indicator_android_background);
        if (drawable != null) {
            setBackgroundDrawable(drawable);
        }
        obtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    public int getFooterColor() {
        return this.mPaintFooterLine.getColor();
    }

    public void setFooterColor(int i) {
        this.mPaintFooterLine.setColor(i);
        this.mPaintFooterIndicator.setColor(i);
        invalidate();
    }

    public float getFooterLineHeight() {
        return this.mFooterLineHeight;
    }

    public void setFooterLineHeight(float f) {
        this.mFooterLineHeight = f;
        this.mPaintFooterLine.setStrokeWidth(f);
        invalidate();
    }

    public float getFooterIndicatorHeight() {
        return this.mFooterIndicatorHeight;
    }

    public void setFooterIndicatorHeight(float f) {
        this.mFooterIndicatorHeight = f;
        invalidate();
    }

    public float getFooterIndicatorPadding() {
        return this.mFooterPadding;
    }

    public void setFooterIndicatorPadding(float f) {
        this.mFooterPadding = f;
        invalidate();
    }

    public IndicatorStyle getFooterIndicatorStyle() {
        return this.mFooterIndicatorStyle;
    }

    public void setFooterIndicatorStyle(IndicatorStyle indicatorStyle) {
        this.mFooterIndicatorStyle = indicatorStyle;
        invalidate();
    }

    public LinePosition getLinePosition() {
        return this.mLinePosition;
    }

    public void setLinePosition(LinePosition linePosition) {
        this.mLinePosition = linePosition;
        invalidate();
    }

    public int getSelectedColor() {
        return this.mColorSelected;
    }

    public void setSelectedColor(int i) {
        this.mColorSelected = i;
        invalidate();
    }

    public boolean isSelectedBold() {
        return this.mBoldText;
    }

    public void setSelectedBold(boolean z) {
        this.mBoldText = z;
        invalidate();
    }

    public int getTextColor() {
        return this.mColorText;
    }

    public void setTextColor(int i) {
        this.mPaintText.setColor(i);
        this.mColorText = i;
        invalidate();
    }

    public float getTextSize() {
        return this.mPaintText.getTextSize();
    }

    public void setTextSize(float f) {
        this.mPaintText.setTextSize(f);
        invalidate();
    }

    public float getTitlePadding() {
        return this.mTitlePadding;
    }

    public void setTitlePadding(float f) {
        this.mTitlePadding = f;
        invalidate();
    }

    public float getTopPadding() {
        return this.mTopPadding;
    }

    public void setTopPadding(float f) {
        this.mTopPadding = f;
        invalidate();
    }

    public float getClipPadding() {
        return this.mClipPadding;
    }

    public void setClipPadding(float f) {
        this.mClipPadding = f;
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
        invalidate();
    }

    public Typeface getTypeface() {
        return this.mPaintText.getTypeface();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int count;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float f;
        int i6;
        ViewPager viewPager;
        super.onDraw(canvas);
        ViewPager viewPager2 = this.mViewPager;
        if (viewPager2 == null || (count = viewPager2.getAdapter().getCount()) == 0) {
            return;
        }
        if (this.mCurrentPage == -1 && (viewPager = this.mViewPager) != null) {
            this.mCurrentPage = viewPager.getCurrentItem();
        }
        ArrayList<Rect> calculateAllBounds = calculateAllBounds(this.mPaintText);
        int size = calculateAllBounds.size();
        if (this.mCurrentPage >= size) {
            setCurrentItem(size - 1);
            return;
        }
        int i7 = count - 1;
        float width = getWidth() / 2.0f;
        int left = getLeft();
        float f2 = left + this.mClipPadding;
        int width2 = getWidth();
        int height = getHeight();
        int i8 = left + width2;
        float f3 = i8 - this.mClipPadding;
        int i9 = this.mCurrentPage;
        float f4 = this.mPageOffset;
        if (f4 > 0.5d) {
            i9++;
            f4 = 1.0f - f4;
        }
        int i10 = i9;
        boolean z = f4 <= SELECTION_FADE_PERCENTAGE;
        boolean z2 = f4 <= BOLD_FADE_PERCENTAGE;
        float f5 = (SELECTION_FADE_PERCENTAGE - f4) / SELECTION_FADE_PERCENTAGE;
        Rect rect = calculateAllBounds.get(this.mCurrentPage);
        float f6 = rect.right - rect.left;
        if (rect.left < f2) {
            clipViewOnTheLeft(rect, f6, left);
        }
        if (rect.right > f3) {
            clipViewOnTheRight(rect, f6, i8);
        }
        int i11 = this.mCurrentPage;
        if (i11 > 0) {
            int i12 = i11 - 1;
            while (i12 >= 0) {
                Rect rect2 = calculateAllBounds.get(i12);
                if (rect2.left < f2) {
                    int i13 = rect2.right - rect2.left;
                    clipViewOnTheLeft(rect2, i13, left);
                    Rect rect3 = calculateAllBounds.get(i12 + 1);
                    f = f2;
                    i6 = width2;
                    if (rect2.right + this.mTitlePadding > rect3.left) {
                        rect2.left = (int) ((rect3.left - i13) - this.mTitlePadding);
                        rect2.right = rect2.left + i13;
                    }
                } else {
                    f = f2;
                    i6 = width2;
                }
                i12--;
                f2 = f;
                width2 = i6;
            }
        }
        int i14 = width2;
        int i15 = this.mCurrentPage;
        if (i15 < i7) {
            for (int i16 = i15 + 1; i16 < count; i16++) {
                Rect rect4 = calculateAllBounds.get(i16);
                if (rect4.right > f3) {
                    int i17 = rect4.right - rect4.left;
                    clipViewOnTheRight(rect4, i17, i8);
                    Rect rect5 = calculateAllBounds.get(i16 - 1);
                    if (rect4.left - this.mTitlePadding < rect5.right) {
                        rect4.left = (int) (rect5.right + this.mTitlePadding);
                        rect4.right = rect4.left + i17;
                    }
                }
            }
        }
        int i18 = this.mColorText >>> 24;
        int i19 = 0;
        while (i19 < count) {
            Rect rect6 = calculateAllBounds.get(i19);
            if ((rect6.left <= left || rect6.left >= i8) && (rect6.right <= left || rect6.right >= i8)) {
                i2 = i8;
                i3 = count;
                i4 = i14;
                i5 = i18;
            } else {
                boolean z3 = i19 == i10;
                CharSequence title = getTitle(i19);
                this.mPaintText.setFakeBoldText(z3 && z2 && this.mBoldText);
                this.mPaintText.setColor(this.mColorText);
                if (z3 && z) {
                    this.mPaintText.setAlpha(i18 - ((int) (i18 * f5)));
                }
                if (i19 < size - 1) {
                    Rect rect7 = calculateAllBounds.get(i19 + 1);
                    if (rect6.right + this.mTitlePadding > rect7.left) {
                        int i20 = rect6.right - rect6.left;
                        rect6.left = (int) ((rect7.left - i20) - this.mTitlePadding);
                        rect6.right = rect6.left + i20;
                    }
                }
                i2 = i8;
                i3 = count;
                i4 = i14;
                i5 = i18;
                canvas.drawText(title, 0, title.length(), rect6.left, rect6.bottom + this.mTopPadding, this.mPaintText);
                if (z3 && z) {
                    this.mPaintText.setColor(this.mColorSelected);
                    this.mPaintText.setAlpha((int) ((this.mColorSelected >>> 24) * f5));
                    canvas.drawText(title, 0, title.length(), rect6.left, rect6.bottom + this.mTopPadding, this.mPaintText);
                }
            }
            i19++;
            i18 = i5;
            i8 = i2;
            i14 = i4;
            count = i3;
        }
        int i21 = i14;
        float f7 = this.mFooterLineHeight;
        float f8 = this.mFooterIndicatorHeight;
        if (this.mLinePosition == LinePosition.Top) {
            f7 = -f7;
            f8 = -f8;
            i = 0;
        } else {
            i = height;
        }
        this.mPath.reset();
        float f9 = i;
        float f10 = f9 - (f7 / 2.0f);
        this.mPath.moveTo(0.0f, f10);
        this.mPath.lineTo(i21, f10);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaintFooterLine);
        float f11 = f9 - f7;
        int i22 = C33991.f2000xdd266061[this.mFooterIndicatorStyle.ordinal()];
        if (i22 == 1) {
            this.mPath.reset();
            this.mPath.moveTo(width, f11 - f8);
            this.mPath.lineTo(width + f8, f11);
            this.mPath.lineTo(width - f8, f11);
            this.mPath.close();
            canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
        } else if (i22 == 2 && z && i10 < size) {
            Rect rect8 = calculateAllBounds.get(i10);
            float f12 = rect8.right + this.mFooterIndicatorUnderlinePadding;
            float f13 = rect8.left - this.mFooterIndicatorUnderlinePadding;
            float f14 = f11 - f8;
            this.mPath.reset();
            this.mPath.moveTo(f13, f11);
            this.mPath.lineTo(f12, f11);
            this.mPath.lineTo(f12, f14);
            this.mPath.lineTo(f13, f14);
            this.mPath.close();
            this.mPaintFooterIndicator.setAlpha((int) (255.0f * f5));
            canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
            this.mPaintFooterIndicator.setAlpha(255);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.viewpagerindicator.DMTitlePageIndicator$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C33991 {

        /* renamed from: $SwitchMap$org$picspool$viewpagerindicator$DMTitlePageIndicator$IndicatorStyle */
        static final /* synthetic */ int[] f2000xdd266061;

        static {
            int[] iArr = new int[IndicatorStyle.values().length];
            f2000xdd266061 = iArr;
            try {
                iArr[IndicatorStyle.Triangle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2000xdd266061[IndicatorStyle.Underline.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (super.onTouchEvent(motionEvent)) {
            return true;
        }
        ViewPager viewPager = this.mViewPager;
        if (viewPager == null || viewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
            this.mLastMotionX = motionEvent.getX();
        } else {
            if (action != 1) {
                if (action == 2) {
                    float x = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    float f = x - this.mLastMotionX;
                    if (!this.mIsDragging && Math.abs(f) > this.mTouchSlop) {
                        this.mIsDragging = true;
                    }
                    if (this.mIsDragging) {
                        this.mLastMotionX = x;
                        if (this.mViewPager.isFakeDragging() || this.mViewPager.beginFakeDrag()) {
                            this.mViewPager.fakeDragBy(f);
                        }
                    }
                } else if (action != 3) {
                    if (action == 5) {
                        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                        this.mLastMotionX = MotionEventCompat.getX(motionEvent, actionIndex);
                        this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    } else if (action == 6) {
                        int actionIndex2 = MotionEventCompat.getActionIndex(motionEvent);
                        if (MotionEventCompat.getPointerId(motionEvent, actionIndex2) == this.mActivePointerId) {
                            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex2 == 0 ? 1 : 0);
                        }
                        this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    }
                }
            }
            if (!this.mIsDragging) {
                int count = this.mViewPager.getAdapter().getCount();
                float width = getWidth();
                float f2 = width / 2.0f;
                float f3 = width / 6.0f;
                float f4 = f2 - f3;
                float f5 = f2 + f3;
                float x2 = motionEvent.getX();
                if (x2 < f4) {
                    int i = this.mCurrentPage;
                    if (i > 0) {
                        if (action != 3) {
                            this.mViewPager.setCurrentItem(i - 1);
                        }
                        return true;
                    }
                } else if (x2 > f5) {
                    int i2 = this.mCurrentPage;
                    if (i2 < count - 1) {
                        if (action != 3) {
                            this.mViewPager.setCurrentItem(i2 + 1);
                        }
                        return true;
                    }
                } else {
                    OnCenterItemClickListener onCenterItemClickListener = this.mCenterItemClickListener;
                    if (onCenterItemClickListener != null && action != 3) {
                        onCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
                    }
                }
            }
            this.mIsDragging = false;
            this.mActivePointerId = -1;
            if (this.mViewPager.isFakeDragging()) {
                this.mViewPager.endFakeDrag();
            }
        }
        return true;
    }

    private void clipViewOnTheRight(Rect rect, float f, int i) {
        rect.right = (int) (i - this.mClipPadding);
        rect.left = (int) (rect.right - f);
    }

    private void clipViewOnTheLeft(Rect rect, float f, int i) {
        rect.left = (int) (i + this.mClipPadding);
        rect.right = (int) (this.mClipPadding + f);
    }

    private ArrayList<Rect> calculateAllBounds(Paint paint) {
        ArrayList<Rect> arrayList = new ArrayList<>();
        int count = this.mViewPager.getAdapter().getCount();
        int width = getWidth();
        int i = width / 2;
        for (int i2 = 0; i2 < count; i2++) {
            Rect calcBounds = calcBounds(i2, paint);
            int i3 = calcBounds.right - calcBounds.left;
            int i4 = calcBounds.bottom - calcBounds.top;
            calcBounds.left = (int) ((i - (i3 / 2.0f)) + (((i2 - this.mCurrentPage) - this.mPageOffset) * width));
            calcBounds.right = calcBounds.left + i3;
            calcBounds.top = 0;
            calcBounds.bottom = i4;
            arrayList.add(calcBounds);
        }
        return arrayList;
    }

    private Rect calcBounds(int i, Paint paint) {
        Rect rect = new Rect();
        CharSequence title = getTitle(i);
        rect.right = (int) paint.measureText(title, 0, title.length());
        rect.bottom = (int) (paint.descent() - paint.ascent());
        return rect;
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setViewPager(ViewPager viewPager) {
        ViewPager viewPager2 = this.mViewPager;
        if (viewPager2 == viewPager) {
            return;
        }
        if (viewPager2 != null) {
            viewPager2.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        invalidate();
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setViewPager(ViewPager viewPager, int i) {
        setViewPager(viewPager);
        setCurrentItem(i);
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void notifyDataSetChanged() {
        invalidate();
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener onCenterItemClickListener) {
        this.mCenterItemClickListener = onCenterItemClickListener;
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setCurrentItem(int i) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        viewPager.setCurrentItem(i);
        this.mCurrentPage = i;
        invalidate();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        this.mScrollState = i;
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        this.mCurrentPage = i;
        this.mPageOffset = f;
        invalidate();
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i, f, i2);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        if (this.mScrollState == 0) {
            this.mCurrentPage = i;
            invalidate();
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        float f;
        int size = MeasureSpec.getSize(i);
        if (MeasureSpec.getMode(i2) == MeasureSpec.EXACTLY) {
            f = MeasureSpec.getSize(i2);
        } else {
            this.mBounds.setEmpty();
            this.mBounds.bottom = (int) (this.mPaintText.descent() - this.mPaintText.ascent());
            f = (this.mBounds.bottom - this.mBounds.top) + this.mFooterLineHeight + this.mFooterPadding + this.mTopPadding;
            if (this.mFooterIndicatorStyle != IndicatorStyle.None) {
                f += this.mFooterIndicatorHeight;
            }
        }
        setMeasuredDimension(size, (int) f);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() { // from class: com.picspool.viewpagerindicator.DMTitlePageIndicator.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int currentPage;

        /* synthetic */ SavedState(Parcel parcel, C33991 c33991) {
            this(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPage = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.currentPage);
        }
    }

    private CharSequence getTitle(int i) {
        CharSequence pageTitle = this.mViewPager.getAdapter().getPageTitle(i);
        return pageTitle == null ? "" : pageTitle;
    }
}
