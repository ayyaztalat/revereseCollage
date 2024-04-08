package com.photo.editor.square.splash.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.sky.testproject.R;


/* loaded from: classes2.dex */
public class CSWebViewActivity extends Activity {
    public static final String ACTION_AGREEMENT = "agreement";
    public static final String ACTION_POLICY = "policy";
    public static final String URL_AGREEMENT = "http://www.picsrun.com/magicvideo/magicvideo_user.html";
    public static final String URL_POLICY = "https://docs.google.com/document/d/1mNIRHW61txmZHF44O9IEkMzZIvCAI2PlE-fE8ixWl3A/edit?usp=sharing";

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        fitFullScreen();
        setContentView(R.layout.activity_webview_cs);
        TextView textView = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() { // from class: com.photo.editor.square.splash.activitys.CSWebViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSWebViewActivity.this.finish();
            }
        });
        if (ACTION_POLICY.equals(getIntent().getAction())) {
            textView.setText("Policy us");
            str = URL_POLICY;
        } else {
            textView.setText("UserAgreement");
            str = URL_AGREEMENT;
        }
        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.loadUrl(str);
    }

    private void fitFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().setBackgroundDrawable(null);
    }
}
