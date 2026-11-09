package fishing.state;

import fishing.FishingSession;

public interface RodState {

    String name();

    default void cast(FishingSession s)     { notNow(s); }
    default void waitTurn(FishingSession s) { notNow(s); }
    default void strike(FishingSession s)   { notNow(s); }
    default void reel(FishingSession s)     { notNow(s); }
    default void loosen(FishingSession s)   { notNow(s); }

    default boolean allowsMethodChange() {
        return false;
    }

    private void notNow(FishingSession s) {
        s.say("Ezt most nem lehet. (" + name() + ")");
    }
}
