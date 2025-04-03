import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Main game class demonstrating composition and integration of all components
public class PokemonAdventureGame {
    private Scanner scanner;
    private Trainer player;
    private List<Monster> capturedMonsters;
    private Location currentLocation;
    private GameWorld gameWorld;
    private BattleSystem battleSystem;
    private BattleLogger battleLogger;
    private Random random;
    private boolean gameRunning;

    public PokemonAdventureGame() {
        scanner = new Scanner(System.in);
        capturedMonsters = new ArrayList<>();
        gameWorld = GameWorld.getInstance();
        battleSystem = new BattleSystem();
        battleLogger = new BattleLogger();
        random = new Random();
        gameRunning = false;
    }

    public void start() {
        System.out.println("Welcome to Pokemon Adventure!");
        System.out.println("==============================");

        initializePlayer();

        // Set starting location
        List<Location> locations = gameWorld.getAllLocations();
        currentLocation = locations.get(0); // Forest

        gameRunning = true;

        System.out.println("\nYou are now in the " + currentLocation.getName() + ".");
        System.out.println(currentLocation.getDescription());

        while (gameRunning) {
            displayMainMenu();
            int choice = getUserChoice(1, 6);

            switch (choice) {
                case 1:
                    exploreArea();
                    break;
                case 2:
                    travelToNewLocation();
                    break;
                case 3:
                    viewPlayerStatus();
                    break;
                case 4:
                    viewCapturedMonsters();
                    break;
                case 5:
                    healTeam();
                    break;
                case 6:
                    gameRunning = false;
                    System.out.println("Thanks for playing Pokemon Adventure! Goodbye!");
                    break;
            }
        }

        scanner.close();
    }

    private void initializePlayer() {
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.println("\nSelect your specialization:");
        System.out.println("1. Fire Trainer");
        System.out.println("2. Water Trainer");
        System.out.println("3. Grass Trainer");
        System.out.println("4. Electric Trainer");

        int choice = getUserChoice(1, 4);
        String specialization;

        switch (choice) {
            case 1:
                specialization = "Fire";
                break;
            case 2:
                specialization = "Water";
                break;
            case 3:
                specialization = "Grass";
                break;
            case 4:
                specialization = "Electric";
                break;
            default:
                specialization = "Normal";
                break;
        }

        player = new Trainer(playerName, specialization);
        System.out.println("\nWelcome, " + player.getName() + " the " + specialization + " Trainer!");
    }

