package com.bjd.demo.controller;

import com.bjd.demo.dto.image.ImageDto;
import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.dto.ticket.TicketDto;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.service.image.ImageService;
import com.bjd.demo.service.route.RouteService;
import com.bjd.demo.service.ticket.TicketService;
import com.bjd.demo.service.user.UserService;
import com.bjd.demo.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUser;
import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;
import static com.bjd.demo.util.UtilConst.AUTHORIZATION;

@Slf4j
@Controller
@RequestMapping("/bjd")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final RouteService routeService;
    private final TicketService ticketService;
    private final ImageService imageService;

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) throws UnsupportedEncodingException {
        UserDto currentUser = getCurrentUser();
        String image = imageService.getImageByUserId(currentUser.getId());
        model.addAttribute("image", image);
        model.addAttribute("findRoute", new FindRouteDto());
        model.addAttribute("user", currentUser);
        return "/dashboard";
    }

    @GetMapping(value = "/waitlist")
    public String waitList(Model model) {
        List<RouteDto> routeDtoList = routeService.findAll();
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("schedule", routeDtoList);
        return "/waitlist";
    }

    @GetMapping(value = "/users")
    public String customers(Model model) {
        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("users", userDtoList);
        return "/users";
    }

    @GetMapping(value = "/railway_stations")
    public String products(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "/railway_stations";
    }

    @GetMapping(value = "/contacts")
    public String knowledgeBase(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "/contacts";
    }

    @GetMapping(value = "/tickets")
    public String agents(Model model) {
        Long userId = getCurrentUserId();
        List<TicketDto> ticketDtoList = ticketService.findAllByUserId(userId);
        model.addAttribute("tickets", ticketDtoList);
        model.addAttribute("user", getCurrentUser());
        return "/tickets";
    }

    @GetMapping(value = "/signout")
    public String signOut(HttpServletRequest request) {
        request.getSession().removeAttribute(AUTHORIZATION);
        return "redirect:/user/signin";
    }
}
