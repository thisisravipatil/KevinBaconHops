package org.example;

public class KevinBaconHopsFinder {
    private HopsFinder hopsFinder;

    public KevinBaconHopsFinder(HopsFinder hopsFinder) {
        this.hopsFinder = hopsFinder;
    }

    int findMinimumHops(String actorName) {
        return hopsFinder.minimumNoOfHopsBetween(actorName, "Kevin%20Bacon");
    }
}
