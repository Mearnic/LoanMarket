package com.market.loan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.market.loan.R;
import com.market.loan.bean.ConfigData;

import java.util.List;

public class DialogItemAdapter extends ArrayAdapter<ConfigData> {

    List<ConfigData> values;
    Context context;
    int resourceId;

    public DialogItemAdapter(@NonNull Context context, List<ConfigData> dicts, int resource) {
        super(context, resource);
        this.resourceId = resource;
        this.context = context;
        this.values = dicts;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ConfigData data = getItem(position);
        view = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        assert data != null;
        holder.text.setText(data.getName());
        return view;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public ConfigData getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        AppCompatTextView text;

        public ViewHolder(@NonNull View itemView) {
            text = itemView.findViewById(R.id.list_item_text);
        }
    }
}