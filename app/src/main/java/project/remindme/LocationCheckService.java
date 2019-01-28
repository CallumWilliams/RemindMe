package project.remindme;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class LocationCheckService extends Service {

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
    }

}
