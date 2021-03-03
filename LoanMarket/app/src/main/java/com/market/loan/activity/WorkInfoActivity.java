package com.market.loan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.market.loan.BaseActivity;
import com.market.loan.R;
import com.market.loan.adapter.DialogItemAdapter;
import com.market.loan.bean.ConfigData;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.WorkRequest;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;
import com.market.loan.tools.DictsFilter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WorkInfoActivity extends BaseActivity {

    List<ConfigData> dicts;
    List<ConfigData> employmentDicts;
    List<ConfigData> salaryDicts;
    List<ConfigData> incomeDicts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            dicts = configResult.getDicts();
        }
        final AppCompatEditText employment = findViewById(R.id.workEmployment);
        final AppCompatEditText salary = findViewById(R.id.workSalary);
        final AppCompatEditText income = findViewById(R.id.workIncome);
        final AppCompatImageButton baseNext = findViewById(R.id.workNext);

        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final WorkRequest workRequest = new WorkRequest();


        final DialogInterface.OnClickListener employmentClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = employmentDicts.get(which);
                employment.setText(data.getName());
                workRequest.setEmployment_type(data.getValue());
                dialog.cancel();
            }
        };


        final DialogInterface.OnClickListener salaryClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = salaryDicts.get(which);
                salary.setText(data.getName());
                workRequest.setMonthly_salary(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener incomeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = incomeDicts.get(which);
                income.setText(data.getName());
                workRequest.setMonthly_family_salary(data.getValue());
                dialog.cancel();
            }
        };

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.workEmployment) {
                    employmentDicts = new DictsFilter(dicts).getDictsByType("Employment Type");
                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(WorkInfoActivity.this, employmentDicts, R.layout.list_view_item), -1, employmentClickListener);
                    builder.create().show();
                } else if (id == R.id.workSalary) {
                    salaryDicts = new DictsFilter(dicts).getDictsByType("Your Monthly Salary");
                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(WorkInfoActivity.this, salaryDicts, R.layout.list_view_item), -1, salaryClickListener);
                    builder.create().show();
                } else if (id == R.id.workIncome) {
                    incomeDicts = new DictsFilter(dicts).getDictsByType("Monthly Family Income");
                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(WorkInfoActivity.this, incomeDicts, R.layout.list_view_item), -1, incomeClickListener);
                    builder.create().show();
                } else if (id == R.id.workNext) {
                    String result = workRequest.checked();
                    if (result != null) {
                        Toast.makeText(WorkInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {
                        show();
                        Call call = new HttpRequest().post(Apis.WORK_SAVE_URL, RequestBody.create(HttpRequest.JSON, JSON.toJSONString(workRequest)));
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
                                    Intent intent = new Intent(WorkInfoActivity.this, BankInfoActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        };
        employment.setOnClickListener(onClickListener);
        salary.setOnClickListener(onClickListener);
        income.setOnClickListener(onClickListener);
        baseNext.setOnClickListener(onClickListener);
    }
}