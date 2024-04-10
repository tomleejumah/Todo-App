package com.stream.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Todo.class},version = 1)
public abstract class TodoRoomDB extends RoomDatabase {
    public abstract TodoDao todoDao();
    private static volatile TodoRoomDB INSTANCE;

    static TodoRoomDB getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TodoRoomDB.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDB.class,"Todo_Database").build();
                }
            }
        }
        return INSTANCE;
    }
}
