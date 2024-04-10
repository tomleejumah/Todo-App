package com.stream.roomdb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<Todo> todoList = new ArrayList<>();
    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        todoViewModel= new ViewModelProvider(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {

                Log.d(TAG, "onChanged: "+todos.toString());
            }
        });
    }

    public void insertSingleTodo() {
        Todo todo = new Todo("dwtdsgvs", false);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(todo);

    }

    public void getAllTodo() {
        //use this to populate rc
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todos = TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().getAllTodos();
                Log.d(TAG, "run: " + todos.toString());
            }
        });
        thread.start();


    }

    public void deleteATodo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //fetch the id
                Todo todo = TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().findTodoById(1);

                TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().deleteTodo(todo);
            }
        }).start();
    }

    public void deleteSelectedTodos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Integer> selectedIds = new ArrayList<>();
                selectedIds.add(1);
                selectedIds.add(4);
                for (int i : selectedIds) {
                    Todo todo = TodoRoomDB.getInstance(getApplicationContext())
                            .todoDao().findTodoById(i);
                    if (todo != null) {

                        TodoRoomDB.getInstance(getApplicationContext())
                                .todoDao().deleteTodo(todo);
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "empty", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void updateATodo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo = TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().findTodoById(2);

                if (todo != null) {

                    todo.setComplete(true);
                    todo.setText("test trials");

                    TodoRoomDB.getInstance(getApplicationContext())
                            .todoDao().updateTodo(todo);
                }

            }
        }).start();

    }

    public void insertMultipleTodos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                todoList.add(new Todo("JHGKW", true));
                todoList.add(new Todo("sdjkh", false));
                todoList.add(new Todo("qwefwa", true));

                TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().insertMultipleTodos(todoList);
            }
        }).start();

    }

    public void findCompletedTodo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> completedTodos = TodoRoomDB.getInstance(getApplicationContext())
                        .todoDao().getAllCompletedTodos();

                todoList.addAll(completedTodos);
            }
        }).start();
    }

    //Observing live data without using View ModelClass
    //commented it out since im now implementing a view Model
//    public void liveDataTodos(){
//        LiveData<List<Todo>> todoLists = TodoRoomDB.getInstance(getApplicationContext())
//                .todoDao().findTodosUsingLiveData();
//
//        todoLists.observe(this, new Observer<List<Todo>>() {
//            @Override
//            public void onChanged(List<Todo> todos) {
//                Log.d(TAG, "onChanged: "+todoLists);
//            }
//        });
//        // should be put in onDestroy.
//        todoLists.removeObservers(this);
//    }

    public class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

        @Override
        protected Void doInBackground(Todo... todos) {
            TodoRoomDB.getInstance(getApplicationContext())
                    .todoDao()
                    .insertTodo(todos[0]);
            return null;
        }
    }
}