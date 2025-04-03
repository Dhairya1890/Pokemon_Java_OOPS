// Class demonstrating the Observer pattern through interfaces
public interface BattleObserver {
    void onBattleStart(Creature player, Creature opponent);

    void onBattleEnd(Creature winner, Creature loser);

    void onAttackExecuted(Creature attacker, Attack attack, Creature defender, int damage);
}