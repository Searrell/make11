import java.io.Serializable;

public class HighScoreEntry implements Comparable<HighScoreEntry>, Serializable {
    private String playerName;
    private int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(this.score, other.score);
    }

    @Override
    public String toString() {
        return playerName + ": " + score;
    }
}
