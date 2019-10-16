package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal,1));
    }

    @Override
    public Meal save(Meal meal,int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            log.info("save {} with userid={}", meal,userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        Meal meal1 = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        log.info("update {} with userid={}", meal1,userId);
        if (meal1!=null && meal1.getUserId()==userId) return meal1;
        return null;
    }

    @Override
    public boolean delete(int id,int userId) {
        log.info("delete {} with userid={}", id,userId);
        if (get(id,userId)==null) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id,int userId) {
        log.info("get {} with userid={}", id,userId);
        Meal meal = repository.get(id);
        if (meal.getUserId()==userId) return meal;
        return null;
    }

    @Override
    public List<Meal> getAll(int userId, boolean filter, LocalDate startDate, LocalDate endDate) {
        log.info("getall with filter={} and userid={}", filter, userId);
        if (!filter) return repository.values().stream()
                .filter(meal -> meal.getUserId()==userId)
                .sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
        return repository.values().stream()
                .filter(meal -> meal.getUserId()==userId)
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(),startDate,endDate))
                .sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }
}

