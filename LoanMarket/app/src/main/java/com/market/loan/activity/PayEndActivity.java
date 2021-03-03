package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.market.loan.BaseActivity;
import com.market.loan.MainActivity;
import com.market.loan.R;
import com.market.loan.adapter.PayEndRecyclerViewAdapter;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.bean.Vip;
import com.market.loan.constant.Status;
import com.market.loan.model.MainViewModel;

import java.util.List;

public class PayEndActivity extends BaseActivity {

    private MainViewModel mainViewModel;
    PayEndRecyclerViewAdapter adapter;
    List<Vip> vips = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_end);

        final AppCompatImageButton moreLoan = findViewById(R.id.getMoreLoan);

        AppCompatImageButton moneyPackageBtn = findViewById(R.id.moneyPackageBtn);
        AppCompatImageButton selfInfoBtn = findViewById(R.id.selfInfoBtn);

        View.OnClickListener bottomClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Class<?> activityClass = null;
                if (id == R.id.moneyPackageBtn){
                    activityClass = MainActivity.class;
                }else if(id == R.id.selfInfoBtn){
                    activityClass = MyPageActivity.class;
                }
                Intent intent = new Intent(PayEndActivity.this, activityClass);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        };
        moneyPackageBtn.setOnClickListener(bottomClick);
        selfInfoBtn.setOnClickListener(bottomClick);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                dismiss();
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    vips = result.getData().getViplist();
                    List<Vip> vipsTemp = vips;
                    if (vips.size() > 4) {
                        vipsTemp = vips.subList(0, 4);
                    }
                    loadLoan(vipsTemp);
                } else if (result.getCode().equals(Status.ACCESS_DENIED_CODE)){
                    Intent intent = new Intent(PayEndActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });show();
        mainViewModel.getProduct();


        moreLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Vip> values = adapter.getValues();
                int size = values.size();
                adapter.setValues(vips);
                adapter.notifyItemRangeInserted(size, vips.size() - size);
                moreLoan.setVisibility(View.GONE);
            }
        });
    }

    private void loadLoan(List<Vip> vipList) {
        RecyclerView loanListView = findViewById(R.id.payEndLoanList);
        loanListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PayEndRecyclerViewAdapter(PayEndActivity.this, vipList, R.layout.activity_pay_list);
        loanListView.setAdapter(adapter);
    }
}