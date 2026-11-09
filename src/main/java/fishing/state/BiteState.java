package fishing.state;

import fishing.FishingSession;
import fishing.fish.Fish;
import fishing.fish.Species;

public class BiteState implements RodState {

    private static final double HOOK_CHANCE = 0.8;
    private final Species species;

    public BiteState(Species species) {
        this.species = species;
    }

    @Override
    public String name() { return "kapás van"; }

    @Override
    public void strike(FishingSession s) {
        if (s.random().nextDouble() < HOOK_CHANCE) {
            Fish fish = species.randomFish(s.random());
            s.say("Bevágtál, akad! " + (species.strength() >= 4 ? "Ez valami nagy..." : "Fárasztás!"));
            s.setState(new FightState(fish, s));
        } else {
            s.say("Elvétetted a bevágást, a hal elment.");
            s.notifyEscaped(species, "rossz bevágás");
            s.setState(new IdleState());
        }
    }

    @Override
    public void waitTurn(FishingSession s) {
        s.say("Túl sokáig vártál, a hal elengedte a csalit.");
        s.notifyEscaped(species, "késő bevágás");
        s.setState(new IdleState());
    }

    @Override
    public void reel(FishingSession s) {
        s.say("Kirántottad a csalit a hal szájából.");
        s.notifyEscaped(species, "elrántott csali");
        s.setState(new IdleState());
    }
}
