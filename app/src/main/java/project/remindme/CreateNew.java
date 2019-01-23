package project.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Random;

public class CreateNew extends AppCompatActivity {

    Global global = new Global();
    ReminderDB reminderDB = global.reminder_db;

    // number range [1, 999999]
    final int RAND_UPPER_LIM = 999998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        Button selectLocation = (Button) findViewById(R.id.buttonSelectLocation);

        selectLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv_name = findViewById(R.id.textbox_name);
                TextView tv_desc = findViewById(R.id.textbox_desc);

                String name = tv_name.getText().toString();
                String desc = tv_desc.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(CreateNew.this, "The field 'Reminder Name' is required.", Toast.LENGTH_SHORT).show();
                } else {
                    // create unique key for reminder
                    int ID;
                    do {
                        ID = new Random().nextInt(RAND_UPPER_LIM) + 1;
                    } while (reminderDB.findHandler(ID) != null);
                    Log.d("KEYGEN", "Generated a key of " + ID);

                    // save reminder and move on to SelectLocation activity
                    Reminder r = new Reminder(ID, name, desc);
                    Intent i = new Intent(CreateNew.this, SelectLocation.class);
                    i.putExtra("Reminder", r);

                    startActivity(i);
                }



            }
        });

    }

}
