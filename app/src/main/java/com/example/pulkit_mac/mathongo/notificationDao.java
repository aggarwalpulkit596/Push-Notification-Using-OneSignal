package com.example.pulkit_mac.mathongo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by pulkit-mac on 25/01/18.
 */
@Dao
public interface notificationDao {


    @Query("SELECT * FROM notifications WHERE img_url is not null")
    List<Messages> getAllNotification();

    @Query("SELECT * FROM notifications WHERE img_url is  null")
    List<Messages> getAlltextNotification();



    @Query("SELECT COUNT(id) FROM notifications WHERE seen = 0")
    int getCount();

    @Insert
    long addtolist(Messages messages);

    @Delete
    void deletefromlist(Messages messages);

    @Query("UPDATE notifications SET seen = 1 where id = :tid")
    void updateseen(long tid);

}
