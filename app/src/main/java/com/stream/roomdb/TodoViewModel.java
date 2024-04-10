package com.stream.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    TodoRoomDB todoRoomDB;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRoomDB = TodoRoomDB.getInstance(application.getApplicationContext());
    }


    public LiveData<List<Todo>> getAllTodo (){
        return todoRoomDB.todoDao().findTodosUsingLiveData();
    }

}
