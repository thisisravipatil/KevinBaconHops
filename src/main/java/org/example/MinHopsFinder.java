package org.example;

import org.example.clients.MovieDBClient;
import org.example.clients.MovieDBHttpClient;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MinHopsFinder implements HopsFinder {

    private MovieDBClient movieDBClient;

    public MinHopsFinder() {
        this.movieDBClient = new MovieDBHttpClient();
    }
    @Override
    public int minimumNoOfHopsBetween(String actor1, String actor2) {
        try {
            Integer sourceActorId = movieDBClient.getActorId(actor1);
            Integer targetActorId = movieDBClient.getActorId(actor2);

            Set<Integer> movieIds = movieDBClient.getMovieIds(sourceActorId);
            Queue<Integer> movieIdQueue = new LinkedList<>();
            movieIdQueue.addAll(movieIds);

            int hops = 1;
            boolean isTargetFound = false;
            Set<Integer> visitedActors = new HashSet<>();
            Set<Integer> visitedMovies = new HashSet<>();
            visitedActors.add(sourceActorId);

            while (!movieIdQueue.isEmpty()) {

                int queueSize = movieIdQueue.size();
                int i = 0;

                while(i++ < queueSize) {
                    Integer movieId = movieIdQueue.poll();
                    Set<Integer> actorsIds = movieDBClient.getActorsIds(movieId);
                    if (actorsIds.contains(targetActorId)){
                        isTargetFound = true;
                        break;
                    }

                    visitedMovies.add(movieId);


                    for (Integer actorId : actorsIds) {
                        if (!visitedActors.contains(actorId)) {
                            Set<Integer> newMovieIds = movieDBClient.getMovieIds(actorId);
                            for (Integer newMovieId: newMovieIds) {
                                if (!visitedMovies.contains(newMovieId)) {
                                    movieIdQueue.add(newMovieId);
                                }
                            }
                        }
                    }

                    visitedActors.addAll(actorsIds);

                }
                if (isTargetFound)
                    break;
                hops++;
            }

            return isTargetFound? hops : -1;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
