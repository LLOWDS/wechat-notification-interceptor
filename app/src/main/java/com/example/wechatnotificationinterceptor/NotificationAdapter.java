package com.example.wechatnotificationinterceptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationItem> notificationList;
    
    public NotificationAdapter(List<NotificationItem> notificationList) {
        this.notificationList = notificationList;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);
        
        holder.titleText.setText(item.getDisplayTitle());
        holder.contentText.setText(item.getDisplayText());
        holder.timeText.setText(item.getTimestamp());
    }
    
    @Override
    public int getItemCount() {
        return notificationList.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView contentText;
        TextView timeText;
        
        ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            contentText = itemView.findViewById(R.id.contentText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}
