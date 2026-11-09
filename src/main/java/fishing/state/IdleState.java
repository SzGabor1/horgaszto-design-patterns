package fishing.state;

import fishing.FishingSession;

/** A szerelék kint van a parton. */
public class IdleState implements RodState {

    @Override
    public String name() { return "a szerelék a parton"; }

    @Override
    public void cast(FishingSession s) {
        s.say("Bedobtál (" + s.method().name() + "). Várj a kapásra!");
        s.setState(new CastState());
    }

    @Override
    public boolean allowsMethodChange() {
        return true;
    }
}
