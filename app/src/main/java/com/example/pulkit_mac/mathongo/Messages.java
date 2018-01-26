package com.example.pulkit_mac.mathongo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pulkit-mac on 25/01/18.
 */
@Entity(tableName = "notifications")
public class Messages {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String message;
    private Boolean seen;
    private String img_url;

    public Messages(String title, String message, Boolean seen) {
        this.title = title;
        this.message = message;
        this.seen = seen;
    }
    @Ignore
    public Messages(String title, String message, Boolean seen, String img_url) {
        this.title = title;
        this.message = message;
        this.seen = seen;
        this.img_url = img_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
