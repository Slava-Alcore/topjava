package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(userMeal2.getId(),USER_ID);
        assertMatch(meal,userMeal2);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserId()  throws Exception {
        service.get(adminMeal1.getId(),USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound()  throws Exception {
        service.get(100100,USER_ID);
    }

    @Test
    public void delete() {
        service.delete(userMeal6.getId(),USER_ID);
        List<Meal> meals = getUserMeals();
        meals.remove(userMeal6);
        assertMatch(service.getAll(USER_ID),meals);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(100100,USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(null,LocalDate.of(2015, Month.MAY,30),USER_ID);
        List<Meal> mealsExpected = new ArrayList<>();
        mealsExpected.add(userMeal1);
        mealsExpected.add(userMeal2);
        mealsExpected.add(userMeal3);
        assertMatch(meals,mealsExpected);
    }

    @Test
    public void getBetweenDatesByWrongId() {
        List<Meal> meals = service.getBetweenDates(null,LocalDate.of(2015, Month.MAY,30),ADMIN_ID);
        assertMatch(meals,new ArrayList<>());
    }

    @Test
    public void getAllUserMeals() {
        assertMatch(service.getAll(USER_ID),getUserMeals());
    }

    @Test
    public void getAllAdminMeals() {
        assertMatch(service.getAll(ADMIN_ID),getAdminMeals());
    }

    @Test(expected = AssertionError.class)
    public void getAllUserMealsByAdminId() {
        assertMatch(service.getAll(ADMIN_ID),getUserMeals());
    }

    @Test
    public void update() {
        Meal updatetedMeal = new Meal(userMeal1);
        updatetedMeal.setCalories(2000);
        updatetedMeal.setDescription("New Meal");
        service.update(updatetedMeal,USER_ID);
        assertMatch(service.get(userMeal1.getId(),USER_ID),updatetedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongUserId() {
        Meal updatetedMeal = new Meal(userMeal1);
        updatetedMeal.setCalories(2000);
        updatetedMeal.setDescription("New Meal");
        service.update(updatetedMeal,ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2015,Month.MAY,29,13,00),"Обед",1500);
        Meal createdMeal = service.create(newMeal,USER_ID);
        newMeal.setId(createdMeal.getId());
        List<Meal> meals = getUserMeals();
        Collections.sort(meals, Comparator.comparingInt(AbstractBaseEntity::getId).reversed());
        meals.add(newMeal);
        assertMatch(service.getAll(USER_ID),meals);
    }
}