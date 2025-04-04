# Pokemon Adventure Game

A text-based adventure game inspired by Pokemon where you can explore different locations, battle wild monsters, capture them, and build your team.

## Game Features

- **Character Customization**: Choose your trainer name and specialization (Fire, Water, Grass, or Electric)
- **Starter Monster**: Receive a starter monster matching your specialization (Fire = Charmander, Water = Squirtle, etc.)
- **Exploration**: Visit multiple locations including Forest, Lake, Mountain, and Cave
- **Battle System**: Turn-based battles with different attack types and elemental advantages
- **Strategic Capturing**: Capture option becomes available after every 3 attacks during battle
- **Monster Collection**: Capture monsters to build and customize your team
- **Level Progression**: Level up your monsters as they gain experience from battles
- **Evolution System**: Monsters can evolve into stronger forms when reaching specific levels
- **Attack Boosters**: Use special items during battle to enhance your monster's attack power
  - Temporary boosters (consumed after use)
  - Permanent boosters (provide lasting benefits)
- **Battle Rewards**: Chance to receive attack boosters (25%) after winning battles
- **Item System**: Use items like Health Potions and Attack Boosters

## Game Structure

The game is built using object-oriented programming principles and design patterns:

- **Singleton Pattern**: Used in the GameWorld class to ensure a single world instance
- **Factory Pattern**: For creating monsters with varying attributes
- **Observer Pattern**: Implemented in the battle system for logging and notifications
- **Composition**: Used throughout to create complex game entities

## OOP Concepts Used

- **Inheritance**: Class hierarchy with Creature as parent class for Monster and Trainer
- **Encapsulation**: Private fields with getter/setter methods to control data access
- **Polymorphism**: Different creature types used interchangeably in battles
- **Abstraction**: Abstract concepts like Creatures and Usable items
- **Interfaces**: Usable interface for items, BattleObserver for notifications
- **Enums**: ElementType for type advantages/disadvantages
- **Collections**: ArrayList and HashMap for game elements storage

## How to Play

1. Run the game with `java PokemonAdventureGame`
2. Create your character by choosing a name and specialization
3. Receive your starter monster based on your chosen specialization
4. Explore different locations to find wild monsters
5. Battle and capture monsters to build your team
   - Capture becomes available after every 3 attacks in battle
   - Capture success depends on monster's health and random factors
6. Use attack boosters during battle to gain advantages
7. Travel between locations to find different types of monsters
8. Heal your team when they're injured
9. Level up your monsters to make them stronger and potentially evolve

## Game Commands

From the main menu, you can:

- **Explore**: Search the current area for wild monsters
- **Travel**: Move to a connected location
- **View Status**: Check your trainer information and attack boosters
- **View Monsters**: See details of your captured monsters
- **Heal Team**: Restore health to your monsters and attack uses
- **Exit**: End the game

During battle, you can:
- **Attack**: Use one of your monster's attacks
- **Use Attack Booster**: Enhance your monster's attack power (available in every turn)
- **Capture**: Attempt to capture the monster (available after every 3 attacks)
- **Run**: Try to escape from the battle

## Project Structure

- `src/`: Source code for the game
  - `PokemonAdventureGame.java`: Main game class and entry point
  - `GameWorld.java`: Manages the game world and locations
  - `Trainer.java`: Player character implementation
  - `Monster.java`: Monster implementation with evolution capability
  - `Location.java`: Game locations and connections
  - `BattleSystem.java`: Handles battle mechanics
  - `AttackBooster.java`: Items that enhance monster attacks
  - And more supporting classes

## Development

This project was developed as an exercise in object-oriented programming, demonstrating principles like inheritance, polymorphism, encapsulation, and abstraction.

## Future Enhancements

Potential future improvements include:

- Gym leaders and badge collection
- Evolution items for special monster evolutions
- Monster friendship system
- Enhanced type advantage mechanics
- In-game shops for items
- Legendary monsters with special abilities
- Weather effects influencing battles
- Monster breeding system

## Getting Started with Development

1. Clone the repository
2. Open the project in VS Code or your preferred Java IDE
3. Run the `PokemonAdventureGame.java` file to start the game
