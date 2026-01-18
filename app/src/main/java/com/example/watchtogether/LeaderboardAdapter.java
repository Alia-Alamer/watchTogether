package com.example.watchtogether;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvRank.setText("#" + (position + 1));
        holder.tvTitle.setText(row.title);
        holder.tvLikes.setText("Likes: " + row.likes);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvRank, tvTitle, tvLikes;

        VH(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tvRank);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvLikes = itemView.findViewById(R.id.tvLikes);
        }
    }
}
