package com.impl.test;

import com.Polymorphic.employee.Employee;

//public abstract class Item{
//    String name;
//    String description;
//    Class E = Effect.class;
//    Effect effect;
//
//    public Item() {}
//
//    public <E extends Effect> E getEffect() {
//        return (E)effect;
//    }
//
//
//
//    public <T> T get() {
//        return (T)effect;
//    }
//}

abstract class Item<T extends Item<T>> {
    // 方法返回具体子类类型
    public abstract T getThis();

    // 示例通用方法
    public T doSomething() {
        System.out.println("Processing " + getClass().getSimpleName());
        return getThis(); // 返回具体子类实例
    }

    @SuppressWarnings("unchecked")
    public <T extends Item> T as() {
        try {
            return (T) this;
        } catch (ClassCastException e) {
            return null;
        }
    }
}