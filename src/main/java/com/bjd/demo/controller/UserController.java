package com.bjd.demo.controller;

import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;
import static com.bjd.demo.util.UtilConst.AUTHORIZATION;
import static com.bjd.demo.util.UtilConst.BEARER;
import static java.util.Objects.nonNull;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/signin")
    public String signIn(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "/login";
    }

    @GetMapping(value = "/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new UserDto());
        return "/registration";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, @ModelAttribute("loginForm") LoginForm loginForm) {
        log.info("UserController.login() run...");
        log.info("email: {}, password: {}", loginForm.getEmail(), loginForm.getPassword());
        UserSignInResponseDto userSignInResponseDto = userService.signIn(loginForm);
        request.getSession().setAttribute(AUTHORIZATION, BEARER + userSignInResponseDto.getAccessToken().getAccessToken());
        return "redirect:/bjd/dashboard";
    }

    @PostMapping(value = "/signup")
    public String signUp(@ModelAttribute("user") UserDto userDto) {
        log.info("UserController.signUp() run...");
        log.info("user: {}", userDto);
        userService.saveUser(userDto);
        return "redirect:/user/signin";
    }

    @GetMapping(value = "/update")
    public String update(Model model) {
        model.addAttribute("user", new UserDto());
        return "/update";
    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("user") UserDto userDto,Model model) {
        log.info("UserController.update() run...");
        log.info("user: {}", userDto);
        Long currentUserId = getCurrentUserId();
        UserDto updatedUser = userService.update(currentUserId, userDto);
        if((nonNull(userDto.getEmail())&&!userDto.getEmail().isEmpty())||(nonNull(userDto.getPassword())&&!userDto.getPassword().isEmpty())){
            model.addAttribute("loginForm", new LoginForm());
            return "/login";
        }
        model.addAttribute("findRoute", new FindRouteDto());
        model.addAttribute("user", updatedUser);
        return "/dashboard";
    }

    @GetMapping(value = "/delete")
    public String deletionConfirmation() {
        return "/delete";
    }

    @PostMapping(value = "/delete")
    public String delete() {
        log.info("UserController.delete() run...");
        Long currentUserId = getCurrentUserId();
        log.info("currentUserId: {}", currentUserId);
        userService.deleteUser(currentUserId);
        return "/index";
    }
}
