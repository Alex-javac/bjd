package com.bjd.demo.controller;

import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.service.route.RouteService;
import com.bjd.demo.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final RouteService routeService;

    @PostMapping(value = "/buy")
    public String buy(@ModelAttribute("user") UserDto userDto) {
        log.info("MainController.signUp() run...");
        log.info("user: {}", userDto);
        return "redirect:/bjd/signin";
    }

    @PostMapping(value = "/find")
    public String find(@ModelAttribute("findRoute") FindRouteDto findRoute, Model model) {
        List<RouteDto> routeDtoList = routeService.find(findRoute);
        model.addAttribute("routes", routeDtoList);
        return "/dashboard";
    }
}
