package com.example.task.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.task.greendao.*;
import com.example.task.model.DaoMaster;
import com.example.task.model.DaoSession;
import com.example.task.model.User;
import com.example.task.model.UserDao;
import org.greenrobot.greendao.query.QueryBuilder;
import static com.example.task.model.database.TaskDataBaseSchema.UserTable.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    public static UserRepository instance;
    private List<User> mPerson = new ArrayList<>();
    private UserDao mUserDao;
    private Context mContext;

    private UserRepository(Context context) {
        this.mContext = context.getApplicationContext();
        SQLiteDatabase db = new TaskOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        mUserDao = daoSession.getUserDao();
    }

    public static UserRepository getInstance(Context context) {

        if (instance == null){
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void insert(User user) {
        mUserDao.insert(user);
    }

    public void delete(UUID mID){
//        User user = getUser(mID);
        mUserDao.delete(getUser(mID));
    }

    public boolean isAdmin(UUID id){
        if (getUser(id).getmUser().equals("admin") && getUser(id).getmPass().equals("123")){
            return true;
        }
        else {
            return false;
        }
    }

    public void update(User user){
        mUserDao.update(user);
    }

    public UUID getUUID(String user, String pass){

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        queryBuilder.where(queryBuilder.and(UserDao.Properties.MUser.eq(user), UserDao.Properties.MPass.eq(pass)));

        return queryBuilder.unique().getmID();
    }

    public String searchUUID(UUID id){
        User user = mUserDao.queryBuilder().where(UserDao.Properties.MID.eq(id)).unique();
        if (user == null){
            return "not exist";
        } else {
            return "exist";
        }
    }

    public User getUser(UUID id){
        return mUserDao.queryBuilder().where(UserDao.Properties.MID.eq(id)).unique();
    }

    public List<User> getUserList(){
        List<User> userList = mUserDao.loadAll();
        userList.remove(getUser(getUUID("admin", "123")));
        return userList;
    }

    public int getTaskCount(UUID id){
        return getUser(id).getmTaskCount();
    }

    public String searchUserPass(String username, String pass){
        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        User user = queryBuilder.where(queryBuilder.and(UserDao.Properties.MUser.eq(username), UserDao.Properties.MPass.eq(pass))).unique();
        if (user == null){
            return "not exist";
        } else {
            return "exist";
        }
    }

    private ContentValues getContentValue(User user){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID, user.getmID().toString());
        values.put(Columns.DATE, user.getmDate().toString());
        values.put(Columns.USER, user.getmUser());
        values.put(Columns.PASS, user.getmPass());
        values.put(Columns.TASK_COUNT, user.getmTaskCount());
        return values;
    }
}
