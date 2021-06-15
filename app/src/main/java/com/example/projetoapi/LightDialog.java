package com.example.projetoapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import static androidx.core.content.ContextCompat.getSystemService;


public class LightDialog extends AppCompatDialogFragment {
    CameraManager cameraManager;
    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert")
                .setMessage("Low light, would you like to turn on the flashlight?")
                .setPositiveButton(" Yes ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(" No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }



}
