package com.example.wechatnotificationinterceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView statusText;
    private Button enableButton;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;
    
    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WeChatNotificationListener.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                String title = intent.getStringExtra("title");
                String text = intent.getStringExtra("text");
                String bigText = intent.getStringExtra("bigText");
                long timestamp = intent.getLongExtra("timestamp", System.currentTimeMillis());
                
                addNotificationItem(title, text, bigText, timestamp);
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupRecyclerView();
        checkNotificationPermission();
        
        // 注册广播接收器
        IntentFilter filter = new IntentFilter(WeChatNotificationListener.ACTION_NOTIFICATION_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver, filter);
    }
    
    private void initViews() {
        statusText = findViewById(R.id.statusText);
        enableButton = findViewById(R.id.enableButton);
        notificationRecyclerView = findViewById(R.id.notificationRecyclerView);
        
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationSettings();
            }
        });
    }
    
    private void setupRecyclerView() {
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerView.setAdapter(adapter);
    }
    
    private void checkNotificationPermission() {
        if (isNotificationServiceEnabled()) {
            statusText.setText("✅ 通知监听服务已启用\n等待微信支付通知...");
            statusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            enableButton.setText("重新设置权限");
        } else {
            statusText.setText("❌ 需要启用通知访问权限\n点击下方按钮进行设置");
            statusText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            enableButton.setText("启用通知权限");
        }
    }
    
    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (flat != null && !flat.isEmpty()) {
            final String[] names = flat.split(":");
            for (String name : names) {
                if (name.contains(pkgName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void openNotificationSettings() {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intent);
        Toast.makeText(this, "请找到本应用并启用通知访问权限", Toast.LENGTH_LONG).show();
    }
    
    private void addNotificationItem(String title, String text, String bigText, long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeStr = sdf.format(new Date(timestamp));
        
        NotificationItem item = new NotificationItem(title, text, bigText, timeStr);
        notificationList.add(0, item); // 添加到列表顶部
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemInserted(0);
                notificationRecyclerView.scrollToPosition(0);
                
                // 更新状态文本
                statusText.setText("✅ 已拦截 " + notificationList.size() + " 条微信支付通知");
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        checkNotificationPermission();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }
}
