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
import java.util.UUID;

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

    public void insert(User user) {
//        mPerson.add(person);
        mDataBase.insert(NAME, null, getContentValue(user));
    }

//    public int search(String user, String pass) {
//        int result = 0;
//        for (int i = 0; i < mPerson.size(); i++) {
//            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
//                result = 1;
//            } else if (user.equals(mPerson.get(i).getmUser())) {
//                result = 2;
//            } else if (pass.equals(mPerson.get(i).getmPass())) {
//                result = 3;
//            }
//        }
//        return result;
//    }

    public UUID getUUID(String user, String pass){
        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUserPass(new String[]{user, pass});

        try {
            cursor.moveToFirst();

            if (cursor == null || cursor.getCount() == 0){
                return null;
            }

//            cursor.moveToNext();

            return cursor.getUser().getmID();
        }
        finally {
            cursor.close();
        }
    }

    public String searchUUID(UUID id){
        String state = "";
        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUUID(new String[]{id.toString()});

        state = checkCursor(cursor);
        return state;
    }

    private String checkCursor(UserCursorWrapper cursor) {
        String state;
        try {
            cursor.moveToFirst();

            if (cursor == null || cursor.getCount() == 0) {
                state = "not exist";
            } else {
                state = "exist";
            }

//            cursor.moveToNext();
        }
        finally {
            cursor.close();
        }
        return state;
    }

    public String searchUserPass(String user, String pass){
        String state = "";
        UserCursorWrapper cursor = (UserCursorWrapper) userQueryUserPass(new String[]{user, pass});

        state = checkCursor(cursor);
        return state;
    }

//    public User getPerson(String user, String pass) {
//        User person = null;
//        for (int i = 0; i < mPerson.size(); i++) {
//            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
//                person = mPerson.get(i);
//            }
//        }
//        return person;
//    }

    private CursorWrapper userQueryUserPass(String[] selectArg){
        Cursor cursor = mDataBase.query(NAME,
                null,
                Columns.USER + "=?" + " and " + Columns.PASS + "=?",
                selectArg,
                null,
                null,
                null);

        return new UserCursorWrapper(cursor);
    }

    private CursorWrapper userQueryUUID(String[] selectArg){
        Cursor cursor = mDataBase.query(NAME,
                null,
                Columns.UUID + "=?",
                selectArg,
                null,
                null,
                null);

        return new UserCursorWrapper(cursor);
    }

    private ContentValues getContentValue(User user){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID, user.getmID().toString());
        values.put(Columns.USER, user.getmUser());
        values.put(Columns.PASS, user.getmPass());
        return values;
    }
}
