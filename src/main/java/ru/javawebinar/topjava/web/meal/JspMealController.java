package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/meals")
public class JspMealController extends MealController {

    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @PostMapping
    public String updateMeal (HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            create(meal);
        } else {
            update(meal, getId(request));
        }
        return "redirect:meals";
    }

    @GetMapping
    public String getMeal (HttpServletRequest request){
        String action = request.getParameter("action");
        switch (action == null? "all" : action){
            case "filter" : {
                log.info("Get Filtered");
                LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate =DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime =DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime =DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
                request.setAttribute("meals", getBetween(startDate,startTime,endDate,endTime));
                return "meals";
            }
            case "all": {
                request.setAttribute("meals", getAll());
                return "meals";
            }
            case "create":
            case "update" : {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        get(getId(request));
                request.setAttribute("meal", meal);
                return "mealForm";
            }
            case "delete" : {
                int id = getId(request);
                delete(id);
                return "redirect:meals";
            }
            default: return null;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
