package com.bjd.demo.service.route;

import com.bjd.demo.dto.route.CreateRouteDto;
import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;

import java.util.List;

public interface RouteService {
    List<RouteDto> find(FindRouteDto findRouteDto);

    List<RouteDto> findAll();

    RouteDto findById(Long ticketId);
    RouteDto create(CreateRouteDto routeDto);

    void reloadFileCsv();

    void reloadFilePdf();

    List<String[]> prepareRows();

    void delete(Long routeId);
}
