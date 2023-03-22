package edu.pdx.cs410J.kgujral;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ErrorDialog extends AppCompatDialogFragment {

    String title;
    String message;
    static String ok = "OK";

    void setTitle(String title){
        this.title = title;
    }

    void setMessage(String message){
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(ok, (dialogInterface, i) -> {

                });
        return builder.create();
    }
}
