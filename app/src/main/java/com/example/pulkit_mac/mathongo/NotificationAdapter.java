package com.example.pulkit_mac.mathongo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pulkit-mac on 26/01/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context mContext;
    private List<Messages> mNotificationList;
    private NotificationListener mListener;

    public NotificationAdapter(Context mContext, List<Messages> mNotificationList, NotificationListener mListener) {
        this.mContext = mContext;
        this.mNotificationList = mNotificationList;
        this.mListener = mListener;
    }

    public interface NotificationListener {
        void onItemClick(View view, int position);

    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_notification, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

        holder.title.setText(mNotificationList.get(position).getMessage() );
        holder.message.setText(mNotificationList.get(position).getTitle() );
        if(mNotificationList.get(position).getSeen()){
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        else
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.grey));

        Log.i("TAG1234", "onBindViewHolder: " + holder.message.getText().toString());

    }

    @Override
    public int getItemCount() {
        Log.i("TAG1234", "getItemCount: " + mNotificationList.size());
        return mNotificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView message;
        CardView cardView;
        NotificationListener mListener;

        public NotificationViewHolder(View itemView, NotificationListener Listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mListener = Listener;
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            cardView = itemView.findViewById(R.id.notification_layout);

        }

        @Override
        public void onClick(View view) {

            int id = view.getId();
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (id == R.id.notification_layout) {
                    mListener.onItemClick(view, position);
                }
            }

        }
    }
}
