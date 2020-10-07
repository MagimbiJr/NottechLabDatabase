package com.tana.nottechlab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MainDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure?..")
                .setMessage("Are you sure you want to delete all data!..")
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
                        int result = DataManager.deleteAllMembers(sqHelper);
                        Toast.makeText(getContext(), result + " records deleted", Toast.LENGTH_SHORT).show();
                        getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
                        getActivity().finish();
                    }
                });
        return builder.create();
    }
}
