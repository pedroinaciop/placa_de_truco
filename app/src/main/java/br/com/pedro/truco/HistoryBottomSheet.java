package br.com.pedro.truco;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;

import br.com.pedro.truco.recyclerview.adapter.HistoryAdapter;

public class HistoryBottomSheet extends BottomSheetDialogFragment {

    private final List<GameHistory> historyList;

    public HistoryBottomSheet(List<GameHistory> historyList) {
        this.historyList = historyList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        HistoryAdapter adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
