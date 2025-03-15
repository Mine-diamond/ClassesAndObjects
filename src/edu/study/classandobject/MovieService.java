package edu.study.classandobject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieService {

     private Movie[] movies;

    public void showInfo(Movie movie) {
        System.out.println(movie.toString());
    }

    public void showAllInfo() {
        System.out.println("The detailed information of the file: "+ "\n");
        for (Movie movie : this.movies) {
            showInfo(movie);
        }
    }

    public void searchMovie(String id) {
        System.out.println("The searching result are as follows\n");
        boolean found = false;
        for (Movie movie : this.movies) {
            if (movie.getId().equals(id)) {
                    showInfo(movie);
                    found = true;
                    break;
            }
        }
        if (!found) {
            System.out.println("The searching result doesn't exist");
        }
    }

}
