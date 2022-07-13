package com.bjd.demo.controller;

import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequestMapping("/bjd")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "/dashboard";
    }

    @GetMapping(value = "/waitlist")
    public String waitList() {
        return "/waitlist";
    }

    @GetMapping(value = "/users")
    public String customers(Model model) {
        return "/users";
    }
    @GetMapping(value = "/railway_stations")
    public String products() {
        return "/railway_stations";
    }

    @GetMapping(value = "/contacts")
    public String knowledgeBase() {
        return "/contacts";
    }

    @GetMapping(value = "/tickets")
    public String agents() {
        return "/tickets";
    }

    @GetMapping(value = "/signout")
    public String signOut() {
        return "redirect:/bjd/signin";
    }

    @GetMapping(value = "/signin")
    public String signIn(Model model) {
        model.addAttribute("loginForm",new LoginForm());
        return "/login";
    }

    @GetMapping(value = "/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new UserDto());
        return "/registration";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm) {
        log.info("MainController.login() run...");
        log.info("email: {}, password: {}", loginForm.getEmail(), loginForm.getPassword());

        return "redirect:/bjd/dashboard";
    }

    @PostMapping(value = "/signup")
    public String signUp(@ModelAttribute("user") UserDto userDto) {
        log.info("MainController.signUp() run...");
        log.info("user: {}", userDto);
        return "redirect:/bjd/signin";
    }
}
