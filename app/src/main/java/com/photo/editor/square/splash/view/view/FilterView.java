package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.photo.editor.square.splash.app.CSMyApp;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.view.bean.Action;
import com.photo.editor.square.splash.view.view.bean.ColorPattern;
import com.photo.editor.square.splash.view.view.bean.FiltereTypes;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class FilterView extends CustomFrameLayoutImags<FiltereTypes> {
    private static final String TAG = "Jie";
    private static final int[] ids = {R.drawable.btn_adjust1,
            R.drawable.btn_adjust2,
            R.drawable.btn_adjust3,
            R.drawable.btn_adjust4,
            R.drawable.btn_adjust5,
            R.drawable.btn_adjust6,
            R.drawable.btn_adjust7,
            R.drawable.btn_adjust8};
    private ColorPattern<?> ColorPattern;
    private List<ColorPattern<?>> ColorPatterns;
    private BackgroundGroupRecycleView adjustView;
    private ColorPattern.ColorColorPattern colorAdjustItemBean;
    private OnColorShowListener colorShowListener;
    private SeekBar seekBar;

    /* loaded from: classes2.dex */
    public interface OnColorShowListener {
        void onColorViewShow();
    }

    public FilterView(Context context) {
        super(context);
    }

    public FilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FilterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
        SeekBar seekBar;
        BackgroundGroupRecycleView backgroundGroupRecycleView = this.adjustView;
        if (backgroundGroupRecycleView != null) {
            backgroundGroupRecycleView.selectedPosition(i);
        }
        List<ColorPattern<?>> list = this.ColorPatterns;
        if (list != null) {
            this.ColorPattern = list.get(i);
        }
        ColorPattern<?> colorPattern = this.ColorPattern;
        if (colorPattern != null && (seekBar = this.seekBar) != null) {
            seekBar.setProgress(colorPattern.getOldProgress());
        }
        m73f8(createFilterBean(), i, false);
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    public int f11() {
        return CSScreenInfoUtil.dip2px(getContext(), 117.0f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(false);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, CSScreenInfoUtil.dip2px(context, 117.0f)));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayout2.addView(linearLayout);
        this.seekBar = (SeekBar) LayoutInflater.from(context).inflate(R.layout.seekbar, (ViewGroup) this, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = CSScreenInfoUtil.dip2px(context, 20.0f);
        int dip2px = CSScreenInfoUtil.dip2px(context, 25.0f);
        layoutParams.rightMargin = dip2px;
        layoutParams.leftMargin = dip2px;
        this.seekBar.setLayoutParams(layoutParams);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.photo.editor.square.splash.view.view.IL26.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (FilterView.this.ColorPattern != null) {
                    FilterView.this.ColorPattern.setProgress(i);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (FilterView.this.il5 != null) {
                    FilterView.this.il5.mo69f4(FilterView.this.createFilterBean());
                }
            }
        });
        linearLayout.addView(this.seekBar);
        BackgroundGroupRecycleView backgroundGroupRecycleView = new BackgroundGroupRecycleView(context);
        this.adjustView = backgroundGroupRecycleView;
        linearLayout.addView(backgroundGroupRecycleView);
        this.adjustView.setSpace(CSScreenInfoUtil.dip2px(context, 0.0f), CSScreenInfoUtil.dip2px(context, 5.0f), 0);
        this.adjustView.setOrientation(0);
        this.adjustView.setSize(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        ArrayList arrayList = new ArrayList();
        this.ColorPatterns = arrayList;
        arrayList.add(new ColorPattern(ids[0], "Brightness", new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.2
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getLiangDu(i);
            }
        }));
        this.ColorPatterns.add(new ColorPattern<>(ids[1], ExifInterface.TAG_CONTRAST, new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.3
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getDuiBiDu(i);
            }
        }));
        this.ColorPatterns.add(new ColorPattern<>(ids[2], "Warmth", new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.4
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getSeWen(i, CSMyApp.getContext());
            }
        }));
        this.ColorPatterns.add(new ColorPattern<>(ids[3], ExifInterface.TAG_SATURATION, new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.5
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getBaoHe(i);
            }
        }));
        ColorPattern.ColorColorPattern colorIL22 = new ColorPattern.ColorColorPattern(ids[4], new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.6
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction, com.photo.editor.square.splash.view.view.bean.Action
            public ColorMatrix doAction(Object... objArr) {
                return createAdjustObject(0);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getColorFilter(FilterView.this.colorAdjustItemBean.getRed() / 100.0f, FilterView.this.colorAdjustItemBean.getGreen() / 100.0f, FilterView.this.colorAdjustItemBean.getBlur() / 100.0f);
            }
        });
        this.colorAdjustItemBean = colorIL22;
        this.ColorPatterns.add(colorIL22);
        this.ColorPatterns.add(new ColorPattern<>(ids[5], "Highlight", new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL26.7
            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getGaoLiang(i);
            }
        }));
        this.ColorPatterns.add(new ColorPattern<>(ids[6], "Sharpen", new AdjustAction<GPUImageFilter>() { // from class: com.photo.editor.square.splash.view.view.IL26.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public GPUImageFilter createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getRuiHua(i);
            }
        }));
        this.ColorPatterns.add(new ColorPattern<>(ids[7], "Vignette", new AdjustAction<GPUImageFilter>() { // from class: com.photo.editor.square.splash.view.view.IL26.9
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.photo.editor.square.splash.view.view.IL26.AdjustAction
            public GPUImageFilter createAdjustObject(int i) {
                ColorPattern unused = FilterView.this.ColorPattern;
                return ColorPattern.getYunYing(i);
            }
        }));
        this.adjustView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL26.10
            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            int getLayoutId() {
                return R.layout.view_bottom_adjust_item;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public int getItemCount() {
                return FilterView.this.ColorPatterns.size();
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new BackgroundGroupRecycleView.SimpleViewHolder(view) { // from class: com.photo.editor.square.splash.view.view.IL26.10.1
                    private ImageView imageView;
                    private TextView title;

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    protected void initData() {
                        this.imageView = (ImageView) this.itemView.findViewById(R.id.imageView);
                        this.title = (TextView) this.itemView.findViewById(R.id.title);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    void onSelected() {
                        this.imageView.setSelected(true);
                        this.title.setSelected(true);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    void onUnSelected() {
                        this.imageView.setSelected(false);
                        this.title.setSelected(false);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onBindView(int i) {
                        ColorPattern colorPattern = (ColorPattern) FilterView.this.ColorPatterns.get(i);
                        this.imageView.setImageResource(colorPattern.getIcon());
                        this.title.setText(colorPattern.getTitle());
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onItemClick(int i) {
                        FilterView.this.ColorPattern = (ColorPattern) FilterView.this.ColorPatterns.get(i);
                        if (FilterView.this.ColorPattern == FilterView.this.colorAdjustItemBean) {
                            FilterView.this.showColorView();
                        }
                        FilterView.this.seekBar.setProgress(FilterView.this.ColorPattern.getProgress());
                        FilterView.this.m74f8(FilterView.this.createFilterBean(), i);
                    }
                };
            }
        });
    }

    public void setColorShowListener(OnColorShowListener onColorShowListener) {
        this.colorShowListener = onColorShowListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f2 */
    public void mo68f2() {
        super.mo68f2();
        for (ColorPattern<?> colorPattern : this.ColorPatterns) {
            colorPattern.applyChange();
        }
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f4 */
    public void mo67f4() {
        for (ColorPattern<?> colorPattern : this.ColorPatterns) {
            colorPattern.resetChange();
        }
        super.mo67f4();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showColorView() {
        OnColorShowListener onColorShowListener = this.colorShowListener;
        if (onColorShowListener != null) {
            onColorShowListener.onColorViewShow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FiltereTypes createFilterBean() {
        GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup(new ArrayList());
        FiltereTypes filtereTypes = new FiltereTypes();
        ColorMatrix colorMatrix = new ColorMatrix();
        for (ColorPattern<?> colorPattern : this.ColorPatterns) {
            if (colorPattern != this.colorAdjustItemBean) {
                Object doAction = colorPattern.getAction().doAction(Integer.valueOf(colorPattern.getProgress()));
                if (doAction instanceof GPUImageFilter) {
                    gPUImageFilterGroup.addFilter((GPUImageFilter) doAction);
                } else if (doAction instanceof ColorMatrix) {
                    colorMatrix.postConcat((ColorMatrix) doAction);
                }
            }
        }
        filtereTypes.setColorMatrix(colorMatrix);
        if (!gPUImageFilterGroup.getFilters().isEmpty()) {
            filtereTypes.setAdjustImageFilter(gPUImageFilterGroup);
        }
        return filtereTypes;
    }

    /* loaded from: classes2.dex */
    private static abstract class AdjustAction<T> implements Action<T> {
        abstract T createAdjustObject(int i);

        private AdjustAction() {
        }

        @Override // com.photo.editor.square.splash.view.view.bean.Action
        public T doAction(Object... objArr) {
            if (objArr[0].equals(0)) {
                return null;
            }
            return createAdjustObject(((Integer) objArr[0]).intValue());
        }
    }
}
