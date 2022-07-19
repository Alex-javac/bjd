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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;

@Slf4j
@Controller
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final RouteService routeService;

    @PostMapping(value = "/buy/{routeId}")
    public String buy(@PathVariable("routeId") Long routeId, Model model) {
        log.info("TicketController.buy() run...");
        Long userId = getCurrentUserId();
        log.info("routeId: {}, userId: {}", routeId, userId);
        ticketService.create(routeId,userId);
        return "redirect:/bjd/tickets";
    }

    @PostMapping(value = "/find")
    public String find(@ModelAttribute("findRoute") FindRouteDto findRoute, Model model) {
        List<RouteDto> routeDtoList = routeService.find(findRoute);
        model.addAttribute("routes", routeDtoList);
        return "/dashboard";
    }

    @GetMapping(value = "/{routeId}")
    public String find(@PathVariable("routeId") Long routeId, Model model) {
       RouteDto routeDto = routeService.findById(routeId);
       model.addAttribute("route", routeDto);
        return "/purchase";
    }

    @PostMapping(value = "/delete/{ticketId}")
    public String delete(@PathVariable("ticketId") Long ticketId) {
        log.info("TicketController.delete() run...");
        log.info("ticketId: {}", ticketId);
        ticketService.delete(ticketId);
        return "redirect:/bjd/tickets";
    }
}
