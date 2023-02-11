import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) throws IOException {
        var categories1 = VehicleCard.newMap(
                5.0,
                1.0,
                1.0,
                1.0,
                20,
                20,
                1.0);
        var categories2 = VehicleCard.newMap(
                2.0,
                1.0,
                1.0,
                1.0,
                20,
                20,
                1.0);

        var vehicleCard1 = new VehicleCard("Card1", categories1);
        var vehicleCard2 = new VehicleCard("Card2", categories2);
        var foilVehicleCard3 = new FoilVehicleCard("Card3", categories1, Set.of(VehicleCard.Category.ECONOMY_MPG,VehicleCard.Category.ACCELERATION));
        System.out.println(vehicleCard1.totalBonus());
        System.out.println(vehicleCard2.totalBonus());

        System.out.println("----------------------------");
        var playerA = new Player("A");
        var playerB = new Player("B");
        var playerC = new Player("C");
        playerA.addCards(List.of(vehicleCard1, foilVehicleCard3));
        playerB.addCards(List.of(vehicleCard2));
        playerC.addCards(List.of(vehicleCard1, foilVehicleCard3, vehicleCard2));

        List<Player> list = new ArrayList<>();
        list.add(playerB);
        list.add(playerA);
        list.add(playerC);
        System.out.println("--------------unsorted--------------");
        System.out.println(list);
        System.out.println("--------------compareByName--------------");
        Collections.sort(list);
        System.out.println(list);
        System.out.println("-------------compareByScore---------------");
        list.sort(Player.compareByScore());
        System.out.println(list);
        System.out.println("-------------compareByDeckSize---------------");
        list.sort(Player.compareByDeckSize());
        System.out.println(list);

        System.out.println("----------------------------");
        System.out.println(playerA.challengePlayer(playerB));
        System.out.println("-------------compareByScore---------------");
        list.sort(Player.compareByScore());
        System.out.println(list);
        System.out.println("----------------------------");
        System.out.println(playerA.challengePlayer(playerC));
        System.out.println("-------------compareByScore---------------");
        list.sort(Player.compareByScore());
        System.out.println(list);
        //System.out.println(playerC.challengePlayer(playerB));


        System.out.println("-------------------");
        System.out.println(vehicleCard1);
        System.out.println(vehicleCard1.getCategories().get(VehicleCard.Category.ACCELERATION));


        System.out.println("--------LATEST-----------");
        final List<String> allLines = SimpleCsvParser.readAllLinesFrom("src/cars.csv");

        int i = 0;
        for(var line: allLines){
            ++i;
            System.out.println(SimpleCsvParser.parseLine(line));
            if(i==4){break;}
        }

    }
}
