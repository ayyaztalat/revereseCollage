package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.view.bean.Action;
import com.photo.editor.square.splash.view.view.bean.ColorPattern;
import com.photo.editor.square.splash.view.view.bean.FiltereTypes;
import com.sky.testproject.R;


/* loaded from: classes2.dex */
public class ColorView extends CustomFrameLayoutImags<FiltereTypes> {
    private static final int[] colors = {Color.parseColor("#F14435"), Color.parseColor("#7ED321"), Color.parseColor("#4A90E2")};
    private ColorPattern.ColorColorPattern colorAdjustItemBean;
    private ColorMatrix colorMatrix;
    private OnColorShowListener colorShowListener;
    private BackgroundGroupRecycleView colorView;
    private SeekBar seekBar;

    /* loaded from: classes2.dex */
    public interface OnColorShowListener {
        void onColorViewHide();

        void onColorViewShow();
    }

    public ColorView(Context context) {
        super(context);
    }

    public ColorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ColorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
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
        showToolView(true);
        setIl6("Color");
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
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.photo.editor.square.splash.view.view.IL30.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (ColorView.this.colorAdjustItemBean != null) {
                    ColorView.this.colorAdjustItemBean.setProgress(i);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ColorView.this.il5 != null) {
                    ColorView.this.il5.mo69f4(ColorView.this.createFilterBean());
                }
            }
        });
        linearLayout.addView(this.seekBar);
        BackgroundGroupRecycleView backgroundGroupRecycleView = new BackgroundGroupRecycleView(context) { // from class: com.photo.editor.square.splash.view.view.IL30.2
            @Override // com.photo.editor.square.splash.view.view.IL33
            protected boolean isItemSelected(int i) {
                return ColorView.this.colorAdjustItemBean.getModel() == i;
            }
        };
        this.colorView = backgroundGroupRecycleView;
        linearLayout.addView(backgroundGroupRecycleView);
        this.colorView.setSpace(CSScreenInfoUtil.dip2px(context, 20.0f), 0, CSScreenInfoUtil.dip2px(context, 30.0f));
        this.colorView.setOrientation(0);
        this.colorView.setSize(-2, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        this.colorAdjustItemBean = new ColorPattern.ColorColorPattern(0, new AdjustAction<ColorMatrix>() { // from class: com.photo.editor.square.splash.view.view.IL30.3
            @Override // com.photo.editor.square.splash.view.view.IL30.AdjustAction, com.photo.editor.square.splash.view.view.bean.Action
            public ColorMatrix doAction(Object... objArr) {
                return createAdjustObject(0);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.photo.editor.square.splash.view.view.IL30.AdjustAction
            public ColorMatrix createAdjustObject(int i) {
                return ColorPattern.getColorFilter(ColorView.this.colorAdjustItemBean.getRed() / 100.0f, ColorView.this.colorAdjustItemBean.getGreen() / 100.0f, ColorView.this.colorAdjustItemBean.getBlur() / 100.0f);
            }
        });
        this.colorMatrix = new ColorMatrix();
        this.colorView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL30.4
            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            int getLayoutId() {
                return R.layout.view_bottom_brush_item;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public int getItemCount() {
                return ColorView.colors.length;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new BackgroundGroupRecycleView.SimpleViewHolder(view) { // from class: com.photo.editor.square.splash.view.view.IL30.4.1
                    private ImageView imageView;
                    private ImageView selectedIcon;

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    protected void initData() {
                        this.imageView = (ImageView) this.itemView.findViewById(R.id.imageView);
                        this.selectedIcon = (ImageView) this.itemView.findViewById(R.id.selectedIcon);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    void onSelected() {
                        this.selectedIcon.setVisibility(View.VISIBLE);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    void onUnSelected() {
                        this.selectedIcon.setVisibility(View.GONE);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onBindView(int i) {
                        this.imageView.setBackgroundColor(ColorView.colors[i]);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onItemClick(int i) {
                        ColorView.this.colorAdjustItemBean.setModel(i);
                        ColorView.this.seekBar.setProgress(ColorView.this.colorAdjustItemBean.getProgress());
                        ColorView.this.m74f8(ColorView.this.createFilterBean(), i);
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
        this.colorAdjustItemBean.applyChange();
        hideColorView();
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f4 */
    public void mo67f4() {
        super.mo67f4();
        this.colorAdjustItemBean.resetChange();
        this.seekBar.setProgress(this.colorAdjustItemBean.getProgress());
        m74f8(createFilterBean(), this.colorAdjustItemBean.getModel());
        hideColorView();
    }

    public void showColorView() {
        OnColorShowListener onColorShowListener = this.colorShowListener;
        if (onColorShowListener != null) {
            onColorShowListener.onColorViewShow();
        }
    }

    private void hideColorView() {
        OnColorShowListener onColorShowListener = this.colorShowListener;
        if (onColorShowListener != null) {
            onColorShowListener.onColorViewHide();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FiltereTypes createFilterBean() {
        FiltereTypes filtereTypes = new FiltereTypes();
        this.colorMatrix.reset();
        this.colorMatrix.postConcat(this.colorAdjustItemBean.getAction().doAction(Integer.valueOf(this.colorAdjustItemBean.getProgress())));
        filtereTypes.setColorMatrix(this.colorMatrix);
        return filtereTypes;
    }

    public boolean isShowColorView() {
        return this.colorView.getVisibility() == View.VISIBLE;
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
