package org.example.clients;

import java.util.Set;

public interface MovieDBClient {

    Integer getActorId(String actorName);

    Set<Integer> getMovieIds(Integer actorId);

    Set<Integer> getActorsIds(Integer movieId);
}
