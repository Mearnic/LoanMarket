package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.market.loan.R;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.MyProfileViewModel;

public class MyProfileActivity extends AppCompatActivity {

    private MyProfileViewModel myProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);

        myProfileViewModel.getUserResult().observe(this, new Observer<Result<UserResult>>() {
            @Override
            public void onChanged(Result<UserResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ConfigCache.userResult = result.getData();
                } else if (result.getCode().equals(Status.ACCESS_DENIED_CODE)){
                    Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        myProfileViewModel.request();

        final AppCompatEditText profileBaseInfo = findViewById(R.id.profileBaseInfo);
        final AppCompatEditText profileWorkInfo = findViewById(R.id.profileWorkInfo);
        final AppCompatEditText profileBankInfo = findViewById(R.id.profileBankInfo);
        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {

            Class<?> activityClass;

            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.profileBaseInfo) {
                    activityClass = ProfileBasicInfoActivity.class;
                } else if (id == R.id.profileWorkInfo) {
                    activityClass = ProfileWorkInfoActivity.class;
                } else if (id == R.id.profileBankInfo) {
                    activityClass = ProfileBankInfoActivity.class;
                }
                Intent intent = new Intent(MyProfileActivity.this, activityClass);
                startActivity(intent);
            }
        };
        profileBaseInfo.setOnClickListener(onClickListener);
        profileWorkInfo.setOnClickListener(onClickListener);
        profileBankInfo.setOnClickListener(onClickListener);
    }

    @Override
    protected void onRestart() {
        myProfileViewModel.request();
        super.onRestart();
    }
}