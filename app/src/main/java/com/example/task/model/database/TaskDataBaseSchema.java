package com.example.task.model.database;

public class TaskDataBaseSchema {
    public static final String NAME = "tasks.db";

    public static final class TaskTable{
        public static final String NAME = "task";

        public static final class Columns{
            public static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String USER_UUID = "user_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String STATE_RADIO_BUTTON = "stateRadioButton";
            public static final String STATE_VIEW_PAGER = "stateViewPager";
        }
    }

    public static final class UserTable{
        public static final String NAME = "user";

        public static final class Columns{
            public static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String USER = "user";
            public static final String PASS = "pass";
            public static final String TASK_COUNT = "taskCount";
        }
    }
}
