package ru.javawebinar.topjava.service.mealtests;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJPAMealServiceTest extends AbstractMealTest {

    @Test
    public void getMealsByUserId (){
        List<Meal> meals = service.getMealsByUserId(USER_ID);
        assertMatch(meals,MEALS);
    }
}
