package project.remindme;

import android.app.Application;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ReminderDB reminderDB = new ReminderDB(this);

    ListView listView;

    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<Reminder> reminderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup list
        listView = findViewById(R.id.list_reminder);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        // check if a previous intent has passed any reminders
        Reminder to_add = (Reminder) getIntent().getSerializableExtra("CompleteReminder");
        if (to_add != null) {
            reminderDB.addHandler(to_add);
        }

        // check database. If it has any elements, add them to the list
        if (reminderDB.countRecords() > 0) {
            reminderList = reminderDB.loadAllReminders();
            for (int i = 0; i < reminderList.size(); i++) {
                addItem(reminderList.get(i));
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the CreateNew Activity
                startActivity(new Intent(MainActivity.this, CreateNew.class));
            }
        });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clicked = listView.getItemAtPosition(position).toString();
                Reminder search = reminderDB.findHandler(clicked);
                Intent i = new Intent(MainActivity.this, ViewReminder.class);
                i.putExtra("SEARCHED", search);
                startActivity(i);
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
