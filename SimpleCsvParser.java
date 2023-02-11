// insert package and imports here

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;

public class SimpleCsvParser {

    private SimpleCsvParser() {
    }

    public static List<String> readAllLinesFrom(final String path) throws IOException {
        return Files.lines(Paths.get(path)).skip(1).collect(Collectors.toList());
    }


    public static VehicleCard parseLine(final String line) {
        // parse the line and return a VehicleCard or null if line is erroneous...
        try{
            String[] parameters = line.split(",");
            String name = parameters[0];
            Map<VehicleCard.Category, Double> values = new HashMap<>();

            for(int i = 0; i < 7; ++i){
                if(parameters[i+1].isEmpty()){ parameters[i+1] = "0";}
                values.put(VehicleCard.Category.values()[i], Double.parseDouble(parameters[i+1]));
            }

            return new VehicleCard(name, values);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

