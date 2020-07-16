package com.android.attendance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.MenuItem;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class viewsinglestudentattendance extends AppCompatActivity {

    DBAdapter dbAdapter=new DBAdapter(this);
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsinglestudentattendance);



        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout2,new viewAttendanceperStudent(dbAdapter)).commit();

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId())
                {
                    case R.id.viewStudentAttendance: getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout2,new viewAttendanceperStudent(dbAdapter)).commit();
                    break;
                    case R.id.generate_excelSheet:getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout2,new generateExcelSheet(dbAdapter)).commit();
                    break;
                }




                return false;
            }
        });


    }




}
