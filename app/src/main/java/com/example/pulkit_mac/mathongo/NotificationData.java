package com.example.pulkit_mac.mathongo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NotificationData extends AppCompatActivity {

    TextView title, message;
    ImageView imageView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_data);


        title = findViewById(R.id.textViewtitle);
        message = findViewById(R.id.textViewmessage);
        imageView = findViewById(R.id.imageView);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            title.setText(b.get("title").toString());
            message.setText(b.get("message").toString());
            if (b.get("image_url") == null) {
                imageView.setVisibility(View.GONE);
            } else {
                Picasso.with(this).load(b.get("image_url").toString()).placeholder(R.drawable.load).into(imageView);

            }
        }
    }
}

