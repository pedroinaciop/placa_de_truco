package br.com.pedro.truco;

public class Team {
    private final Integer ID;
    private String teamName;
    private Integer score;

    public Team(Integer ID, String teamName, Integer score) {
        this.ID = ID;
        this.teamName = teamName;
        this.score = score;
    }

    public Integer getID() {
        return ID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getScore() {
        return score;
    }

    public void resetScore(Integer score) {
        this.score = 0;
    }


    public void addScore(Integer quantity) {
        this.score += quantity;
    }

    public void reduceScore() {
        this.score -= 1;
    }

}
