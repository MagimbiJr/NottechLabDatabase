package com.tana.nottechlab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}