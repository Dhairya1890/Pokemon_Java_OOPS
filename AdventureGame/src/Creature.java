

import java.util.ArrayList;
import java.util.List;

// Abstract base class demonstrating abstraction and inheritance
public abstract class Creature {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int level;
    protected List<Attack> attacks;
    protected ElementType elementType;

    public Creature(String name, int maxHealth, int level, ElementType elementType) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.level = level;
        this.elementType = elementType;
        this.attacks = new ArrayList<>();
    }

    // Abstract method demonstrating abstraction
    public abstract void levelUp();

    public void heal(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
        System.out.println(name + " healed for " + amount + " points. Current health: " + currentHealth);
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
        System.out.println(name + " took " + damage + " damage. Current health: " + currentHealth);

        if (currentHealth <= 0) {
            System.out.println(name + " fainted!");
        }
    }

    public boolean isFainted() {
        return currentHealth <= 0;
    }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    public Attack getAttack(int index) {
        if (index >= 0 && index < attacks.size()) {
            return attacks.get(index);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    @Override
    public String toString() {
        return name + " (Level " + level + ") - " + elementType + " - HP: " + currentHealth + "/" + maxHealth;
    }
}