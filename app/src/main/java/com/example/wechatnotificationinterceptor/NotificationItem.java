package com.example.wechatnotificationinterceptor;

public class NotificationItem {
    private String title;
    private String text;
    private String bigText;
    private String timestamp;
    
    public NotificationItem(String title, String text, String bigText, String timestamp) {
        this.title = title;
        this.text = text;
        this.bigText = bigText;
        this.timestamp = timestamp;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getText() {
        return text;
    }
    
    public String getBigText() {
        return bigText;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getDisplayText() {
        if (bigText != null && !bigText.isEmpty()) {
            return bigText;
        } else if (text != null && !text.isEmpty()) {
            return text;
        } else {
            return "无内容";
        }
    }
    
    public String getDisplayTitle() {
        return title != null && !title.isEmpty() ? title : "微信通知";
    }
}
