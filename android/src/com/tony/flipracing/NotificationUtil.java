package com.tony.flipracing;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NotificationUtil {
    private static boolean before19 = false;

    public static String[] active = {
//            "Try new levels and get awards!",
//            "Hey, here are some puzzles waiting for you.",
//            "Tap here to continue your Bricks Smasher!",
//            "Come and get your daily rewards!",
//            "It is time to play Bricks Smasher!"

            "Try new levels and get awards!",
            "Come back and try the latest levels!",
            "Play (Rider) and have a wonderful time!",
            "Tap here to make the new highest score!",
            "Tap here to start hammering out your drive skills!"
    };

    public static String[] inActive = {
            "It’s been a long time! Come back and play with fun!",
            "What's your highest score? It's time to renew it!",
            "Knock knock! Here is  （Rider） with lots of fun!"
    };

    /**
     * 6次推送
     * 1.第一次为活跃的
     * 2，3，4是不活跃的   再第一类中找
     * 5，6 为不活跃       第二类中找
     * @param random
     * @param index
     * @return
     */
    public static String appendTextRule(Random random, int index){
        if(index == 0){
            return active[random.nextInt(4)];
        }else{
            if (index<4){
                return active[random.nextInt(4)];
            }else {
                return inActive[random.nextInt(2)];
            }
        }
    }
    static long todayZero() {
        Calendar day = Calendar.getInstance();
        Date date = new Date();
        day.setTime(date);
        int hour = day.get(Calendar.HOUR_OF_DAY);
        if (hour >= 19) {
            before19 = false;
            day.set(Calendar.HOUR_OF_DAY, 24);
        } else {
            before19 = true;
            day.set(Calendar.HOUR_OF_DAY, 0);
        }
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime().getTime();
    }

    static int[] days = {
            0,1, 2, 3, 6, 13
    };

    public static void add(Context context) {
        try {
            Random random = new Random();
            long time_zero = todayZero();
            int dayOffset = 0;
            if (!before19) {
                dayOffset = 1;
            }
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            for (int i = 0; i < 6; i++) {  //6次推送
//                long time_ms = time_zero + ((days[i] + dayOffset) * 24 + 19) * 60L * 60L * 1000L + random.nextInt(60) * 60 * 1000;
//                long time_ms = time_zero + ((days[i] + dayOffset) * 24 + 19) * 60L * 60L * 1000L + random.nextInt(60) * 60 * 1000;

                long time_ms = time_zero + ((days[i] + dayOffset) * 24 + 19) * 60L * 60L * 1000L;
                Intent intent = new Intent(context, MYReceiver.class).setAction(AndroidLauncher.class.getName());
                intent.putExtra("id", i);
                intent.putExtra("text", appendTextRule(random, i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.set(AlarmManager.RTC_WAKEUP, time_ms, pendingIntent);
                Log.i("Notification", "addNotification" + i+"     "+time_ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (int i = 0; i < 6; i++) {
            alarmManager.cancel(makePendingIntent(context, i, PendingIntent.FLAG_CANCEL_CURRENT));
        }
    }

    public static PendingIntent makePendingIntent(Context context, int id, int flag) {
        return PendingIntent.getBroadcast(context, id,
                new Intent(context, MYReceiver.class).setAction(AndroidLauncher.class.getName()).putExtra("id", id),
                flag);
    }
}
