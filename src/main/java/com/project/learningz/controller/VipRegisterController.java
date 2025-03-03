package com.project.learningz.controller;


import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.models.QueryStatusTransactionResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.processor.QueryTransactionStatus;
import com.project.learningz.config.MomoProperties;
import com.project.learningz.constant.Role;
import com.project.learningz.entity.Grade;
import com.project.learningz.entity.User;
import com.project.learningz.entity.UserMembership;
import com.project.learningz.entity.VipPackage;
import com.project.learningz.service.GradeService;
import com.project.learningz.service.MembershipService;
import com.project.learningz.service.UserService;
import com.project.learningz.service.VipPackageService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vip-packages")
public class VipRegisterController {
    @Autowired
    private VipPackageService vipPackageService;

    @Autowired
    private MomoProperties momoProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private MembershipService userMembershipService;
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public String showVipPackages(Model model,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                  @AuthenticationPrincipal OAuth2User userOAuth2
    ) {
        String username = null;
        if (user != null) {
            username = user.getUsername();
            model.addAttribute("user", user);
        } else if (userOAuth2 != null) {
            String email = userOAuth2.getAttribute("email");
            username = userService.findUserNameByEmail(email);
            model.addAttribute("user", userOAuth2);
        }
        model.addAttribute("username", username);
        String avatarUrl = userService.getAvtByUsername(username);
        model.addAttribute("avatarUrl", avatarUrl);
        List<Grade> grades = gradeService.getAllGrades();
        model.addAttribute("grades", grades);
        List<VipPackage> packages = vipPackageService.getAllVipPackages();
        model.addAttribute("vipPackages", packages);
        return "course/vip_packages";
    }

    @PostMapping("/momo-pay")
    public String payWithMomo(
            @RequestParam("id") int id,
            @RequestParam("amount") long amount,
            @RequestParam("packageName") String packageName,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            @AuthenticationPrincipal OAuth2User userOAuth2) {
        System.out.println("Received id: " + id);
        System.out.println("Received amount: " + amount);
        System.out.println("Received packageName: " + packageName);
        try {
            String username = null;
            if (user != null) {
                username = user.getUsername();
            } else if (userOAuth2 != null) {
                String email = userOAuth2.getAttribute("email");
                username = userService.findUserNameByEmail(email);
            }
            session.setAttribute("vipPackageId", id);
            PaymentResponse response = MomoPayment(id, amount, packageName);
            System.out.println("Momo Response: " + response);
            return "redirect:" + response.getPayUrl();
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error. Try again!");
            return "redirect:/vip-packages";
        }
    }


    public PaymentResponse MomoPayment(int id, long amount, String packageName) throws Exception {

        String requestId = String.valueOf(System.currentTimeMillis() + "learningz");
        String orderId = String.valueOf(System.currentTimeMillis() + "learningz");
        System.out.println("Sent orderId: " + orderId);
        String orderInfo = "LearningZ payment " + id;
        String returnURL = momoProperties.getReturnUrl();
        String notifyURL = momoProperties.getNotifyURL();
        Environment environment = Environment.selectEnv(momoProperties.getEnvironment());

        System.out.println("Debug: orderId=" + orderId + ", requestId=" + requestId);
        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);
        System.out.println("Transaction Status: " + queryStatusTransactionResponse.getMessage());

//        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
//        System.out.println("QR: " + captureWalletMoMoResponse.getQrCodeUrl());
//        System.out.println("Pay URL: " + captureWalletMoMoResponse.getPayUrl());
//        return captureWalletMoMoResponse;

        PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
        System.out.println("Momo Payment URL: " + captureATMMoMoResponse.getPayUrl());
        return captureATMMoMoResponse;
    }

    @GetMapping("/momo-callback")
    public String handleCallbackMomo(
            @RequestParam(value = "resultCode", required = false) String resultCode,
            @RequestParam(value = "orderId", required = false) String orderId,
            HttpSession session,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            @AuthenticationPrincipal OAuth2User userOAuth2,
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("MoMo Result Code: " + resultCode);
            System.out.println("Received orderId in callback: " + orderId);
            System.out.println("MoMo Callback Triggered: orderId=" + orderId + ", resultCode=" + resultCode);
            Integer vipPackageId = (Integer) session.getAttribute("vipPackageId");
            String username = null;
            if (user != null) {
                username = user.getUsername();
            } else if (userOAuth2 != null) {
                String email = userOAuth2.getAttribute("email");
                username = userService.findUserNameByEmail(email);
            }
            if ("0".equals(resultCode)) {
            //if(1==1){
                User userLoggin = userService.findByUsername(username);
                VipPackage vipPackage = vipPackageService.getVipPackageById(vipPackageId);

                userLoggin.setRole(Role.VIP_STUDENT);
                userService.save(userLoggin);
                UserMembership userMembership = new UserMembership();
                userMembership.setUser(userLoggin);
                userMembership.setVipPackage(vipPackage);
                userMembership.setRegistrationDate(LocalDate.now());
                userMembershipService.save(userMembership);

                redirectAttributes.addFlashAttribute("success", "Payment successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Payment failed");
            }
            return "redirect:/vip-packages";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment processing error!");
            return "redirect:/vip-packages";
        } finally {
            session.removeAttribute("vipPackageId");
        }
    }
}
