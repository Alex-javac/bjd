package com.bjd.demo.controller;

import com.bjd.demo.dto.route.CreateRouteDto;
import com.bjd.demo.service.route.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PostMapping(value = "/delete/{routeId}")
    public String delete(@PathVariable("routeId") Long routeId) {
        log.info("RouteController.delete() run...");
        log.info("routeId: {}", routeId);
        routeService.delete(routeId);
        return "redirect:/bjd/waitlist";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("route", new CreateRouteDto());
        return "/create_route";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute("route") CreateRouteDto route) {
        routeService.create(route);
        return "redirect:/bjd/waitlist";
    }
}
