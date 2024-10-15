package br.com.pedro.truco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Team time1;
    private Team time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time1 = new Team(1, "Nós", 0);
        time2 = new Team(2, "Eles", 0);
        configureScore(time1, time2);
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

        buttonResetScore(time1, time2);
        resetScore(time1, time2);
    }

    private void reduceScore(Team time, ImageButton addScoreButton, TextView scoreView) {
        addScoreButton.setOnClickListener(v -> {
            if (time.getScore() > 0) {
                time.reduceScore();
            }
            scoreView.setText(String.valueOf(time.getScore()));
        });
    }

    private void addScore(Team time, View addScoreButton, TextView scoreView, int quantity) {
        addScoreButton.setOnClickListener(v -> {
            time.addScore(quantity);
            if (time.getScore() >= 12) {
                victoryAlert(time.getTeamName());
            }
            scoreView.setText(String.valueOf(time.getScore()));
        });
    }

    private void victoryAlert(String teamName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);

        builder.setTitle("Parabéns!");
        builder.setMessage("O time '" + teamName + "' ganhou a partida.");

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

    private void buttonResetScore(Team time1, Team time2) {
        ImageButton button_reset_score = findViewById(R.id.reset_game_button);
        button_reset_score.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);

            builder.setTitle("Deseja zerar o placar?");

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
        });
    }

    private void resetScore(Team time1, Team time2) {
        TextView firstTeamScore = findViewById(R.id.first_team_score);
        TextView secondTeamScore = findViewById(R.id.second_team_score);
        firstTeamScore.setText("0");
        secondTeamScore.setText("0");

        time1.resetScore(0);
        time2.resetScore(0);
    }

    private void showAlertWithInput(Team winningTeam) {
        final EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);

        builder.setTitle("Digite o novo nome do time:");
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