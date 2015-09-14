package com.example.zoonim.smsimporter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerButton(this);


        /*
        ContentValues values = new ContentValues();
        values.put("address", "+380937113103");
        values.put("date", "1411717839000");
        values.put("body", "Это новое сообщение");
        values.put("type", 1);
        values.put("read", 1);
        getContentResolver().insert(Uri.parse("content://sms"), values);*/
    }

    public void addListenerButton(final Context context) {
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
                SQLiteDatabase db = adb.openDatabase();
                Cursor c = db.rawQuery("SELECT address, date, body FROM sms ", null);
                if(c.moveToFirst()) {
                    do {
                        ContentValues values = new ContentValues();
                        values.put("address", c.getString(0));
                        values.put("date", c.getString(1));
                        values.put("body", c.getString(2));
                        values.put("type", 1);
                        values.put("read", 1);
                        getContentResolver().insert(Uri.parse("content://sms"), values);
                        // Log.d("Snippet: ", "ОТ: " + c.getString(0) + " СМС: " + c.getString(2));
                    } while (c.moveToNext());
                }
            }
        });
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
