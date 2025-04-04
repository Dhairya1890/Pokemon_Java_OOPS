package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.ElementType;

// Class demonstrating encapsulation and composition
public class Location {
    private String name;
    private String description;
    private List<Location> connections;
    private List<ElementType> commonElementTypes;
    private int monsterLevel;

    public Location(String name, String description, int monsterLevel) {
        this.name = name;
        this.description = description;
        this.monsterLevel = monsterLevel;
        this.connections = new ArrayList<>();
        this.commonElementTypes = new ArrayList<>();
    }

    public void addConnection(Location location) {
        if (!connections.contains(location)) {
            connections.add(location);
            // Bidirectional connection
            if (!location.getConnections().contains(this)) {
                location.addConnection(this);
            }
        }
    }

    public void addCommonElementType(ElementType elementType) {
        commonElementTypes.add(elementType);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Location> getConnections() {
        return connections;
    }

    public List<ElementType> getCommonElementTypes() {
        return commonElementTypes;
    }

    public int getMonsterLevel() {
        return monsterLevel;
    }

    public ElementType getRandomCommonElementType() {
        if (commonElementTypes.isEmpty()) {
            return ElementType.NORMAL;
        }

        Random random = new Random();
        return commonElementTypes.get(random.nextInt(commonElementTypes.size()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Location: ").append(name).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Monster Level: ").append(monsterLevel).append("\n");
        sb.append("Connected to: ");

        for (int i = 0; i < connections.size(); i++) {
            sb.append(connections.get(i).getName());
            if (i < connections.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}