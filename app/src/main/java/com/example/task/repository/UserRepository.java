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

import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    public static UserRepository instance;
    private List<User> mPerson = new ArrayList<>();
//    private SQLiteDatabase mDataBase;
    private UserDao mUserDao;
    private Context mContext;

    private UserRepository(Context context) {
        this.mContext = context.getApplicationContext();
        TaskOpenHelper taskOpenHelper = new TaskOpenHelper(mContext);
//        mDataBase = taskOpenHelper.getWritableDatabase();

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
//        mDataBase.insert(NAME, null, getContentValue(user));
        mUserDao.insert(user);
    }

    public void delete(UUID mID){
//        mDataBase.delete(NAME, Columns.UUID + "=?", new String[]{mID.toString()});
        User user = getUser(mID);
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
//        mDataBase.update(NAME, getContentValue(user), Columns.UUID + "=?", new String[]{user.getmID().toString()});
        mUserDao.update(user);
    }

    public UUID getUUID(String user, String pass){

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        queryBuilder.where(queryBuilder.and(UserDao.Properties.MUser.eq(user), UserDao.Properties.MPass.eq(pass)));

        return queryBuilder.unique().getmID();
//        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUserPass(new String[]{user, pass});
//
//        try {
//            cursor.moveToFirst();
//
//            if (cursor == null || cursor.getCount() == 0){
//                return null;
//            }
//
//            return cursor.getUser().getmID();
//        }
//        finally {
//            cursor.close();
//        }
    }

    public String searchUUID(UUID id){

//        String state = "";
//        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUUID(new String[]{id.toString()});
//
//        state = checkCursor(cursor);

        User user = mUserDao.queryBuilder().where(UserDao.Properties.MID.eq(id)).unique();

        if (user == null){
            return "not exist";
        } else {
            return "exist";
        }
//        return userExist(id);
    }

    public User getUser(UUID id){

        return mUserDao.queryBuilder().where(UserDao.Properties.MID.eq(id)).unique();
//        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUUID(new String[]{id.toString()});
//
//        try {
//            cursor.moveToFirst();
//
//            if (cursor.getCount() == 0){
//                return null;
//            } else {
//                return cursor.getUser();
//            }
//        }
//        finally {
//            cursor.close();
//        }
    }

    public List<User> getUserList(){
//        List<User> userList = new ArrayList<>();

//        UserCursorWrapper cursor = (UserCursorWrapper) userQueryAll();
//
//        try {
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()){

//                if (isAdmin(cursor.getUser().getmID()) == true){
//                    cursor.moveToNext();
//                }
//                else {
//                    userList.add(cursor.getUser());
//                    cursor.moveToNext();
//                }
//            }
//
//        }finally {
//            cursor.close();
//        }

        List<User> userList = mUserDao.loadAll();

        userList.remove(getUser(getUUID("admin", "123")));

        return userList;
    }

    public int getTaskCount(UUID id){
        return getUser(id).getmTaskCount();
    }

//    private String userExist(UUID id) {
//        String state;
//        try {
//            cursor.moveToFirst();
//
//            if (cursor == null || cursor.getCount() == 0) {
//                state = "not exist";
//            } else {
//                state = "exist";
//            }
//        }
//        finally {
//            cursor.close();
//        }

//        User user = mUserDao.queryBuilder().where(UserDao.Properties.MID.eq(id)).unique();
//
//        if (user == null){
//            return "not exist";
//        } else {
//            return "exist";
//        }
//    }

    public String searchUserPass(String username, String pass){
//        String state = "";
//        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUserPass(new String[]{user, pass});
//
//        state = checkCursor(cursor);
//        return state;

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        User user = queryBuilder.where(queryBuilder.and(UserDao.Properties.MUser.eq(username), UserDao.Properties.MPass.eq(pass))).unique();

        if (user == null){
            return "not exist";
        } else {
            return "exist";
        }
    }

//    private CursorWrapper userQueryUserPass(String[] selectArg){
//        Cursor cursor = mDataBase.query(NAME,
//                null,
//                Columns.USER + "=?" + " and " + Columns.PASS + "=?",
//                selectArg,
//                null,
//                null,
//                null);
//
//        return new UserCursorWrapper(cursor);
//    }

//    private CursorWrapper userQueryUUID(String[] selectArg){
//        Cursor cursor = mDataBase.query(NAME,
//                null,
//                Columns.UUID + "=?",
//                selectArg,
//                null,
//                null,
//                null);

//        return new UserCursorWrapper(cursor);
//    }

//    private CursorWrapper userQueryAll(){
//        Cursor cursor = mDataBase.query(NAME, null, null, null, null, null, null);
//        return new UserCursorWrapper(cursor);
//    }

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
