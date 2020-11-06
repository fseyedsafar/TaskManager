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

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskOpenHelper taskOpenHelper = new TaskOpenHelper(context);
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
        mTaskDao.insert(task);
    }

    public void update(Task task, int currentPage, UUID id){
        mTaskDao.update(task);
    }

    public void delete(UUID id){
        mTaskDao.delete(getTask(id));
    }

    public Task getTask(UUID id){
        return mTaskDao.queryBuilder().where(TaskDao.Properties.MID.eq(id)).unique();
    }

    public List<Task> getTaskListFromDBForAdmin(String[] state){
        QueryBuilder queryBuilder = mTaskDao.queryBuilder();
        queryBuilder.where(queryBuilder.or(TaskDao.Properties.MStateRadioButton.eq(state[0]),
                queryBuilder.and(TaskDao.Properties.MStateRadioButton.eq(state[1]), TaskDao.Properties.MStateViewPager.eq(state[0]))));

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
        return mTaskDao.queryBuilder().list();
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
