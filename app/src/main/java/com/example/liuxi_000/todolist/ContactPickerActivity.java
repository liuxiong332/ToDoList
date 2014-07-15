package com.example.liuxi_000.todolist;

import android.app.ListActivity;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;

/**
 * Created by liuxi_000 on 2014/7/15.
 */
public class ContactPickerActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.layout_contact_list_item, cursor,
                new String[]{Contacts.DISPLAY_NAME_PRIMARY},
                new int[]{R.id.contact_name_text_view});
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l,View v, int pos,long rowId) {
        Uri contentUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, rowId);
        Intent resultIntent = new Intent();
        resultIntent.setData(contentUri);
        setResult(Activity.RESULT_OK, resultIntent );
        finish();
    }
}
