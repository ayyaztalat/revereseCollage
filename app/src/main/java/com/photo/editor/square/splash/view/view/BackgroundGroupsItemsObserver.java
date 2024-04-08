package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



import com.bumptech.glide.Glide;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.sky.testproject.R;
//import io.reactivex.Observable;
//import io.reactivex.functions.Function;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes2.dex */
public class BackgroundGroupsItemsObserver extends CustomFrameLayoutImags<Observable<BackgroundGroupsItems.BackgroundGroup>> implements Observer {
    private BackgroundGroupsItems BackgroundGroupsItems;
    private BackgroundGroupRecycleView backgroundGroupRecycleView;

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
    }

    public BackgroundGroupsItemsObserver(Context context) {
        super(context);
    }

    public BackgroundGroupsItemsObserver(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BackgroundGroupsItemsObserver(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        showToolView(false);
        BackgroundGroupRecycleView backgroundGroupRecycleView = new BackgroundGroupRecycleView(context);
        this.backgroundGroupRecycleView = backgroundGroupRecycleView;
        frameLayout2.addView(backgroundGroupRecycleView);
        this.backgroundGroupRecycleView.setOrientation(0);
        this.backgroundGroupRecycleView.setSpace(0, CSScreenInfoUtil.dip2px(getContext(), 10.0f));
        this.backgroundGroupRecycleView.setSize(-1, CSScreenInfoUtil.dip2px(getContext(), 110.0f));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    /* renamed from: f9 */
    public void mo66f9() {
        super.mo66f9();
        BackgroundGroupsItems backgroundGroupsItems = BackgroundGroupsItems.getInstance();
        this.BackgroundGroupsItems = backgroundGroupsItems;
//        il23.addObserver(this);
        this.backgroundGroupRecycleView.initView(new BackgroundGroupRecycleView.SimpleViewHolder.SimpleRecycleViewBuilder() { // from class: com.photo.editor.square.splash.view.view.IL27.1
            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            int getLayoutId() {
                return R.layout.view_background_item;
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public int getItemCount() {
                return BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().size();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: com.photo.editor.square.splash.view.view.IL27$1$1 */
            /* loaded from: classes2.dex */
            public class C22231 extends BackgroundGroupRecycleView.SimpleViewHolder {
                private ImageView icon;
                private ImageView isHot;
                private ImageView isNew;
                private ProgressBar progressBar;
                private TextView title;

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                void onSelected() {
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                void onUnSelected() {
                }

                C22231(View view) {
                    super(view);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                protected void initData() {
                    this.icon = (ImageView) this.itemView.findViewById(R.id.icon);
                    this.isHot = (ImageView) this.itemView.findViewById(R.id.isHot);
                    this.isNew = (ImageView) this.itemView.findViewById(R.id.isNew);
                    this.title = (TextView) this.itemView.findViewById(R.id.title);
                    this.progressBar = (ProgressBar) this.itemView.findViewById(R.id.progressBar);
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                public void onBindView(int i) {
                    Glide.with(BackgroundGroupsItemsObserver.this.getContext()).load(Uri.parse(BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().get(i).getIcon())).placeholder((int) R.drawable.zwt_tu).into(this.icon);
                    this.title.setText(BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().get(i).getName());
                    BackgroundGroupsItems.BackgroundGroup backgroundGroup = BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().get(i);
                    if (backgroundGroup.isDownloading()) {
                        this.progressBar.setVisibility(View.VISIBLE);
                    } else {
                        this.progressBar.setVisibility(View.GONE);
                    }
                    if (backgroundGroup.isHot()) {
                        this.isHot.setVisibility(View.VISIBLE);
                    } else {
                        this.isHot.setVisibility(View.GONE);
                    }
                    if (backgroundGroup.isNew()) {
                        this.isNew.setVisibility(View.VISIBLE);
                    } else {
                        this.isNew.setVisibility(View.GONE);
                    }
                }

                @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder
                public void onItemClick(int i) {
                    if (i < 0 || i >= BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().size()) {
                        return;
                    }
                    final BackgroundGroupsItems.BackgroundGroup backgroundGroup = BackgroundGroupsItemsObserver.this.BackgroundGroupsItems.getBackgroundGroupInfo().get(i);
                    if (backgroundGroup.getOnlineZip() != null) {
                        this.progressBar.setVisibility(View.VISIBLE);
                        backgroundGroup.setDownloading(true);
                    }
//                    IL27.this.m74f8(backgroundGroup.getItemsObservable().map(new Function() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL27$1$1$8y-SPKLHb9MbD_A9Nz-_MflhPXw
//                        @Override // io.reactivex.functions.Function
//                        public final Object apply(Object obj) {
//                            return IL27.C22221.C22231.this.lambda$onItemClick$0$IL27$1$1(backgroundGroup, (List) obj);
//                        }
//                    }), i);
                }

                public /* synthetic */ BackgroundGroupsItems.BackgroundGroup lambda$onItemClick$0$IL27$1$1(BackgroundGroupsItems.BackgroundGroup backgroundGroup, List list) throws Exception {
                    this.progressBar.setVisibility(View.GONE);
                    return backgroundGroup;
                }
            }

            @Override // com.photo.editor.square.splash.view.view.IL33.SimpleViewHolder.SimpleRecycleViewBuilder
            public BackgroundGroupRecycleView.SimpleViewHolder onCreateViewHolder(View view) {
                return new C22231(view);
            }
        });
    }

//    @Override // java.util.Observer
//    public void update(java.util.Observable observable, Object obj) {
//        this.backgroundGroupRecycleView.notifyDataSetChanged();
//    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        this.IL23.deleteObserver(this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
