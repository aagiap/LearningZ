package com.project.learningz.controller;

import com.project.learningz.entity.MonthlyStatistic;
import com.project.learningz.entity.User;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.MonthlyStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/marketer/dashboard")
public class DashboardController {

    @Autowired
    private MonthlyStatisticService monthlyStatisticService;

    @Autowired
    private UserRepository userRepository;

    private void getAuthenticatedUserInfo(org.springframework.ui.Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String avatarUrl = "/static/image/AvartaDefault.jpg";

        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String email = oAuth2User.getAttribute("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                username = user.getUsername();
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
            }
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                user = userRepository.findByEmail(username);
            }
            if (user != null) {
                avatarUrl = user.getAvtUrl() != null ? user.getAvtUrl() : avatarUrl;
            }
        }
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
    }

    @GetMapping
    public String getDashboard(Model model) {
        getAuthenticatedUserInfo(model);
        monthlyStatisticService.updateMonthlyStatistic();

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int previousYear = (currentMonth == 1) ? currentYear - 1 : currentYear;
        int previousMonth = (currentMonth == 1) ? 12 : currentMonth - 1;

        MonthlyStatistic currentStat = monthlyStatisticService.getMonthlyStatistic(currentYear, currentMonth);
        MonthlyStatistic previousStat = monthlyStatisticService.getMonthlyStatistic(previousYear, previousMonth);

        model.addAttribute("usersChange", calculateChange(currentStat.getTotalUsersRegistered(), previousStat.getTotalUsersRegistered()));
        model.addAttribute("coursesChange", calculateChange(currentStat.getTotalCoursesRegistered(), previousStat.getTotalCoursesRegistered()));
        model.addAttribute("visitsChange", calculateChange(currentStat.getTotalVisits(), previousStat.getTotalVisits()));
        model.addAttribute("revenueChange", calculateChange(currentStat.getTotalRevenue().intValue(), previousStat.getTotalRevenue().intValue()));

        model.addAttribute("usersChangeIcon", getChangeIcon(currentStat.getTotalUsersRegistered(), previousStat.getTotalUsersRegistered()));
        model.addAttribute("coursesChangeIcon", getChangeIcon(currentStat.getTotalCoursesRegistered(), previousStat.getTotalCoursesRegistered()));
        model.addAttribute("visitsChangeIcon", getChangeIcon(currentStat.getTotalVisits(), previousStat.getTotalVisits()));
        model.addAttribute("revenueChangeIcon", getChangeIcon(currentStat.getTotalRevenue().intValue(), previousStat.getTotalRevenue().intValue()));

        model.addAttribute("currentStat", currentStat);
        model.addAttribute("previousStat", previousStat);

        return "marketer/dashboard";
    }
    private String calculateChange(int current, int previous) {
        if (previous == 0) {
            return "";
        }
        double change = ((double) (current - previous) / previous) * 100;
        return String.format("%.1f%%", change);
    }

    private String getChangeIcon(int current, int previous) {
        if (previous == 0) {
            return "";
        }
        return (current >= previous) ? "bx bxs-up-arrow text-success" : "bx bxs-down-arrow text-danger";
    }

    @GetMapping("/registration-percentage")
    @ResponseBody
    public Map<String, Object> getRegistrationPercentage() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        MonthlyStatistic currentStat = monthlyStatisticService.getMonthlyStatistic(currentYear, currentMonth);

        int totalUsersRegistered = currentStat.getTotalUsersRegistered();
        int totalCoursesRegistered = currentStat.getTotalCoursesRegistered();

        double registrationPercentage = (totalUsersRegistered > 0)
                ? ((double) totalCoursesRegistered / totalUsersRegistered) * 100
                : 0;

        Map<String, Object> response = new HashMap<>();
        response.put("totalUsers", totalUsersRegistered);
        response.put("totalCourses", totalCoursesRegistered);
        response.put("registrationPercentage", registrationPercentage);

        return response;
    }

}
