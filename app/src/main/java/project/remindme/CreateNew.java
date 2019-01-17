package project.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class CreateNew extends AppCompatActivity {

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
                    // save reminder and move on to SelectLocation activity
                    Reminder r = new Reminder(name, desc);
                    Intent i = new Intent(CreateNew.this, SelectLocation.class);
                    i.putExtra("Reminder", r);

                    startActivity(i);
                }



            }
        });

    }
}
