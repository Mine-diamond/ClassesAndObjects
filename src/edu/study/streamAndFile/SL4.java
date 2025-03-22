package edu.study.streamAndFile;

import java.util.ArrayList;
import java.util.stream.Stream;

public class SL4 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("林青霞");
        list.add("张曼玉");
        list.add("王祖贤");
        list.add("柳岩");
        list.add("张敏");
        list.add("张无忌");

        // 需求1：取前3个数据在控制台输出
        // 需求2：超过3个元素，把剩下的元素在控制台输出
        // 需求3：超过2个元素，把剩下的元素中前2个在控制台输出
        // 需求4：取前4个数据组成一个流
        // 需求5：超过2个数据组成一个流
        // 需求6：合并需求1和需求2得到的流，并把结果在控制台输出
        // 需求7：合并需求1和需求2得到的流，并把结果在控制台输出，要求字符单元不能重复

        System.out.println("1.");
        list.stream().limit(3).forEach(str -> System.out.println(str));
        System.out.println("2.");
        list.stream().skip(3).forEach(str -> System.out.println(str));
        System.out.println("3.");
        list.stream().skip(2).limit(2).forEach(str -> System.out.println(str));
        System.out.println("4.");
        Stream<String> thisStream = list.stream().limit(4);
        System.out.println("5.");
        Stream<String> skipStream = list.stream().skip(2);
        System.out.println("6.");
        Stream.concat(thisStream,skipStream).forEach(str -> System.out.println(str));

    }
}
