package com.market.loan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private AlertDialog loadDialog;

    public void show() {
        loadDialog = new AlertDialog.Builder(this, R.style.TransparentDialog).create();
        loadDialog.setCancelable(false);
        loadDialog.show();
        loadDialog.setContentView(R.layout.loading);
        loadDialog.setCanceledOnTouchOutside(false);
    }

    public void dismiss() {
        loadDialog.dismiss();
    }
}