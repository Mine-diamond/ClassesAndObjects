package com.item.test;

public abstract class Item {

    public Item() {}

    public abstract void accept(Visitor visitor);

}
