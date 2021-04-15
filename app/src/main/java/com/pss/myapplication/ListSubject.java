package com.pss.myapplication;

public class ListSubject {

    private int sbid;
    private String subject_name;
    private int semester;
    private int bid;  //get for branch id
    private String branch_name; //get for branch name

    public ListSubject(int sbid, String subject_name, int semester, String branch_name) {
        this.sbid = sbid;
        this.subject_name = subject_name;
        this.semester = semester;
        this.branch_name = branch_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public int getSbid() {
        return sbid;
    }

    public void setSbid(int sbid) {
        this.sbid = sbid;
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
}
