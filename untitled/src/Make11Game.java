import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Make11Game {
    private static int calculateScore(Card card1, Card card2) {
        String[] pictureCards = {"Jack", "Queen", "King"};
        int score = 0;

        if (isPictureCard(card1)) {
            score += 10;
        } else if ("Ace".equals(card1.rank)) {
            score += 1; // Handle Ace separately
        } else {
            score += Integer.parseInt(card1.rank);
        }

        if (isPictureCard(card2)) {
            score += 10;
        } else if ("Ace".equals(card2.rank)) {
            score += 1; // Handle Ace separately
        } else {
            score += Integer.parseInt(card2.rank);
        }

        return score;
    }

    private static boolean isPictureCard(Card card) {
        String[] pictureCards = {"Jack", "Queen", "King"};
        for (String pictureCard : pictureCards) {
            if (card.rank.equals(pictureCard)) {
                return true;
            }
        }
        return false;
    }

    private static boolean make11(Card playerCard, Card computerCard) {
        return calculateScore(playerCard, computerCard) == 11;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Make 11!\nIf you can't make 11, select a card of the same suit to play again.");
        Deck deck = new Deck();
        int totalScore = 0;
        Scanner scanner = new Scanner(System.in);

        outerLoop:
        while (deck.size() >= 6) {
            int playerScore = 0;
            ArrayList<Card> playerHand = deck.dealCards(5);
            Card computerCard = deck.dealCard();

            System.out.println("\nPlayer's hand:");
            for (int i = 0; i < playerHand.size(); i++) {
                System.out.println((i + 1) + ". " + playerHand.get(i));
            }
            System.out.println("\nComputer's card:");
            System.out.println(computerCard);

            int playerChoice;
            Card selectedCard;

            while (true) {
                try {
                    System.out.print("Select a card to Make 11 (enter the index): ");
                    playerChoice = scanner.nextInt();

                    if (playerChoice < 1 || playerChoice > playerHand.size()) {
                        throw new InputMismatchException("Invalid input. Please enter a valid index.");
                    }

                    selectedCard = playerHand.get(playerChoice - 1);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            if (make11(selectedCard, computerCard)) {
                System.out.println("Make 11! You scored a point.");

                playerScore++;

                // Replace computer's card with a new one from the deck
                computerCard = deck.dealCard();

                // Replace the selected card in the player's hand with a new one from the deck
                int selectedIndex = playerHand.indexOf(selectedCard);
                if (selectedIndex != -1) {
                    playerHand.set(selectedIndex, deck.dealCard());
                }
            } else {
                System.out.println("You didn't make 11. You can continue by playing a card of the same suit.");

                char playSameSuit;
                while (true) {
                    try {
                        // Check if the player wants to continue by playing a card of the same suit
                        System.out.print("Do you want to play a card of the same suit? (y/n): ");
                        playSameSuit = scanner.next().toLowerCase().charAt(0);

                        if (playSameSuit != 'y' && playSameSuit != 'n') {
                            throw new InputMismatchException("Invalid input. Please enter 'y' or 'n'.");
                        }

                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                }

                if (playSameSuit != 'y') {
                    System.out.println("Game over. You chose not to continue.");
                    break outerLoop;
                }
            }

            totalScore += playerScore;
            System.out.println("Round Score: " + playerScore);
            System.out.println("Total Score: " + totalScore);
        }
    }
}
