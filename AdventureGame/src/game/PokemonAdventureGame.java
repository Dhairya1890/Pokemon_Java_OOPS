package game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import attack.Attack;
import attack.AttackBooster;
import battle.BattleLogger;
import battle.BattleSystem;
import entities.Creature;
import entities.Location;
import entities.Monster;
import entities.Trainer;
import utils.ElementType;

// Main game class demonstrating composition and integration of all components
public class PokemonAdventureGame {
    private Scanner scanner;
    private Trainer player;
    private List<Monster> capturedMonsters;
    private List<AttackBooster> attackBoosters;
    private Location currentLocation;
    private GameWorld gameWorld;
    private BattleSystem battleSystem;
    private BattleLogger battleLogger;
    private Random random;
    private boolean gameRunning;

    public PokemonAdventureGame() {
        scanner = new Scanner(System.in);
        capturedMonsters = new ArrayList<>();
        attackBoosters = new ArrayList<>();
        gameWorld = GameWorld.getInstance();
        battleSystem = new BattleSystem();
        battleLogger = new BattleLogger();
        random = new Random();
        gameRunning = false;
        
        // Add some attack boosters for the player to use
        attackBoosters.add(new AttackBooster(10, false)); // Temporary +10 booster
        attackBoosters.add(new AttackBooster(5, true));  // Permanent +5 booster
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
        ElementType starterType;

        switch (choice) {
            case 1:
                specialization = "Fire";
                starterType = ElementType.FIRE;
                break;
            case 2:
                specialization = "Water";
                starterType = ElementType.WATER;
                break;
            case 3:
                specialization = "Grass";
                starterType = ElementType.GRASS;
                break;
            case 4:
                specialization = "Electric";
                starterType = ElementType.ELECTRIC;
                break;
            default:
                specialization = "Normal";
                starterType = ElementType.NORMAL;
                break;
        }

        player = new Trainer(playerName, specialization);
        System.out.println("\nWelcome, " + player.getName() + " the " + specialization + " Trainer!");
        
        // Give player a starter monster based on their specialization
        giveStarterMonster(starterType);
    }

