package com.picspool.lib.p017io;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.picspool.lib.io.DMACache */
/* loaded from: classes3.dex */
public class DMACache {
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    private static final int MAX_SIZE = 50000000;
    public static final int TIME_DAY = 86400;
    public static final int TIME_HOUR = 3600;
    private static Map<String, DMACache> mInstanceMap = new HashMap();
    private ACacheManager mCache;

    public static DMACache get(Context context) {
        return get(context, "DMACache");
    }

    public static DMACache get(Context context, String str) {
        return get(new File(context.getCacheDir(), str), 50000000L, Integer.MAX_VALUE);
    }

    public static DMACache get(File file) {
        return get(file, 50000000L, Integer.MAX_VALUE);
    }

    public static DMACache get(Context context, long j, int i) {
        return get(new File(context.getCacheDir(), "DMACache"), j, i);
    }

    public static DMACache get(File file, long j, int i) {
        Map<String, DMACache> map = mInstanceMap;
        DMACache dMACache = map.get(file.getAbsoluteFile() + myPid());
        if (dMACache == null) {
            DMACache dMACache2 = new DMACache(file, j, i);
            Map<String, DMACache> map2 = mInstanceMap;
            map2.put(file.getAbsolutePath() + myPid(), dMACache2);
            return dMACache2;
        }
        return dMACache;
    }

    private static String myPid() {
        return "_" + Process.myPid();
    }

