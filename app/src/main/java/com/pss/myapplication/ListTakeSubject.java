package com.pss.myapplication;

public class ListTakeSubject {
    private int tsid;
    private String subject_name;
    private int semester;
    private int bid;  //get for branch id
    private int btid;  //get for batch id
    private int sid;  //get for staff id
    private int lid;  //get for lecture id

    public ListTakeSubject(int tsid, String subject_name, int semester, int bid, int btid, int sid, int lid) {
        this.tsid = tsid;
        this.subject_name = subject_name;
        this.semester = semester;
        this.bid = bid;
        this.btid = btid;
        this.sid = sid;
        this.lid = lid;
    }

    public int getTsid() {
        return tsid;
    }

    public void setTsid(int tsid) {
        this.tsid = tsid;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }
}