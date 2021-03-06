package com.pss.myapplication;

import java.util.List;

public class ListProfessor {
    private int sid;
    private String professor_name;
    private String professor_email;
    private String professor_password;
    private String professor_mobileno;
    private int bid; //Branch ID
    private String branch_name;

    public ListProfessor(int sid, String professor_name, String professor_email,
                         String professor_password, String professor_mobileno, String branch_name) {
        this.sid = sid;
        this.professor_name = professor_name;
        this.professor_email = professor_email;
        this.professor_password = professor_password;
        this.professor_mobileno = professor_mobileno;
        this.branch_name = branch_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public String getProfessor_email() {
        return professor_email;
    }

    public void setProfessor_email(String professor_email) {
        this.professor_email = professor_email;
    }

    public String getProfessor_password() {
        return professor_password;
    }

    public void setProfessor_password(String professor_password) {
        this.professor_password = professor_password;
    }

    public String getProfessor_mobileno() {
        return professor_mobileno;
    }

    public void setProfessor_mobileno(String professor_mobileno) {
        this.professor_mobileno = professor_mobileno;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }


}