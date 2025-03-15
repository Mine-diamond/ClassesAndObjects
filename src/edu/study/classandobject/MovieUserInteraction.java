package edu.study.classandobject;

import java.util.Scanner;

public class MovieUserInteraction {
    public void interaction(Movie[] movies) {

        Scanner sc = new Scanner(System.in);
        MovieService movieService = new MovieService(movies);

        lo:
        while (true) {
            System.out.println("---------电影信息系统---------" +
                    "\n1.查询全部电影信息" +
                    "\n2.根据id查询电影信息" +
                    "\n3.退出");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    movieService.showAllInfo();
                }case 2 -> {
                    System.out.println("Enter your movieID: ");
                    String movieID = sc.nextLine();
                    movieService.searchMovie(movieID);
                }case 3 -> {
                    break lo;
                }default -> {
                    System.out.println("输入无效,请重新输入");
                }
            }
        }
    }
}
