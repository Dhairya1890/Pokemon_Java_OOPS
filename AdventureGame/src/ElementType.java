

// Enum demonstrating encapsulation
public enum ElementType {
    FIRE("Fire"),
    WATER("Water"),
    GRASS("Grass"),
    ELECTRIC("Electric"),
    NORMAL("Normal");

    private final String displayName;

    ElementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    // Method to calculate type effectiveness (demonstrating polymorphism)
    public double getEffectivenessAgainst(ElementType defenderType) {
        if (this == FIRE) {
            if (defenderType == GRASS)
                return 2.0; // Super effective
            if (defenderType == WATER)
                return 0.5; // Not very effective
        } else if (this == WATER) {
            if (defenderType == FIRE)
                return 2.0;
            if (defenderType == GRASS)
                return 0.5;
        } else if (this == GRASS) {
            if (defenderType == WATER)
                return 2.0;
            if (defenderType == FIRE)
                return 0.5;
        } else if (this == ELECTRIC) {
            if (defenderType == WATER)
                return 2.0;
        }

        return 1.0; // Normal effectiveness
    }
}