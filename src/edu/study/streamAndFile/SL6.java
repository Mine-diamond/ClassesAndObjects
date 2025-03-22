package edu.study.streamAndFile;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SL6 {
    /*
    * 现在有两个ArrayList集合，分别存储6名男演员和6名女演员，要求完成如下操作：

    - 男演员只要名字为3个字的前两人
    - 女演员只要姓林的，并且不要第一个
    - 把过滤后的男演员姓名和女演员姓名合并到一起
    - 把上一步操作后的元素作为构造方法的参数创建演员对象，遍历数组
    - 演员类Actor，里面有一个成员变量，一个带参构造方法，以及成员变量对应的get/set方法

    * */

    public static void main(String[] args) {
        ArrayList<String> manList = new ArrayList<>();
        manList.add("周润发");
        manList.add("成龙");
        manList.add("刘德华");
        manList.add("吴京");
        manList.add("周星驰");
        manList.add("李连杰");

        ArrayList<String> womanList = new ArrayList<>();
        womanList.add("林心如");
        womanList.add("张曼玉");
        womanList.add("林青霞");
        womanList.add("柳岩");
        womanList.add("林志玲");
        womanList.add("王祖贤");

        ArrayList<Actor> actorList = new ArrayList<>();

        Stream<String> stream1 = manList.stream().filter(s -> s.length() == 3).limit(2);
        Stream<String> stream2 = womanList.stream().filter(s -> s.charAt(0) == '林').skip(1);
        Stream.concat(stream1, stream2).forEach(s -> {
            Actor actor = new Actor(s);
            actorList.add(actor);
        });

        actorList.forEach(actor -> {System.out.println(actor);});

    }
}

class Actor{
    String name;

    public Actor(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
