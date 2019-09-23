package com.example.task.repository;

import com.example.task.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {

    public static final TaskRepository instance = new TaskRepository();
    private List<Task> mTask = new ArrayList<>();
    private List<Task> mTaskToDo = new ArrayList<>();
    private List<Task> mTaskDoing = new ArrayList<>();
    private List<Task> mTaskDone = new ArrayList<>();

    private TaskRepository() {
    }

    public static TaskRepository getInstance()
    {
        return instance;
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

    public void setmTaskToDo(List<Task> mTaskToDo) {
        this.mTaskToDo = mTaskToDo;
    }

    public void setmTaskDoing(List<Task> mTaskDoing) {
        this.mTaskDoing = mTaskDoing;
    }

    public void setmTaskDone(List<Task> mTaskDone) {
        this.mTaskDone = mTaskDone;
    }

    public void insert(Task task, String mStateRadioButton , int currentPage){

        if (mStateRadioButton.equals("")){
            mTask = getTaskList(currentPage);
        }
        else {
            mTask = getTaskList(mStateRadioButton);
        }
        mTask.add(task);
    }

    public void update(Task task, int currentPage){
        mTask = getTaskList(currentPage);
        for (int i = 0 ; i < mTask.size() ; i++){
            if (task.getmID().equals(mTask.get(i).getmID())){
                mTask.set(i , task);
            }
        }
    }

    public void delete(Task task, int currentPage){
        mTask = getTaskList(currentPage);
        for (int i = 0 ; i < mTask.size() ; i++){
            if (task.getmID().equals(mTask.get(i).getmID())){
                mTask.remove(i);
            }
        }
    }

    public Task getTask(UUID id, int currentPage){
        mTask = getTaskList(currentPage);
        for (Task task : mTask) {
            if (task.getmID().equals(id)){
                return task;
            }
        }
        return null;
    }

    public List<Task> getTaskList(String mStateRadioButton){
        List<Task> mTask = null;
        switch (mStateRadioButton){
            case "ToDo":{
                mTask = getmTaskToDo();
                break;
            }
            case "Doing":{
                mTask = getmTaskDoing();
                break;
            }
            case "Done":{
                mTask = getmTaskDone();
                break;
            }
        }
        return mTask;
    }

    public List<Task> getTaskList(int currentPage){
        List<Task> mTask = null;
        switch (currentPage){
            case 0:{
                mTask = getmTaskToDo();
                break;
            }
            case 1:{
                mTask = getmTaskDoing();
                break;
            }
            case 2:{
                mTask = getmTaskDone();
                break;
            }
        }
        return mTask;
    }
}
