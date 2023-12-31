package org.example.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MovieDBHttpClient implements MovieDBClient {

    private static final String HOST = "https://developer.themoviedb.org/reference/search-person";
    private static final String GET_ACTOR_ID = "https://api.themoviedb.org/3/search/person?query=%s";
    private static final String GET_MOVIE_IDS = "https://api.themoviedb.org/3/person/%s/movie_credits";
    private static final String GET_ACTOR_IDS = "https://api.themoviedb.org/3/movie/%s/credits";


    private HttpClient httpClient;

    public MovieDBHttpClient() {
        this.httpClient = initiateClient();
    }

    @Override
    public Integer getActorId(String actorName) {
        String url = String.format(GET_ACTOR_ID, actorName);
        HttpGet request = new HttpGet(url);

        // add request headers
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNDk1MWM1NDFiMWFjZWU2MDU1ODU2NjNmYWUzZjQwZiIsInN1YiI6IjVlMzExMjhkOThmMWYxMDAxOGZlZWQ0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lGzsQ1LurC_pCIrqQxn9BSuIMouOtjYeEO-UbDHfMcw");

        try {
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(result).at("/results").get(0).at("/id").asInt();
            }
            throw new RuntimeException("Actor name not found");

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Set<Integer> getMovieIds(Integer actorId) {
        String url = String.format(GET_MOVIE_IDS, actorId);

        HttpGet request = new HttpGet(url);

        // add request headers
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNDk1MWM1NDFiMWFjZWU2MDU1ODU2NjNmYWUzZjQwZiIsInN1YiI6IjVlMzExMjhkOThmMWYxMDAxOGZlZWQ0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lGzsQ1LurC_pCIrqQxn9BSuIMouOtjYeEO-UbDHfMcw");

        try {
            HttpResponse response = httpClient.execute(request);

            Set<Integer> movieIds = new HashSet<>();

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                Iterator<JsonNode> movieIdIterator = mapper.readTree(result).at("/cast").iterator();
                while (movieIdIterator.hasNext()) {
                    JsonNode next = movieIdIterator.next();
                    movieIds.add(next.at("/id").asInt());
                }

                return movieIds;

            }

            throw new RuntimeException("Actor Id not found");

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        new MovieDBHttpClient().getActorsIds(466420);
    }

    @Override
    public Set<Integer> getActorsIds(Integer movieId) {
        String url = String.format(GET_ACTOR_IDS, movieId);

        HttpGet request = new HttpGet(url);

        // add request headers
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNDk1MWM1NDFiMWFjZWU2MDU1ODU2NjNmYWUzZjQwZiIsInN1YiI6IjVlMzExMjhkOThmMWYxMDAxOGZlZWQ0ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lGzsQ1LurC_pCIrqQxn9BSuIMouOtjYeEO-UbDHfMcw");

        try {
            HttpResponse response = httpClient.execute(request);

            Set<Integer> actorIds = new HashSet<>();

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                Iterator<JsonNode> movieIdIterator = mapper.readTree(result).at("/cast").iterator();
                while (movieIdIterator.hasNext()) {
                    JsonNode next = movieIdIterator.next();
                    actorIds.add(next.at("/id").asInt());
                }

                return actorIds;

            }

            throw new RuntimeException("Movie Id not found");

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    private HttpClient initiateClient() {
        return httpClient = HttpClientBuilder.create().build();
    }
}
