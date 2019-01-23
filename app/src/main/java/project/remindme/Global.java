package project.remindme;

import android.app.Application;

public class Global extends Application {

    // this class is dedicated to any objects that need to be accessible throughout the whole app

    // database
    ReminderDB reminder_db = new ReminderDB(this, null, null, 1);

    public Global() {}

}
