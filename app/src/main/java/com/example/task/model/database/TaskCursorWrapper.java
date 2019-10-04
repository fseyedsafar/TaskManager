package com.example.task.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.task.model.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){

        String stringUUID = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.UUID));
        String stringUserUUID = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.USER_UUID));
        String title = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.TITLE));
        String description = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.DESCRIPTION));
        Long stringDate = getLong(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.DATE));
        Long stringTime = getLong(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.TIME));
        String stateRadioButton = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.STATERADIOBUTTON));
        String stateViewPager = getString(getColumnIndex(TaskDataBaseSchema.TaskTable.Columns.STATEVIEWPAGER));

        UUID ID = java.util.UUID.fromString(stringUUID);
        UUID userUUID = java.util.UUID.fromString(stringUserUUID);
        Date date = new Date(stringDate);
        Date time = new Date(stringTime);


        Task task = new Task(ID);
        task.setmUserID(userUUID);
        task.setmTitle(title);
        task.setmDescription(description);
        task.setmDate(date);
        task.setmTime(time);
        task.setmStateRadioButton(stateRadioButton);
        task.setmStateViewPager(stateViewPager);

        return task;
    }
}
