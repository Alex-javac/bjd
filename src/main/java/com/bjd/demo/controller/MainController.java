package com.bjd.demo.controller;

import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.dto.ticket.TicketDto;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.service.route.RouteService;
import com.bjd.demo.service.ticket.TicketService;
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
import java.util.List;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;
import static com.bjd.demo.util.UtilConst.AUTHORIZATION;
import static com.bjd.demo.util.UtilConst.BEARER;

@Slf4j
@Controller
@RequestMapping("/bjd")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final RouteService routeService;
    private final TicketService ticketService;

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("findRoute", new FindRouteDto());
        return "/dashboard";
    }

    @GetMapping(value = "/waitlist")
    public String waitList(Model model) {
        List<RouteDto> routeDtoList = routeService.findAll();
        model.addAttribute("schedule", routeDtoList);
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
    public String agents(Model model) {
        Long userId = getCurrentUserId();
        List<TicketDto> ticketDtoList = ticketService.findAllByUserId(userId);
        model.addAttribute("tickets", ticketDtoList);
        return "/tickets";
    }

    @GetMapping(value = "/signout")
    public String signOut(HttpServletRequest request) {
        request.getSession().removeAttribute(AUTHORIZATION);
        return "redirect:/bjd/signin";
    }

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
        log.info("MainController.login() run...");
        log.info("email: {}, password: {}", loginForm.getEmail(), loginForm.getPassword());
        UserSignInResponseDto userSignInResponseDto = userService.signIn(loginForm);
        request.getSession().setAttribute(AUTHORIZATION, BEARER + userSignInResponseDto.getAccessToken().getAccessToken());
        return "redirect:/bjd/dashboard";
    }

    @PostMapping(value = "/signup")
    public String signUp(@ModelAttribute("user") UserDto userDto) {
        log.info("MainController.signUp() run...");
        log.info("user: {}", userDto);
        userService.saveUser(userDto);
        return "redirect:/bjd/signin";
    }
}
