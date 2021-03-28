package com.pss.myapplication;

public class ListBranch {
    private int bid;
    private String branch_name;

    public ListBranch(int bid, String branch_name) {
        this.bid = bid;
        this.branch_name = branch_name;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}