package com.example.task.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonAndTask {
    private UUID mID;
    private List<Task> mTaskToDo;
    private List<Task> mTaskDoing;
    private List<Task> mTaskDone;

    public PersonAndTask(UUID mID, List<Task> mTaskToDo, List<Task> mTaskDoing, List<Task> mTaskDone) {
        this.mID = mID;
        this.mTaskToDo = mTaskToDo;
        this.mTaskDoing = mTaskDoing;
        this.mTaskDone = mTaskDone;
    }

    public UUID getmID() {
        return mID;
    }

    public List<Task> getmTaskToDo() {
        return mTaskToDo;
    }

    public List<Task> getmTaskDoing() {
        return mTaskDoing;
    }

    public List<Task> getmTaskDone() {
        return mTaskDone;
    }
}
