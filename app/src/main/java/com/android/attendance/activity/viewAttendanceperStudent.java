package com.android.attendance.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class viewAttendanceperStudent extends Fragment {

    DBAdapter dbAdapter;
    TextView textView;
    EditText regitrationNo;

    Button button;

    public viewAttendanceperStudent(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.view_single_student_attendance_fragment,container,false);
        button=view.findViewById(R.id.Submit_student);
        regitrationNo=view.findViewById(R.id.Student_name_registration_no);
        textView=view.findViewById(R.id.student_single_attendance_details);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s=dbAdapter.getStudentsTotalAttendanceDetails(regitrationNo.getText().toString().trim());


                textView.setText(s);
            }
        });


        return view;
    }
}
