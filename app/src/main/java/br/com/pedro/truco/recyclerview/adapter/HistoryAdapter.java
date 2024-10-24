package br.com.pedro.truco.recyclerview.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.com.pedro.truco.GameHistory;
import br.com.pedro.truco.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<GameHistory> historyList;

    public HistoryAdapter(List<GameHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameHistory historyItem = historyList.get(position);

        holder.historyTextView.setText(historyItem.toString());
        if (historyItem.toString().contains("(-)")) {
            holder.historyTextView.setTextColor(Color.argb(250,192,65,78));
        } else if (historyItem.toString().contains("(+)")) {
            holder.historyTextView.setTextColor(Color.argb(250,31,152,106));
        }
    }

    @Override
    public int getItemCount() {
        return historyList != null ? historyList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView historyTextView;
        public TextView recyclerTextView;

        public ViewHolder(View view) {
            super(view);
            historyTextView = view.findViewById(android.R.id.text1);
            recyclerTextView = view.findViewById(R.id.recyclerTextView);
        }
    }
}
