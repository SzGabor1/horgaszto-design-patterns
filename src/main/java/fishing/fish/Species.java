package fishing.fish;

import java.util.Random;

/** A kifogható halfajok és jellemzőik. */
public enum Species {
    KESZEG("Keszeg", 0.1, 1.0, 1, 0.7),
    PONTY("Ponty", 1.0, 15.0, 3, 10.0),
    AMUR("Amur", 2.0, 20.0, 4, 12.0),
    HARCSA("Harcsa", 3.0, 60.0, 5, 30.0),
    CSUKA("Csuka", 1.0, 12.0, 4, 8.0),
    SULLO("Süllő", 0.8, 8.0, 3, 5.0),
    BALIN("Balin", 0.8, 6.0, 3, 4.0);

    private final String displayName;
    private final double minKg;
    private final double maxKg;
    private final int strength;
    private final double trophyKg;

    Species(String displayName, double minKg, double maxKg, int strength, double trophyKg) {
        this.displayName = displayName;
        this.minKg = minKg;
        this.maxKg = maxKg;
        this.strength = strength;
        this.trophyKg = trophyKg;
    }

    public String displayName() { return displayName; }

    public int strength() { return strength; }

    public double trophyKg() { return trophyKg; }

    public Fish randomFish(Random random) {
        double weight = minKg + (maxKg - minKg) * Math.pow(random.nextDouble(), 3);
        return new Fish(this, Math.round(weight * 100) / 100.0);
    }
}
