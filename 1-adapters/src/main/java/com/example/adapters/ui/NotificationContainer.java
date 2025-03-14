package com.example.adapters.ui;

import java.util.ArrayList;
import java.util.List;

public class NotificationContainer {

    private List<String> notifications = new ArrayList<>();

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void removeNotification(String notification) {
        notifications.remove(notification);
    }
}
