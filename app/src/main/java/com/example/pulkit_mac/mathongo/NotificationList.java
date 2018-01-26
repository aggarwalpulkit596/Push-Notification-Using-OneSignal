package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NotificationList extends AppCompatActivity {

    RecyclerView mNotificationList;
    NotificationAdapter mNotificationAdapter;
    private Toolbar mToolbar;
    List<Messages> mList = new ArrayList<>();


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        mToolbar = findViewById(R.id.toolbarlist);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNotificationList = findViewById(R.id.notificationlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mNotificationList.setLayoutManager(linearLayoutManager);
        mNotificationList.setHasFixedSize(true);


        NotificationDatabase nd = NotificationDatabase.getInstance(this);
        final notificationDao dao = nd.notificationDao();
        new AsyncTask<Void, Void, List<Messages>>() {


            @Override
            protected List<Messages> doInBackground(Void... voids) {
                return dao.getAllNotification();
            }

            @Override
            protected void onPostExecute(List<Messages> messages) {
                mList.clear();
                mList.addAll(messages);
                Log.i("TAG1234", "onPostExecute: "+messages);
                mNotificationAdapter.notifyDataSetChanged();

            }
        }.execute();

        mNotificationAdapter = new NotificationAdapter(this, mList, new NotificationAdapter.NotificationListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        dao.updateseen(mList.get(position).getId());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Intent i = new Intent(NotificationList.this,NotificationData.class);
                        i.putExtra("title",mList.get(position).getTitle());
                        i.putExtra("message",mList.get(position).getMessage());
                        if(mList.get(position).getImg_url()!= null )
                            i.putExtra("image_url",mList.get(position).getImg_url());
                        startActivity(i);
                    }
                }.execute();

            }
        });
        mNotificationList.setAdapter(mNotificationAdapter);


    }
}
