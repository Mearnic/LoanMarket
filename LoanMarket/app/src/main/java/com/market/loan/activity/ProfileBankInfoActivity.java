package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.market.loan.BaseActivity;
import com.market.loan.R;
import com.market.loan.bean.BankRequest;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Apis;
import com.market.loan.core.ConfigCache;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileBankInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_bank_info);
        final AppCompatEditText profileIfscCode = findViewById(R.id.profileBankIfscCode);
        final AppCompatEditText profileBankName = findViewById(R.id.profileBankBankName);
        final AppCompatEditText profileAccountNo = findViewById(R.id.profileBankAccountNo);
        final AppCompatImageButton profileBankSave = findViewById(R.id.profileBankSave);


        UserResult userResult = ConfigCache.userResult;
        final BankRequest bankRequest = new BankRequest(userResult);
        profileIfscCode.setText(userResult.getIfscCode());
        profileBankName.setText(userResult.getBankName());
        profileAccountNo.setText(userResult.getBankAccountNo());

        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.profileBankSave) {
                    bankRequest.setIfsc_code(Objects.requireNonNull(profileIfscCode.getText()).toString());
                    bankRequest.setBank_name(Objects.requireNonNull(profileBankName.getText()).toString());
                    bankRequest.setBank_account_no(Objects.requireNonNull(profileAccountNo.getText()).toString());
                    String result = bankRequest.checked();
                    if (result != null) {
                        Toast.makeText(ProfileBankInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {
                        show();
                        Call call = new HttpRequest().post(Apis.BANK_SAVE_URL, RequestBody.create(HttpRequest.JSON, JSON.toJSONString(bankRequest)));
                        call.enqueue(new CallbackAdapter() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                super.onFailure(call, e);
                                dismiss();
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                                dismiss();
                                assert response.body() != null;
                                if (response.isSuccessful()) {
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        };
        profileBankSave.setOnClickListener(onClickListener);
    }
}