import java.util.ArrayList;
import java.util.List;

// Class implementing the Observer pattern
public class BattleLogger implements BattleObserver {
    private List<String> battleLog;

    public BattleLogger() {
        battleLog = new ArrayList<>();
    }

    @Override
    public void onBattleStart(Creature player, Creature opponent) {
        String logEntry = "Battle started between " + player.getName() + " and " + opponent.getName() + "!";
        battleLog.add(logEntry);
        System.out.println(logEntry);
    }

    @Override
    public void onBattleEnd(Creature winner, Creature loser) {
        String logEntry = winner.getName() + " won the battle against " + loser.getName() + "!";
        battleLog.add(logEntry);
        System.out.println(logEntry);
    }

    @Override
    public void onAttackExecuted(Creature attacker, Attack attack, Creature defender, int damage) {
        String logEntry = attacker.getName() + " used " + attack.getName() + " on " +
                defender.getName() + " for " + damage + " damage!";
        battleLog.add(logEntry);
    }

    public List<String> getBattleLog() {
        return battleLog;
    }

    public void clearLog() {
        battleLog.clear();
    }
}