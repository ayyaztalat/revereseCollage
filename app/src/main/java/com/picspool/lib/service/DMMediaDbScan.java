package com.picspool.lib.service;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.picspool.lib.database.DMResRecordBean;

/* loaded from: classes3.dex */
public class DMMediaDbScan {
    static String TAG = "DMMediaDbScan";

    /* JADX WARN: Removed duplicated region for block: B:10:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.picspool.lib.service.DMMediaBucket scan(android.content.Context r13) {
        /*
            r12 = this;
            com.picspool.lib.service.DMMediaBucket r0 = new com.picspool.lib.service.DMMediaBucket
            r0.<init>(r13)
            r1 = 0
            android.content.ContentResolver r2 = r13.getContentResolver()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            java.lang.String r3 = "_id"
            java.lang.String r4 = "_data"
            java.lang.String r5 = "datetaken"
            java.lang.String r6 = "bucket_id"
            java.lang.String r7 = "bucket_display_name"
            java.lang.String r8 = "orientation"
            java.lang.String r9 = "_size"
            java.lang.String r10 = "date_added"
            java.lang.String r11 = "date_modified"
            java.lang.String[] r4 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10, r11}     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            android.net.Uri r3 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            r5 = 0
            r6 = 0
            java.lang.String r7 = "date_added DESC"
            android.database.Cursor r13 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            boolean r2 = r13.moveToFirst()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            if (r2 == 0) goto L98
            java.lang.String r2 = "_id"
            int r2 = r13.getColumnIndexOrThrow(r2)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r3 = "bucket_id"
            int r3 = r13.getColumnIndex(r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r4 = "bucket_display_name"
            int r4 = r13.getColumnIndex(r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r5 = "_data"
            int r5 = r13.getColumnIndexOrThrow(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r6 = "date_added"
            int r6 = r13.getColumnIndexOrThrow(r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r7 = "orientation"
            int r7 = r13.getColumnIndexOrThrow(r7)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r8 = "_size"
            int r8 = r13.getColumnIndexOrThrow(r8)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
        L5a:
            com.picspool.lib.service.DMImageMediaItem r9 = r12.getMediaItem()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r10 = r13.getString(r2)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setImgId(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r10 = r13.getString(r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setBuketId(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r10 = r13.getString(r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setBuketDisplayName(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            java.lang.String r10 = r13.getString(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setData(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            long r10 = r13.getLong(r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setDateAdded(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            int r10 = r13.getInt(r7)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setOrientation(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            int r10 = r13.getInt(r8)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r9.setImgSize(r10)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            r0.addMediaItem(r9)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            boolean r9 = r13.moveToNext()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lc7
            if (r9 != 0) goto L5a
        L98:
            if (r13 == 0) goto L9d
            r13.close()
        L9d:
            return r0
        L9e:
            r0 = move-exception
            goto La4
        La0:
            r0 = move-exception
            goto Lc9
        La2:
            r0 = move-exception
            r13 = r1
        La4:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Lc7
            java.lang.String r2 = com.picspool.lib.service.DMMediaDbScan.TAG     // Catch: java.lang.Throwable -> Lc7
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc7
            r3.<init>()     // Catch: java.lang.Throwable -> Lc7
            java.lang.String r4 = "Read images db failed"
            r3.append(r4)     // Catch: java.lang.Throwable -> Lc7
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> Lc7
            r3.append(r0)     // Catch: java.lang.Throwable -> Lc7
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> Lc7
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> Lc7
            if (r13 == 0) goto Lc6
            r13.close()
        Lc6:
            return r1
        Lc7:
            r0 = move-exception
            r1 = r13
        Lc9:
            if (r1 == 0) goto Lce
            r1.close()
        Lce:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.service.DMMediaDbScan.scan(android.content.Context):com.picspool.lib.service.DMMediaBucket");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e8  */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.picspool.lib.service.DMMediaBucket scanPath(android.content.Context r14, String r15) {
        /*
            r13 = this;
            java.lang.String r0 = "%"
            r1 = 0
            if (r15 == 0) goto Lec
            java.lang.String r2 = ""
            boolean r2 = r15.equals(r2)
            if (r2 == 0) goto Lf
            goto Lec
        Lf:
            com.picspool.lib.service.DMMediaBucket r2 = new com.picspool.lib.service.DMMediaBucket
            r2.<init>(r14)
            android.content.ContentResolver r3 = r14.getContentResolver()     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            java.lang.String r4 = "_id"
            java.lang.String r5 = "_data"
            java.lang.String r6 = "datetaken"
            java.lang.String r7 = "bucket_id"
            java.lang.String r8 = "bucket_display_name"
            java.lang.String r9 = "orientation"
            java.lang.String r10 = "_size"
            java.lang.String r11 = "date_added"
            java.lang.String r12 = "date_modified"
            java.lang.String[] r5 = new java.lang.String[]{r4, r5, r6, r7, r8, r9, r10, r11, r12}     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            java.lang.String r6 = "_data like ? "
            r4 = 1
            java.lang.String[] r7 = new java.lang.String[r4]     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r4 = 0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r8.<init>()     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r8.append(r0)     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r8.append(r15)     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r8.append(r0)     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            java.lang.String r15 = r8.toString()     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            r7[r4] = r15     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            android.net.Uri r4 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            java.lang.String r8 = "date_added DESC"
            android.database.Cursor r15 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lbf
            boolean r0 = r15.moveToFirst()     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            if (r0 == 0) goto Lb5
            java.lang.String r0 = "_id"
            int r0 = r15.getColumnIndexOrThrow(r0)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r3 = "bucket_id"
            int r3 = r15.getColumnIndex(r3)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r4 = "bucket_display_name"
            int r4 = r15.getColumnIndex(r4)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r5 = "_data"
            int r5 = r15.getColumnIndexOrThrow(r5)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r6 = "date_added"
            int r6 = r15.getColumnIndexOrThrow(r6)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r7 = "orientation"
            int r7 = r15.getColumnIndexOrThrow(r7)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.getContentResolver()     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
        L7d:
            com.picspool.lib.service.DMImageMediaItem r14 = new com.picspool.lib.service.DMImageMediaItem     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.<init>()     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r8 = r15.getString(r0)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setImgId(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r8 = r15.getString(r3)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setBuketId(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r8 = r15.getString(r4)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setBuketDisplayName(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            java.lang.String r8 = r15.getString(r5)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setData(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            long r8 = r15.getLong(r6)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setDateAdded(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            int r8 = r15.getInt(r7)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r14.setOrientation(r8)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            r2.addMediaItem(r14)     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            boolean r14 = r15.moveToNext()     // Catch: java.lang.Exception -> Lbb java.lang.Throwable -> Le4
            if (r14 != 0) goto L7d
        Lb5:
            if (r15 == 0) goto Lba
            r15.close()
        Lba:
            return r2
        Lbb:
            r14 = move-exception
            goto Lc1
        Lbd:
            r14 = move-exception
            goto Le6
        Lbf:
            r14 = move-exception
            r15 = r1
        Lc1:
            r14.printStackTrace()     // Catch: java.lang.Throwable -> Le4
            java.lang.String r0 = com.picspool.lib.service.DMMediaDbScan.TAG     // Catch: java.lang.Throwable -> Le4
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Le4
            r2.<init>()     // Catch: java.lang.Throwable -> Le4
            java.lang.String r3 = "Read images db failed"
            r2.append(r3)     // Catch: java.lang.Throwable -> Le4
            java.lang.String r14 = r14.getMessage()     // Catch: java.lang.Throwable -> Le4
            r2.append(r14)     // Catch: java.lang.Throwable -> Le4
            java.lang.String r14 = r2.toString()     // Catch: java.lang.Throwable -> Le4
            android.util.Log.e(r0, r14)     // Catch: java.lang.Throwable -> Le4
            if (r15 == 0) goto Le3
            r15.close()
        Le3:
            return r1
        Le4:
            r14 = move-exception
            r1 = r15
        Le6:
            if (r1 == 0) goto Leb
            r1.close()
        Leb:
            throw r14
        Lec:
            java.lang.String r14 = com.picspool.lib.service.DMMediaDbScan.TAG
            java.lang.String r15 = "Read images db failed , path is Null"
            android.util.Log.e(r14, r15)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.service.DMMediaDbScan.scanPath(android.content.Context, java.lang.String):com.picspool.lib.service.DMMediaBucket");
    }

    private String scaleThumbPath(ContentResolver contentResolver, String str) {
        String str2;
        String[] strArr = {"_data", "kind", DMResRecordBean.f1945ID};
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, strArr, "image_id=" + str, null, null);
        if (query == null || query.getCount() <= 0) {
            str2 = null;
        } else {
            query.getCount();
            query.moveToFirst();
            str2 = query.getString(query.getColumnIndexOrThrow("_data"));
        }
        query.close();
        return str2;
    }

    public DMImageMediaItem getMediaItem() {
        return new DMImageMediaItem();
    }
}
