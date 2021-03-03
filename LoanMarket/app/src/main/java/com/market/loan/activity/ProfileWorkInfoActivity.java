package com.market.loan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
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
import com.market.loan.bean.UserResult;
import com.market.loan.bean.WorkRequest;
import com.market.loan.constant.Apis;
import com.market.loan.core.ConfigCache;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;
import com.market.loan.tools.DictsFilter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileWorkInfoActivity extends BaseActivity {

    List<ConfigData> dicts;
    List<ConfigData> employmentDicts;
    List<ConfigData> salaryDicts;
    List<ConfigData> incomeDicts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_work_info);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            dicts = configResult.getDicts();
        }

        final AppCompatEditText myProfileEmployment = findViewById(R.id.myProfileEmployment);
        final AppCompatEditText myProfileSalary = findViewById(R.id.myProfileSalary);
        final AppCompatEditText myProfileIncome = findViewById(R.id.myProfileIncome);
        final AppCompatImageButton myProfileWorkSave = findViewById(R.id.myProfileWorkSave);


        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UserResult userResult = ConfigCache.userResult;
        final WorkRequest workRequest = new WorkRequest(ConfigCache.userResult);
        myProfileEmployment.setText(userResult.getEmploymentType());
        myProfileSalary.setText(userResult.getMonthlySalary());
        myProfileIncome.setText(userResult.getMonthlyFamilySalary());
        DictsFilter dictsFilter = new DictsFilter(dicts);
        employmentDicts = dictsFilter.getDictsByType("Employment Type");
        salaryDicts = dictsFilter.getDictsByType("Your Monthly Salary");
        incomeDicts = dictsFilter.getDictsByType("Monthly Family Income");
        String employmentType = dictsFilter.getValue("Employment Type", userResult.getEmploymentType());
        String yourMonthlySalary = dictsFilter.getValue("Your Monthly Salary", userResult.getMonthlySalary());
        String monthlyFamilyIncome = dictsFilter.getValue("Monthly Family Income", userResult.getMonthlyFamilySalary());
        workRequest.setEmployment_type(employmentType);
        workRequest.setMonthly_salary(yourMonthlySalary);
        workRequest.setMonthly_family_salary(monthlyFamilyIncome);
        final DialogInterface.OnClickListener employmentClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = employmentDicts.get(which);
                myProfileEmployment.setText(data.getName());
                workRequest.setEmployment_type(data.getValue());
                dialog.cancel();
            }
        };


        final DialogInterface.OnClickListener salaryClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = salaryDicts.get(which);
                myProfileSalary.setText(data.getName());
                workRequest.setMonthly_salary(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener incomeClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = incomeDicts.get(which);
                myProfileIncome.setText(data.getName());
                workRequest.setMonthly_family_salary(data.getValue());
                dialog.cancel();
            }
        };

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.myProfileEmployment) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileWorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileWorkInfoActivity.this, employmentDicts, R.layout.list_view_item), -1, employmentClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileSalary) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileWorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileWorkInfoActivity.this, salaryDicts, R.layout.list_view_item), -1, salaryClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileIncome) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileWorkInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileWorkInfoActivity.this, incomeDicts, R.layout.list_view_item), -1, incomeClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileWorkSave) {
                    String result = workRequest.checked();
                    if (result != null) {
                        Toast.makeText(ProfileWorkInfoActivity.this, result, Toast.LENGTH_SHORT).show();
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
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        };
        myProfileEmployment.setOnClickListener(onClickListener);
        myProfileSalary.setOnClickListener(onClickListener);
        myProfileIncome.setOnClickListener(onClickListener);
        myProfileWorkSave.setOnClickListener(onClickListener);

    }
}