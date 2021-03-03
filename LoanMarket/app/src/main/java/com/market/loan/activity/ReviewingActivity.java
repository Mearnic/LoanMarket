package com.market.loan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.market.loan.R;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.Limit;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Status;
import com.market.loan.constant.Toasts;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.ReviewingViewModel;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ReviewingActivity extends AppCompatActivity {

    private ReviewingViewModel reviewingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewing);
        reviewingViewModel = new ViewModelProvider(this).get(ReviewingViewModel.class);

        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        AppCompatTextView reviewingText = findViewById(R.id.reviewingText);
        AppCompatImageButton reviewingNext = findViewById(R.id.reviewingNext);

        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            reviewingText.setText(configResult.getTipsProcessing());
        }

        reviewingNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewingViewModel.request();
                Toast.makeText(ReviewingActivity.this, Toasts.WAIT_TIME, Toast.LENGTH_SHORT).show();
            }
        });

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                reviewingViewModel.request();
            }
        }, 1000, 3000);

        reviewingViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ProductResult resultData = result.getData();
                    if (Integer.parseInt(resultData.getPhase()) >= 2) {
                        timer.cancel();
                        List<Limit> limits = resultData.getLimits();
                        Limit limit = limits.get(limits.size() - 1);
                        ConfigCache.amount = limit.getAmount();
                        Intent intent = new Intent(ReviewingActivity.this, ApprovedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}