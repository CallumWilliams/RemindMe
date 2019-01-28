package project.remindme;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DeviceBootReceiver extends BroadcastReceiver {

    public void onReceive(Context c, Intent arg) {
        Intent intent = new Intent(c, Service.class);
        c.startService(intent);
        Log.i("Autostart", "started");
    }

}
