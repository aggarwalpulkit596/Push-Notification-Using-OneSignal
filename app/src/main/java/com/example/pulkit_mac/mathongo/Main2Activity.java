package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    NotificationDatabase nd;
    notificationDao dao;
    Messages messages;
    String title, message, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nd = NotificationDatabase.getInstance(this);
        dao = nd.notificationDao();
        if (getIntent().getExtras() != null) {
            Log.i("TAG123456", "onCreate: "+getIntent().getExtras().containsKey("title"));
            if(
                    getIntent().getExtras().containsKey("title"))
            getdata();
        }
        IntentFilter filter = new IntentFilter();

        filter.addAction("com.hello.action");

        BroadcastReceiver updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                invalidateCart();
            }
        };
        registerReceiver(updateUIReciver, filter);

    }

    private void getdata() {

        for (String key : getIntent().getExtras().keySet()) {

            if (key.equals("title"))
                title = getIntent().getExtras().getString("title");
            else if (key.equals("message"))
                message = getIntent().getExtras().getString("message");
            else if (key.equals("image_url"))
                image = getIntent().getExtras().getString("image_url");
        }
        if (image == null)
            messages = new Messages(title, message, false);
        else
            messages = new Messages(title, message, false, image);

        addtodatabase();

    }

    @SuppressLint("StaticFieldLeak")
    private void addtodatabase() {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                dao.addtolist(messages);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                invalidateCart();
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_shop);
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                Log.i("TAG", "doInBackground: " + dao.getCount());
                return dao.getCount();
            }

            @Override
            protected void onPostExecute(Integer count) {
                menuItem.setIcon(buildCounterDrawable(count, R.drawable.notification));
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(this, NotificationList.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.notification_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    public void invalidateCart() {
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateCart();
    }
}
