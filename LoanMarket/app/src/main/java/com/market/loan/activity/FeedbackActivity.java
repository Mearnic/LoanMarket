package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.market.loan.BaseActivity;
import com.market.loan.R;
import com.market.loan.bean.ConfigResult;
import com.market.loan.constant.Apis;
import com.market.loan.constant.Toasts;
import com.market.loan.core.ConfigCache;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ConfigResult configResult = ConfigCache.configResult;
        String sysServiceEmail = configResult.getSysServiceEmail();
        AppCompatTextView feedBackMail = findViewById(R.id.feedBackMail);
        String mail = feedBackMail.getText().toString() + sysServiceEmail;
        feedBackMail.setText(mail);
        final AppCompatEditText messageBox = findViewById(R.id.messageBox);
        AppCompatImageButton sendMessage = findViewById(R.id.sendMessage);
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
                String content = Objects.requireNonNull(messageBox.getText()).toString();
                if (content.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, Toasts.CONTENT_IS_NOT_EMPTY, Toast.LENGTH_SHORT).show();
                    return;
                }
                show();
                Call call = new HttpRequest().post(Apis.FEEDBACK_URL, RequestBody.create(HttpRequest.JSON, "{content:" + content + "}"));
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
        };
        sendMessage.setOnClickListener(onClickListener);
    }
}