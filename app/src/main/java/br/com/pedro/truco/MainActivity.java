package br.com.pedro.truco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private Team time1;
    private Team time2;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    List<GameHistory> gameHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time1 = new Team(1, "Nós", 0);
        time2 = new Team(2, "Eles", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            configureScore(time1, time2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset_button) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);
            builder.setTitle("DESEJA ZERAR O PLACAR?");

            builder.setNegativeButton("NÃO", (dialog, which) -> {
                dialog.cancel();
            });

            builder.setPositiveButton("SIM", (dialog, which) -> {
                resetScore(time1, time2);
            });

            AlertDialog alerta = builder.create();
            alerta.setOnShowListener(dialog -> {
                Button negativeButton = alerta.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(getResources().getColor(R.color.red));
                negativeButton.setTypeface(null, Typeface.BOLD);

                Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(getResources().getColor(R.color.green));
                positiveButton.setTypeface(null, Typeface.BOLD);
            });

            alerta.setCanceledOnTouchOutside(false);
            alerta.show();

            return true;
        } else if (item.getItemId() == R.id.showBottomSheet) {
            try {
                Log.d("MainActivity", "Tentando mostrar BottomSheetDialog");

                HistoryBottomSheet bottomSheet = new HistoryBottomSheet(gameHistoryList);
                bottomSheet.show(getSupportFragmentManager(), "HistoryBottomSheet");

                Log.d("MainActivity", "BottomSheetDialog configurado, tentando mostrar");

            } catch (Exception e) {
                Log.e("MainActivity", "Erro ao mostrar BottomSheetDialog: ", e);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureScore(Team time1, Team time2) {
        ImageButton button_add_score1 = findViewById(R.id.first_button_add);
        ImageButton button_add_score2 = findViewById(R.id.second_button_add);
        ImageButton reduceScore1 = findViewById(R.id.first_button_reduce);
        ImageButton reduceScore2 = findViewById(R.id.second_button_reduce);

        Button button_truco_1 = findViewById(R.id.first_button_truco_add_3);
        Button button_truco_2 = findViewById(R.id.second_button_truco_add_3);

        TextView firstTeamScore = findViewById(R.id.first_team_score);
        TextView secondTeamScore = findViewById(R.id.second_team_score);

        TextView firstTeamNameText = findViewById(R.id.first_team_name);
        firstTeamNameText.setOnClickListener(v -> showAlertWithInput(time1));

        TextView secondTeamNameText = findViewById(R.id.second_team_name);
        secondTeamNameText.setOnClickListener(v -> showAlertWithInput(time2));

        addScore(time1, button_add_score1, firstTeamScore, 1);
        addScore(time2, button_add_score2, secondTeamScore, 1);
        addScore(time1, button_truco_1, firstTeamScore, 3);
        addScore(time2, button_truco_2, secondTeamScore, 3);

        reduceScore(time1, reduceScore1, firstTeamScore);
        reduceScore(time2, reduceScore2, secondTeamScore);

        resetScore(time1, time2);
    }

    private void reduceScore(Team time, ImageButton addScoreButton, TextView scoreView) {
        addScoreButton.setOnClickListener(v -> {
            if (time.getScore() > 0) {
                time.reduceScore();

                LocalDateTime now = LocalDateTime.now();
                String formattedDateTime = now.format(formatter);

                GameHistory gameHistory = new GameHistory(time.getTeamName(), formattedDateTime, "-", 1);
                gameHistoryList.add(gameHistory);
                Log.d("Reduce", "Lista" + gameHistory);
            }
            scoreView.setText(String.valueOf(time.getScore()));
        });
    }

    private void addScore(Team time, View addScoreButton, TextView scoreView, int quantity) {
        addScoreButton.setOnClickListener(v -> {
            time.addScore(quantity);

            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(formatter);

            GameHistory gameHistory = new GameHistory(time.getTeamName(), formattedDateTime, "+", quantity);
            gameHistoryList.add(gameHistory);
            Log.d("Add", "Lista" + gameHistory);

            if (time.getScore() >= 12) {
                victoryAlert(time.getTeamName());
            }
            scoreView.setText(String.valueOf(time.getScore()));
        });
    }

    private void victoryAlert(String teamName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);

        builder.setTitle("PARABÉNS!");
        builder.setMessage("O TIME '" + teamName.toUpperCase() + "' GANHOU A PARTIDA.");

        builder.setPositiveButton("OK", (dialog, which) -> {
            resetScore(time1, time2);
        });

        AlertDialog alerta = builder.create();

        alerta.setOnShowListener(dialog -> {
            Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.green));
            positiveButton.setTypeface(null, Typeface.BOLD);
        });

        alerta.setCanceledOnTouchOutside(false);
        alerta.show();
    }

    private void resetScore(Team time1, Team time2) {
        TextView firstTeamScore = findViewById(R.id.first_team_score);
        TextView secondTeamScore = findViewById(R.id.second_team_score);
        firstTeamScore.setText("0");
        secondTeamScore.setText("0");

        time1.resetScore(0);
        time2.resetScore(0);
        gameHistoryList.clear();
    }

    private void showAlertWithInput(Team winningTeam) {
        final EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);

        builder.setTitle("DIGITE O NOVO NOME DO TIME: ");
        builder.setView(input);

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userInput = input.getText().toString();
                if (userInput.isEmpty()) {
                    userInput = "Nome";
                } else if (userInput.contains(" ")) {
                    userInput = userInput.replaceAll(" ", "");
                } else if (userInput.length() > 6) {
                    userInput = "Nome";
                }
                TextView teamNameTextView = (winningTeam.getID() == 1) ? findViewById(R.id.first_team_name) : findViewById(R.id.second_team_name);
                winningTeam.setTeamName(userInput);
                teamNameTextView.setText(userInput);
            }
        });

        AlertDialog alerta = builder.create();
        alerta.setOnShowListener(dialog -> {
            Button negativeButton = alerta.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(getResources().getColor(R.color.red));
            negativeButton.setTypeface(null, Typeface.BOLD);

            Button positiveButton = alerta.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.green));
            positiveButton.setTypeface(null, Typeface.BOLD);
        });

        alerta.setCanceledOnTouchOutside(false);
        alerta.show();
    }
}