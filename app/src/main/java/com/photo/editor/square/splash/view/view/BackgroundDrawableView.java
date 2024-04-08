package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.sky.testproject.R;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes2.dex */
public class BackgroundDrawableView extends ImageFramesView<Drawable> {
    private static final String TAG = "Jie";
    private final List<BackgroundGroupsItems.BackgroundItem> data;
    private Disposable disposable;

    @Override
    protected void onSelectedPosition(int i) {
    }

    @Override
    public  int f11() {
        return super.f11();
    }

    public BackgroundDrawableView(Context context, List<BackgroundGroupsItems.BackgroundItem> list) {
        super(context);
        this.data = list;
    }


    @Override
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(true);
        this.recyclerView.setOrientation(0);
        this.recyclerView.setSpace(0, CSScreenInfoUtil.dip2px(getContext(), 10.0f));
        this.recyclerView.setSize(-1, CSScreenInfoUtil.dip2px(getContext(), 110.0f));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        this.recyclerView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL28.1
            private final int color = Color.parseColor("#999999");

            @Override
            int getLayoutId() {
                return R.layout.view_background_item;
            }

            @Override
            public int getItemCount() {
                return BackgroundDrawableView.this.data.size();
            }

            public class C22251 extends BackgroundGroupRecycleView.SimpleViewHolder {
                private ImageView icon;
                private ImageView selectedIcon;
                private TextView title;

                C22251(View view) {
                    super(view);
                }

                @Override
                protected void initData() {
                    this.icon = (ImageView) this.itemView.findViewById(R.id.icon);
                    this.title = (TextView) this.itemView.findViewById(R.id.title);
                    this.selectedIcon = (ImageView) this.itemView.findViewById(R.id.selectedIcon);
                }

                @Override
                void onSelected() {
                    this.selectedIcon.setVisibility(View.VISIBLE);
                    this.title.setTextColor(-1);
                }

                @Override
                void onUnSelected() {
                    this.selectedIcon.setVisibility(View.INVISIBLE);
                    this.title.setTextColor(color);
                }

                @Override
                public void onBindView(int i) {
                    try {
                        if (((BackgroundGroupsItems.BackgroundItem) BackgroundDrawableView.this.data.get(i)).getIcon() != null) {
                            Glide.with(BackgroundDrawableView.this.getContext()).load(((BackgroundGroupsItems.BackgroundItem) BackgroundDrawableView.this.data.get(i)).getIcon()).placeholder((int) R.drawable.zwt_tu).into(this.icon);
                            this.title.setText(((BackgroundGroupsItems.BackgroundItem) BackgroundDrawableView.this.data.get(i)).getName());
                        }
                    } catch (Exception ignored) {
                    }
                }

                @Override
                public void onItemClick(final int i) {
                    if (BackgroundDrawableView.this.disposable != null && !BackgroundDrawableView.this.disposable.isDisposed()) {
                        BackgroundDrawableView.this.disposable.dispose();
                    }
                    // get image wala firebase sa handlle krna hn download krna kah lia
//                    IL28.this.disposable = ((IL23.BackgroundItem) IL28.this.data.get(i)).getImageBitmap().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL28$1$1$oJjfTONBSXxGyur20qy0u0EjJSU
//                        @Override // io.reactivex.functions.Consumer
//                        public final void accept(Object obj) throws Exception {
//                            lambda$onItemClick$0$IL28$1$1(i, (Drawable) obj);
//                        }
//                    }, new Consumer() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL28$1$1$VY7rHh4JokRGu78aj1z2MimByk4
//                        @Override // io.reactivex.functions.Consumer
//                        public final void accept(Object obj) {
//                            Log.e("Jie", "onItemClick: ", (Throwable) obj);
//                        }
//                    });
                }

                public void lambda$onItemClick$0$IL28$1$1(int i, Drawable drawable) {
                    BackgroundDrawableView.this.m74f8(drawable, i);
                }
            }

            @Override
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new C22251(view);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }
}
