package com.example.task.greendao;

import android.content.Context;
import com.example.task.model.DaoMaster;

public class TaskOpenHelper extends DaoMaster.OpenHelper {

    public static final String NAME = "task.db";

    public TaskOpenHelper(Context context) {
        super(context, NAME);
    }
}
