import java.util.*;

public class Test {
    public static void main(String[] args){
        //Category enum Test

        System.out.println(VehicleCard.Category.CYLINDERS_CNT.isInverted());
        System.out.println(VehicleCard.Category.DISPLACEMENT_CCM.isInverted());
        System.out.println(VehicleCard.Category.POWER_HP.isInverted());
        System.out.println(VehicleCard.Category.YEAR.isInverted());
        System.out.println(VehicleCard.Category.ECONOMY_MPG.isInverted());
        System.out.println(VehicleCard.Category.WEIGHT_LBS.isInverted());
        System.out.println(VehicleCard.Category.ACCELERATION.isInverted());

        System.out.println("\n");

        System.out.println(VehicleCard.Category.CYLINDERS_CNT.bonus(1.0));
        System.out.println(VehicleCard.Category.DISPLACEMENT_CCM.bonus(1.0));
        System.out.println(VehicleCard.Category.POWER_HP.bonus(1.0));
        System.out.println(VehicleCard.Category.YEAR.bonus(1.0));
        System.out.println(VehicleCard.Category.ECONOMY_MPG.bonus(1.0));
        System.out.println(VehicleCard.Category.WEIGHT_LBS.bonus(1.0));
        System.out.println(VehicleCard.Category.ACCELERATION.bonus(1.0));
        System.out.println(VehicleCard.Category.YEAR.bonus(0.756)); //sollte glaub ich null rauskommen


        System.out.println("\n");


        System.out.println(VehicleCard.Category.CYLINDERS_CNT);
        System.out.println(VehicleCard.Category.DISPLACEMENT_CCM);
        System.out.println(VehicleCard.Category.POWER_HP);
        System.out.println(VehicleCard.Category.YEAR);
        System.out.println(VehicleCard.Category.ECONOMY_MPG);
        System.out.println(VehicleCard.Category.WEIGHT_LBS);
        System.out.println(VehicleCard.Category.ACCELERATION);





        //VEHICLECARD TEST
        HashMap<VehicleCard.Category, Double> test = new HashMap<>();
        test.put(VehicleCard.Category.CYLINDERS_CNT, 1.);
        test.put(VehicleCard.Category.DISPLACEMENT_CCM, 1.);
        test.put(VehicleCard.Category.POWER_HP, 1.);
        test.put(VehicleCard.Category.YEAR, 1.);
        test.put(VehicleCard.Category.ECONOMY_MPG, 1.);
        test.put(VehicleCard.Category.WEIGHT_LBS, 1.);
        test.put(VehicleCard.Category.ACCELERATION, 1.);


        VehicleCard ente = new VehicleCard("Ente",test);
        VehicleCard lastwagen = new VehicleCard("Lastwagen", VehicleCard.newMap(20.4, 4., 375., 9., 1234., 21., 49));
        VehicleCard bmw = new VehicleCard("Ente",test);
        VehicleCard audi = new VehicleCard("Ente",test);

        test.put(VehicleCard.Category.ACCELERATION,test.get(VehicleCard.Category.ACCELERATION)+1); //bei ausgabe muss Accel noch immer 1 sein sonst keine shallow copy

        ente.getCategories().put(VehicleCard.Category.ACCELERATION, ente.getCategories().get(VehicleCard.Category.ACCELERATION) + 1); //darf nichts verändern

        System.out.println(ente);
        System.out.println(lastwagen); //ob die factory methode funkt

        System.out.println("\n");

        System.out.println("Lastwagen Totalbonus:" + lastwagen.totalBonus());
        System.out.println("Ente Totalbonus:" + ente.totalBonus());

        System.out.println(ente.compareTo(ente)); //wurde so implementiert return this.totalBonus() - rop.totalBonus():
        System.out.println(lastwagen.compareTo(ente)); //linke seite ist kleiner
        System.out.println(ente.compareTo(lastwagen)); //rechte seite ist kleiner
        System.out.println(lastwagen.compareTo(lastwagen)); //gleich groß

        System.out.println("\n");

        System.out.println(ente.equals(ente)); //der Test muss gelten laut vorlesung
        System.out.println(ente.equals(bmw));
        System.out.println(bmw.equals(audi));
        System.out.println(ente.equals(audi));
        System.out.println(ente.equals(null));
        System.out.println(ente.hashCode() == bmw.hashCode());
        System.out.println(ente.hashCode() == audi.hashCode());

        System.out.println("\n");

        System.out.println(ente.equals(lastwagen));
        System.out.println(lastwagen.equals(ente));
        System.out.println(ente.hashCode() == lastwagen.hashCode()); //muss false ergeben weil equals auch false war

        System.out.println("\n");


        //TEST FOILVEHICLECARD
        Set<VehicleCard.Category> test1 = new HashSet<>();
        System.out.println(test1.contains(null));
        final FoilVehicleCard amphicar = new FoilVehicleCard("Amphicar 770", VehicleCard.newMap(21.0, 4., 1147., 38., 2314, 14., 61.), Set.of(VehicleCard.Category.DISPLACEMENT_CCM, VehicleCard.Category.WEIGHT_LBS,VehicleCard.Category.ACCELERATION));
        //final FoilVehicleCard fetterbmw3ercoupe = new FoilVehicleCard("BMW 3er Coupe", VehicleCard.newMap(21.0, 4., 1147., 38., 2314, 14., 61.),Set.of(VehicleCard.Category.DISPLACEMENT_CCM, VehicleCard.Category.WEIGHT_LBS,VehicleCard.Category.ACCELERATION,VehicleCard.Category.DISPLACEMENT_CCM));
        //exception
        final FoilVehicleCard lol = new FoilVehicleCard("Amphicar 770", VehicleCard.newMap(21.0, 4., 1147., 38., 2314, 14., 61.), Set.of(VehicleCard.Category.DISPLACEMENT_CCM, VehicleCard.Category.WEIGHT_LBS,VehicleCard.Category.ACCELERATION));

        System.out.println(amphicar.getSpecials());
        amphicar.getSpecials().add(VehicleCard.Category.YEAR);  //muss unverändert lassen
        System.out.println(amphicar.getSpecials());


        System.out.println(amphicar.equals(lol)); //der Test muss gelten laut vorlesung
        System.out.println(lol.equals(amphicar));
        System.out.println(amphicar.equals(null));
        System.out.println(amphicar.hashCode() == lol.hashCode());
        System.out.println(lol.hashCode() == amphicar.hashCode());


        System.out.println("\n");



        final VehicleCard entevonmain = new VehicleCard("Ente", VehicleCard.newMap(20.4, 4., 375., 9., 1234., 21., 49));
        final FoilVehicleCard amphicarvonmain = new FoilVehicleCard("Amphicar 770", VehicleCard.newMap(21.0, 4., 1147., 38., 2314, 14., 61.), Set.of(VehicleCard.Category.DISPLACEMENT_CCM, VehicleCard.Category.WEIGHT_LBS));
        final FoilVehicleCard test3 = new FoilVehicleCard("Test 330", VehicleCard.newMap(21.0, 4., 1147., 38., 2314, 14., 61.), Set.of(VehicleCard.Category.DISPLACEMENT_CCM, VehicleCard.Category.WEIGHT_LBS));

        System.out.println(entevonmain);
        System.out.println(amphicarvonmain);

        //PLAYER TEST
        System.out.println("\n");

        {
            Player otto = new Player("Otto");
            Player anna = new Player("Anna");
            Player fakeanna = new Player("anna");

            anna.addCards(List.of(ente,ente,amphicar));
            otto.addCards(List.of(ente,amphicar));

            System.out.println(otto.getScore());
            System.out.println(anna.getScore());

            System.out.println("\n");

            System.out.println(anna);
            anna.addCard(test3);  //muss ans ende angehängt werden
            anna.addCards(List.of(ente,test3,amphicarvonmain)); ////muss ans ende angehängt werden
            System.out.println(anna);


            System.out.println("\n");
            System.out.println(otto);
            otto.clearDeck(); //otto hat ein leeres deck
            System.out.println(otto);

            System.out.println("\n");
            otto.getDeck().add(test3); //otto hat noch immer ein leeres deck wegen shallow copy
            System.out.println(otto);

            System.out.println("\n");

            otto.addCard(test3);
            otto.addCard(test3);
            System.out.println(otto);
            otto.peekNextCard();
            System.out.println(otto); //peek lässt deck unverändert
            otto.playNextCard();//poll löscht erstes element
            System.out.println(otto);

            System.out.println("\n");

            System.out.println(otto.compareTo(anna));
            System.out.println(anna.compareTo(otto));
            System.out.println(anna.compareTo(anna));
            System.out.println(otto.compareTo(otto));

            System.out.println("\n");
            System.out.println(anna.equals(otto));
            System.out.println(anna.equals(anna));
            System.out.println(anna.equals(fakeanna));
            System.out.println(anna.hashCode() == fakeanna.hashCode());
            System.out.println(fakeanna.hashCode() == anna.hashCode());
            System.out.println(anna.hashCode() == anna.hashCode());
        }
        System.out.println("\n");

        //challenge Player
        {
            Player otto = new Player("Otto");
            Player anna = new Player("Anna");
            Player georg = new Player("Georg");

            anna.addCards(List.of(ente,ente,amphicar));
            otto.addCards(List.of(ente,amphicar));
            georg.addCards(List.of(ente,ente,ente,ente));

            System.out.println(anna.challengePlayer(georg)); //true anna gewinnt
            System.out.println(anna);
            System.out.println(georg);

            System.out.println("\n");

            System.out.println(anna.challengePlayer(georg)); //keiner gewinnt decks wieder zurück
            System.out.println(anna);
            System.out.println(georg);

            System.out.println("\n");

            System.out.println(anna.challengePlayer(otto)); //false otto gewinnt
            System.out.println(anna);
            System.out.println(otto);


            anna.clearDeck();
            System.out.println("\n");

            System.out.println(anna.challengePlayer(otto)); //false unentschieden
            System.out.println(anna);
            System.out.println(otto);

        }
        System.out.println("\n");
        System.out.println("unsortiert");

        //COMPARATOR TEST
        {
            Player otto = new Player("Otto");
            Player anna = new Player("Anna");
            Player georg = new Player("Georg");

            anna.addCards(List.of(ente,ente,ente));
            otto.addCards(List.of(ente,amphicar));
            georg.addCards(List.of(ente,ente,ente,ente));

            List<Player> test4 = new ArrayList<>();
            test4.addAll(List.of(otto,anna,georg));

            for(Player p : test4)
                System.out.println(p);

            test4.sort(Player.compareByDeckSize());
            System.out.println("\n");

            System.out.println("sortiert nach decksize");

            for(Player p : test4)
                System.out.println(p);

            System.out.println("\n");
            System.out.println("sortiert nach score");

            Collections.sort(test4,Player.compareByScore());
            for(Player p : test4)
                System.out.println(p);


        }

    }

}

