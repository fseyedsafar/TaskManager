package com.example.task.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.task.model.DaoMaster;
import com.example.task.model.DaoSession;
import com.example.task.model.Task;
import com.example.task.model.TaskDao;
import com.example.task.greendao.*;
import org.greenrobot.greendao.query.QueryBuilder;
import static com.example.task.model.database.TaskDataBaseSchema.TaskTable.*;
import java.io.File;
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
    private TaskDao mTaskDao;
//    private SQLiteDatabase mDatabase;

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskOpenHelper taskOpenHelper = new TaskOpenHelper(context);
//        mDatabase = taskOpenHelper.getWritableDatabase();

        SQLiteDatabase db = new TaskOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        mTaskDao = daoSession.getTaskDao();
    }

    public static TaskRepository getInstance(Context context) {

        if (instance == null){
            instance = new TaskRepository(context);
        }

        return instance;
    }

    public void insert(Task task, String mStateRadioButton , int currentPage){

//        ContentValues values = getContentValues(task);
//        mDatabase.insert(NAME, null, values);

        mTaskDao.insert(task);
    }

    public void update(Task task, int currentPage, UUID id){
//        mDatabase.update(NAME, getContentValues(task), Columns.UUID + "=?", new String[]{id.toString()});

        mTaskDao.update(task);
    }

    public void delete(UUID id){
//        mDatabase.delete(NAME,Columns.UUID + "=?", new String[]{id.toString()});

        mTaskDao.delete(getTask(id));
    }

    public ContentValues getContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID, task.getmID().toString());
        values.put(Columns.USER_UUID, task.getmUserID().toString());
        values.put(Columns.TITLE, task.getmTitle());
        values.put(Columns.DESCRIPTION, task.getmDescription());
        values.put(Columns.DATE, task.getmDate().getTime());
        values.put(Columns.TIME, task.getmTime().getTime());
        values.put(Columns.STATE_RADIO_BUTTON, task.getmStateRadioButton());
        values.put(Columns.STATE_VIEW_PAGER, task.getmStateViewPager());

        return values;
    }

    public Task getTask(UUID id){

//        TaskCursorWrapper cursor = (TaskCursorWrapper) queryTaskUUID(new String[]{id.toString()});
//        try {
//            cursor.moveToFirst();
//
//            if (cursor == null || cursor.getCount() == 0){
//                return null;
//            }
//
//            return cursor.getTask();
//
//        }finally {
//            cursor.close();
//        }

        return mTaskDao.queryBuilder().where(TaskDao.Properties.MID.eq(id)).unique();
    }

    public List<Task> getTaskListFromDBForAdmin(String[] state){
//        List<Task> taskList = new ArrayList<>();
//
//        TaskCursorWrapper cursor = (TaskCursorWrapper) queryTaskState(state);
//
//        try {
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()) {
//
//                if (UserRepository.getInstance(mContext).isAdmin(userID) == true){
//                    taskList.add(cursor.getTask());
//                }
//
//                else if (cursor.getTask().getmUserID().equals(userID)) {
//                    taskList.add(cursor.getTask());
//                }
//
//                cursor.moveToNext();
//            }
//        }
//        finally {
//            cursor.close();
//        }

        QueryBuilder queryBuilder = mTaskDao.queryBuilder();
        queryBuilder.where(queryBuilder.or(TaskDao.Properties.MStateRadioButton.eq(state[0]), queryBuilder
                .and(TaskDao.Properties.MStateRadioButton.eq(state[1]), TaskDao.Properties.MStateViewPager.eq(state[0]))));

        return queryBuilder.list();
    }

    public List<Task> getTaskListFromDBForUser(String[] state, UUID userId){
        List<Task> lis = new ArrayList<>();

        for (Task task : getTaskListFromDBForAdmin(state)) {
            if (task.getmUserID().equals(userId)){
                lis.add(task);
            }
        }
        return lis;
    }

    public List<Task> getAllUserTask(String[] userID){
//        List<Task> taskList = new ArrayList<>();
//
//        TaskCursorWrapper cursor = (TaskCursorWrapper) queryAllTask(userID);
//
//        try {
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()) {
//                taskList.add(cursor.getTask());
//
//                cursor.moveToNext();
//            }
//        }
//        finally {
//            cursor.close();
//        }

        return mTaskDao.queryBuilder().list();
//        return mTaskDao.queryBuilder().where(TaskDao.Properties.MUserID.eq(userID)).list();
    }

    public String[] getState(int currentPage){
        String[] state = new String[2];
        switch (currentPage){
            case 0:{
                state[0] = "ToDo";
                break;
            }
            case 1:{
                state[0] = "Doing";
                break;
            }
            case 2:{
                state[0] = "Done";
                break;
            }
        }
        state[1] = "";
        return state;
    }

//    private CursorWrapper queryTaskUUID(String[] id){
//        Cursor cursor = mDatabase.query(NAME,
//                null,
//                Columns.UUID + "=?",
//                id,
//                null,
//                null,
//                null);
//
//        return new TaskCursorWrapper(cursor);
//    }

//    private CursorWrapper queryAllTask(String[] userID){
//        Cursor cursor = mDatabase.query(NAME,
//                null,
//                Columns.USER_UUID + "=?",
//                userID,
//                null,
//                null,
//                null);
//
//        return new TaskCursorWrapper(cursor);
//    }

//    private CursorWrapper queryTaskState(String[] state){
//
//        Cursor cursor = mDatabase.query(NAME,
//                null,
//                (Columns.STATE_RADIO_BUTTON + "=?") + " or " + (Columns.STATE_VIEW_PAGER + "=? and " + Columns.STATE_RADIO_BUTTON + "=?"),
//                state,
//                null,
//                null,
//                null);
//
//        return new TaskCursorWrapper(cursor);
//    }

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

    public File getPhotoFile(Task task){
        return new File(mContext.getFilesDir(), task.getPhotoName());
    }
}
