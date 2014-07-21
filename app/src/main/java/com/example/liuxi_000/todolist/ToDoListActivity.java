package com.example.liuxi_000.todolist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ToDoListActivity extends Activity implements
        NewContentFragment.OnAddContentButtonClickListener, LoaderManager.LoaderCallbacks<Cursor> {

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

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }
    @Override
    public void onAddContent(String item) {
//        todoItems.add(0, new ToDoItem(item));
//        adapter.notifyDataSetChanged();
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(ToDoContentProvider.KEY_TASK, item);

        cr.insert(ToDoContentProvider.CONTENT_URI, values);
        getLoaderManager().restartLoader(0, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                ToDoContentProvider.CONTENT_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        todoItems.clear();
        if(data!=null) {
            int taskIndex = data.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);
            while (data.moveToNext()) {
                ToDoItem item = new ToDoItem(data.getString(taskIndex));
                todoItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private ArrayList<ToDoItem>   todoItems = new  ArrayList<ToDoItem>();
    private ArrayAdapter<ToDoItem>   adapter;
}
