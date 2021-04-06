package com.pss.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class
dbSAMS extends SQLiteOpenHelper {

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
            "mobileno TEXT NOT NULL," +
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
            "mobileno INTEGER NOT NULL," +
            "parent_email TEXT NOT NULL," +
            "parent_mobileno INTEGER NOT NULL," +
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

    /*Change Password Queries*/
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
                q = "UPDATE staff SET password='" + password + "' WHERE email='" + username + "'";
            }
            if (userType.equals("student")) {
                q = "UPDATE student SET password='" + password + "' WHERE email='" + username + "'";
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

    /*Batch  Queries*/
    protected String insertBatch(String batchName) {
        if (!batchName.isEmpty()) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("batch_name", batchName);
            float insert = db.insert("batch", null, cv);
            if (insert == -1) {
                return "Failed to Add Batch";
            } else {
                return "Batch Added Successfully!";
            }
        } else {
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
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(batch)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int batchCount(String batch) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  batch_name FROM batch WHERE batch_name='" + batch + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateBatch(int btid, String batchName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("batch_name", batchName);

        if (isBatchAlready(batchName)) {
            return "Batch name already exists";
        } else {
            int result = db.update("batch", cv, "btid" + "=?", new String[]{String.valueOf(btid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteBatch(int btid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("batch", "btid=?", new String[]{String.valueOf(btid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }


    /*Branch Queries*/
    protected String insertBranch(String branchName) {
        if (!branchName.isEmpty()) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("branch_name", branchName);
            float insert = db.insert("branch", null, cv);
            if (insert == -1) {
                return "Failed to Add Branch";
            } else {
                return "Branch Added Successfully!";
            }
        } else {
            return "Not Inserted!";
        }
    }

    protected Cursor getAllBranch() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM branch ORDER BY bid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected String getBranchID(String branchName) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM branch WHERE branch_name='" + branchName + "'";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(branchName)) {
                    q = cursor.getString(0).trim().toString();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected String getBranchName(int branchID) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM branch WHERE bid=" + branchID + "";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(branchID)) {
                    q = cursor.getString(1).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected boolean isBranchAlready(String branch) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  branch_name FROM branch";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(branch)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int branchCount(String branch) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  branch_name FROM branch WHERE branch_name='" + branch + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateBranch(int bid, String branchName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("branch_name", branchName);

        if (isBatchAlready(branchName)) {
            return "Branch name already exists";
        } else {
            int result = db.update("branch", cv, "bid" + "=?", new String[]{String.valueOf(bid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteBranch(int bid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("branch", "bid=?", new String[]{String.valueOf(bid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }


    /*Subject Queries*/
    protected String insertSubject(String subjectName, int bid, int subjectSemester) {
        try {
            if (!subjectName.isEmpty()) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("subject_name", subjectName);
                cv.put("semester", subjectSemester);
                cv.put("bid", bid);

                float insert = db.insert("subject", null, cv);
                if (insert == -1) {
                    return "Failed to Add Subject";
                } else {
                    return "Subject Added Successfully!";
                }
            } else {
                return "Not Inserted!";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected Cursor getAllSubject() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM subject ORDER BY sbid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected boolean isSubjectAlready(String subject) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  subject_name FROM subject";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(subject)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int subjectCount(String subject) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  subject_name FROM subject WHERE subject_name='" + subject + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateSubject(int sbid, String subjectName, int bid, int subjectSemester) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject_name", subjectName);
        cv.put("semester", subjectSemester);
        cv.put("bid", bid);

        if (subjectCount(subjectName) > 1) {
            return "Subject name already exists";
        } else {
            int result = db.update("subject", cv, "sbid" + "=?", new String[]{String.valueOf(sbid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteSubject(int sbid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("subject", "sbid=?", new String[]{String.valueOf(sbid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

    /*Lecture Queries*/
    protected String insertLecture(String lectureType) {
        if (!lectureType.isEmpty()) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("lecture_type", lectureType);
            float insert = db.insert("lecture", null, cv);
            if (insert == -1) {
                return "Failed to Add Lecture";
            } else {
                return "Lecture Added Successfully!";
            }
        } else {
            return "Not Inserted!";
        }
    }

    protected Cursor getAllLecture() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM lecture";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected String getLectureID(String lectureType) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM lecture WHERE lecture_type='" + lectureType + "'";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(lectureType)) {
                    q = cursor.getString(0).trim().toString();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected String getLecturetype(int lectureID) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM lecture WHERE lid=" + lectureID + "";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(lectureID)) {
                    q = cursor.getString(1).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected boolean isLectureAlready(String lecture) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  lecture_type FROM lecture";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(lecture)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int lectureCount(String lecture) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  lecture_type FROM lecture WHERE lecture_type='" + lecture + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateLecture(int lid, String lectureType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("lecture_type", lectureType);

        if (isBatchAlready(lectureType)) {
            return "Lecture type already exists";
        } else {
            int result = db.update("lecture", cv, "lid" + "=?", new String[]{String.valueOf(lid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteLecture(int lid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("lecture", "lid=?", new String[]{String.valueOf(lid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

    /*Staff Queries*/
    protected String insertProfessor(String professorName,String professorEmail,
                                     String professorPassword,String professorMobile,int bid) {
        try {
            if (!professorName.isEmpty() && !professorEmail.isEmpty() && !professorPassword.isEmpty()) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", professorName);
                cv.put("email", professorEmail);
                cv.put("password", professorPassword);
                cv.put("mobileno", professorMobile);
                cv.put("bid", bid);

                float insert = db.insert("staff", null, cv);
                if (insert == -1) {
                    return "Failed to Add Professor";
                } else {
                    return "Professor Added Successfully!";
                }
            } else {
                return "Not Inserted!";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected Cursor getAllProfessor() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM staff ORDER BY sid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected boolean isProfessorAlready(String professorEmail) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email FROM staff";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(professorEmail)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    protected int professorCount(String email) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email FROM staff WHERE email='" + email + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateProfessor(int sid,String professorName,String professorEmail,
                                     String professorPassword,String professorMobile,int bid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", professorName);
        cv.put("email", professorEmail);
        cv.put("password", professorPassword);
        cv.put("mobileno", professorMobile);
        cv.put("bid", bid);

        if (professorCount(professorEmail) > 1) {
            return "Professor already exists";
        } else {
            int result = db.update("staff", cv, "sid" + "=?", new String[]{String.valueOf(sid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteProfessor(int sid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("staff", "sid=?", new String[]{String.valueOf(sid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

    /*Student Queries*/
    protected String insertStudent(String professorName,String professorEmail,
                                     String professorPassword,int professorMobile,int bid) {
        try {
            if (!professorName.isEmpty() && !professorEmail.isEmpty() && !professorPassword.isEmpty()) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", professorName);
                cv.put("email", professorEmail);
                cv.put("password", professorPassword);
                cv.put("mobileno", professorMobile);
                cv.put("bid", bid);

                float insert = db.insert("staff", null, cv);
                if (insert == -1) {
                    return "Failed to Add Professor";
                } else {
                    return "Professor Added Successfully!";
                }
            } else {
                return "Not Inserted!";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected Cursor getAllStudent() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM student ORDER BY sid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected boolean isStudentAlready(String professorEmail) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email FROM staff";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(professorEmail)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    protected int studentCount(String email) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email FROM staff WHERE email='" + email + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateStudent(int sid,String professorName,String professorEmail,
                                     String professorPassword,int professorMobile,int bid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", professorName);
        cv.put("email", professorEmail);
        cv.put("password", professorPassword);
        cv.put("mobileno", professorMobile);
        cv.put("bid", bid);

        if (studentCount(professorEmail) > 1) {
            return "Professor already exists";
        } else {
            int result = db.update("staff", cv, "sid" + "=?", new String[]{String.valueOf(sid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteStudent(int sid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("staff", "sid=?", new String[]{String.valueOf(sid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

}