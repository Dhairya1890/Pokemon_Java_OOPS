
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Class demonstrating singleton pattern and factory methods
public class GameWorld {
    private static GameWorld instance;
    private Map<String, Location> locations;
    private List<String> monsterNames;
    private Map<ElementType, List<Attack>> attacks;

    private GameWorld() {
        locations = new HashMap<>();
        monsterNames = new ArrayList<>();
        attacks = new HashMap<>();
        initializeWorld();
    }

    public static GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }
        return instance;
    }

    private void initializeWorld() {
        // Initialize locations
        Location forest = new Location("Forest", "A dense forest with tall trees and lush vegetation.", 3);
        forest.addCommonElementType(ElementType.GRASS);
        forest.addCommonElementType(ElementType.NORMAL);

        Location lake = new Location("Lake", "A serene lake with crystal clear water.", 3);
        lake.addCommonElementType(ElementType.WATER);

        Location mountain = new Location("Mountain", "A rocky mountain with steep cliffs.", 5);
        mountain.addCommonElementType(ElementType.FIRE);
        mountain.addCommonElementType(ElementType.ELECTRIC);

        Location cave = new Location("Cave", "A dark cave with mysterious echoes.", 7);
        cave.addCommonElementType(ElementType.NORMAL);
        cave.addCommonElementType(ElementType.WATER);

        // Connect locations
        forest.addConnection(lake);
        lake.addConnection(mountain);
        mountain.addConnection(cave);
        forest.addConnection(mountain);

        // Add locations to map
        locations.put(forest.getName(), forest);
        locations.put(lake.getName(), lake);
        locations.put(mountain.getName(), mountain);
        locations.put(cave.getName(), cave);

        // Initialize monster names
        monsterNames.add("Bulbasaur");
        monsterNames.add("Charmander");
        monsterNames.add("Squirtle");
        monsterNames.add("Pikachu");
        monsterNames.add("Eevee");
        monsterNames.add("Jigglypuff");
        monsterNames.add("Meowth");
        monsterNames.add("Psyduck");
        monsterNames.add("Growlithe");
        monsterNames.add("Poliwag");

        // Initialize attacks
        initializeAttacks();
    }

    private void initializeAttacks() {
        // Normal attacks
        List<Attack> normalAttacks = new ArrayList<>();
        normalAttacks.add(new Attack("Tackle", 40, ElementType.NORMAL, 30));
        normalAttacks.add(new Attack("Quick Attack", 30, ElementType.NORMAL, 35));
        normalAttacks.add(new Attack("Body Slam", 60, ElementType.NORMAL, 15));
        attacks.put(ElementType.NORMAL, normalAttacks);

        // Fire attacks
        List<Attack> fireAttacks = new ArrayList<>();
        fireAttacks.add(new Attack("Ember", 40, ElementType.FIRE, 25));
        fireAttacks.add(new Attack("Flamethrower", 70, ElementType.FIRE, 10));
        fireAttacks.add(new Attack("Fire Blast", 90, ElementType.FIRE, 5));
        attacks.put(ElementType.FIRE, fireAttacks);

        // Water attacks
        List<Attack> waterAttacks = new ArrayList<>();
        waterAttacks.add(new Attack("Water Gun", 40, ElementType.WATER, 25));
        waterAttacks.add(new Attack("Bubble Beam", 60, ElementType.WATER, 15));
        waterAttacks.add(new Attack("Hydro Pump", 90, ElementType.WATER, 5));
        attacks.put(ElementType.WATER, waterAttacks);

        // Grass attacks
        List<Attack> grassAttacks = new ArrayList<>();
        grassAttacks.add(new Attack("Vine Whip", 40, ElementType.GRASS, 25));
        grassAttacks.add(new Attack("Razor Leaf", 60, ElementType.GRASS, 15));
        grassAttacks.add(new Attack("Solar Beam", 90, ElementType.GRASS, 5));
        attacks.put(ElementType.GRASS, grassAttacks);

        // Electric attacks
        List<Attack> electricAttacks = new ArrayList<>();
        electricAttacks.add(new Attack("Thunder Shock", 40, ElementType.ELECTRIC, 25));
        electricAttacks.add(new Attack("Thunderbolt", 70, ElementType.ELECTRIC, 10));
        electricAttacks.add(new Attack("Thunder", 90, ElementType.ELECTRIC, 5));
        attacks.put(ElementType.ELECTRIC, electricAttacks);
    }

    public Location getLocation(String name) {
        return locations.get(name);
    }

    public List<Location> getAllLocations() {
        return new ArrayList<>(locations.values());
    }

    // Factory method for creating monsters (demonstrating factory pattern)
    public Monster createRandomMonster(Location location) {
        Random random = new Random();
        String name = monsterNames.get(random.nextInt(monsterNames.size()));
        int level = location.getMonsterLevel() + random.nextInt(3) - 1; // Level +/- 1
        if (level < 1)
            level = 1;

        ElementType elementType = location.getRandomCommonElementType();
        int maxHealth = 20 + (5 * level);
        boolean canEvolve = random.nextBoolean();
        String evolution = canEvolve ? name + "X" : null;

        Monster monster = new Monster(name, maxHealth, level, elementType, canEvolve, evolution);

        // Add 2-3 attacks
        addRandomAttacks(monster);

        return monster;
    }

    private void addRandomAttacks(Monster monster) {
        Random random = new Random();
        ElementType monsterType = monster.getElementType();

        // Add 1-2 attacks of monster's type
        List<Attack> typeAttacks = attacks.get(monsterType);
        if (typeAttacks != null && !typeAttacks.isEmpty()) {
            int numTypeAttacks = random.nextInt(2) + 1;
            for (int i = 0; i < numTypeAttacks && i < typeAttacks.size(); i++) {
                monster.addAttack(typeAttacks.get(i));
            }
        }

        // Add 1-2 normal attacks
        List<Attack> normalAttacks = attacks.get(ElementType.NORMAL);
        if (normalAttacks != null && !normalAttacks.isEmpty()) {
            int numNormalAttacks = random.nextInt(2) + 1;
            for (int i = 0; i < numNormalAttacks && i < normalAttacks.size(); i++) {
                monster.addAttack(normalAttacks.get(i));
            }
        }
    }
}