package com.picspool.lib.onlinestore.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.picspool.lib.onlinestore.widget.DMBgListAdapter;
import com.picspool.lib.onlinestore.widget.DMDownloadDialog;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMOnlineBgStoreActivity extends DMFragmentActivityTemplate implements DMBgListAdapter.SelectedListener {
    public static final int BG_STORE_CODE = 257;
    public static String BgMaterialType = "bgpics";
    private static String packageName;
    private static String versionName;
    private List<WBMaterialRes> WBRess;
    private DMBgListAdapter adapter;
    private DMDownloadDialog dialog;
    private boolean downChangeFlag;
    private List<WBMaterialRes> filesDirRess;
    private boolean iniFlag;
    private ListView listView;
    private DMMaterialResManager mManager;
    private boolean networkFlag;
    private String requestAppName;
    private String requestFunctionName;
    private List<WBMaterialRes> ress;
    private String wbResult;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dm_bg_store);
        Intent intent = getIntent();
        this.requestAppName = intent.getStringExtra("appName");
        this.requestFunctionName = intent.getStringExtra("functionName");
        findViewById(R.id.activity_store_break).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMOnlineBgStoreActivity.this.setResult(257);
                DMOnlineBgStoreActivity.this.finish();
            }
        });
        findViewById(R.id.activity_store_manager).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMOnlineBgStoreActivity.this.startActivity(new Intent(DMOnlineBgStoreActivity.this, DMOnlineBgManagerActivity.class));
            }
        });
        this.listView = (ListView) findViewById(R.id.bg_list_view);
        this.dialog = new DMDownloadDialog(this, R.style.DownloadDialog);
        this.ress = new ArrayList();
        this.mManager = new DMMaterialResManager(this);
        DMBgListAdapter dMBgListAdapter = new DMBgListAdapter(this);
        this.adapter = dMBgListAdapter;
        dMBgListAdapter.setsListener(this);
        versionName = getVersion();
        packageName = getApplication().getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        boolean checkNetwork = DMOnlineStickerStoreActivity.checkNetwork(this);
        this.networkFlag = checkNetwork;
        if (!checkNetwork || this.iniFlag) {
            if (!this.iniFlag) {
                Toast.makeText(this, getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
            }
            new Handler(getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    DMOnlineBgStoreActivity.this.updateListView();
                }
            });
            return;
        }
        showProcessDialog();
        DMAsyncTextHttp.asyncHttpRequest(getMaterialUrlBase(this.requestAppName, this.requestFunctionName), new DMAsyncTextHttp.AsyncTextHttpTaskListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.4
            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFinishLoad(final String str) {
                new Handler(DMOnlineBgStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DMOnlineBgStoreActivity.this.wbResult = str;
                        DMOnlineBgStoreActivity.this.updateListView();
                        DMOnlineBgStoreActivity.this.dismissProcessDialog();
                    }
                });
            }

            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFailedStatus(Exception exc) {
                new Handler(DMOnlineBgStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(DMOnlineBgStoreActivity.this, DMOnlineBgStoreActivity.this.getResources().getString(R.string.warning_failed_connectnet), Toast.LENGTH_SHORT).show();
                        DMOnlineBgStoreActivity.this.dismissProcessDialog();
                    }
                });
            }
        });
        this.iniFlag = true;
    }

    public void updateListView() {
        this.filesDirRess = WBMaterialFactory.CreateMaterialFromFilesDir(this, BgMaterialType);
        this.adapter.dispose();
        if (!this.networkFlag) {
            this.ress.clear();
            for (WBMaterialRes wBMaterialRes : this.filesDirRess) {
                if (!wBMaterialRes.isContentExist()) {
                    this.ress.add(wBMaterialRes);
                }
            }
            this.mManager.setMaterialRess(this.ress);
            this.adapter.setResManager(this.mManager);
            this.listView.setAdapter((ListAdapter) this.adapter);
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
        this.adapter.setResManager(this.mManager);
        this.listView.setAdapter((ListAdapter) this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearDownloadMaterial() {
        Iterator<WBMaterialRes> it2 = this.ress.iterator();
        while (it2.hasNext()) {
            if (it2.next().isContentExist()) {
                it2.remove();
            }
        }
        if (this.ress.size() == 0) {
            Toast.makeText(this, R.string.no_new_material, Toast.LENGTH_LONG).show();
        }
    }

    public static String getMaterialUrlBase(String str, String str2) {
        String str3;
        String str4;
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0) {
            str = "instasquare";
            str2 = "bgpics";
        } else {
            BgMaterialType = str2;
        }
        String str5 = "http://s1.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str6 = "http://s2.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str7 = "http://s3.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str8 = "http://s4.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str9 = "http://s5.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        ArrayList arrayList = new ArrayList();
        arrayList.add(str5);
        arrayList.add(str6);
        arrayList.add(str7);
        arrayList.add(str8);
        arrayList.add(str9);
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        String str10 = (String) arrayList.get(nextInt);
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String lowerCase = locale.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(lowerCase)) {
                str3 = str10 + "1&&statue=2";
            } else {
                str3 = str10 + "2&&statue=2";
            }
        } else {
            str3 = str10 + "0&&statue=2";
        }
        if (lowerCase.equals("cn")) {
            str4 = str3 + "&&country_code=1";
        } else if (lowerCase.equals("hk") || lowerCase.equals("mo") || lowerCase.equals("tw") || lowerCase.equals("th") || lowerCase.equals("my") || lowerCase.equals("sg") || lowerCase.equals("id") || lowerCase.equals("ph") || lowerCase.equals("jp") || lowerCase.equals("kp") || lowerCase.equals("in")) {
            str4 = str3 + "&&country_code=2";
        } else {
            str4 = str3 + "&&country_code=0";
        }
        String str11 = ((((str4 + "&&country_name=" + lowerCase) + "&&language_name=" + language) + "&&version_name=" + versionName) + "&&plat_type=android") + "&&phone_model=" + Build.MODEL.replaceAll(" ", "");
        if (Build.VERSION.SDK_INT >= 9) {
            str11 = str11 + "&&phone_sdk_version=" + Build.SERIAL;
        }
        return (str11 + "&&phone_sys_version=" + Build.VERSION.RELEASE) + "&&package_name=" + packageName;
    }

    private String getVersion() {
        try {
            return getPackageManager().getPackageInfo(getApplication().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            setResult(257);
            finish();
            return false;
        }
        return false;
    }

    @Override // com.picspool.lib.onlinestore.widget.DMBgListAdapter.SelectedListener
    public void onSelected(WBMaterialRes wBMaterialRes) {
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
            if (DMOnlineBgStoreActivity.this.dialog != null) {
                DMOnlineBgStoreActivity.this.dialog.updateCursor(numArr[0].intValue());
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
                DMOnlineBgStoreActivity.this.clearDownloadMaterial();
                if (DMOnlineBgStoreActivity.this.adapter != null) {
                    DMOnlineBgStoreActivity.this.adapter.notifyDataSetChanged();
                }
                if (DMOnlineBgStoreActivity.this.dialog != null) {
                    DMOnlineBgStoreActivity.this.dialog.dismiss();
                }
            } catch (ZipException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            DMOnlineBgStoreActivity.this.downChangeFlag = true;
        }

        @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
        public void onImageDownLoadFaile() {
            new Handler(DMOnlineBgStoreActivity.this.getMainLooper()).post(new Runnable() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgStoreActivity.DownloadListener.1
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(DMOnlineBgStoreActivity.this, R.string.download_failure, 1).show();
                    if (DownloadListener.this.res != null) {
                        DownloadListener.this.res.delContentFromFile();
                    }
                    if (DMOnlineBgStoreActivity.this.dialog != null) {
                        DMOnlineBgStoreActivity.this.dialog.dismiss();
                    }
                }
            });
        }
    }
}
