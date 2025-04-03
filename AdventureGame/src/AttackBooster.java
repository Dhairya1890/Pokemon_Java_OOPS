

// Another class implementing the same interface (demonstrating polymorphism)
public class AttackBooster implements Usable {
    private String name;
    private String description;
    private int boostAmount;
    private boolean permanent;

    public AttackBooster(int boostAmount, boolean permanent) {
        this.name = permanent ? "Permanent Attack Booster" : "Temporary Attack Booster";
        this.description = "Boosts a monster's attack power by " + boostAmount +
                (permanent ? " permanently." : " for one battle.");
        this.boostAmount = boostAmount;
        this.permanent = permanent;
    }

    @Override
    public void use(Object target) {
        if (target instanceof Monster) {
            Monster monster = (Monster) target;
            // Just a simple implementation for demonstration
            // In a real game, this would modify the monster's attack stats
            System.out.println(monster.getName() + "'s attack power increased by " + boostAmount + "!");
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
        return !permanent;
    }

    public boolean isPermanent() {
        return permanent;
    }
}