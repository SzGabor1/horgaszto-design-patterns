package fishing.method;

import fishing.fish.Species;

import java.util.Random;


public interface FishingMethod {

    String name();

    double biteChance(int waitedTurns);

    Species chooseSpecies(Random random, int waitedTurns);
}
