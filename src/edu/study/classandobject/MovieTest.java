package edu.study.classandobject;

import java.util.Scanner;

public class MovieTest {
    public static void main(String[] args) {
        Movie movie1 = new Movie("001","东八区的先生们",2.1,"2022","中国大陆","剧情 喜剧","m1","st1");
        Movie movie2 = new Movie("002","上海堡垒",2.9,"2019","中国大陆","爱情 战争 科幻","m2","st2");
        Movie movie3 = new Movie("003","纯逐",2.2,"2015","中国大陆","剧情 喜剧","m3","st3");

        Movie[] movies = {movie1, movie2, movie3};


        MovieUserInteraction movieUserInteraction = new MovieUserInteraction();
        movieUserInteraction.interaction(movies);

        System.out.println("The programme has quited");
    }
}
