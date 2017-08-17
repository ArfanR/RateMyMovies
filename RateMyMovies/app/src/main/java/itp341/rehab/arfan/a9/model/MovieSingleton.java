package itp341.rehab.arfan.a9.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ArfanR on 3/21/17.
 */

public class MovieSingleton {

    // instance variables
    private ArrayList<Movie> movies;  //this changes based on the needs of the project
    private Context context;

    //store a static reference to the singleton
    private static MovieSingleton singleton;

    // private singleton constructor
    private MovieSingleton(Context c) {
        context = c;
        //instantiate the data
        movies = new ArrayList<>();
    }

    // Singleton get method
    public static MovieSingleton get(Context c) {
        //only one instance ever is created
        if (singleton == null) { //we have never created a singleton
            singleton = new MovieSingleton(c.getApplicationContext());
        }
        return singleton;
    }

    // get movies (all items)
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public int getNumMovies() {
        return movies.size();
    }

    // get movie (single item)
    public Movie getMovie(int index) {
        return movies.get(index);
    }

    // add movie
    public void addMovie(Movie m) {
        movies.add(m);
    }
    /*
    public void addMovieComment(int index, String comment) {
        if (index >= 0 && index < movies.size()) {
            movies.get(index).getComments().add(comment);
        }
    }
    */
    // add comment to movie
    public void addMovieComment(int index, String user, String comment) {
        if (index >= 0 && index < movies.size()) {
            if (movies.get(index).getComments().containsKey(user)) {
                movies.get(index).getComments().get(user).add(comment);
            }
            else {
                ArrayList<String> comments = new ArrayList<>();
                comments.add(comment);
                movies.get(index).getComments().put(user, comments);
            }
        }
    }

}
