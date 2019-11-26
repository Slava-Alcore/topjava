package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.web.meal.MealRestController.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static org.junit.jupiter.api.Assertions.*;

class MealRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+ '/'+MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL+'/'+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class,()-> mealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+'/'))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(mealService.getAll(UserTestData.USER_ID)));
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL+'/'+MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID,UserTestData.USER_ID),updated);
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(REST_URL+"/filter")
                .param("startDate",LocalDate.of(2015, Month.MAY,30).toString())
                .param("endDate",LocalDate.of(2015, Month.MAY,30).toString())
                .param("startTime","").param("endTime",""))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEAL3,MEAL2,MEAL1));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMEal = getNew();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+'/')
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMEal)))
                .andExpect(status().isCreated());
        Meal created = readFromJson(actions,Meal.class);
        Integer newId = created.getId();
        newMEal.setId(newId);
        assertMatch(created, newMEal);
        assertMatch(mealService.get(newId,UserTestData.USER_ID), newMEal);
    }
}