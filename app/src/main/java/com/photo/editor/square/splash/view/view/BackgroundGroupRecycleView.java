package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;

/* loaded from: classes2.dex */
public class BackgroundGroupRecycleView extends RecyclerView {
    private final int screenWidth;
    private int selectedPos;
    private SimpleViewHolder selectedViewHolder;

    public BackgroundGroupRecycleView(Context context) {
        this(context, null);
    }

    public BackgroundGroupRecycleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BackgroundGroupRecycleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.selectedPos = -1;
        this.screenWidth = CSScreenInfoUtil.screenWidth(context);
    }

    public void initView(SimpleViewHolder.SimpleRecycleViewBuilder simpleRecycleViewBuilder) {
        setAdapter(new SimpleAdapter(simpleRecycleViewBuilder));
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext()) { // from class: com.photo.editor.square.splash.view.view.IL33.1
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onLayoutChildren(Recycler recycler, State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (Exception unused) {
                    }
                }
            });
        }
    }

    public void setSpace(int i, int i2) {
        int i3 = i2 / 2;
        addItemDecoration(new SimpleItemDecoration(i, i3, i3));
    }

    public void setSpace(int i, int i2, int i3) {
        int i4 = i3 / 2;
        addItemDecoration(new SimpleItemDecoration(i, i2 - i4, i4));
    }

    public void setSize(int i, int i2) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
            layoutParams.height = i2;
            return;
        }
        setLayoutParams(new ViewGroup.LayoutParams(i, i2));
    }

    public void setOrientation(int i) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getContext()) { // from class: com.photo.editor.square.splash.view.view.IL33.2
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onLayoutChildren(Recycler recycler, State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (Exception unused) {
                    }
                }
            };
            setLayoutManager(linearLayoutManager);
        }
        linearLayoutManager.setOrientation(i);
    }

    public void notifyDataSetChanged() {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void notifyItemChanged(int i) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void selectedPosition(int i) {
        SimpleViewHolder simpleViewHolder = this.selectedViewHolder;
        if (simpleViewHolder != null) {
            simpleViewHolder.unSelected();
        }
        this.selectedPos = i;
        notifyItemChanged(i);
    }

    /* loaded from: classes2.dex */
    private static class SimpleItemDecoration extends ItemDecoration {
        private final int left;
        private final int space;
        private final int top;

        SimpleItemDecoration(int i, int i2, int i3) {
            this.top = i;
            this.left = i2;
            this.space = i3;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            int i = this.top;
            rect.bottom = i;
            rect.top = i;
            int i2 = this.space;
            rect.right = i2;
            rect.left = i2;
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            if (childAdapterPosition == 0) {
                rect.left += this.left;
            } else if (childAdapterPosition == state.getItemCount() - 1) {
                rect.right += this.left;
            }
        }
    }

    /* loaded from: classes2.dex */
    private class SimpleAdapter extends Adapter<SimpleViewHolder> {
        private final SimpleViewHolder.SimpleRecycleViewBuilder builder;

        public SimpleAdapter(SimpleViewHolder.SimpleRecycleViewBuilder simpleRecycleViewBuilder) {
            this.builder = simpleRecycleViewBuilder;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return this.builder.onCreateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.builder.getLayoutId(), viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
            if (this.builder.getItemWidth() > 0 && BackgroundGroupRecycleView.this.screenWidth / getItemCount() > this.builder.getItemWidth()) {
                simpleViewHolder.itemView.getLayoutParams().width = BackgroundGroupRecycleView.this.screenWidth / getItemCount();
            }
            if (BackgroundGroupRecycleView.this.isItemSelected(i)) {
                if (BackgroundGroupRecycleView.this.selectedViewHolder != null) {
                    BackgroundGroupRecycleView.this.selectedViewHolder.unSelected();
                }
                simpleViewHolder.selected();
                BackgroundGroupRecycleView.this.selectedViewHolder = simpleViewHolder;
            } else {
                simpleViewHolder.unSelected();
            }
            simpleViewHolder.bindView(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.builder.getItemCount();
        }
    }

    protected boolean isItemSelected(int i) {
        return this.selectedPos == i;
    }

    /* loaded from: classes2.dex */
    public static abstract class SimpleViewHolder extends ViewHolder {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static abstract class SimpleRecycleViewBuilder {
            public abstract int getItemCount();

            public int getItemWidth() {
                return 0;
            }

            abstract int getLayoutId();

            public abstract SimpleViewHolder onCreateViewHolder(View view);
        }

        protected abstract void initData();

        public abstract void onBindView(int i);

        public abstract void onItemClick(int i);

        abstract void onSelected();

        abstract void onUnSelected();

        public SimpleViewHolder(View view) {
            super(view);
            view.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.view.-$$Lambda$IL33$SimpleViewHolder$yAJ0odubSNXNibAK1X3OYcEJF5Q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SimpleViewHolder.this.lambda$new$0$IL33$SimpleViewHolder(view2);
                }
            });
            initData();
        }

        public /* synthetic */ void lambda$new$0$IL33$SimpleViewHolder(View view) {
            unSelectSelectedViewHolder();
            selected();
            onItemClick(getAdapterPosition());
        }

        private void unSelectSelectedViewHolder() {
            ViewParent parent = this.itemView.getParent();
            if (parent instanceof BackgroundGroupRecycleView) {
                BackgroundGroupRecycleView backgroundGroupRecycleView = (BackgroundGroupRecycleView) parent;
                if (backgroundGroupRecycleView.selectedViewHolder != null) {
                    backgroundGroupRecycleView.selectedViewHolder.unSelected();
                }
                backgroundGroupRecycleView.selectedPos = getAdapterPosition();
                backgroundGroupRecycleView.selectedViewHolder = this;
            }
        }

        void selected() {
            onSelected();
        }

        void unSelected() {
            onUnSelected();
        }

        void bindView(int i) {
            onBindView(i);
        }
    }
}
