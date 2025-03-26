package com.project.learningz.controller;

import com.project.learningz.constant.MembershipStatus;
import com.project.learningz.constant.Role;
import com.project.learningz.entity.Course;
import com.project.learningz.entity.Grade;
import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import com.project.learningz.repository.UserRepository;
import com.project.learningz.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    int countUpdate = 0;
    int idReload = 0;
    @Autowired
    private UserService userService;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private UsersCourseService usersCourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private MembershipService membershipService;

    @GetMapping(path = "/home/profile")
    public String profile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        String username;
        String avatarUrl = null;
        String email = null;
        String phoneNumber = null;
        Role role = null;
        if (principal instanceof OAuth2User) {
            email = ((OAuth2User) principal).getAttribute("email");
            user = userService.findByEmail(email);
            role = user.getRole();
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            user = userService.findByUsername(username);
            if (user != null) {
                avatarUrl = user.getAvtUrl();
                email = user.getEmail();
                phoneNumber = user.getPhoneNum();
                role = user.getRole();
            }
        }
        if (countUpdate != 0 && idReload != 0) {
            user = userService.findById(idReload);
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
            email = user.getEmail();
            phoneNumber = user.getPhoneNum();
        }

        List<Integer> courseIdList = new ArrayList<>();
        courseIdList = usersCourseService.courseIdListByUserId(user.getId());
        List<Course> courseList = new ArrayList<>();
        if(!courseIdList.isEmpty()){
            for (Integer integer : courseIdList) {
                courseList.add(courseService.findByCourseId(integer));
            }
            model.addAttribute("courseList", courseList);
        }
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);

        List<UserMembership> userMemberships = membershipService.findUserMembershipByUserId(user.getId());
        model.addAttribute("userMemberships", userMemberships);
        UserMembership membership = new UserMembership();
        long dayMemberShipRemain;
        membership = membershipService.findByUserID(user.getId());
        if(membership != null){
            if(membership.getExpirationDate().isAfter(LocalDate.now())){
                dayMemberShipRemain = ChronoUnit.DAYS.between(LocalDate.now(),membership.getExpirationDate());
                model.addAttribute("dayMembershipRemain", dayMemberShipRemain);
            }
        }
        boolean isNormalStudent = userService.isNormalStudent(user.getId(), Role.STUDENT);
        model.addAttribute("isNormalStudent", isNormalStudent);

        model.addAttribute("membership", membershipService.findByUserID(user.getId()));
        model.addAttribute("courseIdList", courseIdList);
        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("email", email);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("role", role);
        model.addAttribute("user",user);
        return "profile/profile";
    }

    @GetMapping(path = "/home/profile/profile_edit")
    public String profile_edit(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        int userId = 0;
        String username;
        String avatarUrl = null;
        String email = null;
        String phoneNumber = null;
        if (principal instanceof OAuth2User) {
            email = ((OAuth2User) principal).getAttribute("email");
            user = userService.findByEmail(email);
            userId = user.getId();
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
            phoneNumber = user.getPhoneNum();
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            user = userService.findByUsername(username);
            if (user != null) {
                userId = user.getId();
                avatarUrl = user.getAvtUrl();
                email = user.getEmail();
                phoneNumber = user.getPhoneNum();
            }
        }
        if (countUpdate != 0 && idReload != 0) {
            user = userService.findById(idReload);
            userId = user.getId();
            username = user.getUsername();
            avatarUrl = user.getAvtUrl();
            email = user.getEmail();
            phoneNumber = user.getPhoneNum();
        }
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);

        model.addAttribute("username", username);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("userId", userId);
        model.addAttribute("user",user);
        return "profile/profile_edit";
    }

    @PostMapping(path = "/home/profile/profile_edit/update_user")
    public String updateProfile(Model model,
                                @RequestParam("userId") int id,
                                @RequestParam("avatarUrl") MultipartFile avatarFile,
                                @RequestParam("username") String username,
                                @RequestParam("phoneNumber") String phoneNumber,
                                HttpSession session) throws GeneralSecurityException, IOException {
        List<String> errors = new ArrayList<String>();
        errors = userService.userCheck(id, username, phoneNumber, avatarFile);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("username", username);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("userId", id);
            if (avatarFile != null) {
                model.addAttribute("avatarUrl", avatarFile.getOriginalFilename());
            } else {
                User user = userService.findById(id);
                model.addAttribute("avatarUrl", user.getAvtUrl());
            }
        } else {
            if (!avatarFile.isEmpty()) {
                User user = userService.findById(id);
                if (user.getAvtUrl() != null && user.getAvtUrl().contains("https://lh3.googleusercontent.com/d/")) {
                    String[] oldAvtId = user.getAvtUrl().split("https://lh3.googleusercontent.com/d/");
                    if (googleDriveService.fiLeExists(oldAvtId[1])) {
                        googleDriveService.deleteFile(oldAvtId[1]);
                    }
                }
                userService.updateUser(id, username, phoneNumber, avatarFile);
            }else{
                userService.updateUser(id, username, phoneNumber);
            }
            countUpdate++;
            idReload = id;

            session.setAttribute("countUpdate", countUpdate);
            session.setAttribute("idReload", idReload);

            List<Grade> grades = gradeService.getAllGrades();
            model.addAttribute("grades", grades);

            model.addAttribute("notification", "Update success");
            model.addAttribute("username", username);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("userId", id);
            model.addAttribute("avatarUrl", userService.getUserById(id).getAvtUrl());
            model.addAttribute("user",userService.getUserById(id));
        }
        return "profile/profile_edit";
    }

    @PostMapping("/home/profile/deactivateVipPackages")
    public String deactivateMembership(@RequestParam("membershipId") Integer membershipId, RedirectAttributes redirectAttributes) {
        try {
            UserMembership membership = membershipService.findById(membershipId);
            if (membership != null && membership.getStatus() == MembershipStatus.ACTIVE) {
                User user = membership.getUser();
                membership.setStatus(MembershipStatus.CANCELED);
                membershipService.save(membership);
                boolean stillHasVIP = membershipService.hasActiveOrCancelableVIP(user.getId());
                if (!stillHasVIP) {
                    user.setRole(Role.STUDENT);
                    userService.save(user);
                }
                redirectAttributes.addFlashAttribute("success", "Membership deactivated successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Membership cannot be deactivated.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while deactivating the membership.");
        }
        return "redirect:/home/profile";
    }

}