package com.example.liuxi_000.todolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class ToDoListActivity extends Activity implements NewContentFragment.OnAddContentButtonClickListener {

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