    private DMACache(File file, long j, int i) {
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + file.getAbsolutePath());
        }
        this.mCache = new ACacheManager(file, j, i);
    }

    public void put(String str, String str2) {
        File newFile = this.mCache.newFile(str);
        BufferedWriter bufferedWriter;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(newFile), 1024);
                try {
                    bufferedWriter2.write(str2);
                } catch (IOException e) {
                    bufferedWriter = bufferedWriter2;
                    e.printStackTrace();
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            this.mCache.put(newFile);
                        }
                    }
                    this.mCache.put(newFile);
                } catch (Throwable th) {
                    th.printStackTrace();
                    bufferedWriter = bufferedWriter2;
                    try {
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    this.mCache.put(newFile);
                    throw th;
                }
                try {
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    this.mCache.put(newFile);
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.mCache.put(newFile);
    }

    public void put(String str, String str2, int i) {
        put(str, C3149Utils.newStringWithDateInfo(i, str2));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    public String getAsString(String str) throws IOException {
        BufferedReader bufferedReader;
        File file = this.mCache.get(str);
        var exists = file.exists();
        try {
            if (!exists) {
                return null;
            }
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                StringBuilder str2 = new StringBuilder();
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        str2.append(readLine);
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        return null;
                    }
                }
                if (C3149Utils.isDue(str2.toString())) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    remove(str);
                    return null;
                }
                String clearDateInfo = C3149Utils.clearDateInfo(str2.toString());
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return clearDateInfo;
            } catch (IOException e5) {
                e5.printStackTrace();
                bufferedReader = null;
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
           bufferedReader = null;
        }
        return bufferedReader.readLine();
    }

    public void put(String str, JSONObject jSONObject) {
        put(str, jSONObject.toString());
    }

    public void put(String str, JSONObject jSONObject, int i) {
        put(str, jSONObject.toString(), i);
    }

    public JSONObject getAsJSONObject(String str) {
        try {
            return new JSONObject(getAsString(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(String str, JSONArray jSONArray) {
        put(str, jSONArray.toString());
    }

    public void put(String str, JSONArray jSONArray, int i) {
        put(str, jSONArray.toString(), i);
    }

    public JSONArray getAsJSONArray(String str) {
        try {
            return new JSONArray(getAsString(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(String str, byte[] bArr) {
        File newFile = this.mCache.newFile(str);
        FileOutputStream fileOutputStream;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(newFile);
                try {
                    fileOutputStream2.write(bArr);
                } catch (Exception e) {
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            this.mCache.put(newFile);
                        }
                    }
                    this.mCache.put(newFile);
                } catch (Throwable th) {
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    this.mCache.put(newFile);
                    throw th;
                }
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (IOException e4) {

                    e4.printStackTrace();
                    this.mCache.put(newFile);
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.mCache.put(newFile);
    }

    public void put(String str, byte[] bArr, int i) {
        put(str, C3149Utils.newByteArrayWithDateInfo(i, bArr));
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0053: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:35:0x0053 */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getAsBinary(String r6) {
        /*
            r5 = this;
            r0 = 0
            com.picspool.lib.io.DMACache$ACacheManager r1 = r5.mCache     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.io.File r1 = com.picspool.lib.p017io.DMACache.ACacheManager.access$400(r1, r6)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            if (r2 != 0) goto Le
            return r0
        Le:
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            java.lang.String r3 = "r"
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            long r3 = r2.length()     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            int r1 = (int) r3     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            byte[] r1 = new byte[r1]     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            r2.read(r1)     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            boolean r3 = com.picspool.lib.p017io.DMACache.C3149Utils.access$800(r1)     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            if (r3 != 0) goto L32
            byte[] r6 = com.picspool.lib.p017io.DMACache.C3149Utils.access$900(r1)     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L52
            r2.close()     // Catch: java.io.IOException -> L2d
            goto L31
        L2d:
            r0 = move-exception
            r0.printStackTrace()
        L31:
            return r6
        L32:
            r2.close()     // Catch: java.io.IOException -> L36
            goto L3a
        L36:
            r1 = move-exception
            r1.printStackTrace()
        L3a:
            r5.remove(r6)
            return r0
        L3e:
            r6 = move-exception
            goto L44
        L40:
            r6 = move-exception
            goto L54
        L42:
            r6 = move-exception
            r2 = r0
        L44:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L52
            if (r2 == 0) goto L51
            r2.close()     // Catch: java.io.IOException -> L4d
            goto L51
        L4d:
            r6 = move-exception
            r6.printStackTrace()
        L51:
            return r0
        L52:
            r6 = move-exception
            r0 = r2
        L54:
            if (r0 == 0) goto L5e
            r0.close()     // Catch: java.io.IOException -> L5a
            goto L5e
        L5a:
            r0 = move-exception
            r0.printStackTrace()
        L5e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.p017io.DMACache.getAsBinary(java.lang.String):byte[]");
    }

    public void put(String str, Serializable serializable) {
        put(str, serializable, -1);
    }

    public void put(String str, Serializable serializable, int i) {
        ObjectOutputStream objectOutputStream = null;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream2.writeObject(serializable);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (i != -1) {
                        put(str, byteArray, i);
                    } else {
                        put(str, byteArray);
                    }
                    objectOutputStream2.close();
                } catch (Exception e) {
                    objectOutputStream = objectOutputStream2;
                    e.printStackTrace();
                    objectOutputStream.close();
                } catch (Throwable th) {
                    objectOutputStream = objectOutputStream2;
                    try {
                        objectOutputStream.close();
                    } catch (IOException ignored) {
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r5v5 */
    public Object getAsObject(String str) {
        ByteArrayInputStream byteArrayInputStream;
        ObjectInputStream objectInputStream = null;
        byte[] asBinary = getAsBinary(str);
        try {
            if (asBinary.length != 0) {
                try {
                    byteArrayInputStream = new ByteArrayInputStream(asBinary);
                    try {
                        objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                        objectInputStream = null;
                    } catch (Throwable th) {
                       th.printStackTrace();
                        asBinary[0] = 0;
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        /// asBinary.close();
                        throw th;
                    }
                } catch (Exception e4) {

                    objectInputStream = null;
                    byteArrayInputStream = null;
                } catch (Throwable th2) {
                    byteArrayInputStream = null;

//                    asBinary = 0;
                }
                try {
                    Object readObject = objectInputStream.readObject();
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    try {
                        objectInputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    return readObject;
                } catch (Exception e7) {
                    e7.printStackTrace();
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                    return null;
                }
            }
            return null;
        } catch (Throwable th3) {
           th3.printStackTrace();
        }
        return null;
    }

    public void put(String str, Bitmap bitmap) {
        put(str, C3149Utils.Bitmap2Bytes(bitmap));
    }

    public void put(String str, Bitmap bitmap, int i) {
        put(str, C3149Utils.Bitmap2Bytes(bitmap), i);
    }

    public Bitmap getAsBitmap(String str) {
        if (getAsBinary(str) == null) {
            return null;
        }
        return C3149Utils.Bytes2Bimap(getAsBinary(str));
    }

    public void put(String str, Drawable drawable) {
        put(str, C3149Utils.drawable2Bitmap(drawable));
    }

    public void put(String str, Drawable drawable, int i) {
        put(str, C3149Utils.drawable2Bitmap(drawable), i);
    }

    public Drawable getAsDrawable(String str) {
        if (getAsBinary(str) == null) {
            return null;
        }
        return C3149Utils.bitmap2Drawable(C3149Utils.Bytes2Bimap(getAsBinary(str)));
    }

    public File file(String str) {
        File newFile = this.mCache.newFile(str);
        if (newFile.exists()) {
            return newFile;
        }
        return null;
    }

    public boolean remove(String str) {
        return this.mCache.remove(str);
    }

    public void clear() {
        this.mCache.clear();
    }

    /* renamed from: com.picspool.lib.io.DMACache$ACacheManager */
    /* loaded from: classes3.dex */
    public class ACacheManager {
        private final AtomicInteger cacheCount;
        protected File cacheDir;
        private final AtomicLong cacheSize;
        private final int countLimit;
        private final Map<File, Long> lastUsageDates;
        private final long sizeLimit;

        private ACacheManager(File file, long j, int i) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = file;
            this.sizeLimit = j;
            this.countLimit = i;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() { // from class: com.picspool.lib.io.DMACache.ACacheManager.1
                @Override // java.lang.Runnable
                public void run() {
                    File[] listFiles = ACacheManager.this.cacheDir.listFiles();
                    if (listFiles != null) {
                        int i = 0;
                        int i2 = 0;
                        for (File file : listFiles) {
                            i = (int) (i + ACacheManager.this.calculateSize(file));
                            i2++;
                            ACacheManager.this.lastUsageDates.put(file, Long.valueOf(file.lastModified()));
                        }
                        ACacheManager.this.cacheSize.set(i);
                        ACacheManager.this.cacheCount.set(i2);
                    }
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void put(File file) {
            int i = this.cacheCount.get();
            while (i + 1 > this.countLimit) {
                this.cacheSize.addAndGet(-removeNext());
                i = this.cacheCount.addAndGet(-1);
            }
            this.cacheCount.addAndGet(1);
            long calculateSize = calculateSize(file);
            long j = this.cacheSize.get();
            while (j + calculateSize > this.sizeLimit) {
                j = this.cacheSize.addAndGet(-removeNext());
            }
            this.cacheSize.addAndGet(calculateSize);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(valueOf.longValue());
            this.lastUsageDates.put(file, valueOf);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File get(String str) {
            File newFile = newFile(str);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            newFile.setLastModified(valueOf.longValue());
            this.lastUsageDates.put(newFile, valueOf);
            return newFile;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File newFile(String str) {
            File file = this.cacheDir;
            return new File(file, str.hashCode() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean remove(String str) {
            return get(str).delete();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.lastUsageDates.clear();
            this.cacheSize.set(0L);
            File[] listFiles = this.cacheDir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    file.delete();
                }
            }
        }

        private long removeNext() {
            File file;
            if (this.lastUsageDates.isEmpty()) {
                return 0L;
            }
            Set<Map.Entry<File, Long>> entrySet = this.lastUsageDates.entrySet();
            synchronized (this.lastUsageDates) {
                file = null;
                Long l = null;
                for (Map.Entry<File, Long> entry : entrySet) {
                    if (file == null) {
                        file = entry.getKey();
                        l = entry.getValue();
                    } else {
                        Long value = entry.getValue();
                        if (value.longValue() < l.longValue()) {
                            file = entry.getKey();
                            l = value;
                        }
                    }
                }
            }
            long calculateSize = calculateSize(file);
            if (file.delete()) {
                this.lastUsageDates.remove(file);
            }
            return calculateSize;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long calculateSize(File file) {
            return file.length();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.picspool.lib.io.DMACache$Utils */
    /* loaded from: classes3.dex */
    public static class C3149Utils {
        private static final char mSeparator = ' ';

        private C3149Utils() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isDue(String str) {
            return isDue(str.getBytes());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isDue(byte[] bArr) {
            String[] dateInfoFromDate = getDateInfoFromDate(bArr);
            if (dateInfoFromDate != null && dateInfoFromDate.length == 2) {
                String str = dateInfoFromDate[0];
                while (str.startsWith("0")) {
                    str = str.substring(1, str.length());
                }
                if (System.currentTimeMillis() > Long.valueOf(str).longValue() + (Long.valueOf(dateInfoFromDate[1]).longValue() * 1000)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String newStringWithDateInfo(int i, String str) {
            return createDateInfo(i) + str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] newByteArrayWithDateInfo(int i, byte[] bArr) {
            byte[] bytes = createDateInfo(i).getBytes();
            byte[] bArr2 = new byte[bytes.length + bArr.length];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
            return bArr2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String clearDateInfo(String str) {
            return (str == null || !hasDateInfo(str.getBytes())) ? str : str.substring(str.indexOf(32) + 1, str.length());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] clearDateInfo(byte[] bArr) {
            return hasDateInfo(bArr) ? copyOfRange(bArr, indexOf(bArr, mSeparator) + 1, bArr.length) : bArr;
        }

        private static boolean hasDateInfo(byte[] bArr) {
            return bArr != null && bArr.length > 15 && bArr[13] == 45 && indexOf(bArr, mSeparator) > 14;
        }

        private static String[] getDateInfoFromDate(byte[] bArr) {
            if (hasDateInfo(bArr)) {
                return new String[]{new String(copyOfRange(bArr, 0, 13)), new String(copyOfRange(bArr, 14, indexOf(bArr, mSeparator)))};
            }
            return null;
        }

        private static int indexOf(byte[] bArr, char c) {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] == c) {
                    return i;
                }
            }
            return -1;
        }

        private static byte[] copyOfRange(byte[] bArr, int i, int i2) {
            int i3 = i2 - i;
            if (i3 < 0) {
                throw new IllegalArgumentException(i + " > " + i2);
            }
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
            return bArr2;
        }

        private static String createDateInfo(int i) {
            String str = System.currentTimeMillis() + "";
            while (str.length() < 13) {
                str = "0" + str;
            }
            return str + "-" + i + mSeparator;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] Bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Bitmap Bytes2Bimap(byte[] bArr) {
            if (bArr.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
            return createBitmap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Drawable bitmap2Drawable(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            return new BitmapDrawable(bitmap);
        }
    }
}
