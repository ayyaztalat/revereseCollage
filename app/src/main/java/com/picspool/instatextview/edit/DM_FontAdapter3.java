package com.picspool.instatextview.edit;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import com.sky.testproject.R;
import com.picspool.instatextview.resource.DMWBFontRes;
import com.picspool.instatextview.resource.manager.DMFontManager;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.lib.packages.DMAppPackages;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class DM_FontAdapter3 extends BaseAdapter {
    private String appName;
    private DM_TextFixedView3 editText;
    private DMFontManager fontManager;
    private Context mContext;
    private List<DMWBFontRes> resList;
    private int selectionItem = 0;
    private boolean isLonding = false;
    private List<Typeface> tfList = DMInstaTextView3.getTfList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DM_FontAdapter3(Context context) {
        this.mContext = context;
        this.appName = DMAppPackages.getAppName(context.getPackageName());
        DMFontManager dMFontManager = new DMFontManager(context);
        this.fontManager = dMFontManager;
        dMFontManager.setContext(context);
        this.resList = this.fontManager.getResList();
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
            view = (LinearLayout) ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_font_item_view, (ViewGroup) null);
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
        if (this.resList.size() > i3 && this.tfList.size() <= i3 && this.resList.get(i3) != null) {
            DMWBFontRes dMWBFontRes = this.resList.get(i3);
            if (dMWBFontRes.getLocationType() == DMWBRes.LocationType.ONLINE) {
                if (dMWBFontRes.isContentExits()) {
                    textView2.setText(this.appName);
                    try {
                        textView2.setTypeface(Typeface.createFromFile(dMWBFontRes.getFontFileName()));
                    } catch (Throwable unused) {
                    }
                    textView2.setTag(Integer.valueOf(i2));
                    textView2.setOnClickListener(new BtnFontClickListener());
                } else if (!this.isLonding) {
                    new DownloadFile(this.mContext).execute(dMWBFontRes.getFontUrl().replace("\\", ""));
                }
            }
        }
        int i4 = i2 + 2;
        if (this.resList.size() > i4 && this.tfList.size() <= i4 && this.resList.get(i4) != null) {
            DMWBFontRes dMWBFontRes2 = this.resList.get(i4);
            if (dMWBFontRes2.getLocationType() == DMWBRes.LocationType.ONLINE) {
                if (dMWBFontRes2.isContentExits()) {
                    textView.setText(this.appName);
                    try {
                        textView.setTypeface(Typeface.createFromFile(dMWBFontRes2.getFontFileName()));
                    } catch (Throwable unused2) {
                    }
                    textView.setTag(Integer.valueOf(i2));
                    textView.setOnClickListener(new BtnFontClickListener());
                } else if (!this.isLonding) {
                    new DownloadFile(this.mContext).execute(dMWBFontRes2.getFontUrl().replace("\\", ""));
                }
            }
        }
        int i5 = this.selectionItem;
        if (i5 == i2) {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.fontselect_color));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.font_color));
        } else if (i5 == i3) {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.font_color));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.fontselect_color));
        } else {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.font_color));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.font_color));
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
            if (DM_FontAdapter3.this.fontManager.getRes(intValue) != null) {
                DMWBFontRes res = DM_FontAdapter3.this.fontManager.getRes(intValue);
                if (res.getLocationType() == DMWBRes.LocationType.ASSERT) {
                    DM_FontAdapter3.this.editText.setTextTypeface((Typeface) DM_FontAdapter3.this.tfList.get(intValue));
                    DM_FontAdapter3.this.editText.getTextDrawer().setTypefaceIndex(intValue);
                    DM_FontAdapter3.this.setSelection(intValue);
                } else if (res.getLocationType() == DMWBRes.LocationType.ONLINE) {
                    try {
                        if (res.isContentExits()) {
                            DM_FontAdapter3.this.editText.setTextTypeface(Typeface.createFromFile(res.getFontFileName()));
                            DM_FontAdapter3.this.setSelection(intValue);
                        } else {
                            File file = new File(DM_FontAdapter3.this.mContext.getCacheDir() + "/picsjoin/" + res.getFontUrl().split("/")[res.getFontUrl().split("/").length - 1]);
                            if (!file.exists()) {
                                return;
                            }
                            DM_FontAdapter3.this.editText.setTextTypeface(Typeface.createFromFile(file.getAbsolutePath()));
                            DM_FontAdapter3.this.setSelection(intValue);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
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

    public void setEditText(DM_TextFixedView3 dM_TextFixedView3) {
        this.editText = dM_TextFixedView3;
    }

    /* loaded from: classes3.dex */
    class DownloadFile extends AsyncTask<String, Integer, String> {
        File file;
        WeakReference<Context> mContext;

        DownloadFile(Context context) {
            this.mContext = new WeakReference<>(context);
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            DM_FontAdapter3.this.isLonding = true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:100:0x014b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0164 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public String doInBackground(String... r15) {
            /*
                Method dump skipped, instructions count: 380
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.picspool.instatextview.edit.DM_FontAdapter3.DownloadFile.doInBackground(java.lang.String[]):java.lang.String");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            super.onPostExecute( str);
            if (!str.isEmpty()) {
                try {
                    Typeface createFromFile = Typeface.createFromFile(str);
                    if (createFromFile != null && !DM_FontAdapter3.this.tfList.contains(createFromFile)) {
                        DM_FontAdapter3.this.tfList.add(createFromFile);
                        DM_FontAdapter3.this.notifyDataSetChanged();
                    }
                } catch (Exception unused) {
                }
            }
            DM_FontAdapter3.this.isLonding = false;
        }
    }
}
