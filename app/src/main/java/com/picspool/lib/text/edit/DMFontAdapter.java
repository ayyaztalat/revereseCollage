package com.picspool.lib.text.edit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import com.picspool.lib.packages.DMAppPackages;

import com.picspool.lib.text.font.DMFontList;
import com.sky.testproject.Opcodes;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMFontAdapter extends BaseAdapter {
    private String appName;
    private DMTextFixedView editText;
    private Context mContext;
    private int selectionItem = 0;
    private List<Typeface> tfList = DMFontList.getTfList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMFontAdapter(Context context) {
        this.mContext = context;
        this.appName = DMAppPackages.getAppName(context.getPackageName());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.tfList.size() % 2 == 0 ? this.tfList.size() / 2 : (this.tfList.size() / 2) + 1;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        TextView textView2;
        if (view == null) {
            view = (LinearLayout) ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_systext_text_font_item_view, (ViewGroup) null);
            textView2 = (TextView) view.findViewById(R.id.font_name1);
            textView = (TextView) view.findViewById(R.id.font_name2);
            Holder holder = new Holder();
            holder.textView1 = textView2;
            holder.textView2 = textView;
            view.setTag(holder);
        } else {
            Holder holder2 = (Holder) view.getTag();
            TextView textView3 = holder2.textView1;
            textView = holder2.textView2;
            textView2 = textView3;
        }
        int i2 = i * 2;
        if (this.tfList.size() > i2) {
            textView2.setText(this.appName);
            textView2.setTypeface(this.tfList.get(i2));
            textView2.setTag(Integer.valueOf(i2));
            textView2.setOnClickListener(new BtnFontClickListener());
        }
        int i3 = i2 + 1;
        if (this.tfList.size() > i3) {
            textView.setText(this.appName);
            textView.setTypeface(this.tfList.get(i3));
            textView.setTag(Integer.valueOf(i3));
            textView.setOnClickListener(new BtnFontClickListener());
        }
        int i4 = this.selectionItem;
        if (i4 == i2) {
            textView2.setTextColor(Color.rgb(35, (int) Opcodes.NEWARRAY, 201));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.text_color));
        } else if (i4 == i3) {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.text_color));
            textView.setTextColor(Color.rgb(35, (int) Opcodes.NEWARRAY, 201));
        } else {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.text_color));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.text_color));
        }
        return view;
    }

    /* loaded from: classes3.dex */
    private class BtnFontClickListener implements View.OnClickListener {
        private BtnFontClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            DMFontAdapter.this.editText.setTextTypeface(DMFontList.getTfList().get(intValue));
            DMFontAdapter.this.editText.getTextDrawer().setTypefaceIndex(intValue);
            DMFontAdapter.this.setSelection(intValue);
        }
    }

    public void setSelection(int i) {
        this.selectionItem = i;
        notifyDataSetChanged();
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        public TextView textView1;
        public TextView textView2;

        private Holder() {
        }
    }

    public void setEditText(DMTextFixedView dMTextFixedView) {
        this.editText = dMTextFixedView;
    }
}
