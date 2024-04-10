package com.stream.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_uid")
    private int uid;
    @ColumnInfo(name = "todo_text")
    private String text;
    @ColumnInfo(name = "isComplete")
    private boolean isComplete;

    public Todo(String text,boolean isComplete){
        this.text = text;
        this.isComplete = isComplete;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nTodo{" + "uid=" +uid +
                ",text='"+text +'\''+
                ",isCompleted="+isComplete+'}';
    }
}
