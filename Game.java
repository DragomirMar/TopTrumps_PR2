// insert package and imports here
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Game {


    public static class SuccessFailCounter {
        public int success = 0;
        public int failure = 0;
    }

    public Map<Player, SuccessFailCounter> playerStats = new HashMap<>();


    private final boolean shuffle;
    private int currentPlayer = -1;
    private final List<Player> players = new ArrayList<>();
    private final List<VehicleCard> vehicleCards = new ArrayList<>();
    private int round = 0;


    public Game() {
        this(true);
    }

    public Game(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean addPlayer(final Player player) {
        return !this.isGameRunning() && !this.players.contains(player) && this.players.add(player);
    }

    public boolean addCard(final VehicleCard vehicleCard) {
        if (!this.vehicleCards.contains(vehicleCard)) {
            return this.vehicleCards.add(vehicleCard);
        }
        return false;
    }

    private boolean hasEnded() {
        return !this.isGameRunning() || this.players.stream().anyMatch(p -> p.getDeck().containsAll(this.vehicleCards))
                || this.round > 100;
    }

    private boolean isGameRunning() {
        return this.currentPlayer != -1;
    }

    private void endGame() {
        this.currentPlayer = -1;
        this.round = 0;
        this.players.forEach(Player::clearDeck);
    }

    public Player play() {
        // preconditions
        if (players.size() <= 1)
            throw new IllegalArgumentException("the game requires at least two players.");
        if (this.vehicleCards.size() < this.players.size())
            throw new IllegalStateException("not enough cards for all players.");

        // mischen
        if (this.shuffle)
            Collections.shuffle(this.vehicleCards);

        // alle karten werden vergeben
        Map<Integer, List<VehicleCard>> groupedCards = this.vehicleCards.stream().collect(Collectors.groupingBy(v -> this.vehicleCards.indexOf(v) % players.size()));
        groupedCards.forEach((key, value) -> this.players.get(key).addCards(value));

        currentPlayer = 0;
        while (!hasEnded()) {
            playCurrentRound();
        }

        final Player winner = getCurrentPlayer();
        endGame();
        return winner;
    }

    protected boolean playCurrentRound() {
        ++this.round;
        final Player currentPlayer = getCurrentPlayer();
        final VehicleCard.Category category = currentPlayer.chooseNextCategory();
        return evaluate(category);
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentPlayer % this.players.size());
    }

    private boolean evaluate(VehicleCard.Category category) {
        Map<VehicleCard, Player> cardPlayerMap = new TreeMap<>(Comparator.comparingDouble(vehicleCard -> category.isInverted() ? vehicleCard.getCategories().get(category) : -vehicleCard.getCategories().get(category)));

        for (Player player : this.players) {
            cardPlayerMap.put(player.playNextCard(), player);
        }

        Player winner = cardPlayerMap.values().stream().findFirst().get();
        Player currentPlayer = players.get(this.currentPlayer);

        final SuccessFailCounter successFailCounter = this.playerStats.getOrDefault(currentPlayer, new SuccessFailCounter());

        if (winner == currentPlayer) ++successFailCounter.success;
        else ++successFailCounter.failure;

        this.playerStats.put(currentPlayer, successFailCounter);

        boolean currentPlayerWon = true;
        if (winner != getCurrentPlayer()) {
            this.currentPlayer = this.players.indexOf(winner);
            currentPlayerWon = false;

        }

        winner.addCards(cardPlayerMap.keySet());
        return currentPlayerWon;
    }


    public void writeStatistics(final OutputStream outputStream) throws IOException {

        List<Object[]> rows = new ArrayList<>();

        // create the rows of the table...
        for(Map.Entry<Player, SuccessFailCounter> player: playerStats.entrySet()){
            Object[] array = new Object[5];
            array[0] = player.getKey().getName();
            array[1] = player.getKey().getStrategy();
            array[2] = player.getValue().success;
            array[3] = player.getValue().failure;
            Double rate = (player.getValue().success*1.0 / (player.getValue().success + player.getValue().failure));
            array[4] = rate;

            rows.add(array);
        }


        SimpleTablePrinter.print(rows, outputStream);
    }

}
