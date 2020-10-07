package com.tana.nottechlab;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {
    String mEmpId;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?..")
                .setMessage("Are you sure you want to delete all data")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NottechLabDBHelper sqHelper = new NottechLabDBHelper(getContext());
                        mEmpId = null;
                        Bundle bundle = getActivity().getIntent().getExtras();
                        if (bundle != null) {
                            mEmpId = bundle.getString(NottechLabDBContract.MembersEntry.COLUMN_ID);
                            int result = DataManager.deleteMember(sqHelper, mEmpId);
                            Toast.makeText(getContext(), result + " record deleted", Toast.LENGTH_SHORT).show();
                            getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
                            getActivity().finish();
                        }

                    }
                });

        return builder.create();
    }
}
