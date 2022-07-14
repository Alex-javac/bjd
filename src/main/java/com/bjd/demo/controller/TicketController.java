package com.bjd.demo.controller;

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
   @GetMapping(value = "/signup")
   public String find(Model model) {
      model.addAttribute("user", new UserDto());
      return "/registration";
   }
}
