package com.market.loan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.market.loan.MainActivity;
import com.market.loan.R;
import com.market.loan.StartActivity;
import com.market.loan.bean.ConfigData;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.MyProfileViewModel;

public class MyPageActivity extends AppCompatActivity {

    private MyProfileViewModel myProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);

        myProfileViewModel.getUserResult().observe(this, new Observer<Result<UserResult>>() {
            @Override
            public void onChanged(Result<UserResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ConfigCache.userResult = result.getData();
                    AppCompatTextView myPageMobile = findViewById(R.id.myPageMobile);
                    myPageMobile.setText(ConfigCache.userResult.getMobile());
                } else if (result.getCode().equals(Status.ACCESS_DENIED_CODE)) {
                    Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        myProfileViewModel.request();
        final AppCompatButton myProfile = findViewById(R.id.myProfile);
        final AppCompatButton myFeedBack = findViewById(R.id.myFeedBack);
        final AppCompatButton myLogOut = findViewById(R.id.myLogOut);
        AppCompatImageButton moneyPackageBtn = findViewById(R.id.moneyPackageBtn);
        AppCompatImageButton selfInfoBtn = findViewById(R.id.selfInfoBtn);
        AppCompatTextView bottomMyPageText = findViewById(R.id.bottom_my_page_text);

        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);


        ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
        assert configResult != null;
        bottomMyPageText.setText(configResult.getSysServiceEmailBak());

        View.OnClickListener bottomClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Class<?> activityClass = null;
                if (id == R.id.moneyPackageBtn) {
                    activityClass = MainActivity.class;
                } else if (id == R.id.selfInfoBtn) {
                    activityClass = MyPageActivity.class;
                }
                Intent intent = new Intent(MyPageActivity.this, activityClass);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        };

        moneyPackageBtn.setOnClickListener(bottomClick);
        selfInfoBtn.setOnClickListener(bottomClick);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            Class<?> activityClass;

            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.myProfile) {
                    activityClass = MyProfileActivity.class;
                } else if (id == R.id.myFeedBack) {
                    activityClass = FeedbackActivity.class;
                } else {
                    if (id == R.id.myLogOut) {
                        AlertDialog dialog = new AlertDialog.Builder(MyPageActivity.this)
                                .setTitle("Confirm logout?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConfigCache.token = "";
                                        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
                                        SharedPreferences.Editor edit = profile.edit();
                                        edit.putString("token", "");
                                        edit.apply();
                                        edit.commit();
                                        dialog.cancel();
                                        activityClass = LoginActivity.class;
                                        Intent intent = new Intent(MyPageActivity.this, activityClass);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                    return;
                }
                Intent intent = new Intent(MyPageActivity.this, activityClass);
                startActivity(intent);
            }
        };
        myProfile.setOnClickListener(onClickListener);
        myFeedBack.setOnClickListener(onClickListener);
        myLogOut.setOnClickListener(onClickListener);
    }


}