package com.item.another;

import com.object.student.Student;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Item {
    /**
     * 基础类型安全转换
     */
    public static <T extends Item> T cast(Item item, Class<T> type) {
        if (!type.isInstance(item)) {
            throw new IllegalArgumentException("类型不匹配: 需要" + type.getSimpleName()
                    + "，实际是" + item.getClass().getSimpleName());
        }
        return type.cast(item);
    }

    // ===== 无额外参数 =====
    public static <T extends Item, R> R with(
            Item item, Class<T> type,
            Function<T, R> action
    ) {
        return action.apply(cast(item, type));
    }

    // ===== 单个Pet参数 =====
    public static <T extends Item, R> R with(
            Item item, Class<T> type,
            Pet pet,
            BiFunction<T, Pet, R> action
    ) {
        return action.apply(cast(item, type), pet);
    }

    // ===== 两个Pet参数 =====
    public static <T extends Item, R> R with(
            Item item, Class<T> type,
            Pet pet1, Pet pet2,
            TriFunction<T, Pet, Pet, R> action
    ) {
        return action.apply(cast(item, type), pet1, pet2);
    }

    // ===== Player参数 =====
    public static <T extends Item, R> R with(
            Item item, Class<T> type,
            Player player,
            BiFunction<T, Player, R> action
    ) {
        return action.apply(cast(item, type), player);
    }

    // ===== Player + Pet参数 =====
    public static <T extends Item, R> R with(
            Item item, Class<T> type,
            Player player, Pet pet,
            TriFunction<T, Player, Pet, R> action
    ) {
        return action.apply(cast(item, type), player, pet);
    }

    // ===== 通用版本 =====
    /**
     * 通用参数处理版本（当上述方法不满足需求时使用）
     */
    public static <T extends Item, R> R withContext(
            Item item, Class<T> type,
            Object context,
            BiFunction<T, Object, R> action
    ) {
        return action.apply(cast(item, type), context);
    }

    // 三参数函数接口
    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}


