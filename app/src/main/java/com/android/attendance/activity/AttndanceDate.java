package com.android.attendance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class AttndanceDate extends AppCompatActivity {


    DBAdapter dbAdapter=new DBAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateattendance);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,new viewAttendanceByDateFragment(dbAdapter)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.total_attendance_by_date,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.total_addendanceByDate)
        {

            Toast.makeText(this, "Total "+ dbAdapter.total_present()+" are present", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
