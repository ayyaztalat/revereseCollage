package com.picspool.lib.onlinestore.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.testproject.R;


/* loaded from: classes3.dex */
public class DMDownloadDialog extends Dialog {
    private DMDownloadView BMDownloadView;
    private TextView downCursor;
    private int progress;
    private View rootView;

    public DMDownloadDialog(Context context) {
        super(context);
    }

    public DMDownloadDialog(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dm_dialog_download);
        this.rootView = findViewById(R.id.root_view);
        this.downCursor = (TextView) findViewById(R.id.download_cursor);
        this.BMDownloadView = (DMDownloadView) findViewById(R.id.download_view);
        setCanceledOnTouchOutside(false);
    }

    public void updateCursor(int i) {
        if (this.progress < i) {
            this.progress = i;
        }
        try {
            this.BMDownloadView.updateProgress(this.progress);
            TextView textView = this.downCursor;
            textView.setText("" + this.progress + "%");
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
        this.progress = 0;
        this.downCursor.setLayoutParams(layoutParams);
        this.downCursor.setText("0%");
        this.BMDownloadView.iniProgress();
        this.rootView.invalidate();
    }
}
