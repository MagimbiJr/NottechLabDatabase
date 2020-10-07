package com.tana.nottechlab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {
    private static final String TAG = MemberListAdapter.class.getSimpleName();
    public final Context mContext;
    public  ArrayList<Members> mMembers;

    public MemberListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Members employees = mMembers.get(position);
        holder.setData(employees.getName(), employees.getDesignation(), position);
        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    public void setEmployee(ArrayList<Members> members) {
        mMembers = members;
        notifyDataSetChanged();
    }

    public  class MemberViewHolder extends RecyclerView.ViewHolder {

        public final TextView mName;
        public final TextView mDesignation;
        public int mPos;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.empName);
            mDesignation = (TextView) itemView.findViewById(R.id.empDesignation);
            mPos = 0;
        }

        public void setData(String name, String designation, int pos) {
            mName.setText(name);
            mDesignation.setText(designation);
            mPos = pos;
        }

        public void setListener() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdateMemberActivity.class);
                    intent.putExtra(NottechLabDBContract.MembersEntry.COLUMN_ID, mMembers.get(mPos).getId());
                    ((Activity) mContext).startActivityForResult(intent, 2);
                }
            });
        }
    }
}
