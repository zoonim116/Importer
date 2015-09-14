package com.example.zoonim.smsimporter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(this);
        SQLiteDatabase db = adb.openDatabase();
        Cursor c = db.rawQuery("SELECT address, date, body FROM sms", null);
        if(c.moveToFirst()) {
            do {
                Log.d("Snippet: ", "ОТ: " + c.getString(0) + " СМС: " + c.getString(2));
            } while (c.moveToNext());
        }

        /*
        ContentValues values = new ContentValues();
        values.put("address", "+380937113103");
        values.put("date", "1411717839000");
        values.put("body", "Это новое сообщение");
        values.put("type", 1);
        values.put("read", 1);
        getContentResolver().insert(Uri.parse("content://sms"), values);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
