package com.android.attendance.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class ViewAttendanceByFacultyActivity extends Activity {

	ArrayList<AttendanceBean> attendanceBeanList;
	private ListView listView ;  
	private ArrayAdapter<String> listAdapter;

	DBAdapter dbAdapter = new DBAdapter(this);
	int TOTAL_PRESENT =0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewmain2);

		listView=(ListView)findViewById(R.id.listviewSecond);
		final ArrayList<String> attendanceList = new ArrayList<String>();
		attendanceList.add("Id | StudentName |  Status");

		attendanceBeanList=((ApplicationContext)ViewAttendanceByFacultyActivity.this.getApplicationContext()).getAttendanceBeanList();

		for(AttendanceBean attendanceBean : attendanceBeanList)
		{
			String users = "";
			if(attendanceBean.getAttendance_session_id() != 0)
			{
				DBAdapter dbAdapter = new DBAdapter(ViewAttendanceByFacultyActivity.this);
				StudentBean studentBean =dbAdapter.getStudentById(attendanceBean.getAttendance_student_id());
				users = attendanceBean.getAttendance_student_id()+".     "+studentBean.getStudent_firstname()+","+studentBean.getStudent_lastname()+"                  "+attendanceBean.getAttendance_status();

				if(attendanceBean.getAttendance_status().equals("P") && studentBean.getStudent_firstname()!=null)
				{

					TOTAL_PRESENT += 1;
					Log.d("totalAtt", "onCreate: "+ TOTAL_PRESENT);
				}
			}
			else
			{
				users = attendanceBean.getAttendance_status();

			}
			
			attendanceList.add(users);
			Log.d("users: ", users); 

		}

		listAdapter = new ArrayAdapter<String>(this, R.layout.view_attendance_list, R.id.labelAttendance, attendanceList);
		listView.setAdapter( listAdapter ); 

		/*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {



				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewAttendanceByFacultyActivity.this);

				alertDialogBuilder.setTitle(getTitle()+"decision");
				alertDialogBuilder.setMessage("Are you sure?");

				alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						facultyList.remove(position);
						listAdapter.notifyDataSetChanged();
						listAdapter.notifyDataSetInvalidated();   

						dbAdapter.deleteFaculty(facultyBeanList.get(position).getFaculty_id());
						facultyBeanList=dbAdapter.getAllFaculty();

						for(FacultyBean facultyBean : facultyBeanList)
						{
							String users = " FirstName: " + facultyBean.getFaculty_firstname()+"\nLastname:"+facultyBean.getFaculty_lastname();
							facultyList.add(users);
							Log.d("users: ", users); 

						}
						
					}
					
				});
				alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// cancel the alert box and put a Toast to the user
						dialog.cancel();
						Toast.makeText(getApplicationContext(), "You choose cancel", 
								Toast.LENGTH_LONG).show();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				// show alert
				alertDialog.show();





				return false;
			}
		});
*/



	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_strength, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		if(item.getItemId()==R.id.date_wise_attendance)
		{
			Intent intent=new Intent(getApplicationContext(),AttndanceDate.class);
			startActivity(intent);
		}


		return super.onOptionsItemSelected(item);
	}
}
