package com.tana.nottechlab;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateMemberActivity extends AppCompatActivity {

    Calendar mCalendar;
    NottechLabDBHelper mSQHelper;
    TextInputEditText mFullName;
    TextInputEditText mDOB;
    TextInputEditText mDesignation;
    Button mSaveButton;
    Button mCancelButton;

    String mEmpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mSQHelper = new NottechLabDBHelper(this);

        mCalendar = Calendar.getInstance();

        mFullName = (TextInputEditText) findViewById(R.id.etEmpName);
        mDOB = (TextInputEditText) findViewById(R.id.etDOB);
        mDesignation = (TextInputEditText) findViewById(R.id.etDesignation);
        mSaveButton = (Button) findViewById(R.id.bSave);
        mCancelButton = (Button) findViewById(R.id.bCancel);

        mEmpId = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mEmpId = bundle.getString(NottechLabDBContract.MembersEntry.COLUMN_ID);
            Members member = DataManager.fetchMember(mSQHelper, mEmpId);

            if (member != null) {
                mFullName.setText(member.getName());
                mDOB.setText(member.getDob() + "");
                mDesignation.setText(member.getDesignation());
            }
        }

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
            public void onClick(View v) {
                saveMember();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteItem() {
        ItemDialog dialog = new ItemDialog();
        dialog.show(getSupportFragmentManager(), "Item Dialog");
    }

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

    private void saveMember() {
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
            String updateName = mFullName.getText().toString();
            long updateDob = mCalendar.getTimeInMillis();
            String updateDesignation = mDesignation.getText().toString();

            Members updateMember = new Members(mEmpId, updateName, updateDob, updateDesignation);

            DataManager.updateMember(mSQHelper, updateMember);
            setResult(Activity.RESULT_OK, getIntent());
            Toast.makeText(this, "Employee Updated", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}