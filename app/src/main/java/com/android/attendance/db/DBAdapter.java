package com.android.attendance.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.AttendanceSessionBean;
import com.android.attendance.bean.FacultyBean;
import com.android.attendance.bean.StudentBean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter extends SQLiteOpenHelper {

    Context context;
    int TOTAL_STRENGTH_COUNT = 0;
    int TOTAL_ACTUAL_STRENGTH = 0;
    ArrayList<String> subjects = new ArrayList<>();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Attendance";

    // Contacts table name
    private static final String FACULTY_INFO_TABLE = "faculty_table";
    private static final String STUDENT_INFO_TABLE = "student_table";
    private static final String ATTENDANCE_SESSION_TABLE = "attendance_session_table";
    private static final String ATTENDANCE_TABLE = "attendance_table";
    private static final String STUDENT_SUBJECT_TABLE_NAME = "subjects";


    // Contacts Table Columns names
    private static final String KEY_FACULTY_ID = "faculty_id";
    private static final String KEY_FACULTY_FIRSTNAME = "faculty_firstname";
    private static final String KEY_FACULTY_LASTNAME = "faculty_Lastname";
    private static final String KEY_FACULTY_MO_NO = "faculty_mobilenumber";
    private static final String KEY_FACULTY_ADDRESS = "faculty_address";
    private static final String KEY_FACULTY_USERNAME = "faculty_username";
    private static final String KEY_FACULTY_PASSWORD = "faculty_password";

    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_FIRSTNAME = "student_firstname";
    private static final String KEY_STUDENT_LASTNAME = "student_lastname";
    private static final String KEY_STUDENT_MO_NO = "student_mobilenumber";
    private static final String KEY_STUDENT_ADDRESS = "student_address";
    private static final String KEY_STUDENT_DEPARTMENT = "student_department";
    private static final String KEY_STUDENT_CLASS = "student_class";

    private static final String KEY_ATTENDANCE_SESSION_ID = "attendance_session_id";
    private static final String KEY_ATTENDANCE_SESSION_FACULTY_ID = "attendance_session_faculty_id";
    private static final String KEY_ATTENDANCE_SESSION_DEPARTMENT = "attendance_session_department";
    private static final String KEY_ATTENDANCE_SESSION_CLASS = "attendance_session_class";
    private static final String KEY_ATTENDANCE_SESSION_DATE = "attendance_session_date";
    private static final String KEY_ATTENDANCE_SESSION_SUBJECT = "attendance_session_subject";

    public static final String KEY_SESSION_ID = "attendance_session_id";
    public static final String KEY_ATTENDANCE_STUDENT_ID = "attendance_student_id";
    public static final String KEY_ATTENDANCE_STATUS = "attendance_status";
    public static final String KEY_DATE = "attendance_date";
    public static final String KEY_SUBJECT = "attendance_subject";


    private static final String STUDENT_SUBJECT_ID = "student_subject_id";
    private static final String STUDENT_SUBJECT = "student_subject";
    private static final String STUDENT_SEMESTER = "student_semester";
    private static final String STUDENT_BRANCH = "student_branch";


    public DBAdapter(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    @Override

    public void onCreate(SQLiteDatabase db) {
//
//		String queryStudentSubject="CREATE TABLE "+STUDENT_SUBJECT_TABLE_NAME+" ("+
//				STUDENT_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
//				STUDENT_SUBJECT + " TEXT,"+
//				STUDENT_SEMESTER + " TEXT,"+
//				STUDENT_BRANCH + " TEXT"+" )";
        String queryStudentSubject = "CREATE TABLE subjects (student_subject_id INTEGER PRIMARY KEY AUTOINCREMENT, student_subject TEXT," +
                "student_semester TEXT,student_branch TEXT); ";


        String queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_FIRSTNAME + " TEXT, " +
                KEY_FACULTY_LASTNAME + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_USERNAME + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")";
        Log.d("queryFaculty", queryFaculty);


        String queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_FIRSTNAME + " TEXT, " +
                KEY_STUDENT_LASTNAME + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DEPARTMENT + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")";
        Log.d("queryStudent", queryStudent);


        String queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER, " +
                KEY_ATTENDANCE_SESSION_DEPARTMENT + " TEXT, " +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " DATE," +
                KEY_ATTENDANCE_SESSION_SUBJECT + " TEXT" + ")";
        Log.d("queryAttendanceSession", queryAttendanceSession);


        String queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_SUBJECT + " TEXT" + " );";
        Log.d("queryAttendance", queryAttendance);


        try {
            db.execSQL(queryStudentSubject);
            db.execSQL(queryFaculty);
            db.execSQL(queryStudent);
            db.execSQL(queryAttendanceSession);
            db.execSQL(queryAttendance);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

//		String queryStudentSubject="CREATE TABLE "+STUDENT_SUBJECT_TABLE_NAME+" ("+
//				STUDENT_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
//				STUDENT_SUBJECT + " TEXT, "+
//				STUDENT_SEMESTER + " TEXT, "+
//				STUDENT_BRANCH + " TEXT"+" )";

        String queryStudentSubject = "CREATE TABLE subjects (student_subject_id INTEGER PRIMARY KEY AUTOINCREMENT, student_subject TEXT," +
                "student_semester TEXT,student_branch TEXT); ";


        String queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_FIRSTNAME + " TEXT, " +
                KEY_FACULTY_LASTNAME + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_USERNAME + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")";
        Log.d("queryFaculty", queryFaculty);


        String queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_FIRSTNAME + " TEXT, " +
                KEY_STUDENT_LASTNAME + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DEPARTMENT + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")";
        Log.d("queryStudent", queryStudent);


        String queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER, " +
                KEY_ATTENDANCE_SESSION_DEPARTMENT + " TEXT, " +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " TEXT," +
                KEY_ATTENDANCE_SESSION_SUBJECT + " TEXT" + ")";
        Log.d("queryAttendanceSession", queryAttendanceSession);


        String queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_SUBJECT + "TEXT" + " );";
        Log.d("queryAttendance", queryAttendance);

        try {
            db.execSQL(queryFaculty);
            db.execSQL(queryStudent);
            db.execSQL(queryAttendanceSession);
            db.execSQL(queryAttendance);
            db.execSQL(queryStudentSubject);
            Log.d("created", "onUpgrade: created all tables");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    //facult crud
    public void addFaculty(FacultyBean facultyBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO faculty_table (faculty_firstname,faculty_Lastname,faculty_mobilenumber,faculty_address,faculty_username,faculty_password) values ('" +
                facultyBean.getFaculty_firstname() + "', '" +
                facultyBean.getFaculty_lastname() + "', '" +
                facultyBean.getFaculty_mobilenumber() + "', '" +
                facultyBean.getFaculty_address() + "', '" +
                facultyBean.getFaculty_username() + "', '" +
                facultyBean.getFaculty_password() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public FacultyBean validateFaculty(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM faculty_table where faculty_username='" + userName + "' and faculty_password='" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            FacultyBean facultyBean = new FacultyBean();
            facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
            facultyBean.setFaculty_firstname(cursor.getString(1));
            facultyBean.setFaculty_lastname(cursor.getString(2));
            facultyBean.setFaculty_mobilenumber(cursor.getString(3));
            facultyBean.setFaculty_address(cursor.getString(4));
            facultyBean.setFaculty_username(cursor.getString(5));
            facultyBean.setFaculty_password(cursor.getString(6));
            return facultyBean;
        }
        return null;
    }

    public ArrayList<FacultyBean> getAllFaculty() {
        Log.d("in get all", "in get all");
        ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM faculty_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                FacultyBean facultyBean = new FacultyBean();
                facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
                facultyBean.setFaculty_firstname(cursor.getString(1));
                facultyBean.setFaculty_lastname(cursor.getString(2));
                facultyBean.setFaculty_mobilenumber(cursor.getString(3));
                facultyBean.setFaculty_address(cursor.getString(4));
                facultyBean.setFaculty_username(cursor.getString(5));
                facultyBean.setFaculty_password(cursor.getString(6));
                list.add(facultyBean);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteFaculty(int facultyId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM faculty_table WHERE faculty_id=" + facultyId;

        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    //student crud
    public void addStudent(StudentBean studentBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO student_table (student_firstname,student_lastname,student_mobilenumber,student_address,student_department,student_class) values ('" +
                studentBean.getStudent_firstname() + "', '" +
                studentBean.getStudent_lastname() + "','" +
                studentBean.getStudent_mobilenumber() + "', '" +
                studentBean.getStudent_address() + "', '" +
                studentBean.getStudent_department() + "', '" +
                studentBean.getStudent_class() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public ArrayList<StudentBean> getAllStudent() {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_firstname(cursor.getString(1));
                studentBean.setStudent_lastname(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_department(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<StudentBean> getAllStudentByBranchYear(String branch, String year) {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table where student_department='" + branch + "' and student_class='" + year + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_firstname(cursor.getString(1));
                studentBean.setStudent_lastname(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_department(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public StudentBean getStudentById(int studentId) {
        StudentBean studentBean = new StudentBean();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table where student_id=" + studentId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_firstname(cursor.getString(1));
                studentBean.setStudent_lastname(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_department(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));

            } while (cursor.moveToNext());
        }
        return studentBean;
    }

    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM student_table WHERE student_id=" + studentId;

        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    //attendance session Table crud
    public int addAttendanceSession(AttendanceSessionBean attendanceSessionBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO attendance_session_table (attendance_session_faculty_id,attendance_session_department,attendance_session_class,attendance_session_date,attendance_session_subject) values ('" +
                attendanceSessionBean.getAttendance_session_faculty_id() + "', '" +
                attendanceSessionBean.getAttendance_session_department() + "','" +
                attendanceSessionBean.getAttendance_session_class() + "', '" +
                attendanceSessionBean.getAttendance_session_date() + "', '" +
                attendanceSessionBean.getAttendance_session_subject() + "')";
        Log.d("query", query);
        db.execSQL(query);

        String query1 = "select max(attendance_session_id) from attendance_session_table";
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            int sessionId = Integer.parseInt(cursor.getString(0));

            return sessionId;
        }


        db.close();
        return 0;
    }

    public ArrayList<AttendanceSessionBean> getAllAttendanceSession() {
        ArrayList<AttendanceSessionBean> list = new ArrayList<AttendanceSessionBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                attendanceSessionBean.setAttendance_session_id(Integer.parseInt(cursor.getString(0)));
                attendanceSessionBean.setAttendance_session_faculty_id(Integer.parseInt(cursor.getString(1)));
                attendanceSessionBean.setAttendance_session_department(cursor.getString(2));
                attendanceSessionBean.setAttendance_session_class(cursor.getString(3));
                attendanceSessionBean.setAttendance_session_date(cursor.getString(4));
                attendanceSessionBean.setAttendance_session_subject(cursor.getString(5));
                list.add(attendanceSessionBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteAttendanceSession(int attendanceSessionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM attendance_session_table WHERE attendance_session_id=" + attendanceSessionId;

        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    //attendance crud
    public void addNewAttendance(AttendanceBean attendanceBean, String Date,String Subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO attendance_table values (" +
                attendanceBean.getAttendance_session_id() + ", " +
                attendanceBean.getAttendance_student_id() + ", '" +
                attendanceBean.getAttendance_status() + "'," + " '" + Date + "', "+
                "'"+Subject+"'"+
                " );";
        Log.d("query", query);
        db.execSQL(query);
        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        db.close();
    }


    public ArrayList<AttendanceBean> getAttendanceBySessionID(AttendanceSessionBean attendanceSessionBean) {
        int attendanceSessionId = 0;
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.getAttendance_session_faculty_id() + ""
                + " AND attendance_session_department='" + attendanceSessionBean.getAttendance_session_department() + "' AND attendance_session_class='" + attendanceSessionBean.getAttendance_session_class() + "'" +
                " AND attendance_session_date='" + attendanceSessionBean.getAttendance_session_date() + "' AND attendance_session_subject='" + attendanceSessionBean.getAttendance_session_subject() + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = (Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        String query1 = "SELECT * FROM attendance_table where attendance_session_id=" + attendanceSessionId + " order by attendance_student_id";
        Cursor cursor1 = db.rawQuery(query1, null);
        if (cursor1.moveToFirst()) {
            do {
                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_session_id(Integer.parseInt(cursor1.getString(0)));
                attendanceBean.setAttendance_student_id(Integer.parseInt(cursor1.getString(1)));
                attendanceBean.setAttendance_status(cursor1.getString(2));
                list.add(attendanceBean);

            } while (cursor1.moveToNext());
        }
        return list;
    }

    public ArrayList<AttendanceBean> getTotalAttendanceBySessionID(AttendanceSessionBean attendanceSessionBean) {
        int attendanceSessionId = 0;
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.getAttendance_session_faculty_id() + ""
                + " AND attendance_session_department='" + attendanceSessionBean.getAttendance_session_department() + "' AND attendance_session_class='" + attendanceSessionBean.getAttendance_session_class() + "'" +
                " AND attendance_session_subject='" + attendanceSessionBean.getAttendance_session_subject() + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = (Integer.parseInt(cursor.getString(0)));

                String query1 = "SELECT * FROM attendance_table where attendance_session_id=" + attendanceSessionId + " order by attendance_student_id";
                Cursor cursor1 = db.rawQuery(query1, null);
                if (cursor1.moveToFirst()) {
                    do {
                        AttendanceBean attendanceBean = new AttendanceBean();
                        attendanceBean.setAttendance_session_id(Integer.parseInt(cursor1.getString(0)));
                        attendanceBean.setAttendance_student_id(Integer.parseInt(cursor1.getString(1)));
                        attendanceBean.setAttendance_status(cursor1.getString(2));
                        list.add(attendanceBean);

                    } while (cursor1.moveToNext());
                }

                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_session_id(0);
                attendanceBean.setAttendance_status("Date : " + cursor.getString(4));
                list.add(attendanceBean);

            } while (cursor.moveToNext());
        }


        return list;
    }

    public ArrayList<AttendanceBean> getAllAttendanceByStudent() {
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT attendance_student_id,count(*) FROM attendance_table where attendance_status='P' group by attendance_student_id";

        Log.d("query", query);

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                Log.d("studentId", "studentId:" + cursor.getString(0) + ", Count:" + cursor.getString(1));
                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_student_id(Integer.parseInt(cursor.getString(0)));
                attendanceBean.setAttendance_session_id(Integer.parseInt(cursor.getString(1)));
                list.add(attendanceBean);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void enterStudentSubject(String subject, String semester, String branch) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String queryToEnterSubject = " INSERT INTO " + STUDENT_SUBJECT_TABLE_NAME + " (student_subject,student_semester,student_branch)" +

                " values (" +
                "'" +
                subject + "'" + "," + "'" + semester + "'" + "," + "'" + branch + "'" + ");";


        sqLiteDatabase.execSQL(queryToEnterSubject);
        sqLiteDatabase.close();

    }


    public ArrayList<String> getAllSubjectsForBranchAndSemester(String branch, String semester) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String getSubjectsQuery = " SELECT " + STUDENT_SUBJECT + " FROM " + STUDENT_SUBJECT_TABLE_NAME + " WHERE " +
                STUDENT_BRANCH + "=" + "'" + branch + "'" + " AND " + STUDENT_SEMESTER + "=" + "'" + semester + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getSubjectsQuery, null);
        if (cursor.moveToFirst()) {
            Toast.makeText(context, "" + cursor.getString(0), Toast.LENGTH_SHORT).show();
            do {
                subjects.add(cursor.getString(0).toString());
                Log.d("values", "getAllSubjectsForBranchAndSemester: " + cursor.getString(cursor.getColumnIndex(STUDENT_SUBJECT)));
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return subjects;
    }

    public ArrayList<String> getAttendanceByDate(String Date, String subject) {

        TOTAL_STRENGTH_COUNT = 0;
        TOTAL_ACTUAL_STRENGTH = 0;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String queryForDateAttendance = "SELECT * FROM " + ATTENDANCE_TABLE + " WHERE " + KEY_SUBJECT + "=" + "'" + subject + "'" + "AND " +
                KEY_DATE + "=" + "'" + Date + "' " + ";";

        ArrayList<String> studentAttendances = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(queryForDateAttendance, null);
        if (cursor.moveToFirst()) {
            do {
//				studentAttendances.add(cursor.getString(0));

//                studentAttendances.add(getStudentBydate(sqLiteDatabase, cursor.getString(1)));
                studentAttendances.add(getStudentInfoByDate(sqLiteDatabase,cursor.getString(1))+"  \t\t\t\t"+getStudentBydate(sqLiteDatabase,cursor.getString(1),Date,subject));
                Log.d("studentID", "getAttendanceByDate: "+cursor.getString(1));
            } while (cursor.moveToNext());
        }
//		Toast.makeText(context, ""+studentAttendances, Toast.LENGTH_SHORT).show();

//
//		String query = "INSERT INTO attendance_table values ("+
//				attendanceBean.getAttendance_session_id()+", "+
//				attendanceBean.getAttendance_student_id()+", '"+
//				attendanceBean.getAttendance_status()+"')";
//		Log.d("query", query);
//		db.execSQL(query);
//		db.close();
        sqLiteDatabase.close();
        return studentAttendances;
    }


    public String getStudentBydate(SQLiteDatabase sqLiteDatabase, String session_id,String date,String subject) {

        String s = "";
        String query = "SELECT * FROM attendance_table WHERE " + KEY_ATTENDANCE_STUDENT_ID + "=" + Integer.parseInt(session_id) + " AND "+KEY_DATE+"='"+date+"'"+" AND "+

               KEY_SUBJECT+"='"+subject+"' " + ";";
        Cursor cursor1 = sqLiteDatabase.rawQuery(query, null);
        if (cursor1.moveToFirst()) {
            do {
                s =  cursor1.getString(2);
                Log.d("status", "getStudentBydate: " + cursor1.getString(2));
                if (cursor1.getString(2).equals("P")) {
                    TOTAL_STRENGTH_COUNT = TOTAL_STRENGTH_COUNT + 1;
                }
                    TOTAL_ACTUAL_STRENGTH = TOTAL_ACTUAL_STRENGTH+ 1;
                Log.d("studentid", "getStudentBydate: " + cursor1.getString(1));
            } while (cursor1.moveToNext());
        }


        return s;

    }


    public String getStudentInfoByDate(SQLiteDatabase sqLiteDatabase, String id) {
        String s = "";
        String query = "SELECT * FROM " + STUDENT_INFO_TABLE + " WHERE " + KEY_STUDENT_ID + "=" + Integer.parseInt(id) + " ;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {

                s = cursor.getString(1) + " : " + cursor.getString(2);
                Log.d("important", "getStudentInfoByDate: "+s);



        }
        return s;

    }

    public String total_present() {
        String Strength_information = TOTAL_STRENGTH_COUNT + " / " + TOTAL_ACTUAL_STRENGTH;
        return Strength_information;
    }


    public boolean checkAttendanceStatus(int id, String date, int studentid, String subject) {
        Boolean flag = false;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String queryForCheckingAttendanceStatus = "SELECT " + KEY_ATTENDANCE_STATUS + " FROM attendance_table WHERE " + KEY_ATTENDANCE_STUDENT_ID + "=" +
                id + " ;";
        Cursor cursor = sqLiteDatabase.rawQuery(queryForCheckingAttendanceStatus, null);
        if (cursor.moveToFirst()) {
            do {
                if (!cursor.getString(0).isEmpty() && checkDate(sqLiteDatabase, date, studentid, subject)) {
                    flag = true;
                } else {
                    flag = false;
                }

            } while (cursor.moveToNext());
        }


        return flag;
    }

    public boolean checkDate(SQLiteDatabase sqLiteDatabase, String date, int studentid, String subject) {
        Boolean flag = false;
        String DATE;
        String SUBJECT;
        String queryToCheckdate = "SELECT " + KEY_DATE + "," + KEY_SUBJECT + " FROM  " + ATTENDANCE_TABLE + " where " + KEY_ATTENDANCE_STUDENT_ID + "=" + studentid + " ;";
        Cursor cursor = sqLiteDatabase.rawQuery(queryToCheckdate, null);
        if (cursor.moveToFirst()) {
            do {
                DATE = cursor.getString(0);
                SUBJECT = cursor.getString(1);
                Log.d("DATE_FOR_CHECK", "checkDate: " + DATE + "    " + date);

                if (DATE.trim().equals(date) && SUBJECT.trim().equals(subject)) {
                    Log.d("DATESUB_1", "checkDate2: " + DATE + " " + date + " sub:" + SUBJECT + " " + subject);
                    flag = true;
                } else {
                    Log.d("DATESUB_2", "checkDate2: " + DATE + " " + date + " sub:" + SUBJECT + " " + subject);

                }

            } while (cursor.moveToNext());
        }
        Log.d("DATEFLAG", "checkDate2: " + flag);
        return flag;
    }

    public String getStatus(String date,String subject,int id)
    {
        String status="null";
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String seeStatusQuery="SELECT "+KEY_ATTENDANCE_STATUS+" FROM attendance_table WHERE "+KEY_DATE+"='"+date+"' "+"AND "+KEY_SUBJECT+"='"+subject+"' "+
                "AND "+KEY_ATTENDANCE_STUDENT_ID+"="+id+" ;";
        Cursor cursor=sqLiteDatabase.rawQuery(seeStatusQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                status=cursor.getString(0);
            }while (cursor.moveToNext());
        }

        return status;
    }


    public void  updateAttendance(String date,String subject,int id,String status)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String updateQuery="UPDATE attendance_table SET "+KEY_ATTENDANCE_STATUS+"='"+
                status+"' WHERE "+KEY_DATE+"='"+date+"' "+"AND "+KEY_SUBJECT+"='"+subject+"' "+
                "AND "+KEY_ATTENDANCE_STUDENT_ID+"="+id+" ;";
      sqLiteDatabase.execSQL(updateQuery);
        Toast.makeText(context, "Updated to "+status, Toast.LENGTH_SHORT).show();
    }


    public void deleteAttendance(String date,String subject)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String updateQuery="DELETE FROM attendance_table WHERE "+KEY_DATE+"='"+date+"' "+"AND "+KEY_SUBJECT+"='"+subject+"' ;";

        sqLiteDatabase.execSQL(updateQuery);
        Toast.makeText(context, "DELETED CAREFULLY", Toast.LENGTH_SHORT).show();
    }

    public String getStudentsTotalAttendanceDetails(String regNo)
    {
        int StudentId=0;

        String strings="";
       if(!strings.isEmpty())
       {
           strings="";
       }
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        String sqlForStudent="SELECT * FROM "+STUDENT_INFO_TABLE+" WHERE "
            +KEY_STUDENT_LASTNAME+"='"+regNo+"' OR " +KEY_STUDENT_FIRSTNAME+"='"+regNo+"'"+" ;";

          Cursor cursor=sqLiteDatabase.rawQuery(sqlForStudent,null);
          if(cursor.moveToFirst())
          {
              do {

                  StudentId=cursor.getInt(0);
                  strings=cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(5)+" "+cursor.getString(6);

              }while (cursor.moveToNext());
          }
       String s= getStudentSpecificAttendance(StudentId);
        return strings+" "+s;

    }



    public String getStudentSpecificAttendance(int StudentId)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        int p=0;
        int a=0;
        int TOTAL_STRENGTH=0;
        HashSet<String> set=new HashSet();
        String sql="SELECT "+KEY_ATTENDANCE_STATUS+","+KEY_SUBJECT+" FROM attendance_table WHERE "
                +KEY_ATTENDANCE_STUDENT_ID+"="+StudentId+" ;";

        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst())
        {
            do{
                TOTAL_STRENGTH+=1;
                String attendanceStatus=cursor.getString(0);
                if(attendanceStatus.equals("P"))
                {
                    p+=1;
                }
                set.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }

        return "  , total present "+p+"/"+TOTAL_STRENGTH+" for this subjects "+set;
    }
//    String queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
//            KEY_SESSION_ID + " INTEGER, " +
//            KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
//            KEY_ATTENDANCE_STATUS + " TEXT, " +
//            KEY_DATE + " TEXT, " +
//            KEY_SUBJECT + " TEXT" + " );";


//	public boolean checksubject(SQLiteDatabase sqLiteDatabase,String date,int studentid)
//	{
//
//	}


//	String queryAttendance="CREATE TABLE "+ ATTENDANCE_TABLE +" (" +
//			KEY_SESSION_ID + " INTEGER, " +
//			KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
//			KEY_ATTENDANCE_STATUS + " TEXT, " +
//			KEY_DATE+" TEXT "+");";

//	String queryStudent="CREATE TABLE "+ STUDENT_INFO_TABLE +" (" +
//			KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//			KEY_STUDENT_FIRSTNAME + " TEXT, " +
//			KEY_STUDENT_LASTNAME + " TEXT, " +
//			KEY_STUDENT_MO_NO + " TEXT, " +
//			KEY_STUDENT_ADDRESS + " TEXT," +
//			KEY_STUDENT_DEPARTMENT + " TEXT," +
//			KEY_STUDENT_CLASS + " TEXT " + ")";
//		Log.d("queryStudent",queryStudent );


	/*public ArrayList<AttendanceBean> getAllAttendanceBySessionID(int sessionId)
	{
		ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM attendance_table where attendance_session_id=" + sessionId;
		Cursor cursor = db.rawQuery(query, null);

		if(!cursor.moveToFirst()) 
		{
			do{
				AttendanceBean attendanceBean = new AttendanceBean();
				attendanceBean.setAttendance_session_id(Integer.parseInt(cursor.getString(0)));
				attendanceBean.setAttendance_student_id(Integer.parseInt(cursor.getString(1)));
				attendanceBean.setAttendance_status(cursor.getString(2));
				list.add(attendanceBean);

			}while(cursor.moveToNext());
		}
		return list;
	}*/


    // Creating Tables
	/*@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_User_Info_TABLE = "CREATE TABLE " + TABLE_INFO_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FIRSTNAME + " TEXT, "+ KEY_LASTNAME + " TEXT, " +KEY_MO_NO +" TEXT, "
				+KEY_EMAIL +" TEXT, " +KEY_USERNAME +" TEXT, " + KEY_PASSWORD +" TEXT " + ")";

		Log.d("rupali",CREATE_User_Info_TABLE );
		db.execSQL(CREATE_User_Info_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO_USER);

		// Create tables again
		onCreate(db);
	}

	 *//**
     * All CRUD(Create, Read, Update, Delete) Operations
     *//*



	void addUserInfo(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRSTNAME, userinfo.getUser_Firstname()); //  Name
		values.put(KEY_LASTNAME, userinfo.getUser_Lastname()); //  Name
		values.put(KEY_MO_NO, userinfo.getUser_MobileNo()); // Contact Phone
		values.put(KEY_EMAIL, userinfo.getUser_EmailId());
		values.put(KEY_USERNAME, userinfo.getUser_Username());
		values.put(KEY_PASSWORD, userinfo.getUser_Password());

		// Inserting Row
		db.insert(TABLE_INFO_USER, null, values);
		//2nd argument is String containing nullColumnHack
		db.close(); // Closing database connection
	}


	// Getting single contact
	UserInfo getUserInfo(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_INFO_USER, new String[] { KEY_ID,
				KEY_FIRSTNAME, KEY_LASTNAME,KEY_MO_NO,  KEY_EMAIL, KEY_USERNAME, KEY_PASSWORD }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
		// return contact
				return userinfo;
	}

	public UserInfo validateUser(String username, String password)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "Select * from User_Info_Table WHERE User_Username='"+ username +"' AND User_Password='"+password+"'";
		Log.d("Rupali", "Login QUERY:" + query);

		Cursor cursor = db.rawQuery(query, null);


		if(!cursor.moveToFirst()) 
		{
			Log.d("Rupali", "cursor is null.. returing NULL");
			return null;
		}
		Log.d("Rupali", "cursor is NOT null.. we got user data...");


		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));

		return userinfo;
	}

	// Updating single contact
	public int updateUserPassword(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PASSWORD, userinfo.getUser_Password());


		// updating row
		return db.update(TABLE_INFO_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(userinfo.getUser_id()) });
	}

	public int updateUserContact(UserInfo userinfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MO_NO, userinfo.getUser_MobileNo());
		values.put(KEY_EMAIL, userinfo.getUser_EmailId());


		// updating row
		return db.update(TABLE_INFO_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(userinfo.getUser_id()) });
	}


	//veiw details

	public UserInfo viewUserInfo(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "Select * from User_Info_Table WHERE id='"+id+"'";
		Cursor cursor = db.rawQuery(query, null);
		if(!cursor.moveToFirst()) 
		{
			Log.d("Rupali", "cursor is null.. returing NULL");
			return null;
		}
		Log.d("Rupali", "cursor is NOT null.. we got user data...");

		UserInfo userinfo = new UserInfo(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
		// return contact
		return userinfo;
	}



	 // Getting All users
    public List<UserInfo> getAllUserInfo() {
        List<UserInfo> userinfolist = new ArrayList<UserInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                UserInfo userinfo=new UserInfo();

                userinfo.setUser_id(Integer.parseInt(cursor.getString(0)));
                userinfo.setUser_Lastname(cursor.getString(2));
                userinfo.setUser_Username(cursor.getString(5));
                userinfo.setUser_Firstname(cursor.getString(1));



                // Adding contact to list
                userinfolist.add(userinfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userinfolist;
    }

    // Deleting single contact
    public void deleteUser(UserInfo userinfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(userinfo.getUser_id()) });
        db.close();
    }
	  */
}