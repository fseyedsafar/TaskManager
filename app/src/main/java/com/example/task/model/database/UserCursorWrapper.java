package com.example.task.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.task.model.Task;
import com.example.task.model.User;

import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        String stringUUID = getString(getColumnIndex(TaskDataBaseSchema.UserTable.Columns.UUID));
        String userString = getString(getColumnIndex(TaskDataBaseSchema.UserTable.Columns.USER));
        String pass = getString(getColumnIndex(TaskDataBaseSchema.UserTable.Columns.PASS));

        UUID ID = java.util.UUID.fromString(stringUUID);

        User user = new User(ID);
        user.setmUser(userString);
        user.setmPass(pass);

        return user;
    }
}
