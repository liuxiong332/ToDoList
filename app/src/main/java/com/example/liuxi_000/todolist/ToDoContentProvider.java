package com.example.liuxi_000.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by liuxi_000 on 2014/7/18.
 */
public class ToDoContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI =
            Uri.parse("content://com.liuxiong.todoprovider/todoitems");
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_CREATION_DATE = "creation_date";

    private static final String DATABASE_NAME = "todoDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "todoItemTable";

    private MySQLiteOpenHelper myOpenHelper;

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.liuxiong.todoprovider", "todoitems", ALLROWS);
        uriMatcher.addURI("com.liuxiong.todoprovider", "todoitems/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        myOpenHelper = new MySQLiteOpenHelper(getContext(),
                DATABASE_NAME, null,  DATABASE_VERSION);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALLROWS:
                return "vnd.android.cursor.dir/todos";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/todos";
            default:
                throw new IllegalArgumentException("Unsupport URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sort) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DATABASE_TABLE);

        switch(uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID+"="+rowId);
            default:    break;
        }
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sort);
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialiValues) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        long id = db.insert(DATABASE_TABLE, null, initialiValues);
        if(id > -1) {
            Uri insertUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertUri, null);
            return insertUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        String selection = where;
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId;
                if(!where.isEmpty())
                    selection += " AND (" + where + ")";
            default:    break;
        }
        int deleteCount = db.delete(DATABASE_TABLE, selection, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String where, String[] whereArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        String selection = where;
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId ;
                if(!where.isEmpty()) {
                    selection += " AND (" + where + ")";
                }
            default:    break;
        }
        int updateCount = db.update(DATABASE_TABLE, values, selection, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context,String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement," +
                KEY_TASK + " text not null," +
                KEY_CREATION_DATE + " long);";
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
