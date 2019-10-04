package com.example.task.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import static com.example.task.model.database.TaskDataBaseSchema.*;

public class TaskOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public TaskOpenHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskTable.NAME +
                "(" +
                TaskTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskTable.Columns.UUID + ", " +
                TaskTable.Columns.USER_UUID + ", " +
                TaskTable.Columns.TITLE + ", " +
                TaskTable.Columns.DESCRIPTION + ", " +
                TaskTable.Columns.DATE + ", " +
                TaskTable.Columns.TIME + ", " +
                TaskTable.Columns.STATERADIOBUTTON + ", " +
                TaskTable.Columns.STATEVIEWPAGER +
                ");"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + UserTable.NAME +
                "(" +
                UserTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.Columns.UUID + ", " +
                UserTable.Columns.USER + ", " +
                UserTable.Columns.PASS +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
