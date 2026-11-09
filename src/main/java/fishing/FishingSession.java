package fishing;

import fishing.event.CatchListener;
import fishing.fish.Fish;
import fishing.fish.Species;
import fishing.method.FishingMethod;
import fishing.state.IdleState;
import fishing.state.RodState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FishingSession {

    private final Random random;
    private final List<CatchListener> listeners = new ArrayList<>();
    private FishingMethod method;
    private RodState state = new IdleState();

    public FishingSession(FishingMethod method, Random random) {
        this.method = method;
        this.random = random;
    }

    // --- parancsok: mindet az aktuális állapot kezeli ---

    public void cast()     { state.cast(this); }
    public void waitTurn() { state.waitTurn(this); }
    public void strike()   { state.strike(this); }
    public void reel()     { state.reel(this); }
    public void loosen()   { state.loosen(this); }


    public void setState(RodState state) {
        this.state = state;
    }

    public RodState state() {
        return state;
    }


    public void changeMethod(FishingMethod method) {
        if (!state.allowsMethodChange()) {
            say("Előbb húzd ki a szerelékből a halat/csalit, csak utána válthatsz módszert.");
            return;
        }
        this.method = method;
        say("Módszer: " + method.name());
    }

    public FishingMethod method() {
        return method;
    }


    public void addListener(CatchListener listener) {
        listeners.add(listener);
    }

    public void notifyCaught(Fish fish) {
        listeners.forEach(l -> l.onCatch(fish));
    }

    public void notifyEscaped(Species species, String reason) {
        listeners.forEach(l -> l.onEscape(species, reason));
    }

    public Random random() {
        return random;
    }

    public void say(String message) {
        System.out.println(message);
    }
}
