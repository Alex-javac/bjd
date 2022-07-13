package com.bjd.demo.service.route;

import com.bjd.demo.mapper.RouteMapper;
import com.bjd.demo.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

}
