package project.remindme;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Reminder implements Serializable {

    private int ID;
    private String name;
    private String desc;

    private String destination;

    // stored separately as the LatLng class isn't serializable
    private double latitude;
    private double longitude;

    // used for DB retrieval
    public Reminder() {}

    public Reminder(int i, String n, String d) {
        this.ID = i;
        this.name = n;
        this.desc = d;
    }

    public void setID(int id) { this.ID = id; }

    public int getID() { return ID; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }

    public void setDestination(String d) { this.destination = d; }

    public String getDestination() { return this.destination; }

    public void setLatitude(double l) { this.latitude = l; }

    public double getLatitude() { return this.latitude; }

    public void setLongitude(double l) { this.longitude = l; }

    public double getLongitude() { return this.longitude; }
}
