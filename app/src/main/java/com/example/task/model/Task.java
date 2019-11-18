package com.example.task.model;

import com.example.task.greendao.DateConverter;
import com.example.task.greendao.UuidConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Task")
public class Task{

    @Id(autoincrement = true)
    private Long _id;

    @Property(nameInDb = "uuid")
    @Index(unique = true)
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mID;

    @Property(nameInDb = "userID")
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mUserID;

    @Property(nameInDb = "title")
    private String mTitle;

    @Property(nameInDb = "description")
    private String mDescription;

    @Property(nameInDb = "date")
    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date mDate = new Date();

    @Property(nameInDb = "time")
    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date mTime = new Date();

    @Property(nameInDb = "stateRadioButton")
    private String mStateRadioButton;

    @Property(nameInDb = "stateViewPager")
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

    @Generated(hash = 2064469781)
    public Task(Long _id, UUID mID, UUID mUserID, String mTitle, String mDescription, Date mDate, Date mTime, String mStateRadioButton,
            String mStateViewPager) {
        this._id = _id;
        this.mID = mID;
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

    public String getPhotoName(){
        return "IMG" + mID + ".jpg";
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public UUID getMID() {
        return this.mID;
    }

    public void setMID(UUID mID) {
        this.mID = mID;
    }

    public UUID getMUserID() {
        return this.mUserID;
    }

    public void setMUserID(UUID mUserID) {
        this.mUserID = mUserID;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMDescription() {
        return this.mDescription;
    }

    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    public Date getMTime() {
        return this.mTime;
    }

    public void setMTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getMStateRadioButton() {
        return this.mStateRadioButton;
    }

    public void setMStateRadioButton(String mStateRadioButton) {
        this.mStateRadioButton = mStateRadioButton;
    }

    public String getMStateViewPager() {
        return this.mStateViewPager;
    }

    public void setMStateViewPager(String mStateViewPager) {
        this.mStateViewPager = mStateViewPager;
    }
}
