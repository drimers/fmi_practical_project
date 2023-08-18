package com.stefanpetkov.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {


    @RequestMapping({"", "/", "/home"})
    public String home() {
//        Long userId = SecurityUtil.getInstance().getLoggedUserId();
//        Long currentUserId = SecurityUtil.getInstance().getLoggedUserId(SecurityContextHolder.getContext().getAuthentication());
//        ApplicationUserRole userRole = SecurityUtil.getInstance().getLoggedUserRole();
//        log.info("Logged user id={}", userId);
//        log.info("Logged user id={}", currentUserId);
//        log.info("Logged user role={}", userRole);
        return "home/home";
    }

}
