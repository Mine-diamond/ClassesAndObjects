package edu.study.apiStudy;

import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        String password = "aA3456789asdf"; // replace with actual password
        Pattern pattern;
        Matcher matcher;

        String regex = "^(?=.{6,12}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9_]*$";

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(password);

        if (matcher.matches()) {
            System.out.println("Password meets requirements!");
        } else {
            System.out.println("Password does not meet requirements.");
        }
    }
}

//String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_@$!%*#?&])[A-Za-z\\d@$!%*#?&_]*$";