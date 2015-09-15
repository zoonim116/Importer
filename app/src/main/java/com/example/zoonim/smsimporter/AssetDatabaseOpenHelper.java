package com.example.zoonim.smsimporter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zoonim on 9/14/15.
 */
public class AssetDatabaseOpenHelper {
    private static final String DB_NAME = "mmssms.db";

    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase(String DB_NAME, String DB_PATH) {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            try {
                /*SQLiteDatabase checkDB = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
                if(checkDB != null){
                    checkDB.close();
                }*/
                //String dir = Environment.getExternalStorageDirectory().getPath();
                dbFile = new File(DB_PATH);
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
                db.close();
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        } else {
            context.deleteDatabase(DB_NAME);
            try {
                /*SQLiteDatabase checkDB = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
                if(checkDB != null){
                    checkDB.close();
                }*/
                String dir = Environment.getExternalStorageDirectory().getPath();
                dbFile = new File(dir+"/"+DB_NAME);
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
                db.close();
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open("databases/"+DB_NAME);
        OutputStream os = new FileOutputStream(dbFile, false);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }
}
