package project.remindme;

import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                String desc = tv_name.getText().toString();

                // move on to next view: SelectLocation Activity

            }
        });

    }
}
