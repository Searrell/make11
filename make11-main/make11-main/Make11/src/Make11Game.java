import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Make11Game {
    private Deck deck;
    private ArrayList<Card> playerHand;
    private int totalScore;
    private int roundCount;
    private HighScoreTable highScoreTable;

    public Make11Game() {
        this.deck = new Deck();
        this.playerHand = new ArrayList<>();
        this.totalScore = 0;
        this.roundCount = 0;
        this.highScoreTable = new HighScoreTable("highscores.txt");

        // Deal initial 5 cards to the player
        for (int i = 0; i < 5; i++) {
            playerHand.add(deck.deal());
        }
    }

    public void playRound() {
        roundCount++;
        System.out.println("Round " + roundCount);

        // Check if the deck is empty
        if (deck.isEmpty()) {
            System.out.println("The deck is empty. Game over.");
            displayTotalScore();
            highScoreTable.displayHighScores();
            System.out.println("Thanks for playing!");
            System.exit(0);
        }

        // Deal a card from the deck for the computer
        Card computerCard = deck.deal();
        System.out.println("Computer's card: " + computerCard);

        // Display player's hand
        System.out.println("Your hand:");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.println(i + 1 + ": " + playerHand.get(i));
        }

        // Get user input for card selection with validation
        int selectedCardIndex = -1;
        do {
            selectedCardIndex = getValidInput("Select a card to Make 11 (enter the corresponding number): ");
        } while (selectedCardIndex < 1 || selectedCardIndex > playerHand.size());

        // Check if Make 11 is achieved
        if (isMake11(playerHand.get(selectedCardIndex - 1), computerCard)) {
            System.out.println("Make 11! You scored 1 point.");

            // Opt to play picture cards from the hand and replace them
            if (optToPlayPictureCards()) {
                replacePictureCards();
            } else {
                // Replace player's card with a new one from the deck
                playerHand.set(selectedCardIndex - 1, deck.deal());
            }

            totalScore++;
        } else {
            // Check if a card of the same suit can be played
            if (playerHand.get(selectedCardIndex - 1).getSuit().equals(computerCard.getSuit())) {
                System.out.println("Same suit! The game continues, but no point is scored.");
                playerHand.set(selectedCardIndex - 1, deck.deal());
            } else {
                System.out.println("You cannot Make 11 or play a card of the same suit. Game over.");
                displayTotalScore();
                highScoreTable.displayHighScores();
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        }
    }

    private int getValidInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // consume invalid input
            }
        }

        return input;
    }

    private boolean optToPlayPictureCards() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play picture cards as well? (yes/no): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes");
    }

    private void replacePictureCards() {
        // Replace picture cards in the hand with new cards from the deck
        for (int i = 0; i < playerHand.size(); i++) {
            String rank = playerHand.get(i).getRank();
            if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
                playerHand.set(i, deck.deal());
            }
        }
    }

    private boolean isMake11(Card playerCard, Card computerCard) {
        int playerRank = getPlayerRankValue(playerCard);
        int computerRank = getPlayerRankValue(computerCard);
        return (playerRank + computerRank) == 11;
    }

    private int getPlayerRankValue(Card card) {
        String rank = card.getRank();
        if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
            return 10;
        } else if (rank.equals("Ace")) {
            return 1;
        } else {
            return Integer.parseInt(rank);
        }
    }

    private void displayTotalScore() {
        System.out.println("Total Score: " + totalScore);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Make 11!");

        Make11Game game = new Make11Game();

        // Continue playing rounds until the player decides to stop
        boolean continuePlaying = true;
        Scanner scanner = new Scanner(System.in);

        while (continuePlaying) {
            game.highScoreTable.displayHighScores(); // Display high scores at the start of each round
            game.playRound();

            // Check if the player wants to continue
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to play another round? (yes/no): ");
                String response = scanner.next().toLowerCase();

                if (response.equals("yes")) {
                    continuePlaying = true;
                    validResponse = true;
                } else if (response.equals("no")) {
                    continuePlaying = false;
                    validResponse = true;
                } else {
                    System.out.println("Error: Please enter 'yes' or 'no'.");
                }
            }
        }

        // Get user input for name if a new high score is achieved
        if (game.highScoreTable.isNewHighScore(game.totalScore)) {
            System.out.print("Congratulations! You achieved a high score. Enter your name: ");
            String playerName = scanner.next();
            game.highScoreTable.addHighScore(playerName, game.totalScore);
        }

        // Display final high scores
        game.highScoreTable.displayHighScores();

        System.out.println("Thanks for playing!");
    }
}
