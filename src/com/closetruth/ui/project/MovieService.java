package com.closetruth.ui.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {
    private static List<Movie> movies = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("MovieService started.");
            System.out.println("1. Add Movie");
            System.out.println("2. Delete Movie");
            System.out.println("3. Query Movies");
            System.out.println("4. Ban actor");
            System.out.println("5. Exit");
            System.out.println("input your choice:");
            String command = sc.next();
            switch (command) {
                case "1":
                    addMovie();
                    break;
                case "2":
                    deleteMovie();
                    break;
                case "3":
                    queryMovies();
                    break;
                case "4":
                    delActor();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                case "6":
                    showAllMovies();
                    break;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private void deleteMovie() {
        System.out.println("Deleting movie...");
        System.out.println("Please input movie name:");
        String name = sc.next();

        Movie movie = getMovieByName(name);
        if (movie != null) {
            movies.remove(movie);
            System.out.println("Movie " + name + " deleted successfully.");
        } else {
            System.out.println("Movie not found.");
        }
    }

    private void showAllMovies() {
        for (Movie movie : movies) {
            System.out.println("Movie name: " + movie.getName() + "Movie actor: " + movie.getActor() + ", Score: " + movie.getScore() + ", Price: " + movie.getPrice());
        }
    }

    private void delActor() {
        System.out.println("Banning actor...");
        System.out.println("Please input actor name:");
        String actorName = sc.next();

        movies.removeIf(movie -> movie.getActor().contains(actorName));
        System.out.println("Actor " + actorName + " has been banned and their movies removed.");
        showAllMovies();
    }

    private void queryMovies() {
        System.out.println("Querying movies...");
        System.out.println("Please input movie name:");
        String name = sc.next();

        Movie movie = getMovieByName(name);
        if (movie != null) {
            System.out.println("Movie found: " + movie.getName() + "Movie actor: " + movie.getActor() + ", Score: " + movie.getScore() + ", Price: " + movie.getPrice());
        } else {
            System.out.println("Movie not found.");
        }
    }

    public Movie getMovieByName(String name) {
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    private void addMovie() {
        Movie movie = new Movie();
        System.out.println("Please input movie name:");
        movie.setName(sc.next());
        System.out.println("Please input movie actor:");
        movie.setActor(sc.next());
        System.out.println("Please input movie score:");
        movie.setScore(sc.nextDouble());
        System.out.println("Please input movie price:");
        movie.setPrice(sc.nextDouble());
        movies.add(movie);
        System.out.println("Movie added successfully.");
    }
}
