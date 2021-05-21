package com.learnwithvel.deviceadminreceiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import static com.learnwithvel.deviceadminreceiver.App.CHANNEL_ID;

/**
 * REFERENCES:
 * https://www.youtube.com/watch?v=FbpD5RZtbCc&list=PLrnPJCHvNZuBhmqlWEQfvxbNtY6B_XJ3n&index=2
 */
public class ApplicationService extends Service {

    public static class MyAdmin extends DeviceAdminReceiver {

        private static final String TAG = "MyAdmin";

        private CameraControllerV2WithoutPreview ccv2WithoutPreview;


        @Override
        public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
            super.onEnabled(context, intent);
            Toast.makeText(context, "Device Admin: Enabled", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
            Toast.makeText(context, "Device Admin: Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
            Log.d(TAG, "onPasswordFailed: " + context.getString(R.string.password_failed));

            Toast.makeText(context, "onPasswordFailed: " + context.getString(R.string.password_failed), Toast.LENGTH_LONG)
                    .show();

            ccv2WithoutPreview = CameraControllerV2WithoutPreview.getInstance(context.getApplicationContext());
            ccv2WithoutPreview.openCamera();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
            ccv2WithoutPreview.takePicture();

        }


        @Override
        public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent) {
            Log.d(TAG, "onPasswordSucceeded: " + context.getString(R.string.password_success));

            Toast.makeText(context, "onPasswordSucceeded: " + context.getString(R.string.password_success), Toast.LENGTH_LONG)
                    .show();

        }



        @Override
        public void onReceive(@NonNull Context context, @NonNull Intent intent) {
            super.onReceive(context, intent);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //only required with bound services.
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra(getString(R.string.INPUT_DATA_EXTRA));


        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notifIntent, 0);

        //Create Notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Application Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //  Warning: Please do heavy work on the background thread. (IntentService,
        // JobIntentService, WorkerManager) are the alternative that create their
        // background thread.

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
