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

@Entity(nameInDb = "User")
public class User {

    @Id(autoincrement = true)
    private Long _id;

    @Property(nameInDb = "uuid")
    @Index(unique = true)
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mID;

    @Property(nameInDb = "date")
    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date mDate;

    @Property(nameInDb = "user")
    private String mUser;

    @Property(nameInDb = "pass")
    private String mPass;

    @Property(nameInDb = "taskCount")
    private int mTaskCount;

    public User(){
        this(UUID.randomUUID(), new Date());
    }

    public User(UUID mID, Date mDate){
        this.mID = mID;
        this.mDate = mDate;
    }

    @Generated(hash = 44493014)
    public User(Long _id, UUID mID, Date mDate, String mUser, String mPass,
            int mTaskCount) {
        this._id = _id;
        this.mID = mID;
        this.mDate = mDate;
        this.mUser = mUser;
        this.mPass = mPass;
        this.mTaskCount = mTaskCount;
    }

    public int getmTaskCount() {
        return mTaskCount;
    }

    public void setmTaskCount(int mTaskCount) {
        this.mTaskCount = mTaskCount;
    }

    public UUID getmID() {
        return mID;
    }

    public String getmUser() {
        return mUser;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
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

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getMUser() {
        return this.mUser;
    }

    public void setMUser(String mUser) {
        this.mUser = mUser;
    }

    public String getMPass() {
        return this.mPass;
    }

    public void setMPass(String mPass) {
        this.mPass = mPass;
    }

    public int getMTaskCount() {
        return this.mTaskCount;
    }

    public void setMTaskCount(int mTaskCount) {
        this.mTaskCount = mTaskCount;
    }
}
