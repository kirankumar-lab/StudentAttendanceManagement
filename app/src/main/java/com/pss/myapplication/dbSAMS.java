package com.pss.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
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

    //take_subject table
    private final String takeSubjectTable = "CREATE TABLE take_subject(" +
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
    //end take_subject table

    //student table
    private final String studentTable = "CREATE TABLE student(" +
            "stid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "mobileno TEXT NOT NULL," +
            "parent_email TEXT NOT NULL," +
            "parent_mobileno TEXT NOT NULL," +
            "semester INTEGER NOT NULL," +
            "bid INTEGER NOT NULL," +
            "btid INTEGER NOT NULL," +
            "FOREIGN KEY(bid) REFERENCES branch(bid)," +
            "FOREIGN KEY(btid) REFERENCES batch(btid)" +
            ");";
    //end student table

    //attendance table
    private final String attendance_detailTable = "CREATE TABLE attendance_detial(" +
            "adid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tsid INTEGER NOT NULL," +
            "semester INTEGER NOT NULL," +
            "slot INTEGER NOT NULL," +
            "date DATE NOT NULL," +
            "descript TEXT NOT NULL," +
            "FOREIGN KEY(tsid) REFERENCES take_subject(tsid)" +
            ");";
    private final String attendanceTable = "CREATE TABLE attendance(" +
            "atid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "stid INTEGER NOT NULL," +
            "attend BOOLEAN NOT NULL," +
            "FOREIGN KEY(adid) REFERENCES attendance_detial(adid)" +
            ");";

    //end attendance table

    public dbSAMS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(adminTable);
        db.execSQL("INSERT INTO admin(name,email,password,mobileno) VALUES('admin','admin@admin" +
                ".com','admin123',9876543210)");
        db.execSQL(branchTable);
        db.execSQL(batchTable);
        db.execSQL(subjectTable);
        db.execSQL(lectureTable);
        db.execSQL(staffTable);
        db.execSQL(takeSubjectTable);
        db.execSQL(studentTable);
        //db.execSQL("drop table IF EXISTS attendance");
        db.execSQL(attendance_detailTable);
        db.execSQL(attendanceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS branch");
        db.execSQL("DROP TABLE IF EXISTS batch");
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS lecture");
        db.execSQL("DROP TABLE IF EXISTS staff");
        db.execSQL("DROP TABLE IF EXISTS take_subject");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS attendance_detail");
        onCreate(db);
    }

    protected Cursor getAllAdmin() {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM admin ORDER BY sid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
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
        String q = "SELECT * FROM batch ORDER BY btid";//remove ASC
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

    protected String getBatchID(String batchName) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM batch WHERE batch_name='" + batchName + "'";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(batchName)) {
                    q = cursor.getString(0).trim().toString();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

//    protected String getBatchName(int batchID)  {
//        SQLiteDatabase db = getWritableDatabase();
//        try {
//            String q = "SELECT * FROM batch WHERE btid =" + batchID + "";
//            Cursor cursor = db.rawQuery(q, null);
//
//            while (cursor.moveToNext()) {
//                if (cursor.getString(0).equals(batchID)) {
//                    q = cursor.getString(1).trim();
//                    break;
//                }
//            }
//            return q;
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }

    protected String getBatchName(int batchID) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM batch WHERE btid=" + batchID;
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(String.valueOf(batchID))) {
                    q = cursor.getString(1).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    /*Batch End Queries*/


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
        String q = "SELECT * FROM branch ORDER BY bid";//remove DESC
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
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
                if (cursor.getString(0).equals(String.valueOf(branchID))) {
                    q = cursor.getString(1).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    /*Branch End Queries*/


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

    protected Cursor getAllSubjectOfBranch(String prof_branch_id) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM subject WHERE bid = '" + prof_branch_id + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected String getSubjectID(String subject_name) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM subject WHERE subject_name ='" + subject_name + "'";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(subject_name)) {
                    q = cursor.getString(0).trim().toString();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected boolean isSubjectAlready(String subject, String bid) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  subject_name,bid FROM subject";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(subject) && cursor.getString(1).equals(getBranchID(bid))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int subjectCount(String subject, String subjectBranch) {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT  subject_name FROM subject WHERE subject_name='" + subject + "' AND bid=" + getBranchID(subjectBranch);
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateSubject(int sbid, String subjectName, int bid, int subjectSemester) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject_name", subjectName);
        cv.put("semester", subjectSemester);
        cv.put("bid", bid);

        if (isSubjectAlready(subjectName, getBranchName(bid))) {
            return "Subject name already exists";
        } else {
            int result = db.update("subject", cv, "sbid" + "=? and bid" + "=?",
                    new String[]{String.valueOf(sbid), String.valueOf(bid)});
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
    /*Subject End Queries*/

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
    /*Lecture End Queries*/


    /*Professor Queries*/
    protected String insertProfessor(String professorName, String professorEmail,
                                     String professorPassword, String professorMobile, int bid) {
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

    protected Cursor getProfDetailsById(String prof_id) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM staff WHERE email =  '" + prof_id + "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected String getProfessorId(String prof_id) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT sid FROM staff WHERE email =  '" + prof_id + "'";
        Cursor cursor = db.rawQuery(q, null);
        String sid = null;
        while (cursor.moveToNext()) {
            sid = cursor.getString(0);
        }
        return sid;
    }

    protected String getProfessorName(int sid) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT name FROM staff WHERE sid=" + sid;
        Cursor cursor = db.rawQuery(q, null);
        String name = null;
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        return name;
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

    protected String updateProfessor(int sid, String professorName, String professorEmail,
                                     String professorPassword, String professorMobile, int bid) {
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
    /*Professor  Queries*/


    /*Student Queries*/
    protected String insertStudent(String name, String email, String mobileno, String emailp, String mobilenop, String password, int btid, int bid, int semester) {
        try {
            if (!name.isEmpty() && !email.isEmpty() && !mobileno.isEmpty() && !emailp.isEmpty() && !mobilenop.isEmpty() && !password.isEmpty()) {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name", name);
                cv.put("email", email);
                cv.put("mobileno", mobileno);
                cv.put("parent_email", emailp);
                cv.put("parent_mobileno", mobilenop);
                cv.put("password", password);
                cv.put("bid", bid);
                cv.put("btid", btid);
                cv.put("semester", semester);

                float insert = db.insert("student", null, cv);
                if (insert == -1) {
                    return "Failed to Add Student";
                } else {
                    return "Student Added Successfully!";
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
        String q = "SELECT * FROM student ORDER BY stid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected boolean isStudentAlready(String email, String mobileno) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email,mobileno FROM student";
        Cursor cursor = db.rawQuery(q, null);

        boolean flag = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(email) || cursor.getString(1).equals(mobileno)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    protected int studentCount(String email, String mobileno) {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT  email FROM staff WHERE email='" + email + "' OR mobileno='" + mobileno +
                "'";
        Cursor cursor = db.rawQuery(q, null);
        return cursor.getCount();
    }

    protected String updateStudent(int stid, String name, String email, String mobileno, String emailp, String mobilenop, String password, int btid, int bid, int semester) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("mobileno", mobileno);
        cv.put("parent_email", emailp);
        cv.put("parent_mobileno", mobilenop);
        cv.put("password", password);
        cv.put("bid", bid);
        cv.put("btid", btid);
        cv.put("semester", semester);

        if (studentCount(email, mobileno) > 1) {
            return "Student already exists";
        } else {
            int result = db.update("student", cv, "stid" + "=?",
                    new String[]{String.valueOf(stid)});
            if (result == 0) {
                return "Failed to Update";
            } else {
                return "Updated Successfully";
            }
        }
    }

    protected String deleteStudent(int stid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("student", "stid=?", new String[]{String.valueOf(stid)});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

    public Cursor getAllStudentList(String bid, String btid) {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT * FROM student WHERE bid=" + getBranchID(bid) + " AND btid=" + getBatchID(btid) +
                        " ORDER " +
                        "BY stid";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    // get password for admin method
    protected String getPasswordForAdmin(String email) {
        String password = "";
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM admin WHERE email ='" + email + "'";

        Cursor r = db.rawQuery(q, null);
        while (r.moveToNext()) {
            password = r.getString(3);
        }

        return password;
    }

    // get password for Professor method
    protected String getPasswordForProfessor(String email) {
        String password = "";
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM staff WHERE email ='" + email + "'";

        Cursor r = db.rawQuery(q, null);
        while (r.moveToNext()) {
            password = r.getString(3);
        }

        return password;
    }

    // get password for Professor method
    protected String getPasswordForStudent(String email) {
        String password = "";
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM student WHERE email ='" + email + "'";

        Cursor r = db.rawQuery(q, null);
        while (r.moveToNext()) {
            password = r.getString(3);
        }

        return password;
    }

    protected boolean checkTakeSubject(int bid, int btid, int sid, int sbid, int lid) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM take_subject WHERE bid=" + bid + " AND btid=" + btid +
                " AND " +
                "sid=" + sid + " AND sbid=" + sbid + " AND lid=" + lid, null);
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }


    protected String insertTakeSubject(int bid, int btid, int sid, int sbid, int lid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bid", bid);
        cv.put("btid", btid);
        cv.put("sid", sid);
        cv.put("sbid", sbid);
        cv.put("lid", lid);
        if (checkTakeSubject(bid, btid, sid, sbid, lid)) {
            float insert = db.insert("take_subject", null, cv);
            if (insert == -1) {
                return "Failed to Take Subject";
            } else {
                return "Take Subject Successfully!";
            }
        } else {
            return "This Subject Already Taken!";
        }

    }

    protected String getTakeSubjectName(String sbid) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM subject WHERE sbid=" + sbid + "";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(sbid)) {
                    q = cursor.getString(1).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected String getTakeSubjectSemester(String sbid) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT * FROM subject WHERE sbid=" + sbid + "";
            Cursor cursor = db.rawQuery(q, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(sbid)) {
                    q = cursor.getString(2).trim();
                    break;
                }
            }
            return q;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }


    protected String deleteTakeSubject(String tsid) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete("take_subject", "tsid=?", new String[]{tsid});
        if (result == 0) {
            return "Failed to Delete";
        } else {
            return "Deleted Successfully";
        }
    }

    protected Cursor getTakeSubject(String sid) {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT * FROM take_subject WHERE sid=" + getProfessorId(sid) +
                        " ORDER " +
                        "BY tsid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected Cursor getTakeSubjectBySID(String sid) {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT DISTINCT(btid) as btid FROM take_subject WHERE sid=" + getProfessorId(sid) +
                        " ORDER " +
                        "BY tsid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected Cursor getTakeSubjectAll() {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT * FROM take_subject ORDER " +
                        "BY tsid DESC";
        Cursor cursor = db.rawQuery(q, null);
        return cursor;
    }

    protected Cursor getTakeSubjectByLecture(String lecture, String batch, String sid, int sem) {
        SQLiteDatabase db = getWritableDatabase();
        String q =
                "SELECT * FROM take_subject join subject on take_subject.sbid = subject.sbid " +
                        "Where take_subject.lid=" + getLectureID(lecture) + " " +
                        "AND " +
                        "take_subject.btid=" + getBatchID(batch) + " AND take_subject.sid=" + getProfessorId(sid) + " AND subject.semester =" + sem;
        try {
            Cursor cursor = db.rawQuery(q, null);
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }

    protected int getTakeSubjectID(int btid, int bid, int lid, int sbid, int sid) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT tsid FROM take_subject WHERE btid=" + btid + " and " +
                    "bid=" + bid + " and " +
                    "lid=" + lid + " and " +
                    " sbid=" + sbid + " and " +
                    " sid=" + sid, null);
            int tsid = 0;
            while (cursor.moveToNext()) {
                tsid = cursor.getInt(0);
            }
            return tsid;
        } catch (Exception ex) {
            return 0;
        }

    }

    protected int getBranchIDBySID(String sid) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT bid FROM staff WHERE email='" + sid + "'", null);
            int bid = 0;
            while (cursor.moveToNext()) {
                bid = cursor.getInt(0);
            }
            return bid;
        } catch (Exception ex) {
            return 0;
        }
    }

    protected Cursor getStudentListByTakeAttend(int tsid) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String q = "SELECT stid,name FROM student join take_subject on student" +
                    ".btid=take_subject.btid" +
                    " and student.bid=take_subject.bid WHERE take_subject.tsid=" + tsid;
            Cursor cursor = db.rawQuery(q, null);

            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }
}