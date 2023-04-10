package com.sarose.lab_05;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Create Database object
    DatabaseHelper myDb;
    EditText Task;
    EditText Date;
    EditText Id;
    Button btnInsert;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define Edit Texts and Buttons
        myDb = new DatabaseHelper(this);
        Task = findViewById(R.id.enterTask);
        Date = findViewById(R.id.enterDate);
        Id = findViewById(R.id.enterID);
        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        insertData();
        getAllData();
        updateData();
        deleteData();
    }

    // Data insert function
    public void insertData() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(Task.getText().toString(), Date.getText().toString());
                if (isInserted) {
                    Toast.makeText(MainActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Inserted Failed", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    // View all data
    public void  getAllData() {
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = myDb.getAllData();
                if(result.getCount() == 0) {
                    showMessage("Error Message", "No Data Found");
                    return;
                }
                StringBuffer resultBuffer = new StringBuffer();
                while (result.moveToNext()) {
                    resultBuffer.append("Id: " + result.getString(0) + "\n" );
                    resultBuffer.append("Task: " + result.getString(1) + "\n" );
                    resultBuffer.append("Date: " + result.getString(2) + "\n\n" );

                }
                showMessage("List of Data:", resultBuffer.toString());


            }
        });
    }

    //Update the existing Record
    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = myDb.updateData(Id.getText().toString(), Task.getText().toString(), Date.getText().toString());
                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Update Failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //Delete the existing record
    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfDeletedRecords = myDb.deleteData(Id.getText().toString());
                if (numberOfDeletedRecords > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Deletion Failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private  void showMessage(String title, String message){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.show();
    }




}