package attack;

import utils.ElementType;

public class Attack {
    private final String name;
    private final int basePower;
    private final ElementType elementType;
    private final int maxUses;
    private int currentUses;

    public Attack(String name, int basePower, ElementType elementType, int maxUses) {
        this.name = name;
        this.basePower = basePower;
        this.elementType = elementType;
        this.maxUses = maxUses;
        this.currentUses = maxUses;
    }

    public String getName() {
        return name;
    }

    public int getBasePower() {
        return basePower;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public boolean canUse() {
        return currentUses > 0;
    }

    public void use() {
        if (currentUses > 0) {
            currentUses--;
        }
    }

    public void restore() {
        currentUses = maxUses;
    }

    public int getCurrentUses() {
        return currentUses;
    }

    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public String toString() {
        return name + " (" + elementType + ") - Power: " + basePower + " - Uses: " + currentUses + "/" + maxUses;
    }
}