package com.learnwithvel.deviceadminreceiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;


/**
 * https://stackoverflow.com/questions/56810178/android-take-a-picture-without-preview-and-without-interaction
 * https://zatackcoder.com/android-camera-2-api-example-without-preview/
 */
public class MyAdmin extends DeviceAdminReceiver {

    private static final String TAG = "MyAdmin";

    CameraControllerV2WithoutPreview ccv2WithoutPreview;

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

        ccv2WithoutPreview = CameraControllerV2WithoutPreview.getInstance(context);
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
