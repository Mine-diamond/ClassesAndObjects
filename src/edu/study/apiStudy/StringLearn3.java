package edu.study.apiStudy;

import java.util.Scanner;

//已知用户名和密码，实现模拟登录
//共三次机会
public class StringLearn3 {
    public static void main(String[] args) {

        Login login = new Login("Alice","1234");
        login.login();
    }
}
