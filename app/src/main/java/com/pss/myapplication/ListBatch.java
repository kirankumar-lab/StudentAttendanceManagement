package com.pss.myapplication;

public class ListBatch {
    private int btid;
    private String batch_name;

    public ListBatch(int btid, String batch_name) {
        this.btid = btid;
        this.batch_name = batch_name;
    }

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }
}