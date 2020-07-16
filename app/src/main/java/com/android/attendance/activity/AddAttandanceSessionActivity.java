package com.android.attendance.activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.AttendanceSessionBean;
import com.android.attendance.bean.FacultyBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddAttandanceSessionActivity<AddAttandanceActivity> extends Activity {

    private ImageButton date;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;
    private EditText dateEditText;
    Button add_attendance;
    Button viewAttendance;
    Button viewTotalAttendance;
    Spinner spinnerbranch, spinneryear, spinnerSubject;
    String branch = "cse";
    String studentSemester = " ";
    String subject = " ";

    String SelectedBranch = "";
    String SelectedSemester = "";


    DBAdapter dbAdapter;

    Boolean flag = false;
    ArrayAdapter<String> adapter_subject;
    private String[] branchString = new String[]{"cse", "ETC", "Mechanical", "civil"};
    private String[] semesterStrings = new String[]{"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
    private String[] subjectSEString = new String[]{"SC", "MC"};
    private String[] subjectTEString = new String[]{"GT", "CN"};
    private String[] subjectBEString = new String[]{"DS", "NS"};

    private String[] subjectFinal = new String[]{"DAA", "DBE", "P S", "SCLD", "SCLD LAB", "DBE LAB", "SCLD LAB"};
    AttendanceSessionBean attendanceSessionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_attandance);


        //Assume subject will be SE
        //subjectFinal = subjectSEString;

        spinnerbranch = (Spinner) findViewById(R.id.spinner1);
        spinneryear = (Spinner) findViewById(R.id.spinneryear);
        spinnerSubject = (Spinner) findViewById(R.id.spinnerSE);

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbranch.setAdapter(adapter_branch);
        spinnerbranch.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
//				((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch = (String) spinnerbranch.getSelectedItem();
                SelectedBranch = branch.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ///......................spinner2
        ArrayAdapter<String> adapter_semester = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesterStrings);
        adapter_semester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter_semester);
        spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                studentSemester = (String) spinneryear.getSelectedItem();
                SelectedSemester = studentSemester.toString();
                Toast.makeText(getApplicationContext(), "semester:" + studentSemester, Toast.LENGTH_SHORT).show();
                flag = true;

                fillTheSubjectSpinner();

				/*if(semester.equalsIgnoreCase("se"))
				{
					subjectFinal = subjectSEString;
				}
				else if(semester.equalsIgnoreCase("te"))
				{
					subjectFinal = subjectTEString;
				}
				else if(semester.equalsIgnoreCase("be"))
				{
					subjectFinal = subjectBEString;
				}*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        if (flag == false) {
            ArrayAdapter<String> adapter_subject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectFinal);
            adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubject.setAdapter(adapter_subject);
        } else {

        }


        spinnerSubject.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (spinnerSubject != null) {

                    subject = (String) spinnerSubject.getSelectedItem();
                } else {
                    Toast.makeText(AddAttandanceSessionActivity.this, "Please Select the subject", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        date = (ImageButton) findViewById(R.id.DateImageButton);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);


        dateEditText = (EditText) findViewById(R.id.DateEditText);
        date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(0);

            }
        });

        add_attendance = (Button) findViewById(R.id.button_add_attendance);
        add_attendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (dateEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AddAttandanceSessionActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                } else if (subject.trim().isEmpty()) {
                    Toast.makeText(AddAttandanceSessionActivity.this, "Please Select the subject", Toast.LENGTH_SHORT).show();
                } else {

                    AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                    FacultyBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

                    attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                    attendanceSessionBean.setAttendance_session_department(branch);
                    attendanceSessionBean.setAttendance_session_class(studentSemester);
                    attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
                    attendanceSessionBean.setAttendance_session_subject(subject);

                    DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);
                    int sessionId = dbAdapter.addAttendanceSession(attendanceSessionBean);

                    ArrayList<StudentBean> studentBeanList = dbAdapter.getAllStudentByBranchYear(branch, studentSemester);
                    ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setStudentBeanList(studentBeanList);


                    Intent intent = new Intent(AddAttandanceSessionActivity.this, AddAttendanceActivity.class);
                    intent.putExtra("sessionId", sessionId);
                    intent.putExtra("today_date", dateEditText.getText().toString());
                    intent.putExtra("current_subject", subject);

                    startActivity(intent);
                }
            }
        });

        //view attendance by date
        viewAttendance = (Button) findViewById(R.id.viewAttendancebutton);
        viewAttendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                FacultyBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(studentSemester);
                attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AddAttandanceSessionActivity.this, ViewAttendancePerStudentActivity.class);
                startActivity(intent);

            }
        });

        viewTotalAttendance = (Button) findViewById(R.id.viewTotalAttendanceButton);
        viewTotalAttendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                FacultyBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getFacultyBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(studentSemester);
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getTotalAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AddAttandanceSessionActivity.this, ViewAttendanceByFacultyActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateEditText.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);
        }
    };

    public void fillTheSubjectSpinner() {
        dbAdapter = new DBAdapter(this);
        ArrayList<String> subjects = dbAdapter.getAllSubjectsForBranchAndSemester(SelectedBranch, SelectedSemester.substring(0, 1));
        ArrayAdapter<String> adapter_subject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);

        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapter_subject);
    }

}
