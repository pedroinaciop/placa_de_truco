package br.com.pedro.truco;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameHistory {
    private String teamName;
    private LocalDateTime timePlay;
    private String typeScore;
    private Integer quantity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameHistory(String teamName, LocalDateTime timePlay, String typeScore, Integer quantity) {
        this.teamName = teamName;
        this.timePlay = timePlay;
        this.typeScore = typeScore;
        this.quantity = quantity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDataHoraFormated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timePlay.format(formatter);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + typeScore + ") " + quantity + " ponto(s) para '" + teamName + "' - " + timePlay;
    }
}
