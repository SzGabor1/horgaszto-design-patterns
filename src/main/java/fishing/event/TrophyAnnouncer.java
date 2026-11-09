package fishing.event;

import fishing.fish.Fish;

public class TrophyAnnouncer implements CatchListener {

    @Override
    public void onCatch(Fish fish) {
        if (fish.isTrophy()) {
            System.out.println("*** TRÓFEA! *** " + fish + " - ezt le kell fotózni!");
        }
    }
}