    private void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Explore " + currentLocation.getName());
        System.out.println("2. Travel to another location");
        System.out.println("3. View status");
        System.out.println("4. View captured monsters");
        System.out.println("5. Heal team");
        System.out.println("6. Exit game");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice(int min, int max) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    validInput = true;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }

        return choice;
    }

    private void exploreArea() {
        System.out.println("\nExploring " + currentLocation.getName() + "...");

        // 70% chance to encounter a monster
        if (random.nextDouble() < 0.7) {
            Monster wildMonster = gameWorld.createRandomMonster(currentLocation);
            System.out.println("A wild " + wildMonster.getName() + " appeared!");

            displayBattleMenu(wildMonster);
        } else {
            System.out.println("You explored the area but didn't find any monsters.");
        }
    }

    private void displayBattleMenu(Monster wildMonster) {
        System.out.println("\n=== Battle Menu ===");
        System.out.println("1. Fight");
        System.out.println("2. Capture");
        System.out.println("3. Run");
        System.out.print("Choose an option: ");

        int choice = getUserChoice(1, 3);

        switch (choice) {
            case 1:
                battleMonster(wildMonster);
                break;
            case 2:
                attemptCapture(wildMonster);
                break;
            case 3:
                if (random.nextDouble() < 0.7) {
                    System.out.println("You successfully ran away!");
                } else {
                    System.out.println("Couldn't escape! The " + wildMonster.getName() + " attacks!");
                    battleMonster(wildMonster);
                }
                break;
        }
    }

    private void battleMonster(Monster opponent) {
        System.out.println("\n=== Battle: " + player.getName() + " vs " + opponent.getName() + " ===");

        // Select active monster
        Monster activeMonster = null;
        if (!capturedMonsters.isEmpty()) {
            System.out.println("Select a monster to battle with:");
            for (int i = 0; i < capturedMonsters.size(); i++) {
                System.out.println((i + 1) + ". " + capturedMonsters.get(i));
            }
            int choice = getUserChoice(1, capturedMonsters.size());
            activeMonster = capturedMonsters.get(choice - 1);
        } else {
            System.out.println("You don't have any monsters! You'll have to fight yourself.");
            activeMonster = null;
        }

        Creature attacker = activeMonster != null ? activeMonster : player;
        boolean playerTurn = true;

        while (!attacker.isFainted() && !opponent.isFainted()) {
            if (playerTurn) {
                // Player's turn
                System.out.println("\n" + attacker.getName() + "'s turn!");
                System.out.println("Select an attack:");

                List<Attack> attacks = attacker.getAttacks();
                if (attacks.isEmpty()) {
                    // If player has no attacks, add a basic one
                    if (attacker == player) {
                        Attack basicAttack = new Attack("Punch", 30, attacker.getElementType(), 40);
                        attacker.addAttack(basicAttack);
                        attacks = attacker.getAttacks();
                    }
                }

                for (int i = 0; i < attacks.size(); i++) {
                    System.out.println((i + 1) + ". " + attacks.get(i));
                }

                int choice = getUserChoice(1, attacks.size());
                Attack selectedAttack = attacks.get(choice - 1);

                battleSystem.executeTurn(attacker, selectedAttack, opponent);
            } else {
                // Opponent's turn
                System.out.println("\n" + opponent.getName() + "'s turn!");

                // Randomly select an opponent attack
                List<Attack> opponentAttacks = opponent.getAttacks();
                Attack randomAttack = opponentAttacks.get(random.nextInt(opponentAttacks.size()));

                battleSystem.executeTurn(opponent, randomAttack, attacker);
            }

            // Switch turns
            playerTurn = !playerTurn;

            // Check if battle is over
            // Check if battle is over
            if (attacker.isFainted() || opponent.isFainted()) {
                break;
            }
        }

        // Determine winner
        if (opponent.isFainted()) {
            System.out.println("\nYou defeated the wild " + opponent.getName() + "!");
            if (activeMonster != null) {
                System.out.println(activeMonster.getName() + " gained experience!");
                activeMonster.levelUp();
            } else {
                player.levelUp();
            }
        } else {
            System.out
                    .println("\nYour " + attacker.getName() + " was defeated by the wild " + opponent.getName() + "!");
            if (activeMonster != null) {
                System.out.println(activeMonster.getName() + " needs time to recover.");
            } else {
                System.out.println("You rush back to safety to recover.");
            }
        }
    }

    private void attemptCapture(Monster monster) {
        System.out.println("\nAttempting to capture " + monster.getName() + "...");

        // Capture chance is based on monster's remaining health percentage
        double healthPercentage = (double) monster.getCurrentHealth() / monster.getMaxHealth();
        double captureChance = 0.8 * (1 - healthPercentage);

        if (random.nextDouble() < captureChance) {
            System.out.println("Capture successful! " + monster.getName() + " joined your team!");
            capturedMonsters.add(monster);
            player.incrementCaptureCount();
        } else {
            System.out.println("Capture failed! " + monster.getName() + " broke free!");
            System.out.println("The wild " + monster.getName() + " attacks!");
            battleMonster(monster);
        }
    }

    private void travelToNewLocation() {
        List<Location> connections = currentLocation.getConnections();

        if (connections.isEmpty()) {
            System.out.println("\nThere are no connected locations to travel to.");
            return;
        }

        System.out.println("\nSelect a location to travel to:");
        for (int i = 0; i < connections.size(); i++) {
            System.out.println((i + 1) + ". " + connections.get(i).getName());
        }

        int choice = getUserChoice(1, connections.size());
        currentLocation = connections.get(choice - 1);

        System.out.println("\nYou are now in the " + currentLocation.getName() + ".");
        System.out.println(currentLocation.getDescription());
    }

    private void viewPlayerStatus() {
        System.out.println("\n=== Player Status ===");
        System.out.println(player);
        System.out.println("Current location: " + currentLocation.getName());
        System.out.println("Monsters captured: " + capturedMonsters.size());
    }

    private void viewCapturedMonsters() {
        System.out.println("\n=== Captured Monsters ===");

        if (capturedMonsters.isEmpty()) {
            System.out.println("You haven't captured any monsters yet.");
            return;
        }

        for (int i = 0; i < capturedMonsters.size(); i++) {
            System.out.println((i + 1) + ". " + capturedMonsters.get(i));
        }

        System.out.println("\nEnter monster number for details (0 to go back): ");
        int choice = getUserChoice(0, capturedMonsters.size());

        if (choice == 0) {
            return;
        }

        Monster selected = capturedMonsters.get(choice - 1);
        System.out.println("\n=== " + selected.getName() + " Details ===");
        System.out.println(selected);
        System.out.println("\nAttacks:");

        List<Attack> attacks = selected.getAttacks();
        for (int i = 0; i < attacks.size(); i++) {
            System.out.println((i + 1) + ". " + attacks.get(i));
        }
    }

    private void healTeam() {
        System.out.println("\nHealing your team...");

        for (Monster monster : capturedMonsters) {
            int previousHealth = monster.getCurrentHealth();
            monster.heal(monster.getMaxHealth());
            System.out.println(monster.getName() + " healed from " + previousHealth + " to "
                    + monster.getCurrentHealth() + " HP.");
        }

        player.heal(player.getMaxHealth());
        System.out.println(player.getName() + " is fully healed!");

        // Restore attack uses
        for (Monster monster : capturedMonsters) {
            for (Attack attack : monster.getAttacks()) {
                attack.restore();
            }
        }

        System.out.println("All attack uses have been restored!");
    }

    public static void main(String[] args) {
        PokemonAdventureGame game = new PokemonAdventureGame();
        game.start();
    }
}