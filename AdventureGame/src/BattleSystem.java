import java.util.Random;

// Class demonstrating composition and strategy pattern
public class BattleSystem {
    private Random random;

    public BattleSystem() {
        random = new Random();
    }

    public void executeTurn(Creature attacker, Attack attack, Creature defender) {
        if (!attack.canUse()) {
            System.out.println("No more uses left for " + attack.getName() + "!");
            return;
        }

        System.out.println(attacker.getName() + " uses " + attack.getName() + "!");
        attack.use();

        // Calculate damage
        int baseDamage = calculateBaseDamage(attacker, attack);
        double typeEffectiveness = attack.getElementType().getEffectivenessAgainst(defender.getElementType());
        int finalDamage = (int) (baseDamage * typeEffectiveness);

        // Apply damage
        defender.takeDamage(finalDamage);

        // Display effectiveness message
        if (typeEffectiveness > 1.0) {
            System.out.println("It's super effective!");
        } else if (typeEffectiveness < 1.0) {
            System.out.println("It's not very effective...");
        }
    }

    private int calculateBaseDamage(Creature attacker, Attack attack) {
        double levelFactor = (2.0 * attacker.getLevel()) / 5.0 + 2;
        double powerFactor = attack.getBasePower();
        double randomFactor = 0.85 + (random.nextDouble() * 0.15); // 0.85 to 1.00

        return (int) (levelFactor * powerFactor * randomFactor) / 50;
    }

    public Creature determineFirstAttacker(Creature creature1, Creature creature2) {
        // For simplicity, just randomly choose who goes first
        return random.nextBoolean() ? creature1 : creature2;
    }
}