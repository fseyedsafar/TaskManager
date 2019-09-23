package com.example.task.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task{
    private UUID mID;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Time mTime;
    private boolean mState;
    private String mStateRadioButton;
    private String mStateViewPager;

    public Task(String mTitle, String mDescription, Date mDate, boolean mState, String mStateRadioButton , String mStateViewPager) {
        this.mID = UUID.randomUUID();;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
//        this.mTime = mtime;
        this.mState = mState;
        this.mStateRadioButton = mStateRadioButton;
        this.mStateViewPager = mStateViewPager;
    }

    public String getmStateRadioButton() {
        return mStateRadioButton;
    }

    public void setmStateRadioButton(String mStateRadioButton) {
        this.mStateRadioButton = mStateRadioButton;
    }

    public UUID getmID() {
        return mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public String getmDescription()
    {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate)
    {
        this.mDate = mDate;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

    public boolean getmState() {
        return mState;
    }

    public void setmState(boolean mState) {
        this.mState = mState;
    }
}
