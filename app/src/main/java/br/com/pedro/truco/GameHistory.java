package br.com.pedro.truco;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class GameHistory {
    private final String teamName;
    private final String timePlay;
    private final String typeScore;
    private final Integer quantity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameHistory(String teamName, String timePlay, String typeScore, Integer quantity) {
        this.teamName = teamName;
        this.timePlay = timePlay;
        this.typeScore = typeScore;
        this.quantity = quantity;
    }


    @NonNull
    @Override
    public String toString() {
        return "(" + typeScore + ") " + quantity + " ponto(s) para '" + teamName + "' - " + timePlay;
    }
}
