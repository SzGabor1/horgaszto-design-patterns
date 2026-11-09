package fishing.state;

import fishing.FishingSession;
import fishing.fish.Species;

public class CastState implements RodState {

    private int waitedTurns;

    @Override
    public String name() { return "bedobva, kapásra vársz"; }

    @Override
    public void waitTurn(FishingSession s) {
        if (s.random().nextDouble() < s.method().biteChance(waitedTurns)) {
            Species species = s.method().chooseSpecies(s.random(), waitedTurns);
            s.say(">>> KAPÁS! Valami rángatja a zsinórt! Vágj be!");
            s.setState(new BiteState(species));
        } else {
            waitedTurns++;
            s.say("...csend. A víz nyugodt.");
        }
    }

    @Override
    public void strike(FishingSession s) {
        s.say("Üresbe vágtál, a csali kirepült a vízből.");
        s.setState(new IdleState());
    }

    @Override
    public void reel(FishingSession s) {
        s.say("Kitekerted a szereléket.");
        s.setState(new IdleState());
    }
}
