package edu.study.classandobject;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    String id;
    String title;
    double rating;
    String time;
    String area;
    String type;
    String director;
    String starring;

    public String toString() {
        return "Title: " + title + "\n" +
                "Rank: " + rating + "\n" +
                "Time: " + time + "\n" +
                "Area: " + area + "\n" +
                "Director: " + director + "\n" +
                "Type: " + type + "\n" +
                "Starring: " + starring + "\n";
    }
}
