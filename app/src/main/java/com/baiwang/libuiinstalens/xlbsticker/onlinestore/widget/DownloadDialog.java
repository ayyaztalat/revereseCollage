package com.baiwang.libuiinstalens.xlbsticker.onlinestore.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class DownloadDialog extends Dialog {
    private TextView downCursor;
    private DownloadView downloadView;
    private View rootView;

    public DownloadDialog(Context context) {
        super(context);
    }

    public DownloadDialog(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_download_progress);
        this.rootView = findViewById(R.id.root_view);
        this.downCursor = (TextView) findViewById(R.id.download_cursor);
        this.downloadView = (DownloadView) findViewById(R.id.download_view);
        setCanceledOnTouchOutside(false);
    }

    public void updateCursor(int i) {
        try {
            this.downloadView.updateProgress(i);
            TextView textView = this.downCursor;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            if (i >= 95) {
                i = 99;
            }
            sb.append(i);
            sb.append("%");
            textView.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.downCursor.getLayoutParams();
        layoutParams.leftMargin = 0;
        layoutParams.topMargin = layoutParams.topMargin;
        this.downCursor.setLayoutParams(layoutParams);
        this.downCursor.setText("0%");
        this.downloadView.iniProgress();
        this.rootView.invalidate();
    }
}
