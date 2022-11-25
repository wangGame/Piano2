package com.tony.flipracing;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.qs.compat_app_25_4_0.NotificationCompat;

public class MYReceiver extends BroadcastReceiver {
    String channelID = "my_channel_01";
    String channelName = "Smasher";

    @Override
    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String title = context.getResources().getString(R.string.app_name);
            int id = intent.getIntExtra("id", 0);
            String content=intent.getStringExtra("text");
            if (id == 0) {
                //活跃
                content=intent.getStringExtra("text");
            }else {
                //不活跃
                if (id == 4 || id == 5){
                    String[] split = content.split("!");
                    if (split.length ==2) {
                        title = split[0]+"!";
                        content = split[1]+"!";
                    }else {
                        content=intent.getStringExtra("text");
                    }
                }else {
                    content=intent.getStringExtra("text");
                }
            }
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel mChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(mChannel);
                Notification.Builder builder1 = new Notification.Builder(context, channelID);
                Intent notificationIntent = new Intent(context, AndroidLauncher.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Drawable drawable = context.getApplicationInfo().loadIcon(context.getPackageManager());
                Bitmap bitmap = getBitmapFromDrawable(drawable);
                builder1.setSmallIcon(R.mipmap.tuisong)
                        .setLargeIcon(bitmap)
                        .setContentIntent(contentIntent)
                        .setContentTitle(title)
                        .setContentText(content);
                notificationManager.notify(id, builder1.build());
            } else if (Build.VERSION.SDK_INT >= 11) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                Intent notificationIntent = new Intent(context, AndroidLauncher.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //版本号大于等于21（5.0）
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    //app_icon_mipmap 为推送小图标的名字
                    Drawable drawable = context.getApplicationInfo().loadIcon(context.getPackageManager());
                    Bitmap bitmap = getBitmapFromDrawable(drawable);
                    builder.setSmallIcon(R.mipmap.tuisong)

                            .setContentIntent(contentIntent)
                            .setContentTitle(title)
                            .setContentText(content);
                    notificationManager.notify(id, builder.build());
                } else {
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(contentIntent)
                            .setContentTitle(title)
                            .setContentText(content);
                    notificationManager.notify(id, builder.build());
                }
            }else{
                com.qs.compat_app_25_4_0.NotificationCompat.Builder builder =
                        new com.qs.compat_app_25_4_0.NotificationCompat.Builder(context);
                Intent notificationIntent = new Intent(context, AndroidLauncher.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context,
                        0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(contentIntent)
                        .setContentTitle(title)
                        .setContentText(content);
                notificationManager.notify(id, builder.build());
            }
        }catch (Exception e){
            e.printStackTrace();
        }catch (NoClassDefFoundError e){
            e.printStackTrace();
        }
    }
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }


}
