package com.market.loan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.market.loan.MainActivity;
import com.market.loan.NoBarActivity;
import com.market.loan.R;
import com.market.loan.bean.LoginResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Constants;
import com.market.loan.constant.Status;
import com.market.loan.constant.Toasts;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.LoginViewModel;

import java.util.Objects;


public class LoginActivity extends NoBarActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        View loginNext = findViewById(R.id.login_next);

        final AppCompatEditText mobileEdit = findViewById(R.id.login_mobile);
        final AppCompatEditText codeEdit = findViewById(R.id.login_code);
        final AppCompatTextView errorMessageText = findViewById(R.id.errorMessage);

        final AppCompatCheckBox agreeCheckbox = findViewById(R.id.agree_checkbox);
        loginNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeCheckbox.isChecked()) {
                    String mobile = Objects.requireNonNull(mobileEdit.getText()).toString();
                    String code = Objects.requireNonNull(codeEdit.getText()).toString();
                    show();
                    loginViewModel.login(mobile, code);
                } else {
                    errorMessageText.setText(Toasts.AGREE_CHECK_BOX);
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<Result<LoginResult>>() {
            @Override
            public void onChanged(Result<LoginResult> result) {
                dismiss();
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ConfigCache.token = result.getData().getToken();
                    SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
                    SharedPreferences.Editor edit = profile.edit();
                    edit.putString("token", ConfigCache.token);
                    edit.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorMessageText.setText(result.getMessage());
                }
            }
        });

        final AppCompatTextView smsCodeText = findViewById(R.id.smsCode);
        final AppCompatTextView codeTimeText = findViewById(R.id.codeTime);
        smsCodeText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String mobile = Objects.requireNonNull(mobileEdit.getText()).toString();
                loginViewModel.smsCode(mobile);
                smsCodeText.setText("resend");
                smsCodeText.setVisibility(View.GONE);
                new CountDownTimer(Constants.MAX_SMS_CODE_TIME, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        codeTimeText.setText("(" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        smsCodeText.setVisibility(View.VISIBLE);
                        codeTimeText.setVisibility(View.GONE);
                    }
                }.start();
                codeTimeText.setVisibility(View.VISIBLE);
            }
        });
        loginViewModel.getSmsCode().observe(this, new Observer<Result<String>>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Result<String> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    if (result.getData() == null){
                        errorMessageText.setText("send code error!");
                    }
                } else {
                    errorMessageText.setText(result.getMessage());
                }
            }
        });


    }
}