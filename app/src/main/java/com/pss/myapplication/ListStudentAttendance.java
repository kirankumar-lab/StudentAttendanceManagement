package com.pss.myapplication;

import androidx.annotation.Nullable;

public class ListStudentAttendance {
    private int sid;
    private String name;
    private boolean isPresent;
    private @Nullable int rollNo;

    public ListStudentAttendance(int sid, String name, boolean isPresent, @Nullable int rollNo) {
        this.sid = sid;
        this.name = name;
        this.isPresent = isPresent;
        this.rollNo = rollNo;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
}