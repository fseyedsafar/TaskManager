package com.example.task.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;

import com.example.task.controller.TaskPagerFragment;
import com.example.task.model.Task;
import com.example.task.model.database.TaskCursorWrapper;
import com.example.task.model.database.TaskOpenHelper;
import static com.example.task.model.database.TaskDataBaseSchema.TaskTable.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {

    public static TaskRepository instance;
    private List<Task> mTask = new ArrayList<>();
    private List<Task> mTaskToDo = new ArrayList<>();
    private List<Task> mTaskDoing = new ArrayList<>();
    private List<Task> mTaskDone = new ArrayList<>();
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskOpenHelper taskOpenHelper = new TaskOpenHelper(context);
        mDatabase = taskOpenHelper.getWritableDatabase();
    }

    public static TaskRepository getInstance(Context context) {

        if (instance == null){
            instance = new TaskRepository(context);
        }

        return instance;
    }

//    public List<Task> getmTask() {
//        return mTask;
//    }
//
//    public void setmTask() {
//        this.mTask = getTaskListFromDB();
//    }

    public List<Task> getmTaskToDo() {
        return mTaskToDo;
    }

    public List<Task> getmTaskDoing() {
        return mTaskDoing;
    }

    public List<Task> getmTaskDone() {
        return mTaskDone;
    }

    public void setmTaskToDo(List<Task> mTaskToDo) {
        this.mTaskToDo = mTaskToDo;
    }

    public void setmTaskDoing(List<Task> mTaskDoing) {
        this.mTaskDoing = mTaskDoing;
    }

    public void setmTaskDone(List<Task> mTaskDone) {
        this.mTaskDone = mTaskDone;
    }

    public void insert(Task task, String mStateRadioButton , int currentPage){

//        if (mStateRadioButton.equals("")){
//            mTask = getTaskList(currentPage);
//        }
//        else {
//            mTask = getTaskList(mStateRadioButton);
//        }
//        mTask.add(task);

        ContentValues values = getContentValues(task);
        mDatabase.insert(NAME, null, values);
    }

    public void update(Task task, int currentPage, UUID id){
//        mTask = getTaskListFromDB(getState(currentPage));
//        for (int i = 0 ; i < mTask.size() ; i++){
//            if (task.getmID().equals(mTask.get(i).getmID())){
//                mTask.set(i , task);
//            }
//        }
        mDatabase.update(NAME, getContentValues(task), Columns.UUID + "=?", new String[]{id.toString()});
    }

    public void delete(Task task, int currentPage, UUID id){
//        mTask = getTaskListFromDB(getState(currentPage));
//        for (int i = 0 ; i < mTask.size() ; i++){
//            if (task.getmID().equals(mTask.get(i).getmID())){
//                mTask.remove(i);
//            }
//        }
        mDatabase.delete(NAME,Columns.UUID + "=?", new String[]{id.toString()});
    }

    public ContentValues getContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID, task.getmID().toString());
        values.put(Columns.USER_UUID, task.getmUserID().toString());
        values.put(Columns.TITLE, task.getmTitle());
        values.put(Columns.DESCRIPTION, task.getmDescription());
        values.put(Columns.DATE, task.getmDate().getTime());
        values.put(Columns.TIME, task.getmTime().getTime());
        values.put(Columns.STATERADIOBUTTON, task.getmStateRadioButton());
        values.put(Columns.STATEVIEWPAGER, task.getmStateViewPager());

//        values.put(Columns.UUID, "_id");
//        values.put(Columns.TITLE, "title");
//        values.put(Columns.DESCRIPTION, "des");
//        values.put(Columns.DATE, "date");
//        values.put(Columns.TIME, "time");
//        values.put(Columns.STATERADIOBUTTON, "ToDo");
//        values.put(Columns.STATEVIEWPAGER, "ToDo");

        return values;
    }

    public Task getTask(UUID id, int currentPage){
//        mTask = getTaskListFromDB(getState(currentPage));
//        for (Task task : mTask) {
//            if (task.getmID().equals(id)){
//                return task;
//            }
//        }
//        return null;

        TaskCursorWrapper cursor = (TaskCursorWrapper) queryTaskUUID(new String[]{id.toString()});
        try {
            cursor.moveToFirst();

            if (cursor == null || cursor.getCount() == 0){
                return null;
            }

            return cursor.getTask();

        }finally {
            cursor.close();
        }
    }

    public List<Task> getTaskListFromDB(String[] state, UUID userID){
        List<Task> taskList = new ArrayList<>();

        TaskCursorWrapper cursor = (TaskCursorWrapper) queryTaskState(state);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                if (cursor.getTask().getmUserID().equals(userID)) {
                    taskList.add(cursor.getTask());
                }

                cursor.moveToNext();
            }

