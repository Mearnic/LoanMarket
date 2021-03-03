package com.market.loan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.market.loan.activity.LoginActivity;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.StartViewModel;

public class StartActivity extends NoBarActivity {

    private StartViewModel startViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        super.onCreate(savedInstanceState);
        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        startViewModel.getConfigResult().observe(this, new Observer<Result<ConfigResult>>() {
            @Override
            public void onChanged(Result<ConfigResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ConfigCache.configResult = result.getData();
                    saveConfig(result.getData());
                    selectorActivity();
                } else {
                    new AlertDialog.Builder(StartActivity.this)
                            .setTitle(result.getMessage())
                            .setPositiveButton("exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startViewModel.request();
                                }
                            }).show();
                }
            }
        });
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    startViewModel.request();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void selectorActivity() {
        Class<?> activityClass;
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String token = profile.getString("token", null);
        ConfigCache.token = token;
        if (token == null) {
            activityClass = LoginActivity.class;
        } else {
            activityClass = MainActivity.class;
        }
        Intent intent = new Intent(StartActivity.this, activityClass);
        startActivity(intent);
        finish();

    }


    private void saveConfig(ConfigResult configResult) {
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        SharedPreferences.Editor edit = profile.edit();
        edit.putString("configResult", JSON.toJSONString(configResult));
        edit.apply();
    }
}