package attack;


import entities.Creature;

public class HealthPotion implements Usable {
    private String name;
    private String description;
    private int healAmount;
    private boolean consumed;

    public HealthPotion(int healAmount) {
        this.name = "Health Potion";
        this.description = "Restores " + healAmount + " HP to a creature.";
        this.healAmount = healAmount;
        this.consumed = false;
    }

    @Override
    public void use(Object target) {
        if (target instanceof Creature) {
            Creature creature = (Creature) target;
            creature.heal(healAmount);
            consumed = true;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    public boolean isConsumed() {
        return consumed;
    }
}