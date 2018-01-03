package com.example.a17009495.star1er;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.a17009495.star1er.MainActivity;

/**
 * Created by GAEL on 05/12/2017.
 */

public class StarService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags,
    int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    private final class ServiceHandler extends Handler {
            public ServiceHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message msg) {
                    //Attente de 1 minute avant de lancer la notification de telechargement
                    //Thread.sleep(60000*6);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class).putExtra("telechargement", 0);
                    PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(android.R.drawable.ic_menu_more).setContentTitle("Mise a jour")
                            .setContentText("Mise a jour disponible").setAutoCancel(true).setContentIntent(resultPendingIntent);
                    notificationManager.notify(123456, mBuilder.build());

                    stopSelf(msg.arg1);
            }
    }

}
