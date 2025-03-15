package edu.study.set;

public class Box<T> {
    T content;
    public Box(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public String toString(){
        return "Box cotaining:" + content;
    }

}

class TestMain{
    public static void main(String[] args) {
        Box<Integer> box = new Box<Integer>(11);
        System.out.println(box.getContent());
        System.out.println(box);

        Box<String> box2 = new Box<String>("hi");
        System.out.println(box2.getContent());
        System.out.println(box2);
    }
}
