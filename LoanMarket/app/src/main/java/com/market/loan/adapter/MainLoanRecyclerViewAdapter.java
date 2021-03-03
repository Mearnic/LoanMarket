package com.market.loan.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.market.loan.MainActivity;
import com.market.loan.R;
import com.market.loan.activity.LoginActivity;
import com.market.loan.activity.PayActivity;
import com.market.loan.bean.Duration;
import com.market.loan.bean.Limit;
import com.market.loan.tools.AmountFormat;

import java.util.List;

public class MainLoanRecyclerViewAdapter extends RecyclerView.Adapter<MainLoanRecyclerViewAdapter.ViewHolder> {

    List<Limit> values;
    Context content;
    int resourceId;

    public MainLoanRecyclerViewAdapter(Context context, List<Limit> limits, int resourceId) {
        this.values = limits;
        this.content = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(content).inflate(resourceId, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int index = position;
        final Limit limit = values.get(position);
        holder.amountText.setText(AmountFormat.format(limit.getAmount()));
        List<Duration> durations = limit.getDurations();
        holder.daysText.setText(durations.get(0).getDuration().split(" ")[0] + "-" + durations.get(durations.size() - 1).getDuration());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) content;
                Intent intent = new Intent(mainActivity, mainActivity.selectorActivity());
                intent.putExtra("index", index);
                mainActivity.startActivity(intent);
            }
        };
        holder.layout.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView amountText;
        AppCompatTextView daysText;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountText = itemView.findViewById(R.id.loanAmount);
            daysText = itemView.findViewById(R.id.loanDays);
            daysText = itemView.findViewById(R.id.loanDays);
            layout = itemView.findViewById(R.id.loanListItem);
        }
    }
}

