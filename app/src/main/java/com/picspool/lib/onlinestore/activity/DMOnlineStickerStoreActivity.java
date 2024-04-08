package com.picspool.lib.onlinestore.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.ZipException;
import com.picspool.lib.activity.DMFragmentActivityTemplate;
import com.picspool.lib.http.DMAsyncTextHttp;
import com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.onlinestore.resource.manager.DMMaterialResManager;
import com.picspool.lib.onlinestore.widget.DMDownloadDialog;
import com.picspool.lib.onlinestore.widget.DMStoreDownListAdapter;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMOnlineStickerStoreActivity extends DMFragmentActivityTemplate implements DMStoreDownListAdapter.SelectedListener {
    public static String BgMaterialType = "sticker";
    public static final int STICKER_STORE_CODE = 256;
    public static final String StickerMaterialType = "sticker";
    private static String packageName;
    private static String versionName;
    private List<WBMaterialRes> WBRess;
    private DMDownloadDialog dialog;
    private DMStoreDownListAdapter downAdapter;
    private boolean downChangeFlag;
    private TextView existDownText;
    private List<WBMaterialRes> filesDirRess;
    private boolean iniFlag;
    private ShowListMode listMode = ShowListMode.noDownload;
    private ListView listView;
    private DMMaterialResManager mManager;
    private boolean networkFlag;
    private TextView noDownText;
    private String requestAppName;
    private String requestFunctionName;
    private List<WBMaterialRes> ress;
    private String wbResult;

    /* loaded from: classes3.dex */
    public enum ShowListMode {
        noDownload,
        existDownload
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dm_online_store);
        findViewById(R.id.activity_store_break).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DMOnlineStickerStoreActivity.this.downChangeFlag) {
                    DMOnlineStickerStoreActivity dMOnlineStickerStoreActivity = DMOnlineStickerStoreActivity.this;
                    dMOnlineStickerStoreActivity.setResult(256, dMOnlineStickerStoreActivity.getIntent());
                    DMOnlineStickerStoreActivity.this.finish();
                    return;
                }
                DMOnlineStickerStoreActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        this.requestAppName = intent.getStringExtra("appName");
        this.requestFunctionName = intent.getStringExtra("functionName");
        this.listView = (ListView) findViewById(R.id.activity_store_list_view);
        TextView textView = (TextView) findViewById(R.id.activity_store_no_download);
        this.noDownText = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMOnlineStickerStoreActivity.this.noDownTextClick();
            }
        });
        TextView textView2 = (TextView) findViewById(R.id.activity_store_exist_download);
        this.existDownText = textView2;
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMOnlineStickerStoreActivity.this.existDownTextClick();
            }
        });
        this.noDownText.setBackgroundResource(R.drawable.dm_store_button_text_bg_shape);
        this.dialog = new DMDownloadDialog(this, R.style.DownloadDialog);
        this.ress = new ArrayList();
        this.mManager = new DMMaterialResManager(this);
        DMStoreDownListAdapter dMStoreDownListAdapter = new DMStoreDownListAdapter(this);
        this.downAdapter = dMStoreDownListAdapter;
        dMStoreDownListAdapter.setsListener(this);
        versionName = getVersion();
        packageName = getApplication().getPackageName();
    }

    protected void existDownTextClick() {
        this.listMode = ShowListMode.existDownload;
        updateListView();
        this.existDownText.setBackgroundResource(R.drawable.dm_store_button_text_bg_shape);
        this.noDownText.setBackgroundColor(0);
        this.existDownText.setTextColor(getResources().getColor(R.color.store_download_button_select_color));
        this.noDownText.setTextColor(getResources().getColor(R.color.store_download_button_color));
    }

    protected void noDownTextClick() {
        this.listMode = ShowListMode.noDownload;
        updateListView();
        this.noDownText.setBackgroundResource(R.drawable.dm_store_button_text_bg_shape);
        this.existDownText.setBackgroundColor(0);
        this.existDownText.setTextColor(getResources().getColor(R.color.store_download_button_select_color));
        this.noDownText.setTextColor(getResources().getColor(R.color.store_download_button_select_color));
    }

    private String getVersion() {
        try {
            return getPackageManager().getPackageInfo(getApplication().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        boolean checkNetwork = checkNetwork(this);
        this.networkFlag = checkNetwork;
        if (!checkNetwork || this.iniFlag) {
            if (!this.iniFlag) {
                Toast.makeText(this, getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
            }
            new Handler(getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    DMOnlineStickerStoreActivity.this.updateListView();
                }
            });
            return;
        }
        showProcessDialog();
        DMAsyncTextHttp.asyncHttpRequest(getMaterialUrlBase(this.requestAppName, this.requestFunctionName), new DMAsyncTextHttp.AsyncTextHttpTaskListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.5
            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFinishLoad(final String str) {
                new Handler(DMOnlineStickerStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DMOnlineStickerStoreActivity.this.wbResult = str;
                        DMOnlineStickerStoreActivity.this.updateListView();
                        DMOnlineStickerStoreActivity.this.dismissProcessDialog();
                    }
                });
            }

            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFailedStatus(Exception exc) {
                new Handler(DMOnlineStickerStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.5.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(DMOnlineStickerStoreActivity.this, DMOnlineStickerStoreActivity.this.getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
                        DMOnlineStickerStoreActivity.this.dismissProcessDialog();
                    }
                });
            }
        });
        this.iniFlag = true;
    }

    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.downChangeFlag) {
                setResult(256, getIntent());
                finish();
                return false;
            }
            finish();
            return false;
        }
        return false;
    }

    public void updateListView() {
        this.filesDirRess = WBMaterialFactory.CreateMaterialFromFilesDir(this, "sticker");
        this.downAdapter.dispose();
        if (!this.networkFlag) {
            this.ress.clear();
            for (WBMaterialRes wBMaterialRes : this.filesDirRess) {
                if (this.listMode == ShowListMode.noDownload) {
                    if (!wBMaterialRes.isContentExist()) {
                        this.ress.add(wBMaterialRes);
                    }
                } else if (this.listMode == ShowListMode.existDownload && wBMaterialRes.isContentExist()) {
                    this.ress.add(wBMaterialRes);
                }
            }
            this.mManager.setMaterialRess(this.ress);
            this.downAdapter.setResManager(this.mManager, this.listMode);
            this.listView.setAdapter((ListAdapter) this.downAdapter);
            return;
        }
        this.WBRess = WBMaterialFactory.CreateMaterialsFromJSON(this, this.wbResult);
        this.ress.clear();
        this.ress.addAll(this.WBRess);
        for (WBMaterialRes wBMaterialRes2 : this.filesDirRess) {
            if (!this.ress.contains(wBMaterialRes2)) {
                if (wBMaterialRes2.isContentExist()) {
                    this.ress.add(wBMaterialRes2);
                }
            } else if (wBMaterialRes2.isContentExist()) {
                this.ress.remove(this.ress.indexOf(wBMaterialRes2));
                this.ress.add(wBMaterialRes2);
            }
        }
        clearDownloadMaterial();
        this.mManager.setMaterialRess(this.ress);
        this.downAdapter.setResManager(this.mManager, this.listMode);
        this.listView.setAdapter((ListAdapter) this.downAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDownloadMaterial() {
        Iterator<WBMaterialRes> it2 = this.ress.iterator();
        while (it2.hasNext()) {
            WBMaterialRes next = it2.next();
            if (this.listMode == ShowListMode.noDownload) {
                if (next.isContentExist()) {
                    it2.remove();
                }
            } else if (!next.isContentExist()) {
                it2.remove();
            }
        }
        if (this.ress.size() == 0) {
            if (this.listMode == ShowListMode.noDownload) {
                Toast.makeText(this, R.string.no_new_material, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.no_downloaded, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getMaterialUrlBase(String str, String str2) {
        String str3;
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0) {
            str = "instasquare";
            str2 = "sticker";
        } else {
            BgMaterialType = str2;
        }
        String str4 = "http://s1.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str5 = "http://s2.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str6 = "http://s3.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str7 = "http://s4.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str8 = "http://s5.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        ArrayList arrayList = new ArrayList();
        arrayList.add(str4);
        arrayList.add(str5);
        arrayList.add(str6);
        arrayList.add(str7);
        arrayList.add(str8);
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        String str9 = (String) arrayList.get(nextInt);
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String lowerCase = locale.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(lowerCase)) {
                str9 = str9 + "1&&statue=2";
            } else if ("tw".equals(lowerCase)) {
                str9 = str9 + "2&&statue=2";
            }
        } else {
            str9 = str9 + "0&&statue=2";
        }
        if (lowerCase.equals("cn")) {
            str3 = str9 + "&&country_code=1";
        } else if (lowerCase.equals("hk") || lowerCase.equals("mo") || lowerCase.equals("tw") || lowerCase.equals("th") || lowerCase.equals("my") || lowerCase.equals("sg") || lowerCase.equals("id") || lowerCase.equals("ph") || lowerCase.equals("jp") || lowerCase.equals("kp") || lowerCase.equals("in")) {
            str3 = str9 + "&&country_code=2";
        } else {
            str3 = str9 + "&&country_code=0";
        }
        String str10 = ((((str3 + "&&country_name=" + lowerCase) + "&&language_name=" + language) + "&&version_name=" + versionName) + "&&plat_type=android") + "&&phone_model=" + Build.MODEL.replaceAll(" ", "");
        if (Build.VERSION.SDK_INT >= 9) {
            str10 = str10 + "&&phone_sdk_version=" + Build.SERIAL;
        }
        return (str10 + "&&phone_sys_version=" + Build.VERSION.RELEASE) + "&&package_name=" + packageName;
    }

    @Override // com.picspool.lib.onlinestore.widget.DMStoreDownListAdapter.SelectedListener
    public void onSelected(WBMaterialRes wBMaterialRes) {
        if (this.listMode == ShowListMode.noDownload) {
            if (this.networkFlag) {
                if (!wBMaterialRes.isContentExist()) {
                    this.dialog.show();
                    wBMaterialRes.downloadFileOnlineRes(this, new DownloadListener(wBMaterialRes));
                    this.downChangeFlag = true;
                    return;
                }
                Toast.makeText(this, getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
            return;
        }
        this.downChangeFlag = true;
        dialog(wBMaterialRes);
    }

    protected void dialog(final WBMaterialRes wBMaterialRes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message);
        builder.setTitle(R.string.dialog_prompt);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (wBMaterialRes.isContentExist()) {
                    wBMaterialRes.delMaterialFromFile();
                    DMOnlineStickerStoreActivity.this.clearDownloadMaterial();
                    if (DMOnlineStickerStoreActivity.this.downAdapter != null) {
                        DMOnlineStickerStoreActivity.this.downAdapter.notifyDataSetChanged();
                    }
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)) == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        return (networkInfo != null ? networkInfo.isConnectedOrConnecting() : false) | (networkInfo2 != null ? networkInfo2.isConnectedOrConnecting() : false);
    }

    /* loaded from: classes3.dex */
    protected class DownloadListener implements DMAsyncDownloadFileLoad.AsyncDownloadFileListener {
        WBMaterialRes res;

        public DownloadListener(WBMaterialRes wBMaterialRes) {
            this.res = null;
            this.res = wBMaterialRes;
        }

        @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onProgressUpdate(Integer... numArr) {
            if (DMOnlineStickerStoreActivity.this.dialog != null) {
                DMOnlineStickerStoreActivity.this.dialog.updateCursor(numArr[0].intValue());
            }
        }

        @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onPostExecute(Object obj) {
            WBMaterialRes wBMaterialRes;
            if (!((Boolean) obj).booleanValue() || (wBMaterialRes = this.res) == null) {
                return;
            }
            try {
                wBMaterialRes.upZip();
                this.res.delContentDownFromFile();
                DMOnlineStickerStoreActivity.this.clearDownloadMaterial();
                if (DMOnlineStickerStoreActivity.this.downAdapter != null) {
                    DMOnlineStickerStoreActivity.this.downAdapter.notifyDataSetChanged();
                }
                if (DMOnlineStickerStoreActivity.this.dialog != null) {
                    DMOnlineStickerStoreActivity.this.dialog.dismiss();
                }
            } catch (ZipException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            DMOnlineStickerStoreActivity.this.downChangeFlag = true;
        }

        @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onImageDownLoadFaile() {
            new Handler(DMOnlineStickerStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity.DownloadListener.1
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(DMOnlineStickerStoreActivity.this, R.string.download_failure, Toast.LENGTH_LONG).show();
                    if (DownloadListener.this.res != null) {
                        DownloadListener.this.res.delContentFromFile();
                    }
                    if (DMOnlineStickerStoreActivity.this.dialog != null) {
                        DMOnlineStickerStoreActivity.this.dialog.dismiss();
                    }
                }
            });
        }
    }
}
