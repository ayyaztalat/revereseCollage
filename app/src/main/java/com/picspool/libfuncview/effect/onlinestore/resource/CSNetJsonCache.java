package com.picspool.libfuncview.effect.onlinestore.resource;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.picspool.libfuncview.effect.onlinestore.resource.CSDiskLruCache;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.picspool.lib.http.DMAsyncTextHttp;

/* loaded from: classes.dex */
public class CSNetJsonCache {
    private static final String SP_NET_API_MAX_AGE = "net_api_max_age";
    private static final int VALUE_COUNT = 1;
    private static final int VERSION = 201710;
    private CSDiskLruCache cache;
    CacheCallback cacheCallback;

    /* loaded from: classes.dex */
    public interface CacheCallback {
        void dataError();

        void jsonDown(String str);
    }

    public CSNetJsonCache(Context context) {
        try {
            this.cache = CSDiskLruCache.open(new File((context.getFilesDir().getAbsolutePath() + "/.picsjoin/") + context.getPackageName() + "/json/"), VERSION, 1, 52428800L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isExpires(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NET_API_MAX_AGE, 0);
        long j = sharedPreferences.getLong(str + "_now", -1L);
        long j2 = sharedPreferences.getLong(str + "_max_age", -1L);
        if (j2 == -1) {
            return true;
        }
        return j + j2 <= System.currentTimeMillis();
    }

    public void setNetApiMaxAge(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NET_API_MAX_AGE, 0).edit();
        edit.putLong(str + "_now", System.currentTimeMillis());
        edit.putLong(str + "_max_age", j);
        edit.commit();
    }

    public boolean isMaxSet(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NET_API_MAX_AGE, 0);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_max_age");
        return sharedPreferences.getLong(sb.toString(), -1L) != -1;
    }

    public void put(String str, String str2) {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                CSDiskLruCache.Editor edit = cSDiskLruCache.edit(key(str));
                edit.set(0, str2);
                edit.commit();
                this.cache.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String get(String str) {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                CSDiskLruCache.Snapshot snapshot = cSDiskLruCache.get(key(str));
                if (snapshot != null) {
                    CSDiskLruCache.Editor edit = snapshot.edit();
                    String string = edit.getString(0);
                    edit.commit();
                    this.cache.flush();
                    return string;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void remove(String str) {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                cSDiskLruCache.remove(key(str));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String str, String str2) {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                CSDiskLruCache.Snapshot snapshot = cSDiskLruCache.get(key(str));
                if (snapshot != null) {
                    CSDiskLruCache.Editor edit = snapshot.edit();
                    edit.set(0, str2);
                    edit.commit();
                    this.cache.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                cSDiskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        CSDiskLruCache cSDiskLruCache = this.cache;
        if (cSDiskLruCache != null) {
            try {
                cSDiskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String key(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return String.valueOf(str.hashCode());
        }
    }

    private String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public void getJsonFromNet(final String str, final int i) {
        DMAsyncTextHttp.asyncHttpRequest(str, new DMAsyncTextHttp.AsyncTextHttpTaskListener() { // from class: com.picspool.libfuncview.effect.onlinestore.resource.CSNetJsonCache.1
            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFailedStatus(Exception exc) {
                if (CSNetJsonCache.this.cacheCallback != null) {
                    CSNetJsonCache.this.cacheCallback.dataError();
                }
            }

            @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
            public void onRequestDidFinishLoad(String str2) {
                if (TextUtils.isEmpty(str2)) {
                    if (CSNetJsonCache.this.cacheCallback != null) {
                        CSNetJsonCache.this.cacheCallback.dataError();
                        return;
                    }
                    return;
                }
                int i2 = i;
                if (i2 == 0) {
                    CSNetJsonCache.this.put(str, str2);
                } else if (i2 == 1) {
                    CSNetJsonCache.this.update(str, str2);
                }
                if (CSNetJsonCache.this.cacheCallback != null) {
                    CSNetJsonCache.this.cacheCallback.jsonDown(str2);
                }
            }
        });
    }

    public void setCacheCallback(CacheCallback cacheCallback) {
        this.cacheCallback = cacheCallback;
    }
}
