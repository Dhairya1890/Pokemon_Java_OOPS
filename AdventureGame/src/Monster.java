

// Concrete class extending the abstract class (demonstrating inheritance)
public class Monster extends Creature {
    private boolean canEvolve;
    private String evolution;

    public Monster(String name, int maxHealth, int level, ElementType elementType, boolean canEvolve,
            String evolution) {
        super(name, maxHealth, level, elementType);
        this.canEvolve = canEvolve;
        this.evolution = evolution;
    }

    @Override
    public void levelUp() {
        level++;
        maxHealth += 5;
        currentHealth = maxHealth;
        System.out.println(name + " leveled up to level " + level + "!");

        if (canEvolve && level >= 10) {
            evolve();
        }
    }

    private void evolve() {
        if (canEvolve && evolution != null) {
            String oldName = name;
            name = evolution;
            canEvolve = false;
            maxHealth += 20;
            currentHealth = maxHealth;
            System.out.println(oldName + " evolved into " + name + "!");
        }
    }

    public boolean canEvolve() {
        return canEvolve;
    }

    @Override
    public String toString() {
        return super.toString() + (canEvolve ? " (Can evolve)" : "");
    }
}