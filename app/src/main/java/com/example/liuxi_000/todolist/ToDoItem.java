package com.example.liuxi_000.todolist;

import java.util.Date;

/**
 * Created by liuxi_000 on 2014/7/14.
 */
public class ToDoItem {
    String task;
    Date createDate;

    ToDoItem(String _task) {
        task = _task;
        createDate = new Date(java.lang.System.currentTimeMillis());
    }
    ToDoItem(String _task, Date date) {
        task = _task;
        createDate = date;
    }
    public String getTask() {
        return task;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
