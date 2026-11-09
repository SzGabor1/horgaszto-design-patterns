package fishing.event;

import fishing.fish.Fish;
import fishing.fish.Species;


public interface CatchListener {

    void onCatch(Fish fish);

    default void onEscape(Species species, String reason) { }
}
