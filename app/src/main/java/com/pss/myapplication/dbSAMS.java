package com.pss.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbSAMS extends SQLiteOpenHelper {

    //database
    private final static String DATABASE_NAME = "db_sams";
    private final static int DATABASE_VERSION = 1;

    //admin table
    private final String adminTable = "CREATE TABLE admin(" +
            "aid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "mobileno NUMERIC NOT NULL" +
            ");";
    //end admin table

    //branch table
    private final String branchTable = "CREATE TABLE branch(" +
            "bid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "branch_name TEXT NOT NULL" +
            ");";
    //end branch table

    //batch table
    private final String batchTable = "CREATE TABLE batch(" +
            "btid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "batch_name TEXT NOT NULL" +
            ");";
    //end batch table

    //subject table
    private final String subjectTable = "CREATE TABLE subject(" +
            "sbid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "subject_name TEXT NOT NULL," +
            "semester INTEGER NOT NULL," +
            "bid INTEGER NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)" +
            ");";
    //end subject table

    //lecture table
    private final String lectureTable = "CREATE TABLE lecture(" +
            "lid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "lecture_type TEXT NOT NULL" +
            ");";
    //end lecture table

    //staff table
    private final String staffTable = "CREATE TABLE staff(" +
            "sid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "mobileno NUMERIC NOT NULL," +
            "bid INTEGER NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)" +
            ");";
    //end table

    //tack_subject table
    private final String tackSubjectTable = "CREATE TABLE tack_subject(" +
            "tsid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "bid INTEGER NOT NULL," +
            "btid INTEGER NOT NULL," +
            "sid INTEGER NOT NULL," +
            "sbid INTEGER NOT NULL," +
            "lid INTEGER NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)," +
            "FOREIGN KEY(btid) REFERENCES batch(btid)," +
            "FOREIGN KEY(sid) REFERENCES staff(sid)," +
            "FOREIGN KEY(sbid) REFERENCES subject(sbid)," +
            "FOREIGN KEY(lid) REFERENCES lecture(lid)" +
            ");";
    //end tack_subject table

    //student table
    private final String studentTable = "CREATE TABLE student(" +
            "stid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "mobileno NUMERIC NOT NULL," +
            "parent_email TEXT NOT NULL," +
            "parent_mobileno NUMERIC NOT NULL," +
            "semester INTEGER NOT NULL," +
            "bid INTEGER NOT NULL," +
            "btid INTEGER NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)," +
            "FOREIGN KEY(btid) REFERENCES batch(btid)" +
            ");";
    //end student table

    //attendance table
    private final String attendanceTable = "CREATE TABLE attendance(" +
            "atid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "bid INTEGER NOT NULL," +
            "btid INTEGER NOT NULL," +
            "sid INTEGER NOT NULL," +
            "stid INTEGER NOT NULL," +
            "sbid INTEGER NOT NULL," +
            "lid INTEGER NOT NULL," +
            "slot INTEGER NOT NULL," +
            "attend BOOLEAN NOT NULL," +
            "date DATE NOT NULL," +
            "descript TEXT NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)," +
            "FOREIGN KEY(btid) REFERENCES batch(bid)," +
            "FOREIGN KEY(sid) REFERENCES staff(sid)," +
            "FOREIGN KEY(stid) REFERENCES student(stid)," +
            "FOREIGN KEY(sbid) REFERENCES subject(sbid)," +
            "FOREIGN KEY(lid) REFERENCES lecture(lid)" +
            ");";
    //end attendance table

    public dbSAMS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(adminTable);
        db.execSQL(branchTable);
        db.execSQL(batchTable);
        db.execSQL(subjectTable);
        db.execSQL(lectureTable);
        db.execSQL(staffTable);
        db.execSQL(tackSubjectTable);
        db.execSQL(studentTable);
        db.execSQL(attendanceTable);
        db.execSQL("INSERT INTO admin(name,email,password,mobileno) VALUES('admin','admin@admin" +
                ".com','admin123',9876543210)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS branch");
        db.execSQL("DROP TABLE IF EXISTS batch");
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS lecture");
        db.execSQL("DROP TABLE IF EXISTS staff");
        db.execSQL("DROP TABLE IF EXISTS tack_subject");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        onCreate(db);
    }

    public Boolean check(String username, String password, String userType) {
        SQLiteDatabase db = getWritableDatabase();
        String q = null;
        if (!userType.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            if (userType.equals("admin")) {
                q = "SELECT email,password FROM admin WHERE email=" + "'" + username + "'" + " " +
                        "AND " + "password='" + password + "'";
            }
            if (userType.equals("staff")) {
                q = "SELECT email,password FROM staff WHERE email=" + "'" + username + "'" + " " +
                        "AND " +
                        "password='" + password + "'";
            }
            if (userType.equals("student")) {
                q = "SELECT email,password FROM student WHERE email=" + "'" + username + "'" + " " +
                        "AND " +
                        "password='" + password + "'";
            }
            Cursor cursor = db.rawQuery(q, null);
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } else {
            return false;
        }
    }

    protected Boolean changePassword(String username, String password, String userType) {
        SQLiteDatabase db = getWritableDatabase();
        String q = null;
        if (!userType.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            if (userType.equals("admin")) {
                q = "UPDATE admin SET password='" + password + "' WHERE email='" + username + "'";
            }
            if (userType.equals("staff")) {
                q = "SELECT email,password FROM staff WHERE email=" + "'" + username + "'" + " " +
                        "AND " +
                        "password='" + password + "'";
            }
            if (userType.equals("student")) {
                q = "SELECT email,password FROM student WHERE email=" + "'" + username + "'" + " " +
                        "AND " +
                        "password='" + password + "'";
            }
            try {
                db.execSQL(q);
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    protected String insertBatch(String batchName) {
        if(!batchName.isEmpty()) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("batch_name", batchName);
            float insert = db.insert("batch", null, cv);
            if (insert == -1) {
                return "Failed to Add Batch";
            } else {
                return "Batch Added Successfully!";
            }
        }
        else
        {
            return "Not Inserted!";
        }
    }


    protected Cursor getAllBatch() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM batch ORDER BY btid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }


    protected boolean isBatchAlready(String batch) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  batch_name FROM batch";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext())
        {
            if ( cursor.getString(0).equals(batch))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected String insertBranch(String branchName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("branch_name", branchName);
        float insert = db.insert("branch", null, cv);
        if (insert == -1) {
            return "Failed to Add Branch";
        } else {
            return "Branch Added Successfully!";
        }
    }
}