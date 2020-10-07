package com.tana.nottechlab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mEmployeeList;
    private ArrayList<Members> mMembers;
    private NottechLabDBHelper mSQHelper;
    private MemberListAdapter mMemberListAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mMembers = new ArrayList<>();

        mSQHelper = new NottechLabDBHelper(this);
        mEmployeeList = (RecyclerView) findViewById(R.id.empList);
        mMemberListAdapter = new MemberListAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mMemberListAdapter.setEmployee(DataManager.fetchAllMembers(mSQHelper));
        mEmployeeList.setLayoutManager(mLayoutManager);
        mEmployeeList.setAdapter(mMemberListAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMember = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivityForResult(addMember, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_deleteAll:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAll() {
        MainDialog dialog = new MainDialog();
        dialog.show(getSupportFragmentManager(), "Main Dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}