package project.remindme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewReminder extends AppCompatActivity {

    AlertDialog.Builder delete_dialog;

    Reminder viewed_reminder;
    ReminderDB reminderDB = new ReminderDB(this);

    Button btn_back, btn_edit, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

        viewed_reminder = (Reminder) getIntent().getSerializableExtra("SEARCHED");

        delete_dialog = new AlertDialog.Builder(this);

        btn_back = findViewById(R.id.button_return);
        btn_edit = findViewById(R.id.button_edit);
        btn_delete = findViewById(R.id.button_delete);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to Main Activity
                startActivity(new Intent(ViewReminder.this, MainActivity.class));
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enable editing
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ask if user wants to delete the reminder. If yes, remove from DB and go back to Main Activity.
                delete_dialog.setMessage(R.string.delete_dialog_confirmation).setTitle(R.string.delete_dialog_title);
                delete_dialog.setPositiveButton(R.string.delete_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete and go back to last activity
                        reminderDB.deleteHandler(viewed_reminder.getID());
                        startActivity(new Intent(ViewReminder.this, MainActivity.class));
                    }
                });
                delete_dialog.setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel
                    }
                });
                delete_dialog.show();
            }
        });

    }
}
