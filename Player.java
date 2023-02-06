import java.util.*;

public class Player implements Comparable<Player>{
    private String name;
    private Queue<VehicleCard> deck = new ArrayDeque<>();

    public Player(final String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        int score = 0;
        for (var card : deck) {
            score += card.totalBonus();
        }

        return score;
    }

    public void addCards(final Collection<VehicleCard> cards) {
        deck.addAll(cards);
    }

    public void addCard(final VehicleCard card) {
        deck.add(card);
    }

    public void clearDeck() {
        deck.clear();
    }

    public List<VehicleCard> getDeck() {
        return new ArrayList<>(deck);
    }

    protected VehicleCard peekNextCard() {
        return deck.peek();
    }

    public VehicleCard playNextCard() {
        return deck.poll();
    }

    @Override
    public int compareTo(final Player other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return Objects.equals(name.toLowerCase(), player.name.toLowerCase()) && Objects.equals(getDeck(), player.getDeck());
    }

    @Override
    public String toString() {
        String cards = "";
        for (var card : deck) {
            if (card instanceof FoilVehicleCard) {
                var foilCard = (FoilVehicleCard) card;
                cards += foilCard.toString();
            } else {
                cards += card.toString();
            }
            cards += "\n";
        }

        return name + "(" + getScore() + "):" + "\n" + cards;
    }

    public boolean challengePlayer(Player p) {
        if (p == null || p.equals(this)) {
            throw new IllegalArgumentException("Player is null or this.");
        }

        List<VehicleCard> myPlayedCards = new ArrayList<>();
        List<VehicleCard> yourPlayedCards = new ArrayList<>();
        while (true) {
            if (this.getDeck().size() == 0 || p.getDeck().size() == 0) {
                this.addCards(myPlayedCards);
                p.addCards(yourPlayedCards);
                return false;
            }

            var myCard = this.playNextCard();
            var yourCard = p.playNextCard();

            if (myCard.totalBonus() > yourCard.totalBonus()) {
                this.addCard(myCard);
                this.addCard(yourCard);
                this.addCards(myPlayedCards);
                this.addCards(yourPlayedCards);
                return true;
            } else if (myCard.totalBonus() < yourCard.totalBonus()) {
                p.addCard(myCard);
                p.addCard(yourCard);
                p.addCards(myPlayedCards);
                p.addCards(yourPlayedCards);
                return false;
            } else {
                myPlayedCards.add(myCard);
                yourPlayedCards.add(yourCard);
            }
        }
    }

    public static Comparator<Player> compareByScore() {
        return Comparator.comparingInt(Player::getScore);
    }

    public static Comparator<Player> compareByDeckSize() {
        return (p1, p2) -> p2.getDeck().size() - p1.getDeck().size();
    }
}