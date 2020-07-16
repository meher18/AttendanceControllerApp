package com.android.attendance.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;

public class generateExcelSheet  extends Fragment{

    EditText date;
    Spinner  branch;
    EditText semester;
    EditText subject;

    String   BRANCH;

    DBAdapter dbAdapter;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button createSheetButton;

    HSSFWorkbook sheets=new HSSFWorkbook();
    HSSFSheet hssfSheet1=sheets.createSheet("sheet 1");
    HSSFRow row ;

    private String[] branchString = new String[]{"cse", "ETC", "Mechanical", "civil"};


    public generateExcelSheet(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.generate_excel_sheet,container,false);
        date=view.findViewById(R.id.excel_date);
        branch=view.findViewById(R.id.excel_spinner);
        semester=view.findViewById(R.id.excel_semester);
        subject=view.findViewById(R.id.excel_subject);
        createSheetButton=view.findViewById(R.id.generate_excel_button);


        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, branchString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(adapter_branch);
        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BRANCH=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(getContext(), "Please select the branch", Toast.LENGTH_SHORT).show();


            }
        });


        createSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateText=date.getText().toString().trim();
                String subjectText=subject.getText().toString().trim();

                getTheDetails(dateText,subjectText);
            }
        });




        return view;
    }


    public void getTheDetails(String date,String subject)
    {

        String query="SELECT * FROM attendance_table WHERE "+dbAdapter.KEY_DATE+"='"+date+"' AND "+
                dbAdapter.KEY_SUBJECT+"='"+subject+"' ;";
        sqLiteDatabase=dbAdapter.getWritableDatabase();
        cursor=sqLiteDatabase.rawQuery(query,null);
        createExcelSheet(cursor);
    }


    public void createExcelSheet(Cursor cursor)
    {
        row=hssfSheet1.createRow(0);
        HSSFCell h0=row.createCell(0);
        HSSFCell h1=row.createCell(1);
        HSSFCell h2=row.createCell(2);
        HSSFCell h3=row.createCell(3);
        h0.setCellValue("student id");
        h1.setCellValue("student attendance status");
        h2.setCellValue("date of attendance");
        h3.setCellValue("subject");



        if(cursor.moveToFirst())
        {
            do {
             row=hssfSheet1.createRow(cursor.getPosition()+2);
                HSSFCell hssfCell=row.createCell(0);
                HSSFCell hssfCell1=row.createCell(1);
                HSSFCell hssfCell2=row.createCell(2);
                HSSFCell hssfCell3=row.createCell(3);

                hssfCell.setCellValue(cursor.getString(1));
                hssfCell1.setCellValue(cursor.getString(2));
                hssfCell2.setCellValue(cursor.getString(3));
                hssfCell3.setCellValue(cursor.getString(4));





            }while(cursor.moveToNext());
            createFile(row);
        }
        else{
            Toast.makeText(getContext(), "No data to create Sheet", Toast.LENGTH_SHORT).show();
        }
    }
       public void createFile(HSSFRow row){

           FileOutputStream fos = null;


           try {

               File sd = getContext().getExternalFilesDir(null);

//               File folder=new File(sd,"excel_sheets");
//               folder.mkdir();

               File directory = new File (sd.getAbsolutePath()+ "/excelSheets");
               directory.mkdirs();
               File file=new File(directory,getString(R.string.app_name)+System.currentTimeMillis()+ ".xls");

               Log.d("file path1", "createFile: "+file.getAbsolutePath());
               fos=new FileOutputStream(file);
               Log.d("file path2", "createFile: "+fos);


               sheets.write(fos);
               Toast.makeText(getContext(), "Excel Sheet Generated \n path : "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               if (fos != null) {
                   try {
                       fos.flush();
                       fos.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

           }

       }

//    private static final String KEY_SESSION_ID = "attendance_session_id";
//    private static final String KEY_ATTENDANCE_STUDENT_ID = "attendance_student_id";
//    private static final String KEY_ATTENDANCE_STATUS = "attendance_status";
//    private static final String KEY_DATE = "attendance_date";
//    private static final String KEY_SUBJECT = "attendance_subject";
}
