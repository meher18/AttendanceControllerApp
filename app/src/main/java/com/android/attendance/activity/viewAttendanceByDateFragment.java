package com.android.attendance.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import java.util.ArrayList;

public class viewAttendanceByDateFragment extends Fragment {

    DBAdapter dbAdapter;
    EditText date_text;
    EditText subject_text;

    Button button_for_checking_attendance;
    ListView listView;

    public viewAttendanceByDateFragment(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.viewattendancebydate,container,false);
        date_text =view.findViewById(R.id.date_for_attendance);
        button_for_checking_attendance =view.findViewById(R.id.button_for_date_attendance);
        listView=view.findViewById(R.id.listview_for_attendance);
        subject_text =view.findViewById(R.id.subject_for_attendance);



        button_for_checking_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> strings=new ArrayList<>();


                 if(strings!=null)
                 {
                     strings.clear();
                 }
                strings = dbAdapter.getAttendanceByDate(date_text.getText().toString(), subject_text.getText().toString());
                if(strings.isEmpty())
                {
                    Toast.makeText(getContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, strings);
                    listView.setAdapter(adapter);
                }
            }
        });

        return view;
    }
}
