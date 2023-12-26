package com.bvr.FriendFlow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(context, "FriendFlow Reminder : Connect & Share!", "Stay connected with FriendFlow! 🌟📱 Share updates and spread joy today. #FriendFlow");
    }
}