package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDao {
    void add(Meal meal);
    void delete(int mealId);
    void update(Meal meal);
    List<Meal> allList();
    Meal getById(int id);
}
