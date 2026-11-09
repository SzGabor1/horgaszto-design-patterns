package fishing.fish;


public record Fish(Species species, double weightKg) {

    public boolean isTrophy() {
        return weightKg >= species.trophyKg();
    }

    @Override
    public String toString() {
        return "%s (%.2f kg)".formatted(species.displayName(), weightKg);
    }
}
