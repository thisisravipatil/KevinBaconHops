package org.example;

public class Main {
    public static void main(String[] args) {
        KevinBaconHopsFinder kevinBaconHopsFinder = new KevinBaconHopsFinder(new MinHopsFinder());

        int minHops = kevinBaconHopsFinder.findMinimumHops("Tom%20Cruise");

        System.out.println("Minimum hops: " + minHops);
    }
}