    private void giveStarterMonster(ElementType elementType) {
        String monsterName = "";
        boolean canEvolve = true;
        String evolution = "";
        
        switch (elementType) {
            case FIRE:
                monsterName = "Charmander";
                evolution = "Charmeleon";
                break;
            case WATER:
                monsterName = "Squirtle";
                evolution = "Wartortle";
                break;
            case GRASS:
                monsterName = "Bulbasaur";
                evolution = "Ivysaur";
                break;
            case ELECTRIC:
                monsterName = "Pikachu";
                evolution = "Raichu";
                break;
            default:
                monsterName = "Eevee";
                evolution = "Eeveelution";
                break;
        }
        
        // Create starter monster at level 5 with appropriate health
        Monster starter = new Monster(monsterName, 40, 5, elementType, canEvolve, evolution);
        
        // Add attacks to the starter monster
        List<Attack> typeAttacks = gameWorld.getAttacks(elementType);
        if (typeAttacks != null && !typeAttacks.isEmpty()) {
            starter.addAttack(typeAttacks.get(0));
        }
        
        List<Attack> normalAttacks = gameWorld.getAttacks(ElementType.NORMAL);
        if (normalAttacks != null && !normalAttacks.isEmpty()) {
            starter.addAttack(normalAttacks.get(0));
        }
        
        // Add the starter monster to the player's collection
        capturedMonsters.add(starter);
        System.out.println("\nCongratulations! You received a " + starter.getName() + " as your starter monster!");
        System.out.println(starter);
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
            
            if (!capturedMonsters.isEmpty()) {
                System.out.println("Your " + capturedMonsters.get(0).getName() + " gets ready for battle!");
            }

            displayBattleMenu(wildMonster);
        } else {
            System.out.println("You explored the area but didn't find any monsters.");
        }
    }

    private void displayBattleMenu(Monster wildMonster) {
        System.out.println("\n=== Battle Menu ===");
        System.out.println("1. Fight");
        System.out.println("2. Run");
        System.out.print("Choose an option: ");

        int choice = getUserChoice(1, 2);

        switch (choice) {
            case 1:
                battleMonster(wildMonster);
                break;
            case 2:
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
            System.out.println("Go, " + activeMonster.getName() + "!");
        } else {
            System.out.println("You don't have any monsters! You'll have to fight yourself.");
            activeMonster = null;
        }

        Creature attacker = activeMonster != null ? activeMonster : player;
        boolean playerTurn = true;
        int attackCount = 0;

        while (!attacker.isFainted() && !opponent.isFainted()) {
            if (playerTurn) {
                // Player's turn
                System.out.println("\n" + attacker.getName() + "'s turn!");
                
                // Main battle options
                System.out.println("\n=== Battle Options ===");
                System.out.println("1. Attack");
                
                // Show capture option after every 3 attacks
                boolean showCaptureOption = (attackCount > 0 && attackCount % 3 == 0);
                if (showCaptureOption) {
                    System.out.println("2. Try to Capture");
                    System.out.println("3. Use Attack Booster");
                    System.out.print("Choose an option: ");
                    
                    int battleChoice = getUserChoice(1, 3);
                    if (battleChoice == 2) {
                        attemptCapture(opponent);
                        return; // Exit battle after capture attempt
                    } else if (battleChoice == 3 && activeMonster != null) {
                        useAttackBooster(activeMonster);
                        // Continue battle after using booster
                        playerTurn = !playerTurn;
                        continue;
                    }
                    // If player chooses to attack, continue with attack selection
                } else {
                    System.out.println("2. Use Attack Booster");
                    System.out.print("Choose an option: ");
                    
                    int battleChoice = getUserChoice(1, 2);
                    if (battleChoice == 2 && activeMonster != null) {
                        useAttackBooster(activeMonster);
                        // Continue battle after using booster
                        playerTurn = !playerTurn;
                        continue;
                    }
                    // If player chooses to attack, continue with attack selection
                }
                
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
                attackCount++; // Increment attack counter after player's attack
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
            
            // 25% chance to get an attack booster as a reward
            if (random.nextDouble() < 0.25) {
                // Create either a temporary or permanent booster (80% temporary, 20% permanent)
                boolean isPermanent = random.nextDouble() < 0.2;
                // Booster strength between 5-15 points
                int boostStrength = 5 + random.nextInt(11);
                
                AttackBooster reward = new AttackBooster(boostStrength, isPermanent);
                attackBoosters.add(reward);
                
                System.out.println("\nYou found a " + reward.getName() + "!");
                System.out.println(reward.getDescription());
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
        // but with more randomness involved
        double healthPercentage = (double) monster.getCurrentHealth() / monster.getMaxHealth();
        
        // Base capture chance ranges from 30% to 80% depending on health
        double baseCaptureChance = 0.3 + (0.5 * (1 - healthPercentage));
        
        // Add some randomness - can increase or decrease by up to 20%
        double randomFactor = (random.nextDouble() * 0.4) - 0.2;
        double finalCaptureChance = baseCaptureChance + randomFactor;
        
        // Ensure the final chance is between 10% and 90%
        finalCaptureChance = Math.max(0.1, Math.min(0.9, finalCaptureChance));

        System.out.println("The monster ball wobbles...");
        try {
            // Add a bit of suspense
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore any interruption
        }

        if (random.nextDouble() < finalCaptureChance) {
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
        System.out.println("Attack boosters: " + attackBoosters.size());
        
        if (!capturedMonsters.isEmpty()) {
            System.out.println("\nStarter Monster: " + capturedMonsters.get(0).getName());
            System.out.println(capturedMonsters.get(0));
        }
        
        if (!attackBoosters.isEmpty()) {
            System.out.println("\n=== Attack Boosters ===");
            for (int i = 0; i < attackBoosters.size(); i++) {
                AttackBooster booster = attackBoosters.get(i);
                System.out.println((i + 1) + ". " + booster.getName() + " - " + booster.getDescription());
            }
        }
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

    private void useAttackBooster(Monster monster) {
        if (attackBoosters.isEmpty()) {
            System.out.println("You don't have any attack boosters!");
            return;
        }

        System.out.println("\n=== Available Attack Boosters ===");
        for (int i = 0; i < attackBoosters.size(); i++) {
            AttackBooster booster = attackBoosters.get(i);
            System.out.println((i + 1) + ". " + booster.getName() + " - " + booster.getDescription());
        }
        System.out.println((attackBoosters.size() + 1) + ". Cancel");
        System.out.print("Choose a booster to use (or cancel): ");

        int choice = getUserChoice(1, attackBoosters.size() + 1);
        
        // Check if user selected Cancel
        if (choice == attackBoosters.size() + 1) {
            System.out.println("You decided not to use a booster.");
            return;
        }
        
        // Get the selected booster
        AttackBooster selectedBooster = attackBoosters.get(choice - 1);
        
        // Apply the booster effect to the monster
        selectedBooster.use(monster);
        
        // Remove the booster if it's consumable
        if (selectedBooster.isConsumable()) {
            attackBoosters.remove(choice - 1);
            System.out.println("The " + selectedBooster.getName() + " was consumed.");
        }
    }

    public static void main(String[] args) {
        PokemonAdventureGame game = new PokemonAdventureGame();
        game.start();
    }
}