package project.remindme;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocationCheckService extends Service {

    private FusedLocationProviderClient fusedLocationClient;

    private final int ALERT_THRESHOLD = 50; // metres

    ReminderDB reminderDB = new ReminderDB(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Location deviceLocation = this.getDeviceLocation();
        LatLng deviceCoords = new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude());
        if (deviceLocation != null) {
            // fetch all active reminders
            ArrayList<Reminder> reminders = reminderDB.loadAllReminders();
            for (int i = 0; i < reminders.size(); i++) {
                // calculate distance between the two points (user's location and reminder location)
                Reminder currentReminder = reminders.get(i);
                LatLng reminderCoords = new LatLng(currentReminder.getLatitude(), currentReminder.getLongitude());
                float distance = calculateDistance(deviceCoords, reminderCoords);
                if (Math.abs(distance) <= ALERT_THRESHOLD) {
                    // add to reminders list
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Location getDeviceLocation() {
        Location location = null;

        try {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final Task task = fusedLocationClient.getLastLocation();
                if (task.isSuccessful()) {
                    location = (Location) task.getResult();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    private float calculateDistance(LatLng src, LatLng dest) {

        final int RADIUS = 6378100; // approx. radius of earth in metres
        double src_lat = src.latitude;
        double src_lng = src.longitude;
        double dest_lat = dest.latitude;
        double dest_lng = dest.longitude;
        double latDiff = Math.toRadians(dest_lat - src_lat);
        double lngDiff = Math.toRadians(dest_lng - src_lng);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(src_lat)) * Math.cos(Math.toRadians(dest_lat)) * Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = c * RADIUS;

        return new Float(distance).floatValue();

    }

}
