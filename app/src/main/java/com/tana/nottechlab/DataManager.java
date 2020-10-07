package com.tana.nottechlab;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.tana.nottechlab.NottechLabDBContract.MembersEntry;

import java.util.ArrayList;

public  class DataManager {
    public static ArrayList<Members> fetchAllMembers(NottechLabDBHelper sqHelper) {
        ArrayList<Members> employees = new  ArrayList();

        SQLiteDatabase db = sqHelper.getReadableDatabase();
        final String[] columns = {
                MembersEntry.COLUMN_ID,
                MembersEntry.COLUMN_NAME,
                MembersEntry.COLUMN_DOB,
                MembersEntry.COLUMN_DESIGNATION
        };

        final Cursor cursor = db.query(MembersEntry.TABLE_NAME, columns, null, null, null, null, null);

        int idPos = cursor.getColumnIndex(MembersEntry.COLUMN_ID);
        int namePos = cursor.getColumnIndex(MembersEntry.COLUMN_NAME);
        int dobPos = cursor.getColumnIndex(MembersEntry.COLUMN_DOB);
        int designationPos = cursor.getColumnIndex(MembersEntry.COLUMN_DESIGNATION);

        while (cursor.moveToNext()) {
            String id = cursor.getString(idPos);
            String name = cursor.getString(namePos);
            long dob = cursor.getLong(dobPos);
            String designation = cursor.getString(designationPos);

            Members employee = new Members(id, name, dob, designation);

            employees.add(employee);
        }
        cursor.close();

         return employees;
    }

    public static Members fetchMember(NottechLabDBHelper sqHelper, String empId) {
        SQLiteDatabase db = sqHelper.getReadableDatabase();
        Members member = null;

        final String[] columns = {
                MembersEntry.COLUMN_NAME,
                MembersEntry.COLUMN_DOB,
                MembersEntry.COLUMN_DESIGNATION
        };

        String selection = MembersEntry.COLUMN_ID + " LIKE ? ";

        final String[] selectionArgs = {empId};

        final Cursor cursor = db.query(MembersEntry.TABLE_NAME,
                columns, selection, selectionArgs,
                null, null, null);

        int namePos = cursor.getColumnIndex(MembersEntry.COLUMN_NAME);
        int dobPos = cursor.getColumnIndex(MembersEntry.COLUMN_DOB);
        int designationPos = cursor.getColumnIndex(MembersEntry.COLUMN_DESIGNATION);

        while (cursor.moveToNext()) {
            String name = cursor.getString(namePos);
            long dob = cursor.getLong(dobPos);
            String designation = cursor.getString(designationPos);

            member = new Members(empId, name, dob, designation);
        }

        cursor.close();

        return member;
    }

    public static void updateMember(NottechLabDBHelper sqHelper, Members member) {
        SQLiteDatabase db = sqHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MembersEntry.COLUMN_NAME, member.getName());
        values.put(MembersEntry.COLUMN_DOB, member.getDob());
        values.put(MembersEntry.COLUMN_DESIGNATION, member.getDesignation());

        String selection = MembersEntry.COLUMN_ID + " LIKE ? ";

        final String[] selectionArgs = {member.getId()};

        db.update(MembersEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public static int deleteMember(NottechLabDBHelper sqHelper, String empId) {
        SQLiteDatabase db = sqHelper.getWritableDatabase();

        String selection = MembersEntry.COLUMN_ID + " LIKE ? ";
        String[] selectionArgs = {empId};

        return db.delete(MembersEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static int deleteAllMembers(NottechLabDBHelper sqHelper) {
        SQLiteDatabase db = sqHelper.getWritableDatabase();
        return db.delete(MembersEntry.TABLE_NAME, "1", null);
    }
}
