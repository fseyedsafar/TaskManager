package com.example.task.repository;

import com.example.task.model.PersonAndTask;
import java.util.ArrayList;
import java.util.List;

public class PersonAndTaskRepository {

    public static final PersonAndTaskRepository instance = new PersonAndTaskRepository();
    List<PersonAndTask> mPersonAndTask = new ArrayList<>();

    private PersonAndTaskRepository() {
    }

    public static PersonAndTaskRepository getInstance() {
        return instance;
    }

    public List<PersonAndTask> getmPersonAndTask() {
        return mPersonAndTask;
    }

    public void insert(PersonAndTask personAndTask){
        mPersonAndTask.add(personAndTask);
    }
}
