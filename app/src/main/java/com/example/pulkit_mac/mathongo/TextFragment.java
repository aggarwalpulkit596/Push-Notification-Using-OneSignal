package com.example.pulkit_mac.mathongo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {

    RecyclerView mNotificationList;
    NotificationAdapter mNotificationAdapter;
    List<Messages> mList = new ArrayList<>();


    public TextFragment() {
        // Required empty public constructor
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_text, container, false);
        mNotificationList = view.findViewById(R.id.textlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mNotificationList.setLayoutManager(linearLayoutManager);
        mNotificationList.setHasFixedSize(true);


        NotificationDatabase nd = NotificationDatabase.getInstance(getContext());
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
                mNotificationAdapter.notifyDataSetChanged();

            }
        }.execute();
                mNotificationAdapter = new NotificationAdapter(getActivity(), mList, new NotificationAdapter.NotificationListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        dao.updateseen(mList.get(position).getId());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Intent i = new Intent(getActivity(), NotificationData.class);
                        i.putExtra("title", mList.get(position).getTitle());
                        i.putExtra("message", mList.get(position).getMessage());
                        if (mList.get(position).getImg_url() != null)
                            i.putExtra("image_url", mList.get(position).getImg_url());
                        startActivity(i);
                    }
                }.execute();

            }
        });
        mNotificationList.setAdapter(mNotificationAdapter);
        return view;
    }

}
