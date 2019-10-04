package com.example.task.model;

import java.util.Date;
import java.util.UUID;

public class Task{
    private UUID mID;
    private UUID mUserID;
    private String mTitle;
    private String mDescription;
    private Date mDate = new Date();
    private Date mTime = new Date();
    private String mStateRadioButton;
    private String mStateViewPager;

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID mID) {
        this.mID = mID;
    }

    public Task(UUID mUserID, String mTitle, String mDescription, Date mDate, Date mTime ,String mStateRadioButton ,String mStateViewPager) {
//        this.mID = mID;
        this.mUserID = mUserID;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mStateRadioButton = mStateRadioButton;
        this.mStateViewPager = mStateViewPager;
    }

    public UUID getmUserID() {
        return mUserID;
    }

    public void setmUserID(UUID mUserID) {
        this.mUserID = mUserID;
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

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getmStateViewPager() {
        return mStateViewPager;
    }

    public void setmStateViewPager(String mStateViewPager) {
        this.mStateViewPager = mStateViewPager;
    }
}
