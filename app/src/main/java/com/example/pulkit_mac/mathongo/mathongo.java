package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by pulkit-mac on 26/01/18.
 */

public class mathongo extends Application {

    String title, body, imgurl;
    Long position;
    Messages messages;
    NotificationDatabase nd;
    notificationDao dao;

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();
        Log.i("TAG", "onCreate: here it is");
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


    private class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {

            title = notification.payload.title;
            body = notification.payload.body;
            imgurl = notification.payload.bigPicture;
            nd = NotificationDatabase.getInstance(getApplicationContext());
            dao = nd.notificationDao();
            if (imgurl == null)
                messages = new Messages(title, body, false);
            else
                messages = new Messages(title, body, false, imgurl);

            addtodatabase();

        }
    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @SuppressLint("StaticFieldLeak")
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            final Intent resultIntent = new Intent(getApplicationContext(), NotificationData.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("message", body);
            if (imgurl != null)
                resultIntent.putExtra("image_url", imgurl);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    dao.updateseen(position);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    startActivity(resultIntent);
                }
            }.execute();


        }
    }

    @SuppressLint("StaticFieldLeak")
    private void addtodatabase() {


        new AsyncTask<Void, Void, Long>() {


            @Override
            protected Long doInBackground(Void... voids) {
                return dao.addtolist(messages);

            }

            @Override
            protected void onPostExecute(Long id) {

                position = id;

                Intent local = new Intent();

                local.setAction("com.hello.action");

                getApplicationContext().sendBroadcast(local);

            }
        }.execute();
    }
}
