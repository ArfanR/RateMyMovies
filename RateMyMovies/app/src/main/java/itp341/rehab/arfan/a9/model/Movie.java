package itp341.rehab.arfan.a9.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ArfanR on 3/21/17.
 */

public class Movie {

    private String title, description;
    private int genre;
    private HashMap<String, ArrayList<String>> comments;
    private String url;

    public Movie() {
        super();
    }

    public Movie(String title, String description, int genre, HashMap<String, ArrayList<String>> comments, String url) {
        super();
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.comments = comments;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    /*
    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
    */
    public HashMap<String, ArrayList<String>> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, ArrayList<String>> comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return title + ": " + description;
    }
}
