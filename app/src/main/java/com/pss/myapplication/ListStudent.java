package com.pss.myapplication;

public class ListStudent {
    private int stid;
    private String student_name;
    private String student_email;
    private String student_password;
    private String student_mobileno;
    private String student_p_email;
    private String student_p_mobileno;
    private int btid; //Batch ID
    private int bid; //Branch ID
    private int semester;

    public ListStudent(int stid, String student_name, String student_email, String student_password, String student_mobileno, String student_p_email, String student_p_mobileno, int btid, int bid, int semester) {
        this.stid = stid;
        this.student_name = student_name;
        this.student_email = student_email;
        this.student_password = student_password;
        this.student_mobileno = student_mobileno;
        this.student_p_email = student_p_email;
        this.student_p_mobileno = student_p_mobileno;
        this.btid = btid;
        this.bid = bid;
        this.semester = semester;
    }

    public int getSid() {
        return stid;
    }

    public void setSid(int stid) {
        this.stid = stid;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    public String getStudent_mobileno() {
        return student_mobileno;
    }

    public void setStudent_mobileno(String student_mobileno) {
        this.student_mobileno = student_mobileno;
    }

    public String getStudent_p_email() {
        return student_p_email;
    }

    public void setStudent_p_email(String student_p_email) {
        this.student_p_email = student_p_email;
    }

    public String getStudent_p_mobileno() {
        return student_p_mobileno;
    }

    public void setStudent_p_mobileno(String student_p_mobileno) {
        this.student_p_mobileno = student_p_mobileno;
    }

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}