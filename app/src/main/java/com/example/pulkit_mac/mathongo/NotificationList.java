package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.content.Intent;
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

//    RecyclerView mNotificationList;
//    NotificationAdapter mNotificationAdapter;
//    private Toolbar mToolbar;
//    List<Messages> mList = new ArrayList<>();

    TextView textlabel, picturelabel;
    ViewPager mMainPager;
    private PagerViewAdapter mPagerViewdapter;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        textlabel = findViewById(R.id.textlabel);
        picturelabel = findViewById(R.id.picturelabel);
        mMainPager = findViewById(R.id.mainPager);

        viewpager();
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
}
