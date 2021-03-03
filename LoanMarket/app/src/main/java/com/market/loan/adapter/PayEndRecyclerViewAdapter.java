package com.market.loan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.market.loan.R;
import com.market.loan.bean.Vip;

import java.util.List;

public class PayEndRecyclerViewAdapter extends RecyclerView.Adapter<PayEndRecyclerViewAdapter.ViewHolder> {

    List<Vip> values;
    Context content;
    int resourceId;

    public List<Vip> getValues() {
        return values;
    }

    public void setValues(List<Vip> values) {
        this.values = values;
    }

    public PayEndRecyclerViewAdapter(Context context, List<Vip> vips, int resourceId) {
        this.values = vips;
        this.content = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(content).inflate(resourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Vip vip = values.get(position);
        Glide.with(content).load(vip.getIconUrl()).into(holder.vipHead);
        holder.vipName.setText(vip.getCashName());
        holder.vipAmount.setText(vip.getMinAmount());
        holder.vipMaxAmount.setText(vip.getMaxAmount());

        holder.payEndApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(vip.getDownloadUrl());
                intent.setData(content_url);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView vipHead;
        AppCompatTextView vipName;
        AppCompatTextView vipAmount;
        AppCompatTextView vipMaxAmount;
        AppCompatImageButton payEndApply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vipHead = itemView.findViewById(R.id.vipHead);
            vipName = itemView.findViewById(R.id.vipName);
            vipAmount = itemView.findViewById(R.id.vipAmount);
            vipMaxAmount = itemView.findViewById(R.id.vipMaxAmount);
            payEndApply = itemView.findViewById(R.id.payEndApply);
        }
    }
}

