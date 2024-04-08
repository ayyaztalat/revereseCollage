package com.baiwang.libuiinstalens.xlbsticker.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreListAdapter;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialGroupRes;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;


/* loaded from: classes.dex */
public class CSOnlineStoreActivity extends AppCompatActivity {
    public static final String STICKER_EXTRA = "sticker_extra";
    private List<CSWBMaterialGroupRes> WBGroupRess;
    private View btn_back;
    private View btn_setting;
    private CSOnlineStoreDownLoadView downLoadView;
    public FrameLayout ly_main;
    private View ly_progress;
    private Intent nextActivityIntent;
    private RecyclerView recyclerView;
    private String requestAppName;
    private CSOnlineStoreListAdapter storeListAdapter;
    private boolean networkFlag = true;
    public boolean isAdLoaded = false;
    public boolean isVungleAd = false;
    private String DContentViewName = "";
    private boolean isDContentViewShowed = false;

    public void lockeResClicked(String str, DMWBRes dMWBRes) {
    }

    public void stickerdownloadClicked(String str, DMWBRes dMWBRes) {
    }

    public void stickerdownloadPrgress(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sticker_onlinestore_cs);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(1024, 1024);
        this.requestAppName = getPackageName();
        this.DContentViewName = getIntent().getStringExtra("dcontentviewName");
        this.nextActivityIntent = (Intent) getIntent().getParcelableExtra("nextActivityIntent");
        this.btn_back = findViewById(R.id.btn_back);
        this.btn_setting = findViewById(R.id.btn_setting);
        this.ly_progress = findViewById(R.id.progressBar);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);
        this.ly_main = (FrameLayout) findViewById(R.id.ly_mian);
        this.btn_back.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSOnlineStoreActivity.this.setResult(-1, new Intent());
                CSOnlineStoreActivity.this.finish();
            }
        });
        this.btn_setting.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSOnlineStoreActivity.this.startActivity(new Intent(CSOnlineStoreActivity.this, CSStickerSettingActivity.class));
            }
        });
        initJsonData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CSOnlineStoreListAdapter cSOnlineStoreListAdapter = this.storeListAdapter;
        if (cSOnlineStoreListAdapter != null) {
            cSOnlineStoreListAdapter.notifyDataSetChanged();
        }
    }

    private void initJsonData() {
        Log.d("xlb", "initJsonData____1");
        this.ly_progress.setVisibility(View.VISIBLE);
        noNetworkOrRequestFailed();
        boolean checkNetwork = CSStickerBarConfig.checkNetwork(this);
        this.networkFlag = checkNetwork;
        if (!checkNetwork) {
            Toast.makeText(this, getResources().getString(R.string.warning_failed_connectnet_new), Toast.LENGTH_SHORT).show();
            this.ly_progress.setVisibility(View.GONE);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        String str = null;
        try {
            jSONObject.put("statue", 2);
            jSONObject.put("county", 1);
            jSONObject.put("version", 2);
            jSONObject.put("package", this.requestAppName);
            str = CSRsaUtils.encrypt(jSONObject.toString(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAto4URLiN/OkTVcD1XNF8\nZ7Guj0TP6ZhsWb3qr9ycv6ZG4ZUI/781SmzKqJLYlb2xAXJj+6wqkiqwLWE/VYlI\n+42G3o466iZt2KCfu/ce+OAeJYbAcFUzkZFGnH+VorZ63YMWx6SHIpJHKYsFdCcg\nwcbPlXkOL37/f9VrzcD1DJAMPAFn7kMRWbWKqCzQskJzi3KyJmcnydbuP8bvXs4/\nYSKqTg0kH12aWZbeNVTGhNli7raOONeN4LTYcgaihER1Rkp0a1gb86bfoco2c3IA\njPp8D9NMeR8t7IRoy70Fhn9H2oJZ3o6RSMCiaSNokK+XYyxTUbdZfxwdy8l9P2CB\n1QIDAQAB");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (str == null) {
            Toast.makeText(this, "data wrong,please try again later", Toast.LENGTH_LONG).show();
            this.ly_progress.setVisibility(View.GONE);
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("data", str);
        new OkHttpClient.Builder().build().newCall(new Request.Builder().post(builder.build()).url(CSStickerBarConfig.getMaterialUrlBase(this.requestAppName, "sticker")).build()).enqueue(new Callback() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.3
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.d("xlb", "onFailure____0");
                if (iOException != null) {
                    try {
                        iOException.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String obj = new JSONObject(response.body().string()).get("data").toString();
                    if (TextUtils.isEmpty(obj)) {
                        return;
                    }
                    try {
                        if (obj.length() <= 5) {
                            return;
                        }
                        final String str2 = new String(Base64.decode(obj.substring(5).getBytes(StandardCharsets.UTF_8), 0), StandardCharsets.UTF_8);
                        Log.e("xlb", "onResponse: " + str2);
                        CSOnlineStoreActivity.this.runOnUiThread(new Runnable() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (str2.length() == 0) {
                                    return;
                                }
                                Log.d("xlb", "result json____2");
//                                CSOnlineStoreActivity.this.handleJsonBindDataServer(str2);
                            }
                        });
                    } catch (Exception unused) {
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public void noNetworkOrRequestFailed() {
        String packageName = getApplicationContext().getPackageName();
        File filesDir = getFilesDir();
        String str = (filesDir.getAbsolutePath() + "/" + packageName + CSWBMaterialFactory.SDRootDirName) + "/.stickerjsoncache.txt";
        if (new File(str).exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                handleJsonBindDataLocal(readLine);
                return;
            } catch (Exception unused) {
                Log.d("TAG", "noNetworkOrRequestFailed: ");
                return;
            }
        }
        Log.d("TAG", "noNetworkOrRequestFailed: ");
    }

    public void handleJsonBindDataLocal(String str) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    List<CSWBMaterialGroupRes> CreateGroupMaterialListFromJSON = CSWBMaterialFactory.CreateGroupMaterialListFromJSON(this, str, ".stickerjsoncache.txt", true);
                    if (this.WBGroupRess != null) {
                        this.WBGroupRess.clear();
                        this.WBGroupRess = null;
                    }
                    this.WBGroupRess = new ArrayList();
                    if (CreateGroupMaterialListFromJSON != null && CreateGroupMaterialListFromJSON.size() > 0) {
                        for (int i = 0; i < CreateGroupMaterialListFromJSON.size(); i++) {
                            this.WBGroupRess.add(CreateGroupMaterialListFromJSON.get(i));
                        }
                    }
                    if (this.WBGroupRess == null) {
                        Toast.makeText(this, "data wrong,please try again later", Toast.LENGTH_LONG).show();
                        this.ly_progress.setVisibility(View.GONE);
                        return;
                    }
                    List<CSWBMaterialGroupRes> list = this.WBGroupRess;
                    if (list != null) {
                        initRecycleview(list);
                        return;
                    }
                    return;
                }
            } catch (Exception unused) {
                Toast.makeText(this, "data wrong,please try again later", Toast.LENGTH_LONG).show();
                this.ly_progress.setVisibility(View.GONE);
                return;
            }
        }
        Toast.makeText(this, "data wrong,please try again later", Toast.LENGTH_LONG).show();
        this.ly_progress.setVisibility(View.GONE);
    }
    private void initRecycleview(List<CSWBMaterialGroupRes> list) {
        Log.d("xlb", " wbgroupres_to adpter____4");
        this.ly_progress.setVisibility(View.GONE);
        if (list.size() <= 0) {
            Toast.makeText(this, "data wrong,please try again later", Toast.LENGTH_LONG).show();
            this.ly_progress.setVisibility(View.GONE);
            return;
        }
        int i = 0;
        CSWBMaterialGroupRes cSWBMaterialGroupRes = list.get(0);
        CSOnlineStoreListAdapter cSOnlineStoreListAdapter = new CSOnlineStoreListAdapter(this, cSWBMaterialGroupRes);
        this.storeListAdapter = cSOnlineStoreListAdapter;
        this.recyclerView.setAdapter(cSOnlineStoreListAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.storeListAdapter.setItemClickListener(new CSOnlineStoreListAdapter.ItemClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.4
            @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreListAdapter.ItemClickListener
            public void onItemClick(View view, int i2, CSWBMaterialRes cSWBMaterialRes) {
                CSOnlineStoreActivity.this.initDContentVew(cSWBMaterialRes);
            }
        });
        if (this.isDContentViewShowed) {
            return;
        }
        List<CSWBMaterialRes> wBMaterialResList = cSWBMaterialGroupRes.getWBMaterialResList();
        int i2 = -1;
        while (true) {
            if (i >= wBMaterialResList.size()) {
                break;
            } else if (wBMaterialResList.get(i).getName().equals(this.DContentViewName)) {
                i2 = i;
                break;
            } else {
                i++;
            }
        }
        if (i2 < 0 || i2 >= wBMaterialResList.size()) {
            return;
        }
        this.isDContentViewShowed = true;
        initDContentVew(wBMaterialResList.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDContentVew(CSWBMaterialRes cSWBMaterialRes) {
        if (this.downLoadView == null) {
            CSOnlineStoreDownLoadView cSOnlineStoreDownLoadView = new CSOnlineStoreDownLoadView(this, cSWBMaterialRes, this.isVungleAd);
            this.downLoadView = cSOnlineStoreDownLoadView;
            cSOnlineStoreDownLoadView.setOnDownLoadViewItemClickListener(new CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity.5
                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
                public void onBackClick() {
                    CSOnlineStoreActivity.this.resetDownloadView();
                }

                @Override
                public void onDownLoadAdClikc(String str, DMWBRes dMWBRes) {
                    CSOnlineStoreActivity.this.lockeResClicked(str, dMWBRes);
                }

                @Override
                public void onDownLoadClick(String str, DMWBRes dMWBRes) {
                    CSOnlineStoreActivity.this.stickerdownloadClicked(str, dMWBRes);
                }

//                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
//                public void onDownLoadAdClikc(String str, DMWBRes dMWBRes) {
//                    CSOnlineStoreActivity.this.lockeResClicked(str, dMWBRes);
//                }

//                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
//                public void onDownLoadClick(String str, DMWBRes dMWBRes) {
//                    CSOnlineStoreActivity.this.stickerdownloadClicked(str, dMWBRes);
//                }

                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
                public void onApplyClick(String str, CSWBMaterialRes cSWBMaterialRes2) {
                    CSOnlineStoreActivity.this.stickerApplyClicked(str, cSWBMaterialRes2);
                }

                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
                public void onDownLoadProgress(int i) {
                    CSOnlineStoreActivity.this.stickerdownloadPrgress(i);
                }

                @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadView.onDownLoadViewItemClickListener
                public boolean isAdLoaded() {
                    return CSOnlineStoreActivity.this.isAdLoaded;
                }
            });
            this.ly_main.addView(this.downLoadView, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    public void stickerApplyClicked(String str, CSWBMaterialRes cSWBMaterialRes) {
        Intent intent = this.nextActivityIntent;
        if (intent == null) {
            Intent intent2 = new Intent();
            intent2.putExtra(STICKER_EXTRA, cSWBMaterialRes.getUniqueName());
            setResult(-1, intent2);
            finish();
            return;
        }
        intent.putExtra(STICKER_EXTRA, cSWBMaterialRes.getUniqueName());
        startActivity(this.nextActivityIntent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetDownloadView() {
        CSOnlineStoreListAdapter cSOnlineStoreListAdapter;
        if (this.downLoadView == null || (cSOnlineStoreListAdapter = this.storeListAdapter) == null) {
            return;
        }
        cSOnlineStoreListAdapter.notifyDataSetChanged();
        this.ly_main.removeView(this.downLoadView);
        this.downLoadView = null;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.downLoadView != null) {
                resetDownloadView();
                return false;
            }
            setResult(-1, new Intent());
            finish();
            return false;
        }
        return false;
    }

    public void locklistNotifyChanged() {
        CSOnlineStoreDownLoadView cSOnlineStoreDownLoadView = this.downLoadView;
        if (cSOnlineStoreDownLoadView != null) {
            cSOnlineStoreDownLoadView.initAd();
            this.downLoadView.onResDowanloaded();
        }
    }
}
