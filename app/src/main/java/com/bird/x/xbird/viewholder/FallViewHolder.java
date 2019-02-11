package com.bird.x.xbird.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bird.x.xbird.R;
import com.bird.x.xbird.databinding.ListItemBinding;

import com.bird.x.xbird.model.Fall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FallViewHolder extends RecyclerView.ViewHolder {

    private ListItemBinding binding;
    private final String PATTERN = "dd/MM/yy HH:mm:ss";

    public FallViewHolder(ListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Fall fall) {
        String text;

        text = String.format(Locale.US, binding.getRoot().getContext().getString(R.string
                .record_fall_text).replace("{placeholder}", "%s"), fall.getMilliseconds());

        binding.textViewFallTime.setText(text);

        Date currentDate = new Date(fall.getTimeRecorded());
        DateFormat df = new SimpleDateFormat(PATTERN);

        text = String.format(Locale.US, binding.getRoot().getContext().getString(R.string
                .record_fall_timestamp).replace("{placeholder}", "%s"), df.format(currentDate));

        binding.textViewRecorded.setText(text);

    }

    public static FallViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
        ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent,
                false);

        return new FallViewHolder(binding);
    }
}

