package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {

    private AtomicInteger idCount = new AtomicInteger(1);

    private CopyOnWriteArrayList<Meal> mealsList = DBUtil.getMealsDB();

    public MealDaoImpl() {
        mealsList.add(new Meal(idCount.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealsList.add(new Meal(idCount.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealsList.add(new Meal(idCount.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealsList.add(new Meal(idCount.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealsList.add(new Meal(idCount.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealsList.add(new Meal(idCount.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void add(Meal meal) {
        meal.setId(idCount.getAndIncrement());
        mealsList.addIfAbsent(meal);
    }

    @Override
    public void delete(int mealId) {
        mealsList.removeIf(meal -> meal.getId() == mealId);
    }

    @Override
    public synchronized void update(Meal meal) {
        Meal mealToUpdate=getById(meal.getId());
        if (mealToUpdate!=null) {
            delete(mealToUpdate.getId());
            add(updateMeal(meal,mealToUpdate));
        }
    }

    @Override
    public List<Meal> allList() {
        return mealsList;
    }

    @Override
    public Meal getById(int id) {
        Meal foundMeal=null;
        for(Meal meal1 : mealsList){
            if(meal1.getId()==id) {
                foundMeal=meal1;
                break;
            }
        }
        return foundMeal;
    }

    private Meal updateMeal (Meal mealSource, Meal mealToUpdate){
        String disc;
        if (mealSource.getDescription()!=null && !mealSource.getDescription().isEmpty()){
            disc=mealSource.getDescription();
        } else {
            disc=mealToUpdate.getDescription();
        }
        int calories;
        if (mealSource.getCalories()!=0){
            calories=mealSource.getCalories();
        } else {
            calories=mealToUpdate.getCalories();
        }
        LocalDateTime localDateTime;
        if (mealSource.getDateTime()!=null){
            localDateTime=mealSource.getDateTime();
        } else {
            localDateTime=mealToUpdate.getDateTime();
        }
        return new Meal(localDateTime,disc,calories);
    }
}
