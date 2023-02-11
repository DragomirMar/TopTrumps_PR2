import java.util.*;

public class Player implements Comparable<Player>, Strategy{
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

    //ZUSATZAUFGABE , ADDITIONAL TASK

    private List<VehicleCard> knownCards = new ArrayList<>();
    private String strategy = "RndStrategy";

    public String getStrategy(){return strategy;}
    public void setStrategy(String newStrategy){strategy = newStrategy;}
    public VehicleCard.Category randomStrategy(){
        Random rand = new Random();
        int categoryNumber = rand.nextInt(7);
        return VehicleCard.Category.values()[categoryNumber];
    }

    public VehicleCard.Category averageStrategy(VehicleCard vehicleCard){
        Map<VehicleCard.Category, Double> average = new HashMap<>();
        for (int i = 0; i <= 6; ++i) {
            VehicleCard.Category category = VehicleCard.Category.values()[i];
            Double avg = 0.0;
            for (var card : knownCards) {
                avg += card.getCategories().get(category);
            }
            avg = avg / knownCards.size();
            average.put(category, avg);
        }
        for (int i = 0; i <= 6; ++i) {
            VehicleCard.Category category = VehicleCard.Category.values()[i];
            if (category.isInverted()) {
                if (vehicleCard.getCategories().get(category) < average.get(category)) {
                    return category;
                } else {
                    if (vehicleCard.getCategories().get(category) > average.get(category)) {
                        return category;
                    }
                }
            }
        }
        return randomStrategy();
    }
    public VehicleCard.Category chooseStrategy(final VehicleCard vehicleCard) {
        switch(strategy){
            case "AverageStrategy": return averageStrategy(vehicleCard);
            default: return randomStrategy();
        }
    }

    public VehicleCard.Category chooseNextCategory(){
        return chooseStrategy(peekNextCard());
    }

    public Player(final String name, List<VehicleCard> knownCards, String strategy){
        this(name);
        this.knownCards = knownCards;
        this.strategy = strategy;
    }
    public Player(final String name, String strategy){
        this(name);
        this.strategy = strategy;
    }
}