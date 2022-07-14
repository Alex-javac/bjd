package com.bjd.demo.service.route;

import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;

import java.util.List;

public interface RouteService {
  List<RouteDto> find(FindRouteDto findRouteDto);
}
