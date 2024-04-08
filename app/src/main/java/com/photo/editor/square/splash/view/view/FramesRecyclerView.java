package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.photo.editor.square.pic.splash.libfreecollage.frame.manager.FreeFrameBorderManagerNew;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


/* loaded from: classes2.dex */
public class FramesRecyclerView extends ImageFramesView<DMWBRes> {
    private static final String TAG = "Jie";
    private final FreeFrameBorderManagerNew data;
    private Disposable disposable;

    @Override // com.photo.editor.square.splash.view.view.IL32, com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
    }

    @Override // com.photo.editor.square.splash.view.view.IL32, com.photo.editor.square.splash.view.view.IL29
    public /* bridge */ /* synthetic */ int f11() {
        return super.f11();
    }

    public FramesRecyclerView(Context context) {
        super(context);
        this.data = new FreeFrameBorderManagerNew(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL32, com.photo.editor.square.splash.view.view.IL29
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(false);
        this.recyclerView.setOrientation(0);
        this.recyclerView.setSpace(0, CSScreenInfoUtil.dip2px(getContext(), 10.0f));
        this.recyclerView.setSize(-1, CSScreenInfoUtil.dip2px(getContext(), 110.0f));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        this.recyclerView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL34.1
            private final int color = Color.parseColor("#999999");

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            int getLayoutId() {
                return R.layout.view_background_item;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public int getItemCount() {
                return FramesRecyclerView.this.data.getCount();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: com.photo.editor.square.splash.view.view.IL34$1$1 */
            /* loaded from: classes2.dex */
            public class C22361 extends BackgroundGroupRecycleView.SimpleViewHolder {
                private ImageView icon;
                private ImageView selectedIcon;
                private TextView title;

                C22361(View view) {
                    super(view);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                protected void initData() {
                    this.icon = (ImageView) this.itemView.findViewById(R.id.icon);
                    this.title = (TextView) this.itemView.findViewById(R.id.title);
                    this.selectedIcon = (ImageView) this.itemView.findViewById(R.id.selectedIcon);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                void onSelected() {
                    this.selectedIcon.setVisibility(View.VISIBLE);
                    this.title.setTextColor(-1);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                void onUnSelected() {
                    this.selectedIcon.setVisibility(View.INVISIBLE);
                    this.title.setTextColor(color);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                public void onBindView(int i) {
                    Glide.with(FramesRecyclerView.this.getContext()).load((Object) FramesRecyclerView.this.data.getRes(i).getIconBitmap()).placeholder((int) R.drawable.zwt_tu).into(this.icon);
                    this.title.setText(FramesRecyclerView.this.data.getRes(i).getName());
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                public void onItemClick(final int i) {
                    if (FramesRecyclerView.this.disposable != null && !FramesRecyclerView.this.disposable.isDisposed()) {
                        FramesRecyclerView.this.disposable.dispose();
                    }
                    FramesRecyclerView.this.disposable = Observable.just(FramesRecyclerView.this.data.getRes(i)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL34$1$1$8A7ZIg01548a_7AhVlxHvtWsXJQ
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Object obj) throws Exception {
                           lambda$onItemClick$0$IL34$1$1(i, (DMWBRes) obj);
                        }
                    }, (Consumer) obj -> Log.e("Jie", "onItemClick: ", (Throwable) obj));
                }

                public void lambda$onItemClick$0$IL34$1$1(int i, DMWBRes dMWBRes) throws Exception {
                    FramesRecyclerView.this.m74f8(dMWBRes, i);
                }
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new C22361(view);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }
}
