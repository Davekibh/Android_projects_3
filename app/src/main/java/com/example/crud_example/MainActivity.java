package com.example.crud_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName, editSurname, editMarks, editId;
    Button addData;
    Button viewDataBtn;
    Button updateDataBtn;
    Button deleteDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editId = (EditText) findViewById(R.id.editTextTextPersonId);
        editName = (EditText) findViewById(R.id.editTextTextPersonName);
        editSurname =(EditText) findViewById(R.id.editTextTextPersonSurname);
        editMarks = (EditText) findViewById(R.id.editTextTextPersonMarks);
        addData = (Button) findViewById(R.id.button_Add);
        viewDataBtn = (Button) findViewById(R.id.button_View);
        updateDataBtn = (Button) findViewById(R.id.button_Update);
        deleteDataBtn = (Button) findViewById(R.id.button_Delete);
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean insertStatus = myDB.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                if(insertStatus == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data Cannot be Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteData(){
        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDB.deleteData(editId.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this, "Data Is Deleted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Cannot be Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateData(){
        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(editId.getText().toString(),editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Cannot be updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll(){
        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0){
                    showMessage("Error", "No Data Found");
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append("Id : " + res.getString(0) + "\n");
                        buffer.append("Name : " + res.getString(1) + "\n");
                        buffer.append("Surname : " + res.getString(2) + "\n");
                        buffer.append("Marks : " + res.getString(3) + "\n\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        });
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}