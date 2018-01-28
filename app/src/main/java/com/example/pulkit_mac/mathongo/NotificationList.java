package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationList extends AppCompatActivity {


    TextView textlabel, picturelabel;
    ViewPager mMainPager;
    private PagerViewAdapter mPagerViewdapter;
    BroadcastReceiver updateUIReciver;
    IntentFilter filter;
    NotificationDatabase nd;
    notificationDao dao;
    Integer imagecount;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        textlabel = findViewById(R.id.textlabel);
        picturelabel = findViewById(R.id.picturelabel);
        mMainPager = findViewById(R.id.mainPager);
        nd = NotificationDatabase.getInstance(this);
        dao = nd.notificationDao();
        filter = new IntentFilter();

        filter.addAction("com.hello.action");

        updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                notificationCount();
            }
        };
        notificationCount();
        viewpager();
    }

    @SuppressLint("StaticFieldLeak")
    private void notificationCount() {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                imagecount = dao.getimageCount();
                return dao.gettextCount();
            }

            @Override
            protected void onPostExecute(Integer count) {
                if (count == 0)
                    textlabel.setText("Text Notification");
                else
                    textlabel.setText("Text Notification (" + count + ")");
                if (imagecount == 0)
                    picturelabel.setText("Picture Notification");
                else
                    picturelabel.setText("Picture Notification (" + imagecount + ")");

            }
        }.execute();
    }

    private void viewpager() {
        mMainPager.setOffscreenPageLimit(1);

        mPagerViewdapter = new PagerViewAdapter(getSupportFragmentManager());

        mMainPager.setAdapter(mPagerViewdapter);

        textlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPager.setCurrentItem(0);
            }
        });

        picturelabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPager.setCurrentItem(1);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            private void changeTabs(int pos) {

                if (pos == 0) {
                    textlabel.setTextColor(getColor(R.color.texttabbright));
                    textlabel.setTextSize(22);

                    picturelabel.setTextColor(getColor(R.color.texttablight));
                    picturelabel.setTextSize(16);

                }

                if (pos == 1) {
                    textlabel.setTextColor(getColor(R.color.texttablight));
                    textlabel.setTextSize(16);

                    picturelabel.setTextColor(getColor(R.color.texttabbright));
                    picturelabel.setTextSize(22);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(updateUIReciver, filter);
        notificationCount();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateUIReciver);
    }

}