////                String stringUUID = cursor.getString(cursor.getColumnIndex(Columns.UUID));
//                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
//                String description = cursor.getString(cursor.getColumnIndex(Columns.DESCRIPTION));
//                Long stringDate = cursor.getLong(cursor.getColumnIndex(Columns.DATE));
//                Long stringTime = cursor.getLong(cursor.getColumnIndex(Columns.TIME));
//                String stateRadioButton = cursor.getString(cursor.getColumnIndex(Columns.STATERADIOBUTTON));
//                String stateViewPager = cursor.getString(cursor.getColumnIndex(Columns.STATEVIEWPAGER));
//
////                UUID ID = java.util.UUID.fromString(stringUUID);
//                Date date = new Date(stringDate);
//                Date time = new Date(stringTime);
//
//                Task task = new Task(title, description, date, time, stateRadioButton, stateViewPager);
//                taskList.add(task)
        }
        finally {
            cursor.close();
        }

        return taskList;
    }

    public List<Task> getAllTask(String[] userID){
        List<Task> taskList = new ArrayList<>();

        TaskCursorWrapper cursor = (TaskCursorWrapper) queryAllTask(userID);

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                taskList.add(cursor.getTask());

                cursor.moveToNext();
            }

////                String stringUUID = cursor.getString(cursor.getColumnIndex(Columns.UUID));
//                String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
//                String description = cursor.getString(cursor.getColumnIndex(Columns.DESCRIPTION));
//                Long stringDate = cursor.getLong(cursor.getColumnIndex(Columns.DATE));
//                Long stringTime = cursor.getLong(cursor.getColumnIndex(Columns.TIME));
//                String stateRadioButton = cursor.getString(cursor.getColumnIndex(Columns.STATERADIOBUTTON));
//                String stateViewPager = cursor.getString(cursor.getColumnIndex(Columns.STATEVIEWPAGER));
//
////                UUID ID = java.util.UUID.fromString(stringUUID);
//                Date date = new Date(stringDate);
//                Date time = new Date(stringTime);
//
//                Task task = new Task(title, description, date, time, stateRadioButton, stateViewPager);
//                taskList.add(task)
        }
        finally {
            cursor.close();
        }

        return taskList;
    }

    public String[] getState(int currentPage, UUID mUserID){
        String[] state = new String[3];
        switch (currentPage){
            case 0:{
                state[0] = state[1] = "ToDo";
//                state[1] = "ToDo";
//                state[2] = "ToDo";
                break;
            }
            case 1:{
                state[0] = state[1] = "Doing";
//                state[1] = "Doing";
                break;
            }
            case 2:{
                state[0] = state[1] = "Done";
//                state[1] = "Done1";
                break;
            }
        }
//        state[0] = mUserID.toString();
        state[2] = "";
        return state;
    }

    private CursorWrapper queryTaskUUID(String[] id){
        Cursor cursor = mDatabase.query(NAME,
                null,
                Columns.UUID + "=?",
                id,
                null,
                null,
                null);

        return new TaskCursorWrapper(cursor);
    }

    private CursorWrapper queryAllTask(String[] userID){
        Cursor cursor = mDatabase.query(NAME,
                null,
                Columns.USER_UUID + "=?",
                userID,
                null,
                null,
                null);

        return new TaskCursorWrapper(cursor);
    }

    private CursorWrapper queryTaskState(String[] state){

        Cursor cursor = mDatabase.query(NAME,
                null,
                (Columns.STATERADIOBUTTON + "=?") + " or " + (Columns.STATEVIEWPAGER + "=? and " + Columns.STATERADIOBUTTON + "=?"),
                state,
                null,
                null,
                null);

        return new TaskCursorWrapper(cursor);
    }

    public String setState(int currentPage){
        String state = "";
        switch (currentPage){
            case 0: {
                state = "ToDo";
                break;
            }
            case 1: {
                state = "Doing";
                break;
            }
            case 2: {
                state = "Done";
                break;
            }
        }
        return state;
    }


//    public List<Task> getTaskList(String mStateRadioButton){
//        List<Task> mTask = null;
//        switch (mStateRadioButton){
//            case "ToDo":{
//                mTask = getmTaskToDo();
//                break;
//            }
//            case "Doing":{
//                mTask = getmTaskDoing();
//                break;
//            }
//            case "Done":{
//                mTask = getmTaskDone();
//                break;
//            }
//        }
//        return mTask;
//    }

//    public List<Task> getTaskList(int currentPage){
//        List<Task> mTask = null;
//        switch (currentPage){
//            case 0:{
//                mTask = getmTaskToDo();
//                break;
//            }
//            case 1:{
//                mTask = getmTaskDoing();
//                break;
//            }
//            case 2:{
//                mTask = getmTaskDone();
//                break;
//            }
//        }
//        return mTask;
//    }
}
