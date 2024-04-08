package com.photo.editor.square.splash.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import java.util.Timer;
import java.util.TimerTask;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSViewLockAd extends FrameLayout {
    private static final String TAG = "xlb";
    private boolean adIsShow;
    private boolean adLoaded;
    private Observer<Boolean> adObserver;
    private PopupWindow lockadWindow;
    public FrameLayout ly_back_ad_native_container;
    private View ly_delete;
    private View ly_main;
    private View ly_root;
    private Context mContext;
    private onLockAdLoadedListener mListener;
    private ProgressBar pb_save;
    private int progress_real;
    private int progress_timer;
    private Timer timer;
    private TextView tv_progress;
    private TextView tv_save_title;

    /* loaded from: classes2.dex */
    public interface onLockAdLoadedListener {
        void onAdLoaded();

        void onCloseClick();

        void onPositiveClicked();

        void onProgressDone();
    }

    public void loadAd() {
    }

    static /* synthetic */ int access$208(CSViewLockAd cSViewLockAd) {
        int i = cSViewLockAd.progress_timer;
        cSViewLockAd.progress_timer = i + 1;
        return i;
    }

    public void setOnLockAdNativeItemListener(onLockAdLoadedListener onlockadloadedlistener) {
        this.mListener = onlockadloadedlistener;
    }

    public CSViewLockAd(Context context) {
        super(context);
        this.adLoaded = false;
        this.progress_real = 0;
        this.progress_timer = 0;
        initView(context);
    }

    public CSViewLockAd(Context context, View view) {
        super(context);
        this.adLoaded = false;
        this.progress_real = 0;
        this.progress_timer = 0;
        this.ly_main = view;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_lock_dialog, (ViewGroup) this, false);
        this.ly_root = inflate;
        this.ly_back_ad_native_container = (FrameLayout) inflate.findViewById(R.id.ly_back_ad_container);
        View findViewById = this.ly_root.findViewById(R.id.img_close);
        this.ly_delete = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.CSViewLockAd.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSViewLockAd.this.lockadWindow != null) {
                    CSViewLockAd.this.lockadWindow.dismiss();
                    CSViewLockAd.this.lockadWindow = null;
                }
                if (CSViewLockAd.this.mListener != null) {
                    CSViewLockAd.this.mListener.onCloseClick();
                }
            }
        });
        this.pb_save = (ProgressBar) this.ly_root.findViewById(R.id.pb_save);
        this.tv_progress = (TextView) this.ly_root.findViewById(R.id.tv_save_progress);
        this.tv_save_title = (TextView) this.ly_root.findViewById(R.id.tv_save_title);
    }

    public void showAdNative(DMWBRes dMWBRes, String str, boolean z) {
        PopupWindow popupWindow = this.lockadWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
            this.lockadWindow = null;
        }
        this.ly_back_ad_native_container.setVisibility(View.VISIBLE);
        PopupWindow popupWindow2 = new PopupWindow(this);
        this.lockadWindow = popupWindow2;
        popupWindow2.setWidth(-1);
        this.lockadWindow.setHeight(-1);
        this.lockadWindow.setContentView(this.ly_root);
        this.lockadWindow.setBackgroundDrawable(new ColorDrawable(-872415232));
        this.lockadWindow.setOutsideTouchable(false);
        this.lockadWindow.setFocusable(true);
        this.lockadWindow.showAtLocation(this.ly_main, 17, 0, 0);
        if (dMWBRes instanceof CSWBMaterialRes) {
            if (((CSWBMaterialRes) dMWBRes).isContentExist(CSWBMaterialFactory.PhotolabContentMain)) {
                this.pb_save.setProgress(0);
                this.progress_timer = 100;
                this.progress_real = 100;
                refreshProgress();
                this.tv_save_title.setText(this.mContext.getString(R.string.libui_ols_download_btn_downloading));
                return;
            }
            this.ly_delete.setVisibility(View.INVISIBLE);
            Timer timer = this.timer;
            if (timer != null) {
                timer.cancel();
                this.timer = null;
            }
            this.pb_save.setProgress(0);
            this.progress_timer = 0;
            this.progress_real = 0;
            this.tv_save_title.setText(this.mContext.getString(R.string.libui_ols_download_btn_downloading));
            Timer timer2 = new Timer();
            this.timer = timer2;
            timer2.schedule(new TimerTask() { // from class: com.photo.editor.square.splash.view.CSViewLockAd.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    if (CSViewLockAd.this.lockadWindow == null || !CSViewLockAd.this.lockadWindow.isShowing()) {
                        CSViewLockAd.this.timer.cancel();
                        return;
                    }
                    CSViewLockAd.access$208(CSViewLockAd.this);
                    CSViewLockAd.this.refreshProgress();
                }
            }, 20L, 30L);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setProgress(int i) {
        this.progress_real = i;
        refreshProgress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshProgress() {
        int i = this.progress_real;
        int i2 = this.progress_timer;
        if (i > i2) {
            i = i2;
        }
        int finalI = i;
        ((Activity) this.mContext).runOnUiThread(new Runnable() { // from class: com.photo.editor.square.splash.view.CSViewLockAd.3
            @Override // java.lang.Runnable
            public void run() {
                CSViewLockAd.this.pb_save.setProgress(finalI);
                TextView textView = CSViewLockAd.this.tv_progress;
                textView.setText(finalI + "%");
                if (finalI >= 100) {
                    CSViewLockAd.this.timer.cancel();
                    CSViewLockAd.this.tv_progress.setText("100%");
                    CSViewLockAd.this.tv_save_title.setText(CSViewLockAd.this.mContext.getString(R.string.libui_ols_download_btn_downloading));
                    CSViewLockAd.this.ly_delete.setVisibility(View.VISIBLE);
                    if (CSViewLockAd.this.mListener != null) {
                        CSViewLockAd.this.mListener.onProgressDone();
                    }
                }
            }
        });
    }
}
