package fishing.state;

import fishing.FishingSession;
import fishing.fish.Fish;


public class FightState implements RodState {

    private static final int SNAP_TENSION = 100;
    private static final int SLACK_TENSION = 5;

    private final Fish fish;
    private int distance;
    private int tension = 30;

    public FightState(Fish fish, FishingSession s) {
        this.fish = fish;
        this.distance = 10 + (int) Math.min(fish.weightKg(), 30) / 2;
        printStatus(s);
    }

    @Override
    public String name() { return "fárasztás"; }

    @Override
    public void reel(FishingSession s) {
        int strength = fish.species().strength();
        distance -= 2 + s.random().nextInt(4);
        tension += 5 + s.random().nextInt(strength * 5 + 1);
        resolve(s);
    }

    @Override
    public void loosen(FishingSession s) {
        tension -= 15 + s.random().nextInt(11);
        distance += s.random().nextInt(fish.species().strength() + 1);
        resolve(s);
    }

    @Override
    public void waitTurn(FishingSession s) {
        s.say("A hal húz!");
        tension += fish.species().strength() * 4;
        distance += 1;
        resolve(s);
    }

    @Override
    public void strike(FishingSession s) {
        s.say("Már akad a hal, most fárasztani kell (teker / enged).");
    }

    private void resolve(FishingSession s) {
        if (tension >= SNAP_TENSION) {
            s.say("PATT! Elszakadt a zsinór. A hal elúszott.");
            s.notifyEscaped(fish.species(), "elszakadt zsinór");
            s.setState(new IdleState());
        } else if (tension <= SLACK_TENSION) {
            s.say("Túl laza lett a zsinór, a hal leakadt.");
            s.notifyEscaped(fish.species(), "laza zsinór");
            s.setState(new IdleState());
        } else if (distance <= 0) {
            s.say("Szákban van! " + fish);
            s.notifyCaught(fish);
            s.setState(new IdleState());
        } else {
            printStatus(s);
        }
    }

    private void printStatus(FishingSession s) {
        int bars = Math.clamp(tension / 10, 0, 10);
        String gauge = "#".repeat(bars) + "-".repeat(10 - bars);
        s.say("  Távolság: %2d m | Feszülés: [%s] %d%%".formatted(distance, gauge, tension));
    }
}
