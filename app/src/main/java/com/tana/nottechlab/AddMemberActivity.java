package com.tana.nottechlab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMemberActivity extends AppCompatActivity {
    NottechLabDBHelper mDBHelper;
    Calendar mCalendar;
    TextInputEditText mFullName;
    TextInputEditText mDesignation;
    TextInputEditText mDOB;
    Button mSaveButton;
    Button mCancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mDBHelper = new NottechLabDBHelper(this);
        mCalendar = Calendar.getInstance();
         mDOB = (TextInputEditText) findViewById(R.id.etDOB);
        mFullName = (TextInputEditText) findViewById(R.id.etEmpName);
        mDesignation = (TextInputEditText) findViewById(R.id.etDesignation);
        mSaveButton = (Button) findViewById(R.id.bSave);
        mCancelButton = (Button) findViewById(R.id.bCancel);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mDOB.setText(getFormattedDate(mCalendar.getTimeInMillis()));
            }
        };

        mDOB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                setUpCalendar(date);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmployee();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveEmployee() {
        boolean isValid = true;

        if (mFullName.getText().toString().isEmpty()) {
            isValid = false;
            mFullName.setError("Required field");
        }
        if (mDesignation.getText().toString().isEmpty()) {
            isValid = false;
            mDesignation.setError("Required field");
        }

        if (isValid) {
            String name = mFullName.getText().toString();
            String designation = mDesignation.getText().toString();
            long dob = mCalendar.getTimeInMillis();

            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NottechLabDBContract.MembersEntry.COLUMN_NAME, name);
            values.put(NottechLabDBContract.MembersEntry.COLUMN_DESIGNATION, designation);
            values.put(NottechLabDBContract.MembersEntry.COLUMN_DOB, dob);

            long result = db.insert(NottechLabDBContract.MembersEntry.TABLE_NAME, null, values);
            setResult(RESULT_OK, new Intent());

            Toast.makeText(getApplicationContext(), "Employee added", Toast.LENGTH_SHORT).show();
        }
        if (isValid)
            finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpCalendar(DatePickerDialog.OnDateSetListener date) {
        new DatePickerDialog(
                this, date, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
    }

    private String getFormattedDate(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyy", Locale.getDefault());
        String dateFormatter = sdf.format(timeInMillis);
        return dateFormatter;
    }
}