package com.picspool.lib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes3.dex */
public class DMSQLiteHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "ResRecord";

    public DMSQLiteHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ResRecord(_id varchar primary key,packagename varchar,managername varchar,clickitemname varchar)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ResRecord");
        onCreate(sQLiteDatabase);
    }

    public void updateColumn(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        try {
            sQLiteDatabase.execSQL("ALTER TABLE ResRecord CHANGE " + str + " " + str2 + " " + str3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
