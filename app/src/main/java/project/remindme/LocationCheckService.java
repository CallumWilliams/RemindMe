package project.remindme;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class LocationCheckService extends IntentService {

    public LocationCheckService() {
        super("LocationCheckService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();
        Log.i("BACKGROUNDSERVICESTARTED", dataString);
    }
}
