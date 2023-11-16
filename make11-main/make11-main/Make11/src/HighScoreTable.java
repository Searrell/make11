import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.Serializable;

public class HighScoreTable {
    private static final int MAX_SCORES = 5;
    private ArrayList<HighScoreEntry> highScores;
    private String fileName;

    public HighScoreTable(String fileName) {
        this.fileName = fileName;
        this.highScores = loadHighScores();
    }

    public void addHighScore(String playerName, int score) {
        highScores.add(new HighScoreEntry(playerName, score));
        Collections.sort(highScores, Collections.reverseOrder());

        // Keep only the top MAX_SCORES scores
        if (highScores.size() > MAX_SCORES) {
            highScores.subList(MAX_SCORES, highScores.size()).clear();
        }

        saveHighScores();
    }

    public boolean isNewHighScore(int score) {
        return highScores.size() < MAX_SCORES || score > highScores.get(highScores.size() - 1).getScore();
    }

    public void displayHighScores() {
        System.out.println("High Scores:");
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println((i + 1) + ". " + highScores.get(i));
        }
        System.out.println();  // Empty line for better formatting
    }

    private ArrayList<HighScoreEntry> loadHighScores() {
        ArrayList<HighScoreEntry> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String playerName = parts[0].trim();
                int score = Integer.parseInt(parts[1].trim());
                scores.add(new HighScoreEntry(playerName, score));
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, initialize with an empty list
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

    private void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (HighScoreEntry entry : highScores) {
                writer.write(entry.getPlayerName() + ": " + entry.getScore() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}