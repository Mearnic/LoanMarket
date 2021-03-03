package com.market.loan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Apis;
import com.market.loan.core.ConfigCache;
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

public class ProfileBasicInfoActivity extends BaseActivity {

    List<ConfigData> dicts;
    List<ConfigData> genderDicts;
    List<ConfigData> educationDicts;
    List<ConfigData> maritalDicts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_basic_info);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            dicts = configResult.getDicts();
        }

        final AppCompatEditText birthday = findViewById(R.id.myProfileBirthday);
        final AppCompatEditText gender = findViewById(R.id.myProfileGender);
        final AppCompatEditText education = findViewById(R.id.myProfileEducation);
        final AppCompatEditText marital = findViewById(R.id.myProfileMarital);
        final AppCompatEditText myProfileName = findViewById(R.id.myProfileFullName);
        final AppCompatEditText myProfileMail = findViewById(R.id.myProfileMail);
        final AppCompatImageButton myProfileSave = findViewById(R.id.myProfileSave);


        final AppCompatImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UserResult userResult = ConfigCache.userResult;
        final BaseRequest myProfileRequest = new BaseRequest(userResult);

        DictsFilter filter = new DictsFilter(dicts);
        genderDicts = filter.getDictsByType("Gender");
        educationDicts = filter.getDictsByType("Education");
        maritalDicts = filter.getDictsByType("Marital");
        myProfileRequest.setGender(filter.getValue("Gender",userResult.getGender()));
        myProfileRequest.setEducation(filter.getValue("Education",userResult.getEducation()));
        myProfileRequest.setMarital(filter.getValue("Marital",userResult.getMarital()));

        birthday.setText(userResult.getBirthday());
        gender.setText(userResult.getGender());
        education.setText(userResult.getEducation());
        marital.setText(userResult.getMarital());
        myProfileName.setText(userResult.getName());
        myProfileMail.setText(userResult.getEmail());

        final DialogInterface.OnClickListener genderClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = genderDicts.get(which);
                gender.setText(data.getName());
                myProfileRequest.setGender(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener educationClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = educationDicts.get(which);
                education.setText(data.getName());
                myProfileRequest.setEducation(data.getValue());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener maritalClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = maritalDicts.get(which);
                marital.setText(data.getName());
                myProfileRequest.setMarital(data.getValue());
                dialog.cancel();
            }
        };


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.myProfileBirthday) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String birthdayValue = year + "-" + month + "-" + dayOfMonth;
                            birthday.setText(birthdayValue);
                            myProfileRequest.setBirthday(birthdayValue);
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(
                            ProfileBasicInfoActivity.this,
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    dialog.show();
                } else if (id == R.id.myProfileGender) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileBasicInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileBasicInfoActivity.this, genderDicts, R.layout.list_view_item), -1, genderClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileEducation) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileBasicInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileBasicInfoActivity.this, educationDicts, R.layout.list_view_item), -1, educationClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileMarital) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileBasicInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(ProfileBasicInfoActivity.this, maritalDicts, R.layout.list_view_item), -1, maritalClickListener);
                    builder.create().show();
                } else if (id == R.id.myProfileSave) {
                    myProfileRequest.setName(Objects.requireNonNull(myProfileName.getText()).toString());
                    myProfileRequest.setEmail(Objects.requireNonNull(myProfileMail.getText()).toString());
                    String result = myProfileRequest.checked();
                    if (result != null) {
                        Toast.makeText(ProfileBasicInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {
                        show();
                        Call call = new HttpRequest().post(Apis.BASE_SAVE_URL, RequestBody.create(HttpRequest.JSON, JSON.toJSONString(myProfileRequest)));
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
        birthday.setOnClickListener(onClickListener);
        gender.setOnClickListener(onClickListener);
        education.setOnClickListener(onClickListener);
        marital.setOnClickListener(onClickListener);
        myProfileSave.setOnClickListener(onClickListener);
    }
}