package com.tr.blebutton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;


public class CihazIsmiDialog extends AppCompatDialogFragment {

    private EditText editTextPassword;
    private SaveDialogListener listeners;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cihazi_sec, null);
        builder.setView(view)
                .setTitle("Cihaz İsmi")
                .setNegativeButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String hatirla = editTextPassword.getText().toString();
                        String hatirlass = "1";
                        listeners.applyS(hatirla, hatirlass);

                    }
                });


        editTextPassword = view.findViewById(R.id.pass);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listeners =(SaveDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface SaveDialogListener {
        void applyS(String Harirla, String hatirlass);
    }
}