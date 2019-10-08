package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.CopyOnWriteArrayList;

public class DBUtil {
    private static CopyOnWriteArrayList<Meal> mealsDB= new CopyOnWriteArrayList<>();

    public static CopyOnWriteArrayList<Meal> getMealsDB() {
        return mealsDB;
    }
}
