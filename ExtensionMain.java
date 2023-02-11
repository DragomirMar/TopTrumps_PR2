import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ExtensionMain {
    public static void main(String[] args)throws Exception{
        List<String> lines = SimpleCsvParser.readAllLinesFrom("src/cars.csv");

        List<VehicleCard> deck = new ArrayList<>();

        for(var line: lines){
            deck.add(SimpleCsvParser.parseLine(line));
        }

        Game g = new Game();

        for(var card: deck){
            g.addCard(card);
        }

        var playerA = new Player("Drago", "AverageStrategy");
        var playerB = new Player("Stefan");
        var playerC = new Player("Moris");

        g.addPlayer(playerA);
        g.addPlayer(playerB);
        g.addPlayer(playerC);

        for(int i=0; i<99; ++i){
            g.play();
        }

        g.writeStatistics(System.out);
    }
}
