package com.bird.x.xbird.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bird.x.xbird.model.Fall;
import com.bird.x.xbird.viewholder.FallViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FallRecyclerViewAdapter extends RecyclerView.Adapter<FallViewHolder> {

    private List<Fall> fallList;

    public FallRecyclerViewAdapter() {
        fallList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return fallList.size();
    }

    @NonNull
    @Override
    public FallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        return FallViewHolder.create(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FallViewHolder holder, int position) {
        final Fall place = fallList.get(position);

        holder.bind(place);
    }

    public void addItems(List<Fall> facts) {
        fallList.addAll(facts);
        notifyDataSetChanged();
    }

    public void addItem(Fall fall) {
        fallList.add(fall);
        notifyDataSetChanged();
    }
}