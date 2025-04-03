// Another concrete class (demonstrating multiple inheritance paths)
public class Trainer extends Creature {
    private int captureCount;
    private String specialization;

    public Trainer(String name, String specialization) {
        super(name, 100, 1, ElementType.NORMAL);
        this.specialization = specialization;
        this.captureCount = 0;
    }

    @Override
    public void levelUp() {
        level++;
        maxHealth += 10;
        currentHealth = maxHealth;
        System.out.println(name + " leveled up to level " + level + "!");
    }

    public void incrementCaptureCount() {
        captureCount++;
        if (captureCount % 5 == 0) {
            levelUp();
        }
    }

    public int getCaptureCount() {
        return captureCount;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return name + " (Level " + level + " " + specialization + " Trainer) - Captures: " + captureCount;
    }
}