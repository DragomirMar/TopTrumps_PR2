import java.util.*;

public class VehicleCard implements Comparable<VehicleCard> {
    public enum Category {
        ECONOMY_MPG("Miles / Gallon"),
        CYLINDERS_CNT("Zylinder"),
        DISPLACEMENT_CCM("Hubraum [cc]"),
        POWER_HP("Leistung [hp]"),
        WEIGHT_LBS("Gewicht [lbs]"),
        ACCELERATION("Beschleunigung"),
        YEAR("Baujahr [19xx]");

        private final String categoryName;

        private Category(final String categoryName) {
            if (categoryName == null || categoryName.isEmpty()) {
                throw new IllegalArgumentException("Category name is null or empty.");
            }

            this.categoryName = categoryName;
        }

        public boolean isInverted() {
            return this == WEIGHT_LBS || this == ACCELERATION;
        }

        public int bonus(final Double value) {
            return isInverted() ? -value.intValue() : value.intValue();
        }

        @Override
        public String toString() {
            return categoryName;
        }
    }

    private String name;
    private Map<Category, Double> categories;

    public VehicleCard(final String name, final Map<Category, Double> categories) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty.");
        }

        if (categories == null) {
            throw new IllegalArgumentException("Categories is null.");
        }

        var vehicleCategories = Category.values();
        for (var category : vehicleCategories) {
            if (!categories.containsKey(category)) {
                throw new IllegalArgumentException("Categories does not contain all enum values.");
            }
        }

        var vehicleValues = categories.values();
        for (var value : vehicleValues) {
            if (value == null || value < 0) {
                throw new IllegalArgumentException("Categories contains null values or such less than 0.");
            }
        }

        this.name = name;
        this.categories = new HashMap<>(categories);
    }

    public String getName() {
        return name;
    }

    public Map<Category, Double> getCategories() {return new HashMap<>(categories);}

    public static Map<Category, Double> newMap(
            double economy,
            double cylinders,
            double displacement,
            double power,
            double weight,
            double acceleration,
            double year) {
        return new HashMap<Category, Double>() {{
            put(Category.ECONOMY_MPG, economy);
            put(Category.CYLINDERS_CNT, cylinders);
            put(Category.DISPLACEMENT_CCM, displacement);
            put(Category.POWER_HP, power);
            put(Category.WEIGHT_LBS, weight);
            put(Category.ACCELERATION, acceleration);
            put(Category.YEAR, year);
        }};
    }

    @Override
    public int compareTo(final VehicleCard other) {
        if (this.totalBonus() > other.totalBonus()) {
            return 1;
        } else if (this.totalBonus() < other.totalBonus()) {
            return -1;
        } else {
            return 0;
        }

        // TODO return this.totalBonus() - other.totalBonus();
    }

    public int totalBonus() {
        int totalBonus = 0;
        for (Map.Entry<Category, Double> entry : categories.entrySet()) {
            totalBonus += entry.getKey().bonus(entry.getValue());
        }

        return totalBonus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, totalBonus());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VehicleCard
                && this.name.equals(((VehicleCard) obj).getName())
                && this.totalBonus() == ((VehicleCard) obj).totalBonus()) {
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        String categoriesWithValues = "";
        boolean firstEntry = true;
        for (Map.Entry<Category, Double> entry : categories.entrySet()) {
            if (firstEntry) {
                firstEntry = false;
            } else {
                categoriesWithValues += ", ";
            }
            categoriesWithValues += entry.getKey() + "=" + entry.getValue();
        }

        return "- " + this.name + "(" + totalBonus() + ") -> {" + categoriesWithValues + "}";
    }
}