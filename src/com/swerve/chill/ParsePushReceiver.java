package com.swerve.chill;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.swerve.chill.main.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

public class ParsePushReceiver extends ParsePushBroadcastReceiver {
    @Override
    public void onPushOpen(final Context context, final Intent intent) {
        Log.e("Push", "Clicked");
        final Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
