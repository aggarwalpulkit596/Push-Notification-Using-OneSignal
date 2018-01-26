package com.example.pulkit_mac.mathongo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by pulkit-mac on 25/01/18.
 */

@Database(entities = {Messages.class},version = 2)
@TypeConverters({Converters.class})
public abstract class NotificationDatabase extends RoomDatabase {

    private static NotificationDatabase INSTANCE;

    private static Object LOCK = new Object();

    public static NotificationDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (LOCK){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,NotificationDatabase.class,NotificationDatabase.DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final String DB_NAME = "notifications_db";

    abstract notificationDao notificationDao();




}
