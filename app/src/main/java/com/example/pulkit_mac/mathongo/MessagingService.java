package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by pulkit-mac on 25/01/18.
 */

public class MessagingService extends FirebaseMessagingService {

    Messages messages;
    NotificationDatabase nd;
    notificationDao dao;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        Log.i(TAG, "onMessageReceived: " + data);

        String notification_title = data.get("title");
        String notification_message = data.get("message");
        String click_action = data.get("click_action");
        String imgurl = data.get("image_url");

        nd = NotificationDatabase.getInstance(this);
        dao = nd.notificationDao();
        if (imgurl == null)
            messages = new Messages(notification_title, notification_message, false);
        else
            messages = new Messages(notification_title, notification_message, false, imgurl);


        Intent resultIntent = new Intent(this, NotificationData.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.putExtra("title", notification_title);
        resultIntent.putExtra("message", notification_message);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT
                );
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(notification_title)
                        .setContentText(notification_message)
                        .setSound(defaultSoundUri)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        if (imgurl != null) {
            ImageRequest imageRequest = new ImageRequest(imgurl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                    int mNotificationId = (int) System.currentTimeMillis();
                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                }
            }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            MySingleton.mySingleton(this).addtoRequestQue(imageRequest);
        } else {
            int mNotificationId = (int) System.currentTimeMillis();
            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
        addtodatabase();

    }

    @SuppressLint("StaticFieldLeak")
    private void addtodatabase() {


        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                dao.addtolist(messages);
                Log.i("TAG", "onPostExecute: " + dao.getAllNotification());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent local = new Intent();

                local.setAction("com.hello.action");

                MessagingService.this.sendBroadcast(local);

            }
        }.execute();
    }
}
