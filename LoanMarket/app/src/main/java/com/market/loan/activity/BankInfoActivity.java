package com.market.loan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import com.alibaba.fastjson.JSON;
import com.market.loan.BaseActivity;
import com.market.loan.R;
import com.market.loan.bean.BankRequest;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BankInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);
        final AppCompatEditText ifscCode = findViewById(R.id.bankIfscCode);
        final AppCompatEditText bankName = findViewById(R.id.bankBankName);
        final AppCompatEditText accountNo = findViewById(R.id.bankAccountNo);
        final AppCompatImageButton bankNext = findViewById(R.id.bankNext);
        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final BankRequest bankRequest = new BankRequest();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.bankNext) {
                    bankRequest.setIfsc_code(Objects.requireNonNull(ifscCode.getText()).toString());
                    bankRequest.setBank_name(Objects.requireNonNull(bankName.getText()).toString());
                    bankRequest.setBank_account_no(Objects.requireNonNull(accountNo.getText()).toString());
                    String result = bankRequest.checked();
                    if (result != null) {
                        Toast.makeText(BankInfoActivity.this, result, Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(BankInfoActivity.this, ReviewingActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        };
        bankNext.setOnClickListener(onClickListener);
    }
}