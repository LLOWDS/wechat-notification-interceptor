package com.example.wechatnotificationinterceptor;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class WeChatNotificationListener extends NotificationListenerService {
    private static final String TAG = "WeChatNotificationListener";
    public static final String ACTION_NOTIFICATION_RECEIVED = "com.example.wechatnotificationinterceptor.NOTIFICATION_RECEIVED";
    
    // 微信包名
    private static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        
        String packageName = sbn.getPackageName();
        Log.d(TAG, "收到通知，包名: " + packageName);
        
        // 只处理微信的通知
        if (WECHAT_PACKAGE_NAME.equals(packageName)) {
            handleWeChatNotification(sbn);
        }
    }
    
    private void handleWeChatNotification(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        
        if (extras != null) {
            String title = extras.getString(Notification.EXTRA_TITLE);
            String text = extras.getString(Notification.EXTRA_TEXT);
            String bigText = extras.getString(Notification.EXTRA_BIG_TEXT);
            
            Log.d(TAG, "微信通知 - 标题: " + title);
            Log.d(TAG, "微信通知 - 内容: " + text);
            Log.d(TAG, "微信通知 - 大文本: " + bigText);
            
            // 检查是否是支付相关通知
            if (isPaymentNotification(title, text, bigText)) {
                Log.i(TAG, "检测到微信支付通知!");
                
                // 发送广播通知主界面
                Intent intent = new Intent(ACTION_NOTIFICATION_RECEIVED);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("bigText", bigText);
                intent.putExtra("timestamp", System.currentTimeMillis());
                intent.putExtra("packageName", sbn.getPackageName());
                
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        }
    }
    
    private boolean isPaymentNotification(String title, String text, String bigText) {
        if (title == null && text == null && bigText == null) {
            return false;
        }
        
        // 支付相关关键词
        String[] paymentKeywords = {
            "支付成功", "收款", "转账", "红包", "微信支付", "付款", "收钱", 
            "Payment", "收到转账", "收到红包", "已收钱", "收款到账"
        };
        
        String fullText = (title != null ? title : "") + " " + 
                         (text != null ? text : "") + " " + 
                         (bigText != null ? bigText : "");
        
        for (String keyword : paymentKeywords) {
            if (fullText.contains(keyword)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "通知被移除: " + sbn.getPackageName());
    }
}
