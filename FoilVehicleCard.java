import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FoilVehicleCard extends VehicleCard {
    private Set<Category> specials;

    public FoilVehicleCard(final String name, final Map<Category, Double> categories, final Set<Category> specials) {
        super(name, categories);

        if (specials == null || specials.size() == 0 || specials.size() > 3) {
            throw new IllegalArgumentException("Set of specials not initialized properly");
        }

        this.specials = new HashSet<>(specials);
    }

    public Set<Category> getSpecials() {
        return this.specials = new HashSet<>(specials);
    }

    @Override
    public int totalBonus() {
        int specialTotalBonus = 0;
        for (var special : specials) {
            specialTotalBonus += getCategories().get(special).intValue();
        }

        return super.totalBonus() + specialTotalBonus;
    }

    @Override
    public String toString() {
        String categoriesWithValues = "";
        boolean firstEntry = true;
        for (Map.Entry<Category, Double> entry : getCategories().entrySet()) {
            if (firstEntry) {
                firstEntry = false;
            } else {
                categoriesWithValues += ", ";
            }

            if (specials.contains(entry.getKey())) {
                categoriesWithValues += "*" + entry.getKey() + "*";
            } else {
                categoriesWithValues += entry.getKey();
            }

            categoriesWithValues += "=" + entry.getValue();
        }

        return "- " + this.getName() + "(" + totalBonus() + ") -> {" + categoriesWithValues + "}";
    }
}