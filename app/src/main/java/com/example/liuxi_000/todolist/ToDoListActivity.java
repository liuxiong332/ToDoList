package com.example.liuxi_000.todolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class ToDoListActivity extends Activity implements NewContentFragment.OnAddContentButtonClickListener {
    private final int CONTACT_PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        FragmentManager manager = getFragmentManager();
        ListFragment listFragment = (ListFragment)manager.findFragmentById(R.id.list_view);

//       adapter = new ArrayAdapter<String>(this,
//                R.layout.view_to_do_list_item,
//                todoItems);
        adapter = new ToDoListAdapter(this, R.layout.view_to_do_list_item, todoItems);
        listFragment.setListAdapter(adapter);

        Button pickButton = (Button)findViewById(R.id.pick_contact_button);
        pickButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("content://contacts/");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, CONTACT_PICK);
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if(reqCode == CONTACT_PICK && resCode == Activity.RESULT_OK) {
            TextView textView = (TextView)findViewById(R.id.contact_name_text_view);
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME_PRIMARY));
            textView.setText(name);
        }
    }

    @Override
    public void onAddContent(String item) {
        todoItems.add(0, new ToDoItem(item));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<ToDoItem>   todoItems = new  ArrayList<ToDoItem>();
    private ArrayAdapter<ToDoItem>   adapter;
}
