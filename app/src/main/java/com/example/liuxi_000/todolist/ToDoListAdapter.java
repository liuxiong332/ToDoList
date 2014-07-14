package com.example.liuxi_000.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuxi_000 on 2014/7/14.
 */
public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {
    private int resource;
    public ToDoListAdapter(Context context, int resources, List<ToDoItem> list) {
        super(context, resources, list);
        this.resource = resources;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout todoView;
        ToDoItem item = getItem(position);

        if(convertView==null) {
            todoView = new LinearLayout(getContext());
            LayoutInflater inflater =
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(resource, todoView, true);
        } else {
            todoView = (LinearLayout)convertView;
        }
        TextView dateView = (TextView)todoView.findViewById(R.id.list_view_date);
        TextView contentView = (TextView)todoView.findViewById(R.id.list_view_content);

        Date date = item.getCreateDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        dateView.setText(dateFormat.format(date));
        contentView.setText(item.getTask());
        return todoView;
    }
}
