package com.market.loan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.market.loan.BaseActivity;
import com.market.loan.R;
import com.market.loan.adapter.DialogItemAdapter;
import com.market.loan.bean.BaseRequest;
import com.market.loan.bean.ConfigData;
import com.market.loan.bean.ConfigResult;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;
import com.market.loan.tools.DictsFilter;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaseInfoActivity extends BaseActivity {

    List<ConfigData> dicts;
    List<ConfigData> genderDicts;
    List<ConfigData> educationDicts;
    List<ConfigData> maritalDicts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            dicts = configResult.getDicts();
        }
        final AppCompatEditText birthday = findViewById(R.id.baseBirthday);
        final AppCompatEditText gender = findViewById(R.id.baseGender);
        final AppCompatEditText education = findViewById(R.id.baseEducation);
        final AppCompatEditText marital = findViewById(R.id.baseMarital);
        final AppCompatEditText baseName = findViewById(R.id.baseFullName);
        final AppCompatEditText baseMail = findViewById(R.id.baseMail);
        final AppCompatImageButton baseNext = findViewById(R.id.baseNext);

        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final BaseRequest baseRequest = new BaseRequest();
        final DialogInterface.OnClickListener genderClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = genderDicts.get(which);
                gender.setText(data.getName());
                baseRequest.setGender(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener educationClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = educationDicts.get(which);
                education.setText(data.getName());
                baseRequest.setEducation(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener maritalClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = maritalDicts.get(which);
                marital.setText(data.getName());
                baseRequest.setMarital(data.getValue());
                dialog.cancel();
            }
        };


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.baseBirthday) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String birthdayValue = year + "-" + month + "-" + dayOfMonth;
                            birthday.setText(birthdayValue);
                            baseRequest.setBirthday(birthdayValue);
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(
                            BaseInfoActivity.this,
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    dialog.show();
                } else if (id == R.id.baseGender) {
                    genderDicts = new DictsFilter(dicts).getDictsByType("Gender");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, genderDicts, R.layout.list_view_item), -1, genderClickListener);
                    builder.create().show();
                } else if (id == R.id.baseEducation) {
                    educationDicts = new DictsFilter(dicts).getDictsByType("Education");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, educationDicts, R.layout.list_view_item), -1, educationClickListener);
                    builder.create().show();
                } else if (id == R.id.baseMarital) {
                    maritalDicts = new DictsFilter(dicts).getDictsByType("Marital");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, maritalDicts, R.layout.list_view_item), -1, maritalClickListener);
                    builder.create().show();
                } else if (id == R.id.baseNext) {
                    baseRequest.setName(Objects.requireNonNull(baseName.getText()).toString());
                    baseRequest.setEmail(Objects.requireNonNull(baseMail.getText()).toString());
                    String result = baseRequest.checked();
                    if (result != null) {
                        Toast.makeText(BaseInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {
                        show();
                        Call call = new HttpRequest().post(Apis.BASE_SAVE_URL, RequestBody.create(HttpRequest.JSON, JSON.toJSONString(baseRequest)));
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
                                    Intent intent = new Intent(BaseInfoActivity.this, WorkInfoActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        };
        birthday.setOnClickListener(onClickListener);
        gender.setOnClickListener(onClickListener);
        education.setOnClickListener(onClickListener);
        marital.setOnClickListener(onClickListener);
        baseNext.setOnClickListener(onClickListener);
    }
}