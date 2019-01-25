package project.remindme;

import android.app.Application;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ReminderDB reminderDB = new ReminderDB(this);

    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<Reminder> reminderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        ListView listView = findViewById(R.id.list_reminder);
        listView.setAdapter(adapter);

        // check if a previous intent has passed any reminders
        Reminder to_add = (Reminder) getIntent().getSerializableExtra("CompleteReminder");
        if (to_add != null) {
            reminderDB.addHandler(to_add);
        }

        // check database. If it has any elements, add them to the list
        reminderList = reminderDB.loadAllReminders();
        if (reminderList.size() != 0) {
            for (int i = 0; i < reminderList.size(); i++) {
                addItem(reminderList.get(i));
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the CreateNew Activity
                startActivity(new Intent(MainActivity.this, CreateNew.class));
            }
        });
    }

    protected void addItem(Reminder r) {
        listItems.add(r.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
