package com.magic.video.editor.effect.gallery.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sky.testproject.R;
import java.util.List;

/* loaded from: classes2.dex */
public class CSGallerySelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AdapterView.OnItemClickListener itemClickListener;
    private final Context mContext;
    private final List<Uri> uris;

    /* loaded from: classes2.dex */
    public static class SelectedListHolder extends RecyclerView.ViewHolder {
        private final ImageView delete;
        private final CSIgnoreRecycleImageView icon;

        SelectedListHolder(View view) {
            super(view);
            this.icon = (CSIgnoreRecycleImageView) view.findViewById(R.id.bg_icon_image);
            this.delete = (ImageView) view.findViewById(R.id.item_delete);
        }
    }

    public CSGallerySelectedAdapter(Context context, List<Uri> list) {
        this.mContext = context;
        this.uris = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SelectedListHolder(((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_gallery_selected_item_cs, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final SelectedListHolder selectedListHolder = (SelectedListHolder) viewHolder;
        if (this.uris.get(i) != null) {
            selectedListHolder.delete.setOnClickListener(new View.OnClickListener() { // from class: com.magic.video.editor.effect.gallery.view.-$$Lambda$CSGallerySelectedAdapter$CMZZ-7a_VE8y4AWhny8GAppWmqc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CSGallerySelectedAdapter.this.lambda$onBindViewHolder$0$CSGallerySelectedAdapter(selectedListHolder, i, view);
                }
            });
            Glide.with(this.mContext).load(this.uris.get(i)).override(selectedListHolder.icon.getWidth(), selectedListHolder.icon.getHeight()).into(selectedListHolder.icon);
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$CSGallerySelectedAdapter(SelectedListHolder selectedListHolder, int i, View view) {
        selectedListHolder.delete.setClickable(false);
        AdapterView.OnItemClickListener onItemClickListener = this.itemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, null, i, 0L);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.uris.size();
    }

    public void setItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public void addData(int i) {
        notifyItemRangeInserted(i, this.uris.size());
    }

    public void removeData(int i) {
        int size = this.uris.size();
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, size);
    }
}
