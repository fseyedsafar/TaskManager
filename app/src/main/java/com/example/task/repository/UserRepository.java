package com.example.task.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import com.example.task.model.User;
import com.example.task.model.database.TaskOpenHelper;
import com.example.task.model.database.UserCursorWrapper;
import com.example.task.model.database.TaskOpenHelper;
import static com.example.task.model.database.TaskDataBaseSchema.UserTable.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static UserRepository instance;
    private List<User> mPerson = new ArrayList<>();
    private SQLiteDatabase mDataBase;
    private Context mContext;

    private UserRepository(Context context) {
        this.mContext = context.getApplicationContext();
        TaskOpenHelper taskOpenHelper = new TaskOpenHelper(mContext);
        mDataBase = taskOpenHelper.getWritableDatabase();
    }

    public static UserRepository getInstance(Context context) {

        if (instance == null){
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void insert(User person) {
        mPerson.add(person);
    }

    public int search(String user, String pass) {
        int result = 0;
        for (int i = 0; i < mPerson.size(); i++) {
            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
                result = 1;
            } else if (user.equals(mPerson.get(i).getmUser())) {
                result = 2;
            } else if (pass.equals(mPerson.get(i).getmPass())) {
                result = 3;
            }
        }
        return result;
    }

    public User getPerson(String user, String pass) {
        User person = null;
        for (int i = 0; i < mPerson.size(); i++) {
            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
                person = mPerson.get(i);
            }
        }
        return person;
    }

    private CursorWrapper userQuery(String[] id){
        Cursor cursor = mDataBase.query(NAME,
                null,
                Columns.UUID + "=?",
                id,
                null,
                null,
                null);

        return (UserCursorWrapper)cursor;
    }

//    private ContentValues getContentValue(User user){
//        ContentValues values = new ContentValues();
//        values.put();
//
//    }
}
