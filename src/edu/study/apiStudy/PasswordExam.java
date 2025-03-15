package edu.study.apiStudy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordExam {
    String password;

    public void ifPasswordUsable() {
        String processedPassword = password.replaceAll("^(?=A-Z)^.*$","***");
        if (processedPassword.equals("***")) {
            System.out.println("Password is valid");
        }else {
            System.out.println("Password is invalid");
            System.out.println(processedPassword);
        }
    }
}
