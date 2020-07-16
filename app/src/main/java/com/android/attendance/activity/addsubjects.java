package com.android.attendance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class addsubjects extends AppCompatActivity {

    DBAdapter dbAdapter=new DBAdapter(this);
    EditText editText;
    Spinner spinner;
    EditText editText2;
    Button ADDSUBJECT;
    String SELECTED_BRANCH;
    boolean flag=false;
    private String[] branchString = new String[]{"cse", "ETC", "Mechanical", "civil"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubjects);
        editText=findViewById(R.id.addSemester);
        spinner=findViewById(R.id.addBranch);
        editText2=findViewById(R.id.addSubjectname);
        ADDSUBJECT=findViewById(R.id.addSubjectButton);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,branchString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SELECTED_BRANCH =adapterView.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                 flag = true;
            }
        });



        ADDSUBJECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag==true)
                {
                    Toast.makeText(addsubjects.this, "Select semester", Toast.LENGTH_SHORT).show();
                }
                else {
                    String semester = editText.getText().toString().trim();
                    String branch = SELECTED_BRANCH;
                    String Subject = editText2.getText().toString().trim();
                    dbAdapter.enterStudentSubject(Subject, semester, branch);
                    Toast.makeText(addsubjects.this, "Subject added", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
