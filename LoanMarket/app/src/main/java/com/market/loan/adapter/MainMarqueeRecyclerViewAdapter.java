package com.market.loan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.market.loan.R;
import com.market.loan.bean.MarqueeResult;

import java.util.List;

public class MainMarqueeRecyclerViewAdapter extends RecyclerView.Adapter<MainMarqueeRecyclerViewAdapter.ViewHolder> {

    private List<MarqueeResult> values;

    public List<MarqueeResult> getValues() {
        return values;
    }

    public void setValues(List<MarqueeResult> values) {
        this.values = values;
    }

    Context content;
    int resourceId;

    public MainMarqueeRecyclerViewAdapter(Context context, List<MarqueeResult> limits, int resourceId) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MarqueeResult marqueeResult = values.get(position);
        holder.phoneText.setText(marqueeResult.getMobile());
        holder.text.setText(marqueeResult.getText());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView phoneText;
        AppCompatTextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneText = itemView.findViewById(R.id.marqueePhone);
            text = itemView.findViewById(R.id.marqueeText);
        }
    }
}

