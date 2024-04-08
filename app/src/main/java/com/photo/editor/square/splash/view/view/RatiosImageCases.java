package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.sky.testproject.R;

import java.util.Arrays;
import java.util.List;


/* loaded from: classes2.dex */
public class RatiosImageCases extends ImageFramesView<String> {
    private final List<Integer> data;
    private final List<String> ratios;

    @Override // com.photo.editor.square.splash.view.view.IL32, com.photo.editor.square.splash.view.view.IL29
    public  int f11() {
        return super.f11();
    }

    public RatiosImageCases(Context context) {
        super(context);
        this.data = Arrays.asList(Integer.valueOf((int) R.drawable.btn_ratio_ori), Integer.valueOf((int) R.drawable.btn_ratio_1_1), Integer.valueOf((int) R.drawable.btn_ratio_2_3), Integer.valueOf((int) R.drawable.btn_ratio_3_2), Integer.valueOf((int) R.drawable.btn_ratio_3_4), Integer.valueOf((int) R.drawable.btn_ratio_4_3), Integer.valueOf((int) R.drawable.btn_ratio_4_5), Integer.valueOf((int) R.drawable.btn_ratio_5_4), Integer.valueOf((int) R.drawable.btn_ratio_9_16), Integer.valueOf((int) R.drawable.btn_ratio_16_9));
        this.ratios = Arrays.asList(null, "1:1", "2:3", "3:2", "3:4", "4:3", "4:5", "5:4", "9:16", "16:9");
    }

    public RatiosImageCases(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.data = Arrays.asList(Integer.valueOf((int) R.drawable.btn_ratio_ori), Integer.valueOf((int) R.drawable.btn_ratio_1_1), Integer.valueOf((int) R.drawable.btn_ratio_2_3), Integer.valueOf((int) R.drawable.btn_ratio_3_2), Integer.valueOf((int) R.drawable.btn_ratio_3_4), Integer.valueOf((int) R.drawable.btn_ratio_4_3), Integer.valueOf((int) R.drawable.btn_ratio_4_5), Integer.valueOf((int) R.drawable.btn_ratio_5_4), Integer.valueOf((int) R.drawable.btn_ratio_9_16), Integer.valueOf((int) R.drawable.btn_ratio_16_9));
        this.ratios = Arrays.asList(null, "1:1", "2:3", "3:2", "3:4", "4:3", "4:5", "5:4", "9:16", "16:9");
    }

    public RatiosImageCases(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.data = Arrays.asList(Integer.valueOf((int) R.drawable.btn_ratio_ori), Integer.valueOf((int) R.drawable.btn_ratio_1_1), Integer.valueOf((int) R.drawable.btn_ratio_2_3), Integer.valueOf((int) R.drawable.btn_ratio_3_2), Integer.valueOf((int) R.drawable.btn_ratio_3_4), Integer.valueOf((int) R.drawable.btn_ratio_4_3), Integer.valueOf((int) R.drawable.btn_ratio_4_5), Integer.valueOf((int) R.drawable.btn_ratio_5_4), Integer.valueOf((int) R.drawable.btn_ratio_9_16), Integer.valueOf((int) R.drawable.btn_ratio_16_9));
        this.ratios = Arrays.asList(null, "1:1", "2:3", "3:2", "3:4", "4:3", "4:5", "5:4", "9:16", "16:9");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        showToolView(false);
        setIl6("Ratio");
        this.recyclerView.setSpace(CSScreenInfoUtil.dip2px(getContext(), 15.0f), CSScreenInfoUtil.dip2px(getContext(), 15.0f), 0);
        this.recyclerView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL31.1
            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            protected int getLayoutId() {
                return R.layout.view_bottom_ratio_item;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public int getItemCount() {
                return RatiosImageCases.this.getData().size();
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new BackgroundGroupRecycleView.SimpleViewHolder(view) { // from class: com.photo.editor.square.splash.view.view.IL31.1.1
                    private ImageView imageView;

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    protected void initData() {
                        this.imageView = (ImageView) this.itemView.findViewById(R.id.imageView);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    protected void onSelected() {
                        this.imageView.setSelected(true);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    protected void onUnSelected() {
                        this.imageView.setSelected(false);
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onBindView(int i) {
                        this.imageView.setImageResource(RatiosImageCases.this.getData().get(i).intValue());
                    }

                    @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                    public void onItemClick(int i) {
                        RatiosImageCases.this.m74f8(RatiosImageCases.this.getRatios().get(i), i);
                    }
                };
            }
        });
    }

    public List<String> getRatios() {
        return this.ratios;
    }

    public List<Integer> getData() {
        return this.data;
    }
}
