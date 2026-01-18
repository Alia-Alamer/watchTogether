package com.example.watchtogether;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.VH> {

    public static class Row {
        public final String title;
        public final int likes;

        public Row(String title, int likes) {
            this.title = title;
            this.likes = likes;
        }
    }

    private final List<Row> items;

    public LeaderboardAdapter(List<Row> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Row row = items.get(position);

        holder.tvRankCircle.setText(String.valueOf(position + 1));
        holder.tvTitle.setText(row.title);
        holder.tvLikes.setText(row.likes + " Likes");

        int maxLikes = 0;
        for (Row r : items) maxLikes = Math.max(maxLikes, r.likes);

        int progress = (maxLikes == 0) ? 0 : (int) Math.round((row.likes * 100.0) / maxLikes);
        holder.pbLikes.setProgress(progress);

        if (position == 0) {
            holder.itemRoot.setBackgroundResource(R.drawable.bg_result_item_selected);
            holder.tvRankCircle.setBackgroundResource(R.drawable.bg_circle_btn_orange);
        } else {
            holder.itemRoot.setBackgroundResource(R.drawable.bg_result_item_normal);
            holder.tvRankCircle.setBackgroundResource(R.drawable.bg_circle_btn_grey);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        View itemRoot;
        TextView tvRankCircle, tvTitle, tvLikes;
        ProgressBar pbLikes;

        VH(@NonNull View itemView) {
            super(itemView);
            itemRoot = itemView.findViewById(R.id.itemRoot);
            tvRankCircle = itemView.findViewById(R.id.tvRankCircle);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            pbLikes = itemView.findViewById(R.id.pbLikes);
        }
    }
}
