package com.pss.myapplication;

public class ListLecture {
    private int lid;
    private String lecture_type;

    public ListLecture(int lid, String lecture_type) {
        this.lid = lid;
        this.lecture_type = lecture_type;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getLecture_type() {
        return lecture_type;
    }

    public void setLecture_type(String lecture_type) {
        this.lecture_type = lecture_type;
    }
}