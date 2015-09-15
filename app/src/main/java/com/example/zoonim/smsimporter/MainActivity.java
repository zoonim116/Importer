package com.example.zoonim.smsimporter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.List;

public class MainActivity extends Activity {

    private Button button;
    private Button fileDialog;
    private String DB_NAME = "";
    private String DB_PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerButton(this);
        fileDialogBtuttonClick(this);
    }

    public void fileDialogBtuttonClick(final Context context) {
        fileDialog = (Button) findViewById(R.id.fileDialog);
        fileDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        DB_NAME = file.getName();
                        DB_PATH = file.getPath();
                    }
                }).showDialog();
            }
        });
    }

    public void addListenerButton(final Context context) {
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetDatabaseOpenHelper adb = new AssetDatabaseOpenHelper(context);
                SQLiteDatabase db = adb.openDatabase(DB_NAME, DB_PATH);
                Cursor c = db.rawQuery("SELECT address, date, body FROM sms  ", null);
                if(c.moveToFirst()) {
                    do {
                        ContentValues values = new ContentValues();
                        values.put("address", c.getString(0));
                        values.put("date", c.getString(1));
                        values.put("body", c.getString(2));
                        values.put("type", 1);
                        values.put("read", 1);
                        getContentResolver().insert(Uri.parse("content://sms"), values);
                        button = (Button) findViewById(R.id.button1);
                        button.setText("In Progress. Do not turn off phone.");
                        // Log.d("Snippet: ", "ОТ: " + c.getString(0) + " СМС: " + c.getString(2));
                    } while (c.moveToNext());
                }
                button = (Button) findViewById(R.id.button1);
                button.setText("Done.");
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
