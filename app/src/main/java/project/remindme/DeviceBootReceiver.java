package project.remindme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class DeviceBootReceiver extends BroadcastReceiver {

    private final long INTERVAL = 10000;
    // private final long INTERVAL = 60000; // 1 minute

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
            Intent serviceIntent = new Intent(context, LocationCheckService.class);

            // setup alarm manager to schedule the background check service
            Calendar calendar = Calendar.getInstance();
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, serviceIntent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), INTERVAL, pendingIntent);

        }
    }

}
