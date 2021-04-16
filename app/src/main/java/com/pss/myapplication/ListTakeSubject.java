package com.pss.myapplication;

public class ListTakeSubject {
    private int tsid;
    private String subject_name;
    private int semester;
    private int bid;  //get for branch id
    private int btid;  //get for batch id
    private int sid;  //get for staff id
    private int lid;  //get for lecture id
    private String batch_name; //get for batch name
    private String professor_name; //get for professor name

    public ListTakeSubject(int tsid, String subject_name, int semester, int bid,
                           String batch_name, String professor_name,
                           int lid) {
        this.tsid = tsid;
        this.subject_name = subject_name;
        this.semester = semester;
        this.bid = bid;
        //this.btid = btid;
        this.batch_name = batch_name;
        this.professor_name = professor_name;
        this.lid = lid;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
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
