package com.android.simanika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simanika.MenuFragment.ArticleFragment;
import com.android.simanika.MenuFragment.NotificationFragment;
import com.android.simanika.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    NotificationData[] notificationData;
    Context context;
    public NotificationAdapter(NotificationData[] notificationData, NotificationFragment activity) {
        this.notificationData = notificationData;
        this.context = activity.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_notification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotificationData NotificationDataList = notificationData[position];
        holder.NotificationNama.setText(NotificationDataList.getNama());
        holder.NotificationInfo.setText(NotificationDataList.getInfo());
        holder.NotificationId = NotificationDataList.getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, NotificationDataList.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView NotificationNama, NotificationInfo;
        int NotificationId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NotificationNama = itemView.findViewById(R.id.nama_notif);
            NotificationInfo = itemView.findViewById(R.id.info_notif);
        }
    }
}