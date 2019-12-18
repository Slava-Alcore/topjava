package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MealFormValidator implements Validator {

    @Autowired
    private MealService mealService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Meal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Meal meal = (Meal) o;
        List<Meal> meals = mealService.getAll(SecurityUtil.authUserId())
                .stream()
                .filter(m -> m.getDateTime().compareTo(meal.getDateTime())==0)
                .collect(Collectors.toList());
        if (!meals.isEmpty()){
            errors.rejectValue("dateTime","meal.duplicateError");
        }
    }
}
