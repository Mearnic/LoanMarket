package com.market.loan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import java.util.Objects;

public class NoBarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            Objects.requireNonNull(supportActionBar).hide();
        }
    }
}
